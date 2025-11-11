public class h {
   private float a;
   private float b;
   private float c;
   private float d;

   public h() {
   }

   public h(float var1, float var2, float var3, float var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public h(g var1) {
      this(_snowman.a(), _snowman.b(), _snowman.c(), 1.0F);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         h _snowman = (h)_snowman;
         if (Float.compare(_snowman.a, this.a) != 0) {
            return false;
         } else if (Float.compare(_snowman.b, this.b) != 0) {
            return false;
         } else {
            return Float.compare(_snowman.c, this.c) != 0 ? false : Float.compare(_snowman.d, this.d) == 0;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = Float.floatToIntBits(this.a);
      _snowman = 31 * _snowman + Float.floatToIntBits(this.b);
      _snowman = 31 * _snowman + Float.floatToIntBits(this.c);
      return 31 * _snowman + Float.floatToIntBits(this.d);
   }

   public float a() {
      return this.a;
   }

   public float b() {
      return this.b;
   }

   public float c() {
      return this.c;
   }

   public float d() {
      return this.d;
   }

   public void a(g var1) {
      this.a = this.a * _snowman.a();
      this.b = this.b * _snowman.b();
      this.c = this.c * _snowman.c();
   }

   public void a(float var1, float var2, float var3, float var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public float a(h var1) {
      return this.a * _snowman.a + this.b * _snowman.b + this.c * _snowman.c + this.d * _snowman.d;
   }

   public boolean e() {
      float _snowman = this.a * this.a + this.b * this.b + this.c * this.c + this.d * this.d;
      if ((double)_snowman < 1.0E-5) {
         return false;
      } else {
         float _snowmanx = afm.i(_snowman);
         this.a *= _snowmanx;
         this.b *= _snowmanx;
         this.c *= _snowmanx;
         this.d *= _snowmanx;
         return true;
      }
   }

   public void a(b var1) {
      float _snowman = this.a;
      float _snowmanx = this.b;
      float _snowmanxx = this.c;
      float _snowmanxxx = this.d;
      this.a = _snowman.a * _snowman + _snowman.b * _snowmanx + _snowman.c * _snowmanxx + _snowman.d * _snowmanxxx;
      this.b = _snowman.e * _snowman + _snowman.f * _snowmanx + _snowman.g * _snowmanxx + _snowman.h * _snowmanxxx;
      this.c = _snowman.i * _snowman + _snowman.j * _snowmanx + _snowman.k * _snowmanxx + _snowman.l * _snowmanxxx;
      this.d = _snowman.m * _snowman + _snowman.n * _snowmanx + _snowman.o * _snowmanxx + _snowman.p * _snowmanxxx;
   }

   public void a(d var1) {
      d _snowman = new d(_snowman);
      _snowman.a(new d(this.a(), this.b(), this.c(), 0.0F));
      d _snowmanx = new d(_snowman);
      _snowmanx.e();
      _snowman.a(_snowmanx);
      this.a(_snowman.a(), _snowman.b(), _snowman.c(), this.d());
   }

   public void f() {
      this.a = this.a / this.d;
      this.b = this.b / this.d;
      this.c = this.c / this.d;
      this.d = 1.0F;
   }

   @Override
   public String toString() {
      return "[" + this.a + ", " + this.b + ", " + this.c + ", " + this.d + "]";
   }
}
