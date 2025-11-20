package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class AshParticle extends AscendingParticle {
   protected AshParticle(
      ClientWorld world,
      double x,
      double y,
      double z,
      double velocityX,
      double velocityY,
      double velocityZ,
      float scaleMultiplier,
      SpriteProvider spriteProvider
   ) {
      super(world, x, y, z, 0.1F, -0.1F, 0.1F, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, 0.5F, 20, -0.004, false);
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new AshParticle(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0, 1.0F, this.spriteProvider);
      }
   }
}
