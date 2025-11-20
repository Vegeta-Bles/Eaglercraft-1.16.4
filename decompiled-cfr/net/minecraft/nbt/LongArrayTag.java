/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.LongSet
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.nbt;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.AbstractListTag;
import net.minecraft.nbt.AbstractNumberTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.PositionTracker;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagReader;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ArrayUtils;

public class LongArrayTag
extends AbstractListTag<LongTag> {
    public static final TagReader<LongArrayTag> READER = new TagReader<LongArrayTag>(){

        @Override
        public LongArrayTag read(DataInput dataInput, int n, PositionTracker positionTracker) throws IOException {
            positionTracker.add(192L);
            int n2 = dataInput.readInt();
            positionTracker.add(64L * (long)n2);
            long[] _snowman2 = new long[n2];
            for (_snowman = 0; _snowman < n2; ++_snowman) {
                _snowman2[_snowman] = dataInput.readLong();
            }
            return new LongArrayTag(_snowman2);
        }

        @Override
        public String getCrashReportName() {
            return "LONG[]";
        }

        @Override
        public String getCommandFeedbackName() {
            return "TAG_Long_Array";
        }

        @Override
        public /* synthetic */ Tag read(DataInput input, int depth, PositionTracker tracker) throws IOException {
            return this.read(input, depth, tracker);
        }
    };
    private long[] value;

    public LongArrayTag(long[] value) {
        this.value = value;
    }

    public LongArrayTag(LongSet value) {
        this.value = value.toLongArray();
    }

    public LongArrayTag(List<Long> value) {
        this(LongArrayTag.toArray(value));
    }

    private static long[] toArray(List<Long> list) {
        long[] lArray = new long[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            Long l = list.get(i);
            lArray[i] = l == null ? 0L : l;
        }
        return lArray;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(this.value.length);
        for (long l : this.value) {
            output.writeLong(l);
        }
    }

    @Override
    public byte getType() {
        return 12;
    }

    public TagReader<LongArrayTag> getReader() {
        return READER;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[L;");
        for (int i = 0; i < this.value.length; ++i) {
            if (i != 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.value[i]).append('L');
        }
        return stringBuilder.append(']').toString();
    }

    @Override
    public LongArrayTag copy() {
        long[] lArray = new long[this.value.length];
        System.arraycopy(this.value, 0, lArray, 0, this.value.length);
        return new LongArrayTag(lArray);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof LongArrayTag && Arrays.equals(this.value, ((LongArrayTag)o).value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public Text toText(String indent, int depth) {
        MutableText mutableText;
        MutableText mutableText2 = new LiteralText("L").formatted(RED);
        mutableText = new LiteralText("[").append(mutableText2).append(";");
        for (int i = 0; i < this.value.length; ++i) {
            MutableText mutableText3 = new LiteralText(String.valueOf(this.value[i])).formatted(GOLD);
            mutableText.append(" ").append(mutableText3).append(mutableText2);
            if (i == this.value.length - 1) continue;
            mutableText.append(",");
        }
        mutableText.append("]");
        return mutableText;
    }

    public long[] getLongArray() {
        return this.value;
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public LongTag get(int n) {
        return LongTag.of(this.value[n]);
    }

    public LongTag method_10606(int n, LongTag longTag) {
        long l = this.value[n];
        this.value[n] = longTag.getLong();
        return LongTag.of(l);
    }

    @Override
    public void add(int n, LongTag longTag) {
        this.value = ArrayUtils.add((long[])this.value, (int)n, (long)longTag.getLong());
    }

    @Override
    public boolean setTag(int index, Tag tag) {
        if (tag instanceof AbstractNumberTag) {
            this.value[index] = ((AbstractNumberTag)tag).getLong();
            return true;
        }
        return false;
    }

    @Override
    public boolean addTag(int index, Tag tag) {
        if (tag instanceof AbstractNumberTag) {
            this.value = ArrayUtils.add((long[])this.value, (int)index, (long)((AbstractNumberTag)tag).getLong());
            return true;
        }
        return false;
    }

    @Override
    public LongTag remove(int n) {
        long l = this.value[n];
        this.value = ArrayUtils.remove((long[])this.value, (int)n);
        return LongTag.of(l);
    }

    @Override
    public byte getElementType() {
        return 4;
    }

    @Override
    public void clear() {
        this.value = new long[0];
    }

    @Override
    public /* synthetic */ Tag remove(int n) {
        return this.remove(n);
    }

    @Override
    public /* synthetic */ void add(int n, Tag tag) {
        this.add(n, (LongTag)tag);
    }

    @Override
    public /* synthetic */ Tag set(int n, Tag tag) {
        return this.method_10606(n, (LongTag)tag);
    }

    @Override
    public /* synthetic */ Tag copy() {
        return this.copy();
    }

    @Override
    public /* synthetic */ Object remove(int n) {
        return this.remove(n);
    }

    @Override
    public /* synthetic */ void add(int n, Object object) {
        this.add(n, (LongTag)object);
    }

    @Override
    public /* synthetic */ Object set(int n, Object object) {
        return this.method_10606(n, (LongTag)object);
    }

    @Override
    public /* synthetic */ Object get(int n) {
        return this.get(n);
    }
}

