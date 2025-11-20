package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.passive.TameableEntity;

public class TrackOwnerAttackerGoal extends TrackTargetGoal {
   private final TameableEntity tameable;
   private LivingEntity attacker;
   private int lastAttackedTime;

   public TrackOwnerAttackerGoal(TameableEntity tameable) {
      super(tameable, false);
      this.tameable = tameable;
      this.setControls(EnumSet.of(Goal.Control.TARGET));
   }

   @Override
   public boolean canStart() {
      if (this.tameable.isTamed() && !this.tameable.isSitting()) {
         LivingEntity _snowman = this.tameable.getOwner();
         if (_snowman == null) {
            return false;
         } else {
            this.attacker = _snowman.getAttacker();
            int _snowmanx = _snowman.getLastAttackedTime();
            return _snowmanx != this.lastAttackedTime && this.canTrack(this.attacker, TargetPredicate.DEFAULT) && this.tameable.canAttackWithOwner(this.attacker, _snowman);
         }
      } else {
         return false;
      }
   }

   @Override
   public void start() {
      this.mob.setTarget(this.attacker);
      LivingEntity _snowman = this.tameable.getOwner();
      if (_snowman != null) {
         this.lastAttackedTime = _snowman.getLastAttackedTime();
      }

      super.start();
   }
}
