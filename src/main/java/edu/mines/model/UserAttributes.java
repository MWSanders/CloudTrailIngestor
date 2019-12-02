package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 11/25/2017.
 */
@Entity
public class UserAttributes {

    @JsonProperty("_id")
    @Id
    private String Id;
    private List<UserAttribute> users = new ArrayList<>();

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public List<UserAttribute> getUsers() {
        return users;
    }

    public void setUsers(List<UserAttribute> users) {
        this.users = users;
    }

}
