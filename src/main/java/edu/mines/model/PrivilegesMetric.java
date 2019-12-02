package edu.mines.model;

import com.amazonaws.auth.policy.Statement;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.*;

/**
 * Created by Matt on 10/8/2015.
 */
@Entity(noClassnameStored = true)
public class PrivilegesMetric {
    @Id
    @JsonProperty("_id")
    private String name;
    private NavigableSet<String> originalGrantedPrivs = new TreeSet<>();
    private NavigableSet<String> originalGrantedAuditPrivs = new TreeSet<>();
    private NavigableSet<String> originalGrantedInauditPrivs = new TreeSet<>();
    private NavigableSet<String> usedPrivs = new TreeSet<>();
    private NavigableSet<String> unusedPrivs = new TreeSet<>();
    //Removing unauditable privs because they are filtered out by the PolicyNormalizer, may put them back in one day
    //    private NavigableSet<String> unauditablePrivs = new TreeSet<>();
    private NavigableSet<String> newGrantedPrivs = new TreeSet<>();
    private NavigableSet<String> eleminatedPrivs = new TreeSet<>();

    private NavigableMap<String, Set<String>> resourceOriginalGrantedPrivs = new TreeMap<>();
    private NavigableMap<String, Set<String>> resourceUsedPrivs = new TreeMap<>();
    private NavigableMap<String, Set<String>> resourceUnusedPrivs = new TreeMap<>();
    private NavigableMap<String, Set<String>> resourceNewGrantedPrivs = new TreeMap<>();
    private NavigableMap<String, Set<String>> resourceEliminatedPrivs = new TreeMap<>();

    private Set<Statement> statements = new HashSet<>();

    private NavigableSet<String> originalGrantedServices = new TreeSet<>();
    private NavigableSet<String> originalGrantedAuditServices = new TreeSet<>();
    private NavigableSet<String> originalGrantedInauditServices = new TreeSet<>();
    private NavigableSet<String> usedServices = new TreeSet<>();
    private NavigableSet<String> unusedServices = new TreeSet<>();
    private NavigableSet<String> newGrantedServices = new TreeSet<>();

    private String type;
    private boolean resourceConstrainedMetrics = false;

    @JsonProperty("_id")
    public String getName() {
        return name;
    }

    @JsonProperty("_id")
    public void setName(String name) {
        this.name = name;
    }

    public NavigableSet<String> getOriginalGrantedPrivs() {
        return originalGrantedPrivs;
    }

    public void setOriginalGrantedPrivs(NavigableSet<String> originalGrantedPrivs) {
        this.originalGrantedPrivs = originalGrantedPrivs;
    }

    public NavigableSet<String> getOriginalGrantedAuditPrivs() {
        return originalGrantedAuditPrivs;
    }

    public void setOriginalGrantedAuditPrivs(NavigableSet<String> originalGrantedAuditPrivs) {
        this.originalGrantedAuditPrivs = originalGrantedAuditPrivs;
    }

    public NavigableSet<String> getOriginalGrantedInauditPrivs() {
        return originalGrantedInauditPrivs;
    }

    public void setOriginalGrantedInauditPrivs(NavigableSet<String> originalGrantedInauditPrivs) {
        this.originalGrantedInauditPrivs = originalGrantedInauditPrivs;
    }

    public NavigableSet<String> getUsedPrivs() {
        return usedPrivs;
    }

    public void setUsedPrivs(NavigableSet<String> usedPrivs) {
        this.usedPrivs = usedPrivs;
    }

    public NavigableSet<String> getUnusedPrivs() {
        return unusedPrivs;
    }

    public void setUnusedPrivs(NavigableSet<String> unusedPrivs) {
        this.unusedPrivs = unusedPrivs;
    }

    public NavigableSet<String> getNewGrantedPrivs() {
        return newGrantedPrivs;
    }

    public void setNewGrantedPrivs(NavigableSet<String> newGrantedPrivs) {
        this.newGrantedPrivs = newGrantedPrivs;
    }

    public NavigableSet<String> getEleminatedPrivs() {
        return eleminatedPrivs;
    }

    public void setEleminatedPrivs(NavigableSet<String> eleminatedPrivs) {
        this.eleminatedPrivs = eleminatedPrivs;
    }

    public NavigableMap<String, Set<String>> getResourceOriginalGrantedPrivs() {
        return resourceOriginalGrantedPrivs;
    }

    public void setResourceOriginalGrantedPrivs(NavigableMap<String, Set<String>> resourceOriginalGrantedPrivs) {
        this.resourceOriginalGrantedPrivs = resourceOriginalGrantedPrivs;
    }

    public NavigableMap<String, Set<String>> getResourceUsedPrivs() {
        return resourceUsedPrivs;
    }

    public void setResourceUsedPrivs(NavigableMap<String, Set<String>> resourceUsedPrivs) {
        this.resourceUsedPrivs = resourceUsedPrivs;
    }

    public NavigableMap<String, Set<String>> getResourceUnusedPrivs() {
        return resourceUnusedPrivs;
    }

    public void setResourceUnusedPrivs(NavigableMap<String, Set<String>> resourceUnusedPrivs) {
        this.resourceUnusedPrivs = resourceUnusedPrivs;
    }

    public NavigableMap<String, Set<String>> getResourceNewGrantedPrivs() {
        return resourceNewGrantedPrivs;
    }

    public void setResourceNewGrantedPrivs(NavigableMap<String, Set<String>> resourceNewGrantedPrivs) {
        this.resourceNewGrantedPrivs = resourceNewGrantedPrivs;
    }

    public NavigableMap<String, Set<String>> getResourceEliminatedPrivs() {
        return resourceEliminatedPrivs;
    }

    public void setResourceEliminatedPrivs(NavigableMap<String, Set<String>> resourceEliminatedPrivs) {
        this.resourceEliminatedPrivs = resourceEliminatedPrivs;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isResourceConstrainedMetrics() {
        return resourceConstrainedMetrics;
    }

    public void setResourceConstrainedMetrics(boolean resourceConstrainedMetrics) {
        this.resourceConstrainedMetrics = resourceConstrainedMetrics;
    }

    public NavigableSet<String> getUsedServices() {
        return usedServices;
    }

    public void setUsedServices(NavigableSet<String> usedServices) {
        this.usedServices = usedServices;
    }

    public NavigableSet<String> getUnusedServices() {
        return unusedServices;
    }

    public void setUnusedServices(NavigableSet<String> unusedServices) {
        this.unusedServices = unusedServices;
    }

    public NavigableSet<String> getOriginalGrantedServices() {
        return originalGrantedServices;
    }

    public void setOriginalGrantedServices(NavigableSet<String> originalGrantedServices) {
        this.originalGrantedServices = originalGrantedServices;
    }

    public NavigableSet<String> getNewGrantedServices() {
        return newGrantedServices;
    }

    public void setNewGrantedServices(NavigableSet<String> newGrantedServices) {
        this.newGrantedServices = newGrantedServices;
    }

    public NavigableSet<String> getOriginalGrantedAuditServices() {
        return originalGrantedAuditServices;
    }

    public void setOriginalGrantedAuditServices(NavigableSet<String> originalGrantedAuditServices) {
        this.originalGrantedAuditServices = originalGrantedAuditServices;
    }

    public NavigableSet<String> getOriginalGrantedInauditServices() {
        return originalGrantedInauditServices;
    }

    public void setOriginalGrantedInauditServices(NavigableSet<String> originalGrantedInauditServices) {
        this.originalGrantedInauditServices = originalGrantedInauditServices;
    }

    public Set<Statement> getStatements() {
        return statements;
    }

    public void setStatements(Set<Statement> statements) {
        this.statements = statements;
    }
}
