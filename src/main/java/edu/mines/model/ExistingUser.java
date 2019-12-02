package edu.mines.model;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Matt on 10/8/15.
 */
public class ExistingUser extends ExistingEntity {
    private Set<String> attachedUserPolicies = new TreeSet<>();
    private Set<String> userPolicies = new TreeSet<>();
    private Set<String> groups = new TreeSet<>();

    public Set<String> getAttachedUserPolicies() {
        return attachedUserPolicies;
    }

    public void setAttachedUserPolicies(Set<String> attachedUserPolicies) {
        this.attachedUserPolicies = attachedUserPolicies;
    }

    public Set<String> getUserPolicies() {
        return userPolicies;
    }

    public void setUserPolicies(Set<String> userPolicies) {
        this.userPolicies = userPolicies;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

}
