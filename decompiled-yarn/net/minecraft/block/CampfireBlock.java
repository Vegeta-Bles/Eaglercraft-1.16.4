package net.minecraft.block;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class CampfireBlock extends BlockWithEntity implements Waterloggable {
   protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
   public static final BooleanProperty LIT = Properties.LIT;
   public static final BooleanProperty SIGNAL_FIRE = Properties.SIGNAL_FIRE;
   public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
   public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
   private static final VoxelShape SMOKEY_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   private final boolean emitsParticles;
   private final int fireDamage;

   public CampfireBlock(boolean emitsParticles, int fireDamage, AbstractBlock.Settings settings) {
      super(settings);
      this.emitsParticles = emitsParticles;
      this.fireDamage = fireDamage;
      this.setDefaultState(
         this.stateManager
            .getDefaultState()
            .with(LIT, Boolean.valueOf(true))
            .with(SIGNAL_FIRE, Boolean.valueOf(false))
            .with(WATERLOGGED, Boolean.valueOf(false))
            .with(FACING, Direction.NORTH)
      );
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof CampfireBlockEntity) {
         CampfireBlockEntity _snowmanx = (CampfireBlockEntity)_snowman;
         ItemStack _snowmanxx = player.getStackInHand(hand);
         Optional<CampfireCookingRecipe> _snowmanxxx = _snowmanx.getRecipeFor(_snowmanxx);
         if (_snowmanxxx.isPresent()) {
            if (!world.isClient && _snowmanx.addItem(player.abilities.creativeMode ? _snowmanxx.copy() : _snowmanxx, _snowmanxxx.get().getCookTime())) {
               player.incrementStat(Stats.INTERACT_WITH_CAMPFIRE);
               return ActionResult.SUCCESS;
            }

            return ActionResult.CONSUME;
         }
      }

      return ActionResult.PASS;
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      if (!entity.isFireImmune() && state.get(LIT) && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
         entity.damage(DamageSource.IN_FIRE, (float)this.fireDamage);
      }

      super.onEntityCollision(state, world, pos, entity);
   }

   @Override
   public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
      if (!state.isOf(newState.getBlock())) {
         BlockEntity _snowman = world.getBlockEntity(pos);
         if (_snowman instanceof CampfireBlockEntity) {
            ItemScatterer.spawn(world, pos, ((CampfireBlockEntity)_snowman).getItemsBeingCooked());
         }

         super.onStateReplaced(state, world, pos, newState, moved);
      }
   }

   @Nullable
   @Override
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      WorldAccess _snowman = ctx.getWorld();
      BlockPos _snowmanx = ctx.getBlockPos();
      boolean _snowmanxx = _snowman.getFluidState(_snowmanx).getFluid() == Fluids.WATER;
      return this.getDefaultState()
         .with(WATERLOGGED, Boolean.valueOf(_snowmanxx))
         .with(SIGNAL_FIRE, Boolean.valueOf(this.doesBlockCauseSignalFire(_snowman.getBlockState(_snowmanx.down()))))
         .with(LIT, Boolean.valueOf(!_snowmanxx))
         .with(FACING, ctx.getPlayerFacing());
   }

   @Override
   public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom) {
      if (state.get(WATERLOGGED)) {
         world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
      }

      return direction == Direction.DOWN
         ? state.with(SIGNAL_FIRE, Boolean.valueOf(this.doesBlockCauseSignalFire(newState)))
         : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
   }

   private boolean doesBlockCauseSignalFire(BlockState state) {
      return state.isOf(Blocks.HAY_BLOCK);
   }

   @Override
   public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return SHAPE;
   }

   @Override
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(LIT)) {
         if (random.nextInt(10) == 0) {
            world.playSound(
               (double)pos.getX() + 0.5,
               (double)pos.getY() + 0.5,
               (double)pos.getZ() + 0.5,
               SoundEvents.BLOCK_CAMPFIRE_CRACKLE,
               SoundCategory.BLOCKS,
               0.5F + random.nextFloat(),
               random.nextFloat() * 0.7F + 0.6F,
               false
            );
         }

         if (this.emitsParticles && random.nextInt(5) == 0) {
            for (int _snowman = 0; _snowman < random.nextInt(1) + 1; _snowman++) {
               world.addParticle(
                  ParticleTypes.LAVA,
                  (double)pos.getX() + 0.5,
                  (double)pos.getY() + 0.5,
                  (double)pos.getZ() + 0.5,
                  (double)(random.nextFloat() / 2.0F),
                  5.0E-5,
                  (double)(random.nextFloat() / 2.0F)
               );
            }
         }
      }
   }

   public static void extinguish(WorldAccess world, BlockPos pos, BlockState state) {
      if (world.isClient()) {
         for (int _snowman = 0; _snowman < 20; _snowman++) {
            spawnSmokeParticle((World)world, pos, state.get(SIGNAL_FIRE), true);
         }
      }

      BlockEntity _snowman = world.getBlockEntity(pos);
      if (_snowman instanceof CampfireBlockEntity) {
         ((CampfireBlockEntity)_snowman).spawnItemsBeingCooked();
      }
   }

   @Override
   public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
      if (!state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
         boolean _snowman = state.get(LIT);
         if (_snowman) {
            if (!world.isClient()) {
               world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }

            extinguish(world, pos, state);
         }

         world.setBlockState(pos, state.with(WATERLOGGED, Boolean.valueOf(true)).with(LIT, Boolean.valueOf(false)), 3);
         world.getFluidTickScheduler().schedule(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
      if (!world.isClient && projectile.isOnFire()) {
         Entity _snowman = projectile.getOwner();
         boolean _snowmanx = _snowman == null || _snowman instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
         if (_snowmanx && !state.get(LIT) && !state.get(WATERLOGGED)) {
            BlockPos _snowmanxx = hit.getBlockPos();
            world.setBlockState(_snowmanxx, state.with(Properties.LIT, Boolean.valueOf(true)), 11);
         }
      }
   }

   public static void spawnSmokeParticle(World world, BlockPos pos, boolean isSignal, boolean lotsOfSmoke) {
      Random _snowman = world.getRandom();
      DefaultParticleType _snowmanx = isSignal ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
      world.addImportantParticle(
         _snowmanx,
         true,
         (double)pos.getX() + 0.5 + _snowman.nextDouble() / 3.0 * (double)(_snowman.nextBoolean() ? 1 : -1),
         (double)pos.getY() + _snowman.nextDouble() + _snowman.nextDouble(),
         (double)pos.getZ() + 0.5 + _snowman.nextDouble() / 3.0 * (double)(_snowman.nextBoolean() ? 1 : -1),
         0.0,
         0.07,
         0.0
      );
      if (lotsOfSmoke) {
         world.addParticle(
            ParticleTypes.SMOKE,
            (double)pos.getX() + 0.25 + _snowman.nextDouble() / 2.0 * (double)(_snowman.nextBoolean() ? 1 : -1),
            (double)pos.getY() + 0.4,
            (double)pos.getZ() + 0.25 + _snowman.nextDouble() / 2.0 * (double)(_snowman.nextBoolean() ? 1 : -1),
            0.0,
            0.005,
            0.0
         );
      }
   }

   public static boolean isLitCampfireInRange(World world, BlockPos pos) {
      for (int _snowman = 1; _snowman <= 5; _snowman++) {
         BlockPos _snowmanx = pos.down(_snowman);
         BlockState _snowmanxx = world.getBlockState(_snowmanx);
         if (isLitCampfire(_snowmanxx)) {
            return true;
         }

         boolean _snowmanxxx = VoxelShapes.matchesAnywhere(SMOKEY_SHAPE, _snowmanxx.getCollisionShape(world, pos, ShapeContext.absent()), BooleanBiFunction.AND);
         if (_snowmanxxx) {
            BlockState _snowmanxxxx = world.getBlockState(_snowmanx.down());
            return isLitCampfire(_snowmanxxxx);
         }
      }

      return false;
   }

   public static boolean isLitCampfire(BlockState state) {
      return state.contains(LIT) && state.isIn(BlockTags.CAMPFIRES) && state.get(LIT);
   }

   @Override
   public FluidState getFluidState(BlockState state) {
      return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
   }

   @Override
   public BlockState rotate(BlockState state, BlockRotation rotation) {
      return state.with(FACING, rotation.rotate(state.get(FACING)));
   }

   @Override
   public BlockState mirror(BlockState state, BlockMirror mirror) {
      return state.rotate(mirror.getRotation(state.get(FACING)));
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(LIT, SIGNAL_FIRE, WATERLOGGED, FACING);
   }

   @Override
   public BlockEntity createBlockEntity(BlockView world) {
      return new CampfireBlockEntity();
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }

   public static boolean method_30035(BlockState _snowman) {
      return _snowman.method_27851(BlockTags.CAMPFIRES, _snowmanx -> _snowmanx.contains(Properties.WATERLOGGED) && _snowmanx.contains(Properties.LIT))
         && !_snowman.get(Properties.WATERLOGGED)
         && !_snowman.get(Properties.LIT);
   }
}
