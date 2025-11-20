package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.command.argument.BrigadierArgumentTypes;
import net.minecraft.network.PacketByteBuf;

public class LongArgumentSerializer implements ArgumentSerializer<LongArgumentType> {
   public LongArgumentSerializer() {
   }

   public void toPacket(LongArgumentType longArgumentType, PacketByteBuf arg) {
      boolean bl = longArgumentType.getMinimum() != Long.MIN_VALUE;
      boolean bl2 = longArgumentType.getMaximum() != Long.MAX_VALUE;
      arg.writeByte(BrigadierArgumentTypes.createFlag(bl, bl2));
      if (bl) {
         arg.writeLong(longArgumentType.getMinimum());
      }

      if (bl2) {
         arg.writeLong(longArgumentType.getMaximum());
      }
   }

   public LongArgumentType fromPacket(PacketByteBuf arg) {
      byte b = arg.readByte();
      long l = BrigadierArgumentTypes.hasMin(b) ? arg.readLong() : Long.MIN_VALUE;
      long m = BrigadierArgumentTypes.hasMax(b) ? arg.readLong() : Long.MAX_VALUE;
      return LongArgumentType.longArg(l, m);
   }

   public void toJson(LongArgumentType longArgumentType, JsonObject jsonObject) {
      if (longArgumentType.getMinimum() != Long.MIN_VALUE) {
         jsonObject.addProperty("min", longArgumentType.getMinimum());
      }

      if (longArgumentType.getMaximum() != Long.MAX_VALUE) {
         jsonObject.addProperty("max", longArgumentType.getMaximum());
      }
   }
}
