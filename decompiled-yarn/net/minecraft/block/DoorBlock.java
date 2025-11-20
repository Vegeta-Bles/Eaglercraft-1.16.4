package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class DoorBlock extends Block {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   public static final BooleanProperty OPEN = Properties.OPEN;
   public static final EnumProperty<DoorHinge> HINGE = Properties.DOOR_HINGE;
   public static final BooleanProperty POWERED = Properties.POWERED;
   public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
   protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
   protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);

   protected DoorBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(FACING, Direction.NORTH)
            .with(OPEN, Boolean.valueOf(false))
            .with(HINGE, DoorHinge.LEFT)
            .with(POWERED, Boolean.valueOf(false))
            .with(HALF, DoubleBlockHalf.LOWER)
      );
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      Direction _snowman = state.get(FACING);
      boolean _snowmanx = !state.get(OPEN);
      boolean _snowmanxx = state.get(HINGE) == DoorHinge.RIGHT;
      switch (_snowman) {
         case EAST:
         default:
            return _snowmanx ? WEST_SHAPE : (_snowmanxx ? SOUTH_SHAPE : NORTH_SHAPE);
         case SOUTH:
            return _snowmanx ? NORTH_SHAPE : (_snowmanxx ? WEST_SHAPE : EAST_SHAPE);
         case WEST:
            return _snowmanx ? EAST_SHAPE : (_snowmanxx ? NORTH_SHAPE : SOUTH_SHAPE);
         case NORTH:
            return _snowmanx ? SOUTH_SHAPE : (_snowmanxx ? EAST_SHAPE : WEST_SHAPE);
      }
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      DoubleBlockHalf _snowman = state.get(HALF);
      if (direction.getAxis() != Direction.Axis.Y || _snowman == DoubleBlockHalf.LOWER != (direction == Direction.UP)) {
         return _snowman == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)
            ? Blocks.AIR.getDefaultState()
            : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         return newState.isOf(this) && newState.get(HALF) != _snowman
            ? state.with(FACING, newState.get(FACING)).with(OPEN, newState.get(OPEN)).with(HINGE, newState.get(HINGE)).with(POWERED, newState.get(POWERED))
            : Blocks.AIR.getDefaultState();
      }
   }

   @Override
   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!world.isClient && player.isCreative()) {
         TallPlantBlock.onBreakInCreative(world, pos, state, player);
      }

      super.onBreak(world, pos, state, player);
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      switch (type) {
         case LAND:
            return state.get(OPEN);
         case WATER:
            return false;
         case AIR:
            return state.get(OPEN);
         default:
            return false;
      }
   }

   private int getOpenSoundEventId() {
      return this.material == Material.METAL ? 1011 : 1012;
   }

   private int getCloseSoundEventId() {
      return this.material == Material.METAL ? 1005 : 1006;
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockPos _snowman = ctx.getBlockPos();
      if (_snowman.getY() < 255 && ctx.getWorld().getBlockState(_snowman.up()).canReplace(ctx)) {
         World _snowmanx = ctx.getWorld();
         boolean _snowmanxx = _snowmanx.isReceivingRedstonePower(_snowman) || _snowmanx.isReceivingRedstonePower(_snowman.up());
         return this.getDefaultState()
            .with(FACING, ctx.getPlayerFacing())
            .with(HINGE, this.getHinge(ctx))
            .with(POWERED, Boolean.valueOf(_snowmanxx))
            .with(OPEN, Boolean.valueOf(_snowmanxx))
            .with(HALF, DoubleBlockHalf.LOWER);
      } else {
         return null;
      }
   }

   @Override
   public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
      world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER), 3);
   }

   private DoorHinge getHinge(ItemPlacementContext ctx) {
      BlockView _snowman = ctx.getWorld();
      BlockPos _snowmanx = ctx.getBlockPos();
      Direction _snowmanxx = ctx.getPlayerFacing();
      BlockPos _snowmanxxx = _snowmanx.up();
      Direction _snowmanxxxx = _snowmanxx.rotateYCounterclockwise();
      BlockPos _snowmanxxxxx = _snowmanx.offset(_snowmanxxxx);
      BlockState _snowmanxxxxxx = _snowman.getBlockState(_snowmanxxxxx);
      BlockPos _snowmanxxxxxxx = _snowmanxxx.offset(_snowmanxxxx);
      BlockState _snowmanxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxx);
      Direction _snowmanxxxxxxxxx = _snowmanxx.rotateYClockwise();
      BlockPos _snowmanxxxxxxxxxx = _snowmanx.offset(_snowmanxxxxxxxxx);
      BlockState _snowmanxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxx);
      BlockPos _snowmanxxxxxxxxxxxx = _snowmanxxx.offset(_snowmanxxxxxxxxx);
      BlockState _snowmanxxxxxxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxx = (_snowmanxxxxxx.isFullCube(_snowman, _snowmanxxxxx) ? -1 : 0)
         + (_snowmanxxxxxxxx.isFullCube(_snowman, _snowmanxxxxxxx) ? -1 : 0)
         + (_snowmanxxxxxxxxxxx.isFullCube(_snowman, _snowmanxxxxxxxxxx) ? 1 : 0)
         + (_snowmanxxxxxxxxxxxxx.isFullCube(_snowman, _snowmanxxxxxxxxxxxx) ? 1 : 0);
      boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxx.isOf(this) && _snowmanxxxxxx.get(HALF) == DoubleBlockHalf.LOWER;
      boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.isOf(this) && _snowmanxxxxxxxxxxx.get(HALF) == DoubleBlockHalf.LOWER;
      if ((!_snowmanxxxxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxxx) && _snowmanxxxxxxxxxxxxxx <= 0) {
         if ((!_snowmanxxxxxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxx) && _snowmanxxxxxxxxxxxxxx >= 0) {
            int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxx.getOffsetX();
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxx.getOffsetZ();
            Vec3d _snowmanxxxxxxxxxxxxxxxxxxx = ctx.getHitPos();
            double _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.x - (double)_snowmanx.getX();
            double _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.z - (double)_snowmanx.getZ();
            return (_snowmanxxxxxxxxxxxxxxxxx >= 0 || !(_snowmanxxxxxxxxxxxxxxxxxxxxx < 0.5))
                  && (_snowmanxxxxxxxxxxxxxxxxx <= 0 || !(_snowmanxxxxxxxxxxxxxxxxxxxxx > 0.5))
                  && (_snowmanxxxxxxxxxxxxxxxxxx >= 0 || !(_snowmanxxxxxxxxxxxxxxxxxxxx > 0.5))
                  && (_snowmanxxxxxxxxxxxxxxxxxx <= 0 || !(_snowmanxxxxxxxxxxxxxxxxxxxx < 0.5))
               ? DoorHinge.LEFT
               : DoorHinge.RIGHT;
         } else {
            return DoorHinge.LEFT;
         }
      } else {
         return DoorHinge.RIGHT;
      }
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (this.material == Material.METAL) {
         return ActionResult.PASS;
      } else {
         state = state.cycle(OPEN);
         world.setBlockState(pos, state, 10);
         world.syncWorldEvent(player, state.get(OPEN) ? this.getCloseSoundEventId() : this.getOpenSoundEventId(), pos, 0);
         return ActionResult.success(world.isClient);
      }
   }

   public boolean method_30841(BlockState _snowman) {
      return _snowman.get(OPEN);
   }

   public void setOpen(World _snowman, BlockState _snowman, BlockPos _snowman, boolean _snowman) {
      if (_snowman.isOf(this) && _snowman.get(OPEN) != _snowman) {
         _snowman.setBlockState(_snowman, _snowman.with(OPEN, Boolean.valueOf(_snowman)), 10);
         this.playOpenCloseSound(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      boolean _snowman = world.isReceivingRedstonePower(pos)
         || world.isReceivingRedstonePower(pos.offset(state.get(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
      if (block != this && _snowman != state.get(POWERED)) {
         if (_snowman != state.get(OPEN)) {
            this.playOpenCloseSound(world, pos, _snowman);
         }

         world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(_snowman)).with(OPEN, Boolean.valueOf(_snowman)), 2);
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos _snowman = pos.down();
      BlockState _snowmanx = world.getBlockState(_snowman);
      return state.get(HALF) == DoubleBlockHalf.LOWER ? _snowmanx.isSideSolidFullSquare(world, _snowman, Direction.UP) : _snowmanx.isOf(this);
   }

   private void playOpenCloseSound(World world, BlockPos pos, boolean open) {
      world.syncWorldEvent(null, open ? this.getCloseSoundEventId() : this.getOpenSoundEventId(), pos, 0);
   }

   @Override
   public PistonBehavior getPistonBehavior(BlockState state) {
      return PistonBehavior.DESTROY;
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return mirror == BlockMirror.NONE ? state : state.rotate(mirror.getRotation(state.get(FACING))).cycle(HINGE);
   }

   @Override
   public long getRenderingSeed(BlockState state, BlockPos pos) {
      return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(HALF, FACING, OPEN, HINGE, POWERED);
   }

   public static boolean isWoodenDoor(World world, BlockPos pos) {
      return isWoodenDoor(world.getBlockState(pos));
   }

   public static boolean isWoodenDoor(BlockState state) {
      return state.getBlock() instanceof DoorBlock && (state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD);
   }
}
