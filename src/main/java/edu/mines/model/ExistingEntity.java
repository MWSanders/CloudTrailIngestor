package edu.mines.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matt on 10/8/2015.
 */
public class ExistingEntity {

    private List<String> rawPolicies = new ArrayList<>();
    private List<String> normalizedPolicies = new ArrayList<>();
    private String assumeRolePolicyDocument;
    private String type;

    @Id
    @JsonProperty("_id")
    private String name;

    public String getAssumeRolePolicyDocument() {
        return assumeRolePolicyDocument;
    }

    public void setAssumeRolePolicyDocument(String assumeRolePolicyDocument) {
        this.assumeRolePolicyDocument = assumeRolePolicyDocument;
    }

    public List<String> getRawPolicies() {
        return rawPolicies;
    }

    public void setRawPolicies(List<String> rawPolicies) {
        this.rawPolicies = rawPolicies;
    }

    public List<String> getNormalizedPolicies() {
        return normalizedPolicies;
    }

    public void setNormalizedPolicies(List<String> normalizedPolicies) {
        this.normalizedPolicies = normalizedPolicies;
    }

    @JsonProperty("_id")
    public String getName() {
        return name;
    }

    @JsonProperty("_id")
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
