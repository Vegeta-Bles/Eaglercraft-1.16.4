public class auu {
   private final aqn a;
   private int b;
   private float c;

   public auu(aqn var1) {
      this.a = _snowman;
   }

   public void a() {
      if (this.f()) {
         this.a.aA = this.a.p;
         this.c();
         this.c = this.a.aC;
         this.b = 0;
      } else {
         if (this.e()) {
            if (Math.abs(this.a.aC - this.c) > 15.0F) {
               this.b = 0;
               this.c = this.a.aC;
               this.b();
            } else {
               this.b++;
               if (this.b > 10) {
                  this.d();
               }
            }
         }
      }
   }

   private void b() {
      this.a.aA = afm.b(this.a.aA, this.a.aC, (float)this.a.Q());
   }

   private void c() {
      this.a.aC = afm.b(this.a.aC, this.a.aA, (float)this.a.Q());
   }

   private void d() {
      int _snowman = this.b - 10;
      float _snowmanx = afm.a((float)_snowman / 10.0F, 0.0F, 1.0F);
      float _snowmanxx = (float)this.a.Q() * (1.0F - _snowmanx);
      this.a.aA = afm.b(this.a.aA, this.a.aC, _snowmanxx);
   }

   private boolean e() {
      return this.a.cn().isEmpty() || !(this.a.cn().get(0) instanceof aqn);
   }

   private boolean f() {
      double _snowman = this.a.cD() - this.a.m;
      double _snowmanx = this.a.cH() - this.a.o;
      return _snowman * _snowman + _snowmanx * _snowmanx > 2.5000003E-7F;
   }
}
