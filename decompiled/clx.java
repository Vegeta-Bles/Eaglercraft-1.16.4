import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class clx implements cma {
   public static final Codec<clx> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ceh.b.fieldOf("contents").forGetter(var0x -> var0x.b),
               ceh.b.fieldOf("rim").forGetter(var0x -> var0x.c),
               afw.a(0, 8, 8).fieldOf("size").forGetter(var0x -> var0x.d),
               afw.a(0, 8, 8).fieldOf("rim_size").forGetter(var0x -> var0x.e)
            )
            .apply(var0, clx::new)
   );
   private final ceh b;
   private final ceh c;
   private final afw d;
   private final afw e;

   public clx(ceh var1, ceh var2, afw var3, afw var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public ceh b() {
      return this.b;
   }

   public ceh c() {
      return this.c;
   }

   public afw d() {
      return this.d;
   }

   public afw e() {
      return this.e;
   }
}
