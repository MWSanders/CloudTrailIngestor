package edu.mines.model.config;

/**
 * Created by Matt on 9/27/2017.
 */
public class SegmentedResource {
    private String resourceType;
    private String resourceName;
    private String ARN;

    public SegmentedResource() {
    }

    public SegmentedResource(Config config) {
        this.resourceName = config.getResourceName();
        this.resourceType = config.getResourceType();
        this.ARN = config.getARN();
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getARN() {
        return ARN;
    }

    public void setARN(String ARN) {
        this.ARN = ARN;
    }
}
