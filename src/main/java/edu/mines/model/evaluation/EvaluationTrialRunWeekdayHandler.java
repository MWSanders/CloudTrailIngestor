package edu.mines.model.evaluation;

/**
 * Created by Matt on 10/27/2016.
 */
public class EvaluationTrialRunWeekdayHandler extends EvaluationTrialRunBase {
    protected String weekdayHandling;

    public EvaluationTrialRunWeekdayHandler() {
    }

    public EvaluationTrialRunWeekdayHandler(String genMethod, String weekdayHandling) {
        super(genMethod);
        this.weekdayHandling = weekdayHandling;
    }

    public EvaluationTrialRunWeekdayHandler(String completeGenMethod) {
        String[] parts = completeGenMethod.split("\\|");
        this.genMethod = parts[0];
        this.weekdayHandling = parts[1];
    }

    public String getWeekdayHandling() {
        return weekdayHandling;
    }

    public void setWeekdayHandling(String weekdayHandling) {
        this.weekdayHandling = weekdayHandling;
    }

    @Override
    public String toString() {
        return super.toString() + "|" + weekdayHandling;
    }
}
