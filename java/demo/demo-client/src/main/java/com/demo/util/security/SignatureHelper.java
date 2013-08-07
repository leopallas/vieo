package com.demo.util.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import net.iharder.Base64;

public class SignatureHelper {
    private static final String ENCODING = "UTF-8";

    private static final String MAC_NAME = "HmacSHA1";

    public static String generateSignature(URL url, String method, String signatureKey) throws IOException {
        byte[] keyBytes = Base64.decode(signatureKey);
        SecretKey key = new SecretKeySpec(keyBytes, MAC_NAME);
        Mac mac;
        try {
            mac = Mac.getInstance(MAC_NAME);
            mac.init(key);
            String message = String.format("%s+%s+%s+%s", method, url.getProtocol(), url.getPath(), url.getQuery());
            mac.update(message.getBytes(ENCODING));
            return Base64.encodeBytes(mac.doFinal());
        } catch (NoSuchAlgorithmException e) {
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean validateSignature(HttpServletRequest request, String signatureKey) throws IOException {
        return validateSignature(request.getParameter("au"), request.getParameter("tkn"), request.getMethod(),
                request.getScheme(), request.getRequestURI(), request.getQueryString(), signatureKey);
    }

    public static boolean validateSignature(String signature, String secretToken, String method, String schema,
            String basePath, String queryString, String signatureKey) throws IOException {
        if (signature == null) {
            return false;
        }
        if (secretToken == null) {
            return false;
        }

        byte[] keyBytes = Base64.decode(signatureKey);
        SecretKey key = new SecretKeySpec(keyBytes, MAC_NAME);
        Mac mac;
        try {
            mac = Mac.getInstance(MAC_NAME);
            mac.init(key);

            String uri = String.format("%s+%s+%s+%s", method, schema, basePath, queryString);
            uri = uri.replace("&au=" + URLEncoder.encode(signature, ENCODING), "");
            uri = uri.replace("&tkn=" + URLEncoder.encode(secretToken, ENCODING), "");
            mac.update(uri.getBytes(ENCODING));
            return Base64.encodeBytes(mac.doFinal()).equals(signature);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
