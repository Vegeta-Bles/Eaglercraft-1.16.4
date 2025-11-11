import javax.annotation.Nullable;

public class bbz extends bbt {
   private static final azg b = new azg().a(128.0);
   private cxd c;
   private dcn d;

   public bbz(bbr var1) {
      super(_snowman);
   }

   @Override
   public bch<bbz> i() {
      return bch.c;
   }

   @Override
   public void d() {
      this.c = null;
      this.d = null;
   }

   @Override
   public void c() {
      double _snowman = this.d == null ? 0.0 : this.d.c(this.a.cD(), this.a.cE(), this.a.cH());
      if (_snowman < 100.0 || _snowman > 22500.0 || this.a.u || this.a.v) {
         this.j();
      }
   }

   @Nullable
   @Override
   public dcn g() {
      return this.d;
   }

   private void j() {
      if (this.c == null || this.c.c()) {
         int _snowman = this.a.eI();
         fx _snowmanx = this.a.l.a(chn.a.f, cjk.a);
         bfw _snowmanxx = this.a.l.a(b, (double)_snowmanx.u(), (double)_snowmanx.v(), (double)_snowmanx.w());
         int _snowmanxxx;
         if (_snowmanxx != null) {
            dcn _snowmanxxxx = new dcn(_snowmanxx.cD(), 0.0, _snowmanxx.cH()).d();
            _snowmanxxx = this.a.p(-_snowmanxxxx.b * 40.0, 105.0, -_snowmanxxxx.d * 40.0);
         } else {
            _snowmanxxx = this.a.p(40.0, (double)_snowmanx.v(), 0.0);
         }

         cxb _snowmanxxxx = new cxb(_snowmanx.u(), _snowmanx.v(), _snowmanx.w());
         this.c = this.a.a(_snowman, _snowmanxxx, _snowmanxxxx);
         if (this.c != null) {
            this.c.a();
         }
      }

      this.k();
      if (this.c != null && this.c.c()) {
         this.a.eK().a(bch.d);
      }
   }

   private void k() {
      if (this.c != null && !this.c.c()) {
         gr _snowman = this.c.g();
         this.c.a();
         double _snowmanx = (double)_snowman.u();
         double _snowmanxx = (double)_snowman.w();

         double _snowmanxxx;
         do {
            _snowmanxxx = (double)((float)_snowman.v() + this.a.cY().nextFloat() * 20.0F);
         } while (_snowmanxxx < (double)_snowman.v());

         this.d = new dcn(_snowmanx, _snowmanxxx, _snowmanxx);
      }
   }
}
