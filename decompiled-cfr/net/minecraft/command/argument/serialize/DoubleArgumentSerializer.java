/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.DoubleArgumentType
 */
package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.network.PacketByteBuf;

public class DoubleArgumentSerializer
implements ArgumentSerializer<DoubleArgumentType> {
    @Override
    public void toPacket(DoubleArgumentType doubleArgumentType, PacketByteBuf packetByteBuf) {
        boolean bl = doubleArgumentType.getMinimum() != -1.7976931348623157E308;
        _snowman = doubleArgumentType.getMaximum() != Double.MAX_VALUE;
        packetByteBuf.writeByte(BrigadierArgumentTypes.createFlag(bl, _snowman));
        if (bl) {
            packetByteBuf.writeDouble(doubleArgumentType.getMinimum());
        }
        if (_snowman) {
            packetByteBuf.writeDouble(doubleArgumentType.getMaximum());
        }
    }

    @Override
    public DoubleArgumentType fromPacket(PacketByteBuf packetByteBuf) {
        byte by = packetByteBuf.readByte();
        double _snowman2 = BrigadierArgumentTypes.hasMin(by) ? packetByteBuf.readDouble() : -1.7976931348623157E308;
        double _snowman3 = BrigadierArgumentTypes.hasMax(by) ? packetByteBuf.readDouble() : Double.MAX_VALUE;
        return DoubleArgumentType.doubleArg((double)_snowman2, (double)_snowman3);
    }

    @Override
    public void toJson(DoubleArgumentType doubleArgumentType, JsonObject jsonObject) {
        if (doubleArgumentType.getMinimum() != -1.7976931348623157E308) {
            jsonObject.addProperty("min", (Number)doubleArgumentType.getMinimum());
        }
        if (doubleArgumentType.getMaximum() != Double.MAX_VALUE) {
            jsonObject.addProperty("max", (Number)doubleArgumentType.getMaximum());
        }
    }

    @Override
    public /* synthetic */ ArgumentType fromPacket(PacketByteBuf packetByteBuf) {
        return this.fromPacket(packetByteBuf);
    }
}

