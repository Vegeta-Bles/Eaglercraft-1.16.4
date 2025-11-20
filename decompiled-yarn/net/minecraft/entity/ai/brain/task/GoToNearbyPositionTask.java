package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.GlobalPos;

public class GoToNearbyPositionTask extends Task<PathAwareEntity> {
   private final MemoryModuleType<GlobalPos> memoryModuleType;
   private final int completionRange;
   private final int maxDistance;
   private final float field_25753;
   private long nextRunTime;

   public GoToNearbyPositionTask(MemoryModuleType<GlobalPos> memoryModuleType, float _snowman, int _snowman, int _snowman) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED, memoryModuleType, MemoryModuleState.VALUE_PRESENT));
      this.memoryModuleType = memoryModuleType;
      this.field_25753 = _snowman;
      this.completionRange = _snowman;
      this.maxDistance = _snowman;
   }

   protected boolean shouldRun(ServerWorld _snowman, PathAwareEntity _snowman) {
      Optional<GlobalPos> _snowmanxx = _snowman.getBrain().getOptionalMemory(this.memoryModuleType);
      return _snowmanxx.isPresent() && _snowman.getRegistryKey() == _snowmanxx.get().getDimension() && _snowmanxx.get().getPos().isWithinDistance(_snowman.getPos(), (double)this.maxDistance);
   }

   protected void run(ServerWorld _snowman, PathAwareEntity _snowman, long _snowman) {
      if (_snowman > this.nextRunTime) {
         Brain<?> _snowmanxxx = _snowman.getBrain();
         Optional<GlobalPos> _snowmanxxxx = _snowmanxxx.getOptionalMemory(this.memoryModuleType);
         _snowmanxxxx.ifPresent(_snowmanxxxxx -> _snowman.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanxxxxx.getPos(), this.field_25753, this.completionRange)));
         this.nextRunTime = _snowman + 80L;
      }
   }
}
