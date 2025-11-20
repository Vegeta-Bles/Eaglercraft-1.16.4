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

   protected boolean shouldRun(ServerWorld _snowman, E _snowman) {
      return _snowman.getOffHandStack().isEmpty();
   }

   protected void run(ServerWorld _snowman, E _snowman, long _snowman) {
      Brain<PiglinEntity> _snowmanxxx = _snowman.getBrain();
      Optional<Integer> _snowmanxxxx = _snowmanxxx.getOptionalMemory(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
      if (!_snowmanxxxx.isPresent()) {
         _snowmanxxx.remember(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, 0);
      } else {
         int _snowmanxxxxx = _snowmanxxxx.get();
         if (_snowmanxxxxx > this.timeLimit) {
            _snowmanxxx.forget(MemoryModuleType.ADMIRING_ITEM);
            _snowmanxxx.forget(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM);
            _snowmanxxx.remember(MemoryModuleType.DISABLE_WALK_TO_ADMIRE_ITEM, true, (long)this.cooldown);
         } else {
            _snowmanxxx.remember(MemoryModuleType.TIME_TRYING_TO_REACH_ADMIRE_ITEM, _snowmanxxxxx + 1);
         }
      }
   }
}
