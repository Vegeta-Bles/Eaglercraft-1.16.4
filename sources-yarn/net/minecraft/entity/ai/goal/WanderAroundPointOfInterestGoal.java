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
   public WanderAroundPointOfInterestGoal(PathAwareEntity arg, double d, boolean bl) {
      super(arg, d, 10, bl);
   }

   @Override
   public boolean canStart() {
      ServerWorld lv = (ServerWorld)this.mob.world;
      BlockPos lv2 = this.mob.getBlockPos();
      return lv.isNearOccupiedPointOfInterest(lv2) ? false : super.canStart();
   }

   @Nullable
   @Override
   protected Vec3d getWanderTarget() {
      ServerWorld lv = (ServerWorld)this.mob.world;
      BlockPos lv2 = this.mob.getBlockPos();
      ChunkSectionPos lv3 = ChunkSectionPos.from(lv2);
      ChunkSectionPos lv4 = LookTargetUtil.getPosClosestToOccupiedPointOfInterest(lv, lv3, 2);
      return lv4 != lv3 ? TargetFinder.findTargetTowards(this.mob, 10, 7, Vec3d.ofBottomCenter(lv4.getCenterPos())) : null;
   }
}
