package net.minecraft.block;

import java.util.Random;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

public abstract class SpreadableBlock extends SnowyBlock {
   protected SpreadableBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   private static boolean canSurvive(BlockState state, WorldView _snowman, BlockPos pos) {
      BlockPos _snowmanx = pos.up();
      BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
      if (_snowmanxx.isOf(Blocks.SNOW) && _snowmanxx.get(SnowBlock.LAYERS) == 1) {
         return true;
      } else if (_snowmanxx.getFluidState().getLevel() == 8) {
         return false;
      } else {
         int _snowmanxxx = ChunkLightProvider.getRealisticOpacity(_snowman, state, pos, _snowmanxx, _snowmanx, Direction.UP, _snowmanxx.getOpacity(_snowman, _snowmanx));
         return _snowmanxxx < _snowman.getMaxLightLevel();
      }
   }

   private static boolean canSpread(BlockState state, WorldView _snowman, BlockPos pos) {
      BlockPos _snowmanx = pos.up();
      return canSurvive(state, _snowman, pos) && !_snowman.getFluidState(_snowmanx).isIn(FluidTags.WATER);
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (!canSurvive(state, world, pos)) {
         world.setBlockState(pos, Blocks.DIRT.getDefaultState());
      } else {
         if (world.getLightLevel(pos.up()) >= 9) {
            BlockState _snowman = this.getDefaultState();

            for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
               BlockPos _snowmanxx = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
               if (world.getBlockState(_snowmanxx).isOf(Blocks.DIRT) && canSpread(_snowman, world, _snowmanxx)) {
                  world.setBlockState(_snowmanxx, _snowman.with(SNOWY, Boolean.valueOf(world.getBlockState(_snowmanxx.up()).isOf(Blocks.SNOW))));
               }
            }
         }
      }
   }
}
