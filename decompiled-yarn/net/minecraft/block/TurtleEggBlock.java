package net.minecraft.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class TurtleEggBlock extends Block {
   private static final VoxelShape SMALL_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 12.0, 7.0, 12.0);
   private static final VoxelShape LARGE_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 7.0, 15.0);
   public static final IntProperty HATCH = Properties.HATCH;
   public static final IntProperty EGGS = Properties.EGGS;

   public TurtleEggBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(HATCH, Integer.valueOf(0)).with(EGGS, Integer.valueOf(1)));
   }

   @Override
   public void onSteppedOn(World world, BlockPos pos, Entity entity) {
      this.tryBreakEgg(world, pos, entity, 100);
      super.onSteppedOn(world, pos, entity);
   }

   @Override
   public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
      if (!(entity instanceof ZombieEntity)) {
         this.tryBreakEgg(world, pos, entity, 3);
      }

      super.onLandedUpon(world, pos, entity, distance);
   }

   private void tryBreakEgg(World world, BlockPos _snowman, Entity entity, int inverseChance) {
      if (this.breaksEgg(world, entity)) {
         if (!world.isClient && world.random.nextInt(inverseChance) == 0) {
            BlockState _snowmanx = world.getBlockState(_snowman);
            if (_snowmanx.isOf(Blocks.TURTLE_EGG)) {
               this.breakEgg(world, _snowman, _snowmanx);
            }
         }
      }
   }

   private void breakEgg(World world, BlockPos pos, BlockState state) {
      world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
      int _snowman = state.get(EGGS);
      if (_snowman <= 1) {
         world.breakBlock(pos, false);
      } else {
         world.setBlockState(pos, state.with(EGGS, Integer.valueOf(_snowman - 1)), 2);
         world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
      }
   }

   @Override
   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
      if (this.shouldHatchProgress(world) && isSand(world, pos)) {
         int _snowman = state.get(HATCH);
         if (_snowman < 2) {
            world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            world.setBlockState(pos, state.with(HATCH, Integer.valueOf(_snowman + 1)), 2);
         } else {
            world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
            world.removeBlock(pos, false);

            for (int _snowmanx = 0; _snowmanx < state.get(EGGS); _snowmanx++) {
               world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));
               TurtleEntity _snowmanxx = EntityType.TURTLE.create(world);
               _snowmanxx.setBreedingAge(-24000);
               _snowmanxx.setHomePos(pos);
               _snowmanxx.refreshPositionAndAngles((double)pos.getX() + 0.3 + (double)_snowmanx * 0.2, (double)pos.getY(), (double)pos.getZ() + 0.3, 0.0F, 0.0F);
               world.spawnEntity(_snowmanxx);
            }
         }
      }
   }

   public static boolean isSand(BlockView _snowman, BlockPos _snowman) {
      return method_29952(_snowman, _snowman.down());
   }

   public static boolean method_29952(BlockView _snowman, BlockPos _snowman) {
      return _snowman.getBlockState(_snowman).isIn(BlockTags.SAND);
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (isSand(world, pos) && !world.isClient) {
         world.syncWorldEvent(2005, pos, 0);
      }
   }

   private boolean shouldHatchProgress(World world) {
      float _snowman = world.getSkyAngle(1.0F);
      return (double)_snowman < 0.69 && (double)_snowman > 0.65 ? true : world.random.nextInt(500) == 0;
   }

   @Override
   public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
      super.afterBreak(world, player, pos, state, blockEntity, stack);
      this.breakEgg(world, pos, state);
   }

   @Override
   public boolean canReplace(BlockState state, ItemPlacementContext context) {
      return context.getStack().getItem() == this.asItem() && state.get(EGGS) < 4 ? true : super.canReplace(state, context);
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      BlockState _snowman = ctx.getWorld().getBlockState(ctx.getBlockPos());
      return _snowman.isOf(this) ? _snowman.with(EGGS, Integer.valueOf(Math.min(4, _snowman.get(EGGS) + 1))) : super.getPlacementState(ctx);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return state.get(EGGS) > 1 ? LARGE_SHAPE : SMALL_SHAPE;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(HATCH, EGGS);
   }

   private boolean breaksEgg(World _snowman, Entity _snowman) {
      if (_snowman instanceof TurtleEntity || _snowman instanceof BatEntity) {
         return false;
      } else {
         return !(_snowman instanceof LivingEntity) ? false : _snowman instanceof PlayerEntity || _snowman.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
      }
   }
}
