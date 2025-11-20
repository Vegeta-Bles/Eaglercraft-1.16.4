/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.arguments.FloatArgumentType
 */
package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.command.argument.serialize.ArgumentSerializer;
import net.minecraft.network.PacketByteBuf;

public class FloatArgumentSerializer
implements ArgumentSerializer<FloatArgumentType> {
    @Override
    public void toPacket(FloatArgumentType floatArgumentType, PacketByteBuf packetByteBuf) {
        boolean bl = floatArgumentType.getMinimum() != -3.4028235E38f;
        _snowman = floatArgumentType.getMaximum() != Float.MAX_VALUE;
        packetByteBuf.writeByte(BrigadierArgumentTypes.createFlag(bl, _snowman));
        if (bl) {
            packetByteBuf.writeFloat(floatArgumentType.getMinimum());
        }
        if (_snowman) {
            packetByteBuf.writeFloat(floatArgumentType.getMaximum());
        }
    }

    @Override
    public FloatArgumentType fromPacket(PacketByteBuf packetByteBuf) {
        byte by = packetByteBuf.readByte();
        float _snowman2 = BrigadierArgumentTypes.hasMin(by) ? packetByteBuf.readFloat() : -3.4028235E38f;
        float _snowman3 = BrigadierArgumentTypes.hasMax(by) ? packetByteBuf.readFloat() : Float.MAX_VALUE;
        return FloatArgumentType.floatArg((float)_snowman2, (float)_snowman3);
    }

    @Override
    public void toJson(FloatArgumentType floatArgumentType, JsonObject jsonObject) {
        if (floatArgumentType.getMinimum() != -3.4028235E38f) {
            jsonObject.addProperty("min", (Number)Float.valueOf(floatArgumentType.getMinimum()));
        }
        if (floatArgumentType.getMaximum() != Float.MAX_VALUE) {
            jsonObject.addProperty("max", (Number)Float.valueOf(floatArgumentType.getMaximum()));
        }
    }

    @Override
    public /* synthetic */ ArgumentType fromPacket(PacketByteBuf packetByteBuf) {
        return this.fromPacket(packetByteBuf);
    }
}

