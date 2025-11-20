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

public class EmotionParticle
extends SpriteBillboardParticle {
    private EmotionParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.velocityX *= (double)0.01f;
        this.velocityY *= (double)0.01f;
        this.velocityZ *= (double)0.01f;
        this.velocityY += 0.1;
        this.scale *= 1.5f;
        this.maxAge = 16;
        this.collidesWithWorld = false;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
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
        if (this.y == this.prevPosY) {
            this.velocityX *= 1.1;
            this.velocityZ *= 1.1;
        }
        this.velocityX *= (double)0.86f;
        this.velocityY *= (double)0.86f;
        this.velocityZ *= (double)0.86f;
        if (this.onGround) {
            this.velocityX *= (double)0.7f;
            this.velocityZ *= (double)0.7f;
        }
    }

    public static class AngryVillagerFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public AngryVillagerFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            EmotionParticle emotionParticle = new EmotionParticle(clientWorld, d, d2 + 0.5, d3);
            emotionParticle.setSprite(this.spriteProvider);
            emotionParticle.setColor(1.0f, 1.0f, 1.0f);
            return emotionParticle;
        }
    }

    public static class HeartFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public HeartFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            EmotionParticle emotionParticle = new EmotionParticle(clientWorld, d, d2, d3);
            emotionParticle.setSprite(this.spriteProvider);
            return emotionParticle;
        }
    }
}

