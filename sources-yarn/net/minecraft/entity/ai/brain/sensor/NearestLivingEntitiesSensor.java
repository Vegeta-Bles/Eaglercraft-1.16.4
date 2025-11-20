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
      Box lv = entity.getBoundingBox().expand(16.0, 16.0, 16.0);
      List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, lv, arg2 -> arg2 != entity && arg2.isAlive());
      list.sort(Comparator.comparingDouble(entity::squaredDistanceTo));
      Brain<?> lv2 = entity.getBrain();
      lv2.remember(MemoryModuleType.MOBS, list);
      lv2.remember(MemoryModuleType.VISIBLE_MOBS, list.stream().filter(arg2 -> method_30954(entity, arg2)).collect(Collectors.toList()));
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.MOBS, MemoryModuleType.VISIBLE_MOBS);
   }
}
