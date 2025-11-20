package net.minecraft.client.render.model.json;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

@Environment(EnvType.CLIENT)
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
      Item lv = stack.getItem();

      for (Entry<Identifier, Float> entry : this.predicateToThresholds.entrySet()) {
         ModelPredicateProvider lv2 = ModelPredicateProviderRegistry.get(lv, entry.getKey());
         if (lv2 == null || lv2.call(stack, world, entity) < entry.getValue()) {
            return false;
         }
      }

      return true;
   }

   @Environment(EnvType.CLIENT)
   public static class Deserializer implements JsonDeserializer<ModelOverride> {
      protected Deserializer() {
      }

      public ModelOverride deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject jsonObject = jsonElement.getAsJsonObject();
         Identifier lv = new Identifier(JsonHelper.getString(jsonObject, "model"));
         Map<Identifier, Float> map = this.deserializeMinPropertyValues(jsonObject);
         return new ModelOverride(lv, map);
      }

      protected Map<Identifier, Float> deserializeMinPropertyValues(JsonObject object) {
         Map<Identifier, Float> map = Maps.newLinkedHashMap();
         JsonObject jsonObject2 = JsonHelper.getObject(object, "predicate");

         for (Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
            map.put(new Identifier(entry.getKey()), JsonHelper.asFloat(entry.getValue(), entry.getKey()));
         }

         return map;
      }
   }
}
