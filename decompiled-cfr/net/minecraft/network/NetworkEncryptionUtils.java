/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.network;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import net.minecraft.network.encryption.NetworkEncryptionException;

public class NetworkEncryptionUtils {
    public static SecretKey generateKey() throws NetworkEncryptionException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return keyGenerator.generateKey();
        }
        catch (Exception exception) {
            throw new NetworkEncryptionException(exception);
        }
    }

    public static KeyPair generateServerKeyPair() throws NetworkEncryptionException {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            return keyPairGenerator.generateKeyPair();
        }
        catch (Exception exception) {
            throw new NetworkEncryptionException(exception);
        }
    }

    public static byte[] generateServerId(String baseServerId, PublicKey publicKey, SecretKey secretKey) throws NetworkEncryptionException {
        try {
            return NetworkEncryptionUtils.hash(baseServerId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
        }
        catch (Exception exception) {
            throw new NetworkEncryptionException(exception);
        }
    }

    private static byte[] hash(byte[] ... byArray) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        for (byte[] byArray2 : byArray) {
            messageDigest.update(byArray2);
        }
        return messageDigest.digest();
    }

    public static PublicKey readEncodedPublicKey(byte[] byArray) throws NetworkEncryptionException {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byArray);
            KeyFactory _snowman2 = KeyFactory.getInstance("RSA");
            return _snowman2.generatePublic(x509EncodedKeySpec);
        }
        catch (Exception exception) {
            throw new NetworkEncryptionException(exception);
        }
    }

    public static SecretKey decryptSecretKey(PrivateKey privateKey, byte[] encryptedSecretKey) throws NetworkEncryptionException {
        byte[] byArray = NetworkEncryptionUtils.decrypt(privateKey, encryptedSecretKey);
        try {
            return new SecretKeySpec(byArray, "AES");
        }
        catch (Exception _snowman2) {
            throw new NetworkEncryptionException(_snowman2);
        }
    }

    public static byte[] encrypt(Key key, byte[] data) throws NetworkEncryptionException {
        return NetworkEncryptionUtils.crypt(1, key, data);
    }

    public static byte[] decrypt(Key key, byte[] data) throws NetworkEncryptionException {
        return NetworkEncryptionUtils.crypt(2, key, data);
    }

    private static byte[] crypt(int opMode, Key key, byte[] data) throws NetworkEncryptionException {
        try {
            return NetworkEncryptionUtils.crypt(opMode, key.getAlgorithm(), key).doFinal(data);
        }
        catch (Exception exception) {
            throw new NetworkEncryptionException(exception);
        }
    }

    private static Cipher crypt(int opMode, String algorithm, Key key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(opMode, key);
        return cipher;
    }

    public static Cipher cipherFromKey(int opMode, Key key) throws NetworkEncryptionException {
        try {
            Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
            cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
            return cipher;
        }
        catch (Exception exception) {
            throw new NetworkEncryptionException(exception);
        }
    }
}

