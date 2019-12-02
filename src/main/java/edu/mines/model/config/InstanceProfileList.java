package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "path",
        "instanceProfileName",
        "instanceProfileId",
        "arn",
        "createDate",
        "roles"
})
public class InstanceProfileList {

    @JsonProperty("path")
    private String path;
    @JsonProperty("instanceProfileName")
    private String instanceProfileName;
    @JsonProperty("instanceProfileId")
    private String instanceProfileId;
    @JsonProperty("arn")
    private String arn;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("roles")
    private List<Role> roles = null;

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty("instanceProfileName")
    public String getInstanceProfileName() {
        return instanceProfileName;
    }

    @JsonProperty("instanceProfileName")
    public void setInstanceProfileName(String instanceProfileName) {
        this.instanceProfileName = instanceProfileName;
    }

    @JsonProperty("instanceProfileId")
    public String getInstanceProfileId() {
        return instanceProfileId;
    }

    @JsonProperty("instanceProfileId")
    public void setInstanceProfileId(String instanceProfileId) {
        this.instanceProfileId = instanceProfileId;
    }

    @JsonProperty("arn")
    public String getArn() {
        return arn;
    }

    @JsonProperty("arn")
    public void setArn(String arn) {
        this.arn = arn;
    }

    @JsonProperty("createDate")
    public String getCreateDate() {
        return createDate;
    }

    @JsonProperty("createDate")
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @JsonProperty("roles")
    public List<Role> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}