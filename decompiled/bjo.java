public class bjo extends bic {
   private final aon c;

   public bjo(int var1, bfv var2) {
      this(_snowman, _snowman, new apa(27));
   }

   public bjo(int var1, bfv var2, aon var3) {
      super(bje.t, _snowman);
      a(_snowman, 27);
      this.c = _snowman;
      _snowman.c_(_snowman.e);
      int _snowman = 3;
      int _snowmanx = 9;

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
            this.a(new bjp(_snowman, _snowmanxxx + _snowmanxx * 9, 8 + _snowmanxxx * 18, 18 + _snowmanxx * 18));
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
            this.a(new bjr(_snowman, _snowmanxxx + _snowmanxx * 9 + 9, 8 + _snowmanxxx * 18, 84 + _snowmanxx * 18));
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
         this.a(new bjr(_snowman, _snowmanxx, 8 + _snowmanxx * 18, 142));
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
