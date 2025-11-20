package net.minecraft.entity.mob;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.FindEntityTask;
import net.minecraft.entity.ai.brain.task.FindInteractionTargetTask;
import net.minecraft.entity.ai.brain.task.FollowMobTask;
import net.minecraft.entity.ai.brain.task.ForgetAngryAtTargetTask;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.GoToIfNearbyTask;
import net.minecraft.entity.ai.brain.task.GoToNearbyPositionTask;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MeleeAttackTask;
import net.minecraft.entity.ai.brain.task.OpenDoorsTask;
import net.minecraft.entity.ai.brain.task.RandomTask;
import net.minecraft.entity.ai.brain.task.RangedApproachTask;
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.dynamic.GlobalPos;

public class PiglinBruteBrain {
   protected static Brain<?> create(PiglinBruteEntity _snowman, Brain<PiglinBruteEntity> _snowman) {
      method_30257(_snowman, _snowman);
      method_30260(_snowman, _snowman);
      method_30262(_snowman, _snowman);
      _snowman.setCoreActivities(ImmutableSet.of(Activity.CORE));
      _snowman.setDefaultActivity(Activity.IDLE);
      _snowman.resetPossibleActivities();
      return _snowman;
   }

   protected static void method_30250(PiglinBruteEntity _snowman) {
      GlobalPos _snowmanx = GlobalPos.create(_snowman.world.getRegistryKey(), _snowman.getBlockPos());
      _snowman.getBrain().remember(MemoryModuleType.HOME, _snowmanx);
   }

   private static void method_30257(PiglinBruteEntity _snowman, Brain<PiglinBruteEntity> _snowman) {
      _snowman.setTaskList(Activity.CORE, 0, ImmutableList.of(new LookAroundTask(45, 90), new WanderAroundTask(), new OpenDoorsTask(), new ForgetAngryAtTargetTask()));
   }

   private static void method_30260(PiglinBruteEntity _snowman, Brain<PiglinBruteEntity> _snowman) {
      _snowman.setTaskList(
         Activity.IDLE,
         10,
         ImmutableList.of(
            new UpdateAttackTargetTask<>(PiglinBruteBrain::method_30247), method_30244(), method_30254(), new FindInteractionTargetTask(EntityType.PLAYER, 4)
         )
      );
   }

   private static void method_30262(PiglinBruteEntity _snowman, Brain<PiglinBruteEntity> _snowman) {
      _snowman.setTaskList(
         Activity.FIGHT,
         10,
         ImmutableList.of(new ForgetAttackTargetTask(_snowmanxxx -> !method_30248(_snowman, _snowmanxxx)), new RangedApproachTask(1.0F), new MeleeAttackTask(20)),
         MemoryModuleType.ATTACK_TARGET
      );
   }

   private static RandomTask<PiglinBruteEntity> method_30244() {
      return new RandomTask<>(
         ImmutableList.of(
            Pair.of(new FollowMobTask(EntityType.PLAYER, 8.0F), 1),
            Pair.of(new FollowMobTask(EntityType.PIGLIN, 8.0F), 1),
            Pair.of(new FollowMobTask(EntityType.PIGLIN_BRUTE, 8.0F), 1),
            Pair.of(new FollowMobTask(8.0F), 1),
            Pair.of(new WaitTask(30, 60), 1)
         )
      );
   }

   private static RandomTask<PiglinBruteEntity> method_30254() {
      return new RandomTask<>(
         ImmutableList.of(
            Pair.of(new StrollTask(0.6F), 2),
            Pair.of(FindEntityTask.create(EntityType.PIGLIN, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
            Pair.of(FindEntityTask.create(EntityType.PIGLIN_BRUTE, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
            Pair.of(new GoToNearbyPositionTask(MemoryModuleType.HOME, 0.6F, 2, 100), 2),
            Pair.of(new GoToIfNearbyTask(MemoryModuleType.HOME, 0.6F, 5), 2),
            Pair.of(new WaitTask(30, 60), 1)
         )
      );
   }

   protected static void method_30256(PiglinBruteEntity _snowman) {
      Brain<PiglinBruteEntity> _snowmanx = _snowman.getBrain();
      Activity _snowmanxx = _snowmanx.getFirstPossibleNonCoreActivity().orElse(null);
      _snowmanx.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
      Activity _snowmanxxx = _snowmanx.getFirstPossibleNonCoreActivity().orElse(null);
      if (_snowmanxx != _snowmanxxx) {
         method_30261(_snowman);
      }

      _snowman.setAttacking(_snowmanx.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
   }

   private static boolean method_30248(AbstractPiglinEntity _snowman, LivingEntity _snowman) {
      return method_30247(_snowman).filter(_snowmanxxx -> _snowmanxxx == _snowman).isPresent();
   }

   private static Optional<? extends LivingEntity> method_30247(AbstractPiglinEntity _snowman) {
      Optional<LivingEntity> _snowmanx = LookTargetUtil.getEntity(_snowman, MemoryModuleType.ANGRY_AT);
      if (_snowmanx.isPresent() && method_30245(_snowmanx.get())) {
         return _snowmanx;
      } else {
         Optional<? extends LivingEntity> _snowmanxx = method_30249(_snowman, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
         return _snowmanxx.isPresent() ? _snowmanxx : _snowman.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
      }
   }

   private static boolean method_30245(LivingEntity _snowman) {
      return EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(_snowman);
   }

   private static Optional<? extends LivingEntity> method_30249(AbstractPiglinEntity _snowman, MemoryModuleType<? extends LivingEntity> _snowman) {
      return _snowman.getBrain().getOptionalMemory(_snowman).filter(_snowmanxxx -> _snowmanxxx.isInRange(_snowman, 12.0));
   }

   protected static void method_30251(PiglinBruteEntity _snowman, LivingEntity _snowman) {
      if (!(_snowman instanceof AbstractPiglinEntity)) {
         PiglinBrain.tryRevenge(_snowman, _snowman);
      }
   }

   protected static void method_30258(PiglinBruteEntity _snowman) {
      if ((double)_snowman.world.random.nextFloat() < 0.0125) {
         method_30261(_snowman);
      }
   }

   private static void method_30261(PiglinBruteEntity _snowman) {
      _snowman.getBrain().getFirstPossibleNonCoreActivity().ifPresent(_snowmanxx -> {
         if (_snowmanxx == Activity.FIGHT) {
            _snowman.playAngrySound();
         }
      });
   }
}
