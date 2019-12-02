package edu.mines.model;

import java.util.ArrayList;

/**
 * Created by Matt on 7/2/2016.
 */
public class SlidingWindowTrial {
    ArrayList<SlidingWindowPeriod> slidingWindowPeriods = new ArrayList<>();
    Integer observationPeriod;
    Integer operationPeriod;
    Integer windowIncrement;

    public SlidingWindowTrial(ArrayList<SlidingWindowPeriod> slidingWindowPeriods, Integer observationPeriod, Integer operationPeriod, Integer windowIncrement) {
        this.slidingWindowPeriods = slidingWindowPeriods;
        this.observationPeriod = observationPeriod;
        this.operationPeriod = operationPeriod;
        this.windowIncrement = windowIncrement;
    }

    public ArrayList<SlidingWindowPeriod> getSlidingWindowPeriods() {
        return slidingWindowPeriods;
    }

    public void setSlidingWindowPeriods(ArrayList<SlidingWindowPeriod> slidingWindowPeriods) {
        this.slidingWindowPeriods = slidingWindowPeriods;
    }

    public Integer getObservationPeriod() {
        return observationPeriod;
    }

    public void setObservationPeriod(Integer observationPeriod) {
        this.observationPeriod = observationPeriod;
    }

    public Integer getOperationPeriod() {
        return operationPeriod;
    }

    public void setOperationPeriod(Integer operationPeriod) {
        this.operationPeriod = operationPeriod;
    }

    public Integer getWindowIncrement() {
        return windowIncrement;
    }

    public void setWindowIncrement(Integer windowIncrement) {
        this.windowIncrement = windowIncrement;
    }

    public String getId() {
        return this.slidingWindowPeriods.get(0).getObsStart() + "-" + this.slidingWindowPeriods.get(this.slidingWindowPeriods.size() - 1).getOprEnd();
    }
}
