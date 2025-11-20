package net.minecraft.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.ShulkerEntityRenderer;
import net.minecraft.client.render.entity.model.ShulkerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

@Environment(EnvType.CLIENT)
public class ShulkerHeadFeatureRenderer extends FeatureRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {
   public ShulkerHeadFeatureRenderer(FeatureRendererContext<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> arg) {
      super(arg);
   }

   public void render(MatrixStack arg, VertexConsumerProvider arg2, int i, ShulkerEntity arg3, float f, float g, float h, float j, float k, float l) {
      arg.push();
      arg.translate(0.0, 1.0, 0.0);
      arg.scale(-1.0F, -1.0F, 1.0F);
      Quaternion lv = arg3.getAttachedFace().getOpposite().getRotationQuaternion();
      lv.conjugate();
      arg.multiply(lv);
      arg.scale(-1.0F, -1.0F, 1.0F);
      arg.translate(0.0, -1.0, 0.0);
      DyeColor lv2 = arg3.getColor();
      Identifier lv3 = lv2 == null ? ShulkerEntityRenderer.TEXTURE : ShulkerEntityRenderer.COLORED_TEXTURES[lv2.getId()];
      VertexConsumer lv4 = arg2.getBuffer(RenderLayer.getEntitySolid(lv3));
      this.getContextModel().getHead().render(arg, lv4, i, LivingEntityRenderer.getOverlay(arg3, 0.0F));
      arg.pop();
   }
}
