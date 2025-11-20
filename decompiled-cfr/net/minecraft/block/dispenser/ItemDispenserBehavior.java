/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class ItemDispenserBehavior
implements DispenserBehavior {
    @Override
    public final ItemStack dispense(BlockPointer blockPointer, ItemStack itemStack) {
        _snowman = this.dispenseSilently(blockPointer, itemStack);
        this.playSound(blockPointer);
        this.spawnParticles(blockPointer, blockPointer.getBlockState().get(DispenserBlock.FACING));
        return _snowman;
    }

    protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
        Position _snowman2 = DispenserBlock.getOutputLocation(pointer);
        ItemStack _snowman3 = stack.split(1);
        ItemDispenserBehavior.spawnItem(pointer.getWorld(), _snowman3, 6, direction, _snowman2);
        return stack;
    }

    public static void spawnItem(World world, ItemStack stack, int offset, Direction side, Position pos) {
        double d = pos.getX();
        _snowman = pos.getY();
        _snowman = pos.getZ();
        _snowman = side.getAxis() == Direction.Axis.Y ? (_snowman -= 0.125) : (_snowman -= 0.15625);
        ItemEntity _snowman2 = new ItemEntity(world, d, _snowman, _snowman, stack);
        _snowman = world.random.nextDouble() * 0.1 + 0.2;
        _snowman2.setVelocity(world.random.nextGaussian() * (double)0.0075f * (double)offset + (double)side.getOffsetX() * _snowman, world.random.nextGaussian() * (double)0.0075f * (double)offset + (double)0.2f, world.random.nextGaussian() * (double)0.0075f * (double)offset + (double)side.getOffsetZ() * _snowman);
        world.spawnEntity(_snowman2);
    }

    protected void playSound(BlockPointer pointer) {
        pointer.getWorld().syncWorldEvent(1000, pointer.getBlockPos(), 0);
    }

    protected void spawnParticles(BlockPointer pointer, Direction side) {
        pointer.getWorld().syncWorldEvent(2000, pointer.getBlockPos(), side.getId());
    }
}

