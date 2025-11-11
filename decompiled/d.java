public final class d {
   public static final d a = new d(0.0F, 0.0F, 0.0F, 1.0F);
   private float b;
   private float c;
   private float d;
   private float e;

   public d(float var1, float var2, float var3, float var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public d(g var1, float var2, boolean var3) {
      if (_snowman) {
         _snowman *= (float) (Math.PI / 180.0);
      }

      float _snowman = c(_snowman / 2.0F);
      this.b = _snowman.a() * _snowman;
      this.c = _snowman.b() * _snowman;
      this.d = _snowman.c() * _snowman;
      this.e = b(_snowman / 2.0F);
   }

   public d(float var1, float var2, float var3, boolean var4) {
      if (_snowman) {
         _snowman *= (float) (Math.PI / 180.0);
         _snowman *= (float) (Math.PI / 180.0);
         _snowman *= (float) (Math.PI / 180.0);
      }

      float _snowman = c(0.5F * _snowman);
      float _snowmanx = b(0.5F * _snowman);
      float _snowmanxx = c(0.5F * _snowman);
      float _snowmanxxx = b(0.5F * _snowman);
      float _snowmanxxxx = c(0.5F * _snowman);
      float _snowmanxxxxx = b(0.5F * _snowman);
      this.b = _snowman * _snowmanxxx * _snowmanxxxxx + _snowmanx * _snowmanxx * _snowmanxxxx;
      this.c = _snowmanx * _snowmanxx * _snowmanxxxxx - _snowman * _snowmanxxx * _snowmanxxxx;
      this.d = _snowman * _snowmanxx * _snowmanxxxxx + _snowmanx * _snowmanxxx * _snowmanxxxx;
      this.e = _snowmanx * _snowmanxxx * _snowmanxxxxx - _snowman * _snowmanxx * _snowmanxxxx;
   }

   public d(d var1) {
      this.b = _snowman.b;
      this.c = _snowman.c;
      this.d = _snowman.d;
      this.e = _snowman.e;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         d _snowman = (d)_snowman;
         if (Float.compare(_snowman.b, this.b) != 0) {
            return false;
         } else if (Float.compare(_snowman.c, this.c) != 0) {
            return false;
         } else {
            return Float.compare(_snowman.d, this.d) != 0 ? false : Float.compare(_snowman.e, this.e) == 0;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = Float.floatToIntBits(this.b);
      _snowman = 31 * _snowman + Float.floatToIntBits(this.c);
      _snowman = 31 * _snowman + Float.floatToIntBits(this.d);
      return 31 * _snowman + Float.floatToIntBits(this.e);
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("Quaternion[").append(this.d()).append(" + ");
      _snowman.append(this.a()).append("i + ");
      _snowman.append(this.b()).append("j + ");
      _snowman.append(this.c()).append("k]");
      return _snowman.toString();
   }

   public float a() {
      return this.b;
   }

   public float b() {
      return this.c;
   }

   public float c() {
      return this.d;
   }

   public float d() {
      return this.e;
   }

   public void a(d var1) {
      float _snowman = this.a();
      float _snowmanx = this.b();
      float _snowmanxx = this.c();
      float _snowmanxxx = this.d();
      float _snowmanxxxx = _snowman.a();
      float _snowmanxxxxx = _snowman.b();
      float _snowmanxxxxxx = _snowman.c();
      float _snowmanxxxxxxx = _snowman.d();
      this.b = _snowmanxxx * _snowmanxxxx + _snowman * _snowmanxxxxxxx + _snowmanx * _snowmanxxxxxx - _snowmanxx * _snowmanxxxxx;
      this.c = _snowmanxxx * _snowmanxxxxx - _snowman * _snowmanxxxxxx + _snowmanx * _snowmanxxxxxxx + _snowmanxx * _snowmanxxxx;
      this.d = _snowmanxxx * _snowmanxxxxxx + _snowman * _snowmanxxxxx - _snowmanx * _snowmanxxxx + _snowmanxx * _snowmanxxxxxxx;
      this.e = _snowmanxxx * _snowmanxxxxxxx - _snowman * _snowmanxxxx - _snowmanx * _snowmanxxxxx - _snowmanxx * _snowmanxxxxxx;
   }

   public void a(float var1) {
      this.b *= _snowman;
      this.c *= _snowman;
      this.d *= _snowman;
      this.e *= _snowman;
   }

   public void e() {
      this.b = -this.b;
      this.c = -this.c;
      this.d = -this.d;
   }

   public void a(float var1, float var2, float var3, float var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   private static float b(float var0) {
      return (float)Math.cos((double)_snowman);
   }

   private static float c(float var0) {
      return (float)Math.sin((double)_snowman);
   }

   public void f() {
      float _snowman = this.a() * this.a() + this.b() * this.b() + this.c() * this.c() + this.d() * this.d();
      if (_snowman > 1.0E-6F) {
         float _snowmanx = afm.i(_snowman);
         this.b *= _snowmanx;
         this.c *= _snowmanx;
         this.d *= _snowmanx;
         this.e *= _snowmanx;
      } else {
         this.b = 0.0F;
         this.c = 0.0F;
         this.d = 0.0F;
         this.e = 0.0F;
      }
   }

   public d g() {
      return new d(this);
   }
}
