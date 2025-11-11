import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;

public class ddu {
   private static final Logger a = LogManager.getLogger();
   private final int b;
   private final AtomicBoolean c = new AtomicBoolean(true);
   private int d = 16384;
   @Nullable
   private enm e;

   @Nullable
   static ddu a() {
      int[] _snowman = new int[1];
      AL10.alGenSources(_snowman);
      return ddy.a("Allocate new source") ? null : new ddu(_snowman[0]);
   }

   private ddu(int var1) {
      this.b = _snowman;
   }

   public void b() {
      if (this.c.compareAndSet(true, false)) {
         AL10.alSourceStop(this.b);
         ddy.a("Stop");
         if (this.e != null) {
            try {
               this.e.close();
            } catch (IOException var2) {
               a.error("Failed to close audio stream", var2);
            }

            this.k();
            this.e = null;
         }

         AL10.alDeleteSources(new int[]{this.b});
         ddy.a("Cleanup");
      }
   }

   public void c() {
      AL10.alSourcePlay(this.b);
   }

   private int j() {
      return !this.c.get() ? 4116 : AL10.alGetSourcei(this.b, 4112);
   }

   public void d() {
      if (this.j() == 4114) {
         AL10.alSourcePause(this.b);
      }
   }

   public void e() {
      if (this.j() == 4115) {
         AL10.alSourcePlay(this.b);
      }
   }

   public void f() {
      if (this.c.get()) {
         AL10.alSourceStop(this.b);
         ddy.a("Stop");
      }
   }

   public boolean g() {
      return this.j() == 4116;
   }

   public void a(dcn var1) {
      AL10.alSourcefv(this.b, 4100, new float[]{(float)_snowman.b, (float)_snowman.c, (float)_snowman.d});
   }

   public void a(float var1) {
      AL10.alSourcef(this.b, 4099, _snowman);
   }

   public void a(boolean var1) {
      AL10.alSourcei(this.b, 4103, _snowman ? 1 : 0);
   }

   public void b(float var1) {
      AL10.alSourcef(this.b, 4106, _snowman);
   }

   public void h() {
      AL10.alSourcei(this.b, 53248, 0);
   }

   public void c(float var1) {
      AL10.alSourcei(this.b, 53248, 53251);
      AL10.alSourcef(this.b, 4131, _snowman);
      AL10.alSourcef(this.b, 4129, 1.0F);
      AL10.alSourcef(this.b, 4128, 0.0F);
   }

   public void b(boolean var1) {
      AL10.alSourcei(this.b, 514, _snowman ? 1 : 0);
   }

   public void a(ddz var1) {
      _snowman.a().ifPresent(var1x -> AL10.alSourcei(this.b, 4105, var1x));
   }

   public void a(enm var1) {
      this.e = _snowman;
      AudioFormat _snowman = _snowman.a();
      this.d = a(_snowman, 1);
      this.a(4);
   }

   private static int a(AudioFormat var0, int var1) {
      return (int)((float)(_snowman * _snowman.getSampleSizeInBits()) / 8.0F * (float)_snowman.getChannels() * _snowman.getSampleRate());
   }

   private void a(int var1) {
      if (this.e != null) {
         try {
            for (int _snowman = 0; _snowman < _snowman; _snowman++) {
               ByteBuffer _snowmanx = this.e.a(this.d);
               if (_snowmanx != null) {
                  new ddz(_snowmanx, this.e.a()).c().ifPresent(var1x -> AL10.alSourceQueueBuffers(this.b, new int[]{var1x}));
               }
            }
         } catch (IOException var4) {
            a.error("Failed to read from audio stream", var4);
         }
      }
   }

   public void i() {
      if (this.e != null) {
         int _snowman = this.k();
         this.a(_snowman);
      }
   }

   private int k() {
      int _snowman = AL10.alGetSourcei(this.b, 4118);
      if (_snowman > 0) {
         int[] _snowmanx = new int[_snowman];
         AL10.alSourceUnqueueBuffers(this.b, _snowmanx);
         ddy.a("Unqueue buffers");
         AL10.alDeleteBuffers(_snowmanx);
         ddy.a("Remove processed buffers");
      }

      return _snowman;
   }
}
