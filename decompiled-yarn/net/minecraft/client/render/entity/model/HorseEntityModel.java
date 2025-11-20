package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.util.math.MathHelper;

public class HorseEntityModel<T extends HorseBaseEntity> extends AnimalModel<T> {
   protected final ModelPart torso;
   protected final ModelPart head;
   private final ModelPart leftBackLeg;
   private final ModelPart rightBackLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart field_20930;
   private final ModelPart field_20931;
   private final ModelPart field_20932;
   private final ModelPart field_20933;
   private final ModelPart tail;
   private final ModelPart[] field_3304;
   private final ModelPart[] field_3301;

   public HorseEntityModel(float scale) {
      super(true, 16.2F, 1.36F, 2.7272F, 2.0F, 20.0F);
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.torso = new ModelPart(this, 0, 32);
      this.torso.addCuboid(-5.0F, -8.0F, -17.0F, 10.0F, 10.0F, 22.0F, 0.05F);
      this.torso.setPivot(0.0F, 11.0F, 5.0F);
      this.head = new ModelPart(this, 0, 35);
      this.head.addCuboid(-2.05F, -6.0F, -2.0F, 4.0F, 12.0F, 7.0F);
      this.head.pitch = (float) (Math.PI / 6);
      ModelPart _snowman = new ModelPart(this, 0, 13);
      _snowman.addCuboid(-3.0F, -11.0F, -2.0F, 6.0F, 5.0F, 7.0F, scale);
      ModelPart _snowmanx = new ModelPart(this, 56, 36);
      _snowmanx.addCuboid(-1.0F, -11.0F, 5.01F, 2.0F, 16.0F, 2.0F, scale);
      ModelPart _snowmanxx = new ModelPart(this, 0, 25);
      _snowmanxx.addCuboid(-2.0F, -11.0F, -7.0F, 4.0F, 5.0F, 5.0F, scale);
      this.head.addChild(_snowman);
      this.head.addChild(_snowmanx);
      this.head.addChild(_snowmanxx);
      this.method_2789(this.head);
      this.leftBackLeg = new ModelPart(this, 48, 21);
      this.leftBackLeg.mirror = true;
      this.leftBackLeg.addCuboid(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, scale);
      this.leftBackLeg.setPivot(4.0F, 14.0F, 7.0F);
      this.rightBackLeg = new ModelPart(this, 48, 21);
      this.rightBackLeg.addCuboid(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, scale);
      this.rightBackLeg.setPivot(-4.0F, 14.0F, 7.0F);
      this.leftFrontLeg = new ModelPart(this, 48, 21);
      this.leftFrontLeg.mirror = true;
      this.leftFrontLeg.addCuboid(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, scale);
      this.leftFrontLeg.setPivot(4.0F, 6.0F, -12.0F);
      this.rightFrontLeg = new ModelPart(this, 48, 21);
      this.rightFrontLeg.addCuboid(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, scale);
      this.rightFrontLeg.setPivot(-4.0F, 6.0F, -12.0F);
      float _snowmanxxx = 5.5F;
      this.field_20930 = new ModelPart(this, 48, 21);
      this.field_20930.mirror = true;
      this.field_20930.addCuboid(-3.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, scale, scale + 5.5F, scale);
      this.field_20930.setPivot(4.0F, 14.0F, 7.0F);
      this.field_20931 = new ModelPart(this, 48, 21);
      this.field_20931.addCuboid(-1.0F, -1.01F, -1.0F, 4.0F, 11.0F, 4.0F, scale, scale + 5.5F, scale);
      this.field_20931.setPivot(-4.0F, 14.0F, 7.0F);
      this.field_20932 = new ModelPart(this, 48, 21);
      this.field_20932.mirror = true;
      this.field_20932.addCuboid(-3.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, scale, scale + 5.5F, scale);
      this.field_20932.setPivot(4.0F, 6.0F, -12.0F);
      this.field_20933 = new ModelPart(this, 48, 21);
      this.field_20933.addCuboid(-1.0F, -1.01F, -1.9F, 4.0F, 11.0F, 4.0F, scale, scale + 5.5F, scale);
      this.field_20933.setPivot(-4.0F, 6.0F, -12.0F);
      this.tail = new ModelPart(this, 42, 36);
      this.tail.addCuboid(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 4.0F, scale);
      this.tail.setPivot(0.0F, -5.0F, 2.0F);
      this.tail.pitch = (float) (Math.PI / 6);
      this.torso.addChild(this.tail);
      ModelPart _snowmanxxxx = new ModelPart(this, 26, 0);
      _snowmanxxxx.addCuboid(-5.0F, -8.0F, -9.0F, 10.0F, 9.0F, 9.0F, 0.5F);
      this.torso.addChild(_snowmanxxxx);
      ModelPart _snowmanxxxxx = new ModelPart(this, 29, 5);
      _snowmanxxxxx.addCuboid(2.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, scale);
      this.head.addChild(_snowmanxxxxx);
      ModelPart _snowmanxxxxxx = new ModelPart(this, 29, 5);
      _snowmanxxxxxx.addCuboid(-3.0F, -9.0F, -6.0F, 1.0F, 2.0F, 2.0F, scale);
      this.head.addChild(_snowmanxxxxxx);
      ModelPart _snowmanxxxxxxx = new ModelPart(this, 32, 2);
      _snowmanxxxxxxx.addCuboid(3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, scale);
      _snowmanxxxxxxx.pitch = (float) (-Math.PI / 6);
      this.head.addChild(_snowmanxxxxxxx);
      ModelPart _snowmanxxxxxxxx = new ModelPart(this, 32, 2);
      _snowmanxxxxxxxx.addCuboid(-3.1F, -6.0F, -8.0F, 0.0F, 3.0F, 16.0F, scale);
      _snowmanxxxxxxxx.pitch = (float) (-Math.PI / 6);
      this.head.addChild(_snowmanxxxxxxxx);
      ModelPart _snowmanxxxxxxxxx = new ModelPart(this, 1, 1);
      _snowmanxxxxxxxxx.addCuboid(-3.0F, -11.0F, -1.9F, 6.0F, 5.0F, 6.0F, 0.2F);
      this.head.addChild(_snowmanxxxxxxxxx);
      ModelPart _snowmanxxxxxxxxxx = new ModelPart(this, 19, 0);
      _snowmanxxxxxxxxxx.addCuboid(-2.0F, -11.0F, -4.0F, 4.0F, 5.0F, 2.0F, 0.2F);
      this.head.addChild(_snowmanxxxxxxxxxx);
      this.field_3304 = new ModelPart[]{_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx};
      this.field_3301 = new ModelPart[]{_snowmanxxxxxxx, _snowmanxxxxxxxx};
   }

   protected void method_2789(ModelPart _snowman) {
      ModelPart _snowmanx = new ModelPart(this, 19, 16);
      _snowmanx.addCuboid(0.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
      ModelPart _snowmanxx = new ModelPart(this, 19, 16);
      _snowmanxx.addCuboid(-2.55F, -13.0F, 4.0F, 2.0F, 3.0F, 1.0F, -0.001F);
      _snowman.addChild(_snowmanx);
      _snowman.addChild(_snowmanxx);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      boolean _snowmanxxxxxx = _snowman.isSaddled();
      boolean _snowmanxxxxxxx = _snowman.hasPassengers();

      for (ModelPart _snowmanxxxxxxxx : this.field_3304) {
         _snowmanxxxxxxxx.visible = _snowmanxxxxxx;
      }

      for (ModelPart _snowmanxxxxxxxx : this.field_3301) {
         _snowmanxxxxxxxx.visible = _snowmanxxxxxxx && _snowmanxxxxxx;
      }

      this.torso.pivotY = 11.0F;
   }

   @Override
   public Iterable<ModelPart> getHeadParts() {
      return ImmutableList.of(this.head);
   }

   @Override
   protected Iterable<ModelPart> getBodyParts() {
      return ImmutableList.of(
         this.torso,
         this.leftBackLeg,
         this.rightBackLeg,
         this.leftFrontLeg,
         this.rightFrontLeg,
         this.field_20930,
         this.field_20931,
         this.field_20932,
         this.field_20933
      );
   }

   public void animateModel(T _snowman, float _snowman, float _snowman, float _snowman) {
      super.animateModel(_snowman, _snowman, _snowman, _snowman);
      float _snowmanxxxx = MathHelper.lerpAngle(_snowman.prevBodyYaw, _snowman.bodyYaw, _snowman);
      float _snowmanxxxxx = MathHelper.lerpAngle(_snowman.prevHeadYaw, _snowman.headYaw, _snowman);
      float _snowmanxxxxxx = MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch);
      float _snowmanxxxxxxx = _snowmanxxxxx - _snowmanxxxx;
      float _snowmanxxxxxxxx = _snowmanxxxxxx * (float) (Math.PI / 180.0);
      if (_snowmanxxxxxxx > 20.0F) {
         _snowmanxxxxxxx = 20.0F;
      }

      if (_snowmanxxxxxxx < -20.0F) {
         _snowmanxxxxxxx = -20.0F;
      }

      if (_snowman > 0.2F) {
         _snowmanxxxxxxxx += MathHelper.cos(_snowman * 0.4F) * 0.15F * _snowman;
      }

      float _snowmanxxxxxxxxx = _snowman.getEatingGrassAnimationProgress(_snowman);
      float _snowmanxxxxxxxxxx = _snowman.getAngryAnimationProgress(_snowman);
      float _snowmanxxxxxxxxxxx = 1.0F - _snowmanxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxx = _snowman.getEatingAnimationProgress(_snowman);
      boolean _snowmanxxxxxxxxxxxxx = _snowman.tailWagTicks != 0;
      float _snowmanxxxxxxxxxxxxxx = (float)_snowman.age + _snowman;
      this.head.pivotY = 4.0F;
      this.head.pivotZ = -12.0F;
      this.torso.pitch = 0.0F;
      this.head.pitch = (float) (Math.PI / 6) + _snowmanxxxxxxxx;
      this.head.yaw = _snowmanxxxxxxx * (float) (Math.PI / 180.0);
      float _snowmanxxxxxxxxxxxxxxx = _snowman.isTouchingWater() ? 0.2F : 1.0F;
      float _snowmanxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxx * _snowman * 0.6662F + (float) Math.PI);
      float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx * 0.8F * _snowman;
      float _snowmanxxxxxxxxxxxxxxxxxx = (1.0F - Math.max(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx))
         * ((float) (Math.PI / 6) + _snowmanxxxxxxxx + _snowmanxxxxxxxxxxxx * MathHelper.sin(_snowmanxxxxxxxxxxxxxx) * 0.05F);
      this.head.pitch = _snowmanxxxxxxxxxx * ((float) (Math.PI / 12) + _snowmanxxxxxxxx)
         + _snowmanxxxxxxxxx * (2.1816616F + MathHelper.sin(_snowmanxxxxxxxxxxxxxx) * 0.05F)
         + _snowmanxxxxxxxxxxxxxxxxxx;
      this.head.yaw = _snowmanxxxxxxxxxx * _snowmanxxxxxxx * (float) (Math.PI / 180.0) + (1.0F - Math.max(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx)) * this.head.yaw;
      this.head.pivotY = _snowmanxxxxxxxxxx * -4.0F + _snowmanxxxxxxxxx * 11.0F + (1.0F - Math.max(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx)) * this.head.pivotY;
      this.head.pivotZ = _snowmanxxxxxxxxxx * -4.0F + _snowmanxxxxxxxxx * -12.0F + (1.0F - Math.max(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx)) * this.head.pivotZ;
      this.torso.pitch = _snowmanxxxxxxxxxx * (float) (-Math.PI / 4) + _snowmanxxxxxxxxxxx * this.torso.pitch;
      float _snowmanxxxxxxxxxxxxxxxxxxx = (float) (Math.PI / 12) * _snowmanxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxx * 0.6F + (float) Math.PI);
      this.leftFrontLeg.pivotY = 2.0F * _snowmanxxxxxxxxxx + 14.0F * _snowmanxxxxxxxxxxx;
      this.leftFrontLeg.pivotZ = -6.0F * _snowmanxxxxxxxxxx - 10.0F * _snowmanxxxxxxxxxxx;
      this.rightFrontLeg.pivotY = this.leftFrontLeg.pivotY;
      this.rightFrontLeg.pivotZ = this.leftFrontLeg.pivotZ;
      float _snowmanxxxxxxxxxxxxxxxxxxxxx = ((float) (-Math.PI / 3) + _snowmanxxxxxxxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxx = ((float) (-Math.PI / 3) - _snowmanxxxxxxxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx;
      this.leftBackLeg.pitch = _snowmanxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxx * 0.5F * _snowman * _snowmanxxxxxxxxxxx;
      this.rightBackLeg.pitch = _snowmanxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxx * 0.5F * _snowman * _snowmanxxxxxxxxxxx;
      this.leftFrontLeg.pitch = _snowmanxxxxxxxxxxxxxxxxxxxxx;
      this.rightFrontLeg.pitch = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
      this.tail.pitch = (float) (Math.PI / 6) + _snowman * 0.75F;
      this.tail.pivotY = -5.0F + _snowman;
      this.tail.pivotZ = 2.0F + _snowman * 2.0F;
      if (_snowmanxxxxxxxxxxxxx) {
         this.tail.yaw = MathHelper.cos(_snowmanxxxxxxxxxxxxxx * 0.7F);
      } else {
         this.tail.yaw = 0.0F;
      }

      this.field_20930.pivotY = this.leftBackLeg.pivotY;
      this.field_20930.pivotZ = this.leftBackLeg.pivotZ;
      this.field_20930.pitch = this.leftBackLeg.pitch;
      this.field_20931.pivotY = this.rightBackLeg.pivotY;
      this.field_20931.pivotZ = this.rightBackLeg.pivotZ;
      this.field_20931.pitch = this.rightBackLeg.pitch;
      this.field_20932.pivotY = this.leftFrontLeg.pivotY;
      this.field_20932.pivotZ = this.leftFrontLeg.pivotZ;
      this.field_20932.pitch = this.leftFrontLeg.pitch;
      this.field_20933.pivotY = this.rightFrontLeg.pivotY;
      this.field_20933.pivotZ = this.rightFrontLeg.pivotZ;
      this.field_20933.pitch = this.rightFrontLeg.pitch;
      boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowman.isBaby();
      this.leftBackLeg.visible = !_snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.rightBackLeg.visible = !_snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.leftFrontLeg.visible = !_snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.rightFrontLeg.visible = !_snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.field_20930.visible = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.field_20931.visible = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.field_20932.visible = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.field_20933.visible = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      this.torso.pivotY = _snowmanxxxxxxxxxxxxxxxxxxxxxxx ? 10.8F : 0.0F;
   }
}
