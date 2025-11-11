import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class cmu implements cma {
   public static final Codec<cmu> a = civ.c.fieldOf("features").xmap(cmu::new, var0 -> var0.b).codec();
   public final List<Supplier<civ<?, ?>>> b;

   public cmu(List<Supplier<civ<?, ?>>> var1) {
      this.b = _snowman;
   }

   @Override
   public Stream<civ<?, ?>> an_() {
      return this.b.stream().flatMap(var0 -> var0.get().d());
   }
}
