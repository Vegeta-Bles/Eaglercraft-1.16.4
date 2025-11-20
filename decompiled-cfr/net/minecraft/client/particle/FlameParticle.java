/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.AbstractSlowingParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class FlameParticle
extends AbstractSlowingParticle {
    private FlameParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
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
        return this.scale * (1.0f - f * f * 0.5f);
    }

    @Override
    public int getColorMultiplier(float tint) {
        float f = ((float)this.age + tint) / (float)this.maxAge;
        f = MathHelper.clamp(f, 0.0f, 1.0f);
        int _snowman2 = super.getColorMultiplier(tint);
        int _snowman3 = _snowman2 & 0xFF;
        int _snowman4 = _snowman2 >> 16 & 0xFF;
        if ((_snowman3 += (int)(f * 15.0f * 16.0f)) > 240) {
            _snowman3 = 240;
        }
        return _snowman3 | _snowman4 << 16;
    }

    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            FlameParticle flameParticle = new FlameParticle(clientWorld, d, d2, d3, d4, d5, d6);
            flameParticle.setSprite(this.spriteProvider);
            return flameParticle;
        }
    }
}

