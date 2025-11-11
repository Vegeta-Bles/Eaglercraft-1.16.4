import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.system.MemoryUtil;

public class dfg extends dfb implements AutoCloseable {
   private static final Logger a = LogManager.getLogger();
   private int b;
   private final int c;
   private final int d;
   private final IntBuffer e;
   private final FloatBuffer f;
   private final String g;
   private boolean h;
   private final dfd i;

   public dfg(String var1, int var2, int var3, dfd var4) {
      this.g = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.i = _snowman;
      if (_snowman <= 3) {
         this.e = MemoryUtil.memAllocInt(_snowman);
         this.f = null;
      } else {
         this.e = null;
         this.f = MemoryUtil.memAllocFloat(_snowman);
      }

      this.b = -1;
      this.c();
   }

   public static int a(int var0, CharSequence var1) {
      return dem.b(_snowman, _snowman);
   }

   public static void a(int var0, int var1) {
      RenderSystem.glUniform1i(_snowman, _snowman);
   }

   public static int b(int var0, CharSequence var1) {
      return dem.c(_snowman, _snowman);
   }

   @Override
   public void close() {
      if (this.e != null) {
         MemoryUtil.memFree(this.e);
      }

      if (this.f != null) {
         MemoryUtil.memFree(this.f);
      }
   }

   private void c() {
      this.h = true;
      if (this.i != null) {
         this.i.b();
      }
   }

   public static int a(String var0) {
      int _snowman = -1;
      if ("int".equals(_snowman)) {
         _snowman = 0;
      } else if ("float".equals(_snowman)) {
         _snowman = 4;
      } else if (_snowman.startsWith("matrix")) {
         if (_snowman.endsWith("2x2")) {
            _snowman = 8;
         } else if (_snowman.endsWith("3x3")) {
            _snowman = 9;
         } else if (_snowman.endsWith("4x4")) {
            _snowman = 10;
         }
      }

      return _snowman;
   }

   public void a(int var1) {
      this.b = _snowman;
   }

   public String a() {
      return this.g;
   }

   @Override
   public void a(float var1) {
      ((Buffer)this.f).position(0);
      this.f.put(0, _snowman);
      this.c();
   }

   @Override
   public void a(float var1, float var2) {
      ((Buffer)this.f).position(0);
      this.f.put(0, _snowman);
      this.f.put(1, _snowman);
      this.c();
   }

   @Override
   public void a(float var1, float var2, float var3) {
      ((Buffer)this.f).position(0);
      this.f.put(0, _snowman);
      this.f.put(1, _snowman);
      this.f.put(2, _snowman);
      this.c();
   }

   @Override
   public void a(float var1, float var2, float var3, float var4) {
      ((Buffer)this.f).position(0);
      this.f.put(_snowman);
      this.f.put(_snowman);
      this.f.put(_snowman);
      this.f.put(_snowman);
      ((Buffer)this.f).flip();
      this.c();
   }

   @Override
   public void b(float var1, float var2, float var3, float var4) {
      ((Buffer)this.f).position(0);
      if (this.d >= 4) {
         this.f.put(0, _snowman);
      }

      if (this.d >= 5) {
         this.f.put(1, _snowman);
      }

      if (this.d >= 6) {
         this.f.put(2, _snowman);
      }

      if (this.d >= 7) {
         this.f.put(3, _snowman);
      }

      this.c();
   }

   @Override
   public void a(int var1, int var2, int var3, int var4) {
      ((Buffer)this.e).position(0);
      if (this.d >= 0) {
         this.e.put(0, _snowman);
      }

      if (this.d >= 1) {
         this.e.put(1, _snowman);
      }

      if (this.d >= 2) {
         this.e.put(2, _snowman);
      }

      if (this.d >= 3) {
         this.e.put(3, _snowman);
      }

      this.c();
   }

   @Override
   public void a(float[] var1) {
      if (_snowman.length < this.c) {
         a.warn("Uniform.set called with a too-small value array (expected {}, got {}). Ignoring.", this.c, _snowman.length);
      } else {
         ((Buffer)this.f).position(0);
         this.f.put(_snowman);
         ((Buffer)this.f).position(0);
         this.c();
      }
   }

   @Override
   public void a(b var1) {
      ((Buffer)this.f).position(0);
      _snowman.a(this.f);
      this.c();
   }

   public void b() {
      if (!this.h) {
      }

      this.h = false;
      if (this.d <= 3) {
         this.d();
      } else if (this.d <= 7) {
         this.e();
      } else {
         if (this.d > 10) {
            a.warn("Uniform.upload called, but type value ({}) is not a valid type. Ignoring.", this.d);
            return;
         }

         this.f();
      }
   }

   private void d() {
      ((Buffer)this.f).clear();
      switch (this.d) {
         case 0:
            RenderSystem.glUniform1(this.b, this.e);
            break;
         case 1:
            RenderSystem.glUniform2(this.b, this.e);
            break;
         case 2:
            RenderSystem.glUniform3(this.b, this.e);
            break;
         case 3:
            RenderSystem.glUniform4(this.b, this.e);
            break;
         default:
            a.warn("Uniform.upload called, but count value ({}) is  not in the range of 1 to 4. Ignoring.", this.c);
      }
   }

   private void e() {
      ((Buffer)this.f).clear();
      switch (this.d) {
         case 4:
            RenderSystem.glUniform1(this.b, this.f);
            break;
         case 5:
            RenderSystem.glUniform2(this.b, this.f);
            break;
         case 6:
            RenderSystem.glUniform3(this.b, this.f);
            break;
         case 7:
            RenderSystem.glUniform4(this.b, this.f);
            break;
         default:
            a.warn("Uniform.upload called, but count value ({}) is not in the range of 1 to 4. Ignoring.", this.c);
      }
   }

   private void f() {
      ((Buffer)this.f).clear();
      switch (this.d) {
         case 8:
            RenderSystem.glUniformMatrix2(this.b, false, this.f);
            break;
         case 9:
            RenderSystem.glUniformMatrix3(this.b, false, this.f);
            break;
         case 10:
            RenderSystem.glUniformMatrix4(this.b, false, this.f);
      }
   }
}
