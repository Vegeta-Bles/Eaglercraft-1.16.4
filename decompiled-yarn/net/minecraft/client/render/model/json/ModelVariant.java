package net.minecraft.client.render.model.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Objects;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class ModelVariant implements ModelBakeSettings {
   private final Identifier location;
   private final AffineTransformation rotation;
   private final boolean uvLock;
   private final int weight;

   public ModelVariant(Identifier location, AffineTransformation _snowman, boolean uvLock, int weight) {
      this.location = location;
      this.rotation = _snowman;
      this.uvLock = uvLock;
      this.weight = weight;
   }

   public Identifier getLocation() {
      return this.location;
   }

   @Override
   public AffineTransformation getRotation() {
      return this.rotation;
   }

   @Override
   public boolean isShaded() {
      return this.uvLock;
   }

   public int getWeight() {
      return this.weight;
   }

   @Override
   public String toString() {
      return "Variant{modelLocation=" + this.location + ", rotation=" + this.rotation + ", uvLock=" + this.uvLock + ", weight=" + this.weight + '}';
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof ModelVariant)) {
         return false;
      } else {
         ModelVariant _snowman = (ModelVariant)o;
         return this.location.equals(_snowman.location) && Objects.equals(this.rotation, _snowman.rotation) && this.uvLock == _snowman.uvLock && this.weight == _snowman.weight;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.location.hashCode();
      _snowman = 31 * _snowman + this.rotation.hashCode();
      _snowman = 31 * _snowman + Boolean.valueOf(this.uvLock).hashCode();
      return 31 * _snowman + this.weight;
   }

   public static class Deserializer implements JsonDeserializer<ModelVariant> {
      public Deserializer() {
      }

      public ModelVariant deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         Identifier _snowmanxxxx = this.deserializeModel(_snowmanxxx);
         net.minecraft.client.render.model.ModelRotation _snowmanxxxxx = this.deserializeRotation(_snowmanxxx);
         boolean _snowmanxxxxxx = this.deserializeUvLock(_snowmanxxx);
         int _snowmanxxxxxxx = this.deserializeWeight(_snowmanxxx);
         return new ModelVariant(_snowmanxxxx, _snowmanxxxxx.getRotation(), _snowmanxxxxxx, _snowmanxxxxxxx);
      }

      private boolean deserializeUvLock(JsonObject object) {
         return JsonHelper.getBoolean(object, "uvlock", false);
      }

      protected net.minecraft.client.render.model.ModelRotation deserializeRotation(JsonObject object) {
         int _snowman = JsonHelper.getInt(object, "x", 0);
         int _snowmanx = JsonHelper.getInt(object, "y", 0);
         net.minecraft.client.render.model.ModelRotation _snowmanxx = net.minecraft.client.render.model.ModelRotation.get(_snowman, _snowmanx);
         if (_snowmanxx == null) {
            throw new JsonParseException("Invalid BlockModelRotation x: " + _snowman + ", y: " + _snowmanx);
         } else {
            return _snowmanxx;
         }
      }

      protected Identifier deserializeModel(JsonObject object) {
         return new Identifier(JsonHelper.getString(object, "model"));
      }

      protected int deserializeWeight(JsonObject object) {
         int _snowman = JsonHelper.getInt(object, "weight", 1);
         if (_snowman < 1) {
            throw new JsonParseException("Invalid weight " + _snowman + " found, expected integer >= 1");
         } else {
            return _snowman;
         }
      }
   }
}
