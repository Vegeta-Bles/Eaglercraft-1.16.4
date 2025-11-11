import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bdm extends bdq {
   private static final us<Boolean> b = uv.a(bdm.class, uu.i);
   private static final us<Integer> d = uv.a(bdm.class, uu.b);
   private float bo;
   private float bp;
   private float bq;
   private float br;
   private float bs;
   private aqm bt;
   private int bu;
   private boolean bv;
   protected awt c;

   public bdm(aqe<? extends bdm> var1, brx var2) {
      super(_snowman, _snowman);
      this.f = 10;
      this.a(cwz.h, 0.0F);
      this.bh = new bdm.c(this);
      this.bo = this.J.nextFloat();
      this.bp = this.bo;
   }

   @Override
   protected void o() {
      awk _snowman = new awk(this, 1.0);
      this.c = new awt(this, 1.0, 80);
      this.bk.a(4, new bdm.a(this));
      this.bk.a(5, _snowman);
      this.bk.a(7, this.c);
      this.bk.a(8, new awd(this, bfw.class, 8.0F));
      this.bk.a(8, new awd(this, bdm.class, 12.0F, 0.01F));
      this.bk.a(9, new aws(this));
      this.c.a(EnumSet.of(avv.a.a, avv.a.b));
      _snowman.a(EnumSet.of(avv.a.a, avv.a.b));
      this.bl.a(1, new axq<>(this, aqm.class, 10, true, false, new bdm.b(this)));
   }

   public static ark.a eM() {
      return bdq.eR().a(arl.f, 6.0).a(arl.d, 0.5).a(arl.b, 16.0).a(arl.a, 30.0);
   }

   @Override
   protected ayj b(brx var1) {
      return new ayl(this, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, false);
      this.R.a(d, 0);
   }

   @Override
   public boolean cM() {
      return true;
   }

   @Override
   public aqq dC() {
      return aqq.e;
   }

   public boolean eN() {
      return this.R.a(b);
   }

   private void t(boolean var1) {
      this.R.b(b, _snowman);
   }

   public int eK() {
      return 80;
   }

   private void a(int var1) {
      this.R.b(d, _snowman);
   }

   public boolean eO() {
      return this.R.a(d) != 0;
   }

   @Nullable
   public aqm eP() {
      if (!this.eO()) {
         return null;
      } else if (this.l.v) {
         if (this.bt != null) {
            return this.bt;
         } else {
            aqa _snowman = this.l.a(this.R.a(d));
            if (_snowman instanceof aqm) {
               this.bt = (aqm)_snowman;
               return this.bt;
            } else {
               return null;
            }
         }
      } else {
         return this.A();
      }
   }

   @Override
   public void a(us<?> var1) {
      super.a(_snowman);
      if (d.equals(_snowman)) {
         this.bu = 0;
         this.bt = null;
      }
   }

   @Override
   public int D() {
      return 160;
   }

   @Override
   protected adp I() {
      return this.aH() ? adq.fs : adq.ft;
   }

   @Override
   protected adp e(apk var1) {
      return this.aH() ? adq.fy : adq.fz;
   }

   @Override
   protected adp dq() {
      return this.aH() ? adq.fv : adq.fw;
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b * 0.5F;
   }

   @Override
   public float a(fx var1, brz var2) {
      return _snowman.b(_snowman).a(aef.b) ? 10.0F + _snowman.y(_snowman) - 0.5F : super.a(_snowman, _snowman);
   }

   @Override
   public void k() {
      if (this.aX()) {
         if (this.l.v) {
            this.bp = this.bo;
            if (!this.aE()) {
               this.bq = 2.0F;
               dcn _snowman = this.cC();
               if (_snowman.c > 0.0 && this.bv && !this.aA()) {
                  this.l.a(this.cD(), this.cE(), this.cH(), this.eL(), this.cu(), 1.0F, 1.0F, false);
               }

               this.bv = _snowman.c < 0.0 && this.l.a(this.cB().c(), this);
            } else if (this.eN()) {
               if (this.bq < 0.5F) {
                  this.bq = 4.0F;
               } else {
                  this.bq = this.bq + (0.5F - this.bq) * 0.1F;
               }
            } else {
               this.bq = this.bq + (0.125F - this.bq) * 0.2F;
            }

            this.bo = this.bo + this.bq;
            this.bs = this.br;
            if (!this.aH()) {
               this.br = this.J.nextFloat();
            } else if (this.eN()) {
               this.br = this.br + (0.0F - this.br) * 0.25F;
            } else {
               this.br = this.br + (1.0F - this.br) * 0.06F;
            }

            if (this.eN() && this.aE()) {
               dcn _snowman = this.f(0.0F);

               for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
                  this.l.a(hh.e, this.d(0.5) - _snowman.b * 1.5, this.cF() - _snowman.c * 1.5, this.g(0.5) - _snowman.d * 1.5, 0.0, 0.0, 0.0);
               }
            }

            if (this.eO()) {
               if (this.bu < this.eK()) {
                  this.bu++;
               }

               aqm _snowman = this.eP();
               if (_snowman != null) {
                  this.t().a(_snowman, 90.0F, 90.0F);
                  this.t().a();
                  double _snowmanx = (double)this.A(0.0F);
                  double _snowmanxx = _snowman.cD() - this.cD();
                  double _snowmanxxx = _snowman.e(0.5) - this.cG();
                  double _snowmanxxxx = _snowman.cH() - this.cH();
                  double _snowmanxxxxx = Math.sqrt(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx);
                  _snowmanxx /= _snowmanxxxxx;
                  _snowmanxxx /= _snowmanxxxxx;
                  _snowmanxxxx /= _snowmanxxxxx;
                  double _snowmanxxxxxx = this.J.nextDouble();

                  while (_snowmanxxxxxx < _snowmanxxxxx) {
                     _snowmanxxxxxx += 1.8 - _snowmanx + this.J.nextDouble() * (1.7 - _snowmanx);
                     this.l.a(hh.e, this.cD() + _snowmanxx * _snowmanxxxxxx, this.cG() + _snowmanxxx * _snowmanxxxxxx, this.cH() + _snowmanxxxx * _snowmanxxxxxx, 0.0, 0.0, 0.0);
                  }
               }
            }
         }

         if (this.aH()) {
            this.j(300);
         } else if (this.t) {
            this.f(this.cC().b((double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.4F), 0.5, (double)((this.J.nextFloat() * 2.0F - 1.0F) * 0.4F)));
            this.p = this.J.nextFloat() * 360.0F;
            this.t = false;
            this.Z = true;
         }

         if (this.eO()) {
            this.p = this.aC;
         }
      }

      super.k();
   }

   protected adp eL() {
      return adq.fx;
   }

   public float y(float var1) {
      return afm.g(_snowman, this.bp, this.bo);
   }

   public float z(float var1) {
      return afm.g(_snowman, this.bs, this.br);
   }

   public float A(float var1) {
      return ((float)this.bu + _snowman) / (float)this.eK();
   }

   @Override
   public boolean a(brz var1) {
      return _snowman.j(this);
   }

   public static boolean b(aqe<? extends bdm> var0, bry var1, aqp var2, fx var3, Random var4) {
      return (_snowman.nextInt(20) == 0 || !_snowman.x(_snowman)) && _snowman.ad() != aor.a && (_snowman == aqp.c || _snowman.b(_snowman).a(aef.b));
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (!this.eN() && !_snowman.t() && _snowman.j() instanceof aqm) {
         aqm _snowman = (aqm)_snowman.j();
         if (!_snowman.d()) {
            _snowman.a(apk.a((aqa)this), 2.0F);
         }
      }

      if (this.c != null) {
         this.c.h();
      }

      return super.a(_snowman, _snowman);
   }

   @Override
   public int O() {
      return 180;
   }

   @Override
   public void g(dcn var1) {
      if (this.dS() && this.aE()) {
         this.a(0.1F, _snowman);
         this.a(aqr.a, this.cC());
         this.f(this.cC().a(0.9));
         if (!this.eN() && this.A() == null) {
            this.f(this.cC().b(0.0, -0.005, 0.0));
         }
      } else {
         super.g(_snowman);
      }
   }

   static class a extends avv {
      private final bdm a;
      private int b;
      private final boolean c;

      public a(bdm var1) {
         this.a = _snowman;
         this.c = _snowman instanceof bdf;
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }

      @Override
      public boolean a() {
         aqm _snowman = this.a.A();
         return _snowman != null && _snowman.aX();
      }

      @Override
      public boolean b() {
         return super.b() && (this.c || this.a.h((aqa)this.a.A()) > 9.0);
      }

      @Override
      public void c() {
         this.b = -10;
         this.a.x().o();
         this.a.t().a(this.a.A(), 90.0F, 90.0F);
         this.a.Z = true;
      }

      @Override
      public void d() {
         this.a.a(0);
         this.a.h(null);
         this.a.c.h();
      }

      @Override
      public void e() {
         aqm _snowman = this.a.A();
         this.a.x().o();
         this.a.t().a(_snowman, 90.0F, 90.0F);
         if (!this.a.D(_snowman)) {
            this.a.h(null);
         } else {
            this.b++;
            if (this.b == 0) {
               this.a.a(this.a.A().Y());
               if (!this.a.aA()) {
                  this.a.l.a(this.a, (byte)21);
               }
            } else if (this.b >= this.a.eK()) {
               float _snowmanx = 1.0F;
               if (this.a.l.ad() == aor.d) {
                  _snowmanx += 2.0F;
               }

               if (this.c) {
                  _snowmanx += 2.0F;
               }

               _snowman.a(apk.c(this.a, this.a), _snowmanx);
               _snowman.a(apk.c(this.a), (float)this.a.b(arl.f));
               this.a.h(null);
            }

            super.e();
         }
      }
   }

   static class b implements Predicate<aqm> {
      private final bdm a;

      public b(bdm var1) {
         this.a = _snowman;
      }

      public boolean a(@Nullable aqm var1) {
         return (_snowman instanceof bfw || _snowman instanceof bav) && _snowman.h(this.a) > 9.0;
      }
   }

   static class c extends avb {
      private final bdm i;

      public c(bdm var1) {
         super(_snowman);
         this.i = _snowman;
      }

      @Override
      public void a() {
         if (this.h == avb.a.b && !this.i.x().m()) {
            dcn _snowman = new dcn(this.b - this.i.cD(), this.c - this.i.cE(), this.d - this.i.cH());
            double _snowmanx = _snowman.f();
            double _snowmanxx = _snowman.b / _snowmanx;
            double _snowmanxxx = _snowman.c / _snowmanx;
            double _snowmanxxxx = _snowman.d / _snowmanx;
            float _snowmanxxxxx = (float)(afm.d(_snowman.d, _snowman.b) * 180.0F / (float)Math.PI) - 90.0F;
            this.i.p = this.a(this.i.p, _snowmanxxxxx, 90.0F);
            this.i.aA = this.i.p;
            float _snowmanxxxxxx = (float)(this.e * this.i.b(arl.d));
            float _snowmanxxxxxxx = afm.g(0.125F, this.i.dN(), _snowmanxxxxxx);
            this.i.q(_snowmanxxxxxxx);
            double _snowmanxxxxxxxx = Math.sin((double)(this.i.K + this.i.Y()) * 0.5) * 0.05;
            double _snowmanxxxxxxxxx = Math.cos((double)(this.i.p * (float) (Math.PI / 180.0)));
            double _snowmanxxxxxxxxxx = Math.sin((double)(this.i.p * (float) (Math.PI / 180.0)));
            double _snowmanxxxxxxxxxxx = Math.sin((double)(this.i.K + this.i.Y()) * 0.75) * 0.05;
            this.i
               .f(
                  this.i
                     .cC()
                     .b(_snowmanxxxxxxxx * _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx * (_snowmanxxxxxxxxxx + _snowmanxxxxxxxxx) * 0.25 + (double)_snowmanxxxxxxx * _snowmanxxx * 0.1, _snowmanxxxxxxxx * _snowmanxxxxxxxxxx)
               );
            ava _snowmanxxxxxxxxxxxx = this.i.t();
            double _snowmanxxxxxxxxxxxxx = this.i.cD() + _snowmanxx * 2.0;
            double _snowmanxxxxxxxxxxxxxx = this.i.cG() + _snowmanxxx / _snowmanx;
            double _snowmanxxxxxxxxxxxxxxx = this.i.cH() + _snowmanxxxx * 2.0;
            double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.d();
            double _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.e();
            double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.f();
            if (!_snowmanxxxxxxxxxxxx.c()) {
               _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx;
            }

            this.i
               .t()
               .a(
                  afm.d(0.125, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx),
                  afm.d(0.125, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx),
                  afm.d(0.125, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx),
                  10.0F,
                  40.0F
               );
            this.i.t(true);
         } else {
            this.i.q(0.0F);
            this.i.t(false);
         }
      }
   }
}
