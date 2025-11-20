/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 */
package net.minecraft.command.argument;

import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;
import net.minecraft.command.argument.CoordinateArgument;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class LookingPosArgument
implements PosArgument {
    private final double x;
    private final double y;
    private final double z;

    public LookingPosArgument(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public Vec3d toAbsolutePos(ServerCommandSource source) {
        Vec2f vec2f = source.getRotation();
        Vec3d _snowman2 = source.getEntityAnchor().positionAt(source);
        float _snowman3 = MathHelper.cos((vec2f.y + 90.0f) * ((float)Math.PI / 180));
        float _snowman4 = MathHelper.sin((vec2f.y + 90.0f) * ((float)Math.PI / 180));
        float _snowman5 = MathHelper.cos(-vec2f.x * ((float)Math.PI / 180));
        float _snowman6 = MathHelper.sin(-vec2f.x * ((float)Math.PI / 180));
        float _snowman7 = MathHelper.cos((-vec2f.x + 90.0f) * ((float)Math.PI / 180));
        float _snowman8 = MathHelper.sin((-vec2f.x + 90.0f) * ((float)Math.PI / 180));
        Vec3d _snowman9 = new Vec3d(_snowman3 * _snowman5, _snowman6, _snowman4 * _snowman5);
        Vec3d _snowman10 = new Vec3d(_snowman3 * _snowman7, _snowman8, _snowman4 * _snowman7);
        Vec3d _snowman11 = _snowman9.crossProduct(_snowman10).multiply(-1.0);
        double _snowman12 = _snowman9.x * this.z + _snowman10.x * this.y + _snowman11.x * this.x;
        double _snowman13 = _snowman9.y * this.z + _snowman10.y * this.y + _snowman11.y * this.x;
        double _snowman14 = _snowman9.z * this.z + _snowman10.z * this.y + _snowman11.z * this.x;
        return new Vec3d(_snowman2.x + _snowman12, _snowman2.y + _snowman13, _snowman2.z + _snowman14);
    }

    @Override
    public Vec2f toAbsoluteRotation(ServerCommandSource source) {
        return Vec2f.ZERO;
    }

    @Override
    public boolean isXRelative() {
        return true;
    }

    @Override
    public boolean isYRelative() {
        return true;
    }

    @Override
    public boolean isZRelative() {
        return true;
    }

    public static LookingPosArgument parse(StringReader reader) throws CommandSyntaxException {
        int n = reader.getCursor();
        double _snowman2 = LookingPosArgument.readCoordinate(reader, n);
        if (!reader.canRead() || reader.peek() != ' ') {
            reader.setCursor(n);
            throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext((ImmutableStringReader)reader);
        }
        reader.skip();
        double _snowman3 = LookingPosArgument.readCoordinate(reader, n);
        if (!reader.canRead() || reader.peek() != ' ') {
            reader.setCursor(n);
            throw Vec3ArgumentType.INCOMPLETE_EXCEPTION.createWithContext((ImmutableStringReader)reader);
        }
        reader.skip();
        double _snowman4 = LookingPosArgument.readCoordinate(reader, n);
        return new LookingPosArgument(_snowman2, _snowman3, _snowman4);
    }

    private static double readCoordinate(StringReader reader, int startingCursorPos) throws CommandSyntaxException {
        if (!reader.canRead()) {
            throw CoordinateArgument.MISSING_COORDINATE.createWithContext((ImmutableStringReader)reader);
        }
        if (reader.peek() != '^') {
            reader.setCursor(startingCursorPos);
            throw Vec3ArgumentType.MIXED_COORDINATE_EXCEPTION.createWithContext((ImmutableStringReader)reader);
        }
        reader.skip();
        return reader.canRead() && reader.peek() != ' ' ? reader.readDouble() : 0.0;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LookingPosArgument)) {
            return false;
        }
        LookingPosArgument lookingPosArgument = (LookingPosArgument)o;
        return this.x == lookingPosArgument.x && this.y == lookingPosArgument.y && this.z == lookingPosArgument.z;
    }

    public int hashCode() {
        return Objects.hash(this.x, this.y, this.z);
    }
}

