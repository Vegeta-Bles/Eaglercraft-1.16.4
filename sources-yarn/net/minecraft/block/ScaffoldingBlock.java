package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class ScaffoldingBlock extends Block implements Waterloggable {
   private static final VoxelShape NORMAL_OUTLINE_SHAPE;
   private static final VoxelShape BOTTOM_OUTLINE_SHAPE;
   private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   private static final VoxelShape OUTLINE_SHAPE = VoxelShapes.fullCube().offset(0.0, -1.0, 0.0);
   public static final IntProperty DISTANCE = Properties.DISTANCE_0_7;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   public static final BooleanProperty BOTTOM = Properties.BOTTOM;

   protected ScaffoldingBlock(AbstractBlock.Settings arg) {
      super(arg);
      this.setDefaultState(
         this.stateManager.getDefaultState().with(DISTANCE, Integer.valueOf(7)).with(WATERLOGGED, Boolean.valueOf(false)).with(BOTTOM, Boolean.valueOf(false))
      );
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(DISTANCE, WATERLOGGED, BOTTOM);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      if (!context.isHolding(state.getBlock().asItem())) {
         return state.get(BOTTOM) ? BOTTOM_OUTLINE_SHAPE : NORMAL_OUTLINE_SHAPE;
      } else {
         return VoxelShapes.fullCube();
      }
   }

   @Override
   public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
      return VoxelShapes.fullCube();
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      return context.getStack().getItem() == this.asItem();
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockPos lv = ctx.getBlockPos();
      World lv2 = ctx.getWorld();
      int i = calculateDistance(lv2, lv);
      return this.getDefaultState()
         .with(WATERLOGGED, Boolean.valueOf(lv2.getFluidState(lv).getFluid() == Fluids.WATER))
         .with(DISTANCE, Integer.valueOf(i))
         .with(BOTTOM, Boolean.valueOf(this.shouldBeBottom(lv2, lv, i)));
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!world.isClient) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      if (!world.isClient()) {
         world.getBlockTickScheduler().schedule(pos, this, 1);
      }

      return state;
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      int i = calculateDistance(world, pos);
      BlockState lv = state.with(DISTANCE, Integer.valueOf(i)).with(BOTTOM, Boolean.valueOf(this.shouldBeBottom(world, pos, i)));
      if (lv.get(DISTANCE) == 7) {
         if (state.get(DISTANCE) == 7) {
            world.spawnEntity(
               new FallingBlockEntity(
                  world, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, lv.with(WATERLOGGED, Boolean.valueOf(false))
               )
            );
         } else {
            world.breakBlock(pos, true);
         }
      } else if (state != lv) {
         world.setBlockState(pos, lv, 3);
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      return calculateDistance(world, pos) < 7;
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      if (context.isAbove(VoxelShapes.fullCube(), pos, true) && !context.isDescending()) {
         return NORMAL_OUTLINE_SHAPE;
      } else {
         return state.get(DISTANCE) != 0 && state.get(BOTTOM) && context.isAbove(OUTLINE_SHAPE, pos, true) ? COLLISION_SHAPE : VoxelShapes.empty();
      }
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   private boolean shouldBeBottom(BlockView world, BlockPos pos, int distance) {
      return distance > 0 && !world.getBlockState(pos.down()).isOf(this);
   }

   public static int calculateDistance(BlockView world, BlockPos pos) {
      BlockPos.Mutable lv = pos.mutableCopy().move(Direction.DOWN);
      BlockState lv2 = world.getBlockState(lv);
      int i = 7;
      if (lv2.isOf(Blocks.SCAFFOLDING)) {
         i = lv2.get(DISTANCE);
      } else if (lv2.isSideSolidFullSquare(world, lv, Direction.UP)) {
         return 0;
      }

      for (Direction lv3 : Direction.Type.HORIZONTAL) {
         BlockState lv4 = world.getBlockState(lv.set(pos, lv3));
         if (lv4.isOf(Blocks.SCAFFOLDING)) {
            i = Math.min(i, lv4.get(DISTANCE) + 1);
            if (i == 1) {
               break;
            }
         }
      }

      return i;
   }

   static {
      VoxelShape lv = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
      VoxelShape lv2 = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 2.0);
      VoxelShape lv3 = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 2.0);
      VoxelShape lv4 = Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 16.0, 16.0);
      VoxelShape lv5 = Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 16.0, 16.0);
      NORMAL_OUTLINE_SHAPE = VoxelShapes.union(lv, lv2, lv3, lv4, lv5);
      VoxelShape lv6 = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 2.0, 16.0);
      VoxelShape lv7 = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 2.0, 16.0);
      VoxelShape lv8 = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
      VoxelShape lv9 = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
      BOTTOM_OUTLINE_SHAPE = VoxelShapes.union(ScaffoldingBlock.COLLISION_SHAPE, NORMAL_OUTLINE_SHAPE, lv7, lv6, lv9, lv8);
   }
}
