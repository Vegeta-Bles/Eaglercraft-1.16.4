package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;

public class StopPanickingTask extends Task<VillagerEntity> {
   public StopPanickingTask() {
      super(ImmutableMap.of());
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      boolean _snowmanxxx = PanicTask.wasHurt(_snowman) || PanicTask.isHostileNearby(_snowman) || wasHurtByNearbyEntity(_snowman);
      if (!_snowmanxxx) {
         _snowman.getBrain().forget(MemoryModuleType.HURT_BY);
         _snowman.getBrain().forget(MemoryModuleType.HURT_BY_ENTITY);
         _snowman.getBrain().refreshActivities(_snowman.getTimeOfDay(), _snowman.getTime());
      }
   }

   private static boolean wasHurtByNearbyEntity(VillagerEntity entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.HURT_BY_ENTITY).filter(_snowmanx -> _snowmanx.squaredDistanceTo(entity) <= 36.0).isPresent();
   }
}
