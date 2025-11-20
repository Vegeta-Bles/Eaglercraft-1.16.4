package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

public class ForgetAttackTargetTask<E extends MobEntity> extends Task<E> {
   private final Predicate<LivingEntity> alternativeCondition;

   public ForgetAttackTargetTask(Predicate<LivingEntity> alternativeCondition) {
      super(
         ImmutableMap.of(
            MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleState.REGISTERED
         )
      );
      this.alternativeCondition = alternativeCondition;
   }

   public ForgetAttackTargetTask() {
      this(arg -> false);
   }

   protected void run(ServerWorld arg, E arg2, long l) {
      if (cannotReachTarget(arg2)) {
         this.forgetAttackTarget(arg2);
      } else if (this.isAttackTargetDead(arg2)) {
         this.forgetAttackTarget(arg2);
      } else if (this.isAttackTargetInAnotherWorld(arg2)) {
         this.forgetAttackTarget(arg2);
      } else if (!EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(this.getAttackTarget(arg2))) {
         this.forgetAttackTarget(arg2);
      } else if (this.alternativeCondition.test(this.getAttackTarget(arg2))) {
         this.forgetAttackTarget(arg2);
      }
   }

   private boolean isAttackTargetInAnotherWorld(E entity) {
      return this.getAttackTarget(entity).world != entity.world;
   }

   private LivingEntity getAttackTarget(E entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
   }

   private static <E extends LivingEntity> boolean cannotReachTarget(E entity) {
      Optional<Long> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      return optional.isPresent() && entity.world.getTime() - optional.get() > 200L;
   }

   private boolean isAttackTargetDead(E entity) {
      Optional<LivingEntity> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
      return optional.isPresent() && !optional.get().isAlive();
   }

   private void forgetAttackTarget(E entity) {
      entity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
   }
}
