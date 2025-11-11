import java.util.List;
import java.util.Random;

public class bxb extends cah implements buq {
   public bxb(ceg.c var1) {
      super(_snowman);
   }

   @Override
   public boolean a(brc var1, fx var2, ceh var3, boolean var4) {
      return _snowman.d_(_snowman.b()).g();
   }

   @Override
   public boolean a(brx var1, Random var2, fx var3, ceh var4) {
      return true;
   }

   @Override
   public void a(aag var1, Random var2, fx var3, ceh var4) {
      fx _snowman = _snowman.b();
      ceh _snowmanx = bup.aR.n();

      label48:
      for (int _snowmanxx = 0; _snowmanxx < 128; _snowmanxx++) {
         fx _snowmanxxx = _snowman;

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx / 16; _snowmanxxxx++) {
            _snowmanxxx = _snowmanxxx.b(_snowman.nextInt(3) - 1, (_snowman.nextInt(3) - 1) * _snowman.nextInt(3) / 2, _snowman.nextInt(3) - 1);
            if (!_snowman.d_(_snowmanxxx.c()).a(this) || _snowman.d_(_snowmanxxx).r(_snowman, _snowmanxxx)) {
               continue label48;
            }
         }

         ceh _snowmanxxxxx = _snowman.d_(_snowmanxxx);
         if (_snowmanxxxxx.a(_snowmanx.b()) && _snowman.nextInt(10) == 0) {
            ((buq)_snowmanx.b()).a(_snowman, _snowman, _snowmanxxx, _snowmanxxxxx);
         }

         if (_snowmanxxxxx.g()) {
            ceh _snowmanxxxxxx;
            if (_snowman.nextInt(8) == 0) {
               List<civ<?, ?>> _snowmanxxxxxxx = _snowman.v(_snowmanxxx).e().b();
               if (_snowmanxxxxxxx.isEmpty()) {
                  continue;
               }

               civ<?, ?> _snowmanxxxxxxxx = _snowmanxxxxxxx.get(0);
               cii _snowmanxxxxxxxxx = (cii)_snowmanxxxxxxxx.e;
               _snowmanxxxxxx = _snowmanxxxxxxxxx.b(_snowman, _snowmanxxx, _snowmanxxxxxxxx.c());
            } else {
               _snowmanxxxxxx = _snowmanx;
            }

            if (_snowmanxxxxxx.a(_snowman, _snowmanxxx)) {
               _snowman.a(_snowmanxxx, _snowmanxxxxxx, 3);
            }
         }
      }
   }
}
