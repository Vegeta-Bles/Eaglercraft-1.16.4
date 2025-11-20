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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class CloudParticle
extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    private CloudParticle(ClientWorld world, double x, double y, double z, double d, double d2, double d3, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.spriteProvider = spriteProvider;
        float f = 2.5f;
        this.velocityX *= (double)0.1f;
        this.velocityY *= (double)0.1f;
        this.velocityZ *= (double)0.1f;
        this.velocityX += d;
        this.velocityY += d2;
        this.velocityZ += d3;
        this.colorRed = _snowman = 1.0f - (float)(Math.random() * (double)0.3f);
        this.colorGreen = _snowman;
        this.colorBlue = _snowman;
        this.scale *= 1.875f;
        int _snowman2 = (int)(8.0 / (Math.random() * 0.8 + 0.3));
        this.maxAge = (int)Math.max((float)_snowman2 * 2.5f, 1.0f);
        this.collidesWithWorld = false;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
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
        this.setSpriteForAge(this.spriteProvider);
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= (double)0.96f;
        this.velocityY *= (double)0.96f;
        this.velocityZ *= (double)0.96f;
        PlayerEntity playerEntity = this.world.getClosestPlayer(this.x, this.y, this.z, 2.0, false);
        if (playerEntity != null && this.y > (_snowman = playerEntity.getY())) {
            this.y += (_snowman - this.y) * 0.2;
            this.velocityY += (playerEntity.getVelocity().y - this.velocityY) * 0.2;
            this.setPos(this.x, this.y, this.z);
        }
        if (this.onGround) {
            this.velocityX *= (double)0.7f;
            this.velocityZ *= (double)0.7f;
        }
    }

    public static class SneezeFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public SneezeFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            CloudParticle cloudParticle = new CloudParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteProvider);
            cloudParticle.setColor(200.0f, 50.0f, 120.0f);
            cloudParticle.setColorAlpha(0.4f);
            return cloudParticle;
        }
    }

    public static class CloudFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public CloudFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new CloudParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteProvider);
        }
    }
}

