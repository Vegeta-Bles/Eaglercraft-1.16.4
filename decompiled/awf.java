import java.util.EnumSet;

public class awf extends avv {
   protected final aqu a;
   private final double b;
   private final boolean c;
   private cxd d;
   private double e;
   private double f;
   private double g;
   private int h;
   private int i;
   private final int j = 20;
   private long k;

   public awf(aqu var1, double var2, boolean var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.a(EnumSet.of(avv.a.a, avv.a.b));
   }

   @Override
   public boolean a() {
      long _snowman = this.a.l.T();
      if (_snowman - this.k < 20L) {
         return false;
      } else {
         this.k = _snowman;
         aqm _snowmanx = this.a.A();
         if (_snowmanx == null) {
            return false;
         } else if (!_snowmanx.aX()) {
            return false;
         } else {
            this.d = this.a.x().a(_snowmanx, 0);
            return this.d != null ? true : this.a(_snowmanx) >= this.a.h(_snowmanx.cD(), _snowmanx.cE(), _snowmanx.cH());
         }
      }
   }

   @Override
   public boolean b() {
      aqm _snowman = this.a.A();
      if (_snowman == null) {
         return false;
      } else if (!_snowman.aX()) {
         return false;
      } else if (!this.c) {
         return !this.a.x().m();
      } else {
         return !this.a.a(_snowman.cB()) ? false : !(_snowman instanceof bfw) || !_snowman.a_() && !((bfw)_snowman).b_();
      }
   }

   @Override
   public void c() {
      this.a.x().a(this.d, this.b);
      this.a.s(true);
      this.h = 0;
      this.i = 0;
   }

   @Override
   public void d() {
      aqm _snowman = this.a.A();
      if (!aqd.e.test(_snowman)) {
         this.a.h(null);
      }

      this.a.s(false);
      this.a.x().o();
   }

   @Override
   public void e() {
      aqm _snowman = this.a.A();
      this.a.t().a(_snowman, 30.0F, 30.0F);
      double _snowmanx = this.a.h(_snowman.cD(), _snowman.cE(), _snowman.cH());
      this.h = Math.max(this.h - 1, 0);
      if ((this.c || this.a.z().a(_snowman))
         && this.h <= 0
         && (this.e == 0.0 && this.f == 0.0 && this.g == 0.0 || _snowman.h(this.e, this.f, this.g) >= 1.0 || this.a.cY().nextFloat() < 0.05F)) {
         this.e = _snowman.cD();
         this.f = _snowman.cE();
         this.g = _snowman.cH();
         this.h = 4 + this.a.cY().nextInt(7);
         if (_snowmanx > 1024.0) {
            this.h += 10;
         } else if (_snowmanx > 256.0) {
            this.h += 5;
         }

         if (!this.a.x().a(_snowman, this.b)) {
            this.h += 15;
         }
      }

      this.i = Math.max(this.i - 1, 0);
      this.a(_snowman, _snowmanx);
   }

   protected void a(aqm var1, double var2) {
      double _snowman = this.a(_snowman);
      if (_snowman <= _snowman && this.i <= 0) {
         this.g();
         this.a.a(aot.a);
         this.a.B(_snowman);
      }
   }

   protected void g() {
      this.i = 20;
   }

   protected boolean h() {
      return this.i <= 0;
   }

   protected int j() {
      return this.i;
   }

   protected int k() {
      return 20;
   }

   protected double a(aqm var1) {
      return (double)(this.a.cy() * 2.0F * this.a.cy() * 2.0F + _snowman.cy());
   }
}
