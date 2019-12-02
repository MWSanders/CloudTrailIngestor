package edu.mines.model;


import com.fasterxml.jackson.annotation.*;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "ARNRegex",
        "ARNFormat",
        "HasResource",
        "StringPrefix",
        "Actions",
        "conditionKeys",
        "auditActionLevel",
        "auditActions"
})
@Entity
public class ServiceMap {

    @JsonProperty("_id")
    @Id
    private String Id;
    @JsonProperty("ARNRegex")
    private String ARNRegex;
    @JsonProperty("ARNFormat")
    private String ARNFormat;
    @JsonProperty("HasResource")
    private Boolean HasResource;
    @JsonProperty("StringPrefix")
    private String StringPrefix;
    @JsonProperty("Actions")
    private List<String> Actions = new ArrayList<String>();
    @JsonProperty("conditionKeys")
    private List<String> conditionKeys = new ArrayList<String>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The Id
     */
    @JsonProperty("_id")
    public String getId() {
        return Id;
    }

    /**
     * @param Id The _id
     */
    @JsonProperty("_id")
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return The ARNRegex
     */
    @JsonProperty("ARNRegex")
    public String getARNRegex() {
        return ARNRegex;
    }

    /**
     * @param ARNRegex The ARNRegex
     */
    @JsonProperty("ARNRegex")
    public void setARNRegex(String ARNRegex) {
        this.ARNRegex = ARNRegex;
    }

    /**
     * @return The ARNFormat
     */
    @JsonProperty("ARNFormat")
    public String getARNFormat() {
        return ARNFormat;
    }

    /**
     * @param ARNFormat The ARNFormat
     */
    @JsonProperty("ARNFormat")
    public void setARNFormat(String ARNFormat) {
        this.ARNFormat = ARNFormat;
    }

    /**
     * @return The HasResource
     */
    @JsonProperty("HasResource")
    public Boolean getHasResource() {
        return HasResource;
    }

    /**
     * @param HasResource The HasResource
     */
    @JsonProperty("HasResource")
    public void setHasResource(Boolean HasResource) {
        this.HasResource = HasResource;
    }

    /**
     * @return The StringPrefix
     */
    @JsonProperty("StringPrefix")
    public String getStringPrefix() {
        return StringPrefix;
    }

    /**
     * @param StringPrefix The StringPrefix
     */
    @JsonProperty("StringPrefix")
    public void setStringPrefix(String StringPrefix) {
        this.StringPrefix = StringPrefix;
    }

    /**
     * @return The Actions
     */
    @JsonProperty("Actions")
    public List<String> getActions() {
        return Actions;
    }

    /**
     * @param Actions The Actions
     */
    @JsonProperty("Actions")
    public void setActions(List<String> Actions) {
        this.Actions = Actions;
    }

    /**
     * @return The conditionKeys
     */
    @JsonProperty("conditionKeys")
    public List<String> getConditionKeys() {
        return conditionKeys;
    }

    /**
     * @param conditionKeys The conditionKeys
     */
    @JsonProperty("conditionKeys")
    public void setConditionKeys(List<String> conditionKeys) {
        this.conditionKeys = conditionKeys;
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