import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class clv implements cma {
   public static final Codec<clv> a = RecordCodecBuilder.create(
      var0 -> var0.group(civ.b.fieldOf("feature").forGetter(var0x -> var0x.b), cpo.a.fieldOf("decorator").forGetter(var0x -> var0x.c)).apply(var0, clv::new)
   );
   public final Supplier<civ<?, ?>> b;
   public final cpo<?> c;

   public clv(Supplier<civ<?, ?>> var1, cpo<?> var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public String toString() {
      return String.format("< %s [%s | %s] >", this.getClass().getSimpleName(), gm.aE.b(this.b.get().b()), this.c);
   }

   @Override
   public Stream<civ<?, ?>> an_() {
      return this.b.get().d();
   }
}
