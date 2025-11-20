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
   public GlassBottleItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      List<AreaEffectCloudEntity> list = world.getEntitiesByClass(
         AreaEffectCloudEntity.class,
         user.getBoundingBox().expand(2.0),
         entity -> entity != null && entity.isAlive() && entity.getOwner() instanceof EnderDragonEntity
      );
      ItemStack lv = user.getStackInHand(hand);
      if (!list.isEmpty()) {
         AreaEffectCloudEntity lv2 = list.get(0);
         lv2.setRadius(lv2.getRadius() - 0.5F);
         world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
         return TypedActionResult.success(this.fill(lv, user, new ItemStack(Items.DRAGON_BREATH)), world.isClient());
      } else {
         HitResult lv3 = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
         if (lv3.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(lv);
         } else {
            if (lv3.getType() == HitResult.Type.BLOCK) {
               BlockPos lv4 = ((BlockHitResult)lv3).getBlockPos();
               if (!world.canPlayerModifyAt(user, lv4)) {
                  return TypedActionResult.pass(lv);
               }

               if (world.getFluidState(lv4).isIn(FluidTags.WATER)) {
                  world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                  return TypedActionResult.success(this.fill(lv, user, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER)), world.isClient());
               }
            }

            return TypedActionResult.pass(lv);
         }
      }
   }

   protected ItemStack fill(ItemStack arg, PlayerEntity arg2, ItemStack arg3) {
      arg2.incrementStat(Stats.USED.getOrCreateStat(this));
      return ItemUsage.method_30012(arg, arg2, arg3);
   }
}
