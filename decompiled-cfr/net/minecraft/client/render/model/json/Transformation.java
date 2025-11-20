/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
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
    public static final Transformation IDENTITY = new Transformation(new Vector3f(), new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f));
    public final Vector3f rotation;
    public final Vector3f translation;
    public final Vector3f scale;

    public Transformation(Vector3f rotation, Vector3f translation, Vector3f scale) {
        this.rotation = rotation.copy();
        this.translation = translation.copy();
        this.scale = scale.copy();
    }

    public void apply(boolean leftHanded, MatrixStack matrices) {
        if (this == IDENTITY) {
            return;
        }
        float f = this.rotation.getX();
        _snowman = this.rotation.getY();
        _snowman = this.rotation.getZ();
        if (leftHanded) {
            _snowman = -_snowman;
            _snowman = -_snowman;
        }
        int _snowman2 = leftHanded ? -1 : 1;
        matrices.translate((float)_snowman2 * this.translation.getX(), this.translation.getY(), this.translation.getZ());
        matrices.multiply(new Quaternion(f, _snowman, _snowman, true));
        matrices.scale(this.scale.getX(), this.scale.getY(), this.scale.getZ());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (this.getClass() == o.getClass()) {
            Transformation transformation = (Transformation)o;
            return this.rotation.equals(transformation.rotation) && this.scale.equals(transformation.scale) && this.translation.equals(transformation.translation);
        }
        return false;
    }

    public int hashCode() {
        int n = this.rotation.hashCode();
        n = 31 * n + this.translation.hashCode();
        n = 31 * n + this.scale.hashCode();
        return n;
    }

    public static class Deserializer
    implements JsonDeserializer<Transformation> {
        private static final Vector3f DEFAULT_ROTATION = new Vector3f(0.0f, 0.0f, 0.0f);
        private static final Vector3f DEFAULT_TRANSLATION = new Vector3f(0.0f, 0.0f, 0.0f);
        private static final Vector3f DEFAULT_SCALE = new Vector3f(1.0f, 1.0f, 1.0f);

        protected Deserializer() {
        }

        public Transformation deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Vector3f _snowman2 = this.parseVector3f(jsonObject, "rotation", DEFAULT_ROTATION);
            Vector3f _snowman3 = this.parseVector3f(jsonObject, "translation", DEFAULT_TRANSLATION);
            _snowman3.scale(0.0625f);
            _snowman3.clamp(-5.0f, 5.0f);
            Vector3f _snowman4 = this.parseVector3f(jsonObject, "scale", DEFAULT_SCALE);
            _snowman4.clamp(-4.0f, 4.0f);
            return new Transformation(_snowman2, _snowman3, _snowman4);
        }

        private Vector3f parseVector3f(JsonObject json, String key, Vector3f fallback) {
            if (!json.has(key)) {
                return fallback;
            }
            JsonArray jsonArray = JsonHelper.getArray(json, key);
            if (jsonArray.size() != 3) {
                throw new JsonParseException("Expected 3 " + key + " values, found: " + jsonArray.size());
            }
            float[] _snowman2 = new float[3];
            for (int i = 0; i < _snowman2.length; ++i) {
                _snowman2[i] = JsonHelper.asFloat(jsonArray.get(i), key + "[" + i + "]");
            }
            return new Vector3f(_snowman2[0], _snowman2[1], _snowman2[2]);
        }

        public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(functionJson, unused, context);
        }
    }
}

