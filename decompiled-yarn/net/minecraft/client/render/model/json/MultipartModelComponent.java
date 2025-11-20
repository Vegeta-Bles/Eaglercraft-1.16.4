package net.minecraft.client.render.model.json;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Streams;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.util.JsonHelper;

public class MultipartModelComponent {
   private final MultipartModelSelector selector;
   private final WeightedUnbakedModel model;

   public MultipartModelComponent(MultipartModelSelector selector, WeightedUnbakedModel model) {
      if (selector == null) {
         throw new IllegalArgumentException("Missing condition for selector");
      } else if (model == null) {
         throw new IllegalArgumentException("Missing variant for selector");
      } else {
         this.selector = selector;
         this.model = model;
      }
   }

   public WeightedUnbakedModel getModel() {
      return this.model;
   }

   public Predicate<BlockState> getPredicate(StateManager<Block, BlockState> stateFactory) {
      return this.selector.getPredicate(stateFactory);
   }

   @Override
   public boolean equals(Object o) {
      return this == o;
   }

   @Override
   public int hashCode() {
      return System.identityHashCode(this);
   }

   public static class Deserializer implements JsonDeserializer<MultipartModelComponent> {
      public Deserializer() {
      }

      public MultipartModelComponent deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         JsonObject _snowmanxxx = _snowman.getAsJsonObject();
         return new MultipartModelComponent(
            this.deserializeSelectorOrDefault(_snowmanxxx), (WeightedUnbakedModel)_snowman.deserialize(_snowmanxxx.get("apply"), WeightedUnbakedModel.class)
         );
      }

      private MultipartModelSelector deserializeSelectorOrDefault(JsonObject object) {
         return object.has("when") ? deserializeSelector(JsonHelper.getObject(object, "when")) : MultipartModelSelector.TRUE;
      }

      @VisibleForTesting
      static MultipartModelSelector deserializeSelector(JsonObject object) {
         Set<Entry<String, JsonElement>> _snowman = object.entrySet();
         if (_snowman.isEmpty()) {
            throw new JsonParseException("No elements found in selector");
         } else if (_snowman.size() == 1) {
            if (object.has("OR")) {
               List<MultipartModelSelector> _snowmanx = Streams.stream(JsonHelper.getArray(object, "OR"))
                  .map(_snowmanxx -> deserializeSelector(_snowmanxx.getAsJsonObject()))
                  .collect(Collectors.toList());
               return new OrMultipartModelSelector(_snowmanx);
            } else if (object.has("AND")) {
               List<MultipartModelSelector> _snowmanx = Streams.stream(JsonHelper.getArray(object, "AND"))
                  .map(_snowmanxx -> deserializeSelector(_snowmanxx.getAsJsonObject()))
                  .collect(Collectors.toList());
               return new AndMultipartModelSelector(_snowmanx);
            } else {
               return createStatePropertySelector(_snowman.iterator().next());
            }
         } else {
            return new AndMultipartModelSelector(_snowman.stream().map(MultipartModelComponent.Deserializer::createStatePropertySelector).collect(Collectors.toList()));
         }
      }

      private static MultipartModelSelector createStatePropertySelector(Entry<String, JsonElement> entry) {
         return new SimpleMultipartModelSelector(entry.getKey(), entry.getValue().getAsString());
      }
   }
}
