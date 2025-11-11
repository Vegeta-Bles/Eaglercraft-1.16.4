import java.nio.FloatBuffer;

public final class b {
   protected float a;
   protected float b;
   protected float c;
   protected float d;
   protected float e;
   protected float f;
   protected float g;
   protected float h;
   protected float i;
   protected float j;
   protected float k;
   protected float l;
   protected float m;
   protected float n;
   protected float o;
   protected float p;

   public b() {
   }

   public b(b var1) {
      this.a = _snowman.a;
      this.b = _snowman.b;
      this.c = _snowman.c;
      this.d = _snowman.d;
      this.e = _snowman.e;
      this.f = _snowman.f;
      this.g = _snowman.g;
      this.h = _snowman.h;
      this.i = _snowman.i;
      this.j = _snowman.j;
      this.k = _snowman.k;
      this.l = _snowman.l;
      this.m = _snowman.m;
      this.n = _snowman.n;
      this.o = _snowman.o;
      this.p = _snowman.p;
   }

   public b(d var1) {
      float _snowman = _snowman.a();
      float _snowmanx = _snowman.b();
      float _snowmanxx = _snowman.c();
      float _snowmanxxx = _snowman.d();
      float _snowmanxxxx = 2.0F * _snowman * _snowman;
      float _snowmanxxxxx = 2.0F * _snowmanx * _snowmanx;
      float _snowmanxxxxxx = 2.0F * _snowmanxx * _snowmanxx;
      this.a = 1.0F - _snowmanxxxxx - _snowmanxxxxxx;
      this.f = 1.0F - _snowmanxxxxxx - _snowmanxxxx;
      this.k = 1.0F - _snowmanxxxx - _snowmanxxxxx;
      this.p = 1.0F;
      float _snowmanxxxxxxx = _snowman * _snowmanx;
      float _snowmanxxxxxxxx = _snowmanx * _snowmanxx;
      float _snowmanxxxxxxxxx = _snowmanxx * _snowman;
      float _snowmanxxxxxxxxxx = _snowman * _snowmanxxx;
      float _snowmanxxxxxxxxxxx = _snowmanx * _snowmanxxx;
      float _snowmanxxxxxxxxxxxx = _snowmanxx * _snowmanxxx;
      this.e = 2.0F * (_snowmanxxxxxxx + _snowmanxxxxxxxxxxxx);
      this.b = 2.0F * (_snowmanxxxxxxx - _snowmanxxxxxxxxxxxx);
      this.i = 2.0F * (_snowmanxxxxxxxxx - _snowmanxxxxxxxxxxx);
      this.c = 2.0F * (_snowmanxxxxxxxxx + _snowmanxxxxxxxxxxx);
      this.j = 2.0F * (_snowmanxxxxxxxx + _snowmanxxxxxxxxxx);
      this.g = 2.0F * (_snowmanxxxxxxxx - _snowmanxxxxxxxxxx);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         b _snowman = (b)_snowman;
         return Float.compare(_snowman.a, this.a) == 0
            && Float.compare(_snowman.b, this.b) == 0
            && Float.compare(_snowman.c, this.c) == 0
            && Float.compare(_snowman.d, this.d) == 0
            && Float.compare(_snowman.e, this.e) == 0
            && Float.compare(_snowman.f, this.f) == 0
            && Float.compare(_snowman.g, this.g) == 0
            && Float.compare(_snowman.h, this.h) == 0
            && Float.compare(_snowman.i, this.i) == 0
            && Float.compare(_snowman.j, this.j) == 0
            && Float.compare(_snowman.k, this.k) == 0
            && Float.compare(_snowman.l, this.l) == 0
            && Float.compare(_snowman.m, this.m) == 0
            && Float.compare(_snowman.n, this.n) == 0
            && Float.compare(_snowman.o, this.o) == 0
            && Float.compare(_snowman.p, this.p) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.a != 0.0F ? Float.floatToIntBits(this.a) : 0;
      _snowman = 31 * _snowman + (this.b != 0.0F ? Float.floatToIntBits(this.b) : 0);
      _snowman = 31 * _snowman + (this.c != 0.0F ? Float.floatToIntBits(this.c) : 0);
      _snowman = 31 * _snowman + (this.d != 0.0F ? Float.floatToIntBits(this.d) : 0);
      _snowman = 31 * _snowman + (this.e != 0.0F ? Float.floatToIntBits(this.e) : 0);
      _snowman = 31 * _snowman + (this.f != 0.0F ? Float.floatToIntBits(this.f) : 0);
      _snowman = 31 * _snowman + (this.g != 0.0F ? Float.floatToIntBits(this.g) : 0);
      _snowman = 31 * _snowman + (this.h != 0.0F ? Float.floatToIntBits(this.h) : 0);
      _snowman = 31 * _snowman + (this.i != 0.0F ? Float.floatToIntBits(this.i) : 0);
      _snowman = 31 * _snowman + (this.j != 0.0F ? Float.floatToIntBits(this.j) : 0);
      _snowman = 31 * _snowman + (this.k != 0.0F ? Float.floatToIntBits(this.k) : 0);
      _snowman = 31 * _snowman + (this.l != 0.0F ? Float.floatToIntBits(this.l) : 0);
      _snowman = 31 * _snowman + (this.m != 0.0F ? Float.floatToIntBits(this.m) : 0);
      _snowman = 31 * _snowman + (this.n != 0.0F ? Float.floatToIntBits(this.n) : 0);
      _snowman = 31 * _snowman + (this.o != 0.0F ? Float.floatToIntBits(this.o) : 0);
      return 31 * _snowman + (this.p != 0.0F ? Float.floatToIntBits(this.p) : 0);
   }

   private static int a(int var0, int var1) {
      return _snowman * 4 + _snowman;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("Matrix4f:\n");
      _snowman.append(this.a);
      _snowman.append(" ");
      _snowman.append(this.b);
      _snowman.append(" ");
      _snowman.append(this.c);
      _snowman.append(" ");
      _snowman.append(this.d);
      _snowman.append("\n");
      _snowman.append(this.e);
      _snowman.append(" ");
      _snowman.append(this.f);
      _snowman.append(" ");
      _snowman.append(this.g);
      _snowman.append(" ");
      _snowman.append(this.h);
      _snowman.append("\n");
      _snowman.append(this.i);
      _snowman.append(" ");
      _snowman.append(this.j);
      _snowman.append(" ");
      _snowman.append(this.k);
      _snowman.append(" ");
      _snowman.append(this.l);
      _snowman.append("\n");
      _snowman.append(this.m);
      _snowman.append(" ");
      _snowman.append(this.n);
      _snowman.append(" ");
      _snowman.append(this.o);
      _snowman.append(" ");
      _snowman.append(this.p);
      _snowman.append("\n");
      return _snowman.toString();
   }

   public void a(FloatBuffer var1) {
      _snowman.put(a(0, 0), this.a);
      _snowman.put(a(0, 1), this.b);
      _snowman.put(a(0, 2), this.c);
      _snowman.put(a(0, 3), this.d);
      _snowman.put(a(1, 0), this.e);
      _snowman.put(a(1, 1), this.f);
      _snowman.put(a(1, 2), this.g);
      _snowman.put(a(1, 3), this.h);
      _snowman.put(a(2, 0), this.i);
      _snowman.put(a(2, 1), this.j);
      _snowman.put(a(2, 2), this.k);
      _snowman.put(a(2, 3), this.l);
      _snowman.put(a(3, 0), this.m);
      _snowman.put(a(3, 1), this.n);
      _snowman.put(a(3, 2), this.o);
      _snowman.put(a(3, 3), this.p);
   }

   public void a() {
      this.a = 1.0F;
      this.b = 0.0F;
      this.c = 0.0F;
      this.d = 0.0F;
      this.e = 0.0F;
      this.f = 1.0F;
      this.g = 0.0F;
      this.h = 0.0F;
      this.i = 0.0F;
      this.j = 0.0F;
      this.k = 1.0F;
      this.l = 0.0F;
      this.m = 0.0F;
      this.n = 0.0F;
      this.o = 0.0F;
      this.p = 1.0F;
   }

   public float b() {
      float _snowman = this.a * this.f - this.b * this.e;
      float _snowmanx = this.a * this.g - this.c * this.e;
      float _snowmanxx = this.a * this.h - this.d * this.e;
      float _snowmanxxx = this.b * this.g - this.c * this.f;
      float _snowmanxxxx = this.b * this.h - this.d * this.f;
      float _snowmanxxxxx = this.c * this.h - this.d * this.g;
      float _snowmanxxxxxx = this.i * this.n - this.j * this.m;
      float _snowmanxxxxxxx = this.i * this.o - this.k * this.m;
      float _snowmanxxxxxxxx = this.i * this.p - this.l * this.m;
      float _snowmanxxxxxxxxx = this.j * this.o - this.k * this.n;
      float _snowmanxxxxxxxxxx = this.j * this.p - this.l * this.n;
      float _snowmanxxxxxxxxxxx = this.k * this.p - this.l * this.o;
      float _snowmanxxxxxxxxxxxx = this.f * _snowmanxxxxxxxxxxx - this.g * _snowmanxxxxxxxxxx + this.h * _snowmanxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxx = -this.e * _snowmanxxxxxxxxxxx + this.g * _snowmanxxxxxxxx - this.h * _snowmanxxxxxxx;
      float _snowmanxxxxxxxxxxxxxx = this.e * _snowmanxxxxxxxxxx - this.f * _snowmanxxxxxxxx + this.h * _snowmanxxxxxx;
      float _snowmanxxxxxxxxxxxxxxx = -this.e * _snowmanxxxxxxxxx + this.f * _snowmanxxxxxxx - this.g * _snowmanxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxx = -this.b * _snowmanxxxxxxxxxxx + this.c * _snowmanxxxxxxxxxx - this.d * _snowmanxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxx = this.a * _snowmanxxxxxxxxxxx - this.c * _snowmanxxxxxxxx + this.d * _snowmanxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxx = -this.a * _snowmanxxxxxxxxxx + this.b * _snowmanxxxxxxxx - this.d * _snowmanxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxx = this.a * _snowmanxxxxxxxxx - this.b * _snowmanxxxxxxx + this.c * _snowmanxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxx = this.n * _snowmanxxxxx - this.o * _snowmanxxxx + this.p * _snowmanxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxx = -this.m * _snowmanxxxxx + this.o * _snowmanxx - this.p * _snowmanx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxx = this.m * _snowmanxxxx - this.n * _snowmanxx + this.p * _snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = -this.m * _snowmanxxx + this.n * _snowmanx - this.o * _snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = -this.j * _snowmanxxxxx + this.k * _snowmanxxxx - this.l * _snowmanxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.i * _snowmanxxxxx - this.k * _snowmanxx + this.l * _snowmanx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = -this.i * _snowmanxxxx + this.j * _snowmanxx - this.l * _snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.i * _snowmanxxx - this.j * _snowmanx + this.k * _snowman;
      this.a = _snowmanxxxxxxxxxxxx;
      this.e = _snowmanxxxxxxxxxxxxx;
      this.i = _snowmanxxxxxxxxxxxxxx;
      this.m = _snowmanxxxxxxxxxxxxxxx;
      this.b = _snowmanxxxxxxxxxxxxxxxx;
      this.f = _snowmanxxxxxxxxxxxxxxxxx;
      this.j = _snowmanxxxxxxxxxxxxxxxxxx;
      this.n = _snowmanxxxxxxxxxxxxxxxxxxx;
      this.c = _snowmanxxxxxxxxxxxxxxxxxxxx;
      this.g = _snowmanxxxxxxxxxxxxxxxxxxxxx;
      this.k = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
      this.o = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.d = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      this.h = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
      this.l = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
      this.p = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      return _snowman * _snowmanxxxxxxxxxxx - _snowmanx * _snowmanxxxxxxxxxx + _snowmanxx * _snowmanxxxxxxxxx + _snowmanxxx * _snowmanxxxxxxxx - _snowmanxxxx * _snowmanxxxxxxx + _snowmanxxxxx * _snowmanxxxxxx;
   }

   public void e() {
      float _snowman = this.e;
      this.e = this.b;
      this.b = _snowman;
      _snowman = this.i;
      this.i = this.c;
      this.c = _snowman;
      _snowman = this.j;
      this.j = this.g;
      this.g = _snowman;
      _snowman = this.m;
      this.m = this.d;
      this.d = _snowman;
      _snowman = this.n;
      this.n = this.h;
      this.h = _snowman;
      _snowman = this.o;
      this.o = this.l;
      this.l = _snowman;
   }

   public boolean c() {
      float _snowman = this.b();
      if (Math.abs(_snowman) > 1.0E-6F) {
         this.a(_snowman);
         return true;
      } else {
         return false;
      }
   }

   public void a(b var1) {
      float _snowman = this.a * _snowman.a + this.b * _snowman.e + this.c * _snowman.i + this.d * _snowman.m;
      float _snowmanx = this.a * _snowman.b + this.b * _snowman.f + this.c * _snowman.j + this.d * _snowman.n;
      float _snowmanxx = this.a * _snowman.c + this.b * _snowman.g + this.c * _snowman.k + this.d * _snowman.o;
      float _snowmanxxx = this.a * _snowman.d + this.b * _snowman.h + this.c * _snowman.l + this.d * _snowman.p;
      float _snowmanxxxx = this.e * _snowman.a + this.f * _snowman.e + this.g * _snowman.i + this.h * _snowman.m;
      float _snowmanxxxxx = this.e * _snowman.b + this.f * _snowman.f + this.g * _snowman.j + this.h * _snowman.n;
      float _snowmanxxxxxx = this.e * _snowman.c + this.f * _snowman.g + this.g * _snowman.k + this.h * _snowman.o;
      float _snowmanxxxxxxx = this.e * _snowman.d + this.f * _snowman.h + this.g * _snowman.l + this.h * _snowman.p;
      float _snowmanxxxxxxxx = this.i * _snowman.a + this.j * _snowman.e + this.k * _snowman.i + this.l * _snowman.m;
      float _snowmanxxxxxxxxx = this.i * _snowman.b + this.j * _snowman.f + this.k * _snowman.j + this.l * _snowman.n;
      float _snowmanxxxxxxxxxx = this.i * _snowman.c + this.j * _snowman.g + this.k * _snowman.k + this.l * _snowman.o;
      float _snowmanxxxxxxxxxxx = this.i * _snowman.d + this.j * _snowman.h + this.k * _snowman.l + this.l * _snowman.p;
      float _snowmanxxxxxxxxxxxx = this.m * _snowman.a + this.n * _snowman.e + this.o * _snowman.i + this.p * _snowman.m;
      float _snowmanxxxxxxxxxxxxx = this.m * _snowman.b + this.n * _snowman.f + this.o * _snowman.j + this.p * _snowman.n;
      float _snowmanxxxxxxxxxxxxxx = this.m * _snowman.c + this.n * _snowman.g + this.o * _snowman.k + this.p * _snowman.o;
      float _snowmanxxxxxxxxxxxxxxx = this.m * _snowman.d + this.n * _snowman.h + this.o * _snowman.l + this.p * _snowman.p;
      this.a = _snowman;
      this.b = _snowmanx;
      this.c = _snowmanxx;
      this.d = _snowmanxxx;
      this.e = _snowmanxxxx;
      this.f = _snowmanxxxxx;
      this.g = _snowmanxxxxxx;
      this.h = _snowmanxxxxxxx;
      this.i = _snowmanxxxxxxxx;
      this.j = _snowmanxxxxxxxxx;
      this.k = _snowmanxxxxxxxxxx;
      this.l = _snowmanxxxxxxxxxxx;
      this.m = _snowmanxxxxxxxxxxxx;
      this.n = _snowmanxxxxxxxxxxxxx;
      this.o = _snowmanxxxxxxxxxxxxxx;
      this.p = _snowmanxxxxxxxxxxxxxxx;
   }

   public void a(d var1) {
      this.a(new b(_snowman));
   }

   public void a(float var1) {
      this.a *= _snowman;
      this.b *= _snowman;
      this.c *= _snowman;
      this.d *= _snowman;
      this.e *= _snowman;
      this.f *= _snowman;
      this.g *= _snowman;
      this.h *= _snowman;
      this.i *= _snowman;
      this.j *= _snowman;
      this.k *= _snowman;
      this.l *= _snowman;
      this.m *= _snowman;
      this.n *= _snowman;
      this.o *= _snowman;
      this.p *= _snowman;
   }

   public static b a(double var0, float var2, float var3, float var4) {
      float _snowman = (float)(1.0 / Math.tan(_snowman * (float) (Math.PI / 180.0) / 2.0));
      b _snowmanx = new b();
      _snowmanx.a = _snowman / _snowman;
      _snowmanx.f = _snowman;
      _snowmanx.k = (_snowman + _snowman) / (_snowman - _snowman);
      _snowmanx.o = -1.0F;
      _snowmanx.l = 2.0F * _snowman * _snowman / (_snowman - _snowman);
      return _snowmanx;
   }

   public static b a(float var0, float var1, float var2, float var3) {
      b _snowman = new b();
      _snowman.a = 2.0F / _snowman;
      _snowman.f = 2.0F / _snowman;
      float _snowmanx = _snowman - _snowman;
      _snowman.k = -2.0F / _snowmanx;
      _snowman.p = 1.0F;
      _snowman.d = -1.0F;
      _snowman.h = -1.0F;
      _snowman.l = -(_snowman + _snowman) / _snowmanx;
      return _snowman;
   }

   public void a(g var1) {
      this.d = this.d + _snowman.a();
      this.h = this.h + _snowman.b();
      this.l = this.l + _snowman.c();
   }

   public b d() {
      return new b(this);
   }

   public static b a(float var0, float var1, float var2) {
      b _snowman = new b();
      _snowman.a = _snowman;
      _snowman.f = _snowman;
      _snowman.k = _snowman;
      _snowman.p = 1.0F;
      return _snowman;
   }

   public static b b(float var0, float var1, float var2) {
      b _snowman = new b();
      _snowman.a = 1.0F;
      _snowman.f = 1.0F;
      _snowman.k = 1.0F;
      _snowman.p = 1.0F;
      _snowman.d = _snowman;
      _snowman.h = _snowman;
      _snowman.l = _snowman;
      return _snowman;
   }
}
