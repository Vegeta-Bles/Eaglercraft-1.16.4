package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.feature.FoxHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class FoxEntityRenderer extends MobEntityRenderer<FoxEntity, FoxEntityModel<FoxEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/fox/fox.png");
   private static final Identifier SLEEPING_TEXTURE = new Identifier("textures/entity/fox/fox_sleep.png");
   private static final Identifier SNOW_TEXTURE = new Identifier("textures/entity/fox/snow_fox.png");
   private static final Identifier SLEEPING_SNOW_TEXTURE = new Identifier("textures/entity/fox/snow_fox_sleep.png");

   public FoxEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new FoxEntityModel<>(), 0.4F);
      this.addFeature(new FoxHeldItemFeatureRenderer(this));
   }

   protected void setupTransforms(FoxEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      super.setupTransforms(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.isChasing() || _snowman.isWalking()) {
         float _snowmanxxxxx = -MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch);
         _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxx));
      }
   }

   public Identifier getTexture(FoxEntity _snowman) {
      if (_snowman.getFoxType() == FoxEntity.Type.RED) {
         return _snowman.isSleeping() ? SLEEPING_TEXTURE : TEXTURE;
      } else {
         return _snowman.isSleeping() ? SLEEPING_SNOW_TEXTURE : SNOW_TEXTURE;
      }
   }
}
