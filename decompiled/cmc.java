import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;

public class cmc implements cma {
   public static final Codec<cmc> a = RecordCodecBuilder.create(
      var0 -> var0.group(cok.b.fieldOf("start_pool").forGetter(cmc::c), Codec.intRange(0, 7).fieldOf("size").forGetter(cmc::b)).apply(var0, cmc::new)
   );
   private final Supplier<cok> b;
   private final int c;

   public cmc(Supplier<cok> var1, int var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public int b() {
      return this.c;
   }

   public Supplier<cok> c() {
      return this.b;
   }
}
