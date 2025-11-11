import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cqh extends cqo<cmg> {
   public cqh(Codec<cmg> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, cmg var2, fx var3) {
      int _snowman = _snowman.nextInt(8) + 4 + _snowman.u();
      int _snowmanx = _snowman.nextInt(8) + 4 + _snowman.w();
      return Stream.of(new fx(_snowman, _snowman.v(), _snowmanx));
   }
}
