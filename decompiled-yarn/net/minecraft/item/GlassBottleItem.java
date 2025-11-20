package net.minecraft.item;

import java.util.List;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class GlassBottleItem extends Item {
   public GlassBottleItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      List<AreaEffectCloudEntity> _snowman = world.getEntitiesByClass(
         AreaEffectCloudEntity.class,
         user.getBoundingBox().expand(2.0),
         entity -> entity != null && entity.isAlive() && entity.getOwner() instanceof EnderDragonEntity
      );
      ItemStack _snowmanx = user.getStackInHand(hand);
      if (!_snowman.isEmpty()) {
         AreaEffectCloudEntity _snowmanxx = _snowman.get(0);
         _snowmanxx.setRadius(_snowmanxx.getRadius() - 0.5F);
         world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
         return TypedActionResult.success(this.fill(_snowmanx, user, new ItemStack(Items.DRAGON_BREATH)), world.isClient());
      } else {
         HitResult _snowmanxx = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
         if (_snowmanxx.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(_snowmanx);
         } else {
            if (_snowmanxx.getType() == HitResult.Type.BLOCK) {
               BlockPos _snowmanxxx = ((BlockHitResult)_snowmanxx).getBlockPos();
               if (!world.canPlayerModifyAt(user, _snowmanxxx)) {
                  return TypedActionResult.pass(_snowmanx);
               }

               if (world.getFluidState(_snowmanxxx).isIn(FluidTags.WATER)) {
                  world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                  return TypedActionResult.success(this.fill(_snowmanx, user, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER)), world.isClient());
               }
            }

            return TypedActionResult.pass(_snowmanx);
         }
      }
   }

   protected ItemStack fill(ItemStack _snowman, PlayerEntity _snowman, ItemStack _snowman) {
      _snowman.incrementStat(Stats.USED.getOrCreateStat(this));
      return ItemUsage.method_30012(_snowman, _snowman, _snowman);
   }
}
