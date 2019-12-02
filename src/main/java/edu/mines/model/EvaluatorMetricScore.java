package edu.mines.model;

import org.mongodb.morphia.annotations.Transient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class EvaluatorMetricScore implements Comparable<EvaluatorMetricScore> {
    @Transient
    private List<Double> uniquePrecisions = new ArrayList<>();
    @Transient
    private List<Double> uniqueRecalls = new ArrayList<>();

    private Set<String> exercisedGrantedActions = new TreeSet<>();

    private Double uniquePrecisionAvg;
    private Double uniqueRecallAvg;

    public void addEvaluatorMetric(EvaluatorMetric evaluatorMetric) {
        uniquePrecisions.add(evaluatorMetric.getAlgorithmMetric().getUniquePrecision());
        uniquePrecisionAvg = uniquePrecisions.stream().mapToDouble(a -> a).average().getAsDouble();
        uniqueRecalls.add(evaluatorMetric.getAlgorithmMetric().getUniqueRecall());
        uniqueRecallAvg = uniqueRecalls.stream().mapToDouble(a -> a).average().getAsDouble();
        exercisedGrantedActions.addAll(evaluatorMetric.getAlgorithmMetric().getExercisedGrantedActions().keySet());
    }

    public Double getUniquePrecisionAvg() {
        return uniquePrecisionAvg;
    }

    public void setUniquePrecisionAvg(double uniquePrecisionAvg) {
        this.uniquePrecisionAvg = uniquePrecisionAvg;
    }

    public Double getUniqueRecallAvg() {
        return uniqueRecallAvg;
    }

    public void setUniqueRecallAvg(double uniqueRecallAvg) {
        this.uniqueRecallAvg = uniqueRecallAvg;
    }

    public Set<String> getExercisedGrantedActions() {
        return exercisedGrantedActions;
    }

    public void setExercisedGrantedActions(Set<String> exercisedGrantedActions) {
        this.exercisedGrantedActions = exercisedGrantedActions;
    }

    @Override
    public int compareTo(EvaluatorMetricScore o) {
        return this.getUniquePrecisionAvg().compareTo(o.getUniquePrecisionAvg());
    }
}