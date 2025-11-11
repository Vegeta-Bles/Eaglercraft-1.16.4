import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import org.lwjgl.stb.STBIEOFCallback;
import org.lwjgl.stb.STBIIOCallbacks;
import org.lwjgl.stb.STBIReadCallback;
import org.lwjgl.stb.STBISkipCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class deu {
   public final int a;
   public final int b;

   public deu(String var1, InputStream var2) throws IOException {
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var4 = null;

      try (deu.a _snowmanx = a(_snowman)) {
         STBIReadCallback _snowmanxx = STBIReadCallback.create(_snowmanx::a);
         Throwable var8 = null;

         try {
            STBISkipCallback _snowmanxxx = STBISkipCallback.create(_snowmanx::a);
            Throwable var10 = null;

            try {
               STBIEOFCallback _snowmanxxxx = STBIEOFCallback.create(_snowmanx::a);
               Throwable var12 = null;

               try {
                  STBIIOCallbacks _snowmanxxxxx = STBIIOCallbacks.mallocStack(_snowman);
                  _snowmanxxxxx.read(_snowmanxx);
                  _snowmanxxxxx.skip(_snowmanxxx);
                  _snowmanxxxxx.eof(_snowmanxxxx);
                  IntBuffer _snowmanxxxxxx = _snowman.mallocInt(1);
                  IntBuffer _snowmanxxxxxxx = _snowman.mallocInt(1);
                  IntBuffer _snowmanxxxxxxxx = _snowman.mallocInt(1);
                  if (!STBImage.stbi_info_from_callbacks(_snowmanxxxxx, 0L, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx)) {
                     throw new IOException("Could not read info from the PNG file " + _snowman + " " + STBImage.stbi_failure_reason());
                  }

                  this.a = _snowmanxxxxxx.get(0);
                  this.b = _snowmanxxxxxxx.get(0);
               } catch (Throwable var122) {
                  var12 = var122;
                  throw var122;
               } finally {
                  if (_snowmanxxxx != null) {
                     if (var12 != null) {
                        try {
                           _snowmanxxxx.close();
                        } catch (Throwable var121) {
                           var12.addSuppressed(var121);
                        }
                     } else {
                        _snowmanxxxx.close();
                     }
                  }
               }
            } catch (Throwable var124) {
               var10 = var124;
               throw var124;
            } finally {
               if (_snowmanxxx != null) {
                  if (var10 != null) {
                     try {
                        _snowmanxxx.close();
                     } catch (Throwable var120) {
                        var10.addSuppressed(var120);
                     }
                  } else {
                     _snowmanxxx.close();
                  }
               }
            }
         } catch (Throwable var126) {
            var8 = var126;
            throw var126;
         } finally {
            if (_snowmanxx != null) {
               if (var8 != null) {
                  try {
                     _snowmanxx.close();
                  } catch (Throwable var119) {
                     var8.addSuppressed(var119);
                  }
               } else {
                  _snowmanxx.close();
               }
            }
         }
      } catch (Throwable var130) {
         var4 = var130;
         throw var130;
      } finally {
         if (_snowman != null) {
            if (var4 != null) {
               try {
                  _snowman.close();
               } catch (Throwable var117) {
                  var4.addSuppressed(var117);
               }
            } else {
               _snowman.close();
            }
         }
      }
   }

   private static deu.a a(InputStream var0) {
      return (deu.a)(_snowman instanceof FileInputStream ? new deu.c(((FileInputStream)_snowman).getChannel()) : new deu.b(Channels.newChannel(_snowman)));
   }

   abstract static class a implements AutoCloseable {
      protected boolean a;

      private a() {
      }

      int a(long var1, long var3, int var5) {
         try {
            return this.b(_snowman, _snowman);
         } catch (IOException var7) {
            this.a = true;
            return 0;
         }
      }

      void a(long var1, int var3) {
         try {
            this.a(_snowman);
         } catch (IOException var5) {
            this.a = true;
         }
      }

      int a(long var1) {
         return this.a ? 1 : 0;
      }

      protected abstract int b(long var1, int var3) throws IOException;

      protected abstract void a(int var1) throws IOException;

      @Override
      public abstract void close() throws IOException;
   }

   static class b extends deu.a {
      private final ReadableByteChannel b;
      private long c = MemoryUtil.nmemAlloc(128L);
      private int d = 128;
      private int e;
      private int f;

      private b(ReadableByteChannel var1) {
         this.b = _snowman;
      }

      private void b(int var1) throws IOException {
         ByteBuffer _snowman = MemoryUtil.memByteBuffer(this.c, this.d);
         if (_snowman + this.f > this.d) {
            this.d = _snowman + this.f;
            _snowman = MemoryUtil.memRealloc(_snowman, this.d);
            this.c = MemoryUtil.memAddress(_snowman);
         }

         ((Buffer)_snowman).position(this.e);

         while (_snowman + this.f > this.e) {
            try {
               int _snowmanx = this.b.read(_snowman);
               if (_snowmanx == -1) {
                  break;
               }
            } finally {
               this.e = _snowman.position();
            }
         }
      }

      @Override
      public int b(long var1, int var3) throws IOException {
         this.b(_snowman);
         if (_snowman + this.f > this.e) {
            _snowman = this.e - this.f;
         }

         MemoryUtil.memCopy(this.c + (long)this.f, _snowman, (long)_snowman);
         this.f += _snowman;
         return _snowman;
      }

      @Override
      public void a(int var1) throws IOException {
         if (_snowman > 0) {
            this.b(_snowman);
            if (_snowman + this.f > this.e) {
               throw new EOFException("Can't skip past the EOF.");
            }
         }

         if (this.f + _snowman < 0) {
            throw new IOException("Can't seek before the beginning: " + (this.f + _snowman));
         } else {
            this.f += _snowman;
         }
      }

      @Override
      public void close() throws IOException {
         MemoryUtil.nmemFree(this.c);
         this.b.close();
      }
   }

   static class c extends deu.a {
      private final SeekableByteChannel b;

      private c(SeekableByteChannel var1) {
         this.b = _snowman;
      }

      @Override
      public int b(long var1, int var3) throws IOException {
         ByteBuffer _snowman = MemoryUtil.memByteBuffer(_snowman, _snowman);
         return this.b.read(_snowman);
      }

      @Override
      public void a(int var1) throws IOException {
         this.b.position(this.b.position() + (long)_snowman);
      }

      @Override
      public int a(long var1) {
         return super.a(_snowman) != 0 && this.b.isOpen() ? 1 : 0;
      }

      @Override
      public void close() throws IOException {
         this.b.close();
      }
   }
}
