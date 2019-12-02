package edu.mines.model.config;

import org.mongodb.morphia.annotations.Entity;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Matt on 9/5/2017.
 */
@Entity(noClassnameStored = true)
public class SegmentedResources extends SegmentedBase {

    // AWS::EC2:Instance, <i-124abcde, config>
    private Map<String, Map<String, Config>> resources = new TreeMap<>();
    // arn:foo:bar, AWS::EC2:Instance
    private Map<String, String> resourceClasses = new TreeMap<>();

    public Map<String, Map<String, Config>> getResources() {
        return resources;
    }

    public void setResources(Map<String, Map<String, Config>> resources) {
        this.resources = resources;
    }

    public Map<String, String> getResourceClasses() {
        return resourceClasses;
    }

    public void setResourceClasses(Map<String, String> resourceClasses) {
        this.resourceClasses = resourceClasses;
    }
}
