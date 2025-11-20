package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.network.PacketByteBuf;

public class FloatArgumentSerializer implements ArgumentSerializer<FloatArgumentType> {
   public FloatArgumentSerializer() {
   }

   public void toPacket(FloatArgumentType _snowman, PacketByteBuf _snowman) {
      boolean _snowmanxx = _snowman.getMinimum() != -Float.MAX_VALUE;
      boolean _snowmanxxx = _snowman.getMaximum() != Float.MAX_VALUE;
      _snowman.writeByte(BrigadierArgumentTypes.createFlag(_snowmanxx, _snowmanxxx));
      if (_snowmanxx) {
         _snowman.writeFloat(_snowman.getMinimum());
      }

      if (_snowmanxxx) {
         _snowman.writeFloat(_snowman.getMaximum());
      }
   }

   public FloatArgumentType fromPacket(PacketByteBuf _snowman) {
      byte _snowmanx = _snowman.readByte();
      float _snowmanxx = BrigadierArgumentTypes.hasMin(_snowmanx) ? _snowman.readFloat() : -Float.MAX_VALUE;
      float _snowmanxxx = BrigadierArgumentTypes.hasMax(_snowmanx) ? _snowman.readFloat() : Float.MAX_VALUE;
      return FloatArgumentType.floatArg(_snowmanxx, _snowmanxxx);
   }

   public void toJson(FloatArgumentType _snowman, JsonObject _snowman) {
      if (_snowman.getMinimum() != -Float.MAX_VALUE) {
         _snowman.addProperty("min", _snowman.getMinimum());
      }

      if (_snowman.getMaximum() != Float.MAX_VALUE) {
         _snowman.addProperty("max", _snowman.getMaximum());
      }
   }
}
