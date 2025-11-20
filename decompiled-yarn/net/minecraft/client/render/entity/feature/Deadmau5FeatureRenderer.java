package net.minecraft.client.render.entity.feature;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;

public class Deadmau5FeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
   public Deadmau5FeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, AbstractClientPlayerEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if ("deadmau5".equals(_snowman.getName().getString()) && _snowman.hasSkinTexture() && !_snowman.isInvisible()) {
         VertexConsumer _snowmanxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntitySolid(_snowman.getSkinTexture()));
         int _snowmanxxxxxxxxxxx = LivingEntityRenderer.getOverlay(_snowman, 0.0F);

         for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxx++) {
            float _snowmanxxxxxxxxxxxxx = MathHelper.lerp(_snowman, _snowman.prevYaw, _snowman.yaw) - MathHelper.lerp(_snowman, _snowman.prevBodyYaw, _snowman.bodyYaw);
            float _snowmanxxxxxxxxxxxxxx = MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch);
            _snowman.push();
            _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxxxxxxx));
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxxxxxxxxxxx));
            _snowman.translate((double)(0.375F * (float)(_snowmanxxxxxxxxxxxx * 2 - 1)), 0.0, 0.0);
            _snowman.translate(0.0, -0.375, 0.0);
            _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-_snowmanxxxxxxxxxxxxxx));
            _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-_snowmanxxxxxxxxxxxxx));
            float _snowmanxxxxxxxxxxxxxxx = 1.3333334F;
            _snowman.scale(1.3333334F, 1.3333334F, 1.3333334F);
            this.getContextModel().renderEars(_snowman, _snowmanxxxxxxxxxx, _snowman, _snowmanxxxxxxxxxxx);
            _snowman.pop();
         }
      }
   }
}
