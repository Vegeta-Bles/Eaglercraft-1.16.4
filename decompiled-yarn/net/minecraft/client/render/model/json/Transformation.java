package net.minecraft.client.render.model.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Quaternion;

public class Transformation {
   public static final Transformation IDENTITY = new Transformation(new Vector3f(), new Vector3f(), new Vector3f(1.0F, 1.0F, 1.0F));
   public final Vector3f rotation;
   public final Vector3f translation;
   public final Vector3f scale;

   public Transformation(Vector3f rotation, Vector3f translation, Vector3f scale) {
      this.rotation = rotation.copy();
      this.translation = translation.copy();
      this.scale = scale.copy();
   }

   public void apply(boolean leftHanded, MatrixStack matrices) {
      if (this != IDENTITY) {
         float _snowman = this.rotation.getX();
         float _snowmanx = this.rotation.getY();
         float _snowmanxx = this.rotation.getZ();
         if (leftHanded) {
            _snowmanx = -_snowmanx;
            _snowmanxx = -_snowmanxx;
         }

         int _snowmanxxx = leftHanded ? -1 : 1;
         matrices.translate((double)((float)_snowmanxxx * this.translation.getX()), (double)this.translation.getY(), (double)this.translation.getZ());
         matrices.multiply(new Quaternion(_snowman, _snowmanx, _snowmanxx, true));
         matrices.scale(this.scale.getX(), this.scale.getY(), this.scale.getZ());
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (this.getClass() != o.getClass()) {
         return false;
      } else {
         Transformation _snowman = (Transformation)o;
         return this.rotation.equals(_snowman.rotation) && this.scale.equals(_snowman.scale) && this.translation.equals(_snowman.translation);
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.rotation.hashCode();
      _snowman = 31 * _snowman + this.translation.hashCode();
      return 31 * _snowman + this.scale.hashCode();
   }

   public static class Deserializer implements JsonDeserializer<Transformation> {
      private static final Vector3f DEFAULT_ROTATION = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f DEFAULT_TRANSLATION = new Vector3f(0.0F, 0.0F, 0.0F);
      private static final Vector3f DEFAULT_SCALE = new Vector3f(1.0F, 1.0F, 1.0F);

      protected Deserializer() {
      }

      public Transformation deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         Vector3f _snowmanxxxx = this.parseVector3f(_snowmanxxx, "rotation", DEFAULT_ROTATION);
         Vector3f _snowmanxxxxx = this.parseVector3f(_snowmanxxx, "translation", DEFAULT_TRANSLATION);
         _snowmanxxxxx.scale(0.0625F);
         _snowmanxxxxx.clamp(-5.0F, 5.0F);
         Vector3f _snowmanxxxxxx = this.parseVector3f(_snowmanxxx, "scale", DEFAULT_SCALE);
         _snowmanxxxxxx.clamp(-4.0F, 4.0F);
         return new Transformation(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      }

      private Vector3f parseVector3f(JsonObject json, String key, Vector3f fallback) {
         if (!json.has(key)) {
            return fallback;
         } else {
            JsonArray _snowman = JsonHelper.getArray(json, key);
            if (_snowman.size() != 3) {
               throw new JsonParseException("Expected 3 " + key + " values, found: " + _snowman.size());
            } else {
               float[] _snowmanx = new float[3];

               for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
                  _snowmanx[_snowmanxx] = JsonHelper.asFloat(_snowman.get(_snowmanxx), key + "[" + _snowmanxx + "]");
               }

               return new Vector3f(_snowmanx[0], _snowmanx[1], _snowmanx[2]);
            }
         }
      }
   }
}
