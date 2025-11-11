import javax.annotation.Nullable;

public class bbx extends bbt {
   private static final azg b = new azg().a(64.0);
   private cxd c;
   private dcn d;
   private boolean e;

   public bbx(bbr var1) {
      super(_snowman);
   }

   @Override
   public bch<bbx> i() {
      return bch.a;
   }

   @Override
   public void c() {
      double _snowman = this.d == null ? 0.0 : this.d.c(this.a.cD(), this.a.cE(), this.a.cH());
      if (_snowman < 100.0 || _snowman > 22500.0 || this.a.u || this.a.v) {
         this.j();
      }
   }

   @Override
   public void d() {
      this.c = null;
      this.d = null;
   }

   @Nullable
   @Override
   public dcn g() {
      return this.d;
   }

   private void j() {
      if (this.c != null && this.c.c()) {
         fx _snowman = this.a.l.a(chn.a.f, new fx(cjk.a));
         int _snowmanx = this.a.eL() == null ? 0 : this.a.eL().c();
         if (this.a.cY().nextInt(_snowmanx + 3) == 0) {
            this.a.eK().a(bch.c);
            return;
         }

         double _snowmanxx = 64.0;
         bfw _snowmanxxx = this.a.l.a(b, (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());
         if (_snowmanxxx != null) {
            _snowmanxx = _snowman.a(_snowmanxxx.cA(), true) / 512.0;
         }

         if (_snowmanxxx != null && !_snowmanxxx.bC.a && (this.a.cY().nextInt(afm.a((int)_snowmanxx) + 2) == 0 || this.a.cY().nextInt(_snowmanx + 2) == 0)) {
            this.a(_snowmanxxx);
            return;
         }
      }

      if (this.c == null || this.c.c()) {
         int _snowmanxxxx = this.a.eI();
         int _snowmanxxxxx = _snowmanxxxx;
         if (this.a.cY().nextInt(8) == 0) {
            this.e = !this.e;
            _snowmanxxxxx = _snowmanxxxx + 6;
         }

         if (this.e) {
            _snowmanxxxxx++;
         } else {
            _snowmanxxxxx--;
         }

         if (this.a.eL() != null && this.a.eL().c() >= 0) {
            _snowmanxxxxx %= 12;
            if (_snowmanxxxxx < 0) {
               _snowmanxxxxx += 12;
            }
         } else {
            _snowmanxxxxx -= 12;
            _snowmanxxxxx &= 7;
            _snowmanxxxxx += 12;
         }

         this.c = this.a.a(_snowmanxxxx, _snowmanxxxxx, null);
         if (this.c != null) {
            this.c.a();
         }
      }

      this.k();
   }

   private void a(bfw var1) {
      this.a.eK().a(bch.b);
      this.a.eK().b(bch.b).a(_snowman);
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

   @Override
   public void a(bbq var1, fx var2, apk var3, @Nullable bfw var4) {
      if (_snowman != null && !_snowman.bC.a) {
         this.a(_snowman);
      }
   }
}
