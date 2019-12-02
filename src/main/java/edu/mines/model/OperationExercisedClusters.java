package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;

/**
 * Created by Matt on 4/22/2016.
 */
@Entity(noClassnameStored = true)
public class OperationExercisedClusters {
    @Id
    @JsonProperty("_id")
    private String id;

    private ArrayList<ArrayList<String>> clusters = new ArrayList<>();
    private ArrayList<String> outliers = new ArrayList<>();
    private float percent_users_clustered;
    private String algorithm_id;
    private String[] userIdentityTypes;
    private String[] observation_period_ids;

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String name) {
        this.id = name;
    }

    public ArrayList<ArrayList<String>> getClusters() {
        return clusters;
    }

    public void setClusters(ArrayList<ArrayList<String>> clusters) {
        this.clusters = clusters;
    }

    public ArrayList<String> getOutliers() {
        return outliers;
    }

    public void setOutliers(ArrayList<String> outliers) {
        this.outliers = outliers;
    }

    public float getPercent_users_clustered() {
        return percent_users_clustered;
    }

    public void setPercent_users_clustered(float percent_users_clustered) {
        this.percent_users_clustered = percent_users_clustered;
    }

    public String getAlgorithm_id() {
        return algorithm_id;
    }

    public void setAlgorithm_id(String algorithm_id) {
        this.algorithm_id = algorithm_id;
    }

    public String[] getUserIdentityTypes() {
        return userIdentityTypes;
    }

    public void setUserIdentityTypes(String[] userIdentityTypes) {
        this.userIdentityTypes = userIdentityTypes;
    }

    public String[] getObservation_period_ids() {
        return observation_period_ids;
    }

    public void setObservation_period_ids(String[] observation_period_ids) {
        this.observation_period_ids = observation_period_ids;
    }
}
