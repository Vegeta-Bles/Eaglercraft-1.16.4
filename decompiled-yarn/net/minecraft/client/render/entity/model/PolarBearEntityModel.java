package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.PolarBearEntity;

public class PolarBearEntityModel<T extends PolarBearEntity> extends QuadrupedEntityModel<T> {
   public PolarBearEntityModel() {
      super(12, 0.0F, true, 16.0F, 4.0F, 2.25F, 2.0F, 24);
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.head = new ModelPart(this, 0, 0);
      this.head.addCuboid(-3.5F, -3.0F, -3.0F, 7.0F, 7.0F, 7.0F, 0.0F);
      this.head.setPivot(0.0F, 10.0F, -16.0F);
      this.head.setTextureOffset(0, 44).addCuboid(-2.5F, 1.0F, -6.0F, 5.0F, 3.0F, 3.0F, 0.0F);
      this.head.setTextureOffset(26, 0).addCuboid(-4.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, 0.0F);
      ModelPart _snowman = this.head.setTextureOffset(26, 0);
      _snowman.mirror = true;
      _snowman.addCuboid(2.5F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, 0.0F);
      this.torso = new ModelPart(this);
      this.torso.setTextureOffset(0, 19).addCuboid(-5.0F, -13.0F, -7.0F, 14.0F, 14.0F, 11.0F, 0.0F);
      this.torso.setTextureOffset(39, 0).addCuboid(-4.0F, -25.0F, -7.0F, 12.0F, 12.0F, 10.0F, 0.0F);
      this.torso.setPivot(-2.0F, 9.0F, 12.0F);
      int _snowmanx = 10;
      this.backRightLeg = new ModelPart(this, 50, 22);
      this.backRightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F, 0.0F);
      this.backRightLeg.setPivot(-3.5F, 14.0F, 6.0F);
      this.backLeftLeg = new ModelPart(this, 50, 22);
      this.backLeftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 8.0F, 0.0F);
      this.backLeftLeg.setPivot(3.5F, 14.0F, 6.0F);
      this.frontRightLeg = new ModelPart(this, 50, 40);
      this.frontRightLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F, 0.0F);
      this.frontRightLeg.setPivot(-2.5F, 14.0F, -7.0F);
      this.frontLeftLeg = new ModelPart(this, 50, 40);
      this.frontLeftLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 10.0F, 6.0F, 0.0F);
      this.frontLeftLeg.setPivot(2.5F, 14.0F, -7.0F);
      this.backRightLeg.pivotX--;
      this.backLeftLeg.pivotX++;
      this.backRightLeg.pivotZ += 0.0F;
      this.backLeftLeg.pivotZ += 0.0F;
      this.frontRightLeg.pivotX--;
      this.frontLeftLeg.pivotX++;
      this.frontRightLeg.pivotZ--;
      this.frontLeftLeg.pivotZ--;
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      super.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      float _snowmanxxxxxx = _snowman - (float)_snowman.age;
      float _snowmanxxxxxxx = _snowman.getWarningAnimationProgress(_snowmanxxxxxx);
      _snowmanxxxxxxx *= _snowmanxxxxxxx;
      float _snowmanxxxxxxxx = 1.0F - _snowmanxxxxxxx;
      this.torso.pitch = (float) (Math.PI / 2) - _snowmanxxxxxxx * (float) Math.PI * 0.35F;
      this.torso.pivotY = 9.0F * _snowmanxxxxxxxx + 11.0F * _snowmanxxxxxxx;
      this.frontRightLeg.pivotY = 14.0F * _snowmanxxxxxxxx - 6.0F * _snowmanxxxxxxx;
      this.frontRightLeg.pivotZ = -8.0F * _snowmanxxxxxxxx - 4.0F * _snowmanxxxxxxx;
      this.frontRightLeg.pitch -= _snowmanxxxxxxx * (float) Math.PI * 0.45F;
      this.frontLeftLeg.pivotY = this.frontRightLeg.pivotY;
      this.frontLeftLeg.pivotZ = this.frontRightLeg.pivotZ;
      this.frontLeftLeg.pitch -= _snowmanxxxxxxx * (float) Math.PI * 0.45F;
      if (this.child) {
         this.head.pivotY = 10.0F * _snowmanxxxxxxxx - 9.0F * _snowmanxxxxxxx;
         this.head.pivotZ = -16.0F * _snowmanxxxxxxxx - 7.0F * _snowmanxxxxxxx;
      } else {
         this.head.pivotY = 10.0F * _snowmanxxxxxxxx - 14.0F * _snowmanxxxxxxx;
         this.head.pivotZ = -16.0F * _snowmanxxxxxxxx - 3.0F * _snowmanxxxxxxx;
      }

      this.head.pitch += _snowmanxxxxxxx * (float) Math.PI * 0.15F;
   }
}
