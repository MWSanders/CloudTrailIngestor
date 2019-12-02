package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import javax.annotation.Generated;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "_id",
        "CloudTrailEnabled",
        "CloudTrailName",
        "IAMName",
        "ResourceLevelControlled",
        "Actions"
})
@Entity
public class Service {
    public enum ResourceControlLevel {SOME, ALL, NONE}

    public enum AuditActionLevel {SOME, ALL, NONE}

    @JsonProperty("_id")
    @Id
    private String Id;
    @JsonProperty("CloudTrailEnabled")
    private Boolean CloudTrailEnabled;
    @JsonProperty("CloudTrailName")
    private String CloudTrailName;
    @JsonProperty("IAMName")
    private String IAMName;
    @JsonProperty("ResourceLevelControlled")
    private ResourceControlLevel ResourceLevelControlled;
    @JsonProperty("Actions")
    private Map<String, Action> Actions = new TreeMap<>();
    @JsonProperty("ServiceLevelARNs")
    private List<ServiceLevelArn> ServiceLevelARNs = new ArrayList<>();
    @JsonProperty("UnconstrainableActions")
    private Set<String> UnconstrainableActions = new HashSet<>();

    @JsonProperty("auditActionLevel")
    private AuditActionLevel auditActionLevel = AuditActionLevel.NONE;
    @JsonProperty("auditActions")
    private List<String> auditedActions = new ArrayList<>();

    private Map<String, String[]> monitoredResources = new TreeMap<>();

    /**
     * @return The Id
     */
    @JsonProperty("_id")
    public String getId() {
        return Id;
    }

    /**
     * @param Id The _id
     */
    @JsonProperty("_id")
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The CloudTrailEnabled
     */
    @JsonProperty("CloudTrailEnabled")
    public Boolean getCloudTrailEnabled() {
        return CloudTrailEnabled;
    }

    /**
     *
     * @param CloudTrailEnabled
     * The CloudTrailEnabled
     */
    @JsonProperty("CloudTrailEnabled")
    public void setCloudTrailEnabled(Boolean CloudTrailEnabled) {
        this.CloudTrailEnabled = CloudTrailEnabled;
    }

    /**
     * @return The CloudTrailName
     */
    @JsonProperty("CloudTrailName")
    public String getCloudTrailName() {
        return CloudTrailName;
    }

    /**
     * @param CloudTrailName The CloudTrailName
     */
    @JsonProperty("CloudTrailName")
    public void setCloudTrailName(String CloudTrailName) {
        this.CloudTrailName = CloudTrailName;
    }

    /**
     * @return The IAMName
     */
    @JsonProperty("IAMName")
    public String getIAMName() {
        return IAMName;
    }

    /**
     * @param IAMName The IAMName
     */
    @JsonProperty("IAMName")
    public void setIAMName(String IAMName) {
        this.IAMName = IAMName;
    }

    /**
     * @return The ResourceLevelControlled
     */
    @JsonProperty("ResourceLevelControlled")
    public ResourceControlLevel getResourceLevelControlled() {
        return ResourceLevelControlled;
    }

    /**
     * @param ResourceLevelControlled The ResourceLevelControlled
     */
    @JsonProperty("ResourceLevelControlled")
    public void setResourceLevelControlled(ResourceControlLevel ResourceLevelControlled) {
        this.ResourceLevelControlled = ResourceLevelControlled;
    }

    /**
     * @return The Actions
     */
    @JsonProperty("Actions")
    public Map<String, Action> getActions() {
        return Actions;
    }

    /**
     * @param Actions The Actions
     */
    @JsonProperty("Actions")
    public void setActions(HashMap<String, Action> Actions) {
        this.Actions = Actions;
    }

    @JsonProperty("ServiceLevelARNs")
    public List<ServiceLevelArn> getServiceLevelARNs() {
        return ServiceLevelARNs;
    }

    @JsonProperty("ServiceLevelARNs")
    public void setServiceLevelARNs(List<ServiceLevelArn> serviceLevelARNs) {
        ServiceLevelARNs = serviceLevelARNs;
    }

    public Set<String> getUnconstrainableActions() {
        return UnconstrainableActions;
    }

    public void setUnconstrainableActions(Set<String> unconstrainableActions) {
        UnconstrainableActions = unconstrainableActions;
    }

    public AuditActionLevel getAuditActionLevel() {
        return auditActionLevel;
    }

    public void setAuditActionLevel(AuditActionLevel auditActionLevel) {
        this.auditActionLevel = auditActionLevel;
    }

    public List<String> getAuditedActions() {
        return auditedActions;
    }

    public void setAuditedActions(List<String> auditedActions) {
        this.auditedActions = auditedActions;
    }

    public Map<String, String[]> getMonitoredResources() {
        return monitoredResources;
    }

    public void setMonitoredResources(Map<String, String[]> monitoredResources) {
        this.monitoredResources = monitoredResources;
    }
}
