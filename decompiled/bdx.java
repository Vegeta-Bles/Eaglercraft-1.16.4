import java.util.EnumSet;
import java.util.Random;

public class bdx extends bdq {
   private bdx.b b;

   public bdx(aqe<? extends bdx> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      this.b = new bdx.b(this);
      this.bk.a(1, new avp(this));
      this.bk.a(3, this.b);
      this.bk.a(4, new awf(this, 1.0, false));
      this.bk.a(5, new bdx.a(this));
      this.bl.a(1, new axp(this).a());
      this.bl.a(2, new axq<>(this, bfw.class, true));
   }

   @Override
   public double bb() {
      return 0.1;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 0.13F;
   }

   public static ark.a m() {
      return bdq.eR().a(arl.a, 8.0).a(arl.d, 0.25).a(arl.f, 1.0);
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   protected adp I() {
      return adq.no;
   }

   @Override
   protected adp e(apk var1) {
      return adq.nq;
   }

   @Override
   protected adp dq() {
      return adq.np;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.nr, 0.15F, 1.0F);
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         if ((_snowman instanceof apl || _snowman == apk.o) && this.b != null) {
            this.b.g();
         }

         return super.a(_snowman, _snowman);
      }
   }

   @Override
   public void j() {
      this.aA = this.p;
      super.j();
   }

   @Override
   public void n(float var1) {
      this.p = _snowman;
      super.n(_snowman);
   }

   @Override
   public float a(fx var1, brz var2) {
      return bxp.h(_snowman.d_(_snowman.c())) ? 10.0F : super.a(_snowman, _snowman);
   }

   public static boolean b(aqe<bdx> var0, bry var1, aqp var2, fx var3, Random var4) {
      if (c(_snowman, _snowman, _snowman, _snowman, _snowman)) {
         bfw _snowman = _snowman.a((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, 5.0, true);
         return _snowman == null;
      } else {
         return false;
      }
   }

   @Override
   public aqq dC() {
      return aqq.c;
   }

   static class a extends awt {
      private gc h;
      private boolean i;

      public a(bdx var1) {
         super(_snowman, 1.0, 10);
         this.a(EnumSet.of(avv.a.a));
      }

      @Override
      public boolean a() {
         if (this.a.A() != null) {
            return false;
         } else if (!this.a.x().m()) {
            return false;
         } else {
            Random _snowman = this.a.cY();
            if (this.a.l.V().b(brt.b) && _snowman.nextInt(10) == 0) {
               this.h = gc.a(_snowman);
               fx _snowmanx = new fx(this.a.cD(), this.a.cE() + 0.5, this.a.cH()).a(this.h);
               ceh _snowmanxx = this.a.l.d_(_snowmanx);
               if (bxp.h(_snowmanxx)) {
                  this.i = true;
                  return true;
               }
            }

            this.i = false;
            return super.a();
         }
      }

      @Override
      public boolean b() {
         return this.i ? false : super.b();
      }

      @Override
      public void c() {
         if (!this.i) {
            super.c();
         } else {
            bry _snowman = this.a.l;
            fx _snowmanx = new fx(this.a.cD(), this.a.cE() + 0.5, this.a.cH()).a(this.h);
            ceh _snowmanxx = _snowman.d_(_snowmanx);
            if (bxp.h(_snowmanxx)) {
               _snowman.a(_snowmanx, bxp.c(_snowmanxx.b()), 3);
               this.a.G();
               this.a.ad();
            }
         }
      }
   }

   static class b extends avv {
      private final bdx a;
      private int b;

      public b(bdx var1) {
         this.a = _snowman;
      }

      public void g() {
         if (this.b == 0) {
            this.b = 20;
         }
      }

      @Override
      public boolean a() {
         return this.b > 0;
      }

      @Override
      public void e() {
         this.b--;
         if (this.b <= 0) {
            brx _snowman = this.a.l;
            Random _snowmanx = this.a.cY();
            fx _snowmanxx = this.a.cB();

            for (int _snowmanxxx = 0; _snowmanxxx <= 5 && _snowmanxxx >= -5; _snowmanxxx = (_snowmanxxx <= 0 ? 1 : 0) - _snowmanxxx) {
               for (int _snowmanxxxx = 0; _snowmanxxxx <= 10 && _snowmanxxxx >= -10; _snowmanxxxx = (_snowmanxxxx <= 0 ? 1 : 0) - _snowmanxxxx) {
                  for (int _snowmanxxxxx = 0; _snowmanxxxxx <= 10 && _snowmanxxxxx >= -10; _snowmanxxxxx = (_snowmanxxxxx <= 0 ? 1 : 0) - _snowmanxxxxx) {
                     fx _snowmanxxxxxx = _snowmanxx.b(_snowmanxxxx, _snowmanxxx, _snowmanxxxxx);
                     ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxxxxx);
                     buo _snowmanxxxxxxxx = _snowmanxxxxxxx.b();
                     if (_snowmanxxxxxxxx instanceof bxp) {
                        if (_snowman.V().b(brt.b)) {
                           _snowman.a(_snowmanxxxxxx, true, this.a);
                        } else {
                           _snowman.a(_snowmanxxxxxx, ((bxp)_snowmanxxxxxxxx).c().n(), 3);
                        }

                        if (_snowmanx.nextBoolean()) {
                           return;
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
