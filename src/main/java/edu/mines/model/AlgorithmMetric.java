package edu.mines.model;

import java.util.*;

/**
 * Created by Matt on 5/9/2016.
 */
public class AlgorithmMetric {

    private Map<String, Long> exercisedGrantedActions = new TreeMap<>();
    private NavigableSet<String> unusedGrantedPrivs = new TreeSet<>();
    private Map<String, Long> exercisedDeniedActions = new TreeMap<>();
    private NavigableSet<String> unusedUngrantedPrivs = new TreeSet<>();
    private HashMap<String, Long> constrainableExercisedDeniedActions = new HashMap<>();
    private HashMap<String, Long> constrainableExercisedGrantedActions = new HashMap<>();
    private Double uniquePrecision;
    private Double uniqueRecall;

    public void calculateScores() {
        this.calcUniquePrecision();
        this.calcUniqueRecall();
    }

    private void calcUniquePrecision() {
        double uniqueExercisedGrantedPrivs = exercisedGrantedActions.size();
        double uniqueGrantedPrivs = exercisedGrantedActions.size() + unusedGrantedPrivs.size();
        uniquePrecision = AlgorithmTrial.safeDivideScoring(uniqueExercisedGrantedPrivs, uniqueGrantedPrivs);
    }

    private void calcUniqueRecall() {
        double uniqueExercisedGrantedPrivs = exercisedGrantedActions.size();
        double uniqueExercisedPrivs = exercisedGrantedActions.size() + exercisedDeniedActions.size();
        uniqueRecall = AlgorithmTrial.safeDivideScoring(uniqueExercisedGrantedPrivs, uniqueExercisedPrivs);
        if (uniqueRecall.doubleValue() == 0.0) {
            System.out.println();
        }
    }

    public Double getUniquePrecision() {
        return uniquePrecision;
    }

    public Double getUniqueRecall() {
        return uniqueRecall;
    }

    public static void addLongToActionMap(HashMap<String, Long> actionMap, String key, long value) {
        if (actionMap.containsKey(key)) {
            Long currentValue = actionMap.get(key);
            actionMap.put(key, currentValue + value);
        } else {
            actionMap.put(key, value);
        }
    }

    public Map<String, Long> getExercisedGrantedActions() {
        return exercisedGrantedActions;
    }

    public void setExercisedGrantedActions(HashMap<String, Long> exercisedGrantedActions) {
        this.exercisedGrantedActions = exercisedGrantedActions;
    }

    public NavigableSet<String> getUnusedGrantedPrivs() {
        return unusedGrantedPrivs;
    }

    public void setUnusedGrantedPrivs(NavigableSet<String> unusedGrantedPrivs) {
        this.unusedGrantedPrivs = unusedGrantedPrivs;
    }

    public Map<String, Long> getExercisedDeniedActions() {
        return exercisedDeniedActions;
    }

    public void setExercisedDeniedActions(HashMap<String, Long> exercisedDeniedActions) {
        this.exercisedDeniedActions = exercisedDeniedActions;
    }

    public HashMap<String, Long> getConstrainableExercisedDeniedActions() {
        return constrainableExercisedDeniedActions;
    }

    public void setConstrainableExercisedDeniedActions(HashMap<String, Long> constrainableExercisedDeniedActions) {
        this.constrainableExercisedDeniedActions = constrainableExercisedDeniedActions;
    }

    public HashMap<String, Long> getConstrainableExercisedGrantedActions() {
        return constrainableExercisedGrantedActions;
    }

    public void setConstrainableExercisedGrantedActions(HashMap<String, Long> constrainableExercisedGrantedActions) {
        this.constrainableExercisedGrantedActions = constrainableExercisedGrantedActions;
    }

    public NavigableSet<String> getUnusedUngrantedPrivs() {
        return unusedUngrantedPrivs;
    }

    public void setUnusedUngrantedPrivs(NavigableSet<String> unusedUngrantedPrivs) {
        this.unusedUngrantedPrivs = unusedUngrantedPrivs;
    }
}
