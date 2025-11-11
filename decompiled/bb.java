import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import javax.annotation.Nullable;

public class bb {
   public static final bb a = new bb();
   public static final bb[] b = new bb[0];
   private final bps c;
   private final bz.d d;

   public bb() {
      this.c = null;
      this.d = bz.d.e;
   }

   public bb(@Nullable bps var1, bz.d var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   public boolean a(Map<bps, Integer> var1) {
      if (this.c != null) {
         if (!_snowman.containsKey(this.c)) {
            return false;
         }

         int _snowman = _snowman.get(this.c);
         if (this.d != null && !this.d.d(_snowman)) {
            return false;
         }
      } else if (this.d != null) {
         for (Integer _snowman : _snowman.values()) {
            if (this.d.d(_snowman)) {
               return true;
            }
         }

         return false;
      }

      return true;
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.c != null) {
            _snowman.addProperty("enchantment", gm.R.b(this.c).toString());
         }

         _snowman.add("levels", this.d.d());
         return _snowman;
      }
   }

   public static bb a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "enchantment");
         bps _snowmanx = null;
         if (_snowman.has("enchantment")) {
            vk _snowmanxx = new vk(afd.h(_snowman, "enchantment"));
            _snowmanx = gm.R.b(_snowmanxx).orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + _snowman + "'"));
         }

         bz.d _snowmanxx = bz.d.a(_snowman.get("levels"));
         return new bb(_snowmanx, _snowmanxx);
      } else {
         return a;
      }
   }

   public static bb[] b(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonArray _snowman = afd.n(_snowman, "enchantments");
         bb[] _snowmanx = new bb[_snowman.size()];

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
            _snowmanx[_snowmanxx] = a(_snowman.get(_snowmanxx));
         }

         return _snowmanx;
      } else {
         return b;
      }
   }
}
