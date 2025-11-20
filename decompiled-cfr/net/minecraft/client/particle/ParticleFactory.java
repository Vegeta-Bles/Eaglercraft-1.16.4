/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;

public interface ParticleFactory<T extends ParticleEffect> {
    @Nullable
    public Particle createParticle(T var1, ClientWorld var2, double var3, double var5, double var7, double var9, double var11, double var13);
}

