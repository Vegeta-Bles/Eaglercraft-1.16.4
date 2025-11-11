import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class bss {
   public static final Codec<bss> a = RecordCodecBuilder.create(
      var0 -> var0.group(adp.a.fieldOf("sound").forGetter(var0x -> var0x.b), Codec.DOUBLE.fieldOf("tick_chance").forGetter(var0x -> var0x.c))
            .apply(var0, bss::new)
   );
   private adp b;
   private double c;

   public bss(adp var1, double var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public adp a() {
      return this.b;
   }

   public double b() {
      return this.c;
   }
}
