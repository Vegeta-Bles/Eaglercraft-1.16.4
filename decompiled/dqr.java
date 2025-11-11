import com.mojang.blaze3d.systems.RenderSystem;

public class dqr extends dpp<bjg> {
   private static final vk A = new vk("textures/gui/container/villager2.png");
   private static final nr B = new of("merchant.trades");
   private static final nr C = new oe(" - ");
   private static final nr D = new of("merchant.deprecated");
   private int E;
   private final dqr.a[] F = new dqr.a[7];
   private int G;
   private boolean H;

   public dqr(bjg var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
      this.b = 276;
      this.r = 107;
   }

   private void i() {
      this.t.d(this.E);
      this.t.g(this.E);
      this.i.w().a(new th(this.E));
   }

   @Override
   protected void b() {
      super.b();
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;
      int _snowmanxx = _snowmanx + 16 + 2;

      for (int _snowmanxxx = 0; _snowmanxxx < 7; _snowmanxxx++) {
         this.F[_snowmanxxx] = this.a(new dqr.a(_snowman + 5, _snowmanxx, _snowmanxxx, var1x -> {
            if (var1x instanceof dqr.a) {
               this.E = ((dqr.a)var1x).a() + this.G;
               this.i();
            }
         }));
         _snowmanxx += 20;
      }
   }

   @Override
   protected void b(dfm var1, int var2, int var3) {
      int _snowman = this.t.g();
      if (_snowman > 0 && _snowman <= 5 && this.t.j()) {
         nr _snowmanx = this.d.e().a(C).a(new of("merchant.level." + _snowman));
         int _snowmanxx = this.o.a(_snowmanx);
         int _snowmanxxx = 49 + this.b / 2 - _snowmanxx / 2;
         this.o.b(_snowman, _snowmanx, (float)_snowmanxxx, 6.0F, 4210752);
      } else {
         this.o.b(_snowman, this.d, (float)(49 + this.b / 2 - this.o.a(this.d) / 2), 6.0F, 4210752);
      }

      this.o.b(_snowman, this.u.d(), (float)this.r, (float)this.s, 4210752);
      int _snowmanx = this.o.a(B);
      this.o.b(_snowman, B, (float)(5 - _snowmanx / 2 + 48), 6.0F, 4210752);
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(A);
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;
      a(_snowman, _snowman, _snowmanx, this.v(), 0.0F, 0.0F, this.b, this.c, 256, 512);
      bqw _snowmanxx = this.t.i();
      if (!_snowmanxx.isEmpty()) {
         int _snowmanxxx = this.E;
         if (_snowmanxxx < 0 || _snowmanxxx >= _snowmanxx.size()) {
            return;
         }

         bqv _snowmanxxxx = _snowmanxx.get(_snowmanxxx);
         if (_snowmanxxxx.p()) {
            this.i.M().a(A);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            a(_snowman, this.w + 83 + 99, this.x + 35, this.v(), 311.0F, 0.0F, 28, 21, 256, 512);
         }
      }
   }

   private void a(dfm var1, int var2, int var3, bqv var4) {
      this.i.M().a(A);
      int _snowman = this.t.g();
      int _snowmanx = this.t.e();
      if (_snowman < 5) {
         a(_snowman, _snowman + 136, _snowman + 16, this.v(), 0.0F, 186.0F, 102, 5, 256, 512);
         int _snowmanxx = bfk.b(_snowman);
         if (_snowmanx >= _snowmanxx && bfk.d(_snowman)) {
            int _snowmanxxx = 100;
            float _snowmanxxxx = 100.0F / (float)(bfk.c(_snowman) - _snowmanxx);
            int _snowmanxxxxx = Math.min(afm.d(_snowmanxxxx * (float)(_snowmanx - _snowmanxx)), 100);
            a(_snowman, _snowman + 136, _snowman + 16, this.v(), 0.0F, 191.0F, _snowmanxxxxx + 1, 5, 256, 512);
            int _snowmanxxxxxx = this.t.f();
            if (_snowmanxxxxxx > 0) {
               int _snowmanxxxxxxx = Math.min(afm.d((float)_snowmanxxxxxx * _snowmanxxxx), 100 - _snowmanxxxxx);
               a(_snowman, _snowman + 136 + _snowmanxxxxx + 1, _snowman + 16 + 1, this.v(), 2.0F, 182.0F, _snowmanxxxxxxx, 3, 256, 512);
            }
         }
      }
   }

   private void a(dfm var1, int var2, int var3, bqw var4) {
      int _snowman = _snowman.size() + 1 - 7;
      if (_snowman > 1) {
         int _snowmanx = 139 - (27 + (_snowman - 1) * 139 / _snowman);
         int _snowmanxx = 1 + _snowmanx / _snowman + 139 / _snowman;
         int _snowmanxxx = 113;
         int _snowmanxxxx = Math.min(113, this.G * _snowmanxx);
         if (this.G == _snowman - 1) {
            _snowmanxxxx = 113;
         }

         a(_snowman, _snowman + 94, _snowman + 18 + _snowmanxxxx, this.v(), 0.0F, 199.0F, 6, 27, 256, 512);
      } else {
         a(_snowman, _snowman + 94, _snowman + 18, this.v(), 6.0F, 199.0F, 6, 27, 256, 512);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
      bqw _snowman = this.t.i();
      if (!_snowman.isEmpty()) {
         int _snowmanx = (this.k - this.b) / 2;
         int _snowmanxx = (this.l - this.c) / 2;
         int _snowmanxxx = _snowmanxx + 16 + 1;
         int _snowmanxxxx = _snowmanx + 5 + 5;
         RenderSystem.pushMatrix();
         RenderSystem.enableRescaleNormal();
         this.i.M().a(A);
         this.a(_snowman, _snowmanx, _snowmanxx, _snowman);
         int _snowmanxxxxx = 0;

         for (bqv _snowmanxxxxxx : _snowman) {
            if (!this.a(_snowman.size()) || _snowmanxxxxx >= this.G && _snowmanxxxxx < 7 + this.G) {
               bmb _snowmanxxxxxxx = _snowmanxxxxxx.a();
               bmb _snowmanxxxxxxxx = _snowmanxxxxxx.b();
               bmb _snowmanxxxxxxxxx = _snowmanxxxxxx.c();
               bmb _snowmanxxxxxxxxxx = _snowmanxxxxxx.d();
               this.j.b = 100.0F;
               int _snowmanxxxxxxxxxxx = _snowmanxxx + 2;
               this.a(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxxxxxxx);
               if (!_snowmanxxxxxxxxx.a()) {
                  this.j.c(_snowmanxxxxxxxxx, _snowmanx + 5 + 35, _snowmanxxxxxxxxxxx);
                  this.j.a(this.o, _snowmanxxxxxxxxx, _snowmanx + 5 + 35, _snowmanxxxxxxxxxxx);
               }

               this.a(_snowman, _snowmanxxxxxx, _snowmanx, _snowmanxxxxxxxxxxx);
               this.j.c(_snowmanxxxxxxxxxx, _snowmanx + 5 + 68, _snowmanxxxxxxxxxxx);
               this.j.a(this.o, _snowmanxxxxxxxxxx, _snowmanx + 5 + 68, _snowmanxxxxxxxxxxx);
               this.j.b = 0.0F;
               _snowmanxxx += 20;
               _snowmanxxxxx++;
            } else {
               _snowmanxxxxx++;
            }
         }

         int _snowmanxxxxxxx = this.E;
         bqv _snowmanxxxxxxxx = _snowman.get(_snowmanxxxxxxx);
         if (this.t.j()) {
            this.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxxxxxxx);
         }

         if (_snowmanxxxxxxxx.p() && this.a(186, 35, 22, 21, (double)_snowman, (double)_snowman) && this.t.h()) {
            this.b(_snowman, D, _snowman, _snowman);
         }

         for (dqr.a _snowmanxxxxxxxxx : this.F) {
            if (_snowmanxxxxxxxxx.g()) {
               _snowmanxxxxxxxxx.a(_snowman, _snowman, _snowman);
            }

            _snowmanxxxxxxxxx.p = _snowmanxxxxxxxxx.a < this.t.i().size();
         }

         RenderSystem.popMatrix();
         RenderSystem.enableDepthTest();
      }

      this.a(_snowman, _snowman, _snowman);
   }

   private void a(dfm var1, bqv var2, int var3, int var4) {
      RenderSystem.enableBlend();
      this.i.M().a(A);
      if (_snowman.p()) {
         a(_snowman, _snowman + 5 + 35 + 20, _snowman + 3, this.v(), 25.0F, 171.0F, 10, 9, 256, 512);
      } else {
         a(_snowman, _snowman + 5 + 35 + 20, _snowman + 3, this.v(), 15.0F, 171.0F, 10, 9, 256, 512);
      }
   }

   private void a(dfm var1, bmb var2, bmb var3, int var4, int var5) {
      this.j.c(_snowman, _snowman, _snowman);
      if (_snowman.E() == _snowman.E()) {
         this.j.a(this.o, _snowman, _snowman, _snowman);
      } else {
         this.j.a(this.o, _snowman, _snowman, _snowman, _snowman.E() == 1 ? "1" : null);
         this.j.a(this.o, _snowman, _snowman + 14, _snowman, _snowman.E() == 1 ? "1" : null);
         this.i.M().a(A);
         this.d(this.v() + 300);
         a(_snowman, _snowman + 7, _snowman + 12, this.v(), 0.0F, 176.0F, 9, 2, 256, 512);
         this.d(this.v() - 300);
      }
   }

   private boolean a(int var1) {
      return _snowman > 7;
   }

   @Override
   public boolean a(double var1, double var3, double var5) {
      int _snowman = this.t.i().size();
      if (this.a(_snowman)) {
         int _snowmanx = _snowman - 7;
         this.G = (int)((double)this.G - _snowman);
         this.G = afm.a(this.G, 0, _snowmanx);
      }

      return true;
   }

   @Override
   public boolean a(double var1, double var3, int var5, double var6, double var8) {
      int _snowman = this.t.i().size();
      if (this.H) {
         int _snowmanx = this.x + 18;
         int _snowmanxx = _snowmanx + 139;
         int _snowmanxxx = _snowman - 7;
         float _snowmanxxxx = ((float)_snowman - (float)_snowmanx - 13.5F) / ((float)(_snowmanxx - _snowmanx) - 27.0F);
         _snowmanxxxx = _snowmanxxxx * (float)_snowmanxxx + 0.5F;
         this.G = afm.a((int)_snowmanxxxx, 0, _snowmanxxx);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      this.H = false;
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;
      if (this.a(this.t.i().size()) && _snowman > (double)(_snowman + 94) && _snowman < (double)(_snowman + 94 + 6) && _snowman > (double)(_snowmanx + 18) && _snowman <= (double)(_snowmanx + 18 + 139 + 1)) {
         this.H = true;
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   class a extends dlj {
      final int a;

      public a(int var2, int var3, int var4, dlj.a var5) {
         super(_snowman, _snowman, 89, 20, oe.d, _snowman);
         this.a = _snowman;
         this.p = false;
      }

      public int a() {
         return this.a;
      }

      @Override
      public void a(dfm var1, int var2, int var3) {
         if (this.n && dqr.this.t.i().size() > this.a + dqr.this.G) {
            if (_snowman < this.l + 20) {
               bmb _snowman = dqr.this.t.i().get(this.a + dqr.this.G).b();
               dqr.this.a(_snowman, _snowman, _snowman, _snowman);
            } else if (_snowman < this.l + 50 && _snowman > this.l + 30) {
               bmb _snowman = dqr.this.t.i().get(this.a + dqr.this.G).c();
               if (!_snowman.a()) {
                  dqr.this.a(_snowman, _snowman, _snowman, _snowman);
               }
            } else if (_snowman > this.l + 65) {
               bmb _snowman = dqr.this.t.i().get(this.a + dqr.this.G).d();
               dqr.this.a(_snowman, _snowman, _snowman, _snowman);
            }
         }
      }
   }
}
