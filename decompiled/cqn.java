import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cqn extends cqo<cmo> {
   public cqn(Codec<cmo> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, cmo var2, fx var3) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.w();
      int _snowmanxx = _snowman.nextInt(_snowman.e - _snowman.d) + _snowman.c;
      return Stream.of(new fx(_snowman, _snowmanxx, _snowmanx));
   }
}
