public class bgf extends aqa implements bgj {
   private static final us<bmb> b = uv.a(bgf.class, uu.g);
   private double c;
   private double d;
   private double e;
   private int f;
   private boolean g;

   public bgf(aqe<? extends bgf> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgf(brx var1, double var2, double var4, double var6) {
      this(aqe.z, _snowman);
      this.f = 0;
      this.d(_snowman, _snowman, _snowman);
   }

   public void b(bmb var1) {
      if (_snowman.b() != bmd.nD || _snowman.n()) {
         this.ab().b(b, x.a(_snowman.i(), var0 -> var0.e(1)));
      }
   }

   private bmb h() {
      return this.ab().a(b);
   }

   @Override
   public bmb g() {
      bmb _snowman = this.h();
      return _snowman.a() ? new bmb(bmd.nD) : _snowman;
   }

   @Override
   protected void e() {
      this.ab().a(b, bmb.b);
   }

   @Override
   public boolean a(double var1) {
      double _snowman = this.cc().a() * 4.0;
      if (Double.isNaN(_snowman)) {
         _snowman = 4.0;
      }

      _snowman *= 64.0;
      return _snowman < _snowman * _snowman;
   }

   public void a(fx var1) {
      double _snowman = (double)_snowman.u();
      int _snowmanx = _snowman.v();
      double _snowmanxx = (double)_snowman.w();
      double _snowmanxxx = _snowman - this.cD();
      double _snowmanxxxx = _snowmanxx - this.cH();
      float _snowmanxxxxx = afm.a(_snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx);
      if (_snowmanxxxxx > 12.0F) {
         this.c = this.cD() + _snowmanxxx / (double)_snowmanxxxxx * 12.0;
         this.e = this.cH() + _snowmanxxxx / (double)_snowmanxxxxx * 12.0;
         this.d = this.cE() + 8.0;
      } else {
         this.c = _snowman;
         this.d = (double)_snowmanx;
         this.e = _snowmanxx;
      }

      this.f = 0;
      this.g = this.J.nextInt(5) > 0;
   }

   @Override
   public void k(double var1, double var3, double var5) {
      this.n(_snowman, _snowman, _snowman);
      if (this.s == 0.0F && this.r == 0.0F) {
         float _snowman = afm.a(_snowman * _snowman + _snowman * _snowman);
         this.p = (float)(afm.d(_snowman, _snowman) * 180.0F / (float)Math.PI);
         this.q = (float)(afm.d(_snowman, (double)_snowman) * 180.0F / (float)Math.PI);
         this.r = this.p;
         this.s = this.q;
      }
   }

   @Override
   public void j() {
      super.j();
      dcn _snowman = this.cC();
      double _snowmanx = this.cD() + _snowman.b;
      double _snowmanxx = this.cE() + _snowman.c;
      double _snowmanxxx = this.cH() + _snowman.d;
      float _snowmanxxxx = afm.a(c(_snowman));
      this.q = bgm.e(this.s, (float)(afm.d(_snowman.c, (double)_snowmanxxxx) * 180.0F / (float)Math.PI));
      this.p = bgm.e(this.r, (float)(afm.d(_snowman.b, _snowman.d) * 180.0F / (float)Math.PI));
      if (!this.l.v) {
         double _snowmanxxxxx = this.c - _snowmanx;
         double _snowmanxxxxxx = this.e - _snowmanxxx;
         float _snowmanxxxxxxx = (float)Math.sqrt(_snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx);
         float _snowmanxxxxxxxx = (float)afm.d(_snowmanxxxxxx, _snowmanxxxxx);
         double _snowmanxxxxxxxxx = afm.d(0.0025, (double)_snowmanxxxx, (double)_snowmanxxxxxxx);
         double _snowmanxxxxxxxxxx = _snowman.c;
         if (_snowmanxxxxxxx < 1.0F) {
            _snowmanxxxxxxxxx *= 0.8;
            _snowmanxxxxxxxxxx *= 0.8;
         }

         int _snowmanxxxxxxxxxxx = this.cE() < this.d ? 1 : -1;
         _snowman = new dcn(
            Math.cos((double)_snowmanxxxxxxxx) * _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx + ((double)_snowmanxxxxxxxxxxx - _snowmanxxxxxxxxxx) * 0.015F, Math.sin((double)_snowmanxxxxxxxx) * _snowmanxxxxxxxxx
         );
         this.f(_snowman);
      }

      float _snowmanxxxxx = 0.25F;
      if (this.aE()) {
         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 4; _snowmanxxxxxx++) {
            this.l.a(hh.e, _snowmanx - _snowman.b * 0.25, _snowmanxx - _snowman.c * 0.25, _snowmanxxx - _snowman.d * 0.25, _snowman.b, _snowman.c, _snowman.d);
         }
      } else {
         this.l
            .a(
               hh.Q,
               _snowmanx - _snowman.b * 0.25 + this.J.nextDouble() * 0.6 - 0.3,
               _snowmanxx - _snowman.c * 0.25 - 0.5,
               _snowmanxxx - _snowman.d * 0.25 + this.J.nextDouble() * 0.6 - 0.3,
               _snowman.b,
               _snowman.c,
               _snowman.d
            );
      }

      if (!this.l.v) {
         this.d(_snowmanx, _snowmanxx, _snowmanxxx);
         this.f++;
         if (this.f > 80 && !this.l.v) {
            this.a(adq.dw, 1.0F, 1.0F);
            this.ad();
            if (this.g) {
               this.l.c(new bcv(this.l, this.cD(), this.cE(), this.cH(), this.g()));
            } else {
               this.l.c(2003, this.cB(), 0);
            }
         }
      } else {
         this.o(_snowmanx, _snowmanxx, _snowmanxxx);
      }
   }

   @Override
   public void b(md var1) {
      bmb _snowman = this.h();
      if (!_snowman.a()) {
         _snowman.a("Item", _snowman.b(new md()));
      }
   }

   @Override
   public void a(md var1) {
      bmb _snowman = bmb.a(_snowman.p("Item"));
      this.b(_snowman);
   }

   @Override
   public float aR() {
      return 1.0F;
   }

   @Override
   public boolean bL() {
      return false;
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }
}
