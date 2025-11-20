package net.minecraft.command.argument.serialize;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType.StringType;
import net.minecraft.network.PacketByteBuf;

public class StringArgumentSerializer implements ArgumentSerializer<StringArgumentType> {
   public StringArgumentSerializer() {
   }

   public void toPacket(StringArgumentType _snowman, PacketByteBuf _snowman) {
      _snowman.writeEnumConstant(_snowman.getType());
   }

   public StringArgumentType fromPacket(PacketByteBuf _snowman) {
      StringType _snowmanx = _snowman.readEnumConstant(StringType.class);
      switch (_snowmanx) {
         case SINGLE_WORD:
            return StringArgumentType.word();
         case QUOTABLE_PHRASE:
            return StringArgumentType.string();
         case GREEDY_PHRASE:
         default:
            return StringArgumentType.greedyString();
      }
   }

   public void toJson(StringArgumentType _snowman, JsonObject _snowman) {
      switch (_snowman.getType()) {
         case SINGLE_WORD:
            _snowman.addProperty("type", "word");
            break;
         case QUOTABLE_PHRASE:
            _snowman.addProperty("type", "phrase");
            break;
         case GREEDY_PHRASE:
         default:
            _snowman.addProperty("type", "greedy");
      }
   }
}
