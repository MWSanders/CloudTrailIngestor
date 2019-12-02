package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;

import java.time.ZonedDateTime;

/**
 * Created by Matt on 9/5/2017.
 */
@Entity(noClassnameStored = true)
public class SegmentedEc2Instance extends SegmentedBase {
    private Ec2Instance ec2Instance;

    public String generateId() {
        return SegmentedBase.generateId(getOprKey(), getEnvironment(), getARN());
    }

    public String generateOprKey() {
        return SegmentedBase.generateOprKey(getStartTime(), getEndTime());
    }

    public SegmentedEc2Instance() {
    }

    public SegmentedEc2Instance(Ec2Instance ec2Instance) {
        this.ec2Instance = ec2Instance;
    }

    @JsonProperty("configuration")
    public Ec2Configuration getConfiguration() {
        return ec2Instance.getConfiguration();
    }

    @JsonProperty("configurationItemStatus")
    public String getConfigurationItemStatus() {
        return ec2Instance.getConfigurationItemStatus();
    }

    @JsonProperty("configurationItemCaptureTime")
    public String getConfigurationItemCaptureTime() {
        return ec2Instance.getConfigurationItemCaptureTime();
    }

    @JsonProperty("configuration")
    public void setConfiguration(Ec2Configuration configuration) {
        ec2Instance.setConfiguration(configuration);
    }

    public ZonedDateTime getResourceCreationZDT() {
        return ec2Instance.getResourceCreationZDT();
    }

    public String getResourceDeletedTime() {
        return ec2Instance.getResourceDeletedTime();
    }

    @JsonProperty("resourceCreationTime")
    public void setResourceCreationTime(String resourceCreationTime) {
        ec2Instance.setResourceCreationTime(resourceCreationTime);
    }

    @JsonProperty("resourceType")
    public void setResourceType(String resourceType) {
        ec2Instance.setResourceType(resourceType);
    }

    @JsonProperty("resourceId")
    public String getResourceId() {
        return ec2Instance.getResourceId();
    }

    @JsonProperty("ARN")
    public String getARN() {
        return ec2Instance.getARN();
    }

    public String getResourceCreationTime() {
        return ec2Instance.getResourceCreationTime();
    }

    @JsonProperty("ARN")
    public void setARN(String aRN) {
        ec2Instance.setARN(aRN);
    }

    public ZonedDateTime getResourceDeletedZDT() {
        return ec2Instance.getResourceDeletedZDT();
    }

    @JsonProperty("awsRegion")
    public String getAwsRegion() {
        return ec2Instance.getAwsRegion();
    }

    @JsonProperty("configurationItemStatus")
    public void setConfigurationItemStatus(String configurationItemStatus) {
        ec2Instance.setConfigurationItemStatus(configurationItemStatus);
    }

    @JsonProperty("awsRegion")
    public void setAwsRegion(String awsRegion) {
        ec2Instance.setAwsRegion(awsRegion);
    }

    @JsonProperty("resourceName")
    public String getResourceName() {
        return ec2Instance.getResourceName();
    }

    @JsonProperty("resourceName")
    public void setResourceName(String resourceName) {
        ec2Instance.setResourceName(resourceName);
    }

    public void setResourceDeletedTime(String resourceDeletedTime) {
        ec2Instance.setResourceDeletedTime(resourceDeletedTime);
    }

    @JsonProperty("resourceType")
    public String getResourceType() {
        return ec2Instance.getResourceType();
    }


    @JsonProperty("configurationItemCaptureTime")
    public void setConfigurationItemCaptureTime(String configurationItemCaptureTime) {
        ec2Instance.setConfigurationItemCaptureTime(configurationItemCaptureTime);
    }

    @JsonProperty("resourceId")
    public void setResourceId(String resourceId) {
        ec2Instance.setResourceId(resourceId);
    }
}
