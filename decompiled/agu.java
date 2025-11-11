import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.lang.reflect.Type;
import org.apache.commons.lang3.StringUtils;

public class agu extends ajv {
   public static final Gson a = new GsonBuilder().registerTypeAdapter(nr.class, new JsonDeserializer<nr>() {
      public nx a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (_snowman.isJsonPrimitive()) {
            return new oe(_snowman.getAsString());
         } else if (_snowman.isJsonArray()) {
            JsonArray _snowman = _snowman.getAsJsonArray();
            nx _snowmanx = null;

            for (JsonElement _snowmanxx : _snowman) {
               nx _snowmanxxx = this.a(_snowmanxx, _snowmanxx.getClass(), _snowman);
               if (_snowmanx == null) {
                  _snowmanx = _snowmanxxx;
               } else {
                  _snowmanx.a(_snowmanxxx);
               }
            }

            return _snowmanx;
         } else {
            throw new JsonParseException("Don't know how to turn " + _snowman + " into a Component");
         }
      }
   }).create();

   public agu(Schema var1, boolean var2) {
      super(_snowman, _snowman, "BlockEntitySignTextStrictJsonFix", akn.k, "Sign");
   }

   private Dynamic<?> a(Dynamic<?> var1, String var2) {
      String _snowman = _snowman.get(_snowman).asString("");
      nr _snowmanx = null;
      if (!"null".equals(_snowman) && !StringUtils.isEmpty(_snowman)) {
         if (_snowman.charAt(0) == '"' && _snowman.charAt(_snowman.length() - 1) == '"' || _snowman.charAt(0) == '{' && _snowman.charAt(_snowman.length() - 1) == '}') {
            try {
               _snowmanx = afd.a(a, _snowman, nr.class, true);
               if (_snowmanx == null) {
                  _snowmanx = oe.d;
               }
            } catch (JsonParseException var8) {
            }

            if (_snowmanx == null) {
               try {
                  _snowmanx = nr.a.a(_snowman);
               } catch (JsonParseException var7) {
               }
            }

            if (_snowmanx == null) {
               try {
                  _snowmanx = nr.a.b(_snowman);
               } catch (JsonParseException var6) {
               }
            }

            if (_snowmanx == null) {
               _snowmanx = new oe(_snowman);
            }
         } else {
            _snowmanx = new oe(_snowman);
         }
      } else {
         _snowmanx = oe.d;
      }

      return _snowman.set(_snowman, _snowman.createString(nr.a.a(_snowmanx)));
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), var1x -> {
         var1x = this.a(var1x, "Text1");
         var1x = this.a(var1x, "Text2");
         var1x = this.a(var1x, "Text3");
         return this.a(var1x, "Text4");
      });
   }
}
