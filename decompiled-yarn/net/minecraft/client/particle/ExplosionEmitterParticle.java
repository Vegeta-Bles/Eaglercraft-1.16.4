package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;

public class ExplosionEmitterParticle extends NoRenderParticle {
   private int age_;
   private final int maxAge_ = 8;

   private ExplosionEmitterParticle(ClientWorld world, double x, double y, double z) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
   }

   @Override
   public void tick() {
      for (int _snowman = 0; _snowman < 6; _snowman++) {
         double _snowmanx = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
         double _snowmanxx = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
         double _snowmanxxx = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
         this.world.addParticle(ParticleTypes.EXPLOSION, _snowmanx, _snowmanxx, _snowmanxxx, (double)((float)this.age_ / (float)this.maxAge_), 0.0, 0.0);
      }

      this.age_++;
      if (this.age_ == this.maxAge_) {
         this.markDead();
      }
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      public Factory() {
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new ExplosionEmitterParticle(_snowman, _snowman, _snowman, _snowman);
      }
   }
}
