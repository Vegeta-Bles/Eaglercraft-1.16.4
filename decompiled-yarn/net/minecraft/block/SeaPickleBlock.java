package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class SeaPickleBlock extends PlantBlock implements Fertilizable, Waterloggable {
   public static final IntProperty PICKLES = Properties.PICKLES;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   protected static final VoxelShape ONE_PICKLE_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 6.0, 10.0);
   protected static final VoxelShape TWO_PICKLES_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
   protected static final VoxelShape THREE_PICKLES_SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
   protected static final VoxelShape FOUR_PICKLES_SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);

   protected SeaPickleBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(PICKLES, Integer.valueOf(1)).with(WATERLOGGED, Boolean.valueOf(true)));
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = ctx.getWorld().getBlockState(ctx.getBlockPos());
      if (_snowman.isOf(this)) {
         return _snowman.with(PICKLES, Integer.valueOf(Math.min(4, _snowman.get(PICKLES) + 1)));
      } else {
         FluidState _snowmanx = ctx.getWorld().getFluidState(ctx.getBlockPos());
         boolean _snowmanxx = _snowmanx.getFluid() == Fluids.WATER;
         return super.getPlacementState(ctx).with(WATERLOGGED, Boolean.valueOf(_snowmanxx));
      }
   }

   public static boolean isDry(BlockState _snowman) {
      return !_snowman.get(WATERLOGGED);
   }

   @Override
   protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
      return !floor.getCollisionShape(world, pos).getFace(Direction.UP).isEmpty() || floor.isSideSolidFullSquare(world, pos, Direction.UP);
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockPos _snowman = pos.down();
      return this.canPlantOnTop(world.getBlockState(_snowman), world, _snowman);
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (!state.canPlaceAt(world, pos)) {
         return Blocks.AIR.getDefaultState();
      } else {
         if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
         }

         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      }
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      return context.getStack().getItem() == this.asItem() && state.get(PICKLES) < 4 ? true : super.canReplace(state, context);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      switch (state.get(PICKLES)) {
         case 1:
         default:
            return ONE_PICKLE_SHAPE;
         case 2:
            return TWO_PICKLES_SHAPE;
         case 3:
            return THREE_PICKLES_SHAPE;
         case 4:
            return FOUR_PICKLES_SHAPE;
      }
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(PICKLES, WATERLOGGED);
   }

   @Override
   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
      return true;
   }

   @Override
   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
      return true;
   }

   @Override
   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
      if (!isDry(state) && world.getBlockState(pos.down()).isIn(BlockTags.CORAL_BLOCKS)) {
         int _snowman = 5;
         int _snowmanx = 1;
         int _snowmanxx = 2;
         int _snowmanxxx = 0;
         int _snowmanxxxx = pos.getX() - 2;
         int _snowmanxxxxx = 0;

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 5; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanx; _snowmanxxxxxxx++) {
               int _snowmanxxxxxxxx = 2 + pos.getY() - 1;

               for (int _snowmanxxxxxxxxx = _snowmanxxxxxxxx - 2; _snowmanxxxxxxxxx < _snowmanxxxxxxxx; _snowmanxxxxxxxxx++) {
                  BlockPos _snowmanxxxxxxxxxx = new BlockPos(_snowmanxxxx + _snowmanxxxxxx, _snowmanxxxxxxxxx, pos.getZ() - _snowmanxxxxx + _snowmanxxxxxxx);
                  if (_snowmanxxxxxxxxxx != pos && random.nextInt(6) == 0 && world.getBlockState(_snowmanxxxxxxxxxx).isOf(Blocks.WATER)) {
                     BlockState _snowmanxxxxxxxxxxx = world.getBlockState(_snowmanxxxxxxxxxx.down());
                     if (_snowmanxxxxxxxxxxx.isIn(BlockTags.CORAL_BLOCKS)) {
                        world.setBlockState(_snowmanxxxxxxxxxx, Blocks.SEA_PICKLE.getDefaultState().with(PICKLES, Integer.valueOf(random.nextInt(4) + 1)), 3);
                     }
                  }
               }
            }

            if (_snowmanxxx < 2) {
               _snowmanx += 2;
               _snowmanxxxxx++;
            } else {
               _snowmanx -= 2;
               _snowmanxxxxx--;
            }

            _snowmanxxx++;
         }

         world.setBlockState(pos, state.with(PICKLES, Integer.valueOf(4)), 2);
      }
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
