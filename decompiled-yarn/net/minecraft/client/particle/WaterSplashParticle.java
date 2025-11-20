package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class WaterSplashParticle extends RainSplashParticle {
   private WaterSplashParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(world, x, y, z);
      this.gravityStrength = 0.04F;
      if (velocityY == 0.0 && (velocityX != 0.0 || velocityZ != 0.0)) {
         this.velocityX = velocityX;
         this.velocityY = 0.1;
         this.velocityZ = velocityZ;
      }
   }

   public static class SplashFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public SplashFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         WaterSplashParticle _snowmanxxxxxxxx = new WaterSplashParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
