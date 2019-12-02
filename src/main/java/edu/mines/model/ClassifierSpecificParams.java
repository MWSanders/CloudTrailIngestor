package edu.mines.model;

/**
 * Created by Matt on 8/9/2016.
 */
public class ClassifierSpecificParams {
    private Integer min_samples_split;
    private String class_weight;
    private String criterion;
    private Integer min_samples_leaf;

    public Integer getMin_samples_split() {
        return min_samples_split;
    }

    public void setMin_samples_split(Integer min_samples_split) {
        this.min_samples_split = min_samples_split;
    }

    public String getClass_weight() {
        return class_weight;
    }

    public void setClass_weight(String class_weight) {
        this.class_weight = class_weight;
    }

    public String getCriterion() {
        return criterion;
    }

    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public Integer getMin_samples_leaf() {
        return min_samples_leaf;
    }

    public void setMin_samples_leaf(Integer min_samples_leaf) {
        this.min_samples_leaf = min_samples_leaf;
    }
}
