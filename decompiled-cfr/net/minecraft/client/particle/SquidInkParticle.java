/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;

public class SquidInkParticle
extends AnimatedParticle {
    private SquidInkParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, spriteProvider, 0.0f);
        this.scale = 0.5f;
        this.setColorAlpha(1.0f);
        this.setColor(0.0f, 0.0f, 0.0f);
        this.maxAge = (int)((double)(this.scale * 12.0f) / (Math.random() * (double)0.8f + (double)0.2f));
        this.setSpriteForAge(spriteProvider);
        this.collidesWithWorld = false;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.setResistance(0.0f);
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
        if (this.age > this.maxAge / 2) {
            this.setColorAlpha(1.0f - ((float)this.age - (float)(this.maxAge / 2)) / (float)this.maxAge);
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        if (this.world.getBlockState(new BlockPos(this.x, this.y, this.z)).isAir()) {
            this.velocityY -= (double)0.008f;
        }
        this.velocityX *= (double)0.92f;
        this.velocityY *= (double)0.92f;
        this.velocityZ *= (double)0.92f;
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
            return new SquidInkParticle(clientWorld, d, d2, d3, d4, d5, d6, this.spriteProvider);
        }
    }
}

