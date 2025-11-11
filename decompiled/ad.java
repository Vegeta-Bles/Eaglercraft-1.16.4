import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class ad {
   private final ag a;

   public ad(ag var1) {
      this.a = _snowman;
   }

   public ad() {
      this.a = null;
   }

   public void a(nf var1) {
   }

   public static ad a(JsonObject var0, ax var1) {
      vk _snowman = new vk(afd.h(_snowman, "trigger"));
      af<?> _snowmanx = ac.a(_snowman);
      if (_snowmanx == null) {
         throw new JsonSyntaxException("Invalid criterion trigger: " + _snowman);
      } else {
         ag _snowmanxx = _snowmanx.a(afd.a(_snowman, "conditions", new JsonObject()), _snowman);
         return new ad(_snowmanxx);
      }
   }

   public static ad b(nf var0) {
      return new ad();
   }

   public static Map<String, ad> b(JsonObject var0, ax var1) {
      Map<String, ad> _snowman = Maps.newHashMap();

      for (Entry<String, JsonElement> _snowmanx : _snowman.entrySet()) {
         _snowman.put(_snowmanx.getKey(), a(afd.m(_snowmanx.getValue(), "criterion"), _snowman));
      }

      return _snowman;
   }

   public static Map<String, ad> c(nf var0) {
      Map<String, ad> _snowman = Maps.newHashMap();
      int _snowmanx = _snowman.i();

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         _snowman.put(_snowman.e(32767), b(_snowman));
      }

      return _snowman;
   }

   public static void a(Map<String, ad> var0, nf var1) {
      _snowman.d(_snowman.size());

      for (Entry<String, ad> _snowman : _snowman.entrySet()) {
         _snowman.a(_snowman.getKey());
         _snowman.getValue().a(_snowman);
      }
   }

   @Nullable
   public ag a() {
      return this.a;
   }

   public JsonElement b() {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("trigger", this.a.a().toString());
      JsonObject _snowmanx = this.a.a(ci.a);
      if (_snowmanx.size() != 0) {
         _snowman.add("conditions", _snowmanx);
      }

      return _snowman;
   }
}
