package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.CatCollarFeatureRenderer;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

public class CatEntityRenderer extends MobEntityRenderer<CatEntity, CatEntityModel<CatEntity>> {
   public CatEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new CatEntityModel<>(0.0F), 0.4F);
      this.addFeature(new CatCollarFeatureRenderer(this));
   }

   public Identifier getTexture(CatEntity _snowman) {
      return _snowman.getTexture();
   }

   protected void scale(CatEntity _snowman, MatrixStack _snowman, float _snowman) {
      super.scale(_snowman, _snowman, _snowman);
      _snowman.scale(0.8F, 0.8F, 0.8F);
   }

   protected void setupTransforms(CatEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowmanxxxxx = _snowman.getSleepAnimation(_snowman);
      if (_snowmanxxxxx > 0.0F) {
         _snowman.translate((double)(0.4F * _snowmanxxxxx), (double)(0.15F * _snowmanxxxxx), (double)(0.1F * _snowmanxxxxx));
         _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerpAngleDegrees(_snowmanxxxxx, 0.0F, 90.0F)));
         BlockPos _snowmanxxxxxx = _snowman.getBlockPos();

         for (PlayerEntity _snowmanxxxxxxx : _snowman.world.getNonSpectatingEntities(PlayerEntity.class, new Box(_snowmanxxxxxx).expand(2.0, 2.0, 2.0))) {
            if (_snowmanxxxxxxx.isSleeping()) {
               _snowman.translate((double)(0.15F * _snowmanxxxxx), 0.0, 0.0);
               break;
            }
         }
      }
   }
}
