package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.util.math.MathHelper;

public class TurtleEntityModel<T extends TurtleEntity> extends QuadrupedEntityModel<T> {
   private final ModelPart plastron;

   public TurtleEntityModel(float scale) {
      super(12, scale, true, 120.0F, 0.0F, 9.0F, 6.0F, 120);
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.head = new ModelPart(this, 3, 0);
      this.head.addCuboid(-3.0F, -1.0F, -3.0F, 6.0F, 5.0F, 6.0F, 0.0F);
      this.head.setPivot(0.0F, 19.0F, -10.0F);
      this.torso = new ModelPart(this);
      this.torso.setTextureOffset(7, 37).addCuboid(-9.5F, 3.0F, -10.0F, 19.0F, 20.0F, 6.0F, 0.0F);
      this.torso.setTextureOffset(31, 1).addCuboid(-5.5F, 3.0F, -13.0F, 11.0F, 18.0F, 3.0F, 0.0F);
      this.torso.setPivot(0.0F, 11.0F, -10.0F);
      this.plastron = new ModelPart(this);
      this.plastron.setTextureOffset(70, 33).addCuboid(-4.5F, 3.0F, -14.0F, 9.0F, 18.0F, 1.0F, 0.0F);
      this.plastron.setPivot(0.0F, 11.0F, -10.0F);
      int _snowman = 1;
      this.backRightLeg = new ModelPart(this, 1, 23);
      this.backRightLeg.addCuboid(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
      this.backRightLeg.setPivot(-3.5F, 22.0F, 11.0F);
      this.backLeftLeg = new ModelPart(this, 1, 12);
      this.backLeftLeg.addCuboid(-2.0F, 0.0F, 0.0F, 4.0F, 1.0F, 10.0F, 0.0F);
      this.backLeftLeg.setPivot(3.5F, 22.0F, 11.0F);
      this.frontRightLeg = new ModelPart(this, 27, 30);
      this.frontRightLeg.addCuboid(-13.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
      this.frontRightLeg.setPivot(-5.0F, 21.0F, -4.0F);
      this.frontLeftLeg = new ModelPart(this, 27, 24);
      this.frontLeftLeg.addCuboid(0.0F, 0.0F, -2.0F, 13.0F, 1.0F, 5.0F, 0.0F);
      this.frontLeftLeg.setPivot(5.0F, 21.0F, -4.0F);
   }

   @Override
   protected Iterable<ModelPart> getBodyParts() {
      return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.plastron));
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      super.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.backRightLeg.pitch = MathHelper.cos(_snowman * 0.6662F * 0.6F) * 0.5F * _snowman;
      this.backLeftLeg.pitch = MathHelper.cos(_snowman * 0.6662F * 0.6F + (float) Math.PI) * 0.5F * _snowman;
      this.frontRightLeg.roll = MathHelper.cos(_snowman * 0.6662F * 0.6F + (float) Math.PI) * 0.5F * _snowman;
      this.frontLeftLeg.roll = MathHelper.cos(_snowman * 0.6662F * 0.6F) * 0.5F * _snowman;
      this.frontRightLeg.pitch = 0.0F;
      this.frontLeftLeg.pitch = 0.0F;
      this.frontRightLeg.yaw = 0.0F;
      this.frontLeftLeg.yaw = 0.0F;
      this.backRightLeg.yaw = 0.0F;
      this.backLeftLeg.yaw = 0.0F;
      this.plastron.pitch = (float) (Math.PI / 2);
      if (!_snowman.isTouchingWater() && _snowman.isOnGround()) {
         float _snowmanxxxxxx = _snowman.isDiggingSand() ? 4.0F : 1.0F;
         float _snowmanxxxxxxx = _snowman.isDiggingSand() ? 2.0F : 1.0F;
         float _snowmanxxxxxxxx = 5.0F;
         this.frontRightLeg.yaw = MathHelper.cos(_snowmanxxxxxx * _snowman * 5.0F + (float) Math.PI) * 8.0F * _snowman * _snowmanxxxxxxx;
         this.frontRightLeg.roll = 0.0F;
         this.frontLeftLeg.yaw = MathHelper.cos(_snowmanxxxxxx * _snowman * 5.0F) * 8.0F * _snowman * _snowmanxxxxxxx;
         this.frontLeftLeg.roll = 0.0F;
         this.backRightLeg.yaw = MathHelper.cos(_snowman * 5.0F + (float) Math.PI) * 3.0F * _snowman;
         this.backRightLeg.pitch = 0.0F;
         this.backLeftLeg.yaw = MathHelper.cos(_snowman * 5.0F) * 3.0F * _snowman;
         this.backLeftLeg.pitch = 0.0F;
      }

      this.plastron.visible = !this.child && _snowman.hasEgg();
   }

   @Override
   public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
      boolean _snowman = this.plastron.visible;
      if (_snowman) {
         matrices.push();
         matrices.translate(0.0, -0.08F, 0.0);
      }

      super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
      if (_snowman) {
         matrices.pop();
      }
   }
}
