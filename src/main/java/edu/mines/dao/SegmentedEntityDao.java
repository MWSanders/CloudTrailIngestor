package edu.mines.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import edu.mines.model.config.SegmentedIamRole;
import edu.mines.utils.DatastoreFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Matt on 9/28/2017.
 */
public class SegmentedEntityDao {
    private static ObjectMapper mapper = new ObjectMapper();
    private static MongoClient mongo = DatastoreFactory.getMongoClient();
    private static Morphia morphia = new Morphia();
    private static Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());
    private static SegmentedEntityDao instance = new SegmentedEntityDao();

    private SegmentedEntityDao() {
    }

    public static SegmentedEntityDao getInstance() {
        return instance;
    }

    public Set<String> getByProfileName(String datekey, String env, String profileName) {
        Query<SegmentedIamRole> serviceQuery = ds.createQuery(SegmentedIamRole.class).field("instanceProfileNames").equal(profileName).project("iamRole", true);
        Set<String> roleNames = new TreeSet<>();
        for (SegmentedIamRole segmentedIamRole : serviceQuery) {
            try {
                if (segmentedIamRole.getIamRole() != null)
                    roleNames.add(segmentedIamRole.getIamRole().getARN());
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                throw npe;
            }
        }
        return roleNames;
    }
}
