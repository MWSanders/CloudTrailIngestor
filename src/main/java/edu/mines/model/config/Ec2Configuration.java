package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "amiLaunchIndex",
        "imageId",
        "instanceId",
        "instanceType",
        "keyName",
        "launchTime",
        "monitoring",
        "placement",
        "privateDnsName",
        "privateIpAddress",
        "productCodes",
        "publicDnsName",
        "state",
        "stateTransitionReason",
        "subnetId",
        "vpcId",
        "architecture",
        "blockDeviceMappings",
        "clientToken",
        "ebsOptimized",
        "enaSupport",
        "hypervisor",
        "iamInstanceProfile",
        "networkInterfaces",
        "rootDeviceName",
        "rootDeviceType",
        "securityGroups",
        "sourceDestCheck",
        "tags",
        "virtualizationType"
})
public class Ec2Configuration {

    @JsonProperty("amiLaunchIndex")
    private Long amiLaunchIndex;
    @JsonProperty("imageId")
    private String imageId;
    @JsonProperty("instanceId")
    private String instanceId;
    @JsonProperty("instanceType")
    private String instanceType;
    @JsonProperty("keyName")
    private String keyName;
    @JsonProperty("launchTime")
    private String launchTime;
    @JsonProperty("privateDnsName")
    private String privateDnsName;
    @JsonProperty("privateIpAddress")
    private String privateIpAddress;
    @JsonProperty("productCodes")
    private List<Object> productCodes = null;
    @JsonProperty("publicDnsName")
    private String publicDnsName;
    @JsonProperty("stateTransitionReason")
    private String stateTransitionReason;
    @JsonProperty("subnetId")
    private String subnetId;
    @JsonProperty("vpcId")
    private String vpcId;
    @JsonProperty("architecture")
    private String architecture;
    @JsonProperty("clientToken")
    private String clientToken;
    @JsonProperty("ebsOptimized")
    private Boolean ebsOptimized;
    @JsonProperty("enaSupport")
    private Boolean enaSupport;
    @JsonProperty("hypervisor")
    private String hypervisor;
    @JsonProperty("iamInstanceProfile")
    private IamInstanceProfile iamInstanceProfile;
    @JsonProperty("rootDeviceName")
    private String rootDeviceName;
    @JsonProperty("rootDeviceType")
    private String rootDeviceType;
    @JsonProperty("sourceDestCheck")
    private Boolean sourceDestCheck;
    @JsonProperty("virtualizationType")
    private String virtualizationType;

    @JsonProperty("amiLaunchIndex")
    public Long getAmiLaunchIndex() {
        return amiLaunchIndex;
    }

    @JsonProperty("amiLaunchIndex")
    public void setAmiLaunchIndex(Long amiLaunchIndex) {
        this.amiLaunchIndex = amiLaunchIndex;
    }

    @JsonProperty("imageId")
    public String getImageId() {
        return imageId;
    }

    @JsonProperty("imageId")
    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @JsonProperty("instanceId")
    public String getInstanceId() {
        return instanceId;
    }

    @JsonProperty("instanceId")
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    @JsonProperty("instanceType")
    public String getInstanceType() {
        return instanceType;
    }

    @JsonProperty("instanceType")
    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    @JsonProperty("keyName")
    public String getKeyName() {
        return keyName;
    }

    @JsonProperty("keyName")
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @JsonProperty("launchTime")
    public String getLaunchTime() {
        return launchTime;
    }

    @JsonProperty("launchTime")
    public void setLaunchTime(String launchTime) {
        this.launchTime = launchTime;
    }

    @JsonProperty("privateDnsName")
    public String getPrivateDnsName() {
        return privateDnsName;
    }

    @JsonProperty("privateDnsName")
    public void setPrivateDnsName(String privateDnsName) {
        this.privateDnsName = privateDnsName;
    }

    @JsonProperty("privateIpAddress")
    public String getPrivateIpAddress() {
        return privateIpAddress;
    }

    @JsonProperty("privateIpAddress")
    public void setPrivateIpAddress(String privateIpAddress) {
        this.privateIpAddress = privateIpAddress;
    }

    @JsonProperty("productCodes")
    public List<Object> getProductCodes() {
        return productCodes;
    }

    @JsonProperty("productCodes")
    public void setProductCodes(List<Object> productCodes) {
        this.productCodes = productCodes;
    }

    @JsonProperty("publicDnsName")
    public String getPublicDnsName() {
        return publicDnsName;
    }

    @JsonProperty("publicDnsName")
    public void setPublicDnsName(String publicDnsName) {
        this.publicDnsName = publicDnsName;
    }

    @JsonProperty("stateTransitionReason")
    public String getStateTransitionReason() {
        return stateTransitionReason;
    }

    @JsonProperty("stateTransitionReason")
    public void setStateTransitionReason(String stateTransitionReason) {
        this.stateTransitionReason = stateTransitionReason;
    }

    @JsonProperty("subnetId")
    public String getSubnetId() {
        return subnetId;
    }

    @JsonProperty("subnetId")
    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    @JsonProperty("vpcId")
    public String getVpcId() {
        return vpcId;
    }

    @JsonProperty("vpcId")
    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    @JsonProperty("architecture")
    public String getArchitecture() {
        return architecture;
    }

    @JsonProperty("architecture")
    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    @JsonProperty("clientToken")
    public String getClientToken() {
        return clientToken;
    }

    @JsonProperty("clientToken")
    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    @JsonProperty("ebsOptimized")
    public Boolean getEbsOptimized() {
        return ebsOptimized;
    }

    @JsonProperty("ebsOptimized")
    public void setEbsOptimized(Boolean ebsOptimized) {
        this.ebsOptimized = ebsOptimized;
    }

    @JsonProperty("enaSupport")
    public Boolean getEnaSupport() {
        return enaSupport;
    }

    @JsonProperty("enaSupport")
    public void setEnaSupport(Boolean enaSupport) {
        this.enaSupport = enaSupport;
    }

    @JsonProperty("hypervisor")
    public String getHypervisor() {
        return hypervisor;
    }

    @JsonProperty("hypervisor")
    public void setHypervisor(String hypervisor) {
        this.hypervisor = hypervisor;
    }

    @JsonProperty("iamInstanceProfile")
    public IamInstanceProfile getIamInstanceProfile() {
        return iamInstanceProfile;
    }

    @JsonProperty("iamInstanceProfile")
    public void setIamInstanceProfile(IamInstanceProfile iamInstanceProfile) {
        this.iamInstanceProfile = iamInstanceProfile;
    }

    @JsonProperty("rootDeviceName")
    public String getRootDeviceName() {
        return rootDeviceName;
    }

    @JsonProperty("rootDeviceName")
    public void setRootDeviceName(String rootDeviceName) {
        this.rootDeviceName = rootDeviceName;
    }

    @JsonProperty("rootDeviceType")
    public String getRootDeviceType() {
        return rootDeviceType;
    }

    @JsonProperty("rootDeviceType")
    public void setRootDeviceType(String rootDeviceType) {
        this.rootDeviceType = rootDeviceType;
    }

    @JsonProperty("sourceDestCheck")
    public Boolean getSourceDestCheck() {
        return sourceDestCheck;
    }

    @JsonProperty("sourceDestCheck")
    public void setSourceDestCheck(Boolean sourceDestCheck) {
        this.sourceDestCheck = sourceDestCheck;
    }

    @JsonProperty("virtualizationType")
    public String getVirtualizationType() {
        return virtualizationType;
    }

    @JsonProperty("virtualizationType")
    public void setVirtualizationType(String virtualizationType) {
        this.virtualizationType = virtualizationType;
    }

}