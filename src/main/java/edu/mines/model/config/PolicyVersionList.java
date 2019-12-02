package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "document",
        "versionId",
        "isDefaultVersion",
        "createDate"
})
public class PolicyVersionList {

    @JsonProperty("document")
    private String document;
    @JsonProperty("versionId")
    private String versionId;
    @JsonProperty("isDefaultVersion")
    private Boolean isDefaultVersion;
    @JsonProperty("createDate")
    private String createDate;

    @JsonProperty("document")
    public String getDocument() {
        return document;
    }

    @JsonProperty("document")
    public void setDocument(String document) {
        this.document = document;
    }

    @JsonProperty("versionId")
    public String getVersionId() {
        return versionId;
    }

    @JsonProperty("versionId")
    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    @JsonProperty("isDefaultVersion")
    public Boolean getIsDefaultVersion() {
        return isDefaultVersion;
    }

    @JsonProperty("isDefaultVersion")
    public void setIsDefaultVersion(Boolean isDefaultVersion) {
        this.isDefaultVersion = isDefaultVersion;
    }

    @JsonProperty("createDate")
    public String getCreateDate() {
        return createDate;
    }

    @JsonProperty("createDate")
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}