import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cqb extends cqo<cmg> {
   public cqb(Codec<cmg> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, cmg var2, fx var3) {
      Stream<fx> _snowman = Stream.empty();
      if (_snowman.nextInt(14) == 0) {
         _snowman = Stream.concat(_snowman, Stream.of(_snowman.b(_snowman.nextInt(16), 55 + _snowman.nextInt(16), _snowman.nextInt(16))));
         if (_snowman.nextInt(4) == 0) {
            _snowman = Stream.concat(_snowman, Stream.of(_snowman.b(_snowman.nextInt(16), 55 + _snowman.nextInt(16), _snowman.nextInt(16))));
         }

         return _snowman;
      } else {
         return Stream.empty();
      }
   }
}
