package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;

public class NearestPlayersSensor extends Sensor<LivingEntity> {
   public NearestPlayersSensor() {
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
   }

   @Override
   protected void sense(ServerWorld world, LivingEntity entity) {
      List<PlayerEntity> _snowman = world.getPlayers()
         .stream()
         .filter(EntityPredicates.EXCEPT_SPECTATOR)
         .filter(_snowmanx -> entity.isInRange(_snowmanx, 16.0))
         .sorted(Comparator.comparingDouble(entity::squaredDistanceTo))
         .collect(Collectors.toList());
      Brain<?> _snowmanx = entity.getBrain();
      _snowmanx.remember(MemoryModuleType.NEAREST_PLAYERS, _snowman);
      List<PlayerEntity> _snowmanxx = _snowman.stream().filter(_snowmanxxx -> method_30954(entity, _snowmanxxx)).collect(Collectors.toList());
      _snowmanx.remember(MemoryModuleType.NEAREST_VISIBLE_PLAYER, _snowmanxx.isEmpty() ? null : _snowmanxx.get(0));
      Optional<PlayerEntity> _snowmanxxx = _snowmanxx.stream().filter(EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL).findFirst();
      _snowmanx.remember(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, _snowmanxxx);
   }
}
