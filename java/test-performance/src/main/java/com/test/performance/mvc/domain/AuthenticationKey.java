package com.test.performance.mvc.domain;


public class AuthenticationKey {
//    public static AuthenticationKey generateAuthenticationKey() {
//        String uuid = UUID.randomUUID().toString();
//        String secretToken = null;
//        String signatureKey = null;
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA1");
//            digest.update(uuid.getBytes());
//            secretToken = Base64.encodeBytes(digest.digest());
//        } catch (NoSuchAlgorithmException e) {
//        }
//
//        SecureRandom random = new SecureRandom(UUID.randomUUID().toString().getBytes());
//        try {
//            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
//            keyGenerator.init(random);
//            SecretKey key = keyGenerator.generateKey();
//            signatureKey = Base64.encodeBytes(key.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//        }
//
//        return new AuthenticationKey(secretToken, signatureKey);
//    }

    private String secretToken;

    private String signatureKey;

    public AuthenticationKey(String secretToken, String signatureKey) {
        this.secretToken = secretToken;
        this.signatureKey = signatureKey;
    }

    public String getSecretToken() {
        return this.secretToken;
    }

    public String getSignatureKey() {
        return this.signatureKey;
    }
}
