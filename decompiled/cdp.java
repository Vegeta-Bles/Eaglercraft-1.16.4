import java.util.Random;
import javax.annotation.Nullable;

public abstract class cdp extends cdq {
   public cdp() {
   }

   @Override
   public boolean a(aag var1, cfy var2, fx var3, ceh var4, Random var5) {
      for (int _snowman = 0; _snowman >= -1; _snowman--) {
         for (int _snowmanx = 0; _snowmanx >= -1; _snowmanx--) {
            if (a(_snowman, _snowman, _snowman, _snowman, _snowmanx)) {
               return this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx);
            }
         }
      }

      return super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   protected abstract civ<cmz, ?> a(Random var1);

   public boolean a(aag var1, cfy var2, fx var3, ceh var4, Random var5, int var6, int var7) {
      civ<cmz, ?> _snowman = this.a(_snowman);
      if (_snowman == null) {
         return false;
      } else {
         _snowman.f.b();
         ceh _snowmanx = bup.a.n();
         _snowman.a(_snowman.b(_snowman, 0, _snowman), _snowmanx, 4);
         _snowman.a(_snowman.b(_snowman + 1, 0, _snowman), _snowmanx, 4);
         _snowman.a(_snowman.b(_snowman, 0, _snowman + 1), _snowmanx, 4);
         _snowman.a(_snowman.b(_snowman + 1, 0, _snowman + 1), _snowmanx, 4);
         if (_snowman.a(_snowman, _snowman, _snowman, _snowman.b(_snowman, 0, _snowman))) {
            return true;
         } else {
            _snowman.a(_snowman.b(_snowman, 0, _snowman), _snowman, 4);
            _snowman.a(_snowman.b(_snowman + 1, 0, _snowman), _snowman, 4);
            _snowman.a(_snowman.b(_snowman, 0, _snowman + 1), _snowman, 4);
            _snowman.a(_snowman.b(_snowman + 1, 0, _snowman + 1), _snowman, 4);
            return false;
         }
      }
   }

   public static boolean a(ceh var0, brc var1, fx var2, int var3, int var4) {
      buo _snowman = _snowman.b();
      return _snowman == _snowman.d_(_snowman.b(_snowman, 0, _snowman)).b() && _snowman == _snowman.d_(_snowman.b(_snowman + 1, 0, _snowman)).b() && _snowman == _snowman.d_(_snowman.b(_snowman, 0, _snowman + 1)).b() && _snowman == _snowman.d_(_snowman.b(_snowman + 1, 0, _snowman + 1)).b();
   }
}
