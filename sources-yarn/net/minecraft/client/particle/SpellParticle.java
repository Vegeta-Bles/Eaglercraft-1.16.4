package net.minecraft.client.particle;

import java.util.Random;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
   public static class DefaultFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public DefaultFactory(SpriteProvider arg) {
         this.spriteProvider = arg;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         return new SpellParticle(arg2, d, e, f, g, h, i, this.spriteProvider);
      }
   }

   @Environment(EnvType.CLIENT)
   public static class EntityAmbientFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public EntityAmbientFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         Particle lv = new SpellParticle(arg2, d, e, f, g, h, i, this.spriteProvider);
         lv.setColorAlpha(0.15F);
         lv.setColor((float)g, (float)h, (float)i);
         return lv;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class EntityFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider field_17873;

      public EntityFactory(SpriteProvider arg) {
         this.field_17873 = arg;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         Particle lv = new SpellParticle(arg2, d, e, f, g, h, i, this.field_17873);
         lv.setColor((float)g, (float)h, (float)i);
         return lv;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class InstantFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider field_17872;

      public InstantFactory(SpriteProvider arg) {
         this.field_17872 = arg;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         return new SpellParticle(arg2, d, e, f, g, h, i, this.field_17872);
      }
   }

   @Environment(EnvType.CLIENT)
   public static class WitchFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider field_17875;

      public WitchFactory(SpriteProvider arg) {
         this.field_17875 = arg;
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         SpellParticle lv = new SpellParticle(arg2, d, e, f, g, h, i, this.field_17875);
         float j = arg2.random.nextFloat() * 0.5F + 0.35F;
         lv.setColor(1.0F * j, 0.0F * j, 1.0F * j);
         return lv;
      }
   }
}
