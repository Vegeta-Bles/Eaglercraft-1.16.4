package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.mob.Hoglin;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class HoglinEntityModel<T extends MobEntity & Hoglin> extends AnimalModel<T> {
   private final ModelPart head;
   private final ModelPart rightEar;
   private final ModelPart leftEar;
   private final ModelPart torso;
   private final ModelPart field_22231;
   private final ModelPart field_22232;
   private final ModelPart field_22233;
   private final ModelPart field_22234;
   private final ModelPart field_25484;

   public HoglinEntityModel() {
      super(true, 8.0F, 6.0F, 1.9F, 2.0F, 24.0F);
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.torso = new ModelPart(this);
      this.torso.setPivot(0.0F, 7.0F, 0.0F);
      this.torso.setTextureOffset(1, 1).addCuboid(-8.0F, -7.0F, -13.0F, 16.0F, 14.0F, 26.0F);
      this.field_25484 = new ModelPart(this);
      this.field_25484.setPivot(0.0F, -14.0F, -5.0F);
      this.field_25484.setTextureOffset(90, 33).addCuboid(0.0F, 0.0F, -9.0F, 0.0F, 10.0F, 19.0F, 0.001F);
      this.torso.addChild(this.field_25484);
      this.head = new ModelPart(this);
      this.head.setPivot(0.0F, 2.0F, -12.0F);
      this.head.setTextureOffset(61, 1).addCuboid(-7.0F, -3.0F, -19.0F, 14.0F, 6.0F, 19.0F);
      this.rightEar = new ModelPart(this);
      this.rightEar.setPivot(-6.0F, -2.0F, -3.0F);
      this.rightEar.setTextureOffset(1, 1).addCuboid(-6.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F);
      this.rightEar.roll = (float) (-Math.PI * 2.0 / 9.0);
      this.head.addChild(this.rightEar);
      this.leftEar = new ModelPart(this);
      this.leftEar.setPivot(6.0F, -2.0F, -3.0F);
      this.leftEar.setTextureOffset(1, 6).addCuboid(0.0F, -1.0F, -2.0F, 6.0F, 1.0F, 4.0F);
      this.leftEar.roll = (float) (Math.PI * 2.0 / 9.0);
      this.head.addChild(this.leftEar);
      ModelPart _snowman = new ModelPart(this);
      _snowman.setPivot(-7.0F, 2.0F, -12.0F);
      _snowman.setTextureOffset(10, 13).addCuboid(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F);
      this.head.addChild(_snowman);
      ModelPart _snowmanx = new ModelPart(this);
      _snowmanx.setPivot(7.0F, 2.0F, -12.0F);
      _snowmanx.setTextureOffset(1, 13).addCuboid(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F);
      this.head.addChild(_snowmanx);
      this.head.pitch = 0.87266463F;
      int _snowmanxx = 14;
      int _snowmanxxx = 11;
      this.field_22231 = new ModelPart(this);
      this.field_22231.setPivot(-4.0F, 10.0F, -8.5F);
      this.field_22231.setTextureOffset(66, 42).addCuboid(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F);
      this.field_22232 = new ModelPart(this);
      this.field_22232.setPivot(4.0F, 10.0F, -8.5F);
      this.field_22232.setTextureOffset(41, 42).addCuboid(-3.0F, 0.0F, -3.0F, 6.0F, 14.0F, 6.0F);
      this.field_22233 = new ModelPart(this);
      this.field_22233.setPivot(-5.0F, 13.0F, 10.0F);
      this.field_22233.setTextureOffset(21, 45).addCuboid(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F);
      this.field_22234 = new ModelPart(this);
      this.field_22234.setPivot(5.0F, 13.0F, 10.0F);
      this.field_22234.setTextureOffset(0, 45).addCuboid(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F);
   }

   @Override
   protected Iterable<ModelPart> getHeadParts() {
      return ImmutableList.of(this.head);
   }

   @Override
   protected Iterable<ModelPart> getBodyParts() {
      return ImmutableList.of(this.torso, this.field_22231, this.field_22232, this.field_22233, this.field_22234);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      this.rightEar.roll = (float) (-Math.PI * 2.0 / 9.0) - _snowman * MathHelper.sin(_snowman);
      this.leftEar.roll = (float) (Math.PI * 2.0 / 9.0) + _snowman * MathHelper.sin(_snowman);
      this.head.yaw = _snowman * (float) (Math.PI / 180.0);
      int _snowmanxxxxxx = _snowman.getMovementCooldownTicks();
      float _snowmanxxxxxxx = 1.0F - (float)MathHelper.abs(10 - 2 * _snowmanxxxxxx) / 10.0F;
      this.head.pitch = MathHelper.lerp(_snowmanxxxxxxx, 0.87266463F, (float) (-Math.PI / 9));
      if (_snowman.isBaby()) {
         this.head.pivotY = MathHelper.lerp(_snowmanxxxxxxx, 2.0F, 5.0F);
         this.field_25484.pivotZ = -3.0F;
      } else {
         this.head.pivotY = 2.0F;
         this.field_25484.pivotZ = -7.0F;
      }

      float _snowmanxxxxxxxx = 1.2F;
      this.field_22231.pitch = MathHelper.cos(_snowman) * 1.2F * _snowman;
      this.field_22232.pitch = MathHelper.cos(_snowman + (float) Math.PI) * 1.2F * _snowman;
      this.field_22233.pitch = this.field_22232.pitch;
      this.field_22234.pitch = this.field_22231.pitch;
   }
}
