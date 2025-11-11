import javax.annotation.Nullable;

public class bbe extends bba implements bdu {
   private static final bon bw = bon.a(bmd.kW, bup.gA.h());
   private static final us<Integer> bx = uv.a(bbe.class, uu.b);
   private static final us<Integer> by = uv.a(bbe.class, uu.b);
   private static final us<Integer> bz = uv.a(bbe.class, uu.b);
   private boolean bA;
   @Nullable
   private bbe bB;
   @Nullable
   private bbe bC;

   public bbe(aqe<? extends bbe> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public boolean fu() {
      return false;
   }

   private void x(int var1) {
      this.R.b(bx, Math.max(1, Math.min(5, _snowman)));
   }

   private void fE() {
      int _snowman = this.J.nextFloat() < 0.04F ? 5 : 3;
      this.x(1 + this.J.nextInt(_snowman));
   }

   public int fv() {
      return this.R.a(bx);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.b("Variant", this.fx());
      _snowman.b("Strength", this.fv());
      if (!this.br.a(1).a()) {
         _snowman.a("DecorItem", this.br.a(1).b(new md()));
      }
   }

   @Override
   public void a(md var1) {
      this.x(_snowman.h("Strength"));
      super.a(_snowman);
      this.w(_snowman.h("Variant"));
      if (_snowman.c("DecorItem", 10)) {
         this.br.a(1, bmb.a(_snowman.p("DecorItem")));
      }

      this.fe();
   }

   @Override
   protected void o() {
      this.bk.a(0, new avp(this));
      this.bk.a(1, new axa(this, 1.2));
      this.bk.a(2, new awc(this, 2.1F));
      this.bk.a(3, new awv(this, 1.25, 40, 20.0F));
      this.bk.a(3, new awp(this, 1.2));
      this.bk.a(4, new avi(this, 1.0));
      this.bk.a(5, new avu(this, 1.0));
      this.bk.a(6, new axk(this, 0.7));
      this.bk.a(7, new awd(this, bfw.class, 6.0F));
      this.bk.a(8, new aws(this));
      this.bl.a(1, new bbe.c(this));
      this.bl.a(2, new bbe.a(this));
   }

   public static ark.a fw() {
      return eL().a(arl.b, 40.0);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bx, 0);
      this.R.a(by, -1);
      this.R.a(bz, 0);
   }

   public int fx() {
      return afm.a(this.R.a(bz), 0, 3);
   }

   public void w(int var1) {
      this.R.b(bz, _snowman);
   }

   @Override
   protected int eN() {
      return this.eM() ? 2 + 3 * this.eU() : super.eN();
   }

   @Override
   public void k(aqa var1) {
      if (this.w(_snowman)) {
         float _snowman = afm.b(this.aA * (float) (Math.PI / 180.0));
         float _snowmanx = afm.a(this.aA * (float) (Math.PI / 180.0));
         float _snowmanxx = 0.3F;
         _snowman.d(this.cD() + (double)(0.3F * _snowmanx), this.cE() + this.bc() + _snowman.bb(), this.cH() - (double)(0.3F * _snowman));
      }
   }

   @Override
   public double bc() {
      return (double)this.cz() * 0.67;
   }

   @Override
   public boolean er() {
      return false;
   }

   @Override
   public boolean k(bmb var1) {
      return bw.a(_snowman);
   }

   @Override
   protected boolean c(bfw var1, bmb var2) {
      int _snowman = 0;
      int _snowmanx = 0;
      float _snowmanxx = 0.0F;
      boolean _snowmanxxx = false;
      blx _snowmanxxxx = _snowman.b();
      if (_snowmanxxxx == bmd.kW) {
         _snowman = 10;
         _snowmanx = 3;
         _snowmanxx = 2.0F;
      } else if (_snowmanxxxx == bup.gA.h()) {
         _snowman = 90;
         _snowmanx = 6;
         _snowmanxx = 10.0F;
         if (this.eW() && this.i() == 0 && this.eP()) {
            _snowmanxxx = true;
            this.g(_snowman);
         }
      }

      if (this.dk() < this.dx() && _snowmanxx > 0.0F) {
         this.b(_snowmanxx);
         _snowmanxxx = true;
      }

      if (this.w_() && _snowman > 0) {
         this.l.a(hh.E, this.d(1.0), this.cF() + 0.5, this.g(1.0), 0.0, 0.0, 0.0);
         if (!this.l.v) {
            this.a(_snowman);
         }

         _snowmanxxx = true;
      }

      if (_snowmanx > 0 && (_snowmanxxx || !this.eW()) && this.fc() < this.fj()) {
         _snowmanxxx = true;
         if (!this.l.v) {
            this.v(_snowmanx);
         }
      }

      if (_snowmanxxx && !this.aA()) {
         adp _snowmanxxxxx = this.fg();
         if (_snowmanxxxxx != null) {
            this.l.a(null, this.cD(), this.cE(), this.cH(), this.fg(), this.cu(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
         }
      }

      return _snowmanxxx;
   }

   @Override
   protected boolean dI() {
      return this.dl() || this.eZ();
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      this.fE();
      int _snowman;
      if (_snowman instanceof bbe.b) {
         _snowman = ((bbe.b)_snowman).a;
      } else {
         _snowman = this.J.nextInt(4);
         _snowman = new bbe.b(_snowman);
      }

      this.w(_snowman);
      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected adp fh() {
      return adq.hg;
   }

   @Override
   protected adp I() {
      return adq.hf;
   }

   @Override
   protected adp e(apk var1) {
      return adq.hk;
   }

   @Override
   protected adp dq() {
      return adq.hi;
   }

   @Nullable
   @Override
   protected adp fg() {
      return adq.hj;
   }

   @Override
   protected void b(fx var1, ceh var2) {
      this.a(adq.hm, 0.15F, 1.0F);
   }

   @Override
   protected void eO() {
      this.a(adq.hh, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
   }

   @Override
   public void fm() {
      adp _snowman = this.fh();
      if (_snowman != null) {
         this.a(_snowman, this.dG(), this.dH());
      }
   }

   @Override
   public int eU() {
      return this.fv();
   }

   @Override
   public boolean fs() {
      return true;
   }

   @Override
   public boolean ft() {
      return !this.br.a(1).a();
   }

   @Override
   public boolean l(bmb var1) {
      blx _snowman = _snowman.b();
      return aeg.g.a(_snowman);
   }

   @Override
   public boolean L_() {
      return false;
   }

   @Override
   public void a(aon var1) {
      bkx _snowman = this.fy();
      super.a(_snowman);
      bkx _snowmanx = this.fy();
      if (this.K > 20 && _snowmanx != null && _snowmanx != _snowman) {
         this.a(adq.hn, 0.5F, 1.0F);
      }
   }

   @Override
   protected void fe() {
      if (!this.l.v) {
         super.fe();
         this.a(m(this.br.a(1)));
      }
   }

   private void a(@Nullable bkx var1) {
      this.R.b(by, _snowman == null ? -1 : _snowman.b());
   }

   @Nullable
   private static bkx m(bmb var0) {
      buo _snowman = buo.a(_snowman.b());
      return _snowman instanceof cby ? ((cby)_snowman).c() : null;
   }

   @Nullable
   public bkx fy() {
      int _snowman = this.R.a(by);
      return _snowman == -1 ? null : bkx.a(_snowman);
   }

   @Override
   public int fj() {
      return 30;
   }

   @Override
   public boolean a(azz var1) {
      return _snowman != this && _snowman instanceof bbe && this.fo() && ((bbe)_snowman).fo();
   }

   public bbe b(aag var1, apy var2) {
      bbe _snowman = this.fz();
      this.a(_snowman, _snowman);
      bbe _snowmanx = (bbe)_snowman;
      int _snowmanxx = this.J.nextInt(Math.max(this.fv(), _snowmanx.fv())) + 1;
      if (this.J.nextFloat() < 0.03F) {
         _snowmanxx++;
      }

      _snowman.x(_snowmanxx);
      _snowman.w(this.J.nextBoolean() ? this.fx() : _snowmanx.fx());
      return _snowman;
   }

   protected bbe fz() {
      return aqe.Q.a(this.l);
   }

   private void i(aqm var1) {
      bgl _snowman = new bgl(this.l, this);
      double _snowmanx = _snowman.cD() - this.cD();
      double _snowmanxx = _snowman.e(0.3333333333333333) - _snowman.cE();
      double _snowmanxxx = _snowman.cH() - this.cH();
      float _snowmanxxxx = afm.a(_snowmanx * _snowmanx + _snowmanxxx * _snowmanxxx) * 0.2F;
      _snowman.c(_snowmanx, _snowmanxx + (double)_snowmanxxxx, _snowmanxxx, 1.5F, 10.0F);
      if (!this.aA()) {
         this.l.a(null, this.cD(), this.cE(), this.cH(), adq.hl, this.cu(), 1.0F, 1.0F + (this.J.nextFloat() - this.J.nextFloat()) * 0.2F);
      }

      this.l.c(_snowman);
      this.bA = true;
   }

   private void A(boolean var1) {
      this.bA = _snowman;
   }

   @Override
   public boolean b(float var1, float var2) {
      int _snowman = this.e(_snowman, _snowman);
      if (_snowman <= 0) {
         return false;
      } else {
         if (_snowman >= 6.0F) {
            this.a(apk.k, (float)_snowman);
            if (this.bs()) {
               for (aqa _snowmanx : this.co()) {
                  _snowmanx.a(apk.k, (float)_snowman);
               }
            }
         }

         this.dt();
         return true;
      }
   }

   public void fA() {
      if (this.bB != null) {
         this.bB.bC = null;
      }

      this.bB = null;
   }

   public void a(bbe var1) {
      this.bB = _snowman;
      this.bB.bC = this;
   }

   public boolean fB() {
      return this.bC != null;
   }

   public boolean fC() {
      return this.bB != null;
   }

   @Nullable
   public bbe fD() {
      return this.bB;
   }

   @Override
   protected double eJ() {
      return 2.0;
   }

   @Override
   protected void fk() {
      if (!this.fC() && this.w_()) {
         super.fk();
      }
   }

   @Override
   public boolean fl() {
      return false;
   }

   @Override
   public void a(aqm var1, float var2) {
      this.i(_snowman);
   }

   @Override
   public dcn cf() {
      return new dcn(0.0, 0.75 * (double)this.ce(), (double)this.cy() * 0.5);
   }

   static class a extends axq<baz> {
      public a(bbe var1) {
         super(_snowman, baz.class, 16, false, true, var0 -> !((baz)var0).eK());
      }

      @Override
      protected double k() {
         return super.k() * 0.25;
      }
   }

   static class b extends apy.a {
      public final int a;

      private b(int var1) {
         super(true);
         this.a = _snowman;
      }
   }

   static class c extends axp {
      public c(bbe var1) {
         super(_snowman);
      }

      @Override
      public boolean b() {
         if (this.e instanceof bbe) {
            bbe _snowman = (bbe)this.e;
            if (_snowman.bA) {
               _snowman.A(false);
               return false;
            }
         }

         return super.b();
      }
   }
}
