package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class CloudParticle extends SpriteBillboardParticle {
   private final SpriteProvider spriteProvider;

   private CloudParticle(ClientWorld world, double x, double y, double z, double _snowman, double _snowman, double _snowman, SpriteProvider spriteProvider) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.spriteProvider = spriteProvider;
      float _snowmanxxx = 2.5F;
      this.velocityX *= 0.1F;
      this.velocityY *= 0.1F;
      this.velocityZ *= 0.1F;
      this.velocityX += _snowman;
      this.velocityY += _snowman;
      this.velocityZ += _snowman;
      float _snowmanxxxx = 1.0F - (float)(Math.random() * 0.3F);
      this.colorRed = _snowmanxxxx;
      this.colorGreen = _snowmanxxxx;
      this.colorBlue = _snowmanxxxx;
      this.scale *= 1.875F;
      int _snowmanxxxxx = (int)(8.0 / (Math.random() * 0.8 + 0.3));
      this.maxAge = (int)Math.max((float)_snowmanxxxxx * 2.5F, 1.0F);
      this.collidesWithWorld = false;
      this.setSpriteForAge(spriteProvider);
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
   }

   @Override
   public float getSize(float tickDelta) {
      return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
   }

   @Override
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.age++ >= this.maxAge) {
         this.markDead();
      } else {
         this.setSpriteForAge(this.spriteProvider);
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.velocityX *= 0.96F;
         this.velocityY *= 0.96F;
         this.velocityZ *= 0.96F;
         PlayerEntity _snowman = this.world.getClosestPlayer(this.x, this.y, this.z, 2.0, false);
         if (_snowman != null) {
            double _snowmanx = _snowman.getY();
            if (this.y > _snowmanx) {
               this.y = this.y + (_snowmanx - this.y) * 0.2;
               this.velocityY = this.velocityY + (_snowman.getVelocity().y - this.velocityY) * 0.2;
               this.setPos(this.x, this.y, this.z);
            }
         }

         if (this.onGround) {
            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
         }
      }
   }

   public static class CloudFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public CloudFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new CloudParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
      }
   }

   public static class SneezeFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public SneezeFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         Particle _snowmanxxxxxxxx = new CloudParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
         _snowmanxxxxxxxx.setColor(200.0F, 50.0F, 120.0F);
         _snowmanxxxxxxxx.setColorAlpha(0.4F);
         return _snowmanxxxxxxxx;
      }
   }
}
