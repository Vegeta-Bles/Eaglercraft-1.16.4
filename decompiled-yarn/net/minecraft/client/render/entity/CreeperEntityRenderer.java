package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.CreeperChargeFeatureRenderer;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class CreeperEntityRenderer extends MobEntityRenderer<CreeperEntity, CreeperEntityModel<CreeperEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/creeper/creeper.png");

   public CreeperEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new CreeperEntityModel<>(), 0.5F);
      this.addFeature(new CreeperChargeFeatureRenderer(this));
   }

   protected void scale(CreeperEntity _snowman, MatrixStack _snowman, float _snowman) {
      float _snowmanxxx = _snowman.getClientFuseTime(_snowman);
      float _snowmanxxxx = 1.0F + MathHelper.sin(_snowmanxxx * 100.0F) * _snowmanxxx * 0.01F;
      _snowmanxxx = MathHelper.clamp(_snowmanxxx, 0.0F, 1.0F);
      _snowmanxxx *= _snowmanxxx;
      _snowmanxxx *= _snowmanxxx;
      float _snowmanxxxxx = (1.0F + _snowmanxxx * 0.4F) * _snowmanxxxx;
      float _snowmanxxxxxx = (1.0F + _snowmanxxx * 0.1F) / _snowmanxxxx;
      _snowman.scale(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxx);
   }

   protected float getAnimationCounter(CreeperEntity _snowman, float _snowman) {
      float _snowmanxx = _snowman.getClientFuseTime(_snowman);
      return (int)(_snowmanxx * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(_snowmanxx, 0.5F, 1.0F);
   }

   public Identifier getTexture(CreeperEntity _snowman) {
      return TEXTURE;
   }
}
