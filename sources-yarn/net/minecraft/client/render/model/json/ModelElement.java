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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
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
      for (Entry<Direction, ModelElementFace> entry : this.faces.entrySet()) {
         float[] fs = this.getRotatedMatrix(entry.getKey());
         entry.getValue().textureData.setUvs(fs);
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

   @Environment(EnvType.CLIENT)
   public static class Deserializer implements JsonDeserializer<ModelElement> {
      protected Deserializer() {
      }

      public ModelElement deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject jsonObject = jsonElement.getAsJsonObject();
         Vector3f lv = this.deserializeFrom(jsonObject);
         Vector3f lv2 = this.deserializeTo(jsonObject);
         ModelRotation lv3 = this.deserializeRotation(jsonObject);
         Map<Direction, ModelElementFace> map = this.deserializeFacesValidating(jsonDeserializationContext, jsonObject);
         if (jsonObject.has("shade") && !JsonHelper.hasBoolean(jsonObject, "shade")) {
            throw new JsonParseException("Expected shade to be a Boolean");
         } else {
            boolean bl = JsonHelper.getBoolean(jsonObject, "shade", true);
            return new ModelElement(lv, lv2, map, lv3, bl);
         }
      }

      @Nullable
      private ModelRotation deserializeRotation(JsonObject object) {
         ModelRotation lv = null;
         if (object.has("rotation")) {
            JsonObject jsonObject2 = JsonHelper.getObject(object, "rotation");
            Vector3f lv2 = this.deserializeVec3f(jsonObject2, "origin");
            lv2.scale(0.0625F);
            Direction.Axis lv3 = this.deserializeAxis(jsonObject2);
            float f = this.deserializeRotationAngle(jsonObject2);
            boolean bl = JsonHelper.getBoolean(jsonObject2, "rescale", false);
            lv = new ModelRotation(lv2, lv3, f, bl);
         }

         return lv;
      }

      private float deserializeRotationAngle(JsonObject object) {
         float f = JsonHelper.getFloat(object, "angle");
         if (f != 0.0F && MathHelper.abs(f) != 22.5F && MathHelper.abs(f) != 45.0F) {
            throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
         } else {
            return f;
         }
      }

      private Direction.Axis deserializeAxis(JsonObject object) {
         String string = JsonHelper.getString(object, "axis");
         Direction.Axis lv = Direction.Axis.fromName(string.toLowerCase(Locale.ROOT));
         if (lv == null) {
            throw new JsonParseException("Invalid rotation axis: " + string);
         } else {
            return lv;
         }
      }

      private Map<Direction, ModelElementFace> deserializeFacesValidating(JsonDeserializationContext context, JsonObject object) {
         Map<Direction, ModelElementFace> map = this.deserializeFaces(context, object);
         if (map.isEmpty()) {
            throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
         } else {
            return map;
         }
      }

      private Map<Direction, ModelElementFace> deserializeFaces(JsonDeserializationContext context, JsonObject object) {
         Map<Direction, ModelElementFace> map = Maps.newEnumMap(Direction.class);
         JsonObject jsonObject2 = JsonHelper.getObject(object, "faces");

         for (Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
            Direction lv = this.getDirection(entry.getKey());
            map.put(lv, (ModelElementFace)context.deserialize(entry.getValue(), ModelElementFace.class));
         }

         return map;
      }

      private Direction getDirection(String name) {
         Direction lv = Direction.byName(name);
         if (lv == null) {
            throw new JsonParseException("Unknown facing: " + name);
         } else {
            return lv;
         }
      }

      private Vector3f deserializeTo(JsonObject object) {
         Vector3f lv = this.deserializeVec3f(object, "to");
         if (!(lv.getX() < -16.0F) && !(lv.getY() < -16.0F) && !(lv.getZ() < -16.0F) && !(lv.getX() > 32.0F) && !(lv.getY() > 32.0F) && !(lv.getZ() > 32.0F)) {
            return lv;
         } else {
            throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + lv);
         }
      }

      private Vector3f deserializeFrom(JsonObject object) {
         Vector3f lv = this.deserializeVec3f(object, "from");
         if (!(lv.getX() < -16.0F) && !(lv.getY() < -16.0F) && !(lv.getZ() < -16.0F) && !(lv.getX() > 32.0F) && !(lv.getY() > 32.0F) && !(lv.getZ() > 32.0F)) {
            return lv;
         } else {
            throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + lv);
         }
      }

      private Vector3f deserializeVec3f(JsonObject object, String name) {
         JsonArray jsonArray = JsonHelper.getArray(object, name);
         if (jsonArray.size() != 3) {
            throw new JsonParseException("Expected 3 " + name + " values, found: " + jsonArray.size());
         } else {
            float[] fs = new float[3];

            for (int i = 0; i < fs.length; i++) {
               fs[i] = JsonHelper.asFloat(jsonArray.get(i), name + "[" + i + "]");
            }

            return new Vector3f(fs[0], fs[1], fs[2]);
         }
      }
   }
}
