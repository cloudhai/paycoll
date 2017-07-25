package com.hai.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cloud on 2017/2/24.
 */
public class CryptionUtils {

    private static final String KEY_ALGORITHM = "RSA";

    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";

    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final String PUBKEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCf84cAmjnz6RRa97+GKlHFzqimMRlCDbY91glbKku5F8QXx0uiFYPElozAcOcxUz8Z/BttWNmxV5yAWxMvPsLxdAvDq8uiqHLrmXa38PWspChkm2TsHU4ct2z4RjsijIwfUy8Se2tVVkp93X/Tv1s1nrv9oYmCewrNIEOQ9LCs9wIDAQAB";

    private static final String PRIKEY="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ/zhwCaOfPpFFr3v4YqUcXOqKYxGUINtj3WCVsqS7kXxBfHS6IVg8SWjMBw5zFTPxn8G21Y2bFXnIBbEy8+wvF0C8Ory6KocuuZdrfw9aykKGSbZOwdThy3bPhGOyKMjB9TLxJ7a1VWSn3df9O/WzWeu/2hiYJ7Cs0gQ5D0sKz3AgMBAAECgYBkS4mNgSdxn+cZm1vGvuaDiDAL9aGG3540a+918LGGoDZfSqJMQf1b2aLsUF9HeJGKs6BnEl1+bBdADNuctlf5lzBH2E+oY2acasM4ooYB0P/lLYuPr2mUVWdQS6iUtzQmGkKMx4HSJBYVuZlfTIAdtysa5FdJ0OIJgQDbqIU2MQJBAPtPRZETfHiOoDGhGGwq8DXudzXjEW2usmplonR19xIRTBuyjWa4QlPZP2u8E2NiIn4rVveYzRMx4dYE0DJAnRUCQQCi78GEg4q5/wiA3glvsccwZ8OGRTt8/jG61YmbPjvjexp+VomgzMek6NEBIFSWb3LX8o4sI2EeKhBP+WA0TBzbAkAnN3igqgzLd15SQFRiVNUFZYAe3Z9ToWZgKhoO2HXE0QQyckuUkv5uvUEjW8cexCqjy7mXi7W84BTg5AFlRiX9AkBAqHIb+vNXQfyG3xXRjDPmYpb1to2X2Wu0n76eq4CqkhR+ZCrzc7AaIgWms8S9efaR+YAvxc5pYPneMrDZsAaXAkAZf8J3VYObso0MGUKMPD1sKTCxWhsVhpb59JV81asHWQEj3BeBHLPoKnZ4jZcH6REdhngmpR6EsC5iBzJbrFgZ";

    private static final Map<String, Object> keyMap = initKey();

    // AES加密密码 要求为16位值
    private final static String AES_KEY = "kaipai1416926535";

    public static String md5Encrypt(String srcStr) {
        return encrypt("MD5", srcStr);
    }

    public static String sha1Encrypt(String srcStr) {
        return encrypt("SHA-1", srcStr);
    }

    public static String sha256Encrypt(String srcStr) {
        return encrypt("SHA-256", srcStr);
    }

    public static String sha384Encrypt(String srcStr) {
        return encrypt("SHA-384", srcStr);
    }

    public static String sha512Encrypt(String srcStr) {
        return encrypt("SHA-512", srcStr);
    }


    public static String aesDecrypt(String sSrc) {
        String sKey = AES_KEY;
        try {
            // 判断Key是否正确
            if (sKey == null || sSrc == null || "".equals(sSrc)) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = hex2byte(sSrc);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static String aesEncrypt(String sSrc) {
        String sKey = AES_KEY;
        if (sKey == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (sKey.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        try {
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");// 根据给定的字符数组来构造一个密钥
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes());
            return byte2hex(encrypted).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

    private static byte[] hex2byte(String strhex) {
        if(StringUtils.isEmpty(strhex)){
            return null;
        }
        int length = strhex.length();
        if (length % 2 != 0) {
            return null;
        }
        byte[] b = new byte[length / 2];
        for (int i = 0; i != length / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    private static String encrypt(String algorithm, String srcStr) {
        try {
            StringBuilder result = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(srcStr.getBytes("utf-8"));
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1)
                    result.append("0");
                result.append(hex);
            }
            return result.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*#######################rsa###########################*/

    /**
     * 生成私钥加密数据签名
     * @param data 加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data,String privateKey) throws Exception{
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /**
     * 公钥加密验证
     * @param data 加密数据
     * @param publicKey 公钥
     * @param sign 签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }


    /**
     * 用户私钥解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(cipher.doFinal(data.getBytes()));
    }

    /**
     * 用户公钥解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return new String(cipher.doFinal(data.getBytes()));
    }

    /**
     * 公钥加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return new String(cipher.doFinal(data.getBytes()));
    }

    /**
     * 私钥加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(String data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return new String(cipher.doFinal(data.getBytes()));
    }

    /**
     * 取得私钥
     * @return
     * @throws Exception
     */
    public static String getPrivateKey()
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return encryptBASE64(key.getEncoded());
    }


    /**
     * 取得公钥
     * @return
     * @throws Exception
     */
    public static String getPublicKey()
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return encryptBASE64(key.getEncoded());
    }


    /**
     * 初始密钥
     * 如果有异常则用固定的密钥
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey()  {
        KeyPairGenerator keyPairGen;
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        try {
            keyPairGen = KeyPairGenerator
                    .getInstance(KEY_ALGORITHM);

            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);

            return keyMap;
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            keyMap.put(PUBLIC_KEY, PUBKEY);
            keyMap.put(PRIVATE_KEY, PRIKEY);
            return keyMap;
        }
    }

    /**
     * Base64加密
     * @param data
     * @return
     */
    private static String encryptBASE64(byte[] data){
        return new String(Base64.encodeBase64(data));
    }

    /**
     * base64解密
     * @param data
     * @return
     */
    private static byte[] decryptBASE64(String data){
        return Base64.decodeBase64(data);
    }

}
