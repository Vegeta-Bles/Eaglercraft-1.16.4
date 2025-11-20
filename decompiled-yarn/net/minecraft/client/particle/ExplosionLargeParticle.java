package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ExplosionLargeParticle extends SpriteBillboardParticle {
   private final SpriteProvider spriteProvider;

   private ExplosionLargeParticle(ClientWorld world, double x, double y, double z, double _snowman, SpriteProvider spriteProvider) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.maxAge = 6 + this.random.nextInt(4);
      float _snowmanx = this.random.nextFloat() * 0.6F + 0.4F;
      this.colorRed = _snowmanx;
      this.colorGreen = _snowmanx;
      this.colorBlue = _snowmanx;
      this.scale = 2.0F * (1.0F - (float)_snowman * 0.5F);
      this.spriteProvider = spriteProvider;
      this.setSpriteForAge(spriteProvider);
   }

   @Override
   public int getColorMultiplier(float tint) {
      return 15728880;
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
      }
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_LIT;
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new ExplosionLargeParticle(_snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
      }
   }
}
