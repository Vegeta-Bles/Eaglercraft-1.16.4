import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWImage.Buffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public final class dez implements AutoCloseable {
   private static final Logger a = LogManager.getLogger();
   private final GLFWErrorCallback b = GLFWErrorCallback.create(this::a);
   private final dfa c;
   private final dev d;
   private final long e;
   private int f;
   private int g;
   private int h;
   private int i;
   private Optional<dey> j;
   private boolean k;
   private boolean l;
   private int m;
   private int n;
   private int o;
   private int p;
   private int q;
   private int r;
   private int s;
   private int t;
   private double u;
   private String v = "";
   private boolean w;
   private int x;
   private boolean y;

   public dez(dfa var1, dev var2, dej var3, @Nullable String var4, String var5) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      this.d = _snowman;
      this.u();
      this.a("Pre startup");
      this.c = _snowman;
      Optional<dey> _snowman = dey.a(_snowman);
      if (_snowman.isPresent()) {
         this.j = _snowman;
      } else if (_snowman.c.isPresent() && _snowman.d.isPresent()) {
         this.j = Optional.of(new dey(_snowman.c.getAsInt(), _snowman.d.getAsInt(), 8, 8, 8, 60));
      } else {
         this.j = Optional.empty();
      }

      this.l = this.k = _snowman.e;
      der _snowmanx = _snowman.a(GLFW.glfwGetPrimaryMonitor());
      this.h = this.o = _snowman.a > 0 ? _snowman.a : 1;
      this.i = this.p = _snowman.b > 0 ? _snowman.b : 1;
      GLFW.glfwDefaultWindowHints();
      GLFW.glfwWindowHint(139265, 196609);
      GLFW.glfwWindowHint(139275, 221185);
      GLFW.glfwWindowHint(139266, 2);
      GLFW.glfwWindowHint(139267, 0);
      GLFW.glfwWindowHint(139272, 0);
      this.e = GLFW.glfwCreateWindow(this.o, this.p, _snowman, this.k && _snowmanx != null ? _snowmanx.f() : 0L, 0L);
      if (_snowmanx != null) {
         dey _snowmanxx = _snowmanx.a(this.k ? this.j : Optional.empty());
         this.f = this.m = _snowmanx.c() + _snowmanxx.a() / 2 - this.o / 2;
         this.g = this.n = _snowmanx.d() + _snowmanxx.b() / 2 - this.p / 2;
      } else {
         int[] _snowmanxx = new int[1];
         int[] _snowmanxxx = new int[1];
         GLFW.glfwGetWindowPos(this.e, _snowmanxx, _snowmanxxx);
         this.f = this.m = _snowmanxx[0];
         this.g = this.n = _snowmanxxx[0];
      }

      GLFW.glfwMakeContextCurrent(this.e);
      GL.createCapabilities();
      this.w();
      this.v();
      GLFW.glfwSetFramebufferSizeCallback(this.e, this::b);
      GLFW.glfwSetWindowPosCallback(this.e, this::a);
      GLFW.glfwSetWindowSizeCallback(this.e, this::c);
      GLFW.glfwSetWindowFocusCallback(this.e, this::a);
      GLFW.glfwSetCursorEnterCallback(this.e, this::b);
   }

   public int a() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GLX._getRefreshRate(this);
   }

   public boolean b() {
      return GLX._shouldClose(this);
   }

   public static void a(BiConsumer<Integer, String> var0) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      MemoryStack _snowman = MemoryStack.stackPush();
      Throwable var2 = null;

      try {
         PointerBuffer _snowmanx = _snowman.mallocPointer(1);
         int _snowmanxx = GLFW.glfwGetError(_snowmanx);
         if (_snowmanxx != 0) {
            long _snowmanxxx = _snowmanx.get();
            String _snowmanxxxx = _snowmanxxx == 0L ? "" : MemoryUtil.memUTF8(_snowmanxxx);
            _snowman.accept(_snowmanxx, _snowmanxxxx);
         }
      } catch (Throwable var15) {
         var2 = var15;
         throw var15;
      } finally {
         if (_snowman != null) {
            if (var2 != null) {
               try {
                  _snowman.close();
               } catch (Throwable var14) {
                  var2.addSuppressed(var14);
               }
            } else {
               _snowman.close();
            }
         }
      }
   }

   public void a(InputStream var1, InputStream var2) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);

      try {
         MemoryStack _snowman = MemoryStack.stackPush();
         Throwable var4 = null;

         try {
            if (_snowman == null) {
               throw new FileNotFoundException("icons/icon_16x16.png");
            }

            if (_snowman == null) {
               throw new FileNotFoundException("icons/icon_32x32.png");
            }

            IntBuffer _snowmanx = _snowman.mallocInt(1);
            IntBuffer _snowmanxx = _snowman.mallocInt(1);
            IntBuffer _snowmanxxx = _snowman.mallocInt(1);
            Buffer _snowmanxxxx = GLFWImage.mallocStack(2, _snowman);
            ByteBuffer _snowmanxxxxx = this.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
            if (_snowmanxxxxx == null) {
               throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }

            _snowmanxxxx.position(0);
            _snowmanxxxx.width(_snowmanx.get(0));
            _snowmanxxxx.height(_snowmanxx.get(0));
            _snowmanxxxx.pixels(_snowmanxxxxx);
            ByteBuffer _snowmanxxxxxx = this.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
            if (_snowmanxxxxxx == null) {
               throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }

            _snowmanxxxx.position(1);
            _snowmanxxxx.width(_snowmanx.get(0));
            _snowmanxxxx.height(_snowmanxx.get(0));
            _snowmanxxxx.pixels(_snowmanxxxxxx);
            _snowmanxxxx.position(0);
            GLFW.glfwSetWindowIcon(this.e, _snowmanxxxx);
            STBImage.stbi_image_free(_snowmanxxxxx);
            STBImage.stbi_image_free(_snowmanxxxxxx);
         } catch (Throwable var19) {
            var4 = var19;
            throw var19;
         } finally {
            if (_snowman != null) {
               if (var4 != null) {
                  try {
                     _snowman.close();
                  } catch (Throwable var18) {
                     var4.addSuppressed(var18);
                  }
               } else {
                  _snowman.close();
               }
            }
         }
      } catch (IOException var21) {
         a.error("Couldn't set icon", var21);
      }
   }

   @Nullable
   private ByteBuffer a(InputStream var1, IntBuffer var2, IntBuffer var3, IntBuffer var4) throws IOException {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      ByteBuffer _snowman = null;

      ByteBuffer var6;
      try {
         _snowman = dex.a(_snowman);
         ((java.nio.Buffer)_snowman).rewind();
         var6 = STBImage.stbi_load_from_memory(_snowman, _snowman, _snowman, _snowman, 0);
      } finally {
         if (_snowman != null) {
            MemoryUtil.memFree(_snowman);
         }
      }

      return var6;
   }

   public void a(String var1) {
      this.v = _snowman;
   }

   private void u() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      GLFW.glfwSetErrorCallback(dez::b);
   }

   private static void b(int var0, long var1) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      String _snowman = "GLFW error " + _snowman + ": " + MemoryUtil.memUTF8(_snowman);
      TinyFileDialogs.tinyfd_messageBox(
         "Minecraft", _snowman + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).", "ok", "error", false
      );
      throw new dez.a(_snowman);
   }

   public void a(int var1, long var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      String _snowman = MemoryUtil.memUTF8(_snowman);
      a.error("########## GL ERROR ##########");
      a.error("@ {}", this.v);
      a.error("{}: {}", _snowman, _snowman);
   }

   public void c() {
      GLFWErrorCallback _snowman = GLFW.glfwSetErrorCallback(this.b);
      if (_snowman != null) {
         _snowman.free();
      }
   }

   public void a(boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.y = _snowman;
      GLFW.glfwSwapInterval(_snowman ? 1 : 0);
   }

   @Override
   public void close() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Callbacks.glfwFreeCallbacks(this.e);
      this.b.close();
      GLFW.glfwDestroyWindow(this.e);
      GLFW.glfwTerminate();
   }

   private void a(long var1, int var3, int var4) {
      this.m = _snowman;
      this.n = _snowman;
   }

   private void b(long var1, int var3, int var4) {
      if (_snowman == this.e) {
         int _snowman = this.k();
         int _snowmanx = this.l();
         if (_snowman != 0 && _snowman != 0) {
            this.q = _snowman;
            this.r = _snowman;
            if (this.k() != _snowman || this.l() != _snowmanx) {
               this.c.a();
            }
         }
      }
   }

   private void v() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      int[] _snowman = new int[1];
      int[] _snowmanx = new int[1];
      GLFW.glfwGetFramebufferSize(this.e, _snowman, _snowmanx);
      this.q = _snowman[0];
      this.r = _snowmanx[0];
   }

   private void c(long var1, int var3, int var4) {
      this.o = _snowman;
      this.p = _snowman;
   }

   private void a(long var1, boolean var3) {
      if (_snowman == this.e) {
         this.c.a(_snowman);
      }
   }

   private void b(long var1, boolean var3) {
      if (_snowman) {
         this.c.b();
      }
   }

   public void a(int var1) {
      this.x = _snowman;
   }

   public int d() {
      return this.x;
   }

   public void e() {
      RenderSystem.flipFrame(this.e);
      if (this.k != this.l) {
         this.l = this.k;
         this.c(this.y);
      }
   }

   public Optional<dey> f() {
      return this.j;
   }

   public void a(Optional<dey> var1) {
      boolean _snowman = !_snowman.equals(this.j);
      this.j = _snowman;
      if (_snowman) {
         this.w = true;
      }
   }

   public void g() {
      if (this.k && this.w) {
         this.w = false;
         this.w();
         this.c.a();
      }
   }

   private void w() {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      boolean _snowman = GLFW.glfwGetWindowMonitor(this.e) != 0L;
      if (this.k) {
         der _snowmanx = this.d.a(this);
         if (_snowmanx == null) {
            a.warn("Failed to find suitable monitor for fullscreen mode");
            this.k = false;
         } else {
            dey _snowmanxx = _snowmanx.a(this.j);
            if (!_snowman) {
               this.f = this.m;
               this.g = this.n;
               this.h = this.o;
               this.i = this.p;
            }

            this.m = 0;
            this.n = 0;
            this.o = _snowmanxx.a();
            this.p = _snowmanxx.b();
            GLFW.glfwSetWindowMonitor(this.e, _snowmanx.f(), this.m, this.n, this.o, this.p, _snowmanxx.f());
         }
      } else {
         this.m = this.f;
         this.n = this.g;
         this.o = this.h;
         this.p = this.i;
         GLFW.glfwSetWindowMonitor(this.e, 0L, this.m, this.n, this.o, this.p, -1);
      }
   }

   public void h() {
      this.k = !this.k;
   }

   private void c(boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);

      try {
         this.w();
         this.c.a();
         this.a(_snowman);
         this.e();
      } catch (Exception var3) {
         a.error("Couldn't toggle fullscreen", var3);
      }
   }

   public int a(int var1, boolean var2) {
      int _snowman = 1;

      while (_snowman != _snowman && _snowman < this.q && _snowman < this.r && this.q / (_snowman + 1) >= 320 && this.r / (_snowman + 1) >= 240) {
         _snowman++;
      }

      if (_snowman && _snowman % 2 != 0) {
         _snowman++;
      }

      return _snowman;
   }

   public void a(double var1) {
      this.u = _snowman;
      int _snowman = (int)((double)this.q / _snowman);
      this.s = (double)this.q / _snowman > (double)_snowman ? _snowman + 1 : _snowman;
      int _snowmanx = (int)((double)this.r / _snowman);
      this.t = (double)this.r / _snowman > (double)_snowmanx ? _snowmanx + 1 : _snowmanx;
   }

   public void b(String var1) {
      GLFW.glfwSetWindowTitle(this.e, _snowman);
   }

   public long i() {
      return this.e;
   }

   public boolean j() {
      return this.k;
   }

   public int k() {
      return this.q;
   }

   public int l() {
      return this.r;
   }

   public int m() {
      return this.o;
   }

   public int n() {
      return this.p;
   }

   public int o() {
      return this.s;
   }

   public int p() {
      return this.t;
   }

   public int q() {
      return this.m;
   }

   public int r() {
      return this.n;
   }

   public double s() {
      return this.u;
   }

   @Nullable
   public der t() {
      return this.d.a(this);
   }

   public void b(boolean var1) {
      deo.a(this.e, _snowman);
   }

   public static class a extends dta {
      private a(String var1) {
         super(_snowman);
      }
   }
}
