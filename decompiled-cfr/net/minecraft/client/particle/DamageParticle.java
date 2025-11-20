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
import net.minecraft.util.math.MathHelper;

public class DamageParticle
extends SpriteBillboardParticle {
    private DamageParticle(ClientWorld world, double x, double y, double z, double d, double d2, double d3) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.velocityX *= (double)0.1f;
        this.velocityY *= (double)0.1f;
        this.velocityZ *= (double)0.1f;
        this.velocityX += d * 0.4;
        this.velocityY += d2 * 0.4;
        this.velocityZ += d3 * 0.4;
        this.colorRed = _snowman = (float)(Math.random() * (double)0.3f + (double)0.6f);
        this.colorGreen = _snowman;
        this.colorBlue = _snowman;
        this.scale *= 0.75f;
        this.maxAge = Math.max((int)(6.0 / (Math.random() * 0.8 + 0.6)), 1);
        this.collidesWithWorld = false;
        this.tick();
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0f, 0.0f, 1.0f);
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
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.colorGreen = (float)((double)this.colorGreen * 0.96);
        this.colorBlue = (float)((double)this.colorBlue * 0.9);
        this.velocityX *= (double)0.7f;
        this.velocityY *= (double)0.7f;
        this.velocityZ *= (double)0.7f;
        this.velocityY -= (double)0.02f;
        if (this.onGround) {
            this.velocityX *= (double)0.7f;
            this.velocityZ *= (double)0.7f;
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public static class DefaultFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public DefaultFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            DamageParticle damageParticle = new DamageParticle(clientWorld, d, d2, d3, d4, d5 + 1.0, d6);
            damageParticle.setMaxAge(20);
            damageParticle.setSprite(this.spriteProvider);
            return damageParticle;
        }
    }

    public static class EnchantedHitFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public EnchantedHitFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            DamageParticle damageParticle = new DamageParticle(clientWorld, d, d2, d3, d4, d5, d6);
            damageParticle.colorRed *= 0.3f;
            damageParticle.colorGreen *= 0.8f;
            damageParticle.setSprite(this.spriteProvider);
            return damageParticle;
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
            DamageParticle damageParticle = new DamageParticle(clientWorld, d, d2, d3, d4, d5, d6);
            damageParticle.setSprite(this.spriteProvider);
            return damageParticle;
        }
    }
}

