package net.minecraft.client.render.model.json;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.MultipartUnbakedModel;
import net.minecraft.state.StateManager;
import net.minecraft.util.JsonHelper;

@Environment(EnvType.CLIENT)
public class ModelVariantMap {
   private final Map<String, WeightedUnbakedModel> variantMap = Maps.newLinkedHashMap();
   private MultipartUnbakedModel multipartModel;

   public static ModelVariantMap deserialize(ModelVariantMap.DeserializationContext context, Reader reader) {
      return JsonHelper.deserialize(context.gson, reader, ModelVariantMap.class);
   }

   public ModelVariantMap(Map<String, WeightedUnbakedModel> variantMap, MultipartUnbakedModel multipartModel) {
      this.multipartModel = multipartModel;
      this.variantMap.putAll(variantMap);
   }

   public ModelVariantMap(List<ModelVariantMap> variantMapList) {
      ModelVariantMap lv = null;

      for (ModelVariantMap lv2 : variantMapList) {
         if (lv2.hasMultipartModel()) {
            this.variantMap.clear();
            lv = lv2;
         }

         this.variantMap.putAll(lv2.variantMap);
      }

      if (lv != null) {
         this.multipartModel = lv.multipartModel;
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         if (o instanceof ModelVariantMap) {
            ModelVariantMap lv = (ModelVariantMap)o;
            if (this.variantMap.equals(lv.variantMap)) {
               return this.hasMultipartModel() ? this.multipartModel.equals(lv.multipartModel) : !lv.hasMultipartModel();
            }
         }

         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.variantMap.hashCode() + (this.hasMultipartModel() ? this.multipartModel.hashCode() : 0);
   }

   public Map<String, WeightedUnbakedModel> getVariantMap() {
      return this.variantMap;
   }

   public boolean hasMultipartModel() {
      return this.multipartModel != null;
   }

   public MultipartUnbakedModel getMultipartModel() {
      return this.multipartModel;
   }

   @Environment(EnvType.CLIENT)
   public static final class DeserializationContext {
      protected final Gson gson = new GsonBuilder()
         .registerTypeAdapter(ModelVariantMap.class, new ModelVariantMap.Deserializer())
         .registerTypeAdapter(ModelVariant.class, new ModelVariant.Deserializer())
         .registerTypeAdapter(WeightedUnbakedModel.class, new WeightedUnbakedModel.Deserializer())
         .registerTypeAdapter(MultipartUnbakedModel.class, new MultipartUnbakedModel.Deserializer(this))
         .registerTypeAdapter(MultipartModelComponent.class, new MultipartModelComponent.Deserializer())
         .create();
      private StateManager<Block, BlockState> stateFactory;

      public DeserializationContext() {
      }

      public StateManager<Block, BlockState> getStateFactory() {
         return this.stateFactory;
      }

      public void setStateFactory(StateManager<Block, BlockState> stateFactory) {
         this.stateFactory = stateFactory;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class Deserializer implements JsonDeserializer<ModelVariantMap> {
      public Deserializer() {
      }

      public ModelVariantMap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         JsonObject jsonObject = jsonElement.getAsJsonObject();
         Map<String, WeightedUnbakedModel> map = this.deserializeVariants(jsonDeserializationContext, jsonObject);
         MultipartUnbakedModel lv = this.deserializeMultipart(jsonDeserializationContext, jsonObject);
         if (!map.isEmpty() || lv != null && !lv.getModels().isEmpty()) {
            return new ModelVariantMap(map, lv);
         } else {
            throw new JsonParseException("Neither 'variants' nor 'multipart' found");
         }
      }

      protected Map<String, WeightedUnbakedModel> deserializeVariants(JsonDeserializationContext context, JsonObject object) {
         Map<String, WeightedUnbakedModel> map = Maps.newHashMap();
         if (object.has("variants")) {
            JsonObject jsonObject2 = JsonHelper.getObject(object, "variants");

            for (Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
               map.put(entry.getKey(), (WeightedUnbakedModel)context.deserialize(entry.getValue(), WeightedUnbakedModel.class));
            }
         }

         return map;
      }

      @Nullable
      protected MultipartUnbakedModel deserializeMultipart(JsonDeserializationContext context, JsonObject object) {
         if (!object.has("multipart")) {
            return null;
         } else {
            JsonArray jsonArray = JsonHelper.getArray(object, "multipart");
            return (MultipartUnbakedModel)context.deserialize(jsonArray, MultipartUnbakedModel.class);
         }
      }
   }
}
