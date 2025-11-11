import java.util.EnumSet;

public class awm extends avv {
   private final brc a;
   private final aqn b;
   private aqm c;
   private int d;

   public awm(aqn var1) {
      this.b = _snowman;
      this.a = _snowman.l;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
   }

   @Override
   public boolean a() {
      aqm _snowman = this.b.A();
      if (_snowman == null) {
         return false;
      } else {
         this.c = _snowman;
         return true;
      }
   }

   @Override
   public boolean b() {
      if (!this.c.aX()) {
         return false;
      } else {
         return this.b.h((aqa)this.c) > 225.0 ? false : !this.b.x().m() || this.a();
      }
   }

   @Override
   public void d() {
      this.c = null;
      this.b.x().o();
   }

   @Override
   public void e() {
      this.b.t().a(this.c, 30.0F, 30.0F);
      double _snowman = (double)(this.b.cy() * 2.0F * this.b.cy() * 2.0F);
      double _snowmanx = this.b.h(this.c.cD(), this.c.cE(), this.c.cH());
      double _snowmanxx = 0.8;
      if (_snowmanx > _snowman && _snowmanx < 16.0) {
         _snowmanxx = 1.33;
      } else if (_snowmanx < 225.0) {
         _snowmanxx = 0.6;
      }

      this.b.x().a(this.c, _snowmanxx);
      this.d = Math.max(this.d - 1, 0);
      if (!(_snowmanx > _snowman)) {
         if (this.d <= 0) {
            this.d = 20;
            this.b.B(this.c);
         }
      }
   }
}
