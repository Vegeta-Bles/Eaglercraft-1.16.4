import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmj implements cma {
   public static final Codec<cmj> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               csu.c.fieldOf("target").forGetter(var0x -> var0x.b),
               ceh.b.fieldOf("state").forGetter(var0x -> var0x.d),
               Codec.intRange(0, 64).fieldOf("size").forGetter(var0x -> var0x.c)
            )
            .apply(var0, cmj::new)
   );
   public final csu b;
   public final int c;
   public final ceh d;

   public cmj(csu var1, ceh var2, int var3) {
      this.c = _snowman;
      this.d = _snowman;
      this.b = _snowman;
   }

   public static final class a {
      public static final csu a = new ctc(aed.aH);
      public static final csu b = new csf(bup.cL);
      public static final csu c = new ctc(aed.aI);
   }
}
