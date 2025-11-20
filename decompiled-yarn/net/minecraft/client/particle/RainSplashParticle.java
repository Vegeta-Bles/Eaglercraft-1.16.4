package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class RainSplashParticle extends SpriteBillboardParticle {
   protected RainSplashParticle(ClientWorld _snowman, double _snowman, double _snowman, double _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, 0.0, 0.0, 0.0);
      this.velocityX *= 0.3F;
      this.velocityY = Math.random() * 0.2F + 0.1F;
      this.velocityZ *= 0.3F;
      this.setBoundingBoxSpacing(0.01F, 0.01F);
      this.gravityStrength = 0.06F;
      this.maxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.maxAge-- <= 0) {
         this.markDead();
      } else {
         this.velocityY = this.velocityY - (double)this.gravityStrength;
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.velocityX *= 0.98F;
         this.velocityY *= 0.98F;
         this.velocityZ *= 0.98F;
         if (this.onGround) {
            if (Math.random() < 0.5) {
               this.markDead();
            }

            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
         }

         BlockPos _snowman = new BlockPos(this.x, this.y, this.z);
         double _snowmanx = Math.max(
            this.world.getBlockState(_snowman).getCollisionShape(this.world, _snowman).getEndingCoord(Direction.Axis.Y, this.x - (double)_snowman.getX(), this.z - (double)_snowman.getZ()),
            (double)this.world.getFluidState(_snowman).getHeight(this.world, _snowman)
         );
         if (_snowmanx > 0.0 && this.y < (double)_snowman.getY() + _snowmanx) {
            this.markDead();
         }
      }
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         RainSplashParticle _snowmanxxxxxxxx = new RainSplashParticle(_snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
