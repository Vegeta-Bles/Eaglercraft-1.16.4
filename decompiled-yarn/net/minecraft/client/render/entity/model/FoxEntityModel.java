package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.math.MathHelper;

public class FoxEntityModel<T extends FoxEntity> extends AnimalModel<T> {
   public final ModelPart head;
   private final ModelPart rightEar;
   private final ModelPart leftEar;
   private final ModelPart nose;
   private final ModelPart torso;
   private final ModelPart rightBackLeg;
   private final ModelPart leftBackLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart tail;
   private float legPitchModifier;

   public FoxEntityModel() {
      super(true, 8.0F, 3.35F);
      this.textureWidth = 48;
      this.textureHeight = 32;
      this.head = new ModelPart(this, 1, 5);
      this.head.addCuboid(-3.0F, -2.0F, -5.0F, 8.0F, 6.0F, 6.0F);
      this.head.setPivot(-1.0F, 16.5F, -3.0F);
      this.rightEar = new ModelPart(this, 8, 1);
      this.rightEar.addCuboid(-3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F);
      this.leftEar = new ModelPart(this, 15, 1);
      this.leftEar.addCuboid(3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F);
      this.nose = new ModelPart(this, 6, 18);
      this.nose.addCuboid(-1.0F, 2.01F, -8.0F, 4.0F, 2.0F, 3.0F);
      this.head.addChild(this.rightEar);
      this.head.addChild(this.leftEar);
      this.head.addChild(this.nose);
      this.torso = new ModelPart(this, 24, 15);
      this.torso.addCuboid(-3.0F, 3.999F, -3.5F, 6.0F, 11.0F, 6.0F);
      this.torso.setPivot(0.0F, 16.0F, -6.0F);
      float _snowman = 0.001F;
      this.rightBackLeg = new ModelPart(this, 13, 24);
      this.rightBackLeg.addCuboid(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.001F);
      this.rightBackLeg.setPivot(-5.0F, 17.5F, 7.0F);
      this.leftBackLeg = new ModelPart(this, 4, 24);
      this.leftBackLeg.addCuboid(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.001F);
      this.leftBackLeg.setPivot(-1.0F, 17.5F, 7.0F);
      this.rightFrontLeg = new ModelPart(this, 13, 24);
      this.rightFrontLeg.addCuboid(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.001F);
      this.rightFrontLeg.setPivot(-5.0F, 17.5F, 0.0F);
      this.leftFrontLeg = new ModelPart(this, 4, 24);
      this.leftFrontLeg.addCuboid(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F, 0.001F);
      this.leftFrontLeg.setPivot(-1.0F, 17.5F, 0.0F);
      this.tail = new ModelPart(this, 30, 0);
      this.tail.addCuboid(2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F);
      this.tail.setPivot(-4.0F, 15.0F, -1.0F);
      this.torso.addChild(this.tail);
   }

   public void animateModel(T _snowman, float _snowman, float _snowman, float _snowman) {
      this.torso.pitch = (float) (Math.PI / 2);
      this.tail.pitch = -0.05235988F;
      this.rightBackLeg.pitch = MathHelper.cos(_snowman * 0.6662F) * 1.4F * _snowman;
      this.leftBackLeg.pitch = MathHelper.cos(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.rightFrontLeg.pitch = MathHelper.cos(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
      this.leftFrontLeg.pitch = MathHelper.cos(_snowman * 0.6662F) * 1.4F * _snowman;
      this.head.setPivot(-1.0F, 16.5F, -3.0F);
      this.head.yaw = 0.0F;
      this.head.roll = _snowman.getHeadRoll(_snowman);
      this.rightBackLeg.visible = true;
      this.leftBackLeg.visible = true;
      this.rightFrontLeg.visible = true;
      this.leftFrontLeg.visible = true;
      this.torso.setPivot(0.0F, 16.0F, -6.0F);
      this.torso.roll = 0.0F;
      this.rightBackLeg.setPivot(-5.0F, 17.5F, 7.0F);
      this.leftBackLeg.setPivot(-1.0F, 17.5F, 7.0F);
      if (_snowman.isInSneakingPose()) {
         this.torso.pitch = 1.6755161F;
         float _snowmanxxxx = _snowman.getBodyRotationHeightOffset(_snowman);
         this.torso.setPivot(0.0F, 16.0F + _snowman.getBodyRotationHeightOffset(_snowman), -6.0F);
         this.head.setPivot(-1.0F, 16.5F + _snowmanxxxx, -3.0F);
         this.head.yaw = 0.0F;
      } else if (_snowman.isSleeping()) {
         this.torso.roll = (float) (-Math.PI / 2);
         this.torso.setPivot(0.0F, 21.0F, -6.0F);
         this.tail.pitch = (float) (-Math.PI * 5.0 / 6.0);
         if (this.child) {
            this.tail.pitch = -2.1816616F;
            this.torso.setPivot(0.0F, 21.0F, -2.0F);
         }

         this.head.setPivot(1.0F, 19.49F, -3.0F);
         this.head.pitch = 0.0F;
         this.head.yaw = (float) (-Math.PI * 2.0 / 3.0);
         this.head.roll = 0.0F;
         this.rightBackLeg.visible = false;
         this.leftBackLeg.visible = false;
         this.rightFrontLeg.visible = false;
         this.leftFrontLeg.visible = false;
      } else if (_snowman.isSitting()) {
         this.torso.pitch = (float) (Math.PI / 6);
         this.torso.setPivot(0.0F, 9.0F, -3.0F);
         this.tail.pitch = (float) (Math.PI / 4);
         this.tail.setPivot(-4.0F, 15.0F, -2.0F);
         this.head.setPivot(-1.0F, 10.0F, -0.25F);
         this.head.pitch = 0.0F;
         this.head.yaw = 0.0F;
         if (this.child) {
            this.head.setPivot(-1.0F, 13.0F, -3.75F);
         }

         this.rightBackLeg.pitch = (float) (-Math.PI * 5.0 / 12.0);
         this.rightBackLeg.setPivot(-5.0F, 21.5F, 6.75F);
         this.leftBackLeg.pitch = (float) (-Math.PI * 5.0 / 12.0);
         this.leftBackLeg.setPivot(-1.0F, 21.5F, 6.75F);
         this.rightFrontLeg.pitch = (float) (-Math.PI / 12);
         this.leftFrontLeg.pitch = (float) (-Math.PI / 12);
      }
   }

   @Override
   protected Iterable<ModelPart> getHeadParts() {
      return ImmutableList.of(this.head);
   }

   @Override
   protected Iterable<ModelPart> getBodyParts() {
      return ImmutableList.of(this.torso, this.rightBackLeg, this.leftBackLeg, this.rightFrontLeg, this.leftFrontLeg);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      if (!_snowman.isSleeping() && !_snowman.isWalking() && !_snowman.isInSneakingPose()) {
         this.head.pitch = _snowman * (float) (Math.PI / 180.0);
         this.head.yaw = _snowman * (float) (Math.PI / 180.0);
      }

      if (_snowman.isSleeping()) {
         this.head.pitch = 0.0F;
         this.head.yaw = (float) (-Math.PI * 2.0 / 3.0);
         this.head.roll = MathHelper.cos(_snowman * 0.027F) / 22.0F;
      }

      if (_snowman.isInSneakingPose()) {
         float _snowmanxxxxxx = MathHelper.cos(_snowman) * 0.01F;
         this.torso.yaw = _snowmanxxxxxx;
         this.rightBackLeg.roll = _snowmanxxxxxx;
         this.leftBackLeg.roll = _snowmanxxxxxx;
         this.rightFrontLeg.roll = _snowmanxxxxxx / 2.0F;
         this.leftFrontLeg.roll = _snowmanxxxxxx / 2.0F;
      }

      if (_snowman.isWalking()) {
         float _snowmanxxxxxx = 0.1F;
         this.legPitchModifier += 0.67F;
         this.rightBackLeg.pitch = MathHelper.cos(this.legPitchModifier * 0.4662F) * 0.1F;
         this.leftBackLeg.pitch = MathHelper.cos(this.legPitchModifier * 0.4662F + (float) Math.PI) * 0.1F;
         this.rightFrontLeg.pitch = MathHelper.cos(this.legPitchModifier * 0.4662F + (float) Math.PI) * 0.1F;
         this.leftFrontLeg.pitch = MathHelper.cos(this.legPitchModifier * 0.4662F) * 0.1F;
      }
   }
}
