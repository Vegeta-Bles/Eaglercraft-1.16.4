package net.minecraft.client.render.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.ModelVariantMap;
import net.minecraft.client.render.model.json.MultipartModelComponent;
import net.minecraft.client.render.model.json.WeightedUnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.state.StateManager;
import net.minecraft.util.Identifier;

public class MultipartUnbakedModel implements UnbakedModel {
   private final StateManager<Block, BlockState> stateFactory;
   private final List<MultipartModelComponent> components;

   public MultipartUnbakedModel(StateManager<Block, BlockState> stateFactory, List<MultipartModelComponent> components) {
      this.stateFactory = stateFactory;
      this.components = components;
   }

   public List<MultipartModelComponent> getComponents() {
      return this.components;
   }

   public Set<WeightedUnbakedModel> getModels() {
      Set<WeightedUnbakedModel> _snowman = Sets.newHashSet();

      for (MultipartModelComponent _snowmanx : this.components) {
         _snowman.add(_snowmanx.getModel());
      }

      return _snowman;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof MultipartUnbakedModel)) {
         return false;
      } else {
         MultipartUnbakedModel _snowman = (MultipartUnbakedModel)o;
         return Objects.equals(this.stateFactory, _snowman.stateFactory) && Objects.equals(this.components, _snowman.components);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.stateFactory, this.components);
   }

   @Override
   public Collection<Identifier> getModelDependencies() {
      return this.getComponents().stream().flatMap(_snowman -> _snowman.getModel().getModelDependencies().stream()).collect(Collectors.toSet());
   }

   @Override
   public Collection<SpriteIdentifier> getTextureDependencies(
      Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences
   ) {
      return this.getComponents()
         .stream()
         .flatMap(_snowmanxx -> _snowmanxx.getModel().getTextureDependencies(unbakedModelGetter, unresolvedTextureReferences).stream())
         .collect(Collectors.toSet());
   }

   @Nullable
   @Override
   public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
      MultipartBakedModel.Builder _snowman = new MultipartBakedModel.Builder();

      for (MultipartModelComponent _snowmanx : this.getComponents()) {
         BakedModel _snowmanxx = _snowmanx.getModel().bake(loader, textureGetter, rotationContainer, modelId);
         if (_snowmanxx != null) {
            _snowman.addComponent(_snowmanx.getPredicate(this.stateFactory), _snowmanxx);
         }
      }

      return _snowman.build();
   }

   public static class Deserializer implements JsonDeserializer<MultipartUnbakedModel> {
      private final ModelVariantMap.DeserializationContext context;

      public Deserializer(ModelVariantMap.DeserializationContext context) {
         this.context = context;
      }

      public MultipartUnbakedModel deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         return new MultipartUnbakedModel(this.context.getStateFactory(), this.deserializeComponents(_snowman, _snowman.getAsJsonArray()));
      }

      private List<MultipartModelComponent> deserializeComponents(JsonDeserializationContext context, JsonArray array) {
         List<MultipartModelComponent> _snowman = Lists.newArrayList();

         for (JsonElement _snowmanx : array) {
            _snowman.add((MultipartModelComponent)context.deserialize(_snowmanx, MultipartModelComponent.class));
         }

         return _snowman;
      }
   }
}
