package net.minecraft.network;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
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
         KeyGenerator _snowman = KeyGenerator.getInstance("AES");
         _snowman.init(128);
         return _snowman.generateKey();
      } catch (Exception var1) {
         throw new NetworkEncryptionException(var1);
      }
   }

   public static KeyPair generateServerKeyPair() throws NetworkEncryptionException {
      try {
         KeyPairGenerator _snowman = KeyPairGenerator.getInstance("RSA");
         _snowman.initialize(1024);
         return _snowman.generateKeyPair();
      } catch (Exception var1) {
         throw new NetworkEncryptionException(var1);
      }
   }

   public static byte[] generateServerId(String baseServerId, PublicKey publicKey, SecretKey secretKey) throws NetworkEncryptionException {
      try {
         return hash(baseServerId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
      } catch (Exception var4) {
         throw new NetworkEncryptionException(var4);
      }
   }

   private static byte[] hash(byte[]... _snowman) throws Exception {
      MessageDigest _snowmanx = MessageDigest.getInstance("SHA-1");

      for (byte[] _snowmanxx : _snowman) {
         _snowmanx.update(_snowmanxx);
      }

      return _snowmanx.digest();
   }

   public static PublicKey readEncodedPublicKey(byte[] _snowman) throws NetworkEncryptionException {
      try {
         EncodedKeySpec _snowmanx = new X509EncodedKeySpec(_snowman);
         KeyFactory _snowmanxx = KeyFactory.getInstance("RSA");
         return _snowmanxx.generatePublic(_snowmanx);
      } catch (Exception var3) {
         throw new NetworkEncryptionException(var3);
      }
   }

   public static SecretKey decryptSecretKey(PrivateKey privateKey, byte[] encryptedSecretKey) throws NetworkEncryptionException {
      byte[] _snowman = decrypt(privateKey, encryptedSecretKey);

      try {
         return new SecretKeySpec(_snowman, "AES");
      } catch (Exception var4) {
         throw new NetworkEncryptionException(var4);
      }
   }

   public static byte[] encrypt(Key key, byte[] data) throws NetworkEncryptionException {
      return crypt(1, key, data);
   }

   public static byte[] decrypt(Key key, byte[] data) throws NetworkEncryptionException {
      return crypt(2, key, data);
   }

   private static byte[] crypt(int opMode, Key key, byte[] data) throws NetworkEncryptionException {
      try {
         return crypt(opMode, key.getAlgorithm(), key).doFinal(data);
      } catch (Exception var4) {
         throw new NetworkEncryptionException(var4);
      }
   }

   private static Cipher crypt(int opMode, String algorithm, Key key) throws Exception {
      Cipher _snowman = Cipher.getInstance(algorithm);
      _snowman.init(opMode, key);
      return _snowman;
   }

   public static Cipher cipherFromKey(int opMode, Key key) throws NetworkEncryptionException {
      try {
         Cipher _snowman = Cipher.getInstance("AES/CFB8/NoPadding");
         _snowman.init(opMode, key, new IvParameterSpec(key.getEncoded()));
         return _snowman;
      } catch (Exception var3) {
         throw new NetworkEncryptionException(var3);
      }
   }
}
