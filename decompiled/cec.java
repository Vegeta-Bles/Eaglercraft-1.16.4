public class cec {
   public static dci a(dci var0, gc var1, double var2) {
      double _snowman = _snowman * (double)_snowman.e().a();
      double _snowmanx = Math.min(_snowman, 0.0);
      double _snowmanxx = Math.max(_snowman, 0.0);
      switch (_snowman) {
         case e:
            return new dci(_snowman.a + _snowmanx, _snowman.b, _snowman.c, _snowman.a + _snowmanxx, _snowman.e, _snowman.f);
         case f:
            return new dci(_snowman.d + _snowmanx, _snowman.b, _snowman.c, _snowman.d + _snowmanxx, _snowman.e, _snowman.f);
         case a:
            return new dci(_snowman.a, _snowman.b + _snowmanx, _snowman.c, _snowman.d, _snowman.b + _snowmanxx, _snowman.f);
         case b:
         default:
            return new dci(_snowman.a, _snowman.e + _snowmanx, _snowman.c, _snowman.d, _snowman.e + _snowmanxx, _snowman.f);
         case c:
            return new dci(_snowman.a, _snowman.b, _snowman.c + _snowmanx, _snowman.d, _snowman.e, _snowman.c + _snowmanxx);
         case d:
            return new dci(_snowman.a, _snowman.b, _snowman.f + _snowmanx, _snowman.d, _snowman.e, _snowman.f + _snowmanxx);
      }
   }
}
