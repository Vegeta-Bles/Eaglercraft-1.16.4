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

public class aeu {
   public static SecretKey a() throws aev {
      try {
         KeyGenerator _snowman = KeyGenerator.getInstance("AES");
         _snowman.init(128);
         return _snowman.generateKey();
      } catch (Exception var1) {
         throw new aev(var1);
      }
   }

   public static KeyPair b() throws aev {
      try {
         KeyPairGenerator _snowman = KeyPairGenerator.getInstance("RSA");
         _snowman.initialize(1024);
         return _snowman.generateKeyPair();
      } catch (Exception var1) {
         throw new aev(var1);
      }
   }

   public static byte[] a(String var0, PublicKey var1, SecretKey var2) throws aev {
      try {
         return a(_snowman.getBytes("ISO_8859_1"), _snowman.getEncoded(), _snowman.getEncoded());
      } catch (Exception var4) {
         throw new aev(var4);
      }
   }

   private static byte[] a(byte[]... var0) throws Exception {
      MessageDigest _snowman = MessageDigest.getInstance("SHA-1");

      for (byte[] _snowmanx : _snowman) {
         _snowman.update(_snowmanx);
      }

      return _snowman.digest();
   }

   public static PublicKey a(byte[] var0) throws aev {
      try {
         EncodedKeySpec _snowman = new X509EncodedKeySpec(_snowman);
         KeyFactory _snowmanx = KeyFactory.getInstance("RSA");
         return _snowmanx.generatePublic(_snowman);
      } catch (Exception var3) {
         throw new aev(var3);
      }
   }

   public static SecretKey a(PrivateKey var0, byte[] var1) throws aev {
      byte[] _snowman = b(_snowman, _snowman);

      try {
         return new SecretKeySpec(_snowman, "AES");
      } catch (Exception var4) {
         throw new aev(var4);
      }
   }

   public static byte[] a(Key var0, byte[] var1) throws aev {
      return a(1, _snowman, _snowman);
   }

   public static byte[] b(Key var0, byte[] var1) throws aev {
      return a(2, _snowman, _snowman);
   }

   private static byte[] a(int var0, Key var1, byte[] var2) throws aev {
      try {
         return a(_snowman, _snowman.getAlgorithm(), _snowman).doFinal(_snowman);
      } catch (Exception var4) {
         throw new aev(var4);
      }
   }

   private static Cipher a(int var0, String var1, Key var2) throws Exception {
      Cipher _snowman = Cipher.getInstance(_snowman);
      _snowman.init(_snowman, _snowman);
      return _snowman;
   }

   public static Cipher a(int var0, Key var1) throws aev {
      try {
         Cipher _snowman = Cipher.getInstance("AES/CFB8/NoPadding");
         _snowman.init(_snowman, _snowman, new IvParameterSpec(_snowman.getEncoded()));
         return _snowman;
      } catch (Exception var3) {
         throw new aev(var3);
      }
   }
}
