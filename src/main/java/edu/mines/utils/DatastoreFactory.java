package edu.mines.utils;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import edu.mines.model.*;
import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.converters.TypeConverter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Matt on 4/24/2016.
 */
public class DatastoreFactory {
    private static Config config = ConfigFactory.load();
    private static MongoClient mongo = new MongoClient(config.getString("mongo.server"));
    private static MongoDatabase database = mongo.getDatabase(config.getString("mongo.db"));
    private static final String EVENT_COLLECTION_NAME = config.getString("Collection.Name.Events");
    public static final String CONFIG_COLLECTION_NAME = config.getString("Collection.Name.Config");
    private static Datastore ds;

    static {
        Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
        Logger.getLogger("com.mongodb.diagnostics").setLevel(Level.SEVERE);
    }

    private DatastoreFactory() {
    }

    public static Datastore getDatastore(TypeConverter... additionalConverters) {
        Morphia morphia = new Morphia();
        morphia.map(PrivilegesMetric.class).map(ExistingRole.class).map(ServiceMap.class).map(ServiceSummary.class)
                .map(EntitySummary.class).map(Service.class).map(OperationExercisedActions.class).map(PolicyHolder.class)
                .map(edu.mines.model.Action.class).map(ActionSummary.class);
        morphia.getMapper().getConverters().addConverter(new ResourceConverter());
        for (TypeConverter converter : additionalConverters) {
            morphia.getMapper().getConverters().addConverter(converter);
        }
        ds = morphia.createDatastore(mongo, config.getString("mongo.db"));
        return ds;
    }

    public static MongoClient getMongoClient() {
        return mongo;
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static MongoCollection<Document> getEventCollection() {
        return database.getCollection(EVENT_COLLECTION_NAME);
    }

    public static String getDbName() {
        return config.getString("mongo.db");
    }
}
