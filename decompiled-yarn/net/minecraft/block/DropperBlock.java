package net.minecraft.block;

import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPointerImpl;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class DropperBlock extends DispenserBlock {
   private static final DispenserBehavior BEHAVIOR = new ItemDispenserBehavior();

   public DropperBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   protected DispenserBehavior getBehaviorForItem(ItemStack stack) {
      return BEHAVIOR;
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new DropperBlockEntity();
   }

   @Override
   protected void dispense(ServerWorld _snowman, BlockPos pos) {
      BlockPointerImpl _snowmanx = new BlockPointerImpl(_snowman, pos);
      DispenserBlockEntity _snowmanxx = _snowmanx.getBlockEntity();
      int _snowmanxxx = _snowmanxx.chooseNonEmptySlot();
      if (_snowmanxxx < 0) {
         _snowman.syncWorldEvent(1001, pos, 0);
      } else {
         ItemStack _snowmanxxxx = _snowmanxx.getStack(_snowmanxxx);
         if (!_snowmanxxxx.isEmpty()) {
            Direction _snowmanxxxxx = _snowman.getBlockState(pos).get(FACING);
            Inventory _snowmanxxxxxx = HopperBlockEntity.getInventoryAt(_snowman, pos.offset(_snowmanxxxxx));
            ItemStack _snowmanxxxxxxx;
            if (_snowmanxxxxxx == null) {
               _snowmanxxxxxxx = BEHAVIOR.dispense(_snowmanx, _snowmanxxxx);
            } else {
               _snowmanxxxxxxx = HopperBlockEntity.transfer(_snowmanxx, _snowmanxxxxxx, _snowmanxxxx.copy().split(1), _snowmanxxxxx.getOpposite());
               if (_snowmanxxxxxxx.isEmpty()) {
                  _snowmanxxxxxxx = _snowmanxxxx.copy();
                  _snowmanxxxxxxx.decrement(1);
               } else {
                  _snowmanxxxxxxx = _snowmanxxxx.copy();
               }
            }

            _snowmanxx.setStack(_snowmanxxx, _snowmanxxxxxxx);
         }
      }
   }
}
