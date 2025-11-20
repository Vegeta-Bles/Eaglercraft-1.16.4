package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class WhiteAshParticle extends AscendingParticle {
   protected WhiteAshParticle(
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
      super(world, x, y, z, 0.1F, -0.1F, 0.1F, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, 0.0F, 20, -5.0E-4, false);
      this.colorRed = 0.7294118F;
      this.colorGreen = 0.69411767F;
      this.colorBlue = 0.7607843F;
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         Random _snowmanxxxxxxxx = _snowman.random;
         double _snowmanxxxxxxxxx = (double)_snowmanxxxxxxxx.nextFloat() * -1.9 * (double)_snowmanxxxxxxxx.nextFloat() * 0.1;
         double _snowmanxxxxxxxxxx = (double)_snowmanxxxxxxxx.nextFloat() * -0.5 * (double)_snowmanxxxxxxxx.nextFloat() * 0.1 * 5.0;
         double _snowmanxxxxxxxxxxx = (double)_snowmanxxxxxxxx.nextFloat() * -1.9 * (double)_snowmanxxxxxxxx.nextFloat() * 0.1;
         return new WhiteAshParticle(_snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, 1.0F, this.spriteProvider);
      }
   }
}
