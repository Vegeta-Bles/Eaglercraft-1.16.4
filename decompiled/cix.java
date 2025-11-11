import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class cix extends ciy {
   public cix(Codec<cmh> var1) {
      super(_snowman);
   }

   @Override
   protected boolean a(bry var1, Random var2, fx var3, ceh var4) {
      if (!this.b(_snowman, _snowman, _snowman, _snowman)) {
         return false;
      } else {
         gc _snowman = gc.c.a.a(_snowman);
         int _snowmanx = _snowman.nextInt(2) + 2;
         List<gc> _snowmanxx = Lists.newArrayList(new gc[]{_snowman, _snowman.g(), _snowman.h()});
         Collections.shuffle(_snowmanxx, _snowman);

         for (gc _snowmanxxx : _snowmanxx.subList(0, _snowmanx)) {
            fx.a _snowmanxxxx = _snowman.i();
            int _snowmanxxxxx = _snowman.nextInt(2) + 1;
            _snowmanxxxx.c(_snowmanxxx);
            int _snowmanxxxxxx;
            gc _snowmanxxxxxxx;
            if (_snowmanxxx == _snowman) {
               _snowmanxxxxxxx = _snowman;
               _snowmanxxxxxx = _snowman.nextInt(3) + 2;
            } else {
               _snowmanxxxx.c(gc.b);
               gc[] _snowmanxxxxxxxx = new gc[]{_snowmanxxx, gc.b};
               _snowmanxxxxxxx = x.a(_snowmanxxxxxxxx, _snowman);
               _snowmanxxxxxx = _snowman.nextInt(3) + 3;
            }

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxx && this.b(_snowman, _snowman, _snowmanxxxx, _snowman); _snowmanxxxxxxxx++) {
               _snowmanxxxx.c(_snowmanxxxxxxx);
            }

            _snowmanxxxx.c(_snowmanxxxxxxx.f());
            _snowmanxxxx.c(gc.b);

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxxx++) {
               _snowmanxxxx.c(_snowman);
               if (!this.b(_snowman, _snowman, _snowmanxxxx, _snowman)) {
                  break;
               }

               if (_snowman.nextFloat() < 0.25F) {
                  _snowmanxxxx.c(gc.b);
               }
            }
         }

         return true;
      }
   }
}
