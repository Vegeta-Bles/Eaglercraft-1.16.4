import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;

public class an {
   public static final an a = new an(null, null, cm.a, cb.a);
   @Nullable
   private final ael<buo> b;
   @Nullable
   private final buo c;
   private final cm d;
   private final cb e;

   public an(@Nullable ael<buo> var1, @Nullable buo var2, cm var3, cb var4) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public boolean a(aag var1, fx var2) {
      if (this == a) {
         return true;
      } else if (!_snowman.p(_snowman)) {
         return false;
      } else {
         ceh _snowman = _snowman.d_(_snowman);
         buo _snowmanx = _snowman.b();
         if (this.b != null && !this.b.a(_snowmanx)) {
            return false;
         } else if (this.c != null && _snowmanx != this.c) {
            return false;
         } else if (!this.d.a(_snowman)) {
            return false;
         } else {
            if (this.e != cb.a) {
               ccj _snowmanxx = _snowman.c(_snowman);
               if (_snowmanxx == null || !this.e.a(_snowmanxx.a(new md()))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static an a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "block");
         cb _snowmanx = cb.a(_snowman.get("nbt"));
         buo _snowmanxx = null;
         if (_snowman.has("block")) {
            vk _snowmanxxx = new vk(afd.h(_snowman, "block"));
            _snowmanxx = gm.Q.a(_snowmanxxx);
         }

         ael<buo> _snowmanxxx = null;
         if (_snowman.has("tag")) {
            vk _snowmanxxxx = new vk(afd.h(_snowman, "tag"));
            _snowmanxxx = aeh.a().a().a(_snowmanxxxx);
            if (_snowmanxxx == null) {
               throw new JsonSyntaxException("Unknown block tag '" + _snowmanxxxx + "'");
            }
         }

         cm _snowmanxxxx = cm.a(_snowman.get("state"));
         return new an(_snowmanxxx, _snowmanxx, _snowmanxxxx, _snowmanx);
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
            _snowman.addProperty("block", gm.Q.b(this.c).toString());
         }

         if (this.b != null) {
            _snowman.addProperty("tag", aeh.a().a().b(this.b).toString());
         }

         _snowman.add("nbt", this.e.a());
         _snowman.add("state", this.d.a());
         return _snowman;
      }
   }

   public static class a {
      @Nullable
      private buo a;
      @Nullable
      private ael<buo> b;
      private cm c = cm.a;
      private cb d = cb.a;

      private a() {
      }

      public static an.a a() {
         return new an.a();
      }

      public an.a a(buo var1) {
         this.a = _snowman;
         return this;
      }

      public an.a a(ael<buo> var1) {
         this.b = _snowman;
         return this;
      }

      public an.a a(cm var1) {
         this.c = _snowman;
         return this;
      }

      public an b() {
         return new an(this.b, this.a, this.c, this.d);
      }
   }
}
