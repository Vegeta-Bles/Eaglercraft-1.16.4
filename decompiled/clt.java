import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class clt implements cma {
   public static final Codec<clt> a = RecordCodecBuilder.create(
      var0 -> var0.group(afw.a(0, 2, 1).fieldOf("reach").forGetter(var0x -> var0x.b), afw.a(1, 5, 5).fieldOf("height").forGetter(var0x -> var0x.c))
            .apply(var0, clt::new)
   );
   private final afw b;
   private final afw c;

   public clt(afw var1, afw var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public afw am_() {
      return this.b;
   }

   public afw b() {
      return this.c;
   }
}
