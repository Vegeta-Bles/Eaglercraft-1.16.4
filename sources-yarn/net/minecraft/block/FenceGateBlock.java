package net.minecraft.block;

import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
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

public class FenceGateBlock extends HorizontalFacingBlock {
   public static final BooleanProperty OPEN = Properties.OPEN;
   public static final BooleanProperty POWERED = Properties.POWERED;
   public static final BooleanProperty IN_WALL = Properties.IN_WALL;
   protected static final VoxelShape Z_AXIS_SHAPE = Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
   protected static final VoxelShape X_AXIS_SHAPE = Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
   protected static final VoxelShape IN_WALL_Z_AXIS_SHAPE = Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 13.0, 10.0);
   protected static final VoxelShape IN_WALL_X_AXIS_SHAPE = Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 13.0, 16.0);
   protected static final VoxelShape Z_AXIS_COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 6.0, 16.0, 24.0, 10.0);
   protected static final VoxelShape X_AXIS_COLLISION_SHAPE = Block.createCuboidShape(6.0, 0.0, 0.0, 10.0, 24.0, 16.0);
   protected static final VoxelShape Z_AXIS_CULL_SHAPE = VoxelShapes.union(
      Block.createCuboidShape(0.0, 5.0, 7.0, 2.0, 16.0, 9.0), Block.createCuboidShape(14.0, 5.0, 7.0, 16.0, 16.0, 9.0)
   );
   protected static final VoxelShape X_AXIS_CULL_SHAPE = VoxelShapes.union(
      Block.createCuboidShape(7.0, 5.0, 0.0, 9.0, 16.0, 2.0), Block.createCuboidShape(7.0, 5.0, 14.0, 9.0, 16.0, 16.0)
   );
   protected static final VoxelShape IN_WALL_Z_AXIS_CULL_SHAPE = VoxelShapes.union(
      Block.createCuboidShape(0.0, 2.0, 7.0, 2.0, 13.0, 9.0), Block.createCuboidShape(14.0, 2.0, 7.0, 16.0, 13.0, 9.0)
   );
   protected static final VoxelShape IN_WALL_X_AXIS_CULL_SHAPE = VoxelShapes.union(
      Block.createCuboidShape(7.0, 2.0, 0.0, 9.0, 13.0, 2.0), Block.createCuboidShape(7.0, 2.0, 14.0, 9.0, 13.0, 16.0)
   );

   public FenceGateBlock(AbstractBlock.Settings arg) {
      super(arg);
      this.setDefaultState(
         this.stateManager.getDefaultState().with(OPEN, Boolean.valueOf(false)).with(POWERED, Boolean.valueOf(false)).with(IN_WALL, Boolean.valueOf(false))
      );
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      if (state.get(IN_WALL)) {
         return state.get(FACING).getAxis() == Direction.Axis.X ? IN_WALL_X_AXIS_SHAPE : IN_WALL_Z_AXIS_SHAPE;
      } else {
         return state.get(FACING).getAxis() == Direction.Axis.X ? X_AXIS_SHAPE : Z_AXIS_SHAPE;
      }
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      Direction.Axis lv = direction.getAxis();
      if (state.get(FACING).rotateYClockwise().getAxis() != lv) {
         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      } else {
         boolean bl = this.isWall(newState) || this.isWall(world.getBlockState(pos.offset(direction.getOpposite())));
         return state.with(IN_WALL, Boolean.valueOf(bl));
      }
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      if (state.get(OPEN)) {
         return VoxelShapes.empty();
      } else {
         return state.get(FACING).getAxis() == Direction.Axis.Z ? Z_AXIS_COLLISION_SHAPE : X_AXIS_COLLISION_SHAPE;
      }
   }

   @Override
   public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
      if (state.get(IN_WALL)) {
         return state.get(FACING).getAxis() == Direction.Axis.X ? IN_WALL_X_AXIS_CULL_SHAPE : IN_WALL_Z_AXIS_CULL_SHAPE;
      } else {
         return state.get(FACING).getAxis() == Direction.Axis.X ? X_AXIS_CULL_SHAPE : Z_AXIS_CULL_SHAPE;
      }
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

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      World lv = ctx.getWorld();
      BlockPos lv2 = ctx.getBlockPos();
      boolean bl = lv.isReceivingRedstonePower(lv2);
      Direction lv3 = ctx.getPlayerFacing();
      Direction.Axis lv4 = lv3.getAxis();
      boolean bl2 = lv4 == Direction.Axis.Z && (this.isWall(lv.getBlockState(lv2.west())) || this.isWall(lv.getBlockState(lv2.east())))
         || lv4 == Direction.Axis.X && (this.isWall(lv.getBlockState(lv2.north())) || this.isWall(lv.getBlockState(lv2.south())));
      return this.getDefaultState().with(FACING, lv3).with(OPEN, Boolean.valueOf(bl)).with(POWERED, Boolean.valueOf(bl)).with(IN_WALL, Boolean.valueOf(bl2));
   }

   private boolean isWall(BlockState state) {
      return state.getBlock().isIn(BlockTags.WALLS);
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      if (state.get(OPEN)) {
         state = state.with(OPEN, Boolean.valueOf(false));
         world.setBlockState(pos, state, 10);
      } else {
         Direction lv = player.getHorizontalFacing();
         if (state.get(FACING) == lv.getOpposite()) {
            state = state.with(FACING, lv);
         }

         state = state.with(OPEN, Boolean.valueOf(true));
         world.setBlockState(pos, state, 10);
      }

      world.syncWorldEvent(player, state.get(OPEN) ? 1008 : 1014, pos, 0);
      return ActionResult.success(world.isClient);
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      if (!world.isClient) {
         boolean bl2 = world.isReceivingRedstonePower(pos);
         if (state.get(POWERED) != bl2) {
            world.setBlockState(pos, state.with(POWERED, Boolean.valueOf(bl2)).with(OPEN, Boolean.valueOf(bl2)), 2);
            if (state.get(OPEN) != bl2) {
               world.syncWorldEvent(null, bl2 ? 1008 : 1014, pos, 0);
            }
         }
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(FACING, OPEN, POWERED, IN_WALL);
   }

   public static boolean canWallConnect(BlockState state, Direction side) {
      return state.get(FACING).getAxis() == side.rotateYClockwise().getAxis();
   }
}
