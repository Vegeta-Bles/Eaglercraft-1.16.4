import java.util.Optional;
import javax.annotation.Nullable;

public class dci {
   public final double a;
   public final double b;
   public final double c;
   public final double d;
   public final double e;
   public final double f;

   public dci(double var1, double var3, double var5, double var7, double var9, double var11) {
      this.a = Math.min(_snowman, _snowman);
      this.b = Math.min(_snowman, _snowman);
      this.c = Math.min(_snowman, _snowman);
      this.d = Math.max(_snowman, _snowman);
      this.e = Math.max(_snowman, _snowman);
      this.f = Math.max(_snowman, _snowman);
   }

   public dci(fx var1) {
      this((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), (double)(_snowman.u() + 1), (double)(_snowman.v() + 1), (double)(_snowman.w() + 1));
   }

   public dci(fx var1, fx var2) {
      this((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w(), (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());
   }

   public dci(dcn var1, dcn var2) {
      this(_snowman.b, _snowman.c, _snowman.d, _snowman.b, _snowman.c, _snowman.d);
   }

   public static dci a(cra var0) {
      return new dci((double)_snowman.a, (double)_snowman.b, (double)_snowman.c, (double)(_snowman.d + 1), (double)(_snowman.e + 1), (double)(_snowman.f + 1));
   }

   public static dci a(dcn var0) {
      return new dci(_snowman.b, _snowman.c, _snowman.d, _snowman.b + 1.0, _snowman.c + 1.0, _snowman.d + 1.0);
   }

   public double a(gc.a var1) {
      return _snowman.a(this.a, this.b, this.c);
   }

   public double b(gc.a var1) {
      return _snowman.a(this.d, this.e, this.f);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof dci)) {
         return false;
      } else {
         dci _snowman = (dci)_snowman;
         if (Double.compare(_snowman.a, this.a) != 0) {
            return false;
         } else if (Double.compare(_snowman.b, this.b) != 0) {
            return false;
         } else if (Double.compare(_snowman.c, this.c) != 0) {
            return false;
         } else if (Double.compare(_snowman.d, this.d) != 0) {
            return false;
         } else {
            return Double.compare(_snowman.e, this.e) != 0 ? false : Double.compare(_snowman.f, this.f) == 0;
         }
      }
   }

   @Override
   public int hashCode() {
      long _snowman = Double.doubleToLongBits(this.a);
      int _snowmanx = (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.b);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.c);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.d);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.e);
      _snowmanx = 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
      _snowman = Double.doubleToLongBits(this.f);
      return 31 * _snowmanx + (int)(_snowman ^ _snowman >>> 32);
   }

   public dci a(double var1, double var3, double var5) {
      double _snowman = this.a;
      double _snowmanx = this.b;
      double _snowmanxx = this.c;
      double _snowmanxxx = this.d;
      double _snowmanxxxx = this.e;
      double _snowmanxxxxx = this.f;
      if (_snowman < 0.0) {
         _snowman -= _snowman;
      } else if (_snowman > 0.0) {
         _snowmanxxx -= _snowman;
      }

      if (_snowman < 0.0) {
         _snowmanx -= _snowman;
      } else if (_snowman > 0.0) {
         _snowmanxxxx -= _snowman;
      }

      if (_snowman < 0.0) {
         _snowmanxx -= _snowman;
      } else if (_snowman > 0.0) {
         _snowmanxxxxx -= _snowman;
      }

      return new dci(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public dci b(dcn var1) {
      return this.b(_snowman.b, _snowman.c, _snowman.d);
   }

   public dci b(double var1, double var3, double var5) {
      double _snowman = this.a;
      double _snowmanx = this.b;
      double _snowmanxx = this.c;
      double _snowmanxxx = this.d;
      double _snowmanxxxx = this.e;
      double _snowmanxxxxx = this.f;
      if (_snowman < 0.0) {
         _snowman += _snowman;
      } else if (_snowman > 0.0) {
         _snowmanxxx += _snowman;
      }

      if (_snowman < 0.0) {
         _snowmanx += _snowman;
      } else if (_snowman > 0.0) {
         _snowmanxxxx += _snowman;
      }

      if (_snowman < 0.0) {
         _snowmanxx += _snowman;
      } else if (_snowman > 0.0) {
         _snowmanxxxxx += _snowman;
      }

      return new dci(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public dci c(double var1, double var3, double var5) {
      double _snowman = this.a - _snowman;
      double _snowmanx = this.b - _snowman;
      double _snowmanxx = this.c - _snowman;
      double _snowmanxxx = this.d + _snowman;
      double _snowmanxxxx = this.e + _snowman;
      double _snowmanxxxxx = this.f + _snowman;
      return new dci(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public dci g(double var1) {
      return this.c(_snowman, _snowman, _snowman);
   }

   public dci a(dci var1) {
      double _snowman = Math.max(this.a, _snowman.a);
      double _snowmanx = Math.max(this.b, _snowman.b);
      double _snowmanxx = Math.max(this.c, _snowman.c);
      double _snowmanxxx = Math.min(this.d, _snowman.d);
      double _snowmanxxxx = Math.min(this.e, _snowman.e);
      double _snowmanxxxxx = Math.min(this.f, _snowman.f);
      return new dci(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public dci b(dci var1) {
      double _snowman = Math.min(this.a, _snowman.a);
      double _snowmanx = Math.min(this.b, _snowman.b);
      double _snowmanxx = Math.min(this.c, _snowman.c);
      double _snowmanxxx = Math.max(this.d, _snowman.d);
      double _snowmanxxxx = Math.max(this.e, _snowman.e);
      double _snowmanxxxxx = Math.max(this.f, _snowman.f);
      return new dci(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   public dci d(double var1, double var3, double var5) {
      return new dci(this.a + _snowman, this.b + _snowman, this.c + _snowman, this.d + _snowman, this.e + _snowman, this.f + _snowman);
   }

   public dci a(fx var1) {
      return new dci(
         this.a + (double)_snowman.u(), this.b + (double)_snowman.v(), this.c + (double)_snowman.w(), this.d + (double)_snowman.u(), this.e + (double)_snowman.v(), this.f + (double)_snowman.w()
      );
   }

   public dci c(dcn var1) {
      return this.d(_snowman.b, _snowman.c, _snowman.d);
   }

   public boolean c(dci var1) {
      return this.a(_snowman.a, _snowman.b, _snowman.c, _snowman.d, _snowman.e, _snowman.f);
   }

   public boolean a(double var1, double var3, double var5, double var7, double var9, double var11) {
      return this.a < _snowman && this.d > _snowman && this.b < _snowman && this.e > _snowman && this.c < _snowman && this.f > _snowman;
   }

   public boolean a(dcn var1, dcn var2) {
      return this.a(Math.min(_snowman.b, _snowman.b), Math.min(_snowman.c, _snowman.c), Math.min(_snowman.d, _snowman.d), Math.max(_snowman.b, _snowman.b), Math.max(_snowman.c, _snowman.c), Math.max(_snowman.d, _snowman.d));
   }

   public boolean d(dcn var1) {
      return this.e(_snowman.b, _snowman.c, _snowman.d);
   }

   public boolean e(double var1, double var3, double var5) {
      return _snowman >= this.a && _snowman < this.d && _snowman >= this.b && _snowman < this.e && _snowman >= this.c && _snowman < this.f;
   }

   public double a() {
      double _snowman = this.b();
      double _snowmanx = this.c();
      double _snowmanxx = this.d();
      return (_snowman + _snowmanx + _snowmanxx) / 3.0;
   }

   public double b() {
      return this.d - this.a;
   }

   public double c() {
      return this.e - this.b;
   }

   public double d() {
      return this.f - this.c;
   }

   public dci h(double var1) {
      return this.g(-_snowman);
   }

   public Optional<dcn> b(dcn var1, dcn var2) {
      double[] _snowman = new double[]{1.0};
      double _snowmanx = _snowman.b - _snowman.b;
      double _snowmanxx = _snowman.c - _snowman.c;
      double _snowmanxxx = _snowman.d - _snowman.d;
      gc _snowmanxxxx = a(this, _snowman, _snowman, null, _snowmanx, _snowmanxx, _snowmanxxx);
      if (_snowmanxxxx == null) {
         return Optional.empty();
      } else {
         double _snowmanxxxxx = _snowman[0];
         return Optional.of(_snowman.b(_snowmanxxxxx * _snowmanx, _snowmanxxxxx * _snowmanxx, _snowmanxxxxx * _snowmanxxx));
      }
   }

   @Nullable
   public static dcj a(Iterable<dci> var0, dcn var1, dcn var2, fx var3) {
      double[] _snowman = new double[]{1.0};
      gc _snowmanx = null;
      double _snowmanxx = _snowman.b - _snowman.b;
      double _snowmanxxx = _snowman.c - _snowman.c;
      double _snowmanxxxx = _snowman.d - _snowman.d;

      for (dci _snowmanxxxxx : _snowman) {
         _snowmanx = a(_snowmanxxxxx.a(_snowman), _snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      }

      if (_snowmanx == null) {
         return null;
      } else {
         double _snowmanxxxxx = _snowman[0];
         return new dcj(_snowman.b(_snowmanxxxxx * _snowmanxx, _snowmanxxxxx * _snowmanxxx, _snowmanxxxxx * _snowmanxxxx), _snowmanx, _snowman, false);
      }
   }

   @Nullable
   private static gc a(dci var0, dcn var1, double[] var2, @Nullable gc var3, double var4, double var6, double var8) {
      if (_snowman > 1.0E-7) {
         _snowman = a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.a, _snowman.b, _snowman.e, _snowman.c, _snowman.f, gc.e, _snowman.b, _snowman.c, _snowman.d);
      } else if (_snowman < -1.0E-7) {
         _snowman = a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.d, _snowman.b, _snowman.e, _snowman.c, _snowman.f, gc.f, _snowman.b, _snowman.c, _snowman.d);
      }

      if (_snowman > 1.0E-7) {
         _snowman = a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.b, _snowman.c, _snowman.f, _snowman.a, _snowman.d, gc.a, _snowman.c, _snowman.d, _snowman.b);
      } else if (_snowman < -1.0E-7) {
         _snowman = a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.e, _snowman.c, _snowman.f, _snowman.a, _snowman.d, gc.b, _snowman.c, _snowman.d, _snowman.b);
      }

      if (_snowman > 1.0E-7) {
         _snowman = a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.c, _snowman.a, _snowman.d, _snowman.b, _snowman.e, gc.c, _snowman.d, _snowman.b, _snowman.c);
      } else if (_snowman < -1.0E-7) {
         _snowman = a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.f, _snowman.a, _snowman.d, _snowman.b, _snowman.e, gc.d, _snowman.d, _snowman.b, _snowman.c);
      }

      return _snowman;
   }

   @Nullable
   private static gc a(
      double[] var0,
      @Nullable gc var1,
      double var2,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      double var14,
      double var16,
      gc var18,
      double var19,
      double var21,
      double var23
   ) {
      double _snowman = (_snowman - _snowman) / _snowman;
      double _snowmanx = _snowman + _snowman * _snowman;
      double _snowmanxx = _snowman + _snowman * _snowman;
      if (0.0 < _snowman && _snowman < _snowman[0] && _snowman - 1.0E-7 < _snowmanx && _snowmanx < _snowman + 1.0E-7 && _snowman - 1.0E-7 < _snowmanxx && _snowmanxx < _snowman + 1.0E-7) {
         _snowman[0] = _snowman;
         return _snowman;
      } else {
         return _snowman;
      }
   }

   @Override
   public String toString() {
      return "AABB[" + this.a + ", " + this.b + ", " + this.c + "] -> [" + this.d + ", " + this.e + ", " + this.f + "]";
   }

   public boolean e() {
      return Double.isNaN(this.a) || Double.isNaN(this.b) || Double.isNaN(this.c) || Double.isNaN(this.d) || Double.isNaN(this.e) || Double.isNaN(this.f);
   }

   public dcn f() {
      return new dcn(afm.d(0.5, this.a, this.d), afm.d(0.5, this.b, this.e), afm.d(0.5, this.c, this.f));
   }

   public static dci g(double var0, double var2, double var4) {
      return new dci(-_snowman / 2.0, -_snowman / 2.0, -_snowman / 2.0, _snowman / 2.0, _snowman / 2.0, _snowman / 2.0);
   }
}
