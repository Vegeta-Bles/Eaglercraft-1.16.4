/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.LongArgumentType
 */
package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.network.PacketByteBuf;

public class LongArgumentSerializer
implements ArgumentSerializer<LongArgumentType> {
    @Override
    public void toPacket(LongArgumentType longArgumentType, PacketByteBuf packetByteBuf) {
        boolean bl = longArgumentType.getMinimum() != Long.MIN_VALUE;
        _snowman = longArgumentType.getMaximum() != Long.MAX_VALUE;
        packetByteBuf.writeByte(BrigadierArgumentTypes.createFlag(bl, _snowman));
        if (bl) {
            packetByteBuf.writeLong(longArgumentType.getMinimum());
        }
        if (_snowman) {
            packetByteBuf.writeLong(longArgumentType.getMaximum());
        }
    }

    @Override
    public LongArgumentType fromPacket(PacketByteBuf packetByteBuf) {
        byte by = packetByteBuf.readByte();
        long _snowman2 = BrigadierArgumentTypes.hasMin(by) ? packetByteBuf.readLong() : Long.MIN_VALUE;
        long _snowman3 = BrigadierArgumentTypes.hasMax(by) ? packetByteBuf.readLong() : Long.MAX_VALUE;
        return LongArgumentType.longArg((long)_snowman2, (long)_snowman3);
    }

    @Override
    public void toJson(LongArgumentType longArgumentType, JsonObject jsonObject) {
        if (longArgumentType.getMinimum() != Long.MIN_VALUE) {
            jsonObject.addProperty("min", (Number)longArgumentType.getMinimum());
        }
        if (longArgumentType.getMaximum() != Long.MAX_VALUE) {
            jsonObject.addProperty("max", (Number)longArgumentType.getMaximum());
        }
    }

    @Override
    public /* synthetic */ ArgumentType fromPacket(PacketByteBuf packetByteBuf) {
        return this.fromPacket(packetByteBuf);
    }
}

