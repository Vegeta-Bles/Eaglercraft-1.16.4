package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import net.minecraft.block.enums.WallShape;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class WallBlock extends Block implements Waterloggable {
   public static final BooleanProperty UP = Properties.UP;
   public static final EnumProperty<WallShape> EAST_SHAPE = Properties.EAST_WALL_SHAPE;
   public static final EnumProperty<WallShape> NORTH_SHAPE = Properties.NORTH_WALL_SHAPE;
   public static final EnumProperty<WallShape> SOUTH_SHAPE = Properties.SOUTH_WALL_SHAPE;
   public static final EnumProperty<WallShape> WEST_SHAPE = Properties.WEST_WALL_SHAPE;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   private final Map<BlockState, VoxelShape> shapeMap;
   private final Map<BlockState, VoxelShape> collisionShapeMap;
   private static final VoxelShape TALL_POST_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final VoxelShape TALL_NORTH_SHAPE = Block.createCuboidShape(7.0, 0.0, 0.0, 9.0, 16.0, 9.0);
   private static final VoxelShape TALL_SOUTH_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 16.0);
   private static final VoxelShape TALL_WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 7.0, 9.0, 16.0, 9.0);
   private static final VoxelShape TALL_EAST_SHAPE = Block.createCuboidShape(7.0, 0.0, 7.0, 16.0, 16.0, 9.0);

   public WallBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(UP, Boolean.valueOf(true))
            .with(NORTH_SHAPE, WallShape.NONE)
            .with(EAST_SHAPE, WallShape.NONE)
            .with(SOUTH_SHAPE, WallShape.NONE)
            .with(WEST_SHAPE, WallShape.NONE)
            .with(WATERLOGGED, Boolean.valueOf(false))
      );
      this.shapeMap = this.getShapeMap(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
      this.collisionShapeMap = this.getShapeMap(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
   }

   private static VoxelShape method_24426(VoxelShape _snowman, WallShape _snowman, VoxelShape _snowman, VoxelShape _snowman) {
      if (_snowman == WallShape.TALL) {
         return VoxelShapes.union(_snowman, _snowman);
      } else {
         return _snowman == WallShape.LOW ? VoxelShapes.union(_snowman, _snowman) : _snowman;
      }
   }

   private Map<BlockState, VoxelShape> getShapeMap(float _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxxx = 8.0F - _snowman;
      float _snowmanxxxxxxx = 8.0F + _snowman;
      float _snowmanxxxxxxxx = 8.0F - _snowman;
      float _snowmanxxxxxxxxx = 8.0F + _snowman;
      VoxelShape _snowmanxxxxxxxxxx = Block.createCuboidShape((double)_snowmanxxxxxx, 0.0, (double)_snowmanxxxxxx, (double)_snowmanxxxxxxx, (double)_snowman, (double)_snowmanxxxxxxx);
      VoxelShape _snowmanxxxxxxxxxxx = Block.createCuboidShape((double)_snowmanxxxxxxxx, (double)_snowman, 0.0, (double)_snowmanxxxxxxxxx, (double)_snowman, (double)_snowmanxxxxxxxxx);
      VoxelShape _snowmanxxxxxxxxxxxx = Block.createCuboidShape((double)_snowmanxxxxxxxx, (double)_snowman, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx, (double)_snowman, 16.0);
      VoxelShape _snowmanxxxxxxxxxxxxx = Block.createCuboidShape(0.0, (double)_snowman, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx, (double)_snowman, (double)_snowmanxxxxxxxxx);
      VoxelShape _snowmanxxxxxxxxxxxxxx = Block.createCuboidShape((double)_snowmanxxxxxxxx, (double)_snowman, (double)_snowmanxxxxxxxx, 16.0, (double)_snowman, (double)_snowmanxxxxxxxxx);
      VoxelShape _snowmanxxxxxxxxxxxxxxx = Block.createCuboidShape((double)_snowmanxxxxxxxx, (double)_snowman, 0.0, (double)_snowmanxxxxxxxxx, (double)_snowman, (double)_snowmanxxxxxxxxx);
      VoxelShape _snowmanxxxxxxxxxxxxxxxx = Block.createCuboidShape((double)_snowmanxxxxxxxx, (double)_snowman, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx, (double)_snowman, 16.0);
      VoxelShape _snowmanxxxxxxxxxxxxxxxxx = Block.createCuboidShape(0.0, (double)_snowman, (double)_snowmanxxxxxxxx, (double)_snowmanxxxxxxxxx, (double)_snowman, (double)_snowmanxxxxxxxxx);
      VoxelShape _snowmanxxxxxxxxxxxxxxxxxx = Block.createCuboidShape((double)_snowmanxxxxxxxx, (double)_snowman, (double)_snowmanxxxxxxxx, 16.0, (double)_snowman, (double)_snowmanxxxxxxxxx);
      Builder<BlockState, VoxelShape> _snowmanxxxxxxxxxxxxxxxxxxx = ImmutableMap.builder();

      for (Boolean _snowmanxxxxxxxxxxxxxxxxxxxx : UP.getValues()) {
         for (WallShape _snowmanxxxxxxxxxxxxxxxxxxxxx : EAST_SHAPE.getValues()) {
            for (WallShape _snowmanxxxxxxxxxxxxxxxxxxxxxx : NORTH_SHAPE.getValues()) {
               for (WallShape _snowmanxxxxxxxxxxxxxxxxxxxxxxx : WEST_SHAPE.getValues()) {
                  for (WallShape _snowmanxxxxxxxxxxxxxxxxxxxxxxxx : SOUTH_SHAPE.getValues()) {
                     VoxelShape _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = VoxelShapes.empty();
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = method_24426(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = method_24426(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = method_24426(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = method_24426(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxxxxx) {
                        _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = VoxelShapes.union(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxx);
                     }

                     BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getDefaultState()
                        .with(UP, _snowmanxxxxxxxxxxxxxxxxxxxx)
                        .with(EAST_SHAPE, _snowmanxxxxxxxxxxxxxxxxxxxxx)
                        .with(WEST_SHAPE, _snowmanxxxxxxxxxxxxxxxxxxxxxxx)
                        .with(NORTH_SHAPE, _snowmanxxxxxxxxxxxxxxxxxxxxxx)
                        .with(SOUTH_SHAPE, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.with(WATERLOGGED, Boolean.valueOf(false)), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
                     _snowmanxxxxxxxxxxxxxxxxxxx.put(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.with(WATERLOGGED, Boolean.valueOf(true)), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
                  }
               }
            }
         }
      }

      return _snowmanxxxxxxxxxxxxxxxxxxx.build();
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.shapeMap.get(state);
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.collisionShapeMap.get(state);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   private boolean shouldConnectTo(BlockState state, boolean faceFullSquare, Direction side) {
      Block _snowman = state.getBlock();
      boolean _snowmanx = _snowman instanceof FenceGateBlock && FenceGateBlock.canWallConnect(state, side);
      return state.isIn(BlockTags.WALLS) || !cannotConnect(_snowman) && faceFullSquare || _snowman instanceof PaneBlock || _snowmanx;
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      WorldView _snowman = ctx.getWorld();
      BlockPos _snowmanx = ctx.getBlockPos();
      FluidState _snowmanxx = ctx.getWorld().getFluidState(ctx.getBlockPos());
      BlockPos _snowmanxxx = _snowmanx.north();
      BlockPos _snowmanxxxx = _snowmanx.east();
      BlockPos _snowmanxxxxx = _snowmanx.south();
      BlockPos _snowmanxxxxxx = _snowmanx.west();
      BlockPos _snowmanxxxxxxx = _snowmanx.up();
      BlockState _snowmanxxxxxxxx = _snowman.getBlockState(_snowmanxxx);
      BlockState _snowmanxxxxxxxxx = _snowman.getBlockState(_snowmanxxxx);
      BlockState _snowmanxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxx);
      BlockState _snowmanxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxx);
      BlockState _snowmanxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxx);
      boolean _snowmanxxxxxxxxxxxxx = this.shouldConnectTo(_snowmanxxxxxxxx, _snowmanxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxx, Direction.SOUTH), Direction.SOUTH);
      boolean _snowmanxxxxxxxxxxxxxx = this.shouldConnectTo(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxx, Direction.WEST), Direction.WEST);
      boolean _snowmanxxxxxxxxxxxxxxx = this.shouldConnectTo(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxxx, Direction.NORTH), Direction.NORTH);
      boolean _snowmanxxxxxxxxxxxxxxxx = this.shouldConnectTo(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx.isSideSolidFullSquare(_snowman, _snowmanxxxxxx, Direction.EAST), Direction.EAST);
      BlockState _snowmanxxxxxxxxxxxxxxxxx = this.getDefaultState().with(WATERLOGGED, Boolean.valueOf(_snowmanxx.getFluid() == Fluids.WATER));
      return this.method_24422(_snowman, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      if (direction == Direction.DOWN) {
         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         return direction == Direction.UP
            ? this.method_24421(world, state, posFrom, newState)
            : this.method_24423(world, pos, state, posFrom, newState, direction);
      }
   }

   private static boolean method_24424(BlockState _snowman, Property<WallShape> _snowman) {
      return _snowman.get(_snowman) != WallShape.NONE;
   }

   private static boolean method_24427(VoxelShape _snowman, VoxelShape _snowman) {
      return !VoxelShapes.matchesAnywhere(_snowman, _snowman, BooleanBiFunction.ONLY_FIRST);
   }

   private BlockState method_24421(WorldView _snowman, BlockState _snowman, BlockPos _snowman, BlockState _snowman) {
      boolean _snowmanxxxx = method_24424(_snowman, NORTH_SHAPE);
      boolean _snowmanxxxxx = method_24424(_snowman, EAST_SHAPE);
      boolean _snowmanxxxxxx = method_24424(_snowman, SOUTH_SHAPE);
      boolean _snowmanxxxxxxx = method_24424(_snowman, WEST_SHAPE);
      return this.method_24422(_snowman, _snowman, _snowman, _snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
   }

   private BlockState method_24423(WorldView _snowman, BlockPos _snowman, BlockState _snowman, BlockPos _snowman, BlockState _snowman, Direction _snowman) {
      Direction _snowmanxxxxxx = _snowman.getOpposite();
      boolean _snowmanxxxxxxx = _snowman == Direction.NORTH ? this.shouldConnectTo(_snowman, _snowman.isSideSolidFullSquare(_snowman, _snowman, _snowmanxxxxxx), _snowmanxxxxxx) : method_24424(_snowman, NORTH_SHAPE);
      boolean _snowmanxxxxxxxx = _snowman == Direction.EAST ? this.shouldConnectTo(_snowman, _snowman.isSideSolidFullSquare(_snowman, _snowman, _snowmanxxxxxx), _snowmanxxxxxx) : method_24424(_snowman, EAST_SHAPE);
      boolean _snowmanxxxxxxxxx = _snowman == Direction.SOUTH ? this.shouldConnectTo(_snowman, _snowman.isSideSolidFullSquare(_snowman, _snowman, _snowmanxxxxxx), _snowmanxxxxxx) : method_24424(_snowman, SOUTH_SHAPE);
      boolean _snowmanxxxxxxxxxx = _snowman == Direction.WEST ? this.shouldConnectTo(_snowman, _snowman.isSideSolidFullSquare(_snowman, _snowman, _snowmanxxxxxx), _snowmanxxxxxx) : method_24424(_snowman, WEST_SHAPE);
      BlockPos _snowmanxxxxxxxxxxx = _snowman.up();
      BlockState _snowmanxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxx);
      return this.method_24422(_snowman, _snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
   }

   private BlockState method_24422(WorldView _snowman, BlockState _snowman, BlockPos _snowman, BlockState _snowman, boolean _snowman, boolean _snowman, boolean _snowman, boolean _snowman) {
      VoxelShape _snowmanxxxxxxxx = _snowman.getCollisionShape(_snowman, _snowman).getFace(Direction.DOWN);
      BlockState _snowmanxxxxxxxxx = this.method_24425(_snowman, _snowman, _snowman, _snowman, _snowman, _snowmanxxxxxxxx);
      return _snowmanxxxxxxxxx.with(UP, Boolean.valueOf(this.method_27092(_snowmanxxxxxxxxx, _snowman, _snowmanxxxxxxxx)));
   }

   private boolean method_27092(BlockState _snowman, BlockState _snowman, VoxelShape _snowman) {
      boolean _snowmanxxx = _snowman.getBlock() instanceof WallBlock && _snowman.get(UP);
      if (_snowmanxxx) {
         return true;
      } else {
         WallShape _snowmanxxxx = _snowman.get(NORTH_SHAPE);
         WallShape _snowmanxxxxx = _snowman.get(SOUTH_SHAPE);
         WallShape _snowmanxxxxxx = _snowman.get(EAST_SHAPE);
         WallShape _snowmanxxxxxxx = _snowman.get(WEST_SHAPE);
         boolean _snowmanxxxxxxxx = _snowmanxxxxx == WallShape.NONE;
         boolean _snowmanxxxxxxxxx = _snowmanxxxxxxx == WallShape.NONE;
         boolean _snowmanxxxxxxxxxx = _snowmanxxxxxx == WallShape.NONE;
         boolean _snowmanxxxxxxxxxxx = _snowmanxxxx == WallShape.NONE;
         boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx && _snowmanxxxxxxxx && _snowmanxxxxxxxxx && _snowmanxxxxxxxxxx || _snowmanxxxxxxxxxxx != _snowmanxxxxxxxx || _snowmanxxxxxxxxx != _snowmanxxxxxxxxxx;
         if (_snowmanxxxxxxxxxxxx) {
            return true;
         } else {
            boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxx == WallShape.TALL && _snowmanxxxxx == WallShape.TALL || _snowmanxxxxxx == WallShape.TALL && _snowmanxxxxxxx == WallShape.TALL;
            return _snowmanxxxxxxxxxxxxx ? false : _snowman.getBlock().isIn(BlockTags.WALL_POST_OVERRIDE) || method_24427(_snowman, TALL_POST_SHAPE);
         }
      }
   }

   private BlockState method_24425(BlockState _snowman, boolean _snowman, boolean _snowman, boolean _snowman, boolean _snowman, VoxelShape _snowman) {
      return _snowman.with(NORTH_SHAPE, this.method_24428(_snowman, _snowman, TALL_NORTH_SHAPE))
         .with(EAST_SHAPE, this.method_24428(_snowman, _snowman, TALL_EAST_SHAPE))
         .with(SOUTH_SHAPE, this.method_24428(_snowman, _snowman, TALL_SOUTH_SHAPE))
         .with(WEST_SHAPE, this.method_24428(_snowman, _snowman, TALL_WEST_SHAPE));
   }

   private WallShape method_24428(boolean _snowman, VoxelShape _snowman, VoxelShape _snowman) {
      if (_snowman) {
         return method_24427(_snowman, _snowman) ? WallShape.TALL : WallShape.LOW;
      } else {
         return WallShape.NONE;
      }
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   @Override
   public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
      return !state.get(WATERLOGGED);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(UP, NORTH_SHAPE, EAST_SHAPE, WEST_SHAPE, SOUTH_SHAPE, WATERLOGGED);
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      switch (rotation) {
         case CLOCKWISE_180:
            return state.with(NORTH_SHAPE, state.get(SOUTH_SHAPE))
               .with(EAST_SHAPE, state.get(WEST_SHAPE))
               .with(SOUTH_SHAPE, state.get(NORTH_SHAPE))
               .with(WEST_SHAPE, state.get(EAST_SHAPE));
         case COUNTERCLOCKWISE_90:
            return state.with(NORTH_SHAPE, state.get(EAST_SHAPE))
               .with(EAST_SHAPE, state.get(SOUTH_SHAPE))
               .with(SOUTH_SHAPE, state.get(WEST_SHAPE))
               .with(WEST_SHAPE, state.get(NORTH_SHAPE));
         case CLOCKWISE_90:
            return state.with(NORTH_SHAPE, state.get(WEST_SHAPE))
               .with(EAST_SHAPE, state.get(NORTH_SHAPE))
               .with(SOUTH_SHAPE, state.get(EAST_SHAPE))
               .with(WEST_SHAPE, state.get(SOUTH_SHAPE));
         default:
            return state;
      }
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      switch (mirror) {
         case LEFT_RIGHT:
            return state.with(NORTH_SHAPE, state.get(SOUTH_SHAPE)).with(SOUTH_SHAPE, state.get(NORTH_SHAPE));
         case FRONT_BACK:
            return state.with(EAST_SHAPE, state.get(WEST_SHAPE)).with(WEST_SHAPE, state.get(EAST_SHAPE));
         default:
            return super.mirror(state, mirror);
      }
   }
}
