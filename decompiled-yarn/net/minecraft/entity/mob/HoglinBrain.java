package net.minecraft.entity.mob;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.Durations;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.ai.brain.task.ConditionalTask;
import net.minecraft.entity.ai.brain.task.FollowMobTask;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.ForgetTask;
import net.minecraft.entity.ai.brain.task.GoToRememberedPositionTask;
import net.minecraft.entity.ai.brain.task.GoTowardsLookTarget;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MeleeAttackTask;
import net.minecraft.entity.ai.brain.task.PacifyTask;
import net.minecraft.entity.ai.brain.task.RandomTask;
import net.minecraft.entity.ai.brain.task.RangedApproachTask;
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TimeLimitedTask;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.ai.brain.task.WalkTowardClosestAdultTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.IntRange;

public class HoglinBrain {
   private static final IntRange AVOID_MEMORY_DURATION = Durations.betweenSeconds(5, 20);
   private static final IntRange WALK_TOWARD_CLOSEST_ADULT_RANGE = IntRange.between(5, 16);

   protected static Brain<?> create(Brain<HoglinEntity> brain) {
      addCoreTasks(brain);
      addIdleTasks(brain);
      addFightTasks(brain);
      addAvoidTasks(brain);
      brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
      brain.setDefaultActivity(Activity.IDLE);
      brain.resetPossibleActivities();
      return brain;
   }

   private static void addCoreTasks(Brain<HoglinEntity> brain) {
      brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new LookAroundTask(45, 90), new WanderAroundTask()));
   }

   private static void addIdleTasks(Brain<HoglinEntity> brain) {
      brain.setTaskList(
         Activity.IDLE,
         10,
         ImmutableList.of(
            new PacifyTask(MemoryModuleType.NEAREST_REPELLENT, 200),
            new BreedTask(EntityType.HOGLIN, 0.6F),
            GoToRememberedPositionTask.toBlock(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, true),
            new UpdateAttackTargetTask<>(HoglinBrain::getNearestVisibleTargetablePlayer),
            new ConditionalTask<>(
               HoglinEntity::isAdult,
               (Task<? super PathAwareEntity>)GoToRememberedPositionTask.toEntity(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, 0.4F, 8, false)
            ),
            new TimeLimitedTask<>(new FollowMobTask(8.0F), IntRange.between(30, 60)),
            new WalkTowardClosestAdultTask(WALK_TOWARD_CLOSEST_ADULT_RANGE, 0.6F),
            makeRandomWalkTask()
         )
      );
   }

   private static void addFightTasks(Brain<HoglinEntity> brain) {
      brain.setTaskList(
         Activity.FIGHT,
         10,
         ImmutableList.of(
            new PacifyTask(MemoryModuleType.NEAREST_REPELLENT, 200),
            new BreedTask(EntityType.HOGLIN, 0.6F),
            new RangedApproachTask(1.0F),
            new ConditionalTask<>(HoglinEntity::isAdult, new MeleeAttackTask(40)),
            new ConditionalTask<>(PassiveEntity::isBaby, new MeleeAttackTask(15)),
            new ForgetAttackTargetTask(),
            new ForgetTask<>(HoglinBrain::hasBreedTarget, MemoryModuleType.ATTACK_TARGET)
         ),
         MemoryModuleType.ATTACK_TARGET
      );
   }

   private static void addAvoidTasks(Brain<HoglinEntity> brain) {
      brain.setTaskList(
         Activity.AVOID,
         10,
         ImmutableList.of(
            GoToRememberedPositionTask.toEntity(MemoryModuleType.AVOID_TARGET, 1.3F, 15, false),
            makeRandomWalkTask(),
            new TimeLimitedTask<>(new FollowMobTask(8.0F), IntRange.between(30, 60)),
            new ForgetTask<>(HoglinBrain::method_25947, MemoryModuleType.AVOID_TARGET)
         ),
         MemoryModuleType.AVOID_TARGET
      );
   }

   private static RandomTask<HoglinEntity> makeRandomWalkTask() {
      return new RandomTask<>(
         ImmutableList.of(Pair.of(new StrollTask(0.4F), 2), Pair.of(new GoTowardsLookTarget(0.4F, 3), 2), Pair.of(new WaitTask(30, 60), 1))
      );
   }

   protected static void refreshActivities(HoglinEntity hoglin) {
      Brain<HoglinEntity> _snowman = hoglin.getBrain();
      Activity _snowmanx = _snowman.getFirstPossibleNonCoreActivity().orElse(null);
      _snowman.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.AVOID, Activity.IDLE));
      Activity _snowmanxx = _snowman.getFirstPossibleNonCoreActivity().orElse(null);
      if (_snowmanx != _snowmanxx) {
         method_30083(hoglin).ifPresent(hoglin::method_30081);
      }

      hoglin.setAttacking(_snowman.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
   }

   protected static void onAttacking(HoglinEntity hoglin, LivingEntity target) {
      if (!hoglin.isBaby()) {
         if (target.getType() == EntityType.PIGLIN && hasMoreHoglinsAround(hoglin)) {
            avoid(hoglin, target);
            askAdultsToAvoid(hoglin, target);
         } else {
            askAdultsForHelp(hoglin, target);
         }
      }
   }

   private static void askAdultsToAvoid(HoglinEntity hoglin, LivingEntity target) {
      getAdultHoglinsAround(hoglin).forEach(hoglinx -> avoidEnemy(hoglinx, target));
   }

   private static void avoidEnemy(HoglinEntity hoglin, LivingEntity target) {
      Brain<HoglinEntity> _snowman = hoglin.getBrain();
      LivingEntity var2 = LookTargetUtil.getCloserEntity(hoglin, _snowman.getOptionalMemory(MemoryModuleType.AVOID_TARGET), target);
      var2 = LookTargetUtil.getCloserEntity(hoglin, _snowman.getOptionalMemory(MemoryModuleType.ATTACK_TARGET), var2);
      avoid(hoglin, var2);
   }

   private static void avoid(HoglinEntity hoglin, LivingEntity target) {
      hoglin.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
      hoglin.getBrain().forget(MemoryModuleType.WALK_TARGET);
      hoglin.getBrain().remember(MemoryModuleType.AVOID_TARGET, target, (long)AVOID_MEMORY_DURATION.choose(hoglin.world.random));
   }

   private static Optional<? extends LivingEntity> getNearestVisibleTargetablePlayer(HoglinEntity hoglin) {
      return !isNearPlayer(hoglin) && !hasBreedTarget(hoglin)
         ? hoglin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)
         : Optional.empty();
   }

   static boolean isWarpedFungusAround(HoglinEntity hoglin, BlockPos pos) {
      Optional<BlockPos> _snowman = hoglin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_REPELLENT);
      return _snowman.isPresent() && _snowman.get().isWithinDistance(pos, 8.0);
   }

   private static boolean method_25947(HoglinEntity _snowman) {
      return _snowman.isAdult() && !hasMoreHoglinsAround(_snowman);
   }

   private static boolean hasMoreHoglinsAround(HoglinEntity hoglin) {
      if (hoglin.isBaby()) {
         return false;
      } else {
         int _snowman = hoglin.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT).orElse(0);
         int _snowmanx = hoglin.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT).orElse(0) + 1;
         return _snowman > _snowmanx;
      }
   }

   protected static void onAttacked(HoglinEntity hoglin, LivingEntity attacker) {
      Brain<HoglinEntity> _snowman = hoglin.getBrain();
      _snowman.forget(MemoryModuleType.PACIFIED);
      _snowman.forget(MemoryModuleType.BREED_TARGET);
      if (hoglin.isBaby()) {
         avoidEnemy(hoglin, attacker);
      } else {
         targetEnemy(hoglin, attacker);
      }
   }

   private static void targetEnemy(HoglinEntity hoglin, LivingEntity target) {
      if (!hoglin.getBrain().hasActivity(Activity.AVOID) || target.getType() != EntityType.PIGLIN) {
         if (EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(target)) {
            if (target.getType() != EntityType.HOGLIN) {
               if (!LookTargetUtil.isNewTargetTooFar(hoglin, target, 4.0)) {
                  setAttackTarget(hoglin, target);
                  askAdultsForHelp(hoglin, target);
               }
            }
         }
      }
   }

   private static void setAttackTarget(HoglinEntity hoglin, LivingEntity target) {
      Brain<HoglinEntity> _snowman = hoglin.getBrain();
      _snowman.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      _snowman.forget(MemoryModuleType.BREED_TARGET);
      _snowman.remember(MemoryModuleType.ATTACK_TARGET, target, 200L);
   }

   private static void askAdultsForHelp(HoglinEntity hoglin, LivingEntity target) {
      getAdultHoglinsAround(hoglin).forEach(hoglinx -> setAttackTargetIfCloser(hoglinx, target));
   }

   private static void setAttackTargetIfCloser(HoglinEntity hoglin, LivingEntity targetCandidate) {
      if (!isNearPlayer(hoglin)) {
         Optional<LivingEntity> _snowman = hoglin.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
         LivingEntity _snowmanx = LookTargetUtil.getCloserEntity(hoglin, _snowman, targetCandidate);
         setAttackTarget(hoglin, _snowmanx);
      }
   }

   public static Optional<SoundEvent> method_30083(HoglinEntity _snowman) {
      return _snowman.getBrain().getFirstPossibleNonCoreActivity().map(_snowmanxx -> method_30082(_snowman, _snowmanxx));
   }

   private static SoundEvent method_30082(HoglinEntity _snowman, Activity _snowman) {
      if (_snowman == Activity.AVOID || _snowman.canConvert()) {
         return SoundEvents.ENTITY_HOGLIN_RETREAT;
      } else if (_snowman == Activity.FIGHT) {
         return SoundEvents.ENTITY_HOGLIN_ANGRY;
      } else {
         return method_30085(_snowman) ? SoundEvents.ENTITY_HOGLIN_RETREAT : SoundEvents.ENTITY_HOGLIN_AMBIENT;
      }
   }

   private static List<HoglinEntity> getAdultHoglinsAround(HoglinEntity hoglin) {
      return hoglin.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS).orElse(ImmutableList.of());
   }

   private static boolean method_30085(HoglinEntity _snowman) {
      return _snowman.getBrain().hasMemoryModule(MemoryModuleType.NEAREST_REPELLENT);
   }

   private static boolean hasBreedTarget(HoglinEntity hoglin) {
      return hoglin.getBrain().hasMemoryModule(MemoryModuleType.BREED_TARGET);
   }

   protected static boolean isNearPlayer(HoglinEntity hoglin) {
      return hoglin.getBrain().hasMemoryModule(MemoryModuleType.PACIFIED);
   }
}
