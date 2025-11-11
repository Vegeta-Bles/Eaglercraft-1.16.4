import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public class cpm extends cqo<cpn> {
   public cpm(Codec<cpn> var1) {
      super(_snowman);
   }

   public Stream<fx> a(Random var1, cpn var2, fx var3) {
      return _snowman.nextFloat() < 1.0F / (float)_snowman.c ? Stream.of(_snowman) : Stream.empty();
   }
}
