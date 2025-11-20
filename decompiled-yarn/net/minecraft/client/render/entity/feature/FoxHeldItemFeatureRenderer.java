package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.item.ItemStack;

public class FoxHeldItemFeatureRenderer extends FeatureRenderer<FoxEntity, FoxEntityModel<FoxEntity>> {
   public FoxHeldItemFeatureRenderer(FeatureRendererContext<FoxEntity, FoxEntityModel<FoxEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, FoxEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      boolean _snowmanxxxxxxxxxx = _snowman.isSleeping();
      boolean _snowmanxxxxxxxxxxx = _snowman.isBaby();
      _snowman.push();
      if (_snowmanxxxxxxxxxxx) {
         float _snowmanxxxxxxxxxxxx = 0.75F;
         _snowman.scale(0.75F, 0.75F, 0.75F);
         _snowman.translate(0.0, 0.5, 0.209375F);
      }

      _snowman.translate(
         (double)(this.getContextModel().head.pivotX / 16.0F),
         (double)(this.getContextModel().head.pivotY / 16.0F),
         (double)(this.getContextModel().head.pivotZ / 16.0F)
      );
      float _snowmanxxxxxxxxxxxx = _snowman.getHeadRoll(_snowman);
      _snowman.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(_snowmanxxxxxxxxxxxx));
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowman));
      _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman));
      if (_snowman.isBaby()) {
         if (_snowmanxxxxxxxxxx) {
            _snowman.translate(0.4F, 0.26F, 0.15F);
         } else {
            _snowman.translate(0.06F, 0.26F, -0.5);
         }
      } else if (_snowmanxxxxxxxxxx) {
         _snowman.translate(0.46F, 0.26F, 0.22F);
      } else {
         _snowman.translate(0.06F, 0.27F, -0.5);
      }

      _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
      if (_snowmanxxxxxxxxxx) {
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
      }

      ItemStack _snowmanxxxxxxxxxxxxx = _snowman.getEquippedStack(EquipmentSlot.MAINHAND);
      MinecraftClient.getInstance().getHeldItemRenderer().renderItem(_snowman, _snowmanxxxxxxxxxxxxx, ModelTransformation.Mode.GROUND, false, _snowman, _snowman, _snowman);
      _snowman.pop();
   }
}
