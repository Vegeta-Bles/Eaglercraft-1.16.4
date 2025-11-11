import com.mojang.serialization.Codec;
import java.util.Random;

public class cky extends cjl<cmw> {
   public cky(Codec<cmw> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmw var5) {
      if (!_snowman.f.contains(_snowman.d_(_snowman.b()).b())) {
         return false;
      } else if (_snowman.c && !_snowman.f.contains(_snowman.d_(_snowman.c()).b())) {
         return false;
      } else {
         ceh _snowman = _snowman.d_(_snowman);
         if (!_snowman.g() && !_snowman.f.contains(_snowman.b())) {
            return false;
         } else {
            int _snowmanx = 0;
            int _snowmanxx = 0;
            if (_snowman.f.contains(_snowman.d_(_snowman.f()).b())) {
               _snowmanxx++;
            }

            if (_snowman.f.contains(_snowman.d_(_snowman.g()).b())) {
               _snowmanxx++;
            }

            if (_snowman.f.contains(_snowman.d_(_snowman.d()).b())) {
               _snowmanxx++;
            }

            if (_snowman.f.contains(_snowman.d_(_snowman.e()).b())) {
               _snowmanxx++;
            }

            if (_snowman.f.contains(_snowman.d_(_snowman.c()).b())) {
               _snowmanxx++;
            }

            int _snowmanxxx = 0;
            if (_snowman.w(_snowman.f())) {
               _snowmanxxx++;
            }

            if (_snowman.w(_snowman.g())) {
               _snowmanxxx++;
            }

            if (_snowman.w(_snowman.d())) {
               _snowmanxxx++;
            }

            if (_snowman.w(_snowman.e())) {
               _snowmanxxx++;
            }

            if (_snowman.w(_snowman.c())) {
               _snowmanxxx++;
            }

            if (_snowmanxx == _snowman.d && _snowmanxxx == _snowman.e) {
               _snowman.a(_snowman, _snowman.b.g(), 2);
               _snowman.I().a(_snowman, _snowman.b.a(), 0);
               _snowmanx++;
            }

            return _snowmanx > 0;
         }
      }
   }
}
