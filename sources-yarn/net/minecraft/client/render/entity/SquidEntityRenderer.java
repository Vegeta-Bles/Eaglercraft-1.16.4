package net.minecraft.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.SquidEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class SquidEntityRenderer extends MobEntityRenderer<SquidEntity, SquidEntityModel<SquidEntity>> {
   private static final Identifier TEXTURE = new Identifier("textures/entity/squid.png");

   public SquidEntityRenderer(EntityRenderDispatcher arg) {
      super(arg, new SquidEntityModel<>(), 0.7F);
   }

   public Identifier getTexture(SquidEntity arg) {
      return TEXTURE;
   }

   protected void setupTransforms(SquidEntity arg, MatrixStack arg2, float f, float g, float h) {
      float i = MathHelper.lerp(h, arg.prevTiltAngle, arg.tiltAngle);
      float j = MathHelper.lerp(h, arg.prevRollAngle, arg.rollAngle);
      arg2.translate(0.0, 0.5, 0.0);
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - g));
      arg2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(i));
      arg2.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(j));
      arg2.translate(0.0, -1.2F, 0.0);
   }

   protected float getAnimationProgress(SquidEntity arg, float f) {
      return MathHelper.lerp(f, arg.prevTentacleAngle, arg.tentacleAngle);
   }
}
