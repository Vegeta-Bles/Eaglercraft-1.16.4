import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cpx extends cqo<cpw> {
   public cpx(Codec<cpw> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, cpw var2, fx var3) {
      int _snowman = _snowman.c;
      int _snowmanx = _snowman.d;
      int _snowmanxx = _snowman.u();
      int _snowmanxxx = _snowman.w();
      int _snowmanxxxx = _snowman.nextInt(_snowmanx) + _snowman.nextInt(_snowmanx) - _snowmanx + _snowman;
      return Stream.of(new fx(_snowmanxx, _snowmanxxxx, _snowmanxxx));
   }
}
