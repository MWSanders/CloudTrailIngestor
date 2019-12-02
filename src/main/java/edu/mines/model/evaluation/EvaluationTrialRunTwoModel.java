package edu.mines.model.evaluation;

/**
 * Created by Matt on 10/27/2016.
 */
public class EvaluationTrialRunTwoModel extends EvaluationTrialRunWeekdayHandler {
    //unsupervised: dbscan|none|2|averageNZ
    //supervised:   decision_tree|filler_weekday|sub_nonover_dow|none|segmented|Beta_fscores.f0_1
    //simple:       simple|filler_weekday
    private String weekdayModelName;
    private String weekendModelName;
    private EvaluationTrialRunWeekdayHandler weekdayHandler;
    private EvaluationTrialRunWeekdayHandler weekendHandler;
//    private String testTrainSplit;
//    private String weekDayFeature;
//    private String scoringMetric;
//    private String learnPosNeg;

    public EvaluationTrialRunTwoModel(String genMethod) { //, String weekdayHandling, String testTrainSplit, String weekDayFeature, String scoringMetric, String learnPosNeg) {
        super(genMethod, "none");//, weekdayHandling);
        String[] parts = genMethod.split("-");
        this.weekdayModelName = parts[0];
        this.weekendModelName = parts[1];
        this.weekdayHandler = getHandlerFromString(weekdayModelName);
        this.weekendHandler = getHandlerFromString(weekendModelName);
//        this.testTrainSplit = testTrainSplit;
//        this.weekDayFeature = weekDayFeature;
//        this.scoringMetric = scoringMetric;
//        this.learnPosNeg = learnPosNeg;
    }

    private EvaluationTrialRunWeekdayHandler getHandlerFromString(String subGenMethod) {
        if (subGenMethod.startsWith("simple")) {
            return new EvaluationTrialRunWeekdayHandler(subGenMethod);
        } else if (subGenMethod.startsWith("dbscan")) {
            return new EvaluationTrialRunCluster(subGenMethod);
        } else if (subGenMethod.startsWith("decision_tree")) {
            return new EvaluationTrialRunClassifier(subGenMethod);
        }
        return null;
    }

    public String getWeekdayModelName() {
        return weekdayModelName;
    }

    public void setWeekdayModelName(String weekdayModelName) {
        this.weekdayModelName = weekdayModelName;
    }

    public String getWeekendModelName() {
        return weekendModelName;
    }

    public void setWeekendModelName(String weekendModelName) {
        this.weekendModelName = weekendModelName;
    }

    public EvaluationTrialRunWeekdayHandler getWeekdayHandler() {
        return weekdayHandler;
    }

    public void setWeekdayHandler(EvaluationTrialRunWeekdayHandler weekdayHandler) {
        this.weekdayHandler = weekdayHandler;
    }

    public EvaluationTrialRunWeekdayHandler getWeekendHandler() {
        return weekendHandler;
    }

    public void setWeekendHandler(EvaluationTrialRunWeekdayHandler weekendHandler) {
        this.weekendHandler = weekendHandler;
    }

    @Override
    public String toString() {
        return weekdayHandler.toString() + "-" + weekendHandler.toString() + "-none";
    }
}
