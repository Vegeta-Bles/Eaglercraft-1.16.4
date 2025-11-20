/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;

public class NoRenderParticle
extends Particle {
    protected NoRenderParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3);
    }

    protected NoRenderParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
    }

    @Override
    public final void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.NO_RENDER;
    }
}

