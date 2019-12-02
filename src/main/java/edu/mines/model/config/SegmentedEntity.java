package edu.mines.model.config;

import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Indexes;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Matt on 9/6/2017.
 */
@Indexes(@Index(fields = {@Field("oprKey"), @Field("type")}))
public class SegmentedEntity extends SegmentedBase {
    protected String type;
    protected String roleType;
    protected IamUser iamUser;
    protected IamRole iamRole;
    @Indexed
    protected Set<String> instanceProfileArns = new TreeSet<>();
    private String resourceName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public IamUser getIamUser() {
        return iamUser;
    }

    public void setIamUser(IamUser iamUser) {
        this.iamUser = iamUser;
    }

    public IamRole getIamRole() {
        return iamRole;
    }

    public void setIamRole(IamRole iamRole) {
        this.iamRole = iamRole;
    }

    public Set<String> getInstanceProfileArns() {
        return instanceProfileArns;
    }

    public void setInstanceProfileArns(Set<String> instanceProfileArns) {
        this.instanceProfileArns = instanceProfileArns;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
