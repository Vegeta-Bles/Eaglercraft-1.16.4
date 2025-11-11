public class bld extends blx implements bnq {
   public bld(blx.a var1) {
      super(_snowman);
      bwa.a(this, bjy.a);
   }

   public static boolean d(bmb var0) {
      return _snowman.g() < _snowman.h() - 1;
   }

   @Override
   public boolean a(bmb var1, bmb var2) {
      return _snowman.b() == bmd.qN;
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      aqf _snowmanx = aqn.j(_snowman);
      bmb _snowmanxx = _snowman.b(_snowmanx);
      if (_snowmanxx.a()) {
         _snowman.a(_snowmanx, _snowman.i());
         _snowman.e(0);
         return aov.a(_snowman, _snowman.s_());
      } else {
         return aov.d(_snowman);
      }
   }
}
