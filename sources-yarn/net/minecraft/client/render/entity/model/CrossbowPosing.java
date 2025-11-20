package net.minecraft.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class CrossbowPosing {
   public static void hold(ModelPart holdingArm, ModelPart otherArm, ModelPart head, boolean rightArmed) {
      ModelPart lv = rightArmed ? holdingArm : otherArm;
      ModelPart lv2 = rightArmed ? otherArm : holdingArm;
      lv.yaw = (rightArmed ? -0.3F : 0.3F) + head.yaw;
      lv2.yaw = (rightArmed ? 0.6F : -0.6F) + head.yaw;
      lv.pitch = (float) (-Math.PI / 2) + head.pitch + 0.1F;
      lv2.pitch = -1.5F + head.pitch;
   }

   public static void charge(ModelPart holdingArm, ModelPart pullingArm, LivingEntity actor, boolean rightArmed) {
      ModelPart lv = rightArmed ? holdingArm : pullingArm;
      ModelPart lv2 = rightArmed ? pullingArm : holdingArm;
      lv.yaw = rightArmed ? -0.8F : 0.8F;
      lv.pitch = -0.97079635F;
      lv2.pitch = lv.pitch;
      float f = (float)CrossbowItem.getPullTime(actor.getActiveItem());
      float g = MathHelper.clamp((float)actor.getItemUseTime(), 0.0F, f);
      float h = g / f;
      lv2.yaw = MathHelper.lerp(h, 0.4F, 0.85F) * (float)(rightArmed ? 1 : -1);
      lv2.pitch = MathHelper.lerp(h, lv2.pitch, (float) (-Math.PI / 2));
   }

   public static <T extends MobEntity> void method_29351(ModelPart arg, ModelPart arg2, T arg3, float f, float g) {
      float h = MathHelper.sin(f * (float) Math.PI);
      float i = MathHelper.sin((1.0F - (1.0F - f) * (1.0F - f)) * (float) Math.PI);
      arg.roll = 0.0F;
      arg2.roll = 0.0F;
      arg.yaw = (float) (Math.PI / 20);
      arg2.yaw = (float) (-Math.PI / 20);
      if (arg3.getMainArm() == Arm.RIGHT) {
         arg.pitch = -1.8849558F + MathHelper.cos(g * 0.09F) * 0.15F;
         arg2.pitch = -0.0F + MathHelper.cos(g * 0.19F) * 0.5F;
         arg.pitch += h * 2.2F - i * 0.4F;
         arg2.pitch += h * 1.2F - i * 0.4F;
      } else {
         arg.pitch = -0.0F + MathHelper.cos(g * 0.19F) * 0.5F;
         arg2.pitch = -1.8849558F + MathHelper.cos(g * 0.09F) * 0.15F;
         arg.pitch += h * 1.2F - i * 0.4F;
         arg2.pitch += h * 2.2F - i * 0.4F;
      }

      method_29350(arg, arg2, g);
   }

   public static void method_29350(ModelPart arg, ModelPart arg2, float f) {
      arg.roll = arg.roll + MathHelper.cos(f * 0.09F) * 0.05F + 0.05F;
      arg2.roll = arg2.roll - (MathHelper.cos(f * 0.09F) * 0.05F + 0.05F);
      arg.pitch = arg.pitch + MathHelper.sin(f * 0.067F) * 0.05F;
      arg2.pitch = arg2.pitch - MathHelper.sin(f * 0.067F) * 0.05F;
   }

   public static void method_29352(ModelPart arg, ModelPart arg2, boolean bl, float f, float g) {
      float h = MathHelper.sin(f * (float) Math.PI);
      float i = MathHelper.sin((1.0F - (1.0F - f) * (1.0F - f)) * (float) Math.PI);
      arg2.roll = 0.0F;
      arg.roll = 0.0F;
      arg2.yaw = -(0.1F - h * 0.6F);
      arg.yaw = 0.1F - h * 0.6F;
      float j = (float) -Math.PI / (bl ? 1.5F : 2.25F);
      arg2.pitch = j;
      arg.pitch = j;
      arg2.pitch += h * 1.2F - i * 0.4F;
      arg.pitch += h * 1.2F - i * 0.4F;
      method_29350(arg2, arg, g);
   }
}
