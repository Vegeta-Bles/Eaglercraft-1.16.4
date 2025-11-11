import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class cqk extends cqo<cql> {
   public cqk(Codec<cql> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, cql var2, fx var3) {
      double _snowman = bsv.f.a((double)_snowman.u() / _snowman.d, (double)_snowman.w() / _snowman.d, false);
      int _snowmanx = (int)Math.ceil((_snowman + _snowman.e) * (double)_snowman.c);
      return IntStream.range(0, _snowmanx).mapToObj(var1x -> _snowman);
   }
}
