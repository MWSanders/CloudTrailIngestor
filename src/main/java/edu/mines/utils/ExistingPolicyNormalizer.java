package edu.mines.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.MongoClient;
import edu.mines.model.ExistingPolicies;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

/**
 * Created by Matt on 8/3/2017.
 */
public class ExistingPolicyNormalizer {
    private static MongoClient mongo = DatastoreFactory.getMongoClient();
    private static Morphia morphia = new Morphia();
    private static Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());

    public static void main(String... args) {
        ExistingPolicies existingPolicies = ds.get(ExistingPolicies.class, args[0]);
        PolicyNormalizer policyNormalizer = new PolicyNormalizer(existingPolicies.getUsers(), existingPolicies.getRoles());

        try {
            policyNormalizer.process();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
