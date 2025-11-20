/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WallStandingBlockItem
extends BlockItem {
    protected final Block wallBlock;

    public WallStandingBlockItem(Block standingBlock, Block wallBlock, Item.Settings settings) {
        super(standingBlock, settings);
        this.wallBlock = wallBlock;
    }

    @Override
    @Nullable
    protected BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockState;
        BlockState blockState2 = this.wallBlock.getPlacementState(context);
        blockState = null;
        World _snowman2 = context.getWorld();
        BlockPos _snowman3 = context.getBlockPos();
        for (Direction direction : context.getPlacementDirections()) {
            if (direction == Direction.UP) continue;
            BlockState blockState3 = _snowman = direction == Direction.DOWN ? this.getBlock().getPlacementState(context) : blockState2;
            if (_snowman == null || !_snowman.canPlaceAt(_snowman2, _snowman3)) continue;
            blockState = _snowman;
            break;
        }
        return blockState != null && _snowman2.canPlace(blockState, _snowman3, ShapeContext.absent()) ? blockState : null;
    }

    @Override
    public void appendBlocks(Map<Block, Item> map, Item item) {
        super.appendBlocks(map, item);
        map.put(this.wallBlock, item);
    }
}

