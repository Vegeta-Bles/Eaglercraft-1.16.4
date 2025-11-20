package net.minecraft.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      float g = ((float)this.age + tickDelta) / (float)this.maxAge;
      float h = 0.05F + 0.5F * MathHelper.sin(g * (float) Math.PI);
      MatrixStack lv = new MatrixStack();
      lv.multiply(camera.getRotation());
      lv.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(150.0F * g - 60.0F));
      lv.scale(-1.0F, -1.0F, 1.0F);
      lv.translate(0.0, -1.101F, 1.5);
      VertexConsumerProvider.Immediate lv2 = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
      VertexConsumer lv3 = lv2.getBuffer(this.LAYER);
      this.model.render(lv, lv3, 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, h);
      lv2.draw();
   }

   @Environment(EnvType.CLIENT)
   public static class Factory implements ParticleFactory<DefaultParticleType> {
      public Factory() {
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         return new ElderGuardianAppearanceParticle(arg2, d, e, f);
      }
   }
}
