package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ExplosionSmokeParticle extends SpriteBillboardParticle {
   private final SpriteProvider spriteProvider;

   protected ExplosionSmokeParticle(ClientWorld world, double x, double y, double z, double _snowman, double _snowman, double _snowman, SpriteProvider spriteProvider) {
      super(world, x, y, z);
      this.spriteProvider = spriteProvider;
      this.velocityX = _snowman + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.velocityY = _snowman + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.velocityZ = _snowman + (Math.random() * 2.0 - 1.0) * 0.05F;
      float _snowmanxxx = this.random.nextFloat() * 0.3F + 0.7F;
      this.colorRed = _snowmanxxx;
      this.colorGreen = _snowmanxxx;
      this.colorBlue = _snowmanxxx;
      this.scale = 0.1F * (this.random.nextFloat() * this.random.nextFloat() * 6.0F + 1.0F);
      this.maxAge = (int)(16.0 / ((double)this.random.nextFloat() * 0.8 + 0.2)) + 2;
      this.setSpriteForAge(spriteProvider);
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
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         this.setSpriteForAge(this.spriteProvider);
         this.velocityY += 0.004;
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.velocityX *= 0.9F;
         this.velocityY *= 0.9F;
         this.velocityZ *= 0.9F;
         if (this.onGround) {
            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
         }
      }
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new ExplosionSmokeParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
      }
   }
}
