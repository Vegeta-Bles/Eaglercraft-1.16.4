import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class cis extends cjl<cmh> {
   public cis(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      brd _snowman = new brd(_snowman);
      List<Integer> _snowmanx = IntStream.rangeClosed(_snowman.d(), _snowman.f()).boxed().collect(Collectors.toList());
      Collections.shuffle(_snowmanx, _snowman);
      List<Integer> _snowmanxx = IntStream.rangeClosed(_snowman.e(), _snowman.g()).boxed().collect(Collectors.toList());
      Collections.shuffle(_snowmanxx, _snowman);
      fx.a _snowmanxxx = new fx.a();

      for (Integer _snowmanxxxx : _snowmanx) {
         for (Integer _snowmanxxxxx : _snowmanxx) {
            _snowmanxxx.d(_snowmanxxxx, 0, _snowmanxxxxx);
            fx _snowmanxxxxxx = _snowman.a(chn.a.f, _snowmanxxx);
            if (_snowman.w(_snowmanxxxxxx) || _snowman.d_(_snowmanxxxxxx).k(_snowman, _snowmanxxxxxx).b()) {
               _snowman.a(_snowmanxxxxxx, bup.bR.n(), 2);
               cdd.a(_snowman, _snowman, _snowmanxxxxxx, cyq.b);
               ceh _snowmanxxxxxxx = bup.bL.n();

               for (gc _snowmanxxxxxxxx : gc.c.a) {
                  fx _snowmanxxxxxxxxx = _snowmanxxxxxx.a(_snowmanxxxxxxxx);
                  if (_snowmanxxxxxxx.a(_snowman, _snowmanxxxxxxxxx)) {
                     _snowman.a(_snowmanxxxxxxxxx, _snowmanxxxxxxx, 2);
                  }
               }

               return true;
            }
         }
      }

      return false;
   }
}
