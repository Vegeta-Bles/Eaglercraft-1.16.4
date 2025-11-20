package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.dimension.AreaHelper;

public abstract class AbstractFireBlock extends Block {
   private final float damage;
   protected static final VoxelShape BASE_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

   public AbstractFireBlock(AbstractBlock.Settings settings, float damage) {
      super(settings);
      this.damage = damage;
   }

   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return getState(ctx.getWorld(), ctx.getBlockPos());
   }

   public static BlockState getState(BlockView world, BlockPos pos) {
      BlockPos _snowman = pos.down();
      BlockState _snowmanx = world.getBlockState(_snowman);
      return SoulFireBlock.isSoulBase(_snowmanx.getBlock()) ? Blocks.SOUL_FIRE.getDefaultState() : ((FireBlock)Blocks.FIRE).getStateForPosition(world, pos);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return BASE_SHAPE;
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (random.nextInt(24) == 0) {
         world.playSound(
            (double)pos.getX() + 0.5,
            (double)pos.getY() + 0.5,
            (double)pos.getZ() + 0.5,
            SoundEvents.BLOCK_FIRE_AMBIENT,
            SoundCategory.BLOCKS,
            1.0F + random.nextFloat(),
            random.nextFloat() * 0.7F + 0.3F,
            false
         );
      }

      BlockPos _snowman = pos.down();
      BlockState _snowmanx = world.getBlockState(_snowman);
      if (!this.isFlammable(_snowmanx) && !_snowmanx.isSideSolidFullSquare(world, _snowman, Direction.UP)) {
         if (this.isFlammable(world.getBlockState(pos.west()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)pos.getX() + random.nextDouble() * 0.1F;
               double _snowmanxxxx = (double)pos.getY() + random.nextDouble();
               double _snowmanxxxxx = (double)pos.getZ() + random.nextDouble();
               world.addParticle(ParticleTypes.LARGE_SMOKE, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.isFlammable(world.getBlockState(pos.east()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)(pos.getX() + 1) - random.nextDouble() * 0.1F;
               double _snowmanxxxx = (double)pos.getY() + random.nextDouble();
               double _snowmanxxxxx = (double)pos.getZ() + random.nextDouble();
               world.addParticle(ParticleTypes.LARGE_SMOKE, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.isFlammable(world.getBlockState(pos.north()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)pos.getX() + random.nextDouble();
               double _snowmanxxxx = (double)pos.getY() + random.nextDouble();
               double _snowmanxxxxx = (double)pos.getZ() + random.nextDouble() * 0.1F;
               world.addParticle(ParticleTypes.LARGE_SMOKE, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.isFlammable(world.getBlockState(pos.south()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)pos.getX() + random.nextDouble();
               double _snowmanxxxx = (double)pos.getY() + random.nextDouble();
               double _snowmanxxxxx = (double)(pos.getZ() + 1) - random.nextDouble() * 0.1F;
               world.addParticle(ParticleTypes.LARGE_SMOKE, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }

         if (this.isFlammable(world.getBlockState(pos.up()))) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               double _snowmanxxx = (double)pos.getX() + random.nextDouble();
               double _snowmanxxxx = (double)(pos.getY() + 1) - random.nextDouble() * 0.1F;
               double _snowmanxxxxx = (double)pos.getZ() + random.nextDouble();
               world.addParticle(ParticleTypes.LARGE_SMOKE, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
         }
      } else {
         for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
            double _snowmanxxx = (double)pos.getX() + random.nextDouble();
            double _snowmanxxxx = (double)pos.getY() + random.nextDouble() * 0.5 + 0.5;
            double _snowmanxxxxx = (double)pos.getZ() + random.nextDouble();
            world.addParticle(ParticleTypes.LARGE_SMOKE, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
         }
      }
   }

   protected abstract boolean isFlammable(BlockState state);

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      if (!entity.isFireImmune()) {
         entity.setFireTicks(entity.getFireTicks() + 1);
         if (entity.getFireTicks() == 0) {
            entity.setOnFireFor(8);
         }

         entity.damage(DamageSource.IN_FIRE, this.damage);
      }

      super.onEntityCollision(state, world, pos, entity);
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!oldState.isOf(state.getBlock())) {
         if (method_30366(world)) {
            Optional<AreaHelper> _snowman = AreaHelper.method_30485(world, pos, Direction.Axis.X);
            if (_snowman.isPresent()) {
               _snowman.get().createPortal();
               return;
            }
         }

         if (!state.canPlaceAt(world, pos)) {
            world.removeBlock(pos, false);
         }
      }
   }

   private static boolean method_30366(World _snowman) {
      return _snowman.getRegistryKey() == World.OVERWORLD || _snowman.getRegistryKey() == World.NETHER;
   }

   @Override
   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!world.isClient()) {
         world.syncWorldEvent(null, 1009, pos, 0);
      }
   }

   public static boolean method_30032(World _snowman, BlockPos _snowman, Direction _snowman) {
      BlockState _snowmanxxx = _snowman.getBlockState(_snowman);
      return !_snowmanxxx.isAir() ? false : getState(_snowman, _snowman).canPlaceAt(_snowman, _snowman) || method_30033(_snowman, _snowman, _snowman);
   }

   private static boolean method_30033(World _snowman, BlockPos _snowman, Direction _snowman) {
      if (!method_30366(_snowman)) {
         return false;
      } else {
         BlockPos.Mutable _snowmanxxx = _snowman.mutableCopy();
         boolean _snowmanxxxx = false;

         for (Direction _snowmanxxxxx : Direction.values()) {
            if (_snowman.getBlockState(_snowmanxxx.set(_snowman).move(_snowmanxxxxx)).isOf(Blocks.OBSIDIAN)) {
               _snowmanxxxx = true;
               break;
            }
         }

         return _snowmanxxxx && AreaHelper.method_30485(_snowman, _snowman, _snowman.rotateYCounterclockwise().getAxis()).isPresent();
      }
   }
}
