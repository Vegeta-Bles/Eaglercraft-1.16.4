package net.minecraft.entity.ai.control;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

public class MoveControl {
   protected final MobEntity entity;
   protected double targetX;
   protected double targetY;
   protected double targetZ;
   protected double speed;
   protected float forwardMovement;
   protected float sidewaysMovement;
   protected MoveControl.State state = MoveControl.State.WAIT;

   public MoveControl(MobEntity entity) {
      this.entity = entity;
   }

   public boolean isMoving() {
      return this.state == MoveControl.State.MOVE_TO;
   }

   public double getSpeed() {
      return this.speed;
   }

   public void moveTo(double x, double y, double z, double speed) {
      this.targetX = x;
      this.targetY = y;
      this.targetZ = z;
      this.speed = speed;
      if (this.state != MoveControl.State.JUMPING) {
         this.state = MoveControl.State.MOVE_TO;
      }
   }

   public void strafeTo(float forward, float sideways) {
      this.state = MoveControl.State.STRAFE;
      this.forwardMovement = forward;
      this.sidewaysMovement = sideways;
      this.speed = 0.25;
   }

   public void tick() {
      if (this.state == MoveControl.State.STRAFE) {
         float _snowman = (float)this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
         float _snowmanx = (float)this.speed * _snowman;
         float _snowmanxx = this.forwardMovement;
         float _snowmanxxx = this.sidewaysMovement;
         float _snowmanxxxx = MathHelper.sqrt(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
         if (_snowmanxxxx < 1.0F) {
            _snowmanxxxx = 1.0F;
         }

         _snowmanxxxx = _snowmanx / _snowmanxxxx;
         _snowmanxx *= _snowmanxxxx;
         _snowmanxxx *= _snowmanxxxx;
         float _snowmanxxxxx = MathHelper.sin(this.entity.yaw * (float) (Math.PI / 180.0));
         float _snowmanxxxxxx = MathHelper.cos(this.entity.yaw * (float) (Math.PI / 180.0));
         float _snowmanxxxxxxx = _snowmanxx * _snowmanxxxxxx - _snowmanxxx * _snowmanxxxxx;
         float _snowmanxxxxxxxx = _snowmanxxx * _snowmanxxxxxx + _snowmanxx * _snowmanxxxxx;
         if (!this.method_25946(_snowmanxxxxxxx, _snowmanxxxxxxxx)) {
            this.forwardMovement = 1.0F;
            this.sidewaysMovement = 0.0F;
         }

         this.entity.setMovementSpeed(_snowmanx);
         this.entity.setForwardSpeed(this.forwardMovement);
         this.entity.setSidewaysSpeed(this.sidewaysMovement);
         this.state = MoveControl.State.WAIT;
      } else if (this.state == MoveControl.State.MOVE_TO) {
         this.state = MoveControl.State.WAIT;
         double _snowmanxxxxx = this.targetX - this.entity.getX();
         double _snowmanxxxxxx = this.targetZ - this.entity.getZ();
         double _snowmanxxxxxxx = this.targetY - this.entity.getY();
         double _snowmanxxxxxxxx = _snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxx * _snowmanxxxxxx;
         if (_snowmanxxxxxxxx < 2.5000003E-7F) {
            this.entity.setForwardSpeed(0.0F);
            return;
         }

         float _snowmanxxxxxxxxx = (float)(MathHelper.atan2(_snowmanxxxxxx, _snowmanxxxxx) * 180.0F / (float)Math.PI) - 90.0F;
         this.entity.yaw = this.changeAngle(this.entity.yaw, _snowmanxxxxxxxxx, 90.0F);
         this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
         BlockPos _snowmanxxxxxxxxxx = this.entity.getBlockPos();
         BlockState _snowmanxxxxxxxxxxx = this.entity.world.getBlockState(_snowmanxxxxxxxxxx);
         Block _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getBlock();
         VoxelShape _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getCollisionShape(this.entity.world, _snowmanxxxxxxxxxx);
         if (_snowmanxxxxxxx > (double)this.entity.stepHeight && _snowmanxxxxx * _snowmanxxxxx + _snowmanxxxxxx * _snowmanxxxxxx < (double)Math.max(1.0F, this.entity.getWidth())
            || !_snowmanxxxxxxxxxxxxx.isEmpty()
               && this.entity.getY() < _snowmanxxxxxxxxxxxxx.getMax(Direction.Axis.Y) + (double)_snowmanxxxxxxxxxx.getY()
               && !_snowmanxxxxxxxxxxxx.isIn(BlockTags.DOORS)
               && !_snowmanxxxxxxxxxxxx.isIn(BlockTags.FENCES)) {
            this.entity.getJumpControl().setActive();
            this.state = MoveControl.State.JUMPING;
         }
      } else if (this.state == MoveControl.State.JUMPING) {
         this.entity.setMovementSpeed((float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));
         if (this.entity.isOnGround()) {
            this.state = MoveControl.State.WAIT;
         }
      } else {
         this.entity.setForwardSpeed(0.0F);
      }
   }

   private boolean method_25946(float _snowman, float _snowman) {
      EntityNavigation _snowmanxx = this.entity.getNavigation();
      if (_snowmanxx != null) {
         PathNodeMaker _snowmanxxx = _snowmanxx.getNodeMaker();
         if (_snowmanxxx != null
            && _snowmanxxx.getDefaultNodeType(
                  this.entity.world,
                  MathHelper.floor(this.entity.getX() + (double)_snowman),
                  MathHelper.floor(this.entity.getY()),
                  MathHelper.floor(this.entity.getZ() + (double)_snowman)
               )
               != PathNodeType.WALKABLE) {
            return false;
         }
      }

      return true;
   }

   protected float changeAngle(float from, float to, float max) {
      float _snowman = MathHelper.wrapDegrees(to - from);
      if (_snowman > max) {
         _snowman = max;
      }

      if (_snowman < -max) {
         _snowman = -max;
      }

      float _snowmanx = from + _snowman;
      if (_snowmanx < 0.0F) {
         _snowmanx += 360.0F;
      } else if (_snowmanx > 360.0F) {
         _snowmanx -= 360.0F;
      }

      return _snowmanx;
   }

   public double getTargetX() {
      return this.targetX;
   }

   public double getTargetY() {
      return this.targetY;
   }

   public double getTargetZ() {
      return this.targetZ;
   }

   public static enum State {
      WAIT,
      MOVE_TO,
      STRAFE,
      JUMPING;

      private State() {
      }
   }
}
