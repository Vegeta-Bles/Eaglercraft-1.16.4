package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;

public class BubbleColumnUpParticle extends SpriteBillboardParticle {
   private BubbleColumnUpParticle(ClientWorld world, double x, double y, double z, double _snowman, double _snowman, double _snowman) {
      super(world, x, y, z);
      this.setBoundingBoxSpacing(0.02F, 0.02F);
      this.scale = this.scale * (this.random.nextFloat() * 0.6F + 0.2F);
      this.velocityX = _snowman * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.velocityY = _snowman * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.velocityZ = _snowman * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.maxAge = (int)(40.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      this.velocityY += 0.005;
      if (this.maxAge-- <= 0) {
         this.markDead();
      } else {
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.velocityX *= 0.85F;
         this.velocityY *= 0.85F;
         this.velocityZ *= 0.85F;
         if (!this.world.getFluidState(new BlockPos(this.x, this.y, this.z)).isIn(FluidTags.WATER)) {
            this.markDead();
         }
      }
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BubbleColumnUpParticle _snowmanxxxxxxxx = new BubbleColumnUpParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
