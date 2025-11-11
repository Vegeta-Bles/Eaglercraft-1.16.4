import java.util.List;
import javax.annotation.Nullable;

public class bdj extends bea {
   private bas bo;

   public bdj(aqe<? extends bdj> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 10;
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(0, new avp(this));
      this.bk.a(1, new bdj.b());
      this.bk.a(2, new avd<>(this, bfw.class, 8.0F, 0.6, 1.0));
      this.bk.a(4, new bdj.c());
      this.bk.a(5, new bdj.a());
      this.bk.a(6, new bdj.d());
      this.bk.a(8, new awt(this, 0.6));
      this.bk.a(9, new awd(this, bfw.class, 3.0F, 1.0F));
      this.bk.a(10, new awd(this, aqn.class, 8.0F));
      this.bl.a(1, new axp(this, bhc.class).a());
      this.bl.a(2, new axq<>(this, bfw.class, true).a(300));
      this.bl.a(3, new axq<>(this, bfe.class, false).a(300));
      this.bl.a(3, new axq<>(this, bai.class, false));
   }

   public static ark.a eK() {
      return bdq.eR().a(arl.d, 0.5).a(arl.b, 12.0).a(arl.a, 24.0);
   }

   @Override
   protected void e() {
      super.e();
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
   }

   @Override
   public adp eL() {
      return adq.dO;
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
   }

   @Override
   protected void N() {
      super.N();
   }

   @Override
   public boolean r(aqa var1) {
      if (_snowman == null) {
         return false;
      } else if (_snowman == this) {
         return true;
      } else if (super.r(_snowman)) {
         return true;
      } else if (_snowman instanceof bee) {
         return this.r(((bee)_snowman).eK());
      } else {
         return _snowman instanceof aqm && ((aqm)_snowman).dC() == aqq.d ? this.bG() == null && _snowman.bG() == null : false;
      }
   }

   @Override
   protected adp I() {
      return adq.dM;
   }

   @Override
   protected adp dq() {
      return adq.dP;
   }

   @Override
   protected adp e(apk var1) {
      return adq.dR;
   }

   private void a(@Nullable bas var1) {
      this.bo = _snowman;
   }

   @Nullable
   private bas fg() {
      return this.bo;
   }

   @Override
   protected adp eM() {
      return adq.dN;
   }

   @Override
   public void a(int var1, boolean var2) {
   }

   class a extends bea.c {
      private a() {
      }

      @Override
      protected int g() {
         return 40;
      }

      @Override
      protected int h() {
         return 100;
      }

      @Override
      protected void j() {
         aqm _snowman = bdj.this.A();
         double _snowmanx = Math.min(_snowman.cE(), bdj.this.cE());
         double _snowmanxx = Math.max(_snowman.cE(), bdj.this.cE()) + 1.0;
         float _snowmanxxx = (float)afm.d(_snowman.cH() - bdj.this.cH(), _snowman.cD() - bdj.this.cD());
         if (bdj.this.h((aqa)_snowman) < 9.0) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < 5; _snowmanxxxx++) {
               float _snowmanxxxxx = _snowmanxxx + (float)_snowmanxxxx * (float) Math.PI * 0.4F;
               this.a(bdj.this.cD() + (double)afm.b(_snowmanxxxxx) * 1.5, bdj.this.cH() + (double)afm.a(_snowmanxxxxx) * 1.5, _snowmanx, _snowmanxx, _snowmanxxxxx, 0);
            }

            for (int _snowmanxxxx = 0; _snowmanxxxx < 8; _snowmanxxxx++) {
               float _snowmanxxxxx = _snowmanxxx + (float)_snowmanxxxx * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
               this.a(bdj.this.cD() + (double)afm.b(_snowmanxxxxx) * 2.5, bdj.this.cH() + (double)afm.a(_snowmanxxxxx) * 2.5, _snowmanx, _snowmanxx, _snowmanxxxxx, 3);
            }
         } else {
            for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
               double _snowmanxxxxx = 1.25 * (double)(_snowmanxxxx + 1);
               int _snowmanxxxxxx = 1 * _snowmanxxxx;
               this.a(bdj.this.cD() + (double)afm.b(_snowmanxxx) * _snowmanxxxxx, bdj.this.cH() + (double)afm.a(_snowmanxxx) * _snowmanxxxxx, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxxxx);
            }
         }
      }

      private void a(double var1, double var3, double var5, double var7, float var9, int var10) {
         fx _snowman = new fx(_snowman, _snowman, _snowman);
         boolean _snowmanx = false;
         double _snowmanxx = 0.0;

         do {
            fx _snowmanxxx = _snowman.c();
            ceh _snowmanxxxx = bdj.this.l.d_(_snowmanxxx);
            if (_snowmanxxxx.d(bdj.this.l, _snowmanxxx, gc.b)) {
               if (!bdj.this.l.w(_snowman)) {
                  ceh _snowmanxxxxx = bdj.this.l.d_(_snowman);
                  ddh _snowmanxxxxxx = _snowmanxxxxx.k(bdj.this.l, _snowman);
                  if (!_snowmanxxxxxx.b()) {
                     _snowmanxx = _snowmanxxxxxx.c(gc.a.b);
                  }
               }

               _snowmanx = true;
               break;
            }

            _snowman = _snowman.c();
         } while (_snowman.v() >= afm.c(_snowman) - 1);

         if (_snowmanx) {
            bdj.this.l.c(new bge(bdj.this.l, _snowman, (double)_snowman.v() + _snowmanxx, _snowman, _snowman, _snowman, bdj.this));
         }
      }

      @Override
      protected adp k() {
         return adq.dS;
      }

      @Override
      protected bea.a l() {
         return bea.a.c;
      }
   }

   class b extends bea.b {
      private b() {
      }

      @Override
      public void e() {
         if (bdj.this.A() != null) {
            bdj.this.t().a(bdj.this.A(), (float)bdj.this.Q(), (float)bdj.this.O());
         } else if (bdj.this.fg() != null) {
            bdj.this.t().a(bdj.this.fg(), (float)bdj.this.Q(), (float)bdj.this.O());
         }
      }
   }

   class c extends bea.c {
      private final azg e = new azg().a(16.0).c().e().a().b();

      private c() {
      }

      @Override
      public boolean a() {
         if (!super.a()) {
            return false;
         } else {
            int _snowman = bdj.this.l.a(bee.class, this.e, bdj.this, bdj.this.cc().g(16.0)).size();
            return bdj.this.J.nextInt(8) + 1 > _snowman;
         }
      }

      @Override
      protected int g() {
         return 100;
      }

      @Override
      protected int h() {
         return 340;
      }

      @Override
      protected void j() {
         aag _snowman = (aag)bdj.this.l;

         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            fx _snowmanxx = bdj.this.cB().b(-2 + bdj.this.J.nextInt(5), 1, -2 + bdj.this.J.nextInt(5));
            bee _snowmanxxx = aqe.aO.a(bdj.this.l);
            _snowmanxxx.a(_snowmanxx, 0.0F, 0.0F);
            _snowmanxxx.a(_snowman, bdj.this.l.d(_snowmanxx), aqp.f, null, null);
            _snowmanxxx.a(bdj.this);
            _snowmanxxx.g(_snowmanxx);
            _snowmanxxx.a(20 * (30 + bdj.this.J.nextInt(90)));
            _snowman.l(_snowmanxxx);
         }
      }

      @Override
      protected adp k() {
         return adq.dT;
      }

      @Override
      protected bea.a l() {
         return bea.a.b;
      }
   }

   public class d extends bea.c {
      private final azg e = new azg().a(16.0).a().a(var0 -> ((bas)var0).eL() == bkx.l);

      public d() {
      }

      @Override
      public boolean a() {
         if (bdj.this.A() != null) {
            return false;
         } else if (bdj.this.eW()) {
            return false;
         } else if (bdj.this.K < this.c) {
            return false;
         } else if (!bdj.this.l.V().b(brt.b)) {
            return false;
         } else {
            List<bas> _snowman = bdj.this.l.a(bas.class, this.e, bdj.this, bdj.this.cc().c(16.0, 4.0, 16.0));
            if (_snowman.isEmpty()) {
               return false;
            } else {
               bdj.this.a(_snowman.get(bdj.this.J.nextInt(_snowman.size())));
               return true;
            }
         }
      }

      @Override
      public boolean b() {
         return bdj.this.fg() != null && this.b > 0;
      }

      @Override
      public void d() {
         super.d();
         bdj.this.a(null);
      }

      @Override
      protected void j() {
         bas _snowman = bdj.this.fg();
         if (_snowman != null && _snowman.aX()) {
            _snowman.b(bkx.o);
         }
      }

      @Override
      protected int m() {
         return 40;
      }

      @Override
      protected int g() {
         return 60;
      }

      @Override
      protected int h() {
         return 140;
      }

      @Override
      protected adp k() {
         return adq.dU;
      }

      @Override
      protected bea.a l() {
         return bea.a.d;
      }
   }
}
