package net.minecraft.client.sound;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;

public class Source {
   private static final Logger LOGGER = LogManager.getLogger();
   private final int pointer;
   private final AtomicBoolean playing = new AtomicBoolean(true);
   private int bufferSize = 16384;
   @Nullable
   private AudioStream stream;

   @Nullable
   static Source create() {
      int[] _snowman = new int[1];
      AL10.alGenSources(_snowman);
      return AlUtil.checkErrors("Allocate new source") ? null : new Source(_snowman[0]);
   }

   private Source(int pointer) {
      this.pointer = pointer;
   }

   public void close() {
      if (this.playing.compareAndSet(true, false)) {
         AL10.alSourceStop(this.pointer);
         AlUtil.checkErrors("Stop");
         if (this.stream != null) {
            try {
               this.stream.close();
            } catch (IOException var2) {
               LOGGER.error("Failed to close audio stream", var2);
            }

            this.removeProcessedBuffers();
            this.stream = null;
         }

         AL10.alDeleteSources(new int[]{this.pointer});
         AlUtil.checkErrors("Cleanup");
      }
   }

   public void play() {
      AL10.alSourcePlay(this.pointer);
   }

   private int getSourceState() {
      return !this.playing.get() ? 4116 : AL10.alGetSourcei(this.pointer, 4112);
   }

   public void pause() {
      if (this.getSourceState() == 4114) {
         AL10.alSourcePause(this.pointer);
      }
   }

   public void resume() {
      if (this.getSourceState() == 4115) {
         AL10.alSourcePlay(this.pointer);
      }
   }

   public void stop() {
      if (this.playing.get()) {
         AL10.alSourceStop(this.pointer);
         AlUtil.checkErrors("Stop");
      }
   }

   public boolean isStopped() {
      return this.getSourceState() == 4116;
   }

   public void setPosition(Vec3d _snowman) {
      AL10.alSourcefv(this.pointer, 4100, new float[]{(float)_snowman.x, (float)_snowman.y, (float)_snowman.z});
   }

   public void setPitch(float _snowman) {
      AL10.alSourcef(this.pointer, 4099, _snowman);
   }

   public void setLooping(boolean _snowman) {
      AL10.alSourcei(this.pointer, 4103, _snowman ? 1 : 0);
   }

   public void setVolume(float _snowman) {
      AL10.alSourcef(this.pointer, 4106, _snowman);
   }

   public void disableAttenuation() {
      AL10.alSourcei(this.pointer, 53248, 0);
   }

   public void setAttenuation(float _snowman) {
      AL10.alSourcei(this.pointer, 53248, 53251);
      AL10.alSourcef(this.pointer, 4131, _snowman);
      AL10.alSourcef(this.pointer, 4129, 1.0F);
      AL10.alSourcef(this.pointer, 4128, 0.0F);
   }

   public void setRelative(boolean _snowman) {
      AL10.alSourcei(this.pointer, 514, _snowman ? 1 : 0);
   }

   public void setBuffer(StaticSound _snowman) {
      _snowman.getStreamBufferPointer().ifPresent(_snowmanx -> AL10.alSourcei(this.pointer, 4105, _snowmanx));
   }

   public void setStream(AudioStream stream) {
      this.stream = stream;
      AudioFormat _snowman = stream.getFormat();
      this.bufferSize = getBufferSize(_snowman, 1);
      this.method_19640(4);
   }

   private static int getBufferSize(AudioFormat format, int time) {
      return (int)((float)(time * format.getSampleSizeInBits()) / 8.0F * (float)format.getChannels() * format.getSampleRate());
   }

   private void method_19640(int _snowman) {
      if (this.stream != null) {
         try {
            for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
               ByteBuffer _snowmanxx = this.stream.getBuffer(this.bufferSize);
               if (_snowmanxx != null) {
                  new StaticSound(_snowmanxx, this.stream.getFormat())
                     .takeStreamBufferPointer()
                     .ifPresent(_snowmanxxx -> AL10.alSourceQueueBuffers(this.pointer, new int[]{_snowmanxxx}));
               }
            }
         } catch (IOException var4) {
            LOGGER.error("Failed to read from audio stream", var4);
         }
      }
   }

   public void tick() {
      if (this.stream != null) {
         int _snowman = this.removeProcessedBuffers();
         this.method_19640(_snowman);
      }
   }

   private int removeProcessedBuffers() {
      int _snowman = AL10.alGetSourcei(this.pointer, 4118);
      if (_snowman > 0) {
         int[] _snowmanx = new int[_snowman];
         AL10.alSourceUnqueueBuffers(this.pointer, _snowmanx);
         AlUtil.checkErrors("Unqueue buffers");
         AL10.alDeleteBuffers(_snowmanx);
         AlUtil.checkErrors("Remove processed buffers");
      }

      return _snowman;
   }
}
