package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.FluidFillable;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class BucketItem extends Item {
   private final Fluid fluid;

   public BucketItem(Fluid fluid, Item.Settings settings) {
      super(settings);
      this.fluid = fluid;
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      HitResult _snowmanx = raycast(world, user, this.fluid == Fluids.EMPTY ? RaycastContext.FluidHandling.SOURCE_ONLY : RaycastContext.FluidHandling.NONE);
      if (_snowmanx.getType() == HitResult.Type.MISS) {
         return TypedActionResult.pass(_snowman);
      } else if (_snowmanx.getType() != HitResult.Type.BLOCK) {
         return TypedActionResult.pass(_snowman);
      } else {
         BlockHitResult _snowmanxx = (BlockHitResult)_snowmanx;
         BlockPos _snowmanxxx = _snowmanxx.getBlockPos();
         Direction _snowmanxxxx = _snowmanxx.getSide();
         BlockPos _snowmanxxxxx = _snowmanxxx.offset(_snowmanxxxx);
         if (!world.canPlayerModifyAt(user, _snowmanxxx) || !user.canPlaceOn(_snowmanxxxxx, _snowmanxxxx, _snowman)) {
            return TypedActionResult.fail(_snowman);
         } else if (this.fluid == Fluids.EMPTY) {
            BlockState _snowmanxxxxxx = world.getBlockState(_snowmanxxx);
            if (_snowmanxxxxxx.getBlock() instanceof FluidDrainable) {
               Fluid _snowmanxxxxxxx = ((FluidDrainable)_snowmanxxxxxx.getBlock()).tryDrainFluid(world, _snowmanxxx, _snowmanxxxxxx);
               if (_snowmanxxxxxxx != Fluids.EMPTY) {
                  user.incrementStat(Stats.USED.getOrCreateStat(this));
                  user.playSound(_snowmanxxxxxxx.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                  ItemStack _snowmanxxxxxxxx = ItemUsage.method_30012(_snowman, user, new ItemStack(_snowmanxxxxxxx.getBucketItem()));
                  if (!world.isClient) {
                     Criteria.FILLED_BUCKET.trigger((ServerPlayerEntity)user, new ItemStack(_snowmanxxxxxxx.getBucketItem()));
                  }

                  return TypedActionResult.success(_snowmanxxxxxxxx, world.isClient());
               }
            }

            return TypedActionResult.fail(_snowman);
         } else {
            BlockState _snowmanxxxxxx = world.getBlockState(_snowmanxxx);
            BlockPos _snowmanxxxxxxx = _snowmanxxxxxx.getBlock() instanceof FluidFillable && this.fluid == Fluids.WATER ? _snowmanxxx : _snowmanxxxxx;
            if (this.placeFluid(user, world, _snowmanxxxxxxx, _snowmanxx)) {
               this.onEmptied(world, _snowman, _snowmanxxxxxxx);
               if (user instanceof ServerPlayerEntity) {
                  Criteria.PLACED_BLOCK.trigger((ServerPlayerEntity)user, _snowmanxxxxxxx, _snowman);
               }

               user.incrementStat(Stats.USED.getOrCreateStat(this));
               return TypedActionResult.success(this.getEmptiedStack(_snowman, user), world.isClient());
            } else {
               return TypedActionResult.fail(_snowman);
            }
         }
      }
   }

   protected ItemStack getEmptiedStack(ItemStack stack, PlayerEntity player) {
      return !player.abilities.creativeMode ? new ItemStack(Items.BUCKET) : stack;
   }

   public void onEmptied(World world, ItemStack stack, BlockPos pos) {
   }

   public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult _snowman) {
      if (!(this.fluid instanceof FlowableFluid)) {
         return false;
      } else {
         BlockState _snowmanx = world.getBlockState(pos);
         Block _snowmanxx = _snowmanx.getBlock();
         Material _snowmanxxx = _snowmanx.getMaterial();
         boolean _snowmanxxxx = _snowmanx.canBucketPlace(this.fluid);
         boolean _snowmanxxxxx = _snowmanx.isAir() || _snowmanxxxx || _snowmanxx instanceof FluidFillable && ((FluidFillable)_snowmanxx).canFillWithFluid(world, pos, _snowmanx, this.fluid);
         if (!_snowmanxxxxx) {
            return _snowman != null && this.placeFluid(player, world, _snowman.getBlockPos().offset(_snowman.getSide()), null);
         } else if (world.getDimension().isUltrawarm() && this.fluid.isIn(FluidTags.WATER)) {
            int _snowmanxxxxxx = pos.getX();
            int _snowmanxxxxxxx = pos.getY();
            int _snowmanxxxxxxxx = pos.getZ();
            world.playSound(
               player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.random.nextFloat() - world.random.nextFloat()) * 0.8F
            );

            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 8; _snowmanxxxxxxxxx++) {
               world.addParticle(
                  ParticleTypes.LARGE_SMOKE,
                  (double)_snowmanxxxxxx + Math.random(),
                  (double)_snowmanxxxxxxx + Math.random(),
                  (double)_snowmanxxxxxxxx + Math.random(),
                  0.0,
                  0.0,
                  0.0
               );
            }

            return true;
         } else if (_snowmanxx instanceof FluidFillable && this.fluid == Fluids.WATER) {
            ((FluidFillable)_snowmanxx).tryFillWithFluid(world, pos, _snowmanx, ((FlowableFluid)this.fluid).getStill(false));
            this.playEmptyingSound(player, world, pos);
            return true;
         } else {
            if (!world.isClient && _snowmanxxxx && !_snowmanxxx.isLiquid()) {
               world.breakBlock(pos, true);
            }

            if (!world.setBlockState(pos, this.fluid.getDefaultState().getBlockState(), 11) && !_snowmanx.getFluidState().isStill()) {
               return false;
            } else {
               this.playEmptyingSound(player, world, pos);
               return true;
            }
         }
      }
   }

   protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
      SoundEvent _snowman = this.fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
      world.playSound(player, pos, _snowman, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }
}
