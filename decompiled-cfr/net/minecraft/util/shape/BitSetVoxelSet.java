/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.shape;

import java.util.BitSet;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.PairList;
import net.minecraft.util.shape.VoxelSet;

public final class BitSetVoxelSet
extends VoxelSet {
    private final BitSet storage;
    private int xMin;
    private int yMin;
    private int zMin;
    private int xMax;
    private int yMax;
    private int zMax;

    public BitSetVoxelSet(int n, int n2, int n3) {
        this(n, n2, n3, n, n2, n3, 0, 0, 0);
    }

    public BitSetVoxelSet(int xMask, int yMask, int zMask, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
        super(xMask, yMask, zMask);
        this.storage = new BitSet(xMask * yMask * zMask);
        this.xMin = xMin;
        this.yMin = yMin;
        this.zMin = zMin;
        this.xMax = xMax;
        this.yMax = yMax;
        this.zMax = zMax;
    }

    public BitSetVoxelSet(VoxelSet other) {
        super(other.xSize, other.ySize, other.zSize);
        if (other instanceof BitSetVoxelSet) {
            this.storage = (BitSet)((BitSetVoxelSet)other).storage.clone();
        } else {
            this.storage = new BitSet(this.xSize * this.ySize * this.zSize);
            for (int i = 0; i < this.xSize; ++i) {
                for (_snowman = 0; _snowman < this.ySize; ++_snowman) {
                    for (_snowman = 0; _snowman < this.zSize; ++_snowman) {
                        if (!other.contains(i, _snowman, _snowman)) continue;
                        this.storage.set(this.getIndex(i, _snowman, _snowman));
                    }
                }
            }
        }
        this.xMin = other.getMin(Direction.Axis.X);
        this.yMin = other.getMin(Direction.Axis.Y);
        this.zMin = other.getMin(Direction.Axis.Z);
        this.xMax = other.getMax(Direction.Axis.X);
        this.yMax = other.getMax(Direction.Axis.Y);
        this.zMax = other.getMax(Direction.Axis.Z);
    }

    protected int getIndex(int x, int y, int z) {
        return (x * this.ySize + y) * this.zSize + z;
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return this.storage.get(this.getIndex(x, y, z));
    }

    @Override
    public void set(int x, int y, int z, boolean resize, boolean included) {
        this.storage.set(this.getIndex(x, y, z), included);
        if (resize && included) {
            this.xMin = Math.min(this.xMin, x);
            this.yMin = Math.min(this.yMin, y);
            this.zMin = Math.min(this.zMin, z);
            this.xMax = Math.max(this.xMax, x + 1);
            this.yMax = Math.max(this.yMax, y + 1);
            this.zMax = Math.max(this.zMax, z + 1);
        }
    }

    @Override
    public boolean isEmpty() {
        return this.storage.isEmpty();
    }

    @Override
    public int getMin(Direction.Axis axis) {
        return axis.choose(this.xMin, this.yMin, this.zMin);
    }

    @Override
    public int getMax(Direction.Axis axis) {
        return axis.choose(this.xMax, this.yMax, this.zMax);
    }

    @Override
    protected boolean isColumnFull(int minZ, int maxZ, int x, int y) {
        if (x < 0 || y < 0 || minZ < 0) {
            return false;
        }
        if (x >= this.xSize || y >= this.ySize || maxZ > this.zSize) {
            return false;
        }
        return this.storage.nextClearBit(this.getIndex(x, y, minZ)) >= this.getIndex(x, y, maxZ);
    }

    @Override
    protected void setColumn(int minZ, int maxZ, int x, int y, boolean included) {
        this.storage.set(this.getIndex(x, y, minZ), this.getIndex(x, y, maxZ), included);
    }

    static BitSetVoxelSet combine(VoxelSet first, VoxelSet second, PairList xPoints, PairList yPoints, PairList zPoints, BooleanBiFunction function) {
        BitSetVoxelSet bitSetVoxelSet = new BitSetVoxelSet(xPoints.getPairs().size() - 1, yPoints.getPairs().size() - 1, zPoints.getPairs().size() - 1);
        int[] _snowman2 = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
        xPoints.forEachPair((n, n2, n3) -> {
            boolean[] blArray = new boolean[]{false};
            boolean _snowman2 = yPoints.forEachPair((n4, n5, n6) -> {
                boolean[] blArray2 = new boolean[]{false};
                boolean _snowman2 = zPoints.forEachPair((n7, n8, n9) -> {
                    boolean bl = function.apply(first.inBoundsAndContains(n, n4, n7), second.inBoundsAndContains(n2, n5, n8));
                    if (bl) {
                        bitSetVoxelSet.storage.set(bitSetVoxelSet.getIndex(n3, n6, n9));
                        nArray[2] = Math.min(_snowman2[2], n9);
                        nArray[5] = Math.max(_snowman2[5], n9);
                        blArray[0] = true;
                    }
                    return true;
                });
                if (blArray2[0]) {
                    nArray[1] = Math.min(_snowman2[1], n6);
                    nArray[4] = Math.max(_snowman2[4], n6);
                    blArray[0] = true;
                }
                return _snowman2;
            });
            if (blArray[0]) {
                nArray[0] = Math.min(_snowman2[0], n3);
                nArray[3] = Math.max(_snowman2[3], n3);
            }
            return _snowman2;
        });
        bitSetVoxelSet.xMin = _snowman2[0];
        bitSetVoxelSet.yMin = _snowman2[1];
        bitSetVoxelSet.zMin = _snowman2[2];
        bitSetVoxelSet.xMax = _snowman2[3] + 1;
        bitSetVoxelSet.yMax = _snowman2[4] + 1;
        bitSetVoxelSet.zMax = _snowman2[5] + 1;
        return bitSetVoxelSet;
    }
}

