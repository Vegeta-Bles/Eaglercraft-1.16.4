package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.network.PacketByteBuf;

public interface ArgumentSerializer<T extends ArgumentType<?>> {
   void toPacket(T var1, PacketByteBuf var2);

   T fromPacket(PacketByteBuf var1);

   void toJson(T var1, JsonObject var2);
}
