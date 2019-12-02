package edu.mines.ingest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mongodb.MongoClient;
import edu.mines.model.EntitySummary;
import edu.mines.model.Service;
import edu.mines.model.ServiceMap;
import edu.mines.model.ServiceSummary;
import edu.mines.utils.DatastoreFactory;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Created by Matt on 10/7/2015.
 */
public class ServiceMapIngestor {
    private String inputfile = "AWSConfigs/ServiceMap.json";
    // from https://github.com/witoff/aws-servicemap/blob/master/servicemap.json
    private static ObjectMapper mapper = new ObjectMapper();
    private MongoClient mongo = DatastoreFactory.getMongoClient();
    private Morphia morphia = new Morphia();

    public ServiceMapIngestor() {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        morphia.map(Service.class).map(edu.mines.model.Action.class).map(ServiceMap.class).map(ServiceSummary.class).map(EntitySummary.class);
    }

    public static void main(String... args) throws Exception {
        ServiceMapIngestor serviceMapIngestor = new ServiceMapIngestor();
        serviceMapIngestor.process();

    }

    public void process() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(inputfile);
        File file = new File(url.getPath());
        Map<String, ServiceMap> result = mapper.readValue(file, new TypeReference<Map<String, ServiceMap>>() {
        });


        Datastore ds = morphia.createDatastore(mongo, DatastoreFactory.getDbName());

        for (String s : result.keySet()) {
            System.out.println(s);
            ServiceMap serviceMap = result.get(s);
            serviceMap.setId(s);
            ds.save(serviceMap);
        }
    }
}
