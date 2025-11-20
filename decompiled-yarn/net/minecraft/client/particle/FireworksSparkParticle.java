package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.FireworkItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class FireworksSparkParticle {
   static class Explosion extends AnimatedParticle {
      private boolean trail;
      private boolean flicker;
      private final ParticleManager particleManager;
      private float field_3801;
      private float field_3800;
      private float field_3799;
      private boolean field_3802;

      private Explosion(
         ClientWorld world,
         double x,
         double y,
         double z,
         double velocityX,
         double velocityY,
         double velocityZ,
         ParticleManager particleManager,
         SpriteProvider spriteProvider
      ) {
         super(world, x, y, z, spriteProvider, -0.004F);
         this.velocityX = velocityX;
         this.velocityY = velocityY;
         this.velocityZ = velocityZ;
         this.particleManager = particleManager;
         this.scale *= 0.75F;
         this.maxAge = 48 + this.random.nextInt(12);
         this.setSpriteForAge(spriteProvider);
      }

      public void setTrail(boolean trail) {
         this.trail = trail;
      }

      public void setFlicker(boolean flicker) {
         this.flicker = flicker;
      }

      @Override
      public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
         if (!this.flicker || this.age < this.maxAge / 3 || (this.age + this.maxAge) / 3 % 2 == 0) {
            super.buildGeometry(vertexConsumer, camera, tickDelta);
         }
      }

      @Override
      public void tick() {
         super.tick();
         if (this.trail && this.age < this.maxAge / 2 && (this.age + this.maxAge) % 2 == 0) {
            FireworksSparkParticle.Explosion _snowman = new FireworksSparkParticle.Explosion(
               this.world, this.x, this.y, this.z, 0.0, 0.0, 0.0, this.particleManager, this.spriteProvider
            );
            _snowman.setColorAlpha(0.99F);
            _snowman.setColor(this.colorRed, this.colorGreen, this.colorBlue);
            _snowman.age = _snowman.maxAge / 2;
            if (this.field_3802) {
               _snowman.field_3802 = true;
               _snowman.field_3801 = this.field_3801;
               _snowman.field_3800 = this.field_3800;
               _snowman.field_3799 = this.field_3799;
            }

            _snowman.flicker = this.flicker;
            this.particleManager.addParticle(_snowman);
         }
      }
   }

   public static class ExplosionFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public ExplosionFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         FireworksSparkParticle.Explosion _snowmanxxxxxxxx = new FireworksSparkParticle.Explosion(
            _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, MinecraftClient.getInstance().particleManager, this.spriteProvider
         );
         _snowmanxxxxxxxx.setColorAlpha(0.99F);
         return _snowmanxxxxxxxx;
      }
   }

   public static class FireworkParticle extends NoRenderParticle {
      private int age;
      private final ParticleManager particleManager;
      private ListTag explosions;
      private boolean flicker;

      public FireworkParticle(
         ClientWorld world,
         double x,
         double y,
         double z,
         double velocityX,
         double velocityY,
         double velocityZ,
         ParticleManager particleManager,
         @Nullable CompoundTag tag
      ) {
         super(world, x, y, z);
         this.velocityX = velocityX;
         this.velocityY = velocityY;
         this.velocityZ = velocityZ;
         this.particleManager = particleManager;
         this.maxAge = 8;
         if (tag != null) {
            this.explosions = tag.getList("Explosions", 10);
            if (this.explosions.isEmpty()) {
               this.explosions = null;
            } else {
               this.maxAge = this.explosions.size() * 2 - 1;

               for (int _snowman = 0; _snowman < this.explosions.size(); _snowman++) {
                  CompoundTag _snowmanx = this.explosions.getCompound(_snowman);
                  if (_snowmanx.getBoolean("Flicker")) {
                     this.flicker = true;
                     this.maxAge += 15;
                     break;
                  }
               }
            }
         }
      }

      @Override
      public void tick() {
         if (this.age == 0 && this.explosions != null) {
            boolean _snowman = this.isFar();
            boolean _snowmanx = false;
            if (this.explosions.size() >= 3) {
               _snowmanx = true;
            } else {
               for (int _snowmanxx = 0; _snowmanxx < this.explosions.size(); _snowmanxx++) {
                  CompoundTag _snowmanxxx = this.explosions.getCompound(_snowmanxx);
                  if (FireworkItem.Type.byId(_snowmanxxx.getByte("Type")) == FireworkItem.Type.LARGE_BALL) {
                     _snowmanx = true;
                     break;
                  }
               }
            }

            SoundEvent _snowmanxxx;
            if (_snowmanx) {
               _snowmanxxx = _snowman ? SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_ROCKET_LARGE_BLAST;
            } else {
               _snowmanxxx = _snowman ? SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST_FAR : SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST;
            }

            this.world.playSound(this.x, this.y, this.z, _snowmanxxx, SoundCategory.AMBIENT, 20.0F, 0.95F + this.random.nextFloat() * 0.1F, true);
         }

         if (this.age % 2 == 0 && this.explosions != null && this.age / 2 < this.explosions.size()) {
            int _snowmanxxx = this.age / 2;
            CompoundTag _snowmanxxxx = this.explosions.getCompound(_snowmanxxx);
            FireworkItem.Type _snowmanxxxxx = FireworkItem.Type.byId(_snowmanxxxx.getByte("Type"));
            boolean _snowmanxxxxxx = _snowmanxxxx.getBoolean("Trail");
            boolean _snowmanxxxxxxx = _snowmanxxxx.getBoolean("Flicker");
            int[] _snowmanxxxxxxxx = _snowmanxxxx.getIntArray("Colors");
            int[] _snowmanxxxxxxxxx = _snowmanxxxx.getIntArray("FadeColors");
            if (_snowmanxxxxxxxx.length == 0) {
               _snowmanxxxxxxxx = new int[]{DyeColor.BLACK.getFireworkColor()};
            }

            switch (_snowmanxxxxx) {
               case SMALL_BALL:
               default:
                  this.explodeBall(0.25, 2, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
                  break;
               case LARGE_BALL:
                  this.explodeBall(0.5, 4, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
                  break;
               case STAR:
                  this.explodeStar(
                     0.5,
                     new double[][]{
                        {0.0, 1.0},
                        {0.3455, 0.309},
                        {0.9511, 0.309},
                        {0.3795918367346939, -0.12653061224489795},
                        {0.6122448979591837, -0.8040816326530612},
                        {0.0, -0.35918367346938773}
                     },
                     _snowmanxxxxxxxx,
                     _snowmanxxxxxxxxx,
                     _snowmanxxxxxx,
                     _snowmanxxxxxxx,
                     false
                  );
                  break;
               case CREEPER:
                  this.explodeStar(
                     0.5,
                     new double[][]{
                        {0.0, 0.2},
                        {0.2, 0.2},
                        {0.2, 0.6},
                        {0.6, 0.6},
                        {0.6, 0.2},
                        {0.2, 0.2},
                        {0.2, 0.0},
                        {0.4, 0.0},
                        {0.4, -0.6},
                        {0.2, -0.6},
                        {0.2, -0.4},
                        {0.0, -0.4}
                     },
                     _snowmanxxxxxxxx,
                     _snowmanxxxxxxxxx,
                     _snowmanxxxxxx,
                     _snowmanxxxxxxx,
                     true
                  );
                  break;
               case BURST:
                  this.explodeBurst(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
            }

            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx[0];
            float _snowmanxxxxxxxxxxx = (float)((_snowmanxxxxxxxxxx & 0xFF0000) >> 16) / 255.0F;
            float _snowmanxxxxxxxxxxxx = (float)((_snowmanxxxxxxxxxx & 0xFF00) >> 8) / 255.0F;
            float _snowmanxxxxxxxxxxxxx = (float)((_snowmanxxxxxxxxxx & 0xFF) >> 0) / 255.0F;
            Particle _snowmanxxxxxxxxxxxxxx = this.particleManager.addParticle(ParticleTypes.FLASH, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            _snowmanxxxxxxxxxxxxxx.setColor(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
         }

         this.age++;
         if (this.age > this.maxAge) {
            if (this.flicker) {
               boolean _snowmanxxx = this.isFar();
               SoundEvent _snowmanxxxx = _snowmanxxx ? SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR : SoundEvents.ENTITY_FIREWORK_ROCKET_TWINKLE;
               this.world.playSound(this.x, this.y, this.z, _snowmanxxxx, SoundCategory.AMBIENT, 20.0F, 0.9F + this.random.nextFloat() * 0.15F, true);
            }

            this.markDead();
         }
      }

      private boolean isFar() {
         MinecraftClient _snowman = MinecraftClient.getInstance();
         return _snowman.gameRenderer.getCamera().getPos().squaredDistanceTo(this.x, this.y, this.z) >= 256.0;
      }

      private void addExplosionParticle(
         double x, double y, double z, double velocityX, double velocityY, double velocityZ, int[] colors, int[] fadeColors, boolean trail, boolean flicker
      ) {
         FireworksSparkParticle.Explosion _snowman = (FireworksSparkParticle.Explosion)this.particleManager
            .addParticle(ParticleTypes.FIREWORK, x, y, z, velocityX, velocityY, velocityZ);
         _snowman.setTrail(trail);
         _snowman.setFlicker(flicker);
         _snowman.setColorAlpha(0.99F);
         int _snowmanx = this.random.nextInt(colors.length);
         _snowman.setColor(colors[_snowmanx]);
         if (fadeColors.length > 0) {
            _snowman.setTargetColor(Util.getRandom(fadeColors, this.random));
         }
      }

      private void explodeBall(double size, int amount, int[] colors, int[] fadeColors, boolean trail, boolean flicker) {
         double _snowman = this.x;
         double _snowmanx = this.y;
         double _snowmanxx = this.z;

         for (int _snowmanxxx = -amount; _snowmanxxx <= amount; _snowmanxxx++) {
            for (int _snowmanxxxx = -amount; _snowmanxxxx <= amount; _snowmanxxxx++) {
               for (int _snowmanxxxxx = -amount; _snowmanxxxxx <= amount; _snowmanxxxxx++) {
                  double _snowmanxxxxxx = (double)_snowmanxxxx + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double _snowmanxxxxxxx = (double)_snowmanxxx + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double _snowmanxxxxxxxx = (double)_snowmanxxxxx + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double _snowmanxxxxxxxxx = (double)MathHelper.sqrt(_snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx) / size
                     + this.random.nextGaussian() * 0.05;
                  this.addExplosionParticle(_snowman, _snowmanx, _snowmanxx, _snowmanxxxxxx / _snowmanxxxxxxxxx, _snowmanxxxxxxx / _snowmanxxxxxxxxx, _snowmanxxxxxxxx / _snowmanxxxxxxxxx, colors, fadeColors, trail, flicker);
                  if (_snowmanxxx != -amount && _snowmanxxx != amount && _snowmanxxxx != -amount && _snowmanxxxx != amount) {
                     _snowmanxxxxx += amount * 2 - 1;
                  }
               }
            }
         }
      }

      private void explodeStar(double size, double[][] pattern, int[] colors, int[] fadeColors, boolean trail, boolean flicker, boolean keepShape) {
         double _snowman = pattern[0][0];
         double _snowmanx = pattern[0][1];
         this.addExplosionParticle(this.x, this.y, this.z, _snowman * size, _snowmanx * size, 0.0, colors, fadeColors, trail, flicker);
         float _snowmanxx = this.random.nextFloat() * (float) Math.PI;
         double _snowmanxxx = keepShape ? 0.034 : 0.34;

         for (int _snowmanxxxx = 0; _snowmanxxxx < 3; _snowmanxxxx++) {
            double _snowmanxxxxx = (double)_snowmanxx + (double)((float)_snowmanxxxx * (float) Math.PI) * _snowmanxxx;
            double _snowmanxxxxxx = _snowman;
            double _snowmanxxxxxxx = _snowmanx;

            for (int _snowmanxxxxxxxx = 1; _snowmanxxxxxxxx < pattern.length; _snowmanxxxxxxxx++) {
               double _snowmanxxxxxxxxx = pattern[_snowmanxxxxxxxx][0];
               double _snowmanxxxxxxxxxx = pattern[_snowmanxxxxxxxx][1];

               for (double _snowmanxxxxxxxxxxx = 0.25; _snowmanxxxxxxxxxxx <= 1.0; _snowmanxxxxxxxxxxx += 0.25) {
                  double _snowmanxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxx) * size;
                  double _snowmanxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxx) * size;
                  double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx * Math.sin(_snowmanxxxxx);
                  _snowmanxxxxxxxxxxxx *= Math.cos(_snowmanxxxxx);

                  for (double _snowmanxxxxxxxxxxxxxxx = -1.0; _snowmanxxxxxxxxxxxxxxx <= 1.0; _snowmanxxxxxxxxxxxxxxx += 2.0) {
                     this.addExplosionParticle(
                        this.x,
                        this.y,
                        this.z,
                        _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx,
                        _snowmanxxxxxxxxxxxxx,
                        _snowmanxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx,
                        colors,
                        fadeColors,
                        trail,
                        flicker
                     );
                  }
               }

               _snowmanxxxxxx = _snowmanxxxxxxxxx;
               _snowmanxxxxxxx = _snowmanxxxxxxxxxx;
            }
         }
      }

      private void explodeBurst(int[] colors, int[] fadeColors, boolean trail, boolean flocker) {
         double _snowman = this.random.nextGaussian() * 0.05;
         double _snowmanx = this.random.nextGaussian() * 0.05;

         for (int _snowmanxx = 0; _snowmanxx < 70; _snowmanxx++) {
            double _snowmanxxx = this.velocityX * 0.5 + this.random.nextGaussian() * 0.15 + _snowman;
            double _snowmanxxxx = this.velocityZ * 0.5 + this.random.nextGaussian() * 0.15 + _snowmanx;
            double _snowmanxxxxx = this.velocityY * 0.5 + this.random.nextDouble() * 0.5;
            this.addExplosionParticle(this.x, this.y, this.z, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, colors, fadeColors, trail, flocker);
         }
      }
   }

   public static class Flash extends SpriteBillboardParticle {
      private Flash(ClientWorld world, double x, double y, double z) {
         super(world, x, y, z);
         this.maxAge = 4;
      }

      @Override
      public ParticleTextureSheet getType() {
         return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
      }

      @Override
      public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
         this.setColorAlpha(0.6F - ((float)this.age + tickDelta - 1.0F) * 0.25F * 0.5F);
         super.buildGeometry(vertexConsumer, camera, tickDelta);
      }

      @Override
      public float getSize(float tickDelta) {
         return 7.1F * MathHelper.sin(((float)this.age + tickDelta - 1.0F) * 0.25F * (float) Math.PI);
      }
   }

   public static class FlashFactory implements ParticleFactory<DefaultParticleType> {
      private final SpriteProvider spriteProvider;

      public FlashFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         FireworksSparkParticle.Flash _snowmanxxxxxxxx = new FireworksSparkParticle.Flash(_snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
