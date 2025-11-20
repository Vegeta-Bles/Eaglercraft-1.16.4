package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.BlockView;

public class AttackGoal extends Goal {
   private final BlockView world;
   private final MobEntity mob;
   private LivingEntity target;
   private int cooldown;

   public AttackGoal(MobEntity mob) {
      this.mob = mob;
      this.world = mob.world;
      this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
   }

   @Override
   public boolean canStart() {
      LivingEntity _snowman = this.mob.getTarget();
      if (_snowman == null) {
         return false;
      } else {
         this.target = _snowman;
         return true;
      }
   }

   @Override
   public boolean shouldContinue() {
      if (!this.target.isAlive()) {
         return false;
      } else {
         return this.mob.squaredDistanceTo(this.target) > 225.0 ? false : !this.mob.getNavigation().isIdle() || this.canStart();
      }
   }

   @Override
   public void stop() {
      this.target = null;
      this.mob.getNavigation().stop();
   }

   @Override
   public void tick() {
      this.mob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
      double _snowman = (double)(this.mob.getWidth() * 2.0F * this.mob.getWidth() * 2.0F);
      double _snowmanx = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
      double _snowmanxx = 0.8;
      if (_snowmanx > _snowman && _snowmanx < 16.0) {
         _snowmanxx = 1.33;
      } else if (_snowmanx < 225.0) {
         _snowmanxx = 0.6;
      }

      this.mob.getNavigation().startMovingTo(this.target, _snowmanxx);
      this.cooldown = Math.max(this.cooldown - 1, 0);
      if (!(_snowmanx > _snowman)) {
         if (this.cooldown <= 0) {
            this.cooldown = 20;
            this.mob.tryAttack(this.target);
         }
      }
   }
}
