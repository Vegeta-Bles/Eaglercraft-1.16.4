public class bjg extends bic {
   private final bqu c;
   private final bjf d;
   private int e;
   private boolean f;
   private boolean g;

   public bjg(int var1, bfv var2) {
      this(_snowman, _snowman, new bfg(_snowman.e));
   }

   public bjg(int var1, bfv var2, bqu var3) {
      super(bje.s, _snowman);
      this.c = _snowman;
      this.d = new bjf(_snowman);
      this.a(new bjr(this.d, 0, 136, 37));
      this.a(new bjr(this.d, 1, 162, 37));
      this.a(new bjh(_snowman.e, _snowman, this.d, 2, 220, 37));

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + _snowman * 9 + 9, 108 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(new bjr(_snowman, _snowman, 108 + _snowman * 18, 142));
      }
   }

   public void a(boolean var1) {
      this.f = _snowman;
   }

   @Override
   public void a(aon var1) {
      this.d.f();
      super.a(_snowman);
   }

   public void d(int var1) {
      this.d.c(_snowman);
   }

   @Override
   public boolean a(bfw var1) {
      return this.c.eM() == _snowman;
   }

   public int e() {
      return this.c.eL();
   }

   public int f() {
      return this.d.h();
   }

   public void e(int var1) {
      this.c.t(_snowman);
   }

   public int g() {
      return this.e;
   }

   public void f(int var1) {
      this.e = _snowman;
   }

   public void b(boolean var1) {
      this.g = _snowman;
   }

   public boolean h() {
      return this.g;
   }

   @Override
   public boolean a(bmb var1, bjr var2) {
      return false;
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         if (_snowman == 2) {
            if (!this.a(_snowmanxx, 3, 39, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
            this.k();
         } else if (_snowman != 0 && _snowman != 1) {
            if (_snowman >= 3 && _snowman < 30) {
               if (!this.a(_snowmanxx, 30, 39, false)) {
                  return bmb.b;
               }
            } else if (_snowman >= 30 && _snowman < 39 && !this.a(_snowmanxx, 3, 30, false)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 3, 39, false)) {
            return bmb.b;
         }

         if (_snowmanxx.a()) {
            _snowmanx.d(bmb.b);
         } else {
            _snowmanx.d();
         }

         if (_snowmanxx.E() == _snowman.E()) {
            return bmb.b;
         }

         _snowmanx.a(_snowman, _snowmanxx);
      }

      return _snowman;
   }

   private void k() {
      if (!this.c.eV().v) {
         aqa _snowman = (aqa)this.c;
         this.c.eV().a(_snowman.cD(), _snowman.cE(), _snowman.cH(), this.c.eQ(), adr.g, 1.0F, 1.0F, false);
      }
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.c.f(null);
      if (!this.c.eV().v) {
         if (!_snowman.aX() || _snowman instanceof aah && ((aah)_snowman).q()) {
            bmb _snowman = this.d.b(0);
            if (!_snowman.a()) {
               _snowman.a(_snowman, false);
            }

            _snowman = this.d.b(1);
            if (!_snowman.a()) {
               _snowman.a(_snowman, false);
            }
         } else {
            _snowman.bm.a(_snowman.l, this.d.b(0));
            _snowman.bm.a(_snowman.l, this.d.b(1));
         }
      }
   }

   public void g(int var1) {
      if (this.i().size() > _snowman) {
         bmb _snowman = this.d.a(0);
         if (!_snowman.a()) {
            if (!this.a(_snowman, 3, 39, true)) {
               return;
            }

            this.d.a(0, _snowman);
         }

         bmb _snowmanx = this.d.a(1);
         if (!_snowmanx.a()) {
            if (!this.a(_snowmanx, 3, 39, true)) {
               return;
            }

            this.d.a(1, _snowmanx);
         }

         if (this.d.a(0).a() && this.d.a(1).a()) {
            bmb _snowmanxx = this.i().get(_snowman).b();
            this.c(0, _snowmanxx);
            bmb _snowmanxxx = this.i().get(_snowman).c();
            this.c(1, _snowmanxxx);
         }
      }
   }

   private void c(int var1, bmb var2) {
      if (!_snowman.a()) {
         for (int _snowman = 3; _snowman < 39; _snowman++) {
            bmb _snowmanx = this.a.get(_snowman).e();
            if (!_snowmanx.a() && this.b(_snowman, _snowmanx)) {
               bmb _snowmanxx = this.d.a(_snowman);
               int _snowmanxxx = _snowmanxx.a() ? 0 : _snowmanxx.E();
               int _snowmanxxxx = Math.min(_snowman.c() - _snowmanxxx, _snowmanx.E());
               bmb _snowmanxxxxx = _snowmanx.i();
               int _snowmanxxxxxx = _snowmanxxx + _snowmanxxxx;
               _snowmanx.g(_snowmanxxxx);
               _snowmanxxxxx.e(_snowmanxxxxxx);
               this.d.a(_snowman, _snowmanxxxxx);
               if (_snowmanxxxxxx >= _snowman.c()) {
                  break;
               }
            }
         }
      }
   }

   private boolean b(bmb var1, bmb var2) {
      return _snowman.b() == _snowman.b() && bmb.a(_snowman, _snowman);
   }

   public void a(bqw var1) {
      this.c.a(_snowman);
   }

   public bqw i() {
      return this.c.eO();
   }

   public boolean j() {
      return this.f;
   }
}
