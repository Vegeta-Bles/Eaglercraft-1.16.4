package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SuspendParticle extends SpriteBillboardParticle {
   private SuspendParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
      super(world, x, y, z, velocityX, velocityY, velocityZ);
      float _snowman = this.random.nextFloat() * 0.1F + 0.2F;
      this.colorRed = _snowman;
      this.colorGreen = _snowman;
      this.colorBlue = _snowman;
      this.setBoundingBoxSpacing(0.02F, 0.02F);
      this.scale = this.scale * (this.random.nextFloat() * 0.6F + 0.5F);
      this.velocityX *= 0.02F;
      this.velocityY *= 0.02F;
      this.velocityZ *= 0.02F;
      this.maxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
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
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      if (this.maxAge-- <= 0) {
         this.markDead();
      } else {
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.velocityX *= 0.99;
         this.velocityY *= 0.99;
         this.velocityZ *= 0.99;
      }
   }

   public static class DolphinFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public DolphinFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         SuspendParticle _snowmanxxxxxxxx = new SuspendParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setColor(0.3F, 0.5F, 1.0F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         _snowmanxxxxxxxx.setColorAlpha(1.0F - _snowman.random.nextFloat() * 0.7F);
         _snowmanxxxxxxxx.setMaxAge(_snowmanxxxxxxxx.getMaxAge() / 2);
         return _snowmanxxxxxxxx;
      }
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         SuspendParticle _snowmanxxxxxxxx = new SuspendParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         _snowmanxxxxxxxx.setColor(1.0F, 1.0F, 1.0F);
         _snowmanxxxxxxxx.setMaxAge(3 + _snowman.getRandom().nextInt(5));
         return _snowmanxxxxxxxx;
      }
   }

   public static class HappyVillagerFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public HappyVillagerFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         SuspendParticle _snowmanxxxxxxxx = new SuspendParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         _snowmanxxxxxxxx.setColor(1.0F, 1.0F, 1.0F);
         return _snowmanxxxxxxxx;
      }
   }

   public static class MyceliumFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public MyceliumFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         SuspendParticle _snowmanxxxxxxxx = new SuspendParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
