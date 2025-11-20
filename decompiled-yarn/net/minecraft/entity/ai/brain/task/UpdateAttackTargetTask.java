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
      this(_snowman -> true, targetGetter);
   }

   protected boolean shouldRun(ServerWorld _snowman, E _snowman) {
      if (!this.startCondition.test(_snowman)) {
         return false;
      } else {
         Optional<? extends LivingEntity> _snowmanxx = this.targetGetter.apply(_snowman);
         return _snowmanxx.isPresent() && _snowmanxx.get().isAlive();
      }
   }

   protected void run(ServerWorld _snowman, E _snowman, long _snowman) {
      this.targetGetter.apply(_snowman).ifPresent(_snowmanxxx -> this.updateAttackTarget(_snowman, _snowmanxxx));
   }

   private void updateAttackTarget(E entity, LivingEntity target) {
      entity.getBrain().remember(MemoryModuleType.ATTACK_TARGET, target);
      entity.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   }
}
