package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;

public class PounceAtTargetGoal extends Goal {
   private final MobEntity mob;
   private LivingEntity target;
   private final float velocity;

   public PounceAtTargetGoal(MobEntity mob, float velocity) {
      this.mob = mob;
      this.velocity = velocity;
      this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
   }

   @Override
   public boolean canStart() {
      if (this.mob.hasPassengers()) {
         return false;
      } else {
         this.target = this.mob.getTarget();
         if (this.target == null) {
            return false;
         } else {
            double _snowman = this.mob.squaredDistanceTo(this.target);
            if (_snowman < 4.0 || _snowman > 16.0) {
               return false;
            } else {
               return !this.mob.isOnGround() ? false : this.mob.getRandom().nextInt(5) == 0;
            }
         }
      }
   }

   @Override
   public boolean shouldContinue() {
      return !this.mob.isOnGround();
   }

   @Override
   public void start() {
      Vec3d _snowman = this.mob.getVelocity();
      Vec3d _snowmanx = new Vec3d(this.target.getX() - this.mob.getX(), 0.0, this.target.getZ() - this.mob.getZ());
      if (_snowmanx.lengthSquared() > 1.0E-7) {
         _snowmanx = _snowmanx.normalize().multiply(0.4).add(_snowman.multiply(0.2));
      }

      this.mob.setVelocity(_snowmanx.x, (double)this.velocity, _snowmanx.z);
   }
}
