package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;

public class WanderAroundPointOfInterestGoal extends WanderAroundGoal {
   public WanderAroundPointOfInterestGoal(PathAwareEntity _snowman, double _snowman, boolean _snowman) {
      super(_snowman, _snowman, 10, _snowman);
   }

   @Override
   public boolean canStart() {
      ServerWorld _snowman = (ServerWorld)this.mob.world;
      BlockPos _snowmanx = this.mob.getBlockPos();
      return _snowman.isNearOccupiedPointOfInterest(_snowmanx) ? false : super.canStart();
   }

   @Nullable
   @Override
   protected Vec3d getWanderTarget() {
      ServerWorld _snowman = (ServerWorld)this.mob.world;
      BlockPos _snowmanx = this.mob.getBlockPos();
      ChunkSectionPos _snowmanxx = ChunkSectionPos.from(_snowmanx);
      ChunkSectionPos _snowmanxxx = LookTargetUtil.getPosClosestToOccupiedPointOfInterest(_snowman, _snowmanxx, 2);
      return _snowmanxxx != _snowmanxx ? TargetFinder.findTargetTowards(this.mob, 10, 7, Vec3d.ofBottomCenter(_snowmanxxx.getCenterPos())) : null;
   }
}
