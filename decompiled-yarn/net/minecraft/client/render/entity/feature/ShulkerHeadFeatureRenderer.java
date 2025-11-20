package net.minecraft.client.render.entity.feature;

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

public class ShulkerHeadFeatureRenderer extends FeatureRenderer<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> {
   public ShulkerHeadFeatureRenderer(FeatureRendererContext<ShulkerEntity, ShulkerEntityModel<ShulkerEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, ShulkerEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      _snowman.push();
      _snowman.translate(0.0, 1.0, 0.0);
      _snowman.scale(-1.0F, -1.0F, 1.0F);
      Quaternion _snowmanxxxxxxxxxx = _snowman.getAttachedFace().getOpposite().getRotationQuaternion();
      _snowmanxxxxxxxxxx.conjugate();
      _snowman.multiply(_snowmanxxxxxxxxxx);
      _snowman.scale(-1.0F, -1.0F, 1.0F);
      _snowman.translate(0.0, -1.0, 0.0);
      DyeColor _snowmanxxxxxxxxxxx = _snowman.getColor();
      Identifier _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx == null ? ShulkerEntityRenderer.TEXTURE : ShulkerEntityRenderer.COLORED_TEXTURES[_snowmanxxxxxxxxxxx.getId()];
      VertexConsumer _snowmanxxxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntitySolid(_snowmanxxxxxxxxxxxx));
      this.getContextModel().getHead().render(_snowman, _snowmanxxxxxxxxxxxxx, _snowman, LivingEntityRenderer.getOverlay(_snowman, 0.0F));
      _snowman.pop();
   }
}
