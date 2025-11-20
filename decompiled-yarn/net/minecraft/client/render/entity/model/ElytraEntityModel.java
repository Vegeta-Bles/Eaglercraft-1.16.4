package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

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

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxxx = (float) (Math.PI / 12);
      float _snowmanxxxxxxx = (float) (-Math.PI / 12);
      float _snowmanxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxx = 0.0F;
      if (_snowman.isFallFlying()) {
         float _snowmanxxxxxxxxxx = 1.0F;
         Vec3d _snowmanxxxxxxxxxxx = _snowman.getVelocity();
         if (_snowmanxxxxxxxxxxx.y < 0.0) {
            Vec3d _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.normalize();
            _snowmanxxxxxxxxxx = 1.0F - (float)Math.pow(-_snowmanxxxxxxxxxxxx.y, 1.5);
         }

         _snowmanxxxxxx = _snowmanxxxxxxxxxx * (float) (Math.PI / 9) + (1.0F - _snowmanxxxxxxxxxx) * _snowmanxxxxxx;
         _snowmanxxxxxxx = _snowmanxxxxxxxxxx * (float) (-Math.PI / 2) + (1.0F - _snowmanxxxxxxxxxx) * _snowmanxxxxxxx;
      } else if (_snowman.isInSneakingPose()) {
         _snowmanxxxxxx = (float) (Math.PI * 2.0 / 9.0);
         _snowmanxxxxxxx = (float) (-Math.PI / 4);
         _snowmanxxxxxxxx = 3.0F;
         _snowmanxxxxxxxxx = 0.08726646F;
      }

      this.field_3365.pivotX = 5.0F;
      this.field_3365.pivotY = _snowmanxxxxxxxx;
      if (_snowman instanceof AbstractClientPlayerEntity) {
         AbstractClientPlayerEntity _snowmanxxxxxxxxxx = (AbstractClientPlayerEntity)_snowman;
         _snowmanxxxxxxxxxx.elytraPitch = (float)((double)_snowmanxxxxxxxxxx.elytraPitch + (double)(_snowmanxxxxxx - _snowmanxxxxxxxxxx.elytraPitch) * 0.1);
         _snowmanxxxxxxxxxx.elytraYaw = (float)((double)_snowmanxxxxxxxxxx.elytraYaw + (double)(_snowmanxxxxxxxxx - _snowmanxxxxxxxxxx.elytraYaw) * 0.1);
         _snowmanxxxxxxxxxx.elytraRoll = (float)((double)_snowmanxxxxxxxxxx.elytraRoll + (double)(_snowmanxxxxxxx - _snowmanxxxxxxxxxx.elytraRoll) * 0.1);
         this.field_3365.pitch = _snowmanxxxxxxxxxx.elytraPitch;
         this.field_3365.yaw = _snowmanxxxxxxxxxx.elytraYaw;
         this.field_3365.roll = _snowmanxxxxxxxxxx.elytraRoll;
      } else {
         this.field_3365.pitch = _snowmanxxxxxx;
         this.field_3365.roll = _snowmanxxxxxxx;
         this.field_3365.yaw = _snowmanxxxxxxxxx;
      }

      this.field_3364.pivotX = -this.field_3365.pivotX;
      this.field_3364.yaw = -this.field_3365.yaw;
      this.field_3364.pivotY = this.field_3365.pivotY;
      this.field_3364.pitch = this.field_3365.pitch;
      this.field_3364.roll = -this.field_3365.roll;
   }
}
