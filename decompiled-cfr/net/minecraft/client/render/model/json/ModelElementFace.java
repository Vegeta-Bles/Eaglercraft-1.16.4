/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.model.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import net.minecraft.client.render.model.json.ModelElementTexture;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.Direction;

public class ModelElementFace {
    public final Direction cullFace;
    public final int tintIndex;
    public final String textureId;
    public final ModelElementTexture textureData;

    public ModelElementFace(@Nullable Direction cullFace, int tintIndex, String textureId, ModelElementTexture textureData) {
        this.cullFace = cullFace;
        this.tintIndex = tintIndex;
        this.textureId = textureId;
        this.textureData = textureData;
    }

    public static class Deserializer
    implements JsonDeserializer<ModelElementFace> {
        protected Deserializer() {
        }

        public ModelElementFace deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Direction _snowman2 = this.deserializeCullFace(jsonObject);
            int _snowman3 = this.deserializeTintIndex(jsonObject);
            String _snowman4 = this.deserializeTexture(jsonObject);
            ModelElementTexture _snowman5 = (ModelElementTexture)jsonDeserializationContext.deserialize((JsonElement)jsonObject, ModelElementTexture.class);
            return new ModelElementFace(_snowman2, _snowman3, _snowman4, _snowman5);
        }

        protected int deserializeTintIndex(JsonObject object) {
            return JsonHelper.getInt(object, "tintindex", -1);
        }

        private String deserializeTexture(JsonObject object) {
            return JsonHelper.getString(object, "texture");
        }

        @Nullable
        private Direction deserializeCullFace(JsonObject object) {
            String string = JsonHelper.getString(object, "cullface", "");
            return Direction.byName(string);
        }

        public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(functionJson, unused, context);
        }
    }
}

