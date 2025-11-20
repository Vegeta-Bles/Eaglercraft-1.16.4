package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.util.math.MathHelper;

public class RedDustParticle extends SpriteBillboardParticle {
   private final SpriteProvider spriteProvider;

   private RedDustParticle(
      ClientWorld world,
      double x,
      double y,
      double z,
      double velocityX,
      double velocityY,
      double velocityZ,
      DustParticleEffect _snowman,
      SpriteProvider spriteProvider
   ) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
      this.spriteProvider = spriteProvider;
      this.velocityX *= 0.1F;
      this.velocityY *= 0.1F;
      this.velocityZ *= 0.1F;
      float _snowmanx = (float)Math.random() * 0.4F + 0.6F;
      this.colorRed = ((float)(Math.random() * 0.2F) + 0.8F) * _snowman.getRed() * _snowmanx;
      this.colorGreen = ((float)(Math.random() * 0.2F) + 0.8F) * _snowman.getGreen() * _snowmanx;
      this.colorBlue = ((float)(Math.random() * 0.2F) + 0.8F) * _snowman.getBlue() * _snowmanx;
      this.scale = this.scale * 0.75F * _snowman.getScale();
      int _snowmanxx = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.maxAge = (int)Math.max((float)_snowmanxx * _snowman.getScale(), 1.0F);
      this.setSpriteForAge(spriteProvider);
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public float getSize(float tickDelta) {
      return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
   }

   @Override
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         this.setSpriteForAge(this.spriteProvider);
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         if (this.y == this.prevPosY) {
            this.velocityX *= 1.1;
            this.velocityZ *= 1.1;
         }

         this.velocityX *= 0.96F;
         this.velocityY *= 0.96F;
         this.velocityZ *= 0.96F;
         if (this.onGround) {
            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
         }
      }
   }

   public static class Factory implements ParticleFactory<DustParticleEffect> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DustParticleEffect _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new RedDustParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
      }
   }
}
