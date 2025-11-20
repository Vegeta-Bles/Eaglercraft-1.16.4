package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;

public class MeetVillagerTask extends Task<LivingEntity> {
   public MeetVillagerTask() {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.MEETING_POINT,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleState.VALUE_ABSENT
         )
      );
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      Brain<?> _snowman = entity.getBrain();
      Optional<GlobalPos> _snowmanx = _snowman.getOptionalMemory(MemoryModuleType.MEETING_POINT);
      return world.getRandom().nextInt(100) == 0
         && _snowmanx.isPresent()
         && world.getRegistryKey() == _snowmanx.get().getDimension()
         && _snowmanx.get().getPos().isWithinDistance(entity.getPos(), 4.0)
         && _snowman.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get().stream().anyMatch(_snowmanxx -> EntityType.VILLAGER.equals(_snowmanxx.getType()));
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Brain<?> _snowman = entity.getBrain();
      _snowman.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS)
         .ifPresent(
            _snowmanxx -> _snowmanxx.stream()
                  .filter(_snowmanxxx -> EntityType.VILLAGER.equals(_snowmanxxx.getType()))
                  .filter(_snowmanxxxx -> _snowmanxxxx.squaredDistanceTo(entity) <= 32.0)
                  .findFirst()
                  .ifPresent(_snowmanxxxx -> {
                     _snowman.remember(MemoryModuleType.INTERACTION_TARGET, _snowmanxxxx);
                     _snowman.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(_snowmanxxxx, true));
                     _snowman.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityLookTarget(_snowmanxxxx, false), 0.3F, 1));
                  })
         );
   }
}
