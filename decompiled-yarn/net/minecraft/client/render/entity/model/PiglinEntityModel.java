package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinActivity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.util.math.MathHelper;

public class PiglinEntityModel<T extends MobEntity> extends PlayerEntityModel<T> {
   public final ModelPart rightEar;
   public final ModelPart leftEar;
   private final ModelPart field_25634;
   private final ModelPart field_25635;
   private final ModelPart field_25632;
   private final ModelPart field_25633;

   public PiglinEntityModel(float scale, int textureWidth, int textureHeight) {
      super(scale, false);
      this.textureWidth = textureWidth;
      this.textureHeight = textureHeight;
      this.torso = new ModelPart(this, 16, 16);
      this.torso.addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, scale);
      this.head = new ModelPart(this);
      this.head.setTextureOffset(0, 0).addCuboid(-5.0F, -8.0F, -4.0F, 10.0F, 8.0F, 8.0F, scale);
      this.head.setTextureOffset(31, 1).addCuboid(-2.0F, -4.0F, -5.0F, 4.0F, 4.0F, 1.0F, scale);
      this.head.setTextureOffset(2, 4).addCuboid(2.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, scale);
      this.head.setTextureOffset(2, 0).addCuboid(-3.0F, -2.0F, -5.0F, 1.0F, 2.0F, 1.0F, scale);
      this.rightEar = new ModelPart(this);
      this.rightEar.setPivot(4.5F, -6.0F, 0.0F);
      this.rightEar.setTextureOffset(51, 6).addCuboid(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, scale);
      this.head.addChild(this.rightEar);
      this.leftEar = new ModelPart(this);
      this.leftEar.setPivot(-4.5F, -6.0F, 0.0F);
      this.leftEar.setTextureOffset(39, 6).addCuboid(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, scale);
      this.head.addChild(this.leftEar);
      this.helmet = new ModelPart(this);
      this.field_25634 = this.torso.method_29991();
      this.field_25635 = this.head.method_29991();
      this.field_25632 = this.leftArm.method_29991();
      this.field_25633 = this.leftArm.method_29991();
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      this.torso.copyPositionAndRotation(this.field_25634);
      this.head.copyPositionAndRotation(this.field_25635);
      this.leftArm.copyPositionAndRotation(this.field_25632);
      this.rightArm.copyPositionAndRotation(this.field_25633);
      super.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowmanxxxxxx = (float) (Math.PI / 6);
      float _snowmanxxxxxxx = _snowman * 0.1F + _snowman * 0.5F;
      float _snowmanxxxxxxxx = 0.08F + _snowman * 0.4F;
      this.rightEar.roll = (float) (-Math.PI / 6) - MathHelper.cos(_snowmanxxxxxxx * 1.2F) * _snowmanxxxxxxxx;
      this.leftEar.roll = (float) (Math.PI / 6) + MathHelper.cos(_snowmanxxxxxxx) * _snowmanxxxxxxxx;
      if (_snowman instanceof AbstractPiglinEntity) {
         AbstractPiglinEntity _snowmanxxxxxxxxx = (AbstractPiglinEntity)_snowman;
         PiglinActivity _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getActivity();
         if (_snowmanxxxxxxxxxx == PiglinActivity.DANCING) {
            float _snowmanxxxxxxxxxxx = _snowman / 60.0F;
            this.leftEar.roll = (float) (Math.PI / 6) + (float) (Math.PI / 180.0) * MathHelper.sin(_snowmanxxxxxxxxxxx * 30.0F) * 10.0F;
            this.rightEar.roll = (float) (-Math.PI / 6) - (float) (Math.PI / 180.0) * MathHelper.cos(_snowmanxxxxxxxxxxx * 30.0F) * 10.0F;
            this.head.pivotX = MathHelper.sin(_snowmanxxxxxxxxxxx * 10.0F);
            this.head.pivotY = MathHelper.sin(_snowmanxxxxxxxxxxx * 40.0F) + 0.4F;
            this.rightArm.roll = (float) (Math.PI / 180.0) * (70.0F + MathHelper.cos(_snowmanxxxxxxxxxxx * 40.0F) * 10.0F);
            this.leftArm.roll = this.rightArm.roll * -1.0F;
            this.rightArm.pivotY = MathHelper.sin(_snowmanxxxxxxxxxxx * 40.0F) * 0.5F + 1.5F;
            this.leftArm.pivotY = MathHelper.sin(_snowmanxxxxxxxxxxx * 40.0F) * 0.5F + 1.5F;
            this.torso.pivotY = MathHelper.sin(_snowmanxxxxxxxxxxx * 40.0F) * 0.35F;
         } else if (_snowmanxxxxxxxxxx == PiglinActivity.ATTACKING_WITH_MELEE_WEAPON && this.handSwingProgress == 0.0F) {
            this.method_29354(_snowman);
         } else if (_snowmanxxxxxxxxxx == PiglinActivity.CROSSBOW_HOLD) {
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, !_snowman.isLeftHanded());
         } else if (_snowmanxxxxxxxxxx == PiglinActivity.CROSSBOW_CHARGE) {
            CrossbowPosing.charge(this.rightArm, this.leftArm, _snowman, !_snowman.isLeftHanded());
         } else if (_snowmanxxxxxxxxxx == PiglinActivity.ADMIRING_ITEM) {
            this.head.pitch = 0.5F;
            this.head.yaw = 0.0F;
            if (_snowman.isLeftHanded()) {
               this.rightArm.yaw = -0.5F;
               this.rightArm.pitch = -0.9F;
            } else {
               this.leftArm.yaw = 0.5F;
               this.leftArm.pitch = -0.9F;
            }
         }
      } else if (_snowman.getType() == EntityType.ZOMBIFIED_PIGLIN) {
         CrossbowPosing.method_29352(this.leftArm, this.rightArm, _snowman.isAttacking(), this.handSwingProgress, _snowman);
      }

      this.leftPantLeg.copyPositionAndRotation(this.leftLeg);
      this.rightPantLeg.copyPositionAndRotation(this.rightLeg);
      this.leftSleeve.copyPositionAndRotation(this.leftArm);
      this.rightSleeve.copyPositionAndRotation(this.rightArm);
      this.jacket.copyPositionAndRotation(this.torso);
      this.helmet.copyPositionAndRotation(this.head);
   }

   protected void method_29353(T _snowman, float _snowman) {
      if (this.handSwingProgress > 0.0F && _snowman instanceof PiglinEntity && ((PiglinEntity)_snowman).getActivity() == PiglinActivity.ATTACKING_WITH_MELEE_WEAPON) {
         CrossbowPosing.method_29351(this.rightArm, this.leftArm, _snowman, this.handSwingProgress, _snowman);
      } else {
         super.method_29353(_snowman, _snowman);
      }
   }

   private void method_29354(T _snowman) {
      if (_snowman.isLeftHanded()) {
         this.leftArm.pitch = -1.8F;
      } else {
         this.rightArm.pitch = -1.8F;
      }
   }
}
