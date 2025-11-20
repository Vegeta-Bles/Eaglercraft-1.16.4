package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.WitherArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.WitherEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class WitherEntityRenderer extends MobEntityRenderer<WitherEntity, WitherEntityModel<WitherEntity>> {
   private static final Identifier INVULNERABLE_TEXTURE = new Identifier("textures/entity/wither/wither_invulnerable.png");
   private static final Identifier TEXTURE = new Identifier("textures/entity/wither/wither.png");

   public WitherEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new WitherEntityModel<>(0.0F), 1.0F);
      this.addFeature(new WitherArmorFeatureRenderer(this));
   }

   protected int getBlockLight(WitherEntity _snowman, BlockPos _snowman) {
      return 15;
   }

   public Identifier getTexture(WitherEntity _snowman) {
      int _snowmanx = _snowman.getInvulnerableTimer();
      return _snowmanx > 0 && (_snowmanx > 80 || _snowmanx / 5 % 2 != 1) ? INVULNERABLE_TEXTURE : TEXTURE;
   }

   protected void scale(WitherEntity _snowman, MatrixStack _snowman, float _snowman) {
      float _snowmanxxx = 2.0F;
      int _snowmanxxxx = _snowman.getInvulnerableTimer();
      if (_snowmanxxxx > 0) {
         _snowmanxxx -= ((float)_snowmanxxxx - _snowman) / 220.0F * 0.5F;
      }

      _snowman.scale(_snowmanxxx, _snowmanxxx, _snowmanxxx);
   }
}
