package com.demo.service;

public class ServerContext {
    private String signatureKey;

    private String secretToken;

    public ServerContext(String signatureKey, String secretToken) {
        this.signatureKey = signatureKey;
        this.secretToken = secretToken;
    }

    public String getSecretToken() {
        return this.secretToken;
    }

    public String getSignatureKey() {
        return this.signatureKey;
    }
}
