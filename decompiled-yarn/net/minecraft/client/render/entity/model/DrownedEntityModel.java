package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class DrownedEntityModel<T extends ZombieEntity> extends ZombieEntityModel<T> {
   public DrownedEntityModel(float _snowman, float _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.rightArm = new ModelPart(this, 32, 48);
      this.rightArm.addCuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.rightArm.setPivot(-5.0F, 2.0F + _snowman, 0.0F);
      this.rightLeg = new ModelPart(this, 16, 48);
      this.rightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, _snowman);
      this.rightLeg.setPivot(-1.9F, 12.0F + _snowman, 0.0F);
   }

   public DrownedEntityModel(float _snowman, boolean _snowman) {
      super(_snowman, 0.0F, 64, _snowman ? 32 : 64);
   }

   public void animateModel(T _snowman, float _snowman, float _snowman, float _snowman) {
      this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
      this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
      ItemStack _snowmanxxxx = _snowman.getStackInHand(Hand.MAIN_HAND);
      if (_snowmanxxxx.getItem() == Items.TRIDENT && _snowman.isAttacking()) {
         if (_snowman.getMainArm() == Arm.RIGHT) {
            this.rightArmPose = BipedEntityModel.ArmPose.THROW_SPEAR;
         } else {
            this.leftArmPose = BipedEntityModel.ArmPose.THROW_SPEAR;
         }
      }

      super.animateModel(_snowman, _snowman, _snowman, _snowman);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      super.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (this.leftArmPose == BipedEntityModel.ArmPose.THROW_SPEAR) {
         this.leftArm.pitch = this.leftArm.pitch * 0.5F - (float) Math.PI;
         this.leftArm.yaw = 0.0F;
      }

      if (this.rightArmPose == BipedEntityModel.ArmPose.THROW_SPEAR) {
         this.rightArm.pitch = this.rightArm.pitch * 0.5F - (float) Math.PI;
         this.rightArm.yaw = 0.0F;
      }

      if (this.leaningPitch > 0.0F) {
         this.rightArm.pitch = this.lerpAngle(this.leaningPitch, this.rightArm.pitch, (float) (-Math.PI * 4.0 / 5.0))
            + this.leaningPitch * 0.35F * MathHelper.sin(0.1F * _snowman);
         this.leftArm.pitch = this.lerpAngle(this.leaningPitch, this.leftArm.pitch, (float) (-Math.PI * 4.0 / 5.0))
            - this.leaningPitch * 0.35F * MathHelper.sin(0.1F * _snowman);
         this.rightArm.roll = this.lerpAngle(this.leaningPitch, this.rightArm.roll, -0.15F);
         this.leftArm.roll = this.lerpAngle(this.leaningPitch, this.leftArm.roll, 0.15F);
         this.leftLeg.pitch = this.leftLeg.pitch - this.leaningPitch * 0.55F * MathHelper.sin(0.1F * _snowman);
         this.rightLeg.pitch = this.rightLeg.pitch + this.leaningPitch * 0.55F * MathHelper.sin(0.1F * _snowman);
         this.head.pitch = 0.0F;
      }
   }
}
