package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.server.world.ServerWorld;

public class FindEntityTask<E extends LivingEntity, T extends LivingEntity> extends Task<E> {
   private final int completionRange;
   private final float speed;
   private final EntityType<? extends T> entityType;
   private final int maxSquaredDistance;
   private final Predicate<T> predicate;
   private final Predicate<E> shouldRunPredicate;
   private final MemoryModuleType<T> targetModule;

   public FindEntityTask(
      EntityType<? extends T> entityType,
      int maxDistance,
      Predicate<E> shouldRunPredicate,
      Predicate<T> predicate,
      MemoryModuleType<T> targetModule,
      float speed,
      int completionRange
   ) {
      super(
         ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleState.VALUE_PRESENT
         )
      );
      this.entityType = entityType;
      this.speed = speed;
      this.maxSquaredDistance = maxDistance * maxDistance;
      this.completionRange = completionRange;
      this.predicate = predicate;
      this.shouldRunPredicate = shouldRunPredicate;
      this.targetModule = targetModule;
   }

   public static <T extends LivingEntity> FindEntityTask<LivingEntity, T> create(
      EntityType<? extends T> entityType, int maxDistance, MemoryModuleType<T> targetModule, float speed, int completionRange
   ) {
      return new FindEntityTask<>(entityType, maxDistance, _snowman -> true, _snowman -> true, targetModule, speed, completionRange);
   }

   @Override
   protected boolean shouldRun(ServerWorld world, E entity) {
      return this.shouldRunPredicate.test(entity) && this.method_24582(entity);
   }

   private boolean method_24582(E _snowman) {
      List<LivingEntity> _snowmanx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).get();
      return _snowmanx.stream().anyMatch(this::method_24583);
   }

   private boolean method_24583(LivingEntity _snowman) {
      return this.entityType.equals(_snowman.getType()) && this.predicate.test((T)_snowman);
   }

   @Override
   protected void run(ServerWorld world, E entity, long time) {
      Brain<?> _snowman = entity.getBrain();
      _snowman.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS)
         .ifPresent(
            _snowmanxx -> _snowmanxx.stream()
                  .filter(_snowmanxxx -> this.entityType.equals(_snowmanxxx.getType()))
                  .map(_snowmanxxx -> _snowmanxxx)
                  .filter(_snowmanxxx -> _snowmanxxx.squaredDistanceTo(entity) <= (double)this.maxSquaredDistance)
                  .filter(this.predicate)
                  .findFirst()
                  .ifPresent(_snowmanxxx -> {
                     _snowman.remember(this.targetModule, (T)_snowmanxxx);
                     _snowman.remember(MemoryModuleType.LOOK_TARGET, new EntityLookTarget(_snowmanxxx, true));
                     _snowman.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityLookTarget(_snowmanxxx, false), this.speed, this.completionRange));
                  })
         );
   }
}
