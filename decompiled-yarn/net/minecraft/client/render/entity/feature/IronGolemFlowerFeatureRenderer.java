package net.minecraft.client.render.entity.feature;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.IronGolemEntity;

public class IronGolemFlowerFeatureRenderer extends FeatureRenderer<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> {
   public IronGolemFlowerFeatureRenderer(FeatureRendererContext<IronGolemEntity, IronGolemEntityModel<IronGolemEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, IronGolemEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (_snowman.getLookingAtVillagerTicks() != 0) {
         _snowman.push();
         ModelPart _snowmanxxxxxxxxxx = this.getContextModel().getRightArm();
         _snowmanxxxxxxxxxx.rotate(_snowman);
         _snowman.translate(-1.1875, 1.0625, -0.9375);
         _snowman.translate(0.5, 0.5, 0.5);
         float _snowmanxxxxxxxxxxx = 0.5F;
         _snowman.scale(0.5F, 0.5F, 0.5F);
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(-90.0F));
         _snowman.translate(-0.5, -0.5, -0.5);
         MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(Blocks.POPPY.getDefaultState(), _snowman, _snowman, _snowman, OverlayTexture.DEFAULT_UV);
         _snowman.pop();
      }
   }
}
