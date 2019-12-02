package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.mongodb.morphia.annotations.Entity;

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
        "ARN",
        "awsRegion",
        "availabilityZone",
        "configurationStateMd5Hash",
        "resourceCreationTime"
})
@Entity(noClassnameStored = true, value = "config")
public class Ec2Instance extends Config {

    @JsonProperty("configuration")
    private Ec2Configuration configuration;

    @JsonProperty("configuration")
    public Ec2Configuration getConfiguration() {
        return configuration;
    }

    @JsonProperty("configuration")
    public void setConfiguration(Ec2Configuration configuration) {
        this.configuration = configuration;
    }

}