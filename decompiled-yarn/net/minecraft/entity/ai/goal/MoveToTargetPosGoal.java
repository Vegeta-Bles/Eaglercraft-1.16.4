package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public abstract class MoveToTargetPosGoal extends Goal {
   protected final PathAwareEntity mob;
   public final double speed;
   protected int cooldown;
   protected int tryingTime;
   private int safeWaitingTime;
   protected BlockPos targetPos = BlockPos.ORIGIN;
   private boolean reached;
   private final int range;
   private final int maxYDifference;
   protected int lowestY;

   public MoveToTargetPosGoal(PathAwareEntity mob, double speed, int range) {
      this(mob, speed, range, 1);
   }

   public MoveToTargetPosGoal(PathAwareEntity mob, double speed, int range, int maxYDifference) {
      this.mob = mob;
      this.speed = speed;
      this.range = range;
      this.lowestY = 0;
      this.maxYDifference = maxYDifference;
      this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.JUMP));
   }

   @Override
   public boolean canStart() {
      if (this.cooldown > 0) {
         this.cooldown--;
         return false;
      } else {
         this.cooldown = this.getInterval(this.mob);
         return this.findTargetPos();
      }
   }

   protected int getInterval(PathAwareEntity mob) {
      return 200 + mob.getRandom().nextInt(200);
   }

   @Override
   public boolean shouldContinue() {
      return this.tryingTime >= -this.safeWaitingTime && this.tryingTime <= 1200 && this.isTargetPos(this.mob.world, this.targetPos);
   }

   @Override
   public void start() {
      this.startMovingToTarget();
      this.tryingTime = 0;
      this.safeWaitingTime = this.mob.getRandom().nextInt(this.mob.getRandom().nextInt(1200) + 1200) + 1200;
   }

   protected void startMovingToTarget() {
      this.mob
         .getNavigation()
         .startMovingTo(
            (double)((float)this.targetPos.getX()) + 0.5, (double)(this.targetPos.getY() + 1), (double)((float)this.targetPos.getZ()) + 0.5, this.speed
         );
   }

   public double getDesiredSquaredDistanceToTarget() {
      return 1.0;
   }

   protected BlockPos getTargetPos() {
      return this.targetPos.up();
   }

   @Override
   public void tick() {
      BlockPos _snowman = this.getTargetPos();
      if (!_snowman.isWithinDistance(this.mob.getPos(), this.getDesiredSquaredDistanceToTarget())) {
         this.reached = false;
         this.tryingTime++;
         if (this.shouldResetPath()) {
            this.mob.getNavigation().startMovingTo((double)((float)_snowman.getX()) + 0.5, (double)_snowman.getY(), (double)((float)_snowman.getZ()) + 0.5, this.speed);
         }
      } else {
         this.reached = true;
         this.tryingTime--;
      }
   }

   public boolean shouldResetPath() {
      return this.tryingTime % 40 == 0;
   }

   protected boolean hasReached() {
      return this.reached;
   }

   protected boolean findTargetPos() {
      int _snowman = this.range;
      int _snowmanx = this.maxYDifference;
      BlockPos _snowmanxx = this.mob.getBlockPos();
      BlockPos.Mutable _snowmanxxx = new BlockPos.Mutable();

      for (int _snowmanxxxx = this.lowestY; _snowmanxxxx <= _snowmanx; _snowmanxxxx = _snowmanxxxx > 0 ? -_snowmanxxxx : 1 - _snowmanxxxx) {
         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= _snowmanxxxxx; _snowmanxxxxxx = _snowmanxxxxxx > 0 ? -_snowmanxxxxxx : 1 - _snowmanxxxxxx) {
               for (int _snowmanxxxxxxx = _snowmanxxxxxx < _snowmanxxxxx && _snowmanxxxxxx > -_snowmanxxxxx ? _snowmanxxxxx : 0; _snowmanxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxx = _snowmanxxxxxxx > 0 ? -_snowmanxxxxxxx : 1 - _snowmanxxxxxxx) {
                  _snowmanxxx.set(_snowmanxx, _snowmanxxxxxx, _snowmanxxxx - 1, _snowmanxxxxxxx);
                  if (this.mob.isInWalkTargetRange(_snowmanxxx) && this.isTargetPos(this.mob.world, _snowmanxxx)) {
                     this.targetPos = _snowmanxxx;
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   protected abstract boolean isTargetPos(WorldView world, BlockPos pos);
}
