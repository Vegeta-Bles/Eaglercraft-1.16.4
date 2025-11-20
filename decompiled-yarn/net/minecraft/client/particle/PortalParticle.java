package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class PortalParticle extends SpriteBillboardParticle {
   private final double startX;
   private final double startY;
   private final double startZ;

   protected PortalParticle(ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.velocityX = _snowman;
      this.velocityY = _snowman;
      this.velocityZ = _snowman;
      this.x = _snowman;
      this.y = _snowman;
      this.z = _snowman;
      this.startX = this.x;
      this.startY = this.y;
      this.startZ = this.z;
      this.scale = 0.1F * (this.random.nextFloat() * 0.2F + 0.5F);
      float _snowmanxxxxxxx = this.random.nextFloat() * 0.6F + 0.4F;
      this.colorRed = _snowmanxxxxxxx * 0.9F;
      this.colorGreen = _snowmanxxxxxxx * 0.3F;
      this.colorBlue = _snowmanxxxxxxx;
      this.maxAge = (int)(Math.random() * 10.0) + 40;
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public void move(double dx, double dy, double dz) {
      this.setBoundingBox(this.getBoundingBox().offset(dx, dy, dz));
      this.repositionFromBoundingBox();
   }

   @Override
   public float getSize(float tickDelta) {
      float _snowman = ((float)this.age + tickDelta) / (float)this.maxAge;
      _snowman = 1.0F - _snowman;
      _snowman *= _snowman;
      _snowman = 1.0F - _snowman;
      return this.scale * _snowman;
   }

   @Override
   public int getColorMultiplier(float tint) {
      int _snowman = super.getColorMultiplier(tint);
      float _snowmanx = (float)this.age / (float)this.maxAge;
      _snowmanx *= _snowmanx;
      _snowmanx *= _snowmanx;
      int _snowmanxx = _snowman & 0xFF;
      int _snowmanxxx = _snowman >> 16 & 0xFF;
      _snowmanxxx += (int)(_snowmanx * 15.0F * 16.0F);
      if (_snowmanxxx > 240) {
         _snowmanxxx = 240;
      }

      return _snowmanxx | _snowmanxxx << 16;
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
         float var3 = -_snowman + _snowman * _snowman * 2.0F;
         float var4 = 1.0F - var3;
         this.x = this.startX + this.velocityX * (double)var4;
         this.y = this.startY + this.velocityY * (double)var4 + (double)(1.0F - _snowman);
         this.z = this.startZ + this.velocityZ * (double)var4;
      }
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         PortalParticle _snowmanxxxxxxxx = new PortalParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
