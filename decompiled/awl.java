import java.util.EnumSet;

public class awl extends avv {
   private final aqu a;
   private aqm b;
   private double c;
   private double d;
   private double e;
   private final double f;
   private final float g;

   public awl(aqu var1, double var2, float var4) {
      this.a = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      this.b = this.a.A();
      if (this.b == null) {
         return false;
      } else if (this.b.h(this.a) > (double)(this.g * this.g)) {
         return false;
      } else {
         dcn _snowman = azj.b(this.a, 16, 7, this.b.cA());
         if (_snowman == null) {
            return false;
         } else {
            this.c = _snowman.b;
            this.d = _snowman.c;
            this.e = _snowman.d;
            return true;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a.x().m() && this.b.aX() && this.b.h(this.a) < (double)(this.g * this.g);
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void c() {
      this.a.x().a(this.c, this.d, this.e, this.f);
   }
}
