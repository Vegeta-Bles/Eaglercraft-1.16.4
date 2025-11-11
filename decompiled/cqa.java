import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cqa extends cqc<cmg> {
   public cqa(Codec<cmg> var1) {
      super(_snowman);
   }

   public Stream<fx> a(cpv var1, Random var2, cmg var3, fx var4) {
      if (_snowman.nextInt(700) == 0) {
         int _snowman = _snowman.nextInt(16) + _snowman.u();
         int _snowmanx = _snowman.nextInt(16) + _snowman.w();
         int _snowmanxx = _snowman.a(chn.a.e, _snowman, _snowmanx);
         if (_snowmanxx > 0) {
            int _snowmanxxx = _snowmanxx + 3 + _snowman.nextInt(7);
            return Stream.of(new fx(_snowman, _snowmanxxx, _snowmanx));
         }
      }

      return Stream.empty();
   }
}
