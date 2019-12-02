package edu.mines.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import edu.mines.utils.DatastoreFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.QueryResults;

/**
 * Created by Matt on 8/15/2017.
 */
public class OperationExercisedActionsDAO {
    private ObjectMapper mapper = new ObjectMapper();
    private MongoClient mongo = DatastoreFactory.getMongoClient();
    private Morphia morphia = new Morphia();
    private Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());
    private Datastore dsRead = DatastoreFactory.getDatastore(new StatementConverter());
    private Datastore ds2 = DatastoreFactory.getDatastore(new DocumentConverter());

    private static final OperationExercisedActionsDAO instance = new OperationExercisedActionsDAO();

    private OperationExercisedActionsDAO() {
    }

    public static OperationExercisedActionsDAO getInstance() {
        return instance;
    }

    public void hydrate(OperationExercisedActions operationExercisedActions) {
        operationExercisedActions.getEntities().clear();
        QueryResults<EntitySummary> entities = ds2.get(EntitySummary.class, operationExercisedActions.getEntitySummaryRefs());
        for (EntitySummary entitySummary : entities) {
            operationExercisedActions.getEntities().put(entitySummary.getEntityName(), entitySummary);
        }
    }

    public OperationExercisedActions get(String key) {
        OperationExercisedActions operationExercisedActions = ds.get(OperationExercisedActions.class, key);
        if (operationExercisedActions != null)
            hydrate(operationExercisedActions);
        return operationExercisedActions;
    }
}
