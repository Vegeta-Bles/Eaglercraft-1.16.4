package net.minecraft.block;

import java.util.Random;
import java.util.stream.IntStream;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.explosion.Explosion;

public class StairsBlock extends Block implements Waterloggable {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   public static final EnumProperty<BlockHalf> HALF = Properties.BLOCK_HALF;
   public static final EnumProperty<StairShape> SHAPE = Properties.STAIR_SHAPE;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   protected static final VoxelShape TOP_SHAPE = SlabBlock.TOP_SHAPE;
   protected static final VoxelShape BOTTOM_SHAPE = SlabBlock.BOTTOM_SHAPE;
   protected static final VoxelShape BOTTOM_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 8.0, 8.0);
   protected static final VoxelShape BOTTOM_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0, 0.0, 8.0, 8.0, 8.0, 16.0);
   protected static final VoxelShape TOP_NORTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0, 8.0, 0.0, 8.0, 16.0, 8.0);
   protected static final VoxelShape TOP_SOUTH_WEST_CORNER_SHAPE = Block.createCuboidShape(0.0, 8.0, 8.0, 8.0, 16.0, 16.0);
   protected static final VoxelShape BOTTOM_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 8.0, 8.0);
   protected static final VoxelShape BOTTOM_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0, 0.0, 8.0, 16.0, 8.0, 16.0);
   protected static final VoxelShape TOP_NORTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0, 8.0, 0.0, 16.0, 16.0, 8.0);
   protected static final VoxelShape TOP_SOUTH_EAST_CORNER_SHAPE = Block.createCuboidShape(8.0, 8.0, 8.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape[] TOP_SHAPES = composeShapes(
      TOP_SHAPE, BOTTOM_NORTH_WEST_CORNER_SHAPE, BOTTOM_NORTH_EAST_CORNER_SHAPE, BOTTOM_SOUTH_WEST_CORNER_SHAPE, BOTTOM_SOUTH_EAST_CORNER_SHAPE
   );
   protected static final VoxelShape[] BOTTOM_SHAPES = composeShapes(
      BOTTOM_SHAPE, TOP_NORTH_WEST_CORNER_SHAPE, TOP_NORTH_EAST_CORNER_SHAPE, TOP_SOUTH_WEST_CORNER_SHAPE, TOP_SOUTH_EAST_CORNER_SHAPE
   );
   private static final int[] SHAPE_INDICES = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};
   private final Block baseBlock;
   private final BlockState baseBlockState;

   private static VoxelShape[] composeShapes(VoxelShape base, VoxelShape northWest, VoxelShape northEast, VoxelShape southWest, VoxelShape southEast) {
      return IntStream.range(0, 16).mapToObj(i -> composeShape(i, base, northWest, northEast, southWest, southEast)).toArray(VoxelShape[]::new);
   }

   private static VoxelShape composeShape(int i, VoxelShape base, VoxelShape northWest, VoxelShape northEast, VoxelShape southWest, VoxelShape southEast) {
      VoxelShape _snowman = base;
      if ((i & 1) != 0) {
         _snowman = VoxelShapes.union(base, northWest);
      }

      if ((i & 2) != 0) {
         _snowman = VoxelShapes.union(_snowman, northEast);
      }

      if ((i & 4) != 0) {
         _snowman = VoxelShapes.union(_snowman, southWest);
      }

      if ((i & 8) != 0) {
         _snowman = VoxelShapes.union(_snowman, southEast);
      }

      return _snowman;
   }

   protected StairsBlock(BlockState baseBlockState, AbstractBlock.Settings settings) {
      super(settings);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(FACING, Direction.NORTH)
            .with(HALF, BlockHalf.BOTTOM)
            .with(SHAPE, StairShape.STRAIGHT)
            .with(WATERLOGGED, Boolean.valueOf(false))
      );
      this.baseBlock = baseBlockState.getBlock();
      this.baseBlockState = baseBlockState;
   }

   @Override
   public boolean hasSidedTransparency(BlockState state) {
      return true;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return (state.get(HALF) == BlockHalf.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_INDICES[this.getShapeIndexIndex(state)]];
   }

   private int getShapeIndexIndex(BlockState state) {
      return state.get(SHAPE).ordinal() * 4 + state.get(FACING).getHorizontal();
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      this.baseBlock.randomDisplayTick(state, world, pos, random);
   }

   @Override
   public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
      this.baseBlockState.onBlockBreakStart(world, pos, player);
   }

   @Override
   public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
      this.baseBlock.onBroken(world, pos, state);
   }

   @Override
   public float getBlastResistance() {
      return this.baseBlock.getBlastResistance();
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!state.isOf(state.getBlock())) {
         this.baseBlockState.neighborUpdate(world, pos, Blocks.AIR, pos, false);
         this.baseBlock.onBlockAdded(this.baseBlockState, world, pos, oldState, false);
      }
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!state.isOf(newState.getBlock())) {
         this.baseBlockState.onStateReplaced(world, pos, newState, moved);
      }
   }

   @Override
   public void onSteppedOn(World world, BlockPos pos, Entity entity) {
      this.baseBlock.onSteppedOn(world, pos, entity);
   }

   @Override
   public boolean hasRandomTicks(BlockState state) {
      return this.baseBlock.hasRandomTicks(state);
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      this.baseBlock.randomTick(state, world, pos, random);
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      this.baseBlock.scheduledTick(state, world, pos, random);
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      return this.baseBlockState.onUse(world, player, hand, hit);
   }

   @Override
   public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
      this.baseBlock.onDestroyedByExplosion(world, pos, explosion);
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      Direction _snowman = ctx.getSide();
      BlockPos _snowmanx = ctx.getBlockPos();
      FluidState _snowmanxx = ctx.getWorld().getFluidState(_snowmanx);
      BlockState _snowmanxxx = this.getDefaultState()
         .with(FACING, ctx.getPlayerFacing())
         .with(HALF, _snowman != Direction.DOWN && (_snowman == Direction.UP || !(ctx.getHitPos().y - (double)_snowmanx.getY() > 0.5)) ? BlockHalf.BOTTOM : BlockHalf.TOP)
         .with(WATERLOGGED, Boolean.valueOf(_snowmanxx.getFluid() == Fluids.WATER));
      return _snowmanxxx.with(SHAPE, getStairShape(_snowmanxxx, ctx.getWorld(), _snowmanx));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return direction.getAxis().isHorizontal()
         ? state.with(SHAPE, getStairShape(state, world, pos))
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   private static StairShape getStairShape(BlockState state, BlockView world, BlockPos pos) {
      Direction _snowman = state.get(FACING);
      BlockState _snowmanx = world.getBlockState(pos.offset(_snowman));
      if (isStairs(_snowmanx) && state.get(HALF) == _snowmanx.get(HALF)) {
         Direction _snowmanxx = _snowmanx.get(FACING);
         if (_snowmanxx.getAxis() != state.get(FACING).getAxis() && method_10678(state, world, pos, _snowmanxx.getOpposite())) {
            if (_snowmanxx == _snowman.rotateYCounterclockwise()) {
               return StairShape.OUTER_LEFT;
            }

            return StairShape.OUTER_RIGHT;
         }
      }

      BlockState _snowmanxx = world.getBlockState(pos.offset(_snowman.getOpposite()));
      if (isStairs(_snowmanxx) && state.get(HALF) == _snowmanxx.get(HALF)) {
         Direction _snowmanxxx = _snowmanxx.get(FACING);
         if (_snowmanxxx.getAxis() != state.get(FACING).getAxis() && method_10678(state, world, pos, _snowmanxxx)) {
            if (_snowmanxxx == _snowman.rotateYCounterclockwise()) {
               return StairShape.INNER_LEFT;
            }

            return StairShape.INNER_RIGHT;
         }
      }

      return StairShape.STRAIGHT;
   }

   private static boolean method_10678(BlockState state, BlockView world, BlockPos pos, Direction dir) {
      BlockState _snowman = world.getBlockState(pos.offset(dir));
      return !isStairs(_snowman) || _snowman.get(FACING) != state.get(FACING) || _snowman.get(HALF) != state.get(HALF);
   }

   public static boolean isStairs(BlockState state) {
      return state.getBlock() instanceof StairsBlock;
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      Direction _snowman = state.get(FACING);
      StairShape _snowmanx = state.get(SHAPE);
      switch (mirror) {
         case LEFT_RIGHT:
            if (_snowman.getAxis() == Direction.Axis.Z) {
               switch (_snowmanx) {
                  case INNER_LEFT:
                     return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                  case INNER_RIGHT:
                     return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                  case OUTER_LEFT:
                     return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                  case OUTER_RIGHT:
                     return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                  default:
                     return state.rotate(BlockRotation.CLOCKWISE_180);
               }
            }
            break;
         case FRONT_BACK:
            if (_snowman.getAxis() == Direction.Axis.X) {
               switch (_snowmanx) {
                  case INNER_LEFT:
                     return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_LEFT);
                  case INNER_RIGHT:
                     return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.INNER_RIGHT);
                  case OUTER_LEFT:
                     return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_RIGHT);
                  case OUTER_RIGHT:
                     return state.rotate(BlockRotation.CLOCKWISE_180).with(SHAPE, StairShape.OUTER_LEFT);
                  case STRAIGHT:
                     return state.rotate(BlockRotation.CLOCKWISE_180);
               }
            }
      }

      return super.mirror(state, mirror);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, HALF, SHAPE, WATERLOGGED);
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
