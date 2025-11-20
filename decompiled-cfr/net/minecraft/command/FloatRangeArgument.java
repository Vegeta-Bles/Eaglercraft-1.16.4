/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  javax.annotation.Nullable
 */
package net.minecraft.command;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.predicate.NumberRange;
import net.minecraft.text.TranslatableText;

public class FloatRangeArgument {
    public static final FloatRangeArgument ANY = new FloatRangeArgument(null, null);
    public static final SimpleCommandExceptionType ONLY_INTS_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("argument.range.ints"));
    private final Float min;
    private final Float max;

    public FloatRangeArgument(@Nullable Float f, @Nullable Float f2) {
        this.min = f;
        this.max = f2;
    }

    @Nullable
    public Float getMin() {
        return this.min;
    }

    @Nullable
    public Float getMax() {
        return this.max;
    }

    public static FloatRangeArgument parse(StringReader reader, boolean allowFloats, Function<Float, Float> transform) throws CommandSyntaxException {
        Float f;
        if (!reader.canRead()) {
            throw NumberRange.EXCEPTION_EMPTY.createWithContext((ImmutableStringReader)reader);
        }
        int n = reader.getCursor();
        Float _snowman2 = FloatRangeArgument.mapFloat(FloatRangeArgument.parseFloat(reader, allowFloats), transform);
        if (reader.canRead(2) && reader.peek() == '.' && reader.peek(1) == '.') {
            reader.skip();
            reader.skip();
            f = FloatRangeArgument.mapFloat(FloatRangeArgument.parseFloat(reader, allowFloats), transform);
            if (_snowman2 == null && f == null) {
                reader.setCursor(n);
                throw NumberRange.EXCEPTION_EMPTY.createWithContext((ImmutableStringReader)reader);
            }
        } else {
            if (!allowFloats && reader.canRead() && reader.peek() == '.') {
                reader.setCursor(n);
                throw ONLY_INTS_EXCEPTION.createWithContext((ImmutableStringReader)reader);
            }
            f = _snowman2;
        }
        if (_snowman2 == null && f == null) {
            reader.setCursor(n);
            throw NumberRange.EXCEPTION_EMPTY.createWithContext((ImmutableStringReader)reader);
        }
        return new FloatRangeArgument(_snowman2, f);
    }

    @Nullable
    private static Float parseFloat(StringReader reader, boolean allowFloats) throws CommandSyntaxException {
        int n = reader.getCursor();
        while (reader.canRead() && FloatRangeArgument.peekDigit(reader, allowFloats)) {
            reader.skip();
        }
        String _snowman2 = reader.getString().substring(n, reader.getCursor());
        if (_snowman2.isEmpty()) {
            return null;
        }
        try {
            return Float.valueOf(Float.parseFloat(_snowman2));
        }
        catch (NumberFormatException _snowman3) {
            if (allowFloats) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext((ImmutableStringReader)reader, (Object)_snowman2);
            }
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext((ImmutableStringReader)reader, (Object)_snowman2);
        }
    }

    private static boolean peekDigit(StringReader reader, boolean allowFloats) {
        char c = reader.peek();
        if (c >= '0' && c <= '9' || c == '-') {
            return true;
        }
        if (allowFloats && c == '.') {
            return !reader.canRead(2) || reader.peek(1) != '.';
        }
        return false;
    }

    @Nullable
    private static Float mapFloat(@Nullable Float f, Function<Float, Float> function) {
        return f == null ? null : function.apply(f);
    }
}

