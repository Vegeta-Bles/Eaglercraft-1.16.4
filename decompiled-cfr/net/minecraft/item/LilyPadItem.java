/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class LilyPadItem
extends BlockItem {
    public LilyPadItem(Block block, Item.Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockHitResult blockHitResult = LilyPadItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        _snowman = blockHitResult.withBlockPos(blockHitResult.getBlockPos().up());
        ActionResult _snowman2 = super.useOnBlock(new ItemUsageContext(user, hand, _snowman));
        return new TypedActionResult<ItemStack>(_snowman2, user.getStackInHand(hand));
    }
}

