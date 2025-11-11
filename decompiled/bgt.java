public abstract class bgt extends bgm {
   protected bgt(aqe<? extends bgt> var1, brx var2) {
      super(_snowman, _snowman);
   }

   protected bgt(aqe<? extends bgt> var1, double var2, double var4, double var6, brx var8) {
      this(_snowman, _snowman);
      this.d(_snowman, _snowman, _snowman);
   }

   protected bgt(aqe<? extends bgt> var1, aqm var2, brx var3) {
      this(_snowman, _snowman.cD(), _snowman.cG() - 0.1F, _snowman.cH(), _snowman);
      this.b((aqa)_snowman);
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
      super.j();
      dcl _snowman = bgn.a(this, this::a);
      boolean _snowmanx = false;
      if (_snowman.c() == dcl.a.b) {
         fx _snowmanxx = ((dcj)_snowman).a();
         ceh _snowmanxxx = this.l.d_(_snowmanxx);
         if (_snowmanxxx.a(bup.cT)) {
            this.d(_snowmanxx);
            _snowmanx = true;
         } else if (_snowmanxxx.a(bup.iF)) {
            ccj _snowmanxxxx = this.l.c(_snowmanxx);
            if (_snowmanxxxx instanceof cdk && cdk.a(this)) {
               ((cdk)_snowmanxxxx).b(this);
            }

            _snowmanx = true;
         }
      }

      if (_snowman.c() != dcl.a.a && !_snowmanx) {
         this.a(_snowman);
      }

      this.ay();
      dcn _snowmanxx = this.cC();
      double _snowmanxxx = this.cD() + _snowmanxx.b;
      double _snowmanxxxx = this.cE() + _snowmanxx.c;
      double _snowmanxxxxx = this.cH() + _snowmanxx.d;
      this.x();
      float _snowmanxxxxxx;
      if (this.aE()) {
         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
            float _snowmanxxxxxxxx = 0.25F;
            this.l.a(hh.e, _snowmanxxx - _snowmanxx.b * 0.25, _snowmanxxxx - _snowmanxx.c * 0.25, _snowmanxxxxx - _snowmanxx.d * 0.25, _snowmanxx.b, _snowmanxx.c, _snowmanxx.d);
         }

         _snowmanxxxxxx = 0.8F;
      } else {
         _snowmanxxxxxx = 0.99F;
      }

      this.f(_snowmanxx.a((double)_snowmanxxxxxx));
      if (!this.aB()) {
         dcn _snowmanxxxxxxx = this.cC();
         this.n(_snowmanxxxxxxx.b, _snowmanxxxxxxx.c - (double)this.k(), _snowmanxxxxxxx.d);
      }

      this.d(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   protected float k() {
      return 0.03F;
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }
}
