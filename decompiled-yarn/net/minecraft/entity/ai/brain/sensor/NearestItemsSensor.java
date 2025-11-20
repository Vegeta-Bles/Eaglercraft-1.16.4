package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;

public class NearestItemsSensor extends Sensor<MobEntity> {
   public NearestItemsSensor() {
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
   }

   protected void sense(ServerWorld _snowman, MobEntity _snowman) {
      Brain<?> _snowmanxx = _snowman.getBrain();
      List<ItemEntity> _snowmanxxx = _snowman.getEntitiesByClass(ItemEntity.class, _snowman.getBoundingBox().expand(8.0, 4.0, 8.0), _snowmanxxxx -> true);
      _snowmanxxx.sort(Comparator.comparingDouble(_snowman::squaredDistanceTo));
      Optional<ItemEntity> _snowmanxxxx = _snowmanxxx.stream()
         .filter(_snowmanxxxxx -> _snowman.canGather(_snowmanxxxxx.getStack()))
         .filter(_snowmanxxxxx -> _snowmanxxxxx.isInRange(_snowman, 9.0))
         .filter(_snowman::canSee)
         .findFirst();
      _snowmanxx.remember(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, _snowmanxxxx);
   }
}
