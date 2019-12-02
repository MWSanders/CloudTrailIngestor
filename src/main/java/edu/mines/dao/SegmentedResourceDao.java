package edu.mines.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import edu.mines.model.config.Config;
import edu.mines.model.config.SegmentedResources;
import edu.mines.utils.DatastoreFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Map;
import java.util.Set;

/**
 * Created by Matt on 9/28/2017.
 */
public class SegmentedResourceDao {
    private static ObjectMapper mapper = new ObjectMapper();
    private static MongoClient mongo = DatastoreFactory.getMongoClient();
    private static Morphia morphia = new Morphia();
    private static Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());
    private static SegmentedResourceDao instance = new SegmentedResourceDao();

    private SegmentedResourceDao() {
    }

    public static SegmentedResourceDao getInstance() {
        return instance;
    }

    public Integer getSegResourceCount(String dateKey, String type) {
        SegmentedResources segmentedResources = ds.get(SegmentedResources.class, dateKey);
        return segmentedResources.getResources().get(type).size();
    }

    public Map<String, Config> getSegResourceMap(String dateKey, String type) {
        SegmentedResources segmentedResources = ds.get(SegmentedResources.class, dateKey);
        if (segmentedResources == null || !segmentedResources.getResources().containsKey(type))
            return null;
        return segmentedResources.getResources().get(type);
    }

    public Set<String> getSegResourceArns(String dateKey, String type) {
        SegmentedResources segmentedResources = ds.get(SegmentedResources.class, dateKey);
        return segmentedResources.getResources().get(type).keySet();
    }

    public Config getSegResourcebyArn(String dateKey, String arn) {
        SegmentedResources segmentedResources = ds.get(SegmentedResources.class, dateKey);
        String configType = segmentedResources.getResourceClasses().get(arn);
        return segmentedResources.getResources().get(configType).get(arn);
    }

}
