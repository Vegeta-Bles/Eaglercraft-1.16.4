import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class bgi extends bgm {
   private final Random b = new Random();
   private boolean c;
   private int d;
   private static final us<Integer> e = uv.a(bgi.class, uu.b);
   private static final us<Boolean> f = uv.a(bgi.class, uu.i);
   private int g;
   private int ag;
   private int ah;
   private int ai;
   private float aj;
   private boolean ak = true;
   private aqa al;
   private bgi.a am = bgi.a.a;
   private final int an;
   private final int ao;

   private bgi(brx var1, bfw var2, int var3, int var4) {
      super(aqe.bd, _snowman);
      this.Y = true;
      this.b(_snowman);
      _snowman.bI = this;
      this.an = Math.max(0, _snowman);
      this.ao = Math.max(0, _snowman);
   }

   public bgi(brx var1, bfw var2, double var3, double var5, double var7) {
      this(_snowman, _snowman, 0, 0);
      this.d(_snowman, _snowman, _snowman);
      this.m = this.cD();
      this.n = this.cE();
      this.o = this.cH();
   }

   public bgi(bfw var1, brx var2, int var3, int var4) {
      this(_snowman, _snowman, _snowman, _snowman);
      float _snowman = _snowman.q;
      float _snowmanx = _snowman.p;
      float _snowmanxx = afm.b(-_snowmanx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanxxx = afm.a(-_snowmanx * (float) (Math.PI / 180.0) - (float) Math.PI);
      float _snowmanxxxx = -afm.b(-_snowman * (float) (Math.PI / 180.0));
      float _snowmanxxxxx = afm.a(-_snowman * (float) (Math.PI / 180.0));
      double _snowmanxxxxxx = _snowman.cD() - (double)_snowmanxxx * 0.3;
      double _snowmanxxxxxxx = _snowman.cG();
      double _snowmanxxxxxxxx = _snowman.cH() - (double)_snowmanxx * 0.3;
      this.b(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanx, _snowman);
      dcn _snowmanxxxxxxxxx = new dcn((double)(-_snowmanxxx), (double)afm.a(-(_snowmanxxxxx / _snowmanxxxx), -5.0F, 5.0F), (double)(-_snowmanxx));
      double _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.f();
      _snowmanxxxxxxxxx = _snowmanxxxxxxxxx.d(
         0.6 / _snowmanxxxxxxxxxx + 0.5 + this.J.nextGaussian() * 0.0045,
         0.6 / _snowmanxxxxxxxxxx + 0.5 + this.J.nextGaussian() * 0.0045,
         0.6 / _snowmanxxxxxxxxxx + 0.5 + this.J.nextGaussian() * 0.0045
      );
      this.f(_snowmanxxxxxxxxx);
      this.p = (float)(afm.d(_snowmanxxxxxxxxx.b, _snowmanxxxxxxxxx.d) * 180.0F / (float)Math.PI);
      this.q = (float)(afm.d(_snowmanxxxxxxxxx.c, (double)afm.a(c(_snowmanxxxxxxxxx))) * 180.0F / (float)Math.PI);
      this.r = this.p;
      this.s = this.q;
   }

   @Override
   protected void e() {
      this.ab().a(e, 0);
      this.ab().a(f, false);
   }

   @Override
   public void a(us<?> var1) {
      if (e.equals(_snowman)) {
         int _snowman = this.ab().a(e);
         this.al = _snowman > 0 ? this.l.a(_snowman - 1) : null;
      }

      if (f.equals(_snowman)) {
         this.c = this.ab().a(f);
         if (this.c) {
            this.n(this.cC().b, (double)(-0.4F * afm.a(this.b, 0.6F, 1.0F)), this.cC().d);
         }
      }

      super.a(_snowman);
   }

   @Override
   public boolean a(double var1) {
      double _snowman = 64.0;
      return _snowman < 4096.0;
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
   }

   @Override
   public void j() {
      this.b.setSeed(this.bS().getLeastSignificantBits() ^ this.l.T());
      super.j();
      bfw _snowman = this.i();
      if (_snowman == null) {
         this.ad();
      } else if (this.l.v || !this.a(_snowman)) {
         if (this.t) {
            this.g++;
            if (this.g >= 1200) {
               this.ad();
               return;
            }
         } else {
            this.g = 0;
         }

         float _snowmanx = 0.0F;
         fx _snowmanxx = this.cB();
         cux _snowmanxxx = this.l.b(_snowmanxx);
         if (_snowmanxxx.a(aef.b)) {
            _snowmanx = _snowmanxxx.a((brc)this.l, _snowmanxx);
         }

         boolean _snowmanxxxx = _snowmanx > 0.0F;
         if (this.am == bgi.a.a) {
            if (this.al != null) {
               this.f(dcn.a);
               this.am = bgi.a.b;
               return;
            }

            if (_snowmanxxxx) {
               this.f(this.cC().d(0.3, 0.2, 0.3));
               this.am = bgi.a.c;
               return;
            }

            this.m();
         } else {
            if (this.am == bgi.a.b) {
               if (this.al != null) {
                  if (this.al.y) {
                     this.al = null;
                     this.am = bgi.a.a;
                  } else {
                     this.d(this.al.cD(), this.al.e(0.8), this.al.cH());
                  }
               }

               return;
            }

            if (this.am == bgi.a.c) {
               dcn _snowmanxxxxx = this.cC();
               double _snowmanxxxxxx = this.cE() + _snowmanxxxxx.c - (double)_snowmanxx.v() - (double)_snowmanx;
               if (Math.abs(_snowmanxxxxxx) < 0.01) {
                  _snowmanxxxxxx += Math.signum(_snowmanxxxxxx) * 0.1;
               }

               this.n(_snowmanxxxxx.b * 0.9, _snowmanxxxxx.c - _snowmanxxxxxx * (double)this.J.nextFloat() * 0.2, _snowmanxxxxx.d * 0.9);
               if (this.ag <= 0 && this.ai <= 0) {
                  this.ak = true;
               } else {
                  this.ak = this.ak && this.d < 10 && this.b(_snowmanxx);
               }

               if (_snowmanxxxx) {
                  this.d = Math.max(0, this.d - 1);
                  if (this.c) {
                     this.f(this.cC().b(0.0, -0.1 * (double)this.b.nextFloat() * (double)this.b.nextFloat(), 0.0));
                  }

                  if (!this.l.v) {
                     this.a(_snowmanxx);
                  }
               } else {
                  this.d = Math.min(10, this.d + 1);
               }
            }
         }

         if (!_snowmanxxx.a(aef.b)) {
            this.f(this.cC().b(0.0, -0.03, 0.0));
         }

         this.a(aqr.a, this.cC());
         this.x();
         if (this.am == bgi.a.a && (this.t || this.u)) {
            this.f(dcn.a);
         }

         double _snowmanxxxxxxx = 0.92;
         this.f(this.cC().a(0.92));
         this.af();
      }
   }

   private boolean a(bfw var1) {
      bmb _snowman = _snowman.dD();
      bmb _snowmanx = _snowman.dE();
      boolean _snowmanxx = _snowman.b() == bmd.mi;
      boolean _snowmanxxx = _snowmanx.b() == bmd.mi;
      if (!_snowman.y && _snowman.aX() && (_snowmanxx || _snowmanxxx) && !(this.h(_snowman) > 1024.0)) {
         return false;
      } else {
         this.ad();
         return true;
      }
   }

   private void m() {
      dcl _snowman = bgn.a(this, this::a);
      this.a(_snowman);
   }

   @Override
   protected boolean a(aqa var1) {
      return super.a(_snowman) || _snowman.aX() && _snowman instanceof bcv;
   }

   @Override
   protected void a(dck var1) {
      super.a(_snowman);
      if (!this.l.v) {
         this.al = _snowman.a();
         this.n();
      }
   }

   @Override
   protected void a(dcj var1) {
      super.a(_snowman);
      this.f(this.cC().d().a(_snowman.a(this)));
   }

   private void n() {
      this.ab().b(e, this.al.Y() + 1);
   }

   private void a(fx var1) {
      aag _snowman = (aag)this.l;
      int _snowmanx = 1;
      fx _snowmanxx = _snowman.b();
      if (this.J.nextFloat() < 0.25F && this.l.t(_snowmanxx)) {
         _snowmanx++;
      }

      if (this.J.nextFloat() < 0.5F && !this.l.e(_snowmanxx)) {
         _snowmanx--;
      }

      if (this.ag > 0) {
         this.ag--;
         if (this.ag <= 0) {
            this.ah = 0;
            this.ai = 0;
            this.ab().b(f, false);
         }
      } else if (this.ai > 0) {
         this.ai -= _snowmanx;
         if (this.ai > 0) {
            this.aj = (float)((double)this.aj + this.J.nextGaussian() * 4.0);
            float _snowmanxxx = this.aj * (float) (Math.PI / 180.0);
            float _snowmanxxxx = afm.a(_snowmanxxx);
            float _snowmanxxxxx = afm.b(_snowmanxxx);
            double _snowmanxxxxxx = this.cD() + (double)(_snowmanxxxx * (float)this.ai * 0.1F);
            double _snowmanxxxxxxx = (double)((float)afm.c(this.cE()) + 1.0F);
            double _snowmanxxxxxxxx = this.cH() + (double)(_snowmanxxxxx * (float)this.ai * 0.1F);
            ceh _snowmanxxxxxxxxx = _snowman.d_(new fx(_snowmanxxxxxx, _snowmanxxxxxxx - 1.0, _snowmanxxxxxxxx));
            if (_snowmanxxxxxxxxx.a(bup.A)) {
               if (this.J.nextFloat() < 0.15F) {
                  _snowman.a(hh.e, _snowmanxxxxxx, _snowmanxxxxxxx - 0.1F, _snowmanxxxxxxxx, 1, (double)_snowmanxxxx, 0.1, (double)_snowmanxxxxx, 0.0);
               }

               float _snowmanxxxxxxxxxx = _snowmanxxxx * 0.04F;
               float _snowmanxxxxxxxxxxx = _snowmanxxxxx * 0.04F;
               _snowman.a(hh.z, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 0, (double)_snowmanxxxxxxxxxxx, 0.01, (double)(-_snowmanxxxxxxxxxx), 1.0);
               _snowman.a(hh.z, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 0, (double)(-_snowmanxxxxxxxxxxx), 0.01, (double)_snowmanxxxxxxxxxx, 1.0);
            }
         } else {
            this.a(adq.em, 0.25F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.4F);
            double _snowmanxxx = this.cE() + 0.5;
            _snowman.a(hh.e, this.cD(), _snowmanxxx, this.cH(), (int)(1.0F + this.cy() * 20.0F), (double)this.cy(), 0.0, (double)this.cy(), 0.2F);
            _snowman.a(hh.z, this.cD(), _snowmanxxx, this.cH(), (int)(1.0F + this.cy() * 20.0F), (double)this.cy(), 0.0, (double)this.cy(), 0.2F);
            this.ag = afm.a(this.J, 20, 40);
            this.ab().b(f, true);
         }
      } else if (this.ah > 0) {
         this.ah -= _snowmanx;
         float _snowmanxxx = 0.15F;
         if (this.ah < 20) {
            _snowmanxxx = (float)((double)_snowmanxxx + (double)(20 - this.ah) * 0.05);
         } else if (this.ah < 40) {
            _snowmanxxx = (float)((double)_snowmanxxx + (double)(40 - this.ah) * 0.02);
         } else if (this.ah < 60) {
            _snowmanxxx = (float)((double)_snowmanxxx + (double)(60 - this.ah) * 0.01);
         }

         if (this.J.nextFloat() < _snowmanxxx) {
            float _snowmanxxxx = afm.a(this.J, 0.0F, 360.0F) * (float) (Math.PI / 180.0);
            float _snowmanxxxxx = afm.a(this.J, 25.0F, 60.0F);
            double _snowmanxxxxxx = this.cD() + (double)(afm.a(_snowmanxxxx) * _snowmanxxxxx * 0.1F);
            double _snowmanxxxxxxx = (double)((float)afm.c(this.cE()) + 1.0F);
            double _snowmanxxxxxxxx = this.cH() + (double)(afm.b(_snowmanxxxx) * _snowmanxxxxx * 0.1F);
            ceh _snowmanxxxxxxxxx = _snowman.d_(new fx(_snowmanxxxxxx, _snowmanxxxxxxx - 1.0, _snowmanxxxxxxxx));
            if (_snowmanxxxxxxxxx.a(bup.A)) {
               _snowman.a(hh.Z, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 2 + this.J.nextInt(2), 0.1F, 0.0, 0.1F, 0.0);
            }
         }

         if (this.ah <= 0) {
            this.aj = afm.a(this.J, 0.0F, 360.0F);
            this.ai = afm.a(this.J, 20, 80);
         }
      } else {
         this.ah = afm.a(this.J, 100, 600);
         this.ah = this.ah - this.ao * 20 * 5;
      }
   }

   private boolean b(fx var1) {
      bgi.b _snowman = bgi.b.c;

      for (int _snowmanx = -1; _snowmanx <= 2; _snowmanx++) {
         bgi.b _snowmanxx = this.a(_snowman.b(-2, _snowmanx, -2), _snowman.b(2, _snowmanx, 2));
         switch (_snowmanxx) {
            case c:
               return false;
            case a:
               if (_snowman == bgi.b.c) {
                  return false;
               }
               break;
            case b:
               if (_snowman == bgi.b.a) {
                  return false;
               }
         }

         _snowman = _snowmanxx;
      }

      return true;
   }

   private bgi.b a(fx var1, fx var2) {
      return fx.b(_snowman, _snowman).map(this::c).reduce((var0, var1x) -> var0 == var1x ? var0 : bgi.b.c).orElse(bgi.b.c);
   }

   private bgi.b c(fx var1) {
      ceh _snowman = this.l.d_(_snowman);
      if (!_snowman.g() && !_snowman.a(bup.dU)) {
         cux _snowmanx = _snowman.m();
         return _snowmanx.a(aef.b) && _snowmanx.b() && _snowman.k(this.l, _snowman).b() ? bgi.b.b : bgi.b.c;
      } else {
         return bgi.b.a;
      }
   }

   public boolean g() {
      return this.ak;
   }

   @Override
   public void b(md var1) {
   }

   @Override
   public void a(md var1) {
   }

   public int b(bmb var1) {
      bfw _snowman = this.i();
      if (!this.l.v && _snowman != null) {
         int _snowmanx = 0;
         if (this.al != null) {
            this.h();
            ac.D.a((aah)_snowman, _snowman, this, Collections.emptyList());
            this.l.a(this, (byte)31);
            _snowmanx = this.al instanceof bcv ? 3 : 5;
         } else if (this.ag > 0) {
            cyv.a _snowmanxx = new cyv.a((aag)this.l).a(dbc.f, this.cA()).a(dbc.i, _snowman).a(dbc.a, this).a(this.J).a((float)this.an + _snowman.eU());
            cyy _snowmanxxx = this.l.l().aJ().a(cyq.ag);
            List<bmb> _snowmanxxxx = _snowmanxxx.a(_snowmanxx.a(dbb.e));
            ac.D.a((aah)_snowman, _snowman, this, _snowmanxxxx);

            for (bmb _snowmanxxxxx : _snowmanxxxx) {
               bcv _snowmanxxxxxx = new bcv(this.l, this.cD(), this.cE(), this.cH(), _snowmanxxxxx);
               double _snowmanxxxxxxx = _snowman.cD() - this.cD();
               double _snowmanxxxxxxxx = _snowman.cE() - this.cE();
               double _snowmanxxxxxxxxx = _snowman.cH() - this.cH();
               double _snowmanxxxxxxxxxx = 0.1;
               _snowmanxxxxxx.n(
                  _snowmanxxxxxxx * 0.1,
                  _snowmanxxxxxxxx * 0.1 + Math.sqrt(Math.sqrt(_snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxxxx * _snowmanxxxxxxxxx)) * 0.08,
                  _snowmanxxxxxxxxx * 0.1
               );
               this.l.c(_snowmanxxxxxx);
               _snowman.l.c(new aqg(_snowman.l, _snowman.cD(), _snowman.cE() + 0.5, _snowman.cH() + 0.5, this.J.nextInt(6) + 1));
               if (_snowmanxxxxx.b().a(aeg.T)) {
                  _snowman.a(aea.Q, 1);
               }
            }

            _snowmanx = 1;
         }

         if (this.t) {
            _snowmanx = 2;
         }

         this.ad();
         return _snowmanx;
      } else {
         return 0;
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 31 && this.l.v && this.al instanceof bfw && ((bfw)this.al).ez()) {
         this.h();
      }

      super.a(_snowman);
   }

   protected void h() {
      aqa _snowman = this.v();
      if (_snowman != null) {
         dcn _snowmanx = new dcn(_snowman.cD() - this.cD(), _snowman.cE() - this.cE(), _snowman.cH() - this.cH()).a(0.1);
         this.al.f(this.al.cC().e(_snowmanx));
      }
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   public void ad() {
      super.ad();
      bfw _snowman = this.i();
      if (_snowman != null) {
         _snowman.bI = null;
      }
   }

   @Nullable
   public bfw i() {
      aqa _snowman = this.v();
      return _snowman instanceof bfw ? (bfw)_snowman : null;
   }

   @Nullable
   public aqa k() {
      return this.al;
   }

   @Override
   public boolean bO() {
      return false;
   }

   @Override
   public oj<?> P() {
      aqa _snowman = this.v();
      return new on(this, _snowman == null ? this.Y() : _snowman.Y());
   }

   static enum a {
      a,
      b,
      c;

      private a() {
      }
   }

   static enum b {
      a,
      b,
      c;

      private b() {
      }
   }
}
