package com.changhong.sso.common;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Random;

/**
 * @Package : com.changhong.sso.common
 * @Author : Fayang Yuan
 * @Email : fayang.yuan@changhong.com ,flyyuanfayang@sina.com
 * @Date : 16/3/30
 * @Time : 上午10:25
 * @Description :
 */
public class DESedeCoder {

    static {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
    }

    private static final String MCRYPT_TRIPLEDES = "DESede";
    private static final String TRANSFORMATION = "DESede/CBC/PKCS5Padding";

    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(MCRYPT_TRIPLEDES);
        SecretKey sec = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec IvParameters = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, sec, IvParameters);
        return cipher.doFinal(data);
    }

    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        SecretKey sec = keyFactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec IvParameters = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, sec, IvParameters);
        return cipher.doFinal(data);
    }

    public static byte[] generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance(MCRYPT_TRIPLEDES);
        return keygen.generateKey().getEncoded();
    }

    public static byte[] randomIVBytes() {
        Random ran = new Random();
        byte[] bytes = new byte[8];
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) ran.nextInt(Byte.MAX_VALUE + 1);
        }
        return bytes;
    }

    public static void main(String args[]) throws Exception {
        String plainText = "a12*&1c中文";
        final byte[] secretBytes = DESedeCoder.generateSecretKey();

        final byte[] ivbytes = DESedeCoder.randomIVBytes();
        System.out.println("plain text: " + plainText);
        byte[] encrypt = DESedeCoder.encrypt(plainText.getBytes(), secretBytes, ivbytes);
        System.out.println("cipher text: " + encrypt);
        System.out.println("decrypt text: " + new String(DESedeCoder.decrypt(encrypt, secretBytes, ivbytes), "UTF-8"));
    }
}
