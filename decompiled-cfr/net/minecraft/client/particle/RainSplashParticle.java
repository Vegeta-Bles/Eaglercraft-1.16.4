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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class RainSplashParticle
extends SpriteBillboardParticle {
    protected RainSplashParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3, 0.0, 0.0, 0.0);
        this.velocityX *= (double)0.3f;
        this.velocityY = Math.random() * (double)0.2f + (double)0.1f;
        this.velocityZ *= (double)0.3f;
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.gravityStrength = 0.06f;
        this.maxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        double d;
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.maxAge-- <= 0) {
            this.markDead();
            return;
        }
        this.velocityY -= (double)this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= (double)0.98f;
        this.velocityY *= (double)0.98f;
        this.velocityZ *= (double)0.98f;
        if (this.onGround) {
            if (Math.random() < 0.5) {
                this.markDead();
            }
            this.velocityX *= (double)0.7f;
            this.velocityZ *= (double)0.7f;
        }
        if ((d = Math.max(this.world.getBlockState(_snowman = new BlockPos(this.x, this.y, this.z)).getCollisionShape(this.world, _snowman).getEndingCoord(Direction.Axis.Y, this.x - (double)_snowman.getX(), this.z - (double)_snowman.getZ()), (double)this.world.getFluidState(_snowman).getHeight(this.world, _snowman))) > 0.0 && this.y < (double)_snowman.getY() + d) {
            this.markDead();
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
            RainSplashParticle rainSplashParticle = new RainSplashParticle(clientWorld, d, d2, d3);
            rainSplashParticle.setSprite(this.spriteProvider);
            return rainSplashParticle;
        }
    }
}

