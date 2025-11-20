/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.util.math;

import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.Validate;

public class WordPackedArray {
    private final long[] array;
    private final int unitSize;
    private final long maxValue;
    private final int length;

    public WordPackedArray(int unitSize, int length) {
        this(unitSize, length, new long[MathHelper.roundUpToMultiple(length * unitSize, 64) / 64]);
    }

    public WordPackedArray(int unitSize, int length, long[] array) {
        Validate.inclusiveBetween((long)1L, (long)32L, (long)unitSize);
        this.length = length;
        this.unitSize = unitSize;
        this.array = array;
        this.maxValue = (1L << unitSize) - 1L;
        int n = MathHelper.roundUpToMultiple(length * unitSize, 64) / 64;
        if (array.length != n) {
            throw new IllegalArgumentException("Invalid length given for storage, got: " + array.length + " but expected: " + n);
        }
    }

    public void set(int index, int value) {
        Validate.inclusiveBetween((long)0L, (long)(this.length - 1), (long)index);
        Validate.inclusiveBetween((long)0L, (long)this.maxValue, (long)value);
        int n = index * this.unitSize;
        _snowman = n >> 6;
        _snowman = (index + 1) * this.unitSize - 1 >> 6;
        _snowman = n ^ _snowman << 6;
        this.array[_snowman] = this.array[_snowman] & (this.maxValue << _snowman ^ 0xFFFFFFFFFFFFFFFFL) | ((long)value & this.maxValue) << _snowman;
        if (_snowman != _snowman) {
            _snowman = 64 - _snowman;
            _snowman = this.unitSize - _snowman;
            this.array[_snowman] = this.array[_snowman] >>> _snowman << _snowman | ((long)value & this.maxValue) >> _snowman;
        }
    }

    public int get(int index) {
        Validate.inclusiveBetween((long)0L, (long)(this.length - 1), (long)index);
        int n = index * this.unitSize;
        _snowman = n >> 6;
        _snowman = (index + 1) * this.unitSize - 1 >> 6;
        _snowman = n ^ _snowman << 6;
        if (_snowman == _snowman) {
            return (int)(this.array[_snowman] >>> _snowman & this.maxValue);
        }
        _snowman = 64 - _snowman;
        return (int)((this.array[_snowman] >>> _snowman | this.array[_snowman] << _snowman) & this.maxValue);
    }

    public long[] getAlignedArray() {
        return this.array;
    }

    public int getUnitSize() {
        return this.unitSize;
    }
}

