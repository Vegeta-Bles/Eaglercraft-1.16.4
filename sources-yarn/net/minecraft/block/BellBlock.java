package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.Attachment;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class BellBlock extends BlockWithEntity {
   public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
   public static final EnumProperty<Attachment> ATTACHMENT = Properties.ATTACHMENT;
   public static final BooleanProperty POWERED = Properties.POWERED;
   private static final VoxelShape NORTH_SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 4.0, 16.0, 16.0, 12.0);
   private static final VoxelShape EAST_WEST_SHAPE = Block.createCuboidShape(4.0, 0.0, 0.0, 12.0, 16.0, 16.0);
   private static final VoxelShape BELL_WAIST_SHAPE = Block.createCuboidShape(5.0, 6.0, 5.0, 11.0, 13.0, 11.0);
   private static final VoxelShape BELL_LIP_SHAPE = Block.createCuboidShape(4.0, 4.0, 4.0, 12.0, 6.0, 12.0);
   private static final VoxelShape BELL_SHAPE = VoxelShapes.union(BELL_LIP_SHAPE, BELL_WAIST_SHAPE);
   private static final VoxelShape NORTH_SOUTH_WALLS_SHAPE = VoxelShapes.union(BELL_SHAPE, Block.createCuboidShape(7.0, 13.0, 0.0, 9.0, 15.0, 16.0));
   private static final VoxelShape EAST_WEST_WALLS_SHAPE = VoxelShapes.union(BELL_SHAPE, Block.createCuboidShape(0.0, 13.0, 7.0, 16.0, 15.0, 9.0));
   private static final VoxelShape WEST_WALL_SHAPE = VoxelShapes.union(BELL_SHAPE, Block.createCuboidShape(0.0, 13.0, 7.0, 13.0, 15.0, 9.0));
   private static final VoxelShape EAST_WALL_SHAPE = VoxelShapes.union(BELL_SHAPE, Block.createCuboidShape(3.0, 13.0, 7.0, 16.0, 15.0, 9.0));
   private static final VoxelShape NORTH_WALL_SHAPE = VoxelShapes.union(BELL_SHAPE, Block.createCuboidShape(7.0, 13.0, 0.0, 9.0, 15.0, 13.0));
   private static final VoxelShape SOUTH_WALL_SHAPE = VoxelShapes.union(BELL_SHAPE, Block.createCuboidShape(7.0, 13.0, 3.0, 9.0, 15.0, 16.0));
   private static final VoxelShape HANGING_SHAPE = VoxelShapes.union(BELL_SHAPE, Block.createCuboidShape(7.0, 13.0, 7.0, 9.0, 16.0, 9.0));

   public BellBlock(AbstractBlock.Settings arg) {
      super(arg);
      this.setDefaultState(
         this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(ATTACHMENT, Attachment.FLOOR).with(POWERED, Boolean.valueOf(false))
      );
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      boolean bl2 = world.isReceivingRedstonePower(pos);
      if (bl2 != state.get(POWERED)) {
         if (bl2) {
            this.ring(world, pos, null);
         }

         world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(bl2)), 3);
      }
   }

   @Override
   public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
      Entity lv = projectile.getOwner();
      PlayerEntity lv2 = lv instanceof PlayerEntity ? (PlayerEntity)lv : null;
      this.ring(world, state, hit, lv2, true);
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      return this.ring(world, state, hit, player, true) ? ActionResult.success(world.isClient) : ActionResult.PASS;
   }

   public boolean ring(World world, BlockState state, BlockHitResult arg3, @Nullable PlayerEntity arg4, boolean bl) {
      Direction lv = arg3.getSide();
      BlockPos lv2 = arg3.getBlockPos();
      boolean bl2 = !bl || this.isPointOnBell(state, lv, arg3.getPos().y - (double)lv2.getY());
      if (bl2) {
         boolean bl3 = this.ring(world, lv2, lv);
         if (bl3 && arg4 != null) {
            arg4.incrementStat(Stats.BELL_RING);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isPointOnBell(BlockState state, Direction side, double y) {
      if (side.getAxis() != Direction.Axis.Y && !(y > 0.8124F)) {
         Direction lv = state.get(FACING);
         Attachment lv2 = state.get(ATTACHMENT);
         switch (lv2) {
            case FLOOR:
               return lv.getAxis() == side.getAxis();
            case SINGLE_WALL:
            case DOUBLE_WALL:
               return lv.getAxis() != side.getAxis();
            case CEILING:
               return true;
            default:
               return false;
         }
      } else {
         return false;
      }
   }

   public boolean ring(World world, BlockPos pos, @Nullable Direction arg3) {
      BlockEntity lv = world.getBlockEntity(pos);
      if (!world.isClient && lv instanceof BellBlockEntity) {
         if (arg3 == null) {
            arg3 = world.getBlockState(pos).get(FACING);
         }

         ((BellBlockEntity)lv).activate(arg3);
         world.playSound(null, pos, SoundEvents.BLOCK_BELL_USE, SoundCategory.BLOCKS, 2.0F, 1.0F);
         return true;
      } else {
         return false;
      }
   }

   private VoxelShape getShape(BlockState state) {
      Direction lv = state.get(FACING);
      Attachment lv2 = state.get(ATTACHMENT);
      if (lv2 == Attachment.FLOOR) {
         return lv != Direction.NORTH && lv != Direction.SOUTH ? EAST_WEST_SHAPE : NORTH_SOUTH_SHAPE;
      } else if (lv2 == Attachment.CEILING) {
         return HANGING_SHAPE;
      } else if (lv2 == Attachment.DOUBLE_WALL) {
         return lv != Direction.NORTH && lv != Direction.SOUTH ? EAST_WEST_WALLS_SHAPE : NORTH_SOUTH_WALLS_SHAPE;
      } else if (lv == Direction.NORTH) {
         return NORTH_WALL_SHAPE;
      } else if (lv == Direction.SOUTH) {
         return SOUTH_WALL_SHAPE;
      } else {
         return lv == Direction.EAST ? EAST_WALL_SHAPE : WEST_WALL_SHAPE;
      }
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.getShape(state);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return this.getShape(state);
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      Direction lv = ctx.getSide();
      BlockPos lv2 = ctx.getBlockPos();
      World lv3 = ctx.getWorld();
      Direction.Axis lv4 = lv.getAxis();
      if (lv4 == Direction.Axis.Y) {
         BlockState lv5 = this.getDefaultState()
            .with(ATTACHMENT, lv == Direction.DOWN ? Attachment.CEILING : Attachment.FLOOR)
            .with(FACING, ctx.getPlayerFacing());
         if (lv5.canPlaceAt(ctx.getWorld(), lv2)) {
            return lv5;
         }
      } else {
         boolean bl = lv4 == Direction.Axis.X
               && lv3.getBlockState(lv2.west()).isSideSolidFullSquare(lv3, lv2.west(), Direction.EAST)
               && lv3.getBlockState(lv2.east()).isSideSolidFullSquare(lv3, lv2.east(), Direction.WEST)
            || lv4 == Direction.Axis.Z
               && lv3.getBlockState(lv2.north()).isSideSolidFullSquare(lv3, lv2.north(), Direction.SOUTH)
               && lv3.getBlockState(lv2.south()).isSideSolidFullSquare(lv3, lv2.south(), Direction.NORTH);
         BlockState lv6 = this.getDefaultState().with(FACING, lv.getOpposite()).with(ATTACHMENT, bl ? Attachment.DOUBLE_WALL : Attachment.SINGLE_WALL);
         if (lv6.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return lv6;
         }

         boolean bl2 = lv3.getBlockState(lv2.down()).isSideSolidFullSquare(lv3, lv2.down(), Direction.UP);
         lv6 = lv6.with(ATTACHMENT, bl2 ? Attachment.FLOOR : Attachment.CEILING);
         if (lv6.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return lv6;
         }
      }

      return null;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      Attachment lv = state.get(ATTACHMENT);
      Direction lv2 = getPlacementSide(state).getOpposite();
      if (lv2 == direction && !state.canPlaceAt(world, pos) && lv != Attachment.DOUBLE_WALL) {
         return Blocks.AIR.getDefaultState();
      } else {
         if (direction.getAxis() == state.get(FACING).getAxis()) {
            if (lv == Attachment.DOUBLE_WALL && !newState.isSideSolidFullSquare(world, posFrom, direction)) {
               return state.with(ATTACHMENT, Attachment.SINGLE_WALL).with(FACING, direction.getOpposite());
            }

            if (lv == Attachment.SINGLE_WALL && lv2.getOpposite() == direction && newState.isSideSolidFullSquare(world, posFrom, state.get(FACING))) {
               return state.with(ATTACHMENT, Attachment.DOUBLE_WALL);
            }
         }

         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      Direction lv = getPlacementSide(state).getOpposite();
      return lv == Direction.UP ? Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) : WallMountedBlock.canPlaceAt(world, pos, lv);
   }

   private static Direction getPlacementSide(BlockState state) {
      switch ((Attachment)state.get(ATTACHMENT)) {
         case FLOOR:
            return Direction.UP;
         case CEILING:
            return Direction.DOWN;
         default:
            return state.get(FACING).getOpposite();
      }
   }

   @Override
   public PistonBehavior getPistonBehavior(BlockState state) {
      return PistonBehavior.DESTROY;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, ATTACHMENT, POWERED);
   }

   @Nullable
   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new BellBlockEntity();
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
