package edu.mines.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.MongoClient;
import edu.mines.model.*;
import edu.mines.utils.DatastoreFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Matt on 8/5/2017.
 */
public class MergedServiceManager {
    private static final MergedServiceManager instance = new MergedServiceManager();
    private ObjectMapper mapper = new ObjectMapper();

    private MongoClient mongo = DatastoreFactory.getMongoClient();
    private Morphia morphia = new Morphia();
    private Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());

    private Map<String, ServiceMap> iamServices = new TreeMap<>();
    private Map<String, Service> logServices = new TreeMap<>();

    private Map<String, MergedService> iamNamedService = new TreeMap<>();
    private Map<String, MergedService> logNamedService = new TreeMap<>();

    public static MergedServiceManager getInstance() {
        return instance;
    }

    private void init() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        morphia.map(Service.class).map(ExistingRole.class).map(ServiceMap.class).map(ServiceSummary.class)
                .map(EntitySummary.class).map(MergedService.class);

        Query<ServiceMap> serviceQuery = ds.createQuery(ServiceMap.class);
        serviceQuery.forEach(serviceMap -> {
            //Address issue of multiple ServiceMap entries having the same StringPrefix value (ec2, aws-marketplace)
            if (iamServices.containsKey(serviceMap.getStringPrefix())) {
                ServiceMap mergedServiceMap = mergeServiceMaps(serviceMap, iamServices.get(serviceMap.getStringPrefix()));
                iamServices.put(serviceMap.getStringPrefix(), mergedServiceMap);
            } else {
                iamServices.put(serviceMap.getStringPrefix(), serviceMap);
            }
        });

        Query<Service> logServiceQuery = ds.createQuery(Service.class);
        logServiceQuery.asList().forEach(myService -> this.logServices.put(myService.getIAMName(), myService));
    }

    private MergedServiceManager() {
        init();
        for (Service logService : this.logServices.values()) {
            if (iamServices.containsKey(logService.getIAMName())) { //Skip services that cannot be IAM controlled (support)
                MergedService mergedService = new MergedService(logService, this.iamServices.get(logService.getIAMName()));
                iamNamedService.put(mergedService.getIamName(), mergedService);
                logNamedService.put(mergedService.getCloudTrailName(), mergedService);
            }
        }
        ds.save(logNamedService.values());
    }

    public ServiceMap mergeServiceMaps(ServiceMap a, ServiceMap b) {
        if (a.getARNRegex() == null && b.getARNRegex() != null)
            a.setARNRegex(b.getARNRegex());
        if (a.getARNFormat() == null && b.getARNFormat() != null)
            a.setARNFormat(b.getARNFormat());
        if (a.getHasResource() == null && b.getHasResource() != null)
            a.setHasResource(b.getHasResource());
        a.getActions().addAll(b.getActions());
        if (a.getConditionKeys() == null && b.getConditionKeys() != null)
            a.setConditionKeys(b.getConditionKeys());
        else if (a.getConditionKeys() != null && b.getConditionKeys() != null)
            a.getConditionKeys().addAll(b.getConditionKeys());
        else if (a.getConditionKeys() != null && b.getConditionKeys() == null)
            assert true;
        return a;
    }

    public boolean isAuditable(String serviceName, String actionName) {
        if (logNamedService.containsKey(serviceName)) {
            MergedService mergedService = logNamedService.get(serviceName);
            if (mergedService.getAuditActionLevel().equals(Service.AuditActionLevel.ALL))
                return true;
            if (mergedService.getAuditActionLevel().equals(Service.AuditActionLevel.SOME) && mergedService.getAuditedActions().contains(actionName))
                return true;
        }
        return false;
    }

    public boolean isMonitorable(GenericAction genericAction) {
        if (logNamedService.containsKey(genericAction.getSplitServiceName())) {
            MergedService mergedService = logNamedService.get(genericAction.getSplitServiceName());
            if (mergedService.getMonitoredResources().containsKey(genericAction.getActionName())) {
                return true;
            }
        }
        return false;
    }

//    public boolean isMonitorable(String serviceName, String actionName) {
//        if (logNamedService.containsKey(serviceName)) {
//            MergedService mergedService = logNamedService.get(serviceName);
//            if (mergedService.getMonitoredResources().containsKey(actionName)) {
//                return true;
//            }
//        }
//        return false;
//    }

    public boolean isResourceConstrainable(String serviceName, String actionName) {
        if (logNamedService.containsKey(serviceName)) {
            MergedService mergedService = logNamedService.get(serviceName);
            if (mergedService.getResourceLevelControlled().equals(Service.ResourceControlLevel.ALL) &&
                    !mergedService.getUnconstrainableActions().contains(actionName))
                return true;
            if (mergedService.getResourceLevelControlled().equals(Service.ResourceControlLevel.SOME) &&
                    mergedService.getResourceControllableActions().containsKey(actionName))
                return true;
        }
        return false;
    }

    public Map<String, MergedService> getLogNamedService() {
        return logNamedService;
    }

    public Map<String, MergedService> getIamNamedService() {
        return iamNamedService;
    }
}
