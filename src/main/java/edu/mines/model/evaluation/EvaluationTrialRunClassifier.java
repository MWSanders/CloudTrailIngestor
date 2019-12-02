package edu.mines.model.evaluation;

import java.util.StringJoiner;

/**
 * Created by Matt on 10/27/2016.
 */
public class EvaluationTrialRunClassifier extends EvaluationTrialRunWeekdayHandler {
    private String testTrainSplit;
    private String weekDayFeature;
    private String scoringMetric;
    private String learnPosNeg;

    public EvaluationTrialRunClassifier(String genMethod, String weekdayHandling, String testTrainSplit, String weekDayFeature, String scoringMetric, String learnPosNeg) {
        super(genMethod, weekdayHandling);
        this.testTrainSplit = testTrainSplit;
        this.weekDayFeature = weekDayFeature;
        this.scoringMetric = scoringMetric;
        this.learnPosNeg = learnPosNeg;
    }

    public EvaluationTrialRunClassifier(String modelMethod) {
        //decision_tree|filler_weekday|sub_nonover_dow|none|segmented|Beta_fscores.f0_1
        String[] parts = modelMethod.split("\\|");
        this.genMethod = parts[0];
        this.weekdayHandling = parts[1];
        this.testTrainSplit = parts[2];
        this.weekDayFeature = parts[3];
        this.learnPosNeg = parts[4];
        this.scoringMetric = parts[5];
    }

    public String getTestTrainSplit() {
        return testTrainSplit;
    }

    public void setTestTrainSplit(String testTrainSplit) {
        this.testTrainSplit = testTrainSplit;
    }

    public String getWeekDayFeature() {
        return weekDayFeature;
    }

    public void setWeekDayFeature(String weekDayFeature) {
        this.weekDayFeature = weekDayFeature;
    }

    public String getScoringMetric() {
        return scoringMetric;
    }

    public void setScoringMetric(String scoringMetric) {
        this.scoringMetric = scoringMetric;
    }

    public String getLearnPosNeg() {
        return learnPosNeg;
    }

    public void setLearnPosNeg(String learnPosNeg) {
        this.learnPosNeg = learnPosNeg;
    }

    @Override
    public String toString() {
        // name|weekdayHandling|TestTrainSplit|WeekdayFeature|learnPosNeg|scoring
        StringJoiner stringJoiner = new StringJoiner("|");
        stringJoiner.add(super.toString()).add(testTrainSplit).add(weekDayFeature).add(learnPosNeg).add(scoringMetric);
        return stringJoiner.toString();
    }
}
