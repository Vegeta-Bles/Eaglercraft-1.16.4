import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class dkf extends dkc {
   protected final float Y;
   protected final double Z;
   protected double aa;
   private final Function<dkd, Double> ab;
   private final BiConsumer<dkd, Double> ac;
   private final BiFunction<dkd, dkf, nr> ad;

   public dkf(String var1, double var2, double var4, float var6, Function<dkd, Double> var7, BiConsumer<dkd, Double> var8, BiFunction<dkd, dkf, nr> var9) {
      super(_snowman);
      this.Z = _snowman;
      this.aa = _snowman;
      this.Y = _snowman;
      this.ab = _snowman;
      this.ac = _snowman;
      this.ad = _snowman;
   }

   @Override
   public dlh a(dkd var1, int var2, int var3, int var4) {
      return new dlz(_snowman, _snowman, _snowman, _snowman, 20, this);
   }

   public double a(double var1) {
      return afm.a((this.d(_snowman) - this.Z) / (this.aa - this.Z), 0.0, 1.0);
   }

   public double b(double var1) {
      return this.d(afm.d(afm.a(_snowman, 0.0, 1.0), this.Z, this.aa));
   }

   private double d(double var1) {
      if (this.Y > 0.0F) {
         _snowman = (double)(this.Y * (float)Math.round(_snowman / (double)this.Y));
      }

      return afm.a(_snowman, this.Z, this.aa);
   }

   public double c() {
      return this.Z;
   }

   public double d() {
      return this.aa;
   }

   public void a(float var1) {
      this.aa = (double)_snowman;
   }

   public void a(dkd var1, double var2) {
      this.ac.accept(_snowman, _snowman);
   }

   public double a(dkd var1) {
      return this.ab.apply(_snowman);
   }

   public nr c(dkd var1) {
      return this.ad.apply(_snowman, this);
   }
}
