public class bvl extends bwo {
   private final ceh a;

   public bvl(buo var1, ceg.c var2) {
      super(_snowman);
      this.a = _snowman.n();
   }

   @Override
   public void a(brx var1, fx var2, ceh var3, ceh var4, bcu var5) {
      if (b(_snowman, _snowman, _snowman)) {
         _snowman.a(_snowman, this.a, 3);
      }
   }

   @Override
   public ceh a(bny var1) {
      brc _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      return b(_snowman, _snowmanx, _snowmanxx) ? this.a : super.a(_snowman);
   }

   private static boolean b(brc var0, fx var1, ceh var2) {
      return l(_snowman) || a(_snowman, _snowman);
   }

   private static boolean a(brc var0, fx var1) {
      boolean _snowman = false;
      fx.a _snowmanx = _snowman.i();

      for (gc _snowmanxx : gc.values()) {
         ceh _snowmanxxx = _snowman.d_(_snowmanx);
         if (_snowmanxx != gc.a || l(_snowmanxxx)) {
            _snowmanx.a(_snowman, _snowmanxx);
            _snowmanxxx = _snowman.d_(_snowmanx);
            if (l(_snowmanxxx) && !_snowmanxxx.d(_snowman, _snowman, _snowmanxx.f())) {
               _snowman = true;
               break;
            }
         }
      }

      return _snowman;
   }

   private static boolean l(ceh var0) {
      return _snowman.m().a(aef.b);
   }

   @Override
   public ceh a(ceh var1, gc var2, ceh var3, bry var4, fx var5, fx var6) {
      return a(_snowman, _snowman) ? this.a : super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public int c(ceh var1, brc var2, fx var3) {
      return _snowman.d(_snowman, _snowman).ai;
   }
}
