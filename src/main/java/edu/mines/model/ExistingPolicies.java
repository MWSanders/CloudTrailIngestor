package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Id;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matt on 7/20/2017.
 */
public class ExistingPolicies {
    private String date;
    private String environment;
    @Id
    @JsonProperty("_id")
    private String name;

    private Map<String, ExistingUser> users = new HashMap<>();
    private Map<String, ExistingRole> roles = new HashMap<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ExistingUser> getUsers() {
        return users;
    }

    public void setUsers(Map<String, ExistingUser> users) {
        this.users = users;
    }

    public Map<String, ExistingRole> getRoles() {
        return roles;
    }

    public void setRoles(Map<String, ExistingRole> roles) {
        this.roles = roles;
    }
}
