package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerClothingFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.util.Identifier;

public class VillagerEntityRenderer extends MobEntityRenderer<VillagerEntity, VillagerResemblingModel<VillagerEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/villager/villager.png");

   public VillagerEntityRenderer(EntityRenderDispatcher dispatcher, ReloadableResourceManager _snowman) {
      super(dispatcher, new VillagerResemblingModel<>(0.0F), 0.5F);
      this.addFeature(new HeadFeatureRenderer<>(this));
      this.addFeature(new VillagerClothingFeatureRenderer<>(this, _snowman, "villager"));
      this.addFeature(new VillagerHeldItemFeatureRenderer<>(this));
   }

   public Identifier getTexture(VillagerEntity _snowman) {
      return TEXTURE;
   }

   protected void scale(VillagerEntity _snowman, MatrixStack _snowman, float _snowman) {
      float _snowmanxxx = 0.9375F;
      if (_snowman.isBaby()) {
         _snowmanxxx = (float)((double)_snowmanxxx * 0.5);
         this.shadowRadius = 0.25F;
      } else {
         this.shadowRadius = 0.5F;
      }

      _snowman.scale(_snowmanxxx, _snowmanxxx, _snowmanxxx);
   }
}
