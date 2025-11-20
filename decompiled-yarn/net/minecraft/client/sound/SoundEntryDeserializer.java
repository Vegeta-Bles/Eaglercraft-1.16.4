package net.minecraft.client.sound;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.Validate;

public class SoundEntryDeserializer implements JsonDeserializer<SoundEntry> {
   public SoundEntryDeserializer() {
   }

   public SoundEntry deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
      JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, "entry");
      boolean _snowmanxxxx = JsonHelper.getBoolean(_snowmanxxx, "replace", false);
      String _snowmanxxxxx = JsonHelper.getString(_snowmanxxx, "subtitle", null);
      List<Sound> _snowmanxxxxxx = this.deserializeSounds(_snowmanxxx);
      return new SoundEntry(_snowmanxxxxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   private List<Sound> deserializeSounds(JsonObject json) {
      List<Sound> _snowman = Lists.newArrayList();
      if (json.has("sounds")) {
         JsonArray _snowmanx = JsonHelper.getArray(json, "sounds");

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            JsonElement _snowmanxxx = _snowmanx.get(_snowmanxx);
            if (JsonHelper.isString(_snowmanxxx)) {
               String _snowmanxxxx = JsonHelper.asString(_snowmanxxx, "sound");
               _snowman.add(new Sound(_snowmanxxxx, 1.0F, 1.0F, 1, Sound.RegistrationType.FILE, false, false, 16));
            } else {
               _snowman.add(this.deserializeSound(JsonHelper.asObject(_snowmanxxx, "sound")));
            }
         }
      }

      return _snowman;
   }

   private Sound deserializeSound(JsonObject json) {
      String _snowman = JsonHelper.getString(json, "name");
      Sound.RegistrationType _snowmanx = this.deserializeType(json, Sound.RegistrationType.FILE);
      float _snowmanxx = JsonHelper.getFloat(json, "volume", 1.0F);
      Validate.isTrue(_snowmanxx > 0.0F, "Invalid volume", new Object[0]);
      float _snowmanxxx = JsonHelper.getFloat(json, "pitch", 1.0F);
      Validate.isTrue(_snowmanxxx > 0.0F, "Invalid pitch", new Object[0]);
      int _snowmanxxxx = JsonHelper.getInt(json, "weight", 1);
      Validate.isTrue(_snowmanxxxx > 0, "Invalid weight", new Object[0]);
      boolean _snowmanxxxxx = JsonHelper.getBoolean(json, "preload", false);
      boolean _snowmanxxxxxx = JsonHelper.getBoolean(json, "stream", false);
      int _snowmanxxxxxxx = JsonHelper.getInt(json, "attenuation_distance", 16);
      return new Sound(_snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanx, _snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxx);
   }

   private Sound.RegistrationType deserializeType(JsonObject json, Sound.RegistrationType fallback) {
      Sound.RegistrationType _snowman = fallback;
      if (json.has("type")) {
         _snowman = Sound.RegistrationType.getByName(JsonHelper.getString(json, "type"));
         Validate.notNull(_snowman, "Invalid type", new Object[0]);
      }

      return _snowman;
   }
}
