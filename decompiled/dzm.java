import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;

public class dzm extends dzj {
   public final dwu e;
   private final aeb bR;
   private final djm bS;
   private final List<eme> bT = Lists.newArrayList();
   private int bU = 0;
   private double bV;
   private double bW;
   private double bX;
   private float bY;
   private float bZ;
   private boolean ca;
   private boolean cb;
   private boolean cc;
   private boolean cd;
   private int ce;
   private boolean cf;
   private String cg;
   public dzk f;
   protected final djz g;
   protected int bJ;
   public int bK;
   public float bL;
   public float bM;
   public float bN;
   public float bO;
   private int ch;
   private float ci;
   public float bP;
   public float bQ;
   private boolean cj;
   private aot ck;
   private boolean cl;
   private boolean cm = true;
   private int cn;
   private boolean co;
   private int cp;
   private boolean cq = true;

   public dzm(djz var1, dwt var2, dwu var3, aeb var4, djm var5, boolean var6, boolean var7) {
      super(_snowman, _snowman.g());
      this.g = _snowman;
      this.e = _snowman;
      this.bR = _snowman;
      this.bS = _snowman;
      this.cc = _snowman;
      this.cd = _snowman;
      this.bT.add(new emv(this, _snowman.W()));
      this.bT.add(new emj(this));
      this.bT.add(new emi(this, _snowman.W(), _snowman.d()));
   }

   @Override
   public boolean a(apk var1, float var2) {
      return false;
   }

   @Override
   public void b(float var1) {
   }

   @Override
   public boolean a(aqa var1, boolean var2) {
      if (!super.a(_snowman, _snowman)) {
         return false;
      } else {
         if (_snowman instanceof bhl) {
            this.g.W().a((emt)(new emo(this, (bhl)_snowman)));
         }

         if (_snowman instanceof bhn) {
            this.r = _snowman.p;
            this.p = _snowman.p;
            this.m(_snowman.p);
         }

         return true;
      }
   }

   @Override
   public void bf() {
      super.bf();
      this.cl = false;
   }

   @Override
   public float g(float var1) {
      return this.q;
   }

   @Override
   public float h(float var1) {
      return this.br() ? super.h(_snowman) : this.p;
   }

   @Override
   public void j() {
      if (this.l.C(new fx(this.cD(), 0.0, this.cH()))) {
         super.j();
         if (this.br()) {
            this.e.a(new st.c(this.p, this.q, this.t));
            this.e.a(new tb(this.aR, this.aT, this.f.g, this.f.h));
            aqa _snowman = this.cr();
            if (_snowman != this && _snowman.cs()) {
               this.e.a(new su(_snowman));
            }
         } else {
            this.O();
         }

         for (eme _snowman : this.bT) {
            _snowman.a();
         }
      }
   }

   public float w() {
      for (eme _snowman : this.bT) {
         if (_snowman instanceof emi) {
            return ((emi)_snowman).b();
         }
      }

      return 0.0F;
   }

   private void O() {
      boolean _snowman = this.bA();
      if (_snowman != this.cd) {
         ta.a _snowmanx = _snowman ? ta.a.d : ta.a.e;
         this.e.a(new ta(this, _snowmanx));
         this.cd = _snowman;
      }

      boolean _snowmanx = this.bu();
      if (_snowmanx != this.cc) {
         ta.a _snowmanxx = _snowmanx ? ta.a.a : ta.a.b;
         this.e.a(new ta(this, _snowmanxx));
         this.cc = _snowmanx;
      }

      if (this.K()) {
         double _snowmanxx = this.cD() - this.bV;
         double _snowmanxxx = this.cE() - this.bW;
         double _snowmanxxxx = this.cH() - this.bX;
         double _snowmanxxxxx = (double)(this.p - this.bY);
         double _snowmanxxxxxx = (double)(this.q - this.bZ);
         this.ce++;
         boolean _snowmanxxxxxxx = _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx > 9.0E-4 || this.ce >= 20;
         boolean _snowmanxxxxxxxx = _snowmanxxxxx != 0.0 || _snowmanxxxxxx != 0.0;
         if (this.br()) {
            dcn _snowmanxxxxxxxxx = this.cC();
            this.e.a(new st.b(_snowmanxxxxxxxxx.b, -999.0, _snowmanxxxxxxxxx.d, this.p, this.q, this.t));
            _snowmanxxxxxxx = false;
         } else if (_snowmanxxxxxxx && _snowmanxxxxxxxx) {
            this.e.a(new st.b(this.cD(), this.cE(), this.cH(), this.p, this.q, this.t));
         } else if (_snowmanxxxxxxx) {
            this.e.a(new st.a(this.cD(), this.cE(), this.cH(), this.t));
         } else if (_snowmanxxxxxxxx) {
            this.e.a(new st.c(this.p, this.q, this.t));
         } else if (this.ca != this.t) {
            this.e.a(new st(this.t));
         }

         if (_snowmanxxxxxxx) {
            this.bV = this.cD();
            this.bW = this.cE();
            this.bX = this.cH();
            this.ce = 0;
         }

         if (_snowmanxxxxxxxx) {
            this.bY = this.p;
            this.bZ = this.q;
         }

         this.ca = this.t;
         this.cm = this.g.k.J;
      }
   }

   @Override
   public boolean a(boolean var1) {
      sz.a _snowman = _snowman ? sz.a.d : sz.a.e;
      this.e.a(new sz(_snowman, fx.b, gc.a));
      return this.bm.a(this.bm.d, _snowman && !this.bm.f().a() ? this.bm.f().E() : 1) != bmb.b;
   }

   public void f(String var1) {
      this.e.a(new se(_snowman));
   }

   @Override
   public void a(aot var1) {
      super.a(_snowman);
      this.e.a(new tq(_snowman));
   }

   @Override
   public void ey() {
      this.e.a(new sf(sf.a.a));
   }

   @Override
   protected void e(apk var1, float var2) {
      if (!this.b(_snowman)) {
         this.c(this.dk() - _snowman);
      }
   }

   @Override
   public void m() {
      this.e.a(new sl(this.bp.b));
      this.x();
   }

   public void x() {
      this.bm.g(bmb.b);
      super.m();
      this.g.a(null);
   }

   public void v(float var1) {
      if (this.cf) {
         float _snowman = this.dk() - _snowman;
         if (_snowman <= 0.0F) {
            this.c(_snowman);
            if (_snowman < 0.0F) {
               this.P = 10;
            }
         } else {
            this.aP = _snowman;
            this.c(this.dk());
            this.P = 20;
            this.e(apk.n, _snowman);
            this.ao = 10;
            this.an = this.ao;
         }
      } else {
         this.c(_snowman);
         this.cf = true;
      }
   }

   @Override
   public void t() {
      this.e.a(new sy(this.bC));
   }

   @Override
   public boolean ez() {
      return true;
   }

   @Override
   public boolean ee() {
      return !this.bC.b && super.ee();
   }

   @Override
   public boolean aO() {
      return !this.bC.b && super.aO();
   }

   @Override
   public boolean cN() {
      return !this.bC.b && super.cN();
   }

   protected void z() {
      this.e.a(new ta(this, ta.a.f, afm.d(this.I() * 100.0F)));
   }

   public void A() {
      this.e.a(new ta(this, ta.a.h));
   }

   public void g(String var1) {
      this.cg = _snowman;
   }

   public String B() {
      return this.cg;
   }

   public aeb D() {
      return this.bR;
   }

   public djm F() {
      return this.bS;
   }

   public void a(boq<?> var1) {
      if (this.bS.d(_snowman)) {
         this.bS.e(_snowman);
         this.e.a(new td(_snowman));
      }
   }

   @Override
   protected int y() {
      return this.bU;
   }

   public void a(int var1) {
      this.bU = _snowman;
   }

   @Override
   public void a(nr var1, boolean var2) {
      if (_snowman) {
         this.g.j.a(_snowman, false);
      } else {
         this.g.j.c().a(_snowman);
      }
   }

   private void b(double var1, double var3) {
      fx _snowman = new fx(_snowman, this.cE(), _snowman);
      if (this.g(_snowman)) {
         double _snowmanx = _snowman - (double)_snowman.u();
         double _snowmanxx = _snowman - (double)_snowman.w();
         gc _snowmanxxx = null;
         double _snowmanxxxx = Double.MAX_VALUE;
         gc[] _snowmanxxxxx = new gc[]{gc.e, gc.f, gc.c, gc.d};

         for (gc _snowmanxxxxxx : _snowmanxxxxx) {
            double _snowmanxxxxxxx = _snowmanxxxxxx.n().a(_snowmanx, 0.0, _snowmanxx);
            double _snowmanxxxxxxxx = _snowmanxxxxxx.e() == gc.b.a ? 1.0 - _snowmanxxxxxxx : _snowmanxxxxxxx;
            if (_snowmanxxxxxxxx < _snowmanxxxx && !this.g(_snowman.a(_snowmanxxxxxx))) {
               _snowmanxxxx = _snowmanxxxxxxxx;
               _snowmanxxx = _snowmanxxxxxx;
            }
         }

         if (_snowmanxxx != null) {
            dcn _snowmanxxxxxxx = this.cC();
            if (_snowmanxxx.n() == gc.a.a) {
               this.n(0.1 * (double)_snowmanxxx.i(), _snowmanxxxxxxx.c, _snowmanxxxxxxx.d);
            } else {
               this.n(_snowmanxxxxxxx.b, _snowmanxxxxxxx.c, 0.1 * (double)_snowmanxxx.k());
            }
         }
      }
   }

   private boolean g(fx var1) {
      dci _snowman = this.cc();
      dci _snowmanx = new dci((double)_snowman.u(), _snowman.b, (double)_snowman.w(), (double)_snowman.u() + 1.0, _snowman.e, (double)_snowman.w() + 1.0).h(1.0E-7);
      return !this.l.a(this, _snowmanx, (var1x, var2x) -> var1x.o(this.l, var2x));
   }

   @Override
   public void g(boolean var1) {
      super.g(_snowman);
      this.bK = 0;
   }

   public void a(float var1, int var2, int var3) {
      this.bF = _snowman;
      this.bE = _snowman;
      this.bD = _snowman;
   }

   @Override
   public void a(nr var1, UUID var2) {
      this.g.j.c().a(_snowman);
   }

   @Override
   public void a(byte var1) {
      if (_snowman >= 24 && _snowman <= 28) {
         this.a(_snowman - 24);
      } else {
         super.a(_snowman);
      }
   }

   public void b(boolean var1) {
      this.cq = _snowman;
   }

   public boolean G() {
      return this.cq;
   }

   @Override
   public void a(adp var1, float var2, float var3) {
      this.l.a(this.cD(), this.cE(), this.cH(), _snowman, this.cu(), _snowman, _snowman, false);
   }

   @Override
   public void a(adp var1, adr var2, float var3, float var4) {
      this.l.a(this.cD(), this.cE(), this.cH(), _snowman, _snowman, _snowman, _snowman, false);
   }

   @Override
   public boolean dS() {
      return true;
   }

   @Override
   public void c(aot var1) {
      bmb _snowman = this.b(_snowman);
      if (!_snowman.a() && !this.dW()) {
         super.c(_snowman);
         this.cj = true;
         this.ck = _snowman;
      }
   }

   @Override
   public boolean dW() {
      return this.cj;
   }

   @Override
   public void ec() {
      super.ec();
      this.cj = false;
   }

   @Override
   public aot dX() {
      return this.ck;
   }

   @Override
   public void a(us<?> var1) {
      super.a(_snowman);
      if (ag.equals(_snowman)) {
         boolean _snowman = (this.R.a(ag) & 1) > 0;
         aot _snowmanx = (this.R.a(ag) & 2) > 0 ? aot.b : aot.a;
         if (_snowman && !this.cj) {
            this.c(_snowmanx);
         } else if (!_snowman && this.cj) {
            this.ec();
         }
      }

      if (S.equals(_snowman) && this.ef() && !this.co) {
         this.g.W().a((emt)(new emk(this)));
      }
   }

   public boolean H() {
      aqa _snowman = this.ct();
      return this.br() && _snowman instanceof aqw && ((aqw)_snowman).P_();
   }

   public float I() {
      return this.ci;
   }

   @Override
   public void a(cdf var1) {
      this.g.a(new dqv(_snowman));
   }

   @Override
   public void a(bqy var1) {
      this.g.a(new dqs(_snowman));
   }

   @Override
   public void a(cco var1) {
      this.g.a(new dpy(_snowman));
   }

   @Override
   public void a(cdj var1) {
      this.g.a(new dqz(_snowman));
   }

   @Override
   public void a(ccz var1) {
      this.g.a(new dqn(_snowman));
   }

   @Override
   public void a(bmb var1, aot var2) {
      blx _snowman = _snowman.b();
      if (_snowman == bmd.oT) {
         this.g.a(new dpu(this, _snowman, _snowman));
      }
   }

   @Override
   public void a(aqa var1) {
      this.g.f.a(_snowman, hh.g);
   }

   @Override
   public void b(aqa var1) {
      this.g.f.a(_snowman, hh.r);
   }

   @Override
   public boolean bu() {
      return this.f != null && this.f.h;
   }

   @Override
   public boolean bz() {
      return this.cb;
   }

   public boolean J() {
      return this.bz() || this.bD();
   }

   @Override
   public void dP() {
      super.dP();
      if (this.K()) {
         this.aR = this.f.a;
         this.aT = this.f.b;
         this.aQ = this.f.g;
         this.bN = this.bL;
         this.bO = this.bM;
         this.bM = (float)((double)this.bM + (double)(this.q - this.bM) * 0.5);
         this.bL = (float)((double)this.bL + (double)(this.p - this.bL) * 0.5);
      }
   }

   protected boolean K() {
      return this.g.aa() == this;
   }

   @Override
   public void k() {
      this.bK++;
      if (this.bJ > 0) {
         this.bJ--;
      }

      this.Q();
      boolean _snowman = this.f.g;
      boolean _snowmanx = this.f.h;
      boolean _snowmanxx = this.eY();
      this.cb = !this.bC.b && !this.bB() && this.c(aqx.f) && (this.bu() || !this.em() && !this.c(aqx.a));
      this.f.a(this.J());
      this.g.ao().a(this.f);
      if (this.dW() && !this.br()) {
         this.f.a *= 0.2F;
         this.f.b *= 0.2F;
         this.bJ = 0;
      }

      boolean _snowmanxxx = false;
      if (this.cn > 0) {
         this.cn--;
         _snowmanxxx = true;
         this.f.g = true;
      }

      if (!this.H) {
         this.b(this.cD() - (double)this.cy() * 0.35, this.cH() + (double)this.cy() * 0.35);
         this.b(this.cD() - (double)this.cy() * 0.35, this.cH() - (double)this.cy() * 0.35);
         this.b(this.cD() + (double)this.cy() * 0.35, this.cH() - (double)this.cy() * 0.35);
         this.b(this.cD() + (double)this.cy() * 0.35, this.cH() + (double)this.cy() * 0.35);
      }

      if (_snowmanx) {
         this.bJ = 0;
      }

      boolean _snowmanxxxx = (float)this.eI().a() > 6.0F || this.bC.c;
      if ((this.t || this.aI()) && !_snowmanx && !_snowmanxx && this.eY() && !this.bA() && _snowmanxxxx && !this.dW() && !this.a(apw.o)) {
         if (this.bJ <= 0 && !this.g.k.al.d()) {
            this.bJ = 7;
         } else {
            this.g(true);
         }
      }

      if (!this.bA() && (!this.aE() || this.aI()) && this.eY() && _snowmanxxxx && !this.dW() && !this.a(apw.o) && this.g.k.al.d()) {
         this.g(true);
      }

      if (this.bA()) {
         boolean _snowmanxxxxx = !this.f.b() || !_snowmanxxxx;
         boolean _snowmanxxxxxx = _snowmanxxxxx || this.u || this.aE() && !this.aI();
         if (this.bB()) {
            if (!this.t && !this.f.h && _snowmanxxxxx || !this.aE()) {
               this.g(false);
            }
         } else if (_snowmanxxxxxx) {
            this.g(false);
         }
      }

      boolean _snowmanxxxxx = false;
      if (this.bC.c) {
         if (this.g.q.j()) {
            if (!this.bC.b) {
               this.bC.b = true;
               _snowmanxxxxx = true;
               this.t();
            }
         } else if (!_snowman && this.f.g && !_snowmanxxx) {
            if (this.br == 0) {
               this.br = 7;
            } else if (!this.bB()) {
               this.bC.b = !this.bC.b;
               _snowmanxxxxx = true;
               this.t();
               this.br = 0;
            }
         }
      }

      if (this.f.g && !_snowmanxxxxx && !_snowman && !this.bC.b && !this.br() && !this.c_()) {
         bmb _snowmanxxxxxx = this.b(aqf.e);
         if (_snowmanxxxxxx.b() == bmd.qo && bld.d(_snowmanxxxxxx) && this.eD()) {
            this.e.a(new ta(this, ta.a.i));
         }
      }

      this.co = this.ef();
      if (this.aE() && this.f.h && this.cT()) {
         this.dL();
      }

      if (this.a(aef.b)) {
         int _snowmanxxxxxx = this.a_() ? 10 : 1;
         this.cp = afm.a(this.cp + _snowmanxxxxxx, 0, 600);
      } else if (this.cp > 0) {
         this.a(aef.b);
         this.cp = afm.a(this.cp - 10, 0, 600);
      }

      if (this.bC.b && this.K()) {
         int _snowmanxxxxxx = 0;
         if (this.f.h) {
            _snowmanxxxxxx--;
         }

         if (this.f.g) {
            _snowmanxxxxxx++;
         }

         if (_snowmanxxxxxx != 0) {
            this.f(this.cC().b(0.0, (double)((float)_snowmanxxxxxx * this.bC.a() * 3.0F), 0.0));
         }
      }

      if (this.H()) {
         aqw _snowmanxxxxxxx = (aqw)this.ct();
         if (this.ch < 0) {
            this.ch++;
            if (this.ch == 0) {
               this.ci = 0.0F;
            }
         }

         if (_snowman && !this.f.g) {
            this.ch = -10;
            _snowmanxxxxxxx.b_(afm.d(this.I() * 100.0F));
            this.z();
         } else if (!_snowman && this.f.g) {
            this.ch = 0;
            this.ci = 0.0F;
         } else if (_snowman) {
            this.ch++;
            if (this.ch < 10) {
               this.ci = (float)this.ch * 0.1F;
            } else {
               this.ci = 0.8F + 2.0F / (float)(this.ch - 9) * 0.1F;
            }
         }
      } else {
         this.ci = 0.0F;
      }

      super.k();
      if (this.t && this.bC.b && !this.g.q.j()) {
         this.bC.b = false;
         this.t();
      }
   }

   private void Q() {
      this.bQ = this.bP;
      if (this.aa) {
         if (this.g.y != null && !this.g.y.ay_()) {
            if (this.g.y instanceof dpp) {
               this.m();
            }

            this.g.a(null);
         }

         if (this.bP == 0.0F) {
            this.g.W().a(emp.b(adq.lP, this.J.nextFloat() * 0.4F + 0.8F, 0.25F));
         }

         this.bP += 0.0125F;
         if (this.bP >= 1.0F) {
            this.bP = 1.0F;
         }

         this.aa = false;
      } else if (this.a(apw.i) && this.b(apw.i).b() > 60) {
         this.bP += 0.006666667F;
         if (this.bP > 1.0F) {
            this.bP = 1.0F;
         }
      } else {
         if (this.bP > 0.0F) {
            this.bP -= 0.05F;
         }

         if (this.bP < 0.0F) {
            this.bP = 0.0F;
         }
      }

      this.E();
   }

   @Override
   public void ba() {
      super.ba();
      this.cl = false;
      if (this.ct() instanceof bhn) {
         bhn _snowman = (bhn)this.ct();
         _snowman.a(this.f.e, this.f.f, this.f.c, this.f.d);
         this.cl = this.cl | (this.f.e || this.f.f || this.f.c || this.f.d);
      }
   }

   public boolean L() {
      return this.cl;
   }

   @Nullable
   @Override
   public apu c(@Nullable aps var1) {
      if (_snowman == apw.i) {
         this.bQ = 0.0F;
         this.bP = 0.0F;
      }

      return super.c(_snowman);
   }

   @Override
   public void a(aqr var1, dcn var2) {
      double _snowman = this.cD();
      double _snowmanx = this.cH();
      super.a(_snowman, _snowman);
      this.g((float)(this.cD() - _snowman), (float)(this.cH() - _snowmanx));
   }

   public boolean M() {
      return this.cm;
   }

   protected void g(float var1, float var2) {
      if (this.eW()) {
         dcn _snowman = this.cA();
         dcn _snowmanx = _snowman.b((double)_snowman, 0.0, (double)_snowman);
         dcn _snowmanxx = new dcn((double)_snowman, 0.0, (double)_snowman);
         float _snowmanxxx = this.dN();
         float _snowmanxxxx = (float)_snowmanxx.g();
         if (_snowmanxxxx <= 0.001F) {
            dcm _snowmanxxxxx = this.f.a();
            float _snowmanxxxxxx = _snowmanxxx * _snowmanxxxxx.i;
            float _snowmanxxxxxxx = _snowmanxxx * _snowmanxxxxx.j;
            float _snowmanxxxxxxxx = afm.a(this.p * (float) (Math.PI / 180.0));
            float _snowmanxxxxxxxxx = afm.b(this.p * (float) (Math.PI / 180.0));
            _snowmanxx = new dcn((double)(_snowmanxxxxxx * _snowmanxxxxxxxxx - _snowmanxxxxxxx * _snowmanxxxxxxxx), _snowmanxx.c, (double)(_snowmanxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxx * _snowmanxxxxxxxx));
            _snowmanxxxx = (float)_snowmanxx.g();
            if (_snowmanxxxx <= 0.001F) {
               return;
            }
         }

         float _snowmanxxxxx = afm.i(_snowmanxxxx);
         dcn _snowmanxxxxxx = _snowmanxx.a((double)_snowmanxxxxx);
         dcn _snowmanxxxxxxx = this.bj();
         float _snowmanxxxxxxxx = (float)(_snowmanxxxxxxx.b * _snowmanxxxxxx.b + _snowmanxxxxxxx.d * _snowmanxxxxxx.d);
         if (!(_snowmanxxxxxxxx < -0.15F)) {
            dcs _snowmanxxxxxxxxx = dcs.a(this);
            fx _snowmanxxxxxxxxxx = new fx(this.cD(), this.cc().e, this.cH());
            ceh _snowmanxxxxxxxxxxx = this.l.d_(_snowmanxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxx.b(this.l, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx).b()) {
               _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.b();
               ceh _snowmanxxxxxxxxxxxx = this.l.d_(_snowmanxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxx.b(this.l, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx).b()) {
                  float _snowmanxxxxxxxxxxxxx = 7.0F;
                  float _snowmanxxxxxxxxxxxxxx = 1.2F;
                  if (this.a(apw.h)) {
                     _snowmanxxxxxxxxxxxxxx += (float)(this.b(apw.h).c() + 1) * 0.75F;
                  }

                  float _snowmanxxxxxxxxxxxxxxx = Math.max(_snowmanxxx * 7.0F, 1.0F / _snowmanxxxxx);
                  dcn _snowmanxxxxxxxxxxxxxxxx = _snowmanx.e(_snowmanxxxxxx.a((double)_snowmanxxxxxxxxxxxxxxx));
                  float _snowmanxxxxxxxxxxxxxxxxx = this.cy();
                  float _snowmanxxxxxxxxxxxxxxxxxx = this.cz();
                  dci _snowmanxxxxxxxxxxxxxxxxxxx = new dci(_snowman, _snowmanxxxxxxxxxxxxxxxx.b(0.0, (double)_snowmanxxxxxxxxxxxxxxxxxx, 0.0))
                     .c((double)_snowmanxxxxxxxxxxxxxxxxx, 0.0, (double)_snowmanxxxxxxxxxxxxxxxxx);
                  dcn var19 = _snowman.b(0.0, 0.51F, 0.0);
                  _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.b(0.0, 0.51F, 0.0);
                  dcn _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.c(new dcn(0.0, 1.0, 0.0));
                  dcn _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.a((double)(_snowmanxxxxxxxxxxxxxxxxx * 0.5F));
                  dcn _snowmanxxxxxxxxxxxxxxxxxxxxxx = var19.d(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                  dcn _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.d(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                  dcn _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = var19.e(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                  dcn _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.e(_snowmanxxxxxxxxxxxxxxxxxxxxx);
                  Iterator<dci> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.l.d(this, _snowmanxxxxxxxxxxxxxxxxxxx, var0 -> true).flatMap(var0 -> var0.d().stream()).iterator();
                  float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = Float.MIN_VALUE;

                  while (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.hasNext()) {
                     dci _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.next();
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx)
                        || _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.e;
                        dcn _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.f();
                        fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new fx(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);

                        for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 1;
                           (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx;
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
                        ) {
                           fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.l.d_(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                           ddh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                           if (!(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b(
                                 this.l, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx
                              ))
                              .b()) {
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.c(gc.a.b)
                                 + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.v();
                              if ((double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx - this.cE() > (double)_snowmanxxxxxxxxxxxxxx) {
                                 return;
                              }
                           }

                           if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx > 1) {
                              _snowmanxxxxxxxxxx = _snowmanxxxxxxxxxx.b();
                              ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.l.d_(_snowmanxxxxxxxxxx);
                              if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b(this.l, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx).b()) {
                                 return;
                              }
                           }
                        }
                        break;
                     }
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx != Float.MIN_VALUE) {
                     float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx - this.cE());
                     if (!(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx <= 0.5F) && !(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx > _snowmanxxxxxxxxxxxxxx)) {
                        this.cn = 1;
                     }
                  }
               }
            }
         }
      }
   }

   private boolean eW() {
      return this.M() && this.cn <= 0 && this.t && !this.es() && !this.br() && this.eX() && (double)this.aq() >= 1.0;
   }

   private boolean eX() {
      dcm _snowman = this.f.a();
      return _snowman.i != 0.0F || _snowman.j != 0.0F;
   }

   private boolean eY() {
      double _snowman = 0.8;
      return this.aI() ? this.f.b() : (double)this.f.b >= 0.8;
   }

   public float N() {
      if (!this.a(aef.b)) {
         return 0.0F;
      } else {
         float _snowman = 600.0F;
         float _snowmanx = 100.0F;
         if ((float)this.cp >= 600.0F) {
            return 1.0F;
         } else {
            float _snowmanxx = afm.a((float)this.cp / 100.0F, 0.0F, 1.0F);
            float _snowmanxxx = (float)this.cp < 100.0F ? 0.0F : afm.a(((float)this.cp - 100.0F) / 500.0F, 0.0F, 1.0F);
            return _snowmanxx * 0.6F + _snowmanxxx * 0.39999998F;
         }
      }
   }

   @Override
   public boolean aI() {
      return this.bB;
   }

   @Override
   protected boolean et() {
      boolean _snowman = this.bB;
      boolean _snowmanx = super.et();
      if (this.a_()) {
         return this.bB;
      } else {
         if (!_snowman && _snowmanx) {
            this.l.a(this.cD(), this.cE(), this.cH(), adq.q, adr.i, 1.0F, 1.0F, false);
            this.g.W().a((emt)(new emw.b(this)));
         }

         if (_snowman && !_snowmanx) {
            this.l.a(this.cD(), this.cE(), this.cH(), adq.r, adr.i, 1.0F, 1.0F, false);
         }

         return this.bB;
      }
   }

   @Override
   public dcn o(float var1) {
      if (this.g.k.g().a()) {
         float _snowman = afm.g(_snowman * 0.5F, this.p, this.r) * (float) (Math.PI / 180.0);
         float _snowmanx = afm.g(_snowman * 0.5F, this.q, this.s) * (float) (Math.PI / 180.0);
         double _snowmanxx = this.dV() == aqi.b ? -1.0 : 1.0;
         dcn _snowmanxxx = new dcn(0.39 * _snowmanxx, -0.6, 0.3);
         return _snowmanxxx.a(-_snowmanx).b(-_snowman).e(this.j(_snowman));
      } else {
         return super.o(_snowman);
      }
   }
}
