import com.mojang.blaze3d.systems.RenderSystem;

public class deg {
   public int a;
   public int b;
   public int c;
   public int d;
   public final boolean e;
   public int f;
   private int i;
   private int j;
   public final float[] g;
   public int h;

   public deg(int var1, int var2, boolean var3, boolean var4) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.e = _snowman;
      this.f = -1;
      this.i = -1;
      this.j = -1;
      this.g = new float[4];
      this.g[0] = 1.0F;
      this.g[1] = 1.0F;
      this.g[2] = 1.0F;
      this.g[3] = 0.0F;
      this.a(_snowman, _snowman, _snowman);
   }

   public void a(int var1, int var2, boolean var3) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.d(_snowman, _snowman, _snowman));
      } else {
         this.d(_snowman, _snowman, _snowman);
      }
   }

   private void d(int var1, int var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      dem.m();
      if (this.f >= 0) {
         this.a();
      }

      this.b(_snowman, _snowman, _snowman);
      dem.h(dek.a, 0);
   }

   public void a() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.d();
      this.e();
      if (this.j > -1) {
         dex.a(this.j);
         this.j = -1;
      }

      if (this.i > -1) {
         dex.a(this.i);
         this.i = -1;
      }

      if (this.f > -1) {
         dem.h(dek.a, 0);
         dem.k(this.f);
         this.f = -1;
      }
   }

   public void a(deg var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      if (dem.U()) {
         dem.h(36008, _snowman.f);
         dem.h(36009, this.f);
         dem.a(0, 0, _snowman.a, _snowman.b, 0, 0, this.a, this.b, 256, 9728);
      } else {
         dem.h(dek.a, this.f);
         int _snowman = dem.r();
         if (_snowman != 0) {
            int _snowmanx = dem.t();
            dem.s(_snowman);
            dem.h(dek.a, _snowman.f);
            dem.a(3553, 0, 0, 0, 0, 0, Math.min(this.a, _snowman.a), Math.min(this.b, _snowman.b));
            dem.s(_snowmanx);
         }
      }

      dem.h(dek.a, 0);
   }

   public void b(int var1, int var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.c = _snowman;
      this.d = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.f = dem.s();
      this.i = dex.a();
      if (this.e) {
         this.j = dex.a();
         dem.s(this.j);
         dem.b(3553, 10241, 9728);
         dem.b(3553, 10240, 9728);
         dem.b(3553, 10242, 10496);
         dem.b(3553, 10243, 10496);
         dem.b(3553, 34892, 0);
         dem.a(3553, 0, 6402, this.a, this.b, 0, 6402, 5126, null);
      }

      this.a(9728);
      dem.s(this.i);
      dem.a(3553, 0, 32856, this.a, this.b, 0, 6408, 5121, null);
      dem.h(dek.a, this.f);
      dem.a(dek.a, dek.c, 3553, this.i, 0);
      if (this.e) {
         dem.a(dek.a, dek.d, 3553, this.j, 0);
      }

      this.b();
      this.b(_snowman);
      this.d();
   }

   public void a(int var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.h = _snowman;
      dem.s(this.i);
      dem.b(3553, 10241, _snowman);
      dem.b(3553, 10240, _snowman);
      dem.b(3553, 10242, 10496);
      dem.b(3553, 10243, 10496);
      dem.s(0);
   }

   public void b() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      int _snowman = dem.l(dek.a);
      if (_snowman != dek.e) {
         if (_snowman == dek.f) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
         } else if (_snowman == dek.g) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
         } else if (_snowman == dek.h) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
         } else if (_snowman == dek.i) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
         } else {
            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + _snowman);
         }
      }
   }

   public void c() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      dem.s(this.i);
   }

   public void d() {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      dem.s(0);
   }

   public void a(boolean var1) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.c(_snowman));
      } else {
         this.c(_snowman);
      }
   }

   private void c(boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      dem.h(dek.a, this.f);
      if (_snowman) {
         dem.d(0, 0, this.c, this.d);
      }
   }

   public void e() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> dem.h(dek.a, 0));
      } else {
         dem.h(dek.a, 0);
      }
   }

   public void a(float var1, float var2, float var3, float var4) {
      this.g[0] = _snowman;
      this.g[1] = _snowman;
      this.g[2] = _snowman;
      this.g[3] = _snowman;
   }

   public void a(int var1, int var2) {
      this.c(_snowman, _snowman, true);
   }

   public void c(int var1, int var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnGameThreadOrInit);
      if (!RenderSystem.isInInitPhase()) {
         RenderSystem.recordRenderCall(() -> this.e(_snowman, _snowman, _snowman));
      } else {
         this.e(_snowman, _snowman, _snowman);
      }
   }

   private void e(int var1, int var2, boolean var3) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThread);
      dem.a(true, true, true, false);
      dem.l();
      dem.a(false);
      dem.w(5889);
      dem.P();
      dem.a(0.0, (double)_snowman, (double)_snowman, 0.0, 1000.0, 3000.0);
      dem.w(5888);
      dem.P();
      dem.c(0.0F, 0.0F, -2000.0F);
      dem.d(0, 0, _snowman, _snowman);
      dem.K();
      dem.g();
      dem.d();
      if (_snowman) {
         dem.n();
         dem.h();
      }

      dem.d(1.0F, 1.0F, 1.0F, 1.0F);
      this.c();
      float _snowman = (float)_snowman;
      float _snowmanx = (float)_snowman;
      float _snowmanxx = (float)this.c / (float)this.a;
      float _snowmanxxx = (float)this.d / (float)this.b;
      dfo _snowmanxxxx = RenderSystem.renderThreadTesselator();
      dfh _snowmanxxxxx = _snowmanxxxx.c();
      _snowmanxxxxx.a(7, dfk.p);
      _snowmanxxxxx.a(0.0, (double)_snowmanx, 0.0).a(0.0F, 0.0F).a(255, 255, 255, 255).d();
      _snowmanxxxxx.a((double)_snowman, (double)_snowmanx, 0.0).a(_snowmanxx, 0.0F).a(255, 255, 255, 255).d();
      _snowmanxxxxx.a((double)_snowman, 0.0, 0.0).a(_snowmanxx, _snowmanxxx).a(255, 255, 255, 255).d();
      _snowmanxxxxx.a(0.0, 0.0, 0.0).a(0.0F, _snowmanxxx).a(255, 255, 255, 255).d();
      _snowmanxxxx.b();
      this.d();
      dem.a(true);
      dem.a(true, true, true, true);
   }

   public void b(boolean var1) {
      RenderSystem.assertThread(RenderSystem::isOnRenderThreadOrInit);
      this.a(true);
      dem.b(this.g[0], this.g[1], this.g[2], this.g[3]);
      int _snowman = 16384;
      if (this.e) {
         dem.a(1.0);
         _snowman |= 256;
      }

      dem.a(_snowman, _snowman);
      this.e();
   }

   public int f() {
      return this.i;
   }

   public int g() {
      return this.j;
   }
}
