import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class ebj {
   private final vk a;
   private final Map<vk, Float> b;

   public ebj(vk var1, Map<vk, Float> var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public vk a() {
      return this.a;
   }

   boolean a(bmb var1, @Nullable dwt var2, @Nullable aqm var3) {
      blx _snowman = _snowman.b();

      for (Entry<vk, Float> _snowmanx : this.b.entrySet()) {
         ejn _snowmanxx = ejm.a(_snowman, _snowmanx.getKey());
         if (_snowmanxx == null || _snowmanxx.call(_snowman, _snowman, _snowman) < _snowmanx.getValue()) {
            return false;
         }
      }

      return true;
   }

   public static class a implements JsonDeserializer<ebj> {
      protected a() {
      }

      public ebj a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         vk _snowmanx = new vk(afd.h(_snowman, "model"));
         Map<vk, Float> _snowmanxx = this.a(_snowman);
         return new ebj(_snowmanx, _snowmanxx);
      }

      protected Map<vk, Float> a(JsonObject var1) {
         Map<vk, Float> _snowman = Maps.newLinkedHashMap();
         JsonObject _snowmanx = afd.t(_snowman, "predicate");

         for (Entry<String, JsonElement> _snowmanxx : _snowmanx.entrySet()) {
            _snowman.put(new vk(_snowmanxx.getKey()), afd.e(_snowmanxx.getValue(), _snowmanxx.getKey()));
         }

         return _snowman;
      }
   }
}
