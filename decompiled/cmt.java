import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;

public class cmt implements cma {
   public static final Codec<cmt> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ceh.b.fieldOf("to_place").forGetter(var0x -> var0x.b),
               ceh.b.listOf().fieldOf("place_on").forGetter(var0x -> var0x.c),
               ceh.b.listOf().fieldOf("place_in").forGetter(var0x -> var0x.d),
               ceh.b.listOf().fieldOf("place_under").forGetter(var0x -> var0x.e)
            )
            .apply(var0, cmt::new)
   );
   public final ceh b;
   public final List<ceh> c;
   public final List<ceh> d;
   public final List<ceh> e;

   public cmt(ceh var1, List<ceh> var2, List<ceh> var3, List<ceh> var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }
}
