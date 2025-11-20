package net.minecraft.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class PathAwareEntity extends MobEntity {
   protected PathAwareEntity(EntityType<? extends PathAwareEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public float getPathfindingFavor(BlockPos pos) {
      return this.getPathfindingFavor(pos, this.world);
   }

   public float getPathfindingFavor(BlockPos pos, WorldView world) {
      return 0.0F;
   }

   @Override
   public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
      return this.getPathfindingFavor(this.getBlockPos(), world) >= 0.0F;
   }

   public boolean isNavigating() {
      return !this.getNavigation().isIdle();
   }

   @Override
   protected void updateLeash() {
      super.updateLeash();
      Entity _snowman = this.getHoldingEntity();
      if (_snowman != null && _snowman.world == this.world) {
         this.setPositionTarget(_snowman.getBlockPos(), 5);
         float _snowmanx = this.distanceTo(_snowman);
         if (this instanceof TameableEntity && ((TameableEntity)this).isInSittingPose()) {
            if (_snowmanx > 10.0F) {
               this.detachLeash(true, true);
            }

            return;
         }

         this.updateForLeashLength(_snowmanx);
         if (_snowmanx > 10.0F) {
            this.detachLeash(true, true);
            this.goalSelector.disableControl(Goal.Control.MOVE);
         } else if (_snowmanx > 6.0F) {
            double _snowmanxx = (_snowman.getX() - this.getX()) / (double)_snowmanx;
            double _snowmanxxx = (_snowman.getY() - this.getY()) / (double)_snowmanx;
            double _snowmanxxxx = (_snowman.getZ() - this.getZ()) / (double)_snowmanx;
            this.setVelocity(
               this.getVelocity().add(Math.copySign(_snowmanxx * _snowmanxx * 0.4, _snowmanxx), Math.copySign(_snowmanxxx * _snowmanxxx * 0.4, _snowmanxxx), Math.copySign(_snowmanxxxx * _snowmanxxxx * 0.4, _snowmanxxxx))
            );
         } else {
            this.goalSelector.enableControl(Goal.Control.MOVE);
            float _snowmanxx = 2.0F;
            Vec3d _snowmanxxx = new Vec3d(_snowman.getX() - this.getX(), _snowman.getY() - this.getY(), _snowman.getZ() - this.getZ())
               .normalize()
               .multiply((double)Math.max(_snowmanx - 2.0F, 0.0F));
            this.getNavigation().startMovingTo(this.getX() + _snowmanxxx.x, this.getY() + _snowmanxxx.y, this.getZ() + _snowmanxxx.z, this.getRunFromLeashSpeed());
         }
      }
   }

   protected double getRunFromLeashSpeed() {
      return 1.0;
   }

   protected void updateForLeashLength(float leashLength) {
   }
}
