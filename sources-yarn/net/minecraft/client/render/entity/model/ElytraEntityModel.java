package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ElytraEntityModel<T extends LivingEntity> extends AnimalModel<T> {
   private final ModelPart field_3364;
   private final ModelPart field_3365 = new ModelPart(this, 22, 0);

   public ElytraEntityModel() {
      this.field_3365.addCuboid(-10.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, 1.0F);
      this.field_3364 = new ModelPart(this, 22, 0);
      this.field_3364.mirror = true;
      this.field_3364.addCuboid(0.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, 1.0F);
   }

   @Override
   protected Iterable<ModelPart> getHeadParts() {
      return ImmutableList.of();
   }

   @Override
   protected Iterable<ModelPart> getBodyParts() {
      return ImmutableList.of(this.field_3365, this.field_3364);
   }

   public void setAngles(T arg, float f, float g, float h, float i, float j) {
      float k = (float) (Math.PI / 12);
      float l = (float) (-Math.PI / 12);
      float m = 0.0F;
      float n = 0.0F;
      if (arg.isFallFlying()) {
         float o = 1.0F;
         Vec3d lv = arg.getVelocity();
         if (lv.y < 0.0) {
            Vec3d lv2 = lv.normalize();
            o = 1.0F - (float)Math.pow(-lv2.y, 1.5);
         }

         k = o * (float) (Math.PI / 9) + (1.0F - o) * k;
         l = o * (float) (-Math.PI / 2) + (1.0F - o) * l;
      } else if (arg.isInSneakingPose()) {
         k = (float) (Math.PI * 2.0 / 9.0);
         l = (float) (-Math.PI / 4);
         m = 3.0F;
         n = 0.08726646F;
      }

      this.field_3365.pivotX = 5.0F;
      this.field_3365.pivotY = m;
      if (arg instanceof AbstractClientPlayerEntity) {
         AbstractClientPlayerEntity lv3 = (AbstractClientPlayerEntity)arg;
         lv3.elytraPitch = (float)((double)lv3.elytraPitch + (double)(k - lv3.elytraPitch) * 0.1);
         lv3.elytraYaw = (float)((double)lv3.elytraYaw + (double)(n - lv3.elytraYaw) * 0.1);
         lv3.elytraRoll = (float)((double)lv3.elytraRoll + (double)(l - lv3.elytraRoll) * 0.1);
         this.field_3365.pitch = lv3.elytraPitch;
         this.field_3365.yaw = lv3.elytraYaw;
         this.field_3365.roll = lv3.elytraRoll;
      } else {
         this.field_3365.pitch = k;
         this.field_3365.roll = l;
         this.field_3365.yaw = n;
      }

      this.field_3364.pivotX = -this.field_3365.pivotX;
      this.field_3364.yaw = -this.field_3365.yaw;
      this.field_3364.pivotY = this.field_3365.pivotY;
      this.field_3364.pitch = this.field_3365.pitch;
      this.field_3364.roll = -this.field_3365.roll;
   }
}
