/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class ReversePortalParticle
extends PortalParticle {
    private ReversePortalParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.scale = (float)((double)this.scale * 1.5);
        this.maxAge = (int)(Math.random() * 2.0) + 60;
    }

    @Override
    public float getSize(float tickDelta) {
        float f = 1.0f - ((float)this.age + tickDelta) / ((float)this.maxAge * 1.5f);
        return this.scale * f;
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
        this.x += this.velocityX * (double)f;
        this.y += this.velocityY * (double)f;
        this.z += this.velocityZ * (double)f;
    }

    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            ReversePortalParticle reversePortalParticle = new ReversePortalParticle(clientWorld, d, d2, d3, d4, d5, d6);
            reversePortalParticle.setSprite(this.spriteProvider);
            return reversePortalParticle;
        }
    }
}

