package net.minecraft.client.sound;

import com.google.common.collect.Sets;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryStack;

public class SoundEngine {
   private static final Logger LOGGER = LogManager.getLogger();
   private long devicePointer;
   private long contextPointer;
   private static final SoundEngine.SourceSet EMPTY_SOURCE_SET = new SoundEngine.SourceSet() {
      @Nullable
      @Override
      public Source createSource() {
         return null;
      }

      @Override
      public boolean release(Source _snowman) {
         return false;
      }

      @Override
      public void close() {
      }

      @Override
      public int getMaxSourceCount() {
         return 0;
      }

      @Override
      public int getSourceCount() {
         return 0;
      }
   };
   private SoundEngine.SourceSet streamingSources = EMPTY_SOURCE_SET;
   private SoundEngine.SourceSet staticSources = EMPTY_SOURCE_SET;
   private final SoundListener listener = new SoundListener();

   public SoundEngine() {
   }

   public void init() {
      this.devicePointer = openDevice();
      ALCCapabilities _snowman = ALC.createCapabilities(this.devicePointer);
      if (AlUtil.checkAlcErrors(this.devicePointer, "Get capabilities")) {
         throw new IllegalStateException("Failed to get OpenAL capabilities");
      } else if (!_snowman.OpenALC11) {
         throw new IllegalStateException("OpenAL 1.1 not supported");
      } else {
         this.contextPointer = ALC10.alcCreateContext(this.devicePointer, (IntBuffer)null);
         ALC10.alcMakeContextCurrent(this.contextPointer);
         int _snowmanx = this.getMonoSourceCount();
         int _snowmanxx = MathHelper.clamp((int)MathHelper.sqrt((float)_snowmanx), 2, 8);
         int _snowmanxxx = MathHelper.clamp(_snowmanx - _snowmanxx, 8, 255);
         this.streamingSources = new SoundEngine.SourceSetImpl(_snowmanxxx);
         this.staticSources = new SoundEngine.SourceSetImpl(_snowmanxx);
         ALCapabilities _snowmanxxxx = AL.createCapabilities(_snowman);
         AlUtil.checkErrors("Initialization");
         if (!_snowmanxxxx.AL_EXT_source_distance_model) {
            throw new IllegalStateException("AL_EXT_source_distance_model is not supported");
         } else {
            AL10.alEnable(512);
            if (!_snowmanxxxx.AL_EXT_LINEAR_DISTANCE) {
               throw new IllegalStateException("AL_EXT_LINEAR_DISTANCE is not supported");
            } else {
               AlUtil.checkErrors("Enable per-source distance models");
               LOGGER.info("OpenAL initialized.");
            }
         }
      }
   }

   private int getMonoSourceCount() {
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var2 = null;

      int var8;
      try {
         int _snowmanx = ALC10.alcGetInteger(this.devicePointer, 4098);
         if (AlUtil.checkAlcErrors(this.devicePointer, "Get attributes size")) {
            throw new IllegalStateException("Failed to get OpenAL attributes");
         }

         IntBuffer _snowmanxx = _snowman.mallocInt(_snowmanx);
         ALC10.alcGetIntegerv(this.devicePointer, 4099, _snowmanxx);
         if (AlUtil.checkAlcErrors(this.devicePointer, "Get attributes")) {
            throw new IllegalStateException("Failed to get OpenAL attributes");
         }

         int _snowmanxxx = 0;

         int _snowmanxxxx;
         int _snowmanxxxxx;
         do {
            if (_snowmanxxx >= _snowmanx) {
               return 30;
            }

            _snowmanxxxx = _snowmanxx.get(_snowmanxxx++);
            if (_snowmanxxxx == 0) {
               return 30;
            }

            _snowmanxxxxx = _snowmanxx.get(_snowmanxxx++);
         } while (_snowmanxxxx != 4112);

         var8 = _snowmanxxxxx;
      } catch (Throwable var18) {
         var2 = var18;
         throw var18;
      } finally {
         if (_snowman != null) {
            if (var2 != null) {
               try {
                  _snowman.close();
               } catch (Throwable var17) {
                  var2.addSuppressed(var17);
               }
            } else {
               _snowman.close();
            }
         }
      }

      return var8;
   }

   private static long openDevice() {
      for (int _snowman = 0; _snowman < 3; _snowman++) {
         long _snowmanx = ALC10.alcOpenDevice((ByteBuffer)null);
         if (_snowmanx != 0L && !AlUtil.checkAlcErrors(_snowmanx, "Open device")) {
            return _snowmanx;
         }
      }

      throw new IllegalStateException("Failed to open OpenAL device");
   }

   public void close() {
      this.streamingSources.close();
      this.staticSources.close();
      ALC10.alcDestroyContext(this.contextPointer);
      if (this.devicePointer != 0L) {
         ALC10.alcCloseDevice(this.devicePointer);
      }
   }

   public SoundListener getListener() {
      return this.listener;
   }

   @Nullable
   public Source createSource(SoundEngine.RunMode mode) {
      return (mode == SoundEngine.RunMode.STREAMING ? this.staticSources : this.streamingSources).createSource();
   }

   public void release(Source source) {
      if (!this.streamingSources.release(source) && !this.staticSources.release(source)) {
         throw new IllegalStateException("Tried to release unknown channel");
      }
   }

   public String getDebugString() {
      return String.format(
         "Sounds: %d/%d + %d/%d",
         this.streamingSources.getSourceCount(),
         this.streamingSources.getMaxSourceCount(),
         this.staticSources.getSourceCount(),
         this.staticSources.getMaxSourceCount()
      );
   }

   public static enum RunMode {
      STATIC,
      STREAMING;

      private RunMode() {
      }
   }

   interface SourceSet {
      @Nullable
      Source createSource();

      boolean release(Source var1);

      void close();

      int getMaxSourceCount();

      int getSourceCount();
   }

   static class SourceSetImpl implements SoundEngine.SourceSet {
      private final int maxSourceCount;
      private final Set<Source> sources = Sets.newIdentityHashSet();

      public SourceSetImpl(int maxSourceCount) {
         this.maxSourceCount = maxSourceCount;
      }

      @Nullable
      @Override
      public Source createSource() {
         if (this.sources.size() >= this.maxSourceCount) {
            SoundEngine.LOGGER.warn("Maximum sound pool size {} reached", this.maxSourceCount);
            return null;
         } else {
            Source _snowman = Source.create();
            if (_snowman != null) {
               this.sources.add(_snowman);
            }

            return _snowman;
         }
      }

      @Override
      public boolean release(Source _snowman) {
         if (!this.sources.remove(_snowman)) {
            return false;
         } else {
            _snowman.close();
            return true;
         }
      }

      @Override
      public void close() {
         this.sources.forEach(Source::close);
         this.sources.clear();
      }

      @Override
      public int getMaxSourceCount() {
         return this.maxSourceCount;
      }

      @Override
      public int getSourceCount() {
         return this.sources.size();
      }
   }
}
