/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.DaylightDetectorBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Tickable;

public class DaylightDetectorBlockEntity
extends BlockEntity
implements Tickable {
    public DaylightDetectorBlockEntity() {
        super(BlockEntityType.DAYLIGHT_DETECTOR);
    }

    @Override
    public void tick() {
        Block block;
        if (this.world != null && !this.world.isClient && this.world.getTime() % 20L == 0L && (block = (_snowman = this.getCachedState()).getBlock()) instanceof DaylightDetectorBlock) {
            DaylightDetectorBlock.updateState(_snowman, this.world, this.pos);
        }
    }
}

