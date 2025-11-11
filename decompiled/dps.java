import com.mojang.blaze3d.systems.RenderSystem;

public class dps extends dpp<bif> {
   private static final vk A = new vk("textures/gui/container/beacon.png");
   private static final nr B = new of("block.minecraft.beacon.primary");
   private static final nr C = new of("block.minecraft.beacon.secondary");
   private dps.b D;
   private boolean E;
   private aps F;
   private aps G;

   public dps(final bif var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
      this.b = 230;
      this.c = 219;
      _snowman.a(new bin() {
         @Override
         public void a(bic var1x, gj<bmb> var2) {
         }

         @Override
         public void a(bic var1x, int var2, bmb var3) {
         }

         @Override
         public void a(bic var1x, int var2, int var3) {
            dps.this.F = _snowman.f();
            dps.this.G = _snowman.g();
            dps.this.E = true;
         }
      });
   }

   @Override
   protected void b() {
      super.b();
      this.D = this.a(new dps.b(this.w + 164, this.x + 107));
      this.a(new dps.a(this.w + 190, this.x + 107));
      this.E = true;
      this.D.o = false;
   }

   @Override
   public void d() {
      super.d();
      int _snowman = this.t.e();
      if (this.E && _snowman >= 0) {
         this.E = false;

         for (int _snowmanx = 0; _snowmanx <= 2; _snowmanx++) {
            int _snowmanxx = cce.a[_snowmanx].length;
            int _snowmanxxx = _snowmanxx * 22 + (_snowmanxx - 1) * 2;

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx; _snowmanxxxx++) {
               aps _snowmanxxxxx = cce.a[_snowmanx][_snowmanxxxx];
               dps.c _snowmanxxxxxx = new dps.c(this.w + 76 + _snowmanxxxx * 24 - _snowmanxxx / 2, this.x + 22 + _snowmanx * 25, _snowmanxxxxx, true);
               this.a(_snowmanxxxxxx);
               if (_snowmanx >= _snowman) {
                  _snowmanxxxxxx.o = false;
               } else if (_snowmanxxxxx == this.F) {
                  _snowmanxxxxxx.e(true);
               }
            }
         }

         int _snowmanx = 3;
         int _snowmanxx = cce.a[3].length + 1;
         int _snowmanxxx = _snowmanxx * 22 + (_snowmanxx - 1) * 2;

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxx - 1; _snowmanxxxxx++) {
            aps _snowmanxxxxxx = cce.a[3][_snowmanxxxxx];
            dps.c _snowmanxxxxxxx = new dps.c(this.w + 167 + _snowmanxxxxx * 24 - _snowmanxxx / 2, this.x + 47, _snowmanxxxxxx, false);
            this.a(_snowmanxxxxxxx);
            if (3 >= _snowman) {
               _snowmanxxxxxxx.o = false;
            } else if (_snowmanxxxxxx == this.G) {
               _snowmanxxxxxxx.e(true);
            }
         }

         if (this.F != null) {
            dps.c _snowmanxxxxxx = new dps.c(this.w + 167 + (_snowmanxx - 1) * 24 - _snowmanxxx / 2, this.x + 47, this.F, false);
            this.a(_snowmanxxxxxx);
            if (3 >= _snowman) {
               _snowmanxxxxxx.o = false;
            } else if (this.F == this.G) {
               _snowmanxxxxxx.e(true);
            }
         }
      }

      this.D.o = this.t.h() && this.F != null;
   }

   @Override
   protected void b(dfm var1, int var2, int var3) {
      a(_snowman, this.o, B, 62, 10, 14737632);
      a(_snowman, this.o, C, 169, 10, 14737632);

      for (dlh _snowman : this.m) {
         if (_snowman.g()) {
            _snowman.a(_snowman, _snowman - this.w, _snowman - this.x);
            break;
         }
      }
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(A);
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      this.j.b = 100.0F;
      this.j.b(new bmb(bmd.kj), _snowman + 20, _snowmanx + 109);
      this.j.b(new bmb(bmd.oV), _snowman + 41, _snowmanx + 109);
      this.j.b(new bmb(bmd.kg), _snowman + 41 + 22, _snowmanx + 109);
      this.j.b(new bmb(bmd.ki), _snowman + 42 + 44, _snowmanx + 109);
      this.j.b(new bmb(bmd.kh), _snowman + 42 + 66, _snowmanx + 109);
      this.j.b = 0.0F;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman);
   }

   class a extends dps.e {
      public a(int var2, int var3) {
         super(_snowman, _snowman, 112, 220);
      }

      @Override
      public void b() {
         dps.this.i.s.e.a(new sl(dps.this.i.s.bp.b));
         dps.this.i.a(null);
      }

      @Override
      public void a(dfm var1, int var2, int var3) {
         dps.this.b(_snowman, nq.d, _snowman, _snowman);
      }
   }

   class b extends dps.e {
      public b(int var2, int var3) {
         super(_snowman, _snowman, 90, 220);
      }

      @Override
      public void b() {
         dps.this.i.w().a(new ti(aps.a(dps.this.F), aps.a(dps.this.G)));
         dps.this.i.s.e.a(new sl(dps.this.i.s.bp.b));
         dps.this.i.a(null);
      }

      @Override
      public void a(dfm var1, int var2, int var3) {
         dps.this.b(_snowman, nq.c, _snowman, _snowman);
      }
   }

   class c extends dps.d {
      private final aps b;
      private final ekc c;
      private final boolean d;
      private final nr e;

      public c(int var2, int var3, aps var4, boolean var5) {
         super(_snowman, _snowman);
         this.b = _snowman;
         this.c = djz.C().at().a(_snowman);
         this.d = _snowman;
         this.e = this.a(_snowman, _snowman);
      }

      private nr a(aps var1, boolean var2) {
         nx _snowman = new of(_snowman.c());
         if (!_snowman && _snowman != apw.j) {
            _snowman.c(" II");
         }

         return _snowman;
      }

      @Override
      public void b() {
         if (!this.a()) {
            if (this.d) {
               dps.this.F = this.b;
            } else {
               dps.this.G = this.b;
            }

            dps.this.m.clear();
            dps.this.e.clear();
            dps.this.b();
            dps.this.d();
         }
      }

      @Override
      public void a(dfm var1, int var2, int var3) {
         dps.this.b(_snowman, this.e, _snowman, _snowman);
      }

      @Override
      protected void a(dfm var1) {
         djz.C().M().a(this.c.m().g());
         a(_snowman, this.l + 2, this.m + 2, this.v(), 18, 18, this.c);
      }
   }

   abstract static class d extends dld {
      private boolean a;

      protected d(int var1, int var2) {
         super(_snowman, _snowman, 22, 22, oe.d);
      }

      @Override
      public void b(dfm var1, int var2, int var3, float var4) {
         djz.C().M().a(dps.A);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int _snowman = 219;
         int _snowmanx = 0;
         if (!this.o) {
            _snowmanx += this.j * 2;
         } else if (this.a) {
            _snowmanx += this.j * 1;
         } else if (this.g()) {
            _snowmanx += this.j * 3;
         }

         this.b(_snowman, this.l, this.m, _snowmanx, 219, this.j, this.k);
         this.a(_snowman);
      }

      protected abstract void a(dfm var1);

      public boolean a() {
         return this.a;
      }

      public void e(boolean var1) {
         this.a = _snowman;
      }
   }

   abstract static class e extends dps.d {
      private final int a;
      private final int b;

      protected e(int var1, int var2, int var3, int var4) {
         super(_snowman, _snowman);
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      protected void a(dfm var1) {
         this.b(_snowman, this.l + 2, this.m + 2, this.a, this.b, 18, 18);
      }
   }
}
