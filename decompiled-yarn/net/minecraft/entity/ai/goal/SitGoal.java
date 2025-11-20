package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

public class SitGoal extends Goal {
   private final TameableEntity tameable;

   public SitGoal(TameableEntity tameable) {
      this.tameable = tameable;
      this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
   }

   @Override
   public boolean shouldContinue() {
      return this.tameable.isSitting();
   }

   @Override
   public boolean canStart() {
      if (!this.tameable.isTamed()) {
         return false;
      } else if (this.tameable.isInsideWaterOrBubbleColumn()) {
         return false;
      } else if (!this.tameable.isOnGround()) {
         return false;
      } else {
         LivingEntity _snowman = this.tameable.getOwner();
         if (_snowman == null) {
            return true;
         } else {
            return this.tameable.squaredDistanceTo(_snowman) < 144.0 && _snowman.getAttacker() != null ? false : this.tameable.isSitting();
         }
      }
   }

   @Override
   public void start() {
      this.tameable.getNavigation().stop();
      this.tameable.setInSittingPose(true);
   }

   @Override
   public void stop() {
      this.tameable.setInSittingPose(false);
   }
}
