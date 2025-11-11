import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class bfk {
   private static final int[] b = new int[]{0, 10, 70, 150, 250};
   public static final Codec<bfk> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               gm.ah.fieldOf("type").orElseGet(() -> bfo.c).forGetter(var0x -> var0x.c),
               gm.ai.fieldOf("profession").orElseGet(() -> bfm.a).forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("level").orElse(1).forGetter(var0x -> var0x.e)
            )
            .apply(var0, bfk::new)
   );
   private final bfo c;
   private final bfm d;
   private final int e;

   public bfk(bfo var1, bfm var2, int var3) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = Math.max(1, _snowman);
   }

   public bfo a() {
      return this.c;
   }

   public bfm b() {
      return this.d;
   }

   public int c() {
      return this.e;
   }

   public bfk a(bfo var1) {
      return new bfk(_snowman, this.d, this.e);
   }

   public bfk a(bfm var1) {
      return new bfk(this.c, _snowman, this.e);
   }

   public bfk a(int var1) {
      return new bfk(this.c, this.d, _snowman);
   }

   public static int b(int var0) {
      return d(_snowman) ? b[_snowman - 1] : 0;
   }

   public static int c(int var0) {
      return d(_snowman) ? b[_snowman] : 0;
   }

   public static boolean d(int var0) {
      return _snowman >= 1 && _snowman < 5;
   }
}
