package net.minecraft.item;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class MinecartItem extends Item {
   private static final DispenserBehavior DISPENSER_BEHAVIOR = new ItemDispenserBehavior() {
      private final ItemDispenserBehavior defaultBehavior = new ItemDispenserBehavior();

      @Override
      public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
         Direction _snowman = pointer.getBlockState().get(DispenserBlock.FACING);
         World _snowmanx = pointer.getWorld();
         double _snowmanxx = pointer.getX() + (double)_snowman.getOffsetX() * 1.125;
         double _snowmanxxx = Math.floor(pointer.getY()) + (double)_snowman.getOffsetY();
         double _snowmanxxxx = pointer.getZ() + (double)_snowman.getOffsetZ() * 1.125;
         BlockPos _snowmanxxxxx = pointer.getBlockPos().offset(_snowman);
         BlockState _snowmanxxxxxx = _snowmanx.getBlockState(_snowmanxxxxx);
         RailShape _snowmanxxxxxxx = _snowmanxxxxxx.getBlock() instanceof AbstractRailBlock
            ? _snowmanxxxxxx.get(((AbstractRailBlock)_snowmanxxxxxx.getBlock()).getShapeProperty())
            : RailShape.NORTH_SOUTH;
         double _snowmanxxxxxxxx;
         if (_snowmanxxxxxx.isIn(BlockTags.RAILS)) {
            if (_snowmanxxxxxxx.isAscending()) {
               _snowmanxxxxxxxx = 0.6;
            } else {
               _snowmanxxxxxxxx = 0.1;
            }
         } else {
            if (!_snowmanxxxxxx.isAir() || !_snowmanx.getBlockState(_snowmanxxxxx.down()).isIn(BlockTags.RAILS)) {
               return this.defaultBehavior.dispense(pointer, stack);
            }

            BlockState _snowmanxxxxxxxxx = _snowmanx.getBlockState(_snowmanxxxxx.down());
            RailShape _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getBlock() instanceof AbstractRailBlock
               ? _snowmanxxxxxxxxx.get(((AbstractRailBlock)_snowmanxxxxxxxxx.getBlock()).getShapeProperty())
               : RailShape.NORTH_SOUTH;
            if (_snowman != Direction.DOWN && _snowmanxxxxxxxxxx.isAscending()) {
               _snowmanxxxxxxxx = -0.4;
            } else {
               _snowmanxxxxxxxx = -0.9;
            }
         }

         AbstractMinecartEntity _snowmanxxxxxxxxx = AbstractMinecartEntity.create(_snowmanx, _snowmanxx, _snowmanxxx + _snowmanxxxxxxxx, _snowmanxxxx, ((MinecartItem)stack.getItem()).type);
         if (stack.hasCustomName()) {
            _snowmanxxxxxxxxx.setCustomName(stack.getName());
         }

         _snowmanx.spawnEntity(_snowmanxxxxxxxxx);
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
      World _snowman = context.getWorld();
      BlockPos _snowmanx = context.getBlockPos();
      BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
      if (!_snowmanxx.isIn(BlockTags.RAILS)) {
         return ActionResult.FAIL;
      } else {
         ItemStack _snowmanxxx = context.getStack();
         if (!_snowman.isClient) {
            RailShape _snowmanxxxx = _snowmanxx.getBlock() instanceof AbstractRailBlock
               ? _snowmanxx.get(((AbstractRailBlock)_snowmanxx.getBlock()).getShapeProperty())
               : RailShape.NORTH_SOUTH;
            double _snowmanxxxxx = 0.0;
            if (_snowmanxxxx.isAscending()) {
               _snowmanxxxxx = 0.5;
            }

            AbstractMinecartEntity _snowmanxxxxxx = AbstractMinecartEntity.create(
               _snowman, (double)_snowmanx.getX() + 0.5, (double)_snowmanx.getY() + 0.0625 + _snowmanxxxxx, (double)_snowmanx.getZ() + 0.5, this.type
            );
            if (_snowmanxxx.hasCustomName()) {
               _snowmanxxxxxx.setCustomName(_snowmanxxx.getName());
            }

            _snowman.spawnEntity(_snowmanxxxxxx);
         }

         _snowmanxxx.decrement(1);
         return ActionResult.success(_snowman.isClient);
      }
   }
}
