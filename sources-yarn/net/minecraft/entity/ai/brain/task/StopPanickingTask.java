package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;

public class StopPanickingTask extends Task<VillagerEntity> {
   public StopPanickingTask() {
      super(ImmutableMap.of());
   }

   protected void run(ServerWorld arg, VillagerEntity arg2, long l) {
      boolean bl = PanicTask.wasHurt(arg2) || PanicTask.isHostileNearby(arg2) || wasHurtByNearbyEntity(arg2);
      if (!bl) {
         arg2.getBrain().forget(MemoryModuleType.HURT_BY);
         arg2.getBrain().forget(MemoryModuleType.HURT_BY_ENTITY);
         arg2.getBrain().refreshActivities(arg.getTimeOfDay(), arg.getTime());
      }
   }

   private static boolean wasHurtByNearbyEntity(VillagerEntity entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.HURT_BY_ENTITY).filter(arg2 -> arg2.squaredDistanceTo(entity) <= 36.0).isPresent();
   }
}
