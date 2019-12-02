package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.NavigableSet;
import java.util.TreeSet;

/**
 * Created by Matt on 4/25/2016.
 */
@Entity(noClassnameStored = true)
public class EvaluatorMetric implements Comparable<EvaluatorMetric> {
    @Id
    @JsonProperty("_id")
    private String name;
    private AlgorithmMetric algorithmMetric = new AlgorithmMetric();
    //private AlgorithmMetric resourceLevelMetric = new AlgorithmMetric();

    // Information derived from policies
    private NavigableSet<String> constrainedPrivs = new TreeSet<>();
    private NavigableSet<String> unconstrainedPrivs = new TreeSet<>();
    private NavigableSet<String> constrainablePrivs = new TreeSet<>();

    public AlgorithmMetric getAlgorithmMetric() {
        return algorithmMetric;
    }

    public void setAlgorithmMetric(AlgorithmMetric algorithmMetric) {
        this.algorithmMetric = algorithmMetric;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NavigableSet<String> getConstrainedPrivs() {
        return constrainedPrivs;
    }

    public void setConstrainedPrivs(NavigableSet<String> constrainedPrivs) {
        this.constrainedPrivs = constrainedPrivs;
    }

    public NavigableSet<String> getConstrainablePrivs() {
        return constrainablePrivs;
    }

    public void setConstrainablePrivs(NavigableSet<String> constrainablePrivs) {
        this.constrainablePrivs = constrainablePrivs;
    }

    public NavigableSet<String> getUnconstrainedPrivs() {
        return unconstrainedPrivs;
    }

    public void setUnconstrainedPrivs(NavigableSet<String> unconstrainedPrivs) {
        this.unconstrainedPrivs = unconstrainedPrivs;
    }

    @Override
    public int compareTo(EvaluatorMetric o) {
        return this.getAlgorithmMetric().getUniquePrecision().compareTo(o.getAlgorithmMetric().getUniquePrecision());
    }
}
