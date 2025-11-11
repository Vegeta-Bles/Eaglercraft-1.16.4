import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class ebb {
   public final g a;
   public final g b;
   public final Map<gc, ebc> c;
   public final ebd d;
   public final boolean e;

   public ebb(g var1, g var2, Map<gc, ebc> var3, @Nullable ebd var4, boolean var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.a();
   }

   private void a() {
      for (Entry<gc, ebc> _snowman : this.c.entrySet()) {
         float[] _snowmanx = this.a(_snowman.getKey());
         _snowman.getValue().d.a(_snowmanx);
      }
   }

   private float[] a(gc var1) {
      switch (_snowman) {
         case a:
            return new float[]{this.a.a(), 16.0F - this.b.c(), this.b.a(), 16.0F - this.a.c()};
         case b:
            return new float[]{this.a.a(), this.a.c(), this.b.a(), this.b.c()};
         case c:
         default:
            return new float[]{16.0F - this.b.a(), 16.0F - this.b.b(), 16.0F - this.a.a(), 16.0F - this.a.b()};
         case d:
            return new float[]{this.a.a(), 16.0F - this.b.b(), this.b.a(), 16.0F - this.a.b()};
         case e:
            return new float[]{this.a.c(), 16.0F - this.b.b(), this.b.c(), 16.0F - this.a.b()};
         case f:
            return new float[]{16.0F - this.b.c(), 16.0F - this.b.b(), 16.0F - this.a.c(), 16.0F - this.a.b()};
      }
   }

   public static class a implements JsonDeserializer<ebb> {
      protected a() {
      }

      public ebb a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         g _snowmanx = this.e(_snowman);
         g _snowmanxx = this.d(_snowman);
         ebd _snowmanxxx = this.a(_snowman);
         Map<gc, ebc> _snowmanxxxx = this.a(_snowman, _snowman);
         if (_snowman.has("shade") && !afd.c(_snowman, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         } else {
            boolean _snowmanxxxxx = afd.a(_snowman, "shade", true);
            return new ebb(_snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx);
         }
      }

      @Nullable
      private ebd a(JsonObject var1) {
         ebd _snowman = null;
         if (_snowman.has("rotation")) {
            JsonObject _snowmanx = afd.t(_snowman, "rotation");
            g _snowmanxx = this.a(_snowmanx, "origin");
            _snowmanxx.b(0.0625F);
            gc.a _snowmanxxx = this.c(_snowmanx);
            float _snowmanxxxx = this.b(_snowmanx);
            boolean _snowmanxxxxx = afd.a(_snowmanx, "rescale", false);
            _snowman = new ebd(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
         }

         return _snowman;
      }

      private float b(JsonObject var1) {
         float _snowman = afd.l(_snowman, "angle");
         if (_snowman != 0.0F && afm.e(_snowman) != 22.5F && afm.e(_snowman) != 45.0F) {
            throw new JsonParseException("Invalid rotation " + _snowman + " found, only -45/-22.5/0/22.5/45 allowed");
         } else {
            return _snowman;
         }
      }

      private gc.a c(JsonObject var1) {
         String _snowman = afd.h(_snowman, "axis");
         gc.a _snowmanx = gc.a.a(_snowman.toLowerCase(Locale.ROOT));
         if (_snowmanx == null) {
            throw new JsonParseException("Invalid rotation axis: " + _snowman);
         } else {
            return _snowmanx;
         }
      }

      private Map<gc, ebc> a(JsonDeserializationContext var1, JsonObject var2) {
         Map<gc, ebc> _snowman = this.b(_snowman, _snowman);
         if (_snowman.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return _snowman;
         }
      }

      private Map<gc, ebc> b(JsonDeserializationContext var1, JsonObject var2) {
         Map<gc, ebc> _snowman = Maps.newEnumMap(gc.class);
         JsonObject _snowmanx = afd.t(_snowman, "faces");

         for (Entry<String, JsonElement> _snowmanxx : _snowmanx.entrySet()) {
            gc _snowmanxxx = this.a(_snowmanxx.getKey());
            _snowman.put(_snowmanxxx, (ebc)_snowman.deserialize(_snowmanxx.getValue(), ebc.class));
         }

         return _snowman;
      }

      private gc a(String var1) {
         gc _snowman = gc.a(_snowman);
         if (_snowman == null) {
            throw new JsonParseException("Unknown facing: " + _snowman);
         } else {
            return _snowman;
         }
      }

      private g d(JsonObject var1) {
         g _snowman = this.a(_snowman, "to");
         if (!(_snowman.a() < -16.0F) && !(_snowman.b() < -16.0F) && !(_snowman.c() < -16.0F) && !(_snowman.a() > 32.0F) && !(_snowman.b() > 32.0F) && !(_snowman.c() > 32.0F)) {
            return _snowman;
         } else {
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + _snowman);
         }
      }

      private g e(JsonObject var1) {
         g _snowman = this.a(_snowman, "from");
         if (!(_snowman.a() < -16.0F) && !(_snowman.b() < -16.0F) && !(_snowman.c() < -16.0F) && !(_snowman.a() > 32.0F) && !(_snowman.b() > 32.0F) && !(_snowman.c() > 32.0F)) {
            return _snowman;
         } else {
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + _snowman);
         }
      }

      private g a(JsonObject var1, String var2) {
         JsonArray _snowman = afd.u(_snowman, _snowman);
         if (_snowman.size() != 3) {
            throw new JsonParseException("Expected 3 " + _snowman + " values, found: " + _snowman.size());
         } else {
            float[] _snowmanx = new float[3];

            for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
               _snowmanx[_snowmanxx] = afd.e(_snowman.get(_snowmanxx), _snowman + "[" + _snowmanxx + "]");
            }

            return new g(_snowmanx[0], _snowmanx[1], _snowmanx[2]);
         }
      }
   }
}
