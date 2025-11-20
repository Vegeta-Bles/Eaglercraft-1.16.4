package net.minecraft.entity.mob;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.AdmireItemTask;
import net.minecraft.entity.ai.brain.task.AdmireItemTimeLimitTask;
import net.minecraft.entity.ai.brain.task.AttackTask;
import net.minecraft.entity.ai.brain.task.ConditionalTask;
import net.minecraft.entity.ai.brain.task.CrossbowAttackTask;
import net.minecraft.entity.ai.brain.task.DefeatTargetTask;
import net.minecraft.entity.ai.brain.task.FindEntityTask;
import net.minecraft.entity.ai.brain.task.FindInteractionTargetTask;
import net.minecraft.entity.ai.brain.task.FollowMobTask;
import net.minecraft.entity.ai.brain.task.ForgetAngryAtTargetTask;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.ForgetTask;
import net.minecraft.entity.ai.brain.task.GoToCelebrateTask;
import net.minecraft.entity.ai.brain.task.GoToRememberedPositionTask;
import net.minecraft.entity.ai.brain.task.GoTowardsLookTarget;
import net.minecraft.entity.ai.brain.task.HuntFinishTask;
import net.minecraft.entity.ai.brain.task.HuntHoglinTask;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MeleeAttackTask;
import net.minecraft.entity.ai.brain.task.MemoryTransferTask;
import net.minecraft.entity.ai.brain.task.OpenDoorsTask;
import net.minecraft.entity.ai.brain.task.RandomTask;
import net.minecraft.entity.ai.brain.task.RangedApproachTask;
import net.minecraft.entity.ai.brain.task.RemoveOffHandItemTask;
import net.minecraft.entity.ai.brain.task.RidingTask;
import net.minecraft.entity.ai.brain.task.StartRidingTask;
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.ai.brain.task.TimeLimitedTask;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.ai.brain.task.WalkToNearestVisibleWantedItemTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.entity.ai.brain.task.WantNewItemTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IntRange;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;

public class PiglinBrain {
   public static final Item BARTERING_ITEM = Items.GOLD_INGOT;
   private static final IntRange HUNT_MEMORY_DURATION = Durations.betweenSeconds(30, 120);
   private static final IntRange MEMORY_TRANSFER_TASK_DURATION = Durations.betweenSeconds(10, 40);
   private static final IntRange RIDE_TARGET_MEMORY_DURATION = Durations.betweenSeconds(10, 30);
   private static final IntRange AVOID_MEMORY_DURATION = Durations.betweenSeconds(5, 20);
   private static final IntRange field_25384 = Durations.betweenSeconds(5, 7);
   private static final IntRange field_25698 = Durations.betweenSeconds(5, 7);
   private static final Set<Item> FOOD = ImmutableSet.of(Items.PORKCHOP, Items.COOKED_PORKCHOP);

   protected static Brain<?> create(PiglinEntity piglin, Brain<PiglinEntity> brain) {
      addCoreActivities(brain);
      addIdleActivities(brain);
      addAdmireItemActivities(brain);
      addFightActivities(piglin, brain);
      addCelebrateActivities(brain);
      addAvoidActivities(brain);
      addRideActivities(brain);
      brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
      brain.setDefaultActivity(Activity.IDLE);
      brain.resetPossibleActivities();
      return brain;
   }

   protected static void setHuntedRecently(PiglinEntity piglin) {
      int i = HUNT_MEMORY_DURATION.choose(piglin.world.random);
      piglin.getBrain().remember(MemoryModuleType.HUNTED_RECENTLY, true, (long)i);
   }

   private static void addCoreActivities(Brain<PiglinEntity> piglin) {
      piglin.setTaskList(
         Activity.CORE,
         0,
         tasks(
            new LookAroundTask(45, 90),
            new WanderAroundTask(),
            new OpenDoorsTask(),
            method_30090(),
            makeGoToZombifiedPiglinTask(),
            new RemoveOffHandItemTask(),
            new AdmireItemTask(120),
            new DefeatTargetTask(300, PiglinBrain::method_29276),
            new ForgetAngryAtTargetTask()
         )
      );
   }

   private static void addIdleActivities(Brain<PiglinEntity> piglin) {
      piglin.setTaskList(
         Activity.IDLE,
         10,
         tasks(
            new FollowMobTask(PiglinBrain::isGoldHoldingPlayer, 14.0F),
            new UpdateAttackTargetTask<>(AbstractPiglinEntity::isAdult, PiglinBrain::getPreferredTarget),
            new ConditionalTask<>(PiglinEntity::canHunt, new HuntHoglinTask<>()),
            makeGoToSoulFireTask(),
            makeRememberRideableHoglinTask(),
            makeRandomFollowTask(),
            makeRandomWanderTask(),
            new FindInteractionTargetTask(EntityType.PLAYER, 4)
         )
      );
   }

   private static void addFightActivities(PiglinEntity piglin, Brain<PiglinEntity> brain) {
      brain.setTaskList(
         Activity.FIGHT,
         10,
         tasks(
            new ForgetAttackTargetTask<PiglinEntity>(target -> !isPreferredAttackTarget(piglin, target)),
            new ConditionalTask<>(PiglinBrain::isHoldingCrossbow, new AttackTask<>(5, 0.75F)),
            new RangedApproachTask(1.0F),
            new MeleeAttackTask(20),
            new CrossbowAttackTask(),
            new HuntFinishTask(),
            new ForgetTask<>(PiglinBrain::getNearestZombifiedPiglin, MemoryModuleType.ATTACK_TARGET)
         ),
         MemoryModuleType.ATTACK_TARGET
      );
   }

   private static void addCelebrateActivities(Brain<PiglinEntity> brain) {
      brain.setTaskList(
         Activity.CELEBRATE,
         10,
         tasks(
            makeGoToSoulFireTask(),
            new FollowMobTask(PiglinBrain::isGoldHoldingPlayer, 14.0F),
            new UpdateAttackTargetTask<>(AbstractPiglinEntity::isAdult, PiglinBrain::getPreferredTarget),
            new ConditionalTask<>(arg -> !arg.isDancing(), new GoToCelebrateTask<>(2, 1.0F)),
            new ConditionalTask<>(PiglinEntity::isDancing, new GoToCelebrateTask<>(4, 0.6F)),
            new RandomTask(
               ImmutableList.of(
                  Pair.of(new FollowMobTask(EntityType.PIGLIN, 8.0F), 1), Pair.of(new StrollTask(0.6F, 2, 1), 1), Pair.of(new WaitTask(10, 20), 1)
               )
            )
         ),
         MemoryModuleType.CELEBRATE_LOCATION
      );
   }

   private static void addAdmireItemActivities(Brain<PiglinEntity> brain) {
      brain.setTaskList(
         Activity.ADMIRE_ITEM,
         10,
         tasks(
            new WalkToNearestVisibleWantedItemTask<>(PiglinBrain::doesNotHaveGoldInOffHand, 1.0F, true, 9),
            new WantNewItemTask(9),
            new AdmireItemTimeLimitTask(200, 200)
         ),
         MemoryModuleType.ADMIRING_ITEM
      );
   }

   private static void addAvoidActivities(Brain<PiglinEntity> brain) {
      brain.setTaskList(
         Activity.AVOID,
         10,
         tasks(
            GoToRememberedPositionTask.toEntity(MemoryModuleType.AVOID_TARGET, 1.0F, 12, true),
            makeRandomFollowTask(),
            makeRandomWanderTask(),
            new ForgetTask<>(PiglinBrain::shouldRunAwayFromHoglins, MemoryModuleType.AVOID_TARGET)
         ),
         MemoryModuleType.AVOID_TARGET
      );
   }

   private static void addRideActivities(Brain<PiglinEntity> brain) {
      brain.setTaskList(
         Activity.RIDE,
         10,
         tasks(
            new StartRidingTask(0.8F),
            new FollowMobTask(PiglinBrain::isGoldHoldingPlayer, 8.0F),
            new ConditionalTask<>(Entity::hasVehicle, makeRandomFollowTask()),
            new RidingTask<>(8, PiglinBrain::canRide)
         ),
         MemoryModuleType.RIDE_TARGET
      );
   }

   private static RandomTask<PiglinEntity> makeRandomFollowTask() {
      return new RandomTask<>(
         ImmutableList.of(
            Pair.of(new FollowMobTask(EntityType.PLAYER, 8.0F), 1),
            Pair.of(new FollowMobTask(EntityType.PIGLIN, 8.0F), 1),
            Pair.of(new FollowMobTask(8.0F), 1),
            Pair.of(new WaitTask(30, 60), 1)
         )
      );
   }

   private static RandomTask<PiglinEntity> makeRandomWanderTask() {
      return new RandomTask<>(
         ImmutableList.of(
            Pair.of(new StrollTask(0.6F), 2),
            Pair.of(FindEntityTask.create(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
            Pair.of(new ConditionalTask<>(PiglinBrain::canWander, new GoTowardsLookTarget(0.6F, 3)), 2),
            Pair.of(new WaitTask(30, 60), 1)
         )
      );
   }

   private static GoToRememberedPositionTask<BlockPos> makeGoToSoulFireTask() {
      return GoToRememberedPositionTask.toBlock(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, false);
   }

   private static MemoryTransferTask<PiglinEntity, LivingEntity> method_30090() {
      return new MemoryTransferTask<>(PiglinEntity::isBaby, MemoryModuleType.NEAREST_VISIBLE_NEMESIS, MemoryModuleType.AVOID_TARGET, field_25698);
   }

   private static MemoryTransferTask<PiglinEntity, LivingEntity> makeGoToZombifiedPiglinTask() {
      return new MemoryTransferTask<>(
         PiglinBrain::getNearestZombifiedPiglin, MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, MemoryModuleType.AVOID_TARGET, field_25384
      );
   }

   protected static void tickActivities(PiglinEntity piglin) {
      Brain<PiglinEntity> lv = piglin.getBrain();
      Activity lv2 = lv.getFirstPossibleNonCoreActivity().orElse(null);
      lv.resetPossibleActivities(ImmutableList.of(Activity.ADMIRE_ITEM, Activity.FIGHT, Activity.AVOID, Activity.CELEBRATE, Activity.RIDE, Activity.IDLE));
      Activity lv3 = lv.getFirstPossibleNonCoreActivity().orElse(null);
      if (lv2 != lv3) {
         method_30091(piglin).ifPresent(piglin::playSound);
      }

      piglin.setAttacking(lv.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
      if (!lv.hasMemoryModule(MemoryModuleType.RIDE_TARGET) && canRideHoglin(piglin)) {
         piglin.stopRiding();
      }

      if (!lv.hasMemoryModule(MemoryModuleType.CELEBRATE_LOCATION)) {
         lv.forget(MemoryModuleType.DANCING);
      }

      piglin.setDancing(lv.hasMemoryModule(MemoryModuleType.DANCING));
   }

   private static boolean canRideHoglin(PiglinEntity piglin) {
      if (!piglin.isBaby()) {
         return false;
      } else {
         Entity lv = piglin.getVehicle();
         return lv instanceof PiglinEntity && ((PiglinEntity)lv).isBaby() || lv instanceof HoglinEntity && ((HoglinEntity)lv).isBaby();
      }
   }

   protected static void loot(PiglinEntity piglin, ItemEntity drop) {
      stopWalking(piglin);
      ItemStack lv;
      if (drop.getStack().getItem() == Items.GOLD_NUGGET) {
         piglin.sendPickup(drop, drop.getStack().getCount());
         lv = drop.getStack();
         drop.remove();
      } else {
         piglin.sendPickup(drop, 1);
         lv = getItemFromStack(drop);
      }

      Item lv3 = lv.getItem();
      if (isGoldenItem(lv3)) {
         piglin.getBrain().forget(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
         swapItemWithOffHand(piglin, lv);
         setAdmiringItem(piglin);
      } else if (isFood(lv3) && !hasAteRecently(piglin)) {
         setEatenRecently(piglin);
      } else {
         boolean bl = piglin.tryEquip(lv);
         if (!bl) {
            barterItem(piglin, lv);
         }
      }
   }

   private static void swapItemWithOffHand(PiglinEntity piglin, ItemStack stack) {
      if (hasItemInOffHand(piglin)) {
         piglin.dropStack(piglin.getStackInHand(Hand.OFF_HAND));
      }

      piglin.equipToOffHand(stack);
   }

   private static ItemStack getItemFromStack(ItemEntity stack) {
      ItemStack lv = stack.getStack();
      ItemStack lv2 = lv.split(1);
      if (lv.isEmpty()) {
         stack.remove();
      } else {
         stack.setStack(lv);
      }

      return lv2;
   }

   public static void consumeOffHandItem(PiglinEntity piglin, boolean bl) {
      ItemStack lv = piglin.getStackInHand(Hand.OFF_HAND);
      piglin.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
      if (piglin.isAdult()) {
         boolean bl2 = acceptsForBarter(lv.getItem());
         if (bl && bl2) {
            doBarter(piglin, getBarteredItem(piglin));
         } else if (!bl2) {
            boolean bl3 = piglin.tryEquip(lv);
            if (!bl3) {
               barterItem(piglin, lv);
            }
         }
      } else {
         boolean bl4 = piglin.tryEquip(lv);
         if (!bl4) {
            ItemStack lv2 = piglin.getMainHandStack();
            if (isGoldenItem(lv2.getItem())) {
               barterItem(piglin, lv2);
            } else {
               doBarter(piglin, Collections.singletonList(lv2));
            }

            piglin.equipToMainHand(lv);
         }
      }
   }

   protected static void pickupItemWithOffHand(PiglinEntity piglin) {
      if (isAdmiringItem(piglin) && !piglin.getOffHandStack().isEmpty()) {
         piglin.dropStack(piglin.getOffHandStack());
         piglin.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
      }
   }

   private static void barterItem(PiglinEntity piglin, ItemStack stack) {
      ItemStack lv = piglin.addItem(stack);
      dropBarteredItem(piglin, Collections.singletonList(lv));
   }

   private static void doBarter(PiglinEntity piglin, List<ItemStack> list) {
      Optional<PlayerEntity> optional = piglin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
      if (optional.isPresent()) {
         dropBarteredItem(piglin, optional.get(), list);
      } else {
         dropBarteredItem(piglin, list);
      }
   }

   private static void dropBarteredItem(PiglinEntity piglin, List<ItemStack> list) {
      drop(piglin, list, findGround(piglin));
   }

   private static void dropBarteredItem(PiglinEntity piglin, PlayerEntity player, List<ItemStack> list) {
      drop(piglin, list, player.getPos());
   }

   private static void drop(PiglinEntity piglin, List<ItemStack> list, Vec3d arg2) {
      if (!list.isEmpty()) {
         piglin.swingHand(Hand.OFF_HAND);

         for (ItemStack lv : list) {
            LookTargetUtil.give(piglin, lv, arg2.add(0.0, 1.0, 0.0));
         }
      }
   }

   private static List<ItemStack> getBarteredItem(PiglinEntity piglin) {
      LootTable lv = piglin.world.getServer().getLootManager().getTable(LootTables.PIGLIN_BARTERING_GAMEPLAY);
      return lv.generateLoot(
         new LootContext.Builder((ServerWorld)piglin.world)
            .parameter(LootContextParameters.THIS_ENTITY, piglin)
            .random(piglin.world.random)
            .build(LootContextTypes.BARTER)
      );
   }

   private static boolean method_29276(LivingEntity arg, LivingEntity arg2) {
      return arg2.getType() != EntityType.HOGLIN ? false : new Random(arg.world.getTime()).nextFloat() < 0.1F;
   }

   protected static boolean canGather(PiglinEntity piglin, ItemStack stack) {
      Item lv = stack.getItem();
      if (lv.isIn(ItemTags.PIGLIN_REPELLENTS)) {
         return false;
      } else if (hasBeenHitByPlayer(piglin) && piglin.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) {
         return false;
      } else if (acceptsForBarter(lv)) {
         return doesNotHaveGoldInOffHand(piglin);
      } else {
         boolean bl = piglin.canInsertIntoInventory(stack);
         if (lv == Items.GOLD_NUGGET) {
            return bl;
         } else if (isFood(lv)) {
            return !hasAteRecently(piglin) && bl;
         } else {
            return !isGoldenItem(lv) ? piglin.method_24846(stack) : doesNotHaveGoldInOffHand(piglin) && bl;
         }
      }
   }

   public static boolean isGoldenItem(Item item) {
      return item.isIn(ItemTags.PIGLIN_LOVED);
   }

   private static boolean canRide(PiglinEntity piglin, Entity ridden) {
      if (!(ridden instanceof MobEntity)) {
         return false;
      } else {
         MobEntity lv = (MobEntity)ridden;
         return !lv.isBaby() || !lv.isAlive() || hasBeenHurt(piglin) || hasBeenHurt(lv) || lv instanceof PiglinEntity && lv.getVehicle() == null;
      }
   }

   private static boolean isPreferredAttackTarget(PiglinEntity piglin, LivingEntity target) {
      return getPreferredTarget(piglin).filter(arg2 -> arg2 == target).isPresent();
   }

   private static boolean getNearestZombifiedPiglin(PiglinEntity piglin) {
      Brain<PiglinEntity> lv = piglin.getBrain();
      if (lv.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED)) {
         LivingEntity lv2 = lv.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED).get();
         return piglin.isInRange(lv2, 6.0);
      } else {
         return false;
      }
   }

   private static Optional<? extends LivingEntity> getPreferredTarget(PiglinEntity piglin) {
      Brain<PiglinEntity> lv = piglin.getBrain();
      if (getNearestZombifiedPiglin(piglin)) {
         return Optional.empty();
      } else {
         Optional<LivingEntity> optional = LookTargetUtil.getEntity(piglin, MemoryModuleType.ANGRY_AT);
         if (optional.isPresent() && shouldAttack(optional.get())) {
            return optional;
         } else {
            if (lv.hasMemoryModule(MemoryModuleType.UNIVERSAL_ANGER)) {
               Optional<PlayerEntity> optional2 = lv.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
               if (optional2.isPresent()) {
                  return optional2;
               }
            }

            Optional<MobEntity> optional3 = lv.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
            if (optional3.isPresent()) {
               return optional3;
            } else {
               Optional<PlayerEntity> optional4 = lv.getOptionalMemory(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
               return optional4.isPresent() && shouldAttack(optional4.get()) ? optional4 : Optional.empty();
            }
         }
      }
   }

   public static void onGuardedBlockInteracted(PlayerEntity player, boolean blockOpen) {
      List<PiglinEntity> list = player.world.getNonSpectatingEntities(PiglinEntity.class, player.getBoundingBox().expand(16.0));
      list.stream().filter(PiglinBrain::hasIdleActivity).filter(arg2 -> !blockOpen || LookTargetUtil.isVisibleInMemory(arg2, player)).forEach(piglin -> {
         if (piglin.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
            becomeAngryWithPlayer(piglin, player);
         } else {
            becomeAngryWith(piglin, player);
         }
      });
   }

   public static ActionResult playerInteract(PiglinEntity piglin, PlayerEntity player, Hand hand) {
      ItemStack lv = player.getStackInHand(hand);
      if (isWillingToTrade(piglin, lv)) {
         ItemStack lv2 = lv.split(1);
         swapItemWithOffHand(piglin, lv2);
         setAdmiringItem(piglin);
         stopWalking(piglin);
         return ActionResult.CONSUME;
      } else {
         return ActionResult.PASS;
      }
   }

   protected static boolean isWillingToTrade(PiglinEntity piglin, ItemStack nearbyItems) {
      return !hasBeenHitByPlayer(piglin) && !isAdmiringItem(piglin) && piglin.isAdult() && acceptsForBarter(nearbyItems.getItem());
   }

   protected static void onAttacked(PiglinEntity piglin, LivingEntity attacker) {
      if (!(attacker instanceof PiglinEntity)) {
         if (hasItemInOffHand(piglin)) {
            consumeOffHandItem(piglin, false);
         }

         Brain<PiglinEntity> lv = piglin.getBrain();
         lv.forget(MemoryModuleType.CELEBRATE_LOCATION);
         lv.forget(MemoryModuleType.DANCING);
         lv.forget(MemoryModuleType.ADMIRING_ITEM);
         if (attacker instanceof PlayerEntity) {
            lv.remember(MemoryModuleType.ADMIRING_DISABLED, true, 400L);
         }

         method_29536(piglin).ifPresent(arg3 -> {
            if (arg3.getType() != attacker.getType()) {
               lv.forget(MemoryModuleType.AVOID_TARGET);
            }
         });
         if (piglin.isBaby()) {
            lv.remember(MemoryModuleType.AVOID_TARGET, attacker, 100L);
            if (shouldAttack(attacker)) {
               angerAtCloserTargets(piglin, attacker);
            }
         } else if (attacker.getType() == EntityType.HOGLIN && hasOutnumberedHoglins(piglin)) {
            runAwayFrom(piglin, attacker);
            groupRunAwayFrom(piglin, attacker);
         } else {
            tryRevenge(piglin, attacker);
         }
      }
   }

   protected static void tryRevenge(AbstractPiglinEntity arg, LivingEntity arg2) {
      if (!arg.getBrain().hasActivity(Activity.AVOID)) {
         if (shouldAttack(arg2)) {
            if (!LookTargetUtil.isNewTargetTooFar(arg, arg2, 4.0)) {
               if (arg2.getType() == EntityType.PLAYER && arg.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
                  becomeAngryWithPlayer(arg, arg2);
                  angerNearbyPiglins(arg);
               } else {
                  becomeAngryWith(arg, arg2);
                  angerAtCloserTargets(arg, arg2);
               }
            }
         }
      }
   }

   public static Optional<SoundEvent> method_30091(PiglinEntity piglin) {
      return piglin.getBrain().getFirstPossibleNonCoreActivity().map(arg2 -> method_30087(piglin, arg2));
   }

   private static SoundEvent method_30087(PiglinEntity piglin, Activity activity) {
      if (activity == Activity.FIGHT) {
         return SoundEvents.ENTITY_PIGLIN_ANGRY;
      } else if (piglin.shouldZombify()) {
         return SoundEvents.ENTITY_PIGLIN_RETREAT;
      } else if (activity == Activity.AVOID && hasTargetToAvoid(piglin)) {
         return SoundEvents.ENTITY_PIGLIN_RETREAT;
      } else if (activity == Activity.ADMIRE_ITEM) {
         return SoundEvents.ENTITY_PIGLIN_ADMIRING_ITEM;
      } else if (activity == Activity.CELEBRATE) {
         return SoundEvents.ENTITY_PIGLIN_CELEBRATE;
      } else if (hasPlayerHoldingWantedItemNearby(piglin)) {
         return SoundEvents.ENTITY_PIGLIN_JEALOUS;
      } else {
         return hasSoulFireNearby(piglin) ? SoundEvents.ENTITY_PIGLIN_RETREAT : SoundEvents.ENTITY_PIGLIN_AMBIENT;
      }
   }

   private static boolean hasTargetToAvoid(PiglinEntity piglin) {
      Brain<PiglinEntity> lv = piglin.getBrain();
      return !lv.hasMemoryModule(MemoryModuleType.AVOID_TARGET) ? false : lv.getOptionalMemory(MemoryModuleType.AVOID_TARGET).get().isInRange(piglin, 12.0);
   }

   public static boolean haveHuntedHoglinsRecently(PiglinEntity piglin) {
      return piglin.getBrain().hasMemoryModule(MemoryModuleType.HUNTED_RECENTLY)
         || getNearbyVisiblePiglins(piglin).stream().anyMatch(arg -> arg.getBrain().hasMemoryModule(MemoryModuleType.HUNTED_RECENTLY));
   }

   private static List<AbstractPiglinEntity> getNearbyVisiblePiglins(PiglinEntity piglin) {
      return piglin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS).orElse(ImmutableList.of());
   }

   private static List<AbstractPiglinEntity> getNearbyPiglins(AbstractPiglinEntity piglin) {
      return piglin.getBrain().getOptionalMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS).orElse(ImmutableList.of());
   }

   public static boolean wearsGoldArmor(LivingEntity entity) {
      for (ItemStack lv : entity.getArmorItems()) {
         Item lv2 = lv.getItem();
         if (lv2 instanceof ArmorItem && ((ArmorItem)lv2).getMaterial() == ArmorMaterials.GOLD) {
            return true;
         }
      }

      return false;
   }

   private static void stopWalking(PiglinEntity piglin) {
      piglin.getBrain().forget(MemoryModuleType.WALK_TARGET);
      piglin.getNavigation().stop();
   }

   private static TimeLimitedTask<PiglinEntity> makeRememberRideableHoglinTask() {
      return new TimeLimitedTask<>(
         new MemoryTransferTask<>(PiglinEntity::isBaby, MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, MemoryModuleType.RIDE_TARGET, RIDE_TARGET_MEMORY_DURATION),
         MEMORY_TRANSFER_TASK_DURATION
      );
   }

   public static void angerAtCloserTargets(AbstractPiglinEntity piglin, LivingEntity target) {
      getNearbyPiglins(piglin).forEach(arg2 -> {
         if (target.getType() != EntityType.HOGLIN || arg2.canHunt() && ((HoglinEntity)target).canBeHunted()) {
            angerAtIfCloser(arg2, target);
         }
      });
   }

   protected static void angerNearbyPiglins(AbstractPiglinEntity piglin) {
      getNearbyPiglins(piglin).forEach(arg -> getNearestDetectedPlayer(arg).ifPresent(arg2 -> becomeAngryWith(arg, arg2)));
   }

   public static void rememberGroupHunting(PiglinEntity piglin) {
      getNearbyVisiblePiglins(piglin).forEach(PiglinBrain::rememberHunting);
   }

   public static void becomeAngryWith(AbstractPiglinEntity piglin, LivingEntity target) {
      if (shouldAttack(target)) {
         piglin.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
         piglin.getBrain().remember(MemoryModuleType.ANGRY_AT, target.getUuid(), 600L);
         if (target.getType() == EntityType.HOGLIN && piglin.canHunt()) {
            rememberHunting(piglin);
         }

         if (target.getType() == EntityType.PLAYER && piglin.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
            piglin.getBrain().remember(MemoryModuleType.UNIVERSAL_ANGER, true, 600L);
         }
      }
   }

   private static void becomeAngryWithPlayer(AbstractPiglinEntity piglin, LivingEntity player) {
      Optional<PlayerEntity> optional = getNearestDetectedPlayer(piglin);
      if (optional.isPresent()) {
         becomeAngryWith(piglin, optional.get());
      } else {
         becomeAngryWith(piglin, player);
      }
   }

   private static void angerAtIfCloser(AbstractPiglinEntity piglin, LivingEntity target) {
      Optional<LivingEntity> optional = getAngryAt(piglin);
      LivingEntity lv = LookTargetUtil.getCloserEntity(piglin, optional, target);
      if (!optional.isPresent() || optional.get() != lv) {
         becomeAngryWith(piglin, lv);
      }
   }

   private static Optional<LivingEntity> getAngryAt(AbstractPiglinEntity piglin) {
      return LookTargetUtil.getEntity(piglin, MemoryModuleType.ANGRY_AT);
   }

   public static Optional<LivingEntity> method_29536(PiglinEntity arg) {
      return arg.getBrain().hasMemoryModule(MemoryModuleType.AVOID_TARGET) ? arg.getBrain().getOptionalMemory(MemoryModuleType.AVOID_TARGET) : Optional.empty();
   }

   public static Optional<PlayerEntity> getNearestDetectedPlayer(AbstractPiglinEntity piglin) {
      return piglin.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)
         ? piglin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)
         : Optional.empty();
   }

   private static void groupRunAwayFrom(PiglinEntity piglin, LivingEntity target) {
      getNearbyVisiblePiglins(piglin)
         .stream()
         .filter(arg -> arg instanceof PiglinEntity)
         .forEach(piglinx -> runAwayFromClosestTarget((PiglinEntity)piglinx, target));
   }

   private static void runAwayFromClosestTarget(PiglinEntity piglin, LivingEntity target) {
      Brain<PiglinEntity> lv = piglin.getBrain();
      LivingEntity lv2 = LookTargetUtil.getCloserEntity(piglin, lv.getOptionalMemory(MemoryModuleType.AVOID_TARGET), target);
      lv2 = LookTargetUtil.getCloserEntity(piglin, lv.getOptionalMemory(MemoryModuleType.ATTACK_TARGET), lv2);
      runAwayFrom(piglin, lv2);
   }

   private static boolean shouldRunAwayFromHoglins(PiglinEntity piglin) {
      Brain<PiglinEntity> lv = piglin.getBrain();
      if (!lv.hasMemoryModule(MemoryModuleType.AVOID_TARGET)) {
         return true;
      } else {
         LivingEntity lv2 = lv.getOptionalMemory(MemoryModuleType.AVOID_TARGET).get();
         EntityType<?> lv3 = lv2.getType();
         if (lv3 == EntityType.HOGLIN) {
            return hasNoAdvantageAgainstHoglins(piglin);
         } else {
            return isZombified(lv3) ? !lv.method_29519(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, lv2) : false;
         }
      }
   }

   private static boolean hasNoAdvantageAgainstHoglins(PiglinEntity piglin) {
      return !hasOutnumberedHoglins(piglin);
   }

   private static boolean hasOutnumberedHoglins(PiglinEntity piglins) {
      int i = piglins.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT).orElse(0) + 1;
      int j = piglins.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(0);
      return j > i;
   }

   private static void runAwayFrom(PiglinEntity piglin, LivingEntity target) {
      piglin.getBrain().forget(MemoryModuleType.ANGRY_AT);
      piglin.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
      piglin.getBrain().forget(MemoryModuleType.WALK_TARGET);
      piglin.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, (long)AVOID_MEMORY_DURATION.choose(piglin.world.random));
      rememberHunting(piglin);
   }

   public static void rememberHunting(AbstractPiglinEntity piglin) {
      piglin.getBrain().remember(MemoryModuleType.HUNTED_RECENTLY, true, (long)HUNT_MEMORY_DURATION.choose(piglin.world.random));
   }

   private static void setEatenRecently(PiglinEntity piglin) {
      piglin.getBrain().remember(MemoryModuleType.ATE_RECENTLY, true, 200L);
   }

   private static Vec3d findGround(PiglinEntity piglin) {
      Vec3d lv = TargetFinder.findGroundTarget(piglin, 4, 2);
      return lv == null ? piglin.getPos() : lv;
   }

   private static boolean hasAteRecently(PiglinEntity piglin) {
      return piglin.getBrain().hasMemoryModule(MemoryModuleType.ATE_RECENTLY);
   }

   protected static boolean hasIdleActivity(AbstractPiglinEntity piglin) {
      return piglin.getBrain().hasActivity(Activity.IDLE);
   }

   private static boolean isHoldingCrossbow(LivingEntity piglin) {
      return piglin.isHolding(Items.CROSSBOW);
   }

   private static void setAdmiringItem(LivingEntity entity) {
      entity.getBrain().remember(MemoryModuleType.ADMIRING_ITEM, true, 120L);
   }

   private static boolean isAdmiringItem(PiglinEntity entity) {
      return entity.getBrain().hasMemoryModule(MemoryModuleType.ADMIRING_ITEM);
   }

   private static boolean acceptsForBarter(Item item) {
      return item == BARTERING_ITEM;
   }

   private static boolean isFood(Item item) {
      return FOOD.contains(item);
   }

   private static boolean shouldAttack(LivingEntity target) {
      return EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(target);
   }

   private static boolean hasSoulFireNearby(PiglinEntity piglin) {
      return piglin.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_REPELLENT);
   }

   private static boolean hasPlayerHoldingWantedItemNearby(LivingEntity entity) {
      return entity.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM);
   }

   private static boolean canWander(LivingEntity piglin) {
      return !hasPlayerHoldingWantedItemNearby(piglin);
   }

   public static boolean isGoldHoldingPlayer(LivingEntity target) {
      return target.getType() == EntityType.PLAYER && target.isHolding(PiglinBrain::isGoldenItem);
   }

   private static boolean hasBeenHitByPlayer(PiglinEntity piglin) {
      return piglin.getBrain().hasMemoryModule(MemoryModuleType.ADMIRING_DISABLED);
   }

   private static boolean hasBeenHurt(LivingEntity piglin) {
      return piglin.getBrain().hasMemoryModule(MemoryModuleType.HURT_BY);
   }

   private static boolean hasItemInOffHand(PiglinEntity piglin) {
      return !piglin.getOffHandStack().isEmpty();
   }

   private static boolean doesNotHaveGoldInOffHand(PiglinEntity piglin) {
      return piglin.getOffHandStack().isEmpty() || !isGoldenItem(piglin.getOffHandStack().getItem());
   }

   public static boolean isZombified(EntityType entityType) {
      return entityType == EntityType.ZOMBIFIED_PIGLIN || entityType == EntityType.ZOGLIN;
   }

   @SafeVarargs
   private static ImmutableList<Task<? super PiglinEntity>> tasks(Task<? super PiglinEntity>... tasks) {
      return ImmutableList.copyOf(tasks);
   }
}
