package edu.mines;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import edu.mines.dao.MergedServiceManager;
import edu.mines.dao.SegmentedEntityDao;
import edu.mines.dao.SegmentedResourceDao;
import edu.mines.model.*;
import edu.mines.model.config.Relationship;
import edu.mines.utils.DatastoreFactory;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;

import static java.util.Arrays.asList;

/**
 * Created by Matt on 8/20/2015.
 * Read CloudTrail events from Mongo, organize them by User->Sevice->Action->Requests.  This organization makes it
 * easier to build policies later and removes unused data about events.
 */
public class ActionNormalizer {

    private static ObjectMapper mapper = new ObjectMapper();
    private static Config config = ConfigFactory.load();
    private static MongoCollection<Document> eventCollection = DatastoreFactory.getEventCollection();
    private static Map<String, Service> resourceControlledServiceMap = new HashMap<>();
    private static Datastore ds = DatastoreFactory.getDatastore(new DocumentConverter());
    private ZonedDateTime startDate, endDate;
    private long duration;
    private static String envName = config.getString("env.name");
    private static MergedServiceManager serviceManager = MergedServiceManager.getInstance();
    private static SegmentedResourceDao segmentedResourceDao = SegmentedResourceDao.getInstance();
    private static SegmentedEntityDao segmentedEntityDao = SegmentedEntityDao.getInstance();
    private static OperationExercisedActionsDAO operationExercisedActionsDAO = OperationExercisedActionsDAO.getInstance();
    private String timeKey;
    private Map<String, edu.mines.model.config.Config> ec2InstanceMap;
    private Map<String, edu.mines.model.config.Config> ec2VolumeMap;

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

        Morphia morphia = new Morphia();
        morphia.map(Service.class).map(edu.mines.model.Action.class).map(ActionSummary.class).map(ServiceSummary.class)
                .map(EntitySummary.class).map(ServiceLevelArn.class).map(OperationExercisedActions.class).map(DocumentPair.class);
        Query<Service> query = ds.createQuery(Service.class);
        List<Service> resourceControlledServices = query.asList();
        resourceControlledServices.forEach(service -> resourceControlledServiceMap.put(service.getId(), service));

        DBCollection actionCollection = ds.getDB().getCollection(config.getString("Collection.Name.Events"));
        actionCollection.createIndex("eventName");
        actionCollection.createIndex("eventSource");
        actionCollection.createIndex("eventTime");
        actionCollection.createIndex(new BasicDBObject("userIdentity.type", 1)
                .append("eventTime", 1)
                .append("errorCode", 1));
    }

    public ActionNormalizer(ZonedDateTime startDate, ZonedDateTime endDate) {
        if (startDate == null) {
            this.startDate = ZonedDateTime.parse(config.getString("env.startDate"));
        } else {
            this.startDate = startDate;
        }

        if (endDate == null)
            this.endDate = ZonedDateTime.parse(config.getString("env.startDate"));
        else
            this.endDate = endDate;
        this.duration = Duration.between(this.startDate, this.endDate).toDays();
        timeKey = OperationExercisedActions.getKey(this.startDate.toString(), this.endDate.toString());
        ec2InstanceMap = segmentedResourceDao.getSegResourceMap(timeKey, "AWS::EC2::Instance");
        ec2VolumeMap = segmentedResourceDao.getSegResourceMap(timeKey, "AWS::EC2::Volume");
    }

    public static void main(String... args) throws Exception {
        ActionNormalizer actionNormalizer = null;
        if (args.length == 2)
            actionNormalizer = new ActionNormalizer(ZonedDateTime.parse(args[0]), ZonedDateTime.parse(args[1]));
        else
            actionNormalizer = new ActionNormalizer(null, null);
        actionNormalizer.process();
    }


    public static void populateOperationExercisedActionsCollection(ZonedDateTime startZ, ZonedDateTime endZ, int observationPeriodMax, int operationPeriodMax) {
        long start = System.currentTimeMillis();
        for (int observationPeriod = 1; observationPeriod <= observationPeriodMax; observationPeriod++) {
            System.out.println("Generating OperationExercisedActions for observationPeriod: " + observationPeriod);
            for (int operationPeriod = 1; (operationPeriod <= operationPeriodMax); operationPeriod++) {
                ArrayList<SlidingWindowPeriod> slidingWindowPeriods = edu.mines.utils.DateUtils.generateWindowPeriods(startZ, endZ, observationPeriod, operationPeriod, 1);
                SlidingWindowTrial slidingWindowTrial = new SlidingWindowTrial(slidingWindowPeriods, observationPeriod, operationPeriod, 1);
                slidingWindowTrial.getSlidingWindowPeriods().parallelStream().forEach(period -> {
                    ActionNormalizer operationActionNormalizer = new ActionNormalizer(period.getObsEndOprStart(), period.getOprEnd());
                    OperationExercisedActions operationPeriodActions = operationActionNormalizer.loadOrProcess();
                    ActionNormalizer observationActionNormalizer = new ActionNormalizer(period.getObsStart(), period.getObsEndOprStart());
                    OperationExercisedActions observationPeriodActions = observationActionNormalizer.loadOrProcess();
                });
            }
        }
        System.out.println("populateOperationExercisedActionsCollection: " + (System.currentTimeMillis() - start) / 60000 + "m");
    }

    public OperationExercisedActions loadOrProcess() {
        String id = OperationExercisedActions.getKey(this.startDate.toString(), this.endDate.toString());
        OperationExercisedActions operationExercisedActions = this.operationExercisedActionsDAO.get(id);
        if (operationExercisedActions != null) {
            return operationExercisedActions;
        } else {
            return process();
        }
    }

    public OperationExercisedActions process() {
        this.process(true);
        return this.process(false);
    }

    public OperationExercisedActions process(boolean isDenied) {
        OperationExercisedActions operationExercisedActions;
        if (isDenied)
            operationExercisedActions = new OperationDeniedActions();
        else
            operationExercisedActions = new OperationExercisedActions();
        operationExercisedActions.setStartTime(this.startDate.toString());
        operationExercisedActions.setEndTime(this.endDate.toString());
        operationExercisedActions.setDays(this.duration);
        operationExercisedActions.setName(OperationExercisedActions.getKey(this.startDate.toString(), this.endDate.toString()));
        if (this.duration == 1) {
            operationExercisedActions.setDayOfWeek(this.startDate.getDayOfWeek());
            operationExercisedActions.setWeekday(OperationExercisedActions.isWeekday(this.startDate));
        }

        //First build map of IAM Users
        Document source = new Document("eventSource", "$eventSource");
        Document eventTimeRange = new Document("eventTime", new Document("$gte", startDate.toString()).append("$lt", endDate.toString()));
        Document iamUserType = new Document("userIdentity.type", "IAMUser");
        Document notErrorAction;
        if (isDenied)
            notErrorAction = new Document("errorCode", "AccessDenied");
        else
            notErrorAction = new Document("errorCode", new Document("$exists", false));
        AggregateIterable<Document> iterable = eventCollection.aggregate(asList(
                new Document("$match", new Document("$and", Arrays.asList(iamUserType, eventTimeRange, notErrorAction))),
                new Document("$group", new Document("_id", source.append("eventName", "$eventName").append("name", "$userIdentity.userName").append("requestParameters", "$requestParameters").append("responseElements", "$responseElements")).append("UniqueRequests", new Document("$sum", 1)))));
        processQueryDocument(operationExercisedActions, iterable, "IAMUser", isDenied);

        //Second build map of Roles
        Document roleType = new Document("userIdentity.type", "AssumedRole");
        iterable = eventCollection.aggregate(asList(
                new Document("$match", new Document("$and", Arrays.asList(roleType, eventTimeRange, notErrorAction))),
                new Document("$group", new Document("_id", source.append("eventName", "$eventName").append("name", "$userIdentity.sessionContext.sessionIssuer.userName").append("requestParameters", "$requestParameters").append("responseElements", "$responseElements")).append("UniqueRequests", new Document("$sum", 1)))));
        processQueryDocument(operationExercisedActions, iterable, "AssumedRole", isDenied);
        ds.save(operationExercisedActions);

        return operationExercisedActions;
    }

    private void processQueryDocument(OperationExercisedActions operationExercisedActions, AggregateIterable<Document> iterable, String type, boolean isDenied) {
        Map<String, EntitySummary> userResourcePermission = generateEntitySummaryMap(operationExercisedActions.getName(), iterable, isDenied);
        decorateUserCounts(operationExercisedActions.getName(), userResourcePermission, isDenied);
        String timeKey = operationExercisedActions.getName();
        for (Map.Entry<String, EntitySummary> entitySummaryEntry : userResourcePermission.entrySet()) {
            if (entitySummaryEntry.getKey() != null && entitySummaryEntry.getValue() != null) {
                String entityName = entitySummaryEntry.getKey();
                EntitySummary entitySummary = entitySummaryEntry.getValue();
                String key = EntitySummary.getKey(timeKey, entityName);
                if (isDenied) {
                    key += "|denied";
                    entitySummary.setDenied(true);
                }
                entitySummary.setId(key);
                entitySummary.setType(type);
//                try {
                ds.save(entitySummary);
//                } catch (BsonSerializationException bse) {
//                    System.out.println("Recrod too large, clearing requests for: " + entityName);
//                    for (ServiceSummary serviceSummary : entitySummary.getRequests().values())
//                        for (ActionSummary actionSummary : serviceSummary.getRequests().values())
//                            actionSummary.getRequests().clear();
//                    ds.save(entitySummary);
//                }

                operationExercisedActions.getEntitySummaryRefs().add(key);
            }
        }
    }

    private void cleanMapKeyNames(Object currentObject) {
        if (currentObject instanceof Map) {
            Map<String, Object>currentMap = (Map<String, Object>)currentObject;
            List<String> keysToRemove = new ArrayList<>();
            for (String key : new HashSet<String>(currentMap.keySet())) {
                if (currentMap.get(key) instanceof Map || currentMap.get(key) instanceof List){
                    cleanMapKeyNames(currentMap.get(key));
                }
                if (key.contains(".")) {
                    System.out.println("Removing Key: " + key);
                    keysToRemove.add(key);
                    currentMap.put(key.replace(".","_"), currentMap.get(key));
                }
            }
            for (String key : keysToRemove) {
                currentMap.remove(key);
            }
        } else if (currentObject instanceof List){
            List currentList = (List) currentObject;
            for (Object o : currentList) {
                this.cleanMapKeyNames(o);
            }
        }
    }

    private Map<String, EntitySummary> generateEntitySummaryMap(String timeKey, AggregateIterable<Document> iterable, boolean isDenied) {
        Map<String, EntitySummary> entitySummaryMap = new HashMap<>();
        for (Document document : iterable) {
            Document idDoc = (Document) document.get("_id");
            String userName = idDoc.getString("name");
            String eventSource = idDoc.getString("eventSource");
            int index = eventSource.indexOf(".amazonaws.com");
            eventSource = eventSource.substring(0, index);
            String eventName = idDoc.getString("eventName");
            if (isResourceLevelControlled(eventSource, eventName) && idDoc.get("requestParameters") == null && !isDenied) {
                System.out.println("Found document that should have requestParams but does not: " + document.toJson());
                continue;
            }

            Document requestParameters = idDoc.get("requestParameters", Document.class);
            if (requestParameters == null)
                requestParameters = new Document();
            else
                this.cleanMapKeyNames(requestParameters);

            Document responseElements = idDoc.get("responseElements", Document.class);
            if (responseElements == null)
                responseElements = new Document();
            else
                this.cleanMapKeyNames(responseElements);

            if (!entitySummaryMap.containsKey(userName)) {
                entitySummaryMap.put(userName, new EntitySummary(timeKey, userName));
            }
            if (!entitySummaryMap.get(userName).getRequests().containsKey(eventSource)) {
                entitySummaryMap.get(userName).getRequests().put(eventSource, new ServiceSummary());
            }
            if (!entitySummaryMap.get(userName).getRequests().get(eventSource).getRequests().containsKey(eventName)) {
                entitySummaryMap.get(userName).getRequests().get(eventSource).getRequests().put(eventName, new ActionSummary());
            }

            requestParameters.put("calp_UniqueRequests", document.getInteger("UniqueRequests"));
            if (entitySummaryMap.get(userName).getRequests().get(eventSource).getRequests().get(eventName) instanceof ActionSummary) {
                ActionSummary actionSummary = entitySummaryMap.get(userName).getRequests().get(eventSource).getRequests().get(eventName);
                DocumentPair reqRespDocumentPair = new DocumentPair(new DocumentProxy(requestParameters), new DocumentProxy(responseElements));
                actionSummary.getRequests().add(reqRespDocumentPair);
            }
        }
        return entitySummaryMap;
    }

    private void decorateResources(String timeKey, String serviceName, ServiceSummary serviceSummary, String actionName, ActionSummary actionSummary) {
        GenericAction genericAction = new GenericAction(serviceName, actionName);
        Set<String> resourceArnsToAdd = new TreeSet<>();
        if (serviceManager.isMonitorable(genericAction)) {
            MergedService mergedService = serviceManager.getLogNamedService().get(serviceName);
            for (String resourceType : mergedService.getMonitoredResources().get(genericAction.getActionName())) {
                if (resourceType.equals("AWS::EC2::Instance")) {
                    for (String resourceArn : actionSummary.getCalpActionResources()) {
                        if (ec2InstanceMap != null && ec2InstanceMap.containsKey(resourceArn)) {
                            String instanceId = ec2InstanceMap.get(resourceArn).getResourceId();
                            for (edu.mines.model.config.Config volumeConfig : ec2VolumeMap.values()) {
                                if (volumeConfig.getRelationships() != null) {
                                    for (Relationship relationship : volumeConfig.getRelationships()) {
                                        if (relationship.getResourceId().equals(instanceId) && relationship.getResourceType().equals("AWS::EC2::Instance")) {
                                            resourceArnsToAdd.add(volumeConfig.getARN());
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // No add RoleARNs used from instanceProfileNames
                    for (DocumentPair reqResPair : actionSummary.getRequests()) {
                        DocumentProxy request = reqResPair.getRequest();
                        if (request.containsKey("iamInstanceProfile")) {
                            Document instanceProfile = (Document) request.get("iamInstanceProfile");
                            String profileName = instanceProfile.getString("name");
                            Set<String> roleNames = segmentedEntityDao.getByProfileName(timeKey, this.envName, profileName);
                            resourceArnsToAdd.addAll(roleNames);
                        }
                    }
                }
            }
        }
        actionSummary.getCalpActionResources().addAll(resourceArnsToAdd);
    }

    //TODO maybe a good spot to use visitor pattern?
    private long decorateUserCounts(String dateKey, Map<String, EntitySummary> userResourcePermission, boolean isDenied) {
        long result = 0;
        for (String key : userResourcePermission.keySet()) {
            int userTotalRequests = (int) decorateServiceCounts(dateKey, userResourcePermission.get(key), isDenied);
            userResourcePermission.get(key).setCalpUserRequests(userTotalRequests);
            result += userTotalRequests;
        }
        return result;
    }

    private long decorateServiceCounts(String dateKey, EntitySummary serviceResourcePermission, boolean isDenied) {
        long result = 0;
        for (String key : serviceResourcePermission.getRequests().keySet()) {
            long requestCounts = 0;
            ServiceSummary serviceSummary = serviceResourcePermission.getRequests().get(key);
            requestCounts = decorateActionCounts(dateKey, serviceSummary, key, isDenied);
            result += requestCounts;
            serviceSummary.setCalpServiceRequests((int) requestCounts);
        }
        return result;
    }

    private long decorateActionCounts(String dateKey, ServiceSummary serviceSummary, String serviceName, boolean isDenied) {
        long result = 0;
        Set<Document> resultDocs = new HashSet<>();
        Set<String> clonedKeys = new HashSet<>(serviceSummary.getRequests().keySet());
        for (String actionName : clonedKeys) {
            if (serviceSummary.getRequests().get(actionName) instanceof ActionSummary) {
                ActionSummary actionSummary = serviceSummary.getRequests().get(actionName);
                long requestCounts = 0;
                Set<String> actionUniqueResources = new HashSet<>();

                for (DocumentPair reqResDocumentPair : actionSummary.getRequests()) {
                    DocumentProxy documentProxy = reqResDocumentPair.getRequest();
                    Map<String,Object> doc = documentProxy.getDelegate();
                    int uniqueRequests = (Integer)doc.get("calp_UniqueRequests");
                    result += uniqueRequests;
                    requestCounts += uniqueRequests;
                    Set<String> requestUniqueResources = ResourceIdNormalizer.normalizeResourceIdentifier(doc, serviceName, actionName, isDenied);
                    requestUniqueResources.addAll(ResourceIdNormalizer.normalizeResourceIdentifier(reqResDocumentPair.getResponse().getDelegate(), serviceName, actionName, isDenied));
                    if (requestUniqueResources.size() == 0 && isResourceLevelControlled(serviceName, actionName) && !isDenied)
                        System.out.println("Could not find resources for: " + serviceName + ":" + actionName);
                    if (requestUniqueResources.size() > 0) {
                        actionUniqueResources.addAll(requestUniqueResources);
                        RequestSummary requestSummary = new RequestSummary();
                        requestSummary.setNormalizedResourceNames(requestUniqueResources);
                        requestSummary.setUniqueRequests((long) uniqueRequests);
                        actionSummary.getCalpActionRequestsWithResources().add(requestSummary);
                    }
                }
                if (actionUniqueResources.size() > 0) {
                    actionSummary.setCalpActionResources(actionUniqueResources);
                    decorateResources(dateKey, serviceName, serviceSummary, actionName, actionSummary);
                }
                actionSummary.setCalpActionRequests((long) requestCounts);
            }
        }
        return result;
    }

    private boolean isResourceLevelControlled(String serviceName, String actionName) {
        if (this.resourceControlledServiceMap.containsKey(serviceName) &&
                (this.resourceControlledServiceMap.get(serviceName).getResourceLevelControlled() != null)) {
            Service service = this.resourceControlledServiceMap.get(serviceName);
            if (service.getResourceLevelControlled().equals(Service.ResourceControlLevel.ALL)) {
                if (service.getUnconstrainableActions().contains(actionName)) {
                    return false;
                } else {
                    return true;
                }
            } else if (service.getResourceLevelControlled().equals(Service.ResourceControlLevel.SOME)) {
                if (service.getActions().containsKey(actionName)) {
                    return true;
                }
            }
        }
        return false;
    }




}
