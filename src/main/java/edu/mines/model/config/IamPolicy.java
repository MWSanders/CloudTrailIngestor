package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_id",
        "relatedEvents",
        "relationships",
        "configuration",
        "supplementaryConfiguration",
        "tags",
        "configurationItemVersion",
        "configurationItemCaptureTime",
        "configurationStateId",
        "awsAccountId",
        "configurationItemStatus",
        "resourceType",
        "resourceId",
        "resourceName",
        "ARN",
        "awsRegion",
        "availabilityZone",
        "configurationStateMd5Hash",
        "resourceCreationTime"
})
public class IamPolicy extends Config {

    @JsonProperty("configuration")
    private PolicyConfiguration configuration;

    @JsonProperty("configuration")
    public PolicyConfiguration getConfiguration() {
        return configuration;
    }

    @JsonProperty("configuration")
    public void setConfiguration(PolicyConfiguration configuration) {
        this.configuration = configuration;
    }

}