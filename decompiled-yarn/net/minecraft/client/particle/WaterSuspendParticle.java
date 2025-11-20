package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

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

   public static class CrimsonSporeFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public CrimsonSporeFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         Random _snowmanxxxxxxxx = _snowman.random;
         double _snowmanxxxxxxxxx = _snowmanxxxxxxxx.nextGaussian() * 1.0E-6F;
         double _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.nextGaussian() * 1.0E-4F;
         double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.nextGaussian() * 1.0E-6F;
         WaterSuspendParticle _snowmanxxxxxxxxxxxx = new WaterSuspendParticle(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
         _snowmanxxxxxxxxxxxx.setSprite(this.spriteProvider);
         _snowmanxxxxxxxxxxxx.setColor(0.9F, 0.4F, 0.5F);
         return _snowmanxxxxxxxxxxxx;
      }
   }

   public static class UnderwaterFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public UnderwaterFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         WaterSuspendParticle _snowmanxxxxxxxx = new WaterSuspendParticle(_snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class WarpedSporeFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public WarpedSporeFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         double _snowmanxxxxxxxx = (double)_snowman.random.nextFloat() * -1.9 * (double)_snowman.random.nextFloat() * 0.1;
         WaterSuspendParticle _snowmanxxxxxxxxx = new WaterSuspendParticle(_snowman, _snowman, _snowman, _snowman, 0.0, _snowmanxxxxxxxx, 0.0);
         _snowmanxxxxxxxxx.setSprite(this.spriteProvider);
         _snowmanxxxxxxxxx.setColor(0.1F, 0.1F, 0.3F);
         _snowmanxxxxxxxxx.setBoundingBoxSpacing(0.001F, 0.001F);
         return _snowmanxxxxxxxxx;
      }
   }
}
