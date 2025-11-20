/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  org.apache.commons.lang3.tuple.Triple
 */
package net.minecraft.util.math;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import org.apache.commons.lang3.tuple.Triple;

public final class Matrix3f {
    private static final float THREE_PLUS_TWO_SQRT_TWO = 3.0f + 2.0f * (float)Math.sqrt(2.0);
    private static final float COS_PI_OVER_EIGHT = (float)Math.cos(0.39269908169872414);
    private static final float SIN_PI_OVER_EIGHT = (float)Math.sin(0.39269908169872414);
    private static final float SQRT_HALF = 1.0f / (float)Math.sqrt(2.0);
    protected float a00;
    protected float a01;
    protected float a02;
    protected float a10;
    protected float a11;
    protected float a12;
    protected float a20;
    protected float a21;
    protected float a22;

    public Matrix3f() {
    }

    public Matrix3f(Quaternion source) {
        float f = source.getX();
        _snowman = source.getY();
        _snowman = source.getZ();
        _snowman = source.getW();
        _snowman = 2.0f * f * f;
        _snowman = 2.0f * _snowman * _snowman;
        _snowman = 2.0f * _snowman * _snowman;
        this.a00 = 1.0f - _snowman - _snowman;
        this.a11 = 1.0f - _snowman - _snowman;
        this.a22 = 1.0f - _snowman - _snowman;
        _snowman = f * _snowman;
        _snowman = _snowman * _snowman;
        _snowman = _snowman * f;
        _snowman = f * _snowman;
        _snowman = _snowman * _snowman;
        _snowman = _snowman * _snowman;
        this.a10 = 2.0f * (_snowman + _snowman);
        this.a01 = 2.0f * (_snowman - _snowman);
        this.a20 = 2.0f * (_snowman - _snowman);
        this.a02 = 2.0f * (_snowman + _snowman);
        this.a21 = 2.0f * (_snowman + _snowman);
        this.a12 = 2.0f * (_snowman - _snowman);
    }

    public static Matrix3f scale(float x, float y, float z) {
        Matrix3f matrix3f = new Matrix3f();
        matrix3f.a00 = x;
        matrix3f.a11 = y;
        matrix3f.a22 = z;
        return matrix3f;
    }

    public Matrix3f(Matrix4f source) {
        this.a00 = source.a00;
        this.a01 = source.a01;
        this.a02 = source.a02;
        this.a10 = source.a10;
        this.a11 = source.a11;
        this.a12 = source.a12;
        this.a20 = source.a20;
        this.a21 = source.a21;
        this.a22 = source.a22;
    }

    public Matrix3f(Matrix3f source) {
        this.a00 = source.a00;
        this.a01 = source.a01;
        this.a02 = source.a02;
        this.a10 = source.a10;
        this.a11 = source.a11;
        this.a12 = source.a12;
        this.a20 = source.a20;
        this.a21 = source.a21;
        this.a22 = source.a22;
    }

    private static Pair<Float, Float> getSinAndCosOfRotation(float upperLeft, float diagonalAverage, float lowerRight) {
        float f = diagonalAverage;
        _snowman = 2.0f * (upperLeft - lowerRight);
        if (THREE_PLUS_TWO_SQRT_TWO * f * f < _snowman * _snowman) {
            _snowman = MathHelper.fastInverseSqrt(f * f + _snowman * _snowman);
            return Pair.of((Object)Float.valueOf(_snowman * f), (Object)Float.valueOf(_snowman * _snowman));
        }
        return Pair.of((Object)Float.valueOf(SIN_PI_OVER_EIGHT), (Object)Float.valueOf(COS_PI_OVER_EIGHT));
    }

    private static Pair<Float, Float> method_22848(float f, float f2) {
        _snowman = (float)Math.hypot(f, f2);
        _snowman = _snowman > 1.0E-6f ? f2 : 0.0f;
        _snowman = Math.abs(f) + Math.max(_snowman, 1.0E-6f);
        if (f < 0.0f) {
            _snowman = _snowman;
            _snowman = _snowman;
            _snowman = _snowman;
        }
        _snowman = MathHelper.fastInverseSqrt(_snowman * _snowman + _snowman * _snowman);
        return Pair.of((Object)Float.valueOf(_snowman *= _snowman), (Object)Float.valueOf(_snowman *= _snowman));
    }

    private static Quaternion method_22857(Matrix3f matrix3f) {
        float _snowman7;
        float _snowman6;
        float _snowman5;
        Quaternion _snowman4;
        Float _snowman3;
        Float _snowman2;
        _snowman = new Matrix3f();
        Quaternion quaternion = Quaternion.IDENTITY.copy();
        if (matrix3f.a01 * matrix3f.a01 + matrix3f.a10 * matrix3f.a10 > 1.0E-6f) {
            Pair<Float, Float> pair = Matrix3f.getSinAndCosOfRotation(matrix3f.a00, 0.5f * (matrix3f.a01 + matrix3f.a10), matrix3f.a11);
            _snowman2 = (Float)pair.getFirst();
            _snowman3 = (Float)pair.getSecond();
            _snowman4 = new Quaternion(0.0f, 0.0f, _snowman2.floatValue(), _snowman3.floatValue());
            _snowman5 = _snowman3.floatValue() * _snowman3.floatValue() - _snowman2.floatValue() * _snowman2.floatValue();
            _snowman6 = -2.0f * _snowman2.floatValue() * _snowman3.floatValue();
            _snowman7 = _snowman3.floatValue() * _snowman3.floatValue() + _snowman2.floatValue() * _snowman2.floatValue();
            quaternion.hamiltonProduct(_snowman4);
            _snowman.loadIdentity();
            _snowman.a00 = _snowman5;
            _snowman.a11 = _snowman5;
            _snowman.a10 = -_snowman6;
            _snowman.a01 = _snowman6;
            _snowman.a22 = _snowman7;
            matrix3f.multiply(_snowman);
            _snowman.transpose();
            _snowman.multiply(matrix3f);
            matrix3f.load(_snowman);
        }
        if (matrix3f.a02 * matrix3f.a02 + matrix3f.a20 * matrix3f.a20 > 1.0E-6f) {
            pair = Matrix3f.getSinAndCosOfRotation(matrix3f.a00, 0.5f * (matrix3f.a02 + matrix3f.a20), matrix3f.a22);
            float _snowman8 = -((Float)pair.getFirst()).floatValue();
            _snowman3 = (Float)pair.getSecond();
            _snowman4 = new Quaternion(0.0f, _snowman8, 0.0f, _snowman3.floatValue());
            _snowman5 = _snowman3.floatValue() * _snowman3.floatValue() - _snowman8 * _snowman8;
            _snowman6 = -2.0f * _snowman8 * _snowman3.floatValue();
            _snowman7 = _snowman3.floatValue() * _snowman3.floatValue() + _snowman8 * _snowman8;
            quaternion.hamiltonProduct(_snowman4);
            _snowman.loadIdentity();
            _snowman.a00 = _snowman5;
            _snowman.a22 = _snowman5;
            _snowman.a20 = _snowman6;
            _snowman.a02 = -_snowman6;
            _snowman.a11 = _snowman7;
            matrix3f.multiply(_snowman);
            _snowman.transpose();
            _snowman.multiply(matrix3f);
            matrix3f.load(_snowman);
        }
        if (matrix3f.a12 * matrix3f.a12 + matrix3f.a21 * matrix3f.a21 > 1.0E-6f) {
            pair = Matrix3f.getSinAndCosOfRotation(matrix3f.a11, 0.5f * (matrix3f.a12 + matrix3f.a21), matrix3f.a22);
            _snowman2 = (Float)pair.getFirst();
            _snowman3 = (Float)pair.getSecond();
            _snowman4 = new Quaternion(_snowman2.floatValue(), 0.0f, 0.0f, _snowman3.floatValue());
            _snowman5 = _snowman3.floatValue() * _snowman3.floatValue() - _snowman2.floatValue() * _snowman2.floatValue();
            _snowman6 = -2.0f * _snowman2.floatValue() * _snowman3.floatValue();
            _snowman7 = _snowman3.floatValue() * _snowman3.floatValue() + _snowman2.floatValue() * _snowman2.floatValue();
            quaternion.hamiltonProduct(_snowman4);
            _snowman.loadIdentity();
            _snowman.a11 = _snowman5;
            _snowman.a22 = _snowman5;
            _snowman.a21 = -_snowman6;
            _snowman.a12 = _snowman6;
            _snowman.a00 = _snowman7;
            matrix3f.multiply(_snowman);
            _snowman.transpose();
            _snowman.multiply(matrix3f);
            matrix3f.load(_snowman);
        }
        return quaternion;
    }

    public void transpose() {
        float f = this.a01;
        this.a01 = this.a10;
        this.a10 = f;
        f = this.a02;
        this.a02 = this.a20;
        this.a20 = f;
        f = this.a12;
        this.a12 = this.a21;
        this.a21 = f;
    }

    public Triple<Quaternion, Vector3f, Quaternion> decomposeLinearTransformation() {
        Quaternion quaternion;
        Quaternion quaternion2 = Quaternion.IDENTITY.copy();
        quaternion = Quaternion.IDENTITY.copy();
        Matrix3f _snowman2 = this.copy();
        _snowman2.transpose();
        _snowman2.multiply(this);
        for (int i = 0; i < 5; ++i) {
            quaternion.hamiltonProduct(Matrix3f.method_22857(_snowman2));
        }
        quaternion.normalize();
        Matrix3f _snowman3 = new Matrix3f(this);
        _snowman3.multiply(new Matrix3f(quaternion));
        float _snowman4 = 1.0f;
        Pair<Float, Float> _snowman5 = Matrix3f.method_22848(_snowman3.a00, _snowman3.a10);
        Float _snowman6 = (Float)_snowman5.getFirst();
        Float _snowman7 = (Float)_snowman5.getSecond();
        float _snowman8 = _snowman7.floatValue() * _snowman7.floatValue() - _snowman6.floatValue() * _snowman6.floatValue();
        float _snowman9 = -2.0f * _snowman6.floatValue() * _snowman7.floatValue();
        float _snowman10 = _snowman7.floatValue() * _snowman7.floatValue() + _snowman6.floatValue() * _snowman6.floatValue();
        _snowman = new Quaternion(0.0f, 0.0f, _snowman6.floatValue(), _snowman7.floatValue());
        quaternion2.hamiltonProduct(_snowman);
        Matrix3f _snowman11 = new Matrix3f();
        _snowman11.loadIdentity();
        _snowman11.a00 = _snowman8;
        _snowman11.a11 = _snowman8;
        _snowman11.a10 = _snowman9;
        _snowman11.a01 = -_snowman9;
        _snowman11.a22 = _snowman10;
        _snowman4 *= _snowman10;
        _snowman11.multiply(_snowman3);
        _snowman5 = Matrix3f.method_22848(_snowman11.a00, _snowman11.a20);
        float _snowman12 = -((Float)_snowman5.getFirst()).floatValue();
        Float _snowman13 = (Float)_snowman5.getSecond();
        float _snowman14 = _snowman13.floatValue() * _snowman13.floatValue() - _snowman12 * _snowman12;
        float _snowman15 = -2.0f * _snowman12 * _snowman13.floatValue();
        float _snowman16 = _snowman13.floatValue() * _snowman13.floatValue() + _snowman12 * _snowman12;
        _snowman = new Quaternion(0.0f, _snowman12, 0.0f, _snowman13.floatValue());
        quaternion2.hamiltonProduct(_snowman);
        Matrix3f _snowman17 = new Matrix3f();
        _snowman17.loadIdentity();
        _snowman17.a00 = _snowman14;
        _snowman17.a22 = _snowman14;
        _snowman17.a20 = -_snowman15;
        _snowman17.a02 = _snowman15;
        _snowman17.a11 = _snowman16;
        _snowman4 *= _snowman16;
        _snowman17.multiply(_snowman11);
        _snowman5 = Matrix3f.method_22848(_snowman17.a11, _snowman17.a21);
        Float _snowman18 = (Float)_snowman5.getFirst();
        Float _snowman19 = (Float)_snowman5.getSecond();
        float _snowman20 = _snowman19.floatValue() * _snowman19.floatValue() - _snowman18.floatValue() * _snowman18.floatValue();
        float _snowman21 = -2.0f * _snowman18.floatValue() * _snowman19.floatValue();
        float _snowman22 = _snowman19.floatValue() * _snowman19.floatValue() + _snowman18.floatValue() * _snowman18.floatValue();
        _snowman = new Quaternion(_snowman18.floatValue(), 0.0f, 0.0f, _snowman19.floatValue());
        quaternion2.hamiltonProduct(_snowman);
        Matrix3f _snowman23 = new Matrix3f();
        _snowman23.loadIdentity();
        _snowman23.a11 = _snowman20;
        _snowman23.a22 = _snowman20;
        _snowman23.a21 = _snowman21;
        _snowman23.a12 = -_snowman21;
        _snowman23.a00 = _snowman22;
        _snowman4 *= _snowman22;
        _snowman23.multiply(_snowman17);
        _snowman4 = 1.0f / _snowman4;
        quaternion2.scale((float)Math.sqrt(_snowman4));
        Vector3f _snowman24 = new Vector3f(_snowman23.a00 * _snowman4, _snowman23.a11 * _snowman4, _snowman23.a22 * _snowman4);
        return Triple.of((Object)quaternion2, (Object)_snowman24, (Object)quaternion);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Matrix3f matrix3f = (Matrix3f)object;
        return Float.compare(matrix3f.a00, this.a00) == 0 && Float.compare(matrix3f.a01, this.a01) == 0 && Float.compare(matrix3f.a02, this.a02) == 0 && Float.compare(matrix3f.a10, this.a10) == 0 && Float.compare(matrix3f.a11, this.a11) == 0 && Float.compare(matrix3f.a12, this.a12) == 0 && Float.compare(matrix3f.a20, this.a20) == 0 && Float.compare(matrix3f.a21, this.a21) == 0 && Float.compare(matrix3f.a22, this.a22) == 0;
    }

    public int hashCode() {
        int n = this.a00 != 0.0f ? Float.floatToIntBits(this.a00) : 0;
        n = 31 * n + (this.a01 != 0.0f ? Float.floatToIntBits(this.a01) : 0);
        n = 31 * n + (this.a02 != 0.0f ? Float.floatToIntBits(this.a02) : 0);
        n = 31 * n + (this.a10 != 0.0f ? Float.floatToIntBits(this.a10) : 0);
        n = 31 * n + (this.a11 != 0.0f ? Float.floatToIntBits(this.a11) : 0);
        n = 31 * n + (this.a12 != 0.0f ? Float.floatToIntBits(this.a12) : 0);
        n = 31 * n + (this.a20 != 0.0f ? Float.floatToIntBits(this.a20) : 0);
        n = 31 * n + (this.a21 != 0.0f ? Float.floatToIntBits(this.a21) : 0);
        n = 31 * n + (this.a22 != 0.0f ? Float.floatToIntBits(this.a22) : 0);
        return n;
    }

    public void load(Matrix3f source) {
        this.a00 = source.a00;
        this.a01 = source.a01;
        this.a02 = source.a02;
        this.a10 = source.a10;
        this.a11 = source.a11;
        this.a12 = source.a12;
        this.a20 = source.a20;
        this.a21 = source.a21;
        this.a22 = source.a22;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Matrix3f:\n");
        stringBuilder.append(this.a00);
        stringBuilder.append(" ");
        stringBuilder.append(this.a01);
        stringBuilder.append(" ");
        stringBuilder.append(this.a02);
        stringBuilder.append("\n");
        stringBuilder.append(this.a10);
        stringBuilder.append(" ");
        stringBuilder.append(this.a11);
        stringBuilder.append(" ");
        stringBuilder.append(this.a12);
        stringBuilder.append("\n");
        stringBuilder.append(this.a20);
        stringBuilder.append(" ");
        stringBuilder.append(this.a21);
        stringBuilder.append(" ");
        stringBuilder.append(this.a22);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public void loadIdentity() {
        this.a00 = 1.0f;
        this.a01 = 0.0f;
        this.a02 = 0.0f;
        this.a10 = 0.0f;
        this.a11 = 1.0f;
        this.a12 = 0.0f;
        this.a20 = 0.0f;
        this.a21 = 0.0f;
        this.a22 = 1.0f;
    }

    public float determinantAndAdjugate() {
        float f = this.a11 * this.a22 - this.a12 * this.a21;
        _snowman = -(this.a10 * this.a22 - this.a12 * this.a20);
        _snowman = this.a10 * this.a21 - this.a11 * this.a20;
        _snowman = -(this.a01 * this.a22 - this.a02 * this.a21);
        _snowman = this.a00 * this.a22 - this.a02 * this.a20;
        _snowman = -(this.a00 * this.a21 - this.a01 * this.a20);
        _snowman = this.a01 * this.a12 - this.a02 * this.a11;
        _snowman = -(this.a00 * this.a12 - this.a02 * this.a10);
        _snowman = this.a00 * this.a11 - this.a01 * this.a10;
        _snowman = this.a00 * f + this.a01 * _snowman + this.a02 * _snowman;
        this.a00 = f;
        this.a10 = _snowman;
        this.a20 = _snowman;
        this.a01 = _snowman;
        this.a11 = _snowman;
        this.a21 = _snowman;
        this.a02 = _snowman;
        this.a12 = _snowman;
        this.a22 = _snowman;
        return _snowman;
    }

    public boolean invert() {
        float f = this.determinantAndAdjugate();
        if (Math.abs(f) > 1.0E-6f) {
            this.multiply(f);
            return true;
        }
        return false;
    }

    public void set(int x, int y, float value) {
        if (x == 0) {
            if (y == 0) {
                this.a00 = value;
            } else if (y == 1) {
                this.a01 = value;
            } else {
                this.a02 = value;
            }
        } else if (x == 1) {
            if (y == 0) {
                this.a10 = value;
            } else if (y == 1) {
                this.a11 = value;
            } else {
                this.a12 = value;
            }
        } else if (y == 0) {
            this.a20 = value;
        } else if (y == 1) {
            this.a21 = value;
        } else {
            this.a22 = value;
        }
    }

    public void multiply(Matrix3f other) {
        float f = this.a00 * other.a00 + this.a01 * other.a10 + this.a02 * other.a20;
        _snowman = this.a00 * other.a01 + this.a01 * other.a11 + this.a02 * other.a21;
        _snowman = this.a00 * other.a02 + this.a01 * other.a12 + this.a02 * other.a22;
        _snowman = this.a10 * other.a00 + this.a11 * other.a10 + this.a12 * other.a20;
        _snowman = this.a10 * other.a01 + this.a11 * other.a11 + this.a12 * other.a21;
        _snowman = this.a10 * other.a02 + this.a11 * other.a12 + this.a12 * other.a22;
        _snowman = this.a20 * other.a00 + this.a21 * other.a10 + this.a22 * other.a20;
        _snowman = this.a20 * other.a01 + this.a21 * other.a11 + this.a22 * other.a21;
        _snowman = this.a20 * other.a02 + this.a21 * other.a12 + this.a22 * other.a22;
        this.a00 = f;
        this.a01 = _snowman;
        this.a02 = _snowman;
        this.a10 = _snowman;
        this.a11 = _snowman;
        this.a12 = _snowman;
        this.a20 = _snowman;
        this.a21 = _snowman;
        this.a22 = _snowman;
    }

    public void multiply(Quaternion quaternion) {
        this.multiply(new Matrix3f(quaternion));
    }

    public void multiply(float scalar) {
        this.a00 *= scalar;
        this.a01 *= scalar;
        this.a02 *= scalar;
        this.a10 *= scalar;
        this.a11 *= scalar;
        this.a12 *= scalar;
        this.a20 *= scalar;
        this.a21 *= scalar;
        this.a22 *= scalar;
    }

    public Matrix3f copy() {
        return new Matrix3f(this);
    }
}

