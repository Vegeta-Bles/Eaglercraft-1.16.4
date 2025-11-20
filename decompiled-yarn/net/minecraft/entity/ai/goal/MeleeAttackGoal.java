package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Hand;

public class MeleeAttackGoal extends Goal {
   protected final PathAwareEntity mob;
   private final double speed;
   private final boolean pauseWhenMobIdle;
   private Path path;
   private double targetX;
   private double targetY;
   private double targetZ;
   private int updateCountdownTicks;
   private int field_24667;
   private final int attackIntervalTicks = 20;
   private long lastUpdateTime;

   public MeleeAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
      this.mob = mob;
      this.speed = speed;
      this.pauseWhenMobIdle = pauseWhenMobIdle;
      this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
   }

   @Override
   public boolean canStart() {
      long _snowman = this.mob.world.getTime();
      if (_snowman - this.lastUpdateTime < 20L) {
         return false;
      } else {
         this.lastUpdateTime = _snowman;
         LivingEntity _snowmanx = this.mob.getTarget();
         if (_snowmanx == null) {
            return false;
         } else if (!_snowmanx.isAlive()) {
            return false;
         } else {
            this.path = this.mob.getNavigation().findPathTo(_snowmanx, 0);
            return this.path != null ? true : this.getSquaredMaxAttackDistance(_snowmanx) >= this.mob.squaredDistanceTo(_snowmanx.getX(), _snowmanx.getY(), _snowmanx.getZ());
         }
      }
   }

   @Override
   public boolean shouldContinue() {
      LivingEntity _snowman = this.mob.getTarget();
      if (_snowman == null) {
         return false;
      } else if (!_snowman.isAlive()) {
         return false;
      } else if (!this.pauseWhenMobIdle) {
         return !this.mob.getNavigation().isIdle();
      } else {
         return !this.mob.isInWalkTargetRange(_snowman.getBlockPos()) ? false : !(_snowman instanceof PlayerEntity) || !_snowman.isSpectator() && !((PlayerEntity)_snowman).isCreative();
      }
   }

   @Override
   public void start() {
      this.mob.getNavigation().startMovingAlong(this.path, this.speed);
      this.mob.setAttacking(true);
      this.updateCountdownTicks = 0;
      this.field_24667 = 0;
   }

   @Override
   public void stop() {
      LivingEntity _snowman = this.mob.getTarget();
      if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(_snowman)) {
         this.mob.setTarget(null);
      }

      this.mob.setAttacking(false);
      this.mob.getNavigation().stop();
   }

   @Override
   public void tick() {
      LivingEntity _snowman = this.mob.getTarget();
      this.mob.getLookControl().lookAt(_snowman, 30.0F, 30.0F);
      double _snowmanx = this.mob.squaredDistanceTo(_snowman.getX(), _snowman.getY(), _snowman.getZ());
      this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
      if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(_snowman))
         && this.updateCountdownTicks <= 0
         && (
            this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0
               || _snowman.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0
               || this.mob.getRandom().nextFloat() < 0.05F
         )) {
         this.targetX = _snowman.getX();
         this.targetY = _snowman.getY();
         this.targetZ = _snowman.getZ();
         this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
         if (_snowmanx > 1024.0) {
            this.updateCountdownTicks += 10;
         } else if (_snowmanx > 256.0) {
            this.updateCountdownTicks += 5;
         }

         if (!this.mob.getNavigation().startMovingTo(_snowman, this.speed)) {
            this.updateCountdownTicks += 15;
         }
      }

      this.field_24667 = Math.max(this.field_24667 - 1, 0);
      this.attack(_snowman, _snowmanx);
   }

   protected void attack(LivingEntity target, double squaredDistance) {
      double _snowman = this.getSquaredMaxAttackDistance(target);
      if (squaredDistance <= _snowman && this.field_24667 <= 0) {
         this.method_28346();
         this.mob.swingHand(Hand.MAIN_HAND);
         this.mob.tryAttack(target);
      }
   }

   protected void method_28346() {
      this.field_24667 = 20;
   }

   protected boolean method_28347() {
      return this.field_24667 <= 0;
   }

   protected int method_28348() {
      return this.field_24667;
   }

   protected int method_28349() {
      return 20;
   }

   protected double getSquaredMaxAttackDistance(LivingEntity entity) {
      return (double)(this.mob.getWidth() * 2.0F * this.mob.getWidth() * 2.0F + entity.getWidth());
   }
}
