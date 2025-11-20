package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.function.Function;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class GoToRememberedPositionTask<T> extends Task<PathAwareEntity> {
   private final MemoryModuleType<T> entityMemory;
   private final float speed;
   private final int range;
   private final Function<T, Vec3d> posRetriever;

   public GoToRememberedPositionTask(MemoryModuleType<T> memoryType, float speed, int range, boolean requiresWalkTarget, Function<T, Vec3d> posRetriever) {
      super(
         ImmutableMap.of(
            MemoryModuleType.WALK_TARGET,
            requiresWalkTarget ? MemoryModuleState.REGISTERED : MemoryModuleState.VALUE_ABSENT,
            memoryType,
            MemoryModuleState.VALUE_PRESENT
         )
      );
      this.entityMemory = memoryType;
      this.speed = speed;
      this.range = range;
      this.posRetriever = posRetriever;
   }

   public static GoToRememberedPositionTask<BlockPos> toBlock(MemoryModuleType<BlockPos> memoryType, float speed, int range, boolean requiresWalkTarget) {
      return new GoToRememberedPositionTask<>(memoryType, speed, range, requiresWalkTarget, Vec3d::ofBottomCenter);
   }

   public static GoToRememberedPositionTask<? extends Entity> toEntity(
      MemoryModuleType<? extends Entity> memoryType, float speed, int range, boolean requiresWalkTarget
   ) {
      return new GoToRememberedPositionTask<>(memoryType, speed, range, requiresWalkTarget, Entity::getPos);
   }

   protected boolean shouldRun(ServerWorld _snowman, PathAwareEntity _snowman) {
      return this.isWalkTargetPresentAndFar(_snowman) ? false : _snowman.getPos().isInRange(this.getPos(_snowman), (double)this.range);
   }

   private Vec3d getPos(PathAwareEntity entity) {
      return this.posRetriever.apply(entity.getBrain().getOptionalMemory(this.entityMemory).get());
   }

   private boolean isWalkTargetPresentAndFar(PathAwareEntity _snowman) {
      if (!_snowman.getBrain().hasMemoryModule(MemoryModuleType.WALK_TARGET)) {
         return false;
      } else {
         WalkTarget _snowmanx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET).get();
         if (_snowmanx.getSpeed() != this.speed) {
            return false;
         } else {
            Vec3d _snowmanxx = _snowmanx.getLookTarget().getPos().subtract(_snowman.getPos());
            Vec3d _snowmanxxx = this.getPos(_snowman).subtract(_snowman.getPos());
            return _snowmanxx.dotProduct(_snowmanxxx) < 0.0;
         }
      }
   }

   protected void run(ServerWorld _snowman, PathAwareEntity _snowman, long _snowman) {
      setWalkTarget(_snowman, this.getPos(_snowman), this.speed);
   }

   private static void setWalkTarget(PathAwareEntity entity, Vec3d pos, float speed) {
      for (int _snowman = 0; _snowman < 10; _snowman++) {
         Vec3d _snowmanx = TargetFinder.findGroundTargetAwayFrom(entity, 16, 7, pos);
         if (_snowmanx != null) {
            entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanx, speed, 0));
            return;
         }
      }
   }
}
