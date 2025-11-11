import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class ddx implements enm {
   private long a;
   private final AudioFormat b;
   private final InputStream c;
   private ByteBuffer d = MemoryUtil.memAlloc(8192);

   public ddx(InputStream var1) throws IOException {
      this.c = _snowman;
      ((Buffer)this.d).limit(0);
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var3 = null;

      try {
         IntBuffer _snowmanx = _snowman.mallocInt(1);
         IntBuffer _snowmanxx = _snowman.mallocInt(1);

         while (this.a == 0L) {
            if (!this.c()) {
               throw new IOException("Failed to find Ogg header");
            }

            int _snowmanxxx = this.d.position();
            ((Buffer)this.d).position(0);
            this.a = STBVorbis.stb_vorbis_open_pushdata(this.d, _snowmanx, _snowmanxx, null);
            ((Buffer)this.d).position(_snowmanxxx);
            int _snowmanxxxx = _snowmanxx.get(0);
            if (_snowmanxxxx == 1) {
               this.d();
            } else if (_snowmanxxxx != 0) {
               throw new IOException("Failed to read Ogg file " + _snowmanxxxx);
            }
         }

         ((Buffer)this.d).position(this.d.position() + _snowmanx.get(0));
         STBVorbisInfo _snowmanxxx = STBVorbisInfo.mallocStack(_snowman);
         STBVorbis.stb_vorbis_get_info(this.a, _snowmanxxx);
         this.b = new AudioFormat((float)_snowmanxxx.sample_rate(), 16, _snowmanxxx.channels(), true, false);
      } catch (Throwable var15) {
         var3 = var15;
         throw var15;
      } finally {
         if (_snowman != null) {
            if (var3 != null) {
               try {
                  _snowman.close();
               } catch (Throwable var14) {
                  var3.addSuppressed(var14);
               }
            } else {
               _snowman.close();
            }
         }
      }
   }

   private boolean c() throws IOException {
      int _snowman = this.d.limit();
      int _snowmanx = this.d.capacity() - _snowman;
      if (_snowmanx == 0) {
         return true;
      } else {
         byte[] _snowmanxx = new byte[_snowmanx];
         int _snowmanxxx = this.c.read(_snowmanxx);
         if (_snowmanxxx == -1) {
            return false;
         } else {
            int _snowmanxxxx = this.d.position();
            ((Buffer)this.d).limit(_snowman + _snowmanxxx);
            ((Buffer)this.d).position(_snowman);
            this.d.put(_snowmanxx, 0, _snowmanxxx);
            ((Buffer)this.d).position(_snowmanxxxx);
            return true;
         }
      }
   }

   private void d() {
      boolean _snowman = this.d.position() == 0;
      boolean _snowmanx = this.d.position() == this.d.limit();
      if (_snowmanx && !_snowman) {
         ((Buffer)this.d).position(0);
         ((Buffer)this.d).limit(0);
      } else {
         ByteBuffer _snowmanxx = MemoryUtil.memAlloc(_snowman ? 2 * this.d.capacity() : this.d.capacity());
         _snowmanxx.put(this.d);
         MemoryUtil.memFree(this.d);
         ((Buffer)_snowmanxx).flip();
         this.d = _snowmanxx;
      }
   }

   private boolean a(ddx.a var1) throws IOException {
      if (this.a == 0L) {
         return false;
      } else {
         MemoryStack _snowman = MemoryStack.stackPush();
         Throwable var3 = null;

         try {
            PointerBuffer _snowmanx = _snowman.mallocPointer(1);
            IntBuffer _snowmanxx = _snowman.mallocInt(1);
            IntBuffer _snowmanxxx = _snowman.mallocInt(1);

            while (true) {
               int _snowmanxxxx = STBVorbis.stb_vorbis_decode_frame_pushdata(this.a, this.d, _snowmanxx, _snowmanx, _snowmanxxx);
               ((Buffer)this.d).position(this.d.position() + _snowmanxxxx);
               int _snowmanxxxxx = STBVorbis.stb_vorbis_get_error(this.a);
               if (_snowmanxxxxx == 1) {
                  this.d();
                  if (!this.c()) {
                     return false;
                  }
               } else {
                  if (_snowmanxxxxx != 0) {
                     throw new IOException("Failed to read Ogg file " + _snowmanxxxxx);
                  }

                  int _snowmanxxxxxx = _snowmanxxx.get(0);
                  if (_snowmanxxxxxx != 0) {
                     int _snowmanxxxxxxx = _snowmanxx.get(0);
                     PointerBuffer _snowmanxxxxxxxx = _snowmanx.getPointerBuffer(_snowmanxxxxxxx);
                     if (_snowmanxxxxxxx == 1) {
                        this.a(_snowmanxxxxxxxx.getFloatBuffer(0, _snowmanxxxxxx), _snowman);
                        return true;
                     }

                     if (_snowmanxxxxxxx == 2) {
                        this.a(_snowmanxxxxxxxx.getFloatBuffer(0, _snowmanxxxxxx), _snowmanxxxxxxxx.getFloatBuffer(1, _snowmanxxxxxx), _snowman);
                        return true;
                     }

                     throw new IllegalStateException("Invalid number of channels: " + _snowmanxxxxxxx);
                  }
               }
            }
         } catch (Throwable var23) {
            var3 = var23;
            throw var23;
         } finally {
            if (_snowman != null) {
               if (var3 != null) {
                  try {
                     _snowman.close();
                  } catch (Throwable var22) {
                     var3.addSuppressed(var22);
                  }
               } else {
                  _snowman.close();
               }
            }
         }
      }
   }

   private void a(FloatBuffer var1, ddx.a var2) {
      while (_snowman.hasRemaining()) {
         _snowman.a(_snowman.get());
      }
   }

   private void a(FloatBuffer var1, FloatBuffer var2, ddx.a var3) {
      while (_snowman.hasRemaining() && _snowman.hasRemaining()) {
         _snowman.a(_snowman.get());
         _snowman.a(_snowman.get());
      }
   }

   @Override
   public void close() throws IOException {
      if (this.a != 0L) {
         STBVorbis.stb_vorbis_close(this.a);
         this.a = 0L;
      }

      MemoryUtil.memFree(this.d);
      this.c.close();
   }

   @Override
   public AudioFormat a() {
      return this.b;
   }

   @Override
   public ByteBuffer a(int var1) throws IOException {
      ddx.a _snowman = new ddx.a(_snowman + 8192);

      while (this.a(_snowman) && _snowman.c < _snowman) {
      }

      return _snowman.a();
   }

   public ByteBuffer b() throws IOException {
      ddx.a _snowman = new ddx.a(16384);

      while (this.a(_snowman)) {
      }

      return _snowman.a();
   }

   static class a {
      private final List<ByteBuffer> a = Lists.newArrayList();
      private final int b;
      private int c;
      private ByteBuffer d;

      public a(int var1) {
         this.b = _snowman + 1 & -2;
         this.b();
      }

      private void b() {
         this.d = BufferUtils.createByteBuffer(this.b);
      }

      public void a(float var1) {
         if (this.d.remaining() == 0) {
            ((Buffer)this.d).flip();
            this.a.add(this.d);
            this.b();
         }

         int _snowman = afm.a((int)(_snowman * 32767.5F - 0.5F), -32768, 32767);
         this.d.putShort((short)_snowman);
         this.c += 2;
      }

      public ByteBuffer a() {
         ((Buffer)this.d).flip();
         if (this.a.isEmpty()) {
            return this.d;
         } else {
            ByteBuffer _snowman = BufferUtils.createByteBuffer(this.c);
            this.a.forEach(_snowman::put);
            _snowman.put(this.d);
            ((Buffer)_snowman).flip();
            return _snowman;
         }
      }
   }
}
