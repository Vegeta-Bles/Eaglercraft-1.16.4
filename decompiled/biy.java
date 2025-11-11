public class biy extends bic {
   private final aon c;
   private final bbb d;

   public biy(int var1, bfv var2, aon var3, final bbb var4) {
      super(null, _snowman);
      this.c = _snowman;
      this.d = _snowman;
      int _snowman = 3;
      _snowman.c_(_snowman.e);
      int _snowmanx = -18;
      this.a(new bjr(_snowman, 0, 8, 18) {
         @Override
         public boolean a(bmb var1) {
            return _snowman.b() == bmd.lO && !this.f() && _snowman.L_();
         }

         @Override
         public boolean b() {
            return _snowman.L_();
         }
      });
      this.a(new bjr(_snowman, 1, 8, 36) {
         @Override
         public boolean a(bmb var1) {
            return _snowman.l(_snowman);
         }

         @Override
         public boolean b() {
            return _snowman.fs();
         }

         @Override
         public int a() {
            return 1;
         }
      });
      if (_snowman instanceof bba && ((bba)_snowman).eM()) {
         for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
            for (int _snowmanxxx = 0; _snowmanxxx < ((bba)_snowman).eU(); _snowmanxxx++) {
               this.a(new bjr(_snowman, 2 + _snowmanxxx + _snowmanxx * ((bba)_snowman).eU(), 80 + _snowmanxxx * 18, 18 + _snowmanxx * 18));
            }
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < 9; _snowmanxxx++) {
            this.a(new bjr(_snowman, _snowmanxxx + _snowmanxx * 9 + 9, 8 + _snowmanxxx * 18, 102 + _snowmanxx * 18 + -18));
         }
      }

      for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
         this.a(new bjr(_snowman, _snowmanxx, 8 + _snowmanxx * 18, 142));
      }
   }

   @Override
   public boolean a(bfw var1) {
      return this.c.a(_snowman) && this.d.aX() && this.d.g(_snowman) < 8.0F;
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         int _snowmanxxx = this.c.Z_();
         if (_snowman < _snowmanxxx) {
            if (!this.a(_snowmanxx, _snowmanxxx, this.a.size(), true)) {
               return bmb.b;
            }
         } else if (this.a(1).a(_snowmanxx) && !this.a(1).f()) {
            if (!this.a(_snowmanxx, 1, 2, false)) {
               return bmb.b;
            }
         } else if (this.a(0).a(_snowmanxx)) {
            if (!this.a(_snowmanxx, 0, 1, false)) {
               return bmb.b;
            }
         } else if (_snowmanxxx <= 2 || !this.a(_snowmanxx, 2, _snowmanxxx, false)) {
            int _snowmanxxxx = _snowmanxxx + 27;
            int _snowmanxxxxx = _snowmanxxxx + 9;
            if (_snowman >= _snowmanxxxx && _snowman < _snowmanxxxxx) {
               if (!this.a(_snowmanxx, _snowmanxxx, _snowmanxxxx, false)) {
                  return bmb.b;
               }
            } else if (_snowman >= _snowmanxxx && _snowman < _snowmanxxxx) {
               if (!this.a(_snowmanxx, _snowmanxxxx, _snowmanxxxxx, false)) {
                  return bmb.b;
               }
            } else if (!this.a(_snowmanxx, _snowmanxxxx, _snowmanxxxx, false)) {
               return bmb.b;
            }

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
