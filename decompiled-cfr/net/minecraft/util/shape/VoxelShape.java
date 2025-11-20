/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.math.DoubleMath
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 *  javax.annotation.Nullable
 */
package net.minecraft.util.shape;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.AxisCycleDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.ArrayVoxelShape;
import net.minecraft.util.shape.OffsetDoubleList;
import net.minecraft.util.shape.SlicedVoxelShape;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.util.shape.VoxelShapes;

public abstract class VoxelShape {
    protected final VoxelSet voxels;
    @Nullable
    private VoxelShape[] shapeCache;

    VoxelShape(VoxelSet voxels) {
        this.voxels = voxels;
    }

    public double getMin(Direction.Axis axis) {
        int n = this.voxels.getMin(axis);
        if (n >= this.voxels.getSize(axis)) {
            return Double.POSITIVE_INFINITY;
        }
        return this.getPointPosition(axis, n);
    }

    public double getMax(Direction.Axis axis) {
        int n = this.voxels.getMax(axis);
        if (n <= 0) {
            return Double.NEGATIVE_INFINITY;
        }
        return this.getPointPosition(axis, n);
    }

    public Box getBoundingBox() {
        if (this.isEmpty()) {
            throw Util.throwOrPause(new UnsupportedOperationException("No bounds for empty shape."));
        }
        return new Box(this.getMin(Direction.Axis.X), this.getMin(Direction.Axis.Y), this.getMin(Direction.Axis.Z), this.getMax(Direction.Axis.X), this.getMax(Direction.Axis.Y), this.getMax(Direction.Axis.Z));
    }

    protected double getPointPosition(Direction.Axis axis, int index) {
        return this.getPointPositions(axis).getDouble(index);
    }

    protected abstract DoubleList getPointPositions(Direction.Axis var1);

    public boolean isEmpty() {
        return this.voxels.isEmpty();
    }

    public VoxelShape offset(double x, double y, double z) {
        if (this.isEmpty()) {
            return VoxelShapes.empty();
        }
        return new ArrayVoxelShape(this.voxels, (DoubleList)new OffsetDoubleList(this.getPointPositions(Direction.Axis.X), x), (DoubleList)new OffsetDoubleList(this.getPointPositions(Direction.Axis.Y), y), (DoubleList)new OffsetDoubleList(this.getPointPositions(Direction.Axis.Z), z));
    }

    public VoxelShape simplify() {
        VoxelShape[] voxelShapeArray = new VoxelShape[]{VoxelShapes.empty()};
        this.forEachBox((d, d2, d3, d4, d5, d6) -> {
            voxelShapeArray[0] = VoxelShapes.combine(voxelShapeArray[0], VoxelShapes.cuboid(d, d2, d3, d4, d5, d6), BooleanBiFunction.OR);
        });
        return voxelShapeArray[0];
    }

    public void forEachEdge(VoxelShapes.BoxConsumer boxConsumer) {
        this.voxels.forEachEdge((n, n2, n3, n4, n5, n6) -> boxConsumer.consume(this.getPointPosition(Direction.Axis.X, n), this.getPointPosition(Direction.Axis.Y, n2), this.getPointPosition(Direction.Axis.Z, n3), this.getPointPosition(Direction.Axis.X, n4), this.getPointPosition(Direction.Axis.Y, n5), this.getPointPosition(Direction.Axis.Z, n6)), true);
    }

    public void forEachBox(VoxelShapes.BoxConsumer boxConsumer) {
        DoubleList doubleList = this.getPointPositions(Direction.Axis.X);
        _snowman = this.getPointPositions(Direction.Axis.Y);
        _snowman = this.getPointPositions(Direction.Axis.Z);
        this.voxels.forEachBox((n, n2, n3, n4, n5, n6) -> boxConsumer.consume(doubleList.getDouble(n), _snowman.getDouble(n2), _snowman.getDouble(n3), doubleList.getDouble(n4), _snowman.getDouble(n5), _snowman.getDouble(n6)), true);
    }

    public List<Box> getBoundingBoxes() {
        ArrayList arrayList = Lists.newArrayList();
        this.forEachBox((d, d2, d3, d4, d5, d6) -> arrayList.add(new Box(d, d2, d3, d4, d5, d6)));
        return arrayList;
    }

    public double getEndingCoord(Direction.Axis axis, double from, double to) {
        _snowman = AxisCycleDirection.FORWARD.cycle(axis);
        _snowman = AxisCycleDirection.BACKWARD.cycle(axis);
        int n = this.getCoordIndex(_snowman, from);
        _snowman = this.voxels.getEndingAxisCoord(axis, n, _snowman = this.getCoordIndex(_snowman, to));
        if (_snowman <= 0) {
            return Double.NEGATIVE_INFINITY;
        }
        return this.getPointPosition(axis, _snowman);
    }

    protected int getCoordIndex(Direction.Axis axis, double coord) {
        return MathHelper.binarySearch(0, this.voxels.getSize(axis) + 1, n -> {
            if (n < 0) {
                return false;
            }
            if (n > this.voxels.getSize(axis)) {
                return true;
            }
            return coord < this.getPointPosition(axis, n);
        }) - 1;
    }

    protected boolean contains(double x, double y, double z) {
        return this.voxels.inBoundsAndContains(this.getCoordIndex(Direction.Axis.X, x), this.getCoordIndex(Direction.Axis.Y, y), this.getCoordIndex(Direction.Axis.Z, z));
    }

    @Nullable
    public BlockHitResult raycast(Vec3d start, Vec3d end, BlockPos pos) {
        if (this.isEmpty()) {
            return null;
        }
        Vec3d vec3d = end.subtract(start);
        if (vec3d.lengthSquared() < 1.0E-7) {
            return null;
        }
        _snowman = start.add(vec3d.multiply(0.001));
        if (this.contains(_snowman.x - (double)pos.getX(), _snowman.y - (double)pos.getY(), _snowman.z - (double)pos.getZ())) {
            return new BlockHitResult(_snowman, Direction.getFacing(vec3d.x, vec3d.y, vec3d.z).getOpposite(), pos, true);
        }
        return Box.raycast(this.getBoundingBoxes(), start, end, pos);
    }

    public VoxelShape getFace(Direction facing) {
        VoxelShape voxelShape;
        if (this.isEmpty() || this == VoxelShapes.fullCube()) {
            return this;
        }
        if (this.shapeCache != null) {
            voxelShape = this.shapeCache[facing.ordinal()];
            if (voxelShape != null) {
                return voxelShape;
            }
        } else {
            this.shapeCache = new VoxelShape[6];
        }
        this.shapeCache[facing.ordinal()] = voxelShape = this.getUncachedFace(facing);
        return voxelShape;
    }

    private VoxelShape getUncachedFace(Direction facing) {
        Direction.Axis axis = facing.getAxis();
        Direction.AxisDirection _snowman2 = facing.getDirection();
        DoubleList _snowman3 = this.getPointPositions(axis);
        if (_snowman3.size() == 2 && DoubleMath.fuzzyEquals((double)_snowman3.getDouble(0), (double)0.0, (double)1.0E-7) && DoubleMath.fuzzyEquals((double)_snowman3.getDouble(1), (double)1.0, (double)1.0E-7)) {
            return this;
        }
        int _snowman4 = this.getCoordIndex(axis, _snowman2 == Direction.AxisDirection.POSITIVE ? 0.9999999 : 1.0E-7);
        return new SlicedVoxelShape(this, axis, _snowman4);
    }

    public double calculateMaxDistance(Direction.Axis axis, Box box, double maxDist) {
        return this.calculateMaxDistance(AxisCycleDirection.between(axis, Direction.Axis.X), box, maxDist);
    }

    protected double calculateMaxDistance(AxisCycleDirection axisCycle, Box box, double maxDist) {
        block11: {
            int _snowman12;
            int _snowman10;
            double _snowman6;
            Direction.Axis _snowman2;
            block10: {
                if (this.isEmpty()) {
                    return maxDist;
                }
                if (Math.abs(maxDist) < 1.0E-7) {
                    return 0.0;
                }
                AxisCycleDirection axisCycleDirection = axisCycle.opposite();
                _snowman2 = axisCycleDirection.cycle(Direction.Axis.X);
                Direction.Axis _snowman3 = axisCycleDirection.cycle(Direction.Axis.Y);
                Direction.Axis _snowman4 = axisCycleDirection.cycle(Direction.Axis.Z);
                double _snowman5 = box.getMax(_snowman2);
                _snowman6 = box.getMin(_snowman2);
                int _snowman7 = this.getCoordIndex(_snowman2, _snowman6 + 1.0E-7);
                int _snowman8 = this.getCoordIndex(_snowman2, _snowman5 - 1.0E-7);
                int _snowman9 = Math.max(0, this.getCoordIndex(_snowman3, box.getMin(_snowman3) + 1.0E-7));
                _snowman10 = Math.min(this.voxels.getSize(_snowman3), this.getCoordIndex(_snowman3, box.getMax(_snowman3) - 1.0E-7) + 1);
                int _snowman11 = Math.max(0, this.getCoordIndex(_snowman4, box.getMin(_snowman4) + 1.0E-7));
                _snowman12 = Math.min(this.voxels.getSize(_snowman4), this.getCoordIndex(_snowman4, box.getMax(_snowman4) - 1.0E-7) + 1);
                int _snowman13 = this.voxels.getSize(_snowman2);
                if (!(maxDist > 0.0)) break block10;
                for (int i = _snowman8 + 1; i < _snowman13; ++i) {
                    for (_snowman = _snowman9; _snowman < _snowman10; ++_snowman) {
                        for (_snowman = _snowman11; _snowman < _snowman12; ++_snowman) {
                            if (!this.voxels.inBoundsAndContains(axisCycleDirection, i, _snowman, _snowman)) continue;
                            double d = this.getPointPosition(_snowman2, i) - _snowman5;
                            if (d >= -1.0E-7) {
                                maxDist = Math.min(maxDist, d);
                            }
                            return maxDist;
                        }
                    }
                }
                break block11;
            }
            if (!(maxDist < 0.0)) break block11;
            for (int i = _snowman7 - 1; i >= 0; --i) {
                for (_snowman = _snowman9; _snowman < _snowman10; ++_snowman) {
                    for (_snowman = _snowman11; _snowman < _snowman12; ++_snowman) {
                        if (!this.voxels.inBoundsAndContains(axisCycleDirection, i, _snowman, _snowman)) continue;
                        double d = this.getPointPosition(_snowman2, i + 1) - _snowman6;
                        if (d <= 1.0E-7) {
                            maxDist = Math.max(maxDist, d);
                        }
                        return maxDist;
                    }
                }
            }
        }
        return maxDist;
    }

    public String toString() {
        return this.isEmpty() ? "EMPTY" : "VoxelShape[" + this.getBoundingBox() + "]";
    }
}

