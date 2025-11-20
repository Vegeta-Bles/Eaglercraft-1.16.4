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
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.PositionTracker;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagReader;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.apache.commons.lang3.ArrayUtils;

public class ByteArrayTag
extends AbstractListTag<ByteTag> {
    public static final TagReader<ByteArrayTag> READER = new TagReader<ByteArrayTag>(){

        @Override
        public ByteArrayTag read(DataInput dataInput, int n, PositionTracker positionTracker) throws IOException {
            positionTracker.add(192L);
            int n2 = dataInput.readInt();
            positionTracker.add(8L * (long)n2);
            byte[] _snowman2 = new byte[n2];
            dataInput.readFully(_snowman2);
            return new ByteArrayTag(_snowman2);
        }

        @Override
        public String getCrashReportName() {
            return "BYTE[]";
        }

        @Override
        public String getCommandFeedbackName() {
            return "TAG_Byte_Array";
        }

        @Override
        public /* synthetic */ Tag read(DataInput input, int depth, PositionTracker tracker) throws IOException {
            return this.read(input, depth, tracker);
        }
    };
    private byte[] value;

    public ByteArrayTag(byte[] value) {
        this.value = value;
    }

    public ByteArrayTag(List<Byte> value) {
        this(ByteArrayTag.toArray(value));
    }

    private static byte[] toArray(List<Byte> list) {
        byte[] byArray = new byte[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            Byte by = list.get(i);
            byArray[i] = by == null ? (byte)0 : by;
        }
        return byArray;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeInt(this.value.length);
        output.write(this.value);
    }

    @Override
    public byte getType() {
        return 7;
    }

    public TagReader<ByteArrayTag> getReader() {
        return READER;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[B;");
        for (int i = 0; i < this.value.length; ++i) {
            if (i != 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this.value[i]).append('B');
        }
        return stringBuilder.append(']').toString();
    }

    @Override
    public Tag copy() {
        byte[] byArray = new byte[this.value.length];
        System.arraycopy(this.value, 0, byArray, 0, this.value.length);
        return new ByteArrayTag(byArray);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof ByteArrayTag && Arrays.equals(this.value, ((ByteArrayTag)o).value);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    @Override
    public Text toText(String indent, int depth) {
        MutableText mutableText;
        MutableText mutableText2 = new LiteralText("B").formatted(RED);
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

    public byte[] getByteArray() {
        return this.value;
    }

    @Override
    public int size() {
        return this.value.length;
    }

    @Override
    public ByteTag get(int n) {
        return ByteTag.of(this.value[n]);
    }

    @Override
    public ByteTag set(int n, ByteTag byteTag) {
        byte by = this.value[n];
        this.value[n] = byteTag.getByte();
        return ByteTag.of(by);
    }

    public void method_10531(int n, ByteTag byteTag) {
        this.value = ArrayUtils.add((byte[])this.value, (int)n, (byte)byteTag.getByte());
    }

    @Override
    public boolean setTag(int index, Tag tag) {
        if (tag instanceof AbstractNumberTag) {
            this.value[index] = ((AbstractNumberTag)tag).getByte();
            return true;
        }
        return false;
    }

    @Override
    public boolean addTag(int index, Tag tag) {
        if (tag instanceof AbstractNumberTag) {
            this.value = ArrayUtils.add((byte[])this.value, (int)index, (byte)((AbstractNumberTag)tag).getByte());
            return true;
        }
        return false;
    }

    public ByteTag method_10536(int n) {
        byte by = this.value[n];
        this.value = ArrayUtils.remove((byte[])this.value, (int)n);
        return ByteTag.of(by);
    }

    @Override
    public byte getElementType() {
        return 1;
    }

    @Override
    public void clear() {
        this.value = new byte[0];
    }

    @Override
    public /* synthetic */ Tag remove(int n) {
        return this.method_10536(n);
    }

    @Override
    public /* synthetic */ void add(int n, Tag tag) {
        this.method_10531(n, (ByteTag)tag);
    }

    @Override
    public /* synthetic */ Tag set(int n, Tag tag) {
        return this.set(n, (ByteTag)tag);
    }

    @Override
    public /* synthetic */ Object remove(int n) {
        return this.method_10536(n);
    }

    @Override
    public /* synthetic */ void add(int n, Object object) {
        this.method_10531(n, (ByteTag)object);
    }

    @Override
    public /* synthetic */ Object set(int n, Object object) {
        return this.set(n, (ByteTag)object);
    }

    @Override
    public /* synthetic */ Object get(int n) {
        return this.get(n);
    }
}

