package edu.mines.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import edu.mines.model.SlidingWindowPeriod;
import edu.mines.model.config.Config;
import edu.mines.model.config.ConfigItemCaptureTimeCompartor;
import edu.mines.utils.DatastoreFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Created by Matt on 9/2/2017.
 */
public class ConfigDAO {
    private static ObjectMapper mapper = new ObjectMapper();
    private static MongoClient mongo = DatastoreFactory.getMongoClient();
    private static Morphia morphia = new Morphia();
    private static Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());
    private static ConfigDAO instance = new ConfigDAO();

    private Map<String, Config> resourceItems = new TreeMap<>();

    private ConfigDAO() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        morphia.map(Config.class);
        ds.ensureIndexes();

        List<String> invalidStatuses = Arrays.asList("ResourceDeletedNotRecorded", "ResourceNotRecorded");
        //String[] resourceTypes = new String[]{"AWS::EC2::Instance", "AWS::S3::Bucket", "AWS::DynamoDB::Table"};
        Query<Config> ec2configs = ds.createQuery(Config.class)
                //.filter("resourceType in", resourceTypes)
                .filter("configurationItemStatus nin", invalidStatuses);
//                .order("resourceId");
        this.resourceItems = (Map<String, Config>) populateConfigs(ec2configs);

//        resourceItems.values().forEach(config -> System.out.println(config.getResourceId() + "\t" + config.getResourceCreationTime() + "\t" + config.getResourceDeletedTime()));
    }

    public static ConfigDAO getInstance() {
        return instance;
    }

    public static <T extends Config> Map<String, ? extends Config> getConfigs(Class<T> t, String configClass) {
        Query<T> ec2configs = ds.createQuery(t).filter("resourceType", configClass);
        Map<String, ? extends Config> configItemMap = populateConfigs(ec2configs);
        return configItemMap;
    }

    public static List<DBObject> getByArn(String Arn) {
        List<DBObject> results = new ArrayList<>();
        BasicDBObject queryObject = new BasicDBObject();
        queryObject.put("ARN", Arn);
        DBCursor cursor = ds.getCollection(Config.class).find(queryObject);
        while (cursor.hasNext()) {
            results.add(cursor.next());
        }
        return results;
    }

    private static Map<String, ? extends Config> populateConfigs(Query<? extends Config> configs) {
        Map<String, Config> configItemMap = new TreeMap<>();

        for (Config ec2Config : configs) {
            String id = ec2Config.getResourceName() != null ? ec2Config.getResourceName() : ec2Config.getResourceId();
            if (!configItemMap.containsKey(id)) {
                configItemMap.put(id, ec2Config);
            }
            Config itemFromMap = configItemMap.get(id);
            if (ec2Config.getConfigurationItemStatus().equals("ResourceDeleted"))
                itemFromMap.setResourceDeletedTime(ec2Config.getConfigurationItemCaptureTime());
            if (ec2Config.getConfigurationItemStatus().equals("ResourceDiscovered"))
                itemFromMap.setResourceCreationTime(ec2Config.getResourceCreationTime());
        }
        return configItemMap;
    }

    public Map<String, Config> getResourceItems() {
        return resourceItems;
    }

    public static Set<String> getDistinctArns(String resourceType) {
        Set<String> returnSet = new HashSet<>();
        Query<Config> configs = ds.createQuery(Config.class)
                .filter("resourceType", resourceType)
                .project("ARN", true);
        for (Config config : configs) {
            returnSet.add(config.getARN());
        }
        return returnSet;
    }

    public static DBObject getLatestRawByArn(String dateTime, String arn) {
        ZonedDateTime zdt = SlidingWindowPeriod.getEndFromKey(dateTime);
        Query<Config> configs = ds.createQuery(Config.class).filter("ARN", arn);
        return findLatestValidConfig(zdt, configs);
    }

    public static DBObject getLatestRawByIdandType(String dateTime, String resourceName, String resourceType) {
        ZonedDateTime zdt = SlidingWindowPeriod.getEndFromKey(dateTime);
        Query<Config> configs = ds.createQuery(Config.class).filter("resourceType", resourceType).filter("resourceName", resourceName);
        return findLatestValidConfig(zdt, configs);
    }

    private static DBObject findLatestValidConfig(ZonedDateTime zdt, Query<Config> configs) {
        String latestId = null;
        ZonedDateTime latestZdt = null;
        String deletedId = null;
        for (Config config : configs) {
            if (!config.getConfigurationItemStatus().equals("ResourceDeleted") && ZonedDateTime.parse(config.getConfigurationItemCaptureTime()).isBefore(zdt)) {
                if (latestZdt == null || ZonedDateTime.parse(config.getConfigurationItemCaptureTime()).isAfter(latestZdt)) {
                    latestZdt = ZonedDateTime.parse(config.getConfigurationItemCaptureTime());
                    latestId = config.getId();
                }
            } else if (config.getConfigurationItemStatus().equals("ResourceDeleted")) {
                deletedId = config.getId();
            }
        }
        if (latestId == null && deletedId != null)
            return ds.getCollection(Config.class).findOne(deletedId);
        return ds.getCollection(Config.class).findOne(latestId);
    }

    public static DBObject getLatestArn(String configId) {
        return ds.getCollection(Config.class).findOne(configId);
    }

    public static <T extends Config> Config getConfigAtTime(Class<T> t, ZonedDateTime dateTime, String arn) {
        Query<T> configs = ds.createQuery(t).filter("ARN", arn);
        Config discoveredConf = null;
        List<Config> okConfigs = new ArrayList<>();
        Config deletedConf = null;
        for (Config config : configs) {
            switch (config.getConfigurationItemStatus()) {
                case "ResourceDiscovered":
//                    if (discoveredConf != null)
//                        throw new RuntimeException("Resource Discovered twice: " + config.getARN());
                    discoveredConf = config;
                    break;
                case "ResourceDeleted":
                    deletedConf = config;
                    break;
                case "OK":
                    okConfigs.add(config);
                    break;
            }
        }
        if (okConfigs.size() > 0)
            okConfigs.sort(new ConfigItemCaptureTimeCompartor());

        ZonedDateTime discoveredTime = discoveredConf == null ? null : ZonedDateTime.parse(discoveredConf.getConfigurationItemCaptureTime());
        ZonedDateTime deletedTime = deletedConf == null ? null : ZonedDateTime.parse(deletedConf.getConfigurationItemCaptureTime());
        // Check if resource was deleted before dateTime
        if (deletedConf != null && dateTime.isAfter(deletedTime)) {
            //System.out.println(arn + " was deleted before " + dateTime);
            return null;
        }
        // Check if resource was created after dateTime
        if (discoveredConf != null && dateTime.isBefore(discoveredTime)) {
            //System.out.println(arn + " was created after " + dateTime);
            return null;
        }
        // Resource was discovered but never updated (case 4)
        if (discoveredConf != null && okConfigs.size() == 0 && deletedConf == null) {
            if (dateTime.isAfter(discoveredTime))
                return discoveredConf;
        }
        // Resource was created and deleted with no updates (case 5)
        if (discoveredConf != null && okConfigs.size() == 0 && deletedConf != null) {
            if (dateTime.isAfter(discoveredTime) && dateTime.isBefore(deletedTime))
                return discoveredConf;
            else
                return null;
        }
        // Resource was discovered and received updates but not deleted (case 6)
        // Resource Discovered, updates, and deleted (case 7)
        // return first config before specified dateTime or discoveredConf if none.
        // assumes okConfigs are reverse chronologically sorted
        if (okConfigs.size() > 0) {
            for (Config config : okConfigs) {
                ZonedDateTime currentConfigTime = ZonedDateTime.parse(config.getConfigurationItemCaptureTime());
                if (currentConfigTime.isBefore(dateTime))
                    return config;
            }
            if (discoveredConf != null)
                return discoveredConf;
        }
        System.out.println("Could not find config for ARN: " + arn);
        return null;
//        throw new RuntimeException("unknown config case");
    }
}
