/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.ExplosionSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class SpitParticle
extends ExplosionSmokeParticle {
    private SpitParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
        this.gravityStrength = 0.5f;
    }

    @Override
    public void tick() {
        super.tick();
        this.velocityY -= 0.004 + 0.04 * (double)this.gravityStrength;
    }

    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new SpitParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteProvider);
        }
    }
}

