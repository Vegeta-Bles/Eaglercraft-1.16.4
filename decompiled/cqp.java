import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cqp extends cqc<cmg> {
   public cqp(Codec<cmg> var1) {
      super(_snowman);
   }

   public Stream<fx> a(cpv var1, Random var2, cmg var3, fx var4) {
      int _snowman = _snowman.nextInt(_snowman.v() + 32);
      return Stream.of(new fx(_snowman.u(), _snowman, _snowman.w()));
   }
}
