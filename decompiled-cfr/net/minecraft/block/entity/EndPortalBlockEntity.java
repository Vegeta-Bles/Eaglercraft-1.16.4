/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.Direction;

public class EndPortalBlockEntity
extends BlockEntity {
    public EndPortalBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    public EndPortalBlockEntity() {
        this(BlockEntityType.END_PORTAL);
    }

    public boolean shouldDrawSide(Direction direction) {
        return direction == Direction.UP;
    }
}

