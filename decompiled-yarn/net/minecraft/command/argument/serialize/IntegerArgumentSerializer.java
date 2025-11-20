package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.network.PacketByteBuf;

public class IntegerArgumentSerializer implements ArgumentSerializer<IntegerArgumentType> {
   public IntegerArgumentSerializer() {
   }

   public void toPacket(IntegerArgumentType _snowman, PacketByteBuf _snowman) {
      boolean _snowmanxx = _snowman.getMinimum() != Integer.MIN_VALUE;
      boolean _snowmanxxx = _snowman.getMaximum() != Integer.MAX_VALUE;
      _snowman.writeByte(BrigadierArgumentTypes.createFlag(_snowmanxx, _snowmanxxx));
      if (_snowmanxx) {
         _snowman.writeInt(_snowman.getMinimum());
      }

      if (_snowmanxxx) {
         _snowman.writeInt(_snowman.getMaximum());
      }
   }

   public IntegerArgumentType fromPacket(PacketByteBuf _snowman) {
      byte _snowmanx = _snowman.readByte();
      int _snowmanxx = BrigadierArgumentTypes.hasMin(_snowmanx) ? _snowman.readInt() : Integer.MIN_VALUE;
      int _snowmanxxx = BrigadierArgumentTypes.hasMax(_snowmanx) ? _snowman.readInt() : Integer.MAX_VALUE;
      return IntegerArgumentType.integer(_snowmanxx, _snowmanxxx);
   }

   public void toJson(IntegerArgumentType _snowman, JsonObject _snowman) {
      if (_snowman.getMinimum() != Integer.MIN_VALUE) {
         _snowman.addProperty("min", _snowman.getMinimum());
      }

      if (_snowman.getMaximum() != Integer.MAX_VALUE) {
         _snowman.addProperty("max", _snowman.getMaximum());
      }
   }
}
