package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Embedded;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Matt on 9/5/2015.
 */
@Embedded
public class ServiceSummary {
    @JsonProperty("calp_serviceRequests")
    private Integer calpServiceRequests;
    @JsonProperty("actions")
    private Map<String, ActionSummary> requests = new TreeMap<>();

    @JsonProperty("calp_serviceRequests")
    public Integer getCalpServiceRequests() {
        return calpServiceRequests;
    }

    @JsonProperty("calp_serviceRequests")
    public void setCalpServiceRequests(Integer calpServiceRequests) {
        this.calpServiceRequests = calpServiceRequests;
    }

    @JsonProperty("actions")
    public Map<String, ActionSummary> getRequests() {
        return requests;
    }

    @JsonProperty("actions")
    public void setRequests(Map<String, ActionSummary> requests) {
        this.requests = requests;
    }
}
