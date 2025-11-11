public class bij extends bic {
   private final aon c;
   private final int d;

   private bij(bje<?> var1, int var2, bfv var3, int var4) {
      this(_snowman, _snowman, _snowman, new apa(9 * _snowman), _snowman);
   }

   public static bij a(int var0, bfv var1) {
      return new bij(bje.a, _snowman, _snowman, 1);
   }

   public static bij b(int var0, bfv var1) {
      return new bij(bje.b, _snowman, _snowman, 2);
   }

   public static bij c(int var0, bfv var1) {
      return new bij(bje.c, _snowman, _snowman, 3);
   }

   public static bij d(int var0, bfv var1) {
      return new bij(bje.d, _snowman, _snowman, 4);
   }

   public static bij e(int var0, bfv var1) {
      return new bij(bje.e, _snowman, _snowman, 5);
   }

   public static bij f(int var0, bfv var1) {
      return new bij(bje.f, _snowman, _snowman, 6);
   }

   public static bij a(int var0, bfv var1, aon var2) {
      return new bij(bje.c, _snowman, _snowman, _snowman, 3);
   }

   public static bij b(int var0, bfv var1, aon var2) {
      return new bij(bje.f, _snowman, _snowman, _snowman, 6);
   }

   public bij(bje<?> var1, int var2, bfv var3, aon var4, int var5) {
      super(_snowman, _snowman);
      a(_snowman, _snowman * 9);
      this.c = _snowman;
      this.d = _snowman;
      _snowman.c_(_snowman.e);
      int _snowman = (this.d - 4) * 18;

      for (int _snowmanx = 0; _snowmanx < this.d; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
            this.a(new bjr(_snowman, _snowmanxx + _snowmanx * 9, 8 + _snowmanxx * 18, 18 + _snowmanx * 18));
         }
      }

      for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
            this.a(new bjr(_snowman, _snowmanxx + _snowmanx * 9 + 9, 8 + _snowmanxx * 18, 103 + _snowmanx * 18 + _snowman));
         }
      }

      for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
         this.a(new bjr(_snowman, _snowmanx, 8 + _snowmanx * 18, 161 + _snowman));
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
         if (_snowman < this.d * 9) {
            if (!this.a(_snowmanxx, this.d * 9, this.a.size(), true)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 0, this.d * 9, false)) {
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

   public aon e() {
      return this.c;
   }

   public int f() {
      return this.d;
   }
}
