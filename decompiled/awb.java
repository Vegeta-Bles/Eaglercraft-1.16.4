import java.util.EnumSet;

public class awb extends avv {
   private final aqn a;
   private aqm b;
   private final float c;

   public awb(aqn var1, float var2) {
      this.a = _snowman;
      this.c = _snowman;
      this.a(EnumSet.of(avv.a.c, avv.a.a));
   }

   @Override
   public boolean a() {
      if (this.a.bs()) {
         return false;
      } else {
         this.b = this.a.A();
         if (this.b == null) {
            return false;
         } else {
            double _snowman = this.a.h((aqa)this.b);
            if (_snowman < 4.0 || _snowman > 16.0) {
               return false;
            } else {
               return !this.a.ao() ? false : this.a.cY().nextInt(5) == 0;
            }
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a.ao();
   }

   @Override
   public void c() {
      dcn _snowman = this.a.cC();
      dcn _snowmanx = new dcn(this.b.cD() - this.a.cD(), 0.0, this.b.cH() - this.a.cH());
      if (_snowmanx.g() > 1.0E-7) {
         _snowmanx = _snowmanx.d().a(0.4).e(_snowman.a(0.2));
      }

      this.a.n(_snowmanx.b, (double)this.c, _snowmanx.d);
   }
}
