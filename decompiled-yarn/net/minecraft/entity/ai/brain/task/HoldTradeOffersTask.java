package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.TradeOffer;

public class HoldTradeOffersTask extends Task<VillagerEntity> {
   @Nullable
   private ItemStack customerHeldStack;
   private final List<ItemStack> offers = Lists.newArrayList();
   private int offerShownTicks;
   private int offerIndex;
   private int ticksLeft;

   public HoldTradeOffersTask(int minRunTime, int maxRunTime) {
      super(ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryModuleState.VALUE_PRESENT), minRunTime, maxRunTime);
   }

   public boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      Brain<?> _snowmanxx = _snowman.getBrain();
      if (!_snowmanxx.getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).isPresent()) {
         return false;
      } else {
         LivingEntity _snowmanxxx = _snowmanxx.getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).get();
         return _snowmanxxx.getType() == EntityType.PLAYER && _snowman.isAlive() && _snowmanxxx.isAlive() && !_snowman.isBaby() && _snowman.squaredDistanceTo(_snowmanxxx) <= 17.0;
      }
   }

   public boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return this.shouldRun(_snowman, _snowman) && this.ticksLeft > 0 && _snowman.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).isPresent();
   }

   public void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      super.run(_snowman, _snowman, _snowman);
      this.findPotentialCustomer(_snowman);
      this.offerShownTicks = 0;
      this.offerIndex = 0;
      this.ticksLeft = 40;
   }

   public void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      LivingEntity _snowmanxxx = this.findPotentialCustomer(_snowman);
      this.setupOffers(_snowmanxxx, _snowman);
      if (!this.offers.isEmpty()) {
         this.refreshShownOffer(_snowman);
      } else {
         _snowman.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
         this.ticksLeft = Math.min(this.ticksLeft, 40);
      }

      this.ticksLeft--;
   }

   public void finishRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      super.finishRunning(_snowman, _snowman, _snowman);
      _snowman.getBrain().forget(MemoryModuleType.INTERACTION_TARGET);
      _snowman.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
      this.customerHeldStack = null;
   }

   private void setupOffers(LivingEntity customer, VillagerEntity villager) {
      boolean _snowman = false;
      ItemStack _snowmanx = customer.getMainHandStack();
      if (this.customerHeldStack == null || !ItemStack.areItemsEqualIgnoreDamage(this.customerHeldStack, _snowmanx)) {
         this.customerHeldStack = _snowmanx;
         _snowman = true;
         this.offers.clear();
      }

      if (_snowman && !this.customerHeldStack.isEmpty()) {
         this.loadPossibleOffers(villager);
         if (!this.offers.isEmpty()) {
            this.ticksLeft = 900;
            this.holdOffer(villager);
         }
      }
   }

   private void holdOffer(VillagerEntity villager) {
      villager.equipStack(EquipmentSlot.MAINHAND, this.offers.get(0));
   }

   private void loadPossibleOffers(VillagerEntity villager) {
      for (TradeOffer _snowman : villager.getOffers()) {
         if (!_snowman.isDisabled() && this.isPossible(_snowman)) {
            this.offers.add(_snowman.getMutableSellItem());
         }
      }
   }

   private boolean isPossible(TradeOffer offer) {
      return ItemStack.areItemsEqualIgnoreDamage(this.customerHeldStack, offer.getAdjustedFirstBuyItem())
         || ItemStack.areItemsEqualIgnoreDamage(this.customerHeldStack, offer.getSecondBuyItem());
   }

   private LivingEntity findPotentialCustomer(VillagerEntity villager) {
      Brain<?> _snowman = villager.getBrain();
      LivingEntity _snowmanx = _snowman.getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).get();
      _snowman.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(_snowmanx, true));
      return _snowmanx;
   }

   private void refreshShownOffer(VillagerEntity villager) {
      if (this.offers.size() >= 2 && ++this.offerShownTicks >= 40) {
         this.offerIndex++;
         this.offerShownTicks = 0;
         if (this.offerIndex > this.offers.size() - 1) {
            this.offerIndex = 0;
         }

         villager.equipStack(EquipmentSlot.MAINHAND, this.offers.get(this.offerIndex));
      }
   }
}
