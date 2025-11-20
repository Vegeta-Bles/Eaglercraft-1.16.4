package net.minecraft.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Optional;
import java.util.Random;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.BlockView;
import net.minecraft.world.CollisionView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

public class RespawnAnchorBlock extends Block {
   public static final IntProperty CHARGES = Properties.CHARGES;
   private static final ImmutableList<Vec3i> field_26442 = ImmutableList.of(
      new Vec3i(0, 0, -1),
      new Vec3i(-1, 0, 0),
      new Vec3i(0, 0, 1),
      new Vec3i(1, 0, 0),
      new Vec3i(-1, 0, -1),
      new Vec3i(1, 0, -1),
      new Vec3i(-1, 0, 1),
      new Vec3i(1, 0, 1)
   );
   private static final ImmutableList<Vec3i> field_26443 = new Builder()
      .addAll(field_26442)
      .addAll(field_26442.stream().map(Vec3i::down).iterator())
      .addAll(field_26442.stream().map(Vec3i::up).iterator())
      .add(new Vec3i(0, 1, 0))
      .build();

   public RespawnAnchorBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.stateManager.getDefaultState().with(CHARGES, Integer.valueOf(0)));
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (hand == Hand.MAIN_HAND && !isChargeItem(_snowman) && isChargeItem(player.getStackInHand(Hand.OFF_HAND))) {
         return ActionResult.PASS;
      } else if (isChargeItem(_snowman) && canCharge(state)) {
         charge(world, pos, state);
         if (!player.abilities.creativeMode) {
            _snowman.decrement(1);
         }

         return ActionResult.success(world.isClient);
      } else if (state.get(CHARGES) == 0) {
         return ActionResult.PASS;
      } else if (!isNether(world)) {
         if (!world.isClient) {
            this.explode(state, world, pos);
         }

         return ActionResult.success(world.isClient);
      } else {
         if (!world.isClient) {
            ServerPlayerEntity _snowmanx = (ServerPlayerEntity)player;
            if (_snowmanx.getSpawnPointDimension() != world.getRegistryKey() || !_snowmanx.getSpawnPointPosition().equals(pos)) {
               _snowmanx.setSpawnPoint(world.getRegistryKey(), pos, 0.0F, false, true);
               world.playSound(
                  null,
                  (double)pos.getX() + 0.5,
                  (double)pos.getY() + 0.5,
                  (double)pos.getZ() + 0.5,
                  SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN,
                  SoundCategory.BLOCKS,
                  1.0F,
                  1.0F
               );
               return ActionResult.SUCCESS;
            }
         }

         return ActionResult.CONSUME;
      }
   }

   private static boolean isChargeItem(ItemStack stack) {
      return stack.getItem() == Items.GLOWSTONE;
   }

   private static boolean canCharge(BlockState state) {
      return state.get(CHARGES) < 4;
   }

   private static boolean hasStillWater(BlockPos pos, World world) {
      FluidState _snowman = world.getFluidState(pos);
      if (!_snowman.isIn(FluidTags.WATER)) {
         return false;
      } else if (_snowman.isStill()) {
         return true;
      } else {
         float _snowmanx = (float)_snowman.getLevel();
         if (_snowmanx < 2.0F) {
            return false;
         } else {
            FluidState _snowmanxx = world.getFluidState(pos.down());
            return !_snowmanxx.isIn(FluidTags.WATER);
         }
      }
   }

   private void explode(BlockState state, World world, BlockPos explodedPos) {
      world.removeBlock(explodedPos, false);
      boolean _snowman = Direction.Type.HORIZONTAL.stream().map(explodedPos::offset).anyMatch(_snowmanx -> hasStillWater(_snowmanx, world));
      final boolean _snowmanx = _snowman || world.getFluidState(explodedPos.up()).isIn(FluidTags.WATER);
      ExplosionBehavior _snowmanxx = new ExplosionBehavior() {
         @Override
         public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
            return pos.equals(explodedPos) && _snowman
               ? Optional.of(Blocks.WATER.getBlastResistance())
               : super.getBlastResistance(explosion, world, pos, blockState, fluidState);
         }
      };
      world.createExplosion(
         null,
         DamageSource.badRespawnPoint(),
         _snowmanxx,
         (double)explodedPos.getX() + 0.5,
         (double)explodedPos.getY() + 0.5,
         (double)explodedPos.getZ() + 0.5,
         5.0F,
         true,
         Explosion.DestructionType.DESTROY
      );
   }

   public static boolean isNether(World _snowman) {
      return _snowman.getDimension().isRespawnAnchorWorking();
   }

   public static void charge(World world, BlockPos pos, BlockState state) {
      world.setBlockState(pos, state.with(CHARGES, Integer.valueOf(state.get(CHARGES) + 1)), 3);
      world.playSound(
         null,
         (double)pos.getX() + 0.5,
         (double)pos.getY() + 0.5,
         (double)pos.getZ() + 0.5,
         SoundEvents.BLOCK_RESPAWN_ANCHOR_CHARGE,
         SoundCategory.BLOCKS,
         1.0F,
         1.0F
      );
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      if (state.get(CHARGES) != 0) {
         if (random.nextInt(100) == 0) {
            world.playSound(
               null,
               (double)pos.getX() + 0.5,
               (double)pos.getY() + 0.5,
               (double)pos.getZ() + 0.5,
               SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT,
               SoundCategory.BLOCKS,
               1.0F,
               1.0F
            );
         }

         double _snowman = (double)pos.getX() + 0.5 + (0.5 - random.nextDouble());
         double _snowmanx = (double)pos.getY() + 1.0;
         double _snowmanxx = (double)pos.getZ() + 0.5 + (0.5 - random.nextDouble());
         double _snowmanxxx = (double)random.nextFloat() * 0.04;
         world.addParticle(ParticleTypes.REVERSE_PORTAL, _snowman, _snowmanx, _snowmanxx, 0.0, _snowmanxxx, 0.0);
      }
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(CHARGES);
   }

   @Override
   public boolean hasComparatorOutput(BlockState state) {
      return true;
   }

   public static int getLightLevel(BlockState state, int maxLevel) {
      return MathHelper.floor((float)(state.get(CHARGES) - 0) / 4.0F * (float)maxLevel);
   }

   @Override
   public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
      return getLightLevel(state, 15);
   }

   public static Optional<Vec3d> findRespawnPosition(EntityType<?> entity, CollisionView _snowman, BlockPos pos) {
      Optional<Vec3d> _snowmanx = method_30842(entity, _snowman, pos, true);
      return _snowmanx.isPresent() ? _snowmanx : method_30842(entity, _snowman, pos, false);
   }

   private static Optional<Vec3d> method_30842(EntityType<?> _snowman, CollisionView _snowman, BlockPos _snowman, boolean _snowman) {
      BlockPos.Mutable _snowmanxxxx = new BlockPos.Mutable();
      UnmodifiableIterator var5 = field_26443.iterator();

      while (var5.hasNext()) {
         Vec3i _snowmanxxxxx = (Vec3i)var5.next();
         _snowmanxxxx.set(_snowman).move(_snowmanxxxxx);
         Vec3d _snowmanxxxxxx = Dismounting.method_30769(_snowman, _snowman, _snowmanxxxx, _snowman);
         if (_snowmanxxxxxx != null) {
            return Optional.of(_snowmanxxxxxx);
         }
      }

      return Optional.empty();
   }

   @Override
   public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
      return false;
   }
}
