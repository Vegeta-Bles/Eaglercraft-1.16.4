package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class LargeFireSmokeParticle extends FireSmokeParticle {
   protected LargeFireSmokeParticle(
      ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider
   ) {
      super(world, x, y, z, velocityX, velocityY, velocityZ, 2.5F, spriteProvider);
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new LargeFireSmokeParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
      }
   }
}
