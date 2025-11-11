import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class cja extends ciy {
   public cja(Codec<cmh> var1) {
      super(_snowman);
   }

   @Override
   protected boolean a(bry var1, Random var2, fx var3, ceh var4) {
      fx.a _snowman = _snowman.i();
      int _snowmanx = _snowman.nextInt(3) + 1;

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         if (!this.b(_snowman, _snowman, _snowman, _snowman)) {
            return true;
         }

         _snowman.c(gc.b);
      }

      fx _snowmanxx = _snowman.h();
      int _snowmanxxx = _snowman.nextInt(3) + 2;
      List<gc> _snowmanxxxx = Lists.newArrayList(gc.c.a);
      Collections.shuffle(_snowmanxxxx, _snowman);

      for (gc _snowmanxxxxx : _snowmanxxxx.subList(0, _snowmanxxx)) {
         _snowman.g(_snowmanxx);
         _snowman.c(_snowmanxxxxx);
         int _snowmanxxxxxx = _snowman.nextInt(5) + 2;
         int _snowmanxxxxxxx = 0;

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx && this.b(_snowman, _snowman, _snowman, _snowman); _snowmanxxxxxxxx++) {
            _snowmanxxxxxxx++;
            _snowman.c(gc.b);
            if (_snowmanxxxxxxxx == 0 || _snowmanxxxxxxx >= 2 && _snowman.nextFloat() < 0.25F) {
               _snowman.c(_snowmanxxxxx);
               _snowmanxxxxxxx = 0;
            }
         }
      }

      return true;
   }
}
