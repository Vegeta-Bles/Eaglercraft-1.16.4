package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;

public class BreedTask extends Task<AnimalEntity> {
   private final EntityType<? extends AnimalEntity> targetType;
   private final float field_23129;
   private long breedTime;

   public BreedTask(EntityType<? extends AnimalEntity> targetType, float _snowman) {
      super(
         ImmutableMap.of(
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleState.VALUE_PRESENT,
            MemoryModuleType.BREED_TARGET,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleState.REGISTERED
         ),
         325
      );
      this.targetType = targetType;
      this.field_23129 = _snowman;
   }

   protected boolean shouldRun(ServerWorld _snowman, AnimalEntity _snowman) {
      return _snowman.isInLove() && this.findBreedTarget(_snowman).isPresent();
   }

   protected void run(ServerWorld _snowman, AnimalEntity _snowman, long _snowman) {
      AnimalEntity _snowmanxxx = this.findBreedTarget(_snowman).get();
      _snowman.getBrain().remember(MemoryModuleType.BREED_TARGET, _snowmanxxx);
      _snowmanxxx.getBrain().remember(MemoryModuleType.BREED_TARGET, _snowman);
      LookTargetUtil.lookAtAndWalkTowardsEachOther(_snowman, _snowmanxxx, this.field_23129);
      int _snowmanxxxx = 275 + _snowman.getRandom().nextInt(50);
      this.breedTime = _snowman + (long)_snowmanxxxx;
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, AnimalEntity _snowman, long _snowman) {
      if (!this.hasBreedTarget(_snowman)) {
         return false;
      } else {
         AnimalEntity _snowmanxxx = this.getBreedTarget(_snowman);
         return _snowmanxxx.isAlive() && _snowman.canBreedWith(_snowmanxxx) && LookTargetUtil.canSee(_snowman.getBrain(), _snowmanxxx) && _snowman <= this.breedTime;
      }
   }

   protected void keepRunning(ServerWorld _snowman, AnimalEntity _snowman, long _snowman) {
      AnimalEntity _snowmanxxx = this.getBreedTarget(_snowman);
      LookTargetUtil.lookAtAndWalkTowardsEachOther(_snowman, _snowmanxxx, this.field_23129);
      if (_snowman.isInRange(_snowmanxxx, 3.0)) {
         if (_snowman >= this.breedTime) {
            _snowman.breed(_snowman, _snowmanxxx);
            _snowman.getBrain().forget(MemoryModuleType.BREED_TARGET);
            _snowmanxxx.getBrain().forget(MemoryModuleType.BREED_TARGET);
         }
      }
   }

   protected void finishRunning(ServerWorld _snowman, AnimalEntity _snowman, long _snowman) {
      _snowman.getBrain().forget(MemoryModuleType.BREED_TARGET);
      _snowman.getBrain().forget(MemoryModuleType.WALK_TARGET);
      _snowman.getBrain().forget(MemoryModuleType.LOOK_TARGET);
      this.breedTime = 0L;
   }

   private AnimalEntity getBreedTarget(AnimalEntity animal) {
      return (AnimalEntity)animal.getBrain().getOptionalMemory(MemoryModuleType.BREED_TARGET).get();
   }

   private boolean hasBreedTarget(AnimalEntity animal) {
      Brain<?> _snowman = animal.getBrain();
      return _snowman.hasMemoryModule(MemoryModuleType.BREED_TARGET) && _snowman.getOptionalMemory(MemoryModuleType.BREED_TARGET).get().getType() == this.targetType;
   }

   private Optional<? extends AnimalEntity> findBreedTarget(AnimalEntity animal) {
      return animal.getBrain()
         .getOptionalMemory(MemoryModuleType.VISIBLE_MOBS)
         .get()
         .stream()
         .filter(_snowman -> _snowman.getType() == this.targetType)
         .map(_snowman -> (AnimalEntity)_snowman)
         .filter(animal::canBreedWith)
         .findFirst();
   }
}
