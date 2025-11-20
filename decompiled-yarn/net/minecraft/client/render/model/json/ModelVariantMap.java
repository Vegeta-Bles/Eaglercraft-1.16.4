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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.MultipartUnbakedModel;
import net.minecraft.state.StateManager;
import net.minecraft.util.JsonHelper;

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
      ModelVariantMap _snowman = null;

      for (ModelVariantMap _snowmanx : variantMapList) {
         if (_snowmanx.hasMultipartModel()) {
            this.variantMap.clear();
            _snowman = _snowmanx;
         }

         this.variantMap.putAll(_snowmanx.variantMap);
      }

      if (_snowman != null) {
         this.multipartModel = _snowman.multipartModel;
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         if (o instanceof ModelVariantMap) {
            ModelVariantMap _snowman = (ModelVariantMap)o;
            if (this.variantMap.equals(_snowman.variantMap)) {
               return this.hasMultipartModel() ? this.multipartModel.equals(_snowman.multipartModel) : !_snowman.hasMultipartModel();
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

   public static class Deserializer implements JsonDeserializer<ModelVariantMap> {
      public Deserializer() {
      }

      public ModelVariantMap deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         Map<String, WeightedUnbakedModel> _snowmanxxxx = this.deserializeVariants(_snowman, _snowmanxxx);
         MultipartUnbakedModel _snowmanxxxxx = this.deserializeMultipart(_snowman, _snowmanxxx);
         if (!_snowmanxxxx.isEmpty() || _snowmanxxxxx != null && !_snowmanxxxxx.getModels().isEmpty()) {
            return new ModelVariantMap(_snowmanxxxx, _snowmanxxxxx);
         } else {
            throw new JsonParseException("Neither 'variants' nor 'multipart' found");
         }
      }

      protected Map<String, WeightedUnbakedModel> deserializeVariants(JsonDeserializationContext context, JsonObject object) {
         Map<String, WeightedUnbakedModel> _snowman = Maps.newHashMap();
         if (object.has("variants")) {
            JsonObject _snowmanx = JsonHelper.getObject(object, "variants");

            for (Entry<String, JsonElement> _snowmanxx : _snowmanx.entrySet()) {
               _snowman.put(_snowmanxx.getKey(), (WeightedUnbakedModel)context.deserialize(_snowmanxx.getValue(), WeightedUnbakedModel.class));
            }
         }

         return _snowman;
      }

      @Nullable
      protected MultipartUnbakedModel deserializeMultipart(JsonDeserializationContext context, JsonObject object) {
         if (!object.has("multipart")) {
            return null;
         } else {
            JsonArray _snowman = JsonHelper.getArray(object, "multipart");
            return (MultipartUnbakedModel)context.deserialize(_snowman, MultipartUnbakedModel.class);
         }
      }
   }
}
