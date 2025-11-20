/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.math.DoubleMath
 *  com.google.common.math.IntMath
 *  it.unimi.dsi.fastutil.doubles.DoubleArrayList
 *  it.unimi.dsi.fastutil.doubles.DoubleList
 */
package net.minecraft.util.shape;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.Util;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.AxisCycleDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.ArrayVoxelShape;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.DisjointPairList;
import net.minecraft.util.shape.FractionalDoubleList;
import net.minecraft.util.shape.FractionalPairList;
import net.minecraft.util.shape.IdentityPairList;
import net.minecraft.util.shape.PairList;
import net.minecraft.util.shape.SimplePairList;
import net.minecraft.util.shape.SimpleVoxelShape;
import net.minecraft.util.shape.SlicedVoxelShape;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.WorldView;

public final class VoxelShapes {
    private static final VoxelShape FULL_CUBE = Util.make(() -> {
        BitSetVoxelSet bitSetVoxelSet = new BitSetVoxelSet(1, 1, 1);
        ((VoxelSet)bitSetVoxelSet).set(0, 0, 0, true, true);
        return new SimpleVoxelShape(bitSetVoxelSet);
    });
    public static final VoxelShape UNBOUNDED = VoxelShapes.cuboid(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    private static final VoxelShape EMPTY = new ArrayVoxelShape((VoxelSet)new BitSetVoxelSet(0, 0, 0), (DoubleList)new DoubleArrayList(new double[]{0.0}), (DoubleList)new DoubleArrayList(new double[]{0.0}), (DoubleList)new DoubleArrayList(new double[]{0.0}));

    public static VoxelShape empty() {
        return EMPTY;
    }

    public static VoxelShape fullCube() {
        return FULL_CUBE;
    }

    public static VoxelShape cuboid(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
        return VoxelShapes.cuboid(new Box(xMin, yMin, zMin, xMax, yMax, zMax));
    }

    public static VoxelShape cuboid(Box box) {
        int n = VoxelShapes.findRequiredBitResolution(box.minX, box.maxX);
        _snowman = VoxelShapes.findRequiredBitResolution(box.minY, box.maxY);
        _snowman = VoxelShapes.findRequiredBitResolution(box.minZ, box.maxZ);
        if (n < 0 || _snowman < 0 || _snowman < 0) {
            return new ArrayVoxelShape(VoxelShapes.FULL_CUBE.voxels, new double[]{box.minX, box.maxX}, new double[]{box.minY, box.maxY}, new double[]{box.minZ, box.maxZ});
        }
        if (n == 0 && _snowman == 0 && _snowman == 0) {
            return box.contains(0.5, 0.5, 0.5) ? VoxelShapes.fullCube() : VoxelShapes.empty();
        }
        _snowman = 1 << n;
        _snowman = 1 << _snowman;
        _snowman = 1 << _snowman;
        _snowman = (int)Math.round(box.minX * (double)_snowman);
        _snowman = (int)Math.round(box.maxX * (double)_snowman);
        _snowman = (int)Math.round(box.minY * (double)_snowman);
        _snowman = (int)Math.round(box.maxY * (double)_snowman);
        _snowman = (int)Math.round(box.minZ * (double)_snowman);
        _snowman = (int)Math.round(box.maxZ * (double)_snowman);
        BitSetVoxelSet _snowman2 = new BitSetVoxelSet(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
        for (long i = (long)_snowman; i < (long)_snowman; ++i) {
            for (_snowman = (long)_snowman; _snowman < (long)_snowman; ++_snowman) {
                for (_snowman = (long)_snowman; _snowman < (long)_snowman; ++_snowman) {
                    _snowman2.set((int)i, (int)_snowman, (int)_snowman, false, true);
                }
            }
        }
        return new SimpleVoxelShape(_snowman2);
    }

    private static int findRequiredBitResolution(double min, double max) {
        if (min < -1.0E-7 || max > 1.0000001) {
            return -1;
        }
        for (int i = 0; i <= 3; ++i) {
            double d = min * (double)(1 << i);
            _snowman = max * (double)(1 << i);
            boolean _snowman2 = Math.abs(d - Math.floor(d)) < 1.0E-7;
            boolean bl = _snowman = Math.abs(_snowman - Math.floor(_snowman)) < 1.0E-7;
            if (!_snowman2 || !_snowman) continue;
            return i;
        }
        return -1;
    }

    protected static long lcm(int a, int b) {
        return (long)a * (long)(b / IntMath.gcd((int)a, (int)b));
    }

    public static VoxelShape union(VoxelShape first, VoxelShape second) {
        return VoxelShapes.combineAndSimplify(first, second, BooleanBiFunction.OR);
    }

    public static VoxelShape union(VoxelShape first, VoxelShape ... others) {
        return Arrays.stream(others).reduce(first, VoxelShapes::union);
    }

    public static VoxelShape combineAndSimplify(VoxelShape first, VoxelShape second, BooleanBiFunction function) {
        return VoxelShapes.combine(first, second, function).simplify();
    }

    public static VoxelShape combine(VoxelShape one, VoxelShape two, BooleanBiFunction function) {
        if (function.apply(false, false)) {
            throw Util.throwOrPause(new IllegalArgumentException());
        }
        if (one == two) {
            return function.apply(true, true) ? one : VoxelShapes.empty();
        }
        boolean bl = function.apply(true, false);
        _snowman = function.apply(false, true);
        if (one.isEmpty()) {
            return _snowman ? two : VoxelShapes.empty();
        }
        if (two.isEmpty()) {
            return bl ? one : VoxelShapes.empty();
        }
        PairList _snowman2 = VoxelShapes.createListPair(1, one.getPointPositions(Direction.Axis.X), two.getPointPositions(Direction.Axis.X), bl, _snowman);
        PairList _snowman3 = VoxelShapes.createListPair(_snowman2.getPairs().size() - 1, one.getPointPositions(Direction.Axis.Y), two.getPointPositions(Direction.Axis.Y), bl, _snowman);
        PairList _snowman4 = VoxelShapes.createListPair((_snowman2.getPairs().size() - 1) * (_snowman3.getPairs().size() - 1), one.getPointPositions(Direction.Axis.Z), two.getPointPositions(Direction.Axis.Z), bl, _snowman);
        BitSetVoxelSet _snowman5 = BitSetVoxelSet.combine(one.voxels, two.voxels, _snowman2, _snowman3, _snowman4, function);
        if (_snowman2 instanceof FractionalPairList && _snowman3 instanceof FractionalPairList && _snowman4 instanceof FractionalPairList) {
            return new SimpleVoxelShape(_snowman5);
        }
        return new ArrayVoxelShape((VoxelSet)_snowman5, _snowman2.getPairs(), _snowman3.getPairs(), _snowman4.getPairs());
    }

    public static boolean matchesAnywhere(VoxelShape shape1, VoxelShape shape2, BooleanBiFunction predicate) {
        if (predicate.apply(false, false)) {
            throw Util.throwOrPause(new IllegalArgumentException());
        }
        if (shape1 == shape2) {
            return predicate.apply(true, true);
        }
        if (shape1.isEmpty()) {
            return predicate.apply(false, !shape2.isEmpty());
        }
        if (shape2.isEmpty()) {
            return predicate.apply(!shape1.isEmpty(), false);
        }
        boolean bl = predicate.apply(true, false);
        _snowman = predicate.apply(false, true);
        for (Direction.Axis axis : AxisCycleDirection.AXES) {
            if (shape1.getMax(axis) < shape2.getMin(axis) - 1.0E-7) {
                return bl || _snowman;
            }
            if (!(shape2.getMax(axis) < shape1.getMin(axis) - 1.0E-7)) continue;
            return bl || _snowman;
        }
        PairList _snowman2 = VoxelShapes.createListPair(1, shape1.getPointPositions(Direction.Axis.X), shape2.getPointPositions(Direction.Axis.X), bl, _snowman);
        PairList _snowman3 = VoxelShapes.createListPair(_snowman2.getPairs().size() - 1, shape1.getPointPositions(Direction.Axis.Y), shape2.getPointPositions(Direction.Axis.Y), bl, _snowman);
        PairList _snowman4 = VoxelShapes.createListPair((_snowman2.getPairs().size() - 1) * (_snowman3.getPairs().size() - 1), shape1.getPointPositions(Direction.Axis.Z), shape2.getPointPositions(Direction.Axis.Z), bl, _snowman);
        return VoxelShapes.matchesAnywhere(_snowman2, _snowman3, _snowman4, shape1.voxels, shape2.voxels, predicate);
    }

    private static boolean matchesAnywhere(PairList mergedX, PairList mergedY, PairList mergedZ, VoxelSet shape1, VoxelSet shape2, BooleanBiFunction predicate) {
        return !mergedX.forEachPair((x1, x2, index1) -> mergedY.forEachPair((y1, y2, index2) -> mergedZ.forEachPair((z1, z2, index3) -> !predicate.apply(shape1.inBoundsAndContains(x1, y1, z1), shape2.inBoundsAndContains(x2, y2, z2)))));
    }

    public static double calculateMaxOffset(Direction.Axis axis, Box box, Stream<VoxelShape> shapes, double maxDist) {
        Iterator iterator = shapes.iterator();
        while (iterator.hasNext()) {
            if (Math.abs(maxDist) < 1.0E-7) {
                return 0.0;
            }
            maxDist = ((VoxelShape)iterator.next()).calculateMaxDistance(axis, box, maxDist);
        }
        return maxDist;
    }

    public static double calculatePushVelocity(Direction.Axis axis, Box box, WorldView world, double initial, ShapeContext context, Stream<VoxelShape> shapes) {
        return VoxelShapes.calculatePushVelocity(box, world, initial, context, AxisCycleDirection.between(axis, Direction.Axis.Z), shapes);
    }

    private static double calculatePushVelocity(Box box, WorldView world, double initial, ShapeContext context, AxisCycleDirection direction, Stream<VoxelShape> shapes) {
        if (box.getXLength() < 1.0E-6 || box.getYLength() < 1.0E-6 || box.getZLength() < 1.0E-6) {
            return initial;
        }
        if (Math.abs(initial) < 1.0E-7) {
            return 0.0;
        }
        AxisCycleDirection axisCycleDirection = direction.opposite();
        Direction.Axis _snowman2 = axisCycleDirection.cycle(Direction.Axis.X);
        Direction.Axis _snowman3 = axisCycleDirection.cycle(Direction.Axis.Y);
        Direction.Axis _snowman4 = axisCycleDirection.cycle(Direction.Axis.Z);
        BlockPos.Mutable _snowman5 = new BlockPos.Mutable();
        int _snowman6 = MathHelper.floor(box.getMin(_snowman2) - 1.0E-7) - 1;
        int _snowman7 = MathHelper.floor(box.getMax(_snowman2) + 1.0E-7) + 1;
        int _snowman8 = MathHelper.floor(box.getMin(_snowman3) - 1.0E-7) - 1;
        int _snowman9 = MathHelper.floor(box.getMax(_snowman3) + 1.0E-7) + 1;
        double _snowman10 = box.getMin(_snowman4) - 1.0E-7;
        double _snowman11 = box.getMax(_snowman4) + 1.0E-7;
        boolean _snowman12 = initial > 0.0;
        int _snowman13 = _snowman12 ? MathHelper.floor(box.getMax(_snowman4) - 1.0E-7) - 1 : MathHelper.floor(box.getMin(_snowman4) + 1.0E-7) + 1;
        int _snowman14 = VoxelShapes.clamp(initial, _snowman10, _snowman11);
        int _snowman15 = _snowman12 ? 1 : -1;
        int _snowman16 = _snowman13;
        while (_snowman12 ? _snowman16 <= _snowman14 : _snowman16 >= _snowman14) {
            for (int i = _snowman6; i <= _snowman7; ++i) {
                for (_snowman = _snowman8; _snowman <= _snowman9; ++_snowman) {
                    _snowman = 0;
                    if (i == _snowman6 || i == _snowman7) {
                        ++_snowman;
                    }
                    if (_snowman == _snowman8 || _snowman == _snowman9) {
                        ++_snowman;
                    }
                    if (_snowman16 == _snowman13 || _snowman16 == _snowman14) {
                        ++_snowman;
                    }
                    if (_snowman >= 3) continue;
                    _snowman5.set(axisCycleDirection, i, _snowman, _snowman16);
                    BlockState blockState = world.getBlockState(_snowman5);
                    if (_snowman == 1 && !blockState.exceedsCube() || _snowman == 2 && !blockState.isOf(Blocks.MOVING_PISTON)) continue;
                    initial = blockState.getCollisionShape(world, _snowman5, context).calculateMaxDistance(_snowman4, box.offset(-_snowman5.getX(), -_snowman5.getY(), -_snowman5.getZ()), initial);
                    if (Math.abs(initial) < 1.0E-7) {
                        return 0.0;
                    }
                    _snowman14 = VoxelShapes.clamp(initial, _snowman10, _snowman11);
                }
            }
            _snowman16 += _snowman15;
        }
        double[] dArray = new double[]{initial};
        shapes.forEach(voxelShape -> {
            dArray[0] = voxelShape.calculateMaxDistance(_snowman4, box, dArray[0]);
        });
        return dArray[0];
    }

    private static int clamp(double value, double min, double max) {
        return value > 0.0 ? MathHelper.floor(max + value) + 1 : MathHelper.floor(min + value) - 1;
    }

    public static boolean isSideCovered(VoxelShape shape, VoxelShape neighbor, Direction direction) {
        if (shape == VoxelShapes.fullCube() && neighbor == VoxelShapes.fullCube()) {
            return true;
        }
        if (neighbor.isEmpty()) {
            return false;
        }
        Direction.Axis axis = direction.getAxis();
        Direction.AxisDirection _snowman2 = direction.getDirection();
        VoxelShape _snowman3 = _snowman2 == Direction.AxisDirection.POSITIVE ? shape : neighbor;
        VoxelShape _snowman4 = _snowman2 == Direction.AxisDirection.POSITIVE ? neighbor : shape;
        BooleanBiFunction _snowman5 = _snowman2 == Direction.AxisDirection.POSITIVE ? BooleanBiFunction.ONLY_FIRST : BooleanBiFunction.ONLY_SECOND;
        return DoubleMath.fuzzyEquals((double)_snowman3.getMax(axis), (double)1.0, (double)1.0E-7) && DoubleMath.fuzzyEquals((double)_snowman4.getMin(axis), (double)0.0, (double)1.0E-7) && !VoxelShapes.matchesAnywhere(new SlicedVoxelShape(_snowman3, axis, _snowman3.voxels.getSize(axis) - 1), new SlicedVoxelShape(_snowman4, axis, 0), _snowman5);
    }

    public static VoxelShape extrudeFace(VoxelShape shape, Direction direction) {
        int _snowman2;
        if (shape == VoxelShapes.fullCube()) {
            return VoxelShapes.fullCube();
        }
        Direction.Axis axis = direction.getAxis();
        if (direction.getDirection() == Direction.AxisDirection.POSITIVE) {
            boolean bl = DoubleMath.fuzzyEquals((double)shape.getMax(axis), (double)1.0, (double)1.0E-7);
            _snowman2 = shape.voxels.getSize(axis) - 1;
        } else {
            bl = DoubleMath.fuzzyEquals((double)shape.getMin(axis), (double)0.0, (double)1.0E-7);
            _snowman2 = 0;
        }
        if (!bl) {
            return VoxelShapes.empty();
        }
        return new SlicedVoxelShape(shape, axis, _snowman2);
    }

    public static boolean adjacentSidesCoverSquare(VoxelShape one, VoxelShape two, Direction direction) {
        if (one == VoxelShapes.fullCube() || two == VoxelShapes.fullCube()) {
            return true;
        }
        Direction.Axis axis = direction.getAxis();
        Direction.AxisDirection _snowman2 = direction.getDirection();
        VoxelShape _snowman3 = _snowman2 == Direction.AxisDirection.POSITIVE ? one : two;
        VoxelShape voxelShape = voxelShape2 = _snowman2 == Direction.AxisDirection.POSITIVE ? two : one;
        if (!DoubleMath.fuzzyEquals((double)_snowman3.getMax(axis), (double)1.0, (double)1.0E-7)) {
            _snowman3 = VoxelShapes.empty();
        }
        if (!DoubleMath.fuzzyEquals((double)voxelShape2.getMin(axis), (double)0.0, (double)1.0E-7)) {
            VoxelShape voxelShape2 = VoxelShapes.empty();
        }
        return !VoxelShapes.matchesAnywhere(VoxelShapes.fullCube(), VoxelShapes.combine(new SlicedVoxelShape(_snowman3, axis, _snowman3.voxels.getSize(axis) - 1), new SlicedVoxelShape(voxelShape2, axis, 0), BooleanBiFunction.OR), BooleanBiFunction.ONLY_FIRST);
    }

    public static boolean unionCoversFullCube(VoxelShape one, VoxelShape two) {
        if (one == VoxelShapes.fullCube() || two == VoxelShapes.fullCube()) {
            return true;
        }
        if (one.isEmpty() && two.isEmpty()) {
            return false;
        }
        return !VoxelShapes.matchesAnywhere(VoxelShapes.fullCube(), VoxelShapes.combine(one, two, BooleanBiFunction.OR), BooleanBiFunction.ONLY_FIRST);
    }

    @VisibleForTesting
    protected static PairList createListPair(int size, DoubleList first, DoubleList second, boolean includeFirst, boolean includeSecond) {
        int n = first.size() - 1;
        _snowman = second.size() - 1;
        if (first instanceof FractionalDoubleList && second instanceof FractionalDoubleList && (long)size * (_snowman = VoxelShapes.lcm(n, _snowman)) <= 256L) {
            return new FractionalPairList(n, _snowman);
        }
        if (first.getDouble(n) < second.getDouble(0) - 1.0E-7) {
            return new DisjointPairList(first, second, false);
        }
        if (second.getDouble(_snowman) < first.getDouble(0) - 1.0E-7) {
            return new DisjointPairList(second, first, true);
        }
        if (n == _snowman && Objects.equals(first, second)) {
            if (first instanceof IdentityPairList) {
                return (PairList)first;
            }
            if (second instanceof IdentityPairList) {
                return (PairList)second;
            }
            return new IdentityPairList(first);
        }
        return new SimplePairList(first, second, includeFirst, includeSecond);
    }

    public static interface BoxConsumer {
        public void consume(double var1, double var3, double var5, double var7, double var9, double var11);
    }
}

