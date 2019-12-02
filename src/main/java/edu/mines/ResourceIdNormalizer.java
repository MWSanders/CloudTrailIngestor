package edu.mines;

import com.google.common.base.CaseFormat;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import edu.mines.model.Action;
import edu.mines.model.DocumentConverter;
import edu.mines.model.Service;
import edu.mines.model.ServiceLevelArn;
import edu.mines.utils.DatastoreFactory;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.*;

/**
 * Created by Matt on 12/14/2017.
 * <p>
 * Generates normalized ARN for all resources within a CloudTrail log entry
 */
public class ResourceIdNormalizer {

    private static Config config = ConfigFactory.load();
    private static Map<String, Service> resourceControlledServiceMap = new HashMap<>();
    private static Datastore ds = DatastoreFactory.getDatastore(new DocumentConverter());

    static {
        Query<Service> query = ds.createQuery(Service.class);
        List<Service> resourceControlledServices = query.asList();
        resourceControlledServices.forEach(service -> resourceControlledServiceMap.put(service.getId(), service));
    }

    public static Set<String> normalizeResourceIdentifier(Map<String, Object> actionDoc, String serviceName, String actionName, boolean isDenied) {
        Set<String> results = new HashSet<>();
        if (actionDoc == null || actionDoc.size() == 0)
            return results;
        // Only add ARN restrictions for services of ResourceControl type ALL or SOME
        if (resourceControlledServiceMap.containsKey(serviceName) &&
                (resourceControlledServiceMap.get(serviceName).getResourceLevelControlled() != null) &&
                !resourceControlledServiceMap.get(serviceName).getResourceLevelControlled().equals(Service.ResourceControlLevel.NONE)) {
            Service service = resourceControlledServiceMap.get(serviceName);
            if (service.getResourceLevelControlled().equals(Service.ResourceControlLevel.ALL)) {
                for (ServiceLevelArn serviceLevelArn : service.getServiceLevelARNs()) {
                    Set<String> resources = evaluateRequestParamsRecursively(actionDoc, serviceLevelArn);
                    results.addAll(resources);
                }
            } else if (service.getResourceLevelControlled().equals(Service.ResourceControlLevel.SOME)) {
                if (service.getActions().containsKey(actionName)) {
                    Action action = service.getActions().get(actionName);
                    for (String resourcePattern : action.getResources()) {
                        String patternParts[] = resourcePattern.split(",");
                        String endofPattern = patternParts[patternParts.length - 1];
                        if (!endofPattern.equals("*")) {
                            String camelResourceId = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, endofPattern);
                            String populatedArnPattern = populateArnParameters(patternParts[0]);
                            Set<String> resources = evaluateRequestParamsRecursively(actionDoc, serviceName, actionName, camelResourceId, populatedArnPattern);
                            if (resources.size() > 0)
                                results.addAll(resources);
                        }
                    }
                }
            }
        }
        return results;
    }

    private static Set<String> evaluateRequestParamsRecursively(Map<String, Object> actionDoc, ServiceLevelArn serviceLevelArn) {
        Set<String> clonedKeys = new HashSet<>(actionDoc.keySet());
        Set<String> results = new HashSet<>();
        String controlledAttributeName = serviceLevelArn.getAttributeName();
        for (String attributeName : clonedKeys) {
            if (actionDoc.get(attributeName) instanceof Map) {
                Map<String, Object> nextDoc = (Map) actionDoc.get(attributeName);
                results.addAll(evaluateRequestParamsRecursively(nextDoc, serviceLevelArn));
            } else if (actionDoc.get(attributeName) instanceof List) {
                for (Object doc : (List) actionDoc.get(attributeName)) {
                    if (doc instanceof Map || doc instanceof List) {
                        results.addAll(evaluateRequestParamsRecursively((Map) doc, serviceLevelArn));
                    } else if (doc instanceof String && attributeName.equals(controlledAttributeName)) {
                        if (((String) doc).startsWith("arn"))
                            results.add(doc.toString());
                        else {
                            String pattern = serviceLevelArn.getArnPattern();
                            String result = populateArnParameters(pattern, controlledAttributeName, (String) doc);
                            results.add(result);
                        }
                    }
                }
            } else if (actionDoc.get(attributeName) instanceof String) {
                // If its an ARN we take it as is
                if (controlledAttributeName.equalsIgnoreCase(attributeName) && ((String) actionDoc.get(attributeName)).startsWith("arn")) {
                    results.add((String) actionDoc.get(attributeName));
                }
                // If not an ARN, take the unique ID and put it in ARN form.
                else if (controlledAttributeName.equalsIgnoreCase(attributeName)) {
                    String pattern = serviceLevelArn.getArnPattern();
                    String result = populateArnParameters(pattern, controlledAttributeName, (String) actionDoc.get(attributeName));
                    results.add(result);
                }
            }
        }
        return results;
    }

    private static Set<String> evaluateRequestParamsRecursively(Map<String, Object> actionDoc, String serviceName, String actionName,
                                                                String camelResourceId, String resourcePattern) {
        Set<String> clonedKeys = new HashSet<>(actionDoc.keySet());
        Set<String> results = new HashSet<>();
        for (String attributeName : clonedKeys) {
            if (actionDoc.get(attributeName) instanceof Map) {
                Map<String, Object> nextDoc = (Map) actionDoc.get(attributeName);
                results.addAll(evaluateRequestParamsRecursively(nextDoc, serviceName, actionName, camelResourceId, resourcePattern));
            } else if (actionDoc.get(attributeName) instanceof List) {
                for (Object doc : (List) actionDoc.get(attributeName)) {
                    if (doc instanceof Document) {
                        results.addAll(evaluateRequestParamsRecursively((Map) doc, serviceName, actionName, camelResourceId, resourcePattern));
                    } else if (doc instanceof String && attributeName.equals(camelResourceId)) {
                        results.add(doc.toString());
                    }
                }
            } else if (actionDoc.get(attributeName) instanceof String) {
                if (camelResourceId.equalsIgnoreCase(attributeName)) {
                    results.add(resourcePattern + "/" + (String) actionDoc.get(attributeName));
                }
            }
        }
        return results;
    }

    private static String populateArnParameters(String arnPattern, String variable, String value) {
        String replacedRegion = StringUtils.replace(arnPattern, "$region", config.getString("region"));
        String replaceAccount = StringUtils.replace(replacedRegion, "$account-id", config.getString("account"));
        String replaceVar = StringUtils.replace(replaceAccount, "$" + variable, value);
        return replaceVar;
    }

    private static String populateArnParameters(String arnPattern) {
        String replacedRegion = StringUtils.replace(arnPattern, "region", config.getString("region"));
        String replaceAccount = StringUtils.replace(replacedRegion, "account", config.getString("account"));
        return replaceAccount;
    }

    public static void main(String... args) {
        MongoCollection<Document> eventCollection = DatastoreFactory.getEventCollection();
        BasicDBObject query = new BasicDBObject();
        query.append("_id", "93e22d9d-a9f3-11e7-ad87-81e47a40d31c-91b80835-1887-4852-a2fc-6416ae17dc6f");
        Document document = eventCollection.find(query).first();
        String serviceName = document.getString("eventSource");
        serviceName = serviceName.split("\\.")[0];
        String actionName = document.getString("eventName");
        System.out.println(normalizeResourceIdentifier(document, serviceName, actionName, false));

    }
}
