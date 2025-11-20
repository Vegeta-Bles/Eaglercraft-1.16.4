package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.HorseEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;

public class HorseArmorFeatureRenderer extends FeatureRenderer<HorseEntity, HorseEntityModel<HorseEntity>> {
   private final HorseEntityModel<HorseEntity> model = new HorseEntityModel<>(0.1F);

   public HorseArmorFeatureRenderer(FeatureRendererContext<HorseEntity, HorseEntityModel<HorseEntity>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, HorseEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      ItemStack _snowmanxxxxxxxxxx = _snowman.getArmorType();
      if (_snowmanxxxxxxxxxx.getItem() instanceof HorseArmorItem) {
         HorseArmorItem _snowmanxxxxxxxxxxx = (HorseArmorItem)_snowmanxxxxxxxxxx.getItem();
         this.getContextModel().copyStateTo(this.model);
         this.model.animateModel(_snowman, _snowman, _snowman, _snowman);
         this.model.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         float _snowmanxxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxxx;
         if (_snowmanxxxxxxxxxxx instanceof DyeableHorseArmorItem) {
            int _snowmanxxxxxxxxxxxxxxx = ((DyeableHorseArmorItem)_snowmanxxxxxxxxxxx).getColor(_snowmanxxxxxxxxxx);
            _snowmanxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxx >> 16 & 0xFF) / 255.0F;
            _snowmanxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxx >> 8 & 0xFF) / 255.0F;
            _snowmanxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxx & 0xFF) / 255.0F;
         } else {
            _snowmanxxxxxxxxxxxx = 1.0F;
            _snowmanxxxxxxxxxxxxx = 1.0F;
            _snowmanxxxxxxxxxxxxxx = 1.0F;
         }

         VertexConsumer _snowmanxxxxxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntityCutoutNoCull(_snowmanxxxxxxxxxxx.getEntityTexture()));
         this.model.render(_snowman, _snowmanxxxxxxxxxxxxxxx, _snowman, OverlayTexture.DEFAULT_UV, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, 1.0F);
      }
   }
}
