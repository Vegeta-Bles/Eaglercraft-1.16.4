package net.minecraft.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.decoration.ArmorStandEntity;

@Environment(EnvType.CLIENT)
public class ArmorStandArmorEntityModel extends BipedEntityModel<ArmorStandEntity> {
   public ArmorStandArmorEntityModel(float f) {
      this(f, 64, 32);
   }

   protected ArmorStandArmorEntityModel(float scale, int textureWidth, int textureHeight) {
      super(scale, 0.0F, textureWidth, textureHeight);
   }

   public void setAngles(ArmorStandEntity arg, float f, float g, float h, float i, float j) {
      this.head.pitch = (float) (Math.PI / 180.0) * arg.getHeadRotation().getPitch();
      this.head.yaw = (float) (Math.PI / 180.0) * arg.getHeadRotation().getYaw();
      this.head.roll = (float) (Math.PI / 180.0) * arg.getHeadRotation().getRoll();
      this.head.setPivot(0.0F, 1.0F, 0.0F);
      this.torso.pitch = (float) (Math.PI / 180.0) * arg.getBodyRotation().getPitch();
      this.torso.yaw = (float) (Math.PI / 180.0) * arg.getBodyRotation().getYaw();
      this.torso.roll = (float) (Math.PI / 180.0) * arg.getBodyRotation().getRoll();
      this.leftArm.pitch = (float) (Math.PI / 180.0) * arg.getLeftArmRotation().getPitch();
      this.leftArm.yaw = (float) (Math.PI / 180.0) * arg.getLeftArmRotation().getYaw();
      this.leftArm.roll = (float) (Math.PI / 180.0) * arg.getLeftArmRotation().getRoll();
      this.rightArm.pitch = (float) (Math.PI / 180.0) * arg.getRightArmRotation().getPitch();
      this.rightArm.yaw = (float) (Math.PI / 180.0) * arg.getRightArmRotation().getYaw();
      this.rightArm.roll = (float) (Math.PI / 180.0) * arg.getRightArmRotation().getRoll();
      this.leftLeg.pitch = (float) (Math.PI / 180.0) * arg.getLeftLegRotation().getPitch();
      this.leftLeg.yaw = (float) (Math.PI / 180.0) * arg.getLeftLegRotation().getYaw();
      this.leftLeg.roll = (float) (Math.PI / 180.0) * arg.getLeftLegRotation().getRoll();
      this.leftLeg.setPivot(1.9F, 11.0F, 0.0F);
      this.rightLeg.pitch = (float) (Math.PI / 180.0) * arg.getRightLegRotation().getPitch();
      this.rightLeg.yaw = (float) (Math.PI / 180.0) * arg.getRightLegRotation().getYaw();
      this.rightLeg.roll = (float) (Math.PI / 180.0) * arg.getRightLegRotation().getRoll();
      this.rightLeg.setPivot(-1.9F, 11.0F, 0.0F);
      this.helmet.copyPositionAndRotation(this.head);
   }
}
