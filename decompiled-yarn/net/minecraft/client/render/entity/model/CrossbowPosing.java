package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class CrossbowPosing {
   public static void hold(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArmed) {
      ModelPart _snowman = rightArmed ? holdingArm : otherArm;
      ModelPart _snowmanx = rightArmed ? otherArm : holdingArm;
      _snowman.yaw = (rightArmed ? -0.3F : 0.3F) + head.yaw;
      _snowmanx.yaw = (rightArmed ? 0.6F : -0.6F) + head.yaw;
      _snowman.pitch = (float) (-Math.PI / 2) + head.pitch + 0.1F;
      _snowmanx.pitch = -1.5F + head.pitch;
   }

   public static void charge(ModelPart holdingArm, ModelPart pullingArm, LivingEntity actor, boolean rightArmed) {
      ModelPart _snowman = rightArmed ? holdingArm : pullingArm;
      ModelPart _snowmanx = rightArmed ? pullingArm : holdingArm;
      _snowman.yaw = rightArmed ? -0.8F : 0.8F;
      _snowman.pitch = -0.97079635F;
      _snowmanx.pitch = _snowman.pitch;
      float _snowmanxx = (float)CrossbowItem.getPullTime(actor.getActiveItem());
      float _snowmanxxx = MathHelper.clamp((float)actor.getItemUseTime(), 0.0F, _snowmanxx);
      float _snowmanxxxx = _snowmanxxx / _snowmanxx;
      _snowmanx.yaw = MathHelper.lerp(_snowmanxxxx, 0.4F, 0.85F) * (float)(rightArmed ? 1 : -1);
      _snowmanx.pitch = MathHelper.lerp(_snowmanxxxx, _snowmanx.pitch, (float) (-Math.PI / 2));
   }

   public static <T extends MobEntity> void method_29351(ModelPart _snowman, ModelPart _snowman, T _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxx = MathHelper.sin(_snowman * (float) Math.PI);
      float _snowmanxxxxxx = MathHelper.sin((1.0F - (1.0F - _snowman) * (1.0F - _snowman)) * (float) Math.PI);
      _snowman.roll = 0.0F;
      _snowman.roll = 0.0F;
      _snowman.yaw = (float) (Math.PI / 20);
      _snowman.yaw = (float) (-Math.PI / 20);
      if (_snowman.getMainArm() == Arm.RIGHT) {
         _snowman.pitch = -1.8849558F + MathHelper.cos(_snowman * 0.09F) * 0.15F;
         _snowman.pitch = -0.0F + MathHelper.cos(_snowman * 0.19F) * 0.5F;
         _snowman.pitch += _snowmanxxxxx * 2.2F - _snowmanxxxxxx * 0.4F;
         _snowman.pitch += _snowmanxxxxx * 1.2F - _snowmanxxxxxx * 0.4F;
      } else {
         _snowman.pitch = -0.0F + MathHelper.cos(_snowman * 0.19F) * 0.5F;
         _snowman.pitch = -1.8849558F + MathHelper.cos(_snowman * 0.09F) * 0.15F;
         _snowman.pitch += _snowmanxxxxx * 1.2F - _snowmanxxxxxx * 0.4F;
         _snowman.pitch += _snowmanxxxxx * 2.2F - _snowmanxxxxxx * 0.4F;
      }

      method_29350(_snowman, _snowman, _snowman);
   }

   public static void method_29350(ModelPart _snowman, ModelPart _snowman, float _snowman) {
      _snowman.roll = _snowman.roll + MathHelper.cos(_snowman * 0.09F) * 0.05F + 0.05F;
      _snowman.roll = _snowman.roll - (MathHelper.cos(_snowman * 0.09F) * 0.05F + 0.05F);
      _snowman.pitch = _snowman.pitch + MathHelper.sin(_snowman * 0.067F) * 0.05F;
      _snowman.pitch = _snowman.pitch - MathHelper.sin(_snowman * 0.067F) * 0.05F;
   }

   public static void method_29352(ModelPart _snowman, ModelPart _snowman, boolean _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxx = MathHelper.sin(_snowman * (float) Math.PI);
      float _snowmanxxxxxx = MathHelper.sin((1.0F - (1.0F - _snowman) * (1.0F - _snowman)) * (float) Math.PI);
      _snowman.roll = 0.0F;
      _snowman.roll = 0.0F;
      _snowman.yaw = -(0.1F - _snowmanxxxxx * 0.6F);
      _snowman.yaw = 0.1F - _snowmanxxxxx * 0.6F;
      float _snowmanxxxxxxx = (float) -Math.PI / (_snowman ? 1.5F : 2.25F);
      _snowman.pitch = _snowmanxxxxxxx;
      _snowman.pitch = _snowmanxxxxxxx;
      _snowman.pitch += _snowmanxxxxx * 1.2F - _snowmanxxxxxx * 0.4F;
      _snowman.pitch += _snowmanxxxxx * 1.2F - _snowmanxxxxxx * 0.4F;
      method_29350(_snowman, _snowman, _snowman);
   }
}
