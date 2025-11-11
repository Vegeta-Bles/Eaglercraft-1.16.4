import javax.annotation.Nullable;

public class bnr extends blx {
   public bnr(blx.a var1) {
      super(_snowman);
   }

   @Override
   public aou a(boa var1) {
      brx _snowman = _snowman.p();
      fx _snowmanx = _snowman.a();
      ceh _snowmanxx = _snowman.d_(_snowmanx);
      if (_snowmanxx.a(bup.lY)) {
         return bxy.a(_snowman, _snowmanx, _snowmanxx, _snowman.m()) ? aou.a(_snowman.v) : aou.c;
      } else {
         return aou.c;
      }
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      _snowman.a(_snowman, _snowman);
      _snowman.b(aea.c.b(this));
      return aov.a(_snowman, _snowman.s_());
   }

   public static boolean a(@Nullable md var0) {
      if (_snowman == null) {
         return false;
      } else if (!_snowman.c("pages", 9)) {
         return false;
      } else {
         mj _snowman = _snowman.d("pages", 8);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            String _snowmanxx = _snowman.j(_snowmanx);
            if (_snowmanxx.length() > 32767) {
               return false;
            }
         }

         return true;
      }
   }
}
