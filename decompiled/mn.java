import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nullable;

public class mn {
   public static md a(File var0) throws IOException {
      md var3;
      try (InputStream _snowman = new FileInputStream(_snowman)) {
         var3 = a(_snowman);
      }

      return var3;
   }

   public static md a(InputStream var0) throws IOException {
      md var3;
      try (DataInputStream _snowman = new DataInputStream(new BufferedInputStream(new GZIPInputStream(_snowman)))) {
         var3 = a(_snowman, mm.a);
      }

      return var3;
   }

   public static void a(md var0, File var1) throws IOException {
      try (OutputStream _snowman = new FileOutputStream(_snowman)) {
         a(_snowman, _snowman);
      }
   }

   public static void a(md var0, OutputStream var1) throws IOException {
      try (DataOutputStream _snowman = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(_snowman)))) {
         a(_snowman, (DataOutput)_snowman);
      }
   }

   public static void b(md var0, File var1) throws IOException {
      try (
         FileOutputStream _snowman = new FileOutputStream(_snowman);
         DataOutputStream _snowmanx = new DataOutputStream(_snowman);
      ) {
         a(_snowman, (DataOutput)_snowmanx);
      }
   }

   @Nullable
   public static md b(File var0) throws IOException {
      if (!_snowman.exists()) {
         return null;
      } else {
         md var5;
         try (
            FileInputStream _snowman = new FileInputStream(_snowman);
            DataInputStream _snowmanx = new DataInputStream(_snowman);
         ) {
            var5 = a(_snowmanx, mm.a);
         }

         return var5;
      }
   }

   public static md a(DataInput var0) throws IOException {
      return a(_snowman, mm.a);
   }

   public static md a(DataInput var0, mm var1) throws IOException {
      mt _snowman = a(_snowman, 0, _snowman);
      if (_snowman instanceof md) {
         return (md)_snowman;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void a(md var0, DataOutput var1) throws IOException {
      a((mt)_snowman, _snowman);
   }

   private static void a(mt var0, DataOutput var1) throws IOException {
      _snowman.writeByte(_snowman.a());
      if (_snowman.a() != 0) {
         _snowman.writeUTF("");
         _snowman.a(_snowman);
      }
   }

   private static mt a(DataInput var0, int var1, mm var2) throws IOException {
      byte _snowman = _snowman.readByte();
      if (_snowman == 0) {
         return mf.b;
      } else {
         _snowman.readUTF();

         try {
            return mw.a(_snowman).b(_snowman, _snowman, _snowman);
         } catch (IOException var7) {
            l _snowmanx = l.a(var7, "Loading NBT data");
            m _snowmanxx = _snowmanx.a("NBT Tag");
            _snowmanxx.a("Tag type", _snowman);
            throw new u(_snowmanx);
         }
      }
   }
}
