package edu.mines.model;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.*;

/**
 * Created by Matt on 8/5/2017.
 */
@Entity(noClassnameStored = true)
public class MergedService {

    @Id
    private String cloudTrailName;
    private String iamName;
    private String arnFormat;
    private String displayName;

    private Service.AuditActionLevel auditActionLevel;
    private Service.ResourceControlLevel resourceLevelControlled = Service.ResourceControlLevel.NONE;

    private List<String> auditedActions;
    private Map<String, Action> resourceControllableActions = new TreeMap<>();
    private Set<String> unconstrainableActions = new HashSet<>();  //used when resourceLeveLControlled = ALL
    private Map<String, String[]> monitoredResources = new TreeMap<>();

    public MergedService(Service logService, ServiceMap iamService) {
        if (iamService == null) {
            System.out.println("Invalid iamService passed in");
            System.exit(1);
        }
        if (logService != null) {
            this.cloudTrailName = logService.getCloudTrailName();
            this.auditActionLevel = logService.getAuditActionLevel();
        }
        this.iamName = iamService.getStringPrefix();
        this.arnFormat = iamService.getARNFormat();
        this.displayName = iamService.getId();

        this.resourceLevelControlled = (logService.getResourceLevelControlled() == null) ? Service.ResourceControlLevel.NONE : logService.getResourceLevelControlled();

        if (this.auditActionLevel.equals(Service.AuditActionLevel.ALL))
            auditedActions = iamService.getActions();
        else if (this.auditActionLevel.equals(Service.AuditActionLevel.SOME))
            auditedActions = logService.getAuditedActions();

        if (this.resourceLevelControlled.equals(Service.ResourceControlLevel.SOME)) {
            resourceControllableActions = logService.getActions();
            unconstrainableActions = logService.getUnconstrainableActions();
        }
        this.monitoredResources = logService.getMonitoredResources();
        //TODO need to refactor ResourceControlLevel.ALL in db to be more like SOME?
    }

    public String getCloudTrailName() {
        return cloudTrailName;
    }

    public void setCloudTrailName(String cloudTrailName) {
        this.cloudTrailName = cloudTrailName;
    }

    public String getIamName() {
        return iamName;
    }

    public void setIamName(String iamName) {
        this.iamName = iamName;
    }

    public String getArnFormat() {
        return arnFormat;
    }

    public void setArnFormat(String arnFormat) {
        this.arnFormat = arnFormat;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Service.AuditActionLevel getAuditActionLevel() {
        return auditActionLevel;
    }

    public void setAuditActionLevel(Service.AuditActionLevel auditActionLevel) {
        this.auditActionLevel = auditActionLevel;
    }

    public Service.ResourceControlLevel getResourceLevelControlled() {
        return resourceLevelControlled;
    }

    public void setResourceLevelControlled(Service.ResourceControlLevel resourceLevelControlled) {
        this.resourceLevelControlled = resourceLevelControlled;
    }

    public List<String> getAuditedActions() {
        return auditedActions;
    }

    public void setAuditedActions(List<String> auditedActions) {
        this.auditedActions = auditedActions;
    }

    public Map<String, Action> getResourceControllableActions() {
        return resourceControllableActions;
    }

    public void setResourceControllableActions(HashMap<String, Action> resourceControllableActions) {
        this.resourceControllableActions = resourceControllableActions;
    }

    public Set<String> getUnconstrainableActions() {
        return unconstrainableActions;
    }

    public void setUnconstrainableActions(Set<String> unconstrainableActions) {
        this.unconstrainableActions = unconstrainableActions;
    }

    public Map<String, String[]> getMonitoredResources() {
        return monitoredResources;
    }

    public void setMonitoredResources(Map<String, String[]> monitoredResources) {
        this.monitoredResources = monitoredResources;
    }
}
