import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class cml implements cma {
   public static final Codec<cml> a = RecordCodecBuilder.create(
      var0 -> var0.group(civ.b.fieldOf("feature_true").forGetter(var0x -> var0x.b), civ.b.fieldOf("feature_false").forGetter(var0x -> var0x.c))
            .apply(var0, cml::new)
   );
   public final Supplier<civ<?, ?>> b;
   public final Supplier<civ<?, ?>> c;

   public cml(Supplier<civ<?, ?>> var1, Supplier<civ<?, ?>> var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public Stream<civ<?, ?>> an_() {
      return Stream.concat(this.b.get().d(), this.c.get().d());
   }
}
