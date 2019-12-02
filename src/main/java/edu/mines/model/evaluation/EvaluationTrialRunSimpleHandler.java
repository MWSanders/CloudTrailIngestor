package edu.mines.model.evaluation;

/**
 * Created by Matt on 1/21/2017.
 */
public class EvaluationTrialRunSimpleHandler extends EvaluationTrialRunWeekdayHandler {
    public EvaluationTrialRunSimpleHandler(String genMethod, String weekdayHandling) {
        super(genMethod, weekdayHandling);
    }

    //simple:       simple|filler_weekday
    public EvaluationTrialRunSimpleHandler(String genMethodComplete) {
        super(genMethodComplete);
    }
}
