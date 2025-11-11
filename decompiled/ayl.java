public class ayl extends ayj {
   private boolean p;

   public ayl(aqn var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected cxf a(int var1) {
      this.p = this.a instanceof baf;
      this.o = new cxg(this.p);
      return new cxf(this.o, _snowman);
   }

   @Override
   protected boolean a() {
      return this.p || this.p();
   }

   @Override
   protected dcn b() {
      return new dcn(this.a.cD(), this.a.e(0.5), this.a.cH());
   }

   @Override
   public void c() {
      this.e++;
      if (this.m) {
         this.j();
      }

      if (!this.m()) {
         if (this.a()) {
            this.l();
         } else if (this.c != null && !this.c.c()) {
            dcn _snowman = this.c.a(this.a);
            if (afm.c(this.a.cD()) == afm.c(_snowman.b) && afm.c(this.a.cE()) == afm.c(_snowman.c) && afm.c(this.a.cH()) == afm.c(_snowman.d)) {
               this.c.a();
            }
         }

         rz.a(this.b, this.a, this.c, this.l);
         if (!this.m()) {
            dcn _snowman = this.c.a(this.a);
            this.a.u().a(_snowman.b, _snowman.c, _snowman.d, this.d);
         }
      }
   }

   @Override
   protected void l() {
      if (this.c != null) {
         dcn _snowman = this.b();
         float _snowmanx = this.a.cy();
         float _snowmanxx = _snowmanx > 0.75F ? _snowmanx / 2.0F : 0.75F - _snowmanx / 2.0F;
         dcn _snowmanxxx = this.a.cC();
         if (Math.abs(_snowmanxxx.b) > 0.2 || Math.abs(_snowmanxxx.d) > 0.2) {
            _snowmanxx = (float)((double)_snowmanxx * _snowmanxxx.f() * 6.0);
         }

         int _snowmanxxxx = 6;
         dcn _snowmanxxxxx = dcn.c(this.c.g());
         if (Math.abs(this.a.cD() - _snowmanxxxxx.b) < (double)_snowmanxx
            && Math.abs(this.a.cH() - _snowmanxxxxx.d) < (double)_snowmanxx
            && Math.abs(this.a.cE() - _snowmanxxxxx.c) < (double)(_snowmanxx * 2.0F)) {
            this.c.a();
         }

         for (int _snowmanxxxxxx = Math.min(this.c.f() + 6, this.c.e() - 1); _snowmanxxxxxx > this.c.f(); _snowmanxxxxxx--) {
            _snowmanxxxxx = this.c.a(this.a, _snowmanxxxxxx);
            if (!(_snowmanxxxxx.g(_snowman) > 36.0) && this.a(_snowman, _snowmanxxxxx, 0, 0, 0)) {
               this.c.c(_snowmanxxxxxx);
               break;
            }
         }

         this.a(_snowman);
      }
   }

   @Override
   protected void a(dcn var1) {
      if (this.e - this.f > 100) {
         if (_snowman.g(this.g) < 2.25) {
            this.o();
         }

         this.f = this.e;
         this.g = _snowman;
      }

      if (this.c != null && !this.c.c()) {
         gr _snowman = this.c.g();
         if (_snowman.equals(this.h)) {
            this.i = this.i + (x.b() - this.j);
         } else {
            this.h = _snowman;
            double _snowmanx = _snowman.f(dcn.a(this.h));
            this.k = this.a.dN() > 0.0F ? _snowmanx / (double)this.a.dN() * 100.0 : 0.0;
         }

         if (this.k > 0.0 && (double)this.i > this.k * 2.0) {
            this.h = gr.d;
            this.i = 0L;
            this.k = 0.0;
            this.o();
         }

         this.j = x.b();
      }
   }

   @Override
   protected boolean a(dcn var1, dcn var2, int var3, int var4, int var5) {
      dcn _snowman = new dcn(_snowman.b, _snowman.c + (double)this.a.cz() * 0.5, _snowman.d);
      return this.b.a(new brf(_snowman, _snowman, brf.a.a, brf.b.a, this.a)).c() == dcl.a.a;
   }

   @Override
   public boolean a(fx var1) {
      return !this.b.d_(_snowman).i(this.b, _snowman);
   }

   @Override
   public void d(boolean var1) {
   }
}
