package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 9/5/2015.
 */
@Entity(noClassnameStored = true)
public class EntitySummary {
    @JsonProperty("calp_userRequests")
    private Integer calpUserRequests;
    @JsonProperty("services")
    private Map<String, ServiceSummary> requests = new HashMap<>();
    @JsonProperty("type")
    private String type;
    private String entityName;
    private String timeKey;
    private boolean denied;

    @Id
    @JsonProperty("_id")
    private String id;

    public EntitySummary() {
    }

    public EntitySummary(String timeKey, String entityName) {
        this.timeKey = timeKey;
        this.entityName = entityName;
        this.id = getKey(timeKey, entityName);
    }

    public boolean isDenied() {
        return denied;
    }

    public void setDenied(boolean denied) {
        this.denied = denied;
    }

    public static String getKey(String timeKey, String entityName) {
        return timeKey + "|" + entityName;
    }

    @JsonProperty("calp_userRequests")
    public Integer getCalpUserRequests() {
        return calpUserRequests;
    }

    @JsonProperty("calp_userRequests")
    public void setCalpUserRequests(Integer calpUserRequests) {
        this.calpUserRequests = calpUserRequests;
    }

    @JsonProperty("services")
    public Map<String, ServiceSummary> getRequests() {
        return requests;
    }

    @JsonProperty("services")
    public void setRequests(Map<String, ServiceSummary> requests) {
        this.requests = requests;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getTimeKey() {
        return timeKey;
    }
}

