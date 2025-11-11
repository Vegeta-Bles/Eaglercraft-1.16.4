import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class baf extends bay {
   private static final us<fx> c = uv.a(baf.class, uu.l);
   private static final us<Boolean> d = uv.a(baf.class, uu.i);
   private static final us<Integer> bo = uv.a(baf.class, uu.b);
   private static final azg bp = new azg().a(10.0).b().a().c();
   public static final Predicate<bcv> b = var0 -> !var0.p() && var0.aX() && var0.aE();

   public baf(aqe<? extends baf> var1, brx var2) {
      super(_snowman, _snowman);
      this.bh = new baf.a(this);
      this.g = new auw(this, 10);
      this.p(true);
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.j(this.bH());
      this.q = 0.0F;
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean cM() {
      return false;
   }

   @Override
   protected void a(int var1) {
   }

   public void g(fx var1) {
      this.R.b(c, _snowman);
   }

   public fx m() {
      return this.R.a(c);
   }

   public boolean eK() {
      return this.R.a(d);
   }

   public void t(boolean var1) {
      this.R.b(d, _snowman);
   }

   public int eL() {
      return this.R.a(bo);
   }

   public void b(int var1) {
      this.R.b(bo, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(c, fx.b);
      this.R.a(d, false);
      this.R.a(bo, 2400);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("TreasurePosX", this.m().u());
      _snowman.b("TreasurePosY", this.m().v());
      _snowman.b("TreasurePosZ", this.m().w());
      _snowman.a("GotFish", this.eK());
      _snowman.b("Moistness", this.eL());
   }

   @Override
   public void a(md var1) {
      int _snowman = _snowman.h("TreasurePosX");
      int _snowmanx = _snowman.h("TreasurePosY");
      int _snowmanxx = _snowman.h("TreasurePosZ");
      this.g(new fx(_snowman, _snowmanx, _snowmanxx));
      super.a(_snowman);
      this.t(_snowman.q("GotFish"));
      this.b(_snowman.h("Moistness"));
   }

   @Override
   protected void o() {
      this.bk.a(0, new avh(this));
      this.bk.a(0, new axh(this));
      this.bk.a(1, new baf.b(this));
      this.bk.a(2, new baf.c(this, 4.0));
      this.bk.a(4, new awu(this, 1.0, 10));
      this.bk.a(4, new aws(this));
      this.bk.a(5, new awd(this, bfw.class, 6.0F));
      this.bk.a(5, new avl(this, 10));
      this.bk.a(6, new awf(this, 1.2F, true));
      this.bk.a(8, new baf.d());
      this.bk.a(8, new avq(this));
      this.bk.a(9, new avd<>(this, bdm.class, 8.0F, 1.0, 1.0));
      this.bl.a(1, new axp(this, bdm.class).a());
   }

   public static ark.a eM() {
      return aqn.p().a(arl.a, 10.0).a(arl.d, 1.2F).a(arl.f, 3.0);
   }

   @Override
   protected ayj b(brx var1) {
      return new ayl(this, _snowman);
   }

   @Override
   public boolean B(aqa var1) {
      boolean _snowman = _snowman.a(apk.c(this), (float)((int)this.b(arl.f)));
      if (_snowman) {
         this.a(this, _snowman);
         this.a(adq.cF, 1.0F, 1.0F);
      }

      return _snowman;
   }

   @Override
   public int bH() {
      return 4800;
   }

   @Override
   protected int m(int var1) {
      return this.bH();
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 0.3F;
   }

   @Override
   public int O() {
      return 1;
   }

   @Override
   public int Q() {
      return 1;
   }

   @Override
   protected boolean n(aqa var1) {
      return true;
   }

   @Override
   public boolean e(bmb var1) {
      aqf _snowman = aqn.j(_snowman);
      return !this.b(_snowman).a() ? false : _snowman == aqf.a && super.e(_snowman);
   }

   @Override
   protected void b(bcv var1) {
      if (this.b(aqf.a).a()) {
         bmb _snowman = _snowman.g();
         if (this.h(_snowman)) {
            this.a(_snowman);
            this.a(aqf.a, _snowman);
            this.bm[aqf.a.b()] = 2.0F;
            this.a(_snowman, _snowman.E());
            _snowman.ad();
         }
      }
   }

   @Override
   public void j() {
      super.j();
      if (this.eD()) {
         this.j(this.bH());
      } else {
         if (this.aG()) {
            this.b(2400);
         } else {
            this.b(this.eL() - 1);
            if (this.eL() <= 0) {
               this.a(apk.t, 1.0F);
            }

            if (this.t) {
               this.f(this.cC().b((double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5, (double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.2F)));
               this.p = this.J.nextFloat() * 360.0F;
               this.t = false;
               this.Z = true;
            }
         }

         if (this.l.v && this.aE() && this.cC().g() > 0.03) {
            dcn _snowman = this.f(0.0F);
            float _snowmanx = afm.b(this.p * (float) (Math.PI / 180.0)) * 0.3F;
            float _snowmanxx = afm.a(this.p * (float) (Math.PI / 180.0)) * 0.3F;
            float _snowmanxxx = 1.2F - this.J.nextFloat() * 0.7F;

            for (int _snowmanxxxx = 0; _snowmanxxxx < 2; _snowmanxxxx++) {
               this.l.a(hh.af, this.cD() - _snowman.b * (double)_snowmanxxx + (double)_snowmanx, this.cE() - _snowman.c, this.cH() - _snowman.d * (double)_snowmanxxx + (double)_snowmanxx, 0.0, 0.0, 0.0);
               this.l.a(hh.af, this.cD() - _snowman.b * (double)_snowmanxxx - (double)_snowmanx, this.cE() - _snowman.c, this.cH() - _snowman.d * (double)_snowmanxxx - (double)_snowmanxx, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 38) {
         this.a(hh.E);
      } else {
         super.a(_snowman);
      }
   }

   private void a(hf var1) {
      for (int _snowman = 0; _snowman < 7; _snowman++) {
         double _snowmanx = this.J.nextGaussian() * 0.01;
         double _snowmanxx = this.J.nextGaussian() * 0.01;
         double _snowmanxxx = this.J.nextGaussian() * 0.01;
         this.l.a(_snowman, this.d(1.0), this.cF() + 0.2, this.g(1.0), _snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   @Override
   protected aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (!_snowman.a() && _snowman.b().a(aeg.T)) {
         if (!this.l.v) {
            this.a(adq.cH, 1.0F, 1.0F);
         }

         this.t(true);
         if (!_snowman.bC.d) {
            _snowman.g(1);
         }

         return aou.a(this.l.v);
      } else {
         return super.b(_snowman, _snowman);
      }
   }

   public static boolean b(aqe<baf> var0, bry var1, aqp var2, fx var3, Random var4) {
      if (_snowman.v() > 45 && _snowman.v() < _snowman.t_()) {
         Optional<vj<bsv>> _snowman = _snowman.i(_snowman);
         return (!Objects.equals(_snowman, Optional.of(btb.a)) || !Objects.equals(_snowman, Optional.of(btb.y))) && _snowman.b(_snowman).a(aef.b);
      } else {
         return false;
      }
   }

   @Override
   protected adp e(apk var1) {
      return adq.cI;
   }

   @Nullable
   @Override
   protected adp dq() {
      return adq.cG;
   }

   @Nullable
   @Override
   protected adp I() {
      return this.aE() ? adq.cE : adq.cD;
   }

   @Override
   protected adp aw() {
      return adq.cL;
   }

   @Override
   protected adp av() {
      return adq.cM;
   }

   protected boolean eN() {
      fx _snowman = this.x().h();
      return _snowman != null ? _snowman.a(this.cA(), 12.0) : false;
   }

   @Override
   public void g(dcn var1) {
      if (this.dS() && this.aE()) {
         this.a(this.dN(), _snowman);
         this.a(aqr.a, this.cC());
         this.f(this.cC().a(0.9));
         if (this.A() == null) {
            this.f(this.cC().b(0.0, -0.005, 0.0));
         }
      } else {
         super.g(_snowman);
      }
   }

   @Override
   public boolean a(bfw var1) {
      return true;
   }

   static class a extends avb {
      private final baf i;

      public a(baf var1) {
         super(_snowman);
         this.i = _snowman;
      }

      @Override
      public void a() {
         if (this.i.aE()) {
            this.i.f(this.i.cC().b(0.0, 0.005, 0.0));
         }

         if (this.h == avb.a.b && !this.i.x().m()) {
            double _snowman = this.b - this.i.cD();
            double _snowmanx = this.c - this.i.cE();
            double _snowmanxx = this.d - this.i.cH();
            double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
            if (_snowmanxxx < 2.5000003E-7F) {
               this.a.t(0.0F);
            } else {
               float _snowmanxxxx = (float)(afm.d(_snowmanxx, _snowman) * 180.0F / (float)Math.PI) - 90.0F;
               this.i.p = this.a(this.i.p, _snowmanxxxx, 10.0F);
               this.i.aA = this.i.p;
               this.i.aC = this.i.p;
               float _snowmanxxxxx = (float)(this.e * this.i.b(arl.d));
               if (this.i.aE()) {
                  this.i.q(_snowmanxxxxx * 0.02F);
                  float _snowmanxxxxxx = -((float)(afm.d(_snowmanx, (double)afm.a(_snowman * _snowman + _snowmanxx * _snowmanxx)) * 180.0F / (float)Math.PI));
                  _snowmanxxxxxx = afm.a(afm.g(_snowmanxxxxxx), -85.0F, 85.0F);
                  this.i.q = this.a(this.i.q, _snowmanxxxxxx, 5.0F);
                  float _snowmanxxxxxxx = afm.b(this.i.q * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxxx = afm.a(this.i.q * (float) (Math.PI / 180.0));
                  this.i.aT = _snowmanxxxxxxx * _snowmanxxxxx;
                  this.i.aS = -_snowmanxxxxxxxx * _snowmanxxxxx;
               } else {
                  this.i.q(_snowmanxxxxx * 0.1F);
               }
            }
         } else {
            this.i.q(0.0F);
            this.i.v(0.0F);
            this.i.u(0.0F);
            this.i.t(0.0F);
         }
      }
   }

   static class b extends avv {
      private final baf a;
      private boolean b;

      b(baf var1) {
         this.a = _snowman;
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean C_() {
         return false;
      }

      @Override
      public boolean a() {
         return this.a.eK() && this.a.bI() >= 100;
      }

      @Override
      public boolean b() {
         fx _snowman = this.a.m();
         return !new fx((double)_snowman.u(), this.a.cE(), (double)_snowman.w()).a(this.a.cA(), 4.0) && !this.b && this.a.bI() >= 100;
      }

      @Override
      public void c() {
         if (this.a.l instanceof aag) {
            aag _snowman = (aag)this.a.l;
            this.b = false;
            this.a.x().o();
            fx _snowmanx = this.a.cB();
            cla<?> _snowmanxx = (double)_snowman.t.nextFloat() >= 0.5 ? cla.m : cla.i;
            fx _snowmanxxx = _snowman.a(_snowmanxx, _snowmanx, 50, false);
            if (_snowmanxxx == null) {
               cla<?> _snowmanxxxx = _snowmanxx.equals(cla.m) ? cla.i : cla.m;
               fx _snowmanxxxxx = _snowman.a(_snowmanxxxx, _snowmanx, 50, false);
               if (_snowmanxxxxx == null) {
                  this.b = true;
                  return;
               }

               this.a.g(_snowmanxxxxx);
            } else {
               this.a.g(_snowmanxxx);
            }

            _snowman.a(this.a, (byte)38);
         }
      }

      @Override
      public void d() {
         fx _snowman = this.a.m();
         if (new fx((double)_snowman.u(), this.a.cE(), (double)_snowman.w()).a(this.a.cA(), 4.0) || this.b) {
            this.a.t(false);
         }
      }

      @Override
      public void e() {
         brx _snowman = this.a.l;
         if (this.a.eN() || this.a.x().m()) {
            dcn _snowmanx = dcn.a(this.a.m());
            dcn _snowmanxx = azj.a(this.a, 16, 1, _snowmanx, (float) (Math.PI / 8));
            if (_snowmanxx == null) {
               _snowmanxx = azj.b(this.a, 8, 4, _snowmanx);
            }

            if (_snowmanxx != null) {
               fx _snowmanxxx = new fx(_snowmanxx);
               if (!_snowman.b(_snowmanxxx).a(aef.b) || !_snowman.d_(_snowmanxxx).a(_snowman, _snowmanxxx, cxe.b)) {
                  _snowmanxx = azj.b(this.a, 8, 5, _snowmanx);
               }
            }

            if (_snowmanxx == null) {
               this.b = true;
               return;
            }

            this.a.t().a(_snowmanxx.b, _snowmanxx.c, _snowmanxx.d, (float)(this.a.Q() + 20), (float)this.a.O());
            this.a.x().a(_snowmanxx.b, _snowmanxx.c, _snowmanxx.d, 1.3);
            if (_snowman.t.nextInt(80) == 0) {
               _snowman.a(this.a, (byte)38);
            }
         }
      }
   }

   static class c extends avv {
      private final baf a;
      private final double b;
      private bfw c;

      c(baf var1, double var2) {
         this.a = _snowman;
         this.b = _snowman;
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean a() {
         this.c = this.a.l.a(baf.bp, this.a);
         return this.c == null ? false : this.c.bB() && this.a.A() != this.c;
      }

      @Override
      public boolean b() {
         return this.c != null && this.c.bB() && this.a.h(this.c) < 256.0;
      }

      @Override
      public void c() {
         this.c.c(new apu(apw.D, 100));
      }

      @Override
      public void d() {
         this.c = null;
         this.a.x().o();
      }

      @Override
      public void e() {
         this.a.t().a(this.c, (float)(this.a.Q() + 20), (float)this.a.O());
         if (this.a.h(this.c) < 6.25) {
            this.a.x().o();
         } else {
            this.a.x().a(this.c, this.b);
         }

         if (this.c.bB() && this.c.l.t.nextInt(6) == 0) {
            this.c.c(new apu(apw.D, 100));
         }
      }
   }

   class d extends avv {
      private int b;

      private d() {
      }

      @Override
      public boolean a() {
         if (this.b > baf.this.K) {
            return false;
         } else {
            List<bcv> _snowman = baf.this.l.a(bcv.class, baf.this.cc().c(8.0, 8.0, 8.0), baf.b);
            return !_snowman.isEmpty() || !baf.this.b(aqf.a).a();
         }
      }

      @Override
      public void c() {
         List<bcv> _snowman = baf.this.l.a(bcv.class, baf.this.cc().c(8.0, 8.0, 8.0), baf.b);
         if (!_snowman.isEmpty()) {
            baf.this.x().a(_snowman.get(0), 1.2F);
            baf.this.a(adq.cK, 1.0F, 1.0F);
         }

         this.b = 0;
      }

      @Override
      public void d() {
         bmb _snowman = baf.this.b(aqf.a);
         if (!_snowman.a()) {
            this.a(_snowman);
            baf.this.a(aqf.a, bmb.b);
            this.b = baf.this.K + baf.this.J.nextInt(100);
         }
      }

      @Override
      public void e() {
         List<bcv> _snowman = baf.this.l.a(bcv.class, baf.this.cc().c(8.0, 8.0, 8.0), baf.b);
         bmb _snowmanx = baf.this.b(aqf.a);
         if (!_snowmanx.a()) {
            this.a(_snowmanx);
            baf.this.a(aqf.a, bmb.b);
         } else if (!_snowman.isEmpty()) {
            baf.this.x().a(_snowman.get(0), 1.2F);
         }
      }

      private void a(bmb var1) {
         if (!_snowman.a()) {
            double _snowman = baf.this.cG() - 0.3F;
            bcv _snowmanx = new bcv(baf.this.l, baf.this.cD(), _snowman, baf.this.cH(), _snowman);
            _snowmanx.a(40);
            _snowmanx.c(baf.this.bS());
            float _snowmanxx = 0.3F;
            float _snowmanxxx = baf.this.J.nextFloat() * (float) (Math.PI * 2);
            float _snowmanxxxx = 0.02F * baf.this.J.nextFloat();
            _snowmanx.n(
               (double)(0.3F * -afm.a(baf.this.p * (float) (Math.PI / 180.0)) * afm.b(baf.this.q * (float) (Math.PI / 180.0)) + afm.b(_snowmanxxx) * _snowmanxxxx),
               (double)(0.3F * afm.a(baf.this.q * (float) (Math.PI / 180.0)) * 1.5F),
               (double)(0.3F * afm.b(baf.this.p * (float) (Math.PI / 180.0)) * afm.b(baf.this.q * (float) (Math.PI / 180.0)) + afm.a(_snowmanxxx) * _snowmanxxxx)
            );
            baf.this.l.c(_snowmanx);
         }
      }
   }
}
