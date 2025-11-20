package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.village.VillagerProfession;

public class GatherItemsVillagerTask extends Task<VillagerEntity> {
   private Set<Item> items = ImmutableSet.of();

   public GatherItemsVillagerTask() {
      super(
         ImmutableMap.of(MemoryModuleType.INTERACTION_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT)
      );
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      return LookTargetUtil.canSee(_snowman.getBrain(), MemoryModuleType.INTERACTION_TARGET, EntityType.VILLAGER);
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return this.shouldRun(_snowman, _snowman);
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      VillagerEntity _snowmanxxx = (VillagerEntity)_snowman.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).get();
      LookTargetUtil.lookAtAndWalkTowardsEachOther(_snowman, _snowmanxxx, 0.5F);
      this.items = getGatherableItems(_snowman, _snowmanxxx);
   }

   protected void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      VillagerEntity _snowmanxxx = (VillagerEntity)_snowman.getBrain().getOptionalMemory(MemoryModuleType.INTERACTION_TARGET).get();
      if (!(_snowman.squaredDistanceTo(_snowmanxxx) > 5.0)) {
         LookTargetUtil.lookAtAndWalkTowardsEachOther(_snowman, _snowmanxxx, 0.5F);
         _snowman.talkWithVillager(_snowman, _snowmanxxx, _snowman);
         if (_snowman.wantsToStartBreeding() && (_snowman.getVillagerData().getProfession() == VillagerProfession.FARMER || _snowmanxxx.canBreed())) {
            giveHalfOfStack(_snowman, VillagerEntity.ITEM_FOOD_VALUES.keySet(), _snowmanxxx);
         }

         if (_snowmanxxx.getVillagerData().getProfession() == VillagerProfession.FARMER && _snowman.getInventory().count(Items.WHEAT) > Items.WHEAT.getMaxCount() / 2) {
            giveHalfOfStack(_snowman, ImmutableSet.of(Items.WHEAT), _snowmanxxx);
         }

         if (!this.items.isEmpty() && _snowman.getInventory().containsAny(this.items)) {
            giveHalfOfStack(_snowman, this.items, _snowmanxxx);
         }
      }
   }

   protected void finishRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      _snowman.getBrain().forget(MemoryModuleType.INTERACTION_TARGET);
   }

   private static Set<Item> getGatherableItems(VillagerEntity _snowman, VillagerEntity _snowman) {
      ImmutableSet<Item> _snowmanxx = _snowman.getVillagerData().getProfession().getGatherableItems();
      ImmutableSet<Item> _snowmanxxx = _snowman.getVillagerData().getProfession().getGatherableItems();
      return _snowmanxx.stream().filter(_snowmanxxxx -> !_snowman.contains(_snowmanxxxx)).collect(Collectors.toSet());
   }

   private static void giveHalfOfStack(VillagerEntity villager, Set<Item> validItems, LivingEntity target) {
      SimpleInventory _snowman = villager.getInventory();
      ItemStack _snowmanx = ItemStack.EMPTY;
      int _snowmanxx = 0;

      while (_snowmanxx < _snowman.size()) {
         ItemStack _snowmanxxx;
         Item _snowmanxxxx;
         int _snowmanxxxxx;
         label28: {
            _snowmanxxx = _snowman.getStack(_snowmanxx);
            if (!_snowmanxxx.isEmpty()) {
               _snowmanxxxx = _snowmanxxx.getItem();
               if (validItems.contains(_snowmanxxxx)) {
                  if (_snowmanxxx.getCount() > _snowmanxxx.getMaxCount() / 2) {
                     _snowmanxxxxx = _snowmanxxx.getCount() / 2;
                     break label28;
                  }

                  if (_snowmanxxx.getCount() > 24) {
                     _snowmanxxxxx = _snowmanxxx.getCount() - 24;
                     break label28;
                  }
               }
            }

            _snowmanxx++;
            continue;
         }

         _snowmanxxx.decrement(_snowmanxxxxx);
         _snowmanx = new ItemStack(_snowmanxxxx, _snowmanxxxxx);
         break;
      }

      if (!_snowmanx.isEmpty()) {
         LookTargetUtil.give(villager, _snowmanx, target.getPos());
      }
   }
}
