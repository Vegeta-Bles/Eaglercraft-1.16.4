package net.minecraft.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class DeadBushBlock extends PlantBlock {
   protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

   protected DeadBushBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
      Block _snowman = floor.getBlock();
      return _snowman == Blocks.SAND
         || _snowman == Blocks.RED_SAND
         || _snowman == Blocks.TERRACOTTA
         || _snowman == Blocks.WHITE_TERRACOTTA
         || _snowman == Blocks.ORANGE_TERRACOTTA
         || _snowman == Blocks.MAGENTA_TERRACOTTA
         || _snowman == Blocks.LIGHT_BLUE_TERRACOTTA
         || _snowman == Blocks.YELLOW_TERRACOTTA
         || _snowman == Blocks.LIME_TERRACOTTA
         || _snowman == Blocks.PINK_TERRACOTTA
         || _snowman == Blocks.GRAY_TERRACOTTA
         || _snowman == Blocks.LIGHT_GRAY_TERRACOTTA
         || _snowman == Blocks.CYAN_TERRACOTTA
         || _snowman == Blocks.PURPLE_TERRACOTTA
         || _snowman == Blocks.BLUE_TERRACOTTA
         || _snowman == Blocks.BROWN_TERRACOTTA
         || _snowman == Blocks.GREEN_TERRACOTTA
         || _snowman == Blocks.RED_TERRACOTTA
         || _snowman == Blocks.BLACK_TERRACOTTA
         || _snowman == Blocks.DIRT
         || _snowman == Blocks.COARSE_DIRT
         || _snowman == Blocks.PODZOL;
   }
}
