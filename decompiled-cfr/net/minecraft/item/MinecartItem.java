/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.State;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class MinecartItem
extends Item {
    private static final DispenserBehavior DISPENSER_BEHAVIOR = new ItemDispenserBehavior(){
        private final ItemDispenserBehavior defaultBehavior = new ItemDispenserBehavior();

        @Override
        public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            double _snowman8;
            Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
            ServerWorld _snowman2 = pointer.getWorld();
            double _snowman3 = pointer.getX() + (double)direction.getOffsetX() * 1.125;
            double _snowman4 = Math.floor(pointer.getY()) + (double)direction.getOffsetY();
            double _snowman5 = pointer.getZ() + (double)direction.getOffsetZ() * 1.125;
            BlockPos _snowman6 = pointer.getBlockPos().offset(direction);
            BlockState _snowman7 = _snowman2.getBlockState(_snowman6);
            RailShape railShape = _snowman = _snowman7.getBlock() instanceof AbstractRailBlock ? _snowman7.get(((AbstractRailBlock)_snowman7.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
            if (_snowman7.isIn(BlockTags.RAILS)) {
                _snowman8 = _snowman.isAscending() ? 0.6 : 0.1;
            } else if (_snowman7.isAir() && _snowman2.getBlockState(_snowman6.down()).isIn(BlockTags.RAILS)) {
                Object object = _snowman2.getBlockState(_snowman6.down());
                RailShape railShape2 = _snowman = ((AbstractBlock.AbstractBlockState)object).getBlock() instanceof AbstractRailBlock ? ((State)object).get(((AbstractRailBlock)((AbstractBlock.AbstractBlockState)object).getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
                _snowman8 = direction == Direction.DOWN || !_snowman.isAscending() ? -0.9 : -0.4;
            } else {
                return this.defaultBehavior.dispense(pointer, stack);
            }
            object = AbstractMinecartEntity.create(_snowman2, _snowman3, _snowman4 + _snowman8, _snowman5, ((MinecartItem)stack.getItem()).type);
            if (stack.hasCustomName()) {
                ((Entity)object).setCustomName(stack.getName());
            }
            _snowman2.spawnEntity((Entity)object);
            stack.decrement(1);
            return stack;
        }

        @Override
        protected void playSound(BlockPointer pointer) {
            pointer.getWorld().syncWorldEvent(1000, pointer.getBlockPos(), 0);
        }
    };
    private final AbstractMinecartEntity.Type type;

    public MinecartItem(AbstractMinecartEntity.Type type, Item.Settings settings) {
        super(settings);
        this.type = type;
        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockState _snowman2 = world.getBlockState(_snowman = context.getBlockPos());
        if (!_snowman2.isIn(BlockTags.RAILS)) {
            return ActionResult.FAIL;
        }
        ItemStack _snowman3 = context.getStack();
        if (!world.isClient) {
            RailShape railShape = _snowman2.getBlock() instanceof AbstractRailBlock ? _snowman2.get(((AbstractRailBlock)_snowman2.getBlock()).getShapeProperty()) : RailShape.NORTH_SOUTH;
            double _snowman4 = 0.0;
            if (railShape.isAscending()) {
                _snowman4 = 0.5;
            }
            AbstractMinecartEntity _snowman5 = AbstractMinecartEntity.create(world, (double)_snowman.getX() + 0.5, (double)_snowman.getY() + 0.0625 + _snowman4, (double)_snowman.getZ() + 0.5, this.type);
            if (_snowman3.hasCustomName()) {
                _snowman5.setCustomName(_snowman3.getName());
            }
            world.spawnEntity(_snowman5);
        }
        _snowman3.decrement(1);
        return ActionResult.success(world.isClient);
    }
}

