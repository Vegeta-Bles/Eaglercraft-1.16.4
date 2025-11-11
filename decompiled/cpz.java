import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class cpz extends cqo<cmg> {
   public cpz(Codec<cmg> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, cmg var2, fx var3) {
      int _snowman = 3 + _snowman.nextInt(6);
      return IntStream.range(0, _snowman).mapToObj(var2x -> {
         int _snowmanx = _snowman.nextInt(16) + _snowman.u();
         int _snowmanx = _snowman.nextInt(16) + _snowman.w();
         int _snowmanxx = _snowman.nextInt(28) + 4;
         return new fx(_snowmanx, _snowmanxx, _snowmanx);
      });
   }
}
