import com.mojang.serialization.Codec;
import java.util.Random;

public class clh extends cjl<cmh> {
   private static final fx a = new fx(8, 3, 8);
   private static final brd ab = new brd(a);

   public clh(Codec<cmh> var1) {
      super(_snowman);
   }

   private static int a(int var0, int var1, int var2, int var3) {
      return Math.max(Math.abs(_snowman - _snowman), Math.abs(_snowman - _snowman));
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      brd _snowman = new brd(_snowman);
      if (a(_snowman.b, _snowman.c, ab.b, ab.c) > 1) {
         return true;
      } else {
         fx.a _snowmanx = new fx.a();

         for (int _snowmanxx = _snowman.e(); _snowmanxx <= _snowman.g(); _snowmanxx++) {
            for (int _snowmanxxx = _snowman.d(); _snowmanxxx <= _snowman.f(); _snowmanxxx++) {
               if (a(a.u(), a.w(), _snowmanxxx, _snowmanxx) <= 16) {
                  _snowmanx.d(_snowmanxxx, a.v(), _snowmanxx);
                  if (_snowmanx.equals(a)) {
                     _snowman.a(_snowmanx, bup.m.n(), 2);
                  } else {
                     _snowman.a(_snowmanx, bup.b.n(), 2);
                  }
               }
            }
         }

         return true;
      }
   }
}
