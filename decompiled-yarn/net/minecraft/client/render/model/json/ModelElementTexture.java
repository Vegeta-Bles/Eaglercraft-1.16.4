package net.minecraft.client.render.model.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.util.JsonHelper;

public class ModelElementTexture {
   public float[] uvs;
   public final int rotation;

   public ModelElementTexture(@Nullable float[] uvs, int rotation) {
      this.uvs = uvs;
      this.rotation = rotation;
   }

   public float getU(int rotation) {
      if (this.uvs == null) {
         throw new NullPointerException("uvs");
      } else {
         int _snowman = this.getRotatedUVIndex(rotation);
         return this.uvs[_snowman != 0 && _snowman != 1 ? 2 : 0];
      }
   }

   public float getV(int rotation) {
      if (this.uvs == null) {
         throw new NullPointerException("uvs");
      } else {
         int _snowman = this.getRotatedUVIndex(rotation);
         return this.uvs[_snowman != 0 && _snowman != 3 ? 3 : 1];
      }
   }

   private int getRotatedUVIndex(int rotation) {
      return (rotation + this.rotation / 90) % 4;
   }

   public int getDirectionIndex(int offset) {
      return (offset + 4 - this.rotation / 90) % 4;
   }

   public void setUvs(float[] uvs) {
      if (this.uvs == null) {
         this.uvs = uvs;
      }
   }

   public static class Deserializer implements JsonDeserializer<ModelElementTexture> {
      protected Deserializer() {
      }

      public ModelElementTexture deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         float[] _snowmanxxxx = this.deserializeUVs(_snowmanxxx);
         int _snowmanxxxxx = this.deserializeRotation(_snowmanxxx);
         return new ModelElementTexture(_snowmanxxxx, _snowmanxxxxx);
      }

      protected int deserializeRotation(JsonObject object) {
         int _snowman = JsonHelper.getInt(object, "rotation", 0);
         if (_snowman >= 0 && _snowman % 90 == 0 && _snowman / 90 <= 3) {
            return _snowman;
         } else {
            throw new JsonParseException("Invalid rotation " + _snowman + " found, only 0/90/180/270 allowed");
         }
      }

      @Nullable
      private float[] deserializeUVs(JsonObject object) {
         if (!object.has("uv")) {
            return null;
         } else {
            JsonArray _snowman = JsonHelper.getArray(object, "uv");
            if (_snowman.size() != 4) {
               throw new JsonParseException("Expected 4 uv values, found: " + _snowman.size());
            } else {
               float[] _snowmanx = new float[4];

               for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
                  _snowmanx[_snowmanxx] = JsonHelper.asFloat(_snowman.get(_snowmanxx), "uv[" + _snowmanxx + "]");
               }

               return _snowmanx;
            }
         }
      }
   }
}
