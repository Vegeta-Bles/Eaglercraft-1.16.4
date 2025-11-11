public class bix extends bic {
   private final aon c;

   public bix(int var1, bfv var2) {
      this(_snowman, _snowman, new apa(5));
   }

   public bix(int var1, bfv var2, aon var3) {
      super(bje.p, _snowman);
      this.c = _snowman;
      a(_snowman, 5);
      _snowman.c_(_snowman.e);
      int _snowman = 51;

      for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
         this.a(new bjr(_snowman, _snowmanx, 44 + _snowmanx * 18, 20));
      }

      for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
            this.a(new bjr(_snowman, _snowmanxx + _snowmanx * 9 + 9, 8 + _snowmanxx * 18, _snowmanx * 18 + 51));
         }
      }

      for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
         this.a(new bjr(_snowman, _snowmanx, 8 + _snowmanx * 18, 109));
      }
   }

   @Override
   public boolean a(bfw var1) {
      return this.c.a(_snowman);
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         if (_snowman < this.c.Z_()) {
            if (!this.a(_snowmanxx, this.c.Z_(), this.a.size(), true)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 0, this.c.Z_(), false)) {
            return bmb.b;
         }

         if (_snowmanxx.a()) {
            _snowmanx.d(bmb.b);
         } else {
            _snowmanx.d();
         }
      }

      return _snowman;
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.c.b_(_snowman);
   }
}
