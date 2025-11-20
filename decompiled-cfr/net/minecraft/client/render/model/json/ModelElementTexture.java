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
 *  javax.annotation.Nullable
 */
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
        }
        int n = this.getRotatedUVIndex(rotation);
        return this.uvs[n == 0 || n == 1 ? 0 : 2];
    }

    public float getV(int rotation) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        }
        int n = this.getRotatedUVIndex(rotation);
        return this.uvs[n == 0 || n == 3 ? 1 : 3];
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

    public static class Deserializer
    implements JsonDeserializer<ModelElementTexture> {
        protected Deserializer() {
        }

        public ModelElementTexture deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            float[] _snowman2 = this.deserializeUVs(jsonObject);
            int _snowman3 = this.deserializeRotation(jsonObject);
            return new ModelElementTexture(_snowman2, _snowman3);
        }

        protected int deserializeRotation(JsonObject object) {
            int n = JsonHelper.getInt(object, "rotation", 0);
            if (n < 0 || n % 90 != 0 || n / 90 > 3) {
                throw new JsonParseException("Invalid rotation " + n + " found, only 0/90/180/270 allowed");
            }
            return n;
        }

        @Nullable
        private float[] deserializeUVs(JsonObject object) {
            if (!object.has("uv")) {
                return null;
            }
            JsonArray jsonArray = JsonHelper.getArray(object, "uv");
            if (jsonArray.size() != 4) {
                throw new JsonParseException("Expected 4 uv values, found: " + jsonArray.size());
            }
            float[] _snowman2 = new float[4];
            for (int i = 0; i < _snowman2.length; ++i) {
                _snowman2[i] = JsonHelper.asFloat(jsonArray.get(i), "uv[" + i + "]");
            }
            return _snowman2;
        }

        public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(functionJson, unused, context);
        }
    }
}

