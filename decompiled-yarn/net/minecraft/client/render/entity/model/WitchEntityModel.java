package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class WitchEntityModel<T extends Entity> extends VillagerResemblingModel<T> {
   private boolean liftingNose;
   private final ModelPart mole = new ModelPart(this).setTextureSize(64, 128);

   public WitchEntityModel(float _snowman) {
      super(_snowman, 64, 128);
      this.mole.setPivot(0.0F, -2.0F, 0.0F);
      this.mole.setTextureOffset(0, 0).addCuboid(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, -0.25F);
      this.nose.addChild(this.mole);
      this.head = new ModelPart(this).setTextureSize(64, 128);
      this.head.setPivot(0.0F, 0.0F, 0.0F);
      this.head.setTextureOffset(0, 0).addCuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, _snowman);
      this.field_17141 = new ModelPart(this).setTextureSize(64, 128);
      this.field_17141.setPivot(-5.0F, -10.03125F, -5.0F);
      this.field_17141.setTextureOffset(0, 64).addCuboid(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F);
      this.head.addChild(this.field_17141);
      this.head.addChild(this.nose);
      ModelPart _snowmanx = new ModelPart(this).setTextureSize(64, 128);
      _snowmanx.setPivot(1.75F, -4.0F, 2.0F);
      _snowmanx.setTextureOffset(0, 76).addCuboid(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F);
      _snowmanx.pitch = -0.05235988F;
      _snowmanx.roll = 0.02617994F;
      this.field_17141.addChild(_snowmanx);
      ModelPart _snowmanxx = new ModelPart(this).setTextureSize(64, 128);
      _snowmanxx.setPivot(1.75F, -4.0F, 2.0F);
      _snowmanxx.setTextureOffset(0, 87).addCuboid(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F);
      _snowmanxx.pitch = -0.10471976F;
      _snowmanxx.roll = 0.05235988F;
      _snowmanx.addChild(_snowmanxx);
      ModelPart _snowmanxxx = new ModelPart(this).setTextureSize(64, 128);
      _snowmanxxx.setPivot(1.75F, -2.0F, 2.0F);
      _snowmanxxx.setTextureOffset(0, 95).addCuboid(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.25F);
      _snowmanxxx.pitch = (float) (-Math.PI / 15);
      _snowmanxxx.roll = 0.10471976F;
      _snowmanxx.addChild(_snowmanxxx);
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
      this.nose.setPivot(0.0F, -2.0F, 0.0F);
      float _snowman = 0.01F * (float)(entity.getEntityId() % 10);
      this.nose.pitch = MathHelper.sin((float)entity.age * _snowman) * 4.5F * (float) (Math.PI / 180.0);
      this.nose.yaw = 0.0F;
      this.nose.roll = MathHelper.cos((float)entity.age * _snowman) * 2.5F * (float) (Math.PI / 180.0);
      if (this.liftingNose) {
         this.nose.setPivot(0.0F, 1.0F, -1.5F);
         this.nose.pitch = -0.9F;
      }
   }

   public ModelPart getNose() {
      return this.nose;
   }

   public void setLiftingNose(boolean _snowman) {
      this.liftingNose = _snowman;
   }
}
