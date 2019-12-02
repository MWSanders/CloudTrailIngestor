package edu.mines.model;

/**
 * Created by Matt on 1/21/2017.
 */

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(noClassnameStored = true)
public class ClusterStats {

    @Id
    @JsonProperty("_id")
    private String _id;
    @JsonProperty("elapsedTime")
    private Double elapsedTime;
    @JsonProperty("avgClusters")
    private Double avgClusters;
    @JsonProperty("algorithm_id")
    private String algorithmId;
    @JsonProperty("algorithm")
    private Map<String, String> algorithm;
    @JsonProperty("percentUsersClustered")
    private Double percentUsersClustered;
    @JsonProperty("avgEntitiesPerCluster")
    private Double avgEntitiesPerCluster;
    @JsonProperty("userIdentityTypes")
    private List<String> userIdentityTypes = null;
    @JsonProperty("days")
    private Integer days;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("_id")
    public String getId() {
        return _id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this._id = id;
    }

    @JsonProperty("elapsedTime")
    public Double getElapsedTime() {
        return elapsedTime;
    }

    @JsonProperty("elapsedTime")
    public void setElapsedTime(Double elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    @JsonProperty("avgClusters")
    public Double getAvgClusters() {
        return avgClusters;
    }

    @JsonProperty("avgClusters")
    public void setAvgClusters(Double avgClusters) {
        this.avgClusters = avgClusters;
    }

    @JsonProperty("algorithm_id")
    public String getAlgorithmId() {
        return algorithmId;
    }

    @JsonProperty("algorithm_id")
    public void setAlgorithmId(String algorithmId) {
        this.algorithmId = algorithmId;
    }

    @JsonProperty("algorithm")
    public Map<String, String> getAlgorithm() {
        return algorithm;
    }

    @JsonProperty("algorithm")
    public void setAlgorithm(Map<String, String> algorithm) {
        this.algorithm = algorithm;
    }

    @JsonProperty("percentUsersClustered")
    public Double getPercentUsersClustered() {
        return percentUsersClustered;
    }

    @JsonProperty("percentUsersClustered")
    public void setPercentUsersClustered(Double percentUsersClustered) {
        this.percentUsersClustered = percentUsersClustered;
    }

    @JsonProperty("avgEntitiesPerCluster")
    public Double getAvgEntitiesPerCluster() {
        return avgEntitiesPerCluster;
    }

    @JsonProperty("avgEntitiesPerCluster")
    public void setAvgEntitiesPerCluster(Double avgEntitiesPerCluster) {
        this.avgEntitiesPerCluster = avgEntitiesPerCluster;
    }

    @JsonProperty("userIdentityTypes")
    public List<String> getUserIdentityTypes() {
        return userIdentityTypes;
    }

    @JsonProperty("userIdentityTypes")
    public void setUserIdentityTypes(List<String> userIdentityTypes) {
        this.userIdentityTypes = userIdentityTypes;
    }

    @JsonProperty("days")
    public Integer getDays() {
        return days;
    }

    @JsonProperty("days")
    public void setDays(Integer days) {
        this.days = days;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
