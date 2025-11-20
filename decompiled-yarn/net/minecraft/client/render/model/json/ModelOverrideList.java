package net.minecraft.client.render.model.json;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModelOverrideList {
   public static final ModelOverrideList EMPTY = new ModelOverrideList();
   private final List<ModelOverride> overrides = Lists.newArrayList();
   private final List<BakedModel> models;

   private ModelOverrideList() {
      this.models = Collections.emptyList();
   }

   public ModelOverrideList(
      ModelLoader modelLoader, JsonUnbakedModel unbakedModel, Function<Identifier, UnbakedModel> unbakedModelGetter, List<ModelOverride> overrides
   ) {
      this.models = overrides.stream().map(_snowmanxxx -> {
         UnbakedModel _snowmanxxxx = unbakedModelGetter.apply(_snowmanxxx.getModelId());
         return Objects.equals(_snowmanxxxx, unbakedModel) ? null : modelLoader.bake(_snowmanxxx.getModelId(), net.minecraft.client.render.model.ModelRotation.X0_Y0);
      }).collect(Collectors.toList());
      Collections.reverse(this.models);

      for (int _snowman = overrides.size() - 1; _snowman >= 0; _snowman--) {
         this.overrides.add(overrides.get(_snowman));
      }
   }

   @Nullable
   public BakedModel apply(BakedModel model, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
      if (!this.overrides.isEmpty()) {
         for (int _snowman = 0; _snowman < this.overrides.size(); _snowman++) {
            ModelOverride _snowmanx = this.overrides.get(_snowman);
            if (_snowmanx.matches(stack, world, entity)) {
               BakedModel _snowmanxx = this.models.get(_snowman);
               if (_snowmanxx == null) {
                  return model;
               }

               return _snowmanxx;
            }
         }
      }

      return model;
   }
}
