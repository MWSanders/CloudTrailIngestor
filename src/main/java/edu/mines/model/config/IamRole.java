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
public class IamRole extends Config {

    @JsonProperty("configuration")
    private RoleConfiguration configuration;

    @JsonProperty("configuration")
    public RoleConfiguration getConfiguration() {
        return configuration;
    }

    @JsonProperty("configuration")
    public void setConfiguration(RoleConfiguration configuration) {
        this.configuration = configuration;
    }
}
