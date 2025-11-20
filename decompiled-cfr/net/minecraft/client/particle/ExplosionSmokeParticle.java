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

public class ExplosionSmokeParticle
extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    protected ExplosionSmokeParticle(ClientWorld world, double x, double y, double z, double d, double d2, double d3, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.velocityX = d + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.velocityY = d2 + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.velocityZ = d3 + (Math.random() * 2.0 - 1.0) * (double)0.05f;
        this.colorRed = _snowman = this.random.nextFloat() * 0.3f + 0.7f;
        this.colorGreen = _snowman;
        this.colorBlue = _snowman;
        this.scale = 0.1f * (this.random.nextFloat() * this.random.nextFloat() * 6.0f + 1.0f);
        this.maxAge = (int)(16.0 / ((double)this.random.nextFloat() * 0.8 + 0.2)) + 2;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }
        this.setSpriteForAge(this.spriteProvider);
        this.velocityY += 0.004;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= (double)0.9f;
        this.velocityY *= (double)0.9f;
        this.velocityZ *= (double)0.9f;
        if (this.onGround) {
            this.velocityX *= (double)0.7f;
            this.velocityZ *= (double)0.7f;
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
            return new ExplosionSmokeParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteProvider);
        }
    }
}

