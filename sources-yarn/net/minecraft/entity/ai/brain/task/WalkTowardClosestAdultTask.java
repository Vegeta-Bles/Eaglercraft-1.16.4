package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.IntRange;

public class WalkTowardClosestAdultTask<E extends PassiveEntity> extends Task<E> {
   private final IntRange executionRange;
   private final float speed;

   public WalkTowardClosestAdultTask(IntRange executionRange, float speed) {
      super(
         ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT)
      );
      this.executionRange = executionRange;
      this.speed = speed;
   }

   protected boolean shouldRun(ServerWorld arg, E arg2) {
      if (!arg2.isBaby()) {
         return false;
      } else {
         PassiveEntity lv = this.getNearestVisibleAdult(arg2);
         return arg2.isInRange(lv, (double)(this.executionRange.getMax() + 1)) && !arg2.isInRange(lv, (double)this.executionRange.getMin());
      }
   }

   protected void run(ServerWorld arg, E arg2, long l) {
      LookTargetUtil.walkTowards(arg2, this.getNearestVisibleAdult(arg2), this.speed, this.executionRange.getMin() - 1);
   }

   private PassiveEntity getNearestVisibleAdult(E entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT).get();
   }
}
