package net.minecraft.client.render.entity.feature;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EndermanEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.mob.EndermanEntity;

public class EndermanBlockFeatureRenderer extends FeatureRenderer<EndermanEntity, EndermanEntityModel<EndermanEntity>> {
   public EndermanBlockFeatureRenderer(FeatureRendererContext<EndermanEntity, EndermanEntityModel<EndermanEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, EndermanEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      BlockState _snowmanxxxxxxxxxx = _snowman.getCarriedBlock();
      if (_snowmanxxxxxxxxxx != null) {
         _snowman.push();
         _snowman.translate(0.0, 0.6875, -0.75);
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(20.0F));
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(45.0F));
         _snowman.translate(0.25, 0.1875, 0.25);
         float _snowmanxxxxxxxxxxx = 0.5F;
         _snowman.scale(-0.5F, -0.5F, 0.5F);
         _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
         MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(_snowmanxxxxxxxxxx, _snowman, _snowman, _snowman, OverlayTexture.DEFAULT_UV);
         _snowman.pop();
      }
   }
}
