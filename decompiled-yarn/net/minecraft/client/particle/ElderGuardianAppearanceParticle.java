package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ElderGuardianEntityRenderer;
import net.minecraft.client.render.entity.model.GuardianEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class ElderGuardianAppearanceParticle extends Particle {
   private final Model model = new GuardianEntityModel();
   private final RenderLayer LAYER = RenderLayer.getEntityTranslucent(ElderGuardianEntityRenderer.TEXTURE);

   private ElderGuardianAppearanceParticle(ClientWorld world, double x, double y, double z) {
      super(world, x, y, z);
      this.gravityStrength = 0.0F;
      this.maxAge = 30;
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.CUSTOM;
   }

   @Override
   public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
      float _snowman = ((float)this.age + tickDelta) / (float)this.maxAge;
      float _snowmanx = 0.05F + 0.5F * MathHelper.sin(_snowman * (float) Math.PI);
      MatrixStack _snowmanxx = new MatrixStack();
      _snowmanxx.multiply(camera.getRotation());
      _snowmanxx.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(150.0F * _snowman - 60.0F));
      _snowmanxx.scale(-1.0F, -1.0F, 1.0F);
      _snowmanxx.translate(0.0, -1.101F, 1.5);
      VertexConsumerProvider.Immediate _snowmanxxx = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
      VertexConsumer _snowmanxxxx = _snowmanxxx.getBuffer(this.LAYER);
      this.model.render(_snowmanxx, _snowmanxxxx, 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, _snowmanx);
      _snowmanxxx.draw();
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      public Factory() {
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new ElderGuardianAppearanceParticle(_snowman, _snowman, _snowman, _snowman);
      }
   }
}
