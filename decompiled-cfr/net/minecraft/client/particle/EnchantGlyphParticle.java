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

public class EnchantGlyphParticle
extends SpriteBillboardParticle {
    private final double startX;
    private final double startY;
    private final double startZ;

    private EnchantGlyphParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.startX = x;
        this.startY = y;
        this.startZ = z;
        this.prevPosX = x + velocityX;
        this.prevPosY = y + velocityY;
        this.prevPosZ = z + velocityZ;
        this.x = this.prevPosX;
        this.y = this.prevPosY;
        this.z = this.prevPosZ;
        this.scale = 0.1f * (this.random.nextFloat() * 0.5f + 0.2f);
        float f = this.random.nextFloat() * 0.6f + 0.4f;
        this.colorRed = 0.9f * f;
        this.colorGreen = 0.9f * f;
        this.colorBlue = f;
        this.collidesWithWorld = false;
        this.maxAge = (int)(Math.random() * 10.0) + 30;
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
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
            return;
        }
        float f = (float)this.age / (float)this.maxAge;
        f = 1.0f - f;
        _snowman = 1.0f - f;
        _snowman *= _snowman;
        _snowman *= _snowman;
        this.x = this.startX + this.velocityX * (double)f;
        this.y = this.startY + this.velocityY * (double)f - (double)(_snowman * 1.2f);
        this.z = this.startZ + this.velocityZ * (double)f;
    }

    public static class NautilusFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public NautilusFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            EnchantGlyphParticle enchantGlyphParticle = new EnchantGlyphParticle(clientWorld, d, d2, d3, d4, d5, d6);
            enchantGlyphParticle.setSprite(this.spriteProvider);
            return enchantGlyphParticle;
        }
    }

    public static class EnchantFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public EnchantFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            EnchantGlyphParticle enchantGlyphParticle = new EnchantGlyphParticle(clientWorld, d, d2, d3, d4, d5, d6);
            enchantGlyphParticle.setSprite(this.spriteProvider);
            return enchantGlyphParticle;
        }
    }
}

