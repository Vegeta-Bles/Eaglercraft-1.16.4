/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
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

public class BlockLeakParticle
extends SpriteBillboardParticle {
    private final Fluid fluid;
    protected boolean obsidianTear;

    private BlockLeakParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.gravityStrength = 0.06f;
        this.fluid = fluid;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getColorMultiplier(float tint) {
        if (this.obsidianTear) {
            return 240;
        }
        return super.getColorMultiplier(tint);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.updateAge();
        if (this.dead) {
            return;
        }
        this.velocityY -= (double)this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.updateVelocity();
        if (this.dead) {
            return;
        }
        this.velocityX *= (double)0.98f;
        this.velocityY *= (double)0.98f;
        this.velocityZ *= (double)0.98f;
        BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
        FluidState _snowman2 = this.world.getFluidState(blockPos);
        if (_snowman2.getFluid() == this.fluid && this.y < (double)((float)blockPos.getY() + _snowman2.getHeight(this.world, blockPos))) {
            this.markDead();
        }
    }

    protected void updateAge() {
        if (this.maxAge-- <= 0) {
            this.markDead();
        }
    }

    protected void updateVelocity() {
    }

    public static class LandingObsidianTearFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public LandingObsidianTearFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Landing landing = new Landing(clientWorld, d, d2, d3, Fluids.EMPTY);
            landing.obsidianTear = true;
            landing.maxAge = (int)(28.0 / (Math.random() * 0.8 + 0.2));
            landing.setColor(0.51171875f, 0.03125f, 0.890625f);
            landing.setSprite(this.spriteProvider);
            return landing;
        }
    }

    public static class FallingObsidianTearFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public FallingObsidianTearFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            ContinuousFalling continuousFalling = new ContinuousFalling(clientWorld, d, d2, d3, Fluids.EMPTY, ParticleTypes.LANDING_OBSIDIAN_TEAR);
            continuousFalling.obsidianTear = true;
            continuousFalling.gravityStrength = 0.01f;
            continuousFalling.setColor(0.51171875f, 0.03125f, 0.890625f);
            continuousFalling.setSprite(this.spriteProvider);
            return continuousFalling;
        }
    }

    public static class DrippingObsidianTearFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public DrippingObsidianTearFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Dripping dripping = new Dripping(clientWorld, d, d2, d3, Fluids.EMPTY, ParticleTypes.FALLING_OBSIDIAN_TEAR);
            dripping.obsidianTear = true;
            dripping.gravityStrength *= 0.01f;
            dripping.maxAge = 100;
            dripping.setColor(0.51171875f, 0.03125f, 0.890625f);
            dripping.setSprite(this.spriteProvider);
            return dripping;
        }
    }

    public static class FallingNectarFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public FallingNectarFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Falling falling = new Falling(clientWorld, d, d2, d3, Fluids.EMPTY);
            falling.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
            falling.gravityStrength = 0.007f;
            falling.setColor(0.92f, 0.782f, 0.72f);
            falling.setSprite(this.spriteProvider);
            return falling;
        }
    }

    public static class LandingHoneyFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public LandingHoneyFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Landing landing = new Landing(clientWorld, d, d2, d3, Fluids.EMPTY);
            landing.maxAge = (int)(128.0 / (Math.random() * 0.8 + 0.2));
            landing.setColor(0.522f, 0.408f, 0.082f);
            landing.setSprite(this.spriteProvider);
            return landing;
        }
    }

    public static class FallingHoneyFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public FallingHoneyFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            FallingHoney fallingHoney = new FallingHoney(clientWorld, d, d2, d3, Fluids.EMPTY, ParticleTypes.LANDING_HONEY);
            fallingHoney.gravityStrength = 0.01f;
            fallingHoney.setColor(0.582f, 0.448f, 0.082f);
            fallingHoney.setSprite(this.spriteProvider);
            return fallingHoney;
        }
    }

    public static class DrippingHoneyFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public DrippingHoneyFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Dripping dripping = new Dripping(clientWorld, d, d2, d3, Fluids.EMPTY, ParticleTypes.FALLING_HONEY);
            dripping.gravityStrength *= 0.01f;
            dripping.maxAge = 100;
            dripping.setColor(0.622f, 0.508f, 0.082f);
            dripping.setSprite(this.spriteProvider);
            return dripping;
        }
    }

    public static class LandingLavaFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public LandingLavaFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Landing landing = new Landing(clientWorld, d, d2, d3, Fluids.LAVA);
            landing.setColor(1.0f, 0.2857143f, 0.083333336f);
            landing.setSprite(this.spriteProvider);
            return landing;
        }
    }

    public static class FallingLavaFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public FallingLavaFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            ContinuousFalling continuousFalling = new ContinuousFalling(clientWorld, d, d2, d3, Fluids.LAVA, ParticleTypes.LANDING_LAVA);
            continuousFalling.setColor(1.0f, 0.2857143f, 0.083333336f);
            continuousFalling.setSprite(this.spriteProvider);
            return continuousFalling;
        }
    }

    public static class DrippingLavaFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public DrippingLavaFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            DrippingLava drippingLava = new DrippingLava(clientWorld, d, d2, d3, Fluids.LAVA, ParticleTypes.FALLING_LAVA);
            drippingLava.setSprite(this.spriteProvider);
            return drippingLava;
        }
    }

    public static class FallingWaterFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public FallingWaterFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            ContinuousFalling continuousFalling = new ContinuousFalling(clientWorld, d, d2, d3, Fluids.WATER, ParticleTypes.SPLASH);
            continuousFalling.setColor(0.2f, 0.3f, 1.0f);
            continuousFalling.setSprite(this.spriteProvider);
            return continuousFalling;
        }
    }

    public static class DrippingWaterFactory
    implements ParticleFactory<DefaultParticleType> {
        protected final SpriteProvider spriteProvider;

        public DrippingWaterFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Dripping dripping = new Dripping(clientWorld, d, d2, d3, Fluids.WATER, ParticleTypes.FALLING_WATER);
            dripping.setColor(0.2f, 0.3f, 1.0f);
            dripping.setSprite(this.spriteProvider);
            return dripping;
        }
    }

    static class Landing
    extends BlockLeakParticle {
        private Landing(ClientWorld world, double x, double y, double z, Fluid fluid) {
            super(world, x, y, z, fluid);
            this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        }
    }

    static class Falling
    extends BlockLeakParticle {
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

    static class FallingHoney
    extends ContinuousFalling {
        private FallingHoney(ClientWorld world, double x, double y, double z, Fluid fluid, ParticleEffect particleEffect) {
            super(world, x, y, z, fluid, particleEffect);
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.playSound(this.x + 0.5, this.y, this.z + 0.5, SoundEvents.BLOCK_BEEHIVE_DRIP, SoundCategory.BLOCKS, 0.3f + this.world.random.nextFloat() * 2.0f / 3.0f, 1.0f, false);
            }
        }
    }

    static class ContinuousFalling
    extends Falling {
        protected final ParticleEffect nextParticle;

        private ContinuousFalling(ClientWorld clientWorld, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(clientWorld, x, y, z, fluid);
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

    static class DrippingLava
    extends Dripping {
        private DrippingLava(ClientWorld clientWorld, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(clientWorld, x, y, z, fluid, nextParticle);
        }

        @Override
        protected void updateAge() {
            this.colorRed = 1.0f;
            this.colorGreen = 16.0f / (float)(40 - this.maxAge + 16);
            this.colorBlue = 4.0f / (float)(40 - this.maxAge + 8);
            super.updateAge();
        }
    }

    static class Dripping
    extends BlockLeakParticle {
        private final ParticleEffect nextParticle;

        private Dripping(ClientWorld clientWorld, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(clientWorld, x, y, z, fluid);
            this.nextParticle = nextParticle;
            this.gravityStrength *= 0.02f;
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
}

