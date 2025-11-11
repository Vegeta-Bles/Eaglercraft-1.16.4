import com.mojang.serialization.Codec;
import java.util.Random;

public class cim extends cjl<cmh> {
   public cim(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      if (_snowman.w(_snowman) && !_snowman.w(_snowman.b())) {
         fx.a _snowman = _snowman.i();
         fx.a _snowmanx = _snowman.i();
         boolean _snowmanxx = true;
         boolean _snowmanxxx = true;
         boolean _snowmanxxxx = true;
         boolean _snowmanxxxxx = true;

         while (_snowman.w(_snowman)) {
            if (brx.m(_snowman)) {
               return true;
            }

            _snowman.a(_snowman, bup.cO.n(), 2);
            _snowmanxx = _snowmanxx && this.b(_snowman, _snowman, _snowmanx.a(_snowman, gc.c));
            _snowmanxxx = _snowmanxxx && this.b(_snowman, _snowman, _snowmanx.a(_snowman, gc.d));
            _snowmanxxxx = _snowmanxxxx && this.b(_snowman, _snowman, _snowmanx.a(_snowman, gc.e));
            _snowmanxxxxx = _snowmanxxxxx && this.b(_snowman, _snowman, _snowmanx.a(_snowman, gc.f));
            _snowman.c(gc.a);
         }

         _snowman.c(gc.b);
         this.a(_snowman, _snowman, _snowmanx.a(_snowman, gc.c));
         this.a(_snowman, _snowman, _snowmanx.a(_snowman, gc.d));
         this.a(_snowman, _snowman, _snowmanx.a(_snowman, gc.e));
         this.a(_snowman, _snowman, _snowmanx.a(_snowman, gc.f));
         _snowman.c(gc.a);
         fx.a _snowmanxxxxxx = new fx.a();

         for (int _snowmanxxxxxxx = -3; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
            for (int _snowmanxxxxxxxx = -3; _snowmanxxxxxxxx < 4; _snowmanxxxxxxxx++) {
               int _snowmanxxxxxxxxx = afm.a(_snowmanxxxxxxx) * afm.a(_snowmanxxxxxxxx);
               if (_snowman.nextInt(10) < 10 - _snowmanxxxxxxxxx) {
                  _snowmanxxxxxx.g(_snowman.b(_snowmanxxxxxxx, 0, _snowmanxxxxxxxx));
                  int _snowmanxxxxxxxxxx = 3;

                  while (_snowman.w(_snowmanx.a(_snowmanxxxxxx, gc.a))) {
                     _snowmanxxxxxx.c(gc.a);
                     if (--_snowmanxxxxxxxxxx <= 0) {
                        break;
                     }
                  }

                  if (!_snowman.w(_snowmanx.a(_snowmanxxxxxx, gc.a))) {
                     _snowman.a(_snowmanxxxxxx, bup.cO.n(), 2);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void a(bry var1, Random var2, fx var3) {
      if (_snowman.nextBoolean()) {
         _snowman.a(_snowman, bup.cO.n(), 2);
      }
   }

   private boolean b(bry var1, Random var2, fx var3) {
      if (_snowman.nextInt(10) != 0) {
         _snowman.a(_snowman, bup.cO.n(), 2);
         return true;
      } else {
         return false;
      }
   }
}
