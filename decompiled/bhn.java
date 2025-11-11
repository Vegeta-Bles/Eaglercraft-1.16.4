import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import javax.annotation.Nullable;

public class bhn extends aqa {
   private static final us<Integer> b = uv.a(bhn.class, uu.b);
   private static final us<Integer> c = uv.a(bhn.class, uu.b);
   private static final us<Float> d = uv.a(bhn.class, uu.c);
   private static final us<Integer> e = uv.a(bhn.class, uu.b);
   private static final us<Boolean> f = uv.a(bhn.class, uu.i);
   private static final us<Boolean> g = uv.a(bhn.class, uu.i);
   private static final us<Integer> ag = uv.a(bhn.class, uu.b);
   private final float[] ah = new float[2];
   private float ai;
   private float aj;
   private float ak;
   private int al;
   private double am;
   private double an;
   private double ao;
   private double ap;
   private double aq;
   private boolean ar;
   private boolean as;
   private boolean at;
   private boolean au;
   private double av;
   private float aw;
   private bhn.a ax;
   private bhn.a ay;
   private double az;
   private boolean aA;
   private boolean aB;
   private float aC;
   private float aD;
   private float aE;

   public bhn(aqe<? extends bhn> var1, brx var2) {
      super(_snowman, _snowman);
      this.i = true;
   }

   public bhn(brx var1, double var2, double var4, double var6) {
      this(aqe.g, _snowman);
      this.d(_snowman, _snowman, _snowman);
      this.f(dcn.a);
      this.m = _snowman;
      this.n = _snowman;
      this.o = _snowman;
   }

   @Override
   protected float a(aqx var1, aqb var2) {
      return _snowman.b;
   }

   @Override
   protected boolean aC() {
      return false;
   }

   @Override
   protected void e() {
      this.R.a(b, 0);
      this.R.a(c, 1);
      this.R.a(d, 0.0F);
      this.R.a(e, bhn.b.a.ordinal());
      this.R.a(f, false);
      this.R.a(g, false);
      this.R.a(ag, 0);
   }

   @Override
   public boolean j(aqa var1) {
      return a(this, _snowman);
   }

   public static boolean a(aqa var0, aqa var1) {
      return (_snowman.aZ() || _snowman.aU()) && !_snowman.x(_snowman);
   }

   @Override
   public boolean aZ() {
      return true;
   }

   @Override
   public boolean aU() {
      return true;
   }

   @Override
   protected dcn a(gc.a var1, i.a var2) {
      return aqm.h(super.a(_snowman, _snowman));
   }

   @Override
   public double bc() {
      return -0.1;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else if (!this.l.v && !this.y) {
         this.c(-this.o());
         this.b(10);
         this.a(this.m() + _snowman * 10.0F);
         this.aS();
         boolean _snowman = _snowman.k() instanceof bfw && ((bfw)_snowman.k()).bC.d;
         if (_snowman || this.m() > 40.0F) {
            if (!_snowman && this.l.V().b(brt.g)) {
               this.a(this.g());
            }

            this.ad();
         }

         return true;
      } else {
         return true;
      }
   }

   @Override
   public void k(boolean var1) {
      if (!this.l.v) {
         this.aA = true;
         this.aB = _snowman;
         if (this.z() == 0) {
            this.d(60);
         }
      }

      this.l.a(hh.Z, this.cD() + (double)this.J.nextFloat(), this.cE() + 0.7, this.cH() + (double)this.J.nextFloat(), 0.0, 0.0, 0.0);
      if (this.J.nextInt(20) == 0) {
         this.l.a(this.cD(), this.cE(), this.cH(), this.aw(), this.cu(), 1.0F, 0.8F + 0.4F * this.J.nextFloat(), false);
      }
   }

   @Override
   public void i(aqa var1) {
      if (_snowman instanceof bhn) {
         if (_snowman.cc().b < this.cc().e) {
            super.i(_snowman);
         }
      } else if (_snowman.cc().b <= this.cc().b) {
         super.i(_snowman);
      }
   }

   public blx g() {
      switch (this.p()) {
         case a:
         default:
            return bmd.lR;
         case b:
            return bmd.qp;
         case c:
            return bmd.qq;
         case d:
            return bmd.qr;
         case e:
            return bmd.qs;
         case f:
            return bmd.qt;
      }
   }

   @Override
   public void bm() {
      this.c(-this.o());
      this.b(10);
      this.a(this.m() * 11.0F);
   }

   @Override
   public boolean aT() {
      return !this.y;
   }

   @Override
   public void a(double var1, double var3, double var5, float var7, float var8, int var9, boolean var10) {
      this.am = _snowman;
      this.an = _snowman;
      this.ao = _snowman;
      this.ap = (double)_snowman;
      this.aq = (double)_snowman;
      this.al = 10;
   }

   @Override
   public gc ca() {
      return this.bZ().g();
   }

   @Override
   public void j() {
      this.ay = this.ax;
      this.ax = this.s();
      if (this.ax != bhn.a.b && this.ax != bhn.a.c) {
         this.aj = 0.0F;
      } else {
         this.aj++;
      }

      if (!this.l.v && this.aj >= 60.0F) {
         this.be();
      }

      if (this.n() > 0) {
         this.b(this.n() - 1);
      }

      if (this.m() > 0.0F) {
         this.a(this.m() - 1.0F);
      }

      super.j();
      this.r();
      if (this.cs()) {
         if (this.cn().isEmpty() || !(this.cn().get(0) instanceof bfw)) {
            this.a(false, false);
         }

         this.v();
         if (this.l.v) {
            this.x();
            this.l.a(new sv(this.a(0), this.a(1)));
         }

         this.a(aqr.a, this.cC());
      } else {
         this.f(dcn.a);
      }

      this.q();

      for (int _snowman = 0; _snowman <= 1; _snowman++) {
         if (this.a(_snowman)) {
            if (!this.aA()
               && (double)(this.ah[_snowman] % (float) (Math.PI * 2)) <= (float) (Math.PI / 4)
               && ((double)this.ah[_snowman] + (float) (Math.PI / 8)) % (float) (Math.PI * 2) >= (float) (Math.PI / 4)) {
               adp _snowmanx = this.h();
               if (_snowmanx != null) {
                  dcn _snowmanxx = this.f(1.0F);
                  double _snowmanxxx = _snowman == 1 ? -_snowmanxx.d : _snowmanxx.d;
                  double _snowmanxxxx = _snowman == 1 ? _snowmanxx.b : -_snowmanxx.b;
                  this.l.a(null, this.cD() + _snowmanxxx, this.cE(), this.cH() + _snowmanxxxx, _snowmanx, this.cu(), 1.0F, 0.8F + 0.4F * this.J.nextFloat());
               }
            }

            this.ah[_snowman] = (float)((double)this.ah[_snowman] + (float) (Math.PI / 8));
         } else {
            this.ah[_snowman] = 0.0F;
         }
      }

      this.ay();
      List<aqa> _snowmanx = this.l.a(this, this.cc().c(0.2F, -0.01F, 0.2F), aqd.a(this));
      if (!_snowmanx.isEmpty()) {
         boolean _snowmanxx = !this.l.v && !(this.cm() instanceof bfw);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx.size(); _snowmanxxx++) {
            aqa _snowmanxxxx = _snowmanx.get(_snowmanxxx);
            if (!_snowmanxxxx.w(this)) {
               if (_snowmanxx
                  && this.cn().size() < 2
                  && !_snowmanxxxx.br()
                  && _snowmanxxxx.cy() < this.cy()
                  && _snowmanxxxx instanceof aqm
                  && !(_snowmanxxxx instanceof bay)
                  && !(_snowmanxxxx instanceof bfw)) {
                  _snowmanxxxx.m(this);
               } else {
                  this.i(_snowmanxxxx);
               }
            }
         }
      }
   }

   private void q() {
      if (this.l.v) {
         int _snowman = this.z();
         if (_snowman > 0) {
            this.aC += 0.05F;
         } else {
            this.aC -= 0.1F;
         }

         this.aC = afm.a(this.aC, 0.0F, 1.0F);
         this.aE = this.aD;
         this.aD = 10.0F * (float)Math.sin((double)(0.5F * (float)this.l.T())) * this.aC;
      } else {
         if (!this.aA) {
            this.d(0);
         }

         int _snowman = this.z();
         if (_snowman > 0) {
            this.d(--_snowman);
            int _snowmanx = 60 - _snowman - 1;
            if (_snowmanx > 0 && _snowman == 0) {
               this.d(0);
               dcn _snowmanxx = this.cC();
               if (this.aB) {
                  this.f(_snowmanxx.b(0.0, -0.7, 0.0));
                  this.be();
               } else {
                  this.n(_snowmanxx.b, this.a(bfw.class) ? 2.7 : 0.6, _snowmanxx.d);
               }
            }

            this.aA = false;
         }
      }
   }

   @Nullable
   protected adp h() {
      switch (this.s()) {
         case a:
         case b:
         case c:
            return adq.aR;
         case d:
            return adq.aQ;
         case e:
         default:
            return null;
      }
   }

   private void r() {
      if (this.cs()) {
         this.al = 0;
         this.c(this.cD(), this.cE(), this.cH());
      }

      if (this.al > 0) {
         double _snowman = this.cD() + (this.am - this.cD()) / (double)this.al;
         double _snowmanx = this.cE() + (this.an - this.cE()) / (double)this.al;
         double _snowmanxx = this.cH() + (this.ao - this.cH()) / (double)this.al;
         double _snowmanxxx = afm.g(this.ap - (double)this.p);
         this.p = (float)((double)this.p + _snowmanxxx / (double)this.al);
         this.q = (float)((double)this.q + (this.aq - (double)this.q) / (double)this.al);
         this.al--;
         this.d(_snowman, _snowmanx, _snowmanxx);
         this.a(this.p, this.q);
      }
   }

   public void a(boolean var1, boolean var2) {
      this.R.b(f, _snowman);
      this.R.b(g, _snowman);
   }

   public float a(int var1, float var2) {
      return this.a(_snowman) ? (float)afm.b((double)this.ah[_snowman] - (float) (Math.PI / 8), (double)this.ah[_snowman], (double)_snowman) : 0.0F;
   }

   private bhn.a s() {
      bhn.a _snowman = this.u();
      if (_snowman != null) {
         this.av = this.cc().e;
         return _snowman;
      } else if (this.t()) {
         return bhn.a.a;
      } else {
         float _snowmanx = this.k();
         if (_snowmanx > 0.0F) {
            this.aw = _snowmanx;
            return bhn.a.d;
         } else {
            return bhn.a.e;
         }
      }
   }

   public float i() {
      dci _snowman = this.cc();
      int _snowmanx = afm.c(_snowman.a);
      int _snowmanxx = afm.f(_snowman.d);
      int _snowmanxxx = afm.c(_snowman.e);
      int _snowmanxxxx = afm.f(_snowman.e - this.az);
      int _snowmanxxxxx = afm.c(_snowman.c);
      int _snowmanxxxxxx = afm.f(_snowman.f);
      fx.a _snowmanxxxxxxx = new fx.a();

      label39:
      for (int _snowmanxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxx++) {
         float _snowmanxxxxxxxxx = 0.0F;

         for (int _snowmanxxxxxxxxxx = _snowmanx; _snowmanxxxxxxxxxx < _snowmanxx; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxxxxx++) {
               _snowmanxxxxxxx.d(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx);
               cux _snowmanxxxxxxxxxxxx = this.l.b(_snowmanxxxxxxx);
               if (_snowmanxxxxxxxxxxxx.a(aef.b)) {
                  _snowmanxxxxxxxxx = Math.max(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxx.a((brc)this.l, _snowmanxxxxxxx));
               }

               if (_snowmanxxxxxxxxx >= 1.0F) {
                  continue label39;
               }
            }
         }

         if (_snowmanxxxxxxxxx < 1.0F) {
            return (float)_snowmanxxxxxxx.v() + _snowmanxxxxxxxxx;
         }
      }

      return (float)(_snowmanxxxx + 1);
   }

   public float k() {
      dci _snowman = this.cc();
      dci _snowmanx = new dci(_snowman.a, _snowman.b - 0.001, _snowman.c, _snowman.d, _snowman.b, _snowman.f);
      int _snowmanxx = afm.c(_snowmanx.a) - 1;
      int _snowmanxxx = afm.f(_snowmanx.d) + 1;
      int _snowmanxxxx = afm.c(_snowmanx.b) - 1;
      int _snowmanxxxxx = afm.f(_snowmanx.e) + 1;
      int _snowmanxxxxxx = afm.c(_snowmanx.c) - 1;
      int _snowmanxxxxxxx = afm.f(_snowmanx.f) + 1;
      ddh _snowmanxxxxxxxx = dde.a(_snowmanx);
      float _snowmanxxxxxxxxx = 0.0F;
      int _snowmanxxxxxxxxxx = 0;
      fx.a _snowmanxxxxxxxxxxx = new fx.a();

      for (int _snowmanxxxxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxx != _snowmanxx && _snowmanxxxxxxxxxxxx != _snowmanxxx - 1 ? 0 : 1)
               + (_snowmanxxxxxxxxxxxxx != _snowmanxxxxxx && _snowmanxxxxxxxxxxxxx != _snowmanxxxxxxx - 1 ? 0 : 1);
            if (_snowmanxxxxxxxxxxxxxx != 2) {
               for (int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxxxxxxxx++) {
                  if (_snowmanxxxxxxxxxxxxxx <= 0 || _snowmanxxxxxxxxxxxxxxx != _snowmanxxxx && _snowmanxxxxxxxxxxxxxxx != _snowmanxxxxx - 1) {
                     _snowmanxxxxxxxxxxx.d(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
                     ceh _snowmanxxxxxxxxxxxxxxxx = this.l.d_(_snowmanxxxxxxxxxxx);
                     if (!(_snowmanxxxxxxxxxxxxxxxx.b() instanceof cbo)
                        && dde.c(
                           _snowmanxxxxxxxxxxxxxxxx.k(this.l, _snowmanxxxxxxxxxxx).a((double)_snowmanxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxxxx),
                           _snowmanxxxxxxxx,
                           dcr.i
                        )) {
                        _snowmanxxxxxxxxx += _snowmanxxxxxxxxxxxxxxxx.b().j();
                        _snowmanxxxxxxxxxx++;
                     }
                  }
               }
            }
         }
      }

      return _snowmanxxxxxxxxx / (float)_snowmanxxxxxxxxxx;
   }

   private boolean t() {
      dci _snowman = this.cc();
      int _snowmanx = afm.c(_snowman.a);
      int _snowmanxx = afm.f(_snowman.d);
      int _snowmanxxx = afm.c(_snowman.b);
      int _snowmanxxxx = afm.f(_snowman.b + 0.001);
      int _snowmanxxxxx = afm.c(_snowman.c);
      int _snowmanxxxxxx = afm.f(_snowman.f);
      boolean _snowmanxxxxxxx = false;
      this.av = Double.MIN_VALUE;
      fx.a _snowmanxxxxxxxx = new fx.a();

      for (int _snowmanxxxxxxxxx = _snowmanx; _snowmanxxxxxxxxx < _snowmanxx; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxxxxx++) {
               _snowmanxxxxxxxx.d(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               cux _snowmanxxxxxxxxxxxx = this.l.b(_snowmanxxxxxxxx);
               if (_snowmanxxxxxxxxxxxx.a(aef.b)) {
                  float _snowmanxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxx.a((brc)this.l, _snowmanxxxxxxxx);
                  this.av = Math.max((double)_snowmanxxxxxxxxxxxxx, this.av);
                  _snowmanxxxxxxx |= _snowman.b < (double)_snowmanxxxxxxxxxxxxx;
               }
            }
         }
      }

      return _snowmanxxxxxxx;
   }

   @Nullable
   private bhn.a u() {
      dci _snowman = this.cc();
      double _snowmanx = _snowman.e + 0.001;
      int _snowmanxx = afm.c(_snowman.a);
      int _snowmanxxx = afm.f(_snowman.d);
      int _snowmanxxxx = afm.c(_snowman.e);
      int _snowmanxxxxx = afm.f(_snowmanx);
      int _snowmanxxxxxx = afm.c(_snowman.c);
      int _snowmanxxxxxxx = afm.f(_snowman.f);
      boolean _snowmanxxxxxxxx = false;
      fx.a _snowmanxxxxxxxxx = new fx.a();

      for (int _snowmanxxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = _snowmanxxxxxx; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxx; _snowmanxxxxxxxxxxxx++) {
               _snowmanxxxxxxxxx.d(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               cux _snowmanxxxxxxxxxxxxx = this.l.b(_snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxx.a(aef.b) && _snowmanx < (double)((float)_snowmanxxxxxxxxx.v() + _snowmanxxxxxxxxxxxxx.a((brc)this.l, _snowmanxxxxxxxxx))) {
                  if (!_snowmanxxxxxxxxxxxxx.b()) {
                     return bhn.a.c;
                  }

                  _snowmanxxxxxxxx = true;
               }
            }
         }
      }

      return _snowmanxxxxxxxx ? bhn.a.b : null;
   }

   private void v() {
      double _snowman = -0.04F;
      double _snowmanx = this.aB() ? 0.0 : -0.04F;
      double _snowmanxx = 0.0;
      this.ai = 0.05F;
      if (this.ay == bhn.a.e && this.ax != bhn.a.e && this.ax != bhn.a.d) {
         this.av = this.e(1.0);
         this.d(this.cD(), (double)(this.i() - this.cz()) + 0.101, this.cH());
         this.f(this.cC().d(1.0, 0.0, 1.0));
         this.az = 0.0;
         this.ax = bhn.a.a;
      } else {
         if (this.ax == bhn.a.a) {
            _snowmanxx = (this.av - this.cE()) / (double)this.cz();
            this.ai = 0.9F;
         } else if (this.ax == bhn.a.c) {
            _snowmanx = -7.0E-4;
            this.ai = 0.9F;
         } else if (this.ax == bhn.a.b) {
            _snowmanxx = 0.01F;
            this.ai = 0.45F;
         } else if (this.ax == bhn.a.e) {
            this.ai = 0.9F;
         } else if (this.ax == bhn.a.d) {
            this.ai = this.aw;
            if (this.cm() instanceof bfw) {
               this.aw /= 2.0F;
            }
         }

         dcn _snowmanxxx = this.cC();
         this.n(_snowmanxxx.b * (double)this.ai, _snowmanxxx.c + _snowmanx, _snowmanxxx.d * (double)this.ai);
         this.ak = this.ak * this.ai;
         if (_snowmanxx > 0.0) {
            dcn _snowmanxxxx = this.cC();
            this.n(_snowmanxxxx.b, (_snowmanxxxx.c + _snowmanxx * 0.06153846016296973) * 0.75, _snowmanxxxx.d);
         }
      }
   }

   private void x() {
      if (this.bs()) {
         float _snowman = 0.0F;
         if (this.ar) {
            this.ak--;
         }

         if (this.as) {
            this.ak++;
         }

         if (this.as != this.ar && !this.at && !this.au) {
            _snowman += 0.005F;
         }

         this.p = this.p + this.ak;
         if (this.at) {
            _snowman += 0.04F;
         }

         if (this.au) {
            _snowman -= 0.005F;
         }

         this.f(this.cC().b((double)(afm.a(-this.p * (float) (Math.PI / 180.0)) * _snowman), 0.0, (double)(afm.b(this.p * (float) (Math.PI / 180.0)) * _snowman)));
         this.a(this.as && !this.ar || this.at, this.ar && !this.as || this.at);
      }
   }

   @Override
   public void k(aqa var1) {
      if (this.w(_snowman)) {
         float _snowman = 0.0F;
         float _snowmanx = (float)((this.y ? 0.01F : this.bc()) + _snowman.bb());
         if (this.cn().size() > 1) {
            int _snowmanxx = this.cn().indexOf(_snowman);
            if (_snowmanxx == 0) {
               _snowman = 0.2F;
            } else {
               _snowman = -0.6F;
            }

            if (_snowman instanceof azz) {
               _snowman = (float)((double)_snowman + 0.2);
            }
         }

         dcn _snowmanxxx = new dcn((double)_snowman, 0.0, 0.0).b(-this.p * (float) (Math.PI / 180.0) - (float) (Math.PI / 2));
         _snowman.d(this.cD() + _snowmanxxx.b, this.cE() + (double)_snowmanx, this.cH() + _snowmanxxx.d);
         _snowman.p = _snowman.p + this.ak;
         _snowman.m(_snowman.bK() + this.ak);
         this.a(_snowman);
         if (_snowman instanceof azz && this.cn().size() > 1) {
            int _snowmanxxxx = _snowman.Y() % 2 == 0 ? 90 : 270;
            _snowman.n(((azz)_snowman).aA + (float)_snowmanxxxx);
            _snowman.m(_snowman.bK() + (float)_snowmanxxxx);
         }
      }
   }

   @Override
   public dcn b(aqm var1) {
      dcn _snowman = a((double)(this.cy() * afm.a), (double)_snowman.cy(), this.p);
      double _snowmanx = this.cD() + _snowman.b;
      double _snowmanxx = this.cH() + _snowman.d;
      fx _snowmanxxx = new fx(_snowmanx, this.cc().e, _snowmanxx);
      fx _snowmanxxxx = _snowmanxxx.c();
      if (!this.l.A(_snowmanxxxx)) {
         double _snowmanxxxxx = (double)_snowmanxxx.v() + this.l.h(_snowmanxxx);
         double _snowmanxxxxxx = (double)_snowmanxxx.v() + this.l.h(_snowmanxxxx);
         UnmodifiableIterator var13 = _snowman.ej().iterator();

         while (var13.hasNext()) {
            aqx _snowmanxxxxxxx = (aqx)var13.next();
            dcn _snowmanxxxxxxxx = bho.a(this.l, _snowmanx, _snowmanxxxxx, _snowmanxx, _snowman, _snowmanxxxxxxx);
            if (_snowmanxxxxxxxx != null) {
               _snowman.b(_snowmanxxxxxxx);
               return _snowmanxxxxxxxx;
            }

            dcn _snowmanxxxxxxxxx = bho.a(this.l, _snowmanx, _snowmanxxxxxx, _snowmanxx, _snowman, _snowmanxxxxxxx);
            if (_snowmanxxxxxxxxx != null) {
               _snowman.b(_snowmanxxxxxxx);
               return _snowmanxxxxxxxxx;
            }
         }
      }

      return super.b(_snowman);
   }

   protected void a(aqa var1) {
      _snowman.n(this.p);
      float _snowman = afm.g(_snowman.p - this.p);
      float _snowmanx = afm.a(_snowman, -105.0F, 105.0F);
      _snowman.r += _snowmanx - _snowman;
      _snowman.p += _snowmanx - _snowman;
      _snowman.m(_snowman.p);
   }

   @Override
   public void l(aqa var1) {
      this.a(_snowman);
   }

   @Override
   protected void b(md var1) {
      _snowman.a("Type", this.p().a());
   }

   @Override
   protected void a(md var1) {
      if (_snowman.c("Type", 8)) {
         this.a(bhn.b.a(_snowman.l("Type")));
      }
   }

   @Override
   public aou a(bfw var1, aot var2) {
      if (_snowman.eq()) {
         return aou.c;
      } else if (this.aj < 60.0F) {
         if (!this.l.v) {
            return _snowman.m(this) ? aou.b : aou.c;
         } else {
            return aou.a;
         }
      } else {
         return aou.c;
      }
   }

   @Override
   protected void a(double var1, boolean var3, ceh var4, fx var5) {
      this.az = this.cC().c;
      if (!this.br()) {
         if (_snowman) {
            if (this.C > 3.0F) {
               if (this.ax != bhn.a.d) {
                  this.C = 0.0F;
                  return;
               }

               this.b(this.C, 1.0F);
               if (!this.l.v && !this.y) {
                  this.ad();
                  if (this.l.V().b(brt.g)) {
                     for (int _snowman = 0; _snowman < 3; _snowman++) {
                        this.a(this.p().b());
                     }

                     for (int _snowman = 0; _snowman < 2; _snowman++) {
                        this.a(bmd.kP);
                     }
                  }
               }
            }

            this.C = 0.0F;
         } else if (!this.l.b(this.cB().c()).a(aef.b) && _snowman < 0.0) {
            this.C = (float)((double)this.C - _snowman);
         }
      }
   }

   public boolean a(int var1) {
      return this.R.a(_snowman == 0 ? f : g) && this.cm() != null;
   }

   public void a(float var1) {
      this.R.b(d, _snowman);
   }

   public float m() {
      return this.R.a(d);
   }

   public void b(int var1) {
      this.R.b(b, _snowman);
   }

   public int n() {
      return this.R.a(b);
   }

   private void d(int var1) {
      this.R.b(ag, _snowman);
   }

   private int z() {
      return this.R.a(ag);
   }

   public float b(float var1) {
      return afm.g(_snowman, this.aE, this.aD);
   }

   public void c(int var1) {
      this.R.b(c, _snowman);
   }

   public int o() {
      return this.R.a(c);
   }

   public void a(bhn.b var1) {
      this.R.b(e, _snowman.ordinal());
   }

   public bhn.b p() {
      return bhn.b.a(this.R.a(e));
   }

   @Override
   protected boolean q(aqa var1) {
      return this.cn().size() < 2 && !this.a(aef.b);
   }

   @Nullable
   @Override
   public aqa cm() {
      List<aqa> _snowman = this.cn();
      return _snowman.isEmpty() ? null : _snowman.get(0);
   }

   public void a(boolean var1, boolean var2, boolean var3, boolean var4) {
      this.ar = _snowman;
      this.as = _snowman;
      this.at = _snowman;
      this.au = _snowman;
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }

   @Override
   public boolean aI() {
      return this.ax == bhn.a.b || this.ax == bhn.a.c;
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e;

      private a() {
      }
   }

   public static enum b {
      a(bup.n, "oak"),
      b(bup.o, "spruce"),
      c(bup.p, "birch"),
      d(bup.q, "jungle"),
      e(bup.r, "acacia"),
      f(bup.s, "dark_oak");

      private final String g;
      private final buo h;

      private b(buo var3, String var4) {
         this.g = _snowman;
         this.h = _snowman;
      }

      public String a() {
         return this.g;
      }

      public buo b() {
         return this.h;
      }

      @Override
      public String toString() {
         return this.g;
      }

      public static bhn.b a(int var0) {
         bhn.b[] _snowman = values();
         if (_snowman < 0 || _snowman >= _snowman.length) {
            _snowman = 0;
         }

         return _snowman[_snowman];
      }

      public static bhn.b a(String var0) {
         bhn.b[] _snowman = values();

         for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
            if (_snowman[_snowmanx].a().equals(_snowman)) {
               return _snowman[_snowmanx];
            }
         }

         return _snowman[0];
      }
   }
}
