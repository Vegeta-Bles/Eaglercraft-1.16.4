import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cql implements clw {
   public static final Codec<cql> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.INT.fieldOf("noise_to_count_ratio").forGetter(var0x -> var0x.c),
               Codec.DOUBLE.fieldOf("noise_factor").forGetter(var0x -> var0x.d),
               Codec.DOUBLE.fieldOf("noise_offset").orElse(0.0).forGetter(var0x -> var0x.e)
            )
            .apply(var0, cql::new)
   );
   public final int c;
   public final double d;
   public final double e;

   public cql(int var1, double var2, double var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }
}
