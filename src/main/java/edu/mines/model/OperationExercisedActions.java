package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Matt on 4/22/2016.
 */
@Entity(noClassnameStored = true)
public class OperationExercisedActions {

    @Id
    @JsonProperty("_id")
    private String name;

    private String startTime;
    private String endTime;
    @Indexed
    private long days;
    private DayOfWeek dayOfWeek;
    private boolean isWeekday;

    @Embedded("entitySummary")
    private Map<String, EntitySummary> entities = new TreeMap<>();

    private Set<String> entitySummaryRefs = new TreeSet<>();

    public static String getKey(String startTime, String endTime) {
        return "From " + startTime + " to " + endTime;
    }

    public static ZonedDateTime getStartFromKey(String id) {
        String startTime = id.substring(5, 22);
        return ZonedDateTime.parse(startTime);
    }

    public static ZonedDateTime getEndFromKey(String id) {
        String endTime = id.substring(26, 43);
        return ZonedDateTime.parse(endTime);
    }

    @JsonProperty("_id")
    public String getName() {
        return name;
    }

    @JsonProperty("_id")
    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getEntitySummaryRefs() {
        return entitySummaryRefs;
    }

    public void setEntitySummaryRefs(Set<String> entitySummaryRefs) {
        this.entitySummaryRefs = entitySummaryRefs;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Map<String, EntitySummary> getEntities() {
        return entities;
    }

    public void setEntities(Map<String, EntitySummary> entities) {
        this.entities = entities;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isWeekday() {
        return isWeekday;
    }

    public void setWeekday(boolean weekday) {
        isWeekday = weekday;
    }

    public static boolean isWeekday(ZonedDateTime zonedDateTime) {
        return !isWeekend(zonedDateTime);
    }

    public static boolean isWeekend(ZonedDateTime zonedDateTime) {
        if ((zonedDateTime.getDayOfWeek() == DayOfWeek.SATURDAY) || (zonedDateTime.getDayOfWeek() == DayOfWeek.SUNDAY))
            return true;
        else
            return false;
    }
}
