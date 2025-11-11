import java.util.EnumSet;

public class aww<T extends bdq & bdu> extends avv {
   private final T a;
   private final double b;
   private int c;
   private final float d;
   private int e = -1;
   private int f;
   private boolean g;
   private boolean h;
   private int i = -1;

   public aww(T var1, double var2, int var4, float var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman * _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
   }

   public void a(int var1) {
      this.c = _snowman;
   }

   @Override
   public boolean a() {
      return this.a.A() == null ? false : this.g();
   }

   protected boolean g() {
      return this.a.a(bmd.kc);
   }

   @Override
   public boolean b() {
      return (this.a() || !this.a.x().m()) && this.g();
   }

   @Override
   public void c() {
      super.c();
      this.a.s(true);
   }

   @Override
   public void d() {
      super.d();
      this.a.s(false);
      this.f = 0;
      this.e = -1;
      this.a.ec();
   }

   @Override
   public void e() {
      aqm _snowman = this.a.A();
      if (_snowman != null) {
         double _snowmanx = this.a.h(_snowman.cD(), _snowman.cE(), _snowman.cH());
         boolean _snowmanxx = this.a.z().a(_snowman);
         boolean _snowmanxxx = this.f > 0;
         if (_snowmanxx != _snowmanxxx) {
            this.f = 0;
         }

         if (_snowmanxx) {
            this.f++;
         } else {
            this.f--;
         }

         if (!(_snowmanx > (double)this.d) && this.f >= 20) {
            this.a.x().o();
            this.i++;
         } else {
            this.a.x().a(_snowman, this.b);
            this.i = -1;
         }

         if (this.i >= 20) {
            if ((double)this.a.cY().nextFloat() < 0.3) {
               this.g = !this.g;
            }

            if ((double)this.a.cY().nextFloat() < 0.3) {
               this.h = !this.h;
            }

            this.i = 0;
         }

         if (this.i > -1) {
            if (_snowmanx > (double)(this.d * 0.75F)) {
               this.h = false;
            } else if (_snowmanx < (double)(this.d * 0.25F)) {
               this.h = true;
            }

            this.a.u().a(this.h ? -0.5F : 0.5F, this.g ? 0.5F : -0.5F);
            this.a.a(_snowman, 30.0F, 30.0F);
         } else {
            this.a.t().a(_snowman, 30.0F, 30.0F);
         }

         if (this.a.dW()) {
            if (!_snowmanxx && this.f < -60) {
               this.a.ec();
            } else if (_snowmanxx) {
               int _snowmanxxxx = this.a.ea();
               if (_snowmanxxxx >= 20) {
                  this.a.ec();
                  this.a.a(_snowman, bkm.a(_snowmanxxxx));
                  this.e = this.c;
               }
            }
         } else if (--this.e <= 0 && this.f >= -60) {
            this.a.c(bgn.a(this.a, bmd.kc));
         }
      }
   }
}
