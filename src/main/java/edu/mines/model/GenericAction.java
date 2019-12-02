package edu.mines.model;

import com.amazonaws.auth.policy.Action;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * Created by Matt on 8/27/2015.
 */
@Entity(noClassnameStored = true)
public class GenericAction implements Action {

    @Id
    @JsonProperty("actionServiceName")
    String actionServiceName;

    public GenericAction() {
    }

    public GenericAction(String serviceAction) {
        this.actionServiceName = serviceAction;
    }

    public GenericAction(String service, String action) {
        this.actionServiceName = service + ":" + action;
    }

    @Override
    public String getActionName() {
        return actionServiceName;
    }

    public void setActionServiceName(String actionServiceName) {
        this.actionServiceName = actionServiceName;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof GenericAction &&
                this.getActionName().equals(((GenericAction) obj).getActionName()));
    }

    public int hashCode() {
        return this.actionServiceName.hashCode();
    }

    public String getSplitActionName() {
        return actionServiceName.split(":")[1];
    }

    public String getSplitServiceName() {
        return actionServiceName.split(":")[0];
    }

    public static String getCombinedAction(String service, String action) {
        return service + ":" + action;
    }
}
