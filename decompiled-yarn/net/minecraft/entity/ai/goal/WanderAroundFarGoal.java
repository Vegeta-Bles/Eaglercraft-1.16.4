package net.minecraft.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

public class WanderAroundFarGoal extends WanderAroundGoal {
   protected final float probability;

   public WanderAroundFarGoal(PathAwareEntity _snowman, double _snowman) {
      this(_snowman, _snowman, 0.001F);
   }

   public WanderAroundFarGoal(PathAwareEntity mob, double speed, float probability) {
      super(mob, speed);
      this.probability = probability;
   }

   @Nullable
   @Override
   protected Vec3d getWanderTarget() {
      if (this.mob.isInsideWaterOrBubbleColumn()) {
         Vec3d _snowman = TargetFinder.findGroundTarget(this.mob, 15, 7);
         return _snowman == null ? super.getWanderTarget() : _snowman;
      } else {
         return this.mob.getRandom().nextFloat() >= this.probability ? TargetFinder.findGroundTarget(this.mob, 10, 7) : super.getWanderTarget();
      }
   }
}
