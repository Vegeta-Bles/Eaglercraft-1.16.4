import com.google.common.collect.Sets;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryStack;

public class ddv {
   private static final Logger a = LogManager.getLogger();
   private long b;
   private long c;
   private static final ddv.a d = new ddv.a() {
      @Nullable
      @Override
      public ddu a() {
         return null;
      }

      @Override
      public boolean a(ddu var1) {
         return false;
      }

      @Override
      public void b() {
      }

      @Override
      public int c() {
         return 0;
      }

      @Override
      public int d() {
         return 0;
      }
   };
   private ddv.a e = d;
   private ddv.a f = d;
   private final ddw g = new ddw();

   public ddv() {
   }

   public void a() {
      this.b = g();
      ALCCapabilities _snowman = ALC.createCapabilities(this.b);
      if (ddy.a(this.b, "Get capabilities")) {
         throw new IllegalStateException("Failed to get OpenAL capabilities");
      } else if (!_snowman.OpenALC11) {
         throw new IllegalStateException("OpenAL 1.1 not supported");
      } else {
         this.c = ALC10.alcCreateContext(this.b, (IntBuffer)null);
         ALC10.alcMakeContextCurrent(this.c);
         int _snowmanx = this.f();
         int _snowmanxx = afm.a((int)afm.c((float)_snowmanx), 2, 8);
         int _snowmanxxx = afm.a(_snowmanx - _snowmanxx, 8, 255);
         this.e = new ddv.b(_snowmanxxx);
         this.f = new ddv.b(_snowmanxx);
         ALCapabilities _snowmanxxxx = AL.createCapabilities(_snowman);
         ddy.a("Initialization");
         if (!_snowmanxxxx.AL_EXT_source_distance_model) {
            throw new IllegalStateException("AL_EXT_source_distance_model is not supported");
         } else {
            AL10.alEnable(512);
            if (!_snowmanxxxx.AL_EXT_LINEAR_DISTANCE) {
               throw new IllegalStateException("AL_EXT_LINEAR_DISTANCE is not supported");
            } else {
               ddy.a("Enable per-source distance models");
               a.info("OpenAL initialized.");
            }
         }
      }
   }

   private int f() {
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var2 = null;

      int var8;
      try {
         int _snowmanx = ALC10.alcGetInteger(this.b, 4098);
         if (ddy.a(this.b, "Get attributes size")) {
            throw new IllegalStateException("Failed to get OpenAL attributes");
         }

         IntBuffer _snowmanxx = _snowman.mallocInt(_snowmanx);
         ALC10.alcGetIntegerv(this.b, 4099, _snowmanxx);
         if (ddy.a(this.b, "Get attributes")) {
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

   private static long g() {
      for (int _snowman = 0; _snowman < 3; _snowman++) {
         long _snowmanx = ALC10.alcOpenDevice((ByteBuffer)null);
         if (_snowmanx != 0L && !ddy.a(_snowmanx, "Open device")) {
            return _snowmanx;
         }
      }

      throw new IllegalStateException("Failed to open OpenAL device");
   }

   public void b() {
      this.e.b();
      this.f.b();
      ALC10.alcDestroyContext(this.c);
      if (this.b != 0L) {
         ALC10.alcCloseDevice(this.b);
      }
   }

   public ddw c() {
      return this.g;
   }

   @Nullable
   public ddu a(ddv.c var1) {
      return (_snowman == ddv.c.b ? this.f : this.e).a();
   }

   public void a(ddu var1) {
      if (!this.e.a(_snowman) && !this.f.a(_snowman)) {
         throw new IllegalStateException("Tried to release unknown channel");
      }
   }

   public String d() {
      return String.format("Sounds: %d/%d + %d/%d", this.e.d(), this.e.c(), this.f.d(), this.f.c());
   }

   interface a {
      @Nullable
      ddu a();

      boolean a(ddu var1);

      void b();

      int c();

      int d();
   }

   static class b implements ddv.a {
      private final int a;
      private final Set<ddu> b = Sets.newIdentityHashSet();

      public b(int var1) {
         this.a = _snowman;
      }

      @Nullable
      @Override
      public ddu a() {
         if (this.b.size() >= this.a) {
            ddv.a.warn("Maximum sound pool size {} reached", this.a);
            return null;
         } else {
            ddu _snowman = ddu.a();
            if (_snowman != null) {
               this.b.add(_snowman);
            }

            return _snowman;
         }
      }

      @Override
      public boolean a(ddu var1) {
         if (!this.b.remove(_snowman)) {
            return false;
         } else {
            _snowman.b();
            return true;
         }
      }

      @Override
      public void b() {
         this.b.forEach(ddu::b);
         this.b.clear();
      }

      @Override
      public int c() {
         return this.a;
      }

      @Override
      public int d() {
         return this.b.size();
      }
   }

   public static enum c {
      a,
      b;

      private c() {
      }
   }
}
