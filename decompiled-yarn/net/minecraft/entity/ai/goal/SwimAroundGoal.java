package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SwimAroundGoal extends WanderAroundGoal {
   public SwimAroundGoal(PathAwareEntity _snowman, double _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Nullable
   @Override
   protected Vec3d getWanderTarget() {
      Vec3d _snowman = TargetFinder.findTarget(this.mob, 10, 7);
      int _snowmanx = 0;

      while (_snowman != null && !this.mob.world.getBlockState(new BlockPos(_snowman)).canPathfindThrough(this.mob.world, new BlockPos(_snowman), NavigationType.WATER) && _snowmanx++ < 10) {
         _snowman = TargetFinder.findTarget(this.mob, 10, 7);
      }

      return _snowman;
   }
}
