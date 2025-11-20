/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.shape;

import net.minecraft.util.math.AxisCycleDirection;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.BitSetVoxelSet;

public abstract class VoxelSet {
    private static final Direction.Axis[] AXES = Direction.Axis.values();
    protected final int xSize;
    protected final int ySize;
    protected final int zSize;

    protected VoxelSet(int xSize, int ySize, int zSize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
    }

    public boolean inBoundsAndContains(AxisCycleDirection cycle, int x, int y, int z) {
        return this.inBoundsAndContains(cycle.choose(x, y, z, Direction.Axis.X), cycle.choose(x, y, z, Direction.Axis.Y), cycle.choose(x, y, z, Direction.Axis.Z));
    }

    public boolean inBoundsAndContains(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0) {
            return false;
        }
        if (x >= this.xSize || y >= this.ySize || z >= this.zSize) {
            return false;
        }
        return this.contains(x, y, z);
    }

    public boolean contains(AxisCycleDirection cycle, int x, int y, int z) {
        return this.contains(cycle.choose(x, y, z, Direction.Axis.X), cycle.choose(x, y, z, Direction.Axis.Y), cycle.choose(x, y, z, Direction.Axis.Z));
    }

    public abstract boolean contains(int var1, int var2, int var3);

    public abstract void set(int var1, int var2, int var3, boolean var4, boolean var5);

    public boolean isEmpty() {
        for (Direction.Axis axis : AXES) {
            if (this.getMin(axis) < this.getMax(axis)) continue;
            return true;
        }
        return false;
    }

    public abstract int getMin(Direction.Axis var1);

    public abstract int getMax(Direction.Axis var1);

    public int getEndingAxisCoord(Direction.Axis axis, int from, int to) {
        if (from < 0 || to < 0) {
            return 0;
        }
        _snowman = AxisCycleDirection.FORWARD.cycle(axis);
        _snowman = AxisCycleDirection.BACKWARD.cycle(axis);
        if (from >= this.getSize(_snowman) || to >= this.getSize(_snowman)) {
            return 0;
        }
        int n = this.getSize(axis);
        AxisCycleDirection _snowman2 = AxisCycleDirection.between(Direction.Axis.X, axis);
        for (_snowman = n - 1; _snowman >= 0; --_snowman) {
            if (!this.contains(_snowman2, _snowman, from, to)) continue;
            return _snowman + 1;
        }
        return 0;
    }

    public int getSize(Direction.Axis axis) {
        return axis.choose(this.xSize, this.ySize, this.zSize);
    }

    public int getXSize() {
        return this.getSize(Direction.Axis.X);
    }

    public int getYSize() {
        return this.getSize(Direction.Axis.Y);
    }

    public int getZSize() {
        return this.getSize(Direction.Axis.Z);
    }

    public void forEachEdge(PositionBiConsumer positionBiConsumer, boolean bl) {
        this.forEachEdge(positionBiConsumer, AxisCycleDirection.NONE, bl);
        this.forEachEdge(positionBiConsumer, AxisCycleDirection.FORWARD, bl);
        this.forEachEdge(positionBiConsumer, AxisCycleDirection.BACKWARD, bl);
    }

    private void forEachEdge(PositionBiConsumer positionBiConsumer, AxisCycleDirection direction, boolean bl) {
        AxisCycleDirection axisCycleDirection = direction.opposite();
        int _snowman2 = this.getSize(axisCycleDirection.cycle(Direction.Axis.X));
        int _snowman3 = this.getSize(axisCycleDirection.cycle(Direction.Axis.Y));
        int _snowman4 = this.getSize(axisCycleDirection.cycle(Direction.Axis.Z));
        for (int i = 0; i <= _snowman2; ++i) {
            for (_snowman = 0; _snowman <= _snowman3; ++_snowman) {
                _snowman = -1;
                for (_snowman = 0; _snowman <= _snowman4; ++_snowman) {
                    _snowman = 0;
                    _snowman = 0;
                    for (_snowman = 0; _snowman <= 1; ++_snowman) {
                        for (_snowman = 0; _snowman <= 1; ++_snowman) {
                            if (!this.inBoundsAndContains(axisCycleDirection, i + _snowman - 1, _snowman + _snowman - 1, _snowman)) continue;
                            ++_snowman;
                            _snowman ^= _snowman ^ _snowman;
                        }
                    }
                    if (_snowman == 1 || _snowman == 3 || _snowman == 2 && !(_snowman & true)) {
                        if (bl) {
                            if (_snowman != -1) continue;
                            _snowman = _snowman;
                            continue;
                        }
                        positionBiConsumer.consume(axisCycleDirection.choose(i, _snowman, _snowman, Direction.Axis.X), axisCycleDirection.choose(i, _snowman, _snowman, Direction.Axis.Y), axisCycleDirection.choose(i, _snowman, _snowman, Direction.Axis.Z), axisCycleDirection.choose(i, _snowman, _snowman + 1, Direction.Axis.X), axisCycleDirection.choose(i, _snowman, _snowman + 1, Direction.Axis.Y), axisCycleDirection.choose(i, _snowman, _snowman + 1, Direction.Axis.Z));
                        continue;
                    }
                    if (_snowman == -1) continue;
                    positionBiConsumer.consume(axisCycleDirection.choose(i, _snowman, _snowman, Direction.Axis.X), axisCycleDirection.choose(i, _snowman, _snowman, Direction.Axis.Y), axisCycleDirection.choose(i, _snowman, _snowman, Direction.Axis.Z), axisCycleDirection.choose(i, _snowman, _snowman, Direction.Axis.X), axisCycleDirection.choose(i, _snowman, _snowman, Direction.Axis.Y), axisCycleDirection.choose(i, _snowman, _snowman, Direction.Axis.Z));
                    _snowman = -1;
                }
            }
        }
    }

    protected boolean isColumnFull(int minZ, int maxZ, int x, int y) {
        for (int i = minZ; i < maxZ; ++i) {
            if (this.inBoundsAndContains(x, y, i)) continue;
            return false;
        }
        return true;
    }

    protected void setColumn(int minZ, int maxZ, int x, int y, boolean included) {
        for (int i = minZ; i < maxZ; ++i) {
            this.set(x, y, i, false, included);
        }
    }

    protected boolean isRectangleFull(int minX, int maxX, int minZ, int maxZ, int y) {
        for (int i = minX; i < maxX; ++i) {
            if (this.isColumnFull(minZ, maxZ, i, y)) continue;
            return false;
        }
        return true;
    }

    public void forEachBox(PositionBiConsumer consumer, boolean largest) {
        BitSetVoxelSet bitSetVoxelSet = new BitSetVoxelSet(this);
        for (int i = 0; i <= this.xSize; ++i) {
            for (_snowman = 0; _snowman <= this.ySize; ++_snowman) {
                _snowman = -1;
                for (_snowman = 0; _snowman <= this.zSize; ++_snowman) {
                    if (bitSetVoxelSet.inBoundsAndContains(i, _snowman, _snowman)) {
                        if (largest) {
                            if (_snowman != -1) continue;
                            _snowman = _snowman;
                            continue;
                        }
                        consumer.consume(i, _snowman, _snowman, i + 1, _snowman + 1, _snowman + 1);
                        continue;
                    }
                    if (_snowman == -1) continue;
                    _snowman = i;
                    _snowman = i;
                    _snowman = _snowman;
                    _snowman = _snowman;
                    ((VoxelSet)bitSetVoxelSet).setColumn(_snowman, _snowman, i, _snowman, false);
                    while (((VoxelSet)bitSetVoxelSet).isColumnFull(_snowman, _snowman, _snowman - 1, _snowman)) {
                        ((VoxelSet)bitSetVoxelSet).setColumn(_snowman, _snowman, _snowman - 1, _snowman, false);
                        --_snowman;
                    }
                    while (((VoxelSet)bitSetVoxelSet).isColumnFull(_snowman, _snowman, _snowman + 1, _snowman)) {
                        ((VoxelSet)bitSetVoxelSet).setColumn(_snowman, _snowman, _snowman + 1, _snowman, false);
                        ++_snowman;
                    }
                    while (bitSetVoxelSet.isRectangleFull(_snowman, _snowman + 1, _snowman, _snowman, _snowman - 1)) {
                        for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                            ((VoxelSet)bitSetVoxelSet).setColumn(_snowman, _snowman, _snowman, _snowman - 1, false);
                        }
                        --_snowman;
                    }
                    while (bitSetVoxelSet.isRectangleFull(_snowman, _snowman + 1, _snowman, _snowman, _snowman + 1)) {
                        for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                            ((VoxelSet)bitSetVoxelSet).setColumn(_snowman, _snowman, _snowman, _snowman + 1, false);
                        }
                        ++_snowman;
                    }
                    consumer.consume(_snowman, _snowman, _snowman, _snowman + 1, _snowman + 1, _snowman);
                    _snowman = -1;
                }
            }
        }
    }

    public void forEachDirection(PositionConsumer positionConsumer) {
        this.forEachDirection(positionConsumer, AxisCycleDirection.NONE);
        this.forEachDirection(positionConsumer, AxisCycleDirection.FORWARD);
        this.forEachDirection(positionConsumer, AxisCycleDirection.BACKWARD);
    }

    private void forEachDirection(PositionConsumer positionConsumer, AxisCycleDirection direction) {
        AxisCycleDirection axisCycleDirection = direction.opposite();
        Direction.Axis _snowman2 = axisCycleDirection.cycle(Direction.Axis.Z);
        int _snowman3 = this.getSize(axisCycleDirection.cycle(Direction.Axis.X));
        int _snowman4 = this.getSize(axisCycleDirection.cycle(Direction.Axis.Y));
        int _snowman5 = this.getSize(_snowman2);
        Direction _snowman6 = Direction.from(_snowman2, Direction.AxisDirection.NEGATIVE);
        Direction _snowman7 = Direction.from(_snowman2, Direction.AxisDirection.POSITIVE);
        for (int i = 0; i < _snowman3; ++i) {
            for (_snowman = 0; _snowman < _snowman4; ++_snowman) {
                boolean bl = false;
                for (int j = 0; j <= _snowman5; ++j) {
                    boolean bl2 = _snowman = j != _snowman5 && this.contains(axisCycleDirection, i, _snowman, j);
                    if (!bl && _snowman) {
                        positionConsumer.consume(_snowman6, axisCycleDirection.choose(i, _snowman, j, Direction.Axis.X), axisCycleDirection.choose(i, _snowman, j, Direction.Axis.Y), axisCycleDirection.choose(i, _snowman, j, Direction.Axis.Z));
                    }
                    if (bl && !_snowman) {
                        positionConsumer.consume(_snowman7, axisCycleDirection.choose(i, _snowman, j - 1, Direction.Axis.X), axisCycleDirection.choose(i, _snowman, j - 1, Direction.Axis.Y), axisCycleDirection.choose(i, _snowman, j - 1, Direction.Axis.Z));
                    }
                    bl = _snowman;
                }
            }
        }
    }

    public static interface PositionConsumer {
        public void consume(Direction var1, int var2, int var3, int var4);
    }

    public static interface PositionBiConsumer {
        public void consume(int var1, int var2, int var3, int var4, int var5, int var6);
    }
}

