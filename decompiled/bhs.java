public class bhs extends bhl {
   private static final us<Boolean> d = uv.a(bhs.class, uu.i);
   private int e;
   public double b;
   public double c;
   private static final bon f = bon.a(bmd.ke, bmd.kf);

   public bhs(aqe<? extends bhs> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bhs(brx var1, double var2, double var4, double var6) {
      super(aqe.W, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public bhl.a o() {
      return bhl.a.c;
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(d, false);
   }

   @Override
   public void j() {
      super.j();
      if (!this.l.s_()) {
         if (this.e > 0) {
            this.e--;
         }

         if (this.e <= 0) {
            this.b = 0.0;
            this.c = 0.0;
         }

         this.o(this.e > 0);
      }

      if (this.u() && this.J.nextInt(4) == 0) {
         this.l.a(hh.L, this.cD(), this.cE() + 0.8, this.cH(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   protected double g() {
      return 0.2;
   }

   @Override
   public void a(apk var1) {
      super.a(_snowman);
      if (!_snowman.d() && this.l.V().b(brt.g)) {
         this.a(bup.bY);
      }
   }

   @Override
   protected void c(fx var1, ceh var2) {
      double _snowman = 1.0E-4;
      double _snowmanx = 0.001;
      super.c(_snowman, _snowman);
      dcn _snowmanxx = this.cC();
      double _snowmanxxx = c(_snowmanxx);
      double _snowmanxxxx = this.b * this.b + this.c * this.c;
      if (_snowmanxxxx > 1.0E-4 && _snowmanxxx > 0.001) {
         double _snowmanxxxxx = (double)afm.a(_snowmanxxx);
         double _snowmanxxxxxx = (double)afm.a(_snowmanxxxx);
         this.b = _snowmanxx.b / _snowmanxxxxx * _snowmanxxxxxx;
         this.c = _snowmanxx.d / _snowmanxxxxx * _snowmanxxxxxx;
      }
   }

   @Override
   protected void i() {
      double _snowman = this.b * this.b + this.c * this.c;
      if (_snowman > 1.0E-7) {
         _snowman = (double)afm.a(_snowman);
         this.b /= _snowman;
         this.c /= _snowman;
         this.f(this.cC().d(0.8, 0.0, 0.8).b(this.b, 0.0, this.c));
      } else {
         this.f(this.cC().d(0.98, 0.0, 0.98));
      }

      super.i();
   }

   @Override
   public aou a(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (f.a(_snowman) && this.e + 3600 <= 32000) {
         if (!_snowman.bC.d) {
            _snowman.g(1);
         }

         this.e += 3600;
      }

      if (this.e > 0) {
         this.b = this.cD() - _snowman.cD();
         this.c = this.cH() - _snowman.cH();
      }

      return aou.a(this.l.v);
   }

   @Override
   protected void b(md var1) {
      super.b(_snowman);
      _snowman.a("PushX", this.b);
      _snowman.a("PushZ", this.c);
      _snowman.a("Fuel", (short)this.e);
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      this.b = _snowman.k("PushX");
      this.c = _snowman.k("PushZ");
      this.e = _snowman.g("Fuel");
   }

   protected boolean u() {
      return this.R.a(d);
   }

   protected void o(boolean var1) {
      this.R.b(d, _snowman);
   }

   @Override
   public ceh q() {
      return bup.bY.n().a(bwy.a, gc.c).a(bwy.b, Boolean.valueOf(this.u()));
   }
}
