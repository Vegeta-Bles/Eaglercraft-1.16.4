import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class avs extends avv {
   private final aqn a;
   private final Predicate<aqn> b;
   private aqn c;
   private final double d;
   private final ayj e;
   private int f;
   private final float g;
   private float h;
   private final float i;

   public avs(aqn var1, double var2, float var4, float var5) {
      this.a = _snowman;
      this.b = var1x -> var1x != null && _snowman.getClass() != var1x.getClass();
      this.d = _snowman;
      this.e = _snowman.x();
      this.g = _snowman;
      this.i = _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
      if (!(_snowman.x() instanceof ayi) && !(_snowman.x() instanceof ayh)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
      }
   }

   @Override
   public boolean a() {
      List<aqn> _snowman = this.a.l.a(aqn.class, this.a.cc().g((double)this.i), this.b);
      if (!_snowman.isEmpty()) {
         for (aqn _snowmanx : _snowman) {
            if (!_snowmanx.bF()) {
               this.c = _snowmanx;
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean b() {
      return this.c != null && !this.e.m() && this.a.h(this.c) > (double)(this.g * this.g);
   }

   @Override
   public void c() {
      this.f = 0;
      this.h = this.a.a(cwz.h);
      this.a.a(cwz.h, 0.0F);
   }

   @Override
   public void d() {
      this.c = null;
      this.e.o();
      this.a.a(cwz.h, this.h);
   }

   @Override
   public void e() {
      if (this.c != null && !this.a.eB()) {
         this.a.t().a(this.c, 10.0F, (float)this.a.O());
         if (--this.f <= 0) {
            this.f = 10;
            double _snowman = this.a.cD() - this.c.cD();
            double _snowmanx = this.a.cE() - this.c.cE();
            double _snowmanxx = this.a.cH() - this.c.cH();
            double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
            if (!(_snowmanxxx <= (double)(this.g * this.g))) {
               this.e.a(this.c, this.d);
            } else {
               this.e.o();
               ava _snowmanxxxx = this.c.t();
               if (_snowmanxxx <= (double)this.g || _snowmanxxxx.d() == this.a.cD() && _snowmanxxxx.e() == this.a.cE() && _snowmanxxxx.f() == this.a.cH()) {
                  double _snowmanxxxxx = this.c.cD() - this.a.cD();
                  double _snowmanxxxxxx = this.c.cH() - this.a.cH();
                  this.e.a(this.a.cD() - _snowmanxxxxx, this.a.cE(), this.a.cH() - _snowmanxxxxxx, this.d);
               }
            }
         }
      }
   }
}
