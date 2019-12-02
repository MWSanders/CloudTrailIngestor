package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "policyName",
        "policyId",
        "arn",
        "path",
        "defaultVersionId",
        "attachmentCount",
        "isAttachable",
        "description",
        "createDate",
        "updateDate",
        "policyVersionList"
})
public class PolicyConfiguration {

    @JsonProperty("policyName")
    private String policyName;
    @JsonProperty("policyId")
    private String policyId;
    @JsonProperty("arn")
    private String arn;
    @JsonProperty("path")
    private String path;
    @JsonProperty("defaultVersionId")
    private String defaultVersionId;
    @JsonProperty("attachmentCount")
    private Long attachmentCount;
    @JsonProperty("isAttachable")
    private Boolean isAttachable;
    @JsonProperty("description")
    private String description;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("updateDate")
    private String updateDate;
    @JsonProperty("policyVersionList")
    private List<PolicyVersionList> policyVersionList = null;

    @JsonProperty("policyName")
    public String getPolicyName() {
        return policyName;
    }

    @JsonProperty("policyName")
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    @JsonProperty("policyId")
    public String getPolicyId() {
        return policyId;
    }

    @JsonProperty("policyId")
    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    @JsonProperty("arn")
    public String getArn() {
        return arn;
    }

    @JsonProperty("arn")
    public void setArn(String arn) {
        this.arn = arn;
    }

    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    @JsonProperty("defaultVersionId")
    public String getDefaultVersionId() {
        return defaultVersionId;
    }

    @JsonProperty("defaultVersionId")
    public void setDefaultVersionId(String defaultVersionId) {
        this.defaultVersionId = defaultVersionId;
    }

    @JsonProperty("attachmentCount")
    public Long getAttachmentCount() {
        return attachmentCount;
    }

    @JsonProperty("attachmentCount")
    public void setAttachmentCount(Long attachmentCount) {
        this.attachmentCount = attachmentCount;
    }

    @JsonProperty("isAttachable")
    public Boolean getIsAttachable() {
        return isAttachable;
    }

    @JsonProperty("isAttachable")
    public void setIsAttachable(Boolean isAttachable) {
        this.isAttachable = isAttachable;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("createDate")
    public String getCreateDate() {
        return createDate;
    }

    @JsonProperty("createDate")
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @JsonProperty("updateDate")
    public String getUpdateDate() {
        return updateDate;
    }

    @JsonProperty("updateDate")
    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    @JsonProperty("policyVersionList")
    public List<PolicyVersionList> getPolicyVersionList() {
        return policyVersionList;
    }

    @JsonProperty("policyVersionList")
    public void setPolicyVersionList(List<PolicyVersionList> policyVersionList) {
        this.policyVersionList = policyVersionList;
    }

}