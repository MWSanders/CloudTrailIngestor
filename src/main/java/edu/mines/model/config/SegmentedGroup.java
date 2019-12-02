package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by Matt on 9/5/2017.
 */
@Entity(noClassnameStored = true)
public class SegmentedGroup extends SegmentedBase {
    private IamGroup iamGroup;

    public SegmentedGroup() {
    }

    public SegmentedGroup(IamGroup iamGroup) {
        this.iamGroup = iamGroup;
    }

    public String generateId() {
        return SegmentedBase.generateId(getOprKey(), getEnvironment(), getARN());
    }

    public String generateOprKey() {
        return SegmentedBase.generateOprKey(getStartTime(), getEndTime());
    }

    public GroupConfiguration getConfiguration() {
        return iamGroup.getConfiguration();
    }

    @JsonProperty("configurationItemStatus")
    public String getConfigurationItemStatus() {
        return iamGroup.getConfigurationItemStatus();
    }

    @JsonProperty("relationships")
    public void setRelationships(List<Relationship> relationships) {
        iamGroup.setRelationships(relationships);
    }

    public ZonedDateTime getResourceDeletedZDT() {
        return iamGroup.getResourceDeletedZDT();
    }

    @JsonProperty("configurationItemCaptureTime")
    public String getConfigurationItemCaptureTime() {
        return iamGroup.getConfigurationItemCaptureTime();
    }

    @JsonProperty("awsRegion")
    public String getAwsRegion() {
        return iamGroup.getAwsRegion();
    }

    public ZonedDateTime getResourceCreationZDT() {
        return iamGroup.getResourceCreationZDT();
    }

    public String getResourceDeletedTime() {
        return iamGroup.getResourceDeletedTime();
    }

    @JsonProperty("resourceCreationTime")
    public void setResourceCreationTime(String resourceCreationTime) {
        iamGroup.setResourceCreationTime(resourceCreationTime);
    }

    @JsonProperty("configurationItemStatus")
    public void setConfigurationItemStatus(String configurationItemStatus) {
        iamGroup.setConfigurationItemStatus(configurationItemStatus);
    }

    @JsonProperty("resourceType")
    public void setResourceType(String resourceType) {
        iamGroup.setResourceType(resourceType);
    }

    @JsonProperty("awsRegion")
    public void setAwsRegion(String awsRegion) {
        iamGroup.setAwsRegion(awsRegion);
    }

    @JsonProperty("resourceName")
    public String getResourceName() {
        return iamGroup.getResourceName();
    }

    @JsonProperty("relationships")
    public List<Relationship> getRelationships() {
        return iamGroup.getRelationships();
    }

    @JsonProperty("resourceName")
    public void setResourceName(String resourceName) {
        iamGroup.setResourceName(resourceName);
    }

    @JsonProperty("resourceId")
    public String getResourceId() {
        return iamGroup.getResourceId();
    }

    public void setResourceDeletedTime(String resourceDeletedTime) {
        iamGroup.setResourceDeletedTime(resourceDeletedTime);
    }

    public void setConfiguration(GroupConfiguration configuration) {
        iamGroup.setConfiguration(configuration);
    }

    @JsonProperty("ARN")
    public String getARN() {
        return iamGroup.getARN();
    }

    @JsonProperty("resourceType")
    public String getResourceType() {
        return iamGroup.getResourceType();
    }

    public String getResourceCreationTime() {
        return iamGroup.getResourceCreationTime();
    }

    @JsonProperty("configurationItemCaptureTime")
    public void setConfigurationItemCaptureTime(String configurationItemCaptureTime) {
        iamGroup.setConfigurationItemCaptureTime(configurationItemCaptureTime);
    }

    @JsonProperty("resourceId")
    public void setResourceId(String resourceId) {
        iamGroup.setResourceId(resourceId);
    }

    @JsonProperty("ARN")
    public void setARN(String aRN) {
        iamGroup.setARN(aRN);
    }
}
