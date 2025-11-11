import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cpw implements clw {
   public static final Codec<cpw> a = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.INT.fieldOf("baseline").forGetter(var0x -> var0x.c), Codec.INT.fieldOf("spread").forGetter(var0x -> var0x.d))
            .apply(var0, cpw::new)
   );
   public final int c;
   public final int d;

   public cpw(int var1, int var2) {
      this.c = _snowman;
      this.d = _snowman;
   }
}
