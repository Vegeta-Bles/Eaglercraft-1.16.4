import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmi implements cma {
   public static final Codec<cmi> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               crm.b.c.fieldOf("biome_temp").forGetter(var0x -> var0x.b),
               Codec.floatRange(0.0F, 1.0F).fieldOf("large_probability").forGetter(var0x -> var0x.c),
               Codec.floatRange(0.0F, 1.0F).fieldOf("cluster_probability").forGetter(var0x -> var0x.d)
            )
            .apply(var0, cmi::new)
   );
   public final crm.b b;
   public final float c;
   public final float d;

   public cmi(crm.b var1, float var2, float var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }
}
