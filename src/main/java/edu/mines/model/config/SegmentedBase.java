package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matt on 9/5/2017.
 */
public abstract class SegmentedBase {
    @JsonProperty("_id")
    @Id
    protected String id;
    protected String startTime;
    protected String endTime;
    protected String environment;
    @Indexed
    protected String oprKey;
    protected Set<String> policies = new HashSet<>();
    protected Set<String> statements = new HashSet<>();

    public static String generateId(String oprKey, String env, String arn) {
        return oprKey + "|" + env + "|" + arn;
    }

    public static String generateOprKey(String startTime, String endTime) {
        return "From " + startTime + " to " + endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getOprKey() {
        return oprKey;
    }

    public void setOprKey(String oprKey) {
        this.oprKey = oprKey;
    }

    public Set<String> getPolicies() {
        return policies;
    }

    public void setPolicies(Set<String> policies) {
        this.policies = policies;
    }

    public Set<String> getStatements() {
        return statements;
    }

    public void setStatements(Set<String> statements) {
        this.statements = statements;
    }
}
