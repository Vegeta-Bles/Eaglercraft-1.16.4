import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class cps extends cpy<cmg> {
   public cps(Codec<cmg> var1) {
      super(_snowman);
   }

   protected chn.a a(cmg var1) {
      return chn.a.e;
   }

   public Stream<fx> a(cpv var1, Random var2, cmg var3, fx var4) {
      return IntStream.range(0, 16).mapToObj(var5 -> {
         int _snowman = var5 / 4;
         int _snowmanx = var5 % 4;
         int _snowmanxx = _snowman * 4 + 1 + _snowman.nextInt(3) + _snowman.u();
         int _snowmanxxx = _snowmanx * 4 + 1 + _snowman.nextInt(3) + _snowman.w();
         int _snowmanxxxx = _snowman.a(this.a(_snowman), _snowmanxx, _snowmanxxx);
         return new fx(_snowmanxx, _snowmanxxxx, _snowmanxxx);
      });
   }
}
