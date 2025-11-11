import com.mojang.serialization.Codec;
import java.util.Random;

public class cjf extends cjl<cmh> {
   private static final cer a = cer.a(bup.C);
   private final ceh ab = bup.hS.n();
   private final ceh ac = bup.at.n();
   private final ceh ad = bup.A.n();

   public cjf(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      _snowman = _snowman.b();

      while (_snowman.w(_snowman) && _snowman.v() > 2) {
         _snowman = _snowman.c();
      }

      if (!a.a(_snowman.d_(_snowman))) {
         return false;
      } else {
         for (int _snowman = -2; _snowman <= 2; _snowman++) {
            for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
               if (_snowman.w(_snowman.b(_snowman, -1, _snowmanx)) && _snowman.w(_snowman.b(_snowman, -2, _snowmanx))) {
                  return false;
               }
            }
         }

         for (int _snowman = -1; _snowman <= 0; _snowman++) {
            for (int _snowmanxx = -2; _snowmanxx <= 2; _snowmanxx++) {
               for (int _snowmanxxx = -2; _snowmanxxx <= 2; _snowmanxxx++) {
                  _snowman.a(_snowman.b(_snowmanxx, _snowman, _snowmanxxx), this.ac, 2);
               }
            }
         }

         _snowman.a(_snowman, this.ad, 2);

         for (gc _snowman : gc.c.a) {
            _snowman.a(_snowman.a(_snowman), this.ad, 2);
         }

         for (int _snowman = -2; _snowman <= 2; _snowman++) {
            for (int _snowmanxx = -2; _snowmanxx <= 2; _snowmanxx++) {
               if (_snowman == -2 || _snowman == 2 || _snowmanxx == -2 || _snowmanxx == 2) {
                  _snowman.a(_snowman.b(_snowman, 1, _snowmanxx), this.ac, 2);
               }
            }
         }

         _snowman.a(_snowman.b(2, 1, 0), this.ab, 2);
         _snowman.a(_snowman.b(-2, 1, 0), this.ab, 2);
         _snowman.a(_snowman.b(0, 1, 2), this.ab, 2);
         _snowman.a(_snowman.b(0, 1, -2), this.ab, 2);

         for (int _snowman = -1; _snowman <= 1; _snowman++) {
            for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
               if (_snowman == 0 && _snowmanxxx == 0) {
                  _snowman.a(_snowman.b(_snowman, 4, _snowmanxxx), this.ac, 2);
               } else {
                  _snowman.a(_snowman.b(_snowman, 4, _snowmanxxx), this.ab, 2);
               }
            }
         }

         for (int _snowman = 1; _snowman <= 3; _snowman++) {
            _snowman.a(_snowman.b(-1, _snowman, -1), this.ac, 2);
            _snowman.a(_snowman.b(-1, _snowman, 1), this.ac, 2);
            _snowman.a(_snowman.b(1, _snowman, -1), this.ac, 2);
            _snowman.a(_snowman.b(1, _snowman, 1), this.ac, 2);
         }

         return true;
      }
   }
}
