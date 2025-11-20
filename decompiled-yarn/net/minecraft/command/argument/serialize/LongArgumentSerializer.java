package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.network.PacketByteBuf;

public class LongArgumentSerializer implements ArgumentSerializer<LongArgumentType> {
   public LongArgumentSerializer() {
   }

   public void toPacket(LongArgumentType _snowman, PacketByteBuf _snowman) {
      boolean _snowmanxx = _snowman.getMinimum() != Long.MIN_VALUE;
      boolean _snowmanxxx = _snowman.getMaximum() != Long.MAX_VALUE;
      _snowman.writeByte(BrigadierArgumentTypes.createFlag(_snowmanxx, _snowmanxxx));
      if (_snowmanxx) {
         _snowman.writeLong(_snowman.getMinimum());
      }

      if (_snowmanxxx) {
         _snowman.writeLong(_snowman.getMaximum());
      }
   }

   public LongArgumentType fromPacket(PacketByteBuf _snowman) {
      byte _snowmanx = _snowman.readByte();
      long _snowmanxx = BrigadierArgumentTypes.hasMin(_snowmanx) ? _snowman.readLong() : Long.MIN_VALUE;
      long _snowmanxxx = BrigadierArgumentTypes.hasMax(_snowmanx) ? _snowman.readLong() : Long.MAX_VALUE;
      return LongArgumentType.longArg(_snowmanxx, _snowmanxxx);
   }

   public void toJson(LongArgumentType _snowman, JsonObject _snowman) {
      if (_snowman.getMinimum() != Long.MIN_VALUE) {
         _snowman.addProperty("min", _snowman.getMinimum());
      }

      if (_snowman.getMaximum() != Long.MAX_VALUE) {
         _snowman.addProperty("max", _snowman.getMaximum());
      }
   }
}
