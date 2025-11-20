package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Supplier;
import net.minecraft.network.PacketByteBuf;

public class ConstantArgumentSerializer<T extends ArgumentType<?>> implements ArgumentSerializer<T> {
   private final Supplier<T> supplier;

   public ConstantArgumentSerializer(Supplier<T> _snowman) {
      this.supplier = _snowman;
   }

   @Override
   public void toPacket(T _snowman, PacketByteBuf _snowman) {
   }

   @Override
   public T fromPacket(PacketByteBuf _snowman) {
      return this.supplier.get();
   }

   @Override
   public void toJson(T _snowman, JsonObject _snowman) {
   }
}
