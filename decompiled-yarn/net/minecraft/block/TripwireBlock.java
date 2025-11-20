package net.minecraft.block;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class TripwireBlock extends Block {
   public static final BooleanProperty POWERED = Properties.POWERED;
   public static final BooleanProperty ATTACHED = Properties.ATTACHED;
   public static final BooleanProperty DISARMED = Properties.DISARMED;
   public static final BooleanProperty NORTH = ConnectingBlock.NORTH;
   public static final BooleanProperty EAST = ConnectingBlock.EAST;
   public static final BooleanProperty SOUTH = ConnectingBlock.SOUTH;
   public static final BooleanProperty WEST = ConnectingBlock.WEST;
   private static final Map<Direction, BooleanProperty> FACING_PROPERTIES = HorizontalConnectingBlock.FACING_PROPERTIES;
   protected static final VoxelShape ATTACHED_SHAPE = Block.createCuboidShape(0.0, 1.0, 0.0, 16.0, 2.5, 16.0);
   protected static final VoxelShape DETACHED_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   private final TripwireHookBlock hookBlock;

   public TripwireBlock(TripwireHookBlock hookBlock, AbstractBlock.Settings settings) {
      super(settings);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(POWERED, Boolean.valueOf(false))
            .with(ATTACHED, Boolean.valueOf(false))
            .with(DISARMED, Boolean.valueOf(false))
            .with(NORTH, Boolean.valueOf(false))
            .with(EAST, Boolean.valueOf(false))
            .with(SOUTH, Boolean.valueOf(false))
            .with(WEST, Boolean.valueOf(false))
      );
      this.hookBlock = hookBlock;
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return state.get(ATTACHED) ? ATTACHED_SHAPE : DETACHED_SHAPE;
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockView _snowman = ctx.getWorld();
      BlockPos _snowmanx = ctx.getBlockPos();
      return this.getDefaultState()
         .with(NORTH, Boolean.valueOf(this.shouldConnectTo(_snowman.getBlockState(_snowmanx.north()), Direction.NORTH)))
         .with(EAST, Boolean.valueOf(this.shouldConnectTo(_snowman.getBlockState(_snowmanx.east()), Direction.EAST)))
         .with(SOUTH, Boolean.valueOf(this.shouldConnectTo(_snowman.getBlockState(_snowmanx.south()), Direction.SOUTH)))
         .with(WEST, Boolean.valueOf(this.shouldConnectTo(_snowman.getBlockState(_snowmanx.west()), Direction.WEST)));
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return direction.getAxis().isHorizontal()
         ? state.with(FACING_PROPERTIES.get(direction), Boolean.valueOf(this.shouldConnectTo(newState, direction)))
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!oldState.isOf(state.getBlock())) {
         this.update(world, pos, state);
      }
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!moved && !state.isOf(newState.getBlock())) {
         this.update(world, pos, state.with(POWERED, Boolean.valueOf(true)));
      }
   }

   @Override
   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!world.isClient && !player.getMainHandStack().isEmpty() && player.getMainHandStack().getItem() == Items.SHEARS) {
         world.setBlockState(pos, state.with(DISARMED, Boolean.valueOf(true)), 4);
      }

      super.onBreak(world, pos, state, player);
   }

   private void update(World world, BlockPos pos, BlockState state) {
      for (Direction _snowman : new Direction[]{Direction.SOUTH, Direction.WEST}) {
         for (int _snowmanx = 1; _snowmanx < 42; _snowmanx++) {
            BlockPos _snowmanxx = pos.offset(_snowman, _snowmanx);
            BlockState _snowmanxxx = world.getBlockState(_snowmanxx);
            if (_snowmanxxx.isOf(this.hookBlock)) {
               if (_snowmanxxx.get(TripwireHookBlock.FACING) == _snowman.getOpposite()) {
                  this.hookBlock.update(world, _snowmanxx, _snowmanxxx, false, true, _snowmanx, state);
               }
               break;
            }

            if (!_snowmanxxx.isOf(this)) {
               break;
            }
         }
      }
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      if (!world.isClient) {
         if (!state.get(POWERED)) {
            this.updatePowered(world, pos);
         }
      }
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (world.getBlockState(pos).get(POWERED)) {
         this.updatePowered(world, pos);
      }
   }

   private void updatePowered(World world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos);
      boolean _snowmanx = _snowman.get(POWERED);
      boolean _snowmanxx = false;
      List<? extends Entity> _snowmanxxx = world.getOtherEntities(null, _snowman.getOutlineShape(world, pos).getBoundingBox().offset(pos));
      if (!_snowmanxxx.isEmpty()) {
         for (Entity _snowmanxxxx : _snowmanxxx) {
            if (!_snowmanxxxx.canAvoidTraps()) {
               _snowmanxx = true;
               break;
            }
         }
      }

      if (_snowmanxx != _snowmanx) {
         _snowman = _snowman.with(POWERED, Boolean.valueOf(_snowmanxx));
         world.setBlockState(pos, _snowman, 3);
         this.update(world, pos, _snowman);
      }

      if (_snowmanxx) {
         world.getBlockTickScheduler().schedule(new BlockPos(pos), this, 10);
      }
   }

   public boolean shouldConnectTo(BlockState state, Direction facing) {
      Block _snowman = state.getBlock();
      return _snowman == this.hookBlock ? state.get(TripwireHookBlock.FACING) == facing.getOpposite() : _snowman == this;
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

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(POWERED, ATTACHED, DISARMED, NORTH, EAST, WEST, SOUTH);
   }
}
