import java.util.EnumSet;

public class awx<T extends bdq & bdu & bdd> extends avv {
   public static final afh a = new afh(20, 40);
   private final T b;
   private awx.a c = awx.a.a;
   private final double d;
   private final float e;
   private int f;
   private int g;
   private int h;

   public awx(T var1, double var2, float var4) {
      this.b = _snowman;
      this.d = _snowman;
      this.e = _snowman * _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
   }

   @Override
   public boolean a() {
      return this.h() && this.g();
   }

   private boolean g() {
      return this.b.a(bmd.qQ);
   }

   @Override
   public boolean b() {
      return this.h() && (this.a() || !this.b.x().m()) && this.g();
   }

   private boolean h() {
      return this.b.A() != null && this.b.A().aX();
   }

   @Override
   public void d() {
      super.d();
      this.b.s(false);
      this.b.h(null);
      this.f = 0;
      if (this.b.dW()) {
         this.b.ec();
         this.b.b(false);
         bkt.a(this.b.dY(), false);
      }
   }

   @Override
   public void e() {
      aqm _snowman = this.b.A();
      if (_snowman != null) {
         boolean _snowmanx = this.b.z().a(_snowman);
         boolean _snowmanxx = this.f > 0;
         if (_snowmanx != _snowmanxx) {
            this.f = 0;
         }

         if (_snowmanx) {
            this.f++;
         } else {
            this.f--;
         }

         double _snowmanxxx = this.b.h((aqa)_snowman);
         boolean _snowmanxxxx = (_snowmanxxx > (double)this.e || this.f < 5) && this.g == 0;
         if (_snowmanxxxx) {
            this.h--;
            if (this.h <= 0) {
               this.b.x().a(_snowman, this.j() ? this.d : this.d * 0.5);
               this.h = a.a(this.b.cY());
            }
         } else {
            this.h = 0;
            this.b.x().o();
         }

         this.b.t().a(_snowman, 30.0F, 30.0F);
         if (this.c == awx.a.a) {
            if (!_snowmanxxxx) {
               this.b.c(bgn.a(this.b, bmd.qQ));
               this.c = awx.a.b;
               this.b.b(true);
            }
         } else if (this.c == awx.a.b) {
            if (!this.b.dW()) {
               this.c = awx.a.a;
            }

            int _snowmanxxxxx = this.b.ea();
            bmb _snowmanxxxxxx = this.b.dY();
            if (_snowmanxxxxx >= bkt.g(_snowmanxxxxxx)) {
               this.b.eb();
               this.c = awx.a.c;
               this.g = 20 + this.b.cY().nextInt(20);
               this.b.b(false);
            }
         } else if (this.c == awx.a.c) {
            this.g--;
            if (this.g == 0) {
               this.c = awx.a.d;
            }
         } else if (this.c == awx.a.d && _snowmanx) {
            this.b.a(_snowman, 1.0F);
            bmb _snowmanxxxxx = this.b.b(bgn.a(this.b, bmd.qQ));
            bkt.a(_snowmanxxxxx, false);
            this.c = awx.a.a;
         }
      }
   }

   private boolean j() {
      return this.c == awx.a.a;
   }

   static enum a {
      a,
      b,
      c,
      d;

      private a() {
      }
   }
}
