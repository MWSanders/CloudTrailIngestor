package edu.mines.utils;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.MongoClient;
import edu.mines.dao.MergedServiceManager;
import edu.mines.model.*;
import org.apache.commons.collections4.CollectionUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.*;

/**
 * Created by Matt on 10/8/2015.
 * Evaluate privileges used vs policies normalized pulled from IAM and generate PrivilegesMetrics
 */
public class MetricsGenerator {
    private static ObjectMapper mapper = new ObjectMapper();
    private static MergedServiceManager serviceManager = MergedServiceManager.getInstance();
    private NavigableMap<String, ExistingEntity> existingEntityMap = new TreeMap<>();
    private NavigableMap<String, EntitySummary> entitySummaryMap = new TreeMap<>();
    private NavigableMap<String, PolicyHolder> policyHolderMap = new TreeMap<>();
    private NavigableMap<String, Service> serviceMap = new TreeMap<>();

    private MongoClient mongo = DatastoreFactory.getMongoClient();
    private Morphia morphia = new Morphia();
    private MyPolicyReader reader = new MyPolicyReader();
    private Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());

    private Set<String> unauditedServices = new HashSet<>();

    public MetricsGenerator() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        morphia.map(PrivilegesMetric.class).map(ExistingRole.class).map(ServiceMap.class).map(ServiceSummary.class)
                .map(EntitySummary.class).map(Service.class);

        Query<ExistingRole> query = ds.createQuery(ExistingRole.class);
        List<ExistingRole> existingRolesList = query.asList();
        existingRolesList.forEach(existingRole -> existingEntityMap.put(existingRole.getName(), existingRole));

        Query<ExistingUser> userQuery = ds.createQuery(ExistingUser.class);
        List<ExistingUser> existingUserList = userQuery.asList();
        existingUserList.forEach(existingUser -> existingEntityMap.put(existingUser.getName(), existingUser));


        Query<ServiceMap> serviceMapQuery = ds.createQuery(ServiceMap.class);
        List<ServiceMap> services = serviceMapQuery.asList();
        serviceManager.getIamNamedService().forEach((s, mergedService) -> {
            if (mergedService.getAuditActionLevel().equals(Service.AuditActionLevel.NONE))
                unauditedServices.add(mergedService.getIamName());
        });

        Query<EntitySummary> entityQuery = ds.createQuery(EntitySummary.class);
        List<EntitySummary> userSummaries = entityQuery.asList();
        userSummaries.forEach(userSummary -> entitySummaryMap.put(userSummary.getId(), userSummary));

        Query<PolicyHolder> policyHolderQuery = ds.createQuery(PolicyHolder.class);
        List<PolicyHolder> policyHolders = policyHolderQuery.asList();
        policyHolders.forEach(policyHolder -> policyHolderMap.put(policyHolder.getUserName(), policyHolder));

        Query<Service> serviceQuery = ds.createQuery(Service.class);
        List<Service> serviceList = serviceQuery.asList();
        serviceList.forEach(service -> serviceMap.put(service.getIAMName(), service));
    }

    public static void main(String... args) throws Exception {
        MetricsGenerator metricsGenerator = new MetricsGenerator();
        metricsGenerator.process();
    }

    private boolean isAuditable(String priv) {
        String serviceName = priv.split(":")[0];
        String actionName = priv.split(":")[1];
        return serviceManager.isAuditable(serviceName, actionName);
    }


    private void process() throws Exception {
        System.out.println("entityName,type,originalGrantedPrivs,usedPrivs,unusedPrivs,newGrantedPrivs,eleminatedPrivs,resourceOriginalGrantedPrivs" +
                ",resourceUsedPrivs,resourceUnusedPrivs,resourceNewGrantedPrivs,policyStatements,originalGrantedServices,usedServices,unusedServices,newGrantedServices," +
                "grantedAuditableActions,grantedAuditableServices");
        for (EntitySummary entitySummary : entitySummaryMap.values()) {
            PrivilegesMetric privilegesMetric = new PrivilegesMetric();
            privilegesMetric.setName(entitySummary.getId());
            privilegesMetric.setType(entitySummary.getType());

            //Calculate NewGranted privs from generated policies
            PolicyHolder policyHolder = policyHolderMap.get(entitySummary.getId());
            Policy policyString = policyHolder.getPolicy();
            Policy policy = policyHolder.getPolicy();// reader.createPolicyFromJsonString(policyString);
            for (Statement statement : policy.getStatements()) {
                privilegesMetric.getStatements().add(statement);
                //Calculate actions and services used
                for (com.amazonaws.auth.policy.Action action : statement.getActions()) {
                    privilegesMetric.getNewGrantedPrivs().add(action.getActionName());
                    String serviceName = action.getActionName().split(":")[0];
                    privilegesMetric.getNewGrantedServices().add(serviceName);
                }
                //Calculate privs newly granted to resource-constrained service
                for (Resource r : statement.getResources()) {
                    //Set<String> actions = getActionsSet(privilegesMetric.getResourceNewGrantedPrivs(), r.getId());
                    for (com.amazonaws.auth.policy.Action action : statement.getActions()) {
                        String serviceName = action.getActionName().split(":")[0];
                        addToResourceActionSet(privilegesMetric.getResourceNewGrantedPrivs(), r.getId(), serviceName, action.getActionName());
                    }
                }
            }

            //Calculate the privileges actually used
            for (String serviceName : entitySummary.getRequests().keySet()) {
                ServiceSummary serviceLevelEntitySummary = entitySummary.getRequests().get(serviceName);
                for (String actionName : serviceLevelEntitySummary.getRequests().keySet()) {
                    // Add to non-resource-constrained set, do not count service:action pairs which cannot be audited
                    if (serviceManager.isAuditable(serviceName, actionName)) {
                        privilegesMetric.getUsedPrivs().add(serviceName + ":" + actionName);
                        privilegesMetric.getUsedServices().add(serviceName);
                    }
                    //Calculate resource-constrained group, do not add actions which cannot be resource level controlled
                    if (serviceMap.containsKey(serviceName) &&
                            !serviceMap.get(serviceName).getResourceLevelControlled().equals(Service.ResourceControlLevel.NONE)) {
                        ActionSummary actionSummary = serviceLevelEntitySummary.getRequests().get(actionName);
                        if (actionSummary.getCalpActionResources().isEmpty()) {
                            //Action is not associated with a resource
                            String key = "*";
                            Set<String> actions = getActionsSet(privilegesMetric.getResourceUsedPrivs(), key);
                            actions.add(serviceName + ":" + actionName);
                        } else {
                            //Add action under associated resource
                            for (String s : actionSummary.getCalpActionResources()) {
                                Set<String> actions = getActionsSet(privilegesMetric.getResourceUsedPrivs(), s);
                                actions.add(serviceName + ":" + actionName);
                            }
                        }
                    }
                }
            }

            //If the original Role/User has not been deleted then it exists in the existingEntityMap and we can calculate Granted Action/Service metrics
            if (existingEntityMap.containsKey(entitySummary.getId())) {
                //Normalize the list of originally granted privs
                privilegesMetric.setResourceConstrainedMetrics(true);
                ExistingEntity existingRole = existingEntityMap.get(entitySummary.getId());
                for (String originalPolicyString : existingRole.getNormalizedPolicies()) {
                    Policy originalPolicy = reader.createPolicyFromJsonString(originalPolicyString);
                    for (Statement statement : originalPolicy.getStatements()) {
                        for (Resource r : statement.getResources()) {
                            //Set<String> actions = getActionsSet(privilegesMetric.getResourceOriginalGrantedPrivs(), r.getId());
                            for (com.amazonaws.auth.policy.Action action : statement.getActions()) {
                                // Add to non-resource-constrained set
                                privilegesMetric.getOriginalGrantedPrivs().add(action.getActionName());
                                // Calculate resource-constrained set
                                String serviceName = action.getActionName().split(":")[0];
                                privilegesMetric.getOriginalGrantedServices().add(serviceName);
                                addToResourceActionSet(privilegesMetric.getResourceOriginalGrantedPrivs(), r.getId(), serviceName, action.getActionName());
                                //if auditable
                                if (isAuditable(action.getActionName())) {
                                    privilegesMetric.getOriginalGrantedAuditPrivs().add(action.getActionName());
                                    privilegesMetric.getOriginalGrantedAuditServices().add(serviceName);
                                } else {
                                    privilegesMetric.getOriginalGrantedInauditPrivs().add(action.getActionName());
                                    privilegesMetric.getOriginalGrantedInauditServices().add(serviceName);
                                }
                            }
                        }
                    }
                }

                //for (String actionName : privilegesMetric.getOriginalGrantedPrivs()) {
                for (String actionName : privilegesMetric.getOriginalGrantedAuditPrivs()) {
                    //Calculate OriginallyGranted-used = unused privs
                    if (!privilegesMetric.getUsedPrivs().contains(actionName)) {
                        privilegesMetric.getUnusedPrivs().add(actionName);
                    }
                    //Calculate OriginallyGranted-NewGranted = eleminated privs
                    if (!privilegesMetric.getNewGrantedPrivs().contains(actionName)) {
                        privilegesMetric.getEleminatedPrivs().add(actionName);
                    }
                }

                for (String serviceName : privilegesMetric.getOriginalGrantedServices()) {
                    //Calculate grantedService-used = unused service
                    if (!privilegesMetric.getUsedServices().contains(serviceName)) {
                        privilegesMetric.getUnusedServices().add(serviceName);
                    }
                }

                //Calculated granted but unused resource constrained privs
                for (String resourceName : privilegesMetric.getResourceOriginalGrantedPrivs().keySet()) {
                    Set<String> originalResourceSet = privilegesMetric.getResourceOriginalGrantedPrivs().get(resourceName);
                    Set<String> usedResourceSet = privilegesMetric.getResourceUsedPrivs().get(resourceName);
                    if (originalResourceSet != null && usedResourceSet != null) {
                        Collection<String> unusedResourceCollection = CollectionUtils.subtract(originalResourceSet, usedResourceSet);
                        if (unusedResourceCollection.size() > 0) {
                            Set<String> unusedResourceSet = new TreeSet<>();
                            unusedResourceSet.addAll(unusedResourceCollection);
                            privilegesMetric.getResourceUnusedPrivs().put(resourceName, unusedResourceSet);
                        }
                    }
                }

                //Calculate granted but eleminated resource constrained privs
                for (String resourceName : privilegesMetric.getResourceOriginalGrantedPrivs().keySet()) {
                    Set<String> originalResourceSet = privilegesMetric.getResourceOriginalGrantedPrivs().get(resourceName);
                    Set<String> grantedSet = privilegesMetric.getResourceNewGrantedPrivs().get(resourceName);
                    if (originalResourceSet != null && grantedSet != null) {
                        Collection<String> eleminatedResourceCollection = CollectionUtils.subtract(originalResourceSet, grantedSet);
                        if (eleminatedResourceCollection.size() > 0) {
                            Set<String> eleminatedResourceSet = new TreeSet<>();
                            eleminatedResourceSet.addAll(eleminatedResourceCollection);
                            privilegesMetric.getResourceUnusedPrivs().put(resourceName, eleminatedResourceSet);
                        }
                    }
                }
            } else {
                //System.out.println("Entity not found in existing entites: " + entitySummary.getId());
            }

            //System.out.println("Saving: " + privilegesMetric.getId());
            System.out.println(privilegesMetric.getName() + "," + privilegesMetric.getType() + "," + privilegesMetric.getOriginalGrantedPrivs().size() + "," +
                    privilegesMetric.getUsedPrivs().size() + "," + privilegesMetric.getUnusedPrivs().size() + "," + privilegesMetric.getNewGrantedPrivs().size() + "," +
                    privilegesMetric.getEleminatedPrivs().size() + "," + privilegesMetric.getResourceOriginalGrantedPrivs().size() + "," +
                    privilegesMetric.getResourceUsedPrivs().size() + "," + privilegesMetric.getResourceUnusedPrivs().size() + "," +
                    privilegesMetric.getResourceNewGrantedPrivs().size() + "," + privilegesMetric.getStatements().size() + "," + privilegesMetric.getOriginalGrantedServices().size() + "," +
                    privilegesMetric.getUsedServices().size() + "," + privilegesMetric.getUnusedServices().size() + "," + privilegesMetric.getNewGrantedServices().size()
                    + "," + privilegesMetric.getOriginalGrantedAuditPrivs().size() + "," + privilegesMetric.getOriginalGrantedAuditServices().size());
            ds.save(privilegesMetric);
        }
    }

    private void addToResourceActionSet(Map<String, Set<String>> privsMap, String resourceName, String serviceName, String serviceActionName) {
        //Only add actions for resource controlled services
//        if (serviceMap.containsKey(serviceName) &&
//                !serviceMap.get(serviceName).getResourceLevelControlled().equals(Service.ResourceControlLevel.NONE)) {
            Set<String> actions = new TreeSet<>();
            String resrouceKey = MetricListKey.makeKey(resourceName);
            if (privsMap.containsKey(resrouceKey)) {
                actions = privsMap.get(resrouceKey.toString());
            } else {
                privsMap.put(resrouceKey.toString(), actions);
            }
            actions.add(serviceActionName);
//        }
//        return false;
    }

    private Set<String> getActionsSet(Map<String, Set<String>> privsMap, String key) {
        Set<String> actions = new TreeSet<>();
        String newKey = MetricListKey.makeKey(key);
        if (privsMap.containsKey(newKey)) {
            actions = privsMap.get(newKey.toString());
        } else {
            privsMap.put(newKey.toString(), actions);
        }
        return actions;
    }
}
