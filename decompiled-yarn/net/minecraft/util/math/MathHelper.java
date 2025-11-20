package net.minecraft.util.math;

import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;
import net.minecraft.util.Util;
import org.apache.commons.lang3.math.NumberUtils;

public class MathHelper {
   public static final float SQUARE_ROOT_OF_TWO = sqrt(2.0F);
   private static final float[] SINE_TABLE = Util.make(new float[65536], _snowman -> {
      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         _snowman[_snowmanx] = (float)Math.sin((double)_snowmanx * Math.PI * 2.0 / 65536.0);
      }
   });
   private static final Random RANDOM = new Random();
   private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{
      0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
   };
   private static final double SMALLEST_FRACTION_FREE_DOUBLE = Double.longBitsToDouble(4805340802404319232L);
   private static final double[] ARCSINE_TABLE = new double[257];
   private static final double[] COSINE_TABLE = new double[257];

   public static float sin(float _snowman) {
      return SINE_TABLE[(int)(_snowman * 10430.378F) & 65535];
   }

   public static float cos(float _snowman) {
      return SINE_TABLE[(int)(_snowman * 10430.378F + 16384.0F) & 65535];
   }

   public static float sqrt(float _snowman) {
      return (float)Math.sqrt((double)_snowman);
   }

   public static float sqrt(double _snowman) {
      return (float)Math.sqrt(_snowman);
   }

   public static int floor(float _snowman) {
      int _snowmanx = (int)_snowman;
      return _snowman < (float)_snowmanx ? _snowmanx - 1 : _snowmanx;
   }

   public static int fastFloor(double _snowman) {
      return (int)(_snowman + 1024.0) - 1024;
   }

   public static int floor(double _snowman) {
      int _snowmanx = (int)_snowman;
      return _snowman < (double)_snowmanx ? _snowmanx - 1 : _snowmanx;
   }

   public static long lfloor(double _snowman) {
      long _snowmanx = (long)_snowman;
      return _snowman < (double)_snowmanx ? _snowmanx - 1L : _snowmanx;
   }

   public static float abs(float _snowman) {
      return Math.abs(_snowman);
   }

   public static int abs(int _snowman) {
      return Math.abs(_snowman);
   }

   public static int ceil(float _snowman) {
      int _snowmanx = (int)_snowman;
      return _snowman > (float)_snowmanx ? _snowmanx + 1 : _snowmanx;
   }

   public static int ceil(double _snowman) {
      int _snowmanx = (int)_snowman;
      return _snowman > (double)_snowmanx ? _snowmanx + 1 : _snowmanx;
   }

   public static int clamp(int value, int min, int max) {
      if (value < min) {
         return min;
      } else {
         return value > max ? max : value;
      }
   }

   public static long clamp(long value, long min, long max) {
      if (value < min) {
         return min;
      } else {
         return value > max ? max : value;
      }
   }

   public static float clamp(float value, float min, float max) {
      if (value < min) {
         return min;
      } else {
         return value > max ? max : value;
      }
   }

   public static double clamp(double value, double min, double max) {
      if (value < min) {
         return min;
      } else {
         return value > max ? max : value;
      }
   }

   public static double clampedLerp(double start, double end, double delta) {
      if (delta < 0.0) {
         return start;
      } else {
         return delta > 1.0 ? end : lerp(delta, start, end);
      }
   }

   public static double absMax(double _snowman, double _snowman) {
      if (_snowman < 0.0) {
         _snowman = -_snowman;
      }

      if (_snowman < 0.0) {
         _snowman = -_snowman;
      }

      return _snowman > _snowman ? _snowman : _snowman;
   }

   public static int floorDiv(int _snowman, int _snowman) {
      return Math.floorDiv(_snowman, _snowman);
   }

   public static int nextInt(Random random, int min, int max) {
      return min >= max ? min : random.nextInt(max - min + 1) + min;
   }

   public static float nextFloat(Random random, float min, float max) {
      return min >= max ? min : random.nextFloat() * (max - min) + min;
   }

   public static double nextDouble(Random random, double min, double max) {
      return min >= max ? min : random.nextDouble() * (max - min) + min;
   }

   public static double average(long[] array) {
      long _snowman = 0L;

      for (long _snowmanx : array) {
         _snowman += _snowmanx;
      }

      return (double)_snowman / (double)array.length;
   }

   public static boolean approximatelyEquals(float a, float b) {
      return Math.abs(b - a) < 1.0E-5F;
   }

   public static boolean approximatelyEquals(double a, double b) {
      return Math.abs(b - a) < 1.0E-5F;
   }

   public static int floorMod(int _snowman, int _snowman) {
      return Math.floorMod(_snowman, _snowman);
   }

   public static float floorMod(float _snowman, float _snowman) {
      return (_snowman % _snowman + _snowman) % _snowman;
   }

   public static double floorMod(double _snowman, double _snowman) {
      return (_snowman % _snowman + _snowman) % _snowman;
   }

   public static int wrapDegrees(int _snowman) {
      int _snowmanx = _snowman % 360;
      if (_snowmanx >= 180) {
         _snowmanx -= 360;
      }

      if (_snowmanx < -180) {
         _snowmanx += 360;
      }

      return _snowmanx;
   }

   public static float wrapDegrees(float _snowman) {
      float _snowmanx = _snowman % 360.0F;
      if (_snowmanx >= 180.0F) {
         _snowmanx -= 360.0F;
      }

      if (_snowmanx < -180.0F) {
         _snowmanx += 360.0F;
      }

      return _snowmanx;
   }

   public static double wrapDegrees(double _snowman) {
      double _snowmanx = _snowman % 360.0;
      if (_snowmanx >= 180.0) {
         _snowmanx -= 360.0;
      }

      if (_snowmanx < -180.0) {
         _snowmanx += 360.0;
      }

      return _snowmanx;
   }

   public static float subtractAngles(float start, float end) {
      return wrapDegrees(end - start);
   }

   public static float angleBetween(float first, float second) {
      return abs(subtractAngles(first, second));
   }

   public static float stepAngleTowards(float from, float to, float step) {
      float _snowman = subtractAngles(from, to);
      float _snowmanx = clamp(_snowman, -step, step);
      return to - _snowmanx;
   }

   public static float stepTowards(float from, float to, float step) {
      step = abs(step);
      return from < to ? clamp(from + step, from, to) : clamp(from - step, to, from);
   }

   public static float stepUnwrappedAngleTowards(float from, float to, float step) {
      float _snowman = subtractAngles(from, to);
      return stepTowards(from, from + _snowman, step);
   }

   public static int parseInt(String string, int fallback) {
      return NumberUtils.toInt(string, fallback);
   }

   public static int smallestEncompassingPowerOfTwo(int value) {
      int _snowman = value - 1;
      _snowman |= _snowman >> 1;
      _snowman |= _snowman >> 2;
      _snowman |= _snowman >> 4;
      _snowman |= _snowman >> 8;
      _snowman |= _snowman >> 16;
      return _snowman + 1;
   }

   public static boolean isPowerOfTwo(int _snowman) {
      return _snowman != 0 && (_snowman & _snowman - 1) == 0;
   }

   public static int log2DeBruijn(int _snowman) {
      _snowman = isPowerOfTwo(_snowman) ? _snowman : smallestEncompassingPowerOfTwo(_snowman);
      return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)((long)_snowman * 125613361L >> 27) & 31];
   }

   public static int log2(int _snowman) {
      return log2DeBruijn(_snowman) - (isPowerOfTwo(_snowman) ? 0 : 1);
   }

   public static int roundUpToMultiple(int value, int divisor) {
      if (divisor == 0) {
         return 0;
      } else if (value == 0) {
         return divisor;
      } else {
         if (value < 0) {
            divisor *= -1;
         }

         int _snowman = value % divisor;
         return _snowman == 0 ? value : value + divisor - _snowman;
      }
   }

   public static int packRgb(float r, float g, float b) {
      return packRgb(floor(r * 255.0F), floor(g * 255.0F), floor(b * 255.0F));
   }

   public static int packRgb(int r, int g, int b) {
      int var3 = (r << 8) + g;
      return (var3 << 8) + b;
   }

   public static float fractionalPart(float value) {
      return value - (float)floor(value);
   }

   public static double fractionalPart(double value) {
      return value - (double)lfloor(value);
   }

   public static long hashCode(Vec3i vec) {
      return hashCode(vec.getX(), vec.getY(), vec.getZ());
   }

   public static long hashCode(int x, int y, int z) {
      long _snowman = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
      _snowman = _snowman * _snowman * 42317861L + _snowman * 11L;
      return _snowman >> 16;
   }

   public static UUID randomUuid(Random random) {
      long _snowman = random.nextLong() & -61441L | 16384L;
      long _snowmanx = random.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(_snowman, _snowmanx);
   }

   public static UUID randomUuid() {
      return randomUuid(RANDOM);
   }

   public static double getLerpProgress(double value, double start, double end) {
      return (value - start) / (end - start);
   }

   public static double atan2(double y, double x) {
      double _snowman = x * x + y * y;
      if (Double.isNaN(_snowman)) {
         return Double.NaN;
      } else {
         boolean _snowmanx = y < 0.0;
         if (_snowmanx) {
            y = -y;
         }

         boolean _snowmanxx = x < 0.0;
         if (_snowmanxx) {
            x = -x;
         }

         boolean _snowmanxxx = y > x;
         if (_snowmanxxx) {
            double _snowmanxxxx = x;
            x = y;
            y = _snowmanxxxx;
         }

         double _snowmanxxxx = fastInverseSqrt(_snowman);
         x *= _snowmanxxxx;
         y *= _snowmanxxxx;
         double _snowmanxxxxx = SMALLEST_FRACTION_FREE_DOUBLE + y;
         int _snowmanxxxxxx = (int)Double.doubleToRawLongBits(_snowmanxxxxx);
         double _snowmanxxxxxxx = ARCSINE_TABLE[_snowmanxxxxxx];
         double _snowmanxxxxxxxx = COSINE_TABLE[_snowmanxxxxxx];
         double _snowmanxxxxxxxxx = _snowmanxxxxx - SMALLEST_FRACTION_FREE_DOUBLE;
         double _snowmanxxxxxxxxxx = y * _snowmanxxxxxxxx - x * _snowmanxxxxxxxxx;
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

   public static float fastInverseSqrt(float x) {
      float _snowman = 0.5F * x;
      int _snowmanx = Float.floatToIntBits(x);
      _snowmanx = 1597463007 - (_snowmanx >> 1);
      x = Float.intBitsToFloat(_snowmanx);
      return x * (1.5F - _snowman * x * x);
   }

   public static double fastInverseSqrt(double x) {
      double _snowman = 0.5 * x;
      long _snowmanx = Double.doubleToRawLongBits(x);
      _snowmanx = 6910469410427058090L - (_snowmanx >> 1);
      x = Double.longBitsToDouble(_snowmanx);
      return x * (1.5 - _snowman * x * x);
   }

   public static float fastInverseCbrt(float x) {
      int _snowman = Float.floatToIntBits(x);
      _snowman = 1419967116 - _snowman / 3;
      float _snowmanx = Float.intBitsToFloat(_snowman);
      _snowmanx = 0.6666667F * _snowmanx + 1.0F / (3.0F * _snowmanx * _snowmanx * x);
      return 0.6666667F * _snowmanx + 1.0F / (3.0F * _snowmanx * _snowmanx * x);
   }

   public static int hsvToRgb(float hue, float saturation, float value) {
      int _snowman = (int)(hue * 6.0F) % 6;
      float _snowmanx = hue * 6.0F - (float)_snowman;
      float _snowmanxx = value * (1.0F - saturation);
      float _snowmanxxx = value * (1.0F - _snowmanx * saturation);
      float _snowmanxxxx = value * (1.0F - (1.0F - _snowmanx) * saturation);
      float _snowmanxxxxx;
      float _snowmanxxxxxx;
      float _snowmanxxxxxxx;
      switch (_snowman) {
         case 0:
            _snowmanxxxxx = value;
            _snowmanxxxxxx = _snowmanxxxx;
            _snowmanxxxxxxx = _snowmanxx;
            break;
         case 1:
            _snowmanxxxxx = _snowmanxxx;
            _snowmanxxxxxx = value;
            _snowmanxxxxxxx = _snowmanxx;
            break;
         case 2:
            _snowmanxxxxx = _snowmanxx;
            _snowmanxxxxxx = value;
            _snowmanxxxxxxx = _snowmanxxxx;
            break;
         case 3:
            _snowmanxxxxx = _snowmanxx;
            _snowmanxxxxxx = _snowmanxxx;
            _snowmanxxxxxxx = value;
            break;
         case 4:
            _snowmanxxxxx = _snowmanxxxx;
            _snowmanxxxxxx = _snowmanxx;
            _snowmanxxxxxxx = value;
            break;
         case 5:
            _snowmanxxxxx = value;
            _snowmanxxxxxx = _snowmanxx;
            _snowmanxxxxxxx = _snowmanxxx;
            break;
         default:
            throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
      }

      int _snowman = clamp((int)(_snowmanxxxxx * 255.0F), 0, 255);
      int _snowmanx = clamp((int)(_snowmanxxxxxx * 255.0F), 0, 255);
      int _snowmanxx = clamp((int)(_snowmanxxxxxxx * 255.0F), 0, 255);
      return _snowman << 16 | _snowmanx << 8 | _snowmanxx;
   }

   public static int idealHash(int _snowman) {
      _snowman ^= _snowman >>> 16;
      _snowman *= -2048144789;
      _snowman ^= _snowman >>> 13;
      _snowman *= -1028477387;
      return _snowman ^ _snowman >>> 16;
   }

   public static int binarySearch(int start, int end, IntPredicate leftPredicate) {
      int _snowman = end - start;

      while (_snowman > 0) {
         int _snowmanx = _snowman / 2;
         int _snowmanxx = start + _snowmanx;
         if (leftPredicate.test(_snowmanxx)) {
            _snowman = _snowmanx;
         } else {
            start = _snowmanxx + 1;
            _snowman -= _snowmanx + 1;
         }
      }

      return start;
   }

   public static float lerp(float delta, float start, float end) {
      return start + delta * (end - start);
   }

   public static double lerp(double delta, double start, double end) {
      return start + delta * (end - start);
   }

   public static double lerp2(double deltaX, double deltaY, double val00, double val10, double val01, double val11) {
      return lerp(deltaY, lerp(deltaX, val00, val10), lerp(deltaX, val01, val11));
   }

   public static double lerp3(
      double deltaX,
      double deltaY,
      double deltaZ,
      double val000,
      double val100,
      double val010,
      double val110,
      double val001,
      double val101,
      double val011,
      double val111
   ) {
      return lerp(deltaZ, lerp2(deltaX, deltaY, val000, val100, val010, val110), lerp2(deltaX, deltaY, val001, val101, val011, val111));
   }

   public static double perlinFade(double _snowman) {
      return _snowman * _snowman * _snowman * (_snowman * (_snowman * 6.0 - 15.0) + 10.0);
   }

   public static int sign(double _snowman) {
      if (_snowman == 0.0) {
         return 0;
      } else {
         return _snowman > 0.0 ? 1 : -1;
      }
   }

   public static float lerpAngleDegrees(float delta, float start, float end) {
      return start + delta * wrapDegrees(end - start);
   }

   @Deprecated
   public static float lerpAngle(float start, float end, float delta) {
      float _snowman = end - start;

      while (_snowman < -180.0F) {
         _snowman += 360.0F;
      }

      while (_snowman >= 180.0F) {
         _snowman -= 360.0F;
      }

      return start + delta * _snowman;
   }

   @Deprecated
   public static float fwrapDegrees(double degrees) {
      while (degrees >= 180.0) {
         degrees -= 360.0;
      }

      while (degrees < -180.0) {
         degrees += 360.0;
      }

      return (float)degrees;
   }

   public static float method_24504(float _snowman, float _snowman) {
      return (Math.abs(_snowman % _snowman - _snowman * 0.5F) - _snowman * 0.25F) / (_snowman * 0.25F);
   }

   public static float square(float n) {
      return n * n;
   }

   static {
      for (int _snowman = 0; _snowman < 257; _snowman++) {
         double _snowmanx = (double)_snowman / 256.0;
         double _snowmanxx = Math.asin(_snowmanx);
         COSINE_TABLE[_snowman] = Math.cos(_snowmanxx);
         ARCSINE_TABLE[_snowman] = _snowmanxx;
      }
   }
}
