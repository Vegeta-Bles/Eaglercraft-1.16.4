import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class cqu extends cqo<clu> {
   public cqu(Codec<clu> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, clu var2, fx var3) {
      List<fx> _snowman = Lists.newArrayList();

      for (int _snowmanx = 0; _snowmanx < _snowman.nextInt(_snowman.nextInt(_snowman.a().a(_snowman)) + 1) + 1; _snowmanx++) {
         int _snowmanxx = _snowman.nextInt(16) + _snowman.u();
         int _snowmanxxx = _snowman.nextInt(16) + _snowman.w();
         int _snowmanxxxx = _snowman.nextInt(120) + 4;
         _snowman.add(new fx(_snowmanxx, _snowmanxxxx, _snowmanxxx));
      }

      return _snowman.stream();
   }
}
