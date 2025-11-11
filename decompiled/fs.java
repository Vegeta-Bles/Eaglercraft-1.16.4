import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType.StringType;

public class fs implements fj<StringArgumentType> {
   public fs() {
   }

   public void a(StringArgumentType var1, nf var2) {
      _snowman.a(_snowman.getType());
   }

   public StringArgumentType a(nf var1) {
      StringType _snowman = _snowman.a(StringType.class);
      switch (_snowman) {
         case SINGLE_WORD:
            return StringArgumentType.word();
         case QUOTABLE_PHRASE:
            return StringArgumentType.string();
         case GREEDY_PHRASE:
         default:
            return StringArgumentType.greedyString();
      }
   }

   public void a(StringArgumentType var1, JsonObject var2) {
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
