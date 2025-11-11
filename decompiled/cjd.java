import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;

public class cjd extends cjl<clx> {
   private static final ImmutableList<buo> a = ImmutableList.of(bup.z, bup.dV, bup.dW, bup.dX, bup.dY, bup.bR, bup.bP);
   private static final gc[] ab = gc.values();

   public cjd(Codec<clx> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, clx var5) {
      boolean _snowman = false;
      boolean _snowmanx = _snowman.nextDouble() < 0.9;
      int _snowmanxx = _snowmanx ? _snowman.e().a(_snowman) : 0;
      int _snowmanxxx = _snowmanx ? _snowman.e().a(_snowman) : 0;
      boolean _snowmanxxxx = _snowmanx && _snowmanxx != 0 && _snowmanxxx != 0;
      int _snowmanxxxxx = _snowman.d().a(_snowman);
      int _snowmanxxxxxx = _snowman.d().a(_snowman);
      int _snowmanxxxxxxx = Math.max(_snowmanxxxxx, _snowmanxxxxxx);

      for (fx _snowmanxxxxxxxx : fx.a(_snowman, _snowmanxxxxx, 0, _snowmanxxxxxx)) {
         if (_snowmanxxxxxxxx.k(_snowman) > _snowmanxxxxxxx) {
            break;
         }

         if (a(_snowman, _snowmanxxxxxxxx, _snowman)) {
            if (_snowmanxxxx) {
               _snowman = true;
               this.a(_snowman, _snowmanxxxxxxxx, _snowman.c());
            }

            fx _snowmanxxxxxxxxx = _snowmanxxxxxxxx.b(_snowmanxx, 0, _snowmanxxx);
            if (a(_snowman, _snowmanxxxxxxxxx, _snowman)) {
               _snowman = true;
               this.a(_snowman, _snowmanxxxxxxxxx, _snowman.b());
            }
         }
      }

      return _snowman;
   }

   private static boolean a(bry var0, fx var1, clx var2) {
      ceh _snowman = _snowman.d_(_snowman);
      if (_snowman.a(_snowman.b().b())) {
         return false;
      } else if (a.contains(_snowman.b())) {
         return false;
      } else {
         for (gc _snowmanx : ab) {
            boolean _snowmanxx = _snowman.d_(_snowman.a(_snowmanx)).g();
            if (_snowmanxx && _snowmanx != gc.b || !_snowmanxx && _snowmanx == gc.b) {
               return false;
            }
         }

         return true;
      }
   }
}
