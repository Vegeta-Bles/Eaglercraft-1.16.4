package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WanderingTraderEntityRenderer extends MobEntityRenderer<WanderingTraderEntity, VillagerResemblingModel<WanderingTraderEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/wandering_trader.png");

   public WanderingTraderEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new VillagerResemblingModel<>(0.0F), 0.5F);
      this.addFeature(new HeadFeatureRenderer<>(this));
      this.addFeature(new VillagerHeldItemFeatureRenderer<>(this));
   }

   public Identifier getTexture(WanderingTraderEntity arg) {
      return TEXTURE;
   }

   protected void scale(WanderingTraderEntity arg, MatrixStack arg2, float f) {
      float g = 0.9375F;
      arg2.scale(0.9375F, 0.9375F, 0.9375F);
   }
}
