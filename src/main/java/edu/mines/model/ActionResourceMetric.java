package edu.mines.model;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Matt on 10/16/2017.
 */
@Embedded
public class ActionResourceMetric {
    private String id;
    private Long TP = 0L;
    private Long FP = 0L;
    private Long FN = 0L;
    private Long TN = 0L;
    private ResourceMetric resourceMetric = new ResourceMetric();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTP() {
        return TP;
    }

    public void setTP(Long TP) {
        this.TP = TP;
    }

    public Long getFP() {
        return FP;
    }

    public void setFP(Long FP) {
        this.FP = FP;
    }

    public Long getFN() {
        return FN;
    }

    public void setFN(Long FN) {
        this.FN = FN;
    }

    public Long getTN() {
        return TN;
    }

    public void setTN(Long TN) {
        this.TN = TN;
    }

    public ResourceMetric getResourceMetric() {
        return resourceMetric;
    }

    public void setResourceMetric(ResourceMetric resourceMetric) {
        this.resourceMetric = resourceMetric;
    }
}
