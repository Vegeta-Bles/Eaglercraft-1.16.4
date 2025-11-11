import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class adn {
   public static final Codec<adn> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               adp.a.fieldOf("sound").forGetter(var0x -> var0x.b),
               Codec.INT.fieldOf("min_delay").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("max_delay").forGetter(var0x -> var0x.d),
               Codec.BOOL.fieldOf("replace_current_music").forGetter(var0x -> var0x.e)
            )
            .apply(var0, adn::new)
   );
   private final adp b;
   private final int c;
   private final int d;
   private final boolean e;

   public adn(adp var1, int var2, int var3, boolean var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public adp a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public boolean d() {
      return this.e;
   }
}
