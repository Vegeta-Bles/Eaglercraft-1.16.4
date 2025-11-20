/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.math;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;

public final class Quaternion {
    public static final Quaternion IDENTITY = new Quaternion(0.0f, 0.0f, 0.0f, 1.0f);
    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vector3f axis, float rotationAngle, boolean degrees) {
        if (degrees) {
            rotationAngle *= (float)Math.PI / 180;
        }
        float f = Quaternion.sin(rotationAngle / 2.0f);
        this.x = axis.getX() * f;
        this.y = axis.getY() * f;
        this.z = axis.getZ() * f;
        this.w = Quaternion.cos(rotationAngle / 2.0f);
    }

    public Quaternion(float x, float y, float z, boolean degrees) {
        if (degrees) {
            x *= (float)Math.PI / 180;
            y *= (float)Math.PI / 180;
            z *= (float)Math.PI / 180;
        }
        float f = Quaternion.sin(0.5f * x);
        _snowman = Quaternion.cos(0.5f * x);
        _snowman = Quaternion.sin(0.5f * y);
        _snowman = Quaternion.cos(0.5f * y);
        _snowman = Quaternion.sin(0.5f * z);
        _snowman = Quaternion.cos(0.5f * z);
        this.x = f * _snowman * _snowman + _snowman * _snowman * _snowman;
        this.y = _snowman * _snowman * _snowman - f * _snowman * _snowman;
        this.z = f * _snowman * _snowman + _snowman * _snowman * _snowman;
        this.w = _snowman * _snowman * _snowman - f * _snowman * _snowman;
    }

    public Quaternion(Quaternion other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Quaternion quaternion = (Quaternion)o;
        if (Float.compare(quaternion.x, this.x) != 0) {
            return false;
        }
        if (Float.compare(quaternion.y, this.y) != 0) {
            return false;
        }
        if (Float.compare(quaternion.z, this.z) != 0) {
            return false;
        }
        return Float.compare(quaternion.w, this.w) == 0;
    }

    public int hashCode() {
        int n = Float.floatToIntBits(this.x);
        n = 31 * n + Float.floatToIntBits(this.y);
        n = 31 * n + Float.floatToIntBits(this.z);
        n = 31 * n + Float.floatToIntBits(this.w);
        return n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Quaternion[").append(this.getW()).append(" + ");
        stringBuilder.append(this.getX()).append("i + ");
        stringBuilder.append(this.getY()).append("j + ");
        stringBuilder.append(this.getZ()).append("k]");
        return stringBuilder.toString();
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

    public void hamiltonProduct(Quaternion other) {
        float f = this.getX();
        _snowman = this.getY();
        _snowman = this.getZ();
        _snowman = this.getW();
        _snowman = other.getX();
        _snowman = other.getY();
        _snowman = other.getZ();
        _snowman = other.getW();
        this.x = _snowman * _snowman + f * _snowman + _snowman * _snowman - _snowman * _snowman;
        this.y = _snowman * _snowman - f * _snowman + _snowman * _snowman + _snowman * _snowman;
        this.z = _snowman * _snowman + f * _snowman - _snowman * _snowman + _snowman * _snowman;
        this.w = _snowman * _snowman - f * _snowman - _snowman * _snowman - _snowman * _snowman;
    }

    public void scale(float scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        this.w *= scale;
    }

    public void conjugate() {
        this.x = -this.x;
        this.y = -this.y;
        this.z = -this.z;
    }

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    private static float cos(float value) {
        return (float)Math.cos(value);
    }

    private static float sin(float value) {
        return (float)Math.sin(value);
    }

    public void normalize() {
        float f = this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ() + this.getW() * this.getW();
        if (f > 1.0E-6f) {
            _snowman = MathHelper.fastInverseSqrt(f);
            this.x *= _snowman;
            this.y *= _snowman;
            this.z *= _snowman;
            this.w *= _snowman;
        } else {
            this.x = 0.0f;
            this.y = 0.0f;
            this.z = 0.0f;
            this.w = 0.0f;
        }
    }

    public Quaternion copy() {
        return new Quaternion(this);
    }
}

