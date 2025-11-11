import com.google.common.annotations.VisibleForTesting;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cgy implements AutoCloseable {
   private static final Logger b = LogManager.getLogger();
   private static final ByteBuffer c = ByteBuffer.allocateDirect(1);
   private final FileChannel d;
   private final Path e;
   private final cha f;
   private final ByteBuffer g = ByteBuffer.allocateDirect(8192);
   private final IntBuffer h;
   private final IntBuffer i;
   @VisibleForTesting
   protected final cgx a = new cgx();

   public cgy(File var1, File var2, boolean var3) throws IOException {
      this(_snowman.toPath(), _snowman.toPath(), cha.b, _snowman);
   }

   public cgy(Path var1, Path var2, cha var3, boolean var4) throws IOException {
      this.f = _snowman;
      if (!Files.isDirectory(_snowman)) {
         throw new IllegalArgumentException("Expected directory, got " + _snowman.toAbsolutePath());
      } else {
         this.e = _snowman;
         this.h = this.g.asIntBuffer();
         ((Buffer)this.h).limit(1024);
         ((Buffer)this.g).position(4096);
         this.i = this.g.asIntBuffer();
         if (_snowman) {
            this.d = FileChannel.open(_snowman, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.DSYNC);
         } else {
            this.d = FileChannel.open(_snowman, StandardOpenOption.CREATE, StandardOpenOption.READ, StandardOpenOption.WRITE);
         }

         this.a.a(0, 2);
         ((Buffer)this.g).position(0);
         int _snowman = this.d.read(this.g, 0L);
         if (_snowman != -1) {
            if (_snowman != 8192) {
               b.warn("Region file {} has truncated header: {}", _snowman, _snowman);
            }

            long _snowmanx = Files.size(_snowman);

            for (int _snowmanxx = 0; _snowmanxx < 1024; _snowmanxx++) {
               int _snowmanxxx = this.h.get(_snowmanxx);
               if (_snowmanxxx != 0) {
                  int _snowmanxxxx = b(_snowmanxxx);
                  int _snowmanxxxxx = a(_snowmanxxx);
                  if (_snowmanxxxx < 2) {
                     b.warn("Region file {} has invalid sector at index: {}; sector {} overlaps with header", _snowman, _snowmanxx, _snowmanxxxx);
                     this.h.put(_snowmanxx, 0);
                  } else if (_snowmanxxxxx == 0) {
                     b.warn("Region file {} has an invalid sector at index: {}; size has to be > 0", _snowman, _snowmanxx);
                     this.h.put(_snowmanxx, 0);
                  } else if ((long)_snowmanxxxx * 4096L > _snowmanx) {
                     b.warn("Region file {} has an invalid sector at index: {}; sector {} is out of bounds", _snowman, _snowmanxx, _snowmanxxxx);
                     this.h.put(_snowmanxx, 0);
                  } else {
                     this.a.a(_snowmanxxxx, _snowmanxxxxx);
                  }
               }
            }
         }
      }
   }

   private Path e(brd var1) {
      String _snowman = "c." + _snowman.b + "." + _snowman.c + ".mcc";
      return this.e.resolve(_snowman);
   }

   @Nullable
   public synchronized DataInputStream a(brd var1) throws IOException {
      int _snowman = this.f(_snowman);
      if (_snowman == 0) {
         return null;
      } else {
         int _snowmanx = b(_snowman);
         int _snowmanxx = a(_snowman);
         int _snowmanxxx = _snowmanxx * 4096;
         ByteBuffer _snowmanxxxx = ByteBuffer.allocate(_snowmanxxx);
         this.d.read(_snowmanxxxx, (long)(_snowmanx * 4096));
         ((Buffer)_snowmanxxxx).flip();
         if (_snowmanxxxx.remaining() < 5) {
            b.error("Chunk {} header is truncated: expected {} but read {}", _snowman, _snowmanxxx, _snowmanxxxx.remaining());
            return null;
         } else {
            int _snowmanxxxxx = _snowmanxxxx.getInt();
            byte _snowmanxxxxxx = _snowmanxxxx.get();
            if (_snowmanxxxxx == 0) {
               b.warn("Chunk {} is allocated, but stream is missing", _snowman);
               return null;
            } else {
               int _snowmanxxxxxxx = _snowmanxxxxx - 1;
               if (a(_snowmanxxxxxx)) {
                  if (_snowmanxxxxxxx != 0) {
                     b.warn("Chunk has both internal and external streams");
                  }

                  return this.a(_snowman, b(_snowmanxxxxxx));
               } else if (_snowmanxxxxxxx > _snowmanxxxx.remaining()) {
                  b.error("Chunk {} stream is truncated: expected {} but read {}", _snowman, _snowmanxxxxxxx, _snowmanxxxx.remaining());
                  return null;
               } else if (_snowmanxxxxxxx < 0) {
                  b.error("Declared size {} of chunk {} is negative", _snowmanxxxxx, _snowman);
                  return null;
               } else {
                  return this.a(_snowman, _snowmanxxxxxx, a(_snowmanxxxx, _snowmanxxxxxxx));
               }
            }
         }
      }
   }

   private static boolean a(byte var0) {
      return (_snowman & 128) != 0;
   }

   private static byte b(byte var0) {
      return (byte)(_snowman & -129);
   }

   @Nullable
   private DataInputStream a(brd var1, byte var2, InputStream var3) throws IOException {
      cha _snowman = cha.a(_snowman);
      if (_snowman == null) {
         b.error("Chunk {} has invalid chunk stream version {}", _snowman, _snowman);
         return null;
      } else {
         return new DataInputStream(new BufferedInputStream(_snowman.a(_snowman)));
      }
   }

   @Nullable
   private DataInputStream a(brd var1, byte var2) throws IOException {
      Path _snowman = this.e(_snowman);
      if (!Files.isRegularFile(_snowman)) {
         b.error("External chunk path {} is not file", _snowman);
         return null;
      } else {
         return this.a(_snowman, _snowman, Files.newInputStream(_snowman));
      }
   }

   private static ByteArrayInputStream a(ByteBuffer var0, int var1) {
      return new ByteArrayInputStream(_snowman.array(), _snowman.position(), _snowman);
   }

   private int a(int var1, int var2) {
      return _snowman << 8 | _snowman;
   }

   private static int a(int var0) {
      return _snowman & 0xFF;
   }

   private static int b(int var0) {
      return _snowman >> 8 & 16777215;
   }

   private static int c(int var0) {
      return (_snowman + 4096 - 1) / 4096;
   }

   public boolean b(brd var1) {
      int _snowman = this.f(_snowman);
      if (_snowman == 0) {
         return false;
      } else {
         int _snowmanx = b(_snowman);
         int _snowmanxx = a(_snowman);
         ByteBuffer _snowmanxxx = ByteBuffer.allocate(5);

         try {
            this.d.read(_snowmanxxx, (long)(_snowmanx * 4096));
            ((Buffer)_snowmanxxx).flip();
            if (_snowmanxxx.remaining() != 5) {
               return false;
            } else {
               int _snowmanxxxx = _snowmanxxx.getInt();
               byte _snowmanxxxxx = _snowmanxxx.get();
               if (a(_snowmanxxxxx)) {
                  if (!cha.b(b(_snowmanxxxxx))) {
                     return false;
                  }

                  if (!Files.isRegularFile(this.e(_snowman))) {
                     return false;
                  }
               } else {
                  if (!cha.b(_snowmanxxxxx)) {
                     return false;
                  }

                  if (_snowmanxxxx == 0) {
                     return false;
                  }

                  int _snowmanxxxxxx = _snowmanxxxx - 1;
                  if (_snowmanxxxxxx < 0 || _snowmanxxxxxx > 4096 * _snowmanxx) {
                     return false;
                  }
               }

               return true;
            }
         } catch (IOException var9) {
            return false;
         }
      }
   }

   public DataOutputStream c(brd var1) throws IOException {
      return new DataOutputStream(new BufferedOutputStream(this.f.a(new cgy.a(_snowman))));
   }

   public void a() throws IOException {
      this.d.force(true);
   }

   protected synchronized void a(brd var1, ByteBuffer var2) throws IOException {
      int _snowman = g(_snowman);
      int _snowmanx = this.h.get(_snowman);
      int _snowmanxx = b(_snowmanx);
      int _snowmanxxx = a(_snowmanx);
      int _snowmanxxxx = _snowman.remaining();
      int _snowmanxxxxx = c(_snowmanxxxx);
      int _snowmanxxxxxx;
      cgy.b _snowmanxxxxxxx;
      if (_snowmanxxxxx >= 256) {
         Path _snowmanxxxxxxxx = this.e(_snowman);
         b.warn("Saving oversized chunk {} ({} bytes} to external file {}", _snowman, _snowmanxxxx, _snowmanxxxxxxxx);
         _snowmanxxxxx = 1;
         _snowmanxxxxxx = this.a.a(_snowmanxxxxx);
         _snowmanxxxxxxx = this.a(_snowmanxxxxxxxx, _snowman);
         ByteBuffer _snowmanxxxxxxxxx = this.b();
         this.d.write(_snowmanxxxxxxxxx, (long)(_snowmanxxxxxx * 4096));
      } else {
         _snowmanxxxxxx = this.a.a(_snowmanxxxxx);
         _snowmanxxxxxxx = () -> Files.deleteIfExists(this.e(_snowman));
         this.d.write(_snowman, (long)(_snowmanxxxxxx * 4096));
      }

      int _snowmanxxxxxxxx = (int)(x.d() / 1000L);
      this.h.put(_snowman, this.a(_snowmanxxxxxx, _snowmanxxxxx));
      this.i.put(_snowman, _snowmanxxxxxxxx);
      this.c();
      _snowmanxxxxxxx.run();
      if (_snowmanxx != 0) {
         this.a.b(_snowmanxx, _snowmanxxx);
      }
   }

   private ByteBuffer b() {
      ByteBuffer _snowman = ByteBuffer.allocate(5);
      _snowman.putInt(1);
      _snowman.put((byte)(this.f.a() | 128));
      ((Buffer)_snowman).flip();
      return _snowman;
   }

   private cgy.b a(Path var1, ByteBuffer var2) throws IOException {
      Path _snowman = Files.createTempFile(this.e, "tmp", null);

      try (FileChannel _snowmanx = FileChannel.open(_snowman, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
         ((Buffer)_snowman).position(5);
         _snowmanx.write(_snowman);
      }

      return () -> Files.move(_snowman, _snowman, StandardCopyOption.REPLACE_EXISTING);
   }

   private void c() throws IOException {
      ((Buffer)this.g).position(0);
      this.d.write(this.g, 0L);
   }

   private int f(brd var1) {
      return this.h.get(g(_snowman));
   }

   public boolean d(brd var1) {
      return this.f(_snowman) != 0;
   }

   private static int g(brd var0) {
      return _snowman.j() + _snowman.k() * 32;
   }

   @Override
   public void close() throws IOException {
      try {
         this.d();
      } finally {
         try {
            this.d.force(true);
         } finally {
            this.d.close();
         }
      }
   }

   private void d() throws IOException {
      int _snowman = (int)this.d.size();
      int _snowmanx = c(_snowman) * 4096;
      if (_snowman != _snowmanx) {
         ByteBuffer _snowmanxx = c.duplicate();
         ((Buffer)_snowmanxx).position(0);
         this.d.write(_snowmanxx, (long)(_snowmanx - 1));
      }
   }

   class a extends ByteArrayOutputStream {
      private final brd b;

      public a(brd var2) {
         super(8096);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(0);
         super.write(cgy.this.f.a());
         this.b = _snowman;
      }

      @Override
      public void close() throws IOException {
         ByteBuffer _snowman = ByteBuffer.wrap(this.buf, 0, this.count);
         _snowman.putInt(0, this.count - 5 + 1);
         cgy.this.a(this.b, _snowman);
      }
   }

   interface b {
      void run() throws IOException;
   }
}
