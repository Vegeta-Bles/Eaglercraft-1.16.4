package net.minecraft.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

@Environment(EnvType.CLIENT)
public class RainSplashParticle extends SpriteBillboardParticle {
   protected RainSplashParticle(ClientWorld arg, double d, double e, double f) {
      super(arg, d, e, f, 0.0, 0.0, 0.0);
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

         BlockPos lv = new BlockPos(this.x, this.y, this.z);
         double d = Math.max(
            this.world
               .getBlockState(lv)
               .getCollisionShape(this.world, lv)
               .getEndingCoord(Direction.Axis.Y, this.x - (double)lv.getX(), this.z - (double)lv.getZ()),
            (double)this.world.getFluidState(lv).getHeight(this.world, lv)
         );
         if (d > 0.0 && this.y < (double)lv.getY() + d) {
            this.markDead();
         }
      }
   }

   @Environment(EnvType.CLIENT)
   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         RainSplashParticle lv = new RainSplashParticle(arg2, d, e, f);
         lv.setSprite(this.spriteProvider);
         return lv;
      }
   }
}
