public class bmu extends blx {
   public bmu(blx.a var1) {
      super(_snowman);
   }

   @Override
   public boolean a(bmb var1, brx var2, ceh var3, fx var4, aqm var5) {
      if (!_snowman.v && !_snowman.b().a(aed.an)) {
         _snowman.a(1, _snowman, var0 -> var0.c(aqf.a));
      }

      return !_snowman.a(aed.I) && !_snowman.a(bup.aQ) && !_snowman.a(bup.aR) && !_snowman.a(bup.aS) && !_snowman.a(bup.aT) && !_snowman.a(bup.dP) && !_snowman.a(bup.em) && !_snowman.a(aed.b)
         ? super.a(_snowman, _snowman, _snowman, _snowman, _snowman)
         : true;
   }

   @Override
   public boolean b(ceh var1) {
      return _snowman.a(bup.aQ) || _snowman.a(bup.bS) || _snowman.a(bup.em);
   }

   @Override
   public float a(bmb var1, ceh var2) {
      if (_snowman.a(bup.aQ) || _snowman.a(aed.I)) {
         return 15.0F;
      } else {
         return _snowman.a(aed.b) ? 5.0F : super.a(_snowman, _snowman);
      }
   }
}
