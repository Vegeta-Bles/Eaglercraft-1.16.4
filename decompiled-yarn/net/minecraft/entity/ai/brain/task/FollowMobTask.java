package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.Predicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.server.world.ServerWorld;

public class FollowMobTask extends Task<LivingEntity> {
   private final Predicate<LivingEntity> predicate;
   private final float maxDistanceSquared;

   public FollowMobTask(SpawnGroup group, float maxDistance) {
      this(_snowmanx -> group.equals(_snowmanx.getType().getSpawnGroup()), maxDistance);
   }

   public FollowMobTask(EntityType<?> type, float maxDistance) {
      this(_snowmanx -> type.equals(_snowmanx.getType()), maxDistance);
   }

   public FollowMobTask(float maxDistance) {
      this(_snowman -> true, maxDistance);
   }

   public FollowMobTask(Predicate<LivingEntity> predicate, float maxDistance) {
      super(ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.VISIBLE_MOBS, MemoryModuleState.VALUE_PRESENT));
      this.predicate = predicate;
      this.maxDistanceSquared = maxDistance * maxDistance;
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      return entity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get().stream().anyMatch(this.predicate);
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Brain<?> _snowman = entity.getBrain();
      _snowman.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS)
         .ifPresent(
            _snowmanxx -> _snowmanxx.stream()
                  .filter(this.predicate)
                  .filter(_snowmanxxx -> _snowmanxxx.squaredDistanceTo(entity) <= (double)this.maxDistanceSquared)
                  .findFirst()
                  .ifPresent(_snowmanxxxx -> _snowman.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(_snowmanxxxx, true)))
         );
   }
}
