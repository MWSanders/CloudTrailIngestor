package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "path",
        "groupName",
        "groupId",
        "arn",
        "createDate",
        "groupPolicyList",
        "attachedManagedPolicies"
})
public class GroupConfiguration {

    @JsonProperty("path")
    private String path;
    @JsonProperty("groupName")
    private String groupName;
    @JsonProperty("groupId")
    private String groupId;
    @JsonProperty("arn")
    private String arn;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("groupPolicyList")
    private List<GroupPolicyList> groupPolicyList = null;
    @JsonProperty("attachedManagedPolicies")
    private List<AttachedManagedPolicy> attachedManagedPolicies = null;

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty("groupName")
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty("groupName")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("groupId")
    public String getGroupId() {
        return groupId;
    }

    @JsonProperty("groupId")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    @JsonProperty("groupPolicyList")
    public List<GroupPolicyList> getGroupPolicyList() {
        return groupPolicyList;
    }

    @JsonProperty("groupPolicyList")
    public void setGroupPolicyList(List<GroupPolicyList> groupPolicyList) {
        this.groupPolicyList = groupPolicyList;
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