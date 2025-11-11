import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class cpq extends cqc<cmf> {
   public cpq(Codec<cmf> var1) {
      super(_snowman);
   }

   public Stream<fx> a(cpv var1, Random var2, cmf var3, fx var4) {
      double _snowman = bsv.f.a((double)_snowman.u() / 200.0, (double)_snowman.w() / 200.0, false);
      int _snowmanx = _snowman < _snowman.c ? _snowman.d : _snowman.e;
      return IntStream.range(0, _snowmanx).mapToObj(var1x -> _snowman);
   }
}
