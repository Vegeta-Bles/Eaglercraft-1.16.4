package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BlazeEntityModel<T extends Entity> extends CompositeEntityModel<T> {
   private final ModelPart[] rods;
   private final ModelPart head = new ModelPart(this, 0, 0);
   private final ImmutableList<ModelPart> parts;

   public BlazeEntityModel() {
      this.head.addCuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
      this.rods = new ModelPart[12];

      for (int i = 0; i < this.rods.length; i++) {
         this.rods[i] = new ModelPart(this, 0, 16);
         this.rods[i].addCuboid(0.0F, 0.0F, 0.0F, 2.0F, 8.0F, 2.0F);
      }

      Builder<ModelPart> builder = ImmutableList.builder();
      builder.add(this.head);
      builder.addAll(Arrays.asList(this.rods));
      this.parts = builder.build();
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return this.parts;
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      float k = animationProgress * (float) Math.PI * -0.1F;

      for (int l = 0; l < 4; l++) {
         this.rods[l].pivotY = -2.0F + MathHelper.cos(((float)(l * 2) + animationProgress) * 0.25F);
         this.rods[l].pivotX = MathHelper.cos(k) * 9.0F;
         this.rods[l].pivotZ = MathHelper.sin(k) * 9.0F;
         k++;
      }

      k = (float) (Math.PI / 4) + animationProgress * (float) Math.PI * 0.03F;

      for (int m = 4; m < 8; m++) {
         this.rods[m].pivotY = 2.0F + MathHelper.cos(((float)(m * 2) + animationProgress) * 0.25F);
         this.rods[m].pivotX = MathHelper.cos(k) * 7.0F;
         this.rods[m].pivotZ = MathHelper.sin(k) * 7.0F;
         k++;
      }

      k = 0.47123894F + animationProgress * (float) Math.PI * -0.05F;

      for (int n = 8; n < 12; n++) {
         this.rods[n].pivotY = 11.0F + MathHelper.cos(((float)n * 1.5F + animationProgress) * 0.5F);
         this.rods[n].pivotX = MathHelper.cos(k) * 5.0F;
         this.rods[n].pivotZ = MathHelper.sin(k) * 5.0F;
         k++;
      }

      this.head.yaw = headYaw * (float) (Math.PI / 180.0);
      this.head.pitch = headPitch * (float) (Math.PI / 180.0);
   }
}
