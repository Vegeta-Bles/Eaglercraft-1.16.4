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

   protected boolean shouldRun(ServerWorld arg, PathAwareEntity arg2) {
      return this.isWalkTargetPresentAndFar(arg2) ? false : arg2.getPos().isInRange(this.getPos(arg2), (double)this.range);
   }

   private Vec3d getPos(PathAwareEntity entity) {
      return this.posRetriever.apply(entity.getBrain().getOptionalMemory(this.entityMemory).get());
   }

   private boolean isWalkTargetPresentAndFar(PathAwareEntity arg) {
      if (!arg.getBrain().hasMemoryModule(MemoryModuleType.WALK_TARGET)) {
         return false;
      } else {
         WalkTarget lv = arg.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET).get();
         if (lv.getSpeed() != this.speed) {
            return false;
         } else {
            Vec3d lv2 = lv.getLookTarget().getPos().subtract(arg.getPos());
            Vec3d lv3 = this.getPos(arg).subtract(arg.getPos());
            return lv2.dotProduct(lv3) < 0.0;
         }
      }
   }

   protected void run(ServerWorld arg, PathAwareEntity arg2, long l) {
      setWalkTarget(arg2, this.getPos(arg2), this.speed);
   }

   private static void setWalkTarget(PathAwareEntity entity, Vec3d pos, float speed) {
      for (int i = 0; i < 10; i++) {
         Vec3d lv = TargetFinder.findGroundTargetAwayFrom(entity, 16, 7, pos);
         if (lv != null) {
            entity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(lv, speed, 0));
            return;
         }
      }
   }
}
