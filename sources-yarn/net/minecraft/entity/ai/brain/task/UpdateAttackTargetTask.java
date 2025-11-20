package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public class UpdateAttackTargetTask<E extends MobEntity> extends Task<E> {
   private final Predicate<E> startCondition;
   private final Function<E, Optional<? extends LivingEntity>> targetGetter;

   public UpdateAttackTargetTask(Predicate<E> startCondition, Function<E, Optional<? extends LivingEntity>> targetGetter) {
      super(
         ImmutableMap.of(
            MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleState.REGISTERED
         )
      );
      this.startCondition = startCondition;
      this.targetGetter = targetGetter;
   }

   public UpdateAttackTargetTask(Function<E, Optional<? extends LivingEntity>> targetGetter) {
      this(arg -> true, targetGetter);
   }

   protected boolean shouldRun(ServerWorld arg, E arg2) {
      if (!this.startCondition.test(arg2)) {
         return false;
      } else {
         Optional<? extends LivingEntity> optional = this.targetGetter.apply(arg2);
         return optional.isPresent() && optional.get().isAlive();
      }
   }

   protected void run(ServerWorld arg, E arg2, long l) {
      this.targetGetter.apply(arg2).ifPresent(arg2x -> this.updateAttackTarget(arg2, arg2x));
   }

   private void updateAttackTarget(E entity, LivingEntity target) {
      entity.getBrain().remember(MemoryModuleType.ATTACK_TARGET, target);
      entity.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   }
}
