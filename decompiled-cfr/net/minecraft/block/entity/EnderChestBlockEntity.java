/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.entity;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.block.ChestAnimationProgress;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.MathHelper;

public class EnderChestBlockEntity
extends BlockEntity
implements ChestAnimationProgress,
Tickable {
    public float animationProgress;
    public float lastAnimationProgress;
    public int viewerCount;
    private int ticks;

    public EnderChestBlockEntity() {
        super(BlockEntityType.ENDER_CHEST);
    }

    @Override
    public void tick() {
        if (++this.ticks % 20 * 4 == 0) {
            this.world.addSyncedBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.viewerCount);
        }
        this.lastAnimationProgress = this.animationProgress;
        int n = this.pos.getX();
        _snowman = this.pos.getY();
        _snowman = this.pos.getZ();
        float _snowman2 = 0.1f;
        if (this.viewerCount > 0 && this.animationProgress == 0.0f) {
            double d = (double)n + 0.5;
            d = (double)_snowman + 0.5;
            this.world.playSound(null, d, (double)_snowman + 0.5, d, SoundEvents.BLOCK_ENDER_CHEST_OPEN, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
        }
        if (this.viewerCount == 0 && this.animationProgress > 0.0f || this.viewerCount > 0 && this.animationProgress < 1.0f) {
            float f = this.animationProgress;
            this.animationProgress = this.viewerCount > 0 ? (this.animationProgress += 0.1f) : (this.animationProgress -= 0.1f);
            if (this.animationProgress > 1.0f) {
                this.animationProgress = 1.0f;
            }
            _snowman = 0.5f;
            if (this.animationProgress < 0.5f && f >= 0.5f) {
                double d = (double)n + 0.5;
                _snowman = (double)_snowman + 0.5;
                this.world.playSound(null, d, (double)_snowman + 0.5, _snowman, SoundEvents.BLOCK_ENDER_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
            }
            if (this.animationProgress < 0.0f) {
                this.animationProgress = 0.0f;
            }
        }
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if (type == 1) {
            this.viewerCount = data;
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }

    @Override
    public void markRemoved() {
        this.resetBlock();
        super.markRemoved();
    }

    public void onOpen() {
        ++this.viewerCount;
        this.world.addSyncedBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.viewerCount);
    }

    public void onClose() {
        --this.viewerCount;
        this.world.addSyncedBlockEvent(this.pos, Blocks.ENDER_CHEST, 1, this.viewerCount);
    }

    public boolean canPlayerUse(PlayerEntity playerEntity) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        }
        return !(playerEntity.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
    }

    @Override
    public float getAnimationProgress(float tickDelta) {
        return MathHelper.lerp(tickDelta, this.lastAnimationProgress, this.animationProgress);
    }
}

