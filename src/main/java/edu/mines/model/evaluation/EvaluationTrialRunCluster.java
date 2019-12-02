package edu.mines.model.evaluation;

import java.util.StringJoiner;

/**
 * Created by Matt on 10/27/2016.
 */
public class EvaluationTrialRunCluster extends EvaluationTrialRunWeekdayHandler {
    private int minSamples;
    private String distanceMetric;

    public EvaluationTrialRunCluster(String genMethod, String weekdayHandling, int minSamples, String distanceMetric) {
        super(genMethod, weekdayHandling);
        this.minSamples = minSamples;
        this.distanceMetric = distanceMetric;
    }

    public EvaluationTrialRunCluster(String modelMethod) {
        //dbscan|none|2|averageNZ
        String[] parts = modelMethod.split("\\|");
        this.genMethod = parts[0];
        this.weekdayHandling = parts[1];
        this.minSamples = Integer.parseInt(parts[2]);
        this.distanceMetric = parts[3];
    }

    public int getMinSamples() {
        return minSamples;
    }

    public void setMinSamples(int minSamples) {
        this.minSamples = minSamples;
    }

    public String getDistanceMetric() {
        return distanceMetric;
    }

    public void setDistanceMetric(String distanceMetric) {
        this.distanceMetric = distanceMetric;
    }

    @Override
    public String toString() {
        // dbscan|2|averageZ
        StringJoiner stringJoiner = new StringJoiner("|");
        stringJoiner.add(super.toString()).add(Integer.toString(minSamples)).add(distanceMetric);
        return stringJoiner.toString();
    }
}
