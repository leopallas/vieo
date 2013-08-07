package com.demo.web.domain;

public class User {
    private String  usrId;

    private String  usrPwd;

    private String  usrName;

    private Integer usrType;

    private String  secretToken;

    private String  signatureKey;

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId == null ? null : usrId.trim();
    }

    public String getUsrPwd() {
        return usrPwd;
    }

    public void setUsrPwd(String usrPwd) {
        this.usrPwd = usrPwd == null ? null : usrPwd.trim();
    }

    public String getUsrName() {
        return usrName;
    }

    public void setUsrName(String usrName) {
        this.usrName = usrName == null ? null : usrName.trim();
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken == null ? null : secretToken.trim();
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public void setSignatureKey(String signatureKey) {
        this.signatureKey = signatureKey == null ? null : signatureKey.trim();
    }

    public Integer getUsrType() {
        return usrType;
    }

    public void setUsrType(Integer usrType) {
        this.usrType = usrType;
    }
}
