package edu.mines.model;

import java.time.ZonedDateTime;

/**
 * Created by Matt on 7/2/2016.
 */
public class SlidingWindowPeriod {
    ZonedDateTime obsStart;
    ZonedDateTime obsEndOprStart;
    ZonedDateTime oprEnd;

    public SlidingWindowPeriod(ZonedDateTime obsStart, ZonedDateTime obsEndOprStart, ZonedDateTime oprEnd) {
        this.obsStart = obsStart;
        this.obsEndOprStart = obsEndOprStart;
        this.oprEnd = oprEnd;
    }

    public static ZonedDateTime getStartFromKey(String id) {
        String startTime = id.substring(5, 22);
        return ZonedDateTime.parse(startTime);
    }

    public static ZonedDateTime getEndFromKey(String id) {
        String endTime = id.substring(26, 43);
        return ZonedDateTime.parse(endTime);
    }

    public ZonedDateTime getObsStart() {
        return obsStart;
    }

    public void setObsStart(ZonedDateTime obsStart) {
        this.obsStart = obsStart;
    }

    public ZonedDateTime getObsEndOprStart() {
        return obsEndOprStart;
    }

    public void setObsEndOprStart(ZonedDateTime obsEndOprStart) {
        this.obsEndOprStart = obsEndOprStart;
    }

    public ZonedDateTime getOprEnd() {
        return oprEnd;
    }

    public void setOprEnd(ZonedDateTime oprEnd) {
        this.oprEnd = oprEnd;
    }

    public String toString() {
        return "ObsStart: " + obsStart + " obsEndOprStart: " + obsEndOprStart + " oprEnd: " + oprEnd;
    }
}
