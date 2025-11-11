import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;

public class cof extends coh {
   public static final Codec<cof> a = RecordCodecBuilder.create(var0 -> var0.group(c(), b(), d()).apply(var0, cof::new));

   protected cof(Either<vk, ctb> var1, Supplier<csz> var2, cok.a var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected csx a(bzm var1, cra var2, boolean var3) {
      csx _snowman = super.a(_snowman, _snowman, _snowman);
      _snowman.b(cse.b);
      _snowman.a(cse.d);
      return _snowman;
   }

   @Override
   public coj<?> a() {
      return coj.e;
   }

   @Override
   public String toString() {
      return "LegacySingle[" + this.c + "]";
   }
}
