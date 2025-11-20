package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;

public class CommandBlockItem extends BlockItem {
   public CommandBlockItem(Block arg, Item.Settings arg2) {
      super(arg, arg2);
   }

   @Nullable
   @Override
   protected BlockState getPlacementState(ItemPlacementContext context) {
      PlayerEntity lv = context.getPlayer();
      return lv != null && !lv.isCreativeLevelTwoOp() ? null : super.getPlacementState(context);
   }
}
