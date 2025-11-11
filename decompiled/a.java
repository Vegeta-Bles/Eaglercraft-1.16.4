import com.mojang.datafixers.util.Pair;
import org.apache.commons.lang3.tuple.Triple;

public final class a {
   private static final float j = 3.0F + 2.0F * (float)Math.sqrt(2.0);
   private static final float k = (float)Math.cos(Math.PI / 8);
   private static final float l = (float)Math.sin(Math.PI / 8);
   private static final float m = 1.0F / (float)Math.sqrt(2.0);
   protected float a;
   protected float b;
   protected float c;
   protected float d;
   protected float e;
   protected float f;
   protected float g;
   protected float h;
   protected float i;

   public a() {
   }

   public a(d var1) {
      float _snowman = _snowman.a();
      float _snowmanx = _snowman.b();
      float _snowmanxx = _snowman.c();
      float _snowmanxxx = _snowman.d();
      float _snowmanxxxx = 2.0F * _snowman * _snowman;
      float _snowmanxxxxx = 2.0F * _snowmanx * _snowmanx;
      float _snowmanxxxxxx = 2.0F * _snowmanxx * _snowmanxx;
      this.a = 1.0F - _snowmanxxxxx - _snowmanxxxxxx;
      this.e = 1.0F - _snowmanxxxxxx - _snowmanxxxx;
      this.i = 1.0F - _snowmanxxxx - _snowmanxxxxx;
      float _snowmanxxxxxxx = _snowman * _snowmanx;
      float _snowmanxxxxxxxx = _snowmanx * _snowmanxx;
      float _snowmanxxxxxxxxx = _snowmanxx * _snowman;
      float _snowmanxxxxxxxxxx = _snowman * _snowmanxxx;
      float _snowmanxxxxxxxxxxx = _snowmanx * _snowmanxxx;
      float _snowmanxxxxxxxxxxxx = _snowmanxx * _snowmanxxx;
      this.d = 2.0F * (_snowmanxxxxxxx + _snowmanxxxxxxxxxxxx);
      this.b = 2.0F * (_snowmanxxxxxxx - _snowmanxxxxxxxxxxxx);
      this.g = 2.0F * (_snowmanxxxxxxxxx - _snowmanxxxxxxxxxxx);
      this.c = 2.0F * (_snowmanxxxxxxxxx + _snowmanxxxxxxxxxxx);
      this.h = 2.0F * (_snowmanxxxxxxxx + _snowmanxxxxxxxxxx);
      this.f = 2.0F * (_snowmanxxxxxxxx - _snowmanxxxxxxxxxx);
   }

   public static a b(float var0, float var1, float var2) {
      a _snowman = new a();
      _snowman.a = _snowman;
      _snowman.e = _snowman;
      _snowman.i = _snowman;
      return _snowman;
   }

   public a(b var1) {
      this.a = _snowman.a;
      this.b = _snowman.b;
      this.c = _snowman.c;
      this.d = _snowman.e;
      this.e = _snowman.f;
      this.f = _snowman.g;
      this.g = _snowman.i;
      this.h = _snowman.j;
      this.i = _snowman.k;
   }

   public a(a var1) {
      this.a = _snowman.a;
      this.b = _snowman.b;
      this.c = _snowman.c;
      this.d = _snowman.d;
      this.e = _snowman.e;
      this.f = _snowman.f;
      this.g = _snowman.g;
      this.h = _snowman.h;
      this.i = _snowman.i;
   }

   private static Pair<Float, Float> a(float var0, float var1, float var2) {
      float _snowman = 2.0F * (_snowman - _snowman);
      if (j * _snowman * _snowman < _snowman * _snowman) {
         float _snowmanx = afm.i(_snowman * _snowman + _snowman * _snowman);
         return Pair.of(_snowmanx * _snowman, _snowmanx * _snowman);
      } else {
         return Pair.of(l, k);
      }
   }

   private static Pair<Float, Float> a(float var0, float var1) {
      float _snowman = (float)Math.hypot((double)_snowman, (double)_snowman);
      float _snowmanx = _snowman > 1.0E-6F ? _snowman : 0.0F;
      float _snowmanxx = Math.abs(_snowman) + Math.max(_snowman, 1.0E-6F);
      if (_snowman < 0.0F) {
         float _snowmanxxx = _snowmanx;
         _snowmanx = _snowmanxx;
         _snowmanxx = _snowmanxxx;
      }

      float _snowmanxxx = afm.i(_snowmanxx * _snowmanxx + _snowmanx * _snowmanx);
      _snowmanxx *= _snowmanxxx;
      _snowmanx *= _snowmanxxx;
      return Pair.of(_snowmanx, _snowmanxx);
   }

   private static d c(a var0) {
      a _snowman = new a();
      d _snowmanx = d.a.g();
      if (_snowman.b * _snowman.b + _snowman.d * _snowman.d > 1.0E-6F) {
         Pair<Float, Float> _snowmanxx = a(_snowman.a, 0.5F * (_snowman.b + _snowman.d), _snowman.e);
         Float _snowmanxxx = (Float)_snowmanxx.getFirst();
         Float _snowmanxxxx = (Float)_snowmanxx.getSecond();
         d _snowmanxxxxx = new d(0.0F, 0.0F, _snowmanxxx, _snowmanxxxx);
         float _snowmanxxxxxx = _snowmanxxxx * _snowmanxxxx - _snowmanxxx * _snowmanxxx;
         float _snowmanxxxxxxx = -2.0F * _snowmanxxx * _snowmanxxxx;
         float _snowmanxxxxxxxx = _snowmanxxxx * _snowmanxxxx + _snowmanxxx * _snowmanxxx;
         _snowmanx.a(_snowmanxxxxx);
         _snowman.c();
         _snowman.a = _snowmanxxxxxx;
         _snowman.e = _snowmanxxxxxx;
         _snowman.d = -_snowmanxxxxxxx;
         _snowman.b = _snowmanxxxxxxx;
         _snowman.i = _snowmanxxxxxxxx;
         _snowman.b(_snowman);
         _snowman.a();
         _snowman.b(_snowman);
         _snowman.a(_snowman);
      }

      if (_snowman.c * _snowman.c + _snowman.g * _snowman.g > 1.0E-6F) {
         Pair<Float, Float> _snowmanxx = a(_snowman.a, 0.5F * (_snowman.c + _snowman.g), _snowman.i);
         float _snowmanxxx = -(Float)_snowmanxx.getFirst();
         Float _snowmanxxxx = (Float)_snowmanxx.getSecond();
         d _snowmanxxxxx = new d(0.0F, _snowmanxxx, 0.0F, _snowmanxxxx);
         float _snowmanxxxxxx = _snowmanxxxx * _snowmanxxxx - _snowmanxxx * _snowmanxxx;
         float _snowmanxxxxxxx = -2.0F * _snowmanxxx * _snowmanxxxx;
         float _snowmanxxxxxxxx = _snowmanxxxx * _snowmanxxxx + _snowmanxxx * _snowmanxxx;
         _snowmanx.a(_snowmanxxxxx);
         _snowman.c();
         _snowman.a = _snowmanxxxxxx;
         _snowman.i = _snowmanxxxxxx;
         _snowman.g = _snowmanxxxxxxx;
         _snowman.c = -_snowmanxxxxxxx;
         _snowman.e = _snowmanxxxxxxxx;
         _snowman.b(_snowman);
         _snowman.a();
         _snowman.b(_snowman);
         _snowman.a(_snowman);
      }

      if (_snowman.f * _snowman.f + _snowman.h * _snowman.h > 1.0E-6F) {
         Pair<Float, Float> _snowmanxx = a(_snowman.e, 0.5F * (_snowman.f + _snowman.h), _snowman.i);
         Float _snowmanxxx = (Float)_snowmanxx.getFirst();
         Float _snowmanxxxx = (Float)_snowmanxx.getSecond();
         d _snowmanxxxxx = new d(_snowmanxxx, 0.0F, 0.0F, _snowmanxxxx);
         float _snowmanxxxxxx = _snowmanxxxx * _snowmanxxxx - _snowmanxxx * _snowmanxxx;
         float _snowmanxxxxxxx = -2.0F * _snowmanxxx * _snowmanxxxx;
         float _snowmanxxxxxxxx = _snowmanxxxx * _snowmanxxxx + _snowmanxxx * _snowmanxxx;
         _snowmanx.a(_snowmanxxxxx);
         _snowman.c();
         _snowman.e = _snowmanxxxxxx;
         _snowman.i = _snowmanxxxxxx;
         _snowman.h = -_snowmanxxxxxxx;
         _snowman.f = _snowmanxxxxxxx;
         _snowman.a = _snowmanxxxxxxxx;
         _snowman.b(_snowman);
         _snowman.a();
         _snowman.b(_snowman);
         _snowman.a(_snowman);
      }

      return _snowmanx;
   }

   public void a() {
      float _snowman = this.b;
      this.b = this.d;
      this.d = _snowman;
      _snowman = this.c;
      this.c = this.g;
      this.g = _snowman;
      _snowman = this.f;
      this.f = this.h;
      this.h = _snowman;
   }

   public Triple<d, g, d> b() {
      d _snowman = d.a.g();
      d _snowmanx = d.a.g();
      a _snowmanxx = this.d();
      _snowmanxx.a();
      _snowmanxx.b(this);

      for (int _snowmanxxx = 0; _snowmanxxx < 5; _snowmanxxx++) {
         _snowmanx.a(c(_snowmanxx));
      }

      _snowmanx.f();
      a _snowmanxxx = new a(this);
      _snowmanxxx.b(new a(_snowmanx));
      float _snowmanxxxx = 1.0F;
      Pair<Float, Float> _snowmanxxxxx = a(_snowmanxxx.a, _snowmanxxx.d);
      Float _snowmanxxxxxx = (Float)_snowmanxxxxx.getFirst();
      Float _snowmanxxxxxxx = (Float)_snowmanxxxxx.getSecond();
      float _snowmanxxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxxx - _snowmanxxxxxx * _snowmanxxxxxx;
      float _snowmanxxxxxxxxx = -2.0F * _snowmanxxxxxx * _snowmanxxxxxxx;
      float _snowmanxxxxxxxxxx = _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxx * _snowmanxxxxxx;
      d _snowmanxxxxxxxxxxx = new d(0.0F, 0.0F, _snowmanxxxxxx, _snowmanxxxxxxx);
      _snowman.a(_snowmanxxxxxxxxxxx);
      a _snowmanxxxxxxxxxxxx = new a();
      _snowmanxxxxxxxxxxxx.c();
      _snowmanxxxxxxxxxxxx.a = _snowmanxxxxxxxx;
      _snowmanxxxxxxxxxxxx.e = _snowmanxxxxxxxx;
      _snowmanxxxxxxxxxxxx.d = _snowmanxxxxxxxxx;
      _snowmanxxxxxxxxxxxx.b = -_snowmanxxxxxxxxx;
      _snowmanxxxxxxxxxxxx.i = _snowmanxxxxxxxxxx;
      _snowmanxxxx *= _snowmanxxxxxxxxxx;
      _snowmanxxxxxxxxxxxx.b(_snowmanxxx);
      _snowmanxxxxx = a(_snowmanxxxxxxxxxxxx.a, _snowmanxxxxxxxxxxxx.g);
      float _snowmanxxxxxxxxxxxxx = -(Float)_snowmanxxxxx.getFirst();
      Float _snowmanxxxxxxxxxxxxxx = (Float)_snowmanxxxxx.getSecond();
      float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxx = -2.0F * _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx;
      d _snowmanxxxxxxxxxxxxxxxxxx = new d(0.0F, _snowmanxxxxxxxxxxxxx, 0.0F, _snowmanxxxxxxxxxxxxxx);
      _snowman.a(_snowmanxxxxxxxxxxxxxxxxxx);
      a _snowmanxxxxxxxxxxxxxxxxxxx = new a();
      _snowmanxxxxxxxxxxxxxxxxxxx.c();
      _snowmanxxxxxxxxxxxxxxxxxxx.a = _snowmanxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.i = _snowmanxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.g = -_snowmanxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.c = _snowmanxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.e = _snowmanxxxxxxxxxxxxxxxxx;
      _snowmanxxxx *= _snowmanxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxx);
      _snowmanxxxxx = a(_snowmanxxxxxxxxxxxxxxxxxxx.e, _snowmanxxxxxxxxxxxxxxxxxxx.h);
      Float _snowmanxxxxxxxxxxxxxxxxxxxx = (Float)_snowmanxxxxx.getFirst();
      Float _snowmanxxxxxxxxxxxxxxxxxxxxx = (Float)_snowmanxxxxx.getSecond();
      float _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = -2.0F * _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxx;
      d _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = new d(_snowmanxxxxxxxxxxxxxxxxxxxx, 0.0F, 0.0F, _snowmanxxxxxxxxxxxxxxxxxxxxx);
      _snowman.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
      a _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = new a();
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.c();
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.e = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.i = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.h = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.f = -_snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxx *= _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxxxxx);
      _snowmanxxxx = 1.0F / _snowmanxxxx;
      _snowman.a((float)Math.sqrt((double)_snowmanxxxx));
      g _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = new g(
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.a * _snowmanxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.e * _snowmanxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.i * _snowmanxxxx
      );
      return Triple.of(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanx);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         a _snowman = (a)_snowman;
         return Float.compare(_snowman.a, this.a) == 0
            && Float.compare(_snowman.b, this.b) == 0
            && Float.compare(_snowman.c, this.c) == 0
            && Float.compare(_snowman.d, this.d) == 0
            && Float.compare(_snowman.e, this.e) == 0
            && Float.compare(_snowman.f, this.f) == 0
            && Float.compare(_snowman.g, this.g) == 0
            && Float.compare(_snowman.h, this.h) == 0
            && Float.compare(_snowman.i, this.i) == 0;
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
      return 31 * _snowman + (this.i != 0.0F ? Float.floatToIntBits(this.i) : 0);
   }

   public void a(a var1) {
      this.a = _snowman.a;
      this.b = _snowman.b;
      this.c = _snowman.c;
      this.d = _snowman.d;
      this.e = _snowman.e;
      this.f = _snowman.f;
      this.g = _snowman.g;
      this.h = _snowman.h;
      this.i = _snowman.i;
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("Matrix3f:\n");
      _snowman.append(this.a);
      _snowman.append(" ");
      _snowman.append(this.b);
      _snowman.append(" ");
      _snowman.append(this.c);
      _snowman.append("\n");
      _snowman.append(this.d);
      _snowman.append(" ");
      _snowman.append(this.e);
      _snowman.append(" ");
      _snowman.append(this.f);
      _snowman.append("\n");
      _snowman.append(this.g);
      _snowman.append(" ");
      _snowman.append(this.h);
      _snowman.append(" ");
      _snowman.append(this.i);
      _snowman.append("\n");
      return _snowman.toString();
   }

   public void c() {
      this.a = 1.0F;
      this.b = 0.0F;
      this.c = 0.0F;
      this.d = 0.0F;
      this.e = 1.0F;
      this.f = 0.0F;
      this.g = 0.0F;
      this.h = 0.0F;
      this.i = 1.0F;
   }

   public float e() {
      float _snowman = this.e * this.i - this.f * this.h;
      float _snowmanx = -(this.d * this.i - this.f * this.g);
      float _snowmanxx = this.d * this.h - this.e * this.g;
      float _snowmanxxx = -(this.b * this.i - this.c * this.h);
      float _snowmanxxxx = this.a * this.i - this.c * this.g;
      float _snowmanxxxxx = -(this.a * this.h - this.b * this.g);
      float _snowmanxxxxxx = this.b * this.f - this.c * this.e;
      float _snowmanxxxxxxx = -(this.a * this.f - this.c * this.d);
      float _snowmanxxxxxxxx = this.a * this.e - this.b * this.d;
      float _snowmanxxxxxxxxx = this.a * _snowman + this.b * _snowmanx + this.c * _snowmanxx;
      this.a = _snowman;
      this.d = _snowmanx;
      this.g = _snowmanxx;
      this.b = _snowmanxxx;
      this.e = _snowmanxxxx;
      this.h = _snowmanxxxxx;
      this.c = _snowmanxxxxxx;
      this.f = _snowmanxxxxxxx;
      this.i = _snowmanxxxxxxxx;
      return _snowmanxxxxxxxxx;
   }

   public boolean f() {
      float _snowman = this.e();
      if (Math.abs(_snowman) > 1.0E-6F) {
         this.a(_snowman);
         return true;
      } else {
         return false;
      }
   }

   public void a(int var1, int var2, float var3) {
      if (_snowman == 0) {
         if (_snowman == 0) {
            this.a = _snowman;
         } else if (_snowman == 1) {
            this.b = _snowman;
         } else {
            this.c = _snowman;
         }
      } else if (_snowman == 1) {
         if (_snowman == 0) {
            this.d = _snowman;
         } else if (_snowman == 1) {
            this.e = _snowman;
         } else {
            this.f = _snowman;
         }
      } else if (_snowman == 0) {
         this.g = _snowman;
      } else if (_snowman == 1) {
         this.h = _snowman;
      } else {
         this.i = _snowman;
      }
   }

   public void b(a var1) {
      float _snowman = this.a * _snowman.a + this.b * _snowman.d + this.c * _snowman.g;
      float _snowmanx = this.a * _snowman.b + this.b * _snowman.e + this.c * _snowman.h;
      float _snowmanxx = this.a * _snowman.c + this.b * _snowman.f + this.c * _snowman.i;
      float _snowmanxxx = this.d * _snowman.a + this.e * _snowman.d + this.f * _snowman.g;
      float _snowmanxxxx = this.d * _snowman.b + this.e * _snowman.e + this.f * _snowman.h;
      float _snowmanxxxxx = this.d * _snowman.c + this.e * _snowman.f + this.f * _snowman.i;
      float _snowmanxxxxxx = this.g * _snowman.a + this.h * _snowman.d + this.i * _snowman.g;
      float _snowmanxxxxxxx = this.g * _snowman.b + this.h * _snowman.e + this.i * _snowman.h;
      float _snowmanxxxxxxxx = this.g * _snowman.c + this.h * _snowman.f + this.i * _snowman.i;
      this.a = _snowman;
      this.b = _snowmanx;
      this.c = _snowmanxx;
      this.d = _snowmanxxx;
      this.e = _snowmanxxxx;
      this.f = _snowmanxxxxx;
      this.g = _snowmanxxxxxx;
      this.h = _snowmanxxxxxxx;
      this.i = _snowmanxxxxxxxx;
   }

   public void a(d var1) {
      this.b(new a(_snowman));
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
   }

   public a d() {
      return new a(this);
   }
}
