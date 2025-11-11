import javax.annotation.Nullable;

public class bcg extends bbt {
   private boolean b;
   private cxd c;
   private dcn d;

   public bcg(bbr var1) {
      super(_snowman);
   }

   @Override
   public void c() {
      if (!this.b && this.c != null) {
         fx _snowman = this.a.l.a(chn.a.f, cjk.a);
         if (!_snowman.a(this.a.cA(), 10.0)) {
            this.a.eK().a(bch.a);
         }
      } else {
         this.b = false;
         this.j();
      }
   }

   @Override
   public void d() {
      this.b = true;
      this.c = null;
      this.d = null;
   }

   private void j() {
      int _snowman = this.a.eI();
      dcn _snowmanx = this.a.x(1.0F);
      int _snowmanxx = this.a.p(-_snowmanx.b * 40.0, 105.0, -_snowmanx.d * 40.0);
      if (this.a.eL() != null && this.a.eL().c() > 0) {
         _snowmanxx %= 12;
         if (_snowmanxx < 0) {
            _snowmanxx += 12;
         }
      } else {
         _snowmanxx -= 12;
         _snowmanxx &= 7;
         _snowmanxx += 12;
      }

      this.c = this.a.a(_snowman, _snowmanxx, null);
      this.k();
   }

   private void k() {
      if (this.c != null) {
         this.c.a();
         if (!this.c.c()) {
            gr _snowman = this.c.g();
            this.c.a();

            double _snowmanx;
            do {
               _snowmanx = (double)((float)_snowman.v() + this.a.cY().nextFloat() * 20.0F);
            } while (_snowmanx < (double)_snowman.v());

            this.d = new dcn((double)_snowman.u(), _snowmanx, (double)_snowman.w());
         }
      }
   }

   @Nullable
   @Override
   public dcn g() {
      return this.d;
   }

   @Override
   public bch<bcg> i() {
      return bch.e;
   }
}
