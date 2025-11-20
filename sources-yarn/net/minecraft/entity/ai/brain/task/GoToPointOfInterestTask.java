package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.WalkTarget;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.poi.PointOfInterestStorage;

public class GoToPointOfInterestTask extends Task<VillagerEntity> {
   private final float speed;
   private final int completionRange;

   public GoToPointOfInterestTask(float speed, int completionRange) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
      this.speed = speed;
      this.completionRange = completionRange;
   }

   protected boolean shouldRun(ServerWorld arg, VillagerEntity arg2) {
      return !arg.isNearOccupiedPointOfInterest(arg2.getBlockPos());
   }

   protected void run(ServerWorld arg, VillagerEntity arg2, long l) {
      PointOfInterestStorage lv = arg.getPointOfInterestStorage();
      int i = lv.getDistanceFromNearestOccupied(ChunkSectionPos.from(arg2.getBlockPos()));
      Vec3d lv2 = null;

      for (int j = 0; j < 5; j++) {
         Vec3d lv3 = TargetFinder.findGroundTarget(arg2, 15, 7, arg2x -> (double)(-arg.getOccupiedPointOfInterestDistance(ChunkSectionPos.from(arg2x))));
         if (lv3 != null) {
            int k = lv.getDistanceFromNearestOccupied(ChunkSectionPos.from(new BlockPos(lv3)));
            if (k < i) {
               lv2 = lv3;
               break;
            }

            if (k == i) {
               lv2 = lv3;
            }
         }
      }

      if (lv2 != null) {
         arg2.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(lv2, this.speed, this.completionRange));
      }
   }
}
