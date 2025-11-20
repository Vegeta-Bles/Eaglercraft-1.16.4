package net.minecraft.item;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

public class WallStandingBlockItem extends BlockItem {
   protected final Block wallBlock;

   public WallStandingBlockItem(Block standingBlock, Block wallBlock, Item.Settings settings) {
      super(standingBlock, settings);
      this.wallBlock = wallBlock;
   }

   @Nullable
   @Override
   protected BlockState getPlacementState(ItemPlacementContext context) {
      BlockState lv = this.wallBlock.getPlacementState(context);
      BlockState lv2 = null;
      WorldView lv3 = context.getWorld();
      BlockPos lv4 = context.getBlockPos();

      for (Direction lv5 : context.getPlacementDirections()) {
         if (lv5 != Direction.UP) {
            BlockState lv6 = lv5 == Direction.DOWN ? this.getBlock().getPlacementState(context) : lv;
            if (lv6 != null && lv6.canPlaceAt(lv3, lv4)) {
               lv2 = lv6;
               break;
            }
         }
      }

      return lv2 != null && lv3.canPlace(lv2, lv4, ShapeContext.absent()) ? lv2 : null;
   }

   @Override
   public void appendBlocks(Map<Block, Item> map, Item item) {
      super.appendBlocks(map, item);
      map.put(this.wallBlock, item);
   }
}
