package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class WanderAroundTask extends Task<MobEntity> {
   private int pathUpdateCountdownTicks;
   @Nullable
   private Path path;
   @Nullable
   private BlockPos lookTargetPos;
   private float speed;

   public WanderAroundTask() {
      this(150, 250);
   }

   public WanderAroundTask(int _snowman, int _snowman) {
      super(
         ImmutableMap.of(
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.PATH,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.VALUE_PRESENT
         ),
         _snowman,
         _snowman
      );
   }

   protected boolean shouldRun(ServerWorld _snowman, MobEntity _snowman) {
      if (this.pathUpdateCountdownTicks > 0) {
         this.pathUpdateCountdownTicks--;
         return false;
      } else {
         Brain<?> _snowmanxx = _snowman.getBrain();
         WalkTarget _snowmanxxx = _snowmanxx.getOptionalMemory(MemoryModuleType.WALK_TARGET).get();
         boolean _snowmanxxxx = this.hasReached(_snowman, _snowmanxxx);
         if (!_snowmanxxxx && this.hasFinishedPath(_snowman, _snowmanxxx, _snowman.getTime())) {
            this.lookTargetPos = _snowmanxxx.getLookTarget().getBlockPos();
            return true;
         } else {
            _snowmanxx.forget(MemoryModuleType.WALK_TARGET);
            if (_snowmanxxxx) {
               _snowmanxx.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            }

            return false;
         }
      }
   }

   protected boolean shouldKeepRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      if (this.path != null && this.lookTargetPos != null) {
         Optional<WalkTarget> _snowmanxxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET);
         EntityNavigation _snowmanxxxx = _snowman.getNavigation();
         return !_snowmanxxxx.isIdle() && _snowmanxxx.isPresent() && !this.hasReached(_snowman, _snowmanxxx.get());
      } else {
         return false;
      }
   }

   protected void finishRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      if (_snowman.getBrain().hasMemoryModule(MemoryModuleType.WALK_TARGET)
         && !this.hasReached(_snowman, _snowman.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET).get())
         && _snowman.getNavigation().isNearPathStartPos()) {
         this.pathUpdateCountdownTicks = _snowman.getRandom().nextInt(40);
      }

      _snowman.getNavigation().stop();
      _snowman.getBrain().forget(MemoryModuleType.WALK_TARGET);
      _snowman.getBrain().forget(MemoryModuleType.PATH);
      this.path = null;
   }

   protected void run(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      _snowman.getBrain().remember(MemoryModuleType.PATH, this.path);
      _snowman.getNavigation().startMovingAlong(this.path, (double)this.speed);
   }

   protected void keepRunning(ServerWorld _snowman, MobEntity _snowman, long _snowman) {
      Path _snowmanxxx = _snowman.getNavigation().getCurrentPath();
      Brain<?> _snowmanxxxx = _snowman.getBrain();
      if (this.path != _snowmanxxx) {
         this.path = _snowmanxxx;
         _snowmanxxxx.remember(MemoryModuleType.PATH, _snowmanxxx);
      }

      if (_snowmanxxx != null && this.lookTargetPos != null) {
         WalkTarget _snowmanxxxxx = _snowmanxxxx.getOptionalMemory(MemoryModuleType.WALK_TARGET).get();
         if (_snowmanxxxxx.getLookTarget().getBlockPos().getSquaredDistance(this.lookTargetPos) > 4.0 && this.hasFinishedPath(_snowman, _snowmanxxxxx, _snowman.getTime())) {
            this.lookTargetPos = _snowmanxxxxx.getLookTarget().getBlockPos();
            this.run(_snowman, _snowman, _snowman);
         }
      }
   }

   private boolean hasFinishedPath(MobEntity _snowman, WalkTarget _snowman, long time) {
      BlockPos _snowmanxx = _snowman.getLookTarget().getBlockPos();
      this.path = _snowman.getNavigation().findPathTo(_snowmanxx, 0);
      this.speed = _snowman.getSpeed();
      Brain<?> _snowmanxxx = _snowman.getBrain();
      if (this.hasReached(_snowman, _snowman)) {
         _snowmanxxx.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      } else {
         boolean _snowmanxxxx = this.path != null && this.path.reachesTarget();
         if (_snowmanxxxx) {
            _snowmanxxx.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
         } else if (!_snowmanxxx.hasMemoryModule(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
            _snowmanxxx.remember(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
         }

         if (this.path != null) {
            return true;
         }

         Vec3d _snowmanxxxxx = TargetFinder.findTargetTowards((PathAwareEntity)_snowman, 10, 7, Vec3d.ofBottomCenter(_snowmanxx));
         if (_snowmanxxxxx != null) {
            this.path = _snowman.getNavigation().findPathTo(_snowmanxxxxx.x, _snowmanxxxxx.y, _snowmanxxxxx.z, 0);
            return this.path != null;
         }
      }

      return false;
   }

   private boolean hasReached(MobEntity entity, WalkTarget walkTarget) {
      return walkTarget.getLookTarget().getBlockPos().getManhattanDistance(entity.getBlockPos()) <= walkTarget.getCompletionRange();
   }
}
