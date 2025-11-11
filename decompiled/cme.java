import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cme implements cma {
   public static final Codec<cme> a = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(var0x -> var0x.b), ckb.b.c.fieldOf("type").forGetter(var0x -> var0x.c))
            .apply(var0, cme::new)
   );
   public final float b;
   public final ckb.b c;

   public cme(float var1, ckb.b var2) {
      this.b = _snowman;
      this.c = _snowman;
   }
}
