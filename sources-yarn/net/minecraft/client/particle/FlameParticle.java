package net.minecraft.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FlameParticle extends AbstractSlowingParticle {
   private FlameParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public void move(double dx, double dy, double dz) {
      this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
      this.repositionFromBoundingBox();
   }

   @Override
   public float getSize(float tickDelta) {
      float g = ((float)this.age + tickDelta) / (float)this.maxAge;
      return this.scale * (1.0F - g * g * 0.5F);
   }

   @Override
   public int getColorMultiplier(float tint) {
      float g = ((float)this.age + tint) / (float)this.maxAge;
      g = MathHelper.clamp(g, 0.0F, 1.0F);
      int i = super.getColorMultiplier(tint);
      int j = i & 0xFF;
      int k = i >> 16 & 0xFF;
      j += (int)(g * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   @Environment(EnvType.CLIENT)
   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         FlameParticle lv = new FlameParticle(arg2, d, e, f, g, h, i);
         lv.setSprite(this.spriteProvider);
         return lv;
      }
   }
}
