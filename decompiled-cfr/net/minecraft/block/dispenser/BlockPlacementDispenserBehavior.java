/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BlockPlacementDispenserBehavior
extends FallibleItemDispenserBehavior {
    @Override
    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        this.setSuccess(false);
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
            BlockPos _snowman2 = pointer.getBlockPos().offset(direction);
            _snowman = pointer.getWorld().isAir(_snowman2.down()) ? direction : Direction.UP;
            this.setSuccess(((BlockItem)item).place(new AutomaticItemPlacementContext((World)pointer.getWorld(), _snowman2, direction, stack, _snowman)).isAccepted());
        }
        return stack;
    }
}

