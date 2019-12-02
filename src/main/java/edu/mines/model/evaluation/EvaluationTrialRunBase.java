package edu.mines.model.evaluation;

/**
 * Created by Matt on 10/27/2016.
 */
public class EvaluationTrialRunBase {
    String genMethod;

    public String getGenMethod() {
        return genMethod;
    }

    public void setGenMethod(String genMethod) {
        this.genMethod = genMethod;
    }

    public EvaluationTrialRunBase(String genMethod) {
        this.genMethod = genMethod;
    }

    public EvaluationTrialRunBase() {
    }

    @Override
    public String toString() {
        return genMethod;
    }
}
