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
      BlockState _snowman = this.wallBlock.getPlacementState(context);
      BlockState _snowmanx = null;
      WorldView _snowmanxx = context.getWorld();
      BlockPos _snowmanxxx = context.getBlockPos();

      for (Direction _snowmanxxxx : context.getPlacementDirections()) {
         if (_snowmanxxxx != Direction.UP) {
            BlockState _snowmanxxxxx = _snowmanxxxx == Direction.DOWN ? this.getBlock().getPlacementState(context) : _snowman;
            if (_snowmanxxxxx != null && _snowmanxxxxx.canPlaceAt(_snowmanxx, _snowmanxxx)) {
               _snowmanx = _snowmanxxxxx;
               break;
            }
         }
      }

      return _snowmanx != null && _snowmanxx.canPlace(_snowmanx, _snowmanxxx, ShapeContext.absent()) ? _snowmanx : null;
   }

   @Override
   public void appendBlocks(Map<Block, Item> map, Item item) {
      super.appendBlocks(map, item);
      map.put(this.wallBlock, item);
   }
}
