/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.RainSplashParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class WaterSplashParticle
extends RainSplashParticle {
    private WaterSplashParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z);
        this.gravityStrength = 0.04f;
        if (velocityY == 0.0 && (velocityX != 0.0 || velocityZ != 0.0)) {
            this.velocityX = velocityX;
            this.velocityY = 0.1;
            this.velocityZ = velocityZ;
        }
    }

    public static class SplashFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public SplashFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            WaterSplashParticle waterSplashParticle = new WaterSplashParticle(clientWorld, d, d2, d3, d4, d5, d6);
            waterSplashParticle.setSprite(this.spriteProvider);
            return waterSplashParticle;
        }
    }
}

