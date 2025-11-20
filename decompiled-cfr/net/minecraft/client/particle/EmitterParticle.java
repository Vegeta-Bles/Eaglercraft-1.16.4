/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public class EmitterParticle
extends NoRenderParticle {
    private final Entity entity;
    private int emitterAge;
    private final int maxEmitterAge;
    private final ParticleEffect parameters;

    public EmitterParticle(ClientWorld world, Entity entity, ParticleEffect parameters) {
        this(world, entity, parameters, 3);
    }

    public EmitterParticle(ClientWorld world, Entity entity, ParticleEffect parameters, int maxEmitterAge) {
        this(world, entity, parameters, maxEmitterAge, entity.getVelocity());
    }

    private EmitterParticle(ClientWorld world, Entity entity, ParticleEffect parameters, int maxEmitterAge, Vec3d velocity) {
        super(world, entity.getX(), entity.getBodyY(0.5), entity.getZ(), velocity.x, velocity.y, velocity.z);
        this.entity = entity;
        this.maxEmitterAge = maxEmitterAge;
        this.parameters = parameters;
        this.tick();
    }

    @Override
    public void tick() {
        for (int i = 0; i < 16; ++i) {
            double d = this.random.nextFloat() * 2.0f - 1.0f;
            if (d * d + (_snowman = (double)(this.random.nextFloat() * 2.0f - 1.0f)) * _snowman + (_snowman = (double)(this.random.nextFloat() * 2.0f - 1.0f)) * _snowman > 1.0) continue;
            _snowman = this.entity.offsetX(d / 4.0);
            _snowman = this.entity.getBodyY(0.5 + _snowman / 4.0);
            _snowman = this.entity.offsetZ(_snowman / 4.0);
            this.world.addParticle(this.parameters, false, _snowman, _snowman, _snowman, d, _snowman + 0.2, _snowman);
        }
        ++this.emitterAge;
        if (this.emitterAge >= this.maxEmitterAge) {
            this.markDead();
        }
    }
}

