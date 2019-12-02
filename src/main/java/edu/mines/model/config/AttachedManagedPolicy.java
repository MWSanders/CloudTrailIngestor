package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "policyName",
        "policyArn"
})
public class AttachedManagedPolicy {

    @JsonProperty("policyName")
    private String policyName;
    @JsonProperty("policyArn")
    private String policyArn;

    @JsonProperty("policyName")
    public String getPolicyName() {
        return policyName;
    }

    @JsonProperty("policyName")
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    @JsonProperty("policyArn")
    public String getPolicyArn() {
        return policyArn;
    }

    @JsonProperty("policyArn")
    public void setPolicyArn(String policyArn) {
        this.policyArn = policyArn;
    }
}
