import com.google.common.base.Charsets;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class aex implements AutoCloseable {
   private final FileChannel a;
   private final FileLock b;
   private static final ByteBuffer c;

   public static aex a(Path var0) throws IOException {
      Path _snowman = _snowman.resolve("session.lock");
      if (!Files.isDirectory(_snowman)) {
         Files.createDirectories(_snowman);
      }

      FileChannel _snowmanx = FileChannel.open(_snowman, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

      try {
         _snowmanx.write(c.duplicate());
         _snowmanx.force(true);
         FileLock _snowmanxx = _snowmanx.tryLock();
         if (_snowmanxx == null) {
            throw aex.a.a(_snowman);
         } else {
            return new aex(_snowmanx, _snowmanxx);
         }
      } catch (IOException var6) {
         try {
            _snowmanx.close();
         } catch (IOException var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }
   }

   private aex(FileChannel var1, FileLock var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void close() throws IOException {
      try {
         if (this.b.isValid()) {
            this.b.release();
         }
      } finally {
         if (this.a.isOpen()) {
            this.a.close();
         }
      }
   }

   public boolean a() {
      return this.b.isValid();
   }

   public static boolean b(Path var0) throws IOException {
      Path _snowman = _snowman.resolve("session.lock");

      try (
         FileChannel _snowmanx = FileChannel.open(_snowman, StandardOpenOption.WRITE);
         FileLock _snowmanxx = _snowmanx.tryLock();
      ) {
         return _snowmanxx == null;
      } catch (AccessDeniedException var37) {
         return true;
      } catch (NoSuchFileException var38) {
         return false;
      }
   }

   static {
      byte[] _snowman = "_snowman".getBytes(Charsets.UTF_8);
      c = ByteBuffer.allocateDirect(_snowman.length);
      c.put(_snowman);
      ((Buffer)c).flip();
   }

   public static class a extends IOException {
      private a(Path var1, String var2) {
         super(_snowman.toAbsolutePath() + ": " + _snowman);
      }

      public static aex.a a(Path var0) {
         return new aex.a(_snowman, "already locked (possibly by other Minecraft instance?)");
      }
   }
}
