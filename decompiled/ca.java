import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class ca {
   public static final ca a = new ca(Collections.emptyMap());
   private final Map<aps, ca.a> b;

   public ca(Map<aps, ca.a> var1) {
      this.b = _snowman;
   }

   public static ca a() {
      return new ca(Maps.newLinkedHashMap());
   }

   public ca a(aps var1) {
      this.b.put(_snowman, new ca.a());
      return this;
   }

   public boolean a(aqa var1) {
      if (this == a) {
         return true;
      } else {
         return _snowman instanceof aqm ? this.a(((aqm)_snowman).di()) : false;
      }
   }

   public boolean a(aqm var1) {
      return this == a ? true : this.a(_snowman.di());
   }

   public boolean a(Map<aps, apu> var1) {
      if (this == a) {
         return true;
      } else {
         for (Entry<aps, ca.a> _snowman : this.b.entrySet()) {
            apu _snowmanx = _snowman.get(_snowman.getKey());
            if (!_snowman.getValue().a(_snowmanx)) {
               return false;
            }
         }

         return true;
      }
   }

   public static ca a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "effects");
         Map<aps, ca.a> _snowmanx = Maps.newLinkedHashMap();

         for (Entry<String, JsonElement> _snowmanxx : _snowman.entrySet()) {
            vk _snowmanxxx = new vk(_snowmanxx.getKey());
            aps _snowmanxxxx = gm.P.b(_snowmanxxx).orElseThrow(() -> new JsonSyntaxException("Unknown effect '" + _snowman + "'"));
            ca.a _snowmanxxxxx = ca.a.a(afd.m(_snowmanxx.getValue(), _snowmanxx.getKey()));
            _snowmanx.put(_snowmanxxxx, _snowmanxxxxx);
         }

         return new ca(_snowmanx);
      } else {
         return a;
      }
   }

   public JsonElement b() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();

         for (Entry<aps, ca.a> _snowmanx : this.b.entrySet()) {
            _snowman.add(gm.P.b(_snowmanx.getKey()).toString(), _snowmanx.getValue().a());
         }

         return _snowman;
      }
   }

   public static class a {
      private final bz.d a;
      private final bz.d b;
      @Nullable
      private final Boolean c;
      @Nullable
      private final Boolean d;

      public a(bz.d var1, bz.d var2, @Nullable Boolean var3, @Nullable Boolean var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public a() {
         this(bz.d.e, bz.d.e, null, null);
      }

      public boolean a(@Nullable apu var1) {
         if (_snowman == null) {
            return false;
         } else if (!this.a.d(_snowman.c())) {
            return false;
         } else if (!this.b.d(_snowman.b())) {
            return false;
         } else {
            return this.c != null && this.c != _snowman.d() ? false : this.d == null || this.d == _snowman.e();
         }
      }

      public JsonElement a() {
         JsonObject _snowman = new JsonObject();
         _snowman.add("amplifier", this.a.d());
         _snowman.add("duration", this.b.d());
         _snowman.addProperty("ambient", this.c);
         _snowman.addProperty("visible", this.d);
         return _snowman;
      }

      public static ca.a a(JsonObject var0) {
         bz.d _snowman = bz.d.a(_snowman.get("amplifier"));
         bz.d _snowmanx = bz.d.a(_snowman.get("duration"));
         Boolean _snowmanxx = _snowman.has("ambient") ? afd.j(_snowman, "ambient") : null;
         Boolean _snowmanxxx = _snowman.has("visible") ? afd.j(_snowman, "visible") : null;
         return new ca.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
      }
   }
}
