package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;

public class CommandBlockItem extends BlockItem {
   public CommandBlockItem(Block _snowman, Item.Settings _snowman) {
      super(_snowman, _snowman);
   }

   @Nullable
   @Override
   protected BlockState getPlacementState(ItemPlacementContext context) {
      PlayerEntity _snowman = context.getPlayer();
      return _snowman != null && !_snowman.isCreativeLevelTwoOp() ? null : super.getPlacementState(context);
   }
}
