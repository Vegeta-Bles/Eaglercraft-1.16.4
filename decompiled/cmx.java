import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cmx {
   public static final Codec<cmx> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, 1023).fieldOf("distance").forGetter(cmx::a),
               Codec.intRange(0, 1023).fieldOf("spread").forGetter(cmx::b),
               Codec.intRange(1, 4095).fieldOf("count").forGetter(cmx::c)
            )
            .apply(var0, cmx::new)
   );
   private final int b;
   private final int c;
   private final int d;

   public cmx(int var1, int var2, int var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public int a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }
}
