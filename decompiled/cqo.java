import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;

public abstract class cqo<DC extends clw> extends cqc<DC> {
   public cqo(Codec<DC> var1) {
      super(_snowman);
   }

   @Override
   public final Stream<fx> a(cpv var1, Random var2, DC var3, fx var4) {
      return this.a(_snowman, _snowman, _snowman);
   }

   protected abstract Stream<fx> a(Random var1, DC var2, fx var3);
}
