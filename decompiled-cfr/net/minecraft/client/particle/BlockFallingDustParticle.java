/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class BlockFallingDustParticle
extends SpriteBillboardParticle {
    private final float field_3809;
    private final SpriteProvider spriteProvider;

    private BlockFallingDustParticle(ClientWorld world, double x, double y, double z, float colorRed, float colorGreen, float colorBlue, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.spriteProvider = spriteProvider;
        this.colorRed = colorRed;
        this.colorGreen = colorGreen;
        this.colorBlue = colorBlue;
        float f = 0.9f;
        this.scale *= 0.67499995f;
        int _snowman2 = (int)(32.0 / (Math.random() * 0.8 + 0.2));
        this.maxAge = (int)Math.max((float)_snowman2 * 0.9f, 1.0f);
        this.setSpriteForAge(spriteProvider);
        this.field_3809 = ((float)Math.random() - 0.5f) * 0.1f;
        this.angle = (float)Math.random() * ((float)Math.PI * 2);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0f, 0.0f, 1.0f);
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
        this.prevAngle = this.angle;
        this.angle += (float)Math.PI * this.field_3809 * 2.0f;
        if (this.onGround) {
            this.angle = 0.0f;
            this.prevAngle = 0.0f;
        }
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityY -= (double)0.003f;
        this.velocityY = Math.max(this.velocityY, (double)-0.14f);
    }

    public static class Factory
    implements ParticleFactory<BlockStateParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        @Nullable
        public Particle createParticle(BlockStateParticleEffect blockStateParticleEffect, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            BlockState blockState = blockStateParticleEffect.getBlockState();
            if (!blockState.isAir() && blockState.getRenderType() == BlockRenderType.INVISIBLE) {
                return null;
            }
            BlockPos _snowman2 = new BlockPos(d, d2, d3);
            int _snowman3 = MinecraftClient.getInstance().getBlockColors().getColor(blockState, clientWorld, _snowman2);
            if (blockState.getBlock() instanceof FallingBlock) {
                _snowman3 = ((FallingBlock)blockState.getBlock()).getColor(blockState, clientWorld, _snowman2);
            }
            float _snowman4 = (float)(_snowman3 >> 16 & 0xFF) / 255.0f;
            float _snowman5 = (float)(_snowman3 >> 8 & 0xFF) / 255.0f;
            float _snowman6 = (float)(_snowman3 & 0xFF) / 255.0f;
            return new BlockFallingDustParticle(clientWorld, d, d2, d3, _snowman4, _snowman5, _snowman6, this.spriteProvider);
        }
    }
}

