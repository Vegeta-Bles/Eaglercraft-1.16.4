package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class EndRodParticle extends AnimatedParticle {
   private EndRodParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
      super(world, x, y, z, spriteProvider, -5.0E-4F);
      this.velocityX = velocityX;
      this.velocityY = velocityY;
      this.velocityZ = velocityZ;
      this.scale *= 0.75F;
      this.maxAge = 60 + this.random.nextInt(12);
      this.setTargetColor(15916745);
      this.setSpriteForAge(spriteProvider);
   }

   @Override
   public void move(double dx, double dy, double dz) {
      this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
      this.repositionFromBoundingBox();
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new EndRodParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
      }
   }
}
