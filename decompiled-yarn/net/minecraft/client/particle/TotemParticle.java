package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class TotemParticle extends AnimatedParticle {
   private TotemParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
      super(world, x, y, z, spriteProvider, -0.05F);
      this.velocityX = velocityX;
      this.velocityY = velocityY;
      this.velocityZ = velocityZ;
      this.scale *= 0.75F;
      this.maxAge = 60 + this.random.nextInt(12);
      this.setSpriteForAge(spriteProvider);
      if (this.random.nextInt(4) == 0) {
         this.setColor(0.6F + this.random.nextFloat() * 0.2F, 0.6F + this.random.nextFloat() * 0.3F, this.random.nextFloat() * 0.2F);
      } else {
         this.setColor(0.1F + this.random.nextFloat() * 0.2F, 0.4F + this.random.nextFloat() * 0.3F, this.random.nextFloat() * 0.2F);
      }

      this.setResistance(0.6F);
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new TotemParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
      }
   }
}
