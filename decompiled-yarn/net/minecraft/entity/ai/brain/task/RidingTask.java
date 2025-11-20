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
      Entity _snowman = entity.getVehicle();
      Entity _snowmanx = entity.getBrain().getOptionalMemory(MemoryModuleType.RIDE_TARGET).orElse(null);
      if (_snowman == null && _snowmanx == null) {
         return false;
      } else {
         Entity _snowmanxx = _snowman == null ? _snowmanx : _snowman;
         return !this.canRideTarget(entity, _snowmanxx) || this.alternativeRideCondition.test(entity, _snowmanxx);
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
