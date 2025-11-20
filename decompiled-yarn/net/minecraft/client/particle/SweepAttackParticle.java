package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SweepAttackParticle extends SpriteBillboardParticle {
   private final SpriteProvider spriteProvider;

   private SweepAttackParticle(ClientWorld world, double x, double y, double z, double scaleMultiplier, SpriteProvider spriteProvider) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.spriteProvider = spriteProvider;
      this.maxAge = 4;
      float _snowman = this.random.nextFloat() * 0.6F + 0.4F;
      this.colorRed = _snowman;
      this.colorGreen = _snowman;
      this.colorBlue = _snowman;
      this.scale = 1.0F - (float)scaleMultiplier * 0.5F;
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
         return new SweepAttackParticle(_snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
      }
   }
}
