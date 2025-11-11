import java.util.Random;

public class byp extends buo implements buq {
   protected byp(ceg.c var1) {
      super(_snowman);
   }

   private static boolean b(ceh var0, brz var1, fx var2) {
      fx _snowman = _snowman.b();
      ceh _snowmanx = _snowman.d_(_snowman);
      int _snowmanxx = cul.a(_snowman, _snowman, _snowman, _snowmanx, _snowman, gc.b, _snowmanx.b(_snowman, _snowman));
      return _snowmanxx < _snowman.K();
   }

   @Override
   public void b(ceh var1, aag var2, fx var3, Random var4) {
      if (!b(_snowman, _snowman, _snowman)) {
         _snowman.a(_snowman, bup.cL.n());
      }
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return _snowman.d_(_snowman.b()).g();
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      ceh _snowman = _snowman.d_(_snowman);
      fx _snowmanx = _snowman.b();
      if (_snowman.a(bup.mu)) {
         ckd.a(_snowman, _snowman, _snowmanx, kh.a.k, 3, 1);
      } else if (_snowman.a(bup.ml)) {
         ckd.a(_snowman, _snowman, _snowmanx, kh.a.l, 3, 1);
         ckd.a(_snowman, _snowman, _snowmanx, kh.a.m, 3, 1);
         if (_snowman.nextInt(8) == 0) {
            cle.a(_snowman, _snowman, _snowmanx, 3, 1, 2);
         }
      }
   }
}
