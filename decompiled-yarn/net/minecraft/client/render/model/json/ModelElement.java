package net.minecraft.client.render.model.json;

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
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

public class ModelElement {
   public final Vector3f from;
   public final Vector3f to;
   public final Map<Direction, ModelElementFace> faces;
   public final ModelRotation rotation;
   public final boolean shade;

   public ModelElement(Vector3f from, Vector3f to, Map<Direction, ModelElementFace> faces, @Nullable ModelRotation rotation, boolean shade) {
      this.from = from;
      this.to = to;
      this.faces = faces;
      this.rotation = rotation;
      this.shade = shade;
      this.initTextures();
   }

   private void initTextures() {
      for (Entry<Direction, ModelElementFace> _snowman : this.faces.entrySet()) {
         float[] _snowmanx = this.getRotatedMatrix(_snowman.getKey());
         _snowman.getValue().textureData.setUvs(_snowmanx);
      }
   }

   private float[] getRotatedMatrix(Direction direction) {
      switch (direction) {
         case DOWN:
            return new float[]{this.from.getX(), 16.0F - this.to.getZ(), this.to.getX(), 16.0F - this.from.getZ()};
         case UP:
            return new float[]{this.from.getX(), this.from.getZ(), this.to.getX(), this.to.getZ()};
         case NORTH:
         default:
            return new float[]{16.0F - this.to.getX(), 16.0F - this.to.getY(), 16.0F - this.from.getX(), 16.0F - this.from.getY()};
         case SOUTH:
            return new float[]{this.from.getX(), 16.0F - this.to.getY(), this.to.getX(), 16.0F - this.from.getY()};
         case WEST:
            return new float[]{this.from.getZ(), 16.0F - this.to.getY(), this.to.getZ(), 16.0F - this.from.getY()};
         case EAST:
            return new float[]{16.0F - this.to.getZ(), 16.0F - this.to.getY(), 16.0F - this.from.getZ(), 16.0F - this.from.getY()};
      }
   }

   public static class Deserializer implements JsonDeserializer<ModelElement> {
      protected Deserializer() {
      }

      public ModelElement deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         Vector3f _snowmanxxxx = this.deserializeFrom(_snowmanxxx);
         Vector3f _snowmanxxxxx = this.deserializeTo(_snowmanxxx);
         ModelRotation _snowmanxxxxxx = this.deserializeRotation(_snowmanxxx);
         Map<Direction, ModelElementFace> _snowmanxxxxxxx = this.deserializeFacesValidating(_snowman, _snowmanxxx);
         if (_snowmanxxx.has("shade") && !JsonHelper.hasBoolean(_snowmanxxx, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         } else {
            boolean _snowmanxxxxxxxx = JsonHelper.getBoolean(_snowmanxxx, "shade", true);
            return new ModelElement(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxx);
         }
      }

      @Nullable
      private ModelRotation deserializeRotation(JsonObject object) {
         ModelRotation _snowman = null;
         if (object.has("rotation")) {
            JsonObject _snowmanx = JsonHelper.getObject(object, "rotation");
            Vector3f _snowmanxx = this.deserializeVec3f(_snowmanx, "origin");
            _snowmanxx.scale(0.0625F);
            Direction.Axis _snowmanxxx = this.deserializeAxis(_snowmanx);
            float _snowmanxxxx = this.deserializeRotationAngle(_snowmanx);
            boolean _snowmanxxxxx = JsonHelper.getBoolean(_snowmanx, "rescale", false);
            _snowman = new ModelRotation(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
         }

         return _snowman;
      }

      private float deserializeRotationAngle(JsonObject object) {
         float _snowman = JsonHelper.getFloat(object, "angle");
         if (_snowman != 0.0F && MathHelper.abs(_snowman) != 22.5F && MathHelper.abs(_snowman) != 45.0F) {
            throw new JsonParseException("Invalid rotation " + _snowman + " found, only -45/-22.5/0/22.5/45 allowed");
         } else {
            return _snowman;
         }
      }

      private Direction.Axis deserializeAxis(JsonObject object) {
         String _snowman = JsonHelper.getString(object, "axis");
         Direction.Axis _snowmanx = Direction.Axis.fromName(_snowman.toLowerCase(Locale.ROOT));
         if (_snowmanx == null) {
            throw new JsonParseException("Invalid rotation axis: " + _snowman);
         } else {
            return _snowmanx;
         }
      }

      private Map<Direction, ModelElementFace> deserializeFacesValidating(JsonDeserializationContext context, JsonObject object) {
         Map<Direction, ModelElementFace> _snowman = this.deserializeFaces(context, object);
         if (_snowman.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return _snowman;
         }
      }

      private Map<Direction, ModelElementFace> deserializeFaces(JsonDeserializationContext context, JsonObject object) {
         Map<Direction, ModelElementFace> _snowman = Maps.newEnumMap(Direction.class);
         JsonObject _snowmanx = JsonHelper.getObject(object, "faces");

         for (Entry<String, JsonElement> _snowmanxx : _snowmanx.entrySet()) {
            Direction _snowmanxxx = this.getDirection(_snowmanxx.getKey());
            _snowman.put(_snowmanxxx, (ModelElementFace)context.deserialize(_snowmanxx.getValue(), ModelElementFace.class));
         }

         return _snowman;
      }

      private Direction getDirection(String name) {
         Direction _snowman = Direction.byName(name);
         if (_snowman == null) {
            throw new JsonParseException("Unknown facing: " + name);
         } else {
            return _snowman;
         }
      }

      private Vector3f deserializeTo(JsonObject object) {
         Vector3f _snowman = this.deserializeVec3f(object, "to");
         if (!(_snowman.getX() < -16.0F) && !(_snowman.getY() < -16.0F) && !(_snowman.getZ() < -16.0F) && !(_snowman.getX() > 32.0F) && !(_snowman.getY() > 32.0F) && !(_snowman.getZ() > 32.0F)) {
            return _snowman;
         } else {
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + _snowman);
         }
      }

      private Vector3f deserializeFrom(JsonObject object) {
         Vector3f _snowman = this.deserializeVec3f(object, "from");
         if (!(_snowman.getX() < -16.0F) && !(_snowman.getY() < -16.0F) && !(_snowman.getZ() < -16.0F) && !(_snowman.getX() > 32.0F) && !(_snowman.getY() > 32.0F) && !(_snowman.getZ() > 32.0F)) {
            return _snowman;
         } else {
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + _snowman);
         }
      }

      private Vector3f deserializeVec3f(JsonObject object, String name) {
         JsonArray _snowman = JsonHelper.getArray(object, name);
         if (_snowman.size() != 3) {
            throw new JsonParseException("Expected 3 " + name + " values, found: " + _snowman.size());
         } else {
            float[] _snowmanx = new float[3];

            for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
               _snowmanx[_snowmanxx] = JsonHelper.asFloat(_snowman.get(_snowmanxx), name + "[" + _snowmanxx + "]");
            }

            return new Vector3f(_snowmanx[0], _snowmanx[1], _snowmanx[2]);
         }
      }
   }
}
