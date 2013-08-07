package com.test.performance.mvc.domain;

public class ServiceDevice extends BasicDevice {
    private String privateIP;

    private int privatePort;

    private String macAddress;

    private String dynamicDNS;

    private String[] provideServices;

    private boolean canWOL;

    private boolean canP2P;

    public String getDynamicDNS() {
        return this.dynamicDNS;
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public String getPrivateIP() {
        return this.privateIP;
    }

    public int getPrivatePort() {
        return this.privatePort;
    }

    public String[] getProvideServices() {
        return this.provideServices;
    }

    public boolean isCanP2P() {
        return this.canP2P;
    }

    public boolean isCanWOL() {
        return this.canWOL;
    }

    public void setCanP2P(boolean canP2P) {
        this.canP2P = canP2P;
    }

    public void setCanWOL(boolean canWOL) {
        this.canWOL = canWOL;
    }

    public void setDynamicDNS(String dynamicDNS) {
        this.dynamicDNS = dynamicDNS;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setPrivateIP(String privateIP) {
        this.privateIP = privateIP;
    }

    public void setPrivatePort(int privatePort) {
        this.privatePort = privatePort;
    }

    public void setProvideServices(String[] provideServices) {
        this.provideServices = provideServices;
    }
}
