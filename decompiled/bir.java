public class bir extends bic {
   private final aon c;

   public bir(int var1, bfv var2) {
      this(_snowman, _snowman, new apa(9));
   }

   public bir(int var1, bfv var2, aon var3) {
      super(bje.g, _snowman);
      a(_snowman, 9);
      this.c = _snowman;
      _snowman.c_(_snowman.e);

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + _snowman * 3, 62 + _snowmanx * 18, 17 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(new bjr(_snowman, _snowman, 8 + _snowman * 18, 142));
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
         if (_snowman < 9) {
            if (!this.a(_snowmanxx, 9, 45, true)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 0, 9, false)) {
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

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.c.b_(_snowman);
   }
}
