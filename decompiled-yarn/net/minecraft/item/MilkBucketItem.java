package net.minecraft.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MilkBucketItem extends Item {
   public MilkBucketItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      if (user instanceof ServerPlayerEntity) {
         ServerPlayerEntity _snowman = (ServerPlayerEntity)user;
         Criteria.CONSUME_ITEM.trigger(_snowman, stack);
         _snowman.incrementStat(Stats.USED.getOrCreateStat(this));
      }

      if (user instanceof PlayerEntity && !((PlayerEntity)user).abilities.creativeMode) {
         stack.decrement(1);
      }

      if (!world.isClient) {
         user.clearStatusEffects();
      }

      return stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack;
   }

   @Override
   public int getMaxUseTime(ItemStack stack) {
      return 32;
   }

   @Override
   public UseAction getUseAction(ItemStack stack) {
      return UseAction.DRINK;
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      return ItemUsage.consumeHeldItem(world, user, hand);
   }
}
