package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Matt on 9/4/2017.
 */
@Entity(noClassnameStored = true, value = "SegmentedEntity")
public class SegmentedIamRole extends SegmentedEntity {

    @Indexed
    private Set<String> instanceProfileNames = new TreeSet<>();

    public SegmentedIamRole() {
    }

    public SegmentedIamRole(IamRole iamRole) {
        this.iamRole = iamRole;
    }

    public String generateId() {
        return SegmentedBase.generateId(getOprKey(), getEnvironment(), getResourceName());
    }

    public String generateOprKey() {
        return SegmentedBase.generateOprKey(getStartTime(), getEndTime());
    }

    @JsonProperty("relationships")
    public List<Relationship> getRelationships() {
        return iamRole.getRelationships();
    }

    @JsonProperty("relationships")
    public void setRelationships(List<Relationship> relationships) {
        iamRole.setRelationships(relationships);
    }

    @JsonProperty("configurationItemStatus")
    public String getConfigurationItemStatus() {
        return iamRole.getConfigurationItemStatus();
    }

    public ZonedDateTime getResourceDeletedZDT() {
        return iamRole.getResourceDeletedZDT();
    }

    @JsonProperty("configurationItemCaptureTime")
    public String getConfigurationItemCaptureTime() {
        return iamRole.getConfigurationItemCaptureTime();
    }

    @JsonProperty("awsRegion")
    public String getAwsRegion() {
        return iamRole.getAwsRegion();
    }

    public ZonedDateTime getResourceCreationZDT() {
        return iamRole.getResourceCreationZDT();
    }

    public String getResourceDeletedTime() {
        return iamRole.getResourceDeletedTime();
    }

    @JsonProperty("configuration")
    public RoleConfiguration getConfiguration() {
        return iamRole.getConfiguration();
    }

    @JsonProperty("resourceCreationTime")
    public void setResourceCreationTime(String resourceCreationTime) {
        iamRole.setResourceCreationTime(resourceCreationTime);
    }

    @JsonProperty("configurationItemStatus")
    public void setConfigurationItemStatus(String configurationItemStatus) {
        iamRole.setConfigurationItemStatus(configurationItemStatus);
    }

    @JsonProperty("resourceType")
    public void setResourceType(String resourceType) {
        iamRole.setResourceType(resourceType);
    }

    @JsonProperty("awsRegion")
    public void setAwsRegion(String awsRegion) {
        iamRole.setAwsRegion(awsRegion);
    }

    @JsonProperty("resourceName")
    public String getResourceName() {
        return iamRole.getResourceName();
    }

    @JsonProperty("resourceName")
    public void setResourceName(String resourceName) {
        iamRole.setResourceName(resourceName);
        super.setResourceName(resourceName);
    }

    @JsonProperty("resourceId")
    public String getResourceId() {
        return iamRole.getResourceId();
    }

    public void setResourceDeletedTime(String resourceDeletedTime) {
        iamRole.setResourceDeletedTime(resourceDeletedTime);
    }

    @JsonProperty("configuration")
    public void setConfiguration(RoleConfiguration configuration) {
        iamRole.setConfiguration(configuration);
    }

    @JsonProperty("ARN")
    public String getARN() {
        return iamRole.getARN();
    }

    @JsonProperty("resourceType")
    public String getResourceType() {
        return iamRole.getResourceType();
    }

    public String getResourceCreationTime() {
        return iamRole.getResourceCreationTime();
    }

    @JsonProperty("configurationItemCaptureTime")
    public void setConfigurationItemCaptureTime(String configurationItemCaptureTime) {
        iamRole.setConfigurationItemCaptureTime(configurationItemCaptureTime);
    }

    @JsonProperty("resourceId")
    public void setResourceId(String resourceId) {
        iamRole.setResourceId(resourceId);
    }

    @JsonProperty("ARN")
    public void setARN(String aRN) {
        iamRole.setARN(aRN);
    }

    public Set<String> getInstanceProfileNames() {
        return instanceProfileNames;
    }

    public void setInstanceProfileNames(Set<String> instanceProfileNames) {
        this.instanceProfileNames = instanceProfileNames;
    }
}
