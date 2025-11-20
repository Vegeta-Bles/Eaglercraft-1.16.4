package net.minecraft.client.render.entity.feature;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class SlimeOverlayFeatureRenderer<T extends LivingEntity> extends FeatureRenderer<T, SlimeEntityModel<T>> {
   private final EntityModel<T> model = new SlimeEntityModel<>(0);

   public SlimeOverlayFeatureRenderer(FeatureRendererContext<T, SlimeEntityModel<T>> _snowman) {
      super(_snowman);
   }

   public void render(MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (!_snowman.isInvisible()) {
         this.getContextModel().copyStateTo(this.model);
         this.model.animateModel(_snowman, _snowman, _snowman, _snowman);
         this.model.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         VertexConsumer _snowmanxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(_snowman)));
         this.model.render(_snowman, _snowmanxxxxxxxxxx, _snowman, LivingEntityRenderer.getOverlay(_snowman, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
      }
   }
}
