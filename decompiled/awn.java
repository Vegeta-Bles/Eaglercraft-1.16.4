import java.util.EnumSet;

public class awn extends avv {
   private static final azg a = new azg().a(6.0).b().a();
   private final bai b;
   private bfj c;
   private int d;

   public awn(bai var1) {
      this.b = _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
   }

   @Override
   public boolean a() {
      if (!this.b.l.M()) {
         return false;
      } else if (this.b.cY().nextInt(8000) != 0) {
         return false;
      } else {
         this.c = this.b.l.a(bfj.class, a, this.b, this.b.cD(), this.b.cE(), this.b.cH(), this.b.cc().c(6.0, 2.0, 6.0));
         return this.c != null;
      }
   }

   @Override
   public boolean b() {
      return this.d > 0;
   }

   @Override
   public void c() {
      this.d = 400;
      this.b.t(true);
   }

   @Override
   public void d() {
      this.b.t(false);
      this.c = null;
   }

   @Override
   public void e() {
      this.b.t().a(this.c, 30.0F, 30.0F);
      this.d--;
   }
}
