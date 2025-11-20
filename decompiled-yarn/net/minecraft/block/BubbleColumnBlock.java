package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class BubbleColumnBlock extends Block implements FluidDrainable {
   public static final BooleanProperty DRAG = Properties.DRAG;

   public BubbleColumnBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(DRAG, Boolean.valueOf(true)));
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      BlockState _snowman = world.getBlockState(pos.up());
      if (_snowman.isAir()) {
         entity.onBubbleColumnSurfaceCollision(state.get(DRAG));
         if (!world.isClient) {
            ServerWorld _snowmanx = (ServerWorld)world;

            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               _snowmanx.spawnParticles(
                  ParticleTypes.SPLASH,
                  (double)pos.getX() + world.random.nextDouble(),
                  (double)(pos.getY() + 1),
                  (double)pos.getZ() + world.random.nextDouble(),
                  1,
                  0.0,
                  0.0,
                  0.0,
                  1.0
               );
               _snowmanx.spawnParticles(
                  ParticleTypes.BUBBLE,
                  (double)pos.getX() + world.random.nextDouble(),
                  (double)(pos.getY() + 1),
                  (double)pos.getZ() + world.random.nextDouble(),
                  1,
                  0.0,
                  0.01,
                  0.0,
                  0.2
               );
            }
         }
      } else {
         entity.onBubbleColumnCollision(state.get(DRAG));
      }
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      update(world, pos.up(), calculateDrag(world, pos.down()));
   }

   @Override
   public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      update(world, pos.up(), calculateDrag(world, pos));
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return Fluids.WATER.getStill(false);
   }

   public static void update(WorldAccess world, BlockPos pos, boolean drag) {
      if (isStillWater(world, pos)) {
         world.setBlockState(pos, Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, Boolean.valueOf(drag)), 2);
      }
   }

   public static boolean isStillWater(WorldAccess world, BlockPos pos) {
      FluidState _snowman = world.getFluidState(pos);
      return world.getBlockState(pos).isOf(Blocks.WATER) && _snowman.getLevel() >= 8 && _snowman.isStill();
   }

   private static boolean calculateDrag(BlockView world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos);
      return _snowman.isOf(Blocks.BUBBLE_COLUMN) ? _snowman.get(DRAG) : !_snowman.isOf(Blocks.SOUL_SAND);
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      double _snowman = (double)pos.getX();
      double _snowmanx = (double)pos.getY();
      double _snowmanxx = (double)pos.getZ();
      if (state.get(DRAG)) {
         world.addImportantParticle(ParticleTypes.CURRENT_DOWN, _snowman + 0.5, _snowmanx + 0.8, _snowmanxx, 0.0, 0.0, 0.0);
         if (random.nextInt(200) == 0) {
            world.playSound(
               _snowman,
               _snowmanx,
               _snowmanxx,
               SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,
               SoundCategory.BLOCKS,
               0.2F + random.nextFloat() * 0.2F,
               0.9F + random.nextFloat() * 0.15F,
               false
            );
         }
      } else {
         world.addImportantParticle(ParticleTypes.BUBBLE_COLUMN_UP, _snowman + 0.5, _snowmanx, _snowmanxx + 0.5, 0.0, 0.04, 0.0);
         world.addImportantParticle(
            ParticleTypes.BUBBLE_COLUMN_UP, _snowman + (double)random.nextFloat(), _snowmanx + (double)random.nextFloat(), _snowmanxx + (double)random.nextFloat(), 0.0, 0.04, 0.0
         );
         if (random.nextInt(200) == 0) {
            world.playSound(
               _snowman,
               _snowmanx,
               _snowmanxx,
               SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_AMBIENT,
               SoundCategory.BLOCKS,
               0.2F + random.nextFloat() * 0.2F,
               0.9F + random.nextFloat() * 0.15F,
               false
            );
         }
      }
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (!state.canPlaceAt(world, pos)) {
         return Blocks.WATER.getDefaultState();
      } else {
         if (direction == Direction.DOWN) {
            world.setBlockState(pos, Blocks.BUBBLE_COLUMN.getDefaultState().with(DRAG, Boolean.valueOf(calculateDrag(world, posFrom))), 2);
         } else if (direction == Direction.UP && !newState.isOf(Blocks.BUBBLE_COLUMN) && isStillWater(world, posFrom)) {
            world.getBlockTickScheduler().schedule(pos, this, 5);
         }

         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
         return super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
      }
   }

   @Override
   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
      BlockState _snowman = world.getBlockState(pos.down());
      return _snowman.isOf(Blocks.BUBBLE_COLUMN) || _snowman.isOf(Blocks.MAGMA_BLOCK) || _snowman.isOf(Blocks.SOUL_SAND);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return VoxelShapes.empty();
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.INVISIBLE;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(DRAG);
   }

   @Override
   public Fluid tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
      world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
      return Fluids.WATER;
   }
}
