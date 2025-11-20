package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;

public class EscapeDangerGoal extends Goal {
   protected final PathAwareEntity mob;
   protected final double speed;
   protected double targetX;
   protected double targetY;
   protected double targetZ;
   protected boolean active;

   public EscapeDangerGoal(PathAwareEntity mob, double speed) {
      this.mob = mob;
      this.speed = speed;
      this.setControls(EnumSet.of(Goal.Control.MOVE));
   }

   @Override
   public boolean canStart() {
      if (this.mob.getAttacker() == null && !this.mob.isOnFire()) {
         return false;
      } else {
         if (this.mob.isOnFire()) {
            BlockPos _snowman = this.locateClosestWater(this.mob.world, this.mob, 5, 4);
            if (_snowman != null) {
               this.targetX = (double)_snowman.getX();
               this.targetY = (double)_snowman.getY();
               this.targetZ = (double)_snowman.getZ();
               return true;
            }
         }

         return this.findTarget();
      }
   }

   protected boolean findTarget() {
      Vec3d _snowman = TargetFinder.findTarget(this.mob, 5, 4);
      if (_snowman == null) {
         return false;
      } else {
         this.targetX = _snowman.x;
         this.targetY = _snowman.y;
         this.targetZ = _snowman.z;
         return true;
      }
   }

   public boolean isActive() {
      return this.active;
   }

   @Override
   public void start() {
      this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
      this.active = true;
   }

   @Override
   public void stop() {
      this.active = false;
   }

   @Override
   public boolean shouldContinue() {
      return !this.mob.getNavigation().isIdle();
   }

   @Nullable
   protected BlockPos locateClosestWater(BlockView blockView, Entity entity, int rangeX, int rangeY) {
      BlockPos _snowman = entity.getBlockPos();
      int _snowmanx = _snowman.getX();
      int _snowmanxx = _snowman.getY();
      int _snowmanxxx = _snowman.getZ();
      float _snowmanxxxx = (float)(rangeX * rangeX * rangeY * 2);
      BlockPos _snowmanxxxxx = null;
      BlockPos.Mutable _snowmanxxxxxx = new BlockPos.Mutable();

      for (int _snowmanxxxxxxx = _snowmanx - rangeX; _snowmanxxxxxxx <= _snowmanx + rangeX; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = _snowmanxx - rangeY; _snowmanxxxxxxxx <= _snowmanxx + rangeY; _snowmanxxxxxxxx++) {
            for (int _snowmanxxxxxxxxx = _snowmanxxx - rangeX; _snowmanxxxxxxxxx <= _snowmanxxx + rangeX; _snowmanxxxxxxxxx++) {
               _snowmanxxxxxx.set(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
               if (blockView.getFluidState(_snowmanxxxxxx).isIn(FluidTags.WATER)) {
                  float _snowmanxxxxxxxxxx = (float)(
                     (_snowmanxxxxxxx - _snowmanx) * (_snowmanxxxxxxx - _snowmanx) + (_snowmanxxxxxxxx - _snowmanxx) * (_snowmanxxxxxxxx - _snowmanxx) + (_snowmanxxxxxxxxx - _snowmanxxx) * (_snowmanxxxxxxxxx - _snowmanxxx)
                  );
                  if (_snowmanxxxxxxxxxx < _snowmanxxxx) {
                     _snowmanxxxx = _snowmanxxxxxxxxxx;
                     _snowmanxxxxx = new BlockPos(_snowmanxxxxxx);
                  }
               }
            }
         }
      }

      return _snowmanxxxxx;
   }
}
