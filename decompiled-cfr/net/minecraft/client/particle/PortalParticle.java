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

public class PortalParticle
extends SpriteBillboardParticle {
    private final double startX;
    private final double startY;
    private final double startZ;

    protected PortalParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3);
        this.velocityX = d4;
        this.velocityY = d5;
        this.velocityZ = d6;
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.startX = this.x;
        this.startY = this.y;
        this.startZ = this.z;
        this.scale = 0.1f * (this.random.nextFloat() * 0.2f + 0.5f);
        float f = this.random.nextFloat() * 0.6f + 0.4f;
        this.colorRed = f * 0.9f;
        this.colorGreen = f * 0.3f;
        this.colorBlue = f;
        this.maxAge = (int)(Math.random() * 10.0) + 40;
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
    public float getSize(float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        f = 1.0f - f;
        f *= f;
        f = 1.0f - f;
        return this.scale * f;
    }

    @Override
    public int getColorMultiplier(float tint) {
        int n = super.getColorMultiplier(tint);
        float _snowman2 = (float)this.age / (float)this.maxAge;
        _snowman2 *= _snowman2;
        _snowman2 *= _snowman2;
        _snowman = n & 0xFF;
        _snowman = n >> 16 & 0xFF;
        if ((_snowman += (int)(_snowman2 * 15.0f * 16.0f)) > 240) {
            _snowman = 240;
        }
        return _snowman | _snowman << 16;
    }

    @Override
    public void tick() {
        float f;
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }
        _snowman = f = (float)this.age / (float)this.maxAge;
        f = -f + f * f * 2.0f;
        f = 1.0f - f;
        this.x = this.startX + this.velocityX * (double)f;
        this.y = this.startY + this.velocityY * (double)f + (double)(1.0f - _snowman);
        this.z = this.startZ + this.velocityZ * (double)f;
    }

    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            PortalParticle portalParticle = new PortalParticle(clientWorld, d, d2, d3, d4, d5, d6);
            portalParticle.setSprite(this.spriteProvider);
            return portalParticle;
        }
    }
}

