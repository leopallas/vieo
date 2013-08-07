package com.demo.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created with IntelliJ IDEA.
 * User: xw
 * Date: 4/15/13
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserVO {
    @JsonProperty("1")
    private String  usrId;

    @JsonProperty("2")
    private String  usrPwd;

    @JsonProperty("3")
    private String  usrName;

    @JsonProperty("4")
    private Integer usrType;  //'0:巡检员, 1:管理员',

//    private String  secretToken;
//
//    private String  signatureKey;

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public void setUsrPwd(String usrPwd) {
        this.usrPwd = usrPwd;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName;
    }

    public void setUsrType(Integer usrType) {
        this.usrType = usrType;
    }
//
//    public void setSecretToken(String secretToken) {
//        this.secretToken = secretToken;
//    }
//
//    public void setSignatureKey(String signatureKey) {
//        this.signatureKey = signatureKey;
//    }

    public String getUsrId() {
        return usrId;
    }

    public String getUsrPwd() {
        return usrPwd;
    }

    public String getUsrName() {
        return usrName;
    }

    public Integer getUsrType() {
        return usrType;
    }

//    public String getSecretToken() {
//        return secretToken;
//    }
//
//    public String getSignatureKey() {
//        return signatureKey;
//    }


    @Override
    public String toString() {
        return "UserVO{" +
                "usrId='" + usrId + '\'' +
                ", usrPwd='" + usrPwd + '\'' +
                ", usrName='" + usrName + '\'' +
                ", usrType=" + usrType +
                '}';
    }
}
