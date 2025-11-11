public abstract class bba extends bbb {
   private static final us<Boolean> bw = uv.a(bba.class, uu.i);

   protected bba(aqe<? extends bba> var1, brx var2) {
      super(_snowman, _snowman);
      this.bu = false;
   }

   @Override
   protected void eK() {
      this.a(arl.a).a((double)this.fp());
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(bw, false);
   }

   public static ark.a eL() {
      return fi().a(arl.d, 0.175F).a(arl.m, 0.5);
   }

   public boolean eM() {
      return this.R.a(bw);
   }

   public void t(boolean var1) {
      this.R.b(bw, _snowman);
   }

   @Override
   protected int eN() {
      return this.eM() ? 17 : super.eN();
   }

   @Override
   public double bc() {
      return super.bc() - 0.25;
   }

   @Override
   protected void dn() {
      super.dn();
      if (this.eM()) {
         if (!this.l.v) {
            this.a(bup.bR);
         }

         this.t(false);
      }
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      _snowman.a("ChestedHorse", this.eM());
      if (this.eM()) {
         mj _snowman = new mj();

         for (int _snowmanx = 2; _snowmanx < this.br.Z_(); _snowmanx++) {
            bmb _snowmanxx = this.br.a(_snowmanx);
            if (!_snowmanxx.a()) {
               md _snowmanxxx = new md();
               _snowmanxxx.a("Slot", (byte)_snowmanx);
               _snowmanxx.b(_snowmanxxx);
               _snowman.add(_snowmanxxx);
            }
         }

         _snowman.a("Items", _snowman);
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      this.t(_snowman.q("ChestedHorse"));
      if (this.eM()) {
         mj _snowman = _snowman.d("Items", 10);
         this.fd();

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            md _snowmanxx = _snowman.a(_snowmanx);
            int _snowmanxxx = _snowmanxx.f("Slot") & 255;
            if (_snowmanxxx >= 2 && _snowmanxxx < this.br.Z_()) {
               this.br.a(_snowmanxxx, bmb.a(_snowmanxx));
            }
         }
      }

      this.fe();
   }

   @Override
   public boolean a_(int var1, bmb var2) {
      if (_snowman == 499) {
         if (this.eM() && _snowman.a()) {
            this.t(false);
            this.fd();
            return true;
         }

         if (!this.eM() && _snowman.b() == bup.bR.h()) {
            this.t(true);
            this.fd();
            return true;
         }
      }

      return super.a_(_snowman, _snowman);
   }

   @Override
   public aou b(bfw var1, aot var2) {
      bmb _snowman = _snowman.b(_snowman);
      if (!this.w_()) {
         if (this.eW() && _snowman.eq()) {
            this.f(_snowman);
            return aou.a(this.l.v);
         }

         if (this.bs()) {
            return super.b(_snowman, _snowman);
         }
      }

      if (!_snowman.a()) {
         if (this.k(_snowman)) {
            return this.b(_snowman, _snowman);
         }

         if (!this.eW()) {
            this.fm();
            return aou.a(this.l.v);
         }

         if (!this.eM() && _snowman.b() == bup.bR.h()) {
            this.t(true);
            this.eO();
            if (!_snowman.bC.d) {
               _snowman.g(1);
            }

            this.fd();
            return aou.a(this.l.v);
         }

         if (!this.w_() && !this.M_() && _snowman.b() == bmd.lO) {
            this.f(_snowman);
            return aou.a(this.l.v);
         }
      }

      if (this.w_()) {
         return super.b(_snowman, _snowman);
      } else {
         this.h(_snowman);
         return aou.a(this.l.v);
      }
   }

   @Override
   protected void eO() {
      this.a(adq.cP, 1.0F, (this.J.nextFloat() - this.J.nextFloat()) * 0.2F + 1.0F);
   }

   public int eU() {
      return 5;
   }
}
