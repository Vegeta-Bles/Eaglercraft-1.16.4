/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlintAndSteelItem
extends Item {
    public FlintAndSteelItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        World _snowman2 = context.getWorld();
        BlockState _snowman3 = _snowman2.getBlockState(_snowman = context.getBlockPos());
        if (CampfireBlock.method_30035(_snowman3)) {
            _snowman2.playSound(playerEntity, _snowman, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, RANDOM.nextFloat() * 0.4f + 0.8f);
            _snowman2.setBlockState(_snowman, (BlockState)_snowman3.with(Properties.LIT, true), 11);
            if (playerEntity != null) {
                context.getStack().damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
            }
            return ActionResult.success(_snowman2.isClient());
        }
        BlockPos _snowman4 = _snowman.offset(context.getSide());
        if (AbstractFireBlock.method_30032(_snowman2, _snowman4, context.getPlayerFacing())) {
            _snowman2.playSound(playerEntity, _snowman4, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0f, RANDOM.nextFloat() * 0.4f + 0.8f);
            BlockState blockState = AbstractFireBlock.getState(_snowman2, _snowman4);
            _snowman2.setBlockState(_snowman4, blockState, 11);
            ItemStack _snowman5 = context.getStack();
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)playerEntity, _snowman4, _snowman5);
                _snowman5.damage(1, playerEntity, p -> p.sendToolBreakStatus(context.getHand()));
            }
            return ActionResult.success(_snowman2.isClient());
        }
        return ActionResult.FAIL;
    }
}

