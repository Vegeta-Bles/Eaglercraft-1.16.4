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
      Item _snowman = stack.getItem();

      for (Entry<Identifier, Float> _snowmanx : this.predicateToThresholds.entrySet()) {
         ModelPredicateProvider _snowmanxx = ModelPredicateProviderRegistry.get(_snowman, _snowmanx.getKey());
         if (_snowmanxx == null || _snowmanxx.call(stack, world, entity) < _snowmanx.getValue()) {
            return false;
         }
      }

      return true;
   }

   public static class Deserializer implements JsonDeserializer<ModelOverride> {
      protected Deserializer() {
      }

      public ModelOverride deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         Identifier _snowmanxxxx = new Identifier(JsonHelper.getString(_snowmanxxx, "model"));
         Map<Identifier, Float> _snowmanxxxxx = this.deserializeMinPropertyValues(_snowmanxxx);
         return new ModelOverride(_snowmanxxxx, _snowmanxxxxx);
      }

      protected Map<Identifier, Float> deserializeMinPropertyValues(JsonObject object) {
         Map<Identifier, Float> _snowman = Maps.newLinkedHashMap();
         JsonObject _snowmanx = JsonHelper.getObject(object, "predicate");

         for (Entry<String, JsonElement> _snowmanxx : _snowmanx.entrySet()) {
            _snowman.put(new Identifier(_snowmanxx.getKey()), JsonHelper.asFloat(_snowmanxx.getValue(), _snowmanxx.getKey()));
         }

         return _snowman;
      }
   }
}
