/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import net.minecraft.nbt.AbstractListTag;
import net.minecraft.nbt.AbstractNumberTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.PositionTracker;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagReader;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ArrayUtils;

public class IntArrayTag
extends AbstractListTag<IntTag> {
    public static final TagReader<IntArrayTag> READER = new TagReader<IntArrayTag>(){

        @Override
        public IntArrayTag read(DataInput dataInput, int n, PositionTracker positionTracker) throws IOException {
            positionTracker.add(192L);
            int n2 = dataInput.readInt();
            positionTracker.add(32L * (long)n2);
            int[] _snowman2 = new int[n2];
            for (_snowman = 0; _snowman < n2; ++_snowman) {
                _snowman2[_snowman] = dataInput.readInt();
            }
            return new IntArrayTag(_snowman2);
        }

        @Override
        public String getCrashReportName() {
            return "INT[]";
        }

        @Override
        public String getCommandFeedbackName() {
            return "TAG_Int_Array";
        }

        @Override
        public /* synthetic */ Tag read(DataInput input, int depth, PositionTracker tracker) throws IOException {
            return this.read(input, depth, tracker);
        }
    };
    private int[] value;

    public IntArrayTag(int[] value) {
        this.value = value;
    }

    public IntArrayTag(List<Integer> value) {
        this(IntArrayTag.toArray(value));
    }

    private static int[] toArray(List<Integer> list) {
        int[] nArray = new int[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            Integer n = list.get(i);
            nArray[i] = n == null ? 0 : n;
        }
        return nArray;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(this.value.length);
        for (int n : this.value) {
            output.writeInt(n);
        }
    }

    @Override
    public byte getType() {
        return 11;
    }

    public TagReader<IntArrayTag> getReader() {
        return READER;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[I;");
        for (int i = 0; i < this.value.length; ++i) {
            if (i != 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.value[i]);
        }
        return stringBuilder.append(']').toString();
    }

    @Override
    public IntArrayTag copy() {
        int[] nArray = new int[this.value.length];
        System.arraycopy(this.value, 0, nArray, 0, this.value.length);
        return new IntArrayTag(nArray);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof IntArrayTag && Arrays.equals(this.value, ((IntArrayTag)o).value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    public int[] getIntArray() {
        return this.value;
    }

    @Override
    public Text toText(String indent, int depth) {
        MutableText mutableText;
        MutableText mutableText2 = new LiteralText("I").formatted(RED);
        mutableText = new LiteralText("[").append(mutableText2).append(";");
        for (int i = 0; i < this.value.length; ++i) {
            mutableText.append(" ").append(new LiteralText(String.valueOf(this.value[i])).formatted(GOLD));
            if (i == this.value.length - 1) continue;
            mutableText.append(",");
        }
        mutableText.append("]");
        return mutableText;
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public IntTag get(int n) {
        return IntTag.of(this.value[n]);
    }

    @Override
    public IntTag set(int n, IntTag intTag) {
        int n2 = this.value[n];
        this.value[n] = intTag.getInt();
        return IntTag.of(n2);
    }

    @Override
    public void add(int n, IntTag intTag) {
        this.value = ArrayUtils.add((int[])this.value, (int)n, (int)intTag.getInt());
    }

    @Override
    public boolean setTag(int index, Tag tag) {
        if (tag instanceof AbstractNumberTag) {
            this.value[index] = ((AbstractNumberTag)tag).getInt();
            return true;
        }
        return false;
    }

    @Override
    public boolean addTag(int index, Tag tag) {
        if (tag instanceof AbstractNumberTag) {
            this.value = ArrayUtils.add((int[])this.value, (int)index, (int)((AbstractNumberTag)tag).getInt());
            return true;
        }
        return false;
    }

    @Override
    public IntTag remove(int n) {
        _snowman = this.value[n];
        this.value = ArrayUtils.remove((int[])this.value, (int)n);
        return IntTag.of(_snowman);
    }

    @Override
    public byte getElementType() {
        return 3;
    }

    @Override
    public void clear() {
        this.value = new int[0];
    }

    @Override
    public /* synthetic */ Tag remove(int n) {
        return this.remove(n);
    }

    @Override
    public /* synthetic */ void add(int n, Tag tag) {
        this.add(n, (IntTag)tag);
    }

    @Override
    public /* synthetic */ Tag set(int n, Tag tag) {
        return this.set(n, (IntTag)tag);
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
        this.add(n, (IntTag)object);
    }

    @Override
    public /* synthetic */ Object set(int n, Object object) {
        return this.set(n, (IntTag)object);
    }

    @Override
    public /* synthetic */ Object get(int n) {
        return this.get(n);
    }
}

