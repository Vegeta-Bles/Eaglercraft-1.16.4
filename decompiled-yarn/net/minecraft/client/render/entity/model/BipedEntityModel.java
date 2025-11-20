package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import java.util.function.Function;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BipedEntityModel<T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {
   public ModelPart head;
   public ModelPart helmet;
   public ModelPart torso;
   public ModelPart rightArm;
   public ModelPart leftArm;
   public ModelPart rightLeg;
   public ModelPart leftLeg;
   public BipedEntityModel.ArmPose leftArmPose = BipedEntityModel.ArmPose.EMPTY;
   public BipedEntityModel.ArmPose rightArmPose = BipedEntityModel.ArmPose.EMPTY;
   public boolean sneaking;
   public float leaningPitch;

   public BipedEntityModel(float scale) {
      this(RenderLayer::getEntityCutoutNoCull, scale, 0.0F, 64, 32);
   }

   protected BipedEntityModel(float scale, float pivotY, int textureWidth, int textureHeight) {
      this(RenderLayer::getEntityCutoutNoCull, scale, pivotY, textureWidth, textureHeight);
   }

   public BipedEntityModel(Function<Identifier, RenderLayer> texturedLayerFactory, float scale, float pivotY, int textureWidth, int textureHeight) {
      super(texturedLayerFactory, true, 16.0F, 0.0F, 2.0F, 2.0F, 24.0F);
      this.textureWidth = textureWidth;
      this.textureHeight = textureHeight;
      this.head = new ModelPart(this, 0, 0);
      this.head.addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, scale);
      this.head.setPivot(0.0F, 0.0F + pivotY, 0.0F);
      this.helmet = new ModelPart(this, 32, 0);
      this.helmet.addCuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, scale + 0.5F);
      this.helmet.setPivot(0.0F, 0.0F + pivotY, 0.0F);
      this.torso = new ModelPart(this, 16, 16);
      this.torso.addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, scale);
      this.torso.setPivot(0.0F, 0.0F + pivotY, 0.0F);
      this.rightArm = new ModelPart(this, 40, 16);
      this.rightArm.addCuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);
      this.rightArm.setPivot(-5.0F, 2.0F + pivotY, 0.0F);
      this.leftArm = new ModelPart(this, 40, 16);
      this.leftArm.mirror = true;
      this.leftArm.addCuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);
      this.leftArm.setPivot(5.0F, 2.0F + pivotY, 0.0F);
      this.rightLeg = new ModelPart(this, 0, 16);
      this.rightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);
      this.rightLeg.setPivot(-1.9F, 12.0F + pivotY, 0.0F);
      this.leftLeg = new ModelPart(this, 0, 16);
      this.leftLeg.mirror = true;
      this.leftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, scale);
      this.leftLeg.setPivot(1.9F, 12.0F + pivotY, 0.0F);
   }

   @Override
   protected Iterable<ModelPart> getHeadParts() {
      return ImmutableList.of(this.head);
   }

   @Override
   protected Iterable<ModelPart> getBodyParts() {
      return ImmutableList.of(this.torso, this.rightArm, this.leftArm, this.rightLeg, this.leftLeg, this.helmet);
   }

   public void animateModel(T _snowman, float _snowman, float _snowman, float _snowman) {
      this.leaningPitch = _snowman.getLeaningPitch(_snowman);
      super.animateModel(_snowman, _snowman, _snowman, _snowman);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      boolean _snowmanxxxxxx = _snowman.getRoll() > 4;
      boolean _snowmanxxxxxxx = _snowman.isInSwimmingPose();
      this.head.yaw = _snowman * (float) (Math.PI / 180.0);
      if (_snowmanxxxxxx) {
         this.head.pitch = (float) (-Math.PI / 4);
      } else if (this.leaningPitch > 0.0F) {
         if (_snowmanxxxxxxx) {
            this.head.pitch = this.lerpAngle(this.leaningPitch, this.head.pitch, (float) (-Math.PI / 4));
         } else {
            this.head.pitch = this.lerpAngle(this.leaningPitch, this.head.pitch, _snowman * (float) (Math.PI / 180.0));
         }
      } else {
         this.head.pitch = _snowman * (float) (Math.PI / 180.0);
      }

      this.torso.yaw = 0.0F;
      this.rightArm.pivotZ = 0.0F;
      this.rightArm.pivotX = -5.0F;
      this.leftArm.pivotZ = 0.0F;
      this.leftArm.pivotX = 5.0F;
      float _snowmanxxxxxxxx = 1.0F;
      if (_snowmanxxxxxx) {
         _snowmanxxxxxxxx = (float)_snowman.getVelocity().lengthSquared();
         _snowmanxxxxxxxx /= 0.2F;
         _snowmanxxxxxxxx *= _snowmanxxxxxxxx * _snowmanxxxxxxxx;
      }

      if (_snowmanxxxxxxxx < 1.0F) {
         _snowmanxxxxxxxx = 1.0F;
      }

      this.rightArm.pitch = MathHelper.cos(_snowman * 0.6662F + (float) Math.PI) * 2.0F * _snowman * 0.5F / _snowmanxxxxxxxx;
      this.leftArm.pitch = MathHelper.cos(_snowman * 0.6662F) * 2.0F * _snowman * 0.5F / _snowmanxxxxxxxx;
      this.rightArm.roll = 0.0F;
      this.leftArm.roll = 0.0F;
      this.rightLeg.pitch = MathHelper.cos(_snowman * 0.6662F) * 1.4F * _snowman / _snowmanxxxxxxxx;
      this.leftLeg.pitch = MathHelper.cos(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman / _snowmanxxxxxxxx;
      this.rightLeg.yaw = 0.0F;
      this.leftLeg.yaw = 0.0F;
      this.rightLeg.roll = 0.0F;
      this.leftLeg.roll = 0.0F;
      if (this.riding) {
         this.rightArm.pitch += (float) (-Math.PI / 5);
         this.leftArm.pitch += (float) (-Math.PI / 5);
         this.rightLeg.pitch = -1.4137167F;
         this.rightLeg.yaw = (float) (Math.PI / 10);
         this.rightLeg.roll = 0.07853982F;
         this.leftLeg.pitch = -1.4137167F;
         this.leftLeg.yaw = (float) (-Math.PI / 10);
         this.leftLeg.roll = -0.07853982F;
      }

      this.rightArm.yaw = 0.0F;
      this.leftArm.yaw = 0.0F;
      boolean _snowmanxxxxxxxxx = _snowman.getMainArm() == Arm.RIGHT;
      boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx ? this.leftArmPose.method_30156() : this.rightArmPose.method_30156();
      if (_snowmanxxxxxxxxx != _snowmanxxxxxxxxxx) {
         this.method_30155(_snowman);
         this.method_30154(_snowman);
      } else {
         this.method_30154(_snowman);
         this.method_30155(_snowman);
      }

      this.method_29353(_snowman, _snowman);
      if (this.sneaking) {
         this.torso.pitch = 0.5F;
         this.rightArm.pitch += 0.4F;
         this.leftArm.pitch += 0.4F;
         this.rightLeg.pivotZ = 4.0F;
         this.leftLeg.pivotZ = 4.0F;
         this.rightLeg.pivotY = 12.2F;
         this.leftLeg.pivotY = 12.2F;
         this.head.pivotY = 4.2F;
         this.torso.pivotY = 3.2F;
         this.leftArm.pivotY = 5.2F;
         this.rightArm.pivotY = 5.2F;
      } else {
         this.torso.pitch = 0.0F;
         this.rightLeg.pivotZ = 0.1F;
         this.leftLeg.pivotZ = 0.1F;
         this.rightLeg.pivotY = 12.0F;
         this.leftLeg.pivotY = 12.0F;
         this.head.pivotY = 0.0F;
         this.torso.pivotY = 0.0F;
         this.leftArm.pivotY = 2.0F;
         this.rightArm.pivotY = 2.0F;
      }

      CrossbowPosing.method_29350(this.rightArm, this.leftArm, _snowman);
      if (this.leaningPitch > 0.0F) {
         float _snowmanxxxxxxxxxxx = _snowman % 26.0F;
         Arm _snowmanxxxxxxxxxxxx = this.getPreferredArm(_snowman);
         float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx == Arm.RIGHT && this.handSwingProgress > 0.0F ? 0.0F : this.leaningPitch;
         float _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx == Arm.LEFT && this.handSwingProgress > 0.0F ? 0.0F : this.leaningPitch;
         if (_snowmanxxxxxxxxxxx < 14.0F) {
            this.leftArm.pitch = this.lerpAngle(_snowmanxxxxxxxxxxxxxx, this.leftArm.pitch, 0.0F);
            this.rightArm.pitch = MathHelper.lerp(_snowmanxxxxxxxxxxxxx, this.rightArm.pitch, 0.0F);
            this.leftArm.yaw = this.lerpAngle(_snowmanxxxxxxxxxxxxxx, this.leftArm.yaw, (float) Math.PI);
            this.rightArm.yaw = MathHelper.lerp(_snowmanxxxxxxxxxxxxx, this.rightArm.yaw, (float) Math.PI);
            this.leftArm.roll = this.lerpAngle(
               _snowmanxxxxxxxxxxxxxx, this.leftArm.roll, (float) Math.PI + 1.8707964F * this.method_2807(_snowmanxxxxxxxxxxx) / this.method_2807(14.0F)
            );
            this.rightArm.roll = MathHelper.lerp(
               _snowmanxxxxxxxxxxxxx, this.rightArm.roll, (float) Math.PI - 1.8707964F * this.method_2807(_snowmanxxxxxxxxxxx) / this.method_2807(14.0F)
            );
         } else if (_snowmanxxxxxxxxxxx >= 14.0F && _snowmanxxxxxxxxxxx < 22.0F) {
            float _snowmanxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxx - 14.0F) / 8.0F;
            this.leftArm.pitch = this.lerpAngle(_snowmanxxxxxxxxxxxxxx, this.leftArm.pitch, (float) (Math.PI / 2) * _snowmanxxxxxxxxxxxxxxx);
            this.rightArm.pitch = MathHelper.lerp(_snowmanxxxxxxxxxxxxx, this.rightArm.pitch, (float) (Math.PI / 2) * _snowmanxxxxxxxxxxxxxxx);
            this.leftArm.yaw = this.lerpAngle(_snowmanxxxxxxxxxxxxxx, this.leftArm.yaw, (float) Math.PI);
            this.rightArm.yaw = MathHelper.lerp(_snowmanxxxxxxxxxxxxx, this.rightArm.yaw, (float) Math.PI);
            this.leftArm.roll = this.lerpAngle(_snowmanxxxxxxxxxxxxxx, this.leftArm.roll, 5.012389F - 1.8707964F * _snowmanxxxxxxxxxxxxxxx);
            this.rightArm.roll = MathHelper.lerp(_snowmanxxxxxxxxxxxxx, this.rightArm.roll, 1.2707963F + 1.8707964F * _snowmanxxxxxxxxxxxxxxx);
         } else if (_snowmanxxxxxxxxxxx >= 22.0F && _snowmanxxxxxxxxxxx < 26.0F) {
            float _snowmanxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxx - 22.0F) / 4.0F;
            this.leftArm.pitch = this.lerpAngle(_snowmanxxxxxxxxxxxxxx, this.leftArm.pitch, (float) (Math.PI / 2) - (float) (Math.PI / 2) * _snowmanxxxxxxxxxxxxxxx);
            this.rightArm.pitch = MathHelper.lerp(_snowmanxxxxxxxxxxxxx, this.rightArm.pitch, (float) (Math.PI / 2) - (float) (Math.PI / 2) * _snowmanxxxxxxxxxxxxxxx);
            this.leftArm.yaw = this.lerpAngle(_snowmanxxxxxxxxxxxxxx, this.leftArm.yaw, (float) Math.PI);
            this.rightArm.yaw = MathHelper.lerp(_snowmanxxxxxxxxxxxxx, this.rightArm.yaw, (float) Math.PI);
            this.leftArm.roll = this.lerpAngle(_snowmanxxxxxxxxxxxxxx, this.leftArm.roll, (float) Math.PI);
            this.rightArm.roll = MathHelper.lerp(_snowmanxxxxxxxxxxxxx, this.rightArm.roll, (float) Math.PI);
         }

         float _snowmanxxxxxxxxxxxxxxx = 0.3F;
         float _snowmanxxxxxxxxxxxxxxxx = 0.33333334F;
         this.leftLeg.pitch = MathHelper.lerp(this.leaningPitch, this.leftLeg.pitch, 0.3F * MathHelper.cos(_snowman * 0.33333334F + (float) Math.PI));
         this.rightLeg.pitch = MathHelper.lerp(this.leaningPitch, this.rightLeg.pitch, 0.3F * MathHelper.cos(_snowman * 0.33333334F));
      }

      this.helmet.copyPositionAndRotation(this.head);
   }

   private void method_30154(T _snowman) {
      switch (this.rightArmPose) {
         case EMPTY:
            this.rightArm.yaw = 0.0F;
            break;
         case BLOCK:
            this.rightArm.pitch = this.rightArm.pitch * 0.5F - 0.9424779F;
            this.rightArm.yaw = (float) (-Math.PI / 6);
            break;
         case ITEM:
            this.rightArm.pitch = this.rightArm.pitch * 0.5F - (float) (Math.PI / 10);
            this.rightArm.yaw = 0.0F;
            break;
         case THROW_SPEAR:
            this.rightArm.pitch = this.rightArm.pitch * 0.5F - (float) Math.PI;
            this.rightArm.yaw = 0.0F;
            break;
         case BOW_AND_ARROW:
            this.rightArm.yaw = -0.1F + this.head.yaw;
            this.leftArm.yaw = 0.1F + this.head.yaw + 0.4F;
            this.rightArm.pitch = (float) (-Math.PI / 2) + this.head.pitch;
            this.leftArm.pitch = (float) (-Math.PI / 2) + this.head.pitch;
            break;
         case CROSSBOW_CHARGE:
            CrossbowPosing.charge(this.rightArm, this.leftArm, _snowman, true);
            break;
         case CROSSBOW_HOLD:
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, true);
      }
   }

   private void method_30155(T _snowman) {
      switch (this.leftArmPose) {
         case EMPTY:
            this.leftArm.yaw = 0.0F;
            break;
         case BLOCK:
            this.leftArm.pitch = this.leftArm.pitch * 0.5F - 0.9424779F;
            this.leftArm.yaw = (float) (Math.PI / 6);
            break;
         case ITEM:
            this.leftArm.pitch = this.leftArm.pitch * 0.5F - (float) (Math.PI / 10);
            this.leftArm.yaw = 0.0F;
            break;
         case THROW_SPEAR:
            this.leftArm.pitch = this.leftArm.pitch * 0.5F - (float) Math.PI;
            this.leftArm.yaw = 0.0F;
            break;
         case BOW_AND_ARROW:
            this.rightArm.yaw = -0.1F + this.head.yaw - 0.4F;
            this.leftArm.yaw = 0.1F + this.head.yaw;
            this.rightArm.pitch = (float) (-Math.PI / 2) + this.head.pitch;
            this.leftArm.pitch = (float) (-Math.PI / 2) + this.head.pitch;
            break;
         case CROSSBOW_CHARGE:
            CrossbowPosing.charge(this.rightArm, this.leftArm, _snowman, false);
            break;
         case CROSSBOW_HOLD:
            CrossbowPosing.hold(this.rightArm, this.leftArm, this.head, false);
      }
   }

   protected void method_29353(T _snowman, float _snowman) {
      if (!(this.handSwingProgress <= 0.0F)) {
         Arm _snowmanxx = this.getPreferredArm(_snowman);
         ModelPart _snowmanxxx = this.getArm(_snowmanxx);
         float _snowmanxxxx = this.handSwingProgress;
         this.torso.yaw = MathHelper.sin(MathHelper.sqrt(_snowmanxxxx) * (float) (Math.PI * 2)) * 0.2F;
         if (_snowmanxx == Arm.LEFT) {
            this.torso.yaw *= -1.0F;
         }

         this.rightArm.pivotZ = MathHelper.sin(this.torso.yaw) * 5.0F;
         this.rightArm.pivotX = -MathHelper.cos(this.torso.yaw) * 5.0F;
         this.leftArm.pivotZ = -MathHelper.sin(this.torso.yaw) * 5.0F;
         this.leftArm.pivotX = MathHelper.cos(this.torso.yaw) * 5.0F;
         this.rightArm.yaw = this.rightArm.yaw + this.torso.yaw;
         this.leftArm.yaw = this.leftArm.yaw + this.torso.yaw;
         this.leftArm.pitch = this.leftArm.pitch + this.torso.yaw;
         _snowmanxxxx = 1.0F - this.handSwingProgress;
         _snowmanxxxx *= _snowmanxxxx;
         _snowmanxxxx *= _snowmanxxxx;
         _snowmanxxxx = 1.0F - _snowmanxxxx;
         float _snowmanxxxxx = MathHelper.sin(_snowmanxxxx * (float) Math.PI);
         float _snowmanxxxxxx = MathHelper.sin(this.handSwingProgress * (float) Math.PI) * -(this.head.pitch - 0.7F) * 0.75F;
         _snowmanxxx.pitch = (float)((double)_snowmanxxx.pitch - ((double)_snowmanxxxxx * 1.2 + (double)_snowmanxxxxxx));
         _snowmanxxx.yaw = _snowmanxxx.yaw + this.torso.yaw * 2.0F;
         _snowmanxxx.roll = _snowmanxxx.roll + MathHelper.sin(this.handSwingProgress * (float) Math.PI) * -0.4F;
      }
   }

   protected float lerpAngle(float _snowman, float _snowman, float _snowman) {
      float _snowmanxxx = (_snowman - _snowman) % (float) (Math.PI * 2);
      if (_snowmanxxx < (float) -Math.PI) {
         _snowmanxxx += (float) (Math.PI * 2);
      }

      if (_snowmanxxx >= (float) Math.PI) {
         _snowmanxxx -= (float) (Math.PI * 2);
      }

      return _snowman + _snowman * _snowmanxxx;
   }

   private float method_2807(float _snowman) {
      return -65.0F * _snowman + _snowman * _snowman;
   }

   public void setAttributes(BipedEntityModel<T> _snowman) {
      super.copyStateTo(_snowman);
      _snowman.leftArmPose = this.leftArmPose;
      _snowman.rightArmPose = this.rightArmPose;
      _snowman.sneaking = this.sneaking;
      _snowman.head.copyPositionAndRotation(this.head);
      _snowman.helmet.copyPositionAndRotation(this.helmet);
      _snowman.torso.copyPositionAndRotation(this.torso);
      _snowman.rightArm.copyPositionAndRotation(this.rightArm);
      _snowman.leftArm.copyPositionAndRotation(this.leftArm);
      _snowman.rightLeg.copyPositionAndRotation(this.rightLeg);
      _snowman.leftLeg.copyPositionAndRotation(this.leftLeg);
   }

   public void setVisible(boolean visible) {
      this.head.visible = visible;
      this.helmet.visible = visible;
      this.torso.visible = visible;
      this.rightArm.visible = visible;
      this.leftArm.visible = visible;
      this.rightLeg.visible = visible;
      this.leftLeg.visible = visible;
   }

   @Override
   public void setArmAngle(Arm arm, MatrixStack matrices) {
      this.getArm(arm).rotate(matrices);
   }

   protected ModelPart getArm(Arm arm) {
      return arm == Arm.LEFT ? this.leftArm : this.rightArm;
   }

   @Override
   public ModelPart getHead() {
      return this.head;
   }

   protected Arm getPreferredArm(T entity) {
      Arm _snowman = entity.getMainArm();
      return entity.preferredHand == Hand.MAIN_HAND ? _snowman : _snowman.getOpposite();
   }

   public static enum ArmPose {
      EMPTY(false),
      ITEM(false),
      BLOCK(false),
      BOW_AND_ARROW(true),
      THROW_SPEAR(false),
      CROSSBOW_CHARGE(true),
      CROSSBOW_HOLD(true);

      private final boolean field_25722;

      private ArmPose(boolean _snowman) {
         this.field_25722 = _snowman;
      }

      public boolean method_30156() {
         return this.field_25722;
      }
   }
}
