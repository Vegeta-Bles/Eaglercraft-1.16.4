package net.minecraft.client.particle;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class WaterSuspendParticle extends SpriteBillboardParticle {
   private WaterSuspendParticle(ClientWorld world, double x, double y, double z) {
      super(world, x, y - 0.125, z);
      this.colorRed = 0.4F;
      this.colorGreen = 0.4F;
      this.colorBlue = 0.7F;
      this.setBoundingBoxSpacing(0.01F, 0.01F);
      this.scale = this.scale * (this.random.nextFloat() * 0.6F + 0.2F);
      this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      this.collidesWithWorld = false;
   }

   private WaterSuspendParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(world, x, y - 0.125, z, velocityX, velocityY, velocityZ);
      this.setBoundingBoxSpacing(0.01F, 0.01F);
      this.scale = this.scale * (this.random.nextFloat() * 0.6F + 0.6F);
      this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      this.collidesWithWorld = false;
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.maxAge-- <= 0) {
         this.markDead();
      } else {
         this.move(this.velocityX, this.velocityY, this.velocityZ);
      }
   }

   @Environment(EnvType.CLIENT)
   public static class CrimsonSporeFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public CrimsonSporeFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         Random random = arg2.random;
         double j = random.nextGaussian() * 1.0E-6F;
         double k = random.nextGaussian() * 1.0E-4F;
         double l = random.nextGaussian() * 1.0E-6F;
         WaterSuspendParticle lv = new WaterSuspendParticle(arg2, d, e, f, j, k, l);
         lv.setSprite(this.spriteProvider);
         lv.setColor(0.9F, 0.4F, 0.5F);
         return lv;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class UnderwaterFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public UnderwaterFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         WaterSuspendParticle lv = new WaterSuspendParticle(arg2, d, e, f);
         lv.setSprite(this.spriteProvider);
         return lv;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class WarpedSporeFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public WarpedSporeFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         double j = (double)arg2.random.nextFloat() * -1.9 * (double)arg2.random.nextFloat() * 0.1;
         WaterSuspendParticle lv = new WaterSuspendParticle(arg2, d, e, f, 0.0, j, 0.0);
         lv.setSprite(this.spriteProvider);
         lv.setColor(0.1F, 0.1F, 0.3F);
         lv.setBoundingBoxSpacing(0.001F, 0.001F);
         return lv;
      }
   }
}
