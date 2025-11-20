package net.minecraft.client.render.entity.feature;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PandaEntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class PandaHeldItemFeatureRenderer extends FeatureRenderer<PandaEntity, PandaEntityModel<PandaEntity>> {
   public PandaHeldItemFeatureRenderer(FeatureRendererContext<PandaEntity, PandaEntityModel<PandaEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, PandaEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      ItemStack _snowmanxxxxxxxxxx = _snowman.getEquippedStack(EquipmentSlot.MAINHAND);
      if (_snowman.isScared() && !_snowman.isScaredByThunderstorm()) {
         float _snowmanxxxxxxxxxxx = -0.6F;
         float _snowmanxxxxxxxxxxxx = 1.4F;
         if (_snowman.isEating()) {
            _snowmanxxxxxxxxxxx -= 0.2F * MathHelper.sin(_snowman * 0.6F) + 0.2F;
            _snowmanxxxxxxxxxxxx -= 0.09F * MathHelper.sin(_snowman * 0.6F);
         }

         _snowman.push();
         _snowman.translate(0.1F, (double)_snowmanxxxxxxxxxxxx, (double)_snowmanxxxxxxxxxxx);
         MinecraftClient.getInstance().getHeldItemRenderer().renderItem(_snowman, _snowmanxxxxxxxxxx, ModelTransformation.Mode.GROUND, false, _snowman, _snowman, _snowman);
         _snowman.pop();
      }
   }
}
