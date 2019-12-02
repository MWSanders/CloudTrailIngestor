package edu.mines.model;

/**
 * Created by Matt on 10/6/2016.
 */
public class RunParams {
    private int test_days;
    private int random_seed;
    private String algorithm;
    private int train_days;
    private String obs_start;
    private String opr_end;
    private int periods;
    private String type;

    public int getTest_days() {
        return test_days;
    }

    public void setTest_days(int test_days) {
        this.test_days = test_days;
    }

    public int getRandom_seed() {
        return random_seed;
    }

    public void setRandom_seed(int random_seed) {
        this.random_seed = random_seed;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public int getTrain_days() {
        return train_days;
    }

    public void setTrain_days(int train_days) {
        this.train_days = train_days;
    }

    public String getObs_start() {
        return obs_start;
    }

    public void setObs_start(String obs_start) {
        this.obs_start = obs_start;
    }

    public String getOpr_end() {
        return opr_end;
    }

    public void setOpr_end(String opr_end) {
        this.opr_end = opr_end;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
