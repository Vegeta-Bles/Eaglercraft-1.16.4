package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ReversePortalParticle extends PortalParticle {
   private ReversePortalParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
      this.scale = (float)((double)this.scale * 1.5);
      this.maxAge = (int)(Math.random() * 2.0) + 60;
   }

   @Override
   public float getSize(float tickDelta) {
      float _snowman = 1.0F - ((float)this.age + tickDelta) / ((float)this.maxAge * 1.5F);
      return this.scale * _snowman;
   }

   @Override
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         float _snowman = (float)this.age / (float)this.maxAge;
         this.x = this.x + this.velocityX * (double)_snowman;
         this.y = this.y + this.velocityY * (double)_snowman;
         this.z = this.z + this.velocityZ * (double)_snowman;
      }
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         ReversePortalParticle _snowmanxxxxxxxx = new ReversePortalParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
