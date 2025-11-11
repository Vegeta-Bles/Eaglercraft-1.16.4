import javax.annotation.Nullable;

public abstract class bja extends bic {
   protected final bjm c = new bjm();
   protected final aon d = new apa(2) {
      @Override
      public void X_() {
         super.X_();
         bja.this.a(this);
      }
   };
   protected final bim e;
   protected final bfw f;

   protected abstract boolean b(bfw var1, boolean var2);

   protected abstract bmb a(bfw var1, bmb var2);

   protected abstract boolean a(ceh var1);

   public bja(@Nullable bje<?> var1, int var2, bfv var3, bim var4) {
      super(_snowman, _snowman);
      this.e = _snowman;
      this.f = _snowman.e;
      this.a(new bjr(this.d, 0, 27, 47));
      this.a(new bjr(this.d, 1, 76, 47));
      this.a(new bjr(this.c, 2, 134, 47) {
         @Override
         public boolean a(bmb var1) {
            return false;
         }

         @Override
         public boolean a(bfw var1) {
            return bja.this.b(_snowman, this.f());
         }

         @Override
         public bmb a(bfw var1, bmb var2) {
            return bja.this.a(_snowman, _snowman);
         }
      });

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + _snowman * 9 + 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(new bjr(_snowman, _snowman, 8 + _snowman * 18, 142));
      }
   }

   public abstract void e();

   @Override
   public void a(aon var1) {
      super.a(_snowman);
      if (_snowman == this.d) {
         this.e();
      }
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.e.a((var2, var3) -> this.a(_snowman, var2, this.d));
   }

   @Override
   public boolean a(bfw var1) {
      return this.e
         .a((var2, var3) -> !this.a(var2.d_(var3)) ? false : _snowman.h((double)var3.u() + 0.5, (double)var3.v() + 0.5, (double)var3.w() + 0.5) <= 64.0, true);
   }

   protected boolean a(bmb var1) {
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
         } else if (_snowman != 0 && _snowman != 1) {
            if (_snowman >= 3 && _snowman < 39) {
               int _snowmanxxx = this.a(_snowman) ? 1 : 0;
               if (!this.a(_snowmanxx, _snowmanxxx, 2, false)) {
                  return bmb.b;
               }
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
}
