import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cqj extends cqc<cpn> {
   public cqj(Codec<cpn> var1) {
      super(_snowman);
   }

   public Stream<fx> a(cpv var1, Random var2, cpn var3, fx var4) {
      if (_snowman.nextInt(_snowman.c) == 0) {
         int _snowman = _snowman.nextInt(16) + _snowman.u();
         int _snowmanx = _snowman.nextInt(16) + _snowman.w();
         int _snowmanxx = _snowman.nextInt(_snowman.a());
         return Stream.of(new fx(_snowman, _snowmanxx, _snowmanx));
      } else {
         return Stream.empty();
      }
   }
}
