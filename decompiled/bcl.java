import com.google.common.collect.ImmutableList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class bcl extends bdq implements aqy, bdu {
   private static final us<Integer> b = uv.a(bcl.class, uu.b);
   private static final us<Integer> c = uv.a(bcl.class, uu.b);
   private static final us<Integer> d = uv.a(bcl.class, uu.b);
   private static final List<us<Integer>> bo = ImmutableList.of(b, c, d);
   private static final us<Integer> bp = uv.a(bcl.class, uu.b);
   private final float[] bq = new float[2];
   private final float[] br = new float[2];
   private final float[] bs = new float[2];
   private final float[] bt = new float[2];
   private final int[] bu = new int[2];
   private final int[] bv = new int[2];
   private int bw;
   private final aad bx = (aad)new aad(this.d(), aok.a.f, aok.b.a).a(true);
   private static final Predicate<aqm> by = var0 -> var0.dC() != aqq.b && var0.ei();
   private static final azg bz = new azg().a(20.0).a(by);

   public bcl(aqe<? extends bcl> var1, brx var2) {
      super(_snowman, _snowman);
      this.c(this.dx());
      this.x().d(true);
      this.f = 50;
   }

   @Override
   protected void o() {
      this.bk.a(0, new bcl.a());
      this.bk.a(2, new awv(this, 1.0, 40, 20.0F));
      this.bk.a(5, new axk(this, 1.0));
      this.bk.a(6, new awd(this, bfw.class, 8.0F));
      this.bk.a(7, new aws(this));
      this.bl.a(1, new axp(this));
      this.bl.a(2, new axq<>(this, aqn.class, 0, false, false, by));
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(b, 0);
      this.R.a(c, 0);
      this.R.a(d, 0);
      this.R.a(bp, 0);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Invul", this.eL());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.s(_snowman.h("Invul"));
      if (this.S()) {
         this.bx.a(this.d());
      }
   }

   @Override
   public void a(@Nullable nr var1) {
      super.a(_snowman);
      this.bx.a(this.d());
   }

   @Override
   protected adp I() {
      return adq.qQ;
   }

   @Override
   protected adp e(apk var1) {
      return adq.qT;
   }

   @Override
   protected adp dq() {
      return adq.qS;
   }

   @Override
   public void k() {
      dcn _snowman = this.cC().d(1.0, 0.6, 1.0);
      if (!this.l.v && this.t(0) > 0) {
         aqa _snowmanx = this.l.a(this.t(0));
         if (_snowmanx != null) {
            double _snowmanxx = _snowman.c;
            if (this.cE() < _snowmanx.cE() || !this.S_() && this.cE() < _snowmanx.cE() + 5.0) {
               _snowmanxx = Math.max(0.0, _snowmanxx);
               _snowmanxx += 0.3 - _snowmanxx * 0.6F;
            }

            _snowman = new dcn(_snowman.b, _snowmanxx, _snowman.d);
            dcn _snowmanxxx = new dcn(_snowmanx.cD() - this.cD(), 0.0, _snowmanx.cH() - this.cH());
            if (c(_snowmanxxx) > 9.0) {
               dcn _snowmanxxxx = _snowmanxxx.d();
               _snowman = _snowman.b(_snowmanxxxx.b * 0.3 - _snowman.b * 0.6, 0.0, _snowmanxxxx.d * 0.3 - _snowman.d * 0.6);
            }
         }
      }

      this.f(_snowman);
      if (c(_snowman) > 0.05) {
         this.p = (float)afm.d(_snowman.d, _snowman.b) * (180.0F / (float)Math.PI) - 90.0F;
      }

      super.k();

      for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
         this.bt[_snowmanx] = this.br[_snowmanx];
         this.bs[_snowmanx] = this.bq[_snowmanx];
      }

      for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
         int _snowmanxxx = this.t(_snowmanx + 1);
         aqa _snowmanxxxx = null;
         if (_snowmanxxx > 0) {
            _snowmanxxxx = this.l.a(_snowmanxxx);
         }

         if (_snowmanxxxx != null) {
            double _snowmanxxxxx = this.u(_snowmanx + 1);
            double _snowmanxxxxxx = this.v(_snowmanx + 1);
            double _snowmanxxxxxxx = this.w(_snowmanx + 1);
            double _snowmanxxxxxxxx = _snowmanxxxx.cD() - _snowmanxxxxx;
            double _snowmanxxxxxxxxx = _snowmanxxxx.cG() - _snowmanxxxxxx;
            double _snowmanxxxxxxxxxx = _snowmanxxxx.cH() - _snowmanxxxxxxx;
            double _snowmanxxxxxxxxxxx = (double)afm.a(_snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx);
            float _snowmanxxxxxxxxxxxx = (float)(afm.d(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx) * 180.0F / (float)Math.PI) - 90.0F;
            float _snowmanxxxxxxxxxxxxx = (float)(-(afm.d(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx) * 180.0F / (float)Math.PI));
            this.bq[_snowmanx] = this.a(this.bq[_snowmanx], _snowmanxxxxxxxxxxxxx, 40.0F);
            this.br[_snowmanx] = this.a(this.br[_snowmanx], _snowmanxxxxxxxxxxxx, 10.0F);
         } else {
            this.br[_snowmanx] = this.a(this.br[_snowmanx], this.aA, 10.0F);
         }
      }

      boolean _snowmanx = this.S_();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 3; _snowmanxxxxx++) {
         double _snowmanxxxxxx = this.u(_snowmanxxxxx);
         double _snowmanxxxxxxx = this.v(_snowmanxxxxx);
         double _snowmanxxxxxxxx = this.w(_snowmanxxxxx);
         this.l
            .a(hh.S, _snowmanxxxxxx + this.J.nextGaussian() * 0.3F, _snowmanxxxxxxx + this.J.nextGaussian() * 0.3F, _snowmanxxxxxxxx + this.J.nextGaussian() * 0.3F, 0.0, 0.0, 0.0);
         if (_snowmanx && this.l.t.nextInt(4) == 0) {
            this.l
               .a(
                  hh.u,
                  _snowmanxxxxxx + this.J.nextGaussian() * 0.3F,
                  _snowmanxxxxxxx + this.J.nextGaussian() * 0.3F,
                  _snowmanxxxxxxxx + this.J.nextGaussian() * 0.3F,
                  0.7F,
                  0.7F,
                  0.5
               );
         }
      }

      if (this.eL() > 0) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 3; _snowmanxxxxxx++) {
            this.l
               .a(hh.u, this.cD() + this.J.nextGaussian(), this.cE() + (double)(this.J.nextFloat() * 3.3F), this.cH() + this.J.nextGaussian(), 0.7F, 0.7F, 0.9F);
         }
      }
   }

   @Override
   protected void N() {
      if (this.eL() > 0) {
         int _snowman = this.eL() - 1;
         if (_snowman <= 0) {
            brp.a _snowmanx = this.l.V().b(brt.b) ? brp.a.c : brp.a.a;
            this.l.a(this, this.cD(), this.cG(), this.cH(), 7.0F, false, _snowmanx);
            if (!this.aA()) {
               this.l.b(1023, this.cB(), 0);
            }
         }

         this.s(_snowman);
         if (this.K % 10 == 0) {
            this.b(10.0F);
         }
      } else {
         super.N();

         for (int _snowmanx = 1; _snowmanx < 3; _snowmanx++) {
            if (this.K >= this.bu[_snowmanx - 1]) {
               this.bu[_snowmanx - 1] = this.K + 10 + this.J.nextInt(10);
               if ((this.l.ad() == aor.c || this.l.ad() == aor.d) && this.bv[_snowmanx - 1]++ > 15) {
                  float _snowmanxx = 10.0F;
                  float _snowmanxxx = 5.0F;
                  double _snowmanxxxx = afm.a(this.J, this.cD() - 10.0, this.cD() + 10.0);
                  double _snowmanxxxxx = afm.a(this.J, this.cE() - 5.0, this.cE() + 5.0);
                  double _snowmanxxxxxx = afm.a(this.J, this.cH() - 10.0, this.cH() + 10.0);
                  this.a(_snowmanx + 1, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, true);
                  this.bv[_snowmanx - 1] = 0;
               }

               int _snowmanxx = this.t(_snowmanx);
               if (_snowmanxx > 0) {
                  aqa _snowmanxxx = this.l.a(_snowmanxx);
                  if (_snowmanxxx == null || !_snowmanxxx.aX() || this.h(_snowmanxxx) > 900.0 || !this.D(_snowmanxxx)) {
                     this.a(_snowmanx, 0);
                  } else if (_snowmanxxx instanceof bfw && ((bfw)_snowmanxxx).bC.a) {
                     this.a(_snowmanx, 0);
                  } else {
                     this.a(_snowmanx + 1, (aqm)_snowmanxxx);
                     this.bu[_snowmanx - 1] = this.K + 40 + this.J.nextInt(20);
                     this.bv[_snowmanx - 1] = 0;
                  }
               } else {
                  List<aqm> _snowmanxxx = this.l.a(aqm.class, bz, this, this.cc().c(20.0, 8.0, 20.0));

                  for (int _snowmanxxxx = 0; _snowmanxxxx < 10 && !_snowmanxxx.isEmpty(); _snowmanxxxx++) {
                     aqm _snowmanxxxxx = _snowmanxxx.get(this.J.nextInt(_snowmanxxx.size()));
                     if (_snowmanxxxxx != this && _snowmanxxxxx.aX() && this.D(_snowmanxxxxx)) {
                        if (_snowmanxxxxx instanceof bfw) {
                           if (!((bfw)_snowmanxxxxx).bC.a) {
                              this.a(_snowmanx, _snowmanxxxxx.Y());
                           }
                        } else {
                           this.a(_snowmanx, _snowmanxxxxx.Y());
                        }
                        break;
                     }

                     _snowmanxxx.remove(_snowmanxxxxx);
                  }
               }
            }
         }

         if (this.A() != null) {
            this.a(0, this.A().Y());
         } else {
            this.a(0, 0);
         }

         if (this.bw > 0) {
            this.bw--;
            if (this.bw == 0 && this.l.V().b(brt.b)) {
               int _snowmanxx = afm.c(this.cE());
               int _snowmanxxx = afm.c(this.cD());
               int _snowmanxxxx = afm.c(this.cH());
               boolean _snowmanxxxxx = false;

               for (int _snowmanxxxxxx = -1; _snowmanxxxxxx <= 1; _snowmanxxxxxx++) {
                  for (int _snowmanxxxxxxx = -1; _snowmanxxxxxxx <= 1; _snowmanxxxxxxx++) {
                     for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx <= 3; _snowmanxxxxxxxx++) {
                        int _snowmanxxxxxxxxx = _snowmanxxx + _snowmanxxxxxx;
                        int _snowmanxxxxxxxxxx = _snowmanxx + _snowmanxxxxxxxx;
                        int _snowmanxxxxxxxxxxx = _snowmanxxxx + _snowmanxxxxxxx;
                        fx _snowmanxxxxxxxxxxxx = new fx(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                        ceh _snowmanxxxxxxxxxxxxx = this.l.d_(_snowmanxxxxxxxxxxxx);
                        if (c(_snowmanxxxxxxxxxxxxx)) {
                           _snowmanxxxxx = this.l.a(_snowmanxxxxxxxxxxxx, true, this) || _snowmanxxxxx;
                        }
                     }
                  }
               }

               if (_snowmanxxxxx) {
                  this.l.a(null, 1022, this.cB(), 0);
               }
            }
         }

         if (this.K % 20 == 0) {
            this.b(1.0F);
         }

         this.bx.a(this.dk() / this.dx());
      }
   }

   public static boolean c(ceh var0) {
      return !_snowman.g() && !aed.ah.a(_snowman.b());
   }

   @Override
   public void m() {
      this.s(220);
      this.c(this.dx() / 3.0F);
   }

   @Override
   public void a(ceh var1, dcn var2) {
   }

   @Override
   public void b(aah var1) {
      super.b(_snowman);
      this.bx.a(_snowman);
   }

   @Override
   public void c(aah var1) {
      super.c(_snowman);
      this.bx.b(_snowman);
   }

   private double u(int var1) {
      if (_snowman <= 0) {
         return this.cD();
      } else {
         float _snowman = (this.aA + (float)(180 * (_snowman - 1))) * (float) (Math.PI / 180.0);
         float _snowmanx = afm.b(_snowman);
         return this.cD() + (double)_snowmanx * 1.3;
      }
   }

   private double v(int var1) {
      return _snowman <= 0 ? this.cE() + 3.0 : this.cE() + 2.2;
   }

   private double w(int var1) {
      if (_snowman <= 0) {
         return this.cH();
      } else {
         float _snowman = (this.aA + (float)(180 * (_snowman - 1))) * (float) (Math.PI / 180.0);
         float _snowmanx = afm.a(_snowman);
         return this.cH() + (double)_snowmanx * 1.3;
      }
   }

   private float a(float var1, float var2, float var3) {
      float _snowman = afm.g(_snowman - _snowman);
      if (_snowman > _snowman) {
         _snowman = _snowman;
      }

      if (_snowman < -_snowman) {
         _snowman = -_snowman;
      }

      return _snowman + _snowman;
   }

   private void a(int var1, aqm var2) {
      this.a(_snowman, _snowman.cD(), _snowman.cE() + (double)_snowman.ce() * 0.5, _snowman.cH(), _snowman == 0 && this.J.nextFloat() < 0.001F);
   }

   private void a(int var1, double var2, double var4, double var6, boolean var8) {
      if (!this.aA()) {
         this.l.a(null, 1024, this.cB(), 0);
      }

      double _snowman = this.u(_snowman);
      double _snowmanx = this.v(_snowman);
      double _snowmanxx = this.w(_snowman);
      double _snowmanxxx = _snowman - _snowman;
      double _snowmanxxxx = _snowman - _snowmanx;
      double _snowmanxxxxx = _snowman - _snowmanxx;
      bgz _snowmanxxxxxx = new bgz(this.l, this, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      _snowmanxxxxxx.b(this);
      if (_snowman) {
         _snowmanxxxxxx.a(true);
      }

      _snowmanxxxxxx.o(_snowman, _snowmanx, _snowmanxx);
      this.l.c(_snowmanxxxxxx);
   }

   @Override
   public void a(aqm var1, float var2) {
      this.a(0, _snowman);
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else if (_snowman == apk.h || _snowman.k() instanceof bcl) {
         return false;
      } else if (this.eL() > 0 && _snowman != apk.m) {
         return false;
      } else {
         if (this.S_()) {
            aqa _snowman = _snowman.j();
            if (_snowman instanceof bga) {
               return false;
            }
         }

         aqa _snowman = _snowman.k();
         if (_snowman != null && !(_snowman instanceof bfw) && _snowman instanceof aqm && ((aqm)_snowman).dC() == this.dC()) {
            return false;
         } else {
            if (this.bw <= 0) {
               this.bw = 20;
            }

            for (int _snowmanx = 0; _snowmanx < this.bv.length; _snowmanx++) {
               this.bv[_snowmanx] = this.bv[_snowmanx] + 3;
            }

            return super.a(_snowman, _snowman);
         }
      }
   }

   @Override
   protected void a(apk var1, int var2, boolean var3) {
      super.a(_snowman, _snowman, _snowman);
      bcv _snowman = this.a(bmd.pm);
      if (_snowman != null) {
         _snowman.r();
      }
   }

   @Override
   public void cI() {
      if (this.l.ad() == aor.a && this.L()) {
         this.ad();
      } else {
         this.aI = 0;
      }
   }

   @Override
   public boolean b(float var1, float var2) {
      return false;
   }

   @Override
   public boolean c(apu var1) {
      return false;
   }

   public static ark.a eK() {
      return bdq.eR().a(arl.a, 300.0).a(arl.d, 0.6F).a(arl.b, 40.0).a(arl.i, 4.0);
   }

   public float a(int var1) {
      return this.br[_snowman];
   }

   public float b(int var1) {
      return this.bq[_snowman];
   }

   public int eL() {
      return this.R.a(bp);
   }

   public void s(int var1) {
      this.R.b(bp, _snowman);
   }

   public int t(int var1) {
      return this.R.a(bo.get(_snowman));
   }

   public void a(int var1, int var2) {
      this.R.b(bo.get(_snowman), _snowman);
   }

   @Override
   public boolean S_() {
      return this.dk() <= this.dx() / 2.0F;
   }

   @Override
   public aqq dC() {
      return aqq.b;
   }

   @Override
   protected boolean n(aqa var1) {
      return false;
   }

   @Override
   public boolean bO() {
      return false;
   }

   @Override
   public boolean d(apu var1) {
      return _snowman.a() == apw.t ? false : super.d(_snowman);
   }

   class a extends avv {
      public a() {
         this.a(EnumSet.of(avv.a.a, avv.a.c, avv.a.b));
      }

      @Override
      public boolean a() {
         return bcl.this.eL() > 0;
      }
   }
}
