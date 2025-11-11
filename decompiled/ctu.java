import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ctu implements ctv {
   public static final Codec<ctu> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ceh.b.fieldOf("top_material").forGetter(var0x -> var0x.b),
               ceh.b.fieldOf("under_material").forGetter(var0x -> var0x.c),
               ceh.b.fieldOf("underwater_material").forGetter(var0x -> var0x.d)
            )
            .apply(var0, ctu::new)
   );
   private final ceh b;
   private final ceh c;
   private final ceh d;

   public ctu(ceh var1, ceh var2, ceh var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public ceh a() {
      return this.b;
   }

   @Override
   public ceh b() {
      return this.c;
   }

   public ceh c() {
      return this.d;
   }
}
