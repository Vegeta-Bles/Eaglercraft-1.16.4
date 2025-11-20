package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.DolphinEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class DolphinHeldItemFeatureRenderer extends FeatureRenderer<DolphinEntity, DolphinEntityModel<DolphinEntity>> {
   public DolphinHeldItemFeatureRenderer(FeatureRendererContext<DolphinEntity, DolphinEntityModel<DolphinEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, DolphinEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      boolean _snowmanxxxxxxxxxx = _snowman.getMainArm() == Arm.RIGHT;
      _snowman.push();
      float _snowmanxxxxxxxxxxx = 1.0F;
      float _snowmanxxxxxxxxxxxx = -1.0F;
      float _snowmanxxxxxxxxxxxxx = MathHelper.abs(_snowman.pitch) / 60.0F;
      if (_snowman.pitch < 0.0F) {
         _snowman.translate(0.0, (double)(1.0F - _snowmanxxxxxxxxxxxxx * 0.5F), (double)(-1.0F + _snowmanxxxxxxxxxxxxx * 0.5F));
      } else {
         _snowman.translate(0.0, (double)(1.0F + _snowmanxxxxxxxxxxxxx * 0.8F), (double)(-1.0F + _snowmanxxxxxxxxxxxxx * 0.2F));
      }

      ItemStack _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx ? _snowman.getMainHandStack() : _snowman.getOffHandStack();
      MinecraftClient.getInstance().getHeldItemRenderer().renderItem(_snowman, _snowmanxxxxxxxxxxxxxx, ModelTransformation.Mode.GROUND, false, _snowman, _snowman, _snowman);
      _snowman.pop();
   }
}
