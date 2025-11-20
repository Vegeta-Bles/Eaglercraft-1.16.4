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

   public BreedTask(EntityType<? extends AnimalEntity> targetType, float f) {
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
      this.field_23129 = f;
   }

   protected boolean shouldRun(ServerWorld arg, AnimalEntity arg2) {
      return arg2.isInLove() && this.findBreedTarget(arg2).isPresent();
   }

   protected void run(ServerWorld arg, AnimalEntity arg2, long l) {
      AnimalEntity lv = this.findBreedTarget(arg2).get();
      arg2.getBrain().remember(MemoryModuleType.BREED_TARGET, lv);
      lv.getBrain().remember(MemoryModuleType.BREED_TARGET, arg2);
      LookTargetUtil.lookAtAndWalkTowardsEachOther(arg2, lv, this.field_23129);
      int i = 275 + arg2.getRandom().nextInt(50);
      this.breedTime = l + (long)i;
   }

   protected boolean shouldKeepRunning(ServerWorld arg, AnimalEntity arg2, long l) {
      if (!this.hasBreedTarget(arg2)) {
         return false;
      } else {
         AnimalEntity lv = this.getBreedTarget(arg2);
         return lv.isAlive() && arg2.canBreedWith(lv) && LookTargetUtil.canSee(arg2.getBrain(), lv) && l <= this.breedTime;
      }
   }

   protected void keepRunning(ServerWorld arg, AnimalEntity arg2, long l) {
      AnimalEntity lv = this.getBreedTarget(arg2);
      LookTargetUtil.lookAtAndWalkTowardsEachOther(arg2, lv, this.field_23129);
      if (arg2.isInRange(lv, 3.0)) {
         if (l >= this.breedTime) {
            arg2.breed(arg, lv);
            arg2.getBrain().forget(MemoryModuleType.BREED_TARGET);
            lv.getBrain().forget(MemoryModuleType.BREED_TARGET);
         }
      }
   }

   protected void finishRunning(ServerWorld arg, AnimalEntity arg2, long l) {
      arg2.getBrain().forget(MemoryModuleType.BREED_TARGET);
      arg2.getBrain().forget(MemoryModuleType.WALK_TARGET);
      arg2.getBrain().forget(MemoryModuleType.LOOK_TARGET);
      this.breedTime = 0L;
   }

   private AnimalEntity getBreedTarget(AnimalEntity animal) {
      return (AnimalEntity)animal.getBrain().getOptionalMemory(MemoryModuleType.BREED_TARGET).get();
   }

   private boolean hasBreedTarget(AnimalEntity animal) {
      Brain<?> lv = animal.getBrain();
      return lv.hasMemoryModule(MemoryModuleType.BREED_TARGET) && lv.getOptionalMemory(MemoryModuleType.BREED_TARGET).get().getType() == this.targetType;
   }

   private Optional<? extends AnimalEntity> findBreedTarget(AnimalEntity animal) {
      return animal.getBrain()
         .getOptionalMemory(MemoryModuleType.VISIBLE_MOBS)
         .get()
         .stream()
         .filter(arg -> arg.getType() == this.targetType)
         .map(arg -> (AnimalEntity)arg)
         .filter(animal::canBreedWith)
         .findFirst();
   }
}
