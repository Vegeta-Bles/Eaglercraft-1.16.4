import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmp implements cma {
   public static final Codec<cmp> a = RecordCodecBuilder.create(
      var0 -> var0.group(ceh.b.fieldOf("target").forGetter(var0x -> var0x.b), ceh.b.fieldOf("state").forGetter(var0x -> var0x.c)).apply(var0, cmp::new)
   );
   public final ceh b;
   public final ceh c;

   public cmp(ceh var1, ceh var2) {
      this.b = _snowman;
      this.c = _snowman;
   }
}
