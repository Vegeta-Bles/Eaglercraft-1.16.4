package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class TntBlock extends Block {
   public static final BooleanProperty UNSTABLE = Properties.UNSTABLE;

   public TntBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
      this.setDefaultState(this.getDefaultState().with(UNSTABLE, Boolean.valueOf(false)));
   }

   @Override
   public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
      if (!oldState.isOf(state.getBlock())) {
         if (world.isReceivingRedstonePower(pos)) {
            primeTnt(world, pos);
            world.removeBlock(pos, false);
         }
      }
   }

   @Override
   public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
      if (world.isReceivingRedstonePower(pos)) {
         primeTnt(world, pos);
         world.removeBlock(pos, false);
      }
   }

   @Override
   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      if (!world.isClient() && !player.isCreative() && state.get(UNSTABLE)) {
         primeTnt(world, pos);
      }

      super.onBreak(world, pos, state, player);
   }

   @Override
   public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
      if (!world.isClient) {
         TntEntity _snowman = new TntEntity(world, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, explosion.getCausingEntity());
         _snowman.setFuse((short)(world.random.nextInt(_snowman.getFuseTimer() / 4) + _snowman.getFuseTimer() / 8));
         world.spawnEntity(_snowman);
      }
   }

   public static void primeTnt(World world, BlockPos pos) {
      primeTnt(world, pos, null);
   }

   private static void primeTnt(World world, BlockPos pos, @Nullable LivingEntity igniter) {
      if (!world.isClient) {
         TntEntity _snowman = new TntEntity(world, (double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, igniter);
         world.spawnEntity(_snowman);
         world.playSound(null, _snowman.getX(), _snowman.getY(), _snowman.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
      }
   }

   @Override
   public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
      ItemStack _snowman = player.getStackInHand(hand);
      Item _snowmanx = _snowman.getItem();
      if (_snowmanx != Items.FLINT_AND_STEEL && _snowmanx != Items.FIRE_CHARGE) {
         return super.onUse(state, world, pos, player, hand, hit);
      } else {
         primeTnt(world, pos, player);
         world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
         if (!player.isCreative()) {
            if (_snowmanx == Items.FLINT_AND_STEEL) {
               _snowman.damage(1, player, _snowmanxx -> _snowmanxx.sendToolBreakStatus(hand));
            } else {
               _snowman.decrement(1);
            }
         }

         return ActionResult.success(world.isClient);
      }
   }

   @Override
   public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
      if (!world.isClient) {
         Entity _snowman = projectile.getOwner();
         if (projectile.isOnFire()) {
            BlockPos _snowmanx = hit.getBlockPos();
            primeTnt(world, _snowmanx, _snowman instanceof LivingEntity ? (LivingEntity)_snowman : null);
            world.removeBlock(_snowmanx, false);
         }
      }
   }

   @Override
   public boolean shouldDropItemsOnExplosion(Explosion explosion) {
      return false;
   }

   @Override
   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
      builder.add(UNSTABLE);
   }
}
