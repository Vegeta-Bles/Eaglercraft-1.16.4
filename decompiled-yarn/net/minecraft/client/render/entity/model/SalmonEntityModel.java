package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class SalmonEntityModel<T extends Entity> extends CompositeEntityModel<T> {
   private final ModelPart torso;
   private final ModelPart tail;
   private final ModelPart head;
   private final ModelPart rightFin;
   private final ModelPart leftFin;

   public SalmonEntityModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      int _snowman = 20;
      this.torso = new ModelPart(this, 0, 0);
      this.torso.addCuboid(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F);
      this.torso.setPivot(0.0F, 20.0F, 0.0F);
      this.tail = new ModelPart(this, 0, 13);
      this.tail.addCuboid(-1.5F, -2.5F, 0.0F, 3.0F, 5.0F, 8.0F);
      this.tail.setPivot(0.0F, 20.0F, 8.0F);
      this.head = new ModelPart(this, 22, 0);
      this.head.addCuboid(-1.0F, -2.0F, -3.0F, 2.0F, 4.0F, 3.0F);
      this.head.setPivot(0.0F, 20.0F, 0.0F);
      ModelPart _snowmanx = new ModelPart(this, 20, 10);
      _snowmanx.addCuboid(0.0F, -2.5F, 0.0F, 0.0F, 5.0F, 6.0F);
      _snowmanx.setPivot(0.0F, 0.0F, 8.0F);
      this.tail.addChild(_snowmanx);
      ModelPart _snowmanxx = new ModelPart(this, 2, 1);
      _snowmanxx.addCuboid(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 3.0F);
      _snowmanxx.setPivot(0.0F, -4.5F, 5.0F);
      this.torso.addChild(_snowmanxx);
      ModelPart _snowmanxxx = new ModelPart(this, 0, 2);
      _snowmanxxx.addCuboid(0.0F, 0.0F, 0.0F, 0.0F, 2.0F, 4.0F);
      _snowmanxxx.setPivot(0.0F, -4.5F, -1.0F);
      this.tail.addChild(_snowmanxxx);
      this.rightFin = new ModelPart(this, -4, 0);
      this.rightFin.addCuboid(-2.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F);
      this.rightFin.setPivot(-1.5F, 21.5F, 0.0F);
      this.rightFin.roll = (float) (-Math.PI / 4);
      this.leftFin = new ModelPart(this, 0, 0);
      this.leftFin.addCuboid(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F);
      this.leftFin.setPivot(1.5F, 21.5F, 0.0F);
      this.leftFin.roll = (float) (Math.PI / 4);
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return ImmutableList.of(this.torso, this.tail, this.head, this.rightFin, this.leftFin);
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      float _snowman = 1.0F;
      float _snowmanx = 1.0F;
      if (!entity.isTouchingWater()) {
         _snowman = 1.3F;
         _snowmanx = 1.7F;
      }

      this.tail.yaw = -_snowman * 0.25F * MathHelper.sin(_snowmanx * 0.6F * animationProgress);
   }
}
