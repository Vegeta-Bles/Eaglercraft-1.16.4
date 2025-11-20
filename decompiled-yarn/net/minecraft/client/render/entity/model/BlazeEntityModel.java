package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class BlazeEntityModel<T extends Entity> extends CompositeEntityModel<T> {
   private final ModelPart[] rods;
   private final ModelPart head = new ModelPart(this, 0, 0);
   private final ImmutableList<ModelPart> parts;

   public BlazeEntityModel() {
      this.head.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.rods = new ModelPart[12];

      for (int _snowman = 0; _snowman < this.rods.length; _snowman++) {
         this.rods[_snowman] = new ModelPart(this, 0, 16);
         this.rods[_snowman].addCuboid(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);
      }

      Builder<ModelPart> _snowman = ImmutableList.builder();
      _snowman.add(this.head);
      _snowman.addAll(Arrays.asList(this.rods));
      this.parts = _snowman.build();
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return this.parts;
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      float _snowman = animationProgress * (float) Math.PI * -0.1F;

      for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
         this.rods[_snowmanx].pivotY = -2.0F + MathHelper.cos(((float)(_snowmanx * 2) + animationProgress) * 0.25F);
         this.rods[_snowmanx].pivotX = MathHelper.cos(_snowman) * 9.0F;
         this.rods[_snowmanx].pivotZ = MathHelper.sin(_snowman) * 9.0F;
         _snowman++;
      }

      _snowman = (float) (Math.PI / 4) + animationProgress * (float) Math.PI * 0.03F;

      for (int _snowmanx = 4; _snowmanx < 8; _snowmanx++) {
         this.rods[_snowmanx].pivotY = 2.0F + MathHelper.cos(((float)(_snowmanx * 2) + animationProgress) * 0.25F);
         this.rods[_snowmanx].pivotX = MathHelper.cos(_snowman) * 7.0F;
         this.rods[_snowmanx].pivotZ = MathHelper.sin(_snowman) * 7.0F;
         _snowman++;
      }

      _snowman = 0.47123894F + animationProgress * (float) Math.PI * -0.05F;

      for (int _snowmanx = 8; _snowmanx < 12; _snowmanx++) {
         this.rods[_snowmanx].pivotY = 11.0F + MathHelper.cos(((float)_snowmanx * 1.5F + animationProgress) * 0.5F);
         this.rods[_snowmanx].pivotX = MathHelper.cos(_snowman) * 5.0F;
         this.rods[_snowmanx].pivotZ = MathHelper.sin(_snowman) * 5.0F;
         _snowman++;
      }

      this.head.yaw = headYaw * (float) (Math.PI / 180.0);
      this.head.pitch = headPitch * (float) (Math.PI / 180.0);
   }
}
