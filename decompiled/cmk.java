import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmk implements chz, cma {
   public static final Codec<cmk> b = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(var0x -> var0x.c)).apply(var0, cmk::new)
   );
   public final float c;

   public cmk(float var1) {
      this.c = _snowman;
   }
}
