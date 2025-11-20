package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.passive.TameableEntity;

public class AttackWithOwnerGoal extends TrackTargetGoal {
   private final TameableEntity tameable;
   private LivingEntity attacking;
   private int lastAttackTime;

   public AttackWithOwnerGoal(TameableEntity tameable) {
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
            this.attacking = _snowman.getAttacking();
            int _snowmanx = _snowman.getLastAttackTime();
            return _snowmanx != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT) && this.tameable.canAttackWithOwner(this.attacking, _snowman);
         }
      } else {
         return false;
      }
   }

   @Override
   public void start() {
      this.mob.setTarget(this.attacking);
      LivingEntity _snowman = this.tameable.getOwner();
      if (_snowman != null) {
         this.lastAttackTime = _snowman.getLastAttackTime();
      }

      super.start();
   }
}
