/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.model.json;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class ModelOverride {
    private final Identifier modelId;
    private final Map<Identifier, Float> predicateToThresholds;

    public ModelOverride(Identifier modelId, Map<Identifier, Float> predicateToThresholds) {
        this.modelId = modelId;
        this.predicateToThresholds = predicateToThresholds;
    }

    public Identifier getModelId() {
        return this.modelId;
    }

    boolean matches(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
        Item item = stack.getItem();
        for (Map.Entry<Identifier, Float> entry : this.predicateToThresholds.entrySet()) {
            ModelPredicateProvider modelPredicateProvider = ModelPredicateProviderRegistry.get(item, entry.getKey());
            if (modelPredicateProvider != null && !(modelPredicateProvider.call(stack, world, entity) < entry.getValue().floatValue())) continue;
            return false;
        }
        return true;
    }

    public static class Deserializer
    implements JsonDeserializer<ModelOverride> {
        protected Deserializer() {
        }

        public ModelOverride deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Identifier _snowman2 = new Identifier(JsonHelper.getString(jsonObject, "model"));
            Map<Identifier, Float> _snowman3 = this.deserializeMinPropertyValues(jsonObject);
            return new ModelOverride(_snowman2, _snowman3);
        }

        protected Map<Identifier, Float> deserializeMinPropertyValues(JsonObject object) {
            LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
            JsonObject _snowman2 = JsonHelper.getObject(object, "predicate");
            for (Map.Entry entry : _snowman2.entrySet()) {
                linkedHashMap.put(new Identifier((String)entry.getKey()), Float.valueOf(JsonHelper.asFloat((JsonElement)entry.getValue(), (String)entry.getKey())));
            }
            return linkedHashMap;
        }

        public /* synthetic */ Object deserialize(JsonElement functionJson, Type unused, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(functionJson, unused, context);
        }
    }
}

