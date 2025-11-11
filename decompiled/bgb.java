public abstract class bgb extends bgm {
   public double b;
   public double c;
   public double d;

   protected bgb(aqe<? extends bgb> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgb(aqe<? extends bgb> var1, double var2, double var4, double var6, double var8, double var10, double var12, brx var14) {
      this(_snowman, _snowman);
      this.b(_snowman, _snowman, _snowman, this.p, this.q);
      this.af();
      double _snowman = (double)afm.a(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman);
      if (_snowman != 0.0) {
         this.b = _snowman / _snowman * 0.1;
         this.c = _snowman / _snowman * 0.1;
         this.d = _snowman / _snowman * 0.1;
      }
   }

   public bgb(aqe<? extends bgb> var1, aqm var2, double var3, double var5, double var7, brx var9) {
      this(_snowman, _snowman.cD(), _snowman.cE(), _snowman.cH(), _snowman, _snowman, _snowman, _snowman);
      this.b(_snowman);
      this.a(_snowman.p, _snowman.q);
   }

   @Override
   protected void e() {
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

   @Override
   public void j() {
      aqa _snowman = this.v();
      if (this.l.v || (_snowman == null || !_snowman.y) && this.l.C(this.cB())) {
         super.j();
         if (this.W_()) {
            this.f(1);
         }

         dcl _snowmanx = bgn.a(this, this::a);
         if (_snowmanx.c() != dcl.a.a) {
            this.a(_snowmanx);
         }

         this.ay();
         dcn _snowmanxx = this.cC();
         double _snowmanxxx = this.cD() + _snowmanxx.b;
         double _snowmanxxxx = this.cE() + _snowmanxx.c;
         double _snowmanxxxxx = this.cH() + _snowmanxx.d;
         bgn.a(this, 0.2F);
         float _snowmanxxxxxx = this.i();
         if (this.aE()) {
            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
               float _snowmanxxxxxxxx = 0.25F;
               this.l.a(hh.e, _snowmanxxx - _snowmanxx.b * 0.25, _snowmanxxxx - _snowmanxx.c * 0.25, _snowmanxxxxx - _snowmanxx.d * 0.25, _snowmanxx.b, _snowmanxx.c, _snowmanxx.d);
            }

            _snowmanxxxxxx = 0.8F;
         }

         this.f(_snowmanxx.b(this.b, this.c, this.d).a((double)_snowmanxxxxxx));
         this.l.a(this.h(), _snowmanxxx, _snowmanxxxx + 0.5, _snowmanxxxxx, 0.0, 0.0, 0.0);
         this.d(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      } else {
         this.ad();
      }
   }

   @Override
   protected boolean a(aqa var1) {
      return super.a(_snowman) && !_snowman.H;
   }

   protected boolean W_() {
      return true;
   }

   protected hf h() {
      return hh.S;
   }

   protected float i() {
      return 0.95F;
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("power", this.a(new double[]{this.b, this.c, this.d}));
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("power", 9)) {
         mj _snowman = _snowman.d("power", 6);
         if (_snowman.size() == 3) {
            this.b = _snowman.h(0);
            this.c = _snowman.h(1);
            this.d = _snowman.h(2);
         }
      }
   }

   @Override
   public boolean aT() {
      return true;
   }

   @Override
   public float bg() {
      return 1.0F;
   }

   @Override
   public boolean a(apk var1, float var2) {
      if (this.b(_snowman)) {
         return false;
      } else {
         this.aS();
         aqa _snowman = _snowman.k();
         if (_snowman != null) {
            dcn _snowmanx = _snowman.bh();
            this.f(_snowmanx);
            this.b = _snowmanx.b * 0.1;
            this.c = _snowmanx.c * 0.1;
            this.d = _snowmanx.d * 0.1;
            this.b(_snowman);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public float aR() {
      return 1.0F;
   }

   @Override
   public oj<?> P() {
      aqa _snowman = this.v();
      int _snowmanx = _snowman == null ? 0 : _snowman.Y();
      return new on(this.Y(), this.bS(), this.cD(), this.cE(), this.cH(), this.q, this.p, this.X(), _snowmanx, new dcn(this.b, this.c, this.d));
   }
}
