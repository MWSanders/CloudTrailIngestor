package edu.mines.model;

import java.util.stream.IntStream;

/**
 * Created by Matt on 5/9/2016.
 */
public class AlgorithmTrial {

    private long uniqueExercisedGrantedPrivs;
    private long uniqueExercisedDeniedPrivs;
    private long uniqueUnusedGrantedPrivs;
    private long uniqueUnusedUngrantedPrivs;

    private long totalExercisedGrantedActions;
    private long totalExercisedDeniedActions;
    private long totalExercisedActions; // totalExercisedGrantedActions + totalExercisedDeniedActions

    private long totalConstrainableExercisedGrantedActions;
    private long totalConstrainableExercisedDeniedActions;
    private long totalConstrainableExercisedActions; // totalExercisedGrantedActions + totalExercisedDeniedActions

    private long uniqueGrantedPrivs; // exercisedGranted + uniqueUnusedGrantedPrivs
    private long uniqueExercisedPrivs; // exercisedGranted + uniqueExercisedDeniedPrivs

    private double uniquePrecision = 1;
    private double uniqueRecall = 1;
    private double absRecall = 1;
    private double uniqueF1;
    private double absF1;

    //public static final double[] fScores = new double[]{0.01, 0.1, 0.2, 0.5, 1, 2, 5, 10, 20, 30, 40, 50, 60, 75, 100};
    public static final String[] fNames = {"1/100", "1/90", "1/80", "1/75", "1/70", "1/60", "1/50", "1/40", "1/30", "1/20", "1/10", "1/5", "1/2",
            "1", "2", "5", "10", "20", "30", "40", "50", "60", "70", "75", "80", "90", "100"};
    public static final String[] fLongNames = {"f0_01", "f0_0111111111111", "f0_0125", "f0_0133333333333", "f0_0142857142857", "f0_0166666666667", "f0_02",
            "f0_025", "f0_0333333333333", "f0_05", "f0_1", "f0_2", "f0_5", "f1", "f2", "f5", "f10", "f20", "f30", "f40", "f50", "f60", "f70", "f75", "f80", "f90", "f100"};
    public static final double[] fScores = new double[]{0.01, (1 / 90f), (1 / 80f), (1 / 75f), (1 / 70f), (1 / 60f), (1 / 50f), (1 / 40f), (1 / 30f), (1 / 20f),
            (1 / 10f), (1 / 5f), (1 / 2f), 1, 2, 5, 10, 20, 30, 40, 50, 60, 70, 75, 80, 90, 100};
    private double[] uniqueFScoreValues = new double[fScores.length];
    private double[] absFScoreValues = new double[fScores.length];

    public void addAlgorithmMetric(AlgorithmMetric algorithmMetric) {
        uniqueExercisedGrantedPrivs += algorithmMetric.getExercisedGrantedActions().size();
        uniqueExercisedDeniedPrivs += algorithmMetric.getExercisedDeniedActions().size();
        uniqueUnusedGrantedPrivs += algorithmMetric.getUnusedGrantedPrivs().size();
        uniqueUnusedUngrantedPrivs += algorithmMetric.getUnusedUngrantedPrivs().size();
        uniqueGrantedPrivs += (algorithmMetric.getExercisedGrantedActions().size() + algorithmMetric.getUnusedGrantedPrivs().size());
        uniqueExercisedPrivs += (algorithmMetric.getExercisedGrantedActions().size() + algorithmMetric.getExercisedDeniedActions().size());

        for (Long i : algorithmMetric.getExercisedGrantedActions().values()) {
            this.totalExercisedGrantedActions += i;
            this.totalExercisedActions += i;
        }
        for (Long i : algorithmMetric.getExercisedDeniedActions().values()) {
            this.totalExercisedDeniedActions += i;
            this.totalExercisedActions += i;
        }
        for (Long i : algorithmMetric.getConstrainableExercisedGrantedActions().values()) {
            this.totalConstrainableExercisedGrantedActions += i;
            this.totalConstrainableExercisedActions += i;
        }
        for (Long i : algorithmMetric.getConstrainableExercisedDeniedActions().values()) {
            this.totalConstrainableExercisedDeniedActions += i;
            this.totalConstrainableExercisedActions += i;
        }

        uniquePrecision = safeDivideScoring(uniqueExercisedGrantedPrivs, uniqueGrantedPrivs);
        uniqueRecall = safeDivideScoring(uniqueExercisedGrantedPrivs, uniqueExercisedPrivs);
        absRecall = safeDivideScoring(totalExercisedGrantedActions, totalExercisedActions);
        uniqueF1 = safeDivide((2 * uniquePrecision * uniqueRecall), (uniquePrecision + uniqueRecall));
        absF1 = safeDivide((2 * uniquePrecision * absRecall), (uniquePrecision + absRecall));

        IntStream.range(0, fScores.length).forEachOrdered(i -> {
            double beta = fScores[i];
            double betaSquared = Math.pow(beta, 2);
            double fBeta = (1 + betaSquared) * safeDivide((uniquePrecision * uniqueRecall), ((betaSquared * uniquePrecision) + uniqueRecall));
            double absfBeta = (1 + betaSquared) * safeDivide((uniquePrecision * absRecall), ((betaSquared * uniquePrecision) + absRecall));
            uniqueFScoreValues[i] = fBeta;
            absFScoreValues[i] = absfBeta;
        });
    }

    public static double safeDivideScoring(double x, double y) {
        if (y == 0 && x == 0)
            return 1;
        else
            return safeDivide(x, y);
    }

    public static double safeDivide(double x, double y) {
        if (y == 0)
            return 0;
        else
            return x / y;
    }

    public long getUniqueGrantedPrivs() {
        return uniqueGrantedPrivs;
    }

    public void setUniqueGrantedPrivs(long uniqueGrantedPrivs) {
        this.uniqueGrantedPrivs = uniqueGrantedPrivs;
    }

    public long getTotalExercisedActions() {
        return totalExercisedActions;
    }

    public void setTotalExercisedActions(long totalExercisedActions) {
        this.totalExercisedActions = totalExercisedActions;
    }

    public long getUniqueExercisedPrivs() {
        return uniqueExercisedPrivs;
    }

    public void setUniqueExercisedPrivs(long uniqueExercisedPrivs) {
        this.uniqueExercisedPrivs = uniqueExercisedPrivs;
    }

    public long getUniqueExercisedGrantedPrivs() {
        return uniqueExercisedGrantedPrivs;
    }

    public void setUniqueExercisedGrantedPrivs(long uniqueExercisedGrantedPrivs) {
        this.uniqueExercisedGrantedPrivs = uniqueExercisedGrantedPrivs;
    }

    public long getUniqueExercisedDeniedPrivs() {
        return uniqueExercisedDeniedPrivs;
    }

    public void setUniqueExercisedDeniedPrivs(long uniqueExercisedDeniedPrivs) {
        this.uniqueExercisedDeniedPrivs = uniqueExercisedDeniedPrivs;
    }

    public long getUniqueUnusedGrantedPrivs() {
        return uniqueUnusedGrantedPrivs;
    }

    public void setUniqueUnusedGrantedPrivs(long uniqueUnusedGrantedPrivs) {
        this.uniqueUnusedGrantedPrivs = uniqueUnusedGrantedPrivs;
    }

    public long getTotalExercisedGrantedActions() {
        return totalExercisedGrantedActions;
    }

    public void setTotalExercisedGrantedActions(long totalExercisedGrantedActions) {
        this.totalExercisedGrantedActions = totalExercisedGrantedActions;
    }

    public long getTotalExercisedDeniedActions() {
        return totalExercisedDeniedActions;
    }

    public void setTotalExercisedDeniedActions(long totalExercisedDeniedActions) {
        this.totalExercisedDeniedActions = totalExercisedDeniedActions;
    }

    public long getTotalConstrainableExercisedGrantedActions() {
        return totalConstrainableExercisedGrantedActions;
    }

    public void setTotalConstrainableExercisedGrantedActions(long totalConstrainableExercisedGrantedActions) {
        this.totalConstrainableExercisedGrantedActions = totalConstrainableExercisedGrantedActions;
    }

    public long getTotalConstrainableExercisedDeniedActions() {
        return totalConstrainableExercisedDeniedActions;
    }

    public void setTotalConstrainableExercisedDeniedActions(long totalConstrainableExercisedDeniedActions) {
        this.totalConstrainableExercisedDeniedActions = totalConstrainableExercisedDeniedActions;
    }

    public long getTotalConstrainableExercisedActions() {
        return totalConstrainableExercisedActions;
    }

    public void setTotalConstrainableExercisedActions(long totalConstrainableExercisedActions) {
        this.totalConstrainableExercisedActions = totalConstrainableExercisedActions;
    }

    public double getUniquePrecision() {
        return uniquePrecision;
    }

    public void setUniquePrecision(double uniquePrecision) {
        this.uniquePrecision = uniquePrecision;
    }

    public double getUniqueRecall() {
        return uniqueRecall;
    }

    public void setUniqueRecall(double uniqueRecall) {
        this.uniqueRecall = uniqueRecall;
    }

    public double getAbsRecall() {
        return absRecall;
    }

    public void setAbsRecall(double absRecall) {
        this.absRecall = absRecall;
    }

    public double getUniqueF1() {
        return uniqueF1;
    }

    public void setUniqueF1(double uniqueF1) {
        this.uniqueF1 = uniqueF1;
    }

    public double getAbsF1() {
        return absF1;
    }

    public void setAbsF1(double absF1) {
        this.absF1 = absF1;
    }

    public long getUniqueUnusedUngrantedPrivs() {
        return uniqueUnusedUngrantedPrivs;
    }

    public void setUniqueUnusedUngrantedPrivs(long uniqueUnusedUngrantedPrivs) {
        this.uniqueUnusedUngrantedPrivs = uniqueUnusedUngrantedPrivs;
    }

    public double[] getUniqueFScoreValues() {
        return uniqueFScoreValues;
    }

    public double[] getAbsFScoreValues() {
        return absFScoreValues;
    }
}
