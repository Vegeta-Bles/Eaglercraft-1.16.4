package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class DamageParticle extends SpriteBillboardParticle {
   private DamageParticle(ClientWorld world, double x, double y, double z, double _snowman, double _snowman, double _snowman) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.velocityX *= 0.1F;
      this.velocityY *= 0.1F;
      this.velocityZ *= 0.1F;
      this.velocityX += _snowman * 0.4;
      this.velocityY += _snowman * 0.4;
      this.velocityZ += _snowman * 0.4;
      float _snowmanxxx = (float)(Math.random() * 0.3F + 0.6F);
      this.colorRed = _snowmanxxx;
      this.colorGreen = _snowmanxxx;
      this.colorBlue = _snowmanxxx;
      this.scale *= 0.75F;
      this.maxAge = Math.max((int)(6.0 / (Math.random() * 0.8 + 0.6)), 1);
      this.collidesWithWorld = false;
      this.tick();
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
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.colorGreen = (float)((double)this.colorGreen * 0.96);
         this.colorBlue = (float)((double)this.colorBlue * 0.9);
         this.velocityX *= 0.7F;
         this.velocityY *= 0.7F;
         this.velocityZ *= 0.7F;
         this.velocityY -= 0.02F;
         if (this.onGround) {
            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
         }
      }
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   public static class DefaultFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public DefaultFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         DamageParticle _snowmanxxxxxxxx = new DamageParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman + 1.0, _snowman);
         _snowmanxxxxxxxx.setMaxAge(20);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class EnchantedHitFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public EnchantedHitFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         DamageParticle _snowmanxxxxxxxx = new DamageParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.colorRed *= 0.3F;
         _snowmanxxxxxxxx.colorGreen *= 0.8F;
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class Factory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public Factory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         DamageParticle _snowmanxxxxxxxx = new DamageParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
