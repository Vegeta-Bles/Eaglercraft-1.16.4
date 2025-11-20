package net.minecraft.client.render.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class WitchEntityModel<T extends Entity> extends VillagerResemblingModel<T> {
   private boolean liftingNose;
   private final ModelPart mole = new ModelPart(this).setTextureSize(64, 128);

   public WitchEntityModel(float f) {
      super(f, 64, 128);
      this.mole.setPivot(0.0F, -2.0F, 0.0F);
      this.mole.setTextureOffset(0, 0).addCuboid(0.0F, 3.0F, -6.75F, 1.0F, 1.0F, 1.0F, -0.25F);
      this.nose.addChild(this.mole);
      this.head = new ModelPart(this).setTextureSize(64, 128);
      this.head.setPivot(0.0F, 0.0F, 0.0F);
      this.head.setTextureOffset(0, 0).addCuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, f);
      this.field_17141 = new ModelPart(this).setTextureSize(64, 128);
      this.field_17141.setPivot(-5.0F, -10.03125F, -5.0F);
      this.field_17141.setTextureOffset(0, 64).addCuboid(0.0F, 0.0F, 0.0F, 10.0F, 2.0F, 10.0F);
      this.head.addChild(this.field_17141);
      this.head.addChild(this.nose);
      ModelPart lv = new ModelPart(this).setTextureSize(64, 128);
      lv.setPivot(1.75F, -4.0F, 2.0F);
      lv.setTextureOffset(0, 76).addCuboid(0.0F, 0.0F, 0.0F, 7.0F, 4.0F, 7.0F);
      lv.pitch = -0.05235988F;
      lv.roll = 0.02617994F;
      this.field_17141.addChild(lv);
      ModelPart lv2 = new ModelPart(this).setTextureSize(64, 128);
      lv2.setPivot(1.75F, -4.0F, 2.0F);
      lv2.setTextureOffset(0, 87).addCuboid(0.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F);
      lv2.pitch = -0.10471976F;
      lv2.roll = 0.05235988F;
      lv.addChild(lv2);
      ModelPart lv3 = new ModelPart(this).setTextureSize(64, 128);
      lv3.setPivot(1.75F, -2.0F, 2.0F);
      lv3.setTextureOffset(0, 95).addCuboid(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.25F);
      lv3.pitch = (float) (-Math.PI / 15);
      lv3.roll = 0.10471976F;
      lv2.addChild(lv3);
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
      this.nose.setPivot(0.0F, -2.0F, 0.0F);
      float k = 0.01F * (float)(entity.getEntityId() % 10);
      this.nose.pitch = MathHelper.sin((float)entity.age * k) * 4.5F * (float) (Math.PI / 180.0);
      this.nose.yaw = 0.0F;
      this.nose.roll = MathHelper.cos((float)entity.age * k) * 2.5F * (float) (Math.PI / 180.0);
      if (this.liftingNose) {
         this.nose.setPivot(0.0F, 1.0F, -1.5F);
         this.nose.pitch = -0.9F;
      }
   }

   public ModelPart getNose() {
      return this.nose;
   }

   public void setLiftingNose(boolean bl) {
      this.liftingNose = bl;
   }
}
