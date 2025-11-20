package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.enums.WallMountLocation;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WallMountedBlock extends HorizontalFacingBlock {
   public static final EnumProperty<WallMountLocation> FACE = Properties.WALL_MOUNT_LOCATION;

   protected WallMountedBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return canPlaceAt(world, pos, getDirection(state).getOpposite());
   }

   public static boolean canPlaceAt(WorldView _snowman, BlockPos pos, Direction direction) {
      BlockPos _snowmanx = pos.offset(direction);
      return _snowman.getBlockState(_snowmanx).isSideSolidFullSquare(_snowman, _snowmanx, direction.getOpposite());
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      for (Direction _snowman : ctx.getPlacementDirections()) {
         BlockState _snowmanx;
         if (_snowman.getAxis() == Direction.Axis.Y) {
            _snowmanx = this.getDefaultState().with(FACE, _snowman == Direction.UP ? WallMountLocation.CEILING : WallMountLocation.FLOOR).with(FACING, ctx.getPlayerFacing());
         } else {
            _snowmanx = this.getDefaultState().with(FACE, WallMountLocation.WALL).with(FACING, _snowman.getOpposite());
         }

         if (_snowmanx.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return _snowmanx;
         }
      }

      return null;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return getDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos)
         ? Blocks.AIR.getDefaultState()
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   protected static Direction getDirection(BlockState state) {
      switch ((WallMountLocation)state.get(FACE)) {
         case CEILING:
            return Direction.DOWN;
         case FLOOR:
            return Direction.UP;
         default:
            return state.get(FACING);
      }
   }
}
