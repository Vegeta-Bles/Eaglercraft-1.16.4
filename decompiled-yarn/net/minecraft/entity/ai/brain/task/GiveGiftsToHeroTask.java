package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;

public class GiveGiftsToHeroTask extends Task<VillagerEntity> {
   private static final Map<VillagerProfession, Identifier> GIFTS = Util.make(Maps.newHashMap(), _snowman -> {
      _snowman.put(VillagerProfession.ARMORER, LootTables.HERO_OF_THE_VILLAGE_ARMORER_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.BUTCHER, LootTables.HERO_OF_THE_VILLAGE_BUTCHER_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.CARTOGRAPHER, LootTables.HERO_OF_THE_VILLAGE_CARTOGRAPHER_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.CLERIC, LootTables.HERO_OF_THE_VILLAGE_CLERIC_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.FARMER, LootTables.HERO_OF_THE_VILLAGE_FARMER_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.FISHERMAN, LootTables.HERO_OF_THE_VILLAGE_FISHERMAN_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.FLETCHER, LootTables.HERO_OF_THE_VILLAGE_FLETCHER_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.LEATHERWORKER, LootTables.HERO_OF_THE_VILLAGE_LEATHERWORKER_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.LIBRARIAN, LootTables.HERO_OF_THE_VILLAGE_LIBRARIAN_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.MASON, LootTables.HERO_OF_THE_VILLAGE_MASON_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.SHEPHERD, LootTables.HERO_OF_THE_VILLAGE_SHEPHERD_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.TOOLSMITH, LootTables.HERO_OF_THE_VILLAGE_TOOLSMITH_GIFT_GAMEPLAY);
      _snowman.put(VillagerProfession.WEAPONSMITH, LootTables.HERO_OF_THE_VILLAGE_WEAPONSMITH_GIFT_GAMEPLAY);
   });
   private int ticksLeft = 600;
   private boolean done;
   private long startTime;

   public GiveGiftsToHeroTask(int delay) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleState.VALUE_PRESENT
         ),
         delay
      );
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      if (!this.isNearestPlayerHero(_snowman)) {
         return false;
      } else if (this.ticksLeft > 0) {
         this.ticksLeft--;
         return false;
      } else {
         return true;
      }
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      this.done = false;
      this.startTime = _snowman;
      PlayerEntity _snowmanxxx = this.getNearestPlayerIfHero(_snowman).get();
      _snowman.getBrain().remember(MemoryModuleType.INTERACTION_TARGET, _snowmanxxx);
      LookTargetUtil.lookAt(_snowman, _snowmanxxx);
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      return this.isNearestPlayerHero(_snowman) && !this.done;
   }

   protected void keepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      PlayerEntity _snowmanxxx = this.getNearestPlayerIfHero(_snowman).get();
      LookTargetUtil.lookAt(_snowman, _snowmanxxx);
      if (this.isCloseEnough(_snowman, _snowmanxxx)) {
         if (_snowman - this.startTime > 20L) {
            this.giveGifts(_snowman, _snowmanxxx);
            this.done = true;
         }
      } else {
         LookTargetUtil.walkTowards(_snowman, _snowmanxxx, 0.5F, 5);
      }
   }

   protected void finishRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      this.ticksLeft = getNextGiftDelay(_snowman);
      _snowman.getBrain().forget(MemoryModuleType.INTERACTION_TARGET);
      _snowman.getBrain().forget(MemoryModuleType.WALK_TARGET);
      _snowman.getBrain().forget(MemoryModuleType.LOOK_TARGET);
   }

   private void giveGifts(VillagerEntity villager, LivingEntity recipient) {
      for (ItemStack _snowman : this.getGifts(villager)) {
         LookTargetUtil.give(villager, _snowman, recipient.getPos());
      }
   }

   private List<ItemStack> getGifts(VillagerEntity villager) {
      if (villager.isBaby()) {
         return ImmutableList.of(new ItemStack(Items.POPPY));
      } else {
         VillagerProfession _snowman = villager.getVillagerData().getProfession();
         if (GIFTS.containsKey(_snowman)) {
            LootTable _snowmanx = villager.world.getServer().getLootManager().getTable(GIFTS.get(_snowman));
            LootContext.Builder _snowmanxx = new LootContext.Builder((ServerWorld)villager.world)
               .parameter(LootContextParameters.ORIGIN, villager.getPos())
               .parameter(LootContextParameters.THIS_ENTITY, villager)
               .random(villager.getRandom());
            return _snowmanx.generateLoot(_snowmanxx.build(LootContextTypes.GIFT));
         } else {
            return ImmutableList.of(new ItemStack(Items.WHEAT_SEEDS));
         }
      }
   }

   private boolean isNearestPlayerHero(VillagerEntity villager) {
      return this.getNearestPlayerIfHero(villager).isPresent();
   }

   private Optional<PlayerEntity> getNearestPlayerIfHero(VillagerEntity villager) {
      return villager.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).filter(this::isHero);
   }

   private boolean isHero(PlayerEntity player) {
      return player.hasStatusEffect(StatusEffects.HERO_OF_THE_VILLAGE);
   }

   private boolean isCloseEnough(VillagerEntity villager, PlayerEntity player) {
      BlockPos _snowman = player.getBlockPos();
      BlockPos _snowmanx = villager.getBlockPos();
      return _snowmanx.isWithinDistance(_snowman, 5.0);
   }

   private static int getNextGiftDelay(ServerWorld world) {
      return 600 + world.random.nextInt(6001);
   }
}
