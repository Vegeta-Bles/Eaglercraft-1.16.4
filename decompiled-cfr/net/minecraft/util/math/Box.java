/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.util.math;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class Box {
    public final double minX;
    public final double minY;
    public final double minZ;
    public final double maxX;
    public final double maxY;
    public final double maxZ;

    public Box(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
    }

    public Box(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    public Box(BlockPos pos1, BlockPos pos2) {
        this(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
    }

    public Box(Vec3d pos1, Vec3d pos2) {
        this(pos1.x, pos1.y, pos1.z, pos2.x, pos2.y, pos2.z);
    }

    public static Box from(BlockBox mutable) {
        return new Box(mutable.minX, mutable.minY, mutable.minZ, mutable.maxX + 1, mutable.maxY + 1, mutable.maxZ + 1);
    }

    public static Box method_29968(Vec3d vec3d) {
        return new Box(vec3d.x, vec3d.y, vec3d.z, vec3d.x + 1.0, vec3d.y + 1.0, vec3d.z + 1.0);
    }

    public double getMin(Direction.Axis axis) {
        return axis.choose(this.minX, this.minY, this.minZ);
    }

    public double getMax(Direction.Axis axis) {
        return axis.choose(this.maxX, this.maxY, this.maxZ);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Box)) {
            return false;
        }
        Box box = (Box)o;
        if (Double.compare(box.minX, this.minX) != 0) {
            return false;
        }
        if (Double.compare(box.minY, this.minY) != 0) {
            return false;
        }
        if (Double.compare(box.minZ, this.minZ) != 0) {
            return false;
        }
        if (Double.compare(box.maxX, this.maxX) != 0) {
            return false;
        }
        if (Double.compare(box.maxY, this.maxY) != 0) {
            return false;
        }
        return Double.compare(box.maxZ, this.maxZ) == 0;
    }

    public int hashCode() {
        long l = Double.doubleToLongBits(this.minX);
        int _snowman2 = (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.minY);
        _snowman2 = 31 * _snowman2 + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.minZ);
        _snowman2 = 31 * _snowman2 + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.maxX);
        _snowman2 = 31 * _snowman2 + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.maxY);
        _snowman2 = 31 * _snowman2 + (int)(l ^ l >>> 32);
        l = Double.doubleToLongBits(this.maxZ);
        _snowman2 = 31 * _snowman2 + (int)(l ^ l >>> 32);
        return _snowman2;
    }

    public Box shrink(double x, double y, double z) {
        double d = this.minX;
        _snowman = this.minY;
        _snowman = this.minZ;
        _snowman = this.maxX;
        _snowman = this.maxY;
        _snowman = this.maxZ;
        if (x < 0.0) {
            d -= x;
        } else if (x > 0.0) {
            _snowman -= x;
        }
        if (y < 0.0) {
            _snowman -= y;
        } else if (y > 0.0) {
            _snowman -= y;
        }
        if (z < 0.0) {
            _snowman -= z;
        } else if (z > 0.0) {
            _snowman -= z;
        }
        return new Box(d, _snowman, _snowman, _snowman, _snowman, _snowman);
    }

    public Box stretch(Vec3d scale) {
        return this.stretch(scale.x, scale.y, scale.z);
    }

    public Box stretch(double x, double y, double z) {
        double d = this.minX;
        _snowman = this.minY;
        _snowman = this.minZ;
        _snowman = this.maxX;
        _snowman = this.maxY;
        _snowman = this.maxZ;
        if (x < 0.0) {
            d += x;
        } else if (x > 0.0) {
            _snowman += x;
        }
        if (y < 0.0) {
            _snowman += y;
        } else if (y > 0.0) {
            _snowman += y;
        }
        if (z < 0.0) {
            _snowman += z;
        } else if (z > 0.0) {
            _snowman += z;
        }
        return new Box(d, _snowman, _snowman, _snowman, _snowman, _snowman);
    }

    public Box expand(double x, double y, double z) {
        double d = this.minX - x;
        _snowman = this.minY - y;
        _snowman = this.minZ - z;
        _snowman = this.maxX + x;
        _snowman = this.maxY + y;
        _snowman = this.maxZ + z;
        return new Box(d, _snowman, _snowman, _snowman, _snowman, _snowman);
    }

    public Box expand(double value) {
        return this.expand(value, value, value);
    }

    public Box intersection(Box box) {
        double d = Math.max(this.minX, box.minX);
        _snowman = Math.max(this.minY, box.minY);
        _snowman = Math.max(this.minZ, box.minZ);
        _snowman = Math.min(this.maxX, box.maxX);
        _snowman = Math.min(this.maxY, box.maxY);
        _snowman = Math.min(this.maxZ, box.maxZ);
        return new Box(d, _snowman, _snowman, _snowman, _snowman, _snowman);
    }

    public Box union(Box box) {
        double d = Math.min(this.minX, box.minX);
        _snowman = Math.min(this.minY, box.minY);
        _snowman = Math.min(this.minZ, box.minZ);
        _snowman = Math.max(this.maxX, box.maxX);
        _snowman = Math.max(this.maxY, box.maxY);
        _snowman = Math.max(this.maxZ, box.maxZ);
        return new Box(d, _snowman, _snowman, _snowman, _snowman, _snowman);
    }

    public Box offset(double x, double y, double z) {
        return new Box(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
    }

    public Box offset(BlockPos blockPos) {
        return new Box(this.minX + (double)blockPos.getX(), this.minY + (double)blockPos.getY(), this.minZ + (double)blockPos.getZ(), this.maxX + (double)blockPos.getX(), this.maxY + (double)blockPos.getY(), this.maxZ + (double)blockPos.getZ());
    }

    public Box offset(Vec3d vec3d) {
        return this.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    public boolean intersects(Box box) {
        return this.intersects(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
    }

    public boolean intersects(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return this.minX < maxX && this.maxX > minX && this.minY < maxY && this.maxY > minY && this.minZ < maxZ && this.maxZ > minZ;
    }

    public boolean intersects(Vec3d from, Vec3d to) {
        return this.intersects(Math.min(from.x, to.x), Math.min(from.y, to.y), Math.min(from.z, to.z), Math.max(from.x, to.x), Math.max(from.y, to.y), Math.max(from.z, to.z));
    }

    public boolean contains(Vec3d vec) {
        return this.contains(vec.x, vec.y, vec.z);
    }

    public boolean contains(double x, double y, double z) {
        return x >= this.minX && x < this.maxX && y >= this.minY && y < this.maxY && z >= this.minZ && z < this.maxZ;
    }

    public double getAverageSideLength() {
        double d = this.getXLength();
        _snowman = this.getYLength();
        _snowman = this.getZLength();
        return (d + _snowman + _snowman) / 3.0;
    }

    public double getXLength() {
        return this.maxX - this.minX;
    }

    public double getYLength() {
        return this.maxY - this.minY;
    }

    public double getZLength() {
        return this.maxZ - this.minZ;
    }

    public Box contract(double value) {
        return this.expand(-value);
    }

    public Optional<Vec3d> raycast(Vec3d min, Vec3d max) {
        double[] dArray = new double[]{1.0};
        double _snowman2 = max.x - min.x;
        double _snowman3 = max.y - min.y;
        double _snowman4 = max.z - min.z;
        Direction _snowman5 = Box.traceCollisionSide(this, min, dArray, null, _snowman2, _snowman3, _snowman4);
        if (_snowman5 == null) {
            return Optional.empty();
        }
        double _snowman6 = dArray[0];
        return Optional.of(min.add(_snowman6 * _snowman2, _snowman6 * _snowman3, _snowman6 * _snowman4));
    }

    @Nullable
    public static BlockHitResult raycast(Iterable<Box> boxes, Vec3d from, Vec3d to, BlockPos pos) {
        double[] dArray = new double[]{1.0};
        Direction _snowman2 = null;
        double _snowman3 = to.x - from.x;
        double _snowman4 = to.y - from.y;
        double _snowman5 = to.z - from.z;
        for (Box box : boxes) {
            _snowman2 = Box.traceCollisionSide(box.offset(pos), from, dArray, _snowman2, _snowman3, _snowman4, _snowman5);
        }
        if (_snowman2 == null) {
            return null;
        }
        double _snowman6 = dArray[0];
        return new BlockHitResult(from.add(_snowman6 * _snowman3, _snowman6 * _snowman4, _snowman6 * _snowman5), _snowman2, pos, false);
    }

    @Nullable
    private static Direction traceCollisionSide(Box box, Vec3d intersectingVector, double[] traceDistanceResult, @Nullable Direction approachDirection, double xDelta, double yDelta, double zDelta) {
        if (xDelta > 1.0E-7) {
            approachDirection = Box.traceCollisionSide(traceDistanceResult, approachDirection, xDelta, yDelta, zDelta, box.minX, box.minY, box.maxY, box.minZ, box.maxZ, Direction.WEST, intersectingVector.x, intersectingVector.y, intersectingVector.z);
        } else if (xDelta < -1.0E-7) {
            approachDirection = Box.traceCollisionSide(traceDistanceResult, approachDirection, xDelta, yDelta, zDelta, box.maxX, box.minY, box.maxY, box.minZ, box.maxZ, Direction.EAST, intersectingVector.x, intersectingVector.y, intersectingVector.z);
        }
        if (yDelta > 1.0E-7) {
            approachDirection = Box.traceCollisionSide(traceDistanceResult, approachDirection, yDelta, zDelta, xDelta, box.minY, box.minZ, box.maxZ, box.minX, box.maxX, Direction.DOWN, intersectingVector.y, intersectingVector.z, intersectingVector.x);
        } else if (yDelta < -1.0E-7) {
            approachDirection = Box.traceCollisionSide(traceDistanceResult, approachDirection, yDelta, zDelta, xDelta, box.maxY, box.minZ, box.maxZ, box.minX, box.maxX, Direction.UP, intersectingVector.y, intersectingVector.z, intersectingVector.x);
        }
        if (zDelta > 1.0E-7) {
            approachDirection = Box.traceCollisionSide(traceDistanceResult, approachDirection, zDelta, xDelta, yDelta, box.minZ, box.minX, box.maxX, box.minY, box.maxY, Direction.NORTH, intersectingVector.z, intersectingVector.x, intersectingVector.y);
        } else if (zDelta < -1.0E-7) {
            approachDirection = Box.traceCollisionSide(traceDistanceResult, approachDirection, zDelta, xDelta, yDelta, box.maxZ, box.minX, box.maxX, box.minY, box.maxY, Direction.SOUTH, intersectingVector.z, intersectingVector.x, intersectingVector.y);
        }
        return approachDirection;
    }

    @Nullable
    private static Direction traceCollisionSide(double[] traceDistanceResult, @Nullable Direction approachDirection, double xDelta, double yDelta, double zDelta, double begin, double minX, double maxX, double minZ, double maxZ, Direction resultDirection, double startX, double startY, double startZ) {
        double d = (begin - startX) / xDelta;
        _snowman = startY + d * yDelta;
        _snowman = startZ + d * zDelta;
        if (0.0 < d && d < traceDistanceResult[0] && minX - 1.0E-7 < _snowman && _snowman < maxX + 1.0E-7 && minZ - 1.0E-7 < _snowman && _snowman < maxZ + 1.0E-7) {
            traceDistanceResult[0] = d;
            return resultDirection;
        }
        return approachDirection;
    }

    public String toString() {
        return "AABB[" + this.minX + ", " + this.minY + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }

    public boolean isValid() {
        return Double.isNaN(this.minX) || Double.isNaN(this.minY) || Double.isNaN(this.minZ) || Double.isNaN(this.maxX) || Double.isNaN(this.maxY) || Double.isNaN(this.maxZ);
    }

    public Vec3d getCenter() {
        return new Vec3d(MathHelper.lerp(0.5, this.minX, this.maxX), MathHelper.lerp(0.5, this.minY, this.maxY), MathHelper.lerp(0.5, this.minZ, this.maxZ));
    }

    public static Box method_30048(double d, double d2, double d3) {
        return new Box(-d / 2.0, -d2 / 2.0, -d3 / 2.0, d / 2.0, d2 / 2.0, d3 / 2.0);
    }
}

