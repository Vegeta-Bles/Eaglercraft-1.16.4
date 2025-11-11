import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;
import org.apache.commons.lang3.math.NumberUtils;

public class afm {
   public static final float a = c(2.0F);
   private static final float[] b = x.a(new float[65536], var0x -> {
      for (int _snowman = 0; _snowman < var0x.length; _snowman++) {
         var0x[_snowman] = (float)Math.sin((double)_snowman * Math.PI * 2.0 / 65536.0);
      }
   });
   private static final Random c = new Random();
   private static final int[] d = new int[]{
      0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
   };
   private static final double e = Double.longBitsToDouble(4805340802404319232L);
   private static final double[] f = new double[257];
   private static final double[] g = new double[257];

   public static float a(float var0) {
      return b[(int)(_snowman * 10430.378F) & 65535];
   }

   public static float b(float var0) {
      return b[(int)(_snowman * 10430.378F + 16384.0F) & 65535];
   }

   public static float c(float var0) {
      return (float)Math.sqrt((double)_snowman);
   }

   public static float a(double var0) {
      return (float)Math.sqrt(_snowman);
   }

   public static int d(float var0) {
      int _snowman = (int)_snowman;
      return _snowman < (float)_snowman ? _snowman - 1 : _snowman;
   }

   public static int b(double var0) {
      return (int)(_snowman + 1024.0) - 1024;
   }

   public static int c(double var0) {
      int _snowman = (int)_snowman;
      return _snowman < (double)_snowman ? _snowman - 1 : _snowman;
   }

   public static long d(double var0) {
      long _snowman = (long)_snowman;
      return _snowman < (double)_snowman ? _snowman - 1L : _snowman;
   }

   public static float e(float var0) {
      return Math.abs(_snowman);
   }

   public static int a(int var0) {
      return Math.abs(_snowman);
   }

   public static int f(float var0) {
      int _snowman = (int)_snowman;
      return _snowman > (float)_snowman ? _snowman + 1 : _snowman;
   }

   public static int f(double var0) {
      int _snowman = (int)_snowman;
      return _snowman > (double)_snowman ? _snowman + 1 : _snowman;
   }

   public static int a(int var0, int var1, int var2) {
      if (_snowman < _snowman) {
         return _snowman;
      } else {
         return _snowman > _snowman ? _snowman : _snowman;
      }
   }

   public static long a(long var0, long var2, long var4) {
      if (_snowman < _snowman) {
         return _snowman;
      } else {
         return _snowman > _snowman ? _snowman : _snowman;
      }
   }

   public static float a(float var0, float var1, float var2) {
      if (_snowman < _snowman) {
         return _snowman;
      } else {
         return _snowman > _snowman ? _snowman : _snowman;
      }
   }

   public static double a(double var0, double var2, double var4) {
      if (_snowman < _snowman) {
         return _snowman;
      } else {
         return _snowman > _snowman ? _snowman : _snowman;
      }
   }

   public static double b(double var0, double var2, double var4) {
      if (_snowman < 0.0) {
         return _snowman;
      } else {
         return _snowman > 1.0 ? _snowman : d(_snowman, _snowman, _snowman);
      }
   }

   public static double a(double var0, double var2) {
      if (_snowman < 0.0) {
         _snowman = -_snowman;
      }

      if (_snowman < 0.0) {
         _snowman = -_snowman;
      }

      return _snowman > _snowman ? _snowman : _snowman;
   }

   public static int a(int var0, int var1) {
      return Math.floorDiv(_snowman, _snowman);
   }

   public static int a(Random var0, int var1, int var2) {
      return _snowman >= _snowman ? _snowman : _snowman.nextInt(_snowman - _snowman + 1) + _snowman;
   }

   public static float a(Random var0, float var1, float var2) {
      return _snowman >= _snowman ? _snowman : _snowman.nextFloat() * (_snowman - _snowman) + _snowman;
   }

   public static double a(Random var0, double var1, double var3) {
      return _snowman >= _snowman ? _snowman : _snowman.nextDouble() * (_snowman - _snowman) + _snowman;
   }

   public static double a(long[] var0) {
      long _snowman = 0L;

      for (long _snowmanx : _snowman) {
         _snowman += _snowmanx;
      }

      return (double)_snowman / (double)_snowman.length;
   }

   public static boolean a(float var0, float var1) {
      return Math.abs(_snowman - _snowman) < 1.0E-5F;
   }

   public static boolean b(double var0, double var2) {
      return Math.abs(_snowman - _snowman) < 1.0E-5F;
   }

   public static int b(int var0, int var1) {
      return Math.floorMod(_snowman, _snowman);
   }

   public static float b(float var0, float var1) {
      return (_snowman % _snowman + _snowman) % _snowman;
   }

   public static double c(double var0, double var2) {
      return (_snowman % _snowman + _snowman) % _snowman;
   }

   public static int b(int var0) {
      int _snowman = _snowman % 360;
      if (_snowman >= 180) {
         _snowman -= 360;
      }

      if (_snowman < -180) {
         _snowman += 360;
      }

      return _snowman;
   }

   public static float g(float var0) {
      float _snowman = _snowman % 360.0F;
      if (_snowman >= 180.0F) {
         _snowman -= 360.0F;
      }

      if (_snowman < -180.0F) {
         _snowman += 360.0F;
      }

      return _snowman;
   }

   public static double g(double var0) {
      double _snowman = _snowman % 360.0;
      if (_snowman >= 180.0) {
         _snowman -= 360.0;
      }

      if (_snowman < -180.0) {
         _snowman += 360.0;
      }

      return _snowman;
   }

   public static float c(float var0, float var1) {
      return g(_snowman - _snowman);
   }

   public static float d(float var0, float var1) {
      return e(c(_snowman, _snowman));
   }

   public static float b(float var0, float var1, float var2) {
      float _snowman = c(_snowman, _snowman);
      float _snowmanx = a(_snowman, -_snowman, _snowman);
      return _snowman - _snowmanx;
   }

   public static float c(float var0, float var1, float var2) {
      _snowman = e(_snowman);
      return _snowman < _snowman ? a(_snowman + _snowman, _snowman, _snowman) : a(_snowman - _snowman, _snowman, _snowman);
   }

   public static float d(float var0, float var1, float var2) {
      float _snowman = c(_snowman, _snowman);
      return c(_snowman, _snowman + _snowman, _snowman);
   }

   public static int a(String var0, int var1) {
      return NumberUtils.toInt(_snowman, _snowman);
   }

   public static int c(int var0) {
      int _snowman = _snowman - 1;
      _snowman |= _snowman >> 1;
      _snowman |= _snowman >> 2;
      _snowman |= _snowman >> 4;
      _snowman |= _snowman >> 8;
      _snowman |= _snowman >> 16;
      return _snowman + 1;
   }

   public static boolean d(int var0) {
      return _snowman != 0 && (_snowman & _snowman - 1) == 0;
   }

   public static int e(int var0) {
      _snowman = d(_snowman) ? _snowman : c(_snowman);
      return d[(int)((long)_snowman * 125613361L >> 27) & 31];
   }

   public static int f(int var0) {
      return e(_snowman) - (d(_snowman) ? 0 : 1);
   }

   public static int c(int var0, int var1) {
      if (_snowman == 0) {
         return 0;
      } else if (_snowman == 0) {
         return _snowman;
      } else {
         if (_snowman < 0) {
            _snowman *= -1;
         }

         int _snowman = _snowman % _snowman;
         return _snowman == 0 ? _snowman : _snowman + _snowman - _snowman;
      }
   }

   public static int e(float var0, float var1, float var2) {
      return b(d(_snowman * 255.0F), d(_snowman * 255.0F), d(_snowman * 255.0F));
   }

   public static int b(int var0, int var1, int var2) {
      int var3 = (_snowman << 8) + _snowman;
      return (var3 << 8) + _snowman;
   }

   public static float h(float var0) {
      return _snowman - (float)d(_snowman);
   }

   public static double h(double var0) {
      return _snowman - (double)d(_snowman);
   }

   public static long a(gr var0) {
      return c(_snowman.u(), _snowman.v(), _snowman.w());
   }

   public static long c(int var0, int var1, int var2) {
      long _snowman = (long)(_snowman * 3129871) ^ (long)_snowman * 116129781L ^ (long)_snowman;
      _snowman = _snowman * _snowman * 42317861L + _snowman * 11L;
      return _snowman >> 16;
   }

   public static UUID a(Random var0) {
      long _snowman = _snowman.nextLong() & -61441L | 16384L;
      long _snowmanx = _snowman.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(_snowman, _snowmanx);
   }

   public static UUID a() {
      return a(c);
   }

   public static double c(double var0, double var2, double var4) {
      return (_snowman - _snowman) / (_snowman - _snowman);
   }

   public static double d(double var0, double var2) {
      double _snowman = _snowman * _snowman + _snowman * _snowman;
      if (Double.isNaN(_snowman)) {
         return Double.NaN;
      } else {
         boolean _snowmanx = _snowman < 0.0;
         if (_snowmanx) {
            _snowman = -_snowman;
         }

         boolean _snowmanxx = _snowman < 0.0;
         if (_snowmanxx) {
            _snowman = -_snowman;
         }

         boolean _snowmanxxx = _snowman > _snowman;
         if (_snowmanxxx) {
            double _snowmanxxxx = _snowman;
            _snowman = _snowman;
            _snowman = _snowmanxxxx;
         }

         double _snowmanxxxx = i(_snowman);
         _snowman *= _snowmanxxxx;
         _snowman *= _snowmanxxxx;
         double _snowmanxxxxx = e + _snowman;
         int _snowmanxxxxxx = (int)Double.doubleToRawLongBits(_snowmanxxxxx);
         double _snowmanxxxxxxx = f[_snowmanxxxxxx];
         double _snowmanxxxxxxxx = g[_snowmanxxxxxx];
         double _snowmanxxxxxxxxx = _snowmanxxxxx - e;
         double _snowmanxxxxxxxxxx = _snowman * _snowmanxxxxxxxx - _snowman * _snowmanxxxxxxxxx;
         double _snowmanxxxxxxxxxxx = (6.0 + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx) * _snowmanxxxxxxxxxx * 0.16666666666666666;
         double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxx;
         if (_snowmanxxx) {
            _snowmanxxxxxxxxxxxx = (Math.PI / 2) - _snowmanxxxxxxxxxxxx;
         }

         if (_snowmanxx) {
            _snowmanxxxxxxxxxxxx = Math.PI - _snowmanxxxxxxxxxxxx;
         }

         if (_snowmanx) {
            _snowmanxxxxxxxxxxxx = -_snowmanxxxxxxxxxxxx;
         }

         return _snowmanxxxxxxxxxxxx;
      }
   }

   public static float i(float var0) {
      float _snowman = 0.5F * _snowman;
      int _snowmanx = Float.floatToIntBits(_snowman);
      _snowmanx = 1597463007 - (_snowmanx >> 1);
      _snowman = Float.intBitsToFloat(_snowmanx);
      return _snowman * (1.5F - _snowman * _snowman * _snowman);
   }

   public static double i(double var0) {
      double _snowman = 0.5 * _snowman;
      long _snowmanx = Double.doubleToRawLongBits(_snowman);
      _snowmanx = 6910469410427058090L - (_snowmanx >> 1);
      _snowman = Double.longBitsToDouble(_snowmanx);
      return _snowman * (1.5 - _snowman * _snowman * _snowman);
   }

   public static float j(float var0) {
      int _snowman = Float.floatToIntBits(_snowman);
      _snowman = 1419967116 - _snowman / 3;
      float _snowmanx = Float.intBitsToFloat(_snowman);
      _snowmanx = 0.6666667F * _snowmanx + 1.0F / (3.0F * _snowmanx * _snowmanx * _snowman);
      return 0.6666667F * _snowmanx + 1.0F / (3.0F * _snowmanx * _snowmanx * _snowman);
   }

   public static int f(float var0, float var1, float var2) {
      int _snowman = (int)(_snowman * 6.0F) % 6;
      float _snowmanx = _snowman * 6.0F - (float)_snowman;
      float _snowmanxx = _snowman * (1.0F - _snowman);
      float _snowmanxxx = _snowman * (1.0F - _snowmanx * _snowman);
      float _snowmanxxxx = _snowman * (1.0F - (1.0F - _snowmanx) * _snowman);
      float _snowmanxxxxx;
      float _snowmanxxxxxx;
      float _snowmanxxxxxxx;
      switch (_snowman) {
         case 0:
            _snowmanxxxxx = _snowman;
            _snowmanxxxxxx = _snowmanxxxx;
            _snowmanxxxxxxx = _snowmanxx;
            break;
         case 1:
            _snowmanxxxxx = _snowmanxxx;
            _snowmanxxxxxx = _snowman;
            _snowmanxxxxxxx = _snowmanxx;
            break;
         case 2:
            _snowmanxxxxx = _snowmanxx;
            _snowmanxxxxxx = _snowman;
            _snowmanxxxxxxx = _snowmanxxxx;
            break;
         case 3:
            _snowmanxxxxx = _snowmanxx;
            _snowmanxxxxxx = _snowmanxxx;
            _snowmanxxxxxxx = _snowman;
            break;
         case 4:
            _snowmanxxxxx = _snowmanxxxx;
            _snowmanxxxxxx = _snowmanxx;
            _snowmanxxxxxxx = _snowman;
            break;
         case 5:
            _snowmanxxxxx = _snowman;
            _snowmanxxxxxx = _snowmanxx;
            _snowmanxxxxxxx = _snowmanxxx;
            break;
         default:
            throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + _snowman + ", " + _snowman + ", " + _snowman);
      }

      int _snowman = a((int)(_snowmanxxxxx * 255.0F), 0, 255);
      int _snowmanx = a((int)(_snowmanxxxxxx * 255.0F), 0, 255);
      int _snowmanxx = a((int)(_snowmanxxxxxxx * 255.0F), 0, 255);
      return _snowman << 16 | _snowmanx << 8 | _snowmanxx;
   }

   public static int g(int var0) {
      _snowman ^= _snowman >>> 16;
      _snowman *= -2048144789;
      _snowman ^= _snowman >>> 13;
      _snowman *= -1028477387;
      return _snowman ^ _snowman >>> 16;
   }

   public static int a(int var0, int var1, IntPredicate var2) {
      int _snowman = _snowman - _snowman;

      while (_snowman > 0) {
         int _snowmanx = _snowman / 2;
         int _snowmanxx = _snowman + _snowmanx;
         if (_snowman.test(_snowmanxx)) {
            _snowman = _snowmanx;
         } else {
            _snowman = _snowmanxx + 1;
            _snowman -= _snowmanx + 1;
         }
      }

      return _snowman;
   }

   public static float g(float var0, float var1, float var2) {
      return _snowman + _snowman * (_snowman - _snowman);
   }

   public static double d(double var0, double var2, double var4) {
      return _snowman + _snowman * (_snowman - _snowman);
   }

   public static double a(double var0, double var2, double var4, double var6, double var8, double var10) {
      return d(_snowman, d(_snowman, _snowman, _snowman), d(_snowman, _snowman, _snowman));
   }

   public static double a(
      double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, double var16, double var18, double var20
   ) {
      return d(_snowman, a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman), a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
   }

   public static double j(double var0) {
      return _snowman * _snowman * _snowman * (_snowman * (_snowman * 6.0 - 15.0) + 10.0);
   }

   public static int k(double var0) {
      if (_snowman == 0.0) {
         return 0;
      } else {
         return _snowman > 0.0 ? 1 : -1;
      }
   }

   public static float h(float var0, float var1, float var2) {
      return _snowman + _snowman * g(_snowman - _snowman);
   }

   @Deprecated
   public static float j(float var0, float var1, float var2) {
      float _snowman = _snowman - _snowman;

      while (_snowman < -180.0F) {
         _snowman += 360.0F;
      }

      while (_snowman >= 180.0F) {
         _snowman -= 360.0F;
      }

      return _snowman + _snowman * _snowman;
   }

   @Deprecated
   public static float l(double var0) {
      while (_snowman >= 180.0) {
         _snowman -= 360.0;
      }

      while (_snowman < -180.0) {
         _snowman += 360.0;
      }

      return (float)_snowman;
   }

   public static float e(float var0, float var1) {
      return (Math.abs(_snowman % _snowman - _snowman * 0.5F) - _snowman * 0.25F) / (_snowman * 0.25F);
   }

   public static float k(float var0) {
      return _snowman * _snowman;
   }

   static {
      for (int _snowman = 0; _snowman < 257; _snowman++) {
         double _snowmanx = (double)_snowman / 256.0;
         double _snowmanxx = Math.asin(_snowmanx);
         g[_snowman] = Math.cos(_snowmanxx);
         f[_snowman] = _snowmanxx;
      }
   }
}
