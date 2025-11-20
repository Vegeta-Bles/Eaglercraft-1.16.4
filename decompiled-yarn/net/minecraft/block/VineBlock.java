package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class VineBlock extends Block {
   public static final BooleanProperty UP = ConnectingBlock.UP;
   public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
   public static final BooleanProperty EAST = ConnectingBlock.EAST;
   public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
   public static final BooleanProperty WEST = ConnectingBlock.WEST;
   public static final Map<Direction, BooleanProperty> FACING_PROPERTIES = ConnectingBlock.FACING_PROPERTIES
      .entrySet()
      .stream()
      .filter(_snowman -> _snowman.getKey() != Direction.DOWN)
      .collect(Util.toMap());
   private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private final Map<BlockState, VoxelShape> field_26659;

   public VineBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(UP, Boolean.valueOf(false))
            .with(NORTH, Boolean.valueOf(false))
            .with(EAST, Boolean.valueOf(false))
            .with(SOUTH, Boolean.valueOf(false))
            .with(WEST, Boolean.valueOf(false))
      );
      this.field_26659 = ImmutableMap.copyOf(this.stateManager.getStates().stream().collect(Collectors.toMap(Function.identity(), VineBlock::method_31018)));
   }

   private static VoxelShape method_31018(BlockState _snowman) {
      VoxelShape _snowmanx = VoxelShapes.empty();
      if (_snowman.get(UP)) {
         _snowmanx = UP_SHAPE;
      }

      if (_snowman.get(NORTH)) {
         _snowmanx = VoxelShapes.union(_snowmanx, SOUTH_SHAPE);
      }

      if (_snowman.get(SOUTH)) {
         _snowmanx = VoxelShapes.union(_snowmanx, NORTH_SHAPE);
      }

      if (_snowman.get(EAST)) {
         _snowmanx = VoxelShapes.union(_snowmanx, WEST_SHAPE);
      }

      if (_snowman.get(WEST)) {
         _snowmanx = VoxelShapes.union(_snowmanx, EAST_SHAPE);
      }

      return _snowmanx;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.field_26659.get(state);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return this.hasAdjacentBlocks(this.getPlacementShape(state, world, pos));
   }

   private boolean hasAdjacentBlocks(BlockState state) {
      return this.getAdjacentBlockCount(state) > 0;
   }

   private int getAdjacentBlockCount(BlockState state) {
      int _snowman = 0;

      for (BooleanProperty _snowmanx : FACING_PROPERTIES.values()) {
         if (state.get(_snowmanx)) {
            _snowman++;
         }
      }

      return _snowman;
   }

   private boolean shouldHaveSide(BlockView world, BlockPos pos, Direction side) {
      if (side == Direction.DOWN) {
         return false;
      } else {
         BlockPos _snowman = pos.offset(side);
         if (shouldConnectTo(world, _snowman, side)) {
            return true;
         } else if (side.getAxis() == Direction.Axis.Y) {
            return false;
         } else {
            BooleanProperty _snowmanx = FACING_PROPERTIES.get(side);
            BlockState _snowmanxx = world.getBlockState(pos.up());
            return _snowmanxx.isOf(this) && _snowmanxx.get(_snowmanx);
         }
      }
   }

   public static boolean shouldConnectTo(BlockView world, BlockPos pos, Direction direction) {
      BlockState _snowman = world.getBlockState(pos);
      return Block.isFaceFullSquare(_snowman.getCollisionShape(world, pos), direction.getOpposite());
   }

   private BlockState getPlacementShape(BlockState state, BlockView world, BlockPos pos) {
      BlockPos _snowman = pos.up();
      if (state.get(UP)) {
         state = state.with(UP, Boolean.valueOf(shouldConnectTo(world, _snowman, Direction.DOWN)));
      }

      BlockState _snowmanx = null;

      for (Direction _snowmanxx : Direction.Type.HORIZONTAL) {
         BooleanProperty _snowmanxxx = getFacingProperty(_snowmanxx);
         if (state.get(_snowmanxxx)) {
            boolean _snowmanxxxx = this.shouldHaveSide(world, pos, _snowmanxx);
            if (!_snowmanxxxx) {
               if (_snowmanx == null) {
                  _snowmanx = world.getBlockState(_snowman);
               }

               _snowmanxxxx = _snowmanx.isOf(this) && _snowmanx.get(_snowmanxxx);
            }

            state = state.with(_snowmanxxx, Boolean.valueOf(_snowmanxxxx));
         }
      }

      return state;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (direction == Direction.DOWN) {
         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         BlockState _snowman = this.getPlacementShape(state, world, pos);
         return !this.hasAdjacentBlocks(_snowman) ? Blocks.AIR.getDefaultState() : _snowman;
      }
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (world.random.nextInt(4) == 0) {
         Direction _snowman = Direction.random(random);
         BlockPos _snowmanx = pos.up();
         if (_snowman.getAxis().isHorizontal() && !state.get(getFacingProperty(_snowman))) {
            if (this.canGrowAt(world, pos)) {
               BlockPos _snowmanxx = pos.offset(_snowman);
               BlockState _snowmanxxx = world.getBlockState(_snowmanxx);
               if (_snowmanxxx.isAir()) {
                  Direction _snowmanxxxx = _snowman.rotateYClockwise();
                  Direction _snowmanxxxxx = _snowman.rotateYCounterclockwise();
                  boolean _snowmanxxxxxx = state.get(getFacingProperty(_snowmanxxxx));
                  boolean _snowmanxxxxxxx = state.get(getFacingProperty(_snowmanxxxxx));
                  BlockPos _snowmanxxxxxxxx = _snowmanxx.offset(_snowmanxxxx);
                  BlockPos _snowmanxxxxxxxxx = _snowmanxx.offset(_snowmanxxxxx);
                  if (_snowmanxxxxxx && shouldConnectTo(world, _snowmanxxxxxxxx, _snowmanxxxx)) {
                     world.setBlockState(_snowmanxx, this.getDefaultState().with(getFacingProperty(_snowmanxxxx), Boolean.valueOf(true)), 2);
                  } else if (_snowmanxxxxxxx && shouldConnectTo(world, _snowmanxxxxxxxxx, _snowmanxxxxx)) {
                     world.setBlockState(_snowmanxx, this.getDefaultState().with(getFacingProperty(_snowmanxxxxx), Boolean.valueOf(true)), 2);
                  } else {
                     Direction _snowmanxxxxxxxxxx = _snowman.getOpposite();
                     if (_snowmanxxxxxx && world.isAir(_snowmanxxxxxxxx) && shouldConnectTo(world, pos.offset(_snowmanxxxx), _snowmanxxxxxxxxxx)) {
                        world.setBlockState(_snowmanxxxxxxxx, this.getDefaultState().with(getFacingProperty(_snowmanxxxxxxxxxx), Boolean.valueOf(true)), 2);
                     } else if (_snowmanxxxxxxx && world.isAir(_snowmanxxxxxxxxx) && shouldConnectTo(world, pos.offset(_snowmanxxxxx), _snowmanxxxxxxxxxx)) {
                        world.setBlockState(_snowmanxxxxxxxxx, this.getDefaultState().with(getFacingProperty(_snowmanxxxxxxxxxx), Boolean.valueOf(true)), 2);
                     } else if ((double)world.random.nextFloat() < 0.05 && shouldConnectTo(world, _snowmanxx.up(), Direction.UP)) {
                        world.setBlockState(_snowmanxx, this.getDefaultState().with(UP, Boolean.valueOf(true)), 2);
                     }
                  }
               } else if (shouldConnectTo(world, _snowmanxx, _snowman)) {
                  world.setBlockState(pos, state.with(getFacingProperty(_snowman), Boolean.valueOf(true)), 2);
               }
            }
         } else {
            if (_snowman == Direction.UP && pos.getY() < 255) {
               if (this.shouldHaveSide(world, pos, _snowman)) {
                  world.setBlockState(pos, state.with(UP, Boolean.valueOf(true)), 2);
                  return;
               }

               if (world.isAir(_snowmanx)) {
                  if (!this.canGrowAt(world, pos)) {
                     return;
                  }

                  BlockState _snowmanxx = state;

                  for (Direction _snowmanxxx : Direction.Type.HORIZONTAL) {
                     if (random.nextBoolean() || !shouldConnectTo(world, _snowmanx.offset(_snowmanxxx), Direction.UP)) {
                        _snowmanxx = _snowmanxx.with(getFacingProperty(_snowmanxxx), Boolean.valueOf(false));
                     }
                  }

                  if (this.hasHorizontalSide(_snowmanxx)) {
                     world.setBlockState(_snowmanx, _snowmanxx, 2);
                  }

                  return;
               }
            }

            if (pos.getY() > 0) {
               BlockPos _snowmanxx = pos.down();
               BlockState _snowmanxxxx = world.getBlockState(_snowmanxx);
               if (_snowmanxxxx.isAir() || _snowmanxxxx.isOf(this)) {
                  BlockState _snowmanxxxxx = _snowmanxxxx.isAir() ? this.getDefaultState() : _snowmanxxxx;
                  BlockState _snowmanxxxxxx = this.getGrownState(state, _snowmanxxxxx, random);
                  if (_snowmanxxxxx != _snowmanxxxxxx && this.hasHorizontalSide(_snowmanxxxxxx)) {
                     world.setBlockState(_snowmanxx, _snowmanxxxxxx, 2);
                  }
               }
            }
         }
      }
   }

   private BlockState getGrownState(BlockState above, BlockState state, Random random) {
      for (Direction _snowman : Direction.Type.HORIZONTAL) {
         if (random.nextBoolean()) {
            BooleanProperty _snowmanx = getFacingProperty(_snowman);
            if (above.get(_snowmanx)) {
               state = state.with(_snowmanx, Boolean.valueOf(true));
            }
         }
      }

      return state;
   }

   private boolean hasHorizontalSide(BlockState state) {
      return state.get(NORTH) || state.get(EAST) || state.get(SOUTH) || state.get(WEST);
   }

   private boolean canGrowAt(BlockView world, BlockPos pos) {
      int _snowman = 4;
      Iterable<BlockPos> _snowmanx = BlockPos.iterate(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
      int _snowmanxx = 5;

      for (BlockPos _snowmanxxx : _snowmanx) {
         if (world.getBlockState(_snowmanxxx).isOf(this)) {
            if (--_snowmanxx <= 0) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      BlockState _snowman = context.getWorld().getBlockState(context.getBlockPos());
      return _snowman.isOf(this) ? this.getAdjacentBlockCount(_snowman) < FACING_PROPERTIES.size() : super.canReplace(state, context);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = ctx.getWorld().getBlockState(ctx.getBlockPos());
      boolean _snowmanx = _snowman.isOf(this);
      BlockState _snowmanxx = _snowmanx ? _snowman : this.getDefaultState();

      for (Direction _snowmanxxx : ctx.getPlacementDirections()) {
         if (_snowmanxxx != Direction.DOWN) {
            BooleanProperty _snowmanxxxx = getFacingProperty(_snowmanxxx);
            boolean _snowmanxxxxx = _snowmanx && _snowman.get(_snowmanxxxx);
            if (!_snowmanxxxxx && this.shouldHaveSide(ctx.getWorld(), ctx.getBlockPos(), _snowmanxxx)) {
               return _snowmanxx.with(_snowmanxxxx, Boolean.valueOf(true));
            }
         }
      }

      return _snowmanx ? _snowmanxx : null;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(UP, NORTH, EAST, SOUTH, WEST);
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      switch (rotation) {
         case CLOCKWISE_180:
            return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
         case COUNTERCLOCKWISE_90:
            return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
         case CLOCKWISE_90:
            return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
         default:
            return state;
      }
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      switch (mirror) {
         case LEFT_RIGHT:
            return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
         case FRONT_BACK:
            return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
         default:
            return super.mirror(state, mirror);
      }
   }

   public static BooleanProperty getFacingProperty(Direction direction) {
      return FACING_PROPERTIES.get(direction);
   }
}
