package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class DolphinEntityModel<T extends Entity> extends CompositeEntityModel<T> {
   private final ModelPart body;
   private final ModelPart tail;
   private final ModelPart flukes;

   public DolphinEntityModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      float f = 18.0F;
      float g = -8.0F;
      this.body = new ModelPart(this, 22, 0);
      this.body.addCuboid(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 13.0F);
      this.body.setPivot(0.0F, 22.0F, -5.0F);
      ModelPart lv = new ModelPart(this, 51, 0);
      lv.addCuboid(-0.5F, 0.0F, 8.0F, 1.0F, 4.0F, 5.0F);
      lv.pitch = (float) (Math.PI / 3);
      this.body.addChild(lv);
      ModelPart lv2 = new ModelPart(this, 48, 20);
      lv2.mirror = true;
      lv2.addCuboid(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F);
      lv2.setPivot(2.0F, -2.0F, 4.0F);
      lv2.pitch = (float) (Math.PI / 3);
      lv2.roll = (float) (Math.PI * 2.0 / 3.0);
      this.body.addChild(lv2);
      ModelPart lv3 = new ModelPart(this, 48, 20);
      lv3.addCuboid(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F);
      lv3.setPivot(-2.0F, -2.0F, 4.0F);
      lv3.pitch = (float) (Math.PI / 3);
      lv3.roll = (float) (-Math.PI * 2.0 / 3.0);
      this.body.addChild(lv3);
      this.tail = new ModelPart(this, 0, 19);
      this.tail.addCuboid(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 11.0F);
      this.tail.setPivot(0.0F, -2.5F, 11.0F);
      this.tail.pitch = -0.10471976F;
      this.body.addChild(this.tail);
      this.flukes = new ModelPart(this, 19, 20);
      this.flukes.addCuboid(-5.0F, -0.5F, 0.0F, 10.0F, 1.0F, 6.0F);
      this.flukes.setPivot(0.0F, 0.0F, 9.0F);
      this.flukes.pitch = 0.0F;
      this.tail.addChild(this.flukes);
      ModelPart lv4 = new ModelPart(this, 0, 0);
      lv4.addCuboid(-4.0F, -3.0F, -3.0F, 8.0F, 7.0F, 6.0F);
      lv4.setPivot(0.0F, -4.0F, -3.0F);
      ModelPart lv5 = new ModelPart(this, 0, 13);
      lv5.addCuboid(-1.0F, 2.0F, -7.0F, 2.0F, 2.0F, 4.0F);
      lv4.addChild(lv5);
      this.body.addChild(lv4);
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return ImmutableList.of(this.body);
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      this.body.pitch = headPitch * (float) (Math.PI / 180.0);
      this.body.yaw = headYaw * (float) (Math.PI / 180.0);
      if (Entity.squaredHorizontalLength(entity.getVelocity()) > 1.0E-7) {
         this.body.pitch = this.body.pitch + -0.05F + -0.05F * MathHelper.cos(animationProgress * 0.3F);
         this.tail.pitch = -0.1F * MathHelper.cos(animationProgress * 0.3F);
         this.flukes.pitch = -0.2F * MathHelper.cos(animationProgress * 0.3F);
      }
   }
}
