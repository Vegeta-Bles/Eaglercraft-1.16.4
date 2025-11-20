package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.BiPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;

public class RidingTask<E extends LivingEntity, T extends Entity> extends Task<E> {
   private final int range;
   private final BiPredicate<E, Entity> alternativeRideCondition;

   public RidingTask(int range, BiPredicate<E, Entity> alternativeRideCondition) {
      super(ImmutableMap.of(MemoryModuleType.RIDE_TARGET, MemoryModuleState.REGISTERED));
      this.range = range;
      this.alternativeRideCondition = alternativeRideCondition;
   }

   @Override
   protected boolean shouldRun(ServerWorld world, E entity) {
      Entity lv = entity.getVehicle();
      Entity lv2 = entity.getBrain().getOptionalMemory(MemoryModuleType.RIDE_TARGET).orElse(null);
      if (lv == null && lv2 == null) {
         return false;
      } else {
         Entity lv3 = lv == null ? lv2 : lv;
         return !this.canRideTarget(entity, lv3) || this.alternativeRideCondition.test(entity, lv3);
      }
   }

   private boolean canRideTarget(E entity, Entity target) {
      return target.isAlive() && target.isInRange(entity, (double)this.range) && target.world == entity.world;
   }

   @Override
   protected void run(ServerWorld world, E entity, long time) {
      entity.stopRiding();
      entity.getBrain().forget(MemoryModuleType.RIDE_TARGET);
   }
}
