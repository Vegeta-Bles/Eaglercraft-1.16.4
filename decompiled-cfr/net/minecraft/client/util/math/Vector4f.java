/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.util.math;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;

public class Vector4f {
    private float x;
    private float y;
    private float z;
    private float w;

    public Vector4f() {
    }

    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4f(Vector3f vector) {
        this(vector.getX(), vector.getY(), vector.getZ(), 1.0f);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Vector4f vector4f = (Vector4f)o;
        if (Float.compare(vector4f.x, this.x) != 0) {
            return false;
        }
        if (Float.compare(vector4f.y, this.y) != 0) {
            return false;
        }
        if (Float.compare(vector4f.z, this.z) != 0) {
            return false;
        }
        return Float.compare(vector4f.w, this.w) == 0;
    }

    public int hashCode() {
        int n = Float.floatToIntBits(this.x);
        n = 31 * n + Float.floatToIntBits(this.y);
        n = 31 * n + Float.floatToIntBits(this.z);
        n = 31 * n + Float.floatToIntBits(this.w);
        return n;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    public float getW() {
        return this.w;
    }

    public void multiplyComponentwise(Vector3f vector) {
        this.x *= vector.getX();
        this.y *= vector.getY();
        this.z *= vector.getZ();
    }

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float dotProduct(Vector4f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public boolean normalize() {
        float f = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
        if ((double)f < 1.0E-5) {
            return false;
        }
        _snowman = MathHelper.fastInverseSqrt(f);
        this.x *= _snowman;
        this.y *= _snowman;
        this.z *= _snowman;
        this.w *= _snowman;
        return true;
    }

    public void transform(Matrix4f matrix) {
        float f = this.x;
        _snowman = this.y;
        _snowman = this.z;
        _snowman = this.w;
        this.x = matrix.a00 * f + matrix.a01 * _snowman + matrix.a02 * _snowman + matrix.a03 * _snowman;
        this.y = matrix.a10 * f + matrix.a11 * _snowman + matrix.a12 * _snowman + matrix.a13 * _snowman;
        this.z = matrix.a20 * f + matrix.a21 * _snowman + matrix.a22 * _snowman + matrix.a23 * _snowman;
        this.w = matrix.a30 * f + matrix.a31 * _snowman + matrix.a32 * _snowman + matrix.a33 * _snowman;
    }

    public void rotate(Quaternion rotation) {
        Quaternion quaternion = new Quaternion(rotation);
        quaternion.hamiltonProduct(new Quaternion(this.getX(), this.getY(), this.getZ(), 0.0f));
        _snowman = new Quaternion(rotation);
        _snowman.conjugate();
        quaternion.hamiltonProduct(_snowman);
        this.set(quaternion.getX(), quaternion.getY(), quaternion.getZ(), this.getW());
    }

    public void normalizeProjectiveCoordinates() {
        this.x /= this.w;
        this.y /= this.w;
        this.z /= this.w;
        this.w = 1.0f;
    }

    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + "]";
    }
}

