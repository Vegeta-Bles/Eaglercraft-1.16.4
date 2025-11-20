package net.minecraft.client.render.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class ShulkerBoxBlockEntityRenderer extends BlockEntityRenderer<ShulkerBoxBlockEntity> {
   private final ShulkerEntityModel<?> model;

   public ShulkerBoxBlockEntityRenderer(ShulkerEntityModel<?> arg, BlockEntityRenderDispatcher arg2) {
      super(arg2);
      this.model = arg;
   }

   public void render(ShulkerBoxBlockEntity arg, float f, MatrixStack arg2, VertexConsumerProvider arg3, int i, int j) {
      Direction lv = Direction.UP;
      if (arg.hasWorld()) {
         BlockState lv2 = arg.getWorld().getBlockState(arg.getPos());
         if (lv2.getBlock() instanceof ShulkerBoxBlock) {
            lv = lv2.get(ShulkerBoxBlock.FACING);
         }
      }

      DyeColor lv3 = arg.getColor();
      SpriteIdentifier lv4;
      if (lv3 == null) {
         lv4 = TexturedRenderLayers.SHULKER_TEXTURE_ID;
      } else {
         lv4 = TexturedRenderLayers.COLORED_SHULKER_BOXES_TEXTURES.get(lv3.getId());
      }

      arg2.push();
      arg2.translate(0.5, 0.5, 0.5);
      float g = 0.9995F;
      arg2.scale(0.9995F, 0.9995F, 0.9995F);
      arg2.multiply(lv.getRotationQuaternion());
      arg2.scale(1.0F, -1.0F, -1.0F);
      arg2.translate(0.0, -1.0, 0.0);
      VertexConsumer lv6 = lv4.getVertexConsumer(arg3, RenderLayer::getEntityCutoutNoCull);
      this.model.getBottomShell().render(arg2, lv6, i, j);
      arg2.translate(0.0, (double)(-arg.getAnimationProgress(f) * 0.5F), 0.0);
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(270.0F * arg.getAnimationProgress(f)));
      this.model.getTopShell().render(arg2, lv6, i, j);
      arg2.pop();
   }
}
