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

public class CampfireSmokeParticle
extends SpriteBillboardParticle {
    private CampfireSmokeParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, boolean signalFire) {
        super(world, x, y, z);
        this.scale(3.0f);
        this.setBoundingBoxSpacing(0.25f, 0.25f);
        this.maxAge = signalFire ? this.random.nextInt(50) + 280 : this.random.nextInt(50) + 80;
        this.gravityStrength = 3.0E-6f;
        this.velocityX = velocityX;
        this.velocityY = velocityY + (double)(this.random.nextFloat() / 500.0f);
        this.velocityZ = velocityZ;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge || this.colorAlpha <= 0.0f) {
            this.markDead();
            return;
        }
        this.velocityX += (double)(this.random.nextFloat() / 5000.0f * (float)(this.random.nextBoolean() ? 1 : -1));
        this.velocityZ += (double)(this.random.nextFloat() / 5000.0f * (float)(this.random.nextBoolean() ? 1 : -1));
        this.velocityY -= (double)this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        if (this.age >= this.maxAge - 60 && this.colorAlpha > 0.01f) {
            this.colorAlpha -= 0.015f;
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class SignalSmokeFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public SignalSmokeFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            CampfireSmokeParticle campfireSmokeParticle = new CampfireSmokeParticle(clientWorld, d, d2, d3, d4, d5, d6, true);
            campfireSmokeParticle.setColorAlpha(0.95f);
            campfireSmokeParticle.setSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }

    public static class CosySmokeFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public CosySmokeFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            CampfireSmokeParticle campfireSmokeParticle = new CampfireSmokeParticle(clientWorld, d, d2, d3, d4, d5, d6, false);
            campfireSmokeParticle.setColorAlpha(0.9f);
            campfireSmokeParticle.setSprite(this.spriteProvider);
            return campfireSmokeParticle;
        }
    }
}

