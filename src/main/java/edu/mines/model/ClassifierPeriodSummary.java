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
public class ClassifierPeriodSummary {

    @Id
    @JsonProperty("_id")
    private String id;

    private float tFscore;
    private HashMap<String, Integer> confusion;
    private HashMap<String, Double> tBeta_fscores;
    private HashMap<String, Double> metrics;
    private float Fscore;
    private String name;
    private EvalPeriod eval_period;
    private HashMap<String, Double> Beta_fscores;
    private HashMap<String, Double> temporal_fscores;
    private String common_params_id;
    private String clf_params_id;
    private HashMap<String, HashMap<String, ArrayList<String>>> policy;
    private HashMap<String, String> type;
    private HashMap<String, String> common_params;
    private HashMap<String, String> clf_params;
    private RunParams run_params;
    //    private HashMap<String, String> run_params;
    private float time;


    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String name) {
        this.id = name;
    }

    public float gettFscore() {
        return tFscore;
    }

    public void settFscore(float tFscore) {
        this.tFscore = tFscore;
    }

    public HashMap<String, Integer> getConfusion() {
        return confusion;
    }

    public void setConfusion(HashMap<String, Integer> confusion) {
        this.confusion = confusion;
    }

    public HashMap<String, Double> gettBeta_fscores() {
        return tBeta_fscores;
    }

    public void settBeta_fscores(HashMap<String, Double> tBeta_fscores) {
        this.tBeta_fscores = tBeta_fscores;
    }

    public HashMap<String, Double> getMetrics() {
        return metrics;
    }

    public void setMetrics(HashMap<String, Double> metrics) {
        this.metrics = metrics;
    }

    public float getFscore() {
        return Fscore;
    }

    public void setFscore(float fscore) {
        Fscore = fscore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EvalPeriod getEval_period() {
        return eval_period;
    }

    public void setEval_period(EvalPeriod eval_period) {
        this.eval_period = eval_period;
    }

    public HashMap<String, Double> getBeta_fscores() {
        return Beta_fscores;
    }

    public void setBeta_fscores(HashMap<String, Double> beta_fscores) {
        Beta_fscores = beta_fscores;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getCommon_params_id() {
        return common_params_id;
    }

    public void setCommon_params_id(String common_params_id) {
        this.common_params_id = common_params_id;
    }

    public String getClf_params_id() {
        return clf_params_id;
    }

    public void setClf_params_id(String clf_params_id) {
        this.clf_params_id = clf_params_id;
    }

    public HashMap<String, Double> getTemporal_fscores() {
        return temporal_fscores;
    }

    public void setTemporal_fscores(HashMap<String, Double> temporal_fscores) {
        this.temporal_fscores = temporal_fscores;
    }

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

    public HashMap<String, String> getCommon_params() {
        return common_params;
    }

    public void setCommon_params(HashMap<String, String> common_params) {
        this.common_params = common_params;
    }

    public RunParams getRun_params() {
        return run_params;
    }

    public void setRun_params(RunParams run_params) {
        this.run_params = run_params;
    }

    public HashMap<String, String> getClf_params() {
        return clf_params;
    }

    public void setClf_params(HashMap<String, String> clf_params) {
        this.clf_params = clf_params;
    }
}
