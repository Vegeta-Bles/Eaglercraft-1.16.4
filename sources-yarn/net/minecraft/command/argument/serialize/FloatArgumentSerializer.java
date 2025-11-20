package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.network.PacketByteBuf;

public class FloatArgumentSerializer implements ArgumentSerializer<FloatArgumentType> {
   public FloatArgumentSerializer() {
   }

   public void toPacket(FloatArgumentType floatArgumentType, PacketByteBuf arg) {
      boolean bl = floatArgumentType.getMinimum() != -Float.MAX_VALUE;
      boolean bl2 = floatArgumentType.getMaximum() != Float.MAX_VALUE;
      arg.writeByte(BrigadierArgumentTypes.createFlag(bl, bl2));
      if (bl) {
         arg.writeFloat(floatArgumentType.getMinimum());
      }

      if (bl2) {
         arg.writeFloat(floatArgumentType.getMaximum());
      }
   }

   public FloatArgumentType fromPacket(PacketByteBuf arg) {
      byte b = arg.readByte();
      float f = BrigadierArgumentTypes.hasMin(b) ? arg.readFloat() : -Float.MAX_VALUE;
      float g = BrigadierArgumentTypes.hasMax(b) ? arg.readFloat() : Float.MAX_VALUE;
      return FloatArgumentType.floatArg(f, g);
   }

   public void toJson(FloatArgumentType floatArgumentType, JsonObject jsonObject) {
      if (floatArgumentType.getMinimum() != -Float.MAX_VALUE) {
         jsonObject.addProperty("min", floatArgumentType.getMinimum());
      }

      if (floatArgumentType.getMaximum() != Float.MAX_VALUE) {
         jsonObject.addProperty("max", floatArgumentType.getMaximum());
      }
   }
}
