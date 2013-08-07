package com.demo.vo;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerVO {
    private String     address;

    private int        port;

    private ServerType type;

    private String     webContext;

    public ServerVO(String address, int port, ServerType type, String webContext) {
        this.address = address;
        this.port = port;
        this.type = type;
        this.webContext = webContext;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerType getType() {
        return type;
    }

    public void setType(ServerType type) {
        this.type = type;
    }

    public String getWebContext() {
        return webContext;
    }

    public void setWebContext(String webContext) {
        this.webContext = webContext;
    }

    @Override
    public String toString() {
        return "ServerVO{" + "address='" + address + '\'' + ", port=" + port + ", type=" + type + ", webContext='"
                + webContext + '\'' + '}';
    }
}
