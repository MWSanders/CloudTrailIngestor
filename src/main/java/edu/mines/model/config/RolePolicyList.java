package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "policyName",
        "policyDocument"
})
public class RolePolicyList {

    @JsonProperty("policyName")
    private String policyName;
    @JsonProperty("policyDocument")
    private String policyDocument;

    @JsonProperty("policyName")
    public String getPolicyName() {
        return policyName;
    }

    @JsonProperty("policyName")
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    @JsonProperty("policyDocument")
    public String getPolicyDocument() {
        return policyDocument;
    }

    @JsonProperty("policyDocument")
    public void setPolicyDocument(String policyDocument) {
        this.policyDocument = policyDocument;
    }

}