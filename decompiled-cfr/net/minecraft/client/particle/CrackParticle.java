/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ItemStackParticleEffect;

public class CrackParticle
extends SpriteBillboardParticle {
    private final float sampleU;
    private final float sampleV;

    private CrackParticle(ClientWorld world, double x, double y, double z, double d, double d2, double d3, ItemStack stack) {
        this(world, x, y, z, stack);
        this.velocityX *= (double)0.1f;
        this.velocityY *= (double)0.1f;
        this.velocityZ *= (double)0.1f;
        this.velocityX += d;
        this.velocityY += d2;
        this.velocityZ += d3;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.TERRAIN_SHEET;
    }

    protected CrackParticle(ClientWorld world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.setSprite(MinecraftClient.getInstance().getItemRenderer().getHeldItemModel(stack, world, null).getSprite());
        this.gravityStrength = 1.0f;
        this.scale /= 2.0f;
        this.sampleU = this.random.nextFloat() * 3.0f;
        this.sampleV = this.random.nextFloat() * 3.0f;
    }

    @Override
    protected float getMinU() {
        return this.sprite.getFrameU((this.sampleU + 1.0f) / 4.0f * 16.0f);
    }

    @Override
    protected float getMaxU() {
        return this.sprite.getFrameU(this.sampleU / 4.0f * 16.0f);
    }

    @Override
    protected float getMinV() {
        return this.sprite.getFrameV(this.sampleV / 4.0f * 16.0f);
    }

    @Override
    protected float getMaxV() {
        return this.sprite.getFrameV((this.sampleV + 1.0f) / 4.0f * 16.0f);
    }

    public static class SnowballFactory
    implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new CrackParticle(clientWorld, d, d2, d3, new ItemStack(Items.SNOWBALL));
        }
    }

    public static class SlimeballFactory
    implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new CrackParticle(clientWorld, d, d2, d3, new ItemStack(Items.SLIME_BALL));
        }
    }

    public static class ItemFactory
    implements ParticleFactory<ItemStackParticleEffect> {
        @Override
        public Particle createParticle(ItemStackParticleEffect itemStackParticleEffect, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new CrackParticle(clientWorld, d, d2, d3, d4, d5, d6, itemStackParticleEffect.getItemStack());
        }
    }
}

