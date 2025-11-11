import it.unimi.dsi.fastutil.floats.Float2FloatFunction;

public final class g {
   public static g a = new g(-1.0F, 0.0F, 0.0F);
   public static g b = new g(1.0F, 0.0F, 0.0F);
   public static g c = new g(0.0F, -1.0F, 0.0F);
   public static g d = new g(0.0F, 1.0F, 0.0F);
   public static g e = new g(0.0F, 0.0F, -1.0F);
   public static g f = new g(0.0F, 0.0F, 1.0F);
   private float g;
   private float h;
   private float i;

   public g() {
   }

   public g(float var1, float var2, float var3) {
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
   }

   public g(dcn var1) {
      this((float)_snowman.b, (float)_snowman.c, (float)_snowman.d);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         g _snowman = (g)_snowman;
         if (Float.compare(_snowman.g, this.g) != 0) {
            return false;
         } else {
            return Float.compare(_snowman.h, this.h) != 0 ? false : Float.compare(_snowman.i, this.i) == 0;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = Float.floatToIntBits(this.g);
      _snowman = 31 * _snowman + Float.floatToIntBits(this.h);
      return 31 * _snowman + Float.floatToIntBits(this.i);
   }

   public float a() {
      return this.g;
   }

   public float b() {
      return this.h;
   }

   public float c() {
      return this.i;
   }

   public void b(float var1) {
      this.g *= _snowman;
      this.h *= _snowman;
      this.i *= _snowman;
   }

   public void b(float var1, float var2, float var3) {
      this.g *= _snowman;
      this.h *= _snowman;
      this.i *= _snowman;
   }

   public void a(float var1, float var2) {
      this.g = afm.a(this.g, _snowman, _snowman);
      this.h = afm.a(this.h, _snowman, _snowman);
      this.i = afm.a(this.i, _snowman, _snowman);
   }

   public void a(float var1, float var2, float var3) {
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
   }

   public void c(float var1, float var2, float var3) {
      this.g += _snowman;
      this.h += _snowman;
      this.i += _snowman;
   }

   public void a(g var1) {
      this.g = this.g + _snowman.g;
      this.h = this.h + _snowman.h;
      this.i = this.i + _snowman.i;
   }

   public void b(g var1) {
      this.g = this.g - _snowman.g;
      this.h = this.h - _snowman.h;
      this.i = this.i - _snowman.i;
   }

   public float c(g var1) {
      return this.g * _snowman.g + this.h * _snowman.h + this.i * _snowman.i;
   }

   public boolean d() {
      float _snowman = this.g * this.g + this.h * this.h + this.i * this.i;
      if ((double)_snowman < 1.0E-5) {
         return false;
      } else {
         float _snowmanx = afm.i(_snowman);
         this.g *= _snowmanx;
         this.h *= _snowmanx;
         this.i *= _snowmanx;
         return true;
      }
   }

   public void d(g var1) {
      float _snowman = this.g;
      float _snowmanx = this.h;
      float _snowmanxx = this.i;
      float _snowmanxxx = _snowman.a();
      float _snowmanxxxx = _snowman.b();
      float _snowmanxxxxx = _snowman.c();
      this.g = _snowmanx * _snowmanxxxxx - _snowmanxx * _snowmanxxxx;
      this.h = _snowmanxx * _snowmanxxx - _snowman * _snowmanxxxxx;
      this.i = _snowman * _snowmanxxxx - _snowmanx * _snowmanxxx;
   }

   public void a(a var1) {
      float _snowman = this.g;
      float _snowmanx = this.h;
      float _snowmanxx = this.i;
      this.g = _snowman.a * _snowman + _snowman.b * _snowmanx + _snowman.c * _snowmanxx;
      this.h = _snowman.d * _snowman + _snowman.e * _snowmanx + _snowman.f * _snowmanxx;
      this.i = _snowman.g * _snowman + _snowman.h * _snowmanx + _snowman.i * _snowmanxx;
   }

   public void a(d var1) {
      d _snowman = new d(_snowman);
      _snowman.a(new d(this.a(), this.b(), this.c(), 0.0F));
      d _snowmanx = new d(_snowman);
      _snowmanx.e();
      _snowman.a(_snowmanx);
      this.a(_snowman.a(), _snowman.b(), _snowman.c());
   }

   public void a(g var1, float var2) {
      float _snowman = 1.0F - _snowman;
      this.g = this.g * _snowman + _snowman.g * _snowman;
      this.h = this.h * _snowman + _snowman.h * _snowman;
      this.i = this.i * _snowman + _snowman.i * _snowman;
   }

   public d c(float var1) {
      return new d(this, _snowman, false);
   }

   public d a(float var1) {
      return new d(this, _snowman, true);
   }

   public g e() {
      return new g(this.g, this.h, this.i);
   }

   public void a(Float2FloatFunction var1) {
      this.g = _snowman.get(this.g);
      this.h = _snowman.get(this.h);
      this.i = _snowman.get(this.i);
   }

   @Override
   public String toString() {
      return "[" + this.g + ", " + this.h + ", " + this.i + "]";
   }
}
