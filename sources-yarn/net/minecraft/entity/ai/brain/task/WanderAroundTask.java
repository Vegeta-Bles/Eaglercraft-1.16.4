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

   public WanderAroundTask(int i, int j) {
      super(
         ImmutableMap.of(
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleState.REGISTERED,
            MemoryModuleType.PATH,
            MemoryModuleState.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleState.VALUE_PRESENT
         ),
         i,
         j
      );
   }

   protected boolean shouldRun(ServerWorld arg, MobEntity arg2) {
      if (this.pathUpdateCountdownTicks > 0) {
         this.pathUpdateCountdownTicks--;
         return false;
      } else {
         Brain<?> lv = arg2.getBrain();
         WalkTarget lv2 = lv.getOptionalMemory(MemoryModuleType.WALK_TARGET).get();
         boolean bl = this.hasReached(arg2, lv2);
         if (!bl && this.hasFinishedPath(arg2, lv2, arg.getTime())) {
            this.lookTargetPos = lv2.getLookTarget().getBlockPos();
            return true;
         } else {
            lv.forget(MemoryModuleType.WALK_TARGET);
            if (bl) {
               lv.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
            }

            return false;
         }
      }
   }

   protected boolean shouldKeepRunning(ServerWorld arg, MobEntity arg2, long l) {
      if (this.path != null && this.lookTargetPos != null) {
         Optional<WalkTarget> optional = arg2.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET);
         EntityNavigation lv = arg2.getNavigation();
         return !lv.isIdle() && optional.isPresent() && !this.hasReached(arg2, optional.get());
      } else {
         return false;
      }
   }

   protected void finishRunning(ServerWorld arg, MobEntity arg2, long l) {
      if (arg2.getBrain().hasMemoryModule(MemoryModuleType.WALK_TARGET)
         && !this.hasReached(arg2, arg2.getBrain().getOptionalMemory(MemoryModuleType.WALK_TARGET).get())
         && arg2.getNavigation().isNearPathStartPos()) {
         this.pathUpdateCountdownTicks = arg.getRandom().nextInt(40);
      }

      arg2.getNavigation().stop();
      arg2.getBrain().forget(MemoryModuleType.WALK_TARGET);
      arg2.getBrain().forget(MemoryModuleType.PATH);
      this.path = null;
   }

   protected void run(ServerWorld arg, MobEntity arg2, long l) {
      arg2.getBrain().remember(MemoryModuleType.PATH, this.path);
      arg2.getNavigation().startMovingAlong(this.path, (double)this.speed);
   }

   protected void keepRunning(ServerWorld arg, MobEntity arg2, long l) {
      Path lv = arg2.getNavigation().getCurrentPath();
      Brain<?> lv2 = arg2.getBrain();
      if (this.path != lv) {
         this.path = lv;
         lv2.remember(MemoryModuleType.PATH, lv);
      }

      if (lv != null && this.lookTargetPos != null) {
         WalkTarget lv3 = lv2.getOptionalMemory(MemoryModuleType.WALK_TARGET).get();
         if (lv3.getLookTarget().getBlockPos().getSquaredDistance(this.lookTargetPos) > 4.0 && this.hasFinishedPath(arg2, lv3, arg.getTime())) {
            this.lookTargetPos = lv3.getLookTarget().getBlockPos();
            this.run(arg, arg2, l);
         }
      }
   }

   private boolean hasFinishedPath(MobEntity arg, WalkTarget arg2, long time) {
      BlockPos lv = arg2.getLookTarget().getBlockPos();
      this.path = arg.getNavigation().findPathTo(lv, 0);
      this.speed = arg2.getSpeed();
      Brain<?> lv2 = arg.getBrain();
      if (this.hasReached(arg, arg2)) {
         lv2.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
      } else {
         boolean bl = this.path != null && this.path.reachesTarget();
         if (bl) {
            lv2.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
         } else if (!lv2.hasMemoryModule(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)) {
            lv2.remember(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, time);
         }

         if (this.path != null) {
            return true;
         }

         Vec3d lv3 = TargetFinder.findTargetTowards((PathAwareEntity)arg, 10, 7, Vec3d.ofBottomCenter(lv));
         if (lv3 != null) {
            this.path = arg.getNavigation().findPathTo(lv3.x, lv3.y, lv3.z, 0);
            return this.path != null;
         }
      }

      return false;
   }

   private boolean hasReached(MobEntity entity, WalkTarget walkTarget) {
      return walkTarget.getLookTarget().getBlockPos().getManhattanDistance(entity.getBlockPos()) <= walkTarget.getCompletionRange();
   }
}
