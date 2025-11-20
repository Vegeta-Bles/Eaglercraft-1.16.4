package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class DolphinEntityModel<T extends Entity> extends CompositeEntityModel<T> {
   private final ModelPart body;
   private final ModelPart tail;
   private final ModelPart flukes;

   public DolphinEntityModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      float _snowman = 18.0F;
      float _snowmanx = -8.0F;
      this.body = new ModelPart(this, 22, 0);
      this.body.addCuboid(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 13.0F);
      this.body.setPivot(0.0F, 22.0F, -5.0F);
      ModelPart _snowmanxx = new ModelPart(this, 51, 0);
      _snowmanxx.addCuboid(-0.5F, 0.0F, 8.0F, 1.0F, 4.0F, 5.0F);
      _snowmanxx.pitch = (float) (Math.PI / 3);
      this.body.addChild(_snowmanxx);
      ModelPart _snowmanxxx = new ModelPart(this, 48, 20);
      _snowmanxxx.mirror = true;
      _snowmanxxx.addCuboid(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F);
      _snowmanxxx.setPivot(2.0F, -2.0F, 4.0F);
      _snowmanxxx.pitch = (float) (Math.PI / 3);
      _snowmanxxx.roll = (float) (Math.PI * 2.0 / 3.0);
      this.body.addChild(_snowmanxxx);
      ModelPart _snowmanxxxx = new ModelPart(this, 48, 20);
      _snowmanxxxx.addCuboid(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F);
      _snowmanxxxx.setPivot(-2.0F, -2.0F, 4.0F);
      _snowmanxxxx.pitch = (float) (Math.PI / 3);
      _snowmanxxxx.roll = (float) (-Math.PI * 2.0 / 3.0);
      this.body.addChild(_snowmanxxxx);
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
      ModelPart _snowmanxxxxx = new ModelPart(this, 0, 0);
      _snowmanxxxxx.addCuboid(-4.0F, -3.0F, -3.0F, 8.0F, 7.0F, 6.0F);
      _snowmanxxxxx.setPivot(0.0F, -4.0F, -3.0F);
      ModelPart _snowmanxxxxxx = new ModelPart(this, 0, 13);
      _snowmanxxxxxx.addCuboid(-1.0F, 2.0F, -7.0F, 2.0F, 2.0F, 4.0F);
      _snowmanxxxxx.addChild(_snowmanxxxxxx);
      this.body.addChild(_snowmanxxxxx);
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
