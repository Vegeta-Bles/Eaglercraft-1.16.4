package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;

public class GolemLastSeenSensor extends Sensor<LivingEntity> {
   public GolemLastSeenSensor() {
      this(200);
   }

   public GolemLastSeenSensor(int _snowman) {
      super(_snowman);
   }

   @Override
   protected void sense(ServerWorld world, LivingEntity entity) {
      senseIronGolem(entity);
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.MOBS);
   }

   public static void senseIronGolem(LivingEntity _snowman) {
      Optional<List<LivingEntity>> _snowmanx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.MOBS);
      if (_snowmanx.isPresent()) {
         boolean _snowmanxx = _snowmanx.get().stream().anyMatch(_snowmanxxx -> _snowmanxxx.getType().equals(EntityType.IRON_GOLEM));
         if (_snowmanxx) {
            method_30233(_snowman);
         }
      }
   }

   public static void method_30233(LivingEntity _snowman) {
      _snowman.getBrain().remember(MemoryModuleType.GOLEM_DETECTED_RECENTLY, true, 600L);
   }
}
