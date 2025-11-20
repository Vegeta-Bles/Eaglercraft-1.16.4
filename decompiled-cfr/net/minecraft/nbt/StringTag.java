/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;
import net.minecraft.nbt.PositionTracker;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagReader;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class StringTag
implements Tag {
    public static final TagReader<StringTag> READER = new TagReader<StringTag>(){

        @Override
        public StringTag read(DataInput dataInput, int n, PositionTracker positionTracker) throws IOException {
            positionTracker.add(288L);
            String string = dataInput.readUTF();
            positionTracker.add(16 * string.length());
            return StringTag.of(string);
        }

        @Override
        public String getCrashReportName() {
            return "STRING";
        }

        @Override
        public String getCommandFeedbackName() {
            return "TAG_String";
        }

        @Override
        public boolean isImmutable() {
            return true;
        }

        @Override
        public /* synthetic */ Tag read(DataInput input, int depth, PositionTracker tracker) throws IOException {
            return this.read(input, depth, tracker);
        }
    };
    private static final StringTag EMPTY = new StringTag("");
    private final String value;

    private StringTag(String value) {
        Objects.requireNonNull(value, "Null string not allowed");
        this.value = value;
    }

    public static StringTag of(String value) {
        if (value.isEmpty()) {
            return EMPTY;
        }
        return new StringTag(value);
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeUTF(this.value);
    }

    @Override
    public byte getType() {
        return 8;
    }

    public TagReader<StringTag> getReader() {
        return READER;
    }

    @Override
    public String toString() {
        return StringTag.escape(this.value);
    }

    @Override
    public StringTag copy() {
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        return o instanceof StringTag && Objects.equals(this.value, ((StringTag)o).value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public String asString() {
        return this.value;
    }

    @Override
    public Text toText(String indent, int depth) {
        String string = StringTag.escape(this.value);
        _snowman = string.substring(0, 1);
        MutableText _snowman2 = new LiteralText(string.substring(1, string.length() - 1)).formatted(GREEN);
        return new LiteralText(_snowman).append(_snowman2).append(_snowman);
    }

    public static String escape(String value) {
        StringBuilder stringBuilder = new StringBuilder(" ");
        int _snowman2 = 0;
        for (int i = 0; i < value.length(); ++i) {
            _snowman = value.charAt(i);
            if (_snowman == 92) {
                stringBuilder.append('\\');
            } else if (_snowman == 34 || _snowman == 39) {
                if (_snowman2 == 0) {
                    int n = _snowman2 = _snowman == 34 ? 39 : 34;
                }
                if (_snowman2 == _snowman) {
                    stringBuilder.append('\\');
                }
            }
            stringBuilder.append((char)_snowman);
        }
        if (_snowman2 == 0) {
            _snowman2 = 34;
        }
        stringBuilder.setCharAt(0, (char)_snowman2);
        stringBuilder.append((char)_snowman2);
        return stringBuilder.toString();
    }

    @Override
    public /* synthetic */ Tag copy() {
        return this.copy();
    }
}

