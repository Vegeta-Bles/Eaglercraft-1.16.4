package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public class LookAroundTask extends Task<MobEntity> {
   public LookAroundTask(int minRunTime, int maxRunTime) {
      super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_PRESENT), minRunTime, maxRunTime);
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      return _snowman.getBrain().getOptionalMemory(MemoryModuleType.LOOK_TARGET).filter(_snowmanxxxx -> _snowmanxxxx.isSeenBy(_snowman)).isPresent();
   }

   protected void finishRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      _snowman.getBrain().forget(MemoryModuleType.LOOK_TARGET);
   }

   protected void keepRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      _snowman.getBrain().getOptionalMemory(MemoryModuleType.LOOK_TARGET).ifPresent(_snowmanxxxx -> _snowman.getLookControl().lookAt(_snowmanxxxx.getPos()));
   }
}
