package com.demo.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class LoginResultVO {

    @JsonProperty("1")
    private String secretToken;

    @JsonProperty("2")
    private String signatureKey;

    @JsonProperty("3")
    private long   serverDatetime;

    @JsonProperty("4")
    private String usrName;

    @JsonProperty("5")
    private int    usrType;

    public LoginResultVO() {
    }

    public LoginResultVO(String secretToken, String signatureKey, String usrName, int usrType, long serverDatetime) {
        this.secretToken = secretToken;
        this.signatureKey = signatureKey;
        this.serverDatetime = serverDatetime;
        this.usrName = usrName;
        this.usrType = usrType;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public long getServerDatetime() {
        return serverDatetime;
    }

    public String getUsrName() {
        return usrName;
    }

    public int getUsrType() {
        return usrType;
    }

    @Override
    public String toString() {
        return "LoginResultVO{" + "secretToken='" + secretToken + '\'' + ", signatureKey='" + signatureKey + '\''
                + ", serverDatetime=" + serverDatetime + ", usrName='" + usrName + '\'' + ", usrType=" + usrType + '}';
    }
}
