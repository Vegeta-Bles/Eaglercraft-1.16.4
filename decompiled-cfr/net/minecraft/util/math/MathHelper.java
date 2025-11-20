/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.math.NumberUtils
 */
package net.minecraft.util.math;

import java.util.Random;
import java.util.UUID;
import java.util.function.IntPredicate;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3i;
import org.apache.commons.lang3.math.NumberUtils;

public class MathHelper {
    public static final float SQUARE_ROOT_OF_TWO = MathHelper.sqrt(2.0f);
    private static final float[] SINE_TABLE = Util.make(new float[65536], fArray -> {
        for (int i = 0; i < ((float[])fArray).length; ++i) {
            fArray[i] = (float)Math.sin((double)i * Math.PI * 2.0 / 65536.0);
        }
    });
    private static final Random RANDOM = new Random();
    private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
    private static final double SMALLEST_FRACTION_FREE_DOUBLE = Double.longBitsToDouble(4805340802404319232L);
    private static final double[] ARCSINE_TABLE = new double[257];
    private static final double[] COSINE_TABLE = new double[257];

    public static float sin(float f) {
        return SINE_TABLE[(int)(f * 10430.378f) & 0xFFFF];
    }

    public static float cos(float f) {
        return SINE_TABLE[(int)(f * 10430.378f + 16384.0f) & 0xFFFF];
    }

    public static float sqrt(float f) {
        return (float)Math.sqrt(f);
    }

    public static float sqrt(double d) {
        return (float)Math.sqrt(d);
    }

    public static int floor(float f) {
        int n = (int)f;
        return f < (float)n ? n - 1 : n;
    }

    public static int fastFloor(double d) {
        return (int)(d + 1024.0) - 1024;
    }

    public static int floor(double d) {
        int n = (int)d;
        return d < (double)n ? n - 1 : n;
    }

    public static long lfloor(double d) {
        long l = (long)d;
        return d < (double)l ? l - 1L : l;
    }

    public static float abs(float f) {
        return Math.abs(f);
    }

    public static int abs(int n) {
        return Math.abs(n);
    }

    public static int ceil(float f) {
        int n = (int)f;
        return f > (float)n ? n + 1 : n;
    }

    public static int ceil(double d) {
        int n = (int)d;
        return d > (double)n ? n + 1 : n;
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static long clamp(long value, long min, long max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static double clamp(double value, double min, double max) {
        if (value < min) {
            return min;
        }
        if (value > max) {
            return max;
        }
        return value;
    }

    public static double clampedLerp(double start, double end, double delta) {
        if (delta < 0.0) {
            return start;
        }
        if (delta > 1.0) {
            return end;
        }
        return MathHelper.lerp(delta, start, end);
    }

    public static double absMax(double d, double d2) {
        if (d < 0.0) {
            d = -d;
        }
        if (d2 < 0.0) {
            d2 = -d2;
        }
        return d > d2 ? d : d2;
    }

    public static int floorDiv(int n, int n2) {
        return Math.floorDiv(n, n2);
    }

    public static int nextInt(Random random, int min, int max) {
        if (min >= max) {
            return min;
        }
        return random.nextInt(max - min + 1) + min;
    }

    public static float nextFloat(Random random, float min, float max) {
        if (min >= max) {
            return min;
        }
        return random.nextFloat() * (max - min) + min;
    }

    public static double nextDouble(Random random, double min, double max) {
        if (min >= max) {
            return min;
        }
        return random.nextDouble() * (max - min) + min;
    }

    public static double average(long[] array) {
        long l = 0L;
        for (long l2 : array) {
            l += l2;
        }
        return (double)l / (double)array.length;
    }

    public static boolean approximatelyEquals(float a, float b) {
        return Math.abs(b - a) < 1.0E-5f;
    }

    public static boolean approximatelyEquals(double a, double b) {
        return Math.abs(b - a) < (double)1.0E-5f;
    }

    public static int floorMod(int n, int n2) {
        return Math.floorMod(n, n2);
    }

    public static float floorMod(float f, float f2) {
        return (f % f2 + f2) % f2;
    }

    public static double floorMod(double d, double d2) {
        return (d % d2 + d2) % d2;
    }

    public static int wrapDegrees(int n) {
        _snowman = n % 360;
        if (_snowman >= 180) {
            _snowman -= 360;
        }
        if (_snowman < -180) {
            _snowman += 360;
        }
        return _snowman;
    }

    public static float wrapDegrees(float f) {
        _snowman = f % 360.0f;
        if (_snowman >= 180.0f) {
            _snowman -= 360.0f;
        }
        if (_snowman < -180.0f) {
            _snowman += 360.0f;
        }
        return _snowman;
    }

    public static double wrapDegrees(double d) {
        _snowman = d % 360.0;
        if (_snowman >= 180.0) {
            _snowman -= 360.0;
        }
        if (_snowman < -180.0) {
            _snowman += 360.0;
        }
        return _snowman;
    }

    public static float subtractAngles(float start, float end) {
        return MathHelper.wrapDegrees(end - start);
    }

    public static float angleBetween(float first, float second) {
        return MathHelper.abs(MathHelper.subtractAngles(first, second));
    }

    public static float stepAngleTowards(float from, float to, float step) {
        float f = MathHelper.subtractAngles(from, to);
        _snowman = MathHelper.clamp(f, -step, step);
        return to - _snowman;
    }

    public static float stepTowards(float from, float to, float step) {
        step = MathHelper.abs(step);
        if (from < to) {
            return MathHelper.clamp(from + step, from, to);
        }
        return MathHelper.clamp(from - step, to, from);
    }

    public static float stepUnwrappedAngleTowards(float from, float to, float step) {
        float f = MathHelper.subtractAngles(from, to);
        return MathHelper.stepTowards(from, from + f, step);
    }

    public static int parseInt(String string, int fallback) {
        return NumberUtils.toInt((String)string, (int)fallback);
    }

    public static int smallestEncompassingPowerOfTwo(int value) {
        int n = value - 1;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        return n + 1;
    }

    public static boolean isPowerOfTwo(int n) {
        return n != 0 && (n & n - 1) == 0;
    }

    public static int log2DeBruijn(int n) {
        n = MathHelper.isPowerOfTwo(n) ? n : MathHelper.smallestEncompassingPowerOfTwo(n);
        return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)((long)n * 125613361L >> 27) & 0x1F];
    }

    public static int log2(int n) {
        return MathHelper.log2DeBruijn(n) - (MathHelper.isPowerOfTwo(n) ? 0 : 1);
    }

    public static int roundUpToMultiple(int value, int divisor) {
        int n;
        if (divisor == 0) {
            return 0;
        }
        if (value == 0) {
            return divisor;
        }
        if (value < 0) {
            divisor *= -1;
        }
        if ((n = value % divisor) == 0) {
            return value;
        }
        return value + divisor - n;
    }

    public static int packRgb(float r, float g, float b) {
        return MathHelper.packRgb(MathHelper.floor(r * 255.0f), MathHelper.floor(g * 255.0f), MathHelper.floor(b * 255.0f));
    }

    public static int packRgb(int r, int g, int b) {
        int n = r;
        n = (n << 8) + g;
        n = (n << 8) + b;
        return n;
    }

    public static float fractionalPart(float value) {
        return value - (float)MathHelper.floor(value);
    }

    public static double fractionalPart(double value) {
        return value - (double)MathHelper.lfloor(value);
    }

    public static long hashCode(Vec3i vec) {
        return MathHelper.hashCode(vec.getX(), vec.getY(), vec.getZ());
    }

    public static long hashCode(int x, int y, int z) {
        long l = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
        l = l * l * 42317861L + l * 11L;
        return l >> 16;
    }

    public static UUID randomUuid(Random random) {
        long l = random.nextLong() & 0xFFFFFFFFFFFF0FFFL | 0x4000L;
        _snowman = random.nextLong() & 0x3FFFFFFFFFFFFFFFL | Long.MIN_VALUE;
        return new UUID(l, _snowman);
    }

    public static UUID randomUuid() {
        return MathHelper.randomUuid(RANDOM);
    }

    public static double getLerpProgress(double value, double start, double end) {
        return (value - start) / (end - start);
    }

    public static double atan2(double y, double x) {
        double d = x * x + y * y;
        if (Double.isNaN(d)) {
            return Double.NaN;
        }
        boolean bl = _snowman = y < 0.0;
        if (_snowman) {
            y = -y;
        }
        boolean bl2 = _snowman = x < 0.0;
        if (_snowman) {
            x = -x;
        }
        boolean bl3 = _snowman = y > x;
        if (_snowman) {
            _snowman = x;
            x = y;
            y = _snowman;
        }
        _snowman = MathHelper.fastInverseSqrt(d);
        x *= _snowman;
        _snowman = SMALLEST_FRACTION_FREE_DOUBLE + (y *= _snowman);
        int _snowman2 = (int)Double.doubleToRawLongBits(_snowman);
        _snowman = ARCSINE_TABLE[_snowman2];
        _snowman = COSINE_TABLE[_snowman2];
        _snowman = _snowman - SMALLEST_FRACTION_FREE_DOUBLE;
        _snowman = y * _snowman - x * _snowman;
        _snowman = (6.0 + _snowman * _snowman) * _snowman * 0.16666666666666666;
        _snowman = _snowman + _snowman;
        if (_snowman) {
            _snowman = 1.5707963267948966 - _snowman;
        }
        if (_snowman) {
            _snowman = Math.PI - _snowman;
        }
        if (_snowman) {
            _snowman = -_snowman;
        }
        return _snowman;
    }

    public static float fastInverseSqrt(float x) {
        float f = 0.5f * x;
        int _snowman2 = Float.floatToIntBits(x);
        _snowman2 = 1597463007 - (_snowman2 >> 1);
        x = Float.intBitsToFloat(_snowman2);
        x *= 1.5f - f * x * x;
        return x;
    }

    public static double fastInverseSqrt(double x) {
        double d = 0.5 * x;
        long _snowman2 = Double.doubleToRawLongBits(x);
        _snowman2 = 6910469410427058090L - (_snowman2 >> 1);
        x = Double.longBitsToDouble(_snowman2);
        x *= 1.5 - d * x * x;
        return x;
    }

    public static float fastInverseCbrt(float x) {
        int n = Float.floatToIntBits(x);
        n = 1419967116 - n / 3;
        float _snowman2 = Float.intBitsToFloat(n);
        _snowman2 = 0.6666667f * _snowman2 + 1.0f / (3.0f * _snowman2 * _snowman2 * x);
        _snowman2 = 0.6666667f * _snowman2 + 1.0f / (3.0f * _snowman2 * _snowman2 * x);
        return _snowman2;
    }

    public static int hsvToRgb(float hue, float saturation, float value) {
        float f;
        int n = (int)(hue * 6.0f) % 6;
        float _snowman2 = hue * 6.0f - (float)n;
        float _snowman3 = value * (1.0f - saturation);
        float _snowman4 = value * (1.0f - _snowman2 * saturation);
        float _snowman5 = value * (1.0f - (1.0f - _snowman2) * saturation);
        switch (n) {
            case 0: {
                f = value;
                _snowman = _snowman5;
                _snowman = _snowman3;
                break;
            }
            case 1: {
                f = _snowman4;
                _snowman = value;
                _snowman = _snowman3;
                break;
            }
            case 2: {
                f = _snowman3;
                _snowman = value;
                _snowman = _snowman5;
                break;
            }
            case 3: {
                f = _snowman3;
                _snowman = _snowman4;
                _snowman = value;
                break;
            }
            case 4: {
                f = _snowman5;
                _snowman = _snowman3;
                _snowman = value;
                break;
            }
            case 5: {
                f = value;
                _snowman = _snowman3;
                _snowman = _snowman4;
                break;
            }
            default: {
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
            }
        }
        int _snowman6 = MathHelper.clamp((int)(f * 255.0f), 0, 255);
        int _snowman7 = MathHelper.clamp((int)(_snowman * 255.0f), 0, 255);
        int _snowman8 = MathHelper.clamp((int)(_snowman * 255.0f), 0, 255);
        return _snowman6 << 16 | _snowman7 << 8 | _snowman8;
    }

    public static int idealHash(int n) {
        n ^= n >>> 16;
        n *= -2048144789;
        n ^= n >>> 13;
        n *= -1028477387;
        n ^= n >>> 16;
        return n;
    }

    public static int binarySearch(int start, int end, IntPredicate leftPredicate) {
        int n = end - start;
        while (n > 0) {
            _snowman = n / 2;
            _snowman = start + _snowman;
            if (leftPredicate.test(_snowman)) {
                n = _snowman;
                continue;
            }
            start = _snowman + 1;
            n -= _snowman + 1;
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
        return MathHelper.lerp(deltaY, MathHelper.lerp(deltaX, val00, val10), MathHelper.lerp(deltaX, val01, val11));
    }

    public static double lerp3(double deltaX, double deltaY, double deltaZ, double val000, double val100, double val010, double val110, double val001, double val101, double val011, double val111) {
        return MathHelper.lerp(deltaZ, MathHelper.lerp2(deltaX, deltaY, val000, val100, val010, val110), MathHelper.lerp2(deltaX, deltaY, val001, val101, val011, val111));
    }

    public static double perlinFade(double d) {
        return d * d * d * (d * (d * 6.0 - 15.0) + 10.0);
    }

    public static int sign(double d) {
        if (d == 0.0) {
            return 0;
        }
        return d > 0.0 ? 1 : -1;
    }

    public static float lerpAngleDegrees(float delta, float start, float end) {
        return start + delta * MathHelper.wrapDegrees(end - start);
    }

    @Deprecated
    public static float lerpAngle(float start, float end, float delta) {
        float f;
        for (f = end - start; f < -180.0f; f += 360.0f) {
        }
        while (f >= 180.0f) {
            f -= 360.0f;
        }
        return start + delta * f;
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

    public static float method_24504(float f, float f2) {
        return (Math.abs(f % f2 - f2 * 0.5f) - f2 * 0.25f) / (f2 * 0.25f);
    }

    public static float square(float n) {
        return n * n;
    }

    static {
        for (int i = 0; i < 257; ++i) {
            double d = (double)i / 256.0;
            _snowman = Math.asin(d);
            MathHelper.COSINE_TABLE[i] = Math.cos(_snowman);
            MathHelper.ARCSINE_TABLE[i] = _snowman;
        }
    }
}

