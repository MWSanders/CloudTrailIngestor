package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Matt on 9/5/2017.
 */
@Entity(noClassnameStored = true, value = "SegmentedEntity")
public class SegmentedIamUser extends SegmentedEntity {

    public SegmentedIamUser() {
    }

    public SegmentedIamUser(IamUser iamUser) {
        this.iamUser = iamUser;
    }

    public String generateId() {
        return SegmentedBase.generateId(getOprKey(), getEnvironment(), getResourceName());
    }

    public String generateOprKey() {
        return SegmentedBase.generateOprKey(getStartTime(), getEndTime());
    }

    @JsonProperty("configuration")
    public UserConfiguration getConfiguration() {
        return iamUser.getConfiguration();
    }

    @JsonProperty("configurationItemStatus")
    public String getConfigurationItemStatus() {
        return iamUser.getConfigurationItemStatus();
    }

    @JsonProperty("relationships")
    public void setRelationships(List<Relationship> relationships) {
        iamUser.setRelationships(relationships);
    }

    public ZonedDateTime getResourceDeletedZDT() {
        return iamUser.getResourceDeletedZDT();
    }

    @JsonProperty("configurationItemCaptureTime")
    public String getConfigurationItemCaptureTime() {
        return iamUser.getConfigurationItemCaptureTime();
    }

    @JsonProperty("awsRegion")
    public String getAwsRegion() {
        return iamUser.getAwsRegion();
    }

    public ZonedDateTime getResourceCreationZDT() {
        return iamUser.getResourceCreationZDT();
    }

    public String getResourceDeletedTime() {
        return iamUser.getResourceDeletedTime();
    }

    @JsonProperty("resourceCreationTime")
    public void setResourceCreationTime(String resourceCreationTime) {
        iamUser.setResourceCreationTime(resourceCreationTime);
    }

    @JsonProperty("configurationItemStatus")
    public void setConfigurationItemStatus(String configurationItemStatus) {
        iamUser.setConfigurationItemStatus(configurationItemStatus);
    }

    @JsonProperty("resourceType")
    public void setResourceType(String resourceType) {
        iamUser.setResourceType(resourceType);
    }

    @JsonProperty("awsRegion")
    public void setAwsRegion(String awsRegion) {
        iamUser.setAwsRegion(awsRegion);
    }

    @JsonProperty("configuration")
    public void setConfiguration(UserConfiguration configuration) {
        iamUser.setConfiguration(configuration);
    }

    @JsonProperty("resourceName")
    public String getResourceName() {
        return iamUser.getResourceName();
    }

    @JsonProperty("relationships")
    public List<Relationship> getRelationships() {
        return iamUser.getRelationships();
    }

    @JsonProperty("resourceName")
    public void setResourceName(String resourceName) {
        iamUser.setResourceName(resourceName);
        super.setResourceName(resourceName);
    }

    @JsonProperty("resourceId")
    public String getResourceId() {
        return iamUser.getResourceId();
    }

    public void setResourceDeletedTime(String resourceDeletedTime) {
        iamUser.setResourceDeletedTime(resourceDeletedTime);
    }

    @JsonProperty("ARN")
    public String getARN() {
        return iamUser.getARN();
    }

    @JsonProperty("resourceType")
    public String getResourceType() {
        return iamUser.getResourceType();
    }

    public String getResourceCreationTime() {
        return iamUser.getResourceCreationTime();
    }

    @JsonProperty("configurationItemCaptureTime")
    public void setConfigurationItemCaptureTime(String configurationItemCaptureTime) {
        iamUser.setConfigurationItemCaptureTime(configurationItemCaptureTime);
    }

    @JsonProperty("resourceId")
    public void setResourceId(String resourceId) {
        iamUser.setResourceId(resourceId);
    }

    @JsonProperty("ARN")
    public void setARN(String aRN) {
        iamUser.setARN(aRN);
    }
}
