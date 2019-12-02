package edu.mines;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Collation;
import com.mongodb.client.model.InsertManyOptions;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import edu.mines.utils.DatastoreFactory;
import org.apache.commons.io.FileUtils;
import org.bson.Document;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Created by Matt on 8/18/2015.
 * Reads CloudTrail events from a directory on disk and inserts them into Mongo
 */
public class CloudTrailIngestor {

    public static void main (String... args) throws Exception{
        Config config = ConfigFactory.load();
        String env = config.getString("env.name");
        String dir = config.getString("dir.logs");
        MongoCollection<Document> collection = DatastoreFactory.getEventCollection();

        ObjectMapper mapper = new ObjectMapper();

        String[] extensions = new String[]{"gz"};
        Collection<File> fileList = FileUtils.listFiles(new File(dir), extensions, true);
        InsertManyOptions insertManyOptions = new InsertManyOptions();
        insertManyOptions.ordered(false);

        File database = FileUtils.getFile(config.getString("geolite2.location"));
        final DatabaseReader reader = new DatabaseReader.Builder(database).build();

        fileList.parallelStream().forEach(f -> {
            System.out.println("Reading file: " + f.getAbsolutePath());
            JsonNode root = null;
            try {
                GZIPInputStream gzippedInputStream = new GZIPInputStream(new ByteArrayInputStream(FileUtils.readFileToByteArray(f)));
                String logFileContent = new String(toByteArray(gzippedInputStream), StandardCharsets.UTF_8);
                root = mapper.readTree(logFileContent);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
            JsonNode records = root.path("Records");
            List<Document> buffer = new ArrayList<>();
            Iterator<JsonNode> iterator = records.elements();
            while (iterator.hasNext()) {
                JsonNode node = iterator.next();
                Document doc = Document.parse(node.toString());
                if (doc.containsKey("errorMessage"))
                    continue;
                if (doc.containsKey("userIdentity"))
                    if (doc.get("userIdentity", Document.class).containsKey("type"))
                        if (doc.get("userIdentity", Document.class).getString("type").equalsIgnoreCase("IAMUser")) {
                            ZonedDateTime eventTime = ZonedDateTime.parse(doc.getString("eventTime"));
                            // Bin IP Addresses
                            String sourceIPAddressBin = doc.getString("sourceIPAddress");
                            if (sourceIPAddressBin.startsWith("10."))
                                sourceIPAddressBin = "vpc.internal";
                            try {
                                String ipBin = null;
                                InetAddress ipAddress = InetAddress.getByName(doc.getString("sourceIPAddress"));
                                CityResponse response = reader.city(ipAddress);
                                Country country = response.getCountry();
                                if (country.getIsoCode().equals("US")) {
                                    Subdivision subdivision = response.getMostSpecificSubdivision();
                                    if (subdivision.getIsoCode() == null)
                                        ipBin = "US:UNKNOWN";
                                    else
                                        ipBin = "US:" + subdivision.getIsoCode();
                                } else {
                                    ipBin = country.getIsoCode();
                                }
                                if (ipBin != null) {
                                    doc.put("sourceIPAddressLocation", ipBin);
                                    sourceIPAddressBin = ipBin;
                                }
                            } catch (Exception ignored) {
                                doc.put("sourceIPAddressLocation", "internal");
                                doc.put("sourceIPAddressInternal", doc.getString("sourceIPAddress"));
                            }
                            doc.put("sourceIPAddressBin", sourceIPAddressBin);
                            // Bin request parameters
                            doc.put("eventTime", Date.from(eventTime.toInstant())); //Insert time as ISODate so it can be filterd on.
                            sanitizeMap(doc);
                            String uniqueId = UUID.randomUUID().toString(); //doc.getString("requestID") + "-" + doc.getString("eventID");
//                            doc.append("_id", uniqueId);
//                            doc.append("environment", env);
                            if (doc.containsKey("requestParameters")) {
                                String eventSource = doc.getString("eventSource").split("\\.")[0];
                                Object obj = doc.get("requestParameters");
                                doc.remove("requestParameters");
                                Document requestParams = new Document();
                                requestParams.put(eventSource, obj);
                                doc.put("requestParameters", requestParams);
                            }
                            buffer.add(doc);
                            if (buffer.size() == 1000) {
                                flushBuffer(collection, insertManyOptions, buffer);
                            }
                        }


            }
            if (buffer.size() > 0) {
                flushBuffer(collection, insertManyOptions, buffer);
            }
        });

        System.exit(0);
    }

    private static void sanitizeMap(Map<String, Object> map) {
        Set<String> keysToRemove = new HashSet<>();
        Map<String, Object> entriesToAdd = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (key.contains(".")) {
                keysToRemove.add(key);
                key = encodeKey(key);
                entriesToAdd.put(key, entry.getValue());
            }
            if (entry.getValue() instanceof Map) {
                sanitizeMap((Map) entry.getValue());
            } else if (entry.getValue() instanceof Collection)
                sanitizeCollection((Collection) entry.getValue());
        }
        keysToRemove.forEach(map::remove);
        map.putAll(entriesToAdd);
    }

    private static void sanitizeCollection(Collection collection) {
        for (Object o : collection) {
            if (o instanceof Map)
                sanitizeMap((Map) o);
            else if (o instanceof Collation)
                sanitizeCollection((Collection) o);
        }

    }

    private static String encodeKey(String key) {
        return key.replace("\\", "\\\\").replace("$", "\\u0024").replace(".", "\\u002e");
    }

    private static void flushBuffer(MongoCollection<Document> collection, InsertManyOptions insertManyOptions, List<Document> buffer) {
        try {
            collection.insertMany(buffer, insertManyOptions);
        } catch (MongoBulkWriteException mbwe) {
            mbwe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        buffer.clear();
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead = 0;
            byte[] bytes = new byte[1024];
            while ((nRead = inputStream.read(bytes, 0, 1024)) != -1) {
                buffer.write(bytes, 0, nRead);
            }
            return buffer.toByteArray();
        }
}
