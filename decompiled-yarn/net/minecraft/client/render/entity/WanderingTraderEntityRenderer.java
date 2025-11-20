package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.util.Identifier;

public class WanderingTraderEntityRenderer extends MobEntityRenderer<WanderingTraderEntity, VillagerResemblingModel<WanderingTraderEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/wandering_trader.png");

   public WanderingTraderEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new VillagerResemblingModel<>(0.0F), 0.5F);
      this.addFeature(new HeadFeatureRenderer<>(this));
      this.addFeature(new VillagerHeldItemFeatureRenderer<>(this));
   }

   public Identifier getTexture(WanderingTraderEntity _snowman) {
      return TEXTURE;
   }

   protected void scale(WanderingTraderEntity _snowman, MatrixStack _snowman, float _snowman) {
      float _snowmanxxx = 0.9375F;
      _snowman.scale(0.9375F, 0.9375F, 0.9375F);
   }
}
