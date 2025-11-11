import java.util.Random;

public abstract class cah extends cab {
   protected cah(ceg.c var1) {
      super(_snowman);
   }

   private static boolean b(ceh var0, brz var1, fx var2) {
      fx _snowman = _snowman.b();
      ceh _snowmanx = _snowman.d_(_snowman);
      if (_snowmanx.a(bup.cC) && _snowmanx.c(caa.a) == 1) {
         return true;
      } else if (_snowmanx.m().e() == 8) {
         return false;
      } else {
         int _snowmanxx = cul.a(_snowman, _snowman, _snowman, _snowmanx, _snowman, gc.b, _snowmanx.b(_snowman, _snowman));
         return _snowmanxx < _snowman.K();
      }
   }

   private static boolean c(ceh var0, brz var1, fx var2) {
      fx _snowman = _snowman.b();
      return b(_snowman, _snowman, _snowman) && !_snowman.b(_snowman).a(aef.b);
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (!b(_snowman, _snowman, _snowman)) {
         _snowman.a(_snowman, bup.j.n());
      } else {
         if (_snowman.B(_snowman.b()) >= 9) {
            ceh _snowman = this.n();

            for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
               fx _snowmanxx = _snowman.b(_snowman.nextInt(3) - 1, _snowman.nextInt(5) - 3, _snowman.nextInt(3) - 1);
               if (_snowman.d_(_snowmanxx).a(bup.j) && c(_snowman, _snowman, _snowmanxx)) {
                  _snowman.a(_snowmanxx, _snowman.a(a, Boolean.valueOf(_snowman.d_(_snowmanxx.b()).a(bup.cC))));
               }
            }
         }
      }
   }
}
