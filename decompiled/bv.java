import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class bv {
   public static final bv a = new bv(bz.d.e);
   private final bz.d b;

   private bv(bz.d var1) {
      this.b = _snowman;
   }

   public boolean a(aag var1, fx var2) {
      if (this == a) {
         return true;
      } else {
         return !_snowman.p(_snowman) ? false : this.b.d(_snowman.B(_snowman));
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("light", this.b.d());
         return _snowman;
      }
   }

   public static bv a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "light");
         bz.d _snowmanx = bz.d.a(_snowman.get("light"));
         return new bv(_snowmanx);
      } else {
         return a;
      }
   }
}
