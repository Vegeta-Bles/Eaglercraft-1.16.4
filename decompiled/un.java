import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.GameProfile;
import java.lang.reflect.Type;
import java.util.UUID;

public class un {
   private nr a;
   private un.a b;
   private un.c c;
   private String d;

   public un() {
   }

   public nr a() {
      return this.a;
   }

   public void a(nr var1) {
      this.a = _snowman;
   }

   public un.a b() {
      return this.b;
   }

   public void a(un.a var1) {
      this.b = _snowman;
   }

   public un.c c() {
      return this.c;
   }

   public void a(un.c var1) {
      this.c = _snowman;
   }

   public void a(String var1) {
      this.d = _snowman;
   }

   public String d() {
      return this.d;
   }

   public static class a {
      private final int a;
      private final int b;
      private GameProfile[] c;

      public a(int var1, int var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public int a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public GameProfile[] c() {
         return this.c;
      }

      public void a(GameProfile[] var1) {
         this.c = _snowman;
      }

      public static class a implements JsonDeserializer<un.a>, JsonSerializer<un.a> {
         public a() {
         }

         public un.a a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject _snowman = afd.m(_snowman, "players");
            un.a _snowmanx = new un.a(afd.n(_snowman, "max"), afd.n(_snowman, "online"));
            if (afd.d(_snowman, "sample")) {
               JsonArray _snowmanxx = afd.u(_snowman, "sample");
               if (_snowmanxx.size() > 0) {
                  GameProfile[] _snowmanxxx = new GameProfile[_snowmanxx.size()];

                  for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.length; _snowmanxxxx++) {
                     JsonObject _snowmanxxxxx = afd.m(_snowmanxx.get(_snowmanxxxx), "player[" + _snowmanxxxx + "]");
                     String _snowmanxxxxxx = afd.h(_snowmanxxxxx, "id");
                     _snowmanxxx[_snowmanxxxx] = new GameProfile(UUID.fromString(_snowmanxxxxxx), afd.h(_snowmanxxxxx, "name"));
                  }

                  _snowmanx.a(_snowmanxxx);
               }
            }

            return _snowmanx;
         }

         public JsonElement a(un.a var1, Type var2, JsonSerializationContext var3) {
            JsonObject _snowman = new JsonObject();
            _snowman.addProperty("max", _snowman.a());
            _snowman.addProperty("online", _snowman.b());
            if (_snowman.c() != null && _snowman.c().length > 0) {
               JsonArray _snowmanx = new JsonArray();

               for (int _snowmanxx = 0; _snowmanxx < _snowman.c().length; _snowmanxx++) {
                  JsonObject _snowmanxxx = new JsonObject();
                  UUID _snowmanxxxx = _snowman.c()[_snowmanxx].getId();
                  _snowmanxxx.addProperty("id", _snowmanxxxx == null ? "" : _snowmanxxxx.toString());
                  _snowmanxxx.addProperty("name", _snowman.c()[_snowmanxx].getName());
                  _snowmanx.add(_snowmanxxx);
               }

               _snowman.add("sample", _snowmanx);
            }

            return _snowman;
         }
      }
   }

   public static class b implements JsonDeserializer<un>, JsonSerializer<un> {
      public b() {
      }

      public un a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = afd.m(_snowman, "status");
         un _snowmanx = new un();
         if (_snowman.has("description")) {
            _snowmanx.a((nr)_snowman.deserialize(_snowman.get("description"), nr.class));
         }

         if (_snowman.has("players")) {
            _snowmanx.a((un.a)_snowman.deserialize(_snowman.get("players"), un.a.class));
         }

         if (_snowman.has("version")) {
            _snowmanx.a((un.c)_snowman.deserialize(_snowman.get("version"), un.c.class));
         }

         if (_snowman.has("favicon")) {
            _snowmanx.a(afd.h(_snowman, "favicon"));
         }

         return _snowmanx;
      }

      public JsonElement a(un var1, Type var2, JsonSerializationContext var3) {
         JsonObject _snowman = new JsonObject();
         if (_snowman.a() != null) {
            _snowman.add("description", _snowman.serialize(_snowman.a()));
         }

         if (_snowman.b() != null) {
            _snowman.add("players", _snowman.serialize(_snowman.b()));
         }

         if (_snowman.c() != null) {
            _snowman.add("version", _snowman.serialize(_snowman.c()));
         }

         if (_snowman.d() != null) {
            _snowman.addProperty("favicon", _snowman.d());
         }

         return _snowman;
      }
   }

   public static class c {
      private final String a;
      private final int b;

      public c(String var1, int var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public String a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public static class a implements JsonDeserializer<un.c>, JsonSerializer<un.c> {
         public a() {
         }

         public un.c a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            JsonObject _snowman = afd.m(_snowman, "version");
            return new un.c(afd.h(_snowman, "name"), afd.n(_snowman, "protocol"));
         }

         public JsonElement a(un.c var1, Type var2, JsonSerializationContext var3) {
            JsonObject _snowman = new JsonObject();
            _snowman.addProperty("name", _snowman.a());
            _snowman.addProperty("protocol", _snowman.b());
            return _snowman;
         }
      }
   }
}
