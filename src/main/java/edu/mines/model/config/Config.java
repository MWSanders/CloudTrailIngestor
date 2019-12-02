package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mongodb.morphia.annotations.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "relatedEvents",
        "relationships",
        "configuration",
        "supplementaryConfiguration",
        "tags",
        "configurationItemVersion",
        "configurationItemCaptureTime",
        "configurationStateId",
        "awsAccountId",
        "configurationItemStatus",
        "resourceType",
        "resourceId",
        "ARN",
        "awsRegion",
        "availabilityZone",
        "configurationStateMd5Hash",
        "resourceCreationTime"
})
@Entity(noClassnameStored = true, value = "config")
@Indexes({@Index(fields = {@Field("ARN"), @Field("configurationItemCaptureTime")}), @Index(fields = {@Field("resourceName"), @Field("resourceType")})})
public class Config {

    @JsonProperty("_id")
    @Id
    private String id;
    @JsonProperty("relationships")
    private List<Relationship> relationships = null;
    @JsonProperty("resourceName")
    @Indexed
    private String resourceName;
    @JsonProperty("configurationItemCaptureTime")
    private String configurationItemCaptureTime;
    @JsonProperty("awsAccountId")
    private String awsAccountId;
    @JsonProperty("configurationItemStatus")
    @Indexed
    private String configurationItemStatus;
    @JsonProperty("resourceType")
    @Indexed
    private String resourceType;
    @JsonProperty("resourceId")
    private String resourceId;
    @JsonProperty("ARN")
    @Indexed
    private String ARN;
    @JsonProperty("awsRegion")
    private String awsRegion;
    @JsonProperty("resourceCreationTime")
    private String resourceCreationTime;
    @JsonProperty("resourceDeletedTime")
    private String resourceDeletedTime;
//    @JsonIgnore
//    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("resourceName")
    public String getResourceName() {
        return resourceName;
    }

    @JsonProperty("resourceName")
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @JsonProperty("configurationItemCaptureTime")
    public String getConfigurationItemCaptureTime() {
        return configurationItemCaptureTime;
    }

    @JsonProperty("configurationItemCaptureTime")
    public void setConfigurationItemCaptureTime(String configurationItemCaptureTime) {
        this.configurationItemCaptureTime = configurationItemCaptureTime;
    }

    @JsonProperty("relationships")
    public List<Relationship> getRelationships() {
        return relationships;
    }

    @JsonProperty("relationships")
    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    @JsonProperty("configurationItemStatus")
    public String getConfigurationItemStatus() {
        return configurationItemStatus;
    }

    @JsonProperty("configurationItemStatus")
    public void setConfigurationItemStatus(String configurationItemStatus) {
        this.configurationItemStatus = configurationItemStatus;
    }

    @JsonProperty("resourceType")
    public String getResourceType() {
        return resourceType;
    }

    @JsonProperty("resourceType")
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    @JsonProperty("resourceId")
    public String getResourceId() {
        return resourceId;
    }

    @JsonProperty("resourceId")
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    @JsonProperty("ARN")
    public String getARN() {
        return ARN;
    }

    @JsonProperty("ARN")
    public void setARN(String aRN) {
        this.ARN = aRN;
    }

    @JsonProperty("awsRegion")
    public String getAwsRegion() {
        return awsRegion;
    }

    @JsonProperty("awsRegion")
    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    public ZonedDateTime getResourceCreationZDT() {
        if (this.resourceCreationTime == null)
            return ZonedDateTime.ofInstant(Instant.EPOCH, ZoneId.systemDefault());
        return ZonedDateTime.parse(resourceCreationTime);
    }

    public ZonedDateTime getResourceDeletedZDT() {
        if (this.resourceDeletedTime == null)
            return ZonedDateTime.now();
        return ZonedDateTime.parse(resourceDeletedTime);
    }

    public String getResourceCreationTime() {
        return resourceCreationTime;
    }

    @JsonProperty("resourceCreationTime")
    public void setResourceCreationTime(String resourceCreationTime) {
        this.resourceCreationTime = resourceCreationTime;
    }

    public String getResourceDeletedTime() {
        return resourceDeletedTime;
    }

    public void setResourceDeletedTime(String resourceDeletedTime) {
        this.resourceDeletedTime = resourceDeletedTime;
    }

    public String getAwsAccountId() {
        return awsAccountId;
    }

    public void setAwsAccountId(String awsAccountId) {
        this.awsAccountId = awsAccountId;
    }

}