package net.minecraft.entity.ai.control;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class FlightMoveControl extends MoveControl {
   private final int maxPitchChange;
   private final boolean noGravity;

   public FlightMoveControl(MobEntity entity, int maxPitchChange, boolean noGravity) {
      super(entity);
      this.maxPitchChange = maxPitchChange;
      this.noGravity = noGravity;
   }

   @Override
   public void tick() {
      if (this.state == MoveControl.State.MOVE_TO) {
         this.state = MoveControl.State.WAIT;
         this.entity.setNoGravity(true);
         double _snowman = this.targetX - this.entity.getX();
         double _snowmanx = this.targetY - this.entity.getY();
         double _snowmanxx = this.targetZ - this.entity.getZ();
         double _snowmanxxx = _snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx;
         if (_snowmanxxx < 2.5000003E-7F) {
            this.entity.setUpwardSpeed(0.0F);
            this.entity.setForwardSpeed(0.0F);
            return;
         }

         float _snowmanxxxx = (float)(MathHelper.atan2(_snowmanxx, _snowman) * 180.0F / (float)Math.PI) - 90.0F;
         this.entity.yaw = this.changeAngle(this.entity.yaw, _snowmanxxxx, 90.0F);
         float _snowmanxxxxx;
         if (this.entity.isOnGround()) {
            _snowmanxxxxx = (float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
         } else {
            _snowmanxxxxx = (float)(this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));
         }

         this.entity.setMovementSpeed(_snowmanxxxxx);
         double _snowmanxxxxxx = (double)MathHelper.sqrt(_snowman * _snowman + _snowmanxx * _snowmanxx);
         float _snowmanxxxxxxx = (float)(-(MathHelper.atan2(_snowmanx, _snowmanxxxxxx) * 180.0F / (float)Math.PI));
         this.entity.pitch = this.changeAngle(this.entity.pitch, _snowmanxxxxxxx, (float)this.maxPitchChange);
         this.entity.setUpwardSpeed(_snowmanx > 0.0 ? _snowmanxxxxx : -_snowmanxxxxx);
      } else {
         if (!this.noGravity) {
            this.entity.setNoGravity(false);
         }

         this.entity.setUpwardSpeed(0.0F);
         this.entity.setForwardSpeed(0.0F);
      }
   }
}
