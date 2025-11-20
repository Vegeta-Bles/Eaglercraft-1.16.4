/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.math;

import java.util.EnumSet;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3i;

public class Vec3d
implements Position {
    public static final Vec3d ZERO = new Vec3d(0.0, 0.0, 0.0);
    public final double x;
    public final double y;
    public final double z;

    public static Vec3d unpackRgb(int rgb) {
        double d = (double)(rgb >> 16 & 0xFF) / 255.0;
        _snowman = (double)(rgb >> 8 & 0xFF) / 255.0;
        _snowman = (double)(rgb & 0xFF) / 255.0;
        return new Vec3d(d, _snowman, _snowman);
    }

    public static Vec3d ofCenter(Vec3i vec) {
        return new Vec3d((double)vec.getX() + 0.5, (double)vec.getY() + 0.5, (double)vec.getZ() + 0.5);
    }

    public static Vec3d of(Vec3i vec) {
        return new Vec3d(vec.getX(), vec.getY(), vec.getZ());
    }

    public static Vec3d ofBottomCenter(Vec3i vec) {
        return new Vec3d((double)vec.getX() + 0.5, vec.getY(), (double)vec.getZ() + 0.5);
    }

    public static Vec3d ofCenter(Vec3i vec, double deltaY) {
        return new Vec3d((double)vec.getX() + 0.5, (double)vec.getY() + deltaY, (double)vec.getZ() + 0.5);
    }

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d(Vector3f vec) {
        this(vec.getX(), vec.getY(), vec.getZ());
    }

    public Vec3d reverseSubtract(Vec3d vec) {
        return new Vec3d(vec.x - this.x, vec.y - this.y, vec.z - this.z);
    }

    public Vec3d normalize() {
        double d = MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
        if (d < 1.0E-4) {
            return ZERO;
        }
        return new Vec3d(this.x / d, this.y / d, this.z / d);
    }

    public double dotProduct(Vec3d vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }

    public Vec3d crossProduct(Vec3d vec) {
        return new Vec3d(this.y * vec.z - this.z * vec.y, this.z * vec.x - this.x * vec.z, this.x * vec.y - this.y * vec.x);
    }

    public Vec3d subtract(Vec3d vec) {
        return this.subtract(vec.x, vec.y, vec.z);
    }

    public Vec3d subtract(double x, double y, double z) {
        return this.add(-x, -y, -z);
    }

    public Vec3d add(Vec3d vec) {
        return this.add(vec.x, vec.y, vec.z);
    }

    public Vec3d add(double x, double y, double z) {
        return new Vec3d(this.x + x, this.y + y, this.z + z);
    }

    public boolean isInRange(Position pos, double radius) {
        return this.squaredDistanceTo(pos.getX(), pos.getY(), pos.getZ()) < radius * radius;
    }

    public double distanceTo(Vec3d vec) {
        double d = vec.x - this.x;
        _snowman = vec.y - this.y;
        _snowman = vec.z - this.z;
        return MathHelper.sqrt(d * d + _snowman * _snowman + _snowman * _snowman);
    }

    public double squaredDistanceTo(Vec3d vec) {
        double d = vec.x - this.x;
        _snowman = vec.y - this.y;
        _snowman = vec.z - this.z;
        return d * d + _snowman * _snowman + _snowman * _snowman;
    }

    public double squaredDistanceTo(double x, double y, double z) {
        double d = x - this.x;
        _snowman = y - this.y;
        _snowman = z - this.z;
        return d * d + _snowman * _snowman + _snowman * _snowman;
    }

    public Vec3d multiply(double mult) {
        return this.multiply(mult, mult, mult);
    }

    public Vec3d negate() {
        return this.multiply(-1.0);
    }

    public Vec3d multiply(Vec3d mult) {
        return this.multiply(mult.x, mult.y, mult.z);
    }

    public Vec3d multiply(double multX, double multY, double multZ) {
        return new Vec3d(this.x * multX, this.y * multY, this.z * multZ);
    }

    public double length() {
        return MathHelper.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double lengthSquared() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vec3d)) {
            return false;
        }
        Vec3d vec3d = (Vec3d)o;
        if (Double.compare(vec3d.x, this.x) != 0) {
            return false;
        }
        if (Double.compare(vec3d.y, this.y) != 0) {
            return false;
        }
        return Double.compare(vec3d.z, this.z) == 0;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.x);
        int _snowman2 = (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.y);
        _snowman2 = 31 * _snowman2 + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.z);
        _snowman2 = 31 * _snowman2 + (int)(l ^ l >>> 32);
        return _snowman2;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public Vec3d rotateX(float angle) {
        float f = MathHelper.cos(angle);
        _snowman = MathHelper.sin(angle);
        double _snowman2 = this.x;
        double _snowman3 = this.y * (double)f + this.z * (double)_snowman;
        double _snowman4 = this.z * (double)f - this.y * (double)_snowman;
        return new Vec3d(_snowman2, _snowman3, _snowman4);
    }

    public Vec3d rotateY(float angle) {
        float f = MathHelper.cos(angle);
        _snowman = MathHelper.sin(angle);
        double _snowman2 = this.x * (double)f + this.z * (double)_snowman;
        double _snowman3 = this.y;
        double _snowman4 = this.z * (double)f - this.x * (double)_snowman;
        return new Vec3d(_snowman2, _snowman3, _snowman4);
    }

    public Vec3d rotateZ(float angle) {
        float f = MathHelper.cos(angle);
        _snowman = MathHelper.sin(angle);
        double _snowman2 = this.x * (double)f + this.y * (double)_snowman;
        double _snowman3 = this.y * (double)f - this.x * (double)_snowman;
        double _snowman4 = this.z;
        return new Vec3d(_snowman2, _snowman3, _snowman4);
    }

    public static Vec3d fromPolar(Vec2f polar) {
        return Vec3d.fromPolar(polar.x, polar.y);
    }

    public static Vec3d fromPolar(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
        _snowman = MathHelper.sin(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
        _snowman = -MathHelper.cos(-pitch * ((float)Math.PI / 180));
        _snowman = MathHelper.sin(-pitch * ((float)Math.PI / 180));
        return new Vec3d(_snowman * _snowman, _snowman, f * _snowman);
    }

    public Vec3d floorAlongAxes(EnumSet<Direction.Axis> axes) {
        double d = axes.contains(Direction.Axis.X) ? (double)MathHelper.floor(this.x) : this.x;
        _snowman = axes.contains(Direction.Axis.Y) ? (double)MathHelper.floor(this.y) : this.y;
        _snowman = axes.contains(Direction.Axis.Z) ? (double)MathHelper.floor(this.z) : this.z;
        return new Vec3d(d, _snowman, _snowman);
    }

    public double getComponentAlongAxis(Direction.Axis axis) {
        return axis.choose(this.x, this.y, this.z);
    }

    @Override
    public final double getX() {
        return this.x;
    }

    @Override
    public final double getY() {
        return this.y;
    }

    @Override
    public final double getZ() {
        return this.z;
    }
}

