import javax.annotation.Nullable;

public class bgy extends bga {
   private static final us<Byte> g = uv.a(bgy.class, uu.a);
   private static final us<Boolean> ag = uv.a(bgy.class, uu.i);
   private bmb ah = new bmb(bmd.qM);
   private boolean ai;
   public int f;

   public bgy(aqe<? extends bgy> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgy(brx var1, aqm var2, bmb var3) {
      super(aqe.aK, _snowman, _snowman);
      this.ah = _snowman.i();
      this.R.b(g, (byte)bpu.f(_snowman));
      this.R.b(ag, _snowman.u());
   }

   public bgy(brx var1, double var2, double var4, double var6) {
      super(aqe.aK, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(g, (byte)0);
      this.R.a(ag, false);
   }

   @Override
   public void j() {
      if (this.c > 4) {
         this.ai = true;
      }

      aqa _snowman = this.v();
      if ((this.ai || this.t()) && _snowman != null) {
         int _snowmanx = this.R.a(g);
         if (_snowmanx > 0 && !this.z()) {
            if (!this.l.v && this.d == bga.a.b) {
               this.a(this.m(), 0.1F);
            }

            this.ad();
         } else if (_snowmanx > 0) {
            this.o(true);
            dcn _snowmanxx = new dcn(_snowman.cD() - this.cD(), _snowman.cG() - this.cE(), _snowman.cH() - this.cH());
            this.o(this.cD(), this.cE() + _snowmanxx.c * 0.015 * (double)_snowmanx, this.cH());
            if (this.l.v) {
               this.E = this.cE();
            }

            double _snowmanxxx = 0.05 * (double)_snowmanx;
            this.f(this.cC().a(0.95).e(_snowmanxx.d().a(_snowmanxxx)));
            if (this.f == 0) {
               this.a(adq.pf, 10.0F, 1.0F);
            }

            this.f++;
         }
      }

      super.j();
   }

   private boolean z() {
      aqa _snowman = this.v();
      return _snowman == null || !_snowman.aX() ? false : !(_snowman instanceof aah) || !_snowman.a_();
   }

   @Override
   protected bmb m() {
      return this.ah.i();
   }

   @Override
   public boolean u() {
      return this.R.a(ag);
   }

   @Nullable
   @Override
   protected dck a(dcn var1, dcn var2) {
      return this.ai ? null : super.a(_snowman, _snowman);
   }

   @Override
   protected void a(dck var1) {
      aqa _snowman = _snowman.a();
      float _snowmanx = 8.0F;
      if (_snowman instanceof aqm) {
         aqm _snowmanxx = (aqm)_snowman;
         _snowmanx += bpu.a(this.ah, _snowmanxx.dC());
      }

      aqa _snowmanxx = this.v();
      apk _snowmanxxx = apk.a(this, (aqa)(_snowmanxx == null ? this : _snowmanxx));
      this.ai = true;
      adp _snowmanxxxx = adq.pd;
      if (_snowman.a(_snowmanxxx, _snowmanx)) {
         if (_snowman.X() == aqe.u) {
            return;
         }

         if (_snowman instanceof aqm) {
            aqm _snowmanxxxxx = (aqm)_snowman;
            if (_snowmanxx instanceof aqm) {
               bpu.a(_snowmanxxxxx, _snowmanxx);
               bpu.b((aqm)_snowmanxx, _snowmanxxxxx);
            }

            this.a(_snowmanxxxxx);
         }
      }

      this.f(this.cC().d(-0.01, -0.1, -0.01));
      float _snowmanxxxxx = 1.0F;
      if (this.l instanceof aag && this.l.W() && bpu.h(this.ah)) {
         fx _snowmanxxxxxx = _snowman.cB();
         if (this.l.e(_snowmanxxxxxx)) {
            aql _snowmanxxxxxxx = aqe.P.a(this.l);
            _snowmanxxxxxxx.d(dcn.c(_snowmanxxxxxx));
            _snowmanxxxxxxx.d(_snowmanxx instanceof aah ? (aah)_snowmanxx : null);
            this.l.c(_snowmanxxxxxxx);
            _snowmanxxxx = adq.pk;
            _snowmanxxxxx = 5.0F;
         }
      }

      this.a(_snowmanxxxx, _snowmanxxxxx, 1.0F);
   }

   @Override
   protected adp i() {
      return adq.pe;
   }

   @Override
   public void a_(bfw var1) {
      aqa _snowman = this.v();
      if (_snowman == null || _snowman.bS() == _snowman.bS()) {
         super.a_(_snowman);
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("Trident", 10)) {
         this.ah = bmb.a(_snowman.p("Trident"));
      }

      this.ai = _snowman.q("DealtDamage");
      this.R.b(g, (byte)bpu.f(this.ah));
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("Trident", this.ah.b(new md()));
      _snowman.a("DealtDamage", this.ai);
   }

   @Override
   public void h() {
      int _snowman = this.R.a(g);
      if (this.d != bga.a.b || _snowman <= 0) {
         super.h();
      }
   }

   @Override
   protected float s() {
      return 0.99F;
   }

   @Override
   public boolean j(double var1, double var3, double var5) {
      return true;
   }
}
