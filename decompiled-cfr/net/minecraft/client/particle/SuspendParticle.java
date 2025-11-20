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
import net.minecraft.particle.DefaultParticleType;

public class SuspendParticle
extends SpriteBillboardParticle {
    private SuspendParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        float f;
        this.colorRed = f = this.random.nextFloat() * 0.1f + 0.2f;
        this.colorGreen = f;
        this.colorBlue = f;
        this.setBoundingBoxSpacing(0.02f, 0.02f);
        this.scale *= this.random.nextFloat() * 0.6f + 0.5f;
        this.velocityX *= (double)0.02f;
        this.velocityY *= (double)0.02f;
        this.velocityZ *= (double)0.02f;
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
            return;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= 0.99;
        this.velocityY *= 0.99;
        this.velocityZ *= 0.99;
    }

    public static class DolphinFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public DolphinFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            SuspendParticle suspendParticle = new SuspendParticle(clientWorld, d, d2, d3, d4, d5, d6);
            suspendParticle.setColor(0.3f, 0.5f, 1.0f);
            suspendParticle.setSprite(this.spriteProvider);
            suspendParticle.setColorAlpha(1.0f - clientWorld.random.nextFloat() * 0.7f);
            suspendParticle.setMaxAge(suspendParticle.getMaxAge() / 2);
            return suspendParticle;
        }
    }

    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            SuspendParticle suspendParticle = new SuspendParticle(clientWorld, d, d2, d3, d4, d5, d6);
            suspendParticle.setSprite(this.spriteProvider);
            suspendParticle.setColor(1.0f, 1.0f, 1.0f);
            suspendParticle.setMaxAge(3 + clientWorld.getRandom().nextInt(5));
            return suspendParticle;
        }
    }

    public static class HappyVillagerFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public HappyVillagerFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            SuspendParticle suspendParticle = new SuspendParticle(clientWorld, d, d2, d3, d4, d5, d6);
            suspendParticle.setSprite(this.spriteProvider);
            suspendParticle.setColor(1.0f, 1.0f, 1.0f);
            return suspendParticle;
        }
    }

    public static class MyceliumFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public MyceliumFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            SuspendParticle suspendParticle = new SuspendParticle(clientWorld, d, d2, d3, d4, d5, d6);
            suspendParticle.setSprite(this.spriteProvider);
            return suspendParticle;
        }
    }
}

