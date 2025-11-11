import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import org.apache.commons.lang3.Validate;

public class ems implements JsonDeserializer<emr> {
   public ems() {
   }

   public emr a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
      JsonObject _snowman = afd.m(_snowman, "entry");
      boolean _snowmanx = afd.a(_snowman, "replace", false);
      String _snowmanxx = afd.a(_snowman, "subtitle", null);
      List<emq> _snowmanxxx = this.a(_snowman);
      return new emr(_snowmanxxx, _snowmanx, _snowmanxx);
   }

   private List<emq> a(JsonObject var1) {
      List<emq> _snowman = Lists.newArrayList();
      if (_snowman.has("sounds")) {
         JsonArray _snowmanx = afd.u(_snowman, "sounds");

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            JsonElement _snowmanxxx = _snowmanx.get(_snowmanxx);
            if (afd.a(_snowmanxxx)) {
               String _snowmanxxxx = afd.a(_snowmanxxx, "sound");
               _snowman.add(new emq(_snowmanxxxx, 1.0F, 1.0F, 1, emq.a.a, false, false, 16));
            } else {
               _snowman.add(this.b(afd.m(_snowmanxxx, "sound")));
            }
         }
      }

      return _snowman;
   }

   private emq b(JsonObject var1) {
      String _snowman = afd.h(_snowman, "name");
      emq.a _snowmanx = this.a(_snowman, emq.a.a);
      float _snowmanxx = afd.a(_snowman, "volume", 1.0F);
      Validate.isTrue(_snowmanxx > 0.0F, "Invalid volume", new Object[0]);
      float _snowmanxxx = afd.a(_snowman, "pitch", 1.0F);
      Validate.isTrue(_snowmanxxx > 0.0F, "Invalid pitch", new Object[0]);
      int _snowmanxxxx = afd.a(_snowman, "weight", 1);
      Validate.isTrue(_snowmanxxxx > 0, "Invalid weight", new Object[0]);
      boolean _snowmanxxxxx = afd.a(_snowman, "preload", false);
      boolean _snowmanxxxxxx = afd.a(_snowman, "stream", false);
      int _snowmanxxxxxxx = afd.a(_snowman, "attenuation_distance", 16);
      return new emq(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanx, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxx);
   }

   private emq.a a(JsonObject var1, emq.a var2) {
      emq.a _snowman = _snowman;
      if (_snowman.has("type")) {
         _snowman = emq.a.a(afd.h(_snowman, "type"));
         Validate.notNull(_snowman, "Invalid type", new Object[0]);
      }

      return _snowman;
   }
}
