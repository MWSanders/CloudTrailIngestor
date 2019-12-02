package edu.mines.model;

/**
 * Created by Matt on 8/9/2016.
 */
public class ClassifierCommonParams {
    private String opr_privs;
    private String obs_privs;
    private Float prediction_threshold;

    public String getOpr_privs() {
        return opr_privs;
    }

    public void setOpr_privs(String opr_privs) {
        this.opr_privs = opr_privs;
    }

    public String getObs_privs() {
        return obs_privs;
    }

    public void setObs_privs(String obs_privs) {
        this.obs_privs = obs_privs;
    }

    public Float getPrediction_threshold() {
        return prediction_threshold;
    }

    public void setPrediction_threshold(Float prediction_threshold) {
        this.prediction_threshold = prediction_threshold;
    }
}
