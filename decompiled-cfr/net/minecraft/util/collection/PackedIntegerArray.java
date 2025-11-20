/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 */
package net.minecraft.util.collection;

import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import org.apache.commons.lang3.Validate;

public class PackedIntegerArray {
    private static final int[] field_24078 = new int[]{-1, -1, 0, Integer.MIN_VALUE, 0, 0, 0x55555555, 0x55555555, 0, Integer.MIN_VALUE, 0, 1, 0x33333333, 0x33333333, 0, 0x2AAAAAAA, 0x2AAAAAAA, 0, 0x24924924, 0x24924924, 0, Integer.MIN_VALUE, 0, 2, 0x1C71C71C, 0x1C71C71C, 0, 0x19999999, 0x19999999, 0, 390451572, 390451572, 0, 0x15555555, 0x15555555, 0, 0x13B13B13, 0x13B13B13, 0, 306783378, 306783378, 0, 0x11111111, 0x11111111, 0, Integer.MIN_VALUE, 0, 3, 0xF0F0F0F, 0xF0F0F0F, 0, 0xE38E38E, 0xE38E38E, 0, 226050910, 226050910, 0, 0xCCCCCCC, 0xCCCCCCC, 0, 0xC30C30C, 0xC30C30C, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 0xAAAAAAA, 0xAAAAAAA, 0, 171798691, 171798691, 0, 0x9D89D89, 0x9D89D89, 0, 159072862, 159072862, 0, 0x9249249, 0x9249249, 0, 148102320, 148102320, 0, 0x8888888, 0x8888888, 0, 138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 0x7878787, 0x7878787, 0, 0x7507507, 0x7507507, 0, 0x71C71C7, 0x71C71C7, 0, 116080197, 116080197, 0, 113025455, 113025455, 0, 0x6906906, 0x6906906, 0, 0x6666666, 0x6666666, 0, 104755299, 104755299, 0, 0x6186186, 0x6186186, 0, 99882960, 99882960, 0, 97612893, 97612893, 0, 0x5B05B05, 0x5B05B05, 0, 93368854, 93368854, 0, 91382282, 91382282, 0, 0x5555555, 0x5555555, 0, 87652393, 87652393, 0, 85899345, 85899345, 0, 0x5050505, 0x5050505, 0, 0x4EC4EC4, 0x4EC4EC4, 0, 81037118, 81037118, 0, 79536431, 79536431, 0, 78090314, 78090314, 0, 0x4924924, 0x4924924, 0, 75350303, 75350303, 0, 74051160, 74051160, 0, 72796055, 72796055, 0, 0x4444444, 0x4444444, 0, 70409299, 70409299, 0, 69273666, 69273666, 0, 0x4104104, 0x4104104, 0, Integer.MIN_VALUE, 0, 5};
    private final long[] storage;
    private final int elementBits;
    private final long maxValue;
    private final int size;
    private final int field_24079;
    private final int field_24080;
    private final int field_24081;
    private final int field_24082;

    public PackedIntegerArray(int elementBits, int size) {
        this(elementBits, size, null);
    }

    public PackedIntegerArray(int elementBits, int size, @Nullable long[] storage) {
        Validate.inclusiveBetween((long)1L, (long)32L, (long)elementBits);
        this.size = size;
        this.elementBits = elementBits;
        this.maxValue = (1L << elementBits) - 1L;
        this.field_24079 = (char)(64 / elementBits);
        int n = 3 * (this.field_24079 - 1);
        this.field_24080 = field_24078[n + 0];
        this.field_24081 = field_24078[n + 1];
        this.field_24082 = field_24078[n + 2];
        _snowman = (size + this.field_24079 - 1) / this.field_24079;
        if (storage != null) {
            if (storage.length != _snowman) {
                throw Util.throwOrPause(new RuntimeException("Invalid length given for storage, got: " + storage.length + " but expected: " + _snowman));
            }
            this.storage = storage;
        } else {
            this.storage = new long[_snowman];
        }
    }

    private int method_27284(int n) {
        long l = Integer.toUnsignedLong(this.field_24080);
        _snowman = Integer.toUnsignedLong(this.field_24081);
        return (int)((long)n * l + _snowman >> 32 >> this.field_24082);
    }

    public int setAndGetOldValue(int index, int value) {
        Validate.inclusiveBetween((long)0L, (long)(this.size - 1), (long)index);
        Validate.inclusiveBetween((long)0L, (long)this.maxValue, (long)value);
        int n = this.method_27284(index);
        long _snowman2 = this.storage[n];
        _snowman = (index - n * this.field_24079) * this.elementBits;
        _snowman = (int)(_snowman2 >> _snowman & this.maxValue);
        this.storage[n] = _snowman2 & (this.maxValue << _snowman ^ 0xFFFFFFFFFFFFFFFFL) | ((long)value & this.maxValue) << _snowman;
        return _snowman;
    }

    public void set(int index, int value) {
        Validate.inclusiveBetween((long)0L, (long)(this.size - 1), (long)index);
        Validate.inclusiveBetween((long)0L, (long)this.maxValue, (long)value);
        int n = this.method_27284(index);
        long _snowman2 = this.storage[n];
        _snowman = (index - n * this.field_24079) * this.elementBits;
        this.storage[n] = _snowman2 & (this.maxValue << _snowman ^ 0xFFFFFFFFFFFFFFFFL) | ((long)value & this.maxValue) << _snowman;
    }

    public int get(int index) {
        Validate.inclusiveBetween((long)0L, (long)(this.size - 1), (long)index);
        int n = this.method_27284(index);
        long _snowman2 = this.storage[n];
        _snowman = (index - n * this.field_24079) * this.elementBits;
        return (int)(_snowman2 >> _snowman & this.maxValue);
    }

    public long[] getStorage() {
        return this.storage;
    }

    public int getSize() {
        return this.size;
    }

    public void forEach(IntConsumer consumer) {
        int n = 0;
        for (long l : this.storage) {
            for (int i = 0; i < this.field_24079; ++i) {
                consumer.accept((int)(l & this.maxValue));
                l >>= this.elementBits;
                if (++n < this.size) continue;
                return;
            }
        }
    }
}

