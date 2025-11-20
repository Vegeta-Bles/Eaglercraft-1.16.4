/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BoatDispenserBehavior
extends ItemDispenserBehavior {
    private final ItemDispenserBehavior itemDispenser = new ItemDispenserBehavior();
    private final BoatEntity.Type boatType;

    public BoatDispenserBehavior(BoatEntity.Type type) {
        this.boatType = type;
    }

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        double d;
        Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
        ServerWorld _snowman2 = pointer.getWorld();
        double _snowman3 = pointer.getX() + (double)((float)direction.getOffsetX() * 1.125f);
        double _snowman4 = pointer.getY() + (double)((float)direction.getOffsetY() * 1.125f);
        double _snowman5 = pointer.getZ() + (double)((float)direction.getOffsetZ() * 1.125f);
        BlockPos _snowman6 = pointer.getBlockPos().offset(direction);
        if (_snowman2.getFluidState(_snowman6).isIn(FluidTags.WATER)) {
            d = 1.0;
        } else if (_snowman2.getBlockState(_snowman6).isAir() && _snowman2.getFluidState(_snowman6.down()).isIn(FluidTags.WATER)) {
            d = 0.0;
        } else {
            return this.itemDispenser.dispense(pointer, stack);
        }
        BoatEntity _snowman7 = new BoatEntity(_snowman2, _snowman3, _snowman4 + d, _snowman5);
        _snowman7.setBoatType(this.boatType);
        _snowman7.yaw = direction.asRotation();
        _snowman2.spawnEntity(_snowman7);
        stack.decrement(1);
        return stack;
    }

    @Override
    protected void playSound(BlockPointer pointer) {
        pointer.getWorld().syncWorldEvent(1000, pointer.getBlockPos(), 0);
    }
}

