package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Id;

import java.util.HashMap;

/**
 * Created by Matt on 11/28/2016.
 */
public class ClassifierTrialRunChoice {

    @Id
    @JsonProperty("_id")
    private String id;

    private String algorithmId;
    private String entitySummarySubsetId;
    private String common_params_id;
    private String clf_params_id;
    private HashMap<String, String> clf_params;
    private HashMap<String, String> common_params;
    private EvalPeriod eval_period;
    private RunParams run_params;

    public ClassifierTrialRunChoice() {
    }

    public ClassifierTrialRunChoice(ClassifierPeriodSummary classifierPeriodSummary, String algorithmId, String entitySummarySubsetId) {
        this.algorithmId = algorithmId;
        this.entitySummarySubsetId = entitySummarySubsetId;
        this.id = algorithmId + '|' + entitySummarySubsetId;
        this.common_params_id = classifierPeriodSummary.getCommon_params_id();
        this.clf_params_id = classifierPeriodSummary.getClf_params_id();
        this.clf_params = classifierPeriodSummary.getClf_params();
        this.common_params = classifierPeriodSummary.getCommon_params();
        this.eval_period = classifierPeriodSummary.getEval_period();
        this.run_params = classifierPeriodSummary.getRun_params();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(String algorithmId) {
        this.algorithmId = algorithmId;
    }

    public String getEntitySummarySubsetId() {
        return entitySummarySubsetId;
    }

    public void setEntitySummarySubsetId(String entitySummarySubsetId) {
        this.entitySummarySubsetId = entitySummarySubsetId;
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

    public HashMap<String, String> getClf_params() {
        return clf_params;
    }

    public void setClf_params(HashMap<String, String> clf_params) {
        this.clf_params = clf_params;
    }

    public HashMap<String, String> getCommon_params() {
        return common_params;
    }

    public void setCommon_params(HashMap<String, String> common_params) {
        this.common_params = common_params;
    }

    public EvalPeriod getEval_period() {
        return eval_period;
    }

    public void setEval_period(EvalPeriod eval_period) {
        this.eval_period = eval_period;
    }
}
