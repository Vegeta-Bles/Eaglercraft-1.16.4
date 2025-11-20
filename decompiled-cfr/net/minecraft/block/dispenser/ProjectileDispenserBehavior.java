/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.block.dispenser;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public abstract class ProjectileDispenserBehavior
extends ItemDispenserBehavior {
    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        ServerWorld serverWorld = pointer.getWorld();
        Position _snowman2 = DispenserBlock.getOutputLocation(pointer);
        Direction _snowman3 = pointer.getBlockState().get(DispenserBlock.FACING);
        ProjectileEntity _snowman4 = this.createProjectile(serverWorld, _snowman2, stack);
        _snowman4.setVelocity(_snowman3.getOffsetX(), (float)_snowman3.getOffsetY() + 0.1f, _snowman3.getOffsetZ(), this.getForce(), this.getVariation());
        serverWorld.spawnEntity(_snowman4);
        stack.decrement(1);
        return stack;
    }

    @Override
    protected void playSound(BlockPointer pointer) {
        pointer.getWorld().syncWorldEvent(1002, pointer.getBlockPos(), 0);
    }

    protected abstract ProjectileEntity createProjectile(World var1, Position var2, ItemStack var3);

    protected float getVariation() {
        return 6.0f;
    }

    protected float getForce() {
        return 1.1f;
    }
}

