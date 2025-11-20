package net.minecraft.client.resource.metadata;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.Validate;

public class AnimationResourceMetadataReader implements ResourceMetadataReader<AnimationResourceMetadata> {
   public AnimationResourceMetadataReader() {
   }

   public AnimationResourceMetadata fromJson(JsonObject _snowman) {
      List<AnimationFrameResourceMetadata> _snowmanx = Lists.newArrayList();
      int _snowmanxx = JsonHelper.getInt(_snowman, "frametime", 1);
      if (_snowmanxx != 1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)_snowmanxx, "Invalid default frame time");
      }

      if (_snowman.has("frames")) {
         try {
            JsonArray _snowmanxxx = JsonHelper.getArray(_snowman, "frames");

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
               JsonElement _snowmanxxxxx = _snowmanxxx.get(_snowmanxxxx);
               AnimationFrameResourceMetadata _snowmanxxxxxx = this.readFrameMetadata(_snowmanxxxx, _snowmanxxxxx);
               if (_snowmanxxxxxx != null) {
                  _snowmanx.add(_snowmanxxxxxx);
               }
            }
         } catch (ClassCastException var8) {
            throw new JsonParseException("Invalid animation->frames: expected array, was " + _snowman.get("frames"), var8);
         }
      }

      int _snowmanxxx = JsonHelper.getInt(_snowman, "width", -1);
      int _snowmanxxxxx = JsonHelper.getInt(_snowman, "height", -1);
      if (_snowmanxxx != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)_snowmanxxx, "Invalid width");
      }

      if (_snowmanxxxxx != -1) {
         Validate.inclusiveBetween(1L, 2147483647L, (long)_snowmanxxxxx, "Invalid height");
      }

      boolean _snowmanxxxxxx = JsonHelper.getBoolean(_snowman, "interpolate", false);
      return new AnimationResourceMetadata(_snowmanx, _snowmanxxx, _snowmanxxxxx, _snowmanxx, _snowmanxxxxxx);
   }

   private AnimationFrameResourceMetadata readFrameMetadata(int frame, JsonElement json) {
      if (json.isJsonPrimitive()) {
         return new AnimationFrameResourceMetadata(JsonHelper.asInt(json, "frames[" + frame + "]"));
      } else if (json.isJsonObject()) {
         JsonObject _snowman = JsonHelper.asObject(json, "frames[" + frame + "]");
         int _snowmanx = JsonHelper.getInt(_snowman, "time", -1);
         if (_snowman.has("time")) {
            Validate.inclusiveBetween(1L, 2147483647L, (long)_snowmanx, "Invalid frame time");
         }

         int _snowmanxx = JsonHelper.getInt(_snowman, "index");
         Validate.inclusiveBetween(0L, 2147483647L, (long)_snowmanxx, "Invalid frame index");
         return new AnimationFrameResourceMetadata(_snowmanxx, _snowmanx);
      } else {
         return null;
      }
   }

   @Override
   public String getKey() {
      return "animation";
   }
}
