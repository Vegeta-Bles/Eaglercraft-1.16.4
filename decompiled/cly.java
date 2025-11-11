import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class cly implements cma {
   public static final Codec<cly> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ceh.b.fieldOf("state").forGetter(var0x -> var0x.b),
               afw.a(0, 4, 4).fieldOf("radius").forGetter(var0x -> var0x.c),
               Codec.intRange(0, 4).fieldOf("half_height").forGetter(var0x -> var0x.d),
               ceh.b.listOf().fieldOf("targets").forGetter(var0x -> var0x.e)
            )
            .apply(var0, cly::new)
   );
   public final ceh b;
   public final afw c;
   public final int d;
   public final List<ceh> e;

   public cly(ceh var1, afw var2, int var3, List<ceh> var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }
}
