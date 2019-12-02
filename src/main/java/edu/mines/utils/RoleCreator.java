package edu.mines.utils;

import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;
import com.amazonaws.services.identitymanagement.model.CreateRoleRequest;
import com.amazonaws.services.identitymanagement.model.DeleteRoleRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.MongoClient;
import edu.mines.model.*;
import org.apache.commons.io.FileUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Matt on 10/7/2015.
 */
public class RoleCreator {
    private static ObjectMapper mapper = new ObjectMapper();
    private AmazonIdentityManagementClient iamClient = new AmazonIdentityManagementClient();
    private Map<String, EntitySummary> userSummaryMap = new HashMap<>();
    private String principalDocument;

    public RoleCreator() throws IOException {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

        Morphia morphia = new Morphia();
        morphia.map(Service.class).map(edu.mines.model.Action.class).map(ActionSummary.class).map(ServiceSummary.class).map(EntitySummary.class);
        MongoClient mongo = DatastoreFactory.getMongoClient();
        Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());

        Query<EntitySummary> userQuery = ds.createQuery(EntitySummary.class);
        List<EntitySummary> userSummaries = userQuery.asList();
        userSummaries.forEach(userSummary -> userSummaryMap.put(userSummary.getId(), userSummary));

        URL url = Thread.currentThread().getContextClassLoader().getResource("rolePolicy.json");
        File file = new File(url.getPath());
        principalDocument = FileUtils.readFileToString(file);
    }

    public static void main(String... args) throws IOException {
        RoleCreator roleCreator = new RoleCreator();
        roleCreator.process();
    }

    private void process() {
        for (Map.Entry<String, EntitySummary> entry : userSummaryMap.entrySet()) {
            if (entry.getValue().getType().equals(PolicyHolder.TYPE.AssumedRole.name())) {
                System.out.println(entry.getKey());

                try {
                    DeleteRoleRequest deleteRoleRequest = new DeleteRoleRequest();
                    deleteRoleRequest.setRoleName(entry.getKey());
                    iamClient.deleteRole(deleteRoleRequest);
                    System.out.println("deleted " + entry.getKey());
                } catch (Exception e) {
                    System.out.println(entry.getKey() + " does not exist");
                }

                CreateRoleRequest createRoleRequest = new CreateRoleRequest();
                createRoleRequest.setRoleName(entry.getKey());
                createRoleRequest.setAssumeRolePolicyDocument(principalDocument);
                iamClient.createRole(createRoleRequest);
                System.out.println("Created " + entry.getKey());
            }
        }
    }
}
