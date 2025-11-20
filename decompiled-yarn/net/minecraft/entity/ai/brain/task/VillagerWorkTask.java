package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.brain.BlockPosLookTarget;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;

public class VillagerWorkTask extends Task<VillagerEntity> {
   private long lastCheckedTime;

   public VillagerWorkTask() {
      super(ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED));
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      if (_snowman.getTime() - this.lastCheckedTime < 300L) {
         return false;
      } else if (_snowman.random.nextInt(2) != 0) {
         return false;
      } else {
         this.lastCheckedTime = _snowman.getTime();
         GlobalPos _snowmanxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE).get();
         return _snowmanxx.getDimension() == _snowman.getRegistryKey() && _snowmanxx.getPos().isWithinDistance(_snowman.getPos(), 1.73);
      }
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      Brain<VillagerEntity> _snowmanxxx = _snowman.getBrain();
      _snowmanxxx.remember(MemoryModuleType.LAST_WORKED_AT_POI, _snowman);
      _snowmanxxx.getOptionalMemory(MemoryModuleType.JOB_SITE).ifPresent(_snowmanxxxx -> _snowman.remember(MemoryModuleType.LOOK_TARGET, new BlockPosLookTarget(_snowmanxxxx.getPos())));
      _snowman.playWorkSound();
      this.performAdditionalWork(_snowman, _snowman);
      if (_snowman.shouldRestock()) {
         _snowman.restock();
      }
   }

   protected void performAdditionalWork(ServerWorld world, VillagerEntity entity) {
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      Optional<GlobalPos> _snowmanxxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.JOB_SITE);
      if (!_snowmanxxx.isPresent()) {
         return false;
      } else {
         GlobalPos _snowmanxxxx = _snowmanxxx.get();
         return _snowmanxxxx.getDimension() == _snowman.getRegistryKey() && _snowmanxxxx.getPos().isWithinDistance(_snowman.getPos(), 1.73);
      }
   }
}
