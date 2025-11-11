import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class ebg {
   private final Map<String, ebn> a = Maps.newLinkedHashMap();
   private ebs b;

   public static ebg a(ebg.a var0, Reader var1) {
      return afd.a(_snowman.a, _snowman, ebg.class);
   }

   public ebg(Map<String, ebn> var1, ebs var2) {
      this.b = _snowman;
      this.a.putAll(_snowman);
   }

   public ebg(List<ebg> var1) {
      ebg _snowman = null;

      for (ebg _snowmanx : _snowman) {
         if (_snowmanx.c()) {
            this.a.clear();
            _snowman = _snowmanx;
         }

         this.a.putAll(_snowmanx.a);
      }

      if (_snowman != null) {
         this.b = _snowman.b;
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else {
         if (_snowman instanceof ebg) {
            ebg _snowman = (ebg)_snowman;
            if (this.a.equals(_snowman.a)) {
               return this.c() ? this.b.equals(_snowman.b) : !_snowman.c();
            }
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.a.hashCode() + (this.c() ? this.b.hashCode() : 0);
   }

   public Map<String, ebn> a() {
      return this.a;
   }

   public boolean c() {
      return this.b != null;
   }

   public ebs d() {
      return this.b;
   }

   public static final class a {
      protected final Gson a = new GsonBuilder()
         .registerTypeAdapter(ebg.class, new ebg.b())
         .registerTypeAdapter(ebo.class, new ebo.a())
         .registerTypeAdapter(ebn.class, new ebn.a())
         .registerTypeAdapter(ebs.class, new ebs.a(this))
         .registerTypeAdapter(ebu.class, new ebu.a())
         .create();
      private cei<buo, ceh> b;

      public a() {
      }

      public cei<buo, ceh> a() {
         return this.b;
      }

      public void a(cei<buo, ceh> var1) {
         this.b = _snowman;
      }
   }

   public static class b implements JsonDeserializer<ebg> {
      public b() {
      }

      public ebg a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         Map<String, ebn> _snowmanx = this.a(_snowman, _snowman);
         ebs _snowmanxx = this.b(_snowman, _snowman);
         if (!_snowmanx.isEmpty() || _snowmanxx != null && !_snowmanxx.b().isEmpty()) {
            return new ebg(_snowmanx, _snowmanxx);
         } else {
            throw new JsonParseException("Neither 'variants' nor 'multipart' found");
         }
      }

      protected Map<String, ebn> a(JsonDeserializationContext var1, JsonObject var2) {
         Map<String, ebn> _snowman = Maps.newHashMap();
         if (_snowman.has("variants")) {
            JsonObject _snowmanx = afd.t(_snowman, "variants");

            for (Entry<String, JsonElement> _snowmanxx : _snowmanx.entrySet()) {
               _snowman.put(_snowmanxx.getKey(), (ebn)_snowman.deserialize(_snowmanxx.getValue(), ebn.class));
            }
         }

         return _snowman;
      }

      @Nullable
      protected ebs b(JsonDeserializationContext var1, JsonObject var2) {
         if (!_snowman.has("multipart")) {
            return null;
         } else {
            JsonArray _snowman = afd.u(_snowman, "multipart");
            return (ebs)_snowman.deserialize(_snowman, ebs.class);
         }
      }
   }
}
