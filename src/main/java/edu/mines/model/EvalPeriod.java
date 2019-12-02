package edu.mines.model;

/**
 * Created by Matt on 9/17/2016.
 */
public class EvalPeriod {
    private String opr_key;
    private String obs_key;
    private String obs_opr_key;

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

    public String getObs_opr_key() {
        return obs_opr_key;
    }

    public void setObs_opr_key(String obs_opr_key) {
        this.obs_opr_key = obs_opr_key;
    }
}
