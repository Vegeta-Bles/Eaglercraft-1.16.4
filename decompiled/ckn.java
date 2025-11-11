import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;

public class ckn extends cjl<cmq> {
   public ckn(Codec<cmq> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmq var5) {
      buo _snowman = _snowman.b.b();
      fx _snowmanx = a(_snowman, _snowman.i().a(gc.a.b, 1, _snowman.L() - 1), _snowman);
      if (_snowmanx == null) {
         return false;
      } else {
         int _snowmanxx = _snowman.b().a(_snowman);
         boolean _snowmanxxx = false;

         for (fx _snowmanxxxx : fx.a(_snowmanx, _snowmanxx, _snowmanxx, _snowmanxx)) {
            if (_snowmanxxxx.k(_snowmanx) > _snowmanxx) {
               break;
            }

            ceh _snowmanxxxxx = _snowman.d_(_snowmanxxxx);
            if (_snowmanxxxxx.a(_snowman)) {
               this.a(_snowman, _snowmanxxxx, _snowman.c);
               _snowmanxxx = true;
            }
         }

         return _snowmanxxx;
      }
   }

   @Nullable
   private static fx a(bry var0, fx.a var1, buo var2) {
      while (_snowman.v() > 1) {
         ceh _snowman = _snowman.d_(_snowman);
         if (_snowman.a(_snowman)) {
            return _snowman;
         }

         _snowman.c(gc.a);
      }

      return null;
   }
}
