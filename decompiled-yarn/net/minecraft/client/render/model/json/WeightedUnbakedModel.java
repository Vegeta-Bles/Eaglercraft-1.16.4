package net.minecraft.client.render.model.json;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.WeightedBakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class WeightedUnbakedModel implements UnbakedModel {
   private final List<ModelVariant> variants;

   public WeightedUnbakedModel(List<ModelVariant> variants) {
      this.variants = variants;
   }

   public List<ModelVariant> getVariants() {
      return this.variants;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o instanceof WeightedUnbakedModel) {
         WeightedUnbakedModel _snowman = (WeightedUnbakedModel)o;
         return this.variants.equals(_snowman.variants);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.variants.hashCode();
   }

   @Override
   public Collection<Identifier> getModelDependencies() {
      return this.getVariants().stream().map(ModelVariant::getLocation).collect(Collectors.toSet());
   }

   @Override
   public Collection<SpriteIdentifier> getTextureDependencies(
      Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences
   ) {
      return this.getVariants()
         .stream()
         .map(ModelVariant::getLocation)
         .distinct()
         .flatMap(_snowmanxx -> unbakedModelGetter.apply(_snowmanxx).getTextureDependencies(unbakedModelGetter, unresolvedTextureReferences).stream())
         .collect(Collectors.toSet());
   }

   @Nullable
   @Override
   public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {
      if (this.getVariants().isEmpty()) {
         return null;
      } else {
         WeightedBakedModel.Builder _snowman = new WeightedBakedModel.Builder();

         for (ModelVariant _snowmanx : this.getVariants()) {
            BakedModel _snowmanxx = loader.bake(_snowmanx.getLocation(), _snowmanx);
            _snowman.add(_snowmanxx, _snowmanx.getWeight());
         }

         return _snowman.getFirst();
      }
   }

   public static class Deserializer implements JsonDeserializer<WeightedUnbakedModel> {
      public Deserializer() {
      }

      public WeightedUnbakedModel deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         List<ModelVariant> _snowmanxxx = Lists.newArrayList();
         if (_snowman.isJsonArray()) {
            JsonArray _snowmanxxxx = _snowman.getAsJsonArray();
            if (_snowmanxxxx.size() == 0) {
               throw new JsonParseException("Empty variant array");
            }

            for (JsonElement _snowmanxxxxx : _snowmanxxxx) {
               _snowmanxxx.add((ModelVariant)_snowman.deserialize(_snowmanxxxxx, ModelVariant.class));
            }
         } else {
            _snowmanxxx.add((ModelVariant)_snowman.deserialize(_snowman, ModelVariant.class));
         }

         return new WeightedUnbakedModel(_snowmanxxx);
      }
   }
}
