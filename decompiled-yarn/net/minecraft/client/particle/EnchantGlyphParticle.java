package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class EnchantGlyphParticle extends SpriteBillboardParticle {
   private final double startX;
   private final double startY;
   private final double startZ;

   private EnchantGlyphParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(world, x, y, z);
      this.velocityX = velocityX;
      this.velocityY = velocityY;
      this.velocityZ = velocityZ;
      this.startX = x;
      this.startY = y;
      this.startZ = z;
      this.prevPosX = x + velocityX;
      this.prevPosY = y + velocityY;
      this.prevPosZ = z + velocityZ;
      this.x = this.prevPosX;
      this.y = this.prevPosY;
      this.z = this.prevPosZ;
      this.scale = 0.1F * (this.random.nextFloat() * 0.5F + 0.2F);
      float _snowman = this.random.nextFloat() * 0.6F + 0.4F;
      this.colorRed = 0.9F * _snowman;
      this.colorGreen = 0.9F * _snowman;
      this.colorBlue = _snowman;
      this.collidesWithWorld = false;
      this.maxAge = (int)(Math.random() * 10.0) + 30;
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
         _snowman = 1.0F - _snowman;
         float _snowmanx = 1.0F - _snowman;
         _snowmanx *= _snowmanx;
         _snowmanx *= _snowmanx;
         this.x = this.startX + this.velocityX * (double)_snowman;
         this.y = this.startY + this.velocityY * (double)_snowman - (double)(_snowmanx * 1.2F);
         this.z = this.startZ + this.velocityZ * (double)_snowman;
      }
   }

   public static class EnchantFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public EnchantFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         EnchantGlyphParticle _snowmanxxxxxxxx = new EnchantGlyphParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class NautilusFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public NautilusFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         EnchantGlyphParticle _snowmanxxxxxxxx = new EnchantGlyphParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
