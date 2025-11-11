import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;
import org.apache.commons.lang3.Validate;

public class eld implements abn<elc> {
   public eld() {
   }

   public elc b(JsonObject var1) {
      List<elb> _snowman = Lists.newArrayList();
      int _snowmanx = afd.a(_snowman, "frametime", 1);
      if (_snowmanx != 1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)_snowmanx, "Invalid default frame time");
      }

      if (_snowman.has("frames")) {
         try {
            JsonArray _snowmanxx = afd.u(_snowman, "frames");

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
               JsonElement _snowmanxxxx = _snowmanxx.get(_snowmanxxx);
               elb _snowmanxxxxx = this.a(_snowmanxxx, _snowmanxxxx);
               if (_snowmanxxxxx != null) {
                  _snowman.add(_snowmanxxxxx);
               }
            }
         } catch (ClassCastException var8) {
            throw new JsonParseException("Invalid animation->frames: expected array, was " + _snowman.get("frames"), var8);
         }
      }

      int _snowmanxx = afd.a(_snowman, "width", -1);
      int _snowmanxxxx = afd.a(_snowman, "height", -1);
      if (_snowmanxx != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)_snowmanxx, "Invalid width");
      }

      if (_snowmanxxxx != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)_snowmanxxxx, "Invalid height");
      }

      boolean _snowmanxxxxx = afd.a(_snowman, "interpolate", false);
      return new elc(_snowman, _snowmanxx, _snowmanxxxx, _snowmanx, _snowmanxxxxx);
   }

   private elb a(int var1, JsonElement var2) {
      if (_snowman.isJsonPrimitive()) {
         return new elb(afd.g(_snowman, "frames[" + _snowman + "]"));
      } else if (_snowman.isJsonObject()) {
         JsonObject _snowman = afd.m(_snowman, "frames[" + _snowman + "]");
         int _snowmanx = afd.a(_snowman, "time", -1);
         if (_snowman.has("time")) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)_snowmanx, "Invalid frame time");
         }

         int _snowmanxx = afd.n(_snowman, "index");
         Validate.inclusiveBetween(0L, 2147483647L, (long)_snowmanxx, "Invalid frame index");
         return new elb(_snowmanxx, _snowmanx);
      } else {
         return null;
      }
   }

   @Override
   public String a() {
      return "animation";
   }
}
