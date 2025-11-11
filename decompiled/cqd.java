import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cqd implements clw {
   public static final Codec<cqd> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.INT.fieldOf("count").forGetter(var0x -> var0x.c),
               Codec.FLOAT.fieldOf("extra_chance").forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("extra_count").forGetter(var0x -> var0x.e)
            )
            .apply(var0, cqd::new)
   );
   public final int c;
   public final float d;
   public final int e;

   public cqd(int var1, float var2, int var3) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }
}
