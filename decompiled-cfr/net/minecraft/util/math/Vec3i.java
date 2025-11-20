/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.mojang.serialization.Codec
 *  javax.annotation.concurrent.Immutable
 */
package net.minecraft.util.math;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import java.util.stream.IntStream;
import javax.annotation.concurrent.Immutable;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Position;

@Immutable
public class Vec3i
implements Comparable<Vec3i> {
    public static final Codec<Vec3i> CODEC = Codec.INT_STREAM.comapFlatMap(intStream -> Util.toIntArray(intStream, 3).map(nArray -> new Vec3i(nArray[0], nArray[1], nArray[2])), vec3i -> IntStream.of(vec3i.getX(), vec3i.getY(), vec3i.getZ()));
    public static final Vec3i ZERO = new Vec3i(0, 0, 0);
    private int x;
    private int y;
    private int z;

    public Vec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3i(double x, double y, double z) {
        this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Vec3i)) {
            return false;
        }
        Vec3i vec3i = (Vec3i)object;
        if (this.getX() != vec3i.getX()) {
            return false;
        }
        if (this.getY() != vec3i.getY()) {
            return false;
        }
        return this.getZ() == vec3i.getZ();
    }

    public int hashCode() {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    @Override
    public int compareTo(Vec3i vec3i) {
        if (this.getY() == vec3i.getY()) {
            if (this.getZ() == vec3i.getZ()) {
                return this.getX() - vec3i.getX();
            }
            return this.getZ() - vec3i.getZ();
        }
        return this.getY() - vec3i.getY();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    protected void setX(int x) {
        this.x = x;
    }

    protected void setY(int y) {
        this.y = y;
    }

    protected void setZ(int z) {
        this.z = z;
    }

    public Vec3i up() {
        return this.up(1);
    }

    public Vec3i up(int distance) {
        return this.offset(Direction.UP, distance);
    }

    public Vec3i down() {
        return this.down(1);
    }

    public Vec3i down(int distance) {
        return this.offset(Direction.DOWN, distance);
    }

    public Vec3i offset(Direction direction, int distance) {
        if (distance == 0) {
            return this;
        }
        return new Vec3i(this.getX() + direction.getOffsetX() * distance, this.getY() + direction.getOffsetY() * distance, this.getZ() + direction.getOffsetZ() * distance);
    }

    public Vec3i crossProduct(Vec3i vec) {
        return new Vec3i(this.getY() * vec.getZ() - this.getZ() * vec.getY(), this.getZ() * vec.getX() - this.getX() * vec.getZ(), this.getX() * vec.getY() - this.getY() * vec.getX());
    }

    public boolean isWithinDistance(Vec3i vec, double distance) {
        return this.getSquaredDistance(vec.getX(), vec.getY(), vec.getZ(), false) < distance * distance;
    }

    public boolean isWithinDistance(Position pos, double distance) {
        return this.getSquaredDistance(pos.getX(), pos.getY(), pos.getZ(), true) < distance * distance;
    }

    public double getSquaredDistance(Vec3i vec) {
        return this.getSquaredDistance(vec.getX(), vec.getY(), vec.getZ(), true);
    }

    public double getSquaredDistance(Position pos, boolean treatAsBlockPos) {
        return this.getSquaredDistance(pos.getX(), pos.getY(), pos.getZ(), treatAsBlockPos);
    }

    public double getSquaredDistance(double x, double y, double z, boolean treatAsBlockPos) {
        double d = treatAsBlockPos ? 0.5 : 0.0;
        _snowman = (double)this.getX() + d - x;
        _snowman = (double)this.getY() + d - y;
        _snowman = (double)this.getZ() + d - z;
        return _snowman * _snowman + _snowman * _snowman + _snowman * _snowman;
    }

    public int getManhattanDistance(Vec3i vec) {
        float f = Math.abs(vec.getX() - this.getX());
        _snowman = Math.abs(vec.getY() - this.getY());
        _snowman = Math.abs(vec.getZ() - this.getZ());
        return (int)(f + _snowman + _snowman);
    }

    public int getComponentAlongAxis(Direction.Axis axis) {
        return axis.choose(this.x, this.y, this.z);
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }

    public String toShortString() {
        return "" + this.getX() + ", " + this.getY() + ", " + this.getZ();
    }

    @Override
    public /* synthetic */ int compareTo(Object object) {
        return this.compareTo((Vec3i)object);
    }
}

