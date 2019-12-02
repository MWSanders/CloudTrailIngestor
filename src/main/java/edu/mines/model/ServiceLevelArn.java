package edu.mines.model;

import com.fasterxml.jackson.annotation.*;
import org.mongodb.morphia.annotations.Embedded;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "attributeName",
        "arnPattern"
})
@Embedded
public class ServiceLevelArn {

    @JsonProperty("attributeName")
    private String attributeName;
    @JsonProperty("arnPattern")
    private String arnPattern;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The attributeName
     */
    @JsonProperty("attributeName")
    public String getAttributeName() {
        return attributeName;
    }

    /**
     * @param attributeName The attributeName
     */
    @JsonProperty("attributeName")
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * @return The arnPattern
     */
    @JsonProperty("arnPattern")
    public String getArnPattern() {
        return arnPattern;
    }

    /**
     * @param arnPattern The arnPattern
     */
    @JsonProperty("arnPattern")
    public void setArnPattern(String arnPattern) {
        this.arnPattern = arnPattern;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}