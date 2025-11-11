import java.util.Random;

public class byn extends buo implements buq {
   public byn(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      if (!_snowman.d_(_snowman.b()).a(_snowman, _snowman)) {
         return false;
      } else {
         for (fx _snowman : fx.a(_snowman.b(-1, -1, -1), _snowman.b(1, 1, 1))) {
            if (_snowman.d_(_snowman).a(aed.ao)) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      boolean _snowman = false;
      boolean _snowmanx = false;

      for (fx _snowmanxx : fx.a(_snowman.b(-1, -1, -1), _snowman.b(1, 1, 1))) {
         ceh _snowmanxxx = _snowman.d_(_snowmanxx);
         if (_snowmanxxx.a(bup.ml)) {
            _snowmanx = true;
         }

         if (_snowmanxxx.a(bup.mu)) {
            _snowman = true;
         }

         if (_snowmanx && _snowman) {
            break;
         }
      }

      if (_snowmanx && _snowman) {
         _snowman.a(_snowman, _snowman.nextBoolean() ? bup.ml.n() : bup.mu.n(), 3);
      } else if (_snowmanx) {
         _snowman.a(_snowman, bup.ml.n(), 3);
      } else if (_snowman) {
         _snowman.a(_snowman, bup.mu.n(), 3);
      }
   }
}
