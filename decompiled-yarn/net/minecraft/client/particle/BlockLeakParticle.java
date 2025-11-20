package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class BlockLeakParticle extends SpriteBillboardParticle {
   private final Fluid fluid;
   protected boolean obsidianTear;

   private BlockLeakParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
      super(world, x, y, z);
      this.setBoundingBoxSpacing(0.01F, 0.01F);
      this.gravityStrength = 0.06F;
      this.fluid = fluid;
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
   }

   @Override
   public int getColorMultiplier(float tint) {
      return this.obsidianTear ? 240 : super.getColorMultiplier(tint);
   }

   @Override
   public void tick() {
      this.prevPosX = this.x;
      this.prevPosY = this.y;
      this.prevPosZ = this.z;
      this.updateAge();
      if (!this.dead) {
         this.velocityY = this.velocityY - (double)this.gravityStrength;
         this.move(this.velocityX, this.velocityY, this.velocityZ);
         this.updateVelocity();
         if (!this.dead) {
            this.velocityX *= 0.98F;
            this.velocityY *= 0.98F;
            this.velocityZ *= 0.98F;
            BlockPos _snowman = new BlockPos(this.x, this.y, this.z);
            FluidState _snowmanx = this.world.getFluidState(_snowman);
            if (_snowmanx.getFluid() == this.fluid && this.y < (double)((float)_snowman.getY() + _snowmanx.getHeight(this.world, _snowman))) {
               this.markDead();
            }
         }
      }
   }

   protected void updateAge() {
      if (this.maxAge-- <= 0) {
         this.markDead();
      }
   }

   protected void updateVelocity() {
   }

   static class ContinuousFalling extends BlockLeakParticle.Falling {
      protected final ParticleEffect nextParticle;

      private ContinuousFalling(ClientWorld _snowman, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
         super(_snowman, x, y, z, fluid);
         this.nextParticle = nextParticle;
      }

      @Override
      protected void updateVelocity() {
         if (this.onGround) {
            this.markDead();
            this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
         }
      }
   }

   static class Dripping extends BlockLeakParticle {
      private final ParticleEffect nextParticle;

      private Dripping(ClientWorld _snowman, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
         super(_snowman, x, y, z, fluid);
         this.nextParticle = nextParticle;
         this.gravityStrength *= 0.02F;
         this.maxAge = 40;
      }

      @Override
      protected void updateAge() {
         if (this.maxAge-- <= 0) {
            this.markDead();
            this.world.addParticle(this.nextParticle, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
         }
      }

      @Override
      protected void updateVelocity() {
         this.velocityX *= 0.02;
         this.velocityY *= 0.02;
         this.velocityZ *= 0.02;
      }
   }

   public static class DrippingHoneyFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public DrippingHoneyFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle.Dripping _snowmanxxxxxxxx = new BlockLeakParticle.Dripping(_snowman, _snowman, _snowman, _snowman, Fluids.EMPTY, ParticleTypes.FALLING_HONEY);
         _snowmanxxxxxxxx.gravityStrength *= 0.01F;
         _snowmanxxxxxxxx.maxAge = 100;
         _snowmanxxxxxxxx.setColor(0.622F, 0.508F, 0.082F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   static class DrippingLava extends BlockLeakParticle.Dripping {
      private DrippingLava(ClientWorld _snowman, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
         super(_snowman, x, y, z, fluid, nextParticle);
      }

      @Override
      protected void updateAge() {
         this.colorRed = 1.0F;
         this.colorGreen = 16.0F / (float)(40 - this.maxAge + 16);
         this.colorBlue = 4.0F / (float)(40 - this.maxAge + 8);
         super.updateAge();
      }
   }

   public static class DrippingLavaFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public DrippingLavaFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle.DrippingLava _snowmanxxxxxxxx = new BlockLeakParticle.DrippingLava(_snowman, _snowman, _snowman, _snowman, Fluids.LAVA, ParticleTypes.FALLING_LAVA);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class DrippingObsidianTearFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public DrippingObsidianTearFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle.Dripping _snowmanxxxxxxxx = new BlockLeakParticle.Dripping(_snowman, _snowman, _snowman, _snowman, Fluids.EMPTY, ParticleTypes.FALLING_OBSIDIAN_TEAR);
         _snowmanxxxxxxxx.obsidianTear = true;
         _snowmanxxxxxxxx.gravityStrength *= 0.01F;
         _snowmanxxxxxxxx.maxAge = 100;
         _snowmanxxxxxxxx.setColor(0.51171875F, 0.03125F, 0.890625F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class DrippingWaterFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public DrippingWaterFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle _snowmanxxxxxxxx = new BlockLeakParticle.Dripping(_snowman, _snowman, _snowman, _snowman, Fluids.WATER, ParticleTypes.FALLING_WATER);
         _snowmanxxxxxxxx.setColor(0.2F, 0.3F, 1.0F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   static class Falling extends BlockLeakParticle {
      private Falling(ClientWorld world, double x, double y, double z, Fluid fluid) {
         super(world, x, y, z, fluid);
         this.maxAge = (int)(64.0 / (Math.random() * 0.8 + 0.2));
      }

      @Override
      protected void updateVelocity() {
         if (this.onGround) {
            this.markDead();
         }
      }
   }

   static class FallingHoney extends BlockLeakParticle.ContinuousFalling {
      private FallingHoney(ClientWorld world, double x, double y, double z, Fluid fluid, ParticleEffect _snowman) {
         super(world, x, y, z, fluid, _snowman);
      }

      @Override
      protected void updateVelocity() {
         if (this.onGround) {
            this.markDead();
            this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            this.world
               .playSound(
                  this.x + 0.5,
                  this.y,
                  this.z + 0.5,
                  SoundEvents.BLOCK_BEEHIVE_DRIP,
                  SoundCategory.BLOCKS,
                  0.3F + this.world.random.nextFloat() * 2.0F / 3.0F,
                  1.0F,
                  false
               );
         }
      }
   }

   public static class FallingHoneyFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public FallingHoneyFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle _snowmanxxxxxxxx = new BlockLeakParticle.FallingHoney(_snowman, _snowman, _snowman, _snowman, Fluids.EMPTY, ParticleTypes.LANDING_HONEY);
         _snowmanxxxxxxxx.gravityStrength = 0.01F;
         _snowmanxxxxxxxx.setColor(0.582F, 0.448F, 0.082F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class FallingLavaFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public FallingLavaFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle _snowmanxxxxxxxx = new BlockLeakParticle.ContinuousFalling(_snowman, _snowman, _snowman, _snowman, Fluids.LAVA, ParticleTypes.LANDING_LAVA);
         _snowmanxxxxxxxx.setColor(1.0F, 0.2857143F, 0.083333336F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class FallingNectarFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public FallingNectarFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle _snowmanxxxxxxxx = new BlockLeakParticle.Falling(_snowman, _snowman, _snowman, _snowman, Fluids.EMPTY);
         _snowmanxxxxxxxx.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
         _snowmanxxxxxxxx.gravityStrength = 0.007F;
         _snowmanxxxxxxxx.setColor(0.92F, 0.782F, 0.72F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class FallingObsidianTearFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public FallingObsidianTearFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle _snowmanxxxxxxxx = new BlockLeakParticle.ContinuousFalling(_snowman, _snowman, _snowman, _snowman, Fluids.EMPTY, ParticleTypes.LANDING_OBSIDIAN_TEAR);
         _snowmanxxxxxxxx.obsidianTear = true;
         _snowmanxxxxxxxx.gravityStrength = 0.01F;
         _snowmanxxxxxxxx.setColor(0.51171875F, 0.03125F, 0.890625F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class FallingWaterFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public FallingWaterFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle _snowmanxxxxxxxx = new BlockLeakParticle.ContinuousFalling(_snowman, _snowman, _snowman, _snowman, Fluids.WATER, ParticleTypes.SPLASH);
         _snowmanxxxxxxxx.setColor(0.2F, 0.3F, 1.0F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   static class Landing extends BlockLeakParticle {
      private Landing(ClientWorld world, double x, double y, double z, Fluid fluid) {
         super(world, x, y, z, fluid);
         this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      }
   }

   public static class LandingHoneyFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public LandingHoneyFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle _snowmanxxxxxxxx = new BlockLeakParticle.Landing(_snowman, _snowman, _snowman, _snowman, Fluids.EMPTY);
         _snowmanxxxxxxxx.maxAge = (int)(128.0 / (Math.random() * 0.8 + 0.2));
         _snowmanxxxxxxxx.setColor(0.522F, 0.408F, 0.082F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class LandingLavaFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public LandingLavaFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle _snowmanxxxxxxxx = new BlockLeakParticle.Landing(_snowman, _snowman, _snowman, _snowman, Fluids.LAVA);
         _snowmanxxxxxxxx.setColor(1.0F, 0.2857143F, 0.083333336F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }

   public static class LandingObsidianTearFactory implements ParticleFactory<DefaultParticleType> {
      protected final SpriteProvider spriteProvider;

      public LandingObsidianTearFactory(SpriteProvider spriteProvider) {
         this.spriteProvider = spriteProvider;
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         BlockLeakParticle _snowmanxxxxxxxx = new BlockLeakParticle.Landing(_snowman, _snowman, _snowman, _snowman, Fluids.EMPTY);
         _snowmanxxxxxxxx.obsidianTear = true;
         _snowmanxxxxxxxx.maxAge = (int)(28.0 / (Math.random() * 0.8 + 0.2));
         _snowmanxxxxxxxx.setColor(0.51171875F, 0.03125F, 0.890625F);
         _snowmanxxxxxxxx.setSprite(this.spriteProvider);
         return _snowmanxxxxxxxx;
      }
   }
}
