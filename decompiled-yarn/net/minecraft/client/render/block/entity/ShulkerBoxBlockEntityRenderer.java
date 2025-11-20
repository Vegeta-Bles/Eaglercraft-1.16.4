package net.minecraft.client.render.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Direction;

public class ShulkerBoxBlockEntityRenderer extends BlockEntityRenderer<ShulkerBoxBlockEntity> {
   private final ShulkerEntityModel<?> model;

   public ShulkerBoxBlockEntityRenderer(ShulkerEntityModel<?> _snowman, BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
      this.model = _snowman;
   }

   public void render(ShulkerBoxBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      Direction _snowmanxxxxxx = Direction.UP;
      if (_snowman.hasWorld()) {
         BlockState _snowmanxxxxxxx = _snowman.getWorld().getBlockState(_snowman.getPos());
         if (_snowmanxxxxxxx.getBlock() instanceof ShulkerBoxBlock) {
            _snowmanxxxxxx = _snowmanxxxxxxx.get(ShulkerBoxBlock.FACING);
         }
      }

      DyeColor _snowmanxxxxxxx = _snowman.getColor();
      SpriteIdentifier _snowmanxxxxxxxx;
      if (_snowmanxxxxxxx == null) {
         _snowmanxxxxxxxx = TexturedRenderLayers.SHULKER_TEXTURE_ID;
      } else {
         _snowmanxxxxxxxx = TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(_snowmanxxxxxxx.getId());
      }

      _snowman.push();
      _snowman.translate(0.5, 0.5, 0.5);
      float _snowmanxxxxxxxxx = 0.9995F;
      _snowman.scale(0.9995F, 0.9995F, 0.9995F);
      _snowman.multiply(_snowmanxxxxxx.getRotationQuaternion());
      _snowman.scale(1.0F, -1.0F, -1.0F);
      _snowman.translate(0.0, -1.0, 0.0);
      VertexConsumer _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getVertexConsumer(_snowman, RenderLayer::getEntityCutoutNoCull);
      this.model.getBottomShell().render(_snowman, _snowmanxxxxxxxxxx, _snowman, _snowman);
      _snowman.translate(0.0, (double)(-_snowman.getAnimationProgress(_snowman) * 0.5F), 0.0);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270.0F * _snowman.getAnimationProgress(_snowman)));
      this.model.getTopShell().render(_snowman, _snowmanxxxxxxxxxx, _snowman, _snowman);
      _snowman.pop();
   }
}
