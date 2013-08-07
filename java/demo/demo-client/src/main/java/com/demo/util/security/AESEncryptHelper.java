package com.demo.util.security;

import net.iharder.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

public class AESEncryptHelper {
    // private static final String TAG = AESEncryptHelper.class.getName();

    private static final String ALGORITHM           = "AES";

    private static final String ENCODING            = "UTF-8";

    private static final String TRANSFORMATION_NAME = "AES/CBC/PKCS5Padding";

    // private static final String PRNG_ALGORITHM = "SHA1PRNG";

    public static byte[]        iv                  = new byte[] { 82, 22, 50, 44, -16, 124, -40, -114, -87, -40, 37,
            23, -56, 23, -33, 75                   };

    // public static void encryptVO(CommonVO vo, String password) {
    // Class<? extends CommonVO> clazz = vo.getClass();
    // for (Field field : clazz.getDeclaredFields()) {
    // if (field.isAnnotationPresent(Encrpt.class)) {
    // if (!field.getType().equals(String.class)) {
    // throw new RuntimeException("encrypt field type must be String");
    // }
    // field.setAccessible(true);
    // try {
    // String value = field.get(vo).toString();
    // field.set(vo, encrypt(value, password));
    // } catch (IllegalArgumentException e) {
    // e.printStackTrace();
    // } catch (IllegalAccessException e) {
    // e.printStackTrace();
    // } catch (NullPointerException e) {
    // e.printStackTrace();
    // // ignore
    // }
    // }
    // }
    //
    // }
    //
    // public static void decryptVO(CommonVO vo, String password) {
    // Class<? extends CommonVO> clazz = vo.getClass();
    // for (Field field : clazz.getDeclaredFields()) {
    // if (field.isAnnotationPresent(Decrypt.class)) {
    // if (!field.getType().equals(String.class)) {
    // throw new RuntimeException("encrypt field type must be String");
    // }
    // field.setAccessible(true);
    // try {
    // String value = field.get(vo).toString();
    // field.set(vo, decrypt(value, password));
    // } catch (IllegalArgumentException e) {
    // e.printStackTrace();
    // } catch (IllegalAccessException e) {
    // e.printStackTrace();
    // } catch (NullPointerException e) {
    // e.printStackTrace();
    // }
    // }
    // }
    // }

    public static String encrypt(String content, String password) {
        try {
            if (password == null) {
                System.out.print("key is null");
                return null;
            }

            int mod = password.length() % 16;
            if (mod != 0) {
                for (int i = mod; i < 16; i++)
                    password += "0";
            }

            if (password.length() > 16) {
                password = password.substring(password.length() - 16, password.length());
            }

            // KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
            // Provider[] ps = Security.getProviders();
            //
            // for (Provider p : ps) {
            // System.out.println("Provider :: " + p.getName());
            // System.out.println("Provider :: " + p.getInfo());
            // }
            //
            // SecureRandom random = SecureRandom.getInstance(PRNG_ALGORITHM);
            // random.setSeed(password.getBytes());
            // kgen.init(128, random);
            // SecretKey secretKey = kgen.generateKey();
            // byte[] enCodeFormat = secretKey.getEncoded();

            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            byte[] enCodeFormat = password.getBytes(ENCODING);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            return Base64.encodeBytes(cipher.doFinal(content.getBytes(ENCODING)));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String content, String password) {
        try {
            if (password == null) {
                System.out.print("key is null");
                return null;
            }

            int mod = password.length() % 16;
            if (mod != 0) {
                for (int i = mod; i < 16; i++)
                    password += "0";
            }

            if (password.length() > 16) {
                password = password.substring(password.length() - 16, password.length());
            }

            // KeyGenerator kgen = KeyGenerator.getInstance(ALGORITHM);
            // // Provider p = Security.getProvider("BC");
            // SecureRandom random = SecureRandom.getInstance(PRNG_ALGORITHM);
            // random.setSeed(password.getBytes());
            // kgen.init(128, random);
            // SecretKey secretKey = kgen.generateKey();
            // byte[] enCodeFormat = secretKey.getEncoded();
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            byte[] enCodeFormat = password.getBytes(ENCODING);
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION_NAME);
            cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            return new String(cipher.doFinal(Base64.decode(content)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // private static String byteArrayToHexString(byte[] b) {
    // StringBuilder sb = new StringBuilder(b.length * 2);
    // for (byte aB : b) {
    // int v = aB & 0xff;
    // if (v < 16) {
    // sb.append('0');
    // }
    // sb.append(Integer.toHexString(v));
    // }
    // return sb.toString().toUpperCase();
    // }
    //
    // private static byte[] hexStringToByteArray(String s) {
    // byte[] b = new byte[s.length() / 2];
    // for (int i = 0; i < b.length; i++) {
    // int index = i * 2;
    // int v = Integer.parseInt(s.substring(index, index + 2), 16);
    // b[i] = (byte) v;
    // }
    // return b;
    // }

    public static void main(String[] args) throws Exception {
        String content = "123456";
        String password = "00000000-6bd5-5c32-111b-f6a2330e426d";

        // content = HashHelper.getSHA1(HashHelper.getSHA1(content) + password);
        // System.out.println(content);
        // content = AESEncryptHelper.encrypt(content, password);
        // System.out.println(content);
        content = "HiV4RGLChKroe9BGClS652Ps9c6tVQBvrEuosDNz0VuSE21Qxy62+aH+D7NFUTe2";
        System.out.println(AESEncryptHelper.decrypt(content, password));
    }
}
