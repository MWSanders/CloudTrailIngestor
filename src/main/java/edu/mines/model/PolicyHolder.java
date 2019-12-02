package edu.mines.model;

import com.amazonaws.auth.policy.Action;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Matt on 9/7/2015.
 */
@Entity(noClassnameStored = true)
public class PolicyHolder implements Cloneable {
    public enum TYPE {IAMUser, AssumedRole}

    @Id
    @JsonProperty("_id")
    private String userName;
    private String policyName;
    private String policyCreateCommend;
    private TYPE type;
    private String policyFileName;
    private Policy policy;

    private Map<String, Set<String>> normalizedActions = new TreeMap<>();   //<Service, <Action, <Resource>>>

    public PolicyHolder(String userName, String policyName, String policyCreateCommend) {
        this.userName = userName;
        this.policyName = policyName;
        this.policyCreateCommend = policyCreateCommend;
    }

    public PolicyHolder() {
    }

    public PolicyHolder(String userName, String policyName) {
        this(userName, policyName, null);
    }

    public PolicyHolder(String userName) {
        this(userName, null, null);
    }

    public void normalizePolicies() {
        for (Statement statement : policy.getStatements()) {
            for (Action action : statement.getActions()) {
                Set<String> actionSet = null;
                if (normalizedActions.containsKey(action.getActionName()))
                    actionSet = normalizedActions.get(action.getActionName());
                else {
                    actionSet = new HashSet<>();
                    normalizedActions.put(action.getActionName(), actionSet);
                }
                for (Resource resource : statement.getResources())
                    actionSet.add(resource.getId());
            }
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyCreateCommend() {
        return policyCreateCommend;
    }

    public void setPolicyCreateCommend(String policyCreateCommend) {
        this.policyCreateCommend = policyCreateCommend;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getPolicyFileName() {
        return policyFileName;
    }

    public void setPolicyFileName(String policyFileName) {
        this.policyFileName = policyFileName;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
        for (Statement statement : policy.getStatements())
            for (int i = 0; i < statement.getActions().size(); i++) {
                Action action = statement.getActions().get(i);
                if (!(action instanceof GenericAction)) {
                    statement.getActions().set(i, new GenericAction(action.getActionName()));
                }
            }
    }

    public PolicyHolder clone() {
        try {
            return (PolicyHolder) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Map<String, Set<String>> getNormalizedActions() {
        return normalizedActions;
    }

    public void setNormalizedActions(Map<String, Set<String>> normalizedActions) {
        this.normalizedActions = normalizedActions;
    }
}
