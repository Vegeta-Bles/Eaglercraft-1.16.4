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
      this(_snowman -> false);
   }

   protected void run(ServerWorld _snowman, E _snowman, long _snowman) {
      if (cannotReachTarget(_snowman)) {
         this.forgetAttackTarget(_snowman);
      } else if (this.isAttackTargetDead(_snowman)) {
         this.forgetAttackTarget(_snowman);
      } else if (this.isAttackTargetInAnotherWorld(_snowman)) {
         this.forgetAttackTarget(_snowman);
      } else if (!EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(this.getAttackTarget(_snowman))) {
         this.forgetAttackTarget(_snowman);
      } else if (this.alternativeCondition.test(this.getAttackTarget(_snowman))) {
         this.forgetAttackTarget(_snowman);
      }
   }

   private boolean isAttackTargetInAnotherWorld(E entity) {
      return this.getAttackTarget(entity).world != entity.world;
   }

   private LivingEntity getAttackTarget(E entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
   }

   private static <E extends LivingEntity> boolean cannotReachTarget(E entity) {
      Optional<Long> _snowman = entity.getBrain().getOptionalMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      return _snowman.isPresent() && entity.world.getTime() - _snowman.get() > 200L;
   }

   private boolean isAttackTargetDead(E entity) {
      Optional<LivingEntity> _snowman = entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
      return _snowman.isPresent() && !_snowman.get().isAlive();
   }

   private void forgetAttackTarget(E entity) {
      entity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
   }
}
