package net.minecraft.block;

import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class ConcretePowderBlock extends FallingBlock {
   private final BlockState hardenedState;

   public ConcretePowderBlock(Block hardened, AbstractBlock.Settings settings) {
      super(settings);
      this.hardenedState = hardened.getDefaultState();
   }

   @Override
   public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
      if (shouldHarden(world, pos, currentStateInPos)) {
         world.setBlockState(pos, this.hardenedState, 3);
      }
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockView _snowman = ctx.getWorld();
      BlockPos _snowmanx = ctx.getBlockPos();
      BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
      return shouldHarden(_snowman, _snowmanx, _snowmanxx) ? this.hardenedState : super.getPlacementState(ctx);
   }

   private static boolean shouldHarden(BlockView world, BlockPos pos, BlockState state) {
      return hardensIn(state) || hardensOnAnySide(world, pos);
   }

   private static boolean hardensOnAnySide(BlockView world, BlockPos pos) {
      boolean _snowman = false;
      BlockPos.Mutable _snowmanx = pos.mutableCopy();

      for (Direction _snowmanxx : Direction.values()) {
         BlockState _snowmanxxx = world.getBlockState(_snowmanx);
         if (_snowmanxx != Direction.DOWN || hardensIn(_snowmanxxx)) {
            _snowmanx.set(pos, _snowmanxx);
            _snowmanxxx = world.getBlockState(_snowmanx);
            if (hardensIn(_snowmanxxx) && !_snowmanxxx.isSideSolidFullSquare(world, pos, _snowmanxx.getOpposite())) {
               _snowman = true;
               break;
            }
         }
      }

      return _snowman;
   }

   private static boolean hardensIn(BlockState state) {
      return state.getFluidState().isIn(FluidTags.WATER);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return hardensOnAnySide(world, pos) ? this.hardenedState : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public int getColor(BlockState state, BlockView world, BlockPos pos) {
      return state.getTopMaterialColor(world, pos).color;
   }
}
