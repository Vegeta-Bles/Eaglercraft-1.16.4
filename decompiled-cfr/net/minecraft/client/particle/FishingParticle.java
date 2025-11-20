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

public class FishingParticle
extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    private FishingParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.spriteProvider = spriteProvider;
        this.velocityX *= (double)0.3f;
        this.velocityY = Math.random() * (double)0.2f + (double)0.1f;
        this.velocityZ *= (double)0.3f;
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.maxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.setSpriteForAge(spriteProvider);
        this.gravityStrength = 0.0f;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
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
        int n = 60 - this.maxAge;
        if (this.maxAge-- <= 0) {
            this.markDead();
            return;
        }
        this.velocityY -= (double)this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= (double)0.98f;
        this.velocityY *= (double)0.98f;
        this.velocityZ *= (double)0.98f;
        float _snowman2 = (float)n * 0.001f;
        this.setBoundingBoxSpacing(_snowman2, _snowman2);
        this.setSprite(this.spriteProvider.getSprite(n % 4, 4));
    }

    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new FishingParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteProvider);
        }
    }
}

