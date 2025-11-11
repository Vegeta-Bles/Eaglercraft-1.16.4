import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class bst {
   public static final Codec<bst> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               adp.a.fieldOf("sound").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("tick_delay").forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("block_search_extent").forGetter(var0x -> var0x.e),
               Codec.DOUBLE.fieldOf("offset").forGetter(var0x -> var0x.f)
            )
            .apply(var0, bst::new)
   );
   public static final bst b = new bst(adq.a, 6000, 8, 2.0);
   private adp c;
   private int d;
   private int e;
   private double f;

   public bst(adp var1, int var2, int var3, double var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public adp a() {
      return this.c;
   }

   public int b() {
      return this.d;
   }

   public int c() {
      return this.e;
   }

   public double d() {
      return this.f;
   }
}
