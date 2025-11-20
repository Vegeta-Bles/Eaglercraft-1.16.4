package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class SkeletonEntityModel<T extends MobEntity & RangedAttackMob> extends BipedEntityModel<T> {
   public SkeletonEntityModel() {
      this(0.0F, false);
   }

   public SkeletonEntityModel(float stretch, boolean isClothing) {
      super(stretch);
      if (!isClothing) {
         this.rightArm = new ModelPart(this, 40, 16);
         this.rightArm.addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, stretch);
         this.rightArm.setPivot(-5.0F, 2.0F, 0.0F);
         this.leftArm = new ModelPart(this, 40, 16);
         this.leftArm.mirror = true;
         this.leftArm.addCuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, stretch);
         this.leftArm.setPivot(5.0F, 2.0F, 0.0F);
         this.rightLeg = new ModelPart(this, 0, 16);
         this.rightLeg.addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, stretch);
         this.rightLeg.setPivot(-2.0F, 12.0F, 0.0F);
         this.leftLeg = new ModelPart(this, 0, 16);
         this.leftLeg.mirror = true;
         this.leftLeg.addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, stretch);
         this.leftLeg.setPivot(2.0F, 12.0F, 0.0F);
      }
   }

   public void animateModel(T _snowman, float _snowman, float _snowman, float _snowman) {
      this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
      this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
      ItemStack _snowmanxxxx = _snowman.getStackInHand(Hand.MAIN_HAND);
      if (_snowmanxxxx.getItem() == Items.BOW && _snowman.isAttacking()) {
         if (_snowman.getMainArm() == Arm.RIGHT) {
            this.rightArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
         } else {
            this.leftArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
         }
      }

      super.animateModel(_snowman, _snowman, _snowman, _snowman);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      super.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      ItemStack _snowmanxxxxxx = _snowman.getMainHandStack();
      if (_snowman.isAttacking() && (_snowmanxxxxxx.isEmpty() || _snowmanxxxxxx.getItem() != Items.BOW)) {
         float _snowmanxxxxxxx = MathHelper.sin(this.handSwingProgress * (float) Math.PI);
         float _snowmanxxxxxxxx = MathHelper.sin((1.0F - (1.0F - this.handSwingProgress) * (1.0F - this.handSwingProgress)) * (float) Math.PI);
         this.rightArm.roll = 0.0F;
         this.leftArm.roll = 0.0F;
         this.rightArm.yaw = -(0.1F - _snowmanxxxxxxx * 0.6F);
         this.leftArm.yaw = 0.1F - _snowmanxxxxxxx * 0.6F;
         this.rightArm.pitch = (float) (-Math.PI / 2);
         this.leftArm.pitch = (float) (-Math.PI / 2);
         this.rightArm.pitch -= _snowmanxxxxxxx * 1.2F - _snowmanxxxxxxxx * 0.4F;
         this.leftArm.pitch -= _snowmanxxxxxxx * 1.2F - _snowmanxxxxxxxx * 0.4F;
         CrossbowPosing.method_29350(this.rightArm, this.leftArm, _snowman);
      }
   }

   @Override
   public void setArmAngle(Arm arm, MatrixStack matrices) {
      float _snowman = arm == Arm.RIGHT ? 1.0F : -1.0F;
      ModelPart _snowmanx = this.getArm(arm);
      _snowmanx.pivotX += _snowman;
      _snowmanx.rotate(matrices);
      _snowmanx.pivotX -= _snowman;
   }
}
