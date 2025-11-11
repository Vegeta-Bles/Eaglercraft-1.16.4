import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class cmm implements cma {
   public static final Codec<cmm> a = RecordCodecBuilder.create(
      var0 -> var0.apply2(cmm::new, clj.a.listOf().fieldOf("features").forGetter(var0x -> var0x.b), civ.b.fieldOf("default").forGetter(var0x -> var0x.c))
   );
   public final List<clj> b;
   public final Supplier<civ<?, ?>> c;

   public cmm(List<clj> var1, civ<?, ?> var2) {
      this(_snowman, () -> _snowman);
   }

   private cmm(List<clj> var1, Supplier<civ<?, ?>> var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public Stream<civ<?, ?>> an_() {
      return Stream.concat(this.b.stream().flatMap(var0 -> var0.b.get().d()), this.c.get().d());
   }
}
