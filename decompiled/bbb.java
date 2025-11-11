import com.google.common.collect.UnmodifiableIterator;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public abstract class bbb extends azz implements aop, aqw, ara {
   private static final Predicate<aqm> bw = var0 -> var0 instanceof bbb && ((bbb)var0).fb();
   private static final azg bx = new azg().a(16.0).a().b().c().a(bw);
   private static final bon by = bon.a(bmd.kW, bmd.mM, bup.gA.h(), bmd.kb, bmd.pd, bmd.lA, bmd.lB);
   private static final us<Byte> bz = uv.a(bbb.class, uu.a);
   private static final us<Optional<UUID>> bA = uv.a(bbb.class, uu.o);
   private int bB;
   private int bC;
   private int bD;
   public int bo;
   public int bp;
   protected boolean bq;
   protected apa br;
   protected int bs;
   protected float bt;
   private boolean bE;
   private float bF;
   private float bG;
   private float bH;
   private float bI;
   private float bJ;
   private float bK;
   protected boolean bu = true;
   protected int bv;

   protected bbb(aqe<? extends bbb> var1, brx var2) {
      super(_snowman, _snowman);
      this.G = 1.0F;
      this.fd();
   }

   @Override
   protected void o() {
      this.bk.a(1, new awp(this, 1.2));
      this.bk.a(1, new axa(this, 1.2));
      this.bk.a(2, new avi(this, 1.0, bbb.class));
      this.bk.a(4, new avu(this, 1.0));
      this.bk.a(6, new axk(this, 0.7));
      this.bk.a(7, new awd(this, bfw.class, 6.0F));
      this.bk.a(8, new aws(this));
      this.eV();
   }

   protected void eV() {
      this.bk.a(0, new avp(this));
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bz, (byte)0);
      this.R.a(bA, Optional.empty());
   }

   protected boolean t(int var1) {
      return (this.R.a(bz) & _snowman) != 0;
   }

   protected void d(int var1, boolean var2) {
      byte _snowman = this.R.a(bz);
      if (_snowman) {
         this.R.b(bz, (byte)(_snowman | _snowman));
      } else {
         this.R.b(bz, (byte)(_snowman & ~_snowman));
      }
   }

   public boolean eW() {
      return this.t(2);
   }

   @Nullable
   public UUID eX() {
      return this.R.a(bA).orElse(null);
   }

   public void b(@Nullable UUID var1) {
      this.R.b(bA, Optional.ofNullable(_snowman));
   }

   public boolean eY() {
      return this.bq;
   }

   public void u(boolean var1) {
      this.d(2, _snowman);
   }

   public void v(boolean var1) {
      this.bq = _snowman;
   }

   @Override
   protected void x(float var1) {
      if (_snowman > 6.0F && this.eZ()) {
         this.x(false);
      }
   }

   public boolean eZ() {
      return this.t(16);
   }

   public boolean fa() {
      return this.t(32);
   }

   public boolean fb() {
      return this.t(8);
   }

   public void w(boolean var1) {
      this.d(8, _snowman);
   }

   @Override
   public boolean L_() {
      return this.aX() && !this.w_() && this.eW();
   }

   @Override
   public void a(@Nullable adr var1) {
      this.br.a(0, new bmb(bmd.lO));
      if (_snowman != null) {
         this.l.a(null, this, adq.ga, _snowman, 0.5F, 1.0F);
      }
   }

   @Override
   public boolean M_() {
      return this.t(4);
   }

   public int fc() {
      return this.bs;
   }

   public void u(int var1) {
      this.bs = _snowman;
   }

   public int v(int var1) {
      int _snowman = afm.a(this.fc() + _snowman, 0, this.fj());
      this.u(_snowman);
      return _snowman;
   }

   @Override
   public boolean aU() {
      return !this.bs();
   }

   private void eL() {
      this.eO();
      if (!this.aA()) {
         adp _snowman = this.fg();
         if (_snowman != null) {
            this.l.a(null, this.cD(), this.cE(), this.cH(), _snowman, this.cu(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
         }
      }
   }

   @Override
   public boolean b(float var1, float var2) {
      if (_snowman > 1.0F) {
         this.a(adq.fZ, 0.4F, 1.0F);
      }

      int _snowman = this.e(_snowman, _snowman);
      if (_snowman <= 0) {
         return false;
      } else {
         this.a(apk.k, (float)_snowman);
         if (this.bs()) {
            for (aqa _snowmanx : this.co()) {
               _snowmanx.a(apk.k, (float)_snowman);
            }
         }

         this.dt();
         return true;
      }
   }

   @Override
   protected int e(float var1, float var2) {
      return afm.f((_snowman * 0.5F - 3.0F) * _snowman);
   }

   protected int eN() {
      return 2;
   }

   protected void fd() {
      apa _snowman = this.br;
      this.br = new apa(this.eN());
      if (_snowman != null) {
         _snowman.b(this);
         int _snowmanx = Math.min(_snowman.Z_(), this.br.Z_());

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
            bmb _snowmanxxx = _snowman.a(_snowmanxx);
            if (!_snowmanxxx.a()) {
               this.br.a(_snowmanxx, _snowmanxxx.i());
            }
         }
      }

      this.br.a(this);
      this.fe();
   }

   protected void fe() {
      if (!this.l.v) {
         this.d(4, !this.br.a(0).a());
      }
   }

   @Override
   public void a(aon var1) {
      boolean _snowman = this.M_();
      this.fe();
      if (this.K > 20 && !_snowman && this.M_()) {
         this.a(adq.ga, 0.5F, 1.0F);
      }
   }

   public double ff() {
      return this.b(arl.m);
   }

   @Nullable
   protected adp fg() {
      return null;
   }

   @Nullable
   @Override
   protected adp dq() {
      return null;
   }

   @Nullable
   @Override
   protected adp e(apk var1) {
      if (this.J.nextInt(3) == 0) {
         this.eU();
      }

      return null;
   }

   @Nullable
   @Override
   protected adp I() {
      if (this.J.nextInt(10) == 0 && !this.dI()) {
         this.eU();
      }

      return null;
   }

   @Nullable
   protected adp fh() {
      this.eU();
      return null;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      if (!_snowman.c().a()) {
         ceh _snowman = this.l.d_(_snowman.b());
         cae _snowmanx = _snowman.o();
         if (_snowman.a(bup.cC)) {
            _snowmanx = _snowman.o();
         }

         if (this.bs() && this.bu) {
            this.bv++;
            if (this.bv > 5 && this.bv % 3 == 0) {
               this.a(_snowmanx);
            } else if (this.bv <= 5) {
               this.a(adq.gc, _snowmanx.a() * 0.15F, _snowmanx.b());
            }
         } else if (_snowmanx == cae.a) {
            this.a(adq.gc, _snowmanx.a() * 0.15F, _snowmanx.b());
         } else {
            this.a(adq.gb, _snowmanx.a() * 0.15F, _snowmanx.b());
         }
      }
   }

   protected void a(cae var1) {
      this.a(adq.fW, _snowman.a() * 0.15F, _snowman.b());
   }

   public static ark.a fi() {
      return aqn.p().a(arl.m).a(arl.a, 53.0).a(arl.d, 0.225F);
   }

   @Override
   public int eq() {
      return 6;
   }

   public int fj() {
      return 100;
   }

   @Override
   protected float dG() {
      return 0.8F;
   }

   @Override
   public int D() {
      return 400;
   }

   public void f(bfw var1) {
      if (!this.l.v && (!this.bs() || this.w(_snowman)) && this.eW()) {
         _snowman.a(this, this.br);
      }
   }

   public aou b(bfw var1, bmb var2) {
      boolean _snowman = this.c(_snowman, _snowman);
      if (!_snowman.bC.d) {
         _snowman.g(1);
      }

      if (this.l.v) {
         return aou.b;
      } else {
         return _snowman ? aou.a : aou.c;
      }
   }

   protected boolean c(bfw var1, bmb var2) {
      boolean _snowman = false;
      float _snowmanx = 0.0F;
      int _snowmanxx = 0;
      int _snowmanxxx = 0;
      blx _snowmanxxxx = _snowman.b();
      if (_snowmanxxxx == bmd.kW) {
         _snowmanx = 2.0F;
         _snowmanxx = 20;
         _snowmanxxx = 3;
      } else if (_snowmanxxxx == bmd.mM) {
         _snowmanx = 1.0F;
         _snowmanxx = 30;
         _snowmanxxx = 3;
      } else if (_snowmanxxxx == bup.gA.h()) {
         _snowmanx = 20.0F;
         _snowmanxx = 180;
      } else if (_snowmanxxxx == bmd.kb) {
         _snowmanx = 3.0F;
         _snowmanxx = 60;
         _snowmanxxx = 3;
      } else if (_snowmanxxxx == bmd.pd) {
         _snowmanx = 4.0F;
         _snowmanxx = 60;
         _snowmanxxx = 5;
         if (!this.l.v && this.eW() && this.i() == 0 && !this.eS()) {
            _snowman = true;
            this.g(_snowman);
         }
      } else if (_snowmanxxxx == bmd.lA || _snowmanxxxx == bmd.lB) {
         _snowmanx = 10.0F;
         _snowmanxx = 240;
         _snowmanxxx = 10;
         if (!this.l.v && this.eW() && this.i() == 0 && !this.eS()) {
            _snowman = true;
            this.g(_snowman);
         }
      }

      if (this.dk() < this.dx() && _snowmanx > 0.0F) {
         this.b(_snowmanx);
         _snowman = true;
      }

      if (this.w_() && _snowmanxx > 0) {
         this.l.a(hh.E, this.d(1.0), this.cF() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
         if (!this.l.v) {
            this.a(_snowmanxx);
         }

         _snowman = true;
      }

      if (_snowmanxxx > 0 && (_snowman || !this.eW()) && this.fc() < this.fj()) {
         _snowman = true;
         if (!this.l.v) {
            this.v(_snowmanxxx);
         }
      }

      if (_snowman) {
         this.eL();
      }

      return _snowman;
   }

   protected void h(bfw var1) {
      this.x(false);
      this.y(false);
      if (!this.l.v) {
         _snowman.p = this.p;
         _snowman.q = this.q;
         _snowman.m(this);
      }
   }

   @Override
   protected boolean dI() {
      return super.dI() && this.bs() && this.M_() || this.eZ() || this.fa();
   }

   @Override
   public boolean k(bmb var1) {
      return by.a(_snowman);
   }

   private void eM() {
      this.bo = 1;
   }

   @Override
   protected void dn() {
      super.dn();
      if (this.br != null) {
         for (int _snowman = 0; _snowman < this.br.Z_(); _snowman++) {
            bmb _snowmanx = this.br.a(_snowman);
            if (!_snowmanx.a() && !bpu.e(_snowmanx)) {
               this.a(_snowmanx);
            }
         }
      }
   }

   @Override
   public void k() {
      if (this.J.nextInt(200) == 0) {
         this.eM();
      }

      super.k();
      if (!this.l.v && this.aX()) {
         if (this.J.nextInt(900) == 0 && this.aq == 0) {
            this.b(1.0F);
         }

         if (this.fl()) {
            if (!this.eZ() && !this.bs() && this.J.nextInt(300) == 0 && this.l.d_(this.cB().c()).a(bup.i)) {
               this.x(true);
            }

            if (this.eZ() && ++this.bB > 50) {
               this.bB = 0;
               this.x(false);
            }
         }

         this.fk();
      }
   }

   protected void fk() {
      if (this.fb() && this.w_() && !this.eZ()) {
         aqm _snowman = this.l.a(bbb.class, bx, this, this.cD(), this.cE(), this.cH(), this.cc().g(16.0));
         if (_snowman != null && this.h(_snowman) > 4.0) {
            this.bj.a(_snowman, 0);
         }
      }
   }

   public boolean fl() {
      return true;
   }

   @Override
   public void j() {
      super.j();
      if (this.bC > 0 && ++this.bC > 30) {
         this.bC = 0;
         this.d(64, false);
      }

      if ((this.cs() || this.dS()) && this.bD > 0 && ++this.bD > 20) {
         this.bD = 0;
         this.y(false);
      }

      if (this.bo > 0 && ++this.bo > 8) {
         this.bo = 0;
      }

      if (this.bp > 0) {
         this.bp++;
         if (this.bp > 300) {
            this.bp = 0;
         }
      }

      this.bG = this.bF;
      if (this.eZ()) {
         this.bF = this.bF + (1.0F - this.bF) * 0.4F + 0.05F;
         if (this.bF > 1.0F) {
            this.bF = 1.0F;
         }
      } else {
         this.bF = this.bF + ((0.0F - this.bF) * 0.4F - 0.05F);
         if (this.bF < 0.0F) {
            this.bF = 0.0F;
         }
      }

      this.bI = this.bH;
      if (this.fa()) {
         this.bF = 0.0F;
         this.bG = this.bF;
         this.bH = this.bH + (1.0F - this.bH) * 0.4F + 0.05F;
         if (this.bH > 1.0F) {
            this.bH = 1.0F;
         }
      } else {
         this.bE = false;
         this.bH = this.bH + ((0.8F * this.bH * this.bH * this.bH - this.bH) * 0.6F - 0.05F);
         if (this.bH < 0.0F) {
            this.bH = 0.0F;
         }
      }

      this.bK = this.bJ;
      if (this.t(64)) {
         this.bJ = this.bJ + (1.0F - this.bJ) * 0.7F + 0.05F;
         if (this.bJ > 1.0F) {
            this.bJ = 1.0F;
         }
      } else {
         this.bJ = this.bJ + ((0.0F - this.bJ) * 0.7F - 0.05F);
         if (this.bJ < 0.0F) {
            this.bJ = 0.0F;
         }
      }
   }

   private void eO() {
      if (!this.l.v) {
         this.bC = 1;
         this.d(64, true);
      }
   }

   public void x(boolean var1) {
      this.d(16, _snowman);
   }

   public void y(boolean var1) {
      if (_snowman) {
         this.x(false);
      }

      this.d(32, _snowman);
   }

   private void eU() {
      if (this.cs() || this.dS()) {
         this.bD = 1;
         this.y(true);
      }
   }

   public void fm() {
      if (!this.fa()) {
         this.eU();
         adp _snowman = this.fh();
         if (_snowman != null) {
            this.a(_snowman, this.dG(), this.dH());
         }
      }
   }

   public boolean i(bfw var1) {
      this.b(_snowman.bS());
      this.u(true);
      if (_snowman instanceof aah) {
         ac.x.a((aah)_snowman, this);
      }

      this.l.a(this, (byte)7);
      return true;
   }

   @Override
   public void g(dcn var1) {
      if (this.aX()) {
         if (this.bs() && this.er() && this.M_()) {
            aqm _snowman = (aqm)this.cm();
            this.p = _snowman.p;
            this.r = this.p;
            this.q = _snowman.q * 0.5F;
            this.a(this.p, this.q);
            this.aA = this.p;
            this.aC = this.aA;
            float _snowmanx = _snowman.aR * 0.5F;
            float _snowmanxx = _snowman.aT;
            if (_snowmanxx <= 0.0F) {
               _snowmanxx *= 0.25F;
               this.bv = 0;
            }

            if (this.t && this.bt == 0.0F && this.fa() && !this.bE) {
               _snowmanx = 0.0F;
               _snowmanxx = 0.0F;
            }

            if (this.bt > 0.0F && !this.eY() && this.t) {
               double _snowmanxxx = this.ff() * (double)this.bt * (double)this.aq();
               double _snowmanxxxx;
               if (this.a(apw.h)) {
                  _snowmanxxxx = _snowmanxxx + (double)((float)(this.b(apw.h).c() + 1) * 0.1F);
               } else {
                  _snowmanxxxx = _snowmanxxx;
               }

               dcn _snowmanxxxxx = this.cC();
               this.n(_snowmanxxxxx.b, _snowmanxxxx, _snowmanxxxxx.d);
               this.v(true);
               this.Z = true;
               if (_snowmanxx > 0.0F) {
                  float _snowmanxxxxxx = afm.a(this.p * (float) (Math.PI / 180.0));
                  float _snowmanxxxxxxx = afm.b(this.p * (float) (Math.PI / 180.0));
                  this.f(this.cC().b((double)(-0.4F * _snowmanxxxxxx * this.bt), 0.0, (double)(0.4F * _snowmanxxxxxxx * this.bt)));
               }

               this.bt = 0.0F;
            }

            this.aE = this.dN() * 0.1F;
            if (this.cs()) {
               this.q((float)this.b(arl.d));
               super.g(new dcn((double)_snowmanx, _snowman.c, (double)_snowmanxx));
            } else if (_snowman instanceof bfw) {
               this.f(dcn.a);
            }

            if (this.t) {
               this.bt = 0.0F;
               this.v(false);
            }

            this.a(this, false);
         } else {
            this.aE = 0.02F;
            super.g(_snowman);
         }
      }
   }

   protected void fn() {
      this.a(adq.fY, 0.4F, 1.0F);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("EatingHaystack", this.eZ());
      _snowman.a("Bred", this.fb());
      _snowman.b("Temper", this.fc());
      _snowman.a("Tame", this.eW());
      if (this.eX() != null) {
         _snowman.a("Owner", this.eX());
      }

      if (!this.br.a(0).a()) {
         _snowman.a("SaddleItem", this.br.a(0).b(new md()));
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.x(_snowman.q("EatingHaystack"));
      this.w(_snowman.q("Bred"));
      this.u(_snowman.h("Temper"));
      this.u(_snowman.q("Tame"));
      UUID _snowman;
      if (_snowman.b("Owner")) {
         _snowman = _snowman.a("Owner");
      } else {
         String _snowmanx = _snowman.l("Owner");
         _snowman = act.a(this.ch(), _snowmanx);
      }

      if (_snowman != null) {
         this.b(_snowman);
      }

      if (_snowman.c("SaddleItem", 10)) {
         bmb _snowmanx = bmb.a(_snowman.p("SaddleItem"));
         if (_snowmanx.b() == bmd.lO) {
            this.br.a(0, _snowmanx);
         }
      }

      this.fe();
   }

   @Override
   public boolean a(azz var1) {
      return false;
   }

   protected boolean fo() {
      return !this.bs() && !this.br() && this.eW() && !this.w_() && this.dk() >= this.dx() && this.eS();
   }

   @Nullable
   @Override
   public apy a(aag var1, apy var2) {
      return null;
   }

   protected void a(apy var1, bbb var2) {
      double _snowman = this.c(arl.a) + _snowman.c(arl.a) + (double)this.fp();
      _snowman.a(arl.a).a(_snowman / 3.0);
      double _snowmanx = this.c(arl.m) + _snowman.c(arl.m) + this.fq();
      _snowman.a(arl.m).a(_snowmanx / 3.0);
      double _snowmanxx = this.c(arl.d) + _snowman.c(arl.d) + this.fr();
      _snowman.a(arl.d).a(_snowmanxx / 3.0);
   }

   @Override
   public boolean er() {
      return this.cm() instanceof aqm;
   }

   public float y(float var1) {
      return afm.g(_snowman, this.bG, this.bF);
   }

   public float z(float var1) {
      return afm.g(_snowman, this.bI, this.bH);
   }

   public float A(float var1) {
      return afm.g(_snowman, this.bK, this.bJ);
   }

   @Override
   public void b_(int var1) {
      if (this.M_()) {
         if (_snowman < 0) {
            _snowman = 0;
         } else {
            this.bE = true;
            this.eU();
         }

         if (_snowman >= 90) {
            this.bt = 1.0F;
         } else {
            this.bt = 0.4F + 0.4F * (float)_snowman / 90.0F;
         }
      }
   }

   @Override
   public boolean P_() {
      return this.M_();
   }

   @Override
   public void b(int var1) {
      this.bE = true;
      this.eU();
      this.fn();
   }

   @Override
   public void c() {
   }

   protected void z(boolean var1) {
      hf _snowman = _snowman ? hh.G : hh.S;

      for (int _snowmanx = 0; _snowmanx < 7; _snowmanx++) {
         double _snowmanxx = this.J.nextGaussian() * 0.02;
         double _snowmanxxx = this.J.nextGaussian() * 0.02;
         double _snowmanxxxx = this.J.nextGaussian() * 0.02;
         this.l.a(_snowman, this.d(1.0), this.cF() + 0.5, this.g(1.0), _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 7) {
         this.z(true);
      } else if (_snowman == 6) {
         this.z(false);
      } else {
         super.a(_snowman);
      }
   }

   @Override
   public void k(aqa var1) {
      super.k(_snowman);
      if (_snowman instanceof aqn) {
         aqn _snowman = (aqn)_snowman;
         this.aA = _snowman.aA;
      }

      if (this.bI > 0.0F) {
         float _snowman = afm.a(this.aA * (float) (Math.PI / 180.0));
         float _snowmanx = afm.b(this.aA * (float) (Math.PI / 180.0));
         float _snowmanxx = 0.7F * this.bI;
         float _snowmanxxx = 0.15F * this.bI;
         _snowman.d(this.cD() + (double)(_snowmanxx * _snowman), this.cE() + this.bc() + _snowman.bb() + (double)_snowmanxxx, this.cH() - (double)(_snowmanxx * _snowmanx));
         if (_snowman instanceof aqm) {
            ((aqm)_snowman).aA = this.aA;
         }
      }
   }

   protected float fp() {
      return 15.0F + (float)this.J.nextInt(8) + (float)this.J.nextInt(9);
   }

   protected double fq() {
      return 0.4F + this.J.nextDouble() * 0.2 + this.J.nextDouble() * 0.2 + this.J.nextDouble() * 0.2;
   }

   protected double fr() {
      return (0.45F + this.J.nextDouble() * 0.3 + this.J.nextDouble() * 0.3 + this.J.nextDouble() * 0.3) * 0.25;
   }

   @Override
   public boolean c_() {
      return false;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return _snowman.b * 0.95F;
   }

   public boolean fs() {
      return false;
   }

   public boolean ft() {
      return !this.b(aqf.e).a();
   }

   public boolean l(bmb var1) {
      return false;
   }

   @Override
   public boolean a_(int var1, bmb var2) {
      int _snowman = _snowman - 400;
      if (_snowman >= 0 && _snowman < 2 && _snowman < this.br.Z_()) {
         if (_snowman == 0 && _snowman.b() != bmd.lO) {
            return false;
         } else if (_snowman != 1 || this.fs() && this.l(_snowman)) {
            this.br.a(_snowman, _snowman);
            this.fe();
            return true;
         } else {
            return false;
         }
      } else {
         int _snowmanx = _snowman - 500 + 2;
         if (_snowmanx >= 2 && _snowmanx < this.br.Z_()) {
            this.br.a(_snowmanx, _snowman);
            return true;
         } else {
            return false;
         }
      }
   }

   @Nullable
   @Override
   public aqa cm() {
      return this.cn().isEmpty() ? null : this.cn().get(0);
   }

   @Nullable
   private dcn a(dcn var1, aqm var2) {
      double _snowman = this.cD() + _snowman.b;
      double _snowmanx = this.cc().b;
      double _snowmanxx = this.cH() + _snowman.d;
      fx.a _snowmanxxx = new fx.a();
      UnmodifiableIterator var10 = _snowman.ej().iterator();

      while (var10.hasNext()) {
         aqx _snowmanxxxx = (aqx)var10.next();
         _snowmanxxx.c(_snowman, _snowmanx, _snowmanxx);
         double _snowmanxxxxx = this.cc().e + 0.75;

         do {
            double _snowmanxxxxxx = this.l.h(_snowmanxxx);
            if ((double)_snowmanxxx.v() + _snowmanxxxxxx > _snowmanxxxxx) {
               break;
            }

            if (bho.a(_snowmanxxxxxx)) {
               dci _snowmanxxxxxxx = _snowman.f(_snowmanxxxx);
               dcn _snowmanxxxxxxxx = new dcn(_snowman, (double)_snowmanxxx.v() + _snowmanxxxxxx, _snowmanxx);
               if (bho.a(this.l, _snowman, _snowmanxxxxxxx.c(_snowmanxxxxxxxx))) {
                  _snowman.b(_snowmanxxxx);
                  return _snowmanxxxxxxxx;
               }
            }

            _snowmanxxx.c(gc.b);
         } while (!((double)_snowmanxxx.v() < _snowmanxxxxx));
      }

      return null;
   }

   @Override
   public dcn b(aqm var1) {
      dcn _snowman = a((double)this.cy(), (double)_snowman.cy(), this.p + (_snowman.dV() == aqi.b ? 90.0F : -90.0F));
      dcn _snowmanx = this.a(_snowman, _snowman);
      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         dcn _snowmanxx = a((double)this.cy(), (double)_snowman.cy(), this.p + (_snowman.dV() == aqi.a ? 90.0F : -90.0F));
         dcn _snowmanxxx = this.a(_snowmanxx, _snowman);
         return _snowmanxxx != null ? _snowmanxxx : this.cA();
      }
   }

   protected void eK() {
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      if (_snowman == null) {
         _snowman = new apy.a(0.2F);
      }

      this.eK();
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }
}
