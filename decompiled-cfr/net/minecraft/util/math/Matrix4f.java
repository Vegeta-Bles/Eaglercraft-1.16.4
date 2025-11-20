/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.math;

import java.nio.FloatBuffer;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Quaternion;

public final class Matrix4f {
    protected float a00;
    protected float a01;
    protected float a02;
    protected float a03;
    protected float a10;
    protected float a11;
    protected float a12;
    protected float a13;
    protected float a20;
    protected float a21;
    protected float a22;
    protected float a23;
    protected float a30;
    protected float a31;
    protected float a32;
    protected float a33;

    public Matrix4f() {
    }

    public Matrix4f(Matrix4f source) {
        this.a00 = source.a00;
        this.a01 = source.a01;
        this.a02 = source.a02;
        this.a03 = source.a03;
        this.a10 = source.a10;
        this.a11 = source.a11;
        this.a12 = source.a12;
        this.a13 = source.a13;
        this.a20 = source.a20;
        this.a21 = source.a21;
        this.a22 = source.a22;
        this.a23 = source.a23;
        this.a30 = source.a30;
        this.a31 = source.a31;
        this.a32 = source.a32;
        this.a33 = source.a33;
    }

    public Matrix4f(Quaternion quaternion) {
        float f = quaternion.getX();
        _snowman = quaternion.getY();
        _snowman = quaternion.getZ();
        _snowman = quaternion.getW();
        _snowman = 2.0f * f * f;
        _snowman = 2.0f * _snowman * _snowman;
        _snowman = 2.0f * _snowman * _snowman;
        this.a00 = 1.0f - _snowman - _snowman;
        this.a11 = 1.0f - _snowman - _snowman;
        this.a22 = 1.0f - _snowman - _snowman;
        this.a33 = 1.0f;
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

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Matrix4f matrix4f = (Matrix4f)o;
        return Float.compare(matrix4f.a00, this.a00) == 0 && Float.compare(matrix4f.a01, this.a01) == 0 && Float.compare(matrix4f.a02, this.a02) == 0 && Float.compare(matrix4f.a03, this.a03) == 0 && Float.compare(matrix4f.a10, this.a10) == 0 && Float.compare(matrix4f.a11, this.a11) == 0 && Float.compare(matrix4f.a12, this.a12) == 0 && Float.compare(matrix4f.a13, this.a13) == 0 && Float.compare(matrix4f.a20, this.a20) == 0 && Float.compare(matrix4f.a21, this.a21) == 0 && Float.compare(matrix4f.a22, this.a22) == 0 && Float.compare(matrix4f.a23, this.a23) == 0 && Float.compare(matrix4f.a30, this.a30) == 0 && Float.compare(matrix4f.a31, this.a31) == 0 && Float.compare(matrix4f.a32, this.a32) == 0 && Float.compare(matrix4f.a33, this.a33) == 0;
    }

    public int hashCode() {
        int n = this.a00 != 0.0f ? Float.floatToIntBits(this.a00) : 0;
        n = 31 * n + (this.a01 != 0.0f ? Float.floatToIntBits(this.a01) : 0);
        n = 31 * n + (this.a02 != 0.0f ? Float.floatToIntBits(this.a02) : 0);
        n = 31 * n + (this.a03 != 0.0f ? Float.floatToIntBits(this.a03) : 0);
        n = 31 * n + (this.a10 != 0.0f ? Float.floatToIntBits(this.a10) : 0);
        n = 31 * n + (this.a11 != 0.0f ? Float.floatToIntBits(this.a11) : 0);
        n = 31 * n + (this.a12 != 0.0f ? Float.floatToIntBits(this.a12) : 0);
        n = 31 * n + (this.a13 != 0.0f ? Float.floatToIntBits(this.a13) : 0);
        n = 31 * n + (this.a20 != 0.0f ? Float.floatToIntBits(this.a20) : 0);
        n = 31 * n + (this.a21 != 0.0f ? Float.floatToIntBits(this.a21) : 0);
        n = 31 * n + (this.a22 != 0.0f ? Float.floatToIntBits(this.a22) : 0);
        n = 31 * n + (this.a23 != 0.0f ? Float.floatToIntBits(this.a23) : 0);
        n = 31 * n + (this.a30 != 0.0f ? Float.floatToIntBits(this.a30) : 0);
        n = 31 * n + (this.a31 != 0.0f ? Float.floatToIntBits(this.a31) : 0);
        n = 31 * n + (this.a32 != 0.0f ? Float.floatToIntBits(this.a32) : 0);
        n = 31 * n + (this.a33 != 0.0f ? Float.floatToIntBits(this.a33) : 0);
        return n;
    }

    private static int pack(int x, int y) {
        return y * 4 + x;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Matrix4f:\n");
        stringBuilder.append(this.a00);
        stringBuilder.append(" ");
        stringBuilder.append(this.a01);
        stringBuilder.append(" ");
        stringBuilder.append(this.a02);
        stringBuilder.append(" ");
        stringBuilder.append(this.a03);
        stringBuilder.append("\n");
        stringBuilder.append(this.a10);
        stringBuilder.append(" ");
        stringBuilder.append(this.a11);
        stringBuilder.append(" ");
        stringBuilder.append(this.a12);
        stringBuilder.append(" ");
        stringBuilder.append(this.a13);
        stringBuilder.append("\n");
        stringBuilder.append(this.a20);
        stringBuilder.append(" ");
        stringBuilder.append(this.a21);
        stringBuilder.append(" ");
        stringBuilder.append(this.a22);
        stringBuilder.append(" ");
        stringBuilder.append(this.a23);
        stringBuilder.append("\n");
        stringBuilder.append(this.a30);
        stringBuilder.append(" ");
        stringBuilder.append(this.a31);
        stringBuilder.append(" ");
        stringBuilder.append(this.a32);
        stringBuilder.append(" ");
        stringBuilder.append(this.a33);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public void writeToBuffer(FloatBuffer floatBuffer) {
        floatBuffer.put(Matrix4f.pack(0, 0), this.a00);
        floatBuffer.put(Matrix4f.pack(0, 1), this.a01);
        floatBuffer.put(Matrix4f.pack(0, 2), this.a02);
        floatBuffer.put(Matrix4f.pack(0, 3), this.a03);
        floatBuffer.put(Matrix4f.pack(1, 0), this.a10);
        floatBuffer.put(Matrix4f.pack(1, 1), this.a11);
        floatBuffer.put(Matrix4f.pack(1, 2), this.a12);
        floatBuffer.put(Matrix4f.pack(1, 3), this.a13);
        floatBuffer.put(Matrix4f.pack(2, 0), this.a20);
        floatBuffer.put(Matrix4f.pack(2, 1), this.a21);
        floatBuffer.put(Matrix4f.pack(2, 2), this.a22);
        floatBuffer.put(Matrix4f.pack(2, 3), this.a23);
        floatBuffer.put(Matrix4f.pack(3, 0), this.a30);
        floatBuffer.put(Matrix4f.pack(3, 1), this.a31);
        floatBuffer.put(Matrix4f.pack(3, 2), this.a32);
        floatBuffer.put(Matrix4f.pack(3, 3), this.a33);
    }

    public void loadIdentity() {
        this.a00 = 1.0f;
        this.a01 = 0.0f;
        this.a02 = 0.0f;
        this.a03 = 0.0f;
        this.a10 = 0.0f;
        this.a11 = 1.0f;
        this.a12 = 0.0f;
        this.a13 = 0.0f;
        this.a20 = 0.0f;
        this.a21 = 0.0f;
        this.a22 = 1.0f;
        this.a23 = 0.0f;
        this.a30 = 0.0f;
        this.a31 = 0.0f;
        this.a32 = 0.0f;
        this.a33 = 1.0f;
    }

    public float determinantAndAdjugate() {
        float f = this.a00 * this.a11 - this.a01 * this.a10;
        _snowman = this.a00 * this.a12 - this.a02 * this.a10;
        _snowman = this.a00 * this.a13 - this.a03 * this.a10;
        _snowman = this.a01 * this.a12 - this.a02 * this.a11;
        _snowman = this.a01 * this.a13 - this.a03 * this.a11;
        _snowman = this.a02 * this.a13 - this.a03 * this.a12;
        _snowman = this.a20 * this.a31 - this.a21 * this.a30;
        _snowman = this.a20 * this.a32 - this.a22 * this.a30;
        _snowman = this.a20 * this.a33 - this.a23 * this.a30;
        _snowman = this.a21 * this.a32 - this.a22 * this.a31;
        _snowman = this.a21 * this.a33 - this.a23 * this.a31;
        _snowman = this.a22 * this.a33 - this.a23 * this.a32;
        _snowman = this.a11 * _snowman - this.a12 * _snowman + this.a13 * _snowman;
        _snowman = -this.a10 * _snowman + this.a12 * _snowman - this.a13 * _snowman;
        _snowman = this.a10 * _snowman - this.a11 * _snowman + this.a13 * _snowman;
        _snowman = -this.a10 * _snowman + this.a11 * _snowman - this.a12 * _snowman;
        _snowman = -this.a01 * _snowman + this.a02 * _snowman - this.a03 * _snowman;
        _snowman = this.a00 * _snowman - this.a02 * _snowman + this.a03 * _snowman;
        _snowman = -this.a00 * _snowman + this.a01 * _snowman - this.a03 * _snowman;
        _snowman = this.a00 * _snowman - this.a01 * _snowman + this.a02 * _snowman;
        _snowman = this.a31 * _snowman - this.a32 * _snowman + this.a33 * _snowman;
        _snowman = -this.a30 * _snowman + this.a32 * _snowman - this.a33 * _snowman;
        _snowman = this.a30 * _snowman - this.a31 * _snowman + this.a33 * f;
        _snowman = -this.a30 * _snowman + this.a31 * _snowman - this.a32 * f;
        _snowman = -this.a21 * _snowman + this.a22 * _snowman - this.a23 * _snowman;
        _snowman = this.a20 * _snowman - this.a22 * _snowman + this.a23 * _snowman;
        _snowman = -this.a20 * _snowman + this.a21 * _snowman - this.a23 * f;
        _snowman = this.a20 * _snowman - this.a21 * _snowman + this.a22 * f;
        this.a00 = _snowman;
        this.a10 = _snowman;
        this.a20 = _snowman;
        this.a30 = _snowman;
        this.a01 = _snowman;
        this.a11 = _snowman;
        this.a21 = _snowman;
        this.a31 = _snowman;
        this.a02 = _snowman;
        this.a12 = _snowman;
        this.a22 = _snowman;
        this.a32 = _snowman;
        this.a03 = _snowman;
        this.a13 = _snowman;
        this.a23 = _snowman;
        this.a33 = _snowman;
        return f * _snowman - _snowman * _snowman + _snowman * _snowman + _snowman * _snowman - _snowman * _snowman + _snowman * _snowman;
    }

    public void transpose() {
        float f = this.a10;
        this.a10 = this.a01;
        this.a01 = f;
        f = this.a20;
        this.a20 = this.a02;
        this.a02 = f;
        f = this.a21;
        this.a21 = this.a12;
        this.a12 = f;
        f = this.a30;
        this.a30 = this.a03;
        this.a03 = f;
        f = this.a31;
        this.a31 = this.a13;
        this.a13 = f;
        f = this.a32;
        this.a32 = this.a23;
        this.a23 = f;
    }

    public boolean invert() {
        float f = this.determinantAndAdjugate();
        if (Math.abs(f) > 1.0E-6f) {
            this.multiply(f);
            return true;
        }
        return false;
    }

    public void multiply(Matrix4f matrix) {
        float f = this.a00 * matrix.a00 + this.a01 * matrix.a10 + this.a02 * matrix.a20 + this.a03 * matrix.a30;
        _snowman = this.a00 * matrix.a01 + this.a01 * matrix.a11 + this.a02 * matrix.a21 + this.a03 * matrix.a31;
        _snowman = this.a00 * matrix.a02 + this.a01 * matrix.a12 + this.a02 * matrix.a22 + this.a03 * matrix.a32;
        _snowman = this.a00 * matrix.a03 + this.a01 * matrix.a13 + this.a02 * matrix.a23 + this.a03 * matrix.a33;
        _snowman = this.a10 * matrix.a00 + this.a11 * matrix.a10 + this.a12 * matrix.a20 + this.a13 * matrix.a30;
        _snowman = this.a10 * matrix.a01 + this.a11 * matrix.a11 + this.a12 * matrix.a21 + this.a13 * matrix.a31;
        _snowman = this.a10 * matrix.a02 + this.a11 * matrix.a12 + this.a12 * matrix.a22 + this.a13 * matrix.a32;
        _snowman = this.a10 * matrix.a03 + this.a11 * matrix.a13 + this.a12 * matrix.a23 + this.a13 * matrix.a33;
        _snowman = this.a20 * matrix.a00 + this.a21 * matrix.a10 + this.a22 * matrix.a20 + this.a23 * matrix.a30;
        _snowman = this.a20 * matrix.a01 + this.a21 * matrix.a11 + this.a22 * matrix.a21 + this.a23 * matrix.a31;
        _snowman = this.a20 * matrix.a02 + this.a21 * matrix.a12 + this.a22 * matrix.a22 + this.a23 * matrix.a32;
        _snowman = this.a20 * matrix.a03 + this.a21 * matrix.a13 + this.a22 * matrix.a23 + this.a23 * matrix.a33;
        _snowman = this.a30 * matrix.a00 + this.a31 * matrix.a10 + this.a32 * matrix.a20 + this.a33 * matrix.a30;
        _snowman = this.a30 * matrix.a01 + this.a31 * matrix.a11 + this.a32 * matrix.a21 + this.a33 * matrix.a31;
        _snowman = this.a30 * matrix.a02 + this.a31 * matrix.a12 + this.a32 * matrix.a22 + this.a33 * matrix.a32;
        _snowman = this.a30 * matrix.a03 + this.a31 * matrix.a13 + this.a32 * matrix.a23 + this.a33 * matrix.a33;
        this.a00 = f;
        this.a01 = _snowman;
        this.a02 = _snowman;
        this.a03 = _snowman;
        this.a10 = _snowman;
        this.a11 = _snowman;
        this.a12 = _snowman;
        this.a13 = _snowman;
        this.a20 = _snowman;
        this.a21 = _snowman;
        this.a22 = _snowman;
        this.a23 = _snowman;
        this.a30 = _snowman;
        this.a31 = _snowman;
        this.a32 = _snowman;
        this.a33 = _snowman;
    }

    public void multiply(Quaternion quaternion) {
        this.multiply(new Matrix4f(quaternion));
    }

    public void multiply(float scalar) {
        this.a00 *= scalar;
        this.a01 *= scalar;
        this.a02 *= scalar;
        this.a03 *= scalar;
        this.a10 *= scalar;
        this.a11 *= scalar;
        this.a12 *= scalar;
        this.a13 *= scalar;
        this.a20 *= scalar;
        this.a21 *= scalar;
        this.a22 *= scalar;
        this.a23 *= scalar;
        this.a30 *= scalar;
        this.a31 *= scalar;
        this.a32 *= scalar;
        this.a33 *= scalar;
    }

    public static Matrix4f viewboxMatrix(double fov, float aspectRatio, float cameraDepth, float viewDistance) {
        float f = (float)(1.0 / Math.tan(fov * 0.01745329238474369 / 2.0));
        Matrix4f _snowman2 = new Matrix4f();
        _snowman2.a00 = f / aspectRatio;
        _snowman2.a11 = f;
        _snowman2.a22 = (viewDistance + cameraDepth) / (cameraDepth - viewDistance);
        _snowman2.a32 = -1.0f;
        _snowman2.a23 = 2.0f * viewDistance * cameraDepth / (cameraDepth - viewDistance);
        return _snowman2;
    }

    public static Matrix4f projectionMatrix(float width, float height, float nearPlane, float farPlane) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.a00 = 2.0f / width;
        matrix4f.a11 = 2.0f / height;
        float _snowman2 = farPlane - nearPlane;
        matrix4f.a22 = -2.0f / _snowman2;
        matrix4f.a33 = 1.0f;
        matrix4f.a03 = -1.0f;
        matrix4f.a13 = -1.0f;
        matrix4f.a23 = -(farPlane + nearPlane) / _snowman2;
        return matrix4f;
    }

    public void addToLastColumn(Vector3f vector) {
        this.a03 += vector.getX();
        this.a13 += vector.getY();
        this.a23 += vector.getZ();
    }

    public Matrix4f copy() {
        return new Matrix4f(this);
    }

    public static Matrix4f scale(float x, float y, float z) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.a00 = x;
        matrix4f.a11 = y;
        matrix4f.a22 = z;
        matrix4f.a33 = 1.0f;
        return matrix4f;
    }

    public static Matrix4f translate(float x, float y, float z) {
        Matrix4f matrix4f = new Matrix4f();
        matrix4f.a00 = 1.0f;
        matrix4f.a11 = 1.0f;
        matrix4f.a22 = 1.0f;
        matrix4f.a33 = 1.0f;
        matrix4f.a03 = x;
        matrix4f.a13 = y;
        matrix4f.a23 = z;
        return matrix4f;
    }
}

