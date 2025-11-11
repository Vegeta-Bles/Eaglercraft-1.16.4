import java.util.EnumSet;

public class awv extends avv {
   private final aqn a;
   private final bdu b;
   private aqm c;
   private int d = -1;
   private final double e;
   private int f;
   private final int g;
   private final int h;
   private final float i;
   private final float j;

   public awv(bdu var1, double var2, int var4, float var5) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public awv(bdu var1, double var2, int var4, int var5, float var6) {
      if (!(_snowman instanceof aqm)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      } else {
         this.b = _snowman;
         this.a = (aqn)_snowman;
         this.e = _snowman;
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
         this.j = _snowman * _snowman;
         this.a(EnumSet.of(avv.a.a, avv.a.b));
      }
   }

   @Override
   public boolean a() {
      aqm _snowman = this.a.A();
      if (_snowman != null && _snowman.aX()) {
         this.c = _snowman;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b() {
      return this.a() || !this.a.x().m();
   }

   @Override
   public void d() {
      this.c = null;
      this.f = 0;
      this.d = -1;
   }

   @Override
   public void e() {
      double _snowman = this.a.h(this.c.cD(), this.c.cE(), this.c.cH());
      boolean _snowmanx = this.a.z().a(this.c);
      if (_snowmanx) {
         this.f++;
      } else {
         this.f = 0;
      }

      if (!(_snowman > (double)this.j) && this.f >= 5) {
         this.a.x().o();
      } else {
         this.a.x().a(this.c, this.e);
      }

      this.a.t().a(this.c, 30.0F, 30.0F);
      if (--this.d == 0) {
         if (!_snowmanx) {
            return;
         }

         float _snowmanxx = afm.a(_snowman) / this.i;
         float var5 = afm.a(_snowmanxx, 0.1F, 1.0F);
         this.b.a(this.c, var5);
         this.d = afm.d(_snowmanxx * (float)(this.h - this.g) + (float)this.g);
      } else if (this.d < 0) {
         float _snowmanxx = afm.a(_snowman) / this.i;
         this.d = afm.d(_snowmanxx * (float)(this.h - this.g) + (float)this.g);
      }
   }
}
