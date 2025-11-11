import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmf implements clw {
   public static final Codec<cmf> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.DOUBLE.fieldOf("noise_level").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("below_noise").forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("above_noise").forGetter(var0x -> var0x.e)
            )
            .apply(var0, cmf::new)
   );
   public final double c;
   public final int d;
   public final int e;

   public cmf(double var1, int var3, int var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }
}
