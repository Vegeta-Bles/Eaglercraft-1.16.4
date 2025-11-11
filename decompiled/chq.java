import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class chq {
   private static final Codec<Double> b = Codec.doubleRange(0.001, 1000.0);
   public static final Codec<chq> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               b.fieldOf("xz_scale").forGetter(chq::a),
               b.fieldOf("y_scale").forGetter(chq::b),
               b.fieldOf("xz_factor").forGetter(chq::c),
               b.fieldOf("y_factor").forGetter(chq::d)
            )
            .apply(var0, chq::new)
   );
   private final double c;
   private final double d;
   private final double e;
   private final double f;

   public chq(double var1, double var3, double var5, double var7) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public double a() {
      return this.c;
   }

   public double b() {
      return this.d;
   }

   public double c() {
      return this.e;
   }

   public double d() {
      return this.f;
   }
}
