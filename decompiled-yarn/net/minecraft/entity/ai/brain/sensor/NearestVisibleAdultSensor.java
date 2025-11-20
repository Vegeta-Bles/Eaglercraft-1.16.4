package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;

public class NearestVisibleAdultSensor extends Sensor<PassiveEntity> {
   public NearestVisibleAdultSensor() {
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.VISIBLE_MOBS);
   }

   protected void sense(ServerWorld _snowman, PassiveEntity _snowman) {
      _snowman.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).ifPresent(_snowmanxx -> this.findNearestVisibleAdult(_snowman, _snowmanxx));
   }

   private void findNearestVisibleAdult(PassiveEntity entity, List<LivingEntity> visibleMobs) {
      Optional<PassiveEntity> _snowman = visibleMobs.stream()
         .filter(_snowmanx -> _snowmanx.getType() == entity.getType())
         .map(_snowmanx -> (PassiveEntity)_snowmanx)
         .filter(_snowmanx -> !_snowmanx.isBaby())
         .findFirst();
      entity.getBrain().remember(MemoryModuleType.NEAREST_VISIBLE_ADULT, _snowman);
   }
}
