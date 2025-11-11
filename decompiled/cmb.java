import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmb implements cma {
   public static final Codec<cmb> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               cnt.a.fieldOf("cap_provider").forGetter(var0x -> var0x.b),
               cnt.a.fieldOf("stem_provider").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter(var0x -> var0x.d)
            )
            .apply(var0, cmb::new)
   );
   public final cnt b;
   public final cnt c;
   public final int d;

   public cmb(cnt var1, cnt var2, int var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }
}
