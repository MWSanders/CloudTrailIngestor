package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matt on 4/22/2016.
 */
@Entity(noClassnameStored = true)
public class OperationExercisedClassifiers {

    @Id
    @JsonProperty("_id")
    private String id;

    private String opr_key;
    private String obs_key;
    private String name;
    //    private ClassifierSpecificParams clf_params;
//    private ClassifierCommonParams common_params;
    private HashMap<String, HashMap<String, ArrayList<String>>> policy;
    private HashMap<String, String> type;
    private String common_params_id;
    private String clf_params_id;

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String name) {
        this.id = name;
    }


    public String getOpr_key() {
        return opr_key;
    }

    public void setOpr_key(String opr_key) {
        this.opr_key = opr_key;
    }

    public String getObs_key() {
        return obs_key;
    }

    public void setObs_key(String obs_key) {
        this.obs_key = obs_key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public ClassifierSpecificParams getClf_params() {
//        return clf_params;
//    }
//
//    public void setClf_params(ClassifierSpecificParams clf_params) {
//        this.clf_params = clf_params;
//    }
//
//    public ClassifierCommonParams getCommon_params() {
//        return common_params;
//    }
//
//    public void setCommon_params(ClassifierCommonParams common_params) {
//        this.common_params = common_params;
//    }

    public HashMap<String, HashMap<String, ArrayList<String>>> getPolicy() {
        return policy;
    }

    public void setPolicy(HashMap<String, HashMap<String, ArrayList<String>>> policy) {
        this.policy = policy;
    }

    public HashMap<String, String> getType() {
        return type;
    }

    public void setType(HashMap<String, String> type) {
        this.type = type;
    }
}
