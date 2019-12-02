package edu.mines.model.config;

import com.amazonaws.services.identitymanagement.model.Policy;
import org.mongodb.morphia.annotations.Entity;

import java.util.Date;

/**
 * Created by Matt on 9/3/2017.
 */
@Entity(noClassnameStored = true)
public class SegmentedPolicy extends SegmentedBase {
    private Policy iamPolicy;
    private String document;

    public SegmentedPolicy() {
    }

    public SegmentedPolicy(Policy iamPolicy) {
        this.iamPolicy = iamPolicy;
    }

    public String generateId() {
        return SegmentedBase.generateId(getOprKey(), getEnvironment(), getArn());
    }

    public String generateOprKey() {
        return SegmentedBase.generateOprKey(getStartTime(), getEndTime());
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setPolicyName(String policyName) {
        iamPolicy.setPolicyName(policyName);
    }

    public Date getCreateDate() {
        return iamPolicy.getCreateDate();
    }

    public Boolean isAttachable() {
        return iamPolicy.isAttachable();
    }

    public Policy withAttachmentCount(Integer attachmentCount) {
        return iamPolicy.withAttachmentCount(attachmentCount);
    }

    public void setPolicyId(String policyId) {
        iamPolicy.setPolicyId(policyId);
    }

    public Boolean getIsAttachable() {
        return iamPolicy.getIsAttachable();
    }

    public void setCreateDate(Date createDate) {
        iamPolicy.setCreateDate(createDate);
    }

    public Policy withArn(String arn) {
        return iamPolicy.withArn(arn);
    }

    public Policy withIsAttachable(Boolean isAttachable) {
        return iamPolicy.withIsAttachable(isAttachable);
    }

    public String getPolicyName() {
        return iamPolicy.getPolicyName();
    }

    public void setDescription(String description) {
        iamPolicy.setDescription(description);
    }

    public void setIsAttachable(Boolean isAttachable) {
        iamPolicy.setIsAttachable(isAttachable);
    }

    public Policy withDescription(String description) {
        return iamPolicy.withDescription(description);
    }

    public Date getUpdateDate() {
        return iamPolicy.getUpdateDate();
    }

    public void setDefaultVersionId(String defaultVersionId) {
        iamPolicy.setDefaultVersionId(defaultVersionId);
    }

    public String getDefaultVersionId() {
        return iamPolicy.getDefaultVersionId();
    }

    public Integer getAttachmentCount() {
        return iamPolicy.getAttachmentCount();
    }

    public Policy withPath(String path) {
        return iamPolicy.withPath(path);
    }

    public Policy withCreateDate(Date createDate) {
        return iamPolicy.withCreateDate(createDate);
    }

    public Policy withUpdateDate(Date updateDate) {
        return iamPolicy.withUpdateDate(updateDate);
    }

    public void setPath(String path) {
        iamPolicy.setPath(path);
    }

    public String getArn() {
        return iamPolicy.getArn();
    }

    public Policy withPolicyId(String policyId) {
        return iamPolicy.withPolicyId(policyId);
    }

    public Policy withDefaultVersionId(String defaultVersionId) {
        return iamPolicy.withDefaultVersionId(defaultVersionId);
    }

    public String getPolicyId() {
        return iamPolicy.getPolicyId();
    }

    public String getDescription() {
        return iamPolicy.getDescription();
    }

    public Policy withPolicyName(String policyName) {
        return iamPolicy.withPolicyName(policyName);
    }

    public void setAttachmentCount(Integer attachmentCount) {
        iamPolicy.setAttachmentCount(attachmentCount);
    }

    public void setArn(String arn) {
        iamPolicy.setArn(arn);
    }

    public void setUpdateDate(Date updateDate) {
        iamPolicy.setUpdateDate(updateDate);
    }

    public String getPath() {
        return iamPolicy.getPath();
    }
}
