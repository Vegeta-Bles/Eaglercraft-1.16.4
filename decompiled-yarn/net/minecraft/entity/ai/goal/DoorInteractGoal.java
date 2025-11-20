package net.minecraft.entity.ai.goal;

import net.minecraft.class_5493;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;

public abstract class DoorInteractGoal extends Goal {
   protected MobEntity mob;
   protected BlockPos doorPos = BlockPos.ORIGIN;
   protected boolean doorValid;
   private boolean shouldStop;
   private float xOffset;
   private float zOffset;

   public DoorInteractGoal(MobEntity mob) {
      this.mob = mob;
      if (!class_5493.method_30955(mob)) {
         throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
      }
   }

   protected boolean isDoorOpen() {
      if (!this.doorValid) {
         return false;
      } else {
         BlockState _snowman = this.mob.world.getBlockState(this.doorPos);
         if (!(_snowman.getBlock() instanceof DoorBlock)) {
            this.doorValid = false;
            return false;
         } else {
            return _snowman.get(DoorBlock.OPEN);
         }
      }
   }

   protected void setDoorOpen(boolean open) {
      if (this.doorValid) {
         BlockState _snowman = this.mob.world.getBlockState(this.doorPos);
         if (_snowman.getBlock() instanceof DoorBlock) {
            ((DoorBlock)_snowman.getBlock()).setOpen(this.mob.world, _snowman, this.doorPos, open);
         }
      }
   }

   @Override
   public boolean canStart() {
      if (!class_5493.method_30955(this.mob)) {
         return false;
      } else if (!this.mob.horizontalCollision) {
         return false;
      } else {
         MobNavigation _snowman = (MobNavigation)this.mob.getNavigation();
         Path _snowmanx = _snowman.getCurrentPath();
         if (_snowmanx != null && !_snowmanx.isFinished() && _snowman.canEnterOpenDoors()) {
            for (int _snowmanxx = 0; _snowmanxx < Math.min(_snowmanx.getCurrentNodeIndex() + 2, _snowmanx.getLength()); _snowmanxx++) {
               PathNode _snowmanxxx = _snowmanx.getNode(_snowmanxx);
               this.doorPos = new BlockPos(_snowmanxxx.x, _snowmanxxx.y + 1, _snowmanxxx.z);
               if (!(this.mob.squaredDistanceTo((double)this.doorPos.getX(), this.mob.getY(), (double)this.doorPos.getZ()) > 2.25)) {
                  this.doorValid = DoorBlock.isWoodenDoor(this.mob.world, this.doorPos);
                  if (this.doorValid) {
                     return true;
                  }
               }
            }

            this.doorPos = this.mob.getBlockPos().up();
            this.doorValid = DoorBlock.isWoodenDoor(this.mob.world, this.doorPos);
            return this.doorValid;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean shouldContinue() {
      return !this.shouldStop;
   }

   @Override
   public void start() {
      this.shouldStop = false;
      this.xOffset = (float)((double)this.doorPos.getX() + 0.5 - this.mob.getX());
      this.zOffset = (float)((double)this.doorPos.getZ() + 0.5 - this.mob.getZ());
   }

   @Override
   public void tick() {
      float _snowman = (float)((double)this.doorPos.getX() + 0.5 - this.mob.getX());
      float _snowmanx = (float)((double)this.doorPos.getZ() + 0.5 - this.mob.getZ());
      float _snowmanxx = this.xOffset * _snowman + this.zOffset * _snowmanx;
      if (_snowmanxx < 0.0F) {
         this.shouldStop = true;
      }
   }
}
