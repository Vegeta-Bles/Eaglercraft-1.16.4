package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public abstract class EnergySwirlOverlayFeatureRenderer<T extends Entity & SkinOverlayOwner, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
   public EnergySwirlOverlayFeatureRenderer(FeatureRendererContext<T, M> _snowman) {
      super(_snowman);
   }

   @Override
   public void render(
      MatrixStack matrices,
      VertexConsumerProvider vertexConsumers,
      int light,
      T entity,
      float limbAngle,
      float limbDistance,
      float tickDelta,
      float animationProgress,
      float headYaw,
      float headPitch
   ) {
      if (entity.shouldRenderOverlay()) {
         float _snowman = (float)entity.age + tickDelta;
         EntityModel<T> _snowmanx = this.getEnergySwirlModel();
         _snowmanx.animateModel(entity, limbAngle, limbDistance, tickDelta);
         this.getContextModel().copyStateTo(_snowmanx);
         VertexConsumer _snowmanxx = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(this.getEnergySwirlTexture(), this.getEnergySwirlX(_snowman), _snowman * 0.01F));
         _snowmanx.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
         _snowmanx.render(matrices, _snowmanxx, light, OverlayTexture.DEFAULT_UV, 0.5F, 0.5F, 0.5F, 1.0F);
      }
   }

   protected abstract float getEnergySwirlX(float partialAge);

   protected abstract Identifier getEnergySwirlTexture();

   protected abstract EntityModel<T> getEnergySwirlModel();
}
