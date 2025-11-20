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
import net.minecraft.particle.ParticleTypes;

public class LavaEmberParticle
extends SpriteBillboardParticle {
    private LavaEmberParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.velocityX *= (double)0.8f;
        this.velocityY *= (double)0.8f;
        this.velocityZ *= (double)0.8f;
        this.velocityY = this.random.nextFloat() * 0.4f + 0.05f;
        this.scale *= this.random.nextFloat() * 2.0f + 0.2f;
        this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getColorMultiplier(float tint) {
        int n = super.getColorMultiplier(tint);
        _snowman = 240;
        _snowman = n >> 16 & 0xFF;
        return 0xF0 | _snowman << 16;
    }

    @Override
    public float getSize(float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        return this.scale * (1.0f - f * f);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        float f = (float)this.age / (float)this.maxAge;
        if (this.random.nextFloat() > f) {
            this.world.addParticle(ParticleTypes.SMOKE, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
        }
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }
        this.velocityY -= 0.03;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= (double)0.999f;
        this.velocityY *= (double)0.999f;
        this.velocityZ *= (double)0.999f;
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
            LavaEmberParticle lavaEmberParticle = new LavaEmberParticle(clientWorld, d, d2, d3);
            lavaEmberParticle.setSprite(this.spriteProvider);
            return lavaEmberParticle;
        }
    }
}

