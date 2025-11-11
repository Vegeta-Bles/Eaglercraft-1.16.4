import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmo implements clw {
   public static final Codec<cmo> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.INT.fieldOf("bottom_offset").orElse(0).forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("top_offset").orElse(0).forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("maximum").orElse(0).forGetter(var0x -> var0x.e)
            )
            .apply(var0, cmo::new)
   );
   public final int c;
   public final int d;
   public final int e;

   public cmo(int var1, int var2, int var3) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }
}
