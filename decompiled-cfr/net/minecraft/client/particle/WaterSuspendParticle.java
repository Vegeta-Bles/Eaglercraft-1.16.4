/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class WaterSuspendParticle
extends SpriteBillboardParticle {
    private WaterSuspendParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y - 0.125, z);
        this.colorRed = 0.4f;
        this.colorGreen = 0.4f;
        this.colorBlue = 0.7f;
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.scale *= this.random.nextFloat() * 0.6f + 0.2f;
        this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.collidesWithWorld = false;
    }

    private WaterSuspendParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y - 0.125, z, velocityX, velocityY, velocityZ);
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.scale *= this.random.nextFloat() * 0.6f + 0.6f;
        this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        this.collidesWithWorld = false;
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
        if (this.maxAge-- <= 0) {
            this.markDead();
            return;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);
    }

    public static class WarpedSporeFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public WarpedSporeFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            _snowman = (double)clientWorld.random.nextFloat() * -1.9 * (double)clientWorld.random.nextFloat() * 0.1;
            WaterSuspendParticle waterSuspendParticle = new WaterSuspendParticle(clientWorld, d, d2, d3, 0.0, _snowman, 0.0);
            waterSuspendParticle.setSprite(this.spriteProvider);
            waterSuspendParticle.setColor(0.1f, 0.1f, 0.3f);
            waterSuspendParticle.setBoundingBoxSpacing(0.001f, 0.001f);
            return waterSuspendParticle;
        }
    }

    public static class CrimsonSporeFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public CrimsonSporeFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            Random random = clientWorld.random;
            double _snowman2 = random.nextGaussian() * (double)1.0E-6f;
            double _snowman3 = random.nextGaussian() * (double)1.0E-4f;
            double _snowman4 = random.nextGaussian() * (double)1.0E-6f;
            WaterSuspendParticle _snowman5 = new WaterSuspendParticle(clientWorld, d, d2, d3, _snowman2, _snowman3, _snowman4);
            _snowman5.setSprite(this.spriteProvider);
            _snowman5.setColor(0.9f, 0.4f, 0.5f);
            return _snowman5;
        }
    }

    public static class UnderwaterFactory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public UnderwaterFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            WaterSuspendParticle waterSuspendParticle = new WaterSuspendParticle(clientWorld, d, d2, d3);
            waterSuspendParticle.setSprite(this.spriteProvider);
            return waterSuspendParticle;
        }
    }
}

