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

   protected boolean shouldRun(ServerWorld _snowman, VillagerEntity _snowman) {
      return !_snowman.isNearOccupiedPointOfInterest(_snowman.getBlockPos());
   }

   protected void run(ServerWorld _snowman, VillagerEntity _snowman, long _snowman) {
      PointOfInterestStorage _snowmanxxx = _snowman.getPointOfInterestStorage();
      int _snowmanxxxx = _snowmanxxx.getDistanceFromNearestOccupied(ChunkSectionPos.from(_snowman.getBlockPos()));
      Vec3d _snowmanxxxxx = null;

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 5; _snowmanxxxxxx++) {
         Vec3d _snowmanxxxxxxx = TargetFinder.findGroundTarget(_snowman, 15, 7, _snowmanxxxxxxxx -> (double)(-_snowman.getOccupiedPointOfInterestDistance(ChunkSectionPos.from(_snowmanxxxxxxxx))));
         if (_snowmanxxxxxxx != null) {
            int _snowmanxxxxxxxx = _snowmanxxx.getDistanceFromNearestOccupied(ChunkSectionPos.from(new BlockPos(_snowmanxxxxxxx)));
            if (_snowmanxxxxxxxx < _snowmanxxxx) {
               _snowmanxxxxx = _snowmanxxxxxxx;
               break;
            }

            if (_snowmanxxxxxxxx == _snowmanxxxx) {
               _snowmanxxxxx = _snowmanxxxxxxx;
            }
         }
      }

      if (_snowmanxxxxx != null) {
         _snowman.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(_snowmanxxxxx, this.speed, this.completionRange));
      }
   }
}
