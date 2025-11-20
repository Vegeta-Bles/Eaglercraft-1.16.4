/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 */
package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.network.PacketByteBuf;

public class IntegerArgumentSerializer
implements ArgumentSerializer<IntegerArgumentType> {
    @Override
    public void toPacket(IntegerArgumentType integerArgumentType, PacketByteBuf packetByteBuf) {
        boolean bl = integerArgumentType.getMinimum() != Integer.MIN_VALUE;
        _snowman = integerArgumentType.getMaximum() != Integer.MAX_VALUE;
        packetByteBuf.writeByte(BrigadierArgumentTypes.createFlag(bl, _snowman));
        if (bl) {
            packetByteBuf.writeInt(integerArgumentType.getMinimum());
        }
        if (_snowman) {
            packetByteBuf.writeInt(integerArgumentType.getMaximum());
        }
    }

    @Override
    public IntegerArgumentType fromPacket(PacketByteBuf packetByteBuf) {
        byte by = packetByteBuf.readByte();
        int _snowman2 = BrigadierArgumentTypes.hasMin(by) ? packetByteBuf.readInt() : Integer.MIN_VALUE;
        int _snowman3 = BrigadierArgumentTypes.hasMax(by) ? packetByteBuf.readInt() : Integer.MAX_VALUE;
        return IntegerArgumentType.integer((int)_snowman2, (int)_snowman3);
    }

    @Override
    public void toJson(IntegerArgumentType integerArgumentType, JsonObject jsonObject) {
        if (integerArgumentType.getMinimum() != Integer.MIN_VALUE) {
            jsonObject.addProperty("min", (Number)integerArgumentType.getMinimum());
        }
        if (integerArgumentType.getMaximum() != Integer.MAX_VALUE) {
            jsonObject.addProperty("max", (Number)integerArgumentType.getMaximum());
        }
    }

    @Override
    public /* synthetic */ ArgumentType fromPacket(PacketByteBuf packetByteBuf) {
        return this.fromPacket(packetByteBuf);
    }
}

