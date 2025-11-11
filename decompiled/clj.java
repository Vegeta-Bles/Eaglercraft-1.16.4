import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.Supplier;

public class clj {
   public static final Codec<clj> a = RecordCodecBuilder.create(
      var0 -> var0.group(civ.b.fieldOf("feature").forGetter(var0x -> var0x.b), Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(var0x -> var0x.c))
            .apply(var0, clj::new)
   );
   public final Supplier<civ<?, ?>> b;
   public final float c;

   public clj(civ<?, ?> var1, float var2) {
      this(() -> _snowman, _snowman);
   }

   private clj(Supplier<civ<?, ?>> var1, float var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4) {
      return this.b.get().a(_snowman, _snowman, _snowman, _snowman);
   }
}
