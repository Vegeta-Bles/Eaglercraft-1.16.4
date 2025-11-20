package net.minecraft.fluid;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class LavaFluid extends FlowableFluid {
   public LavaFluid() {
   }

   @Override
   public Fluid getFlowing() {
      return Fluids.FLOWING_LAVA;
   }

   @Override
   public Fluid getStill() {
      return Fluids.LAVA;
   }

   @Override
   public Item getBucketItem() {
      return Items.LAVA_BUCKET;
   }

   @Override
   public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
      BlockPos _snowman = pos.up();
      if (world.getBlockState(_snowman).isAir() && !world.getBlockState(_snowman).isOpaqueFullCube(world, _snowman)) {
         if (random.nextInt(100) == 0) {
            double _snowmanx = (double)pos.getX() + random.nextDouble();
            double _snowmanxx = (double)pos.getY() + 1.0;
            double _snowmanxxx = (double)pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.LAVA, _snowmanx, _snowmanxx, _snowmanxxx, 0.0, 0.0, 0.0);
            world.playSound(
               _snowmanx, _snowmanxx, _snowmanxxx, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false
            );
         }

         if (random.nextInt(200) == 0) {
            world.playSound(
               (double)pos.getX(),
               (double)pos.getY(),
               (double)pos.getZ(),
               SoundEvents.BLOCK_LAVA_AMBIENT,
               SoundCategory.BLOCKS,
               0.2F + random.nextFloat() * 0.2F,
               0.9F + random.nextFloat() * 0.15F,
               false
            );
         }
      }
   }

   @Override
   public void onRandomTick(World world, BlockPos pos, FluidState state, Random _snowman) {
      if (world.getGameRules().getBoolean(GameRules.DO_FIRE_TICK)) {
         int _snowmanx = _snowman.nextInt(3);
         if (_snowmanx > 0) {
            BlockPos _snowmanxx = pos;

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanx; _snowmanxxx++) {
               _snowmanxx = _snowmanxx.add(_snowman.nextInt(3) - 1, 1, _snowman.nextInt(3) - 1);
               if (!world.canSetBlock(_snowmanxx)) {
                  return;
               }

               BlockState _snowmanxxxx = world.getBlockState(_snowmanxx);
               if (_snowmanxxxx.isAir()) {
                  if (this.canLightFire(world, _snowmanxx)) {
                     world.setBlockState(_snowmanxx, AbstractFireBlock.getState(world, _snowmanxx));
                     return;
                  }
               } else if (_snowmanxxxx.getMaterial().blocksMovement()) {
                  return;
               }
            }
         } else {
            for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
               BlockPos _snowmanxxx = pos.add(_snowman.nextInt(3) - 1, 0, _snowman.nextInt(3) - 1);
               if (!world.canSetBlock(_snowmanxxx)) {
                  return;
               }

               if (world.isAir(_snowmanxxx.up()) && this.hasBurnableBlock(world, _snowmanxxx)) {
                  world.setBlockState(_snowmanxxx.up(), AbstractFireBlock.getState(world, _snowmanxxx));
               }
            }
         }
      }
   }

   private boolean canLightFire(WorldView world, BlockPos pos) {
      for (Direction _snowman : Direction.values()) {
         if (this.hasBurnableBlock(world, pos.offset(_snowman))) {
            return true;
         }
      }

      return false;
   }

   private boolean hasBurnableBlock(WorldView world, BlockPos pos) {
      return pos.getY() >= 0 && pos.getY() < 256 && !world.isChunkLoaded(pos) ? false : world.getBlockState(pos).getMaterial().isBurnable();
   }

   @Nullable
   @Override
   public ParticleEffect getParticle() {
      return ParticleTypes.DRIPPING_LAVA;
   }

   @Override
   protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
      this.playExtinguishEvent(world, pos);
   }

   @Override
   public int getFlowSpeed(WorldView world) {
      return world.getDimension().isUltrawarm() ? 4 : 2;
   }

   @Override
   public BlockState toBlockState(FluidState state) {
      return Blocks.LAVA.getDefaultState().with(FluidBlock.LEVEL, Integer.valueOf(method_15741(state)));
   }

   @Override
   public boolean matchesType(Fluid fluid) {
      return fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA;
   }

   @Override
   public int getLevelDecreasePerBlock(WorldView world) {
      return world.getDimension().isUltrawarm() ? 1 : 2;
   }

   @Override
   public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
      return state.getHeight(world, pos) >= 0.44444445F && fluid.isIn(FluidTags.WATER);
   }

   @Override
   public int getTickRate(WorldView world) {
      return world.getDimension().isUltrawarm() ? 10 : 30;
   }

   @Override
   public int getNextTickDelay(World world, BlockPos pos, FluidState oldState, FluidState newState) {
      int _snowman = this.getTickRate(world);
      if (!oldState.isEmpty()
         && !newState.isEmpty()
         && !oldState.get(FALLING)
         && !newState.get(FALLING)
         && newState.getHeight(world, pos) > oldState.getHeight(world, pos)
         && world.getRandom().nextInt(4) != 0) {
         _snowman *= 4;
      }

      return _snowman;
   }

   private void playExtinguishEvent(WorldAccess world, BlockPos pos) {
      world.syncWorldEvent(1501, pos, 0);
   }

   @Override
   protected boolean isInfinite() {
      return false;
   }

   @Override
   protected void flow(WorldAccess world, BlockPos pos, BlockState state, Direction direction, FluidState fluidState) {
      if (direction == Direction.DOWN) {
         FluidState _snowman = world.getFluidState(pos);
         if (this.isIn(FluidTags.LAVA) && _snowman.isIn(FluidTags.WATER)) {
            if (state.getBlock() instanceof FluidBlock) {
               world.setBlockState(pos, Blocks.STONE.getDefaultState(), 3);
            }

            this.playExtinguishEvent(world, pos);
            return;
         }
      }

      super.flow(world, pos, state, direction, fluidState);
   }

   @Override
   protected boolean hasRandomTicks() {
      return true;
   }

   @Override
   protected float getBlastResistance() {
      return 100.0F;
   }

   public static class Flowing extends LavaFluid {
      public Flowing() {
      }

      @Override
      protected void appendProperties(StateManager.Builder<Fluid, FluidState> _snowman) {
         super.appendProperties(_snowman);
         _snowman.add(LEVEL);
      }

      @Override
      public int getLevel(FluidState state) {
         return state.get(LEVEL);
      }

      @Override
      public boolean isStill(FluidState state) {
         return false;
      }
   }

   public static class Still extends LavaFluid {
      public Still() {
      }

      @Override
      public int getLevel(FluidState state) {
         return 8;
      }

      @Override
      public boolean isStill(FluidState state) {
         return true;
      }
   }
}
