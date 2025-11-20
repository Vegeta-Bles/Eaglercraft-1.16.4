package net.minecraft.client.render.entity;

import net.minecraft.client.render.entity.model.SquidEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SquidEntityRenderer extends MobEntityRenderer<SquidEntity, SquidEntityModel<SquidEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/squid.png");

   public SquidEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman, new SquidEntityModel<>(), 0.7F);
   }

   public Identifier getTexture(SquidEntity _snowman) {
      return TEXTURE;
   }

   protected void setupTransforms(SquidEntity _snowman, MatrixStack _snowman, float _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxx = MathHelper.lerp(_snowman, _snowman.prevTiltAngle, _snowman.tiltAngle);
      float _snowmanxxxxxx = MathHelper.lerp(_snowman, _snowman.prevRollAngle, _snowman.rollAngle);
      _snowman.translate(0.0, 0.5, 0.0);
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - _snowman));
      _snowman.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(_snowmanxxxxx));
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxx));
      _snowman.translate(0.0, -1.2F, 0.0);
   }

   protected float getAnimationProgress(SquidEntity _snowman, float _snowman) {
      return MathHelper.lerp(_snowman, _snowman.prevTentacleAngle, _snowman.tentacleAngle);
   }
}
