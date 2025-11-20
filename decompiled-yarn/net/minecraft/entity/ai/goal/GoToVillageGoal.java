package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;

public class GoToVillageGoal extends Goal {
   private final PathAwareEntity mob;
   private final int searchRange;
   @Nullable
   private BlockPos targetPosition;

   public GoToVillageGoal(PathAwareEntity mob, int searchRange) {
      this.mob = mob;
      this.searchRange = searchRange;
      this.setControls(EnumSet.of(Goal.Control.MOVE));
   }

   @Override
   public boolean canStart() {
      if (this.mob.hasPassengers()) {
         return false;
      } else if (this.mob.world.isDay()) {
         return false;
      } else if (this.mob.getRandom().nextInt(this.searchRange) != 0) {
         return false;
      } else {
         ServerWorld _snowman = (ServerWorld)this.mob.world;
         BlockPos _snowmanx = this.mob.getBlockPos();
         if (!_snowman.isNearOccupiedPointOfInterest(_snowmanx, 6)) {
            return false;
         } else {
            Vec3d _snowmanxx = TargetFinder.findGroundTarget(this.mob, 15, 7, _snowmanxxx -> (double)(-_snowman.getOccupiedPointOfInterestDistance(ChunkSectionPos.from(_snowmanxxx))));
            this.targetPosition = _snowmanxx == null ? null : new BlockPos(_snowmanxx);
            return this.targetPosition != null;
         }
      }
   }

   @Override
   public boolean shouldContinue() {
      return this.targetPosition != null && !this.mob.getNavigation().isIdle() && this.mob.getNavigation().getTargetPos().equals(this.targetPosition);
   }

   @Override
   public void tick() {
      if (this.targetPosition != null) {
         EntityNavigation _snowman = this.mob.getNavigation();
         if (_snowman.isIdle() && !this.targetPosition.isWithinDistance(this.mob.getPos(), 10.0)) {
            Vec3d _snowmanx = Vec3d.ofBottomCenter(this.targetPosition);
            Vec3d _snowmanxx = this.mob.getPos();
            Vec3d _snowmanxxx = _snowmanxx.subtract(_snowmanx);
            _snowmanx = _snowmanxxx.multiply(0.4).add(_snowmanx);
            Vec3d _snowmanxxxx = _snowmanx.subtract(_snowmanxx).normalize().multiply(10.0).add(_snowmanxx);
            BlockPos _snowmanxxxxx = new BlockPos(_snowmanxxxx);
            _snowmanxxxxx = this.mob.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, _snowmanxxxxx);
            if (!_snowman.startMovingTo((double)_snowmanxxxxx.getX(), (double)_snowmanxxxxx.getY(), (double)_snowmanxxxxx.getZ(), 1.0)) {
               this.findOtherWaypoint();
            }
         }
      }
   }

   private void findOtherWaypoint() {
      Random _snowman = this.mob.getRandom();
      BlockPos _snowmanx = this.mob
         .world
         .getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, this.mob.getBlockPos().add(-8 + _snowman.nextInt(16), 0, -8 + _snowman.nextInt(16)));
      this.mob.getNavigation().startMovingTo((double)_snowmanx.getX(), (double)_snowmanx.getY(), (double)_snowmanx.getZ(), 1.0);
   }
}
