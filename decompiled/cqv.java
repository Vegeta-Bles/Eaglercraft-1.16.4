import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class cqv extends cqo<clu> {
   public cqv(Codec<clu> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, clu var2, fx var3) {
      return IntStream.range(0, _snowman.nextInt(_snowman.nextInt(_snowman.a().a(_snowman)) + 1)).mapToObj(var2x -> {
         int _snowman = _snowman.nextInt(16) + _snowman.u();
         int _snowmanx = _snowman.nextInt(16) + _snowman.w();
         int _snowmanxx = _snowman.nextInt(120) + 4;
         return new fx(_snowman, _snowmanxx, _snowmanx);
      });
   }
}
