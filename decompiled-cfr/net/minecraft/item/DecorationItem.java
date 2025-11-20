/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DecorationItem
extends Item {
    private final EntityType<? extends AbstractDecorationEntity> entityType;

    public DecorationItem(EntityType<? extends AbstractDecorationEntity> type, Item.Settings settings) {
        super(settings);
        this.entityType = type;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        AbstractDecorationEntity _snowman6;
        BlockPos blockPos = context.getBlockPos();
        Direction _snowman2 = context.getSide();
        blockPos2 = blockPos.offset(_snowman2);
        PlayerEntity _snowman3 = context.getPlayer();
        ItemStack _snowman4 = context.getStack();
        if (_snowman3 != null && !this.canPlaceOn(_snowman3, _snowman2, _snowman4, blockPos2)) {
            return ActionResult.FAIL;
        }
        World _snowman5 = context.getWorld();
        if (this.entityType == EntityType.PAINTING) {
            _snowman6 = new PaintingEntity(_snowman5, blockPos2, _snowman2);
        } else if (this.entityType == EntityType.ITEM_FRAME) {
            BlockPos blockPos2;
            _snowman6 = new ItemFrameEntity(_snowman5, blockPos2, _snowman2);
        } else {
            return ActionResult.success(_snowman5.isClient);
        }
        CompoundTag compoundTag = _snowman4.getTag();
        if (compoundTag != null) {
            EntityType.loadFromEntityTag(_snowman5, _snowman3, _snowman6, compoundTag);
        }
        if (_snowman6.canStayAttached()) {
            if (!_snowman5.isClient) {
                _snowman6.onPlace();
                _snowman5.spawnEntity(_snowman6);
            }
            _snowman4.decrement(1);
            return ActionResult.success(_snowman5.isClient);
        }
        return ActionResult.CONSUME;
    }

    protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
        return !side.getAxis().isVertical() && player.canPlaceOn(pos, side, stack);
    }
}

