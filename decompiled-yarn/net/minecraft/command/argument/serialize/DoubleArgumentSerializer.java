package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.network.PacketByteBuf;

public class DoubleArgumentSerializer implements ArgumentSerializer<DoubleArgumentType> {
   public DoubleArgumentSerializer() {
   }

   public void toPacket(DoubleArgumentType _snowman, PacketByteBuf _snowman) {
      boolean _snowmanxx = _snowman.getMinimum() != -Double.MAX_VALUE;
      boolean _snowmanxxx = _snowman.getMaximum() != Double.MAX_VALUE;
      _snowman.writeByte(BrigadierArgumentTypes.createFlag(_snowmanxx, _snowmanxxx));
      if (_snowmanxx) {
         _snowman.writeDouble(_snowman.getMinimum());
      }

      if (_snowmanxxx) {
         _snowman.writeDouble(_snowman.getMaximum());
      }
   }

   public DoubleArgumentType fromPacket(PacketByteBuf _snowman) {
      byte _snowmanx = _snowman.readByte();
      double _snowmanxx = BrigadierArgumentTypes.hasMin(_snowmanx) ? _snowman.readDouble() : -Double.MAX_VALUE;
      double _snowmanxxx = BrigadierArgumentTypes.hasMax(_snowmanx) ? _snowman.readDouble() : Double.MAX_VALUE;
      return DoubleArgumentType.doubleArg(_snowmanxx, _snowmanxxx);
   }

   public void toJson(DoubleArgumentType _snowman, JsonObject _snowman) {
      if (_snowman.getMinimum() != -Double.MAX_VALUE) {
         _snowman.addProperty("min", _snowman.getMinimum());
      }

      if (_snowman.getMaximum() != Double.MAX_VALUE) {
         _snowman.addProperty("max", _snowman.getMaximum());
      }
   }
}
