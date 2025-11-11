import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class chs {
   public static final Codec<chs> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.INT.fieldOf("target").forGetter(chs::a),
               Codec.intRange(0, 256).fieldOf("size").forGetter(chs::b),
               Codec.INT.fieldOf("offset").forGetter(chs::c)
            )
            .apply(var0, chs::new)
   );
   private final int b;
   private final int c;
   private final int d;

   public chs(int var1, int var2, int var3) {
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
