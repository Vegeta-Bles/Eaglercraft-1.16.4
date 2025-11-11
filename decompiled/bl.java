import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class bl {
   public static final bl a = new bl(null, null, cm.a);
   @Nullable
   private final ael<cuw> b;
   @Nullable
   private final cuw c;
   private final cm d;

   public bl(@Nullable ael<cuw> var1, @Nullable cuw var2, cm var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   public boolean a(aag var1, fx var2) {
      if (this == a) {
         return true;
      } else if (!_snowman.p(_snowman)) {
         return false;
      } else {
         cux _snowman = _snowman.b(_snowman);
         cuw _snowmanx = _snowman.a();
         if (this.b != null && !this.b.a(_snowmanx)) {
            return false;
         } else {
            return this.c != null && _snowmanx != this.c ? false : this.d.a(_snowman);
         }
      }
   }

   public static bl a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "fluid");
         cuw _snowmanx = null;
         if (_snowman.has("fluid")) {
            vk _snowmanxx = new vk(afd.h(_snowman, "fluid"));
            _snowmanx = gm.O.a(_snowmanxx);
         }

         ael<cuw> _snowmanxx = null;
         if (_snowman.has("tag")) {
            vk _snowmanxxx = new vk(afd.h(_snowman, "tag"));
            _snowmanxx = aeh.a().c().a(_snowmanxxx);
            if (_snowmanxx == null) {
               throw new JsonSyntaxException("Unknown fluid tag '" + _snowmanxxx + "'");
            }
         }

         cm _snowmanxxx = cm.a(_snowman.get("state"));
         return new bl(_snowmanxx, _snowmanx, _snowmanxxx);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.c != null) {
            _snowman.addProperty("fluid", gm.O.b(this.c).toString());
         }

         if (this.b != null) {
            _snowman.addProperty("tag", aeh.a().c().b(this.b).toString());
         }

         _snowman.add("state", this.d.a());
         return _snowman;
      }
   }
}
