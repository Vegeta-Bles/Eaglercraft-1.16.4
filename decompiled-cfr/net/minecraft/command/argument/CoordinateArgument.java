/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.command.argument;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.text.TranslatableText;

public class CoordinateArgument {
    public static final SimpleCommandExceptionType MISSING_COORDINATE = new SimpleCommandExceptionType((Message)new TranslatableText("argument.pos.missing.double"));
    public static final SimpleCommandExceptionType MISSING_BLOCK_POSITION = new SimpleCommandExceptionType((Message)new TranslatableText("argument.pos.missing.int"));
    private final boolean relative;
    private final double value;

    public CoordinateArgument(boolean relative, double value) {
        this.relative = relative;
        this.value = value;
    }

    public double toAbsoluteCoordinate(double offset) {
        if (this.relative) {
            return this.value + offset;
        }
        return this.value;
    }

    public static CoordinateArgument parse(StringReader reader, boolean centerIntegers) throws CommandSyntaxException {
        if (reader.canRead() && reader.peek() == '^') {
            throw Vec3ArgumentType.MIXED_COORDINATE_EXCEPTION.createWithContext((ImmutableStringReader)reader);
        }
        if (!reader.canRead()) {
            throw MISSING_COORDINATE.createWithContext((ImmutableStringReader)reader);
        }
        boolean bl = CoordinateArgument.isRelative(reader);
        int _snowman2 = reader.getCursor();
        double _snowman3 = reader.canRead() && reader.peek() != ' ' ? reader.readDouble() : 0.0;
        String _snowman4 = reader.getString().substring(_snowman2, reader.getCursor());
        if (bl && _snowman4.isEmpty()) {
            return new CoordinateArgument(true, 0.0);
        }
        if (!_snowman4.contains(".") && !bl && centerIntegers) {
            _snowman3 += 0.5;
        }
        return new CoordinateArgument(bl, _snowman3);
    }

    public static CoordinateArgument parse(StringReader reader) throws CommandSyntaxException {
        if (reader.canRead() && reader.peek() == '^') {
            throw Vec3ArgumentType.MIXED_COORDINATE_EXCEPTION.createWithContext((ImmutableStringReader)reader);
        }
        if (!reader.canRead()) {
            throw MISSING_BLOCK_POSITION.createWithContext((ImmutableStringReader)reader);
        }
        boolean bl = CoordinateArgument.isRelative(reader);
        double _snowman2 = reader.canRead() && reader.peek() != ' ' ? (bl ? reader.readDouble() : (double)reader.readInt()) : 0.0;
        return new CoordinateArgument(bl, _snowman2);
    }

    public static boolean isRelative(StringReader reader) {
        boolean bl;
        if (reader.peek() == '~') {
            bl = true;
            reader.skip();
        } else {
            bl = false;
        }
        return bl;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CoordinateArgument)) {
            return false;
        }
        CoordinateArgument coordinateArgument = (CoordinateArgument)o;
        if (this.relative != coordinateArgument.relative) {
            return false;
        }
        return Double.compare(coordinateArgument.value, this.value) == 0;
    }

    public int hashCode() {
        int n = this.relative ? 1 : 0;
        long _snowman2 = Double.doubleToLongBits(this.value);
        n = 31 * n + (int)(_snowman2 ^ _snowman2 >>> 32);
        return n;
    }

    public boolean isRelative() {
        return this.relative;
    }
}

