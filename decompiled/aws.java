import java.util.EnumSet;

public class aws extends avv {
   private final aqn a;
   private double b;
   private double c;
   private int d;

   public aws(aqn var1) {
      this.a = _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
   }

   @Override
   public boolean a() {
      return this.a.cY().nextFloat() < 0.02F;
   }

   @Override
   public boolean b() {
      return this.d >= 0;
   }

   @Override
   public void c() {
      double _snowman = (Math.PI * 2) * this.a.cY().nextDouble();
      this.b = Math.cos(_snowman);
      this.c = Math.sin(_snowman);
      this.d = 20 + this.a.cY().nextInt(20);
   }

   @Override
   public void e() {
      this.d--;
      this.a.t().a(this.a.cD() + this.b, this.a.cG(), this.a.cH() + this.c);
   }
}
