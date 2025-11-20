/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.model.json;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.render.model.json.ModelElementFace;
import net.minecraft.client.render.model.json.ModelRotation;
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
        for (Map.Entry<Direction, ModelElementFace> entry : this.faces.entrySet()) {
            float[] fArray = this.getRotatedMatrix(entry.getKey());
            entry.getValue().textureData.setUvs(fArray);
        }
    }

    private float[] getRotatedMatrix(Direction direction) {
        switch (direction) {
            case DOWN: {
                return new float[]{this.from.getX(), 16.0f - this.to.getZ(), this.to.getX(), 16.0f - this.from.getZ()};
            }
            case UP: {
                return new float[]{this.from.getX(), this.from.getZ(), this.to.getX(), this.to.getZ()};
            }
            default: {
                return new float[]{16.0f - this.to.getX(), 16.0f - this.to.getY(), 16.0f - this.from.getX(), 16.0f - this.from.getY()};
            }
            case SOUTH: {
                return new float[]{this.from.getX(), 16.0f - this.to.getY(), this.to.getX(), 16.0f - this.from.getY()};
            }
            case WEST: {
                return new float[]{this.from.getZ(), 16.0f - this.to.getY(), this.to.getZ(), 16.0f - this.from.getY()};
            }
            case EAST: 
        }
        return new float[]{16.0f - this.to.getZ(), 16.0f - this.to.getY(), 16.0f - this.from.getZ(), 16.0f - this.from.getY()};
    }

    public static class Deserializer
    implements JsonDeserializer<ModelElement> {
        protected Deserializer() {
        }

        public ModelElement deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Vector3f _snowman2 = this.deserializeFrom(jsonObject);
            Vector3f _snowman3 = this.deserializeTo(jsonObject);
            ModelRotation _snowman4 = this.deserializeRotation(jsonObject);
            Map<Direction, ModelElementFace> _snowman5 = this.deserializeFacesValidating(jsonDeserializationContext, jsonObject);
            if (jsonObject.has("shade") && !JsonHelper.hasBoolean(jsonObject, "shade")) {
                throw new JsonParseException("Expected shade to be a Boolean");
            }
            boolean _snowman6 = JsonHelper.getBoolean(jsonObject, "shade", true);
            return new ModelElement(_snowman2, _snowman3, _snowman5, _snowman4, _snowman6);
        }

        @Nullable
        private ModelRotation deserializeRotation(JsonObject object) {
            ModelRotation _snowman6 = null;
            if (object.has("rotation")) {
                JsonObject jsonObject = JsonHelper.getObject(object, "rotation");
                Vector3f _snowman2 = this.deserializeVec3f(jsonObject, "origin");
                _snowman2.scale(0.0625f);
                Direction.Axis _snowman3 = this.deserializeAxis(jsonObject);
                float _snowman4 = this.deserializeRotationAngle(jsonObject);
                boolean _snowman5 = JsonHelper.getBoolean(jsonObject, "rescale", false);
                _snowman6 = new ModelRotation(_snowman2, _snowman3, _snowman4, _snowman5);
            }
            return _snowman6;
        }

        private float deserializeRotationAngle(JsonObject object) {
            float f = JsonHelper.getFloat(object, "angle");
            if (f != 0.0f && MathHelper.abs(f) != 22.5f && MathHelper.abs(f) != 45.0f) {
                throw new JsonParseException("Invalid rotation " + f + " found, only -45/-22.5/0/22.5/45 allowed");
            }
            return f;
        }

        private Direction.Axis deserializeAxis(JsonObject object) {
            String string = JsonHelper.getString(object, "axis");
            Direction.Axis _snowman2 = Direction.Axis.fromName(string.toLowerCase(Locale.ROOT));
            if (_snowman2 == null) {
                throw new JsonParseException("Invalid rotation axis: " + string);
            }
            return _snowman2;
        }

        private Map<Direction, ModelElementFace> deserializeFacesValidating(JsonDeserializationContext context, JsonObject object) {
            Map<Direction, ModelElementFace> map = this.deserializeFaces(context, object);
            if (map.isEmpty()) {
                throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
            }
            return map;
        }

        private Map<Direction, ModelElementFace> deserializeFaces(JsonDeserializationContext context, JsonObject object) {
            EnumMap enumMap = Maps.newEnumMap(Direction.class);
            JsonObject _snowman2 = JsonHelper.getObject(object, "faces");
            for (Map.Entry entry : _snowman2.entrySet()) {
                Direction direction = this.getDirection((String)entry.getKey());
                enumMap.put(direction, context.deserialize((JsonElement)entry.getValue(), ModelElementFace.class));
            }
            return enumMap;
        }

        private Direction getDirection(String name) {
            Direction direction = Direction.byName(name);
            if (direction == null) {
                throw new JsonParseException("Unknown facing: " + name);
            }
            return direction;
        }

        private Vector3f deserializeTo(JsonObject object) {
            Vector3f vector3f = this.deserializeVec3f(object, "to");
            if (vector3f.getX() < -16.0f || vector3f.getY() < -16.0f || vector3f.getZ() < -16.0f || vector3f.getX() > 32.0f || vector3f.getY() > 32.0f || vector3f.getZ() > 32.0f) {
                throw new JsonParseException("'to' specifier exceeds the allowed boundaries: " + vector3f);
            }
            return vector3f;
        }

        private Vector3f deserializeFrom(JsonObject object) {
            Vector3f vector3f = this.deserializeVec3f(object, "from");
            if (vector3f.getX() < -16.0f || vector3f.getY() < -16.0f || vector3f.getZ() < -16.0f || vector3f.getX() > 32.0f || vector3f.getY() > 32.0f || vector3f.getZ() > 32.0f) {
                throw new JsonParseException("'from' specifier exceeds the allowed boundaries: " + vector3f);
            }
            return vector3f;
        }

        private Vector3f deserializeVec3f(JsonObject object, String name) {
            JsonArray jsonArray = JsonHelper.getArray(object, name);
            if (jsonArray.size() != 3) {
                throw new JsonParseException("Expected 3 " + name + " values, found: " + jsonArray.size());
            }
            float[] _snowman2 = new float[3];
            for (int i = 0; i < _snowman2.length; ++i) {
                _snowman2[i] = JsonHelper.asFloat(jsonArray.get(i), name + "[" + i + "]");
            }
            return new Vector3f(_snowman2[0], _snowman2[1], _snowman2[2]);
        }

        public /* synthetic */ Object deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(json, type, context);
        }
    }
}

