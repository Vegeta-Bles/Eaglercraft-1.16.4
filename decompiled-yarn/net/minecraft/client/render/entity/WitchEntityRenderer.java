package net.minecraft.client.render.entity;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.WitchHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.WitchEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.util.Identifier;

public class WitchEntityRenderer extends MobEntityRenderer<WitchEntity, WitchEntityModel<WitchEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/witch.png");

   public WitchEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new WitchEntityModel<>(0.0F), 0.5F);
      this.addFeature(new WitchHeldItemFeatureRenderer<>(this));
   }

   public void render(WitchEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      this.model.setLiftingNose(!_snowman.getMainHandStack().isEmpty());
      super.render(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public Identifier getTexture(WitchEntity _snowman) {
      return TEXTURE;
   }

   protected void scale(WitchEntity _snowman, MatrixStack _snowman, float _snowman) {
      float _snowmanxxx = 0.9375F;
      _snowman.scale(0.9375F, 0.9375F, 0.9375F);
   }
}
