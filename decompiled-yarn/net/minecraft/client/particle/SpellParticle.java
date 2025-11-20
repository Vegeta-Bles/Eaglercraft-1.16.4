package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SpellParticle extends SpriteBillboardParticle {
   private static final Random RANDOM = new Random();
   private final SpriteProvider spriteProvider;

   private SpellParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
      super(world, x, y, z, 0.5 - RANDOM.nextDouble(), velocityY, 0.5 - RANDOM.nextDouble());
      this.spriteProvider = spriteProvider;
      this.velocityY *= 0.2F;
      if (velocityX == 0.0 && velocityZ == 0.0) {
         this.velocityX *= 0.1F;
         this.velocityZ *= 0.1F;
      }

      this.scale *= 0.75F;
      this.maxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.collidesWithWorld = false;
      this.setSpriteForAge(spriteProvider);
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
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
         this.velocityY += 0.004;
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         if (this.y == this.prevPosY) {
            this.velocityX *= 1.1;
            this.velocityZ *= 1.1;
         }

         this.velocityX *= 0.96F;
         this.velocityY *= 0.96F;
         this.velocityZ *= 0.96F;
         if (this.onGround) {
            this.velocityX *= 0.7F;
            this.velocityZ *= 0.7F;
         }
      }
   }

   public static class DefaultFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public DefaultFactory(SpriteProvider _snowman) {
         this.spriteProvider = _snowman;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new SpellParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
      }
   }

   public static class EntityAmbientFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public EntityAmbientFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         Particle _snowmanxxxxxxxx = new SpellParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.spriteProvider);
         _snowmanxxxxxxxx.setColorAlpha(0.15F);
         _snowmanxxxxxxxx.setColor((float)_snowman, (float)_snowman, (float)_snowman);
         return _snowmanxxxxxxxx;
      }
   }

   public static class EntityFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider field_17873;

      public EntityFactory(SpriteProvider _snowman) {
         this.field_17873 = _snowman;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         Particle _snowmanxxxxxxxx = new SpellParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.field_17873);
         _snowmanxxxxxxxx.setColor((float)_snowman, (float)_snowman, (float)_snowman);
         return _snowmanxxxxxxxx;
      }
   }

   public static class InstantFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider field_17872;

      public InstantFactory(SpriteProvider _snowman) {
         this.field_17872 = _snowman;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new SpellParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.field_17872);
      }
   }

   public static class WitchFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider field_17875;

      public WitchFactory(SpriteProvider _snowman) {
         this.field_17875 = _snowman;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         SpellParticle _snowmanxxxxxxxx = new SpellParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.field_17875);
         float _snowmanxxxxxxxxx = _snowman.random.nextFloat() * 0.5F + 0.35F;
         _snowmanxxxxxxxx.setColor(1.0F * _snowmanxxxxxxxxx, 0.0F * _snowmanxxxxxxxxx, 1.0F * _snowmanxxxxxxxxx);
         return _snowmanxxxxxxxx;
      }
   }
}
