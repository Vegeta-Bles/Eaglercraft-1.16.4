package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SoulParticle extends AbstractSlowingParticle {
   private final SpriteProvider spriteProvider;

   private SoulParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
      this.spriteProvider = spriteProvider;
      this.scale(1.5F);
      this.setSpriteForAge(spriteProvider);
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.dead) {
         this.setSpriteForAge(this.spriteProvider);
      }
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         SoulParticle _snowmanxxxxxxxx = new SoulParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
         _snowmanxxxxxxxx.setColorAlpha(1.0F);
         return _snowmanxxxxxxxx;
      }
   }
}
