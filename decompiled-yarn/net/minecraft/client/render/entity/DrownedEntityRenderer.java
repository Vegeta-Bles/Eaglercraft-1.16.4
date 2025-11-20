package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.DrownedOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.mob.DrownedEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class DrownedEntityRenderer extends ZombieBaseEntityRenderer<DrownedEntity, DrownedEntityModel<DrownedEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/zombie/drowned.png");

   public DrownedEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new DrownedEntityModel<>(0.0F, 0.0F, 64, 64), new DrownedEntityModel<>(0.5F, true), new DrownedEntityModel<>(1.0F, true));
      this.addFeature(new DrownedOverlayFeatureRenderer<>(this));
   }

   @Override
   public Identifier getTexture(ZombieEntity _snowman) {
      return TEXTURE;
   }

   protected void setupTransforms(DrownedEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowmanxxxxx = _snowman.getLeaningPitch(_snowman);
      if (_snowmanxxxxx > 0.0F) {
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.lerp(_snowmanxxxxx, _snowman.pitch, -10.0F - _snowman.pitch)));
      }
   }
}
