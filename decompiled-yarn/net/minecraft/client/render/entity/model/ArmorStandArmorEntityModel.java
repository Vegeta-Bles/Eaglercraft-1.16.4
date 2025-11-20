package net.minecraft.client.render.entity.model;

import net.minecraft.entity.decoration.ArmorStandEntity;

public class ArmorStandArmorEntityModel extends BipedEntityModel<ArmorStandEntity> {
   public ArmorStandArmorEntityModel(float _snowman) {
      this(_snowman, 64, 32);
   }

   protected ArmorStandArmorEntityModel(float scale, int textureWidth, int textureHeight) {
      super(scale, 0.0F, textureWidth, textureHeight);
   }

   public void setAngles(ArmorStandEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      this.head.pitch = (float) (Math.PI / 180.0) * _snowman.getHeadRotation().getPitch();
      this.head.yaw = (float) (Math.PI / 180.0) * _snowman.getHeadRotation().getYaw();
      this.head.roll = (float) (Math.PI / 180.0) * _snowman.getHeadRotation().getRoll();
      this.head.setPivot(0.0F, 1.0F, 0.0F);
      this.torso.pitch = (float) (Math.PI / 180.0) * _snowman.getBodyRotation().getPitch();
      this.torso.yaw = (float) (Math.PI / 180.0) * _snowman.getBodyRotation().getYaw();
      this.torso.roll = (float) (Math.PI / 180.0) * _snowman.getBodyRotation().getRoll();
      this.leftArm.pitch = (float) (Math.PI / 180.0) * _snowman.getLeftArmRotation().getPitch();
      this.leftArm.yaw = (float) (Math.PI / 180.0) * _snowman.getLeftArmRotation().getYaw();
      this.leftArm.roll = (float) (Math.PI / 180.0) * _snowman.getLeftArmRotation().getRoll();
      this.rightArm.pitch = (float) (Math.PI / 180.0) * _snowman.getRightArmRotation().getPitch();
      this.rightArm.yaw = (float) (Math.PI / 180.0) * _snowman.getRightArmRotation().getYaw();
      this.rightArm.roll = (float) (Math.PI / 180.0) * _snowman.getRightArmRotation().getRoll();
      this.leftLeg.pitch = (float) (Math.PI / 180.0) * _snowman.getLeftLegRotation().getPitch();
      this.leftLeg.yaw = (float) (Math.PI / 180.0) * _snowman.getLeftLegRotation().getYaw();
      this.leftLeg.roll = (float) (Math.PI / 180.0) * _snowman.getLeftLegRotation().getRoll();
      this.leftLeg.setPivot(1.9F, 11.0F, 0.0F);
      this.rightLeg.pitch = (float) (Math.PI / 180.0) * _snowman.getRightLegRotation().getPitch();
      this.rightLeg.yaw = (float) (Math.PI / 180.0) * _snowman.getRightLegRotation().getYaw();
      this.rightLeg.roll = (float) (Math.PI / 180.0) * _snowman.getRightLegRotation().getRoll();
      this.rightLeg.setPivot(-1.9F, 11.0F, 0.0F);
      this.helmet.copyPositionAndRotation(this.head);
   }
}
