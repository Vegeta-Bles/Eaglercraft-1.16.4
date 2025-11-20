package net.minecraft.client.render.entity;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.util.math.MathHelper;

public class TntMinecartEntityRenderer extends MinecartEntityRenderer<TntMinecartEntity> {
   public TntMinecartEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   protected void renderBlock(TntMinecartEntity _snowman, float _snowman, BlockState _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      int _snowmanxxxxxx = _snowman.getFuseTicks();
      if (_snowmanxxxxxx > -1 && (float)_snowmanxxxxxx - _snowman + 1.0F < 10.0F) {
         float _snowmanxxxxxxx = 1.0F - ((float)_snowmanxxxxxx - _snowman + 1.0F) / 10.0F;
         _snowmanxxxxxxx = MathHelper.clamp(_snowmanxxxxxxx, 0.0F, 1.0F);
         _snowmanxxxxxxx *= _snowmanxxxxxxx;
         _snowmanxxxxxxx *= _snowmanxxxxxxx;
         float _snowmanxxxxxxxx = 1.0F + _snowmanxxxxxxx * 0.3F;
         _snowman.scale(_snowmanxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxx);
      }

      renderFlashingBlock(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxx > -1 && _snowmanxxxxxx / 5 % 2 == 0);
   }

   public static void renderFlashingBlock(BlockState blockState, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, boolean drawFlash) {
      int _snowman;
      if (drawFlash) {
         _snowman = OverlayTexture.packUv(OverlayTexture.getU(1.0F), 10);
      } else {
         _snowman = OverlayTexture.DEFAULT_UV;
      }

      MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(blockState, matrices, vertexConsumers, light, _snowman);
   }
}
