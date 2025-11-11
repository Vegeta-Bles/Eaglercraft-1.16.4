public class ava {
   protected final aqn a;
   protected float b;
   protected float c;
   protected boolean d;
   protected double e;
   protected double f;
   protected double g;

   public ava(aqn var1) {
      this.a = _snowman;
   }

   public void a(dcn var1) {
      this.a(_snowman.b, _snowman.c, _snowman.d);
   }

   public void a(aqa var1, float var2, float var3) {
      this.a(_snowman.cD(), b(_snowman), _snowman.cH(), _snowman, _snowman);
   }

   public void a(double var1, double var3, double var5) {
      this.a(_snowman, _snowman, _snowman, (float)this.a.ep(), (float)this.a.O());
   }

   public void a(double var1, double var3, double var5, float var7, float var8) {
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = true;
   }

   public void a() {
      if (this.b()) {
         this.a.q = 0.0F;
      }

      if (this.d) {
         this.d = false;
         this.a.aC = this.a(this.a.aC, this.h(), this.b);
         this.a.q = this.a(this.a.q, this.g(), this.c);
      } else {
         this.a.aC = this.a(this.a.aC, this.a.aA, 10.0F);
      }

      if (!this.a.x().m()) {
         this.a.aC = afm.b(this.a.aC, this.a.aA, (float)this.a.Q());
      }
   }

   protected boolean b() {
      return true;
   }

   public boolean c() {
      return this.d;
   }

   public double d() {
      return this.e;
   }

   public double e() {
      return this.f;
   }

   public double f() {
      return this.g;
   }

   protected float g() {
      double _snowman = this.e - this.a.cD();
      double _snowmanx = this.f - this.a.cG();
      double _snowmanxx = this.g - this.a.cH();
      double _snowmanxxx = (double)afm.a(_snowman * _snowman + _snowmanxx * _snowmanxx);
      return (float)(-(afm.d(_snowmanx, _snowmanxxx) * 180.0F / (float)Math.PI));
   }

   protected float h() {
      double _snowman = this.e - this.a.cD();
      double _snowmanx = this.g - this.a.cH();
      return (float)(afm.d(_snowmanx, _snowman) * 180.0F / (float)Math.PI) - 90.0F;
   }

   protected float a(float var1, float var2, float var3) {
      float _snowman = afm.c(_snowman, _snowman);
      float _snowmanx = afm.a(_snowman, -_snowman, _snowman);
      return _snowman + _snowmanx;
   }

   private static double b(aqa var0) {
      return _snowman instanceof aqm ? _snowman.cG() : (_snowman.cc().b + _snowman.cc().e) / 2.0;
   }
}
