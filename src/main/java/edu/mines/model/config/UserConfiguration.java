package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "path",
        "userName",
        "userId",
        "arn",
        "createDate",
        "userPolicyList",
        "groupList",
        "attachedManagedPolicies"
})
public class UserConfiguration {

    @JsonProperty("path")
    private String path;
    @JsonProperty("userName")
    private String userName;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("arn")
    private String arn;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("userPolicyList")
    private List<UserPolicyList> userPolicyList = new ArrayList<>();
    @JsonProperty("groupList")
    private List<String> groupList = new ArrayList<>();
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

    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
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

    @JsonProperty("userPolicyList")
    public List<UserPolicyList> getUserPolicyList() {
        return userPolicyList;
    }

    @JsonProperty("userPolicyList")
    public void setUserPolicyList(List<UserPolicyList> userPolicyList) {
        this.userPolicyList = userPolicyList;
    }

    @JsonProperty("groupList")
    public List<String> getGroupList() {
        return groupList;
    }

    @JsonProperty("groupList")
    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
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