import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;

public class cmy {
   public static final Codec<cmy> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.intRange(0, 4096).fieldOf("spacing").forGetter(var0x -> var0x.b),
                  Codec.intRange(0, 4096).fieldOf("separation").forGetter(var0x -> var0x.c),
                  Codec.intRange(0, Integer.MAX_VALUE).fieldOf("salt").forGetter(var0x -> var0x.d)
               )
               .apply(var0, cmy::new)
      )
      .comapFlatMap(var0 -> var0.b <= var0.c ? DataResult.error("Spacing has to be smaller than separation") : DataResult.success(var0), Function.identity());
   private final int b;
   private final int c;
   private final int d;

   public cmy(int var1, int var2, int var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public int a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }
}
