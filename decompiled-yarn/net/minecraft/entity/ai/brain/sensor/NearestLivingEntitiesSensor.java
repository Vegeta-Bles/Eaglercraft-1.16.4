package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

public class NearestLivingEntitiesSensor extends Sensor<LivingEntity> {
   public NearestLivingEntitiesSensor() {
   }

   @Override
   protected void sense(ServerWorld world, LivingEntity entity) {
      Box _snowman = entity.getBoundingBox().expand(16.0, 16.0, 16.0);
      List<LivingEntity> _snowmanx = world.getEntitiesByClass(LivingEntity.class, _snowman, _snowmanxx -> _snowmanxx != entity && _snowmanxx.isAlive());
      _snowmanx.sort(Comparator.comparingDouble(entity::squaredDistanceTo));
      Brain<?> _snowmanxx = entity.getBrain();
      _snowmanxx.remember(MemoryModuleType.MOBS, _snowmanx);
      _snowmanxx.remember(MemoryModuleType.VISIBLE_MOBS, _snowmanx.stream().filter(_snowmanxxx -> method_30954(entity, _snowmanxxx)).collect(Collectors.toList()));
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS);
   }
}
