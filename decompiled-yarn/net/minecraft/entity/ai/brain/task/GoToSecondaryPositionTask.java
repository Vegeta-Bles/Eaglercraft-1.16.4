package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;

public class GoToSecondaryPositionTask extends Task<VillagerEntity> {
   private final MemoryModuleType<List<GlobalPos>> secondaryPositions;
   private final MemoryModuleType<GlobalPos> primaryPosition;
   private final float speed;
   private final int completionRange;
   private final int primaryPositionActivationDistance;
   private long nextRunTime;
   @Nullable
   private GlobalPos chosenPosition;

   public GoToSecondaryPositionTask(
      MemoryModuleType<List<GlobalPos>> secondaryPositions,
      float speed,
      int completionRange,
      int primaryPositionActivationDistance,
      MemoryModuleType<GlobalPos> primaryPosition
   ) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.REGISTERED,
            secondaryPositions,
            MemoryModuleState.VALUE_PRESENT,
            primaryPosition,
            MemoryModuleState.VALUE_PRESENT
         )
      );
      this.secondaryPositions = secondaryPositions;
      this.speed = speed;
      this.completionRange = completionRange;
      this.primaryPositionActivationDistance = primaryPositionActivationDistance;
      this.primaryPosition = primaryPosition;
   }

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      Optional<List<GlobalPos>> _snowmanxx = _snowman.getBrain().getOptionalMemory(this.secondaryPositions);
      Optional<GlobalPos> _snowmanxxx = _snowman.getBrain().getOptionalMemory(this.primaryPosition);
      if (_snowmanxx.isPresent() && _snowmanxxx.isPresent()) {
         List<GlobalPos> _snowmanxxxx = _snowmanxx.get();
         if (!_snowmanxxxx.isEmpty()) {
            this.chosenPosition = _snowmanxxxx.get(_snowman.getRandom().nextInt(_snowmanxxxx.size()));
            return this.chosenPosition != null
               && _snowman.getRegistryKey() == this.chosenPosition.getDimension()
               && _snowmanxxx.get().getPos().isWithinDistance(_snowman.getPos(), (double)this.primaryPositionActivationDistance);
         }
      }

      return false;
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      if (_snowman > this.nextRunTime && this.chosenPosition != null) {
         _snowman.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(this.chosenPosition.getPos(), this.speed, this.completionRange));
         this.nextRunTime = _snowman + 100L;
      }
   }
}
