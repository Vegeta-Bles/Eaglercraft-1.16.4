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
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.UpdateAttackTargetTask;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.dynamic.GlobalPos;

public class PiglinBruteBrain {
   protected static Brain<?> create(PiglinBruteEntity arg, Brain<PiglinBruteEntity> arg2) {
      method_30257(arg, arg2);
      method_30260(arg, arg2);
      method_30262(arg, arg2);
      arg2.setCoreActivities(ImmutableSet.of(Activity.CORE));
      arg2.setDefaultActivity(Activity.IDLE);
      arg2.resetPossibleActivities();
      return arg2;
   }

   protected static void method_30250(PiglinBruteEntity arg) {
      GlobalPos lv = GlobalPos.create(arg.world.getRegistryKey(), arg.getBlockPos());
      arg.getBrain().remember(MemoryModuleType.HOME, lv);
   }

   private static void method_30257(PiglinBruteEntity arg, Brain<PiglinBruteEntity> arg2) {
      arg2.setTaskList(
         Activity.CORE, 0, tasks(new LookAroundTask(45, 90), new WanderAroundTask(), new OpenDoorsTask(), new ForgetAngryAtTargetTask())
      );
   }

   private static void method_30260(PiglinBruteEntity arg, Brain<PiglinBruteEntity> arg2) {
      arg2.setTaskList(
         Activity.IDLE,
         10,
         tasks(new UpdateAttackTargetTask<>(PiglinBruteBrain::method_30247), method_30244(), method_30254(), new FindInteractionTargetTask(EntityType.PLAYER, 4))
      );
   }

   private static void method_30262(PiglinBruteEntity arg, Brain<PiglinBruteEntity> arg2) {
      arg2.setTaskList(
         Activity.FIGHT,
         10,
         tasks(
            new ForgetAttackTargetTask<PiglinBruteEntity>(target -> !method_30248(arg, target)),
            new RangedApproachTask(1.0F),
            new MeleeAttackTask(20)
         ),
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

   protected static void method_30256(PiglinBruteEntity arg) {
      Brain<PiglinBruteEntity> lv = arg.getBrain();
      Activity lv2 = lv.getFirstPossibleNonCoreActivity().orElse(null);
      lv.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
      Activity lv3 = lv.getFirstPossibleNonCoreActivity().orElse(null);
      if (lv2 != lv3) {
         method_30261(arg);
      }

      arg.setAttacking(lv.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
   }

   private static boolean method_30248(AbstractPiglinEntity arg, LivingEntity arg2) {
      return method_30247(arg).filter(arg2x -> arg2x == arg2).isPresent();
   }

   private static Optional<? extends LivingEntity> method_30247(AbstractPiglinEntity arg) {
      Optional<LivingEntity> optional = LookTargetUtil.getEntity(arg, MemoryModuleType.ANGRY_AT);
      if (optional.isPresent() && method_30245(optional.get())) {
         return optional;
      } else {
         Optional<? extends LivingEntity> optional2 = method_30249(arg, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
         return optional2.isPresent() ? optional2 : arg.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
      }
   }

   private static boolean method_30245(LivingEntity arg) {
      return EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(arg);
   }

   private static Optional<? extends LivingEntity> method_30249(AbstractPiglinEntity arg, MemoryModuleType<? extends LivingEntity> arg2) {
      return arg.getBrain().getOptionalMemory(arg2).filter(arg2x -> arg2x.isInRange(arg, 12.0));
   }

   protected static void method_30251(PiglinBruteEntity arg, LivingEntity arg2) {
      if (!(arg2 instanceof AbstractPiglinEntity)) {
         PiglinBrain.tryRevenge(arg, arg2);
      }
   }

   protected static void method_30258(PiglinBruteEntity arg) {
      if ((double)arg.world.random.nextFloat() < 0.0125) {
         method_30261(arg);
      }
   }

   private static void method_30261(PiglinBruteEntity arg) {
      arg.getBrain().getFirstPossibleNonCoreActivity().ifPresent(arg2 -> {
         if (arg2 == Activity.FIGHT) {
            arg.playAngrySound();
         }
      });
   }

   @SafeVarargs
   private static ImmutableList<Task<? super PiglinBruteEntity>> tasks(Task<? super PiglinBruteEntity>... tasks) {
      return ImmutableList.copyOf(tasks);
   }
}
