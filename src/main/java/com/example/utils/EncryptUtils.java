package com.example.utils;

import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

@Slf4j
public class EncryptUtils {
    private static final int MAX_ENCRYPT_BLOCK = 117;

    private static final int MAX_DECRYPT_BLOCK = 256;

    private static final String AES_GCM_NOPADDING = "AES/GCM/NoPadding";

    public static byte[] aesEncrypt(byte[] contentByte, byte[] key) {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[12];
        // do not reuse iv with the same key
        secureRandom.nextBytes(iv);

        try {
            Cipher cipher = Cipher.getInstance(AES_GCM_NOPADDING);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, parameterSpec);
            byte[] encrypted = cipher.doFinal(contentByte);

            ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + encrypted.length);
            byteBuffer.putInt(iv.length);
            byteBuffer.put(iv);
            byteBuffer.put(encrypted);

            byte[] cipherMessage = byteBuffer.array();
            //return Base64.getEncoder().encodeToString(cipherMessage);
            return cipherMessage;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException e) {
            log.error("Encryption error: {}", e);
            throw new RuntimeException(e);
        }
    }

    public static String aesEncrypt(String content, String key) {

        if(content == null || key == null){
            return null;
        }
        if("".equals(content)){
            return "";
        }

        return Base64.getEncoder().encodeToString(
                aesEncrypt(content.getBytes(StandardCharsets.UTF_8), key.getBytes()));

    }

    public static byte[] aesDecrypt(byte[] content, byte[] key) {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        ByteBuffer byteBuffer = ByteBuffer.wrap(content);
        int ivLength = byteBuffer.getInt();
        if (ivLength != 12) {
            throw new IllegalArgumentException("invalid iv length");
        }

        try {
            byte[] iv = new byte[ivLength];
            byteBuffer.get(iv);
            byte[] cipherText = new byte[byteBuffer.remaining()];
            byteBuffer.get(cipherText);
            Cipher cipher = Cipher.getInstance(AES_GCM_NOPADDING);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, parameterSpec);
            byte[] original = cipher.doFinal(cipherText);
            //return new String(original);
            return original;
        } catch (Exception e) {
            log.error("Decryption error: {}", e);
            throw new RuntimeException(e);
        }
    }

    public static String aesDecrypt(String content, String key) {
        if(content == null || key == null){
            return null;
        }
        if("".equals(content)){
            return "";
        }

        return new String(aesDecrypt(Base64.getDecoder().decode(content), key.getBytes()));

    }
/*
    public static void main(String[] args) throws Exception {
        int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");
        System.out.println("MaxAllowedKeyLength=[" + maxKeyLen + "].");
        // byte[] key = new byte[] { 'T', 'h', 'i', 's', 'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y' };
        byte[] key = "1234567890abcdef1234567890abcdef".getBytes("UTF-8");

        String content = "this is a screct";

        String encrypted = Base64.getEncoder().encodeToString(EncryptUtils.aesEncrypt(content.getBytes(), key));
        log.info("encrypted = " + encrypted);
        String decrypted = new String(EncryptUtils.aesDecrypt(Base64.getDecoder().decode(encrypted), key));
        log.info("decrypted = " + decrypted);

        InputStream in = new FileInputStream("D:\\TrialTwo/aa/eid-key.pem");

        byte[] privateKey = Base64.getDecoder()
                .decode(new String(ByteStreams.toByteArray(in)).replaceAll("\r\n", "")
                        .replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", ""));
        String data = "";

        EncryptUtils.rsaDecrypt(privateKey, Base64.getDecoder().decode(data));
    }
*/
    public static byte[] rsaEncrypt(byte[] publicKey, byte[] data) throws Exception {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    public static byte[] rsaDecrypt(byte[] privateKey, byte[] data) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        if (data.length == 257) {
            data = Arrays.copyOfRange(data, 1, data.length);
            inputLen = data.length;
        }

        byte[] cache;
        int i = 0;

        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    // mask data
    public static String maskData(String value) {
        return maskData(value, "*", 4);
    }

    public static String maskData(String value, int maskLength) {
        return maskData(value, "*", maskLength);
    }

    public static String maskData(String value, String maskChar, int maskLength) {
        if (StringUtils.isBlank(value))
            return value;

        String part1 = value.substring(0, maskLength);
        String part2 = value.substring(maskLength).replaceAll("[0-9A-Za-z]", maskChar);

        return part1 + part2;
    }
}