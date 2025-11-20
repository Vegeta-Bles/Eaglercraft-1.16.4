package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.server.world.ServerWorld;

public class AdmireItemTimeLimitTask<E extends PiglinEntity> extends Task<E> {
   private final int timeLimit;
   private final int cooldown;

   public AdmireItemTimeLimitTask(int timeLimit, int cooldown) {
      super(
         ImmutableMap.of(
            MemoryModuleType.ADMIRING_ITEM,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM,
            MemoryModuleState.REGISTERED
         )
      );
      this.timeLimit = timeLimit;
      this.cooldown = cooldown;
   }

   protected boolean shouldRun(ServerWorld arg, E arg2) {
      return arg2.getOffHandStack().isEmpty();
   }

   protected void run(ServerWorld arg, E arg2, long l) {
      Brain<PiglinEntity> lv = arg2.getBrain();
      Optional<Integer> optional = lv.getOptionalMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
      if (!optional.isPresent()) {
         lv.remember(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, 0);
      } else {
         int i = optional.get();
         if (i > this.timeLimit) {
            lv.forget(MemoryModuleType.ADMIRING_ITEM);
            lv.forget(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            lv.remember(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, true, (long)this.cooldown);
         } else {
            lv.remember(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, i + 1);
         }
      }
   }
}
