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
      .filter(entry -> entry.getKey() != Direction.DOWN)
      .collect(Util.toMap());
   private static final VoxelShape UP_SHAPE = Block.createCuboidShape(0.0, 15.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape WEST_SHAPE = Block.createCuboidShape(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
   private final Map<BlockState, VoxelShape> field_26659;

   public VineBlock(AbstractBlock.Settings arg) {
      super(arg);
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

   private static VoxelShape method_31018(BlockState arg) {
      VoxelShape lv = VoxelShapes.empty();
      if (arg.get(UP)) {
         lv = UP_SHAPE;
      }

      if (arg.get(NORTH)) {
         lv = VoxelShapes.union(lv, SOUTH_SHAPE);
      }

      if (arg.get(SOUTH)) {
         lv = VoxelShapes.union(lv, NORTH_SHAPE);
      }

      if (arg.get(EAST)) {
         lv = VoxelShapes.union(lv, WEST_SHAPE);
      }

      if (arg.get(WEST)) {
         lv = VoxelShapes.union(lv, EAST_SHAPE);
      }

      return lv;
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
      int i = 0;

      for (BooleanProperty lv : FACING_PROPERTIES.values()) {
         if (state.get(lv)) {
            i++;
         }
      }

      return i;
   }

   private boolean shouldHaveSide(BlockView world, BlockPos pos, Direction side) {
      if (side == Direction.DOWN) {
         return false;
      } else {
         BlockPos lv = pos.offset(side);
         if (shouldConnectTo(world, lv, side)) {
            return true;
         } else if (side.getAxis() == Direction.Axis.Y) {
            return false;
         } else {
            BooleanProperty lv2 = FACING_PROPERTIES.get(side);
            BlockState lv3 = world.getBlockState(pos.up());
            return lv3.isOf(this) && lv3.get(lv2);
         }
      }
   }

   public static boolean shouldConnectTo(BlockView world, BlockPos pos, Direction direction) {
      BlockState lv = world.getBlockState(pos);
      return Block.isFaceFullSquare(lv.getCollisionShape(world, pos), direction.getOpposite());
   }

   private BlockState getPlacementShape(BlockState state, BlockView world, BlockPos pos) {
      BlockPos lv = pos.up();
      if (state.get(UP)) {
         state = state.with(UP, Boolean.valueOf(shouldConnectTo(world, lv, Direction.DOWN)));
      }

      BlockState lv2 = null;

      for (Direction lv3 : Direction.Type.HORIZONTAL) {
         BooleanProperty lv4 = getFacingProperty(lv3);
         if (state.get(lv4)) {
            boolean bl = this.shouldHaveSide(world, pos, lv3);
            if (!bl) {
               if (lv2 == null) {
                  lv2 = world.getBlockState(lv);
               }

               bl = lv2.isOf(this) && lv2.get(lv4);
            }

            state = state.with(lv4, Boolean.valueOf(bl));
         }
      }

      return state;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (direction == Direction.DOWN) {
         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         BlockState lv = this.getPlacementShape(state, world, pos);
         return !this.hasAdjacentBlocks(lv) ? Blocks.AIR.getDefaultState() : lv;
      }
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (world.random.nextInt(4) == 0) {
         Direction lv = Direction.random(random);
         BlockPos lv2 = pos.up();
         if (lv.getAxis().isHorizontal() && !state.get(getFacingProperty(lv))) {
            if (this.canGrowAt(world, pos)) {
               BlockPos lv3 = pos.offset(lv);
               BlockState lv4 = world.getBlockState(lv3);
               if (lv4.isAir()) {
                  Direction lv5 = lv.rotateYClockwise();
                  Direction lv6 = lv.rotateYCounterclockwise();
                  boolean bl = state.get(getFacingProperty(lv5));
                  boolean bl2 = state.get(getFacingProperty(lv6));
                  BlockPos lv7 = lv3.offset(lv5);
                  BlockPos lv8 = lv3.offset(lv6);
                  if (bl && shouldConnectTo(world, lv7, lv5)) {
                     world.setBlockState(lv3, this.getDefaultState().with(getFacingProperty(lv5), Boolean.valueOf(true)), 2);
                  } else if (bl2 && shouldConnectTo(world, lv8, lv6)) {
                     world.setBlockState(lv3, this.getDefaultState().with(getFacingProperty(lv6), Boolean.valueOf(true)), 2);
                  } else {
                     Direction lv9 = lv.getOpposite();
                     if (bl && world.isAir(lv7) && shouldConnectTo(world, pos.offset(lv5), lv9)) {
                        world.setBlockState(lv7, this.getDefaultState().with(getFacingProperty(lv9), Boolean.valueOf(true)), 2);
                     } else if (bl2 && world.isAir(lv8) && shouldConnectTo(world, pos.offset(lv6), lv9)) {
                        world.setBlockState(lv8, this.getDefaultState().with(getFacingProperty(lv9), Boolean.valueOf(true)), 2);
                     } else if ((double)world.random.nextFloat() < 0.05 && shouldConnectTo(world, lv3.up(), Direction.UP)) {
                        world.setBlockState(lv3, this.getDefaultState().with(UP, Boolean.valueOf(true)), 2);
                     }
                  }
               } else if (shouldConnectTo(world, lv3, lv)) {
                  world.setBlockState(pos, state.with(getFacingProperty(lv), Boolean.valueOf(true)), 2);
               }
            }
         } else {
            if (lv == Direction.UP && pos.getY() < 255) {
               if (this.shouldHaveSide(world, pos, lv)) {
                  world.setBlockState(pos, state.with(UP, Boolean.valueOf(true)), 2);
                  return;
               }

               if (world.isAir(lv2)) {
                  if (!this.canGrowAt(world, pos)) {
                     return;
                  }

                  BlockState lv10 = state;

                  for (Direction lv11 : Direction.Type.HORIZONTAL) {
                     if (random.nextBoolean() || !shouldConnectTo(world, lv2.offset(lv11), Direction.UP)) {
                        lv10 = lv10.with(getFacingProperty(lv11), Boolean.valueOf(false));
                     }
                  }

                  if (this.hasHorizontalSide(lv10)) {
                     world.setBlockState(lv2, lv10, 2);
                  }

                  return;
               }
            }

            if (pos.getY() > 0) {
               BlockPos lv12 = pos.down();
               BlockState lv13 = world.getBlockState(lv12);
               if (lv13.isAir() || lv13.isOf(this)) {
                  BlockState lv14 = lv13.isAir() ? this.getDefaultState() : lv13;
                  BlockState lv15 = this.getGrownState(state, lv14, random);
                  if (lv14 != lv15 && this.hasHorizontalSide(lv15)) {
                     world.setBlockState(lv12, lv15, 2);
                  }
               }
            }
         }
      }
   }

   private BlockState getGrownState(BlockState above, BlockState state, Random random) {
      for (Direction lv : Direction.Type.HORIZONTAL) {
         if (random.nextBoolean()) {
            BooleanProperty lv2 = getFacingProperty(lv);
            if (above.get(lv2)) {
               state = state.with(lv2, Boolean.valueOf(true));
            }
         }
      }

      return state;
   }

   private boolean hasHorizontalSide(BlockState state) {
      return state.get(NORTH) || state.get(EAST) || state.get(SOUTH) || state.get(WEST);
   }

   private boolean canGrowAt(BlockView world, BlockPos pos) {
      int i = 4;
      Iterable<BlockPos> iterable = BlockPos.iterate(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
      int j = 5;

      for (BlockPos lv : iterable) {
         if (world.getBlockState(lv).isOf(this)) {
            if (--j <= 0) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      BlockState lv = context.getWorld().getBlockState(context.getBlockPos());
      return lv.isOf(this) ? this.getAdjacentBlockCount(lv) < FACING_PROPERTIES.size() : super.canReplace(state, context);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState lv = ctx.getWorld().getBlockState(ctx.getBlockPos());
      boolean bl = lv.isOf(this);
      BlockState lv2 = bl ? lv : this.getDefaultState();

      for (Direction lv3 : ctx.getPlacementDirections()) {
         if (lv3 != Direction.DOWN) {
            BooleanProperty lv4 = getFacingProperty(lv3);
            boolean bl2 = bl && lv.get(lv4);
            if (!bl2 && this.shouldHaveSide(ctx.getWorld(), ctx.getBlockPos(), lv3)) {
               return lv2.with(lv4, Boolean.valueOf(true));
            }
         }
      }

      return bl ? lv2 : null;
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
