package edu.mines.model;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Matt on 10/16/2017.
 */
@Embedded
public class ResourceMetric {
    private Long TP = 0L;
    private Long FP = 0L;
    private Long FN = 0L;
    private Long TN = 0L;

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
}
