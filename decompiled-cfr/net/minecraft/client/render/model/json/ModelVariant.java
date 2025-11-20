/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 */
package net.minecraft.client.render.model.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Objects;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelRotation;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class ModelVariant
implements ModelBakeSettings {
    private final Identifier location;
    private final AffineTransformation rotation;
    private final boolean uvLock;
    private final int weight;

    public ModelVariant(Identifier location, AffineTransformation affineTransformation, boolean uvLock, int weight) {
        this.location = location;
        this.rotation = affineTransformation;
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

    public String toString() {
        return "Variant{modelLocation=" + this.location + ", rotation=" + this.rotation + ", uvLock=" + this.uvLock + ", weight=" + this.weight + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof ModelVariant) {
            ModelVariant modelVariant = (ModelVariant)o;
            return this.location.equals(modelVariant.location) && Objects.equals(this.rotation, modelVariant.rotation) && this.uvLock == modelVariant.uvLock && this.weight == modelVariant.weight;
        }
        return false;
    }

    public int hashCode() {
        int n = this.location.hashCode();
        n = 31 * n + this.rotation.hashCode();
        n = 31 * n + Boolean.valueOf(this.uvLock).hashCode();
        n = 31 * n + this.weight;
        return n;
    }

    public static class Deserializer
    implements JsonDeserializer<ModelVariant> {
        public ModelVariant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Identifier _snowman2 = this.deserializeModel(jsonObject);
            ModelRotation _snowman3 = this.deserializeRotation(jsonObject);
            boolean _snowman4 = this.deserializeUvLock(jsonObject);
            int _snowman5 = this.deserializeWeight(jsonObject);
            return new ModelVariant(_snowman2, _snowman3.getRotation(), _snowman4, _snowman5);
        }

        private boolean deserializeUvLock(JsonObject object) {
            return JsonHelper.getBoolean(object, "uvlock", false);
        }

        protected ModelRotation deserializeRotation(JsonObject object) {
            int n = JsonHelper.getInt(object, "x", 0);
            ModelRotation _snowman2 = ModelRotation.get(n, _snowman = JsonHelper.getInt(object, "y", 0));
            if (_snowman2 == null) {
                throw new JsonParseException("Invalid BlockModelRotation x: " + n + ", y: " + _snowman);
            }
            return _snowman2;
        }

        protected Identifier deserializeModel(JsonObject object) {
            return new Identifier(JsonHelper.getString(object, "model"));
        }

        protected int deserializeWeight(JsonObject object) {
            int n = JsonHelper.getInt(object, "weight", 1);
            if (n < 1) {
                throw new JsonParseException("Invalid weight " + n + " found, expected integer >= 1");
            }
            return n;
        }

        public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(functionJson, unused, context);
        }
    }
}

