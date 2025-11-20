/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.util.math.BlockPos;

public class BlockDustParticle
extends SpriteBillboardParticle {
    private final BlockState blockState;
    private BlockPos blockPos;
    private final float sampleU;
    private final float sampleV;

    public BlockDustParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, BlockState blockState) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.blockState = blockState;
        this.setSprite(MinecraftClient.getInstance().getBlockRenderManager().getModels().getSprite(blockState));
        this.gravityStrength = 1.0f;
        this.colorRed = 0.6f;
        this.colorGreen = 0.6f;
        this.colorBlue = 0.6f;
        this.scale /= 2.0f;
        this.sampleU = this.random.nextFloat() * 3.0f;
        this.sampleV = this.random.nextFloat() * 3.0f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.TERRAIN_SHEET;
    }

    public BlockDustParticle setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
        if (this.blockState.isOf(Blocks.GRASS_BLOCK)) {
            return this;
        }
        this.updateColor(blockPos);
        return this;
    }

    public BlockDustParticle setBlockPosFromPosition() {
        this.blockPos = new BlockPos(this.x, this.y, this.z);
        if (this.blockState.isOf(Blocks.GRASS_BLOCK)) {
            return this;
        }
        this.updateColor(this.blockPos);
        return this;
    }

    protected void updateColor(@Nullable BlockPos blockPos) {
        int n = MinecraftClient.getInstance().getBlockColors().getColor(this.blockState, this.world, blockPos, 0);
        this.colorRed *= (float)(n >> 16 & 0xFF) / 255.0f;
        this.colorGreen *= (float)(n >> 8 & 0xFF) / 255.0f;
        this.colorBlue *= (float)(n & 0xFF) / 255.0f;
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

    @Override
    public int getColorMultiplier(float tint) {
        int n = super.getColorMultiplier(tint);
        _snowman = 0;
        if (this.world.isChunkLoaded(this.blockPos)) {
            _snowman = WorldRenderer.getLightmapCoordinates(this.world, this.blockPos);
        }
        return n == 0 ? _snowman : n;
    }

    public static class Factory
    implements ParticleFactory<BlockStateParticleEffect> {
        @Override
        public Particle createParticle(BlockStateParticleEffect blockStateParticleEffect, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            BlockState blockState = blockStateParticleEffect.getBlockState();
            if (blockState.isAir() || blockState.isOf(Blocks.MOVING_PISTON)) {
                return null;
            }
            return new BlockDustParticle(clientWorld, d, d2, d3, d4, d5, d6, blockState).setBlockPosFromPosition();
        }
    }
}

