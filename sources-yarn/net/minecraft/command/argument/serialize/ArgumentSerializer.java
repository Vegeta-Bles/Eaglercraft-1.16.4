package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.network.PacketByteBuf;

public interface ArgumentSerializer<T extends ArgumentType<?>> {
   void toPacket(T argumentType, PacketByteBuf arg);

   T fromPacket(PacketByteBuf arg);

   void toJson(T argumentType, JsonObject jsonObject);
}
