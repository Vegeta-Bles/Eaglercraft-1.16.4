/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemConvertible;
import net.minecraft.particle.DefaultParticleType;

public class BarrierParticle
extends SpriteBillboardParticle {
    private BarrierParticle(ClientWorld world, double x, double y, double z, ItemConvertible itemConvertible) {
        super(world, x, y, z);
        this.setSprite(MinecraftClient.getInstance().getItemRenderer().getModels().getSprite(itemConvertible));
        this.gravityStrength = 0.0f;
        this.maxAge = 80;
        this.collidesWithWorld = false;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.TERRAIN_SHEET;
    }

    @Override
    public float getSize(float tickDelta) {
        return 0.5f;
    }

    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new BarrierParticle(clientWorld, d, d2, d3, Blocks.BARRIER.asItem());
        }
    }
}

