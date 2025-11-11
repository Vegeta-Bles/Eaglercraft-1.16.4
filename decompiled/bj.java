import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import javax.annotation.Nullable;

public class bj {
   public static final bj a = new bj(false);
   private boolean b;

   private bj(boolean var1) {
      this.b = _snowman;
   }

   public static bj a(boolean var0) {
      return new bj(_snowman);
   }

   public static bj a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "fishing_hook");
         JsonElement _snowmanx = _snowman.get("in_open_water");
         return _snowmanx != null ? new bj(afd.c(_snowmanx, "in_open_water")) : a;
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("in_open_water", new JsonPrimitive(this.b));
         return _snowman;
      }
   }

   public boolean a(aqa var1) {
      if (this == a) {
         return true;
      } else if (!(_snowman instanceof bgi)) {
         return false;
      } else {
         bgi _snowman = (bgi)_snowman;
         return this.b == _snowman.g();
      }
   }
}
