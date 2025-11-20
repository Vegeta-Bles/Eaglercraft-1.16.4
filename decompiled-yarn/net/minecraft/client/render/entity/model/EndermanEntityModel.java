package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.LivingEntity;

public class EndermanEntityModel<T extends LivingEntity> extends BipedEntityModel<T> {
   public boolean carryingBlock;
   public boolean angry;

   public EndermanEntityModel(float _snowman) {
      super(0.0F, -14.0F, 64, 32);
      float _snowmanx = -14.0F;
      this.helmet = new ModelPart(this, 0, 16);
      this.helmet.addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, _snowman - 0.5F);
      this.helmet.setPivot(0.0F, -14.0F, 0.0F);
      this.torso = new ModelPart(this, 32, 16);
      this.torso.addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, _snowman);
      this.torso.setPivot(0.0F, -14.0F, 0.0F);
      this.rightArm = new ModelPart(this, 56, 0);
      this.rightArm.addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, _snowman);
      this.rightArm.setPivot(-3.0F, -12.0F, 0.0F);
      this.leftArm = new ModelPart(this, 56, 0);
      this.leftArm.mirror = true;
      this.leftArm.addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, _snowman);
      this.leftArm.setPivot(5.0F, -12.0F, 0.0F);
      this.rightLeg = new ModelPart(this, 56, 0);
      this.rightLeg.addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, _snowman);
      this.rightLeg.setPivot(-2.0F, -2.0F, 0.0F);
      this.leftLeg = new ModelPart(this, 56, 0);
      this.leftLeg.mirror = true;
      this.leftLeg.addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, _snowman);
      this.leftLeg.setPivot(2.0F, -2.0F, 0.0F);
   }

   @Override
   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      super.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.head.visible = true;
      float _snowmanxxxxxx = -14.0F;
      this.torso.pitch = 0.0F;
      this.torso.pivotY = -14.0F;
      this.torso.pivotZ = -0.0F;
      this.rightLeg.pitch -= 0.0F;
      this.leftLeg.pitch -= 0.0F;
      this.rightArm.pitch = (float)((double)this.rightArm.pitch * 0.5);
      this.leftArm.pitch = (float)((double)this.leftArm.pitch * 0.5);
      this.rightLeg.pitch = (float)((double)this.rightLeg.pitch * 0.5);
      this.leftLeg.pitch = (float)((double)this.leftLeg.pitch * 0.5);
      float _snowmanxxxxxxx = 0.4F;
      if (this.rightArm.pitch > 0.4F) {
         this.rightArm.pitch = 0.4F;
      }

      if (this.leftArm.pitch > 0.4F) {
         this.leftArm.pitch = 0.4F;
      }

      if (this.rightArm.pitch < -0.4F) {
         this.rightArm.pitch = -0.4F;
      }

      if (this.leftArm.pitch < -0.4F) {
         this.leftArm.pitch = -0.4F;
      }

      if (this.rightLeg.pitch > 0.4F) {
         this.rightLeg.pitch = 0.4F;
      }

      if (this.leftLeg.pitch > 0.4F) {
         this.leftLeg.pitch = 0.4F;
      }

      if (this.rightLeg.pitch < -0.4F) {
         this.rightLeg.pitch = -0.4F;
      }

      if (this.leftLeg.pitch < -0.4F) {
         this.leftLeg.pitch = -0.4F;
      }

      if (this.carryingBlock) {
         this.rightArm.pitch = -0.5F;
         this.leftArm.pitch = -0.5F;
         this.rightArm.roll = 0.05F;
         this.leftArm.roll = -0.05F;
      }

      this.rightArm.pivotZ = 0.0F;
      this.leftArm.pivotZ = 0.0F;
      this.rightLeg.pivotZ = 0.0F;
      this.leftLeg.pivotZ = 0.0F;
      this.rightLeg.pivotY = -5.0F;
      this.leftLeg.pivotY = -5.0F;
      this.head.pivotZ = -0.0F;
      this.head.pivotY = -13.0F;
      this.helmet.pivotX = this.head.pivotX;
      this.helmet.pivotY = this.head.pivotY;
      this.helmet.pivotZ = this.head.pivotZ;
      this.helmet.pitch = this.head.pitch;
      this.helmet.yaw = this.head.yaw;
      this.helmet.roll = this.head.roll;
      if (this.angry) {
         float _snowmanxxxxxxxx = 1.0F;
         this.head.pivotY -= 5.0F;
      }

      float _snowmanxxxxxxxx = -14.0F;
      this.rightArm.setPivot(-5.0F, -12.0F, 0.0F);
      this.leftArm.setPivot(5.0F, -12.0F, 0.0F);
   }
}
