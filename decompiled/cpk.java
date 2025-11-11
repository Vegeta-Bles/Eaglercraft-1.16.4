import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class cpk extends cqc<cpl> {
   public cpk(Codec<cpl> var1) {
      super(_snowman);
   }

   public Stream<fx> a(cpv var1, Random var2, cpl var3, fx var4) {
      brd _snowman = new brd(_snowman);
      BitSet _snowmanx = _snowman.a(_snowman, _snowman.c);
      return IntStream.range(0, _snowmanx.length()).filter(var3x -> _snowman.get(var3x) && _snowman.nextFloat() < _snowman.d).mapToObj(var1x -> {
         int _snowmanxx = var1x & 15;
         int _snowmanx = var1x >> 4 & 15;
         int _snowmanxx = var1x >> 8;
         return new fx(_snowman.d() + _snowmanxx, _snowmanxx, _snowman.e() + _snowmanx);
      });
   }
}
