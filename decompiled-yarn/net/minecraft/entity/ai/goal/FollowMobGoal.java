package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;

public class FollowMobGoal extends Goal {
   private final MobEntity mob;
   private final Predicate<MobEntity> targetPredicate;
   private MobEntity target;
   private final double speed;
   private final EntityNavigation navigation;
   private int updateCountdownTicks;
   private final float minDistance;
   private float oldWaterPathFindingPenalty;
   private final float maxDistance;

   public FollowMobGoal(MobEntity mob, double speed, float minDistance, float maxDistance) {
      this.mob = mob;
      this.targetPredicate = _snowmanx -> _snowmanx != null && mob.getClass() != _snowmanx.getClass();
      this.speed = speed;
      this.navigation = mob.getNavigation();
      this.minDistance = minDistance;
      this.maxDistance = maxDistance;
      this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
      if (!(mob.getNavigation() instanceof MobNavigation) && !(mob.getNavigation() instanceof BirdNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
      }
   }

   @Override
   public boolean canStart() {
      List<MobEntity> _snowman = this.mob.world.getEntitiesByClass(MobEntity.class, this.mob.getBoundingBox().expand((double)this.maxDistance), this.targetPredicate);
      if (!_snowman.isEmpty()) {
         for (MobEntity _snowmanx : _snowman) {
            if (!_snowmanx.isInvisible()) {
               this.target = _snowmanx;
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public boolean shouldContinue() {
      return this.target != null && !this.navigation.isIdle() && this.mob.squaredDistanceTo(this.target) > (double)(this.minDistance * this.minDistance);
   }

   @Override
   public void start() {
      this.updateCountdownTicks = 0;
      this.oldWaterPathFindingPenalty = this.mob.getPathfindingPenalty(PathNodeType.WATER);
      this.mob.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
   }

   @Override
   public void stop() {
      this.target = null;
      this.navigation.stop();
      this.mob.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathFindingPenalty);
   }

   @Override
   public void tick() {
      if (this.target != null && !this.mob.isLeashed()) {
         this.mob.getLookControl().lookAt(this.target, 10.0F, (float)this.mob.getLookPitchSpeed());
         if (--this.updateCountdownTicks <= 0) {
            this.updateCountdownTicks = 10;
            double _snowman = this.mob.getX() - this.target.getX();
            double _snowmanx = this.mob.getY() - this.target.getY();
            double _snowmanxx = this.mob.getZ() - this.target.getZ();
            double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
            if (!(_snowmanxxx <= (double)(this.minDistance * this.minDistance))) {
               this.navigation.startMovingTo(this.target, this.speed);
            } else {
               this.navigation.stop();
               LookControl _snowmanxxxx = this.target.getLookControl();
               if (_snowmanxxx <= (double)this.minDistance
                  || _snowmanxxxx.getLookX() == this.mob.getX() && _snowmanxxxx.getLookY() == this.mob.getY() && _snowmanxxxx.getLookZ() == this.mob.getZ()) {
                  double _snowmanxxxxx = this.target.getX() - this.mob.getX();
                  double _snowmanxxxxxx = this.target.getZ() - this.mob.getZ();
                  this.navigation.startMovingTo(this.mob.getX() - _snowmanxxxxx, this.mob.getY(), this.mob.getZ() - _snowmanxxxxxx, this.speed);
               }
            }
         }
      }
   }
}
