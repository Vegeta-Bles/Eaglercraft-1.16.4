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

   public BellBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(
         this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(ATTACHMENT, Attachment.FLOOR).with(POWERED, Boolean.valueOf(false))
      );
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      boolean _snowman = world.isReceivingRedstonePower(pos);
      if (_snowman != state.get(POWERED)) {
         if (_snowman) {
            this.ring(world, pos, null);
         }

         world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(_snowman)), 3);
      }
   }

   @Override
   public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
      Entity _snowman = projectile.getOwner();
      PlayerEntity _snowmanx = _snowman instanceof PlayerEntity ? (PlayerEntity)_snowman : null;
      this.ring(world, state, hit, _snowmanx, true);
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      return this.ring(world, state, hit, player, true) ? ActionResult.success(world.isClient) : ActionResult.PASS;
   }

   public boolean ring(World world, BlockState state, BlockHitResult _snowman, @Nullable PlayerEntity _snowman, boolean _snowman) {
      Direction _snowmanxxx = _snowman.getSide();
      BlockPos _snowmanxxxx = _snowman.getBlockPos();
      boolean _snowmanxxxxx = !_snowman || this.isPointOnBell(state, _snowmanxxx, _snowman.getPos().y - (double)_snowmanxxxx.getY());
      if (_snowmanxxxxx) {
         boolean _snowmanxxxxxx = this.ring(world, _snowmanxxxx, _snowmanxxx);
         if (_snowmanxxxxxx && _snowman != null) {
            _snowman.incrementStat(Stats.BELL_RING);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean isPointOnBell(BlockState state, Direction side, double y) {
      if (side.getAxis() != Direction.Axis.Y && !(y > 0.8124F)) {
         Direction _snowman = state.get(FACING);
         Attachment _snowmanx = state.get(ATTACHMENT);
         switch (_snowmanx) {
            case FLOOR:
               return _snowman.getAxis() == side.getAxis();
            case SINGLE_WALL:
            case DOUBLE_WALL:
               return _snowman.getAxis() != side.getAxis();
            case CEILING:
               return true;
            default:
               return false;
         }
      } else {
         return false;
      }
   }

   public boolean ring(World world, BlockPos pos, @Nullable Direction _snowman) {
      BlockEntity _snowmanx = world.getBlockEntity(pos);
      if (!world.isClient && _snowmanx instanceof BellBlockEntity) {
         if (_snowman == null) {
            _snowman = world.getBlockState(pos).get(FACING);
         }

         ((BellBlockEntity)_snowmanx).activate(_snowman);
         world.playSound(null, pos, SoundEvents.BLOCK_BELL_USE, SoundCategory.BLOCKS, 2.0F, 1.0F);
         return true;
      } else {
         return false;
      }
   }

   private VoxelShape getShape(BlockState state) {
      Direction _snowman = state.get(FACING);
      Attachment _snowmanx = state.get(ATTACHMENT);
      if (_snowmanx == Attachment.FLOOR) {
         return _snowman != Direction.NORTH && _snowman != Direction.SOUTH ? EAST_WEST_SHAPE : NORTH_SOUTH_SHAPE;
      } else if (_snowmanx == Attachment.CEILING) {
         return HANGING_SHAPE;
      } else if (_snowmanx == Attachment.DOUBLE_WALL) {
         return _snowman != Direction.NORTH && _snowman != Direction.SOUTH ? EAST_WEST_WALLS_SHAPE : NORTH_SOUTH_WALLS_SHAPE;
      } else if (_snowman == Direction.NORTH) {
         return NORTH_WALL_SHAPE;
      } else if (_snowman == Direction.SOUTH) {
         return SOUTH_WALL_SHAPE;
      } else {
         return _snowman == Direction.EAST ? EAST_WALL_SHAPE : WEST_WALL_SHAPE;
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
      Direction _snowman = ctx.getSide();
      BlockPos _snowmanx = ctx.getBlockPos();
      World _snowmanxx = ctx.getWorld();
      Direction.Axis _snowmanxxx = _snowman.getAxis();
      if (_snowmanxxx == Direction.Axis.Y) {
         BlockState _snowmanxxxx = this.getDefaultState()
            .with(ATTACHMENT, _snowman == Direction.DOWN ? Attachment.CEILING : Attachment.FLOOR)
            .with(FACING, ctx.getPlayerFacing());
         if (_snowmanxxxx.canPlaceAt(ctx.getWorld(), _snowmanx)) {
            return _snowmanxxxx;
         }
      } else {
         boolean _snowmanxxxx = _snowmanxxx == Direction.Axis.X
               && _snowmanxx.getBlockState(_snowmanx.west()).isSideSolidFullSquare(_snowmanxx, _snowmanx.west(), Direction.EAST)
               && _snowmanxx.getBlockState(_snowmanx.east()).isSideSolidFullSquare(_snowmanxx, _snowmanx.east(), Direction.WEST)
            || _snowmanxxx == Direction.Axis.Z
               && _snowmanxx.getBlockState(_snowmanx.north()).isSideSolidFullSquare(_snowmanxx, _snowmanx.north(), Direction.SOUTH)
               && _snowmanxx.getBlockState(_snowmanx.south()).isSideSolidFullSquare(_snowmanxx, _snowmanx.south(), Direction.NORTH);
         BlockState _snowmanxxxxx = this.getDefaultState().with(FACING, _snowman.getOpposite()).with(ATTACHMENT, _snowmanxxxx ? Attachment.DOUBLE_WALL : Attachment.SINGLE_WALL);
         if (_snowmanxxxxx.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return _snowmanxxxxx;
         }

         boolean _snowmanxxxxxx = _snowmanxx.getBlockState(_snowmanx.down()).isSideSolidFullSquare(_snowmanxx, _snowmanx.down(), Direction.UP);
         _snowmanxxxxx = _snowmanxxxxx.with(ATTACHMENT, _snowmanxxxxxx ? Attachment.FLOOR : Attachment.CEILING);
         if (_snowmanxxxxx.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return _snowmanxxxxx;
         }
      }

      return null;
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      Attachment _snowman = state.get(ATTACHMENT);
      Direction _snowmanx = getPlacementSide(state).getOpposite();
      if (_snowmanx == direction && !state.canPlaceAt(world, pos) && _snowman != Attachment.DOUBLE_WALL) {
         return Blocks.AIR.getDefaultState();
      } else {
         if (direction.getAxis() == state.get(FACING).getAxis()) {
            if (_snowman == Attachment.DOUBLE_WALL && !newState.isSideSolidFullSquare(world, posFrom, direction)) {
               return state.with(ATTACHMENT, Attachment.SINGLE_WALL).with(FACING, direction.getOpposite());
            }

            if (_snowman == Attachment.SINGLE_WALL && _snowmanx.getOpposite() == direction && newState.isSideSolidFullSquare(world, posFrom, state.get(FACING))) {
               return state.with(ATTACHMENT, Attachment.DOUBLE_WALL);
            }
         }

         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      Direction _snowman = getPlacementSide(state).getOpposite();
      return _snowman == Direction.UP ? Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) : WallMountedBlock.canPlaceAt(world, pos, _snowman);
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
