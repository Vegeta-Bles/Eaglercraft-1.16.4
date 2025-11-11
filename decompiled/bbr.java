import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bbr extends aqn implements bdi {
   private static final Logger bv = LogManager.getLogger();
   public static final us<Integer> b = uv.a(bbr.class, uu.b);
   private static final azg bw = new azg().a(64.0);
   public final double[][] c = new double[64][3];
   public int d = -1;
   private final bbp[] bx;
   public final bbp bo;
   private final bbp by;
   private final bbp bz;
   private final bbp bA;
   private final bbp bB;
   private final bbp bC;
   private final bbp bD;
   private final bbp bE;
   public float bp;
   public float bq;
   public boolean br;
   public int bs;
   public float bt;
   @Nullable
   public bbq bu;
   @Nullable
   private final chg bF;
   private final bci bG;
   private int bH = 100;
   private int bI;
   private final cxb[] bJ = new cxb[24];
   private final int[] bK = new int[24];
   private final cwy bL = new cwy();

   public bbr(aqe<? extends bbr> var1, brx var2) {
      super(aqe.t, _snowman);
      this.bo = new bbp(this, "head", 1.0F, 1.0F);
      this.by = new bbp(this, "neck", 3.0F, 3.0F);
      this.bz = new bbp(this, "body", 5.0F, 3.0F);
      this.bA = new bbp(this, "tail", 2.0F, 2.0F);
      this.bB = new bbp(this, "tail", 2.0F, 2.0F);
      this.bC = new bbp(this, "tail", 2.0F, 2.0F);
      this.bD = new bbp(this, "wing", 4.0F, 2.0F);
      this.bE = new bbp(this, "wing", 4.0F, 2.0F);
      this.bx = new bbp[]{this.bo, this.by, this.bz, this.bA, this.bB, this.bC, this.bD, this.bE};
      this.c(this.dx());
      this.H = true;
      this.Y = true;
      if (_snowman instanceof aag) {
         this.bF = ((aag)_snowman).D();
      } else {
         this.bF = null;
      }

      this.bG = new bci(this);
   }

   public static ark.a m() {
      return aqn.p().a(arl.a, 200.0);
   }

   @Override
   protected void e() {
      super.e();
      this.ab().a(b, bch.k.b());
   }

   public double[] a(int var1, float var2) {
      if (this.dl()) {
         _snowman = 0.0F;
      }

      _snowman = 1.0F - _snowman;
      int _snowman = this.d - _snowman & 63;
      int _snowmanx = this.d - _snowman - 1 & 63;
      double[] _snowmanxx = new double[3];
      double _snowmanxxx = this.c[_snowman][0];
      double _snowmanxxxx = afm.g(this.c[_snowmanx][0] - _snowmanxxx);
      _snowmanxx[0] = _snowmanxxx + _snowmanxxxx * (double)_snowman;
      _snowmanxxx = this.c[_snowman][1];
      _snowmanxxxx = this.c[_snowmanx][1] - _snowmanxxx;
      _snowmanxx[1] = _snowmanxxx + _snowmanxxxx * (double)_snowman;
      _snowmanxx[2] = afm.d((double)_snowman, this.c[_snowman][2], this.c[_snowmanx][2]);
      return _snowmanxx;
   }

   @Override
   public void k() {
      if (this.l.v) {
         this.c(this.dk());
         if (!this.aA()) {
            float _snowman = afm.b(this.bq * (float) (Math.PI * 2));
            float _snowmanx = afm.b(this.bp * (float) (Math.PI * 2));
            if (_snowmanx <= -0.3F && _snowman >= -0.3F) {
               this.l.a(this.cD(), this.cE(), this.cH(), adq.ds, this.cu(), 5.0F, 0.8F + this.J.nextFloat() * 0.3F, false);
            }

            if (!this.bG.a().a() && --this.bH < 0) {
               this.l.a(this.cD(), this.cE(), this.cH(), adq.dt, this.cu(), 2.5F, 0.8F + this.J.nextFloat() * 0.3F, false);
               this.bH = 200 + this.J.nextInt(200);
            }
         }
      }

      this.bp = this.bq;
      if (this.dl()) {
         float _snowmanxx = (this.J.nextFloat() - 0.5F) * 8.0F;
         float _snowmanxxx = (this.J.nextFloat() - 0.5F) * 4.0F;
         float _snowmanxxxx = (this.J.nextFloat() - 0.5F) * 8.0F;
         this.l.a(hh.w, this.cD() + (double)_snowmanxx, this.cE() + 2.0 + (double)_snowmanxxx, this.cH() + (double)_snowmanxxxx, 0.0, 0.0, 0.0);
      } else {
         this.eN();
         dcn _snowmanxx = this.cC();
         float _snowmanxxx = 0.2F / (afm.a(c(_snowmanxx)) * 10.0F + 1.0F);
         _snowmanxxx *= (float)Math.pow(2.0, _snowmanxx.c);
         if (this.bG.a().a()) {
            this.bq += 0.1F;
         } else if (this.br) {
            this.bq += _snowmanxxx * 0.5F;
         } else {
            this.bq += _snowmanxxx;
         }

         this.p = afm.g(this.p);
         if (this.eD()) {
            this.bq = 0.5F;
         } else {
            if (this.d < 0) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < this.c.length; _snowmanxxxx++) {
                  this.c[_snowmanxxxx][0] = (double)this.p;
                  this.c[_snowmanxxxx][1] = this.cE();
               }
            }

            if (++this.d == this.c.length) {
               this.d = 0;
            }

            this.c[this.d][0] = (double)this.p;
            this.c[this.d][1] = this.cE();
            if (this.l.v) {
               if (this.aU > 0) {
                  double _snowmanxxxx = this.cD() + (this.aV - this.cD()) / (double)this.aU;
                  double _snowmanxxxxx = this.cE() + (this.aW - this.cE()) / (double)this.aU;
                  double _snowmanxxxxxx = this.cH() + (this.aX - this.cH()) / (double)this.aU;
                  double _snowmanxxxxxxx = afm.g(this.aY - (double)this.p);
                  this.p = (float)((double)this.p + _snowmanxxxxxxx / (double)this.aU);
                  this.q = (float)((double)this.q + (this.aZ - (double)this.q) / (double)this.aU);
                  this.aU--;
                  this.d(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
                  this.a(this.p, this.q);
               }

               this.bG.a().b();
            } else {
               bcb _snowmanxxxx = this.bG.a();
               _snowmanxxxx.c();
               if (this.bG.a() != _snowmanxxxx) {
                  _snowmanxxxx = this.bG.a();
                  _snowmanxxxx.c();
               }

               dcn _snowmanxxxxx = _snowmanxxxx.g();
               if (_snowmanxxxxx != null) {
                  double _snowmanxxxxxx = _snowmanxxxxx.b - this.cD();
                  double _snowmanxxxxxxx = _snowmanxxxxx.c - this.cE();
                  double _snowmanxxxxxxxx = _snowmanxxxxx.d - this.cH();
                  double _snowmanxxxxxxxxx = _snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx;
                  float _snowmanxxxxxxxxxx = _snowmanxxxx.f();
                  double _snowmanxxxxxxxxxxx = (double)afm.a(_snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx);
                  if (_snowmanxxxxxxxxxxx > 0.0) {
                     _snowmanxxxxxxx = afm.a(_snowmanxxxxxxx / _snowmanxxxxxxxxxxx, (double)(-_snowmanxxxxxxxxxx), (double)_snowmanxxxxxxxxxx);
                  }

                  this.f(this.cC().b(0.0, _snowmanxxxxxxx * 0.01, 0.0));
                  this.p = afm.g(this.p);
                  double _snowmanxxxxxxxxxxxx = afm.a(afm.g(180.0 - afm.d(_snowmanxxxxxx, _snowmanxxxxxxxx) * 180.0F / (float)Math.PI - (double)this.p), -50.0, 50.0);
                  dcn _snowmanxxxxxxxxxxxxx = _snowmanxxxxx.a(this.cD(), this.cE(), this.cH()).d();
                  dcn _snowmanxxxxxxxxxxxxxx = new dcn(
                        (double)afm.a(this.p * (float) (Math.PI / 180.0)), this.cC().c, (double)(-afm.b(this.p * (float) (Math.PI / 180.0)))
                     )
                     .d();
                  float _snowmanxxxxxxxxxxxxxxx = Math.max(((float)_snowmanxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxx) + 0.5F) / 1.5F, 0.0F);
                  this.bt *= 0.8F;
                  this.bt = (float)((double)this.bt + _snowmanxxxxxxxxxxxx * (double)_snowmanxxxx.h());
                  this.p = this.p + this.bt * 0.1F;
                  float _snowmanxxxxxxxxxxxxxxxx = (float)(2.0 / (_snowmanxxxxxxxxx + 1.0));
                  float _snowmanxxxxxxxxxxxxxxxxx = 0.06F;
                  this.a(0.06F * (_snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx + (1.0F - _snowmanxxxxxxxxxxxxxxxx)), new dcn(0.0, 0.0, -1.0));
                  if (this.br) {
                     this.a(aqr.a, this.cC().a(0.8F));
                  } else {
                     this.a(aqr.a, this.cC());
                  }

                  dcn _snowmanxxxxxxxxxxxxxxxxxx = this.cC().d();
                  double _snowmanxxxxxxxxxxxxxxxxxxx = 0.8 + 0.15 * (_snowmanxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxx) + 1.0) / 2.0;
                  this.f(this.cC().d(_snowmanxxxxxxxxxxxxxxxxxxx, 0.91F, _snowmanxxxxxxxxxxxxxxxxxxx));
               }
            }

            this.aA = this.p;
            dcn[] _snowmanxxxxx = new dcn[this.bx.length];

            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < this.bx.length; _snowmanxxxxxxxxxxxx++) {
               _snowmanxxxxx[_snowmanxxxxxxxxxxxx] = new dcn(this.bx[_snowmanxxxxxxxxxxxx].cD(), this.bx[_snowmanxxxxxxxxxxxx].cE(), this.bx[_snowmanxxxxxxxxxxxx].cH());
            }

            float _snowmanxxxxxxxxxxxx = (float)(this.a(5, 1.0F)[1] - this.a(10, 1.0F)[1]) * 10.0F * (float) (Math.PI / 180.0);
            float _snowmanxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxx);
            float _snowmanxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxx);
            float _snowmanxxxxxxxxxxxxxxx = this.p * (float) (Math.PI / 180.0);
            float _snowmanxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxxxxx);
            float _snowmanxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxxxxx);
            this.a(this.bz, (double)(_snowmanxxxxxxxxxxxxxxxx * 0.5F), 0.0, (double)(-_snowmanxxxxxxxxxxxxxxxxx * 0.5F));
            this.a(this.bD, (double)(_snowmanxxxxxxxxxxxxxxxxx * 4.5F), 2.0, (double)(_snowmanxxxxxxxxxxxxxxxx * 4.5F));
            this.a(this.bE, (double)(_snowmanxxxxxxxxxxxxxxxxx * -4.5F), 2.0, (double)(_snowmanxxxxxxxxxxxxxxxx * -4.5F));
            if (!this.l.v && this.an == 0) {
               this.a(this.l.a(this, this.bD.cc().c(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0), aqd.e));
               this.a(this.l.a(this, this.bE.cc().c(4.0, 2.0, 4.0).d(0.0, -2.0, 0.0), aqd.e));
               this.b(this.l.a(this, this.bo.cc().g(1.0), aqd.e));
               this.b(this.l.a(this, this.by.cc().g(1.0), aqd.e));
            }

            float _snowmanxxxxxxxxxxxxxxxxxx = afm.a(this.p * (float) (Math.PI / 180.0) - this.bt * 0.01F);
            float _snowmanxxxxxxxxxxxxxxxxxxx = afm.b(this.p * (float) (Math.PI / 180.0) - this.bt * 0.01F);
            float _snowmanxxxxxxxxxxxxxxxxxxxx = this.eM();
            this.a(
               this.bo,
               (double)(_snowmanxxxxxxxxxxxxxxxxxx * 6.5F * _snowmanxxxxxxxxxxxxx),
               (double)(_snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx * 6.5F),
               (double)(-_snowmanxxxxxxxxxxxxxxxxxxx * 6.5F * _snowmanxxxxxxxxxxxxx)
            );
            this.a(
               this.by,
               (double)(_snowmanxxxxxxxxxxxxxxxxxx * 5.5F * _snowmanxxxxxxxxxxxxx),
               (double)(_snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx * 5.5F),
               (double)(-_snowmanxxxxxxxxxxxxxxxxxxx * 5.5F * _snowmanxxxxxxxxxxxxx)
            );
            double[] _snowmanxxxxxxxxxxxxxxxxxxxxx = this.a(5, 1.0F);

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
               bbp _snowmanxxxxxxxxxxxxxxxxxxxxxxx = null;
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxx == 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.bA;
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxx == 1) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.bB;
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxx == 2) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.bC;
               }

               double[] _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = this.a(12 + _snowmanxxxxxxxxxxxxxxxxxxxxxx * 2, 1.0F);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.p * (float) (Math.PI / 180.0)
                  + this.i(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx[0] - _snowmanxxxxxxxxxxxxxxxxxxxxx[0]) * (float) (Math.PI / 180.0);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 1.5F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 1) * 2.0F;
               this.a(
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
                  (double)(-(_snowmanxxxxxxxxxxxxxxxx * 1.5F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxxxxx),
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx[1] - _snowmanxxxxxxxxxxxxxxxxxxxxx[1] - (double)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1.5F) * _snowmanxxxxxxxxxxxxxx) + 1.5,
                  (double)((_snowmanxxxxxxxxxxxxxxxxx * 1.5F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxxxxx)
               );
            }

            if (!this.l.v) {
               this.br = this.b(this.bo.cc()) | this.b(this.by.cc()) | this.b(this.bz.cc());
               if (this.bF != null) {
                  this.bF.b(this);
               }
            }

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxx < this.bx.length; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
               this.bx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].m = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].b;
               this.bx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].n = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].c;
               this.bx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].o = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].d;
               this.bx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].D = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].b;
               this.bx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].E = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].c;
               this.bx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].F = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].d;
            }
         }
      }
   }

   private void a(bbp var1, double var2, double var4, double var6) {
      _snowman.d(this.cD() + _snowman, this.cE() + _snowman, this.cH() + _snowman);
   }

   private float eM() {
      if (this.bG.a().a()) {
         return -1.0F;
      } else {
         double[] _snowman = this.a(5, 1.0F);
         double[] _snowmanx = this.a(0, 1.0F);
         return (float)(_snowman[1] - _snowmanx[1]);
      }
   }

   private void eN() {
      if (this.bu != null) {
         if (this.bu.y) {
            this.bu = null;
         } else if (this.K % 10 == 0 && this.dk() < this.dx()) {
            this.c(this.dk() + 1.0F);
         }
      }

      if (this.J.nextInt(10) == 0) {
         List<bbq> _snowman = this.l.a(bbq.class, this.cc().g(32.0));
         bbq _snowmanx = null;
         double _snowmanxx = Double.MAX_VALUE;

         for (bbq _snowmanxxx : _snowman) {
            double _snowmanxxxx = _snowmanxxx.h(this);
            if (_snowmanxxxx < _snowmanxx) {
               _snowmanxx = _snowmanxxxx;
               _snowmanx = _snowmanxxx;
            }
         }

         this.bu = _snowmanx;
      }
   }

   private void a(List<aqa> var1) {
      double _snowman = (this.bz.cc().a + this.bz.cc().d) / 2.0;
      double _snowmanx = (this.bz.cc().c + this.bz.cc().f) / 2.0;

      for (aqa _snowmanxx : _snowman) {
         if (_snowmanxx instanceof aqm) {
            double _snowmanxxx = _snowmanxx.cD() - _snowman;
            double _snowmanxxxx = _snowmanxx.cH() - _snowmanx;
            double _snowmanxxxxx = Math.max(_snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx, 0.1);
            _snowmanxx.i(_snowmanxxx / _snowmanxxxxx * 4.0, 0.2F, _snowmanxxxx / _snowmanxxxxx * 4.0);
            if (!this.bG.a().a() && ((aqm)_snowmanxx).da() < _snowmanxx.K - 2) {
               _snowmanxx.a(apk.c(this), 5.0F);
               this.a(this, _snowmanxx);
            }
         }
      }
   }

   private void b(List<aqa> var1) {
      for (aqa _snowman : _snowman) {
         if (_snowman instanceof aqm) {
            _snowman.a(apk.c(this), 10.0F);
            this.a(this, _snowman);
         }
      }
   }

   private float i(double var1) {
      return (float)afm.g(_snowman);
   }

   private boolean b(dci var1) {
      int _snowman = afm.c(_snowman.a);
      int _snowmanx = afm.c(_snowman.b);
      int _snowmanxx = afm.c(_snowman.c);
      int _snowmanxxx = afm.c(_snowman.d);
      int _snowmanxxxx = afm.c(_snowman.e);
      int _snowmanxxxxx = afm.c(_snowman.f);
      boolean _snowmanxxxxxx = false;
      boolean _snowmanxxxxxxx = false;

      for (int _snowmanxxxxxxxx = _snowman; _snowmanxxxxxxxx <= _snowmanxxx; _snowmanxxxxxxxx++) {
         for (int _snowmanxxxxxxxxx = _snowmanx; _snowmanxxxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxxxxx++) {
               fx _snowmanxxxxxxxxxxx = new fx(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
               ceh _snowmanxxxxxxxxxxxx = this.l.d_(_snowmanxxxxxxxxxxx);
               buo _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.b();
               if (!_snowmanxxxxxxxxxxxx.g() && _snowmanxxxxxxxxxxxx.c() != cva.n) {
                  if (this.l.V().b(brt.b) && !aed.ag.a(_snowmanxxxxxxxxxxxxx)) {
                     _snowmanxxxxxxx = this.l.a(_snowmanxxxxxxxxxxx, false) || _snowmanxxxxxxx;
                  } else {
                     _snowmanxxxxxx = true;
                  }
               }
            }
         }
      }

      if (_snowmanxxxxxxx) {
         fx _snowmanxxxxxxxx = new fx(_snowman + this.J.nextInt(_snowmanxxx - _snowman + 1), _snowmanx + this.J.nextInt(_snowmanxxxx - _snowmanx + 1), _snowmanxx + this.J.nextInt(_snowmanxxxxx - _snowmanxx + 1));
         this.l.c(2008, _snowmanxxxxxxxx, 0);
      }

      return _snowmanxxxxxx;
   }

   public boolean a(bbp var1, apk var2, float var3) {
      if (this.bG.a().i() == bch.j) {
         return false;
      } else {
         _snowman = this.bG.a().a(_snowman, _snowman);
         if (_snowman != this.bo) {
            _snowman = _snowman / 4.0F + Math.min(_snowman, 1.0F);
         }

         if (_snowman < 0.01F) {
            return false;
         } else {
            if (_snowman.k() instanceof bfw || _snowman.d()) {
               float _snowman = this.dk();
               this.f(_snowman, _snowman);
               if (this.dl() && !this.bG.a().a()) {
                  this.c(1.0F);
                  this.bG.a(bch.j);
               }

               if (this.bG.a().a()) {
                  this.bI = (int)((float)this.bI + (_snowman - this.dk()));
                  if ((float)this.bI > 0.25F * this.dx()) {
                     this.bI = 0;
                     this.bG.a(bch.e);
                  }
               }
            }

            return true;
         }
      }
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (_snowman instanceof apl && ((apl)_snowman).y()) {
         this.a(this.bz, _snowman, _snowman);
      }

      return false;
   }

   protected boolean f(apk var1, float var2) {
      return super.a(_snowman, _snowman);
   }

   @Override
   public void aa() {
      this.ad();
      if (this.bF != null) {
         this.bF.b(this);
         this.bF.a(this);
      }
   }

   @Override
   protected void cU() {
      if (this.bF != null) {
         this.bF.b(this);
      }

      this.bs++;
      if (this.bs >= 180 && this.bs <= 200) {
         float _snowman = (this.J.nextFloat() - 0.5F) * 8.0F;
         float _snowmanx = (this.J.nextFloat() - 0.5F) * 4.0F;
         float _snowmanxx = (this.J.nextFloat() - 0.5F) * 8.0F;
         this.l.a(hh.v, this.cD() + (double)_snowman, this.cE() + 2.0 + (double)_snowmanx, this.cH() + (double)_snowmanxx, 0.0, 0.0, 0.0);
      }

      boolean _snowman = this.l.V().b(brt.e);
      int _snowmanx = 500;
      if (this.bF != null && !this.bF.d()) {
         _snowmanx = 12000;
      }

      if (!this.l.v) {
         if (this.bs > 150 && this.bs % 5 == 0 && _snowman) {
            this.a(afm.d((float)_snowmanx * 0.08F));
         }

         if (this.bs == 1 && !this.aA()) {
            this.l.b(1028, this.cB(), 0);
         }
      }

      this.a(aqr.a, new dcn(0.0, 0.1F, 0.0));
      this.p += 20.0F;
      this.aA = this.p;
      if (this.bs == 200 && !this.l.v) {
         if (_snowman) {
            this.a(afm.d((float)_snowmanx * 0.2F));
         }

         if (this.bF != null) {
            this.bF.a(this);
         }

         this.ad();
      }
   }

   private void a(int var1) {
      while (_snowman > 0) {
         int _snowman = aqg.a(_snowman);
         _snowman -= _snowman;
         this.l.c(new aqg(this.l, this.cD(), this.cE(), this.cH(), _snowman));
      }
   }

   public int eI() {
      if (this.bJ[0] == null) {
         for (int _snowman = 0; _snowman < 24; _snowman++) {
            int _snowmanx = 5;
            int _snowmanxx;
            int _snowmanxxx;
            if (_snowman < 12) {
               _snowmanxx = afm.d(60.0F * afm.b(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)_snowman)));
               _snowmanxxx = afm.d(60.0F * afm.a(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)_snowman)));
            } else if (_snowman < 20) {
               int var3 = _snowman - 12;
               _snowmanxx = afm.d(40.0F * afm.b(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)var3)));
               _snowmanxxx = afm.d(40.0F * afm.a(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)var3)));
               _snowmanx += 10;
            } else {
               int var7 = _snowman - 20;
               _snowmanxx = afm.d(20.0F * afm.b(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
               _snowmanxxx = afm.d(20.0F * afm.a(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
            }

            int _snowmanxxxx = Math.max(this.l.t_() + 10, this.l.a(chn.a.f, new fx(_snowmanxx, 0, _snowmanxxx)).v() + _snowmanx);
            this.bJ[_snowman] = new cxb(_snowmanxx, _snowmanxxxx, _snowmanxxx);
         }

         this.bK[0] = 6146;
         this.bK[1] = 8197;
         this.bK[2] = 8202;
         this.bK[3] = 16404;
         this.bK[4] = 32808;
         this.bK[5] = 32848;
         this.bK[6] = 65696;
         this.bK[7] = 131392;
         this.bK[8] = 131712;
         this.bK[9] = 263424;
         this.bK[10] = 526848;
         this.bK[11] = 525313;
         this.bK[12] = 1581057;
         this.bK[13] = 3166214;
         this.bK[14] = 2138120;
         this.bK[15] = 6373424;
         this.bK[16] = 4358208;
         this.bK[17] = 12910976;
         this.bK[18] = 9044480;
         this.bK[19] = 9706496;
         this.bK[20] = 15216640;
         this.bK[21] = 13688832;
         this.bK[22] = 11763712;
         this.bK[23] = 8257536;
      }

      return this.p(this.cD(), this.cE(), this.cH());
   }

   public int p(double var1, double var3, double var5) {
      float _snowman = 10000.0F;
      int _snowmanx = 0;
      cxb _snowmanxx = new cxb(afm.c(_snowman), afm.c(_snowman), afm.c(_snowman));
      int _snowmanxxx = 0;
      if (this.bF == null || this.bF.c() == 0) {
         _snowmanxxx = 12;
      }

      for (int _snowmanxxxx = _snowmanxxx; _snowmanxxxx < 24; _snowmanxxxx++) {
         if (this.bJ[_snowmanxxxx] != null) {
            float _snowmanxxxxx = this.bJ[_snowmanxxxx].b(_snowmanxx);
            if (_snowmanxxxxx < _snowman) {
               _snowman = _snowmanxxxxx;
               _snowmanx = _snowmanxxxx;
            }
         }
      }

      return _snowmanx;
   }

   @Nullable
   public cxd a(int var1, int var2, @Nullable cxb var3) {
      for (int _snowman = 0; _snowman < 24; _snowman++) {
         cxb _snowmanx = this.bJ[_snowman];
         _snowmanx.i = false;
         _snowmanx.g = 0.0F;
         _snowmanx.e = 0.0F;
         _snowmanx.f = 0.0F;
         _snowmanx.h = null;
         _snowmanx.d = -1;
      }

      cxb _snowman = this.bJ[_snowman];
      cxb _snowmanx = this.bJ[_snowman];
      _snowman.e = 0.0F;
      _snowman.f = _snowman.a(_snowmanx);
      _snowman.g = _snowman.f;
      this.bL.a();
      this.bL.a(_snowman);
      cxb _snowmanxx = _snowman;
      int _snowmanxxx = 0;
      if (this.bF == null || this.bF.c() == 0) {
         _snowmanxxx = 12;
      }

      while (!this.bL.e()) {
         cxb _snowmanxxxx = this.bL.c();
         if (_snowmanxxxx.equals(_snowmanx)) {
            if (_snowman != null) {
               _snowman.h = _snowmanx;
               _snowmanx = _snowman;
            }

            return this.a(_snowman, _snowmanx);
         }

         if (_snowmanxxxx.a(_snowmanx) < _snowmanxx.a(_snowmanx)) {
            _snowmanxx = _snowmanxxxx;
         }

         _snowmanxxxx.i = true;
         int _snowmanxxxxx = 0;

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 24; _snowmanxxxxxx++) {
            if (this.bJ[_snowmanxxxxxx] == _snowmanxxxx) {
               _snowmanxxxxx = _snowmanxxxxxx;
               break;
            }
         }

         for (int _snowmanxxxxxxx = _snowmanxxx; _snowmanxxxxxxx < 24; _snowmanxxxxxxx++) {
            if ((this.bK[_snowmanxxxxx] & 1 << _snowmanxxxxxxx) > 0) {
               cxb _snowmanxxxxxxxx = this.bJ[_snowmanxxxxxxx];
               if (!_snowmanxxxxxxxx.i) {
                  float _snowmanxxxxxxxxx = _snowmanxxxx.e + _snowmanxxxx.a(_snowmanxxxxxxxx);
                  if (!_snowmanxxxxxxxx.c() || _snowmanxxxxxxxxx < _snowmanxxxxxxxx.e) {
                     _snowmanxxxxxxxx.h = _snowmanxxxx;
                     _snowmanxxxxxxxx.e = _snowmanxxxxxxxxx;
                     _snowmanxxxxxxxx.f = _snowmanxxxxxxxx.a(_snowmanx);
                     if (_snowmanxxxxxxxx.c()) {
                        this.bL.a(_snowmanxxxxxxxx, _snowmanxxxxxxxx.e + _snowmanxxxxxxxx.f);
                     } else {
                        _snowmanxxxxxxxx.g = _snowmanxxxxxxxx.e + _snowmanxxxxxxxx.f;
                        this.bL.a(_snowmanxxxxxxxx);
                     }
                  }
               }
            }
         }
      }

      if (_snowmanxx == _snowman) {
         return null;
      } else {
         bv.debug("Failed to find path from {} to {}", _snowman, _snowman);
         if (_snowman != null) {
            _snowman.h = _snowmanxx;
            _snowmanxx = _snowman;
         }

         return this.a(_snowman, _snowmanxx);
      }
   }

   private cxd a(cxb var1, cxb var2) {
      List<cxb> _snowman = Lists.newArrayList();
      cxb _snowmanx = _snowman;
      _snowman.add(0, _snowman);

      while (_snowmanx.h != null) {
         _snowmanx = _snowmanx.h;
         _snowman.add(0, _snowmanx);
      }

      return new cxd(_snowman, new fx(_snowman.a, _snowman.b, _snowman.c), true);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("DragonPhase", this.bG.a().i().b());
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.e("DragonPhase")) {
         this.bG.a(bch.a(_snowman.h("DragonPhase")));
      }
   }

   @Override
   public void cI() {
   }

   public bbp[] eJ() {
      return this.bx;
   }

   @Override
   public boolean aT() {
      return false;
   }

   @Override
   public adr cu() {
      return adr.f;
   }

   @Override
   protected adp I() {
      return adq.dp;
   }

   @Override
   protected adp e(apk var1) {
      return adq.du;
   }

   @Override
   protected float dG() {
      return 5.0F;
   }

   public float a(int var1, double[] var2, double[] var3) {
      bcb _snowman = this.bG.a();
      bch<? extends bcb> _snowmanx = _snowman.i();
      double _snowmanxx;
      if (_snowmanx == bch.d || _snowmanx == bch.e) {
         fx _snowmanxxx = this.l.a(chn.a.f, cjk.a);
         float _snowmanxxxx = Math.max(afm.a(_snowmanxxx.a(this.cA(), true)) / 4.0F, 1.0F);
         _snowmanxx = (double)((float)_snowman / _snowmanxxxx);
      } else if (_snowman.a()) {
         _snowmanxx = (double)_snowman;
      } else if (_snowman == 6) {
         _snowmanxx = 0.0;
      } else {
         _snowmanxx = _snowman[1] - _snowman[1];
      }

      return (float)_snowmanxx;
   }

   public dcn x(float var1) {
      bcb _snowman = this.bG.a();
      bch<? extends bcb> _snowmanx = _snowman.i();
      dcn _snowmanxx;
      if (_snowmanx == bch.d || _snowmanx == bch.e) {
         fx _snowmanxxx = this.l.a(chn.a.f, cjk.a);
         float _snowmanxxxx = Math.max(afm.a(_snowmanxxx.a(this.cA(), true)) / 4.0F, 1.0F);
         float _snowmanxxxxx = 6.0F / _snowmanxxxx;
         float _snowmanxxxxxx = this.q;
         float _snowmanxxxxxxx = 1.5F;
         this.q = -_snowmanxxxxx * 1.5F * 5.0F;
         _snowmanxx = this.f(_snowman);
         this.q = _snowmanxxxxxx;
      } else if (_snowman.a()) {
         float _snowmanxxx = this.q;
         float _snowmanxxxx = 1.5F;
         this.q = -45.0F;
         _snowmanxx = this.f(_snowman);
         this.q = _snowmanxxx;
      } else {
         _snowmanxx = this.f(_snowman);
      }

      return _snowmanxx;
   }

   public void a(bbq var1, fx var2, apk var3) {
      bfw _snowman;
      if (_snowman.k() instanceof bfw) {
         _snowman = (bfw)_snowman.k();
      } else {
         _snowman = this.l.a(bw, (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());
      }

      if (_snowman == this.bu) {
         this.a(this.bo, apk.d(_snowman), 10.0F);
      }

      this.bG.a().a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(us<?> var1) {
      if (b.equals(_snowman) && this.l.v) {
         this.bG.a(bch.a(this.ab().a(b)));
      }

      super.a(_snowman);
   }

   public bci eK() {
      return this.bG;
   }

   @Nullable
   public chg eL() {
      return this.bF;
   }

   @Override
   public boolean c(apu var1) {
      return false;
   }

   @Override
   protected boolean n(aqa var1) {
      return false;
   }

   @Override
   public boolean bO() {
      return false;
   }
}
