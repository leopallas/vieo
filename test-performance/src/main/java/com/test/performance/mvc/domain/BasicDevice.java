package com.test.performance.mvc.domain;

import java.util.Date;
import java.util.UUID;

public class BasicDevice {
    private String id;

    public static void assignDeviceID(BasicDevice device) {
        device.setDeviceID(UUID.randomUUID().toString());
    }

    private String userID;

    private String deviceID;

    private String serialID;

    private String manufacture;

    private String name;

    private String version;

    private String osVersion;

    private String extraInfo;

    private String publicIP;

    private int publicPort;

    private AuthenticationKey authenticationKey;

    private Date lastActiveTime;

    private int status;

    public AuthenticationKey getAuthenticationKey() {
        return this.authenticationKey;
    }

    public String getDeviceID() {
        return this.deviceID;
    }

    public String getExtraInfo() {
        return this.extraInfo;
    }

    public Date getLastActiveTime() {
        return this.lastActiveTime;
    }

    public String getManufacture() {
        return this.manufacture;
    }

    public String getName() {
        return this.name;
    }

    public String getOsVersion() {
        return this.osVersion;
    }

    public String getPublicIP() {
        return this.publicIP;
    }

    public int getPublicPort() {
        return this.publicPort;
    }

    public String getSerialID() {
        return this.serialID;
    }

    public int getStatus() {
        return this.status;
    }

    public String getUserID() {
        return this.userID;
    }

    public String getVersion() {
        return this.version;
    }

    public void setAuthenticationKey(AuthenticationKey authenticationKey) {
        this.authenticationKey = authenticationKey;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public void setPublicIP(String publicIP) {
        this.publicIP = publicIP;
    }

    public void setPublicPort(int publicPort) {
        this.publicPort = publicPort;
    }

    public void setSerialID(String serialID) {
        this.serialID = serialID;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
