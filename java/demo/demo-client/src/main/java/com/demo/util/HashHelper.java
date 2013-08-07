package com.demo.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashHelper {
    private static char hexChar[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String getMD5(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return getHash(digest, text);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getSHA1(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            return getHash(digest, text);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMD5FilePathSelector(String content) {
        String digest = getMD5(content);
        StringBuffer sb = new StringBuffer();
        // sb.append(digest.substring(0, 1));
        // sb.append("/");
        int level = 3;
        for (int i = 0; i < digest.length(); i += 2) {
            if (level == 0) {
                break;
            }
            if (level != 3) { // added by WangHua
                sb.append("/");
            }
            sb.append(digest.substring(i, i + 2));
            level--;
        }
        return sb.toString();
    }

    private static String getHash(MessageDigest digest, String text) throws IOException {
        digest.update(text.getBytes(Charset.forName("utf8")));
        return toHexString(digest.digest());
    }

    private static String toHexString(byte digest[]) {
        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (byte aDigest : digest) {
            sb.append(hexChar[(aDigest & 0xf0) >>> 4]);
            sb.append(hexChar[aDigest & 0xf]);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(HashHelper.getMD5FilePathSelector("C1"));
    }
}
