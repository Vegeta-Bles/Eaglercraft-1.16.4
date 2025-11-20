package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class SnowBlock extends Block {
   public static final IntProperty LAYERS = Properties.LAYERS;
   protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{
      VoxelShapes.empty(),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };

   protected SnowBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(LAYERS, Integer.valueOf(1)));
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      switch (type) {
         case LAND:
            return state.get(LAYERS) < 5;
         case WATER:
            return false;
         case AIR:
            return false;
         default:
            return false;
      }
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return LAYERS_TO_SHAPE[state.get(LAYERS)];
   }

   @Override
   public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return LAYERS_TO_SHAPE[state.get(LAYERS) - 1];
   }

   @Override
   public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
      return LAYERS_TO_SHAPE[state.get(LAYERS)];
   }

   @Override
   public VoxelShape getVisualShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return LAYERS_TO_SHAPE[state.get(LAYERS)];
   }

   @Override
   public boolean hasSidedTransparency(BlockState state) {
      return true;
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos.down());
      if (_snowman.isOf(Blocks.ICE) || _snowman.isOf(Blocks.PACKED_ICE) || _snowman.isOf(Blocks.BARRIER)) {
         return false;
      } else {
         return !_snowman.isOf(Blocks.HONEY_BLOCK) && !_snowman.isOf(Blocks.SOUL_SAND)
            ? Block.isFaceFullSquare(_snowman.getCollisionShape(world, pos.down()), Direction.UP) || _snowman.getBlock() == this && _snowman.get(LAYERS) == 8
            : true;
      }
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (world.getLightLevel(LightType.BLOCK, pos) > 11) {
         dropStacks(state, world, pos);
         world.removeBlock(pos, false);
      }
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      int _snowman = state.get(LAYERS);
      if (context.getStack().getItem() != this.asItem() || _snowman >= 8) {
         return _snowman == 1;
      } else {
         return context.canReplaceExisting() ? context.getSide() == Direction.UP : true;
      }
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = ctx.getWorld().getBlockState(ctx.getBlockPos());
      if (_snowman.isOf(this)) {
         int _snowmanx = _snowman.get(LAYERS);
         return _snowman.with(LAYERS, Integer.valueOf(Math.min(8, _snowmanx + 1)));
      } else {
         return super.getPlacementState(ctx);
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(LAYERS);
   }
}
