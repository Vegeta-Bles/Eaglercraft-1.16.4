import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class cpr extends cqo<cqd> {
   public cpr(Codec<cqd> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, cqd var2, fx var3) {
      int _snowman = _snowman.c + (_snowman.nextFloat() < _snowman.d ? _snowman.e : 0);
      return IntStream.range(0, _snowman).mapToObj(var1x -> _snowman);
   }
}
