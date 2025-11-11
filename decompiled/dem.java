import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.EXTFramebufferBlit;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryUtil;

public class dem {
   private static final FloatBuffer a = GLX.make(MemoryUtil.memAllocFloat(16), var0 -> dei.a(MemoryUtil.memAddress(var0)));
   private static final dem.a b = new dem.a();
   private static final dem.c c = new dem.c(2896);
   private static final dem.c[] d = IntStream.range(0, 8).mapToObj(var0 -> new dem.c(16384 + var0)).toArray(dem.c[]::new);
   private static final dem.g e = new dem.g();
   private static final dem.b f = new dem.b();
   private static final dem.i g = new dem.i();
   private static final dem.n h = new dem.n();
   private static final dem.h i = new dem.h();
   private static final dem.p j = new dem.p();
   private static final dem.e k = new dem.e();
   private static final dem.w l = new dem.w();
   private static final dem.t m = new dem.t();
   private static final dem.q n = new dem.q();
   private static final FloatBuffer o = deq.b(4);
   private static int p;
   private static final dem.x[] q = IntStream.range(0, 12).mapToObj(var0 -> new dem.x()).toArray(dem.x[]::new);
   private static int r = 7425;
   private static final dem.c s = new dem.c(32826);
   private static final dem.f t = new dem.f();
   private static final dem.d u = new dem.d();
   private static dem.l v;
   private static dem.k w;

   @Deprecated
   public static void a() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPushAttrib(8256);
   }

   @Deprecated
   public static void b() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPushAttrib(270336);
   }

   @Deprecated
   public static void c() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPopAttrib();
   }

   @Deprecated
   public static void d() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      b.a.a();
   }

   @Deprecated
   public static void e() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      b.a.b();
   }

   @Deprecated
   public static void a(int var0, float var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (_snowman != b.b || _snowman != b.c) {
         b.b = _snowman;
         b.c = _snowman;
         GL11.glAlphaFunc(_snowman, _snowman);
      }
   }

   @Deprecated
   public static void f() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      c.b();
   }

   @Deprecated
   public static void g() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      c.a();
   }

   @Deprecated
   public static void a(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      d[_snowman].b();
   }

   @Deprecated
   public static void h() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      e.a.b();
   }

   @Deprecated
   public static void i() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      e.a.a();
   }

   @Deprecated
   public static void a(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != e.b || _snowman != e.c) {
         e.b = _snowman;
         e.c = _snowman;
         GL11.glColorMaterial(_snowman, _snowman);
      }
   }

   @Deprecated
   public static void a(int var0, int var1, FloatBuffer var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glLightfv(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void a(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glLightModelfv(_snowman, _snowman);
   }

   @Deprecated
   public static void a(float var0, float var1, float var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glNormal3f(_snowman, _snowman, _snowman);
   }

   public static void j() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      n.a.a();
   }

   public static void k() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      n.a.b();
   }

   public static void a(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL20.glScissor(_snowman, _snowman, _snowman, _snowman);
   }

   public static void l() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      g.a.a();
   }

   public static void m() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      g.a.b();
   }

   public static void b(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (_snowman != g.c) {
         g.c = _snowman;
         GL11.glDepthFunc(_snowman);
      }
   }

   public static void a(boolean var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != g.b) {
         g.b = _snowman;
         GL11.glDepthMask(_snowman);
      }
   }

   public static void n() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      f.a.a();
   }

   public static void o() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      f.a.b();
   }

   public static void b(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != f.b || _snowman != f.c) {
         f.b = _snowman;
         f.c = _snowman;
         GL11.glBlendFunc(_snowman, _snowman);
      }
   }

   public static void b(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != f.b || _snowman != f.c || _snowman != f.d || _snowman != f.e) {
         f.b = _snowman;
         f.c = _snowman;
         f.d = _snowman;
         f.e = _snowman;
         c(_snowman, _snowman, _snowman, _snowman);
      }
   }

   public static void a(float var0, float var1, float var2, float var3) {
      GL14.glBlendColor(_snowman, _snowman, _snowman, _snowman);
   }

   public static void c(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL14.glBlendEquation(_snowman);
   }

   public static String a(GLCapabilities var0) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      if (_snowman.OpenGL30) {
         w = dem.k.a;
      } else if (_snowman.GL_EXT_framebuffer_blit) {
         w = dem.k.b;
      } else {
         w = dem.k.c;
      }

      if (_snowman.OpenGL30) {
         v = dem.l.a;
         dek.a = 36160;
         dek.b = 36161;
         dek.c = 36064;
         dek.d = 36096;
         dek.e = 36053;
         dek.f = 36054;
         dek.g = 36055;
         dek.h = 36059;
         dek.i = 36060;
         return "OpenGL 3.0";
      } else if (_snowman.GL_ARB_framebuffer_object) {
         v = dem.l.b;
         dek.a = 36160;
         dek.b = 36161;
         dek.c = 36064;
         dek.d = 36096;
         dek.e = 36053;
         dek.g = 36055;
         dek.f = 36054;
         dek.h = 36059;
         dek.i = 36060;
         return "ARB_framebuffer_object extension";
      } else if (_snowman.GL_EXT_framebuffer_object) {
         v = dem.l.c;
         dek.a = 36160;
         dek.b = 36161;
         dek.c = 36064;
         dek.d = 36096;
         dek.e = 36053;
         dek.g = 36055;
         dek.f = 36054;
         dek.h = 36059;
         dek.i = 36060;
         return "EXT_framebuffer_object extension";
      } else {
         throw new IllegalStateException("Could not initialize framebuffer support.");
      }
   }

   public static int c(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetProgrami(_snowman, _snowman);
   }

   public static void d(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glAttachShader(_snowman, _snowman);
   }

   public static void d(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glDeleteShader(_snowman);
   }

   public static int e(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glCreateShader(_snowman);
   }

   public static void a(int var0, CharSequence var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glShaderSource(_snowman, _snowman);
   }

   public static void f(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glCompileShader(_snowman);
   }

   public static int e(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetShaderi(_snowman, _snowman);
   }

   public static void g(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUseProgram(_snowman);
   }

   public static int p() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glCreateProgram();
   }

   public static void h(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glDeleteProgram(_snowman);
   }

   public static void i(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glLinkProgram(_snowman);
   }

   public static int b(int var0, CharSequence var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetUniformLocation(_snowman, _snowman);
   }

   public static void a(int var0, IntBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform1iv(_snowman, _snowman);
   }

   public static void f(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform1i(_snowman, _snowman);
   }

   public static void b(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform1fv(_snowman, _snowman);
   }

   public static void b(int var0, IntBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform2iv(_snowman, _snowman);
   }

   public static void c(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform2fv(_snowman, _snowman);
   }

   public static void c(int var0, IntBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform3iv(_snowman, _snowman);
   }

   public static void d(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform3fv(_snowman, _snowman);
   }

   public static void d(int var0, IntBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform4iv(_snowman, _snowman);
   }

   public static void e(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniform4fv(_snowman, _snowman);
   }

   public static void a(int var0, boolean var1, FloatBuffer var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniformMatrix2fv(_snowman, _snowman, _snowman);
   }

   public static void b(int var0, boolean var1, FloatBuffer var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniformMatrix3fv(_snowman, _snowman, _snowman);
   }

   public static void c(int var0, boolean var1, FloatBuffer var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glUniformMatrix4fv(_snowman, _snowman, _snowman);
   }

   public static int c(int var0, CharSequence var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetAttribLocation(_snowman, _snowman);
   }

   public static int q() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL15.glGenBuffers();
   }

   public static void g(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL15.glBindBuffer(_snowman, _snowman);
   }

   public static void a(int var0, ByteBuffer var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL15.glBufferData(_snowman, _snowman, _snowman);
   }

   public static void j(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL15.glDeleteBuffers(_snowman);
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL20.glCopyTexSubImage2D(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static void h(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch (v) {
         case a:
            GL30.glBindFramebuffer(_snowman, _snowman);
            break;
         case b:
            ARBFramebufferObject.glBindFramebuffer(_snowman, _snowman);
            break;
         case c:
            EXTFramebufferObject.glBindFramebufferEXT(_snowman, _snowman);
      }
   }

   public static int r() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch (v) {
         case a:
            if (GL30.glGetFramebufferAttachmentParameteri(36160, 36096, 36048) == 5890) {
               return GL30.glGetFramebufferAttachmentParameteri(36160, 36096, 36049);
            }
            break;
         case b:
            if (ARBFramebufferObject.glGetFramebufferAttachmentParameteri(36160, 36096, 36048) == 5890) {
               return ARBFramebufferObject.glGetFramebufferAttachmentParameteri(36160, 36096, 36049);
            }
            break;
         case c:
            if (EXTFramebufferObject.glGetFramebufferAttachmentParameteriEXT(36160, 36096, 36048) == 5890) {
               return EXTFramebufferObject.glGetFramebufferAttachmentParameteriEXT(36160, 36096, 36049);
            }
      }

      return 0;
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch (w) {
         case a:
            GL30.glBlitFramebuffer(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            break;
         case b:
            EXTFramebufferBlit.glBlitFramebufferEXT(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         case c:
      }
   }

   public static void k(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch (v) {
         case a:
            GL30.glDeleteFramebuffers(_snowman);
            break;
         case b:
            ARBFramebufferObject.glDeleteFramebuffers(_snowman);
            break;
         case c:
            EXTFramebufferObject.glDeleteFramebuffersEXT(_snowman);
      }
   }

   public static int s() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch (v) {
         case a:
            return GL30.glGenFramebuffers();
         case b:
            return ARBFramebufferObject.glGenFramebuffers();
         case c:
            return EXTFramebufferObject.glGenFramebuffersEXT();
         default:
            return -1;
      }
   }

   public static int l(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch (v) {
         case a:
            return GL30.glCheckFramebufferStatus(_snowman);
         case b:
            return ARBFramebufferObject.glCheckFramebufferStatus(_snowman);
         case c:
            return EXTFramebufferObject.glCheckFramebufferStatusEXT(_snowman);
         default:
            return -1;
      }
   }

   public static void a(int var0, int var1, int var2, int var3, int var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      switch (v) {
         case a:
            GL30.glFramebufferTexture2D(_snowman, _snowman, _snowman, _snowman, _snowman);
            break;
         case b:
            ARBFramebufferObject.glFramebufferTexture2D(_snowman, _snowman, _snowman, _snowman, _snowman);
            break;
         case c:
            EXTFramebufferObject.glFramebufferTexture2DEXT(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Deprecated
   public static int t() {
      return q[p].b;
   }

   public static void m(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL13.glActiveTexture(_snowman);
   }

   @Deprecated
   public static void n(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL13.glClientActiveTexture(_snowman);
   }

   @Deprecated
   public static void a(int var0, float var1, float var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL13.glMultiTexCoord2f(_snowman, _snowman, _snowman);
   }

   public static void c(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL14.glBlendFuncSeparate(_snowman, _snowman, _snowman, _snowman);
   }

   public static String i(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetShaderInfoLog(_snowman, _snowman);
   }

   public static String j(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL20.glGetProgramInfoLog(_snowman, _snowman);
   }

   public static void u() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      a(8960, 8704, 34160);
      o(7681, 34168);
   }

   public static void v() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      a(8960, 8704, 8448);
      e(8448, 5890, 34168, 34166);
   }

   public static void k(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      q(33985);
      K();
      w(5890);
      P();
      float _snowman = 1.0F / (float)(_snowman - 1);
      b(_snowman, _snowman, _snowman);
      w(5888);
      s(_snowman);
      b(3553, 10241, 9728);
      b(3553, 10240, 9728);
      b(3553, 10242, 10496);
      b(3553, 10243, 10496);
      a(8960, 8704, 34160);
      e(34165, 34168, 5890, 5890);
      p(7681, 34168);
      q(33984);
   }

   public static void w() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      q(33985);
      L();
      q(33984);
   }

   private static void o(int var0, int var1) {
      a(8960, 34161, _snowman);
      a(8960, 34176, _snowman);
      a(8960, 34192, 768);
   }

   private static void e(int var0, int var1, int var2, int var3) {
      a(8960, 34161, _snowman);
      a(8960, 34176, _snowman);
      a(8960, 34192, 768);
      a(8960, 34177, _snowman);
      a(8960, 34193, 768);
      a(8960, 34178, _snowman);
      a(8960, 34194, 770);
   }

   private static void p(int var0, int var1) {
      a(8960, 34162, _snowman);
      a(8960, 34184, _snowman);
      a(8960, 34200, 770);
   }

   public static void a(g var0, g var1, b var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      Q();
      P();
      a(0);
      a(1);
      h _snowman = new h(_snowman);
      _snowman.a(_snowman);
      a(16384, 4611, e(_snowman.a(), _snowman.b(), _snowman.c(), 0.0F));
      float _snowmanx = 0.6F;
      a(16384, 4609, e(0.6F, 0.6F, 0.6F, 1.0F));
      a(16384, 4608, e(0.0F, 0.0F, 0.0F, 1.0F));
      a(16384, 4610, e(0.0F, 0.0F, 0.0F, 1.0F));
      h _snowmanxx = new h(_snowman);
      _snowmanxx.a(_snowman);
      a(16385, 4611, e(_snowmanxx.a(), _snowmanxx.b(), _snowmanxx.c(), 0.0F));
      a(16385, 4609, e(0.6F, 0.6F, 0.6F, 1.0F));
      a(16385, 4608, e(0.0F, 0.0F, 0.0F, 1.0F));
      a(16385, 4610, e(0.0F, 0.0F, 0.0F, 1.0F));
      t(7424);
      float _snowmanxxx = 0.4F;
      a(2899, e(0.4F, 0.4F, 0.4F, 1.0F));
      R();
   }

   public static void a(g var0, g var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      b _snowman = new b();
      _snowman.a();
      _snowman.a(b.a(1.0F, -1.0F, 1.0F));
      _snowman.a(g.d.a(-22.5F));
      _snowman.a(g.b.a(135.0F));
      a(_snowman, _snowman, _snowman);
   }

   public static void b(g var0, g var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      b _snowman = new b();
      _snowman.a();
      _snowman.a(g.d.a(62.0F));
      _snowman.a(g.b.a(185.5F));
      _snowman.a(b.a(1.0F, -1.0F, 1.0F));
      _snowman.a(g.d.a(-22.5F));
      _snowman.a(g.b.a(135.0F));
      a(_snowman, _snowman, _snowman);
   }

   private static FloatBuffer e(float var0, float var1, float var2, float var3) {
      ((Buffer)o).clear();
      o.put(_snowman).put(_snowman).put(_snowman).put(_snowman);
      ((Buffer)o).flip();
      return o;
   }

   public static void x() {
      a(dem.u.a, 9216);
      a(dem.u.b, 9216);
      a(dem.u.c, 9216);
      a(dem.u.a, 9474, e(1.0F, 0.0F, 0.0F, 0.0F));
      a(dem.u.b, 9474, e(0.0F, 1.0F, 0.0F, 0.0F));
      a(dem.u.c, 9474, e(0.0F, 0.0F, 1.0F, 0.0F));
      a(dem.u.a);
      a(dem.u.b);
      a(dem.u.c);
   }

   public static void y() {
      b(dem.u.a);
      b(dem.u.b);
      b(dem.u.c);
   }

   public static void z() {
      f(2983, a);
      a(a);
      f(2982, a);
      a(a);
   }

   @Deprecated
   public static void A() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      h.a.b();
   }

   @Deprecated
   public static void B() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      h.a.a();
   }

   @Deprecated
   public static void o(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != h.b) {
         h.b = _snowman;
         l(2917, _snowman);
      }
   }

   @Deprecated
   public static void a(float var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != h.c) {
         h.c = _snowman;
         GL11.glFogf(2914, _snowman);
      }
   }

   @Deprecated
   public static void b(float var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != h.d) {
         h.d = _snowman;
         GL11.glFogf(2915, _snowman);
      }
   }

   @Deprecated
   public static void c(float var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != h.e) {
         h.e = _snowman;
         GL11.glFogf(2916, _snowman);
      }
   }

   @Deprecated
   public static void a(int var0, float[] var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glFogfv(_snowman, _snowman);
   }

   @Deprecated
   public static void l(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glFogi(_snowman, _snowman);
   }

   public static void C() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      i.a.b();
   }

   public static void D() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      i.a.a();
   }

   public static void m(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPolygonMode(_snowman, _snowman);
   }

   public static void E() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      j.a.b();
   }

   public static void F() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      j.a.a();
   }

   public static void G() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      j.b.b();
   }

   public static void H() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      j.b.a();
   }

   public static void a(float var0, float var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != j.c || _snowman != j.d) {
         j.c = _snowman;
         j.d = _snowman;
         GL11.glPolygonOffset(_snowman, _snowman);
      }
   }

   public static void I() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      k.a.b();
   }

   public static void J() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      k.a.a();
   }

   public static void p(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != k.b) {
         k.b = _snowman;
         GL11.glLogicOp(_snowman);
      }
   }

   @Deprecated
   public static void a(dem.u var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      c(_snowman).a.b();
   }

   @Deprecated
   public static void b(dem.u var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      c(_snowman).a.a();
   }

   @Deprecated
   public static void a(dem.u var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      dem.v _snowman = c(_snowman);
      if (_snowman != _snowman.c) {
         _snowman.c = _snowman;
         GL11.glTexGeni(_snowman.b, 9472, _snowman);
      }
   }

   @Deprecated
   public static void a(dem.u var0, int var1, FloatBuffer var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTexGenfv(c(_snowman).b, _snowman, _snowman);
   }

   @Deprecated
   private static dem.v c(dem.u var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      switch (_snowman) {
         case a:
            return l.a;
         case b:
            return l.b;
         case c:
            return l.c;
         case d:
            return l.d;
         default:
            return l.a;
      }
   }

   public static void q(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (p != _snowman - 33984) {
         p = _snowman - 33984;
         m(_snowman);
      }
   }

   public static void K() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      q[p].a.b();
   }

   public static void L() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      q[p].a.a();
   }

   @Deprecated
   public static void a(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTexEnvi(_snowman, _snowman, _snowman);
   }

   public static void a(int var0, int var1, float var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexParameterf(_snowman, _snowman, _snowman);
   }

   public static void b(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexParameteri(_snowman, _snowman, _snowman);
   }

   public static int c(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isInInitPhase);
      return GL11.glGetTexLevelParameteri(_snowman, _snowman, _snowman);
   }

   public static int M() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL11.glGenTextures();
   }

   public static void a(int[] var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glGenTextures(_snowman);
   }

   public static void r(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glDeleteTextures(_snowman);

      for (dem.x _snowman : q) {
         if (_snowman.b == _snowman) {
            _snowman.b = -1;
         }
      }
   }

   public static void b(int[] var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);

      for (dem.x _snowman : q) {
         for (int _snowmanx : _snowman) {
            if (_snowman.b == _snowmanx) {
               _snowman.b = -1;
            }
         }
      }

      GL11.glDeleteTextures(_snowman);
   }

   public static void s(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (_snowman != q[p].b) {
         q[p].b = _snowman;
         GL11.glBindTexture(3553, _snowman);
      }
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, @Nullable IntBuffer var8) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexImage2D(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, long var8) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glTexSubImage2D(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(int var0, int var1, int var2, int var3, long var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glGetTexImage(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void t(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (_snowman != r) {
         r = _snowman;
         GL11.glShadeModel(_snowman);
      }
   }

   @Deprecated
   public static void N() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      s.b();
   }

   @Deprecated
   public static void O() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      s.a();
   }

   public static void d(int var0, int var1, int var2, int var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      dem.y.a.b = _snowman;
      dem.y.a.c = _snowman;
      dem.y.a.d = _snowman;
      dem.y.a.e = _snowman;
      GL11.glViewport(_snowman, _snowman, _snowman, _snowman);
   }

   public static void a(boolean var0, boolean var1, boolean var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != t.a || _snowman != t.b || _snowman != t.c || _snowman != t.d) {
         t.a = _snowman;
         t.b = _snowman;
         t.c = _snowman;
         t.d = _snowman;
         GL11.glColorMask(_snowman, _snowman, _snowman, _snowman);
      }
   }

   public static void d(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != m.a.a || _snowman != m.a.b || _snowman != m.a.c) {
         m.a.a = _snowman;
         m.a.b = _snowman;
         m.a.c = _snowman;
         GL11.glStencilFunc(_snowman, _snowman, _snowman);
      }
   }

   public static void u(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != m.b) {
         m.b = _snowman;
         GL11.glStencilMask(_snowman);
      }
   }

   public static void e(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != m.c || _snowman != m.d || _snowman != m.e) {
         m.c = _snowman;
         m.d = _snowman;
         m.e = _snowman;
         GL11.glStencilOp(_snowman, _snowman, _snowman);
      }
   }

   public static void a(double var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glClearDepth(_snowman);
   }

   public static void b(float var0, float var1, float var2, float var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glClearColor(_snowman, _snowman, _snowman, _snowman);
   }

   public static void v(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glClearStencil(_snowman);
   }

   public static void a(int var0, boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glClear(_snowman);
      if (_snowman) {
         T();
      }
   }

   @Deprecated
   public static void w(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glMatrixMode(_snowman);
   }

   @Deprecated
   public static void P() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glLoadIdentity();
   }

   @Deprecated
   public static void Q() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPushMatrix();
   }

   @Deprecated
   public static void R() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPopMatrix();
   }

   @Deprecated
   public static void f(int var0, FloatBuffer var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glGetFloatv(_snowman, _snowman);
   }

   @Deprecated
   public static void a(double var0, double var2, double var4, double var6, double var8, double var10) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glOrtho(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void c(float var0, float var1, float var2, float var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glRotatef(_snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void b(float var0, float var1, float var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glScalef(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void a(double var0, double var2, double var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glScaled(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void c(float var0, float var1, float var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTranslatef(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void b(double var0, double var2, double var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTranslated(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void a(FloatBuffer var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glMultMatrixf(_snowman);
   }

   @Deprecated
   public static void a(b var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      _snowman.a(a);
      ((Buffer)a).rewind();
      a(a);
   }

   @Deprecated
   public static void d(float var0, float var1, float var2, float var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      if (_snowman != u.a || _snowman != u.b || _snowman != u.c || _snowman != u.d) {
         u.a = _snowman;
         u.b = _snowman;
         u.c = _snowman;
         u.d = _snowman;
         GL11.glColor4f(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Deprecated
   public static void S() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      u.a = -1.0F;
      u.b = -1.0F;
      u.c = -1.0F;
      u.d = -1.0F;
   }

   @Deprecated
   public static void a(int var0, int var1, long var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glNormalPointer(_snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void a(int var0, int var1, int var2, long var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glTexCoordPointer(_snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void b(int var0, int var1, int var2, long var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glVertexPointer(_snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void c(int var0, int var1, int var2, long var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glColorPointer(_snowman, _snowman, _snowman, _snowman);
   }

   public static void a(int var0, int var1, int var2, boolean var3, int var4, long var5) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glVertexAttribPointer(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Deprecated
   public static void x(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glEnableClientState(_snowman);
   }

   @Deprecated
   public static void y(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glDisableClientState(_snowman);
   }

   public static void z(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glEnableVertexAttribArray(_snowman);
   }

   public static void A(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL20.glEnableVertexAttribArray(_snowman);
   }

   public static void f(int var0, int var1, int var2) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glDrawArrays(_snowman, _snowman, _snowman);
   }

   public static void d(float var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glLineWidth(_snowman);
   }

   public static void n(int var0, int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      GL11.glPixelStorei(_snowman, _snowman);
   }

   public static void b(int var0, float var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glPixelTransferf(_snowman, _snowman);
   }

   public static void a(int var0, int var1, int var2, int var3, int var4, int var5, ByteBuffer var6) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      GL11.glReadPixels(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static int T() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL11.glGetError();
   }

   public static String B(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      return GL11.glGetString(_snowman);
   }

   public static int C(int var0) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      return GL11.glGetInteger(_snowman);
   }

   public static boolean U() {
      return w != dem.k.c;
   }

   @Deprecated
   static class a {
      public final dem.c a = new dem.c(3008);
      public int b = 519;
      public float c = -1.0F;

      private a() {
      }
   }

   static class b {
      public final dem.c a = new dem.c(3042);
      public int b = 1;
      public int c = 0;
      public int d = 1;
      public int e = 0;

      private b() {
      }
   }

   static class c {
      private final int a;
      private boolean b;

      public c(int var1) {
         this.a = _snowman;
      }

      public void a() {
         this.a(false);
      }

      public void b() {
         this.a(true);
      }

      public void a(boolean var1) {
         RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
         if (_snowman != this.b) {
            this.b = _snowman;
            if (_snowman) {
               GL11.glEnable(this.a);
            } else {
               GL11.glDisable(this.a);
            }
         }
      }
   }

   @Deprecated
   static class d {
      public float a = 1.0F;
      public float b = 1.0F;
      public float c = 1.0F;
      public float d = 1.0F;

      public d() {
         this(1.0F, 1.0F, 1.0F, 1.0F);
      }

      public d(float var1, float var2, float var3, float var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }
   }

   static class e {
      public final dem.c a = new dem.c(3058);
      public int b = 5379;

      private e() {
      }
   }

   static class f {
      public boolean a = true;
      public boolean b = true;
      public boolean c = true;
      public boolean d = true;

      private f() {
      }
   }

   @Deprecated
   static class g {
      public final dem.c a = new dem.c(2903);
      public int b = 1032;
      public int c = 5634;

      private g() {
      }
   }

   static class h {
      public final dem.c a = new dem.c(2884);
      public int b = 1029;

      private h() {
      }
   }

   static class i {
      public final dem.c a = new dem.c(2929);
      public boolean b = true;
      public int c = 513;

      private i() {
      }
   }

   public static enum j {
      a(32771),
      b(32769),
      c(772),
      d(774),
      e(1),
      f(32772),
      g(32770),
      h(773),
      i(775),
      j(771),
      k(769),
      l(770),
      m(768),
      n(0);

      public final int o;

      private j(int var3) {
         this.o = _snowman;
      }
   }

   public static enum k {
      a,
      b,
      c;

      private k() {
      }
   }

   public static enum l {
      a,
      b,
      c;

      private l() {
      }
   }

   @Deprecated
   public static enum m {
      a(9729),
      b(2048),
      c(2049);

      public final int d;

      private m(int var3) {
         this.d = _snowman;
      }
   }

   @Deprecated
   static class n {
      public final dem.c a = new dem.c(2912);
      public int b = 2048;
      public float c = 1.0F;
      public float d;
      public float e = 1.0F;

      private n() {
      }
   }

   public static enum o {
      a(5377),
      b(5380),
      c(5378),
      d(5376),
      e(5379),
      f(5388),
      g(5385),
      h(5386),
      i(5390),
      j(5381),
      k(5384),
      l(5383),
      m(5389),
      n(5387),
      o(5391),
      p(5382);

      public final int q;

      private o(int var3) {
         this.q = _snowman;
      }
   }

   static class p {
      public final dem.c a = new dem.c(32823);
      public final dem.c b = new dem.c(10754);
      public float c;
      public float d;

      private p() {
      }
   }

   static class q {
      public final dem.c a = new dem.c(3089);

      private q() {
      }
   }

   public static enum r {
      a(32771),
      b(32769),
      c(772),
      d(774),
      e(1),
      f(32772),
      g(32770),
      h(773),
      i(775),
      j(771),
      k(769),
      l(770),
      m(776),
      n(768),
      o(0);

      public final int p;

      private r(int var3) {
         this.p = _snowman;
      }
   }

   static class s {
      public int a = 519;
      public int b;
      public int c = -1;

      private s() {
      }
   }

   static class t {
      public final dem.s a = new dem.s();
      public int b = -1;
      public int c = 7680;
      public int d = 7680;
      public int e = 7680;

      private t() {
      }
   }

   @Deprecated
   public static enum u {
      a,
      b,
      c,
      d;

      private u() {
      }
   }

   @Deprecated
   static class v {
      public final dem.c a;
      public final int b;
      public int c = -1;

      public v(int var1, int var2) {
         this.b = _snowman;
         this.a = new dem.c(_snowman);
      }
   }

   @Deprecated
   static class w {
      public final dem.v a = new dem.v(8192, 3168);
      public final dem.v b = new dem.v(8193, 3169);
      public final dem.v c = new dem.v(8194, 3170);
      public final dem.v d = new dem.v(8195, 3171);

      private w() {
      }
   }

   static class x {
      public final dem.c a = new dem.c(3553);
      public int b;

      private x() {
      }
   }

   public static enum y {
      a;

      protected int b;
      protected int c;
      protected int d;
      protected int e;

      private y() {
      }
   }
}
