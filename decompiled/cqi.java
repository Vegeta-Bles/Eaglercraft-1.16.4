import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cqi extends cqc<cpn> {
   public cqi(Codec<cpn> var1) {
      super(_snowman);
   }

   public Stream<fx> a(cpv var1, Random var2, cpn var3, fx var4) {
      if (_snowman.nextInt(_snowman.c / 10) == 0) {
         int _snowman = _snowman.nextInt(16) + _snowman.u();
         int _snowmanx = _snowman.nextInt(16) + _snowman.w();
         int _snowmanxx = _snowman.nextInt(_snowman.nextInt(_snowman.a() - 8) + 8);
         if (_snowmanxx < _snowman.b() || _snowman.nextInt(_snowman.c / 8) == 0) {
            return Stream.of(new fx(_snowman, _snowmanxx, _snowmanx));
         }
      }

      return Stream.empty();
   }
}
