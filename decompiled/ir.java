import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ir implements Supplier<JsonElement> {
   private final Map<it<?>, it<?>.a> a = Maps.newLinkedHashMap();

   public ir() {
   }

   public <T> ir a(it<T> var1, T var2) {
      it<?>.a _snowman = this.a.put(_snowman, _snowman.a(_snowman));
      if (_snowman != null) {
         throw new IllegalStateException("Replacing value of " + _snowman + " with " + _snowman);
      } else {
         return this;
      }
   }

   public static ir a() {
      return new ir();
   }

   public static ir a(ir var0, ir var1) {
      ir _snowman = new ir();
      _snowman.a.putAll(_snowman.a);
      _snowman.a.putAll(_snowman.a);
      return _snowman;
   }

   public JsonElement b() {
      JsonObject _snowman = new JsonObject();
      this.a.values().forEach(var1x -> var1x.a(_snowman));
      return _snowman;
   }

   public static JsonElement a(List<ir> var0) {
      if (_snowman.size() == 1) {
         return _snowman.get(0).b();
      } else {
         JsonArray _snowman = new JsonArray();
         _snowman.forEach(var1x -> _snowman.add(var1x.b()));
         return _snowman;
      }
   }
}
