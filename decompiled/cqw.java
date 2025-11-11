import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cqw extends cqc<cmg> {
   public cqw(Codec<cmg> var1) {
      super(_snowman);
   }

   public Stream<fx> a(cpv var1, Random var2, cmg var3, fx var4) {
      int _snowman = _snowman.b();
      int _snowmanx = _snowman - 5 + _snowman.nextInt(10);
      return Stream.of(new fx(_snowman.u(), _snowmanx, _snowman.w()));
   }
}
