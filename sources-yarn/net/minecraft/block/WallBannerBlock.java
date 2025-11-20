package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WallBannerBlock extends AbstractBannerBlock {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   private static final Map<Direction, VoxelShape> FACING_TO_SHAPE = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 12.5, 16.0),
         Direction.SOUTH,
         Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.5, 2.0),
         Direction.WEST,
         Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 12.5, 16.0),
         Direction.EAST,
         Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 12.5, 16.0)
      )
   );

   public WallBannerBlock(DyeColor arg, AbstractBlock.Settings arg2) {
      super(arg, arg2);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
   }

   @Override
   public String getTranslationKey() {
      return this.asItem().getTranslationKey();
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return world.getBlockState(pos.offset(state.get(FACING).getOpposite())).getMaterial().isSolid();
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return direction == state.get(FACING).getOpposite() && !state.canPlaceAt(world, pos)
         ? Blocks.AIR.getDefaultState()
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return FACING_TO_SHAPE.get(state.get(FACING));
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState lv = this.getDefaultState();
      WorldView lv2 = ctx.getWorld();
      BlockPos lv3 = ctx.getBlockPos();
      Direction[] lvs = ctx.getPlacementDirections();

      for (Direction lv4 : lvs) {
         if (lv4.getAxis().isHorizontal()) {
            Direction lv5 = lv4.getOpposite();
            lv = lv.with(FACING, lv5);
            if (lv.canPlaceAt(lv2, lv3)) {
               return lv;
            }
         }
      }

      return null;
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation(state.get(FACING)));
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING);
   }
}
