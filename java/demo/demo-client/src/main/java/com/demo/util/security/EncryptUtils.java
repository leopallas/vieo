package com.demo.util.security;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encryption / Decryption Utils
 * 
 * @author UPPower Studio
 * 
 */
public class EncryptUtils {
    private static byte[] iv = new byte[16];

    /**
     * Encrypt Data via DES
     * 
     * @param encryptString
     *            String type data
     * @param encryptKey
     *            Key
     * @return encrypted data
     * @throws Exception
     */
    public static String encryptToString(String encryptString, String encryptKey) throws Exception {
        int mod = encryptKey.length() % 16;
        if (mod != 0) {
            for (int i = mod; i < 16; i++)
                encryptKey += "0";
        }
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));

        return parseByte2HexStr(encryptedData);
    }

    /**
     * Encrypt Data via DES
     * 
     * @param encryptBytes
     *            byte array data
     * @param encryptKey
     *            Key
     * @return encrypted data
     * @throws Exception
     */
    public static String encryptToString(byte[] encryptBytes, String encryptKey) throws Exception {
        int mod = encryptKey.length() % 16;
        if (mod != 0) {
            for (int i = mod; i < 16; i++)
                encryptKey += "0";
        }
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptBytes);

        return parseByte2HexStr(encryptedData);
    }

    public static byte[] encryptToByteArray(byte[] encryptBytes, String encryptKey) throws Exception {
        int mod = encryptKey.length() % 16;
        if (mod != 0) {
            for (int i = mod; i < 16; i++)
                encryptKey += "0";
        }
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptBytes);

        return encryptedData;
    }

    /**
     * Decrypt Data via DES, special for file, images etc.
     * 
     * @param decryptString
     * @param decryptKey
     * @return byte array decrypted data
     * @throws Exception
     */
    public static byte[] decryptToByteArray(String decryptString, String decryptKey) throws Exception {
        int mod = decryptKey.length() % 16;
        if (mod != 0) {
            for (int i = mod; i < 16; i++)
                decryptKey += "0";
        }
        byte[] byteMi = parseHexStr2Byte(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);

        return decryptedData;
    }

    public static byte[] decryptToByteArray(byte[] decryptString, String decryptKey) throws Exception {
        int mod = decryptKey.length() % 16;
        if (mod != 0) {
            for (int i = mod; i < 16; i++)
                decryptKey += "0";
        }
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(decryptString);

        return decryptedData;
    }

    /**
     * Decrypt Data via DES
     * 
     * @param decryptString
     * @param decryptKey
     * @return String decrypted data
     * @throws Exception
     */
    public static String decryptToString(String decryptString, String decryptKey) throws Exception {
        int mod = decryptKey.length() % 16;
        if (mod != 0) {
            for (int i = mod; i < 16; i++)
                decryptKey += "0";
        }
        byte[] byteMi = parseHexStr2Byte(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptedData = cipher.doFinal(byteMi);
        return new String(decryptedData, "UTF-8");
    }

    /**
     * Encrypt the file
     * 
     * @param sourceFilePath
     *            The source file path, read to do encrypt, include file name,
     *            such as "/sdcard/iToss/video/test.mp4"
     * @param destFilePath
     *            The file path after encrypted, such as
     *            "/sdcard/iToss/video/encrypted-test.mp4"
     * @param encryptKey
     *            The encrypt key
     * @throws Exception
     */
    public static void encryptFile(String sourceFilePath, String destFilePath, String encryptKey) throws Exception {
        int mod = encryptKey.length() % 16;
        if (mod != 0) {
            for (int i = mod; i < 16; i++)
                encryptKey += "0";
        }
        File sourceFile = new File(sourceFilePath);
        FileInputStream fin = new FileInputStream(sourceFile);
        FileOutputStream fout = new FileOutputStream(destFilePath);

        byte[] iv = new byte[16];
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);

        byte[] ivs = cipher.getIV();
        DataOutputStream dout = new DataOutputStream(fout);
        dout.writeInt(ivs.length);
        dout.write(ivs);
        byte[] input = new byte[64];
        while (true) {
            int bytesRead = fin.read(input);
            if (bytesRead == -1)
                break;
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null)
                dout.write(output);
        }
        byte[] output = cipher.doFinal();
        if (output != null)
            dout.write(output);
        fin.close();
        dout.flush();
        dout.close();
    }

    /**
     * Decrypt file
     * 
     * @param decryptedFilePath
     *            The encrypted file path, include file name
     * @param destFilePath
     *            The decrypted file save path, include file name
     * @param encryptKey
     *            The encrypt key
     * @throws Exception
     */
    public static void decryptFile(String decryptedFilePath, String destFilePath, String encryptKey) throws Exception {
        int mod = encryptKey.length() % 16;
        if (mod != 0) {
            for (int i = mod; i < 16; i++)
                encryptKey += "0";
        }
        FileInputStream fin = new FileInputStream(decryptedFilePath);
        FileOutputStream fout = new FileOutputStream(destFilePath);

        // Read the initialization vector.
        DataInputStream din = new DataInputStream(fin);
        int ivSize = din.readInt();
        byte[] iv = new byte[ivSize];
        din.readFully(iv);

        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);

        byte[] input = new byte[64];
        while (true) {
            int bytesRead = fin.read(input);
            if (bytesRead == -1)
                break;
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null)
                fout.write(output);
        }
        byte[] output = cipher.doFinal();
        if (output != null)
            fout.write(output);
        fin.close();
        fout.flush();
        fout.close();
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 灏�16杩涘埗杞崲涓轰簩杩涘埗
     * 
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
