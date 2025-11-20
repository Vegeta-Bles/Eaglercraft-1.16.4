package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class WallSkullBlock extends AbstractSkullBlock {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   private static final Map<Direction, VoxelShape> FACING_TO_SHAPE = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.createCuboidShape(4.0, 4.0, 8.0, 12.0, 12.0, 16.0),
         Direction.SOUTH,
         Block.createCuboidShape(4.0, 4.0, 0.0, 12.0, 12.0, 8.0),
         Direction.EAST,
         Block.createCuboidShape(0.0, 4.0, 4.0, 8.0, 12.0, 12.0),
         Direction.WEST,
         Block.createCuboidShape(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)
      )
   );

   protected WallSkullBlock(SkullBlock.SkullType _snowman, AbstractBlock.Settings _snowman) {
      super(_snowman, _snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
   }

   @Override
   public String getTranslationKey() {
      return this.asItem().getTranslationKey();
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return FACING_TO_SHAPE.get(state.get(FACING));
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = this.getDefaultState();
      BlockView _snowmanx = ctx.getWorld();
      BlockPos _snowmanxx = ctx.getBlockPos();
      Direction[] _snowmanxxx = ctx.getPlacementDirections();

      for (Direction _snowmanxxxx : _snowmanxxx) {
         if (_snowmanxxxx.getAxis().isHorizontal()) {
            Direction _snowmanxxxxx = _snowmanxxxx.getOpposite();
            _snowman = _snowman.with(FACING, _snowmanxxxxx);
            if (!_snowmanx.getBlockState(_snowmanxx.offset(_snowmanxxxx)).canReplace(ctx)) {
               return _snowman;
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
