import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Map;

public class czc {
   private static final Map<vk, Class<? extends czb>> a = Maps.newHashMap();

   public static czb a(JsonElement var0, JsonDeserializationContext var1) throws JsonParseException {
      if (_snowman.isJsonPrimitive()) {
         return (czb)_snowman.deserialize(_snowman, cyr.class);
      } else {
         JsonObject _snowman = _snowman.getAsJsonObject();
         String _snowmanx = afd.a(_snowman, "type", czb.b.toString());
         Class<? extends czb> _snowmanxx = a.get(new vk(_snowmanx));
         if (_snowmanxx == null) {
            throw new JsonParseException("Unknown generator: " + _snowmanx);
         } else {
            return (czb)_snowman.deserialize(_snowman, _snowmanxx);
         }
      }
   }

   public static JsonElement a(czb var0, JsonSerializationContext var1) {
      JsonElement _snowman = _snowman.serialize(_snowman);
      if (_snowman.isJsonObject()) {
         _snowman.getAsJsonObject().addProperty("type", _snowman.a().toString());
      }

      return _snowman;
   }

   static {
      a.put(czb.b, czd.class);
      a.put(czb.c, cyp.class);
      a.put(czb.a, cyr.class);
   }
}
