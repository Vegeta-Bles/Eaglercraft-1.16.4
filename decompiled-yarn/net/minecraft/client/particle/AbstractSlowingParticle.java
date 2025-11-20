package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;

public abstract class AbstractSlowingParticle extends SpriteBillboardParticle {
   protected AbstractSlowingParticle(ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.velocityX = this.velocityX * 0.01F + _snowman;
      this.velocityY = this.velocityY * 0.01F + _snowman;
      this.velocityZ = this.velocityZ * 0.01F + _snowman;
      this.x = this.x + (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
      this.y = this.y + (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
      this.z = this.z + (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.05F);
      this.maxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
   }

   @Override
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.velocityX *= 0.96F;
         this.velocityY *= 0.96F;
         this.velocityZ *= 0.96F;
         if (this.onGround) {
            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
         }
      }
   }
}
