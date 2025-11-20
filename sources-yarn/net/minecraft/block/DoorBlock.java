package net.minecraft.block;

import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   protected DoorBlock(AbstractBlock.Settings arg) {
      super(arg);
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
      Direction lv = state.get(FACING);
      boolean bl = !state.get(OPEN);
      boolean bl2 = state.get(HINGE) == DoorHinge.RIGHT;
      switch (lv) {
         case EAST:
         default:
            return bl ? WEST_SHAPE : (bl2 ? SOUTH_SHAPE : NORTH_SHAPE);
         case SOUTH:
            return bl ? NORTH_SHAPE : (bl2 ? WEST_SHAPE : EAST_SHAPE);
         case WEST:
            return bl ? EAST_SHAPE : (bl2 ? NORTH_SHAPE : SOUTH_SHAPE);
         case NORTH:
            return bl ? SOUTH_SHAPE : (bl2 ? EAST_SHAPE : WEST_SHAPE);
      }
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      DoubleBlockHalf lv = state.get(HALF);
      if (direction.getAxis() != Direction.Axis.Y || lv == DoubleBlockHalf.LOWER != (direction == Direction.UP)) {
         return lv == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)
            ? Blocks.AIR.getDefaultState()
            : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         return newState.isOf(this) && newState.get(HALF) != lv
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
      BlockPos lv = ctx.getBlockPos();
      if (lv.getY() < 255 && ctx.getWorld().getBlockState(lv.up()).canReplace(ctx)) {
         World lv2 = ctx.getWorld();
         boolean bl = lv2.isReceivingRedstonePower(lv) || lv2.isReceivingRedstonePower(lv.up());
         return this.getDefaultState()
            .with(FACING, ctx.getPlayerFacing())
            .with(HINGE, this.getHinge(ctx))
            .with(POWERED, Boolean.valueOf(bl))
            .with(OPEN, Boolean.valueOf(bl))
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
      BlockView lv = ctx.getWorld();
      BlockPos lv2 = ctx.getBlockPos();
      Direction lv3 = ctx.getPlayerFacing();
      BlockPos lv4 = lv2.up();
      Direction lv5 = lv3.rotateYCounterclockwise();
      BlockPos lv6 = lv2.offset(lv5);
      BlockState lv7 = lv.getBlockState(lv6);
      BlockPos lv8 = lv4.offset(lv5);
      BlockState lv9 = lv.getBlockState(lv8);
      Direction lv10 = lv3.rotateYClockwise();
      BlockPos lv11 = lv2.offset(lv10);
      BlockState lv12 = lv.getBlockState(lv11);
      BlockPos lv13 = lv4.offset(lv10);
      BlockState lv14 = lv.getBlockState(lv13);
      int i = (lv7.isFullCube(lv, lv6) ? -1 : 0)
         + (lv9.isFullCube(lv, lv8) ? -1 : 0)
         + (lv12.isFullCube(lv, lv11) ? 1 : 0)
         + (lv14.isFullCube(lv, lv13) ? 1 : 0);
      boolean bl = lv7.isOf(this) && lv7.get(HALF) == DoubleBlockHalf.LOWER;
      boolean bl2 = lv12.isOf(this) && lv12.get(HALF) == DoubleBlockHalf.LOWER;
      if ((!bl || bl2) && i <= 0) {
         if ((!bl2 || bl) && i >= 0) {
            int j = lv3.getOffsetX();
            int k = lv3.getOffsetZ();
            Vec3d lv15 = ctx.getHitPos();
            double d = lv15.x - (double)lv2.getX();
            double e = lv15.z - (double)lv2.getZ();
            return (j >= 0 || !(e < 0.5)) && (j <= 0 || !(e > 0.5)) && (k >= 0 || !(d > 0.5)) && (k <= 0 || !(d < 0.5)) ? DoorHinge.LEFT : DoorHinge.RIGHT;
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

   public boolean method_30841(BlockState arg) {
      return arg.get(OPEN);
   }

   public void setOpen(World arg, BlockState arg2, BlockPos arg3, boolean bl) {
      if (arg2.isOf(this) && arg2.get(OPEN) != bl) {
         arg.setBlockState(arg3, arg2.with(OPEN, Boolean.valueOf(bl)), 10);
         this.playOpenCloseSound(arg, arg3, bl);
      }
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      boolean bl2 = world.isReceivingRedstonePower(pos)
         || world.isReceivingRedstonePower(pos.offset(state.get(HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN));
      if (block != this && bl2 != state.get(POWERED)) {
         if (bl2 != state.get(OPEN)) {
            this.playOpenCloseSound(world, pos, bl2);
         }

         world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(bl2)).with(OPEN, Boolean.valueOf(bl2)), 2);
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos lv = pos.down();
      BlockState lv2 = world.getBlockState(lv);
      return state.get(HALF) == DoubleBlockHalf.LOWER ? lv2.isSideSolidFullSquare(world, lv, Direction.UP) : lv2.isOf(this);
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

   @Environment(EnvType.CLIENT)
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
