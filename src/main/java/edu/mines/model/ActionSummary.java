package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mongodb.morphia.annotations.Embedded;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "calp_actionRequests",
        "calp_actionResources",
        "requests"
})
@Embedded
public class ActionSummary {

    @JsonProperty("calp_actionRequests")
    private Long calpActionRequests;
    @JsonProperty("calp_actionResources")
    private Set<String> calpActionResources = new TreeSet<>();
    @JsonProperty("calp_actionRequestsWithResources")
    private List<RequestSummary> calpActionRequestsWithResources = new ArrayList<>();
    @Embedded("requests")
    private List<DocumentPair> requests = new ArrayList<>();

    /**
     * @return The calpActionRequests
     */
    @JsonProperty("calp_actionRequests")
    public Long getCalpActionRequests() {
        return calpActionRequests;
    }

    /**
     * @param calpActionRequests The calp_actionRequests
     */
    @JsonProperty("calp_actionRequests")
    public void setCalpActionRequests(Long calpActionRequests) {
        this.calpActionRequests = calpActionRequests;
    }

    /**
     * @return The calpActionResources
     */
    @JsonProperty("calp_actionResources")
    public Set<String> getCalpActionResources() {
        return calpActionResources;
    }

    /**
     * @param calpActionResources The calp_actionResources
     */
    @JsonProperty("calp_actionResources")
    public void setCalpActionResources(Set<String> calpActionResources) {
        this.calpActionResources = calpActionResources;
    }

    @JsonProperty("calp_actionRequestsWithResources")
    public List<RequestSummary> getCalpActionRequestsWithResources() {
        return calpActionRequestsWithResources;
    }

    @JsonProperty("calp_actionRequestsWithResources")
    public void setCalpActionRequestsWithResources(List<RequestSummary> calpActionRequestsWithResources) {
        this.calpActionRequestsWithResources = calpActionRequestsWithResources;
    }

    public List<DocumentPair> getRequests() {
        return requests;
    }

    public void setRequests(List<DocumentPair> requests) {
        this.requests = requests;
    }

}