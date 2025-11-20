/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;

public class ExplosionEmitterParticle
extends NoRenderParticle {
    private int age_;
    private final int maxAge_;

    private ExplosionEmitterParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.maxAge_ = 8;
    }

    @Override
    public void tick() {
        for (int i = 0; i < 6; ++i) {
            double d = this.x + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            _snowman = this.y + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            _snowman = this.z + (this.random.nextDouble() - this.random.nextDouble()) * 4.0;
            this.world.addParticle(ParticleTypes.EXPLOSION, d, _snowman, _snowman, (float)this.age_ / (float)this.maxAge_, 0.0, 0.0);
        }
        ++this.age_;
        if (this.age_ == this.maxAge_) {
            this.markDead();
        }
    }

    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new ExplosionEmitterParticle(clientWorld, d, d2, d3);
        }
    }
}

