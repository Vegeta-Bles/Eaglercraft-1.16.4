import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmq implements cma {
   public static final Codec<cmq> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ceh.b.fieldOf("target").forGetter(var0x -> var0x.b),
               ceh.b.fieldOf("state").forGetter(var0x -> var0x.c),
               afw.a.fieldOf("radius").forGetter(var0x -> var0x.d)
            )
            .apply(var0, cmq::new)
   );
   public final ceh b;
   public final ceh c;
   private final afw d;

   public cmq(ceh var1, ceh var2, afw var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public afw b() {
      return this.d;
   }
}
