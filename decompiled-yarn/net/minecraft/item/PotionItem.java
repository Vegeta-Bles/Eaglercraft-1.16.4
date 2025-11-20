package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class PotionItem extends Item {
   public PotionItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ItemStack getDefaultStack() {
      return PotionUtil.setPotion(super.getDefaultStack(), Potions.WATER);
   }

   @Override
   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      PlayerEntity _snowman = user instanceof PlayerEntity ? (PlayerEntity)user : null;
      if (_snowman instanceof ServerPlayerEntity) {
         Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)_snowman, stack);
      }

      if (!world.isClient) {
         for (StatusEffectInstance _snowmanx : PotionUtil.getPotionEffects(stack)) {
            if (_snowmanx.getEffectType().isInstant()) {
               _snowmanx.getEffectType().applyInstantEffect(_snowman, _snowman, user, _snowmanx.getAmplifier(), 1.0);
            } else {
               user.addStatusEffect(new StatusEffectInstance(_snowmanx));
            }
         }
      }

      if (_snowman != null) {
         _snowman.incrementStat(Stats.USED.getOrCreateStat(this));
         if (!_snowman.abilities.creativeMode) {
            stack.decrement(1);
         }
      }

      if (_snowman == null || !_snowman.abilities.creativeMode) {
         if (stack.isEmpty()) {
            return new ItemStack(Items.GLASS_BOTTLE);
         }

         if (_snowman != null) {
            _snowman.inventory.insertStack(new ItemStack(Items.GLASS_BOTTLE));
         }
      }

      return stack;
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

   @Override
   public String getTranslationKey(ItemStack stack) {
      return PotionUtil.getPotion(stack).finishTranslationKey(this.getTranslationKey() + ".effect.");
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      PotionUtil.buildTooltip(stack, tooltip, 1.0F);
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return super.hasGlint(stack) || !PotionUtil.getPotionEffects(stack).isEmpty();
   }

   @Override
   public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
      if (this.isIn(group)) {
         for (Potion _snowman : Registry.POTION) {
            if (_snowman != Potions.EMPTY) {
               stacks.add(PotionUtil.setPotion(new ItemStack(this), _snowman));
            }
         }
      }
   }
}
