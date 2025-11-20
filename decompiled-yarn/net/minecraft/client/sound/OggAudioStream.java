package net.minecraft.client.sound;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class OggAudioStream implements AudioStream {
   private long pointer;
   private final AudioFormat format;
   private final InputStream inputStream;
   private ByteBuffer buffer = MemoryUtil.memAlloc(8192);

   public OggAudioStream(InputStream inputStream) throws IOException {
      this.inputStream = inputStream;
      ((Buffer)this.buffer).limit(0);
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var3 = null;

      try {
         IntBuffer _snowmanx = _snowman.mallocInt(1);
         IntBuffer _snowmanxx = _snowman.mallocInt(1);

         while (this.pointer == 0L) {
            if (!this.readHeader()) {
               throw new IOException("Failed to find Ogg header");
            }

            int _snowmanxxx = this.buffer.position();
            ((Buffer)this.buffer).position(0);
            this.pointer = STBVorbis.stb_vorbis_open_pushdata(this.buffer, _snowmanx, _snowmanxx, null);
            ((Buffer)this.buffer).position(_snowmanxxx);
            int _snowmanxxxx = _snowmanxx.get(0);
            if (_snowmanxxxx == 1) {
               this.increaseBufferSize();
            } else if (_snowmanxxxx != 0) {
               throw new IOException("Failed to read Ogg file " + _snowmanxxxx);
            }
         }

         ((Buffer)this.buffer).position(this.buffer.position() + _snowmanx.get(0));
         STBVorbisInfo _snowmanxxx = STBVorbisInfo.mallocStack(_snowman);
         STBVorbis.stb_vorbis_get_info(this.pointer, _snowmanxxx);
         this.format = new AudioFormat((float)_snowmanxxx.sample_rate(), 16, _snowmanxxx.channels(), true, false);
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

   private boolean readHeader() throws IOException {
      int _snowman = this.buffer.limit();
      int _snowmanx = this.buffer.capacity() - _snowman;
      if (_snowmanx == 0) {
         return true;
      } else {
         byte[] _snowmanxx = new byte[_snowmanx];
         int _snowmanxxx = this.inputStream.read(_snowmanxx);
         if (_snowmanxxx == -1) {
            return false;
         } else {
            int _snowmanxxxx = this.buffer.position();
            ((Buffer)this.buffer).limit(_snowman + _snowmanxxx);
            ((Buffer)this.buffer).position(_snowman);
            this.buffer.put(_snowmanxx, 0, _snowmanxxx);
            ((Buffer)this.buffer).position(_snowmanxxxx);
            return true;
         }
      }
   }

   private void increaseBufferSize() {
      boolean _snowman = this.buffer.position() == 0;
      boolean _snowmanx = this.buffer.position() == this.buffer.limit();
      if (_snowmanx && !_snowman) {
         ((Buffer)this.buffer).position(0);
         ((Buffer)this.buffer).limit(0);
      } else {
         ByteBuffer _snowmanxx = MemoryUtil.memAlloc(_snowman ? 2 * this.buffer.capacity() : this.buffer.capacity());
         _snowmanxx.put(this.buffer);
         MemoryUtil.memFree(this.buffer);
         ((Buffer)_snowmanxx).flip();
         this.buffer = _snowmanxx;
      }
   }

   private boolean readOggFile(OggAudioStream.ChannelList _snowman) throws IOException {
      if (this.pointer == 0L) {
         return false;
      } else {
         MemoryStack _snowmanx = MemoryStack.stackPush();
         Throwable var3 = null;

         try {
            PointerBuffer _snowmanxx = _snowmanx.mallocPointer(1);
            IntBuffer _snowmanxxx = _snowmanx.mallocInt(1);
            IntBuffer _snowmanxxxx = _snowmanx.mallocInt(1);

            while (true) {
               int _snowmanxxxxx = STBVorbis.stb_vorbis_decode_frame_pushdata(this.pointer, this.buffer, _snowmanxxx, _snowmanxx, _snowmanxxxx);
               ((Buffer)this.buffer).position(this.buffer.position() + _snowmanxxxxx);
               int _snowmanxxxxxx = STBVorbis.stb_vorbis_get_error(this.pointer);
               if (_snowmanxxxxxx == 1) {
                  this.increaseBufferSize();
                  if (!this.readHeader()) {
                     return false;
                  }
               } else {
                  if (_snowmanxxxxxx != 0) {
                     throw new IOException("Failed to read Ogg file " + _snowmanxxxxxx);
                  }

                  int _snowmanxxxxxxx = _snowmanxxxx.get(0);
                  if (_snowmanxxxxxxx != 0) {
                     int _snowmanxxxxxxxx = _snowmanxxx.get(0);
                     PointerBuffer _snowmanxxxxxxxxx = _snowmanxx.getPointerBuffer(_snowmanxxxxxxxx);
                     if (_snowmanxxxxxxxx == 1) {
                        this.readChannels(_snowmanxxxxxxxxx.getFloatBuffer(0, _snowmanxxxxxxx), _snowman);
                        return true;
                     }

                     if (_snowmanxxxxxxxx == 2) {
                        this.readChannels(_snowmanxxxxxxxxx.getFloatBuffer(0, _snowmanxxxxxxx), _snowmanxxxxxxxxx.getFloatBuffer(1, _snowmanxxxxxxx), _snowman);
                        return true;
                     }

                     throw new IllegalStateException("Invalid number of channels: " + _snowmanxxxxxxxx);
                  }
               }
            }
         } catch (Throwable var23) {
            var3 = var23;
            throw var23;
         } finally {
            if (_snowmanx != null) {
               if (var3 != null) {
                  try {
                     _snowmanx.close();
                  } catch (Throwable var22) {
                     var3.addSuppressed(var22);
                  }
               } else {
                  _snowmanx.close();
               }
            }
         }
      }
   }

   private void readChannels(FloatBuffer _snowman, OggAudioStream.ChannelList _snowman) {
      while (_snowman.hasRemaining()) {
         _snowman.addChannel(_snowman.get());
      }
   }

   private void readChannels(FloatBuffer _snowman, FloatBuffer _snowman, OggAudioStream.ChannelList _snowman) {
      while (_snowman.hasRemaining() && _snowman.hasRemaining()) {
         _snowman.addChannel(_snowman.get());
         _snowman.addChannel(_snowman.get());
      }
   }

   @Override
   public void close() throws IOException {
      if (this.pointer != 0L) {
         STBVorbis.stb_vorbis_close(this.pointer);
         this.pointer = 0L;
      }

      MemoryUtil.memFree(this.buffer);
      this.inputStream.close();
   }

   @Override
   public AudioFormat getFormat() {
      return this.format;
   }

   @Override
   public ByteBuffer getBuffer(int size) throws IOException {
      OggAudioStream.ChannelList _snowman = new OggAudioStream.ChannelList(size + 8192);

      while (this.readOggFile(_snowman) && _snowman.currentBufferSize < size) {
      }

      return _snowman.getBuffer();
   }

   public ByteBuffer getBuffer() throws IOException {
      OggAudioStream.ChannelList _snowman = new OggAudioStream.ChannelList(16384);

      while (this.readOggFile(_snowman)) {
      }

      return _snowman.getBuffer();
   }

   static class ChannelList {
      private final List<ByteBuffer> buffers = Lists.newArrayList();
      private final int size;
      private int currentBufferSize;
      private ByteBuffer buffer;

      public ChannelList(int size) {
         this.size = size + 1 & -2;
         this.init();
      }

      private void init() {
         this.buffer = BufferUtils.createByteBuffer(this.size);
      }

      public void addChannel(float _snowman) {
         if (this.buffer.remaining() == 0) {
            ((Buffer)this.buffer).flip();
            this.buffers.add(this.buffer);
            this.init();
         }

         int _snowmanx = MathHelper.clamp((int)(_snowman * 32767.5F - 0.5F), -32768, 32767);
         this.buffer.putShort((short)_snowmanx);
         this.currentBufferSize += 2;
      }

      public ByteBuffer getBuffer() {
         ((Buffer)this.buffer).flip();
         if (this.buffers.isEmpty()) {
            return this.buffer;
         } else {
            ByteBuffer _snowman = BufferUtils.createByteBuffer(this.currentBufferSize);
            this.buffers.forEach(_snowman::put);
            _snowman.put(this.buffer);
            ((Buffer)_snowman).flip();
            return _snowman;
         }
      }
   }
}
