/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FireChargeItem
extends Item {
    public FireChargeItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos _snowman2 = context.getBlockPos();
        BlockState _snowman3 = world.getBlockState(_snowman2);
        boolean _snowman4 = false;
        if (CampfireBlock.method_30035(_snowman3)) {
            this.playUseSound(world, _snowman2);
            world.setBlockState(_snowman2, (BlockState)_snowman3.with(CampfireBlock.LIT, true));
            _snowman4 = true;
        } else if (AbstractFireBlock.method_30032(world, _snowman2 = _snowman2.offset(context.getSide()), context.getPlayerFacing())) {
            this.playUseSound(world, _snowman2);
            world.setBlockState(_snowman2, AbstractFireBlock.getState(world, _snowman2));
            _snowman4 = true;
        }
        if (_snowman4) {
            context.getStack().decrement(1);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.FAIL;
    }

    private void playUseSound(World world, BlockPos pos) {
        world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, (RANDOM.nextFloat() - RANDOM.nextFloat()) * 0.2f + 1.0f);
    }
}

