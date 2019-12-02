package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "path",
        "roleName",
        "roleId",
        "arn",
        "createDate",
        "assumeRolePolicyDocument",
        "instanceProfileList",
        "rolePolicyList",
        "attachedManagedPolicies"
})
public class RoleConfiguration {

    @JsonProperty("path")
    private String path;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("roleId")
    private String roleId;
    @JsonProperty("arn")
    private String arn;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("assumeRolePolicyDocument")
    private String assumeRolePolicyDocument;
    @JsonProperty("instanceProfileList")
    private List<InstanceProfileList> instanceProfileList = new ArrayList<>();
    @JsonProperty("rolePolicyList")
    private List<RolePolicyList> rolePolicyList = new ArrayList<>();
    @JsonProperty("attachedManagedPolicies")
    private List<AttachedManagedPolicy> attachedManagedPolicies = new ArrayList<>();

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty("roleName")
    public String getRoleName() {
        return roleName;
    }

    @JsonProperty("roleName")
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @JsonProperty("roleId")
    public String getRoleId() {
        return roleId;
    }

    @JsonProperty("roleId")
    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    @JsonProperty("assumeRolePolicyDocument")
    public String getAssumeRolePolicyDocument() {
        return assumeRolePolicyDocument;
    }

    @JsonProperty("assumeRolePolicyDocument")
    public void setAssumeRolePolicyDocument(String assumeRolePolicyDocument) {
        this.assumeRolePolicyDocument = assumeRolePolicyDocument;
    }

    @JsonProperty("instanceProfileList")
    public List<InstanceProfileList> getInstanceProfileList() {
        return instanceProfileList;
    }

    @JsonProperty("instanceProfileList")
    public void setInstanceProfileList(List<InstanceProfileList> instanceProfileList) {
        this.instanceProfileList = instanceProfileList;
    }

    @JsonProperty("rolePolicyList")
    public List<RolePolicyList> getRolePolicyList() {
        return rolePolicyList;
    }

    @JsonProperty("rolePolicyList")
    public void setRolePolicyList(List<RolePolicyList> rolePolicyList) {
        this.rolePolicyList = rolePolicyList;
    }

    @JsonProperty("attachedManagedPolicies")
    public List<AttachedManagedPolicy> getAttachedManagedPolicies() {
        return attachedManagedPolicies;
    }

    @JsonProperty("attachedManagedPolicies")
    public void setAttachedManagedPolicies(List<AttachedManagedPolicy> attachedManagedPolicies) {
        this.attachedManagedPolicies = attachedManagedPolicies;
    }

}