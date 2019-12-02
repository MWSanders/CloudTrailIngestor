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
public class IamUser extends Config {
    @JsonProperty("configuration")
    private UserConfiguration configuration;

    @JsonProperty("configuration")
    public UserConfiguration getConfiguration() {
        return configuration;
    }

    @JsonProperty("configuration")
    public void setConfiguration(UserConfiguration configuration) {
        this.configuration = configuration;
    }
}